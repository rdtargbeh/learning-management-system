package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.entity.Assessment;
import learning_management_system.backend.entity.Question;
import learning_management_system.backend.entity.QuestionBank;
import learning_management_system.backend.repository.AssessmentRepository;
import learning_management_system.backend.repository.QuestionBankRepository;
import learning_management_system.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
public class QuestionMapper {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Convert a Question entity to a QuestionDto.
     *
     * @param question the Question entity.
     * @return the mapped QuestionDto.
     */
    public QuestionDto toDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionDto dto = new QuestionDto();
        dto.setQuestionId(question.getQuestionId());
        dto.setQuestionText(question.getQuestionText());
        dto.setQuestionType(question.getQuestionType());
        dto.setQuestionOptions(question.getQuestionOptions());
        dto.setCorrectAnswer(question.getCorrectAnswer());
        dto.setDifficulty(question.getDifficulty());
        dto.setTags(question.getTags());
        dto.setExplanation(question.getExplanation());
        dto.setFeedback(question.getFeedback());
        dto.setMedia(question.getMedia());
        dto.setRandomized(question.getRandomized());
        dto.setTimeLimit(question.getTimeLimit());
        dto.setMetadata(question.getMetadata());
        dto.setAssessmentId(question.getAssessment() != null ? question.getAssessment().getAssessmentId() : null);
        dto.setQuestionBankId(question.getQuestionBank() != null ? question.getQuestionBank().getQuestionBankId() : null);
        dto.setShowCorrectAnswerImmediately(question.isShowCorrectAnswerImmediately());
        dto.setCorrectAttempts(question.getCorrectAttempts());
        dto.setIncorrectAttempts(question.getIncorrectAttempts());
        dto.setPartialAttempts(question.getPartialAttempts());
        dto.setAdaptiveLearning(question.getAdaptiveLearning());

        dto.setNextQuestionIfCorrect(question.getNextQuestionIfCorrect() != null
                ? question.getNextQuestionIfCorrect().getQuestionId()
                : null);
        dto.setNextQuestionIfIncorrect(question.getNextQuestionIfIncorrect() != null
                ? question.getNextQuestionIfIncorrect().getQuestionId()
                : null);

        return dto;
    }

    /**
     * Convert a QuestionDto to a Question entity.
     *
     * @param dto the QuestionDto.
     * @return the mapped Question entity.
     */
    public Question toEntity(QuestionDto dto) {
        if (dto == null) {
            return null;
        }

        Question question = new Question();
        question.setQuestionId(dto.getQuestionId());
        question.setQuestionText(dto.getQuestionText());
        question.setQuestionType(dto.getQuestionType());
        question.setQuestionOptions(dto.getQuestionOptions());
        question.setCorrectAnswer(dto.getCorrectAnswer());
        question.setDifficulty(dto.getDifficulty());
        question.setTags(dto.getTags());
        question.setExplanation(dto.getExplanation());
        question.setFeedback(dto.getFeedback());
        question.setMedia(dto.getMedia());
        question.setRandomized(dto.getRandomized());
        question.setTimeLimit(dto.getTimeLimit());
        question.setMetadata(dto.getMetadata());
        question.setShowCorrectAnswerImmediately(dto.isShowCorrectAnswerImmediately());
        question.setCorrectAttempts(dto.getCorrectAttempts());
        question.setIncorrectAttempts(dto.getIncorrectAttempts());
        question.setPartialAttempts(dto.getPartialAttempts());
        question.setAdaptiveLearning(dto.getAdaptiveLearning());

        // Fetch and set Assessment
        if (dto.getAssessmentId() != null) {
            Assessment assessment = assessmentRepository.findById(dto.getAssessmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Assessment not found with ID: " + dto.getAssessmentId()));
            question.setAssessment(assessment);
        }

        // Fetch and set QuestionBank
        if (dto.getQuestionBankId() != null) {
            QuestionBank questionBank = questionBankRepository.findById(dto.getQuestionBankId())
                    .orElseThrow(() -> new IllegalArgumentException("Question Bank not found with ID: " + dto.getQuestionBankId()));
            question.setQuestionBank(questionBank);
        }

        // Fetch and set next question if correct
        if (dto.getNextQuestionIfCorrect() != null) {
            Question nextCorrect = questionRepository.findById(dto.getNextQuestionIfCorrect())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Question not found with ID: " + dto.getNextQuestionIfCorrect()));
            question.setNextQuestionIfCorrect(nextCorrect);
        }

// Fetch and set next question if incorrect
        if (dto.getNextQuestionIfIncorrect() != null) {
            Question nextIncorrect = questionRepository.findById(dto.getNextQuestionIfIncorrect())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Question not found with ID: " + dto.getNextQuestionIfIncorrect()));
            question.setNextQuestionIfIncorrect(nextIncorrect);
        }

        return question;
    }

}

