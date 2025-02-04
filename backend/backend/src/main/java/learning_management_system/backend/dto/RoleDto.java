package learning_management_system.backend.dto;

import learning_management_system.backend.enums.RoleType;

import java.util.Date;

public class RoleDto {

    private Long roleId;
    private String roleName;
    private String description;
    private RoleType roleType;
    private Long parentRoleId; // ID of the parent role for hierarchical relationships
    private Long tenantId; // Null for global roles
    private Integer priority;
    private Boolean isActive = true;  // ✅ Set default value to TRUE;
    private Long createdBy;
    private Long updatedBy;
    private Boolean isDeleted =  false; // ✅ Set default value to FALSE
    private String metadata; // JSON or custom data
    private Date dateCreated;
    private Date dateUpdated;



    // Constructor
    public RoleDto() {}

    public RoleDto(Long roleId, String roleName, String description, RoleType roleType, Long parentRoleId, Long tenantId,
                   Integer priority, Boolean isActive, Long createdBy, Long updatedBy, String metadata, Boolean isDeleted,
                   Date dateCreated, Date dateUpdated) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
        this.roleType = roleType;
        this.parentRoleId = parentRoleId;
        this.tenantId = tenantId;
        this.priority = priority;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.metadata = metadata;
        this.isDeleted = isDeleted;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

// Getter and Setter
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

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
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
}

