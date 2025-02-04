package learning_management_system.backend.utility;

/**
 * Data Transfer Object for grading analytics.
 * Captures statistics like average score and completion count.
 */
public class GradingAnalyticsDto {

    /** The average score for the assignment. */
    private Double averageScore;

    /** The count of completed submissions. */
    private Long completedCount;

    // Constructor
    public GradingAnalyticsDto(Double averageScore, Long completedCount) {
        this.averageScore = averageScore;
        this.completedCount = completedCount;
    }

    // Getters and Setters
    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Long getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Long completedCount) {
        this.completedCount = completedCount;
    }

    @Override
    public String toString() {
        return "GradingAnalyticsDto{" +
                "averageScore=" + averageScore +
                ", completedCount=" + completedCount +
                '}';
    }
}

