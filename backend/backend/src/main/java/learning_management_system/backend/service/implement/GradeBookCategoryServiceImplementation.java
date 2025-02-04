package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.GradeBookCategoryDto;
import learning_management_system.backend.dto.GradeBookCategoryStatisticsDto;
import learning_management_system.backend.entity.GradeBook;
import learning_management_system.backend.entity.GradeBookCategory;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.mapper.GradeBookCategoryMapper;
import learning_management_system.backend.repository.GradeBookCategoryRepository;
import learning_management_system.backend.repository.GradeBookItemRepository;
import learning_management_system.backend.repository.GradeBookRecordRepository;
import learning_management_system.backend.repository.GradeBookRepository;
import learning_management_system.backend.service.GradeBookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeBookCategoryServiceImplementation implements GradeBookCategoryService {

    @Autowired
    private GradeBookCategoryRepository repository;
    @Autowired
    private GradeBookRepository gradeBookRepository;
    @Autowired
    private GradeBookCategoryMapper mapper;
    @Autowired
    private   GradeBookCategoryRepository gradeBookCategoryRepository;
    @Autowired
    private GradeBookCategoryMapper gradeBookCategoryMapper;
    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private  GradeBookRecordRepository gradeBookRecordRepository;


    @Override
    public GradeBookCategoryDto createCategory(GradeBookCategoryDto dto) {
        GradeBook gradeBook = gradeBookRepository.findById(dto.getGradeBookId())
                .orElseThrow(() -> new RuntimeException("GradeBook not found with ID: " + dto.getGradeBookId()));

        GradeBookCategory category = mapper.toEntity(dto);
        category.setGradeBook(gradeBook);

        return mapper.toDto(repository.save(category));
    }

    @Override
    public List<GradeBookCategoryDto> getCategoriesByGradeBook(Long gradeBookId) {
        // Retrieve categories and map them to DTOs
        return repository.findByGradeBook_GradeBookId(gradeBookId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GradeBookCategoryDto> getCategoriesByCourse(Long courseId) {
        // Retrieve categories and map them to DTOs
        return repository.findByCourseId(courseId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteCategory(Long categoryId) {
        if (!repository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
        repository.deleteById(categoryId);
    }

    @Override
    public GradeBookCategoryDto updateCategory(Long categoryId, GradeBookCategoryDto dto) {
        GradeBookCategory category = repository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        category.setName(dto.getName());
        category.setWeight(dto.getWeight());
        category.setTotalPoints(dto.getTotalPoints());
        category.setMetadata(dto.getMetadata());
        category.setDateUpdated(LocalDateTime.now());

        return mapper.toDto(repository.save(category));
    }

    private void validateCategoryWeights(Long gradeBookId, Double newWeight) {
        Double totalWeight = gradeBookCategoryRepository.sumWeightsByGradeBookId(gradeBookId);
        if (totalWeight + newWeight > 100.0) {
            throw new IllegalArgumentException("Total weight for grade book categories cannot exceed 100%.");
        }
    }

    @Override
    @Transactional
    public GradeBookCategoryDto createCategoryWithValidation(GradeBookCategoryDto categoryDto) {
        validateCategoryWeights(categoryDto.getGradeBookId(), categoryDto.getWeight());
        GradeBookCategory category = gradeBookCategoryMapper.toEntity(categoryDto);
        return gradeBookCategoryMapper.toDto(gradeBookCategoryRepository.save(category));
    }

    @Override
    @Transactional
    public GradeBookCategoryDto updateCategoryWithValidation(Long categoryId, GradeBookCategoryDto categoryDto) {
        // Retrieve the existing category
        GradeBookCategory existingCategory = gradeBookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        // Validate category weights
        validateCategoryWeights(existingCategory.getGradeBook().getGradeBookId(),
                categoryDto.getWeight() - existingCategory.getWeight());

        // Update the entity manually using values from the DTO
        existingCategory.setName(categoryDto.getName());
        existingCategory.setWeight(categoryDto.getWeight());
        existingCategory.setTotalPoints(categoryDto.getTotalPoints());
        existingCategory.setEnableLateDrop(categoryDto.getEnableLateDrop());
        existingCategory.setMetadata(categoryDto.getMetadata());

        // Save the updated category and map it back to a DTO
        return mapper.toDto(gradeBookCategoryRepository.save(existingCategory));
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getCategoryAnalytics(Long categoryId) {
        GradeBookCategory category = gradeBookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        Double averageScore = gradeBookRecordRepository.calculateAverageScoreByCategoryId(categoryId);
        Long totalItems = gradeBookItemRepository.countByCategoryId(categoryId);

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("categoryName", category.getName());
        analytics.put("averageScore", averageScore);
        analytics.put("totalItems", totalItems);

        return analytics;
    }

    @Transactional
    public List<GradeBookCategoryDto> batchCreateOrUpdateCategories(Long gradeBookId, List<GradeBookCategoryDto> categoryDtos) {
        List<GradeBookCategory> categories = categoryDtos.stream()
                .map(gradeBookCategoryMapper::toEntity)
                .peek(category -> category.setGradeBook(gradeBookRepository.findById(gradeBookId)
                        .orElseThrow(() -> new RuntimeException("Grade book not found."))))
                .collect(Collectors.toList());
        List<GradeBookCategory> savedCategories = gradeBookCategoryRepository.saveAll(categories);
        return savedCategories.stream().map(gradeBookCategoryMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GradeBookCategoryStatisticsDto getCategoryStatistics(Long categoryId) {
        GradeBookCategory category = gradeBookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        // Fetch total points and current points
        Double totalPoints = gradeBookCategoryRepository.calculateTotalPointsByCategory(categoryId);
        Double currentPointsAchieved = gradeBookRecordRepository.calculateAchievedPointsByCategory(categoryId);

        // Return statistics DTO
        return new GradeBookCategoryStatisticsDto(
                category.getName(),
                category.getWeight(),
                totalPoints != null ? totalPoints : 0.0,
                currentPointsAchieved != null ? currentPointsAchieved : 0.0
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeBookCategoryStatisticsDto> getStatisticsForGradeBook(Long gradeBookId) {
        // Fetch all categories for the gradebook
        List<GradeBookCategory> categories = gradeBookCategoryRepository.findByGradeBook_GradeBookId(gradeBookId);

        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found for the specified GradeBook ID: " + gradeBookId);
        }

        // Collect statistics for each category
        return categories.stream().map(category -> {
            // Fetch total points and current points achieved
            Double totalPoints = gradeBookCategoryRepository.calculateTotalPointsByCategory(category.getCategoryId());
            Double currentPointsAchieved = gradeBookRecordRepository.calculateAchievedPointsByCategory(category.getCategoryId());

            // Map to DTO
            return new GradeBookCategoryStatisticsDto(
                    category.getName(),
                    category.getWeight(),
                    totalPoints != null ? totalPoints : 0.0,
                    currentPointsAchieved != null ? currentPointsAchieved : 0.0
            );
        }).collect(Collectors.toList());
    }


    // Implement logic for dropping the lowest grades in a category.
    @Override
    @Transactional
    public void applyLateDrop(Long categoryId) {
        GradeBookCategory category = gradeBookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        if (!category.getEnableLateDrop()) {
            throw new RuntimeException("Late drop is not enabled for this category.");
        }

        // Fetch all records in this category
        List<GradeBookRecord> records = gradeBookRecordRepository.findByCategoryId(categoryId);

        // Find the record(s) with the lowest score
        Optional<GradeBookRecord> lowestRecord = records.stream()
                .filter(record -> record.getScore() != null)
                .min(Comparator.comparingDouble(GradeBookRecord::getScore));

        // Drop the lowest grade(s)
        lowestRecord.ifPresent(record -> gradeBookRecordRepository.delete(record));
    }


    /**
     * Updates the late drop setting for a grade book category.
     *
     * @param categoryId the ID of the grade book category
     * @param enable true to enable late drop, false to disable
     */
    @Override
    public void updateLateDrop(Long categoryId, Boolean enable) {
        // Fetch the category by ID
        GradeBookCategory category = gradeBookCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("GradeBookCategory not found with ID: " + categoryId));

        // Update the late drop setting
        category.setEnableLateDrop(enable);

        // Save the updated category
        gradeBookCategoryRepository.save(category);
    }

}
