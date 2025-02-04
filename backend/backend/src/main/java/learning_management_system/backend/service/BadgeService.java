package learning_management_system.backend.service;

import learning_management_system.backend.dto.BadgeDto;

import java.util.List;

public interface BadgeService {

    BadgeDto createBadge(BadgeDto badgeDto);

    BadgeDto updateBadge(Long badgeId, BadgeDto badgeDto);

    void deleteBadge(Long badgeId);

    List<BadgeDto> getAllBadges();

    BadgeDto getBadgeById(Long badgeId);

    List<BadgeDto> getBadgesByTenantId(Long tenantId);

    List<BadgeDto> getBadgesByCreator(Long userId);

    List<BadgeDto> getActiveBadges();
}

