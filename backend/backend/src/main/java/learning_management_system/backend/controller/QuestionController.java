package learning_management_system.backend.controller;

import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.enums.QuestionDifficultyLevel;
import learning_management_system.backend.service.QuestionService;
import learning_management_system.backend.utility.QuestionFilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private  QuestionService questionService;


    @PostMapping
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(questionDto));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        return ResponseEntity.ok(questionService.updateQuestion(questionId, questionDto));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long questionId) {
        return ResponseEntity.ok(questionService.getQuestionById(questionId));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByAssessmentId(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(questionService.getQuestionsByAssessmentId(assessmentId));
    }

    @GetMapping("/bank/{questionBankId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByQuestionBankId(@PathVariable Long questionBankId) {
        return ResponseEntity.ok(questionService.getQuestionsByQuestionBankId(questionBankId));
    }

    @GetMapping("/tag")
    public ResponseEntity<List<QuestionDto>> getQuestionsByTag(@RequestParam String tag) {
        return ResponseEntity.ok(questionService.getQuestionsByTag(tag));
    }

//    @GetMapping("/questions-by-tag/{tag}")
//    public ResponseEntity<List<QuestionDto>> getQuestionsByTag(@PathVariable String tag) {
//        List<QuestionDto> questions = questionService.getQuestionsByTag(tag);
//        return ResponseEntity.ok(questions);
//    }



    @GetMapping("/difficulty")
    public ResponseEntity<List<QuestionDto>> getQuestionsByDifficulty(@RequestParam QuestionDifficultyLevel difficulty) {
        return ResponseEntity.ok(questionService.getQuestionsByDifficulty(difficulty));
    }

    @PostMapping("/search")
    public ResponseEntity<List<QuestionDto>> getQuestionsByCriteria(@RequestBody QuestionFilterCriteria filterCriteria) {
        List<QuestionDto> questions = questionService.getQuestionsByCriteria(filterCriteria);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{assessmentId}/questions/{currentQuestionId}/next")
    public ResponseEntity<QuestionDto> getNextQuestion(
            @PathVariable Long assessmentId,
            @PathVariable Long currentQuestionId,
            @RequestParam boolean isCorrect
    ) {
        QuestionDto nextQuestion = questionService.getNextQuestion(assessmentId, currentQuestionId, isCorrect);
        return ResponseEntity.ok(nextQuestion);
    }

    @GetMapping("/questions/{questionId}/feedback")
    public ResponseEntity<Map<String, String>> getFeedbackAndExplanation(
            @PathVariable Long questionId,
            @RequestParam boolean isCorrect
    ) {
        Map<String, String> feedbackAndExplanation = questionService.getFeedbackAndExplanation(questionId, isCorrect);
        return ResponseEntity.ok(feedbackAndExplanation);
    }

    @GetMapping("/questions/{questionId}/stats")
    public ResponseEntity<Map<String, Long>> getQuestionAttemptStats(@PathVariable Long questionId) {
        Map<String, Long> stats = questionService.getQuestionAttemptStats(questionId);
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/{questionBankId}/add-question")
    public ResponseEntity<QuestionDto> addQuestionToBank(
            @PathVariable Long questionBankId,
            @RequestBody QuestionDto questionDto) {
        QuestionDto createdQuestion = questionService.createQuestionInBank(questionBankId, questionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }


}
