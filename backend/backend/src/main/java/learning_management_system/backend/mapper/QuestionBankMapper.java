package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.QuestionBankDto;
import learning_management_system.backend.entity.Question;
import learning_management_system.backend.entity.QuestionBank;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.QuestionBankRepository;
import learning_management_system.backend.repository.QuestionRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring")

@Component
public class QuestionBankMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionBankRepository questionBankRepository;

    /**
     * Convert a QuestionBank entity to a QuestionBankDto.
     *
     * @param questionBank the QuestionBank entity.
     * @return the mapped QuestionBankDto.
     */
    public QuestionBankDto toDto(QuestionBank questionBank) {
        if (questionBank == null) {
            return null;
        }

        QuestionBankDto dto = new QuestionBankDto();
        dto.setQuestionBankId(questionBank.getQuestionBankId());
        dto.setName(questionBank.getName());
        dto.setDescription(questionBank.getDescription());
        dto.setTags(questionBank.getTags());
        dto.setType(questionBank.getType());
        dto.setParentBankId(questionBank.getParentBank() != null ? questionBank.getParentBank().getQuestionBankId() : null);
        dto.setVersion(questionBank.getVersion());
        dto.setCreatedById(questionBank.getCreatedBy() != null ? questionBank.getCreatedBy().getUserId() : null);
        dto.setSharedWith(questionBank.getSharedWith());
        dto.setQuestionIds(questionBank.getQuestions() != null
                ? questionBank.getQuestions().stream().map(Question::getQuestionId).collect(Collectors.toList())
                : null);
        dto.setLinkedAssessments(questionBank.getLinkedAssessments());
        dto.setStatistics(questionBank.getStatistics());
        dto.setAccessLogs(questionBank.getAccessLogs());
        dto.setRandomizationRules(questionBank.getRandomizationRules());
        dto.setUsageStatistics(questionBank.getUsageStatistics());
        dto.setHierarchicalTags(questionBank.getHierarchicalTags());
        dto.setBulkOperationsLog(questionBank.getBulkOperationsLog());
        dto.setDefaultDifficultyDistribution(questionBank.getDefaultDifficultyDistribution());
        dto.setAdaptiveQuestioningEnabled(questionBank.getAdaptiveQuestioningEnabled());

        return dto;
    }

    /**
     * Convert a QuestionBankDto to a QuestionBank entity.
     *
     * @param dto the QuestionBankDto.
     * @return the mapped QuestionBank entity.
     */
    public QuestionBank toEntity(QuestionBankDto dto) {
        if (dto == null) {
            return null;
        }

        QuestionBank questionBank = new QuestionBank();
        questionBank.setQuestionBankId(dto.getQuestionBankId());
        questionBank.setName(dto.getName());
        questionBank.setDescription(dto.getDescription());
        questionBank.setTags(dto.getTags());
        questionBank.setType(dto.getType());
        questionBank.setVersion(dto.getVersion());
        questionBank.setSharedWith(dto.getSharedWith());
        questionBank.setLinkedAssessments(dto.getLinkedAssessments());
        questionBank.setStatistics(dto.getStatistics());
        questionBank.setAccessLogs(dto.getAccessLogs());
        questionBank.setRandomizationRules(dto.getRandomizationRules());
        questionBank.setUsageStatistics(dto.getUsageStatistics());
        questionBank.setHierarchicalTags(dto.getHierarchicalTags());
        questionBank.setBulkOperationsLog(dto.getBulkOperationsLog());
        questionBank.setDefaultDifficultyDistribution(dto.getDefaultDifficultyDistribution());
        questionBank.setAdaptiveQuestioningEnabled(dto.getAdaptiveQuestioningEnabled());

        // Fetch and set the createdBy User
        if (dto.getCreatedById() != null) {
            User createdBy = userRepository.findById(dto.getCreatedById())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getCreatedById()));
            questionBank.setCreatedBy(createdBy);
        }

        // Fetch and set the Parent QuestionBank
        if (dto.getParentBankId() != null) {
            QuestionBank parentBank = questionBankRepository.findById(dto.getParentBankId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent QuestionBank not found with ID: " + dto.getParentBankId()));
            questionBank.setParentBank(parentBank);
        }

        // Fetch and set the Questions
        if (dto.getQuestionIds() != null) {
            List<Question> questions = questionRepository.findAllById(dto.getQuestionIds());
            questionBank.setQuestions(new ArrayList<>(questions));
        }

        return questionBank;
    }

}
