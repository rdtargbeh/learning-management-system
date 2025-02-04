package learning_management_system.backend.utility;

public class AssessmentAnalyticsDto {

    private String title; // Title of the assessment
    private Double averageScore; // Average score of completed submissions
    private Long completedCount; // Number of students who completed the assessment
    private Integer allowedAttempts; // Allowed attempts for the assessment
    private Integer totalSubmissions; // Total submissions for the assessment

    // Constructor
    public AssessmentAnalyticsDto(String title, Double averageScore, Long completedCount, Integer allowedAttempts, Integer totalSubmissions) {
        this.title = title;
        this.averageScore = averageScore;
        this.completedCount = completedCount;
        this.allowedAttempts = allowedAttempts;
        this.totalSubmissions = totalSubmissions;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Integer getAllowedAttempts() {
        return allowedAttempts;
    }

    public void setAllowedAttempts(Integer allowedAttempts) {
        this.allowedAttempts = allowedAttempts;
    }

    public Integer getTotalSubmissions() {
        return totalSubmissions;
    }

    public void setTotalSubmissions(Integer totalSubmissions) {
        this.totalSubmissions = totalSubmissions;
    }
}
