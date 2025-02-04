package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AssignmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.service.AssignmentService;
import learning_management_system.backend.utility.GradingAnalyticsDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingSubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;


    @PostMapping
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignmentService.createAssignment(assignmentDto));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDto> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AssignmentDto assignmentDto) {
        return ResponseEntity.ok(assignmentService.updateAssignment(assignmentId, assignmentDto));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentDto>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByCourseId(courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AssignmentDto>> getAssignmentsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByStudentId(studentId));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId));
    }


    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.ok("Assignment deleted successfully.");
    }

    @PostMapping("/{assignmentId}/attachments")
    public ResponseEntity<AttachmentDto> addAttachmentToAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AttachmentDto attachmentDto) {
        return ResponseEntity.ok(assignmentService.addAttachmentToAssignment(assignmentId, attachmentDto));
    }

    /**
     * Retrieve the grading configuration for a specific assignment.
     *
     * @param assignmentId ID of the assignment.
     * @return GradingDto containing grading configuration details.
     */
    @GetMapping("/{assignmentId}/grading")
    public ResponseEntity<GradingDto> getGradingConfiguration(@PathVariable Long assignmentId) {
        GradingDto grading = assignmentService.getGradingConfiguration(assignmentId);
        return ResponseEntity.ok(grading);
    }

    /**
     * Configure grading for a specific assignment.
     *
     * @param assignmentId ID of the assignment.
     * @param gradingDto Grading configuration details.
     * @return GradingDto containing updated grading configuration.
     */
    @PostMapping("/{assignmentId}/configure-grading")
    public ResponseEntity<GradingDto> configureGrading(@PathVariable Long assignmentId, @RequestBody GradingDto gradingDto) {
        GradingDto savedGrading = assignmentService.configureGrading(assignmentId, gradingDto);
        return ResponseEntity.ok(savedGrading);
    }


    /**
     * Grade a student's submission for a specific assignment.
     *
     * @param assignmentId ID of the assignment.
     * @param submissionDto Submission details from the student.
     * @return GradingResultDto containing the grading result.
     */
    @PostMapping("/{assignmentId}/grade-submission")
    public ResponseEntity<GradingResultDto> gradeAssignmentSubmission(
            @PathVariable Long assignmentId, @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto gradingResult = assignmentService.gradeAssignmentSubmission(assignmentId, submissionDto);
        return ResponseEntity.ok(gradingResult);
    }

    /**
     * Fetch analytics for a specific assignment.
     *
     * @param assignmentId ID of the assignment.
     * @return GradingAnalyticsDto containing analytics data.
     */
    @GetMapping("/{assignmentId}/analytics")
    public ResponseEntity<GradingAnalyticsDto> getAssignmentAnalytics(@PathVariable Long assignmentId) {
        GradingAnalyticsDto analyticsDto = assignmentService.getAssignmentAnalytics(assignmentId);
        return ResponseEntity.ok(analyticsDto);
    }

    /**
     * Grade an assignment by delegating to the grading service.
     *
     * @param assignmentId ID of the assignment.
     * @param submissionDto Submission details from the student.
     * @return GradingResultDto containing the grading result.
     */
    @PostMapping("/{assignmentId}/grade")
    public ResponseEntity<GradingResultDto> gradeAssignment(
            @PathVariable Long assignmentId, @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto gradingResult = assignmentService.gradeAssignment(assignmentId, submissionDto);
        return ResponseEntity.ok(gradingResult);
    }

}
