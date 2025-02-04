package learning_management_system.backend.dto;

import learning_management_system.backend.enums.GradeBookLinkedEntityType;

import java.time.LocalDateTime;


public class GradeBookItemDto {

    private Long itemId;
    private Long categoryId;
    private String name;
    private GradeBookLinkedEntityType linkedEntityType;
    private Long linkedEntityId;
    private LocalDateTime dueDate;
    private Double maxPoints;
    private GradingDto grading; // Nested DTO for grading
    private Long groupId;
    private Boolean gradeVerificationRequired;
    private Boolean isGroupGrading;
    private String metadata;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // Constructor
    public GradeBookItemDto() {}
    public GradeBookItemDto(Long itemId, Long categoryId, String name, GradeBookLinkedEntityType linkedEntityType, Long linkedEntityId,
                            LocalDateTime dueDate, Double maxPoints, GradingDto grading, Long groupId, Boolean gradeVerificationRequired,
                            Boolean isGroupGrading, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.name = name;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.dueDate = dueDate;
        this.maxPoints = maxPoints;
        this.grading = grading;
        this.groupId = groupId;
        this.gradeVerificationRequired = gradeVerificationRequired;
        this.isGroupGrading = isGroupGrading;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public GradingDto getGrading() {
        return grading;
    }

    public void setGrading(GradingDto grading) {
        this.grading = grading;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Boolean getGradeVerificationRequired() {
        return gradeVerificationRequired;
    }

    public void setGradeVerificationRequired(Boolean gradeVerificationRequired) {
        this.gradeVerificationRequired = gradeVerificationRequired;
    }

    public Boolean getGroupGrading() {
        return isGroupGrading;
    }

    public void setGroupGrading(Boolean groupGrading) {
        isGroupGrading = groupGrading;
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
