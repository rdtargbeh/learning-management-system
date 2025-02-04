package learning_management_system.backend.dto;

import learning_management_system.backend.enums.GradingPolicy;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for GradeBook.
 */
public class GradeBookDto {

    private Long gradeBookId;
    private Long courseId;
    private String courseName;
    private GradingPolicy gradingPolicy;
    private String gradingScale;
    private Double totalCoursePoints;
    private Double currentPointsAchieved;
    private Double totalWeight;
    private Double completionPercentage;
    private String customGradingScale;
    private Boolean enableNormalization;
    private String metadata;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // Constructor

    public GradeBookDto() {}
    public GradeBookDto(Long gradeBookId, Long courseId, String courseName, GradingPolicy gradingPolicy, String gradingScale, Double totalCoursePoints, Double currentPointsAchieved, Double totalWeight, Double completionPercentage, String customGradingScale, Boolean enableNormalization, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.gradeBookId = gradeBookId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.gradingPolicy = gradingPolicy;
        this.gradingScale = gradingScale;
        this.totalCoursePoints = totalCoursePoints;
        this.currentPointsAchieved = currentPointsAchieved;
        this.totalWeight = totalWeight;
        this.completionPercentage = completionPercentage;
        this.customGradingScale = customGradingScale;
        this.enableNormalization = enableNormalization;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

// Getters and Setters
    public Long getGradeBookId() {
        return gradeBookId;
    }

    public void setGradeBookId(Long gradeBookId) {
        this.gradeBookId = gradeBookId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public String getCustomGradingScale() {
        return customGradingScale;
    }

    public void setCustomGradingScale(String customGradingScale) {
        this.customGradingScale = customGradingScale;
    }

    public Boolean getEnableNormalization() {
        return enableNormalization;
    }

    public void setEnableNormalization(Boolean enableNormalization) {
        this.enableNormalization = enableNormalization;
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
