package learning_management_system.backend.controller;

import learning_management_system.backend.dto.GradeBookHistoryDto;
import learning_management_system.backend.service.GradeBookHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grade-book-history")
public class GradeBookHistoryController {

    @Autowired
    private GradeBookHistoryService gradeBookHistoryService;


    /**
     * Retrieves grade change history for a specific record.
     *
     * @param recordId the grade book record ID
     * @return a list of grade book history entries
     */
    @GetMapping("/record/{recordId}")
    public ResponseEntity<List<GradeBookHistoryDto>> getHistoryByRecordId(@PathVariable Long recordId) {
        List<GradeBookHistoryDto> histories = gradeBookHistoryService.getHistoryByRecordId(recordId);
        return ResponseEntity.ok(histories);
    }

    /**
     * Retrieves grade change history for a specific user.
     *
     * @param userId the user ID
     * @return a list of grade book history entries
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GradeBookHistoryDto>> getHistoryByUserId(@PathVariable Long userId) {
        List<GradeBookHistoryDto> histories = gradeBookHistoryService.getHistoryByUserId(userId);
        return ResponseEntity.ok(histories);
    }

    @GetMapping("/gradebook-history/reports")
    public ResponseEntity<Map<String, Object>> getCustomReports(@RequestParam Long userId) {
        return ResponseEntity.ok(gradeBookHistoryService.getCustomReports(userId));
    }


    @GetMapping("/gradebook-history/paginated")
    public ResponseEntity<Page<GradeBookHistoryDto>> getPaginatedHistory(
            @RequestParam Long recordId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(gradeBookHistoryService.getPaginatedGradeBookHistory(recordId, page, size));
    }

    /**
     * Retrieve filtered grade history.
     *
     * @param userId    ID of the user who made changes (optional).
     * @param recordId  ID of the grade record (optional).
     * @param startDate Start date for filtering history (optional).
     * @param endDate   End date for filtering history (optional).
     * @return List of filtered grade history entries.
     */
    @GetMapping("/filter")
    public ResponseEntity<List<GradeBookHistoryDto>> getFilteredHistory(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long recordId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<GradeBookHistoryDto> history = gradeBookHistoryService.getFilteredHistory(userId, recordId, startDate, endDate);
        return ResponseEntity.ok(history);
    }

    /**
     * Record a grade change and notify the student.
     *
     * @param recordId      ID of the grade record.
     * @param previousScore The previous score before the change.
     * @param newScore      The new score after the change.
     * @param reason        Reason for the grade change.
     * @param changedById   ID of the user who made the change.
     * @return ResponseEntity with no content if successful.
     */
    @PostMapping("/record-grade-change")
    @Transactional
    public ResponseEntity<String> recordGradeChange(
            @RequestParam Long recordId,
            @RequestParam Double previousScore,
            @RequestParam Double newScore,
            @RequestParam String reason,
            @RequestParam Long changedById) {

        try {
            gradeBookHistoryService.recordGradeChange(recordId, previousScore, newScore, reason, changedById);
            return ResponseEntity.ok("Grade change recorded successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error recording grade change: " + e.getMessage());
        }
    }

    //    @PostMapping("/record-grade-change")
//    public ResponseEntity<Void> recordGradeChange(
//            @RequestParam Long recordId,
//            @RequestParam Double previousScore,
//            @RequestParam Double newScore,
//            @RequestParam String reason,
//            @RequestParam Long changedById) {
//
//        gradeBookHistoryService.recordGradeChange(recordId, previousScore, newScore, reason, changedById);
//        return ResponseEntity.ok().build();
//    }

}
