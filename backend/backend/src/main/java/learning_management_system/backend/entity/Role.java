package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.RoleType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin("*")
@Entity
@Table(name = "roles")
@Access(AccessType.FIELD) // Explicitly set field access
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;  // e.g., "Super Admin", "Tenant Admin", "Teacher", etc.

    @Column(name = "description", length = 255)
    private String description;  // Brief description of the role's purpose

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;  // GLOBAL, TENANT, or SYSTEM

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_role_id")
    private Role parentRole;  // Optional: for hierarchical relationships (inheritance)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;  // Null for global roles like "Super Admin"

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @Column(name = "priority", nullable = false)
    private Integer priority;  // Defines precedence in case of conflicts (lower = higher precedence)

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;  // Status of the role (active/inactive)

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permission> permissions = new HashSet<>(); // Associated permissions

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();  // When the role was created

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();  // When the role was last updated

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;  // User who created the role

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;  // User who last updated the role

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;  // Optional: JSON or custom data for tenant-specific customization


//    // Constructor
    public Role(){}

    public Role(Long roleId, String roleName, String description, RoleType roleType, Role parentRole, Tenant tenant,
                Set<User> users, Integer priority, Boolean isActive, Set<Permission> permissions, Date dateCreated,
                Date dateUpdated, User createdBy, User updatedBy, Boolean isDeleted, String metadata) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.roleType = roleType;
        this.parentRole = parentRole;
        this.tenant = tenant;
        this.users = users;
        this.priority = priority;
        this.isActive = true;
        this.permissions = permissions;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDeleted = false;
        this.metadata = metadata;
    }

    // Getters and Setter

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role getParentRole() {
        return parentRole;
    }

    public void setParentRole(Role parentRole) {
        this.parentRole = parentRole;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}