package learning_management_system.backend.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for Submission entity.
 */
public class SubmissionDto {

    private Long submissionId;
    private Long assignmentId; // Linked assignment ID (if applicable)
    private Long assessmentId; // Linked assessment ID (if applicable)
    private Long studentId; // ID of the student making the submission
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long timeTaken; // Time taken in seconds
    private Double marksObtained;
    private String status; // SubmissionStatus as String
    private String gradingStatus; // GradingStatus as String
    private String feedback;
    private String rubricDetails;
    private String gradeComments;
    private String gradeLevel;
    private String timePerQuestion; // JSON-based analytics
    private String scoreDistribution; // JSON-based scoring trends
    private Double plagiarismScore;
    private String analysisReportUrl;
    private Boolean isFlaggedForPlagiarism;
    private Integer attemptNumber;
    private Integer retryCount;
    private Boolean autoGraded;
    private String submittedContent;
    private String metadata;
    private Boolean isFinalized;
    private Boolean isReviewed;
    private Date submissionDate;
    private Date dateCreated;
    private Date dateUpdated;
    private Long proctoringSessionId; // Linked proctoring session ID
    private List<Long> attachmentIds; // List of linked attachment IDs

    // Constructor

    public SubmissionDto() {}

    public SubmissionDto(Long submissionId, Long assignmentId, Long assessmentId, Long studentId, LocalDateTime startTime,
                         LocalDateTime endTime, Long timeTaken, Double marksObtained, String status, String gradingStatus,
                         String feedback, String rubricDetails, String gradeComments, String gradeLevel, String timePerQuestion,
                         String scoreDistribution, Double plagiarismScore, String analysisReportUrl, Boolean isFlaggedForPlagiarism,
                         Integer attemptNumber, Integer retryCount, Boolean autoGraded, String submittedContent, String metadata,
                         Boolean isFinalized, Boolean isReviewed, Date submissionDate, Date dateCreated, Date dateUpdated,
                         Long proctoringSessionId, List<Long> attachmentIds) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.assessmentId = assessmentId;
        this.studentId = studentId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeTaken = timeTaken;
        this.marksObtained = marksObtained;
        this.status = status;
        this.gradingStatus = gradingStatus;
        this.feedback = feedback;
        this.rubricDetails = rubricDetails;
        this.gradeComments = gradeComments;
        this.gradeLevel = gradeLevel;
        this.timePerQuestion = timePerQuestion;
        this.scoreDistribution = scoreDistribution;
        this.plagiarismScore = plagiarismScore;
        this.analysisReportUrl = analysisReportUrl;
        this.isFlaggedForPlagiarism = isFlaggedForPlagiarism;
        this.attemptNumber = attemptNumber;
        this.retryCount = retryCount;
        this.autoGraded = autoGraded;
        this.submittedContent = submittedContent;
        this.metadata = metadata;
        this.isFinalized = isFinalized;
        this.isReviewed = isReviewed;
        this.submissionDate = submissionDate;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.proctoringSessionId = proctoringSessionId;
        this.attachmentIds = attachmentIds;
    }

// Getter and Setter
    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGradingStatus() {
        return gradingStatus;
    }

    public void setGradingStatus(String gradingStatus) {
        this.gradingStatus = gradingStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRubricDetails() {
        return rubricDetails;
    }

    public void setRubricDetails(String rubricDetails) {
        this.rubricDetails = rubricDetails;
    }

    public String getGradeComments() {
        return gradeComments;
    }

    public void setGradeComments(String gradeComments) {
        this.gradeComments = gradeComments;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(String timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public String getScoreDistribution() {
        return scoreDistribution;
    }

    public void setScoreDistribution(String scoreDistribution) {
        this.scoreDistribution = scoreDistribution;
    }

    public Double getPlagiarismScore() {
        return plagiarismScore;
    }

    public void setPlagiarismScore(Double plagiarismScore) {
        this.plagiarismScore = plagiarismScore;
    }

    public String getAnalysisReportUrl() {
        return analysisReportUrl;
    }

    public void setAnalysisReportUrl(String analysisReportUrl) {
        this.analysisReportUrl = analysisReportUrl;
    }

    public Boolean getFlaggedForPlagiarism() {
        return isFlaggedForPlagiarism;
    }

    public void setFlaggedForPlagiarism(Boolean flaggedForPlagiarism) {
        isFlaggedForPlagiarism = flaggedForPlagiarism;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Boolean getAutoGraded() {
        return autoGraded;
    }

    public void setAutoGraded(Boolean autoGraded) {
        this.autoGraded = autoGraded;
    }

    public String getSubmittedContent() {
        return submittedContent;
    }

    public void setSubmittedContent(String submittedContent) {
        this.submittedContent = submittedContent;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public Boolean getReviewed() {
        return isReviewed;
    }

    public void setReviewed(Boolean reviewed) {
        isReviewed = reviewed;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
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

    public Long getProctoringSessionId() {
        return proctoringSessionId;
    }

    public void setProctoringSessionId(Long proctoringSessionId) {
        this.proctoringSessionId = proctoringSessionId;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}
