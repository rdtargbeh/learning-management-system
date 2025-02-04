package learning_management_system.backend.controller;

import learning_management_system.backend.dto.GradeBookDto;
import learning_management_system.backend.dto.GradeBookMetricsDto;
import learning_management_system.backend.enums.GradingPolicy;
import learning_management_system.backend.service.GradeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gradebooks")
public class GradeBookController {

    @Autowired
    private GradeBookService gradeBookService;


    @PostMapping
    public ResponseEntity<GradeBookDto> createGradeBook(@RequestBody GradeBookDto gradeBookDto) {
        GradeBookDto createdGradeBook = gradeBookService.createGradeBook(gradeBookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGradeBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeBookDto> updateGradeBook(@PathVariable Long id, @RequestBody GradeBookDto gradeBookDto) {
        GradeBookDto updatedGradeBook = gradeBookService.updateGradeBook(id, gradeBookDto);
        return ResponseEntity.ok(updatedGradeBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeBookDto> getGradeBookById(@PathVariable Long id) {
        GradeBookDto gradeBook = gradeBookService.getGradeBookById(id);
        return ResponseEntity.ok(gradeBook);
    }

    @GetMapping("/grading-policy")
    public ResponseEntity<List<GradeBookDto>> getGradeBooksByGradingPolicy(@RequestParam GradingPolicy gradingPolicy) {
        List<GradeBookDto> gradeBooks = gradeBookService.getGradeBooksByGradingPolicy(gradingPolicy);
        return ResponseEntity.ok(gradeBooks);
    }

    @GetMapping("/completion-threshold")
    public ResponseEntity<List<GradeBookDto>> getGradeBooksByCompletionThreshold(@RequestParam Double threshold) {
        List<GradeBookDto> gradeBooks = gradeBookService.getGradeBooksByCompletionThreshold(threshold);
        return ResponseEntity.ok(gradeBooks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGradeBook(@PathVariable Long id) {
        gradeBookService.deleteGradeBook(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Recalculate and update the progress for a specific gradebook.
     *
     * @param gradeBookId The ID of the gradebook.
     * @return ResponseEntity indicating success.
     */
    @PostMapping("/{gradeBookId}/update-progress")
    public ResponseEntity<Void> updateProgress(@PathVariable Long gradeBookId) {
        gradeBookService.updateProgressOnRecordChange(gradeBookId);
        return ResponseEntity.ok().build();
    }

    /**
     * Fetch metrics for a gradebook (total points, weight, current progress, etc.).
     *
     * @param gradeBookId ID of the gradebook
     * @return GradeBookMetricsDto containing the calculated metrics
     */
    @GetMapping("/{gradeBookId}/metrics")
    public ResponseEntity<GradeBookMetricsDto> getGradeBookMetrics(@PathVariable Long gradeBookId) {
        GradeBookMetricsDto metrics = gradeBookService.getGradeBookMetrics(gradeBookId);
        return ResponseEntity.ok(metrics);
    }

    @PostMapping("/{gradeBookId}/normalize-grades")
    public ResponseEntity<Void> normalizeGrades(@PathVariable Long gradeBookId) {
        gradeBookService.normalizeGrades(gradeBookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gradeBookId}/grade-letter")
    public ResponseEntity<String> getGradeLetter(@RequestParam Double score, @PathVariable Long gradeBookId) {
        String gradeLetter = gradeBookService.getGradeLetter(score, gradeBookId);
        return ResponseEntity.ok(gradeLetter);
    }
}

