package learning_management_system.backend.controller;

import learning_management_system.backend.dto.GradeBookCategoryDto;
import learning_management_system.backend.dto.GradeBookCategoryStatisticsDto;
import learning_management_system.backend.service.GradeBookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gradebook-categories")
public class GradeBookCategoryController {

    @Autowired
    private GradeBookCategoryService gradeBookCategoryService;

    @PostMapping
    public ResponseEntity<GradeBookCategoryDto> createCategory(@RequestBody GradeBookCategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeBookCategoryService.createCategory(dto));
    }

    @GetMapping("/gradebook/{gradeBookId}")
    public ResponseEntity<List<GradeBookCategoryDto>> getCategoriesByGradeBook(@PathVariable Long gradeBookId) {
        return ResponseEntity.ok(gradeBookCategoryService.getCategoriesByGradeBook(gradeBookId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<GradeBookCategoryDto>> getCategoriesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(gradeBookCategoryService.getCategoriesByCourse(courseId));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<GradeBookCategoryDto> updateCategory(
            @PathVariable Long categoryId,
            @RequestBody GradeBookCategoryDto dto) {
        return ResponseEntity.ok(gradeBookCategoryService.updateCategory(categoryId, dto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        gradeBookCategoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-category")
    public ResponseEntity<GradeBookCategoryDto> createCategoryWithValidation(@RequestBody GradeBookCategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeBookCategoryService.createCategoryWithValidation(categoryDto));
    }

    @PutMapping("/validate-category/{categoryId}")
    public ResponseEntity<GradeBookCategoryDto> updateCategoryWithValidation(@PathVariable Long categoryId, @RequestBody GradeBookCategoryDto categoryDto) {
        return ResponseEntity.ok(gradeBookCategoryService.updateCategoryWithValidation(categoryId, categoryDto));
    }

    @GetMapping("/analytics/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryAnalytics(@PathVariable Long categoryId) {
        return ResponseEntity.ok(gradeBookCategoryService.getCategoryAnalytics(categoryId));
    }


    @PostMapping("/batch-create")
    public ResponseEntity<List<GradeBookCategoryDto>> batchCreateOrUpdateCategories(
            @RequestParam Long gradeBookId,
            @RequestBody List<GradeBookCategoryDto> categoryDtos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradeBookCategoryService.batchCreateOrUpdateCategories(gradeBookId, categoryDtos));
    }

    @GetMapping("/{categoryId}/statistics")
    public ResponseEntity<GradeBookCategoryStatisticsDto> getCategoryStatistics(@PathVariable Long categoryId) {
        GradeBookCategoryStatisticsDto statistics = gradeBookCategoryService.getCategoryStatistics(categoryId);
        return ResponseEntity.ok(statistics);
    }


    @GetMapping("/gradebook/{gradeBookId}/statistics")
    public ResponseEntity<List<GradeBookCategoryStatisticsDto>> getStatisticsForGradeBook(@PathVariable Long gradeBookId) {
        List<GradeBookCategoryStatisticsDto> statistics = gradeBookCategoryService.getStatisticsForGradeBook(gradeBookId);
        return ResponseEntity.ok(statistics);
    }

    @PostMapping("/{categoryId}/apply-late-drop")
    public ResponseEntity<Void> applyLateDrop(@PathVariable Long categoryId) {
        gradeBookCategoryService.applyLateDrop(categoryId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{categoryId}/enable-late-drop")
    public ResponseEntity<Void> enableLateDrop(@PathVariable Long categoryId, @RequestParam Boolean enable) {
        gradeBookCategoryService.updateLateDrop(categoryId, enable);
        return ResponseEntity.ok().build();
    }

}
