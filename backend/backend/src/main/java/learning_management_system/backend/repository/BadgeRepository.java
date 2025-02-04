package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    List<Badge> findByTenantTenantId(Long tenantId);

    List<Badge> findByCreatedByUserId(Long userId);

    List<Badge> findByIsActive(Boolean isActive);
}
