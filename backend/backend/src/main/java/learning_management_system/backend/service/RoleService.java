package learning_management_system.backend.service;

import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.dto.RoleDto;
import learning_management_system.backend.entity.Role;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

//    RoleDto createRole(RoleDto roleDto);

    void deleteRole(Long roleId);

    Set<User> getUsersByRoleId(Long roleId);

    RoleDto updateRole(Long roleId, RoleDto roleDto);

    RoleDto getRoleById(Long roleId);

    List<RoleDto> getRolesByTenant(Long tenantId);

    List<RoleDto> getRolesByType(RoleType roleType);

    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);

    Set<PermissionDto> getEffectivePermissions(Long roleId);

    void removePermissionsFromRole(Long roleId, List<Long> permissionIds);

    Page<RoleDto> getRoles(Pageable pageable);


    Page<RoleDto> getRolesByCriteria(String roleType, Boolean isActive, Long tenantId, Pageable pageable);


    RoleDto createRole(RoleDto roleDto, Long creatorId);


//    Optional<Role> getRoleById(Long roleId);

//    Role createRole(Role role);

//    Role updateRole(Long roleId, Role roleDetails);




}
