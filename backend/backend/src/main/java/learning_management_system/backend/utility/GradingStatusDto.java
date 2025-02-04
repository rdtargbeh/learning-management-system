package learning_management_system.backend.utility;

import learning_management_system.backend.enums.GradingPolicy;
import learning_management_system.backend.enums.LateSubmissionPolicy;
import lombok.Data;

@Data
//@AllArgsConstructor
public class GradingStatusDto {
//    private Long entityId;
    private GradingPolicy gradingPolicy;
//    private String status;
    private Integer totalMarks;
    private Double attemptPenalty;
    private LateSubmissionPolicy lateSubmissionPolicy;
    private Double lateSubmissionPenaltyPercentage;

    // Constructor


    public GradingStatusDto(GradingPolicy gradingPolicy, Integer totalMarks, Double attemptPenalty, LateSubmissionPolicy lateSubmissionPolicy,
                            Double lateSubmissionPenaltyPercentage) {
        this.gradingPolicy = gradingPolicy;
        this.totalMarks = totalMarks;
        this.attemptPenalty = attemptPenalty;
        this.lateSubmissionPolicy = lateSubmissionPolicy;
        this.lateSubmissionPenaltyPercentage = lateSubmissionPenaltyPercentage;
    }

    // Getters and setters
    public GradingPolicy getGradingPolicy() {
        return gradingPolicy;
    }

    public void setGradingPolicy(GradingPolicy gradingPolicy) {
        this.gradingPolicy = gradingPolicy;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getAttemptPenalty() {
        return attemptPenalty;
    }

    public void setAttemptPenalty(Double attemptPenalty) {
        this.attemptPenalty = attemptPenalty;
    }

    public LateSubmissionPolicy getLateSubmissionPolicy() {
        return lateSubmissionPolicy;
    }

    public void setLateSubmissionPolicy(LateSubmissionPolicy lateSubmissionPolicy) {
        this.lateSubmissionPolicy = lateSubmissionPolicy;
    }

    public Double getLateSubmissionPenaltyPercentage() {
        return lateSubmissionPenaltyPercentage;
    }

    public void setLateSubmissionPenaltyPercentage(Double lateSubmissionPenaltyPercentage) {
        this.lateSubmissionPenaltyPercentage = lateSubmissionPenaltyPercentage;
    }
}
