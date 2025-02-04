package learning_management_system.backend.dto;

import java.time.LocalDate;
import java.util.Date;

public class TenantDto {

    private Long tenantId;               // Unique ID of the tenant
    private String domain;               // Domain name
    private String tenantName;           // Institution name
    private String schemaName;           // Schema name
    private String subscriptionPlan;     // Subscription details
    private Boolean isSubscriptionActive;// Is the subscription active
    private LocalDate subscriptionStart; // Subscription start date
    private LocalDate subscriptionEnd;   // Subscription end date
    private Integer maxUsers;            // Maximum number of users
    private Integer maxCourses;          // Maximum number of courses
    private Double storageLimit;         // Storage limit in GB
    private String region;               // Region of the tenant
    private String status;               // Status of the tenant (ACTIVE, SUSPENDED)
    private Long createdById;
    private Long updatedById;
    private Date dateCreated;           // Timestamp when the tenant was created. */
    private Date dateUpdated;           // Timestamp when the tenant was last updated. */
    private Date lastAccessed;          // Timestamp when the tenant was last accessed. */
    private Long adminId;               // User ID of the administrator for the tenant.
    private String metadata;            // Metadata for extensibility and custom attributes. */
    /** User ID of the creator of the tenant record. */
    private Long createdBy;
    /** User ID of the person who last updated the tenant record. */
    private Long updatedBy;
    // New Attributes
    private Long totalCourses;           // Total courses for this tenant
    private Long totalUsers;             // Total users for this tenant
    private String adminEmail;           // Admin email for tenant communication

    //Constructors
    public TenantDto(){}

    public TenantDto(Long tenantId, String domain, String tenantName, String schemaName, String subscriptionPlan,
                     Boolean isSubscriptionActive, LocalDate subscriptionStart, LocalDate subscriptionEnd, Integer maxUsers,
                     Integer maxCourses, Double storageLimit, String region, String status, Long createdById, Long updatedById,
                     Date dateCreated, Date dateUpdated, Date lastAccessed, Long adminId, String metadata, Long createdBy,
                     Long updatedBy, Long totalCourses, Long totalUsers, String adminEmail) {
        this.tenantId = tenantId;
        this.domain = domain;
        this.tenantName = tenantName;
        this.schemaName = schemaName;
        this.subscriptionPlan = subscriptionPlan;
        this.isSubscriptionActive = isSubscriptionActive;
        this.subscriptionStart = subscriptionStart;
        this.subscriptionEnd = subscriptionEnd;
        this.maxUsers = maxUsers;
        this.maxCourses = maxCourses;
        this.storageLimit = storageLimit;
        this.region = region;
        this.status = status;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.lastAccessed = lastAccessed;
        this.adminId = adminId;
        this.metadata = metadata;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.totalCourses = totalCourses;
        this.totalUsers = totalUsers;
        this.adminEmail = adminEmail;
    }

    // Getters and Setters

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(String subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public Boolean getSubscriptionActive() {
        return isSubscriptionActive;
    }

    public void setSubscriptionActive(Boolean subscriptionActive) {
        isSubscriptionActive = subscriptionActive;
    }

    public LocalDate getSubscriptionStart() {
        return subscriptionStart;
    }

    public void setSubscriptionStart(LocalDate subscriptionStart) {
        this.subscriptionStart = subscriptionStart;
    }

    public LocalDate getSubscriptionEnd() {
        return subscriptionEnd;
    }

    public void setSubscriptionEnd(LocalDate subscriptionEnd) {
        this.subscriptionEnd = subscriptionEnd;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Integer getMaxCourses() {
        return maxCourses;
    }

    public void setMaxCourses(Integer maxCourses) {
        this.maxCourses = maxCourses;
    }

    public Double getStorageLimit() {
        return storageLimit;
    }

    public void setStorageLimit(Double storageLimit) {
        this.storageLimit = storageLimit;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}

