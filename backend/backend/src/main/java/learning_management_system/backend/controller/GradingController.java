package learning_management_system.backend.controller;

import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.service.GradingService;
import learning_management_system.backend.utility.GradingOverrideDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingStatusDto;
import learning_management_system.backend.utility.GradingSubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gradings")
public class GradingController {

    @Autowired
    private GradingService gradingService;


    @PostMapping
    public ResponseEntity<GradingDto> createGrading(@RequestBody GradingDto gradingDto) {
        GradingDto createdGrading = gradingService.createGrading(gradingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrading);
    }

    @PutMapping("/{gradingId}")
    public ResponseEntity<GradingDto> updateGrading(
            @PathVariable Long gradingId,
            @RequestBody GradingDto gradingDto) {
        GradingDto updatedGrading = gradingService.updateGrading(gradingId, gradingDto);
        return ResponseEntity.ok(updatedGrading);
    }

    @GetMapping("/{gradingId}")
    public ResponseEntity<GradingDto> getGradingById(@PathVariable Long gradingId) {
        GradingDto grading = gradingService.getGradingById(gradingId);
        return ResponseEntity.ok(grading);
    }

    @GetMapping
    public ResponseEntity<List<GradingDto>> getAllGradings() {
        List<GradingDto> gradings = gradingService.getAllGradings();
        return ResponseEntity.ok(gradings);
    }

    @GetMapping("/normalized")
    public ResponseEntity<List<GradingDto>> getNormalizedGradings() {
        List<GradingDto> normalizedGradings = gradingService.getNormalizedGradings();
        return ResponseEntity.ok(normalizedGradings);
    }

    @DeleteMapping("/{gradingId}")
    public ResponseEntity<Void> deleteGrading(@PathVariable Long gradingId) {
        gradingService.deleteGrading(gradingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{entityType}/{entityId}/status")
    public ResponseEntity<GradingStatusDto> getGradingStatus(
            @PathVariable GradingLinkedEntityType entityType,
            @PathVariable Long entityId) {
        GradingStatusDto status = gradingService.getGradingStatus(entityType, entityId);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/{gradingId}/override")
    public ResponseEntity<GradingResultDto> overrideGrade(
            @PathVariable Long gradingId,
            @RequestBody GradingOverrideDto overrideDto) {
        GradingResultDto updatedResult = gradingService.overrideGrade(gradingId, overrideDto);
        return ResponseEntity.ok(updatedResult);
    }

    @PostMapping("/assignments/{assignmentId}/grade")
    public ResponseEntity<GradingResultDto> gradeAssignment(
            @PathVariable Long assignmentId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeAssignment(assignmentId, submissionDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/assessments/{assessmentId}/grade")
    public ResponseEntity<GradingResultDto> gradeAssessment(
            @PathVariable Long assessmentId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeAssessment(assessmentId, submissionDto);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/questions/{questionId}/grade")
    public ResponseEntity<GradingResultDto> gradeQuestion(
            @PathVariable Long questionId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeQuestion(questionId, submissionDto);
        return ResponseEntity.ok(result);
    }

//    @PostMapping("/{entityType}/{entityId}/grade")
//    public ResponseEntity<GradingResultDto> gradeEntity(
//            @PathVariable GradingLinkedEntityType entityType,
//            @PathVariable Long entityId,
//            @RequestBody GradingSubmissionDto submissionDto) {
//        GradingResultDto result = gradingService.gradeEntity(entityType, entityId, submissionDto);
//        return ResponseEntity.ok(result);
//    }

    /**
     * Grade an entity based on its type (e.g., assignment, assessment, question).
     *
     * @param entityType The type of the entity being graded.
     * @param entityId   The ID of the entity being graded.
     * @param submissionDto The student's submission data.
     * @return GradingResultDto containing the result of the grading.
     */
    @PostMapping("/grade/{entityType}/{entityId}")
    public ResponseEntity<GradingResultDto> gradeByEntityType(
            @PathVariable GradingLinkedEntityType entityType,
            @PathVariable Long entityId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeByEntityType(entityType, entityId, submissionDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Grade using a specific grading configuration.
     *
     * @param gradingId   The ID of the grading configuration.
     * @param submissionDto The student's submission data.
     * @return GradingResultDto containing the result of the grading.
     */
    @PostMapping("/grade-by-grading-id/{gradingId}")
    public ResponseEntity<GradingResultDto> gradeByGradingId(
            @PathVariable Long gradingId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeByGradingId(gradingId, submissionDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Grade a specific assignment submission.
     *
     * @param assignmentId   The ID of the assignment being graded.
     * @param submissionDto The student's submission data.
     * @return GradingResultDto containing the result of the grading.
     */
    @PostMapping("/grade-assignment/{assignmentId}")
    public ResponseEntity<GradingResultDto> gradeAssignmentSubmission(
            @PathVariable Long assignmentId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = gradingService.gradeAssignmentSubmission(assignmentId, submissionDto);
        return ResponseEntity.ok(result);
    }

}

