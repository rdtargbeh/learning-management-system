package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.entity.Question;
import learning_management_system.backend.entity.QuestionBank;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.enums.QuestionType;
import learning_management_system.backend.mapper.QuestionMapper;
import learning_management_system.backend.repository.QuestionBankRepository;
import learning_management_system.backend.repository.QuestionRepository;
import learning_management_system.backend.service.ActivityLogsService;
import learning_management_system.backend.service.QuestionService;
import learning_management_system.backend.service.UserService;
import learning_management_system.backend.utility.QuestionFilterCriteria;
import learning_management_system.backend.utility.QuestionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImplementation implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ActivityLogsService activityLogsService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionBankRepository questionBankRepository;


    /**
     * Create a new question.
     */
    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {
        validateQuestion(questionDto);

        Question question = questionMapper.toEntity(questionDto);
        question = questionRepository.save(question);

        // Fetch the current user as User entity
        User currentUser = userService.getUserEntityById(userService.getCurrentUserId());

        // Log creation
        activityLogsService.logAction(
                currentUser,
                ActionCategory.QUESTION_MANAGEMENT,
                ActionType.CREATE,
                "Question",
                question.getQuestionId(),
                true,
                null,
                Map.of("questionText", question.getQuestionText())
        );

        return questionMapper.toDto(question);
    }


    /**
     * Update an existing question.
     */
    @Override
    @Transactional
    public QuestionDto updateQuestion(Long questionId, QuestionDto questionDto) {
        // Fetch the existing Question entity
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        // Validate the Question DTO
        validateQuestion(questionDto);

        // Update fields manually using the QuestionMapper class
        Question updatedQuestion = questionMapper.toEntity(questionDto);

        // Preserve the original ID and linked entities that are not updated via DTO
        updatedQuestion.setQuestionId(existingQuestion.getQuestionId());
        updatedQuestion.setAssessment(existingQuestion.getAssessment());
        updatedQuestion.setQuestionBank(existingQuestion.getQuestionBank());

        // Save the updated Question entity
        Question savedQuestion = questionRepository.save(updatedQuestion);

        // Fetch the current user as a User entity
        User currentUser = userService.getUserEntityById(userService.getCurrentUserId());

        // Log the update action
        activityLogsService.logAction(
                currentUser,
                ActionCategory.QUESTION_MANAGEMENT,
                ActionType.UPDATE,
                "Question",
                questionId,
                true,
                null,
                Map.of("questionText", questionDto.getQuestionText())
        );

        // Convert the updated entity back to DTO and return
        return questionMapper.toDto(savedQuestion);
    }


    /**
     * Validate question details.
     */
    private void validateQuestion(QuestionDto questionDto) {
        if (questionDto.getQuestionText() == null || questionDto.getQuestionText().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty.");
        }

        if (questionDto.getQuestionType() == null) {
            throw new IllegalArgumentException("Question type is required.");
        }

        if (questionDto.getQuestionType() == QuestionType.MCQ && (questionDto.getQuestionOptions() == null || questionDto.getQuestionOptions().isEmpty())) {
            throw new IllegalArgumentException("Question options are required for multiple-choice questions.");
        }

        // Additional validation as needed...
    }

    /**
     * Fetch a question by ID.
     */
    @Override
    @Transactional(readOnly = true)
    public QuestionDto getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
        return questionMapper.toDto(question);
    }

    /**
     * Delete a question by ID.
     */
    @Override
    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new RuntimeException("Question not found with ID: " + questionId);
        }
        questionRepository.deleteById(questionId);
    }

    /**
     * Get all questions by assessment ID.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByAssessmentId(Long assessmentId) {
        return questionRepository.findByAssessment_AssessmentId(assessmentId).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all questions by question bank ID.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByQuestionBankId(Long questionBankId) {
        return questionRepository.findByQuestionBank_QuestionBankId(questionBankId).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Get questions by difficulty level.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByDifficulty(QuestionDifficultyLevel difficulty) {
        return questionRepository.findByDifficulty(difficulty).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Question getNextQuestion(Long currentQuestionId, boolean isAnswerCorrect) {
        // Fetch the current question
        Question question = questionRepository.findById(currentQuestionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + currentQuestionId));

        // Determine the next question based on correctness of the answer
        Question nextQuestion = isAnswerCorrect ? question.getNextQuestionIfCorrect() : question.getNextQuestionIfIncorrect();

        // Return the next question if it's not null, otherwise return null
        return nextQuestion != null ? questionRepository.findById(nextQuestion.getQuestionId()).orElse(null) : null;
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByCriteria(QuestionFilterCriteria filterCriteria) {
        // Build the specification
        Specification<Question> specification = QuestionSpecification.matchesCriteria(filterCriteria);

        // Fetch questions based on the specification
        List<Question> questions = questionRepository.findAll(specification);

        // Map each Question entity to QuestionDto
        List<QuestionDto> questionDtos = questions.stream()
                .map(questionMapper::toDto) // Use the `toDto` method for each question
                .collect(Collectors.toList());

        return questionDtos;
    }


    @Override
    @Transactional(readOnly = true)
    public QuestionDto getNextQuestion(Long assessmentId, Long currentQuestionId, boolean isCorrect) {
        // Fetch the current question
        Question currentQuestion = questionRepository.findById(currentQuestionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + currentQuestionId));
        // Determine the next question based on correctness and extract its ID
        Question nextQuestionEntity = isCorrect ? currentQuestion.getNextQuestionIfCorrect() : currentQuestion.getNextQuestionIfIncorrect();

        if (nextQuestionEntity == null) {
            throw new RuntimeException("No next question defined for the current flow.");
        }
        // Fetch the next question entity
        Question nextQuestion = questionRepository.findById(nextQuestionEntity.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Next question not found with ID: " + nextQuestionEntity.getQuestionId()));
        // Map to DTO and return
        return questionMapper.toDto(nextQuestion);
    }



    private void validateQuestionTimeLimit(Question question, LocalDateTime startTime) {
        if (question.getTimeLimit() == null || question.getTimeLimit() <= 0) {
            return; // No time limit set
        }

        LocalDateTime currentTime = LocalDateTime.now();
        long elapsedTime = Duration.between(startTime, currentTime).toSeconds();

        if (elapsedTime > question.getTimeLimit() * 60) {
            throw new RuntimeException("Time limit exceeded for this question.");
        }
    }



    @Transactional(readOnly = true)
    public Map<String, String> getFeedbackAndExplanation(Long questionId, boolean isCorrect) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        Map<String, String> response = new HashMap<>();
        response.put("feedback", question.getFeedback());
        response.put("explanation", isCorrect ? question.getExplanation() : "Better luck next time!");

        return response;
    }


    @Transactional(readOnly = true)
    public Map<String, Long> getQuestionAttemptStats(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        Map<String, Long> stats = new HashMap<>();
        stats.put("correctAttempts", question.getCorrectAttempts());
        stats.put("incorrectAttempts", question.getIncorrectAttempts());
        stats.put("partialAttempts", question.getPartialAttempts());

        return stats;
    }


    // QUESTION BANK ENTITY RELATED METHODS
    @Transactional
    public QuestionDto createQuestionInBank(Long questionBankId, QuestionDto questionDto) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("QuestionBank not found"));

        Question question = questionMapper.toEntity(questionDto);
        question.setQuestionBank(questionBank);

        return questionMapper.toDto(questionRepository.save(question));
    }

    /**
     * Get questions by tag.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsByTag(String tag) {
        return questionRepository.findByTag(tag).stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a list of questions based on given criteria.
     *
     * @param criteria The filtering criteria.
     * @return A list of matching questions.
     */
    public List<Question> findQuestionsByCriteria(QuestionFilterCriteria criteria) {
        return questionRepository.findAll(QuestionSpecification.matchesCriteria(criteria));
    }

    /**
     * Counts the number of questions matching the given criteria.
     *
     * @param criteria The filtering criteria.
     * @return The number of matching questions.
     */
    public long countQuestionsByCriteria(QuestionFilterCriteria criteria) {
        return questionRepository.count(QuestionSpecification.matchesCriteria(criteria));
    }

    /**
     * Retrieves a random set of questions based on filtering rules.
     *
     * @param randomizationRules JSON-based filtering rules.
     * @param count The number of questions to retrieve.
     * @return A list of randomly selected questions.
     */
    public List<Question> findRandomQuestions(String randomizationRules, int count) {
        return questionRepository.findAll(QuestionSpecification.randomQuestions(randomizationRules), PageRequest.of(0, count)).getContent();
    }


}
