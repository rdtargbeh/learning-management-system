package learning_management_system.backend.controller;

import learning_management_system.backend.dto.QuestionBankDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.enums.QuestionBankType;
import learning_management_system.backend.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question-banks")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;


    @PostMapping
    public ResponseEntity<QuestionBankDto> createQuestionBank(@RequestBody QuestionBankDto questionBankDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionBankService.createQuestionBank(questionBankDto));
    }

    @PutMapping("/{questionBankId}")
    public ResponseEntity<QuestionBankDto> updateQuestionBank(@PathVariable Long questionBankId,
                                                              @RequestBody QuestionBankDto questionBankDto) {
        return ResponseEntity.ok(questionBankService.updateQuestionBank(questionBankId, questionBankDto));
    }

    @DeleteMapping("/{questionBankId}")
    public ResponseEntity<Void> deleteQuestionBank(@PathVariable Long questionBankId) {
        questionBankService.deleteQuestionBank(questionBankId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{questionBankId}")
    public ResponseEntity<QuestionBankDto> getQuestionBankById(@PathVariable Long questionBankId) {
        return ResponseEntity.ok(questionBankService.getQuestionBankById(questionBankId));
    }


    @GetMapping
    public ResponseEntity<List<QuestionBankDto>> getAllQuestionBanks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(questionBankService.getAllQuestionBanks(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<QuestionBankDto>> searchQuestionBanks(@RequestParam String query) {
        return ResponseEntity.ok(questionBankService.searchQuestionBanks(query));
    }

    @PostMapping("/{questionBankId}/bulk-add")
    public ResponseEntity<Void> bulkAddQuestions(@PathVariable Long questionBankId, @RequestBody List<Long> questionIds) {
        questionBankService.bulkAddQuestions(questionBankId, questionIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{questionBankId}/bulk-remove")
    public ResponseEntity<Void> bulkRemoveQuestions(@PathVariable Long questionBankId, @RequestBody List<Long> questionIds) {
        questionBankService.bulkRemoveQuestions(questionBankId, questionIds);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get question banks created by a specific creator.
     *
     * @param creatorId ID of the creator.
     * @return List of question banks created by the specified creator.
     */
    @GetMapping("/by-creator/{creatorId}")
    public ResponseEntity<List<QuestionBankDto>> getQuestionBanksByCreator(@PathVariable Long creatorId) {
        List<QuestionBankDto> questionBanks = questionBankService.getQuestionBanksByCreator(creatorId);
        return ResponseEntity.ok(questionBanks);
    }

    /**
     * Get question banks by tag.
     *
     * @param tag The tag to search for.
     * @return List of question banks containing the specified tag.
     */
    @GetMapping("/by-tag")
    public ResponseEntity<List<QuestionBankDto>> getQuestionBanksByTag(@RequestParam String tag) {
        List<QuestionBankDto> questionBanks = questionBankService.getQuestionBanksByTag(tag);
        return ResponseEntity.ok(questionBanks);
    }

    /**
     * Get question banks by type.
     *
     * @param type The type of question bank (e.g., EXAM, QUIZ).
     * @return List of question banks of the specified type.
     */
    @GetMapping("/by-type")
    public ResponseEntity<List<QuestionBankDto>> getQuestionBanksByType(@RequestParam QuestionBankType type) {
        List<QuestionBankDto> questionBanks = questionBankService.getQuestionBanksByType(type);
        return ResponseEntity.ok(questionBanks);
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<Void> shareQuestionBank(
            @PathVariable Long id,
            @RequestBody List<Long> userIds) {
        questionBankService.shareQuestionBank(id, userIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/shared")
    public ResponseEntity<List<QuestionBankDto>> getSharedQuestionBanks(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(questionBankService.getSharedQuestionBanks(userId, page, size));
    }


    @PatchMapping("/{id}/update-version")
    public ResponseEntity<Void> updateVersion(@PathVariable Long id) {
        questionBankService.updateVersion(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/enable-adaptive-questioning")
    public ResponseEntity<Void> enableAdaptiveQuestioning(@PathVariable Long id) {
        questionBankService.enableAdaptiveQuestioning(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Update usage statistics for a question bank.
     *
     * @param questionBankId The ID of the question bank to update usage statistics for.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PatchMapping("/{questionBankId}/update-usage")
    public ResponseEntity<Void> updateUsageStatistics(@PathVariable Long questionBankId) {
        questionBankService.updateUsageStatistics(questionBankId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{questionBankId}/add-question")
    public ResponseEntity<QuestionDto> addQuestionToBank(
            @PathVariable Long questionBankId,
            @RequestBody QuestionDto questionDto) {
        QuestionDto savedQuestion = questionBankService.addQuestionToBank(questionBankId, questionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @GetMapping("/{questionBankId}/random-questions")
    public ResponseEntity<List<QuestionDto>> generateRandomizedQuestions(
            @PathVariable Long questionBankId,
            @RequestParam(defaultValue = "10") int count) {
        List<QuestionDto> questions = questionBankService.generateRandomizedQuestions(questionBankId, count);
        return ResponseEntity.ok(questions);
    }



//    @GetMapping("/{questionBankId}/random-questions/{count}")
//    public ResponseEntity<List<QuestionDto>> generateRandomQuestions(
//            @PathVariable Long questionBankId,
//            @PathVariable int count) {
//        List<QuestionDto> questions = questionBankService.generateRandomizedQuestions(questionBankId, count);
//        return ResponseEntity.ok(questions);
//    }


//    @PostMapping("/{questionBankId}/validate-difficulty")
//    public ResponseEntity<Void> validateQuestionDifficulty(
//            @PathVariable Long questionBankId,
//            @RequestBody QuestionDto questionDto) {
//        questionBankService.validateQuestionDifficulty(questionBankId, questionDto);
//        return ResponseEntity.ok().build();
//    }
}
