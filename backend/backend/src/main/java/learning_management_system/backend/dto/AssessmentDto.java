package learning_management_system.backend.dto;

import learning_management_system.backend.enums.AssessmentType;
import learning_management_system.backend.enums.FeedbackPolicy;
import learning_management_system.backend.enums.QuestionNavigation;

import java.util.Date;
import java.util.List;

//@Data
public class AssessmentDto {
    private Long assessmentId;
    private String title;
    private String description;
    private AssessmentType type;
    private Long courseId;
//    private Long userId;
    private Long createdBy;
    private Boolean shuffleQuestions;
    private Integer timeLimit;
    private Integer timeRemainingWarning;
    private Integer allowedAttempts;
    private Date startDate;
    private Date endDate;
    private Boolean isPublished;
    private Boolean isAutoGraded;
    private Boolean showCorrectAnswers;
    private FeedbackPolicy feedbackPolicy;
    private Boolean requiresPassword;
    private String password;
    private Boolean isProctored;
    private QuestionNavigation questionNavigation;
    private Boolean randomizeQuestions;
    private Boolean allowRetakeOnFailure;
    private List<Long> questionIds;
    private List<Long> attachmentIds;
    private Long gradingId;
    private Date dateCreated;
    private Date dateUpdated;
    private String accommodations; // JSON-based accessibility configuration.
    private String accessibleFor;

    // Constructor
    public AssessmentDto(){}
    public AssessmentDto(Long assessmentId, String title, String description, AssessmentType type, Long courseId, Boolean shuffleQuestions,
                         Integer timeLimit, Integer timeRemainingWarning, Integer allowedAttempts, Date startDate, Date endDate,
                         Boolean isPublished, Boolean isAutoGraded, Boolean showCorrectAnswers, FeedbackPolicy feedbackPolicy,
                         Boolean requiresPassword, String password, Boolean isProctored, QuestionNavigation questionNavigation,
                         Boolean randomizeQuestions, Boolean allowRetakeOnFailure, List<Long> questionIds, List<Long> attachmentIds,
                         Long gradingId, Long createdBy, Date dateCreated, Date dateUpdated) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.courseId = courseId;
        this.shuffleQuestions = shuffleQuestions;
        this.timeLimit = timeLimit;
        this.timeRemainingWarning = timeRemainingWarning;
        this.allowedAttempts = allowedAttempts;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPublished = isPublished;
        this.isAutoGraded = isAutoGraded;
        this.showCorrectAnswers = showCorrectAnswers;
        this.feedbackPolicy = feedbackPolicy;
        this.requiresPassword = requiresPassword;
        this.password = password;
        this.isProctored = isProctored;
        this.questionNavigation = questionNavigation;
        this.randomizeQuestions = randomizeQuestions;
        this.allowRetakeOnFailure = allowRetakeOnFailure;
        this.questionIds = questionIds;
        this.attachmentIds = attachmentIds;
        this.gradingId = gradingId;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getter and Setter

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
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

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Boolean getShuffleQuestions() {
        return shuffleQuestions;
    }

    public void setShuffleQuestions(Boolean shuffleQuestions) {
        this.shuffleQuestions = shuffleQuestions;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getTimeRemainingWarning() {
        return timeRemainingWarning;
    }

    public void setTimeRemainingWarning(Integer timeRemainingWarning) {
        this.timeRemainingWarning = timeRemainingWarning;
    }

    public Integer getAllowedAttempts() {
        return allowedAttempts;
    }

    public void setAllowedAttempts(Integer allowedAttempts) {
        this.allowedAttempts = allowedAttempts;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getAutoGraded() {
        return isAutoGraded;
    }

    public void setAutoGraded(Boolean autoGraded) {
        isAutoGraded = autoGraded;
    }

    public Boolean getShowCorrectAnswers() {
        return showCorrectAnswers;
    }

    public void setShowCorrectAnswers(Boolean showCorrectAnswers) {
        this.showCorrectAnswers = showCorrectAnswers;
    }

    public FeedbackPolicy getFeedbackPolicy() {
        return feedbackPolicy;
    }

    public void setFeedbackPolicy(FeedbackPolicy feedbackPolicy) {
        this.feedbackPolicy = feedbackPolicy;
    }

    public Boolean getRequiresPassword() {
        return requiresPassword;
    }

    public void setRequiresPassword(Boolean requiresPassword) {
        this.requiresPassword = requiresPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getProctored() {
        return isProctored;
    }

    public void setProctored(Boolean proctored) {
        isProctored = proctored;
    }

    public QuestionNavigation getQuestionNavigation() {
        return questionNavigation;
    }

    public void setQuestionNavigation(QuestionNavigation questionNavigation) {
        this.questionNavigation = questionNavigation;
    }

    public Boolean getRandomizeQuestions() {
        return randomizeQuestions;
    }

    public void setRandomizeQuestions(Boolean randomizeQuestions) {
        this.randomizeQuestions = randomizeQuestions;
    }

    public Boolean getAllowRetakeOnFailure() {
        return allowRetakeOnFailure;
    }

    public void setAllowRetakeOnFailure(Boolean allowRetakeOnFailure) {
        this.allowRetakeOnFailure = allowRetakeOnFailure;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public Long getGradingId() {
        return gradingId;
    }

    public void setGradingId(Long gradingId) {
        this.gradingId = gradingId;
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
}
