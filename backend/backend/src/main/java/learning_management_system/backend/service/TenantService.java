package learning_management_system.backend.service;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.TenantDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.utility.TenantSummaryDto;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TenantService {

    List<TenantDto> getAllTenants();

    TenantDto getTenantById(Long tenantId);

    TenantDto getTenantByDomain(String domain);

    TenantDto createTenant(TenantDto tenantDto);

    TenantDto updateTenant(Long tenantId, TenantDto tenantDto);

    void deleteTenant(Long tenantId);

    List<TenantDto> getTenantsBySubscriptionPlan(String subscriptionPlan);

    List<TenantDto> getActiveTenants(Boolean isActive);

    List<TenantDto> getTenantsByMinUsers(Integer minUsers);

    List<TenantDto> getTenantsWithSubscriptionExpiringSoon(LocalDate startDate, LocalDate endDate);

    List<TenantDto> getTenantsNearCourseLimit(Integer threshold);

    List<TenantDto> getTenantsNearStorageLimit(Double usedStorage, Double threshold);

    List<TenantDto> getSortedActiveTenants(Boolean isActive, Sort sort);

    void validateTenantConstraints(Long tenantId);

    LocalDateTime getLastAccessed(Long tenantId);

    void addUserToTenant(Long tenantId, UserDto userDto);

    void addCourseToTenant(Long tenantId, CourseDto courseDto);

    /**
     * Retrieve summaries of all tenants, including course and user counts.
     *
     * @return a list of TenantSummaryDto objects
     */
    List<TenantSummaryDto> getTenantSummaries();

    boolean tenantExistsByDomain(String domain);

    Optional<Tenant> getTenantByName(String tenantName);

//    List<TenantDto> getSortedActiveTenants(Boolean isActive, org.springframework.data.domain.Sort sort);

}
