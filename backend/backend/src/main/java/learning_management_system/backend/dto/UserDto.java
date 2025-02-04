package learning_management_system.backend.dto;

import java.util.Date;
import java.util.Set;

public class UserDto {
    private Long userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private Boolean isEmailVerified;
    private boolean isActive;
    private Integer failedLoginAttempts;
    private String preferredLanguage;
    private String notificationPreference;
    private String timezone;
    private String preferences;
    private String metadata;
    private Date dateCreated;
    private Date dateUpdated;
    private Long tenantId; // Maps to Tenant
    private Set<Long> roleIds;
    private Long assignmentId; // Not use


// Constructor
    public UserDto() {}

    public UserDto(Long userId, String userName, String email, String firstName, String lastName, String profilePictureUrl,
                   Boolean isEmailVerified, boolean isActive, Integer failedLoginAttempts, String preferredLanguage,
                   String notificationPreference, String timezone, String preferences, String metadata, Date dateCreated,
                   Date dateUpdated, Long tenantId, Set<Long> roleIds, Long assignmentId) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
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
        this.tenantId = tenantId;
        this.roleIds = roleIds;
        this.assignmentId = assignmentId;
    }

    // Getter and Setter

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Set<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getAssignment() {
        return assignmentId;
    }

    public void setAssignment(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
}