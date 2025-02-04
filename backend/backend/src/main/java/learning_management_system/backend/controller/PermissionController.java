package learning_management_system.backend.controller;

import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.entity.Permission;
import learning_management_system.backend.service.PermissionService;
import learning_management_system.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;



    /**
     * Create a new permission.
     *
     * @param permissionDto The DTO containing permission details.
     * @return The created permission as a DTO.
     */
    @PostMapping
    public ResponseEntity<PermissionDto> createPermission(@RequestBody PermissionDto permissionDto) {
        try {
            PermissionDto createdPermission = permissionService.createPermission(permissionDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPermission);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    /**
     * Update an existing permission.
     *
     * @param permissionId  The ID of the permission to update.
     * @param lmsPermissionDto The updated permission details.
     * @return The updated permission.
     */
    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> updatePermission(@PathVariable Long permissionId,
                                                             @RequestBody PermissionDto lmsPermissionDto) {
        PermissionDto updatedPermission = permissionService.updatePermission(permissionId, lmsPermissionDto);
        return ResponseEntity.ok(updatedPermission);
    }

    /**
     * Delete a permission.
     *
     * @param permissionId The ID of the permission to delete.
     */
    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all permissions.
     *
     * @return List of permissions.
     */
    @GetMapping
    public ResponseEntity<List<PermissionDto>> getAllPermissions() {
        List<PermissionDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    /**
     * Get a permission by ID.
     *
     * @param permissionId The ID of the permission.
     * @return The permission details.
     */
    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionDto> getPermissionById(@PathVariable Long permissionId) {
        PermissionDto permission = permissionService.getPermissionById(permissionId);
        return ResponseEntity.ok(permission);
    }

    /**
     * Get permissions by role.
     *
     * @param roleId The ID of the role.
     * @return List of permissions for the role.
     */
    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<PermissionDto>> getPermissionsByRole(@PathVariable Long roleId) {
        List<PermissionDto> permissions = permissionService.getPermissionsByRole(roleId);
        return ResponseEntity.ok(permissions);
    }

    /**
     * Get permissions by resource type.
     *
     * @param resource The resource type (e.g., "COURSE", "USER").
     * @return List of permissions for the resource.
     */
    @GetMapping("/resource/{resource}")
    public ResponseEntity<List<PermissionDto>> getPermissionsByResource(@PathVariable String resource) {
        List<PermissionDto> permissions = permissionService.getPermissionsByResource(resource);
        return ResponseEntity.ok(permissions);
    }

    /**
     * Get all active permissions.
     *
     * @return List of active permissions.
     */
    @GetMapping("/active")
    public ResponseEntity<List<PermissionDto>> getActivePermissions() {
        List<PermissionDto> permissions = permissionService.getActivePermissions();
        return ResponseEntity.ok(permissions);
    }

    /**
     * Get child permissions for a parent permission.
     *
     * @param parentPermission The ID of the parent permission.
     * @return List of child permissions.
     */
    @GetMapping("/parent/{parentPermissionId}")
    public ResponseEntity<List<PermissionDto>> getChildPermissions(@PathVariable Permission parentPermission) {
        List<PermissionDto> permissions = permissionService.getChildPermissions(parentPermission);
        return ResponseEntity.ok(permissions);
    }


    @PostMapping("/group/{groupId}")
    public ResponseEntity<Void> addPermissionsToGroup(@PathVariable Long groupId, @RequestBody List<Long> permissionIds) {
        permissionService.addPermissionsToGroup(groupId, permissionIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

