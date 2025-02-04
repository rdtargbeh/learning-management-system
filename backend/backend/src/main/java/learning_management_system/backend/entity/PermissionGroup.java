package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@Entity
@Table(name = "permission_groups")
public class PermissionGroup {

    /** Unique identifier for the permission group. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false, updatable = false)
    private Long groupId;

    /** Name of the permission group. */
    @Column(name = "group_name", nullable = false, unique = true, length = 100)
    private String groupName;

    /** Description of the permission group. */
    @Column(name = "description", length = 255)
    private String description;

    /** Status to indicate whether the group is active or inactive. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** The tenant to which this permission group belongs (for multi-tenant systems). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /** Permissions associated with this group. */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Permission> permissions = new HashSet<>();

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Date and time when the group was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Date and time when the group was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** User who created the group. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** User who last updated the group. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;


    public PermissionGroup() {
    }

    public PermissionGroup(Long groupId, String groupName, String description, Boolean isActive, Tenant tenant, Set<Permission> permissions, String metadata, Date dateCreated, Date dateUpdated, User createdBy, User updatedBy) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isActive = isActive;
        this.tenant = tenant;
        this.permissions = permissions;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
}


