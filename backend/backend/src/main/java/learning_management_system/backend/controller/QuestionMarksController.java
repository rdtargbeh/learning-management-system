package learning_management_system.backend.controller;

import learning_management_system.backend.dto.QuestionMarksDto;
import learning_management_system.backend.service.QuestionMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question-marks")
public class QuestionMarksController {

    @Autowired
    private QuestionMarksService questionMarksService;

    @PostMapping
    public ResponseEntity<QuestionMarksDto> createQuestionMarks(@RequestBody QuestionMarksDto questionMarksDto) {
        return ResponseEntity.ok(questionMarksService.createQuestionMarks(questionMarksDto));
    }

    /**
     * Updates an existing QuestionMarks entry.
     *
     * @param questionMarksDto DTO containing updated QuestionMarks details.
     * @return Updated QuestionMarksDto.
     */
    @PutMapping("/update")
    public ResponseEntity<QuestionMarksDto> updateQuestionMarks(@RequestBody QuestionMarksDto questionMarksDto) {
        QuestionMarksDto updatedQuestionMarks = questionMarksService.updateQuestionMarks(questionMarksDto);
        return ResponseEntity.ok(updatedQuestionMarks);
    }

    @GetMapping("/{gradingId}")
    public ResponseEntity<List<QuestionMarksDto>> getQuestionMarksByGradingId(@PathVariable Long gradingId) {
        return ResponseEntity.ok(questionMarksService.getQuestionMarksByGradingId(gradingId));
    }

    @GetMapping("/{gradingId}/average-marks")
    public ResponseEntity<Double> calculateAverageMarks(@PathVariable Long gradingId) {
        return ResponseEntity.ok(questionMarksService.calculateAverageMarks(gradingId));
    }

    @GetMapping("/{gradingId}/average-partial-marks")
    public ResponseEntity<Double> calculateAveragePartialMarks(@PathVariable Long gradingId) {
        return ResponseEntity.ok(questionMarksService.calculateAveragePartialMarks(gradingId));
    }

    @GetMapping("/grading/{gradingId}/question-marks")
    public ResponseEntity<Page<QuestionMarksDto>> getQuestionMarksWithFilters(
            @PathVariable Long gradingId,
            @RequestParam(defaultValue = "0") int minMarks,
            @RequestParam(defaultValue = "100") int maxMarks,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(questionMarksService.getQuestionMarksWithPaginationAndFilters(gradingId, minMarks, maxMarks, page, size));
    }

    @PatchMapping("/grading/{gradingId}/override-student")
    public ResponseEntity<Void> overrideGradingForStudent(
            @PathVariable Long gradingId,
            @RequestParam Long studentId,
            @RequestParam int newMarks) {
        questionMarksService.overrideGradingForStudent(gradingId, studentId, newMarks);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/grading/{gradingId}/difficulty-performance")
    public ResponseEntity<Map<String, Double>> getDifficultyWisePerformance(@PathVariable Long gradingId) {
        return ResponseEntity.ok(questionMarksService.getDifficultyWisePerformance(gradingId));
    }

    /**
     * Bulk creates QuestionMarks entries.
     *
     * @param questionMarksDtos List of QuestionMarksDto to be created.
     * @return HTTP status indicating the operation result.
     */
    @PostMapping("/bulk-create")
    public ResponseEntity<Void> bulkCreateQuestionMarks(@RequestBody List<QuestionMarksDto> questionMarksDtos) {
        questionMarksService.bulkCreateQuestionMarks(questionMarksDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Calculates the average marks for a given question.
     *
     * @param questionId ID of the question.
     * @return Average marks for the question.
     */
    @GetMapping("/{questionId}/average-marks")
    public ResponseEntity<Double> calculateAverageMarksByQuestionId(@PathVariable Long questionId) {
        Double averageMarks = questionMarksService.calculateAverageMarksByQuestionId(questionId);
        return ResponseEntity.ok(averageMarks);
    }

}
