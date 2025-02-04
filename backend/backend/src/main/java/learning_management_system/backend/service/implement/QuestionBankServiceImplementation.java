package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.QuestionBankDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.entity.Question;
import learning_management_system.backend.entity.QuestionBank;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.enums.QuestionBankType;
import learning_management_system.backend.mapper.QuestionBankMapper;
import learning_management_system.backend.mapper.QuestionMapper;
import learning_management_system.backend.repository.QuestionBankRepository;
import learning_management_system.backend.repository.QuestionRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.ActivityLogsService;
import learning_management_system.backend.service.QuestionBankService;
import learning_management_system.backend.service.QuestionService;
import learning_management_system.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionBankServiceImplementation implements QuestionBankService {

    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Autowired
    private QuestionBankMapper questionBankMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityLogsService activityLogsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;


    @Override
    @Transactional
    public QuestionBankDto createQuestionBank(QuestionBankDto questionBankDto) {

        // Validate input
        if (questionBankDto.getName() == null || questionBankDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Question bank name cannot be null or empty.");
        }
        if (questionBankDto.getType() == null) {
            throw new IllegalArgumentException("Question bank type is required.");
        }

        // Retrieve the User entity
        User currentUser = userService.getUserEntityById(userService.getCurrentUserId());

        // Check if a QuestionBank with the same name exists for the current user
        if (questionBankRepository.existsByNameAndCreatedBy(questionBankDto.getName(), currentUser)) {
            throw new RuntimeException("A question bank with this name already exists.");
        }

        if (questionBankRepository.existsByNameAndCreatedBy(questionBankDto.getName(), currentUser)) {
            throw new RuntimeException("A question bank with this name already exists.");
        }

        // Map DTO to entity
        QuestionBank questionBank = questionBankMapper.toEntity(questionBankDto);
        questionBank.setCreatedBy(currentUser);  // Set the creator
        questionBank.setVersion(1); // Initialize the version to 1
        questionBank.setDateCreated(new Date());
        questionBank.setDateUpdated(new Date());
        questionBank.setUsageStatistics("0"); // Initialize usage statistics


        // Save to the database
        questionBank = questionBankRepository.save(questionBank);


        // Log creation
        activityLogsService.logAction(
                currentUser,
                ActionCategory.QUESTION_BANK_MANAGEMENT,
                ActionType.CREATE,
                "QuestionBank",
                questionBank.getQuestionBankId(),
                true,
                null,
                Map.of("name", questionBank.getName())
        );

        // Return the created QuestionBankDto
        return questionBankMapper.toDto(questionBank);
    }


    @Override
    @Transactional
    public QuestionBankDto updateQuestionBank(Long questionBankId, QuestionBankDto questionBankDto) {
        // Fetch the existing question bank
        QuestionBank existingBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        // Map the DTO to update the entity's fields
        existingBank.setName(questionBankDto.getName());
        existingBank.setDescription(questionBankDto.getDescription());
        existingBank.setTags(questionBankDto.getTags());
        existingBank.setRandomizationRules(questionBankDto.getRandomizationRules());
        existingBank.setAdaptiveQuestioningEnabled(questionBankDto.getAdaptiveQuestioningEnabled());

        // Validate question difficulties in the bank
        List<Question> questions = questionRepository.findByQuestionBank_QuestionBankId(questionBankId);
        for (Question question : questions) {
            QuestionDto questionDto = questionMapper.toDto(question); // Map to DTO for validation
            validateQuestionDifficulty(questionBankId, questionDto);
        }

        // Save the updated question bank
        return questionBankMapper.toDto(questionBankRepository.save(existingBank));
    }

    @Override
    @Transactional
    public void deleteQuestionBank(Long questionBankId) {
        if (!questionBankRepository.existsById(questionBankId)) {
            throw new RuntimeException("Question bank not found with ID: " + questionBankId);
        }
        questionBankRepository.deleteById(questionBankId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionBankDto getQuestionBankById(Long questionBankId) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));
        return questionBankMapper.toDto(questionBank);
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> searchQuestionBanks(String query) {
        List<QuestionBank> questionBanks = questionBankRepository.findByNameContainingIgnoreCase(query);
        return questionBanks.stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> getQuestionBanksByCreator(Long creatorId) {
        List<QuestionBank> questionBanks = questionBankRepository.findByCreatedByUserId(creatorId);
        return questionBanks.stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> getQuestionBanksByTag(String tag) {
        List<QuestionBank> questionBanks = questionBankRepository.findByTagsContainingIgnoreCase(tag);
        return questionBanks.stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> getQuestionBanksByType(QuestionBankType type) {
        List<QuestionBank> questionBanks = questionBankRepository.findByType(type);
        return questionBanks.stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void bulkAddQuestions(Long questionBankId, List<Long> questionIds) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        List<Question> questions = questionRepository.findAllById(questionIds);
        questions.forEach(q -> q.setQuestionBank(questionBank));
        questionRepository.saveAll(questions);
    }

    @Override
    @Transactional
    public void bulkRemoveQuestions(Long questionBankId, List<Long> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);
        questions.forEach(q -> q.setQuestionBank(null));
        questionRepository.saveAll(questions);
    }

    @Override
    @Transactional
    public QuestionBankDto shareQuestionBank(Long questionBankId, List<Long> userIds) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        List<User> users = userRepository.findAllById(userIds);
        if (users.isEmpty()) {
            throw new RuntimeException("No valid users found to share with.");
        }

        String existingSharedWith = questionBank.getSharedWith();
        Set<Long> sharedIds = existingSharedWith == null
                ? new HashSet<>()
                : Arrays.stream(existingSharedWith.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toSet());
        sharedIds.addAll(userIds);

        questionBank.setSharedWith(sharedIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
        questionBankRepository.save(questionBank);

        return questionBankMapper.toDto(questionBank);
    }

    /**
     * Get shared question banks with pagination.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> getSharedQuestionBanks(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        String userIdString = String.valueOf(userId);
        Page<QuestionBank> sharedBanksPage = questionBankRepository.findSharedWithUser(userIdString, pageable);
        return sharedBanksPage.getContent().stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Get all question banks with pagination.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionBankDto> getAllQuestionBanks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionBank> questionBanksPage = questionBankRepository.findAll(pageable);
        return questionBanksPage.getContent().stream()
                .map(questionBankMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void updateVersion(Long questionBankId) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        questionBank.setVersion(questionBank.getVersion() + 1);
        questionBankRepository.save(questionBank);
    }

    @Override
    @Transactional
    public void enableAdaptiveQuestioning(Long questionBankId) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        questionBank.setAdaptiveQuestioningEnabled(true);
        questionBankRepository.save(questionBank);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> generateRandomizedQuestions(Long questionBankId, int count) {
        QuestionBank bank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));

        List<Question> randomizedQuestions = questionService.findRandomQuestions(bank.getRandomizationRules(), count);

        return randomizedQuestions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

//    public List<QuestionDto> generateRandomizedQuestions(Long questionBankId, int count) {
//        QuestionBank bank = questionBankRepository.findById(questionBankId)
//                .orElseThrow(() -> new RuntimeException("Question bank not found with ID: " + questionBankId));
//
//        // If no rules exist, get random questions
//        List<Question> randomizedQuestions;
//        if (bank.getRandomizationRules() == null || bank.getRandomizationRules().isEmpty()) {
//            randomizedQuestions = questionRepository.findRandomQuestions(count);
//        } else {
//            randomizedQuestions = customQuestionRepository.findRandomQuestionsWithRules(bank.getRandomizationRules(), count);
//        }
//
//        return randomizedQuestions.stream()
//                .map(questionMapper::toDto)
//                .collect(Collectors.toList());
//    }


    @Transactional
    private void validateQuestionDifficulty(Long questionBankId, QuestionDto questionDto) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("QuestionBank not found"));

        if (!questionBank.getDefaultDifficultyDistribution().contains(questionDto.getDifficulty().name())) {
            throw new RuntimeException("Question difficulty does not match bank's allowed distribution.");
        }
    }


    @Override
    @Transactional
    public void updateUsageStatistics(Long questionBankId) {
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("QuestionBank not found"));

        long totalQuestions = questionRepository.countByQuestionBank_QuestionBankId(questionBankId);

        questionBank.setUsageStatistics("Total Questions: " + totalQuestions);
        questionBankRepository.save(questionBank);
    }

    @Transactional
    public QuestionDto addQuestionToBank(Long questionBankId, QuestionDto questionDto) {
        // Step 1: Validate the question bank
        QuestionBank questionBank = questionBankRepository.findById(questionBankId)
                .orElseThrow(() -> new RuntimeException("QuestionBank not found with ID: " + questionBankId));

        // Step 2: Validate the question's difficulty
        validateQuestionDifficulty(questionBankId, questionDto);

        // Step 3: Map DTO to Entity
        Question question = questionMapper.toEntity(questionDto);
        question.setQuestionBank(questionBank);

        // Step 4: Save the question
        question = questionRepository.save(question);

        // Step 5: Update question bank usage statistics
        updateUsageStatistics(questionBankId);

        // Step 6: Return the added question as DTO
        return questionMapper.toDto(question);
    }


}

