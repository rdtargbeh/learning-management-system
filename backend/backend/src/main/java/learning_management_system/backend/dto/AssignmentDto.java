package learning_management_system.backend.dto;

import learning_management_system.backend.enums.AssignmentStatus;
import learning_management_system.backend.enums.AssignmentType;
import learning_management_system.backend.enums.AssignmentVisibility;
import learning_management_system.backend.enums.SubmissionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AssignmentDto {

    private Long assignmentId;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private LocalDateTime availableFrom;
    private LocalDateTime availableUntil;
    private Boolean isPublished;
    private Boolean isActive;
    private Boolean enablePeerReviews;
    private Double completionRate;
    private Double averageGrade;
    private SubmissionType submissionType;
    private AssignmentType assignmentType;
    private Long gradingId;
    private Integer maxAttempts;
    private Integer gracePeriodMinutes;
    private AssignmentVisibility visibility;
    private Long courseId;
    private Long moduleId;
    private Long studentGroupId;
    private Set<Long> assignedStudentIds;
    private String instructions;
    private AssignmentStatus status;
    private Boolean gradingCompleted;
    private Long createdBy;
    private String metadata;
    private List<Long> attachmentIds; // List of Attachment IDs associated with the assignment



    // Constructors
    public AssignmentDto() {}

    public AssignmentDto(Long assignmentId, String title, String description, LocalDateTime dueDate, LocalDateTime availableFrom,
                         LocalDateTime availableUntil, Boolean isPublished, Boolean isActive, Boolean enablePeerReviews,
                         Double completionRate, Double averageGrade, SubmissionType submissionType, AssignmentType assignmentType,
                         Long gradingId, Integer maxAttempts, Integer gracePeriodMinutes, AssignmentVisibility visibility,
                         Long courseId, Long moduleId, Long studentGroupId, Set<Long> assignedStudentIds, String instructions,
                         AssignmentStatus status, Boolean gradingCompleted, Long createdBy, String metadata, List<Long> attachmentIds) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.isPublished = isPublished;
        this.isActive = isActive;
        this.enablePeerReviews = enablePeerReviews;
        this.completionRate = completionRate;
        this.averageGrade = averageGrade;
        this.submissionType = submissionType;
        this.assignmentType = assignmentType;
        this.gradingId = gradingId;
        this.maxAttempts = maxAttempts;
        this.gracePeriodMinutes = gracePeriodMinutes;
        this.visibility = visibility;
        this.courseId = courseId;
        this.moduleId = moduleId;
        this.studentGroupId = studentGroupId;
        this.assignedStudentIds = assignedStudentIds;
        this.instructions = instructions;
        this.status = status;
        this.gradingCompleted = gradingCompleted;
        this.createdBy = createdBy;
        this.metadata = metadata;
        this.attachmentIds = attachmentIds;
    }


    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDateTime getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalDateTime availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getEnablePeerReviews() {
        return enablePeerReviews;
    }

    public void setEnablePeerReviews(Boolean enablePeerReviews) {
        this.enablePeerReviews = enablePeerReviews;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Long getGradingId() {
        return gradingId;
    }

    public void setGradingId(Long gradingId) {
        this.gradingId = gradingId;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Integer getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }

    public void setGracePeriodMinutes(Integer gracePeriodMinutes) {
        this.gracePeriodMinutes = gracePeriodMinutes;
    }

    public AssignmentVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(AssignmentVisibility visibility) {
        this.visibility = visibility;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public Set<Long> getAssignedStudentIds() {
        return assignedStudentIds;
    }

    public void setAssignedStudentIds(Set<Long> assignedStudentIds) {
        this.assignedStudentIds = assignedStudentIds;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Boolean getGradingCompleted() {
        return gradingCompleted;
    }

    public void setGradingCompleted(Boolean gradingCompleted) {
        this.gradingCompleted = gradingCompleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}

