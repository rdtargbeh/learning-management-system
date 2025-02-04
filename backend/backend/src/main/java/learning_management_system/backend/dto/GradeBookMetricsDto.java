package learning_management_system.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class GradeBookMetricsDto {
    private Double totalCoursePoints;
    private Double totalWeight;
    private Double currentPointsAchieved;
    private Double completionPercentage;

    public GradeBookMetricsDto(Double totalCoursePoints, Double totalWeight, Double currentPointsAchieved, Double completionPercentage) {
        this.totalCoursePoints = totalCoursePoints;
        this.totalWeight = totalWeight;
        this.currentPointsAchieved = currentPointsAchieved;
        this.completionPercentage = completionPercentage;
    }

    // Getters and setters
    public Double getTotalCoursePoints() {
        return totalCoursePoints;
    }

    public void setTotalCoursePoints(Double totalCoursePoints) {
        this.totalCoursePoints = totalCoursePoints;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getCurrentPointsAchieved() {
        return currentPointsAchieved;
    }

    public void setCurrentPointsAchieved(Double currentPointsAchieved) {
        this.currentPointsAchieved = currentPointsAchieved;
    }

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}
