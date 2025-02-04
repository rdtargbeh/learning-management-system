package learning_management_system.backend.utility;

import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.enums.AssessmentType;

import java.util.List;

public class AssessmentPreviewDto {

    /**
     * Title of the assessment.
     */
    private String title;

    /**
     * Detailed description of the assessment.
     */
    private String description;

    /**
     * Type of the assessment (Exam, Quiz).
     */
    private AssessmentType type;

    /**
     * List of questions for the assessment.
     */
    private List<QuestionDto> questions;

    // Constructors
    public AssessmentPreviewDto() {}

    public AssessmentPreviewDto(String title, String description, AssessmentType type, List<QuestionDto> questions) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.questions = questions;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssessmentType getType() {
        return type;
    }

    public void setType(AssessmentType type) {
        this.type = type;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }
}
