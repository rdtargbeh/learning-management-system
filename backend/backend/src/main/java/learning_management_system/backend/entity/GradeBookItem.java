package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.GradeBookLinkedEntityType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

/**
 * Represents an individual graded item within a grade book category.
 */
@CrossOrigin("*")
@Entity
@Table(name = "grade_book_items")
public class GradeBookItem {

    /** Unique identifier for the grade book item. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false, updatable = false)
    private Long itemId;

    /** The category this grade book item belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private GradeBookCategory category;

    /** Name of the item (e.g., "Assignment 1", "Midterm Exam"). */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /** Type of the linked entity (e.g., Assignment, Assessment). */
    @Enumerated(EnumType.STRING)
    @Column(name = "linked_entity_type", nullable = false, length = 50)
    private GradeBookLinkedEntityType linkedEntityType;

    /** ID of the linked entity. */
    @Column(name = "linked_entity_id", nullable = false)
    private Long linkedEntityId;

    /** Due date for the grade book item. */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /** Maximum points allocated for the item. */
    @Column(name = "max_points", nullable = false)
    private Double maxPoints;

    /** Grading configuration for the item. */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    /**
     * Whether group grading is enabled for this item.
     * This allows assigning a grade to a group instead of individual students.
     */
    @Column(name = "is_group_grading", nullable = false)
    private Boolean isGroupGrading = false;

    /**
     * Links the item to a specific group for grading.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private StudentGroup studentGroup;

//    @Column(name = "group_id", nullable = true)
//    private Long groupId;

    /**
     * Marks whether this grade book item requires verification before grades are finalized.
     */
    @Column(name = "grade_verification_required", nullable = false)
    private Boolean gradeVerificationRequired = false;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the grade book item was created. */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the grade book item was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    public GradeBookItem() {}

    public GradeBookItem(Long itemId, GradeBookCategory category, String name, GradeBookLinkedEntityType linkedEntityType,
                         Long linkedEntityId, LocalDateTime dueDate, Double maxPoints, Grading grading, Boolean isGroupGrading,
                         Long groupId, Boolean gradeVerificationRequired, String metadata, LocalDateTime dateCreated,
                         LocalDateTime dateUpdated, StudentGroup studentGroup) {
        this.itemId = itemId;
        this.category = category;
        this.name = name;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.dueDate = dueDate;
        this.maxPoints = maxPoints;
        this.grading = grading;
        this.isGroupGrading = isGroupGrading;
//        this.groupId = groupId;
        this.gradeVerificationRequired = gradeVerificationRequired;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.studentGroup = studentGroup;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public GradeBookCategory getCategory() {
        return category;
    }

    public void setCategory(GradeBookCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GradeBookLinkedEntityType getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(GradeBookLinkedEntityType linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public Long getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(Long linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Double getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Double maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Boolean getGroupGrading() {
        return isGroupGrading;
    }

    public void setGroupGrading(Boolean groupGrading) {
        isGroupGrading = groupGrading;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Boolean getGradeVerificationRequired() {
        return gradeVerificationRequired;
    }

    public void setGradeVerificationRequired(Boolean gradeVerificationRequired) {
        this.gradeVerificationRequired = gradeVerificationRequired;
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

