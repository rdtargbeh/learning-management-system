package learning_management_system.backend.controller;

import learning_management_system.backend.dto.GradeBookItemDto;
import learning_management_system.backend.service.GradeBookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/grade-book-items")
public class GradeBookItemController {

    @Autowired
    private GradeBookItemService gradeBookItemService;

    @PostMapping
    public ResponseEntity<?> createGradeBookItem(@RequestBody GradeBookItemDto itemDto) {
        try {
            GradeBookItemDto savedItem = gradeBookItemService.createGradeBookItem(itemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{itemId}")
    public ResponseEntity<GradeBookItemDto> updateGradeBookItem(@PathVariable Long itemId, @RequestBody GradeBookItemDto dto) {
        return ResponseEntity.ok(gradeBookItemService.updateGradeBookItem(itemId, dto));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<GradeBookItemDto> getGradeBookItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(gradeBookItemService.getGradeBookItem(itemId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<GradeBookItemDto>> getItemsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(gradeBookItemService.getItemsByCategoryId(categoryId));
    }

    @GetMapping("/due-date-range")
    public ResponseEntity<List<GradeBookItemDto>> getItemsByDueDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(gradeBookItemService.getItemsByDueDateRange(startDate, endDate));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteGradeBookItem(@PathVariable Long itemId) {
        gradeBookItemService.deleteGradeBookItem(itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Batch create grade book items.
     *
     * @param itemDtos List of GradeBookItemDto objects to create.
     * @return List of created GradeBookItemDto objects.
     */
    @PostMapping("/batch")
    public ResponseEntity<List<GradeBookItemDto>> batchCreateGradeBookItems(
            @RequestBody List<GradeBookItemDto> itemDtos) {
        List<GradeBookItemDto> createdItems = gradeBookItemService.batchCreateGradeBookItems(itemDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItems);
    }

    /**
     * Get overdue grade book items for a grade book.
     *
     * @param gradeBookId ID of the grade book.
     * @return List of overdue GradeBookItemDto objects.
     */
    @GetMapping("/{gradeBookId}/overdue")
    public ResponseEntity<List<GradeBookItemDto>> getOverdueItems(
            @PathVariable Long gradeBookId) {
        List<GradeBookItemDto> overdueItems = gradeBookItemService.getOverdueItems(gradeBookId);
        return ResponseEntity.ok(overdueItems);
    }

    @PatchMapping("/{itemId}/group-grading")
    public ResponseEntity<Void> updateGroupGrading(
            @PathVariable Long itemId,
            @RequestParam Boolean isGroupGrading,
            @RequestParam(required = false) Long groupId) {
        gradeBookItemService.updateGroupGrading(itemId, isGroupGrading, groupId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{itemId}/verification-required")
    public ResponseEntity<Void> setGradeVerificationRequired(
            @PathVariable Long itemId,
            @RequestParam Boolean verificationRequired) {
        gradeBookItemService.setGradeVerificationRequired(itemId, verificationRequired);
        return ResponseEntity.ok().build();
    }


}

