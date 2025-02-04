package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.PermissionGroupDto;
import learning_management_system.backend.entity.PermissionGroup;
import learning_management_system.backend.mapper.PermissionGroupMapper;
import learning_management_system.backend.repository.PermissionGroupRepository;
import learning_management_system.backend.repository.PermissionRepository;
import learning_management_system.backend.service.PermissionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionGroupServiceImplementation implements PermissionGroupService {
    @Autowired
    private PermissionGroupRepository permissionGroupRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionGroupMapper permissionGroupMapper;

    @Override
    public PermissionGroupDto createPermissionGroup(PermissionGroupDto permissionGroupDto) {
        PermissionGroup group = permissionGroupMapper.toEntity(permissionGroupDto);
        group.setActive(true); // Default to active
        return permissionGroupMapper.toDto(permissionGroupRepository.save(group));
    }

    @Override
    public PermissionGroupDto updatePermissionGroup(Long groupId, PermissionGroupDto permissionGroupDto) {
        PermissionGroup group = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Permission group not found."));

        group.setGroupName(permissionGroupDto.getGroupName());
        group.setDescription(permissionGroupDto.getDescription());
        group.setActive(permissionGroupDto.getActive());
        return permissionGroupMapper.toDto(permissionGroupRepository.save(group));
    }

    @Override
    public void deletePermissionGroup(Long groupId) {
        if (!permissionGroupRepository.existsById(groupId)) {
            throw new RuntimeException("Permission group not found.");
        }
        permissionGroupRepository.deleteById(groupId);
    }

    @Override
    public PermissionGroupDto getPermissionGroupById(Long groupId) {
        PermissionGroup group = permissionGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Permission group not found."));
        return permissionGroupMapper.toDto(group);
    }

    @Override
    public List<PermissionGroupDto> getAllPermissionGroups() {
        return permissionGroupRepository.findAll().stream()
                .map(permissionGroupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionGroupDto> getActivePermissionGroups() {
        return permissionGroupRepository.findByIsActive(true).stream()
                .map(permissionGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}

