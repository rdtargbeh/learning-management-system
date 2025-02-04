package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.AssessmentType;
import learning_management_system.backend.enums.FeedbackPolicy;
import learning_management_system.backend.enums.QuestionNavigation;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

/**
 * Represents an assessment (exam or quiz) in the LMS.
 */

//@Data
@CrossOrigin("*")
@Entity
@Table (name = "assessments")
public class Assessment {

    /** Unique identifier for the assessment. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Long assessmentId;

    /** Title of the assessment. */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /** Detailed description or instructions for the assessment. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Specifies whether the assessment is an exam or quiz. */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AssessmentType type;

    /** Links the assessment to a specific course. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** Links to the user who created the assessment. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "accommodations", columnDefinition = "TEXT")
    private String accommodations; // JSON-based accessibility configuration.

    @Column(name = "accessible_for", columnDefinition = "TEXT")
    private String accessibleFor; // User roles/groups with accessibility options.

    /** Indicates if questions should be shuffled. */
    @Column(name = "shuffle_questions", nullable = false)
    private Boolean shuffleQuestions = false;

    /** Time limit for completing the assessment, in minutes. */
    @Column(name = "time_limit")
    private Integer timeLimit;

    /** Time (in minutes) when a warning is shown for remaining time. */
    @Column(name = "time_remaining_warning")
    private Integer timeRemainingWarning;

    /** Number of attempts allowed for the assessment. */
    @Column(name = "allowed_attempts", nullable = false)
    private Integer allowedAttempts = 1;

    /** Date and time when the assessment becomes available. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    /** Date and time when the assessment closes. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    /** Indicates if the assessment is visible to students. */
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    /** Indicates whether the assessment is fully auto-graded. */
    @Column(name = "is_auto_graded", nullable = false)
    private Boolean isAutoGraded = false;

    /** Indicates if correct answers are shown after submission. */
    @Column(name = "show_correct_answers", nullable = false)
    private Boolean showCorrectAnswers = true;

    /** Defines when feedback is provided. */
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_policy")
    private FeedbackPolicy feedbackPolicy;

    /** Indicates if the assessment requires a password for access. */
    @Column(name = "requires_password", nullable = false)
    private Boolean requiresPassword = false;

    /** Password required to access the assessment. */
    @Column(name = "password", length = 50)
    private String password;

    /** Indicates if the assessment requires proctoring. */
    @Column(name = "is_proctored", nullable = false)
    private Boolean isProctored = false;

    /** Controls navigation between questions. */
    @Enumerated(EnumType.STRING)
    @Column(name = "question_navigation")
    private QuestionNavigation questionNavigation;

    /** List of questions associated with the assessment. */
    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    /** Determines if questions are drawn randomly from a pool. */
    @Column(name = "randomize_questions", nullable = false)
    private Boolean randomizeQuestions = false;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** List of attachments associated with the assessment. */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    /** Indicates if students can retake the assessment upon failure. */
    @Column(name = "allow_retake_on_failure", nullable = false)
    private Boolean allowRetakeOnFailure = false;

    /** Timestamp for when the assessment was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the assessment was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    @Column(name = "pooling_criteria", columnDefinition = "TEXT")
    private String poolingCriteria; // JSON configuration for tags, topics, difficulty.




    // Constructor
    public Assessment(){}

    public Assessment(Long assessmentId, String title, String description, AssessmentType type, Course course, Boolean shuffleQuestions,
                      Integer timeLimit, Integer timeRemainingWarning, Integer allowedAttempts, Date startDate, Date endDate,
                      Boolean isPublished, Boolean isAutoGraded, Boolean showCorrectAnswers, FeedbackPolicy feedbackPolicy,
                      Boolean requiresPassword, String password, Boolean isProctored, QuestionNavigation questionNavigation,
                      List<Question> questions, Boolean randomizeQuestions, String metadata, List<Attachment> attachments,
                      List<Submission> submissions, Grading grading, Boolean allowRetakeOnFailure, User createdBy,
                      Date dateCreated, Date dateUpdated, String accommodations, String accessibleFor,String poolingCriteria) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.poolingCriteria = poolingCriteria;
        this.course = course;
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
        this.questions = questions;
        this.randomizeQuestions = randomizeQuestions;
        this.metadata = metadata;
        this.attachments = attachments;
        this.submissions = submissions;
        this.grading = grading;
        this.allowRetakeOnFailure = allowRetakeOnFailure;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.accommodations = accommodations;
        this.accessibleFor = accessibleFor;
    }

    // Getter and Setter

    public String getPoolingCriteria() {
        return poolingCriteria;
    }

    public void setPoolingCriteria(String poolingCriteria) {
        this.poolingCriteria = poolingCriteria;
    }

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Boolean getRandomizeQuestions() {
        return randomizeQuestions;
    }

    public void setRandomizeQuestions(Boolean randomizeQuestions) {
        this.randomizeQuestions = randomizeQuestions;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Boolean getAllowRetakeOnFailure() {
        return allowRetakeOnFailure;
    }

    public void setAllowRetakeOnFailure(Boolean allowRetakeOnFailure) {
        this.allowRetakeOnFailure = allowRetakeOnFailure;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
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

    public String getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public String getAccessibleFor() {
        return accessibleFor;
    }

    public void setAccessibleFor(String accessibleFor) {
        this.accessibleFor = accessibleFor;
    }
}
