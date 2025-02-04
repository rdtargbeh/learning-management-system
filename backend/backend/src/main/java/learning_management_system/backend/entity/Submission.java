package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.GradingStatus;
import learning_management_system.backend.enums.SubmissionStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Represents a submission made by a student for an assignment, quiz, or exam in the LMS.
 */


@CrossOrigin("*")
@Entity
@Table(name = "submissions")
public class Submission {

    /** Unique identifier for the submission. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long submissionId;

    /** Links the submission to an assignment, if applicable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = true)
    private Assignment assignment;

    /** Links the submission to an assessment, if applicable. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = true)
    private Assessment assessment;

    /** The student who made the submission. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Time Tracking
    /**The timestamp when the submission was started.*/
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /** The timestamp when the submission was completed.*/
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /** Total marks obtained in this submission.*/
    @Column(name = "time_taken")
    private Long timeTaken; // Time taken in seconds

    // Grading and Feedback
    /** Marks obtained for this submission. */
    @Column(name = "marks_obtained", nullable = true)
    private Double marksObtained;

    /** Status of the submission (e.g., SUBMITTED, PENDING, GRADED). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SubmissionStatus status = SubmissionStatus.SUBMITTED;

    /** Grading status of the submission (e.g., PENDING, COMPLETED).*/
    @Column(name = "grading_status", nullable = false, length = 50)
    private GradingStatus gradingStatus = GradingStatus.PENDING; // Status of grading (e.g., PENDING, COMPLETED)

    /** Feedback provided by the grader. */
    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    /**Rubric-based grading details, if applicable.*/
    @Column(name = "rubric_details", columnDefinition = "TEXT")
    private String rubricDetails;

    private String gradeComments; // New

    /** The grade level of the submission (e.g., Pass, Fail).*/
    @Column(name = "grade_level", length = 50)
    private String gradeLevel;

    private String timePerQuestion; // JSON-based analytics

    private String scoreDistribution; // JSON-based scoring trends

    /** Plagiarism score for the submission. */
    @Column(name = "plagiarism_score", nullable = true)
    private Double plagiarismScore;

    /** URL to the plagiarism or analysis report. */
    @Column(name = "analysis_report_url", length = 255)
    private String analysisReportUrl;

    /**Indicates if the submission was flagged for plagiarism.*/
    @Column(name = "is_flagged_for_plagiarism", nullable = false)
    private Boolean isFlaggedForPlagiarism = false;


    // Retry and Attempts
    /** Attempt number for resubmissions. */
    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber = 1;

    /** Number of retries allowed for the submission.*/
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0; // Number of retries for the assessment

    /** List of answers provided in the submission.*/
    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;

    /** List of attachments associated with the submission. */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    /** Indicates if the submission was automatically graded. */
    @Column(name = "auto_graded", nullable = false)
    private Boolean autoGraded = false;

    /** Inline text or other content submitted by the student. */
    @Column(name = "submitted_content", columnDefinition = "TEXT")
    private String submittedContent;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /**Indicates whether the submission is finalized and locked from further changes.*/
    @Column(name = "is_finalized", nullable = false)
    private Boolean isFinalized = false;

    /** Indicates if the submission has been reviewed by a teacher or proctor.*/
    @Column(name = "is_reviewed", nullable = false)
    private Boolean isReviewed = false;

    /** Timestamp for when the submission was made. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "submission_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date submissionDate = new Date();

    /** Timestamp for when the submission was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the submission was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** Optional reference to the proctoring session associated with this submission.*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proctoring_session_id")
    private ProctoringSession proctoringSession;


    public Submission() {
    }

    public Submission(Long submissionId, Assignment assignment, Assessment assessment, User student, LocalDateTime startTime, LocalDateTime endTime, Long timeTaken, Double marksObtained, SubmissionStatus status, GradingStatus gradingStatus, String feedback, String rubricDetails, String gradeComments, String gradeLevel, String timePerQuestion, String scoreDistribution, Double plagiarismScore, String analysisReportUrl, Boolean isFlaggedForPlagiarism, Integer attemptNumber, Integer retryCount, List<Answer> answers, List<Attachment> attachments, Boolean autoGraded, String submittedContent, String metadata, Boolean isFinalized, Boolean isReviewed, Date submissionDate, Date dateCreated, Date dateUpdated, ProctoringSession proctoringSession) {
        this.submissionId = submissionId;
        this.assignment = assignment;
        this.assessment = assessment;
        this.student = student;
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
        this.answers = answers;
        this.attachments = attachments;
        this.autoGraded = autoGraded;
        this.submittedContent = submittedContent;
        this.metadata = metadata;
        this.isFinalized = isFinalized;
        this.isReviewed = isReviewed;
        this.submissionDate = submissionDate;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.proctoringSession = proctoringSession;
    }


    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
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

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public GradingStatus getGradingStatus() {
        return gradingStatus;
    }

    public void setGradingStatus(GradingStatus gradingStatus) {
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public ProctoringSession getProctoringSession() {
        return proctoringSession;
    }

    public void setProctoringSession(ProctoringSession proctoringSession) {
        this.proctoringSession = proctoringSession;
    }
}
