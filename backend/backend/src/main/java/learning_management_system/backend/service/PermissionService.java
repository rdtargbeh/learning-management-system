package learning_management_system.backend.service;

import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.entity.Permission;

import java.util.List;

public interface PermissionService {
    PermissionDto createPermission(PermissionDto lmsPermissionDto);

    PermissionDto updatePermission(Long permissionId, PermissionDto lmsPermissionDto);

    void deletePermission(Long permissionId);

    List<PermissionDto> getAllPermissions();

    PermissionDto getPermissionById(Long permissionId);

    List<PermissionDto> getPermissionsByRole(Long roleId);

    List<PermissionDto> getPermissionsByResource(String resource);

    List<PermissionDto> getActivePermissions();

    List<PermissionDto> getChildPermissions(Permission parentPermission);
//    List<PermissionDto> getChildPermissions(Long parentPermissionId);


    void addPermissionsToGroup(Long groupId, List<Long> permissionIds);

}
