package learning_management_system.backend.dto;

import learning_management_system.backend.enums.PermissionAction;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for the Permission entity.
 */
public class PermissionDto {

    private Long permissionId;           // Unique identifier for the permission
    private String permissionName;       // Name of the permission (e.g., "CREATE_COURSE")
    private String description;          // Brief description of the permission
    private String resource;             // Resource type (e.g., "COURSE", "USER")
    private PermissionAction action; // Enum instead of String
    private String scope;                // Scope of permission (e.g., SYSTEM, TENANT, RESOURCE)
    private Long roleId;                 // ID of the associated role
    private Boolean isActive;            // Whether the permission is active
    private String conditions;           // Conditional logic in JSON or plain text
    private LocalDateTime expiresAt;     // Expiration timestamp for the permission
    private Long parentPermissionId;     // ID of the parent permission for hierarchy
    private String metadata;            // Custom metadata

    // Constructor

    public PermissionDto() {}

    public PermissionDto(Long permissionId, String permissionName, String description, String resource, PermissionAction action, String scope, Long roleId, Boolean isActive, String conditions, LocalDateTime expiresAt, Long parentPermissionId, String metadata) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.description = description;
        this.resource = resource;
        this.action = action;
        this.scope = scope;
        this.roleId = roleId;
        this.isActive = isActive;
        this.conditions = conditions;
        this.expiresAt = expiresAt;
        this.parentPermissionId = parentPermissionId;
        this.metadata = metadata;
    }

// Getter and Setter
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public Long getParentPermissionId() {
        return parentPermissionId;
    }

    public void setParentPermissionId(Long parentPermissionId) {
        this.parentPermissionId = parentPermissionId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
