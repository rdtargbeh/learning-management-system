package learning_management_system.backend.controller;

import learning_management_system.backend.dto.ProctoringDetailsDto;
import learning_management_system.backend.dto.SubmissionDto;
import learning_management_system.backend.enums.SubmissionType;
import learning_management_system.backend.service.SubmissionService;
import learning_management_system.backend.utility.PerformanceInsightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Controller for managing Submission entities.
 */
@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    /**
     * Creates a new submission.
     *
     * @param submissionDto the submission data to create
     * @return the created submission
     */
    @PostMapping
    public ResponseEntity<SubmissionDto> createSubmission(@RequestBody SubmissionDto submissionDto) {
        SubmissionDto createdSubmission = submissionService.createSubmission(submissionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
    }

    /**
     * Updates an existing submission.
     *
     * @param submissionId   the ID of the submission to update
     * @param submissionDto the updated submission data
     * @return the updated submission
     */
    @PutMapping("/{submissionId}")
    public ResponseEntity<SubmissionDto> updateSubmission(
            @PathVariable Long submissionId,
            @RequestBody SubmissionDto submissionDto) {
        SubmissionDto updatedSubmission = submissionService.updateSubmission(submissionId, submissionDto);
        return ResponseEntity.ok(updatedSubmission);
    }


    /**
     * Retrieves submissions by a specific student.
     *
     * @param studentId the ID of the student
     * @param pageable  pagination information
     * @return paginated submissions for the student
     */
    @GetMapping("/students/{studentId}")
    public ResponseEntity<Page<SubmissionDto>> getSubmissionsByStudent(
            @PathVariable Long studentId,
            Pageable pageable) {
        Page<SubmissionDto> submissions = submissionService.getSubmissionsByStudent(studentId, pageable);
        return ResponseEntity.ok(submissions);
    }

    /**
     * Retrieves submissions for a specific assignment.
     *
     * @param assignmentId the ID of the assignment
     * @return the list of submissions for the assignment
     */
    @GetMapping("/assignments/{assignmentId}")
    public ResponseEntity<List<SubmissionDto>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        List<SubmissionDto> submissions = submissionService.getSubmissionsByAssignment(assignmentId);
        return ResponseEntity.ok(submissions);
    }


    /**
     * Retrieves the average marks for a specific assessment.
     *
     * @param assessmentId the ID of the assessment
     * @return the average marks
     */
    @GetMapping("/assessments/{assessmentId}/average-marks")
    public ResponseEntity<Double> getAverageMarksByAssessment(@PathVariable Long assessmentId) {
        Double averageMarks = submissionService.getAverageMarksByAssessment(assessmentId);
        return ResponseEntity.ok(averageMarks);
    }

    /**
     * Retrieves flagged submissions.
     *
     * @return the list of flagged submissions
     */
    @GetMapping("/flagged")
    public ResponseEntity<List<SubmissionDto>> getFlaggedSubmissions() {
        List<SubmissionDto> flaggedSubmissions = submissionService.getFlaggedSubmissions();
        return ResponseEntity.ok(flaggedSubmissions);
    }

    /**
     * Fetch the latest submission for a student based on the type (Assignment/Assessment).
     *
     * @param studentId The ID of the student.
     * @param entityId  The ID of the assignment or assessment.
     * @param type      The type of submission (ASSIGNMENT or ASSESSMENT).
     * @return The latest submission as a SubmissionDto.
     */
    @GetMapping("/latest")
    public ResponseEntity<SubmissionDto> getLatestSubmission(
            @RequestParam Long studentId,
            @RequestParam Long entityId,
            @RequestParam SubmissionType type) {
        SubmissionDto latestSubmission = submissionService.getLatestSubmission(studentId, entityId, type);
        return ResponseEntity.ok(latestSubmission);
    }

    /**
     * Deletes a submission by ID.
     *
     * @param submissionId the ID of the submission to delete
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{submissionId}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long submissionId) {
        submissionService.deleteSubmission(submissionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Validates whether a submission is late.
     *
     * @param submissionId the ID of the submission
     * @param type         the type of submission (e.g., ASSIGNMENT, ASSESSMENT)
     * @return true if the submission is late; false otherwise
     */
    @GetMapping("/{submissionId}/is-late")
    public ResponseEntity<Boolean> isSubmissionLate(
            @PathVariable Long submissionId,
            @RequestParam SubmissionType type) {
        boolean isLate = submissionService.isSubmissionLate(submissionId, type);
        return ResponseEntity.ok(isLate);
    }

    /**
     * Validates a submission.
     *
     * @param submissionId the ID of the submission to validate
     * @return HTTP 200 OK if valid; throws exceptions for invalid submissions
     */
    @GetMapping("/{submissionId}/validate")
    public ResponseEntity<Void> validateSubmission(@PathVariable Long submissionId) {
        submissionService.validateSubmission(submissionId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{submissionId}/finalize")
    public ResponseEntity<Void> finalizeSubmissionIfMaxAttemptsReached(@PathVariable Long submissionId) {
        submissionService.finalizeSubmissionIfMaxAttemptsReached(submissionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/submissions/{submissionId}/plagiarism")
    public ResponseEntity<Map<String, Object>> getPlagiarismDetails(@PathVariable Long submissionId) {
        Map<String, Object> plagiarismDetails = submissionService.getPlagiarismDetails(submissionId);
        return ResponseEntity.ok(plagiarismDetails);
    }

    @PostMapping("/submissions/{submissionId}/notify-plagiarism")
    public ResponseEntity<Void> notifyPlagiarism(
            @PathVariable Long submissionId,
            @RequestParam String message,
            @RequestParam Double plagiarismThreshold) {
        submissionService.notifyPlagiarism(submissionId, message, plagiarismThreshold);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/submissions/{submissionId}/plagiarism")
    public ResponseEntity<Void> updatePlagiarismDetails(
            @PathVariable Long submissionId,
            @RequestParam Double plagiarismScore,
            @RequestParam Boolean isFlagged) {
        submissionService.updatePlagiarismDetails(submissionId, plagiarismScore, isFlagged);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/submissions/{submissionId}/proctoring-details")
    public ResponseEntity<ProctoringDetailsDto> getProctoringDetails(@PathVariable Long submissionId) {
        ProctoringDetailsDto details = submissionService.getProctoringDetails(submissionId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/submissions/{submissionId}/insights")
    public ResponseEntity<PerformanceInsightsDto> getSubmissionInsights(@PathVariable Long submissionId) {
        PerformanceInsightsDto insights = submissionService.getSubmissionInsights(submissionId);
        return ResponseEntity.ok(insights);
    }


}

