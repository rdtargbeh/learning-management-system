package learning_management_system.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class GradeBookCategoryDto {

    private Long categoryId;
    private Long gradeBookId;
    private String name;
    private Double weight;
    private Double totalPoints;
    private Double currentPointsAchieved;
    private Double totalWeight;
    private Boolean enableLateDrop = false;
    private String metadata;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private List<GradeBookItemDto> gradeItems;

    // Constructor
    public GradeBookCategoryDto(){}
    public GradeBookCategoryDto(Long categoryId, Long gradeBookId, String name, Double weight, Double totalPoints,
                                Double currentPointsAchieved, Double totalWeight, Boolean enableLateDrop, String metadata,
                                LocalDateTime dateCreated, LocalDateTime dateUpdated, List<GradeBookItemDto> gradeItems) {
        this.categoryId = categoryId;
        this.gradeBookId = gradeBookId;
        this.name = name;
        this.weight = weight;
        this.totalPoints = totalPoints;
        this.currentPointsAchieved = currentPointsAchieved;
        this.totalWeight = totalWeight;
        this.enableLateDrop = enableLateDrop;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.gradeItems = gradeItems;
    }

    // Getters and Setters

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getGradeBookId() {
        return gradeBookId;
    }

    public void setGradeBookId(Long gradeBookId) {
        this.gradeBookId = gradeBookId;
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

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Boolean getEnableLateDrop() {
        return enableLateDrop;
    }

    public void setEnableLateDrop(Boolean enableLateDrop) {
        this.enableLateDrop = enableLateDrop;
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

    public List<GradeBookItemDto> getGradeItems() {
        return gradeItems;
    }

    public void setGradeItems(List<GradeBookItemDto> gradeItems) {
        this.gradeItems = gradeItems;
    }
}

