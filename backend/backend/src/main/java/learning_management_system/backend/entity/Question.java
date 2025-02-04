package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.enums.QuestionType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false, length = 50)
    private QuestionType questionType;

    @Column(name = "question_options", columnDefinition = "TEXT")
    private String questionOptions;

    @Column(name = "correct_answer", columnDefinition = "TEXT")
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 20)
    private QuestionDifficultyLevel difficulty;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "media", columnDefinition = "TEXT")
    private String media;

    @Column(name = "is_randomized", nullable = false)
    private Boolean isRandomized = false;

    @Column(name = "time_limit")
    private Integer timeLimit;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /**
     * The grading configuration for this assignment.
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "grading_id")
    private Grading grading;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_bank_id")
    private QuestionBank questionBank;

    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    @Column(nullable = false)
    private boolean showCorrectAnswerImmediately; // Whether to show correct answer immediately after submission

    @Column(name = "correct_attempts", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long correctAttempts = 0L; // Number of correct attempts

    @Column(name = "incorrect_attempts", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long incorrectAttempts = 0L; // Number of incorrect attempts

    @Column(name = "partial_attempts", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long partialAttempts = 0L; // Number of partial attempts

    @Column(name = "adaptive_learning", nullable = false)
    private Boolean adaptiveLearning = false; // Adapts based on student performance

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_question_correct_id")
    private Question nextQuestionIfCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_question_incorrect_id")
    private Question nextQuestionIfIncorrect;


    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = new Date();
    }

    public Question() {
    }

    public Question(Long questionId, String questionText, QuestionType questionType, String questionOptions, String correctAnswer,
                    QuestionDifficultyLevel difficulty, String tags, String explanation, String feedback, String media,
                    Boolean isRandomized, Integer timeLimit, String metadata, Grading grading, Assessment assessment,
                    QuestionBank questionBank, List<Attachment> attachments, Date dateCreated, Date dateUpdated,
                    boolean showCorrectAnswerImmediately, Long correctAttempts, Long incorrectAttempts, Long partialAttempts,
                    Boolean adaptiveLearning, Question nextQuestionIfCorrect, Question nextQuestionIfIncorrect) {
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
        this.grading = grading;
        this.assessment = assessment;
        this.questionBank = questionBank;
        this.attachments = attachments;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.showCorrectAnswerImmediately = showCorrectAnswerImmediately;
        this.correctAttempts = correctAttempts;
        this.incorrectAttempts = incorrectAttempts;
        this.partialAttempts = partialAttempts;
        this.adaptiveLearning = adaptiveLearning;
        this.nextQuestionIfCorrect = nextQuestionIfCorrect;
        this.nextQuestionIfIncorrect = nextQuestionIfIncorrect;
    }

    // Getters and setters omitted for brevity.
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

    public Grading getGrading() {
        return grading;
    }

    public void setGrading(Grading grading) {
        this.grading = grading;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public QuestionBank getQuestionBank() {
        return questionBank;
    }

    public void setQuestionBank(QuestionBank questionBank) {
        this.questionBank = questionBank;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
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

    public Question getNextQuestionIfCorrect() {
        return nextQuestionIfCorrect;
    }

    public void setNextQuestionIfCorrect(Question nextQuestionIfCorrect) {
        this.nextQuestionIfCorrect = nextQuestionIfCorrect;
    }

    public Question getNextQuestionIfIncorrect() {
        return nextQuestionIfIncorrect;
    }

    public void setNextQuestionIfIncorrect(Question nextQuestionIfIncorrect) {
        this.nextQuestionIfIncorrect = nextQuestionIfIncorrect;
    }
}
