package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Institution entity.
 */
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    List<Institution> findByBillingAddress(String billingAddress);

    List<Institution> findByTheme(String theme);

//    List<Institution> findByRegion(String region);

    // Navigate through the tenant relationship to access the region property
    @Query("SELECT i FROM Institution i WHERE i.tenant.region = :region")
    Page<Institution> findByRegion(@Param("region") String region, Pageable pageable);



    @Query("SELECT t FROM Tenant t WHERE t.region = :region AND t.isSubscriptionActive = true AND t.maxUsers >= :minUsers")
    List<Institution> findActiveInstitutionsByRegionAndMinUsers(@Param("region") String region, @Param("minUsers") Integer minUsers);


}