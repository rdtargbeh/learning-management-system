package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.TenantStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a tenant in the LMS system. A tenant is an organization or
 * institution using the LMS, with attributes for subscription management,
 * localization, customization, and relationships to users, courses, and roles.
 */

@CrossOrigin("*")
@Entity
@Table(name = "tenants")
public class Tenant {

    /** Unique identifier for the tenant. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", nullable = false, updatable = false)
    private Long tenantId;

    /** Unique domain for the tenant (e.g., "school.edu"). */
    @Column(name = "domain", nullable = false, unique = true, length = 255)
    private String domain;

    /** Official name of the institution. */
    @Column(name = "tenant_name", nullable = false, unique = true, length = 100)
    private String tenantName;

//    /** The email address of the user. Must be unique and verified. */
//    @Column(name = "email", nullable = false, unique = true, length = 50)
//    private String email;

    /** Schema name for tenant-specific data isolation. */
    @Column(name = "schema_name", nullable = false, unique = true)
    private String schemaName;

    /** Subscription plan (e.g., Basic, Premium). */
    @Column(name = "subscription_plan", nullable = false, length = 50)
    private String subscriptionPlan;

    /** Indicates whether the subscription is active. */
    @Column(name = "is_subscription_active", nullable = false)
    private Boolean isSubscriptionActive = true;

    /** Subscription start date. */
    @Column(name = "subscription_start", nullable = false)
    private LocalDate subscriptionStart;

    /** Subscription end date. */
    @Column(name = "subscription_end", nullable = false)
    private LocalDate subscriptionEnd;

    /** Maximum number of users allowed for the tenant. */
    @Column(name = "max_users", nullable = false)
    private Integer maxUsers;

    /** Maximum number of courses allowed for the tenant. */
    @Column(name = "max_courses", nullable = false)
    private Integer maxCourses;

    /** Storage limit for the tenant (in GB). */
    @Column(name = "storage_limit", nullable = false)
    private Double storageLimit;

    /** Geographical region of the tenant. */
    @Column(name = "region", nullable = false, length = 100)
    private String region;

    /** Status of the tenant (e.g., ACTIVE, INACTIVE, SUSPENDED). */
    @Column(name = "status", nullable = false, length = 20)
    private TenantStatus status = TenantStatus.ACTIVE;

    /** Timestamp when the tenant was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp when the tenant was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** User who created the tenant record. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** User who last updated the tenant record. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_accessed")
    private Date lastAccessed;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    /** Courses associated with the tenant. */
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    /** Users associated with the tenant. */
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    /** Roles associated with the tenant. */
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<>();


    public boolean isTenantValid() {
        return isSubscriptionActive && LocalDate.now().isBefore(subscriptionEnd) && TenantStatus.ACTIVE.equals(status);
    }

    public void updateAuditFields(User updatedByUser) {
        this.updatedBy = updatedByUser;
        this.dateUpdated = new Date();
    }

    public boolean hasReachedMaxUsers() {
        return users.size() >= maxUsers;
    }

    public boolean hasReachedMaxCourses() {
        return courses.size() >= maxCourses;
    }


    public Tenant() {
    }

    public Tenant(Long tenantId, String domain, String tenantName, String schemaName, String subscriptionPlan, Boolean isSubscriptionActive, LocalDate subscriptionStart, LocalDate subscriptionEnd, Integer maxUsers, Integer maxCourses, Double storageLimit, String region, TenantStatus status, Date dateCreated, Date dateUpdated, User createdBy, User updatedBy, Date lastAccessed, String metadata, User admin, Set<Course> courses, Set<User> users, Set<Role> roles) {
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
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.lastAccessed = lastAccessed;
        this.metadata = metadata;
        this.admin = admin;
        this.courses = courses;
        this.users = users;
        this.roles = roles;
    }

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

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}