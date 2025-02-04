package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.AssignmentStatus;
import learning_management_system.backend.enums.AssignmentType;
import learning_management_system.backend.enums.AssignmentVisibility;
import learning_management_system.backend.enums.SubmissionType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Data

@CrossOrigin("*")
@Entity
@Table(name = "assignments")
public class Assignment {

    /** Unique identifier for the assignment. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    /** Title of the assignment. */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /** Detailed description of the assignment. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Deadline for submitting the assignment. */
    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    /** Date when the assignment becomes available. */
    @Column(name = "available_from")
    private LocalDateTime availableFrom;

    /** Date after which submissions are no longer accepted. */
    @Column(name = "available_until")
    private LocalDateTime availableUntil;

    /** Indicates if the assignment is published and visible to students. */
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    /** Indicates if the assignment is active or archived. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Enables peer review functionality for submissions. */
    @Column(name = "enable_peer_reviews", nullable = false)
    private Boolean enablePeerReviews = false;

    /** Tracks the percentage of students who have completed the assignment. */
    @Column(name = "completion_rate")
    private Double completionRate;

    /** Tracks the average grade of submissions. */
    @Column(name = "average_grade")
    private Double averageGrade;

    /** Type of submission allowed for the assignment. */
    @Enumerated(EnumType.STRING)
    @Column(name = "submission_type", nullable = false, length = 20)
    private SubmissionType submissionType;

    /** Specifies the type of assignment (e.g., ESSAY, PROJECT). */
    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false, length = 20)
    private AssignmentType assignmentType;

    /**
     * The grading configuration for this assignment.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    /** Maximum number of submission attempts allowed. */
    @Column(name = "max_attempts", nullable = false)
    private Integer maxAttempts;

    /** Defines a grace period after the deadline before penalties apply. */
    @Column(name = "grace_period_minutes", nullable = false)
    private Integer gracePeriodMinutes = 0;

    /** Access control for the assignment. */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private AssignmentVisibility visibility;

    /** The course this assignment belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** The module this assignment is part of (optional). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private CourseModule module;

    /** Group of students assigned to the assignment. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_group_id")
    private StudentGroup studentGroup;

    /** Explicitly assigned students for the assignment. */
//    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    @ManyToMany
    @JoinTable(
            name = "assignment_students", // Join table
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedStudents = new HashSet<>();

    /** Detailed instructions or guidelines for completing the assignment. */
    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    /** Tracks the current status of the assignment. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AssignmentStatus status = AssignmentStatus.DRAFT;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    /** Indicates whether grading for this assignment is completed. */
    @Column(name = "grading_completed", nullable = false)
    private Boolean gradingCompleted = false;

    /** User who created the assignment. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the assignment was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the assignment was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();




    // Constructor
    public Assignment(){}

    public Assignment(Long assignmentId, String title, String description, LocalDateTime dueDate, LocalDateTime availableFrom,
                      LocalDateTime availableUntil, Boolean isPublished, Boolean isActive, Boolean enablePeerReviews, Double completionRate,
                      Double averageGrade, SubmissionType submissionType, AssignmentType assignmentType, Grading grading, Integer maxAttempts,
                      Integer gracePeriodMinutes, AssignmentVisibility visibility, Course course, CourseModule module, StudentGroup studentGroup,
                      Set<User> assignedStudents, String instructions, AssignmentStatus status, List<Submission> submissions,
                      List<Attachment> attachments, Boolean gradingCompleted, User createdBy, String metadata,
                      Date dateCreated, Date dateUpdated) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.isPublished = isPublished;
        this.isActive = isActive;
        this.enablePeerReviews = enablePeerReviews;
        this.completionRate = completionRate;
        this.averageGrade = averageGrade;
        this.submissionType = submissionType;
        this.assignmentType = assignmentType;
        this.grading = grading;
        this.maxAttempts = maxAttempts;
        this.gracePeriodMinutes = gracePeriodMinutes;
        this.visibility = visibility;
        this.course = course;
        this.module = module;
        this.studentGroup = studentGroup;
        this.assignedStudents = assignedStudents;
        this.instructions = instructions;
        this.status = status;
        this.submissions = submissions;
        this.attachments = attachments;
        this.gradingCompleted = gradingCompleted;
        this.createdBy = createdBy;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalDateTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalDateTime getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalDateTime availableUntil) {
        this.availableUntil = availableUntil;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getEnablePeerReviews() {
        return enablePeerReviews;
    }

    public void setEnablePeerReviews(Boolean enablePeerReviews) {
        this.enablePeerReviews = enablePeerReviews;
    }

    public Double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public SubmissionType getSubmissionType() {
        return submissionType;
    }

    public void setSubmissionType(SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Integer getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }

    public void setGracePeriodMinutes(Integer gracePeriodMinutes) {
        this.gracePeriodMinutes = gracePeriodMinutes;
    }

    public AssignmentVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(AssignmentVisibility visibility) {
        this.visibility = visibility;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Set<User> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(Set<User> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Boolean getGradingCompleted() {
        return gradingCompleted;
    }

    public void setGradingCompleted(Boolean gradingCompleted) {
        this.gradingCompleted = gradingCompleted;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

