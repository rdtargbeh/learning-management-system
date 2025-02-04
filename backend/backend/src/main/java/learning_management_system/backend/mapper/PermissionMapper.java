package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.entity.Permission;
import learning_management_system.backend.entity.Role;
import learning_management_system.backend.repository.PermissionRepository;
import learning_management_system.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring", uses = {RoleMapper.class})

@Component
public class PermissionMapper {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * Convert a Permission entity to a PermissionDto.
     *
     * @param permission the Permission entity.
     * @return the mapped PermissionDto.
     */
    public PermissionDto toDto(Permission permission) {
        if (permission == null) {
            return null;
        }

        PermissionDto dto = new PermissionDto();
        dto.setPermissionId(permission.getPermissionId());
        dto.setPermissionName(permission.getPermissionName());
        dto.setDescription(permission.getDescription());
        dto.setResource(permission.getResource());
        dto.setAction(permission.getAction());
        dto.setScope(permission.getScope());
        dto.setActive(permission.getActive());
        dto.setConditions(permission.getConditions());
        dto.setExpiresAt(permission.getExpiresAt());
        dto.setMetadata(permission.getMetadata());
        dto.setRoleId(permission.getRole() != null ? permission.getRole().getRoleId() : null);
        dto.setParentPermissionId(permission.getParentPermission() != null ? permission.getParentPermission().getPermissionId() : null);

        return dto;
    }

    /**
     * Convert a PermissionDto to a Permission entity.
     *
     * @param dto the PermissionDto.
     * @return the mapped Permission entity.
     */
    public Permission toEntity(PermissionDto dto) {
        if (dto == null) {
            return null;
        }

        Permission permission = new Permission();
        permission.setPermissionId(dto.getPermissionId());
        permission.setPermissionName(dto.getPermissionName());
        permission.setDescription(dto.getDescription());
        permission.setResource(dto.getResource());
        permission.setAction(dto.getAction());
        permission.setScope(dto.getScope());
        permission.setActive(dto.getActive());
        permission.setConditions(dto.getConditions());
        permission.setExpiresAt(dto.getExpiresAt());
        permission.setMetadata(dto.getMetadata());

        // Fetch and set Role
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Role not found with ID: " + dto.getRoleId()));
            permission.setRole(role);
        }

        // Fetch and set Parent Permission
        if (dto.getParentPermissionId() != null) {
            Permission parentPermission = permissionRepository.findById(dto.getParentPermissionId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent Permission not found with ID: " + dto.getParentPermissionId()));
            permission.setParentPermission(parentPermission);
        }

        return permission;
    }


}
