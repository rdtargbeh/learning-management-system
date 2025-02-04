package learning_management_system.backend.utility;

import lombok.Data;

import java.util.Map;

@Data
public class GradingSubmissionDto {
    private Long studentId;
    private Map<String, String> answers; // Key: Question ID, Value: Answer

    public GradingSubmissionDto(Long studentId, Map<String, String> answers) {
        this.studentId = studentId;
        this.answers = answers;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }
}

