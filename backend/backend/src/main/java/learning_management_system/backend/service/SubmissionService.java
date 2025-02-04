package learning_management_system.backend.service;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.ProctoringDetailsDto;
import learning_management_system.backend.dto.SubmissionDto;
import learning_management_system.backend.enums.SubmissionType;
import learning_management_system.backend.utility.PerformanceInsightsDto;
import learning_management_system.backend.utility.SubmissionGradingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


/**
 * Service interface for managing Submission entities.
 */
public interface SubmissionService {

    SubmissionDto createSubmission(SubmissionDto submissionDto);

    SubmissionDto updateSubmission(Long submissionId, SubmissionDto submissionDto);

    Page<SubmissionDto> getSubmissionsByStudent(Long studentId, Pageable pageable);

    List<SubmissionDto> getSubmissionsByAssignment(Long assignmentId);

    Double getAverageMarksByAssessment(Long assessmentId);

    List<SubmissionDto> getFlaggedSubmissions();

    SubmissionDto getLatestSubmission(Long studentId, Long entityId, SubmissionType type);

    void deleteSubmission(Long submissionId);

    boolean isSubmissionLate(Long submissionId, SubmissionType type);

    void validateSubmission(Long submissionId);

    void finalizeSubmissionIfMaxAttemptsReached(Long submissionId);

    void setGradingDetails(Long submissionId, SubmissionGradingDto gradingDto);

    SubmissionGradingDto getGradingSummary(Long submissionId);

    void analyzeSubmissionPerformance(Long submissionId, String timePerQuestion, String scoreDistribution);

    void setPlagiarismDetails(Long submissionId, Double plagiarismScore, String analysisReportUrl, Boolean isFlaggedForPlagiarism);

    void markAsReviewed(Long submissionId);

    void setAutoGradedStatus(Long submissionId, Boolean autoGraded);

    void linkProctoringSession(Long submissionId, Long proctoringSessionId);

    void addAttachments(Long submissionId, List<AttachmentDto> attachmentDtos);

    List<AttachmentDto> getAttachments(Long submissionId);

    void notifySubmissionStatus(Long submissionId, String message);

    Map<String, Object> getPlagiarismDetails(Long submissionId);

    void notifyPlagiarism(Long submissionId, String message, Double plagiarismThreshold);

    void updatePlagiarismDetails(Long submissionId, Double plagiarismScore, Boolean isFlagged);

    ProctoringDetailsDto getProctoringDetails(Long submissionId);

    PerformanceInsightsDto getSubmissionInsights(Long submissionId);
}

