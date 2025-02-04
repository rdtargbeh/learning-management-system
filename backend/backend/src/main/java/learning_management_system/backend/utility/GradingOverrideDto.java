package learning_management_system.backend.utility;

//@Data
public class GradingOverrideDto {

    private Double previousScore;
    private Double newScore;
    private String feedback; // Add this field
    private String changeReason;
    private Long changedById;

    // Constructor
    public GradingOverrideDto(Double previousScore, Double newScore, String feedback, String changeReason, Long changedById) {
        this.previousScore = previousScore;
        this.newScore = newScore;
        this.feedback = feedback; // Initialize feedback
        this.changeReason = changeReason;
        this.changedById = changedById;
    }

    // Getters and Setters
    public Double getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(Double previousScore) {
        this.previousScore = previousScore;
    }

    public Double getNewScore() {
        return newScore;
    }

    public void setNewScore(Double newScore) {
        this.newScore = newScore;
    }

    public String getFeedback() { // Ensure this getter exists
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Long getChangedById() {
        return changedById;
    }

    public void setChangedById(Long changedById) {
        this.changedById = changedById;
    }
}

