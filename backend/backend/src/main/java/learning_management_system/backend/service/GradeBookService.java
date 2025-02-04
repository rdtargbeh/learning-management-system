package learning_management_system.backend.service;


import learning_management_system.backend.dto.GradeBookDto;
import learning_management_system.backend.dto.GradeBookMetricsDto;
import learning_management_system.backend.enums.GradingPolicy;

import java.util.List;

public interface GradeBookService {

    GradeBookDto createGradeBook(GradeBookDto gradeBookDto);

    GradeBookDto updateGradeBook(Long gradeBookId, GradeBookDto gradeBookDto);

    GradeBookDto getGradeBookById(Long gradeBookId);

    List<GradeBookDto> getGradeBooksByGradingPolicy(GradingPolicy gradingPolicy);

    List<GradeBookDto> getGradeBooksByCompletionThreshold(Double threshold);

    void deleteGradeBook(Long gradeBookId);

    void updateProgressOnRecordChange(Long gradeBookId);

    GradeBookMetricsDto getGradeBookMetrics(Long gradeBookId);

    String getGradeLetter(Double score, Long gradeBookId);

    void normalizeGrades(Long gradeBookId);
}

