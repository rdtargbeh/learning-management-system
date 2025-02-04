package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.TenantDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.TenantStatus;
import learning_management_system.backend.mapper.CourseMapper;
import learning_management_system.backend.mapper.TenantMapper;
import learning_management_system.backend.mapper.UserMapper;
import learning_management_system.backend.repository.CourseRepository;
import learning_management_system.backend.repository.TenantRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.AuthenticationService;
import learning_management_system.backend.service.TenantService;
import learning_management_system.backend.utility.StorageService;
import learning_management_system.backend.utility.TenantSummaryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TenantServiceImplementation implements TenantService {

    private static final Logger log = LoggerFactory.getLogger(TenantServiceImplementation.class);

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;


    // Create New Tenant
    @Override
    public TenantDto createTenant(TenantDto tenantDto) {
        // Step 1: Validate and fetch `createdBy` user
        if (tenantDto.getCreatedById() == null) {
            throw new IllegalArgumentException("CreatedBy ID is required.");
        }
        User creator = userRepository.findById(tenantDto.getCreatedById())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + tenantDto.getCreatedById()));

        // Step 2: Fetch the current user for `createdBy` field
        User currentUser = userRepository.findByUserName("globalAdmin")
                .orElseThrow(() -> new IllegalStateException("Global admin user not found."));

        // Step 2: Fetch the current authenticated user.
//        User currentUser = authenticationService.getCurrentUser();
//        if (currentUser == null) {
//            throw new IllegalStateException("No authenticated user found to set as 'createdBy'.");
//        }

        // Step 3: Map DTO to entity and set default values
        Tenant tenant = TenantMapper.toEntity(tenantDto, creator);
        tenant.setUpdatedBy(currentUser); // Set the current user as the updater
        tenant.setSubscriptionActive(tenantDto.getSubscriptionActive() != null ? tenantDto.getSubscriptionActive() : true);

        // Step 4: Save tenant
        Tenant savedTenant = tenantRepository.save(tenant);

        // Step 5: Convert entity back to DTO and return
        return TenantMapper.toDto(savedTenant);
    }


    // Update Tenant Profile
    @Override
    public TenantDto updateTenant(Long tenantId, TenantDto tenantDto) {
        // Fetch and update tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
        User updatedBy = userRepository.findById(tenantDto.getUpdatedById())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + tenantDto.getUpdatedById()));

        tenant.setTenantName(tenantDto.getTenantName());
        tenant.setDomain(tenantDto.getDomain());
        tenant.setSchemaName(tenantDto.getSchemaName());
//        tenant.setUpdatedBy(updatedBy);
        tenant.setDateUpdated(java.sql.Timestamp.valueOf(LocalDateTime.now()));   // Convert LocalDateTime to Date
        tenant.setSubscriptionPlan(tenantDto.getSubscriptionPlan());
        tenant.setSubscriptionActive(tenantDto.getSubscriptionActive());
        tenant.setSubscriptionStart(tenantDto.getSubscriptionStart());
        tenant.setSubscriptionEnd(tenantDto.getSubscriptionEnd());
        tenant.setMaxUsers(tenantDto.getMaxUsers());
        tenant.setMaxCourses(tenantDto.getMaxCourses());
        tenant.setStorageLimit(tenantDto.getStorageLimit());
        tenant.setRegion(tenantDto.getRegion());
//        tenant.setStatus(TenantStatus.valueOf(tenantDto.getStatus()));
        // Handle enum conversion for status
        try {
            tenant.setStatus(
                    tenantDto.getStatus() != null ? TenantStatus.valueOf(tenantDto.getStatus().toUpperCase()) : null
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + tenantDto.getStatus());
        }
        // Fetch the current user for the "updatedBy" field
        User currentUser = authenticationService.getCurrentUser();
        tenant.setDateUpdated(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        tenant.setUpdatedBy(currentUser);
        // Save updated tenant
        Tenant updatedTenant = tenantRepository.save(tenant);
        // Return DTO with additional data
        return TenantMapper.toDto(updatedTenant);
    }

    // Get All Students
    @Override
    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll()
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

//    public List<TenantDto> getAllTenants() {
//        return tenantRepository.findAll()
//        List<Tenant> tenants = tenantRepository.findAll();
//        tenants.forEach(tenant -> {
//            Hibernate.initialize(tenant.getCourses());
//            Hibernate.initialize(tenant.getUsers());
//        });
//        return tenants.stream()
//                .map(TenantMapper::toDto)
//                .collect(Collectors.toList());
   // }

