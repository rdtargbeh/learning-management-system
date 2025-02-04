package learning_management_system.backend.utility;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class QuestionMarksId implements Serializable {
    private Long gradingId;
    private Long questionId;


    // Constructor
    public QuestionMarksId(){}
    public QuestionMarksId(Long gradingId, Long questionId) {
        this.gradingId = gradingId;
        this.questionId = questionId;
    }


    // Getters, setters, equals, and hashCode
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getGradingId() {
        return gradingId;
    }

    public void setGradingId(Long gradingId) {
        this.gradingId = gradingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionMarksId that = (QuestionMarksId) o;
        return Objects.equals(gradingId, that.gradingId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradingId, questionId);
    }
}
