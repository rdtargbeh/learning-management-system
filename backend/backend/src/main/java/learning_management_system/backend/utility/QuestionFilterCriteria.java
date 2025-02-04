package learning_management_system.backend.utility;


import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.enums.QuestionType;

public class QuestionFilterCriteria {

    private String questionText; // Partial or full text of the question
    private QuestionType questionType; // Type of the question (e.g., MULTIPLE_CHOICE, TRUE_FALSE)
    private QuestionDifficultyLevel difficulty; // Difficulty level (e.g., EASY, MEDIUM, HARD)
    private Long assessmentId; // ID of the associated assessment
    private Long questionBankId; // ID of the associated question bank
    private Boolean isRandomized; // Whether the question is randomized

    // Additional criteria as needed

    public QuestionFilterCriteria(){}

    public QuestionFilterCriteria(String questionText, QuestionType questionType, QuestionDifficultyLevel difficulty,
                                  Long assessmentId, Long questionBankId, Boolean isRandomized) {
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty;
        this.assessmentId = assessmentId;
        this.questionBankId = questionBankId;
        this.isRandomized = isRandomized;
    }

    // Getters and setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionDifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QuestionDifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Long getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
    }

    public Boolean getIsRandomized() {
        return isRandomized;
    }

    public void setIsRandomized(Boolean isRandomized) {
        this.isRandomized = isRandomized;
    }
}

