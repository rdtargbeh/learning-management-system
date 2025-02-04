package learning_management_system.backend.utility;

public class PerformanceInsightsDto {

    private String timePerQuestion; // JSON-based analytics of time spent per question
    private String scoreDistribution; // JSON-based score trends

    // Constructor
    public PerformanceInsightsDto(String timePerQuestion, String scoreDistribution) {
        this.timePerQuestion = timePerQuestion;
        this.scoreDistribution = scoreDistribution;
    }

    // Getters and setters
    public String getTimePerQuestion() {
        return timePerQuestion;
    }

    public void setTimePerQuestion(String timePerQuestion) {
        this.timePerQuestion = timePerQuestion;
    }

    public String getScoreDistribution() {
        return scoreDistribution;
    }

    public void setScoreDistribution(String scoreDistribution) {
        this.scoreDistribution = scoreDistribution;
    }
}
