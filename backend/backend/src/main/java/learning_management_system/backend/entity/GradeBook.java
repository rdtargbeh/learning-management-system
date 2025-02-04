package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.GradingPolicy;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a grade book for a course, managing all grading-related information.
 */
@CrossOrigin("*")
@Entity
@Table(name = "grade_books")
public class GradeBook {

    /**
     * Unique identifier for the grade book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_book_id", nullable = false, updatable = false)
    private Long gradeBookId;

    /**
     * The course associated with this grade book.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * Grading policy: TOTAL_POINTS or WEIGHTED.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "grading_policy", nullable = false)
    private GradingPolicy gradingPolicy;

    /**
     * JSON-based custom grading scale.
     */
    @Column(name = "grading_scale", columnDefinition = "TEXT")
    private String gradingScale;

    /**
     * Total points for all graded activities in the course.
     */
    @Column(name = "total_course_points", nullable = false)
    private Double totalCoursePoints = 0.0;

    /**
     * Total points the students have achieved in the course so far.
     */
    @Column(name = "current_points_achieved", nullable = false)
    private Double currentPointsAchieved = 0.0;

    /**
     * Total weight of all grade categories for weighted grading.
     */
    @Column(name = "total_weight")
    private Double totalWeight;

    /**
     * Whether grade normalization is enabled.
     * This allows normalization of grades across different sections/instructors.
     */
    @Column(name = "enable_normalization", nullable = false)
    private Boolean enableNormalization = false;

    /**
     * Custom grading scale in JSON format for letter grades (e.g., A+, A, B-).
     */
    @Column(name = "custom_grading_scale", columnDefinition = "TEXT")
    private String customGradingScale;

    /**
     * Percentage of the course completed based on graded activities.
     */
    @Column(name = "completion_percentage", nullable = false)
    private Double completionPercentage = 0.0;

    /**
     * Metadata for extensibility (custom settings).
     */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /**
     * Timestamp for when the grade book was created.
     */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /**
     * Timestamp for when the grade book was last updated.
     */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /**
     * List of grade categories in this grade book.
     */
    @OneToMany(mappedBy = "gradeBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeBookCategory> categories = new ArrayList<>();

    /**
     * List of grade change histories for auditing.
     */
    @OneToMany(mappedBy = "gradeBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeBookHistory> gradeHistories = new ArrayList<>();


    // Constructor
    public GradeBook() {}

    public GradeBook(Long gradeBookId, Course course, GradingPolicy gradingPolicy, String gradingScale, Double totalCoursePoints, Double currentPointsAchieved, Double totalWeight, Boolean enableNormalization, String customGradingScale, Double completionPercentage, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated, List<GradeBookCategory> categories, List<GradeBookHistory> gradeHistories) {
        this.gradeBookId = gradeBookId;
        this.course = course;
        this.gradingPolicy = gradingPolicy;
        this.gradingScale = gradingScale;
        this.totalCoursePoints = totalCoursePoints;
        this.currentPointsAchieved = currentPointsAchieved;
        this.totalWeight = totalWeight;
        this.enableNormalization = enableNormalization;
        this.customGradingScale = customGradingScale;
        this.completionPercentage = completionPercentage;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.categories = categories;
        this.gradeHistories = gradeHistories;
    }


// Getter and Setter
    public Long getGradeBookId() {
        return gradeBookId;
    }

    public void setGradeBookId(Long gradeBookId) {
        this.gradeBookId = gradeBookId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public GradingPolicy getGradingPolicy() {
        return gradingPolicy;
    }

    public void setGradingPolicy(GradingPolicy gradingPolicy) {
        this.gradingPolicy = gradingPolicy;
    }

    public String getGradingScale() {
        return gradingScale;
    }

    public void setGradingScale(String gradingScale) {
        this.gradingScale = gradingScale;
    }

    public Double getTotalCoursePoints() {
        return totalCoursePoints;
    }

    public void setTotalCoursePoints(Double totalCoursePoints) {
        this.totalCoursePoints = totalCoursePoints;
    }

    public Double getCurrentPointsAchieved() {
        return currentPointsAchieved;
    }

    public void setCurrentPointsAchieved(Double currentPointsAchieved) {
        this.currentPointsAchieved = currentPointsAchieved;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Boolean getEnableNormalization() {
        return enableNormalization;
    }

    public void setEnableNormalization(Boolean enableNormalization) {
        this.enableNormalization = enableNormalization;
    }

    public String getCustomGradingScale() {
        return customGradingScale;
    }

    public void setCustomGradingScale(String customGradingScale) {
        this.customGradingScale = customGradingScale;
    }

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
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

    public List<GradeBookCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<GradeBookCategory> categories) {
        this.categories = categories;
    }

    public List<GradeBookHistory> getGradeHistories() {
        return gradeHistories;
    }

    public void setGradeHistories(List<GradeBookHistory> gradeHistories) {
        this.gradeHistories = gradeHistories;
    }
}
