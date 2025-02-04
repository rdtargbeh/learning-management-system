package learning_management_system.backend.service;

import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.entity.Question;
import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.utility.QuestionFilterCriteria;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    QuestionDto createQuestion(QuestionDto questionDto);

    QuestionDto updateQuestion(Long questionId, QuestionDto questionDto);

    void deleteQuestion(Long questionId);

    QuestionDto getQuestionById(Long questionId);

    List<QuestionDto> getQuestionsByAssessmentId(Long assessmentId);

    List<QuestionDto> getQuestionsByQuestionBankId(Long questionBankId);

    List<QuestionDto> getQuestionsByTag(String tag);

    List<QuestionDto> getQuestionsByDifficulty(QuestionDifficultyLevel difficulty);

    List<QuestionDto> getQuestionsByCriteria(QuestionFilterCriteria filterCriteria);

    QuestionDto getNextQuestion(Long assessmentId, Long currentQuestionId, boolean isCorrect);

    Map<String, String> getFeedbackAndExplanation(Long questionId, boolean isCorrect);

    Map<String, Long> getQuestionAttemptStats(Long questionId);

    QuestionDto createQuestionInBank(Long questionBankId, QuestionDto questionDto);

    Question getNextQuestion(Long currentQuestionId, boolean isAnswerCorrect);

    List<Question> findQuestionsByCriteria(QuestionFilterCriteria criteria);

    long countQuestionsByCriteria(QuestionFilterCriteria criteria);

    List<Question> findRandomQuestions(String randomizationRules, int count);

}
