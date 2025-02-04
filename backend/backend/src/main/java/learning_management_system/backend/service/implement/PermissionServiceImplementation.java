package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.PermissionDto;
import learning_management_system.backend.entity.Permission;
import learning_management_system.backend.entity.PermissionGroup;
import learning_management_system.backend.entity.Role;
import learning_management_system.backend.mapper.PermissionMapper;
import learning_management_system.backend.repository.PermissionGroupRepository;
import learning_management_system.backend.repository.PermissionRepository;
import learning_management_system.backend.repository.RoleRepository;
import learning_management_system.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImplementation implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;


    @Override
    public PermissionDto createPermission(PermissionDto permissionDto) {
        // Validate uniqueness of permission name
        permissionRepository.findByPermissionName(permissionDto.getPermissionName())
                .ifPresent(existingPermission -> {
                    throw new RuntimeException("Permission with the same name already exists.");
                });

        // Map DTO to entity
        Permission permission = permissionMapper.toEntity(permissionDto);

        // Directly assign the action as it's already a PermissionAction
        permission.setAction(permissionDto.getAction());

        // Validate parent permission if provided
        if (permissionDto.getParentPermissionId() != null) {
            Permission parentPermission = permissionRepository.findById(permissionDto.getParentPermissionId())
                    .orElseThrow(() -> new RuntimeException("Parent permission not found."));
            Permission.validateParentPermission(permission, parentPermission);
            permission.setParentPermission(parentPermission);
        }

        // Associate role if provided
        if (permissionDto.getRoleId() != null) {
            Role role = roleRepository.findById(permissionDto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found."));
            permission.setRole(role);
        }

        // Save permission
        Permission savedLmsPermission = permissionRepository.save(permission);
        return permissionMapper.toDto(savedLmsPermission);
    }


    @Override
    public PermissionDto updatePermission(Long permissionId, PermissionDto permissionDto) {
        Permission existingPermission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));

        // Validate uniqueness of updated permission name
        permissionRepository.findByPermissionName(permissionDto.getPermissionName())
                .ifPresent(existing -> {
                    if (!existing.getPermissionId().equals(permissionId)) {
                        throw new RuntimeException("Permission with the same name already exists.");
                    }
                });

        // Update fields
        existingPermission.setPermissionName(permissionDto.getPermissionName());
        existingPermission.setDescription(permissionDto.getDescription());
        existingPermission.setResource(permissionDto.getResource());
        existingPermission.setScope(permissionDto.getScope());
        existingPermission.setConditions(permissionDto.getConditions());
        existingPermission.setExpiresAt(permissionDto.getExpiresAt());
        existingPermission.setActive(permissionDto.getActive());
        existingPermission.setMetadata(permissionDto.getMetadata());

        // Directly assign the action as it's already a PermissionAction
        existingPermission.setAction(permissionDto.getAction());

        // Update parent permission if applicable
        if (permissionDto.getParentPermissionId() != null) {
            Permission parentPermission = permissionRepository.findById(permissionDto.getParentPermissionId())
                    .orElseThrow(() -> new RuntimeException("Parent permission not found."));
            Permission.validateParentPermission(existingPermission, parentPermission);
            existingPermission.setParentPermission(parentPermission);
        }

        // Save updated permission
        Permission updatedPermission = permissionRepository.save(existingPermission);
        return permissionMapper.toDto(updatedPermission);
    }



    @Override
    public void deletePermission(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));
        permission.setDeleted(true);
        permissionRepository.save(permission);
    }

    @Override
    public List<PermissionDto> getAllPermissions() {
        return permissionRepository.findByIsDeletedFalse().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public PermissionDto getPermissionById(Long permissionId) {
        Permission permission = permissionRepository.findByIdAndIsDeletedFalse(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found."));
        return permissionMapper.toDto(permission);
    }


    @Override
    public List<PermissionDto> getPermissionsByRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found."));

        return role.getPermissions().stream()
                .filter(permission -> permission.isActiveAndValid()) // Ensure 'permission' is of type 'Permission'
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<PermissionDto> getPermissionsByResource(String resource) {
        return permissionRepository.findByResourceAndIsDeletedFalse(resource).stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDto> getActivePermissions() {
        return permissionRepository.findByIsActiveTrueAndIsDeletedFalse().stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionDto> getChildPermissions(Permission parentPermission) {
        return permissionRepository.findByParentPermissionAndIsDeletedFalse(parentPermission).stream()
                .map(permissionMapper::toDto)
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 * * * ?") // Every hour
    public void deactivateExpiredPermissions() {
        List<Permission> expiredPermissions = permissionRepository.findExpiredPermissions();
        expiredPermissions.forEach(permission -> permission.setActive(false));
        permissionRepository.saveAll(expiredPermissions);
    }


    @Override
    @Transactional
    public void addPermissionsToGroup(Long groupId, List<Long> permissionIds) {
        // Fetch the permission group
        PermissionGroup group = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Permission group not found."));

        // Fetch permissions to be added
        List<Permission> lmsPermissions = permissionRepository.findAllById(permissionIds);

        // Ensure permissions exist
        if (lmsPermissions.isEmpty()) {
            throw new RuntimeException("No valid permissions found for the provided IDs.");
        }

        // Assign permissions to the group
        lmsPermissions.forEach(permission -> {
            if (permission.getGroup() != null && !permission.getGroup().getGroupId().equals(groupId)) {
                throw new RuntimeException("Permission already belongs to another group: " + permission.getPermissionName());
            }
            permission.setGroup(group);
        });

        // Add permissions to the group
        group.getPermissions().addAll(lmsPermissions);

        // Save the updated group
        permissionGroupRepository.save(group);
    }


    @Transactional
    public void removePermissionsFromGroup(Long groupId, List<Long> permissionIds) {
        PermissionGroup group = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Permission group not found."));

        List<Permission> lmsPermissions = permissionRepository.findAllById(permissionIds);

        lmsPermissions.forEach(permission -> {
            if (permission.getGroup() == null || !permission.getGroup().getGroupId().equals(groupId)) {
                throw new RuntimeException("Permission does not belong to the specified group: " + permission.getPermissionName());
            }
            permission.setGroup(null); // Remove the association
        });

        group.getPermissions().removeAll(lmsPermissions);
        permissionGroupRepository.save(group);
    }




}

