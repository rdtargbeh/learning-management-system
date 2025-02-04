package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.enums.GradingPolicy;
import learning_management_system.backend.enums.LateSubmissionPolicy;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Centralized grading configuration for assignments and assessments.
 */
@CrossOrigin("*")
@Entity
@Table(name = "gradings")
public class Grading {

    /** Unique identifier for the grading configuration. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grading_id", nullable = false, updatable = false)
    private Long gradingId;

    /** Grading policy: AUTO or MANUAL. */
    @Enumerated(EnumType.STRING)
    @Column(name = "grading_policy", nullable = false, length = 20)
    private GradingPolicy gradingPolicy;

    /** Total marks allocated for the assignment or assessment. */
    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    /** JSON-based definition of grading criteria or rubrics. */
    @Column(name = "grading_rubric", columnDefinition = "TEXT")
    private String gradingRubric;

    /** Penalty for additional attempts. */
    @Column(name = "attempt_penalty", nullable = true)
    private Double attemptPenalty;

    /** Defines how late submissions are handled. */
    @Enumerated(EnumType.STRING)
    @Column(name = "late_submission_policy", nullable = true)
    private LateSubmissionPolicy lateSubmissionPolicy;

    /** Percentage penalty applied for late submissions. */
    @Column(name = "late_submission_penalty_percentage", nullable = false)
    private Double lateSubmissionPenaltyPercentage = 0.0;

    /** Marks distribution for individual questions. */
    @ElementCollection
    @CollectionTable(name = "question_marks", joinColumns = @JoinColumn(name = "grading_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "marks", nullable = false)
    private Map<Long, Integer> questionMarks;

    /** JSON format for grading tiers (e.g., A: 90–100). */
    @Column(name = "grading_tiers", columnDefinition = "TEXT")
    private String gradingTiers;

    /** Whether to normalize grades. */
    @Column(name = "enable_normalization", nullable = false)
    private Boolean enableNormalization = false;

    /** Automatic grade publishing. */
    @Column(name = "auto_publish", nullable = false)
    private Boolean autoPublish = false;

    /** Scaling factor for grade adjustments. */
    @Column(name = "scaling_factor", nullable = true)
    private Double scalingFactor;

    /** Associated assessment (e.g., quiz or exam). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    /** Associated assignment (e.g., project or essay). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    /** Associated assignment (e.g., project or essay). */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_book_item_id")
    private GradeBookItem gradeBookItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "linked_entity_type", nullable = false)
    private GradingLinkedEntityType linkedEntityType;

    @Column(name = "linked_entity_id", nullable = false)
    private Long linkedEntityId;

    /** Timestamp for when the grading configuration was created. */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the grading configuration was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    public Grading() {
    }

    public Grading(Long gradingId, GradingPolicy gradingPolicy, Integer totalMarks, String gradingRubric, Double attemptPenalty, LateSubmissionPolicy lateSubmissionPolicy, Double lateSubmissionPenaltyPercentage, Map<Long, Integer> questionMarks, String gradingTiers, Boolean enableNormalization, Boolean autoPublish, Double scalingFactor, Assessment assessment, Assignment assignment, Question question, GradeBookItem gradeBookItem, GradingLinkedEntityType linkedEntityType, Long linkedEntityId, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
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
        this.assessment = assessment;
        this.assignment = assignment;
        this.question = question;
        this.gradeBookItem = gradeBookItem;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and setters omitted for brevity

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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public GradeBookItem getGradeBookItem() {
        return gradeBookItem;
    }

    public void setGradeBookItem(GradeBookItem gradeBookItem) {
        this.gradeBookItem = gradeBookItem;
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

