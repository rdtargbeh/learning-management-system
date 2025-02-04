package learning_management_system.backend.dto;

import java.util.List;

public class PermissionGroupDto {

    private Long groupId;
    private String groupName;
    private String description;
    private Boolean isActive;
    private List<Long> permissionIds; // IDs of associated permissions
    private Long tenantId; // If tenant-specific groups are supported


    // Constructor
    public PermissionGroupDto() {}

    public PermissionGroupDto(Long groupId, String groupName, String description, Boolean isActive,
                              List<Long> permissionIds, Long tenantId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isActive = isActive;
        this.permissionIds = permissionIds;
        this.tenantId = tenantId;
    }


// Getter and Setter
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

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}

