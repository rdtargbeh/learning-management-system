package learning_management_system.backend.dto;

import learning_management_system.backend.enums.AvailabilityStatus;
import learning_management_system.backend.enums.EmploymentStatus;
import learning_management_system.backend.enums.EngagementStatus;
import learning_management_system.backend.enums.StaffType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class StaffDto {

//    private Long staffId;
    private Long userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private StaffType staffType;
    private String jobTitle;
    private String subjectSpecialization;
    private LocalDate dateHired;
    private EmploymentStatus employmentStatus;
    private String officeLocation;
    private String accessibilityPreferences;
    private String workSchedule;
    private Long departmentId;
    private Long tenantId;
    private Set<Long> linkedTenantIds;
    private List<Long> assignedCourseIds;
    private Set<Long> assignedTeacherIds;
    private String skills;
    private Integer currentWorkload;
    private Integer maxWorkload;
    private AvailabilityStatus availabilityStatus;
    private EngagementStatus engagementStatus;
    private String externalReferenceId;
    private String metadata;
    private Long lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    private List<LmsNotificationDto> notifications;
    private List<ActivityLogsDto> activityLogs;

    // Constructors
    public StaffDto(){}

    public StaffDto(Long userId, String userName, String email, String firstName, String lastName, StaffType staffType, String jobTitle,
                    String subjectSpecialization, LocalDate dateHired, EmploymentStatus employmentStatus, String officeLocation,
                    String accessibilityPreferences, String workSchedule, Long departmentId, Long tenantId, Set<Long> linkedTenantIds,
                    List<Long> assignedCourseIds, Set<Long> assignedTeacherIds, String skills, Integer currentWorkload, Integer maxWorkload,
                    AvailabilityStatus availabilityStatus, EngagementStatus engagementStatus, String externalReferenceId, String metadata,
                    Long lastUpdatedBy, LocalDateTime lastUpdatedAt, List<LmsNotificationDto> notifications, List<ActivityLogsDto> activityLogs) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.staffType = staffType;
        this.jobTitle = jobTitle;
        this.subjectSpecialization = subjectSpecialization;
        this.dateHired = dateHired;
        this.employmentStatus = employmentStatus;
        this.officeLocation = officeLocation;
        this.accessibilityPreferences = accessibilityPreferences;
        this.workSchedule = workSchedule;
        this.departmentId = departmentId;
        this.tenantId = tenantId;
        this.linkedTenantIds = linkedTenantIds;
        this.assignedCourseIds = assignedCourseIds;
        this.assignedTeacherIds = assignedTeacherIds;
        this.skills = skills;
        this.currentWorkload = currentWorkload;
        this.maxWorkload = maxWorkload;
        this.availabilityStatus = availabilityStatus;
        this.engagementStatus = engagementStatus;
        this.externalReferenceId = externalReferenceId;
        this.metadata = metadata;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
        this.notifications = notifications;
        this.activityLogs = activityLogs;
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

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    public LocalDate getDateHired() {
        return dateHired;
    }

    public void setDateHired(LocalDate dateHired) {
        this.dateHired = dateHired;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getAccessibilityPreferences() {
        return accessibilityPreferences;
    }

    public void setAccessibilityPreferences(String accessibilityPreferences) {
        this.accessibilityPreferences = accessibilityPreferences;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Set<Long> getLinkedTenantIds() {
        return linkedTenantIds;
    }

    public void setLinkedTenantIds(Set<Long> linkedTenantIds) {
        this.linkedTenantIds = linkedTenantIds;
    }

    public List<Long> getAssignedCourseIds() {
        return assignedCourseIds;
    }

    public void setAssignedCourseIds(List<Long> assignedCourseIds) {
        this.assignedCourseIds = assignedCourseIds;
    }

    public Set<Long> getAssignedTeacherIds() {
        return assignedTeacherIds;
    }

    public void setAssignedTeacherIds(Set<Long> assignedTeacherIds) {
        this.assignedTeacherIds = assignedTeacherIds;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getCurrentWorkload() {
        return currentWorkload;
    }

    public void setCurrentWorkload(Integer currentWorkload) {
        this.currentWorkload = currentWorkload;
    }

    public Integer getMaxWorkload() {
        return maxWorkload;
    }

    public void setMaxWorkload(Integer maxWorkload) {
        this.maxWorkload = maxWorkload;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public EngagementStatus getEngagementStatus() {
        return engagementStatus;
    }

    public void setEngagementStatus(EngagementStatus engagementStatus) {
        this.engagementStatus = engagementStatus;
    }

    public String getExternalReferenceId() {
        return externalReferenceId;
    }

    public void setExternalReferenceId(String externalReferenceId) {
        this.externalReferenceId = externalReferenceId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public List<LmsNotificationDto> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<LmsNotificationDto> notifications) {
        this.notifications = notifications;
    }

    public List<ActivityLogsDto> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLogsDto> activityLogs) {
        this.activityLogs = activityLogs;
    }
}