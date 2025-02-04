package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.RoleDto;
import learning_management_system.backend.entity.Role;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.RoleType;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RoleMapper {

    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setDescription(role.getDescription());
        roleDto.setRoleType(RoleType.valueOf(role.getRoleType().name()));
        roleDto.setParentRoleId(role.getParentRole() != null ? role.getParentRole().getRoleId() : null);
        roleDto.setTenantId(role.getTenant() != null ? role.getTenant().getTenantId() : null);
        roleDto.setPriority(role.getPriority());
        roleDto.setActive(role.getActive());
        roleDto.setDateCreated(role.getDateCreated());
        roleDto.setDateUpdated(role.getDateUpdated());
        roleDto.setCreatedBy(role.getCreatedBy() != null ? role.getCreatedBy().getUserId() : null);
        roleDto.setUpdatedBy(role.getUpdatedBy() != null ? role.getUpdatedBy().getUserId() : null);
        roleDto.setDeleted(role.getDeleted());
        roleDto.setMetadata(role.getMetadata());

        return roleDto;
    }

    public Role toEntity(RoleDto roleDto, User createdBy) {
        if (roleDto == null) {
            return null;
        }

        Role role = new Role();
        role.setRoleId(roleDto.getRoleId());
        role.setRoleName(roleDto.getRoleName());
        role.setDescription(roleDto.getDescription());

        // Safely handle RoleType conversion
        String roleTypeStr = String.valueOf(roleDto.getRoleType());
        if (roleTypeStr != null && !roleTypeStr.trim().isEmpty()) {
            try {
                role.setRoleType(RoleType.valueOf(roleTypeStr.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role type: " + roleTypeStr);
            }
        } else {
            role.setRoleType(null); // Handle null or empty RoleType gracefully, or assign a default value
        }

        role.setPriority(roleDto.getPriority());
        role.setActive(roleDto.getActive());
        role.setDateCreated(new Date());
        role.setDateUpdated(new Date());
        role.setDeleted(roleDto.getDeleted());
        role.setMetadata(roleDto.getMetadata());
        role.setCreatedBy(createdBy); // Associate the creator directly

//        role.setDateCreated(roleDto.getDateCreated());
//        role.setDateUpdated(roleDto.getDateUpdated());

        // ParentRole and Tenant should be set in the service layer
        return role;
    }

}

