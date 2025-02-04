package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

/**
 * Represents a user in the LMS system. This entity serves as the base class for
 * all users, including Admin, Teacher, Student, and other roles.
 * It supports relationships with roles, permissions, tenants, and courses,
 * allowing flexible and dynamic role assignments.
 */

@CrossOrigin("*")
@Entity
@Table(name = "users")

public class User {

    // Core Identity  ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /** Unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /** The username used for login. Must be unique. */
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    /** The email address of the user. Must be unique and verified. */
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    /** The securely hashed password for the user. */
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    /** The user's first name. */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /** The user's last name. */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /** The URL to the user's profile picture. */
    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    // Security ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /** Tracks whether the user's email has been verified. */
    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;

    /** Indicates whether the user account is active. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Number of failed login attempts for security purposes. */
    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts = 0;


    // Preferences ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Column(name = "preferred_language")
    private String preferredLanguage;  // User's language preference (e.g., "en", "es")

    @Column(name = "notification_preference")
    private String notificationPreference;  // e.g., "EMAIL", "SMS", "PUSH"
    @Column(name = "timezone")
    private String timezone;  // e.g., "America/New_York"


    // Metadata and Timestamps  +++++++++++++++++++++++++++++++++++++++++++++++++++
    /** Customizable user preferences, stored as JSON. */
    @Column(name = "preferences", columnDefinition = "TEXT")
    private String preferences;

    /** Optional metadata for tenant-specific or custom fields. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp when the user account was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated;

    /** Timestamp when the user account was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated;

    // Relationships  +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    /** The tenant to which the user belongs. Null for global users. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /** The roles assigned to the user. */
    @ManyToMany
    @JoinTable(
            name = "User_Roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

//    private String roleName;
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LmsNotification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityLogs> activityLogs = new ArrayList<>();

    /** The assignments assigned to this user. */
    @ManyToMany(mappedBy = "assignedStudents")
    private Set<Assignment> assignments = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assignment_id", nullable = true)
//    private Assignment assignment;


    // Helper Methods
    /**
     * Get the primary role for the user.
     *
     * @return the first role in the set of roles or null if no roles exist.
     */
    public Role getPrimaryRole() {
        return roles.stream().findFirst().orElse(null);
    }

    /**
     * Get the name of the primary role.
     *
     * @return the name of the first role or null if no roles exist.
     */
    public String getPrimaryRoleName() {
        Role primaryRole = getPrimaryRole();
        return primaryRole != null ? primaryRole.getRoleName() : null;
    }

    // Constructors   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public User(){}

    public User(Long userId, String userName, String email, String password, String firstName, String lastName, String profilePictureUrl,
                Boolean isEmailVerified, Boolean isActive, Integer failedLoginAttempts, String preferredLanguage, String notificationPreference,
                String timezone, String preferences, String metadata, Date dateCreated, Date dateUpdated, Tenant tenant, String roleName,
                Set<Role> roles, List<LmsNotification> notifications, List<ActivityLogs> activityLogs, Set<Assignment> assignments) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePictureUrl = profilePictureUrl;
        this.isEmailVerified = isEmailVerified;
        this.isActive = isActive;
        this.failedLoginAttempts = failedLoginAttempts;
        this.preferredLanguage = preferredLanguage;
        this.notificationPreference = notificationPreference;
        this.timezone = timezone;
        this.preferences = preferences;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.tenant = tenant;
//        this.roleName = roleName;
        this.roles = roles;
        this.notifications = notifications;
        this.activityLogs = activityLogs;
        this.assignments = assignments;
    }

// Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getNotificationPreference() {
        return notificationPreference;
    }

    public void setNotificationPreference(String notificationPreference) {
        this.notificationPreference = notificationPreference;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

//    public String getRoleName() {
//        return roleName;
//    }
//
//    public void setRoleName(String roleName) {
//        this.roleName = roleName;
//    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<LmsNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<LmsNotification> notifications) {
        this.notifications = notifications;
    }

    public List<ActivityLogs> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLogs> activityLogs) {
        this.activityLogs = activityLogs;
    }

    public Set<Assignment> getAssignment() {
        return assignments;
    }

    public void setAssignment(Set<Assignment> assignment) {
        this.assignments = assignment;
    }
}