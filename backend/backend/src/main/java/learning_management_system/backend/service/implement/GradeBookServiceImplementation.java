package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning_management_system.backend.dto.GradeBookDto;
import learning_management_system.backend.dto.GradeBookMetricsDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.GradeBook;
import learning_management_system.backend.entity.GradeBookRecord;
import learning_management_system.backend.enums.GradingPolicy;
import learning_management_system.backend.mapper.GradeBookMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.GradeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GradeBookServiceImplementation implements GradeBookService {

    @Autowired
    private GradeBookRepository gradeBookRepository;
    @Autowired
    private GradeBookMapper gradeBookMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GradeBookItemRepository gradeBookItemRepository;
    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;
    @Autowired
    private GradeBookCategoryRepository gradeBookCategoryRepository;


    @Override
    @Transactional
    public GradeBookDto createGradeBook(GradeBookDto gradeBookDto) {
        Course course = courseRepository.findById(gradeBookDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found."));
        GradeBook gradeBook = gradeBookMapper.toEntity(gradeBookDto);
        gradeBook.setCourse(course);
        GradeBook savedGradeBook = gradeBookRepository.save(gradeBook);
        return gradeBookMapper.toDto(savedGradeBook);
    }

    @Override
    @Transactional
    public GradeBookDto updateGradeBook(Long gradeBookId, GradeBookDto gradeBookDto) {
        GradeBook gradeBook = gradeBookRepository.findById(gradeBookId)
                .orElseThrow(() -> new RuntimeException("GradeBook not found."));
        gradeBook.setGradingPolicy(gradeBookDto.getGradingPolicy());
        gradeBook.setGradingScale(gradeBookDto.getGradingScale());
        gradeBook.setTotalCoursePoints(gradeBookDto.getTotalCoursePoints());
        gradeBook.setCurrentPointsAchieved(gradeBookDto.getCurrentPointsAchieved());
        gradeBook.setTotalWeight(gradeBookDto.getTotalWeight());
        gradeBook.setCompletionPercentage(gradeBookDto.getCompletionPercentage());
        gradeBook.setMetadata(gradeBookDto.getMetadata());
        gradeBook.setDateUpdated(LocalDateTime.now());
        GradeBook updatedGradeBook = gradeBookRepository.save(gradeBook);
        return gradeBookMapper.toDto(updatedGradeBook);
    }

    /**
     * Updates the completion percentage of a gradebook based on finalized grade records.
     *
     * @param gradeBookId The ID of the gradebook to update.
     */
    @Override
    @Transactional
    public void updateProgressOnRecordChange(Long gradeBookId) {
        Double totalPoints = gradeBookRepository.calculateTotalPoints(gradeBookId);
        Double achievedPoints = gradeBookRepository.calculateAchievedPoints(gradeBookId);

        if (totalPoints == null || totalPoints == 0) {
            throw new RuntimeException("Total points for the gradebook cannot be zero or null.");
        }

        GradeBook gradeBook = gradeBookRepository.findById(gradeBookId)
                .orElseThrow(() -> new RuntimeException("GradeBook not found."));
        gradeBook.setCompletionPercentage((achievedPoints != null ? achievedPoints : 0) / totalPoints * 100.0);

        gradeBookRepository.save(gradeBook);
    }


    @Override
    @Transactional(readOnly = true)
    public GradeBookMetricsDto getGradeBookMetrics(Long gradeBookId) {
        // Fetch calculated metrics
        Double totalCoursePoints = gradeBookItemRepository.calculateTotalPointsByGradeBook(gradeBookId);
        Double totalWeight = gradeBookCategoryRepository.calculateTotalWeightByGradeBook(gradeBookId);
        Double currentPointsAchieved = gradeBookRecordRepository.calculateAchievedPointsByGradeBook(gradeBookId);

        if (totalCoursePoints == null || totalCoursePoints == 0) {
            throw new RuntimeException("Total course points cannot be zero or null.");
        }

        // Calculate completion percentage
        Double completionPercentage = (currentPointsAchieved / totalCoursePoints) * 100.0;

        // Build DTO
        return new GradeBookMetricsDto(
                totalCoursePoints,
                totalWeight,
                currentPointsAchieved,
                completionPercentage
        );
    }


    @Override
    public GradeBookDto getGradeBookById(Long gradeBookId) {
        GradeBook gradeBook = gradeBookRepository.findById(gradeBookId)
                .orElseThrow(() -> new RuntimeException("GradeBook not found."));
        return gradeBookMapper.toDto(gradeBook);
    }

    @Override
    public List<GradeBookDto> getGradeBooksByGradingPolicy(GradingPolicy gradingPolicy) {
        List<GradeBook> gradeBooks = gradeBookRepository.findByGradingPolicy(gradingPolicy);
        return gradeBooks.stream().map(gradeBookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<GradeBookDto> getGradeBooksByCompletionThreshold(Double threshold) {
        List<GradeBook> gradeBooks = gradeBookRepository.findGradeBooksByCompletionPercentage(threshold);
        return gradeBooks.stream().map(gradeBookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteGradeBook(Long gradeBookId) {
        GradeBook gradeBook = gradeBookRepository.findById(gradeBookId)
                .orElseThrow(() -> new RuntimeException("GradeBook not found."));
        gradeBookRepository.delete(gradeBook);
    }

    // Enable Grade Normalization
    @Override
    @Transactional(readOnly = true)
    public void normalizeGrades(Long gradeBookId) {
        // Get all GradeBookItems for the given GradeBook
        List<Long> itemIds = gradeBookRecordRepository.findItemIdsByGradeBookId(gradeBookId);

        // Fetch all records for those items
        List<GradeBookRecord> records = gradeBookRecordRepository.findByGradeBookItem_ItemIdIn(itemIds);

        if (records.isEmpty()) {
            throw new RuntimeException("No records found for the given GradeBook.");
        }

        // Calculate mean and standard deviation
        Double mean = records.stream().mapToDouble(GradeBookRecord::getScore).average().orElse(0.0);
        Double stdDev = Math.sqrt(records.stream()
                .mapToDouble(record -> Math.pow(record.getScore() - mean, 2))
                .average().orElse(0.0));

        // Normalize scores
        for (GradeBookRecord record : records) {
            Double normalizedScore = (record.getScore() - mean) / (stdDev > 0 ? stdDev : 1) * 10 + 50;
            record.setScore(normalizedScore);
        }

        // Save the normalized records
        gradeBookRecordRepository.saveAll(records);
    }


    // Support for Custom Grading Scales: Parse and apply the JSON-based grading scale.
    @Override
    public String getGradeLetter(Double score, Long gradeBookId) {
        GradeBook gradeBook = gradeBookRepository.findById(gradeBookId)
                .orElseThrow(() -> new RuntimeException("GradeBook not found."));
        String gradingScaleJson = gradeBook.getCustomGradingScale();
        if (gradingScaleJson == null) {
            throw new RuntimeException("Custom grading scale not set.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Double> gradingScale = objectMapper.readValue(gradingScaleJson, new TypeReference<>() {});
            return gradingScale.entrySet().stream()
                    .filter(entry -> score >= entry.getValue())
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse("F");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid grading scale JSON.", e);
        }
    }


}
