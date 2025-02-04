package learning_management_system.backend.dto;

import java.time.LocalDate;
import java.util.Set;

public class StudentDto {
    private Long studentId; // Same as user_id, but specifically for the Student context
//    private Long userId;
    private String gradeLevel;
    private String major;
    private LocalDate dateEnrolled;
    private Boolean isActive;
    private String enrollmentStatus;
    private String bio;
    private Double overallProgress;
    private Integer leaderboardPoints;
    private Set<Long> studentGroupIds;
    private Set<Long> badgeIds;
    private Set<Long> advisorIds;
    private String attendance;
    private LocalDate graduationDate;
    private String profileVisibility;
    private String customAttributes;
    private String metadata;
    private Long tenantId;

    // Enrolled course IDs
    private Set<Long> enrolledCourseIds;

    // User-related fields for displaying basic student info
    private String userName;
    private String email;
    private String firstName;
    private String lastName;


    // Constructor
    public StudentDto() {}

    public StudentDto(Long studentId, String gradeLevel, String major, LocalDate dateEnrolled, Boolean isActive, String enrollmentStatus, String bio, Double overallProgress, Integer leaderboardPoints, Set<Long> studentGroupIds, Set<Long> badgeIds, Set<Long> advisorIds, String attendance, LocalDate graduationDate, String profileVisibility, String customAttributes, String metadata, Long tenantId, Set<Long> enrolledCourseIds, String userName, String email, String firstName, String lastName) {
        this.studentId = studentId;
        this.gradeLevel = gradeLevel;
        this.major = major;
        this.dateEnrolled = dateEnrolled;
        this.isActive = isActive;
        this.enrollmentStatus = enrollmentStatus;
        this.bio = bio;
        this.overallProgress = overallProgress;
        this.leaderboardPoints = leaderboardPoints;
        this.studentGroupIds = studentGroupIds;
        this.badgeIds = badgeIds;
        this.advisorIds = advisorIds;
        this.attendance = attendance;
        this.graduationDate = graduationDate;
        this.profileVisibility = profileVisibility;
        this.customAttributes = customAttributes;
        this.metadata = metadata;
        this.tenantId = tenantId;
        this.enrolledCourseIds = enrolledCourseIds;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter and Setter


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public LocalDate getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(LocalDate dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Double getOverallProgress() {
        return overallProgress;
    }

    public void setOverallProgress(Double overallProgress) {
        this.overallProgress = overallProgress;
    }

    public Integer getLeaderboardPoints() {
        return leaderboardPoints;
    }

    public void setLeaderboardPoints(Integer leaderboardPoints) {
        this.leaderboardPoints = leaderboardPoints;
    }

    public Set<Long> getStudentGroupIds() {
        return studentGroupIds;
    }

    public void setStudentGroupIds(Set<Long> studentGroupIds) {
        this.studentGroupIds = studentGroupIds;
    }

    public Set<Long> getBadgeIds() {
        return badgeIds;
    }

    public void setBadgeIds(Set<Long> badgeIds) {
        this.badgeIds = badgeIds;
    }

    public Set<Long> getAdvisorIds() {
        return advisorIds;
    }

    public void setAdvisorIds(Set<Long> advisorIds) {
        this.advisorIds = advisorIds;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getProfileVisibility() {
        return profileVisibility;
    }

    public void setProfileVisibility(String profileVisibility) {
        this.profileVisibility = profileVisibility;
    }

    public String getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(String customAttributes) {
        this.customAttributes = customAttributes;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Set<Long> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void setEnrolledCourseIds(Set<Long> enrolledCourseIds) {
        this.enrolledCourseIds = enrolledCourseIds;
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
}