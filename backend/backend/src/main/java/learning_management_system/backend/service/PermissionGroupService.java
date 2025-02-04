package learning_management_system.backend.service;

import learning_management_system.backend.dto.PermissionGroupDto;

import java.util.List;

public interface PermissionGroupService {
    PermissionGroupDto createPermissionGroup(PermissionGroupDto permissionGroupDto);

    PermissionGroupDto updatePermissionGroup(Long groupId, PermissionGroupDto permissionGroupDto);

    void deletePermissionGroup(Long groupId);

    PermissionGroupDto getPermissionGroupById(Long groupId);

    List<PermissionGroupDto> getAllPermissionGroups();

    List<PermissionGroupDto> getActivePermissionGroups();
}

