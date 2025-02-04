package learning_management_system.backend.dto;

import learning_management_system.backend.enums.CourseStatus;
import learning_management_system.backend.enums.CourseVisibility;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class CourseDto {

    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private String courseDescription;
    private CourseStatus courseStatus;
    private Boolean isPublished;
    private boolean isArchived;
    private Long tenantId;
    private Long departmentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate enrollmentStart;
    private LocalDate enrollmentEnd;
    private Integer maxEnrollments;
    private CourseVisibility visibility;
    private Set<String> tags;
    private Set<Long> staffIds;
    private Set<Long> studentIds;
    private Long createdBy;
    private Date dateCreated;
    private Date dateUpdated;
    private String metadata;


    // Constructor
    public CourseDto(){}
    public CourseDto(Long courseId, String courseCode, String courseTitle, String courseDescription, CourseStatus courseStatus,
                     Boolean isPublished, boolean isArchived, Long tenantId, Long departmentId, LocalDate startDate, LocalDate endDate,
                     LocalDate enrollmentStart, LocalDate enrollmentEnd, Integer maxEnrollments, CourseVisibility visibility,
                     Set<String> tags, Set<Long> staffIds, Set<Long> studentIds, Long createdBy, Date dateCreated,
                     Date dateUpdated, String metadata) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseStatus = courseStatus;
        this.isPublished = isPublished;
        this.isArchived = isArchived;
        this.tenantId = tenantId;
        this.departmentId = departmentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentStart = enrollmentStart;
        this.enrollmentEnd = enrollmentEnd;
        this.maxEnrollments = maxEnrollments;
        this.visibility = visibility;
        this.tags = tags;
        this.staffIds = staffIds;
        this.studentIds = studentIds;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.metadata = metadata;
    }

// Getter and Setter
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEnrollmentStart() {
        return enrollmentStart;
    }

    public void setEnrollmentStart(LocalDate enrollmentStart) {
        this.enrollmentStart = enrollmentStart;
    }

    public LocalDate getEnrollmentEnd() {
        return enrollmentEnd;
    }

    public void setEnrollmentEnd(LocalDate enrollmentEnd) {
        this.enrollmentEnd = enrollmentEnd;
    }

    public Integer getMaxEnrollments() {
        return maxEnrollments;
    }

    public void setMaxEnrollments(Integer maxEnrollments) {
        this.maxEnrollments = maxEnrollments;
    }

    public CourseVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(CourseVisibility visibility) {
        this.visibility = visibility;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(Set<Long> staffIds) {
        this.staffIds = staffIds;
    }

    public Set<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}