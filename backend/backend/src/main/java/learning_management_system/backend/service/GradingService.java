package learning_management_system.backend.service;

import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.entity.Grading;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.utility.GradingOverrideDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingStatusDto;
import learning_management_system.backend.utility.GradingSubmissionDto;

import java.util.List;

public interface GradingService {

    GradingDto createGrading(GradingDto gradingDto);

    GradingDto updateGrading(Long gradingId, GradingDto gradingDto);

    GradingDto getGradingById(Long gradingId);

    List<GradingDto> getAllGradings();

    List<GradingDto> getNormalizedGradings();

    void deleteGrading(Long gradingId);

    GradingResultDto gradeByEntityType(GradingLinkedEntityType entityType,
                                       Long entityId, GradingSubmissionDto submissionDto);

    GradingResultDto gradeByGradingId(Long gradingId, GradingSubmissionDto submissionDto);

    GradingResultDto gradeAssignment(Long assignmentId, GradingSubmissionDto submissionDto);

    GradingResultDto gradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto);

    GradingResultDto gradeQuestion(Long questionId, GradingSubmissionDto submissionDto);

    GradingStatusDto getGradingStatus(GradingLinkedEntityType entityType, Long entityId);

    GradingResultDto overrideGrade(Long gradingId, GradingOverrideDto overrideDto);

    Double calculateRubricScore(String gradingRubric, GradingSubmissionDto submissionDto);

    GradingResultDto saveGradingResult(Grading grading, Long studentId, Double score);

    GradingResultDto gradeAssignmentSubmission(Long assignmentId, GradingSubmissionDto submissionDto);
}
