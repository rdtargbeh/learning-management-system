package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.utility.TenantSummaryDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findByDomain(String domain);

    List<Tenant> findBySubscriptionPlan(String subscriptionPlan);

    List<Tenant> findByIsSubscriptionActive(Boolean isSubscriptionActive);

    @Query("SELECT t FROM Tenant t WHERE t.maxUsers >= :minUsers")
    List<Tenant> findByMinUsers(@Param("minUsers") Integer minUsers);

    @Query("SELECT t FROM Tenant t WHERE t.subscriptionEnd BETWEEN :startDate AND :endDate")
    List<Tenant> findTenantsWithSubscriptionExpiringSoon(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Tenant t WHERE t.maxCourses - (SELECT COUNT(c) FROM Course c WHERE c.tenant = t) <= :threshold")
    List<Tenant> findTenantsNearCourseLimit(@Param("threshold") Integer threshold);

    @Query("SELECT t FROM Tenant t WHERE t.storageLimit - :usedStorage <= :threshold")
    List<Tenant> findTenantsNearStorageLimit(@Param("usedStorage") Double usedStorage, @Param("threshold") Double threshold);

    List<Tenant> findByIsSubscriptionActive(Boolean isSubscriptionActive, Sort sort);

    @Query("SELECT new learning_management_system.backend.utility.TenantSummaryDto(t.tenantId, t.tenantName, COUNT(c), COUNT(u)) " +
            "FROM Tenant t " +
            "LEFT JOIN t.courses c " +
            "LEFT JOIN t.users u " +
            "GROUP BY t.tenantId, t.tenantName")
    List<TenantSummaryDto> getTenantSummaries();


    /**
     * Check if a tenant exists by its domain.
     *
     * @param domain The domain of the tenant.
     * @return true if a tenant with the specified domain exists, false otherwise.
     */
    boolean existsByDomain(String domain);

    /**
     * Find a tenant by its name.
     *
     * @param tenantName The name of the tenant.
     * @return An Optional containing the Tenant if found, or empty if not.
     */
    Optional<Tenant> findByTenantName(String tenantName);

//    @Query("SELECT t FROM Tenant t LEFT JOIN FETCH t.courses LEFT JOIN FETCH t.users WHERE t.tenantId = :tenantId")
//    Optional<Tenant> findTenantWithDetailsById(@Param("tenantId") Long tenantId);
//
//    @Query("SELECT t FROM Tenant t LEFT JOIN FETCH t.courses LEFT JOIN FETCH t.users")
//    List<Tenant> findAllWithDetails();


}

