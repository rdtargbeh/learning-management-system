package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.entity.Grading;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.repository.AssessmentRepository;
import learning_management_system.backend.repository.AssignmentRepository;
import learning_management_system.backend.repository.GradeBookItemRepository;
import learning_management_system.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
//@RequiredArgsConstructor
public class GradingMapper {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Converts a Grading entity to a GradingDto.
     *
     * @param grading the Grading entity
     * @return the corresponding GradingDto
     */
    public GradingDto toDto(Grading grading) {
        if (grading == null) {
            return null;
        }

        GradingDto dto = new GradingDto();
        dto.setGradingId(grading.getGradingId());
        dto.setGradingPolicy(grading.getGradingPolicy());
        dto.setTotalMarks(grading.getTotalMarks());
        dto.setGradingRubric(grading.getGradingRubric());
        dto.setAttemptPenalty(grading.getAttemptPenalty());
        dto.setLateSubmissionPolicy(grading.getLateSubmissionPolicy());
        dto.setLateSubmissionPenaltyPercentage(grading.getLateSubmissionPenaltyPercentage());
        dto.setGradingTiers(grading.getGradingTiers());
        dto.setEnableNormalization(grading.getEnableNormalization());
        dto.setAutoPublish(grading.getAutoPublish());
        dto.setScalingFactor(grading.getScalingFactor());
        dto.setDateCreated(grading.getDateCreated());
        dto.setDateUpdated(grading.getDateUpdated());
        dto.setLinkedEntityType(grading.getLinkedEntityType());
        dto.setLinkedEntityId(grading.getLinkedEntityId());

        // Convert questionMarks map
        dto.setQuestionMarks(grading.getQuestionMarks());

        // Set linked entity IDs
        if (grading.getLinkedEntityType() == GradingLinkedEntityType.ASSESSMENT) {
            dto.setAssessmentId(grading.getLinkedEntityId());
        } else if (grading.getLinkedEntityType() == GradingLinkedEntityType.ASSIGNMENT) {
            dto.setAssignmentId(grading.getLinkedEntityId());
        } else if (grading.getLinkedEntityType() == GradingLinkedEntityType.GRADE_BOOK_ITEM) {
            dto.setGradeBookItemId(grading.getLinkedEntityId());
        } else if (grading.getLinkedEntityType() == GradingLinkedEntityType.QUESTION) {
            dto.setQuestionId(grading.getLinkedEntityId());
        }

        return dto;
    }

    /**
     * Converts a GradingDto to a Grading entity.
     *
     * @param dto the GradingDto
     * @return the corresponding Grading entity
     */
    public Grading toEntity(GradingDto dto) {
        if (dto == null) {
            return null;
        }

        Grading grading = new Grading();
        grading.setGradingId(dto.getGradingId());
        grading.setGradingPolicy(dto.getGradingPolicy());
        grading.setTotalMarks(dto.getTotalMarks());
        grading.setGradingRubric(dto.getGradingRubric());
        grading.setAttemptPenalty(dto.getAttemptPenalty());
        grading.setLateSubmissionPolicy(dto.getLateSubmissionPolicy());
        grading.setLateSubmissionPenaltyPercentage(dto.getLateSubmissionPenaltyPercentage());
        grading.setGradingTiers(dto.getGradingTiers());
        grading.setEnableNormalization(dto.getEnableNormalization());
        grading.setAutoPublish(dto.getAutoPublish());
        grading.setScalingFactor(dto.getScalingFactor());
        grading.setDateCreated(dto.getDateCreated());
        grading.setDateUpdated(dto.getDateUpdated());
        grading.setQuestionMarks(dto.getQuestionMarks());

        // Determine the linked entity based on the provided IDs
        if (dto.getAssessmentId() != null) {
            grading.setLinkedEntityType(GradingLinkedEntityType.ASSESSMENT);
            grading.setLinkedEntityId(dto.getAssessmentId());
        } else if (dto.getAssignmentId() != null) {
            grading.setLinkedEntityType(GradingLinkedEntityType.ASSIGNMENT);
            grading.setLinkedEntityId(dto.getAssignmentId());
        } else if (dto.getGradeBookItemId() != null) {
            grading.setLinkedEntityType(GradingLinkedEntityType.GRADE_BOOK_ITEM);
            grading.setLinkedEntityId(dto.getGradeBookItemId());
        } else if (dto.getQuestionId() != null) {
            grading.setLinkedEntityType(GradingLinkedEntityType.QUESTION);
            grading.setLinkedEntityId(dto.getQuestionId());
        }

        return grading;
    }

    public void updateEntity(GradingDto dto, Grading entity) {
        if (dto.getGradingPolicy() != null) {
            entity.setGradingPolicy(dto.getGradingPolicy());
        }
        if (dto.getTotalMarks() != null) {
            entity.setTotalMarks(dto.getTotalMarks());
        }
        if (dto.getGradingRubric() != null) {
            entity.setGradingRubric(dto.getGradingRubric());
        }
        // Map other fields as needed
    }

}

