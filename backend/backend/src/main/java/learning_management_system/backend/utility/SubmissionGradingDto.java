package learning_management_system.backend.utility;


public class SubmissionGradingDto {
    private String feedback;
    private String rubricDetails;
    private String gradeComments;
    private String gradeLevel;


    // Constructors
    public SubmissionGradingDto(){}
    public SubmissionGradingDto(String feedback, String rubricDetails, String gradeComments, String gradeLevel) {
        this.feedback = feedback;
        this.rubricDetails = rubricDetails;
        this.gradeComments = gradeComments;
        this.gradeLevel = gradeLevel;
    }

    // Getter and Setter

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRubricDetails() {
        return rubricDetails;
    }

    public void setRubricDetails(String rubricDetails) {
        this.rubricDetails = rubricDetails;
    }

    public String getGradeComments() {
        return gradeComments;
    }

    public void setGradeComments(String gradeComments) {
        this.gradeComments = gradeComments;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}

