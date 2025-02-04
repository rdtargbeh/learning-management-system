package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.QuestionMarksDto;
import learning_management_system.backend.entity.QuestionMarks;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.mapper.QuestionMarksMapper;
import learning_management_system.backend.repository.GradingRepository;
import learning_management_system.backend.repository.QuestionMarksRepository;
import learning_management_system.backend.repository.QuestionRepository;
import learning_management_system.backend.service.ActivityLogsService;
import learning_management_system.backend.service.QuestionMarksService;
import learning_management_system.backend.service.UserService;
import learning_management_system.backend.utility.QuestionMarksId;
import learning_management_system.backend.utility.RubricValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionMarksServiceImplementation implements QuestionMarksService {

    @Autowired
    private QuestionMarksRepository questionMarksRepository;
    @Autowired
    private QuestionMarksMapper questionMarksMapper;
    @Autowired
    private GradingRepository gradingRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ActivityLogsService activityLogsService;
    @Autowired
    private UserService userService;



    @Override
    @Transactional
    public QuestionMarksDto createQuestionMarks(QuestionMarksDto questionMarksDto) {
        // Step 1: Validate input
        validateQuestionMarks(questionMarksDto); // Ensure Grading and Question exist

        // Step 2: Prevent duplicates
        if (questionMarksRepository.existsById(new QuestionMarksId(
                questionMarksDto.getGradingId(),
                questionMarksDto.getQuestionId()
        ))) {
            throw new IllegalArgumentException("QuestionMarks entry already exists for the given Grading and Question.");
        }
        // Step 3: Validate Rubric (if applicable)
        if (questionMarksDto.getRubric() != null && !validateRubric(questionMarksDto.getRubric())) {
            throw new IllegalArgumentException("Invalid rubric format.");
        }
        // Step 4: Map DTO to Entity
        QuestionMarks questionMarks = questionMarksMapper.toEntity(questionMarksDto);
        questionMarks.setDateCreated(new Date());
        questionMarks.setDateUpdated(new Date());

        questionMarks = questionMarksRepository.save(questionMarks);     // Step 5: Save to Database
        // Step 6: Update Aggregated Data
        updateQuestionAggregates(questionMarks.getQuestion().getQuestionId());
        // Fetch the current user as User entity
        User currentUser = userService.getUserEntityById(userService.getCurrentUserId());
        // Step 7: Log the operation
        activityLogsService.logAction(
                currentUser,
                ActionCategory.GRADING_MANAGEMENT,
                ActionType.CREATE,
                "QuestionMarks",
                Long.valueOf(questionMarks.getId().toString()), // Log composite key as String
                true,
                null,
                Map.of("marks", String.valueOf(questionMarks.getMarks()))
        );
        // Step 8: Return the Created DTO
        return questionMarksMapper.toDto(questionMarks);
    }


    @Override
    @Transactional
    public QuestionMarksDto updateQuestionMarks(QuestionMarksDto questionMarksDto) {
        validateQuestionMarks(questionMarksDto); // Check data validity
        QuestionMarks existingMarks = questionMarksRepository.findById(new QuestionMarksId(
                        questionMarksDto.getGradingId(),
                        questionMarksDto.getQuestionId()))
                .orElseThrow(() -> new IllegalArgumentException("QuestionMarks entry not found"));

        existingMarks.setMarks(questionMarksDto.getMarks());
        existingMarks.setPartialMarks(questionMarksDto.getPartialMarks());
        questionMarksRepository.save(existingMarks);
        return questionMarksMapper.toDto(existingMarks);
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionMarksDto> getQuestionMarksByGradingId(Long gradingId) {
        List<QuestionMarks> questionMarks = questionMarksRepository.findById_GradingId(gradingId);
        return questionMarks.stream()
                .map(questionMarksMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageMarks(Long gradingId) {
        return questionMarksRepository.findAverageMarksByGradingId(gradingId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAveragePartialMarks(Long gradingId) {
        return questionMarksRepository.findAveragePartialMarksByGradingId(gradingId);
    }

    @Override
    @Transactional
    public void bulkCreateQuestionMarks(List<QuestionMarksDto> questionMarksDtos) {
        for (QuestionMarksDto dto : questionMarksDtos) {
            validateQuestionMarks(dto);
        }
        List<QuestionMarks> questionMarks = questionMarksDtos.stream()
                .map(questionMarksMapper::toEntity)
                .collect(Collectors.toList());
        questionMarksRepository.saveAll(questionMarks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuestionMarksDto> getQuestionMarksWithPaginationAndFilters(Long gradingId, int minMarks, int maxMarks, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionMarks> questionMarksPage = questionMarksRepository.findById_GradingIdAndMarksBetween(gradingId, minMarks, maxMarks, pageable);
        return questionMarksPage.map(questionMarksMapper::toDto);
    }

    @Override
    @Transactional
    public void overrideGradingForStudent(Long gradingId, Long studentId, int newMarks) {
        QuestionMarks questionMarks = questionMarksRepository.findByGrading_IdAndStudentId(gradingId, studentId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found."));
        questionMarks.setMarks(newMarks);
        questionMarksRepository.save(questionMarks);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Double> getDifficultyWisePerformance(Long gradingId) {
        List<Object[]> results = questionMarksRepository.getDifficultyWisePerformance(gradingId);
        return results.stream()
                .collect(Collectors.toMap(
                        r -> r[0].toString(),
                        r -> Double.valueOf(r[1].toString())
                ));
    }

    /**
     * Calculates the average marks for a specific question.
     *
     * @param questionId The ID of the question.
     * @return The average marks for the question.
     */
    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageMarksByQuestionId(Long questionId) {
        return questionMarksRepository.findAverageMarksByQuestionId(questionId);
    }


    private void validateQuestionMarks(QuestionMarksDto questionMarksDto) {
        // Validate grading exists
        gradingRepository.findById(questionMarksDto.getGradingId())
                .orElseThrow(() -> new IllegalArgumentException("Grading not found with ID: " + questionMarksDto.getGradingId()));

        // Validate question exists
        questionRepository.findById(questionMarksDto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + questionMarksDto.getQuestionId()));
    }

    private void updateQuestionAggregates(Long questionId) {
        List<QuestionMarks> questionMarksList = questionMarksRepository.findByQuestion_QuestionId(questionId);
        long totalAttempts = questionMarksList.size();
        long totalMarksEarned = questionMarksList.stream().mapToLong(QuestionMarks::getMarks).sum();
        double averageScore = totalAttempts > 0 ? (double) totalMarksEarned / totalAttempts : 0.0;

        // Update the attributes in QuestionMarks
        questionMarksList.forEach(questionMarks -> {
            questionMarks.setTotalAttempts(totalAttempts);
            questionMarks.setTotalMarksEarned(totalMarksEarned);
            questionMarks.setAverageScore(averageScore);
            questionMarksRepository.save(questionMarks);
        });
    }


    private boolean validateRubric(String rubric) {
        if (!RubricValidator.isValidRubric(rubric)) {
            throw new RuntimeException("Rubric format is invalid.");
        }
        return true;
    }

//    private boolean validateRubric(String rubric) {   // same as above
//        return RubricValidator.isValidRubric(rubric);
//    }


//    private void updateQuestionAggregates(Long questionId) {
//        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));
//
//        List<QuestionMarks> questionMarksList = questionMarksRepository.findByQuestion_QuestionId(questionId);
//
//        long totalAttempts = questionMarksList.size();
//        long totalMarksEarned = questionMarksList.stream().mapToLong(QuestionMarks::getMarks).sum();
//        double averageScore = totalAttempts > 0 ? (double) totalMarksEarned / totalAttempts : 0;
//
//        question.setTotalAttempts(totalAttempts);
//        question.setTotalMarksEarned(totalMarksEarned);
//        question.setAverageScore(averageScore);
//
//        questionRepository.save(question);
//    }






}