package learning_management_system.backend.dto;

import java.util.Date;

/**
 * Data Transfer Object for QuestionMarks entity.
 */
public class QuestionMarksDto {

    /** Composite key for QuestionMarks (gradingId and questionId).*/
    private Long gradingId;
    private Long questionId;

    /** Marks assigned to the question.*/
    private Integer marks;

    /** Marks for partially correct answers.*/
    private Integer partialMarks;

    /** JSON-based rubric for detailed grading.*/
    private String rubric;

    /** Feedback for the student.*/
    private String feedback;

    /** Indicates if this grading configuration is adaptive.*/
    private Boolean adaptiveGrading;

    /** The average score for this question across all submissions.*/
    private Double averageScore;

    /** The total number of attempts for this question.*/
    private Long totalAttempts;
    /** The total marks earned for this question across all attempts.*/
    private Long totalMarksEarned;
    /** Timestamp for when the entry was created.*/
    private Date dateCreated;
    /** Timestamp for when the entry was last updated.*/
    private Date dateUpdated;


    // Constructor

    public QuestionMarksDto() {}

    public QuestionMarksDto(Long gradingId, Long questionId, Integer marks, Integer partialMarks, String rubric, String feedback, Boolean adaptiveGrading, Double averageScore, Long totalAttempts, Long totalMarksEarned, Date dateCreated, Date dateUpdated) {
        this.gradingId = gradingId;
        this.questionId = questionId;
        this.marks = marks;
        this.partialMarks = partialMarks;
        this.rubric = rubric;
        this.feedback = feedback;
        this.adaptiveGrading = adaptiveGrading;
        this.averageScore = averageScore;
        this.totalAttempts = totalAttempts;
        this.totalMarksEarned = totalMarksEarned;
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getPartialMarks() {
        return partialMarks;
    }

    public void setPartialMarks(Integer partialMarks) {
        this.partialMarks = partialMarks;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Boolean getAdaptiveGrading() {
        return adaptiveGrading;
    }

    public void setAdaptiveGrading(Boolean adaptiveGrading) {
        this.adaptiveGrading = adaptiveGrading;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Long getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(Long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public Long getTotalMarksEarned() {
        return totalMarksEarned;
    }

    public void setTotalMarksEarned(Long totalMarksEarned) {
        this.totalMarksEarned = totalMarksEarned;
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
