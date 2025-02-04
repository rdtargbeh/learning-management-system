package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.ProctoringStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

@CrossOrigin("*")
@Entity
@Table(name = "proctoring_sessions")
public class ProctoringSession {

    /** Unique identifier for the proctoring session. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proctoring_session_id")
    private Long proctoringSessionId;

    /** The assessment linked to this proctoring session. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    /** The student who is being proctored. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /** Status of the proctoring session (e.g., INITIATED, IN_PROGRESS, APPROVED, REJECTED). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProctoringStatus status;

    /** Start time of the proctoring session. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /** End time of the proctoring session. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the proctoring session was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false)
    private Date dateCreated = new Date();

    /** Timestamp for when the proctoring session was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();


    // Utility method to update status
    public void updateStatus(ProctoringStatus newStatus) {
        this.status = newStatus;
        this.dateUpdated = new Date(); // Update timestamp
    }

    // Utility method to check readiness
    public boolean isReadyForValidation() {
        return this.startTime != null && this.metadata != null && !this.metadata.isEmpty();
    }


    public ProctoringSession(){}

    public ProctoringSession(Long proctoringSessionId, Assessment assessment, Student student, ProctoringStatus status, Date startTime, Date endTime, String metadata, Date dateCreated, Date dateUpdated) {
        this.proctoringSessionId = proctoringSessionId;
        this.assessment = assessment;
        this.student = student;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and Setters

    public Long getProctoringSessionId() {
        return proctoringSessionId;
    }

    public void setProctoringSessionId(Long proctoringSessionId) {
        this.proctoringSessionId = proctoringSessionId;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ProctoringStatus getStatus() {
        return status;
    }

    public void setStatus(ProctoringStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
