package learning_management_system.backend.service;


import learning_management_system.backend.dto.AssessmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.utility.*;

import java.util.List;

public interface AssessmentService {

    AssessmentDto createAssessment(AssessmentDto assessmentDto);

    AssessmentDto updateAssessment(Long assessmentId, AssessmentDto assessmentDto);

    AssessmentDto getAssessmentById(Long assessmentId);

    List<AssessmentDto> getAssessmentsByCourseId(Long courseId);

    List<AssessmentDto> getPublishedAssessments();

    List<AssessmentDto> getAssessmentsByCreator(Long creatorId);

    void deleteAssessment(Long assessmentId);

    AttachmentDto addAttachmentToAssessment(Long assessmentId, AttachmentDto attachmentDto);

    GradingResultDto gradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto);

    GradingDto configureGrading(Long assessmentId, GradingDto gradingDto);

    GradingDto getGradingConfiguration(Long assessmentId);

    GradingResultDto gradeAssessmentSubmission(Long assessmentId, GradingSubmissionDto submissionDto);

    void validateAssessmentAvailability(Long assessmentId);

    List<QuestionDto> getShuffledQuestions(Long assessmentId);

    boolean validateRemainingAttempts(Long assessmentId, Long studentId);

    GradingResultDto autoGradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto);

    void publishAssessment(Long assessmentId);

    void validateAssessmentPassword(Long assessmentId, String inputPassword);

    void validateProctoring(Long assessmentId, Long studentId);

    void startAssessment(Long assessmentId, Long studentId);

    AssessmentPreviewDto previewAssessment(Long assessmentId);

    void startProctoredAssessment(Long assessmentId, Long studentId, Long proctoringSessionId);

    AssessmentAnalyticsDto getAssessmentAnalytics(Long assessmentId);

    //  question related methods
    List<QuestionDto> generateAssessmentQuestions(Long assessmentId);

    List<QuestionDto> addQuestionsToAssessment(Long assessmentId, List<QuestionDto> questionDtos);

    List<QuestionDto> getQuestionsForAssessment(Long assessmentId);

    void removeQuestionFromAssessment(Long assessmentId, Long questionId);

    QuestionDto updateQuestionInAssessment(Long assessmentId, QuestionDto questionDto);

    List<QuestionDto> shuffleQuestionsForAssessment(Long assessmentId);

    boolean validatePoolingCriteria(Long assessmentId);

    List<QuestionDto> filterQuestionsForAssessment(Long assessmentId, QuestionFilterCriteria criteria);

    void setQuestionOrder(Long assessmentId, List<Long> questionIdsInOrder);

}
