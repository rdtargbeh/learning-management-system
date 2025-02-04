package learning_management_system.backend.controller;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.TenantDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.service.TenantService;
import learning_management_system.backend.utility.TenantSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;


    // Build A Create Tenants REST API
    @PostMapping
    public ResponseEntity<TenantDto> createTenant(@RequestBody TenantDto tenantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.createTenant(tenantDto));
    }

    // Build An Update Tenants REST API
    @PutMapping("/{tenantId}")
    public ResponseEntity<TenantDto> updateTenant(
            @PathVariable Long tenantId,
            @RequestBody TenantDto tenantDto) {
        return ResponseEntity.ok(tenantService.updateTenant(tenantId, tenantDto));
    }

    // Build A Get All Tenants REST API
    @GetMapping
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    // Build A Get Tenants By ID REST API
    @GetMapping("/{tenantId}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable Long tenantId) {
        return ResponseEntity.ok(tenantService.getTenantById(tenantId));
    }

    // Build A Get Tenants By Domain REST API
    @GetMapping("/domain")
    public ResponseEntity<TenantDto> getTenantByDomain(@RequestParam String domain) {
        return ResponseEntity.ok(tenantService.getTenantByDomain(domain));
    }

    // Build A Delete Tenants REST API
    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long tenantId) {
        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscription-plan/{plan}")
    public ResponseEntity<List<TenantDto>> getTenantsBySubscriptionPlan(@PathVariable String plan) {
        return ResponseEntity.ok(tenantService.getTenantsBySubscriptionPlan(plan));
    }

    @GetMapping("/active")
    public ResponseEntity<List<TenantDto>> getActiveTenants(@RequestParam Boolean isActive) {
        return ResponseEntity.ok(tenantService.getActiveTenants(isActive));
    }

    @GetMapping("/min-users")
    public ResponseEntity<List<TenantDto>> getTenantsByMinUsers(@RequestParam Integer minUsers) {
        return ResponseEntity.ok(tenantService.getTenantsByMinUsers(minUsers));
    }

    @GetMapping("/subscription-expiry")
    public ResponseEntity<List<TenantDto>> getTenantsWithSubscriptionExpiringSoon(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(tenantService.getTenantsWithSubscriptionExpiringSoon(startDate, endDate));
    }

    @GetMapping("/near-course-limit")
    public ResponseEntity<List<TenantDto>> getTenantsNearCourseLimit(@RequestParam Integer threshold) {
        return ResponseEntity.ok(tenantService.getTenantsNearCourseLimit(threshold));
    }

    @GetMapping("/near-storage-limit")
    public ResponseEntity<List<TenantDto>> getTenantsNearStorageLimit(
            @RequestParam Double usedStorage, @RequestParam Double threshold) {
        return ResponseEntity.ok(tenantService.getTenantsNearStorageLimit(usedStorage, threshold));
    }

    @GetMapping("/active-sorted")
    public ResponseEntity<List<TenantDto>> getSortedActiveTenants(
            @RequestParam Boolean isActive, Sort sort) {
        return ResponseEntity.ok(tenantService.getSortedActiveTenants(isActive, sort));
    }

    @GetMapping("/summaries")
    public ResponseEntity<List<TenantSummaryDto>> getTenantSummaries() {
        return ResponseEntity.ok(tenantService.getTenantSummaries());
    }

    /**
     * Get the last accessed timestamp for a tenant.
     *
     * @param tenantId ID of the tenant.
     * @return Last accessed timestamp as LocalDateTime.
     */
    @GetMapping("/{tenantId}/last-accessed")
    public ResponseEntity<LocalDateTime> getLastAccessed(@PathVariable Long tenantId) {
        LocalDateTime lastAccessed = tenantService.getLastAccessed(tenantId);
        return ResponseEntity.ok(lastAccessed);
    }

    /**
     * Add a new user to a tenant.
     *
     * @param tenantId ID of the tenant.
     * @param userDto  Details of the user to be added.
     * @return ResponseEntity indicating success.
     */
    @PostMapping("/{tenantId}/users")
    public ResponseEntity<String> addUserToTenant(@PathVariable Long tenantId, @RequestBody UserDto userDto) {
        tenantService.addUserToTenant(tenantId, userDto);
        return ResponseEntity.ok("User added to tenant successfully.");
    }

    /**
     * Add a new course to a tenant.
     *
     * @param tenantId   ID of the tenant.
     * @param courseDto  Details of the course to be added.
     * @return ResponseEntity indicating success.
     */
    @PostMapping("/{tenantId}/courses")
    public ResponseEntity<String> addCourseToTenant(@PathVariable Long tenantId, @RequestBody CourseDto courseDto) {
        tenantService.addCourseToTenant(tenantId, courseDto);
        return ResponseEntity.ok("Course added to tenant successfully.");
    }


    /**
     * Check if a tenant exists by domain.
     *
     * @param domain The domain to check.
     * @return true if the tenant exists, false otherwise.
     */
    @GetMapping("/exists-by-domain")
    public ResponseEntity<Boolean> tenantExistsByDomain(@RequestParam String domain) {
        boolean exists = tenantService.tenantExistsByDomain(domain);
        return ResponseEntity.ok(exists);
    }

    /**
     * Get a tenant by its name.
     *
     * @param tenantName The name of the tenant.
     * @return The tenant if found, or a 404 response if not.
     */
    @GetMapping("/by-name")
    public ResponseEntity<Tenant> getTenantByName(@RequestParam String tenantName) {
        Optional<Tenant> tenant = tenantService.getTenantByName(tenantName);
        return tenant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

