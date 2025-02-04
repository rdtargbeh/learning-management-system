package learning_management_system.backend.mapper;


import learning_management_system.backend.dto.AssessmentDto;
import learning_management_system.backend.entity.Assessment;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.Question;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AssessmentMapper {

    /**
     * Converts an `Assessment` entity to an `AssessmentDto`.
     * @param assessment The `Assessment` entity.
     * @return The corresponding `AssessmentDto`.
     */
    public AssessmentDto toDto(Assessment assessment) {
        if (assessment == null) {
            return null;
        }

        AssessmentDto dto = new AssessmentDto();
        dto.setAssessmentId(assessment.getAssessmentId());
        dto.setTitle(assessment.getTitle());
        dto.setDescription(assessment.getDescription());
        dto.setType(assessment.getType());
        dto.setCourseId(assessment.getCourse() != null ? assessment.getCourse().getCourseId() : null);
        dto.setCreatedBy(assessment.getCreatedBy() != null ? assessment.getCreatedBy().getUserId() : null);
        dto.setShuffleQuestions(assessment.getShuffleQuestions());
        dto.setTimeLimit(assessment.getTimeLimit());
        dto.setTimeRemainingWarning(assessment.getTimeRemainingWarning());
        dto.setAllowedAttempts(assessment.getAllowedAttempts());
        dto.setStartDate(assessment.getStartDate());
        dto.setEndDate(assessment.getEndDate());
        dto.setPublished(assessment.getPublished());
        dto.setAutoGraded(assessment.getAutoGraded());
        dto.setShowCorrectAnswers(assessment.getShowCorrectAnswers());
        dto.setFeedbackPolicy(assessment.getFeedbackPolicy());
        dto.setRequiresPassword(assessment.getRequiresPassword());
        dto.setPassword(assessment.getPassword());
        dto.setProctored(assessment.getProctored());
        dto.setQuestionNavigation(assessment.getQuestionNavigation());
        dto.setRandomizeQuestions(assessment.getRandomizeQuestions());
        dto.setAllowRetakeOnFailure(assessment.getAllowRetakeOnFailure());
        dto.setQuestionIds(assessment.getQuestions() != null ?
                assessment.getQuestions().stream().map(Question::getQuestionId).collect(Collectors.toList()) : null);
        dto.setAttachmentIds(assessment.getAttachments() != null ?
                assessment.getAttachments().stream().map(Attachment::getAttachmentId).collect(Collectors.toList()) : null);
        dto.setGradingId(assessment.getGrading() != null ? assessment.getGrading().getGradingId() : null);
        dto.setDateCreated(assessment.getDateCreated());
        dto.setDateUpdated(assessment.getDateUpdated());
//        dto.setAccommodations(assessment.getAccommodations());
//        dto.setAccessibleFor(assessment.getAccessibleFor());

        return dto;
    }

    /**
     * Converts an `AssessmentDto` to an `Assessment` entity.
     * @param dto The `AssessmentDto`.
     * @return The corresponding `Assessment` entity.
     */
    public Assessment toEntity(AssessmentDto dto) {
        if (dto == null) {
            return null;
        }

        Assessment assessment = new Assessment();
        assessment.setAssessmentId(dto.getAssessmentId());
        assessment.setTitle(dto.getTitle());
        assessment.setDescription(dto.getDescription());
        assessment.setType(dto.getType());
        assessment.setShuffleQuestions(dto.getShuffleQuestions());
        assessment.setTimeLimit(dto.getTimeLimit());
        assessment.setTimeRemainingWarning(dto.getTimeRemainingWarning());
        assessment.setAllowedAttempts(dto.getAllowedAttempts());
        assessment.setStartDate(dto.getStartDate());
        assessment.setEndDate(dto.getEndDate());
        assessment.setPublished(dto.getPublished());
        assessment.setAutoGraded(dto.getAutoGraded());
        assessment.setShowCorrectAnswers(dto.getShowCorrectAnswers());
        assessment.setFeedbackPolicy(dto.getFeedbackPolicy());
        assessment.setRequiresPassword(dto.getRequiresPassword());
        assessment.setPassword(dto.getPassword());
        assessment.setProctored(dto.getProctored());
        assessment.setQuestionNavigation(dto.getQuestionNavigation());
        assessment.setRandomizeQuestions(dto.getRandomizeQuestions());
        assessment.setAllowRetakeOnFailure(dto.getAllowRetakeOnFailure());
//        assessment.setAccommodations(dto.getAccommodations());
//        assessment.setAccessibleFor(dto.getAccessibleFor());

        return assessment;
    }
}
