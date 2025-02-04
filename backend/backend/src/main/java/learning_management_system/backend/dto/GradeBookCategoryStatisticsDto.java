package learning_management_system.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradeBookCategoryStatisticsDto {

    private String categoryName;  // e.g., "Exam"
    private Double weight;        // Weight of the category (if applicable)
    private Double totalPoints;   // Total points for the category
    private Double currentPointsAchieved;  // Current points achieved in the category

    // Getters and setters or Lombok annotations
    public GradeBookCategoryStatisticsDto(String categoryName, Double weight, Double totalPoints, Double currentPointsAchieved) {
        this.categoryName = categoryName;
        this.weight = weight;
        this.totalPoints = totalPoints;
        this.currentPointsAchieved = currentPointsAchieved;
    }
}

