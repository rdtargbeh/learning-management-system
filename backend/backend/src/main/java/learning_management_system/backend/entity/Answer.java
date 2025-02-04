package learning_management_system.backend.entity;

import jakarta.persistence.*;

//@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "selected_option", nullable = true)
    private String selectedOption; // For MCQs

    @Column(name = "text_response", columnDefinition = "TEXT")
    private String textResponse; // For open-ended questions

    @Column(name = "marks_earned", nullable = true)
    private Double marksEarned;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback; // Question-specific feedback


    // Constructor

    public Answer(Long answerId, Submission submission, Question question, String selectedOption, String textResponse, Double marksEarned, String feedback) {
        this.answerId = answerId;
        this.submission = submission;
        this.question = question;
        this.selectedOption = selectedOption;
        this.textResponse = textResponse;
        this.marksEarned = marksEarned;
        this.feedback = feedback;
    }

    // Getter and Setter
    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getTextResponse() {
        return textResponse;
    }

    public void setTextResponse(String textResponse) {
        this.textResponse = textResponse;
    }

    public Double getMarksEarned() {
        return marksEarned;
    }

    public void setMarksEarned(Double marksEarned) {
        this.marksEarned = marksEarned;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

