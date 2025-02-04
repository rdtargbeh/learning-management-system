package learning_management_system.backend.service;

import learning_management_system.backend.dto.GradeBookCategoryDto;
import learning_management_system.backend.dto.GradeBookCategoryStatisticsDto;

import java.util.List;
import java.util.Map;

public interface GradeBookCategoryService {

    GradeBookCategoryDto createCategory(GradeBookCategoryDto dto);

    List<GradeBookCategoryDto> getCategoriesByGradeBook(Long gradeBookId);

    List<GradeBookCategoryDto> getCategoriesByCourse(Long courseId);

    void deleteCategory(Long categoryId);

    GradeBookCategoryDto updateCategory(Long categoryId, GradeBookCategoryDto dto);

    GradeBookCategoryDto createCategoryWithValidation(GradeBookCategoryDto categoryDto);

    GradeBookCategoryDto updateCategoryWithValidation(Long categoryId, GradeBookCategoryDto categoryDto);

    Map<String, Object> getCategoryAnalytics(Long categoryId);

    List<GradeBookCategoryDto> batchCreateOrUpdateCategories(Long gradeBookId, List<GradeBookCategoryDto> categoryDtos);

    GradeBookCategoryStatisticsDto getCategoryStatistics(Long categoryId);

    List<GradeBookCategoryStatisticsDto> getStatisticsForGradeBook(Long gradeBookId);

    void applyLateDrop(Long categoryId);

    void updateLateDrop(Long categoryId, Boolean enable);


}
