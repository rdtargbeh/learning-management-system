package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category within a grade book (e.g., Assignments, Exams).
 */
@CrossOrigin("*")
@Entity
@Table(name = "grade_book_categories")
public class GradeBookCategory {

    /** Unique identifier for the category. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false, updatable = false)
    private Long categoryId;

    /** The grade book this category belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_book_id", nullable = false)
    private GradeBook gradeBook;

    /** Name of the category (e.g., Assignments, Exams). */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /** Weight of the category (if weighted grading is used). */
    @Column(name = "weight", nullable = true)
    private Double weight;

    /** Total points allocated for the category. */
    @Column(name = "total_points", nullable = true)
    private Double totalPoints;

    /**
     * Total points the students have achieved in the course so far.
     */
    @Column(name = "current_points_achieved", nullable = false)
    private Double currentPointsAchieved = 0.0;

    /**
     * Whether to enable dropping the lowest grade(s) in this category.
     */
    @Column(name = "enable_late_drop", nullable = false)
    private Boolean enableLateDrop = false;

    /**
     * Ensures the sum of category weights does not exceed 100% for weighted grading.
     */
    @Column(name = "total_weight", nullable = true)
    private Double totalWeight;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the category was created. */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the category was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /** List of grade items in this category. */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeBookItem> gradeItems = new ArrayList<>();

    // Constructor
    public GradeBookCategory() {}

    public GradeBookCategory(Long categoryId, GradeBook gradeBook, String name, Double weight, Double totalPoints, Double currentPointsAchieved, Boolean enableLateDrop, Double totalWeight, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated, List<GradeBookItem> gradeItems) {
        this.categoryId = categoryId;
        this.gradeBook = gradeBook;
        this.name = name;
        this.weight = weight;
        this.totalPoints = totalPoints;
        this.currentPointsAchieved = currentPointsAchieved;
        this.enableLateDrop = enableLateDrop;
        this.totalWeight = totalWeight;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.gradeItems = gradeItems;
    }

    // Getter and Setter
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public GradeBook getGradeBook() {
        return gradeBook;
    }

    public void setGradeBook(GradeBook gradeBook) {
        this.gradeBook = gradeBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getCurrentPointsAchieved() {
        return currentPointsAchieved;
    }

    public void setCurrentPointsAchieved(Double currentPointsAchieved) {
        this.currentPointsAchieved = currentPointsAchieved;
    }

    public Boolean getEnableLateDrop() {
        return enableLateDrop;
    }

    public void setEnableLateDrop(Boolean enableLateDrop) {
        this.enableLateDrop = enableLateDrop;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
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

    public List<GradeBookItem> getGradeItems() {
        return gradeItems;
    }

    public void setGradeItems(List<GradeBookItem> gradeItems) {
        this.gradeItems = gradeItems;
    }
}
