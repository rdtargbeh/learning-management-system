package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

/**
 * Represents the history of changes made to grade book records.
 */
@CrossOrigin("*")
@Entity
@Table(name = "grade_book_histories")
public class GradeBookHistory {

    /** Unique identifier for the grade history entry. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", nullable = false, updatable = false)
    private Long historyId;

    /** The grade book record being modified. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private GradeBookRecord gradeBookRecord;

    /** The previous score before the change. */
    @Column(name = "previous_score", nullable = true)
    private Double previousScore;

    /** The new score after the change. */
    @Column(name = "new_score", nullable = true)
    private Double newScore;

    /** The user who made the change. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private User changedBy;

    /** The reason for the grade change. */
    @Column(name = "change_reason", columnDefinition = "TEXT")
    private String changeReason;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the grade change was made. */
    @Column(name = "date_changed", nullable = false)
    private LocalDateTime dateChanged = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_book_id", nullable = false)
    private GradeBook gradeBook;

    // Constructor

    public GradeBookHistory() {}

    public GradeBookHistory(Long historyId, GradeBookRecord gradeBookRecord, Double previousScore, Double newScore, User changedBy,
                            String changeReason, String metadata, LocalDateTime dateChanged, GradeBook gradeBook) {
        this.historyId = historyId;
        this.gradeBookRecord = gradeBookRecord;
        this.previousScore = previousScore;
        this.newScore = newScore;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
        this.metadata = metadata;
        this.dateChanged = dateChanged;
        this.gradeBook = gradeBook;
    }

    // Getter and Setter

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public GradeBookRecord getGradeBookRecord() {
        return gradeBookRecord;
    }

    public void setGradeBookRecord(GradeBookRecord gradeBookRecord) {
        this.gradeBookRecord = gradeBookRecord;
    }

    public Double getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(Double previousScore) {
        this.previousScore = previousScore;
    }

    public Double getNewScore() {
        return newScore;
    }

    public void setNewScore(Double newScore) {
        this.newScore = newScore;
    }

    public User getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(User changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }

    public GradeBook getGradeBook() {
        return gradeBook;
    }

    public void setGradeBook(GradeBook gradeBook) {
        this.gradeBook = gradeBook;
    }
}

