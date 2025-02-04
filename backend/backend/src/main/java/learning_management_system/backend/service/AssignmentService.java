package learning_management_system.backend.service;

import learning_management_system.backend.dto.AssignmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.utility.GradingAnalyticsDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingSubmissionDto;

import java.util.List;

public interface AssignmentService {

    List<AssignmentDto> getAllAssignments();

    List<AssignmentDto> getAssignmentsByCourseId(Long courseId);

    List<AssignmentDto> getAssignmentsByStudentId(Long studentId);

    AssignmentDto getAssignmentById(Long assignmentId);

    AssignmentDto createAssignment(AssignmentDto assignmentDto);

    AssignmentDto updateAssignment(Long assignmentId, AssignmentDto assignmentDto);

    void deleteAssignment(Long assignmentId);

    AttachmentDto addAttachmentToAssignment(Long assignmentId, AttachmentDto attachmentDto);

    GradingDto getGradingConfiguration(Long assignmentId);

    GradingDto configureGrading(Long assignmentId, GradingDto gradingDto);

    GradingResultDto gradeAssignmentSubmission(Long assignmentId, GradingSubmissionDto submissionDto);

    GradingAnalyticsDto getAssignmentAnalytics(Long assignmentId);

    GradingResultDto gradeAssignment(Long assignmentId, GradingSubmissionDto submissionDto);

}
