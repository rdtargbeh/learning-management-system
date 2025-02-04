package learning_management_system.backend.service;

import learning_management_system.backend.dto.QuestionMarksDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface QuestionMarksService {

    QuestionMarksDto createQuestionMarks(QuestionMarksDto questionMarksDto);

    QuestionMarksDto updateQuestionMarks(QuestionMarksDto questionMarksDto);

    List<QuestionMarksDto> getQuestionMarksByGradingId(Long gradingId);

    Double calculateAverageMarks(Long gradingId);

    Double calculateAveragePartialMarks(Long gradingId);

    void bulkCreateQuestionMarks(List<QuestionMarksDto> questionMarksDtos);

    Page<QuestionMarksDto> getQuestionMarksWithPaginationAndFilters(Long gradingId, int minMarks, int maxMarks, int page, int size);

    void overrideGradingForStudent(Long gradingId, Long studentId, int newMarks);

    Map<String, Double> getDifficultyWisePerformance(Long gradingId);

    Double calculateAverageMarksByQuestionId(Long questionId);

}
