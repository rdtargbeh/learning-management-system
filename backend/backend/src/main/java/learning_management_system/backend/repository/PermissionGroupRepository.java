package learning_management_system.backend.repository;

import learning_management_system.backend.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {

    List<PermissionGroup> findByTenant_TenantId(Long tenantId); // Fetch groups by tenant

    List<PermissionGroup> findByIsActive(Boolean isActive); // Fetch active groups
}

