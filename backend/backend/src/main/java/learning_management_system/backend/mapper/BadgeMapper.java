package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.BadgeDto;
import learning_management_system.backend.entity.Badge;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
public class BadgeMapper {

    private final UserRepository userRepository; // For fetching User entities if needed.

    @Autowired
    public BadgeMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Converts a `Badge` entity to a `BadgeDto`.
     *
     * @param badge The `Badge` entity.
     * @return The corresponding `BadgeDto`.
     */
    public BadgeDto toDto(Badge badge) {
        if (badge == null) {
            return null;
        }

        BadgeDto dto = new BadgeDto();
        dto.setBadgeId(badge.getBadgeId());
        dto.setName(badge.getName());
        dto.setDescription(badge.getDescription());
        dto.setIconUrl(badge.getIconUrl());
        dto.setCriteria(badge.getCriteria());
        dto.setTenantId(badge.getTenant() != null ? badge.getTenant().getTenantId() : null); // Extract Tenant ID
        dto.setCreatedBy(badge.getCreatedBy() != null ? badge.getCreatedBy().getUserId() : null); // Extract Creator ID
        dto.setActive(badge.getActive());
        dto.setMetadata(badge.getMetadata());
        dto.setDateCreated(badge.getDateCreated());
        dto.setDateUpdated(badge.getDateUpdated());

        return dto;
    }

    /**
     * Converts a `BadgeDto` to a `Badge` entity.
     *
     * @param dto The `BadgeDto`.
     * @return The corresponding `Badge` entity.
     */
    public Badge toEntity(BadgeDto dto) {
        if (dto == null) {
            return null;
        }

        Badge badge = new Badge();
        badge.setBadgeId(dto.getBadgeId());
        badge.setName(dto.getName());
        badge.setDescription(dto.getDescription());
        badge.setIconUrl(dto.getIconUrl());
        badge.setCriteria(dto.getCriteria());
        badge.setActive(dto.getActive());
        badge.setMetadata(dto.getMetadata());
        badge.setDateCreated(dto.getDateCreated());
        badge.setDateUpdated(dto.getDateUpdated());

        // Fetch and set the Tenant entity from Tenant ID
        if (dto.getTenantId() != null) {
            Tenant tenant = new Tenant();
            tenant.setTenantId(dto.getTenantId());
            badge.setTenant(tenant); // Set only ID to avoid unnecessary database fetch.
        }

        // Fetch and set the CreatedBy User entity from CreatedBy ID
        if (dto.getCreatedBy() != null) {
            User createdBy = userRepository.findById(dto.getCreatedBy())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getCreatedBy()));
            badge.setCreatedBy(createdBy);
        }

        return badge;
    }


}
