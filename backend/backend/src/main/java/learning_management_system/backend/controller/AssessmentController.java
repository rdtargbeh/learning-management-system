package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AssessmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.mapper.QuestionMapper;
import learning_management_system.backend.service.AssessmentService;
import learning_management_system.backend.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")

public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private QuestionMapper questionMapper;

    public AssessmentController(AssessmentService assessmentService, QuestionMapper questionMapper) {
        this.assessmentService = assessmentService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public ResponseEntity<AssessmentDto> createAssessment(@RequestBody AssessmentDto assessmentDto) {
        return ResponseEntity.ok(assessmentService.createAssessment(assessmentDto));
    }

    @PutMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDto> updateAssessment(@PathVariable Long assessmentId,
                                                          @RequestBody AssessmentDto assessmentDto) {
        return ResponseEntity.ok(assessmentService.updateAssessment(assessmentId, assessmentDto));
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDto> getAssessmentById(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(assessmentService.getAssessmentById(assessmentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<AssessmentDto>> getAssessmentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(assessmentService.getAssessmentsByCourseId(courseId));
    }

    @GetMapping("/published")
    public ResponseEntity<List<AssessmentDto>> getPublishedAssessments() {
        return ResponseEntity.ok(assessmentService.getPublishedAssessments());
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long assessmentId) {
        assessmentService.deleteAssessment(assessmentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{assessmentId}/attachments")
    public ResponseEntity<AttachmentDto> addAttachmentToAssessment(
            @PathVariable Long assessmentId,
            @RequestBody AttachmentDto attachmentDto) {
        return ResponseEntity.ok(assessmentService.addAttachmentToAssessment(assessmentId, attachmentDto));
    }

    /**
     * Configure grading for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param gradingDto Grading configuration details.
     * @return Updated grading configuration.
     */
    @PostMapping("/{assessmentId}/grading")
    public ResponseEntity<GradingDto> configureGrading(
            @PathVariable Long assessmentId, @RequestBody GradingDto gradingDto) {
        GradingDto updatedGrading = assessmentService.configureGrading(assessmentId, gradingDto);
        return ResponseEntity.ok(updatedGrading);
    }

    /**
     * Retrieve grading configuration for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @return Grading configuration.
     */
    @GetMapping("/{assessmentId}/grading")
    public ResponseEntity<GradingDto> getGradingConfiguration(@PathVariable Long assessmentId) {
        GradingDto gradingDto = assessmentService.getGradingConfiguration(assessmentId);
        return ResponseEntity.ok(gradingDto);
    }

    /**
     * Grade a student's submission for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param submissionDto Submission details.
     * @return Grading result.
     */
    @PostMapping("/{assessmentId}/grade-submission")
    public ResponseEntity<GradingResultDto> gradeAssessmentSubmission(
            @PathVariable Long assessmentId, @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto gradingResult = assessmentService.gradeAssessmentSubmission(assessmentId, submissionDto);
        return ResponseEntity.ok(gradingResult);
    }

    /**
     * Fetch analytics for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @return Analytics data.
     */
    @GetMapping("/{assessmentId}/analytics")
    public ResponseEntity<AssessmentAnalyticsDto> getAssessmentAnalytics(@PathVariable Long assessmentId) {
        AssessmentAnalyticsDto analyticsDto = assessmentService.getAssessmentAnalytics(assessmentId);
        return ResponseEntity.ok(analyticsDto);
    }

    /**
     * Fetches all assessments created by a specific user.
     *
     * @param creatorId The ID of the creator (instructor).
     * @return A list of assessments created by the specified user.
     */
    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<AssessmentDto>> getAssessmentsByCreator(@PathVariable Long creatorId) {
        List<AssessmentDto> assessments = assessmentService.getAssessmentsByCreator(creatorId);
        return ResponseEntity.ok(assessments);
    }

    /**
     * Grades a student's submission for an assessment.
     *
     * @param assessmentId The ID of the assessment.
     * @param submissionDto The submission details from the student.
     * @return The grading result as a DTO.
     */
    @PostMapping("/{assessmentId}/grade")
    public ResponseEntity<GradingResultDto> gradeAssessment(
            @PathVariable Long assessmentId,
            @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto gradingResult = assessmentService.gradeAssessment(assessmentId, submissionDto);
        return ResponseEntity.ok(gradingResult);
    }

    // Validate and Fetch Assessment
    @GetMapping("/{assessmentId}/validate")
    public ResponseEntity<Void> validateAssessment(@PathVariable Long assessmentId) {
        assessmentService.validateAssessmentAvailability(assessmentId);
        return ResponseEntity.ok().build();
    }

    // Shuffle Questions
    @GetMapping("/{assessmentId}/shuffled-questions")
    public ResponseEntity<List<QuestionDto>> getShuffledQuestions(@PathVariable Long assessmentId) {
        List<QuestionDto> questions = assessmentService.getShuffledQuestions(assessmentId);
        return ResponseEntity.ok(questions);
    }

    // . Publish Assessment
    @PatchMapping("/{assessmentId}/publish")
    public ResponseEntity<Void> publishAssessment(@PathVariable Long assessmentId) {
        assessmentService.publishAssessment(assessmentId);
        return ResponseEntity.ok().build();
    }

    //  Auto-Grade Assessment
    @PostMapping("/{assessmentId}/auto-grade")
    public ResponseEntity<GradingResultDto> autoGradeAssessment(
            @PathVariable Long assessmentId, @RequestBody GradingSubmissionDto submissionDto) {
        GradingResultDto result = assessmentService.autoGradeAssessment(assessmentId, submissionDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Validates if the student has remaining attempts for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param studentId ID of the student.
     * @return ResponseEntity with status OK if attempts remain; BAD_REQUEST otherwise.
     */
    @GetMapping("/{assessmentId}/validate-attempts/{studentId}")
    public ResponseEntity<Void> validateRemainingAttempts(
            @PathVariable Long assessmentId,
            @PathVariable Long studentId) {
        boolean hasRemainingAttempts = assessmentService.validateRemainingAttempts(assessmentId, studentId);
        if (hasRemainingAttempts) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Validates the password for accessing an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param inputPassword The password input from the user.
     * @return ResponseEntity with status OK if the password is valid; BAD_REQUEST otherwise.
     */
    @PostMapping("/{assessmentId}/validate-password")
    public ResponseEntity<Void> validateAssessmentPassword(
            @PathVariable Long assessmentId,
            @RequestBody String inputPassword) {
        try {
            assessmentService.validateAssessmentPassword(assessmentId, inputPassword);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PostMapping("/start")
    public ResponseEntity<Void> startAssessment(
            @RequestParam Long assessmentId,
            @RequestParam Long studentId) {
        assessmentService.startAssessment(assessmentId, studentId);
        return ResponseEntity.ok().build();
    }


    /**
     * Validates the proctoring session for a given assessment and student.
     *
     * @param assessmentId The ID of the assessment.
     * @param studentId The ID of the student.
     * @return ResponseEntity with validation status.
     */
    @GetMapping("/validate")
    public ResponseEntity<String> validateProctoring(
            @RequestParam Long assessmentId,
            @RequestParam Long studentId) {
        try {
            assessmentService.validateProctoring(assessmentId, studentId);
            return ResponseEntity.ok("Proctoring session validated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{assessmentId}/preview")
    public ResponseEntity<AssessmentPreviewDto> previewAssessment(@PathVariable Long assessmentId) {
        AssessmentPreviewDto preview = assessmentService.previewAssessment(assessmentId);
        return ResponseEntity.ok(preview);
    }

    @PostMapping("/{assessmentId}/start-proctored")
    public ResponseEntity<Void> startProctoredAssessment(
            @PathVariable Long assessmentId,
            @RequestParam Long studentId,
            @RequestParam Long proctoringSessionId) {
        assessmentService.startProctoredAssessment(assessmentId, studentId, proctoringSessionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{assessmentId}/generate-questions")
    public ResponseEntity<List<QuestionDto>> generateAssessmentQuestions(@PathVariable Long assessmentId) {
        List<QuestionDto> questions = assessmentService.generateAssessmentQuestions(assessmentId);
        return ResponseEntity.ok(questions);
    }


    @PostMapping("/{assessmentId}/questions")
    public ResponseEntity<List<QuestionDto>> addQuestionsToAssessment(
            @PathVariable Long assessmentId,
            @RequestBody List<QuestionDto> questionDtos) {
        List<QuestionDto> addedQuestions = assessmentService.addQuestionsToAssessment(assessmentId, questionDtos);
        return ResponseEntity.ok(addedQuestions);
    }

    /**
     * Get all questions for a specific assessment.
     *
     * @param assessmentId ID of the assessment.
     * @return List of QuestionDto objects.
     */
    @GetMapping("/{assessmentId}/questions")
    public ResponseEntity<List<QuestionDto>> getQuestionsForAssessment(@PathVariable Long assessmentId) {
        List<QuestionDto> questions = assessmentService.getQuestionsForAssessment(assessmentId);
        return ResponseEntity.ok(questions);
    }

    @DeleteMapping("/{assessmentId}/questions/{questionId}")
    public ResponseEntity<Void> removeQuestionFromAssessment(@PathVariable Long assessmentId, @PathVariable Long questionId) {
        assessmentService.removeQuestionFromAssessment(assessmentId, questionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{assessmentId}/questions")
    public ResponseEntity<QuestionDto> updateQuestionInAssessment(@PathVariable Long assessmentId, @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = assessmentService.updateQuestionInAssessment(assessmentId, questionDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    @PostMapping("/{assessmentId}/questions/shuffle")
    public ResponseEntity<List<QuestionDto>> shuffleQuestionsForAssessment(@PathVariable Long assessmentId) {
        List<QuestionDto> shuffledQuestions = assessmentService.shuffleQuestionsForAssessment(assessmentId);
        return ResponseEntity.ok(shuffledQuestions);
    }

    @GetMapping("/{assessmentId}/validate-pooling-criteria")
    public ResponseEntity<Boolean> validatePoolingCriteria(@PathVariable Long assessmentId) {
        boolean isValid = assessmentService.validatePoolingCriteria(assessmentId);
        return ResponseEntity.ok(isValid);
    }


    @PostMapping("/{assessmentId}/questions/filter")
    public ResponseEntity<List<QuestionDto>> filterQuestionsForAssessment(
            @PathVariable Long assessmentId,
            @RequestBody QuestionFilterCriteria criteria) {
        List<QuestionDto> filteredQuestions = assessmentService.filterQuestionsForAssessment(assessmentId, criteria);
        return ResponseEntity.ok(filteredQuestions);
    }

    @PostMapping("/{assessmentId}/questions/order")
    public ResponseEntity<Void> setQuestionOrder(@PathVariable Long assessmentId, @RequestBody List<Long> questionIdsInOrder) {
        assessmentService.setQuestionOrder(assessmentId, questionIdsInOrder);
        return ResponseEntity.ok().build();
    }



}

