package learning_management_system.backend.utility;


public class GradingResultDto {
    private Double score;
    private String message;


    public GradingResultDto(Double score, String message) {
        this.score = score;
        this.message = message;
    }


    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
