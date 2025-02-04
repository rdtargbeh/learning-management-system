package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grade record for a student for a specific grade book item.
 */

@CrossOrigin("*")
@Entity
@Table(name = "grade_book_records")
public class GradeBookRecord {

    /** Unique identifier for the grade book record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false, updatable = false)
    private Long recordId;

    /** The grade book item this record belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private GradeBookItem gradeBookItem;

    /** The student associated with this grade record. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /** The score obtained by the student. */
    @Column(name = "score", nullable = true)
    private Double score;

    /**
     * Whether the grade has been verified by an instructor or administrator.
     */
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    /**
     * Tracks the user who verified the grade for auditing purposes.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by", nullable = true)
    private User verifiedBy;

    /**
     * Whether the grade record is locked to prevent modifications.
     */
    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    /** Feedback or comments provided by the instructor. */
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    /** Feedback for the student */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbackList = new ArrayList<>();

    /** Indicates whether the grade is finalized. */
    @Column(name = "is_finalized", nullable = false)
    private Boolean isFinalized = false;

    /** Metadata for extensibility (custom attributes). */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the grade record was created. */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the grade record was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    public GradeBookRecord() {}
    public GradeBookRecord(Long recordId, GradeBookItem gradeBookItem, Student student, Double score, Boolean isVerified, User verifiedBy, Boolean isLocked, String feedback, List<Feedback> feedbackList, Boolean isFinalized, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.recordId = recordId;
        this.gradeBookItem = gradeBookItem;
        this.student = student;
        this.score = score;
        this.isVerified = isVerified;
        this.verifiedBy = verifiedBy;
        this.isLocked = isLocked;
        this.feedback = feedback;
        this.feedbackList = feedbackList;
        this.isFinalized = isFinalized;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }


    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public GradeBookItem getGradeBookItem() {
        return gradeBookItem;
    }

    public void setGradeBookItem(GradeBookItem gradeBookItem) {
        this.gradeBookItem = gradeBookItem;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
