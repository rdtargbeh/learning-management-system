package learning_management_system.backend.dto;

import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.enums.QuestionType;

public class QuestionDto {

    private Long questionId;
    private String questionText;
    private QuestionType questionType;
    private String questionOptions;
    private String correctAnswer;
    private QuestionDifficultyLevel difficulty;
    private String tags;
    private String explanation;
    private String feedback;
    private String media;
    private Boolean isRandomized;
    private Integer timeLimit;
    private String metadata;
    private Long assessmentId;
    private Long questionBankId;
    private boolean showCorrectAnswerImmediately;
    private Long correctAttempts;
    private Long incorrectAttempts;
    private Long partialAttempts;
    private Boolean adaptiveLearning;
    private Long nextQuestionIfCorrect;
    private Long nextQuestionIfIncorrect;


    // Constructor
    public QuestionDto(){}
    public QuestionDto(Long questionId, String questionText, QuestionType questionType, String questionOptions, String correctAnswer, QuestionDifficultyLevel difficulty, String tags, String explanation, String feedback, String media, Boolean isRandomized, Integer timeLimit, String metadata, Long assessmentId, Long questionBankId, boolean showCorrectAnswerImmediately, Long correctAttempts, Long incorrectAttempts, Long partialAttempts, Boolean adaptiveLearning, Long nextQuestionIfCorrect, Long nextQuestionIfIncorrect) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.questionOptions = questionOptions;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.tags = tags;
        this.explanation = explanation;
        this.feedback = feedback;
        this.media = media;
        this.isRandomized = isRandomized;
        this.timeLimit = timeLimit;
        this.metadata = metadata;
        this.assessmentId = assessmentId;
        this.questionBankId = questionBankId;
        this.showCorrectAnswerImmediately = showCorrectAnswerImmediately;
        this.correctAttempts = correctAttempts;
        this.incorrectAttempts = incorrectAttempts;
        this.partialAttempts = partialAttempts;
        this.adaptiveLearning = adaptiveLearning;
        this.nextQuestionIfCorrect = nextQuestionIfCorrect;
        this.nextQuestionIfIncorrect = nextQuestionIfIncorrect;
    }

    // Getters and Setters

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

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

    public String getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(String questionOptions) {
        this.questionOptions = questionOptions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionDifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(QuestionDifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Boolean getRandomized() {
        return isRandomized;
    }

    public void setRandomized(Boolean randomized) {
        isRandomized = randomized;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

    public boolean isShowCorrectAnswerImmediately() {
        return showCorrectAnswerImmediately;
    }

    public void setShowCorrectAnswerImmediately(boolean showCorrectAnswerImmediately) {
        this.showCorrectAnswerImmediately = showCorrectAnswerImmediately;
    }

    public Long getCorrectAttempts() {
        return correctAttempts;
    }

    public void setCorrectAttempts(Long correctAttempts) {
        this.correctAttempts = correctAttempts;
    }

    public Long getIncorrectAttempts() {
        return incorrectAttempts;
    }

    public void setIncorrectAttempts(Long incorrectAttempts) {
        this.incorrectAttempts = incorrectAttempts;
    }

    public Long getPartialAttempts() {
        return partialAttempts;
    }

    public void setPartialAttempts(Long partialAttempts) {
        this.partialAttempts = partialAttempts;
    }

    public Boolean getAdaptiveLearning() {
        return adaptiveLearning;
    }

    public void setAdaptiveLearning(Boolean adaptiveLearning) {
        this.adaptiveLearning = adaptiveLearning;
    }

    public Long getNextQuestionIfCorrect() {
        return nextQuestionIfCorrect;
    }

    public void setNextQuestionIfCorrect(Long nextQuestionIfCorrect) {
        this.nextQuestionIfCorrect = nextQuestionIfCorrect;
    }

    public Long getNextQuestionIfIncorrect() {
        return nextQuestionIfIncorrect;
    }

    public void setNextQuestionIfIncorrect(Long nextQuestionIfIncorrect) {
        this.nextQuestionIfIncorrect = nextQuestionIfIncorrect;
    }
}

