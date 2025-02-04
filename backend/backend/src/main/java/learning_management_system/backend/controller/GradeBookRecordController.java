package learning_management_system.backend.controller;

import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.dto.GradeBookRecordDto;
import learning_management_system.backend.service.GradeBookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gradebook-records")
public class GradeBookRecordController {

    @Autowired
    private GradeBookRecordService gradeBookRecordService;


    @PostMapping
    public ResponseEntity<GradeBookRecordDto> createGradeBookRecord(@RequestBody GradeBookRecordDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeBookRecordService.createGradeBookRecord(dto));
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<GradeBookRecordDto> updateGradeBookRecord(@PathVariable Long recordId,
                                                                    @RequestBody GradeBookRecordDto dto) {
        return ResponseEntity.ok(gradeBookRecordService.updateGradeBookRecord(recordId, dto));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<GradeBookRecordDto>> getRecordsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(gradeBookRecordService.getRecordsByItem(itemId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeBookRecordDto>> getRecordsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeBookRecordService.getRecordsByStudent(studentId));
    }

    @GetMapping("/item/{itemId}/average")
    public ResponseEntity<Double> calculateAverageScoreByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(gradeBookRecordService.calculateAverageScoreByItem(itemId));
    }

    /**
     * Add feedback for a grade book record.
     *
     * @param recordId The ID of the grade book record.
     * @param feedbackDto The feedback details.
     * @return The created FeedbackDto.
     */
    @PostMapping("/{recordId}/feedback")
    public ResponseEntity<FeedbackDto> addFeedback(@PathVariable Long recordId,
                                                   @RequestBody FeedbackDto feedbackDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeBookRecordService.addFeedbackForRecord(recordId, feedbackDto));
    }

    /**
     * Retrieve feedback for a grade book record.
     *
     * @param recordId The ID of the grade book record.
     * @return List of FeedbackDto.
     */
    @GetMapping("/{recordId}/feedback")
    public ResponseEntity<List<FeedbackDto>> getFeedback(@PathVariable Long recordId) {
        return ResponseEntity.ok(gradeBookRecordService.getFeedbackForRecord(recordId));
    }

    /**
     * Count finalized records for a grade book item.
     *
     * @param itemId the ID of the grade book item.
     * @return the count of finalized records.
     */
    @GetMapping("/{itemId}/finalized-count")
    public ResponseEntity<Long> countFinalizedRecords(@PathVariable Long itemId) {
        Long count = gradeBookRecordService.countFinalizedRecords(itemId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/records/finalized/{itemId}")
    public ResponseEntity<Page<GradeBookRecordDto>> getFinalizedRecords(
            @PathVariable("itemId") Long itemId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<GradeBookRecordDto> records = gradeBookRecordService.getFinalizedRecords(itemId, page, size);
        return ResponseEntity.ok(records);
    }

//    /**
//     * Get all finalized records for a grade book item.
//     *
//     * @param itemId the ID of the grade book item.
//     * @return a list of finalized grade book records.
//     */
//    @GetMapping("/{itemId}/finalized-records")
//    public ResponseEntity<List<GradeBookRecordDto>> getFinalizedRecords(@PathVariable Long itemId) {
//        List<GradeBookRecordDto> records = gradeBookRecordService.getFinalizedRecords(itemId);
//        return ResponseEntity.ok(records);
//    }

    /**
     * Calculate total points scored by a student in a grade book.
     *
     * @param studentId   the ID of the student.
     * @param gradeBookId the ID of the grade book.
     * @return the total points scored.
     */
    @GetMapping("/student/{studentId}/gradebook/{gradeBookId}/total-points")
    public ResponseEntity<Double> calculateTotalPointsByStudent(
            @PathVariable Long studentId,
            @PathVariable Long gradeBookId
    ) {
        Double totalPoints = gradeBookRecordService.calculateTotalPointsByStudent(studentId, gradeBookId);
        return ResponseEntity.ok(totalPoints);
    }

    /**
     * Finalize all records for a specific grade book item.
     *
     * @param itemId the ID of the grade book item.
     * @return a success message.
     */
    @PostMapping("/{itemId}/finalize")
    public ResponseEntity<String> finalizeAllRecordsForItem(@PathVariable Long itemId) {
        gradeBookRecordService.finalizeAllRecordsForItem(itemId);
        return ResponseEntity.ok("All records for the item have been finalized.");
    }

    /**
     * Generate analytics for a grade book.
     *
     * @param gradeBookId the ID of the grade book.
     * @return analytics data including average score and total items.
     */
    @GetMapping("/analytics/{gradeBookId}")
    public ResponseEntity<Map<String, Object>> generateItemAnalytics(@PathVariable Long gradeBookId) {
        Map<String, Object> analytics = gradeBookRecordService.generateItemAnalytics(gradeBookId);
        return ResponseEntity.ok(analytics);
    }

    /**
     * Finalizes the grade for a specific record.
     *
     * @param recordId The ID of the grade book record.
     * @param score    The finalized score to set.
     * @return ResponseEntity indicating success.
     */
    @PostMapping("/{recordId}/finalize")
    public ResponseEntity<Void> finalizeGrade(@PathVariable Long recordId, @RequestParam Double score) {
        gradeBookRecordService.finalizeGrade(recordId, score);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recordId}/verify")
    public ResponseEntity<Void> verifyGrade(@PathVariable Long recordId, @RequestParam Long verifierId) {
        gradeBookRecordService.verifyGrade(recordId, verifierId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{recordId}/lock")
    public ResponseEntity<Void> setGradeLockStatus(@PathVariable Long recordId, @RequestParam Boolean lock) {
        gradeBookRecordService.setGradeLockStatus(recordId, lock);
        return ResponseEntity.ok().build();
    }
}
