package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.QuestionMarksDto;
import learning_management_system.backend.entity.QuestionMarks;
import learning_management_system.backend.utility.QuestionMarksId;
import org.springframework.stereotype.Component;

@Component
public class QuestionMarksMapper {

    public QuestionMarksDto toDto(QuestionMarks questionMarks) {
        if (questionMarks == null) {
            return null;
        }

        QuestionMarksDto dto = new QuestionMarksDto();

        // Map embedded key
        if (questionMarks.getId() != null) {
            dto.setGradingId(questionMarks.getId().getGradingId());
            dto.setQuestionId(questionMarks.getId().getQuestionId());
        }

        dto.setMarks(questionMarks.getMarks());
        dto.setPartialMarks(questionMarks.getPartialMarks());
        dto.setRubric(questionMarks.getRubric());
        dto.setAdaptiveGrading(questionMarks.getAdaptiveGrading());
        dto.setAverageScore(questionMarks.getAverageScore());
        dto.setTotalAttempts(questionMarks.getTotalAttempts());
        dto.setTotalMarksEarned(questionMarks.getTotalMarksEarned());
        dto.setDateCreated(questionMarks.getDateCreated());
        dto.setDateUpdated(questionMarks.getDateUpdated());

        // Map feedback
        if (questionMarks.getFeedback() != null) {
            dto.setFeedback(questionMarks.getFeedback().getFeedbackText()); // Adjust based on Feedback entity
        }

        return dto;
    }

    public QuestionMarks toEntity(QuestionMarksDto dto) {
        if (dto == null) {
            return null;
        }

        QuestionMarks questionMarks = new QuestionMarks();

        QuestionMarksId id = new QuestionMarksId();
        id.setGradingId(dto.getGradingId());
        id.setQuestionId(dto.getQuestionId());
        questionMarks.setId(id);

        questionMarks.setMarks(dto.getMarks());
        questionMarks.setPartialMarks(dto.getPartialMarks());
        questionMarks.setRubric(dto.getRubric());
        questionMarks.setAdaptiveGrading(dto.getAdaptiveGrading());
        questionMarks.setAverageScore(dto.getAverageScore());
        questionMarks.setTotalAttempts(dto.getTotalAttempts());
        questionMarks.setTotalMarksEarned(dto.getTotalMarksEarned());
        questionMarks.setDateCreated(dto.getDateCreated());
        questionMarks.setDateUpdated(dto.getDateUpdated());

        // Feedback mapping omitted; handle it in the service layer if needed
        return questionMarks;
    }

}
