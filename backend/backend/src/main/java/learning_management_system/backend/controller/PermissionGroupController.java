package learning_management_system.backend.controller;

import learning_management_system.backend.dto.PermissionGroupDto;
import learning_management_system.backend.service.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission-groups")
public class PermissionGroupController {

    @Autowired
    private PermissionGroupService permissionGroupService;


    @PostMapping
    public ResponseEntity<PermissionGroupDto> createPermissionGroup(@RequestBody PermissionGroupDto permissionGroupDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionGroupService.createPermissionGroup(permissionGroupDto));
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<PermissionGroupDto> updatePermissionGroup(@PathVariable Long groupId,
                                                                    @RequestBody PermissionGroupDto permissionGroupDto) {
        return ResponseEntity.ok(permissionGroupService.updatePermissionGroup(groupId, permissionGroupDto));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deletePermissionGroup(@PathVariable Long groupId) {
        permissionGroupService.deletePermissionGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<PermissionGroupDto> getPermissionGroupById(@PathVariable Long groupId) {
        return ResponseEntity.ok(permissionGroupService.getPermissionGroupById(groupId));
    }

    @GetMapping
    public ResponseEntity<List<PermissionGroupDto>> getAllPermissionGroups() {
        return ResponseEntity.ok(permissionGroupService.getAllPermissionGroups());
    }

    @GetMapping("/active")
    public ResponseEntity<List<PermissionGroupDto>> getActivePermissionGroups() {
        return ResponseEntity.ok(permissionGroupService.getActivePermissionGroups());
    }
}

