package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.BadgeDto;
import learning_management_system.backend.entity.Badge;
import learning_management_system.backend.mapper.BadgeMapper;
import learning_management_system.backend.repository.BadgeRepository;
import learning_management_system.backend.repository.TenantRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BadgeServiceImplementation implements BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private BadgeMapper badgeMapper;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;


    // Create a new badge
    @Override
    public BadgeDto createBadge(BadgeDto badgeDto) {
        Badge badge = badgeMapper.toEntity(badgeDto);

        badge.setTenant(tenantRepository.findById(badgeDto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + badgeDto.getTenantId())));

        badge.setCreatedBy(userRepository.findById(badgeDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + badgeDto.getCreatedBy())));

        return badgeMapper.toDto(badgeRepository.save(badge));
    }

    // Update a Badge
    @Override
    public BadgeDto updateBadge(Long badgeId, BadgeDto badgeDto) {
        // Retrieve the existing badge
        Badge existingBadge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge not found with ID: " + badgeId));

        // Manually update fields from the DTO to the entity
        existingBadge.setName(badgeDto.getName());
        existingBadge.setDescription(badgeDto.getDescription());
        existingBadge.setIconUrl(badgeDto.getIconUrl());
        existingBadge.setCriteria(badgeDto.getCriteria());
        existingBadge.setActive(badgeDto.getActive());
        existingBadge.setMetadata(badgeDto.getMetadata());
        existingBadge.setDateUpdated(new Date());

        // Update tenant if provided
        if (badgeDto.getTenantId() != null) {
            existingBadge.setTenant(tenantRepository.findById(badgeDto.getTenantId())
                    .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + badgeDto.getTenantId())));
        }

        // Update createdBy user if provided
        if (badgeDto.getCreatedBy() != null) {
            existingBadge.setCreatedBy(userRepository.findById(badgeDto.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + badgeDto.getCreatedBy())));
        }

        // Save the updated badge and convert it back to DTO
        return badgeMapper.toDto(badgeRepository.save(existingBadge));
    }


    // Delete badge
    @Override
    public void deleteBadge(Long badgeId) {
        badgeRepository.deleteById(badgeId);
    }

    // Get All Badges
    @Override
    public List<BadgeDto> getAllBadges() {
        return badgeRepository.findAll()
                .stream()
                .map(badgeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Badge by Id
    @Override
    public BadgeDto getBadgeById(Long badgeId) {
        return badgeRepository.findById(badgeId)
                .map(badgeMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Badge not found with ID: " + badgeId));
    }

    // Get badge by Tenant ID
    @Override
    public List<BadgeDto> getBadgesByTenantId(Long tenantId) {
        return badgeRepository.findByTenantTenantId(tenantId)
                .stream()
                .map(badgeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Badge by Creator
    @Override
    public List<BadgeDto> getBadgesByCreator(Long userId) {
        return badgeRepository.findByCreatedByUserId(userId)
                .stream()
                .map(badgeMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Active Badge
    @Override
    public List<BadgeDto> getActiveBadges() {
        return badgeRepository.findByIsActive(true)
                .stream()
                .map(badgeMapper::toDto)
                .collect(Collectors.toList());
    }
}

