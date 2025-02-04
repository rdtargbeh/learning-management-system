package learning_management_system.backend.dto;

import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.enums.GradingPolicy;
import learning_management_system.backend.enums.LateSubmissionPolicy;

import java.time.LocalDateTime;
import java.util.Map;

public class GradingDto {

    private Long gradingId;
    private GradingPolicy gradingPolicy;
    private Integer totalMarks;
    private String gradingRubric;
    private Double attemptPenalty;
    private LateSubmissionPolicy lateSubmissionPolicy;
    private Double lateSubmissionPenaltyPercentage;
    private Map<Long, Integer> questionMarks;
    private String gradingTiers;
    private Boolean enableNormalization;
    private Boolean autoPublish;
    private Double scalingFactor;
    private Long assessmentId;
    private Long assignmentId;
    private Long gradeBookItemId;
    private Long questionId;
    private GradingLinkedEntityType linkedEntityType; // ASSIGNMENT, ASSESSMENT, QUESTION
    private Long linkedEntityId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // Constructor
    public GradingDto(){}

    public GradingDto(Long gradingId, GradingPolicy gradingPolicy, Integer totalMarks, String gradingRubric, Double attemptPenalty,
                      LateSubmissionPolicy lateSubmissionPolicy, Double lateSubmissionPenaltyPercentage, Map<Long, Integer> questionMarks,
                      String gradingTiers, Boolean enableNormalization, Boolean autoPublish, Double scalingFactor, Long assessmentId,
                      Long assignmentId, Long gradeBookItemId, Long questionId, GradingLinkedEntityType linkedEntityType,
                      Long linkedEntityId, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.gradingId = gradingId;
        this.gradingPolicy = gradingPolicy;
        this.totalMarks = totalMarks;
        this.gradingRubric = gradingRubric;
        this.attemptPenalty = attemptPenalty;
        this.lateSubmissionPolicy = lateSubmissionPolicy;
        this.lateSubmissionPenaltyPercentage = lateSubmissionPenaltyPercentage;
        this.questionMarks = questionMarks;
        this.gradingTiers = gradingTiers;
        this.enableNormalization = enableNormalization;
        this.autoPublish = autoPublish;
        this.scalingFactor = scalingFactor;
        this.assessmentId = assessmentId;
        this.assignmentId = assignmentId;
        this.gradeBookItemId = gradeBookItemId;
        this.questionId = questionId;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getter and Setter
    public Long getGradingId() {
        return gradingId;
    }

    public void setGradingId(Long gradingId) {
        this.gradingId = gradingId;
    }

    public GradingPolicy getGradingPolicy() {
        return gradingPolicy;
    }

    public void setGradingPolicy(GradingPolicy gradingPolicy) {
        this.gradingPolicy = gradingPolicy;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getGradingRubric() {
        return gradingRubric;
    }

    public void setGradingRubric(String gradingRubric) {
        this.gradingRubric = gradingRubric;
    }

    public Double getAttemptPenalty() {
        return attemptPenalty;
    }

    public void setAttemptPenalty(Double attemptPenalty) {
        this.attemptPenalty = attemptPenalty;
    }

    public LateSubmissionPolicy getLateSubmissionPolicy() {
        return lateSubmissionPolicy;
    }

    public void setLateSubmissionPolicy(LateSubmissionPolicy lateSubmissionPolicy) {
        this.lateSubmissionPolicy = lateSubmissionPolicy;
    }

    public Double getLateSubmissionPenaltyPercentage() {
        return lateSubmissionPenaltyPercentage;
    }

    public void setLateSubmissionPenaltyPercentage(Double lateSubmissionPenaltyPercentage) {
        this.lateSubmissionPenaltyPercentage = lateSubmissionPenaltyPercentage;
    }

    public Map<Long, Integer> getQuestionMarks() {
        return questionMarks;
    }

    public void setQuestionMarks(Map<Long, Integer> questionMarks) {
        this.questionMarks = questionMarks;
    }

    public String getGradingTiers() {
        return gradingTiers;
    }

    public void setGradingTiers(String gradingTiers) {
        this.gradingTiers = gradingTiers;
    }

    public Boolean getEnableNormalization() {
        return enableNormalization;
    }

    public void setEnableNormalization(Boolean enableNormalization) {
        this.enableNormalization = enableNormalization;
    }

    public Boolean getAutoPublish() {
        return autoPublish;
    }

    public void setAutoPublish(Boolean autoPublish) {
        this.autoPublish = autoPublish;
    }

    public Double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(Double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getGradeBookItemId() {
        return gradeBookItemId;
    }

    public void setGradeBookItemId(Long gradeBookItemId) {
        this.gradeBookItemId = gradeBookItemId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public GradingLinkedEntityType getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(GradingLinkedEntityType linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public Long getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(Long linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}