//    public List<TenantDto> getAllTenants() {
//        List<Tenant> tenants = tenantRepository.findAll();
//
//        if (tenants.isEmpty()) {
//            log.info("No tenants found in the database.");
//            return Collections.emptyList();
//        }
//        return tenants.stream()
//                .map(TenantMapper::toDto) // Map each entity to a DTO
//                .collect(Collectors.toList());
//    }


    // Get Tenants By ID
    @Override
    public TenantDto getTenantById(Long tenantId) {
        log.info("Fetching tenant with ID: {}", tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    log.error("Tenant not found with ID: {}", tenantId);
                    return new IllegalArgumentException("Tenant not found with ID: " + tenantId);
                });

        log.info("Tenant retrieved: {}", tenant);
        return TenantMapper.toDto(tenant);
    }


    // Get Tenants By Domain Name
    @Override
    public TenantDto getTenantByDomain(String domain) {
        Tenant tenant = tenantRepository.findByDomain(domain)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with domain: " + domain));

        // Map to DTO
        return TenantMapper.toDto(tenant);
    }

    // Delete Tenant Info
    @Override
    public void deleteTenant(Long tenantId) {
        if (!tenantRepository.existsById(tenantId)) {
            throw new IllegalArgumentException("Tenant not found with ID: " + tenantId);
        }
        tenantRepository.deleteById(tenantId);
    }

    // Get Tenant By Subscription Plan
    @Override
    public List<TenantDto> getTenantsBySubscriptionPlan(String subscriptionPlan) {
        return tenantRepository.findBySubscriptionPlan(subscriptionPlan)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Active Tenants
    @Override
    public List<TenantDto> getActiveTenants(Boolean isActive) {
        return tenantRepository.findByIsSubscriptionActive(isActive)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Tenants by Minimum Users
    @Override
    public List<TenantDto> getTenantsByMinUsers(Integer minUsers) {
        return tenantRepository.findByMinUsers(minUsers)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Tenants With Subscription ExpiringSoon
    @Override
    public List<TenantDto> getTenantsWithSubscriptionExpiringSoon(LocalDate startDate, LocalDate endDate) {
        return tenantRepository.findTenantsWithSubscriptionExpiringSoon(startDate, endDate)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Tenants Near Course Limit
    @Override
    public List<TenantDto> getTenantsNearCourseLimit(Integer threshold) {
        return tenantRepository.findTenantsNearCourseLimit(threshold)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve summaries of all tenants, including course and user counts.
     *
     * @return a list of TenantSummaryDto objects
     */
    @Override
    public List<TenantSummaryDto> getTenantSummaries() {
        return tenantRepository.getTenantSummaries();
    }



    // Get Tenants Near Storage Limit
    @Override
    public List<TenantDto> getTenantsNearStorageLimit(Double usedStorage, Double threshold) {
        return tenantRepository.findTenantsNearStorageLimit(usedStorage, threshold)
                .stream()
                .map(TenantMapper::toDto)

                .collect(Collectors.toList());
    }

    // Get Sorted Active Tenants
    @Override
    public List<TenantDto> getSortedActiveTenants(Boolean isActive, Sort sort) {
        return tenantRepository.findByIsSubscriptionActive(isActive, sort)
                .stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Mock implementation for fetching the current authenticated user.
     * Replace this with the actual user context from your security framework.
     */
    private User getCurrentUser() {
        // Example: Fetch the currently logged-in user from the security context
        return userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void validateTenantConstraints(Long tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Subscription must be active
        if (!tenant.getSubscriptionActive()) {
            throw new IllegalStateException("The tenant's subscription is not active.");
        }
        // Max users constraint
        long currentUsers = userRepository.countByTenant_TenantId(tenantId);
        if (currentUsers >= tenant.getMaxUsers()) {
            throw new IllegalStateException("Tenant has reached the maximum number of allowed users.");
        }
        // Max courses constraint
        long currentCourses = courseRepository.countByTenant_TenantId(tenantId);
        if (currentCourses >= tenant.getMaxCourses()) {
            throw new IllegalStateException("Tenant has reached the maximum number of allowed courses.");
        }
        // Storage limit constraint
        double currentStorageUsage = storageService.getUsedStorage(tenantId); // Example method
        if (currentStorageUsage >= tenant.getStorageLimit()) {
            throw new IllegalStateException("Tenant has exceeded the storage limit.");
        }
        // Status constraint
        if (!tenant.getStatus().equals(TenantStatus.ACTIVE.name())) {
            throw new IllegalStateException("The tenant is not active. Current status: " + tenant.getStatus());
        }
    }

    // Get Last Accessed
    public LocalDateTime getLastAccessed(Long tenantId) {
        // Fetch tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Use last accessed date from the tenant or other related entities
        Date lastAccessedDate = tenant.getDateUpdated(); // Assuming this tracks the last update
        return lastAccessedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    // Add User to Tenant
    @Override
    @Transactional
    public void addUserToTenant(Long tenantId, UserDto userDto) {
        // Validate tenant constraints
        validateTenantConstraints(tenantId);
        // Fetch tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
        // Convert UserDto to User entity and associate with the tenant
        User user = userMapper.toEntity(userDto);
        user.setTenant(tenant);
        // Save the user
        userRepository.save(user);
    }

    // Add Course to Tenant
    @Transactional
    public void addCourseToTenant(Long tenantId, CourseDto courseDto) {
        // Validate tenant constraints
        validateTenantConstraints(tenantId);
        // Fetch tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
        // Convert CourseDto to Course entity and associate with the tenant
        Course course = courseMapper.toEntity(courseDto);
        course.setTenant(tenant);
        // Save the course
        courseRepository.save(course);
    }

    @Override
    public boolean tenantExistsByDomain(String domain) {
        return tenantRepository.existsByDomain(domain);
    }

    @Override
    public Optional<Tenant> getTenantByName(String tenantName) {
        return tenantRepository.findByTenantName(tenantName);
    }

}
