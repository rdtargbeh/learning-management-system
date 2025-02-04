package learning_management_system.backend.controller;

import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.dto.RoleDto;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RoleType;
import learning_management_system.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;


    // Build A Get All Roles REST API
    @GetMapping
    public ResponseEntity<Page<RoleDto>> getRoles(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<RoleDto> roles = roleService.getRoles((Pageable) PageRequest.of(page, size));
        return ResponseEntity.ok(roles);
    }

//    @GetMapping
//    public ResponseEntity<List<Role>> getAllRoles() {
//        List<Role> roles = roleService.getAllRoles();
//        return ResponseEntity.ok(roles);
//    }

    // Build A Get Role By ID REST API
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }


    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) {
        try {
            // ✅ Ensure createdBy is present in request body
            if (roleDto.getCreatedBy() == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("error", "CreatedBy (creator ID) is required"));
            }

            RoleDto createdRole = roleService.createRole(roleDto, roleDto.getCreatedBy());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

//    @PostMapping
//    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto, @RequestParam Long creatorId) {
//        try {
//            RoleDto createdRole = roleService.createRole(roleDto, creatorId);
//            return ResponseEntity.ok(createdRole);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

//    @PostMapping
//    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto, @RequestParam Long creatorId) {
//        System.out.println("Received creatorId: " + creatorId);
//        System.out.println("Received roleDto: " + roleDto); // This will print null if the body is empty or malformed
//
//        try {
//            RoleDto createdRole = roleService.createRole(roleDto, creatorId);
//            return ResponseEntity.ok(createdRole);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }




    // Build A Create Role REST API
//    @PostMapping
//    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto role) {
//        try {
//            RoleDto createdRole = roleService.createRole(role);
//            return ResponseEntity.ok(createdRole);
//        } catch (IllegalArgumentException e) {
//            // Return a bad request response with a descriptive message
//            return ResponseEntity.badRequest().build();
//        } catch (Exception e) {
//            // Return an internal server error response
//            return ResponseEntity.status(500).build();
//        }
//    }

    // Build A Update Role REST API
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDetails) {
        try {
            RoleDto updatedRole = roleService.updateRole(roleId, roleDetails);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{roleId}")
//    public ResponseEntity<RoleDto> updateRole(@PathVariable Long roleId, @RequestBody RoleDto roleDto) {
//        try {
//            RoleDto updatedRole = roleService.updateRole(roleId, roleDto);
//            return ResponseEntity.ok(updatedRole);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    // Build A Delete Role REST API
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Build A Get Users By Role ID REST API
    @GetMapping("/{roleId}/users")
    public ResponseEntity<Set<User>> getUsersByRoleId(@PathVariable Long roleId) {
        Set<User> users = roleService.getUsersByRoleId(roleId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<RoleDto>> getRolesByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(roleService.getRolesByTenant(tenantId));
    }


    @GetMapping("/type/{roleType}")
    public ResponseEntity<List<RoleDto>> getRolesByType(@PathVariable RoleType roleType) {
        return ResponseEntity.ok(roleService.getRolesByType(roleType));
    }


    /**
     * Assign permissions to a role.
     *
     * @param roleId        the ID of the role
     * @param permissionIds the list of permission IDs to assign
     * @return HTTP status 200 if successful
     */
    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<Void> assignPermissionsToRole(@PathVariable Long roleId,
                                                        @RequestBody List<Long> permissionIds) {
        roleService.assignPermissionsToRole(roleId, permissionIds);
        return ResponseEntity.ok().build();
    }


    /**
     * Remove permissions from a role.
     */
    @DeleteMapping("/roles/{roleId}/permissions")
    public ResponseEntity<Void> removePermissionsFromRole(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        roleService.removePermissionsFromRole(roleId, permissionIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/roles")
    public Page<RoleDto> getRolesByCriteria(
            @RequestParam(required = false) String roleType,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Long tenantId,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return roleService.getRolesByCriteria(roleType, isActive, tenantId, pageable);
    }

    /**
     * Fetches the effective permissions for a given role.
     *
     * @param roleId The ID of the role.
     * @return A set of effective permissions.
     */
    @GetMapping("/{roleId}/permissions")
    public Set<PermissionDto> getEffectivePermissions(@PathVariable Long roleId) {
        return roleService.getEffectivePermissions(roleId);
    }


//    @GetMapping("/{roleId}/effective-permissions")
//    public ResponseEntity<List<PermissionDto>> getEffectivePermissions(@PathVariable Long roleId) {
//        return ResponseEntity.ok(roleService.getEffectivePermissions(roleId));
//    }





//    @GetMapping("/{id}")
//    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
//        Optional<Role> role = roleService.getRoleById(id);
//        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    //    @PostMapping
//    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
//        return ResponseEntity.ok(roleService.createRole(roleDto));
//    }
}