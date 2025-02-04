package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.PermissionAction;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Date;

@CrossOrigin("*")
@Entity
@Table(name = "permissions")
public class Permission {

    /** Unique identifier for the permission. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionId;

    /** Name of the permission (e.g., "CREATE_COURSE"). */
    @Column(name = "permission_name", nullable = false, unique = true, length = 100)
    private String permissionName;

    /** Brief explanation of the permission. */
    @Column(name = "description", length = 255)
    private String description;

    /** Resource type associated with the permission (e.g., "COURSE", "USER"). */
    @Column(name = "resource", nullable = false, length = 100)
    private String resource;

    /** Action type (e.g., CREATE, READ, UPDATE, DELETE). */
    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private PermissionAction action;

    /** Scope of the permission (e.g., SYSTEM, TENANT, RESOURCE). */
    @Column(name = "scope", nullable = false, length = 50)
    private String scope;

    /** Associated role for this permission. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    /** Whether the permission is active. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Custom conditional logic for the permission. */
    @Column(name = "conditions", columnDefinition = "TEXT")
    private String conditions;

    /** Expiration timestamp for the permission. */
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    /** Timestamp for when the permission was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated;

    /** Timestamp for when the permission was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated;

    /** Parent permission for hierarchical relationships. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_permission_id")
    private Permission parentPermission;

    /** Custom metadata for tenant-specific or conditional requirements. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Soft deletion flag. */
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "priority", nullable = false)
    private Integer priority; // Lower value = higher precedence

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private PermissionGroup group; // Logical grouping of permissions


    /**
     * Determines if the permission is active and valid.
     *
     * @return true if the permission is active and not expired, false otherwise.
     */
    public  boolean isActiveAndValid() {
        if (!isActive) {
            return false;
        }
        return expiresAt == null || expiresAt.isAfter(LocalDateTime.now());
    }

    /**
     * Combines conditions from parent permissions.
     *
     * @return Combined conditions for the permission.
     */
    public String getEffectiveConditions() {
        StringBuilder effectiveConditions = new StringBuilder(conditions != null ? conditions : "");
        Permission parent = parentPermission;
        while (parent != null) {
            if (parent.getConditions() != null) {
                effectiveConditions.append(parent.getConditions());
            }
            parent = parent.getParentPermission();
        }
        return effectiveConditions.toString();
    }

    /**
     * Validates that parent permissions do not create circular references.
     *
     * @param lmsPermission        The current permission being validated.
     * @param parentLmsPermission  The parent permission to check.
     */
    public static void validateParentPermission(Permission lmsPermission, Permission parentLmsPermission) {
        Permission current = parentLmsPermission;
        while (current != null) {
            if (current.equals(lmsPermission)) {
                throw new RuntimeException("Cyclic permission hierarchy detected.");
            }
            current = current.getParentPermission();
        }
    }


    public Permission() {
    }

    public Permission(Long permissionId, String permissionName, String description, String resource, PermissionAction action, String scope, Role role, Boolean isActive, String conditions, LocalDateTime expiresAt, Date dateCreated, Date dateUpdated, Permission parentPermission, String metadata, Boolean isDeleted, Integer priority, PermissionGroup group) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.description = description;
        this.resource = resource;
        this.action = action;
        this.scope = scope;
        this.role = role;
        this.isActive = isActive;
        this.conditions = conditions;
        this.expiresAt = expiresAt;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.parentPermission = parentPermission;
        this.metadata = metadata;
        this.isDeleted = isDeleted;
        this.priority = priority;
        this.group = group;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public PermissionAction getAction() {
        return action;
    }

    public void setAction(PermissionAction action) {
        this.action = action;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Permission getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(Permission parentPermission) {
        this.parentPermission = parentPermission;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public PermissionGroup getGroup() {
        return group;
    }

    public void setGroup(PermissionGroup group) {
        this.group = group;
    }
}
