package learning_management_system.backend.service;

import learning_management_system.backend.dto.QuestionBankDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.enums.QuestionBankType;

import java.util.List;

public interface QuestionBankService {

    QuestionBankDto createQuestionBank(QuestionBankDto questionBankDto);

    QuestionBankDto updateQuestionBank(Long questionBankId, QuestionBankDto questionBankDto);

    void deleteQuestionBank(Long questionBankId);

    QuestionBankDto getQuestionBankById(Long questionBankId);

    List<QuestionBankDto> getAllQuestionBanks(int page, int size);

    List<QuestionBankDto> searchQuestionBanks(String query);

    List<QuestionBankDto> getQuestionBanksByCreator(Long creatorId);

    List<QuestionBankDto> getQuestionBanksByTag(String tag);

    List<QuestionBankDto> getQuestionBanksByType(QuestionBankType type);

    void bulkAddQuestions(Long questionBankId, List<Long> questionIds);

    void bulkRemoveQuestions(Long questionBankId, List<Long> questionIds);

    QuestionBankDto shareQuestionBank(Long questionBankId, List<Long> userIds);

    List<QuestionBankDto> getSharedQuestionBanks(Long userId, int page, int size);

    void updateVersion(Long questionBankId);

    void updateUsageStatistics(Long questionBankId);

    void enableAdaptiveQuestioning(Long questionBankId);

    List<QuestionDto> generateRandomizedQuestions(Long questionBankId, int count);

    QuestionDto addQuestionToBank(Long questionBankId, QuestionDto questionDto);

//    void validateQuestionDifficulty(Long questionBankId, QuestionDto questionDto);

}
