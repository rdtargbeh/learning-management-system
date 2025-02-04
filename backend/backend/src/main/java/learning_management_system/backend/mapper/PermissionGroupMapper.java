package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.PermissionGroupDto;
import learning_management_system.backend.entity.Permission;
import learning_management_system.backend.entity.PermissionGroup;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.repository.PermissionRepository;
import learning_management_system.backend.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", uses = {PermissionMapper.class})

@Component
public class PermissionGroupMapper {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private TenantRepository tenantRepository;

    /**
     * Convert a PermissionGroup entity to a PermissionGroupDto.
     *
     * @param permissionGroup the PermissionGroup entity.
     * @return the mapped PermissionGroupDto.
     */
    public PermissionGroupDto toDto(PermissionGroup permissionGroup) {
        if (permissionGroup == null) {
            return null;
        }

        PermissionGroupDto dto = new PermissionGroupDto();
        dto.setGroupId(permissionGroup.getGroupId());
        dto.setGroupName(permissionGroup.getGroupName());
        dto.setDescription(permissionGroup.getDescription());
        dto.setActive(permissionGroup.getActive());
        dto.setTenantId(permissionGroup.getTenant() != null ? permissionGroup.getTenant().getTenantId() : null);

        // Map permissions to their IDs
        if (permissionGroup.getPermissions() != null) {
            dto.setPermissionIds(permissionGroup.getPermissions().stream()
                    .map(Permission::getPermissionId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Convert a PermissionGroupDto to a PermissionGroup entity.
     *
     * @param dto the PermissionGroupDto.
     * @return the mapped PermissionGroup entity.
     */
    public PermissionGroup toEntity(PermissionGroupDto dto) {
        if (dto == null) {
            return null;
        }

        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setGroupId(dto.getGroupId());
        permissionGroup.setGroupName(dto.getGroupName());
        permissionGroup.setDescription(dto.getDescription());
        permissionGroup.setActive(dto.getActive());

        // Fetch and set Tenant
        if (dto.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + dto.getTenantId()));
            permissionGroup.setTenant(tenant);
        }

        // Fetch and set Permissions
        if (dto.getPermissionIds() != null) {
            List<Permission> permissions = permissionRepository.findAllById(dto.getPermissionIds());
            permissionGroup.setPermissions(new HashSet<>(permissions));
        }

        return permissionGroup;
    }

}

