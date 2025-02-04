package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.utility.QuestionMarksId;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;


@CrossOrigin("*")
@Entity
@Table(name = "question_marks")
public class QuestionMarks {

    /**
     * Composite key: gradingId and questionId.
     */
    @EmbeddedId
    private QuestionMarksId id; // Composite key: gradingId and questionId

    /** Many-to-one association with the Grading entity.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gradingId")
    private Grading grading;

    /** Many-to-one association with the Question entity.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("questionId")
    private Question question;

    /** Marks assigned to the question.*/
    @Column(nullable = false)
    private Integer marks;

    /** Marks for partially correct answers.*/
    @Column(name = "partial_marks")
    private Integer partialMarks;

    /** JSON-based rubric for detailed grading.*/
    @Column(name = "rubric", columnDefinition = "TEXT")
    private String rubric;

    /** Indicates if this grading configuration is adaptive.*/
    @Column(name = "adaptive_grading", nullable = false)
    private Boolean adaptiveGrading = false;

    /** The average score for this question across all submissions.*/
    @Column(name = "average_score")
    private Double averageScore;

    /** The total number of attempts for this question.*/
    @Column(name = "total_attempts")
    private Long totalAttempts;

    /** The total marks earned for this question across all attempts.*/
    @Column(name = "total_marks_earned")
    private Long totalMarksEarned;

    /** Timestamp for when the entry was created.*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the entry was last updated.*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Adjust as needed
    @JoinColumn(name = "feedback_id")
    private Feedback feedback; // Link to Feedback

    /** Validates if the rubric conforms to the required JSON schema.*/
    public boolean isValidRubric() {
        // Stub for JSON schema validation.
        // Replace with actual validation logic if needed.
        return rubric != null && !rubric.isEmpty();
    }


    public QuestionMarks() {
    }

    public QuestionMarks(QuestionMarksId id, Grading grading, Question question, Integer marks, Integer partialMarks,
                         String rubric, Boolean adaptiveGrading, Double averageScore, Long totalAttempts, Long totalMarksEarned,
                         Date dateCreated, Date dateUpdated, Feedback feedback) {
        this.id = id;
        this.grading = grading;
        this.question = question;
        this.marks = marks;
        this.partialMarks = partialMarks;
        this.rubric = rubric;
        this.adaptiveGrading = adaptiveGrading;
        this.averageScore = averageScore;
        this.totalAttempts = totalAttempts;
        this.totalMarksEarned = totalMarksEarned;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.feedback = feedback;
    }

    public QuestionMarksId getId() {
        return id;
    }

    public void setId(QuestionMarksId id) {
        this.id = id;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }
}

