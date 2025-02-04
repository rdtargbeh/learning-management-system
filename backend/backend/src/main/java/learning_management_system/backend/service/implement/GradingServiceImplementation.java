package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.mapper.GradingMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.GradeBookHistoryService;
import learning_management_system.backend.service.GradingService;
import learning_management_system.backend.service.LmsNotificationService;
import learning_management_system.backend.service.StudentService;
import learning_management_system.backend.utility.GradingOverrideDto;
import learning_management_system.backend.utility.GradingResultDto;
import learning_management_system.backend.utility.GradingStatusDto;
import learning_management_system.backend.utility.GradingSubmissionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradingServiceImplementation implements GradingService {

    @Autowired
    private GradingRepository gradingRepository;
    @Autowired
    private GradingMapper gradingMapper;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private  QuestionRepository questionRepository;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;
    @Autowired
    private GradeBookHistoryService gradeBookHistoryService;


    @Override
    @Transactional
    public GradingDto createGrading(GradingDto gradingDto) {
        validateGradingDto(gradingDto); // Validate input

        // Check for existing configuration
        if (gradingRepository.existsByLinkedEntityIdAndLinkedEntityType(gradingDto.getLinkedEntityId(), gradingDto.getLinkedEntityType())) {
        throw new RuntimeException("Grading configuration already exists for this entity.");
        }

        Grading grading = gradingMapper.toEntity(gradingDto);

        // Associate with the linked entity
        if (gradingDto.getLinkedEntityType() == GradingLinkedEntityType.ASSIGNMENT) {
            Assignment assignment = assignmentRepository.findById(gradingDto.getLinkedEntityId())
                .orElseThrow(() -> new RuntimeException("Assignment not found."));
            grading.setAssignment(assignment);
        } else if (gradingDto.getLinkedEntityType() == GradingLinkedEntityType.ASSESSMENT) {
            Assessment assessment = assessmentRepository.findById(gradingDto.getLinkedEntityId())
                .orElseThrow(() -> new RuntimeException("Assessment not found."));
            grading.setAssessment(assessment);
        }
        // Save and return
        grading = gradingRepository.save(grading);
        return gradingMapper.toDto(grading);
    }


    @Override
    @Transactional
    public GradingDto updateGrading(Long gradingId, GradingDto gradingDto) {
        validateGradingDto(gradingDto); // Validate the input DTO

        // Fetch the existing Grading entity
        Grading grading = gradingRepository.findById(gradingId)
                .orElseThrow(() -> new RuntimeException("Grading not found."));

        // Use the mapper to update the entity from the DTO
        gradingMapper.updateEntity(gradingDto, grading);

        // Re-associate linked entities based on `linkedEntityType`
        if (gradingDto.getLinkedEntityType() != null && gradingDto.getLinkedEntityId() != null) {
            switch (gradingDto.getLinkedEntityType()) {
                case ASSIGNMENT:
                    Assignment assignment = assignmentRepository.findById(gradingDto.getLinkedEntityId())
                            .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + gradingDto.getLinkedEntityId()));
                    grading.setAssignment(assignment);
                    break;

                case ASSESSMENT:
                    Assessment assessment = assessmentRepository.findById(gradingDto.getLinkedEntityId())
                            .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + gradingDto.getLinkedEntityId()));
                    grading.setAssessment(assessment);
                    break;

                case QUESTION:
                    Question question = questionRepository.findById(gradingDto.getLinkedEntityId())
                            .orElseThrow(() -> new RuntimeException("Question not found with ID: " + gradingDto.getLinkedEntityId()));
                    grading.setQuestion(question);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported linked entity type: " + gradingDto.getLinkedEntityType());
            }
        }

        // Save the updated Grading entity
        grading = gradingRepository.save(grading);

        // Convert the updated entity back to a DTO and return
        return gradingMapper.toDto(grading);
    }



    // Ensure required fields like gradingPolicy, totalMarks, and gradingRubric are provided
    private void validateGradingDto(GradingDto gradingDto) {
        if (gradingDto.getGradingPolicy() == null) {
            throw new IllegalArgumentException("Grading policy is required.");
        }
        if (gradingDto.getTotalMarks() == null || gradingDto.getTotalMarks() <= 0) {
            throw new IllegalArgumentException("Total marks must be greater than zero.");
        }
        if (gradingDto.getGradingRubric() == null) {
            throw new IllegalArgumentException("Grading rubric is required.");
        }
        // Additional validation as needed
    }


    @Override
    @Transactional(readOnly = true)
    public GradingDto getGradingById(Long gradingId) {
        Grading grading = gradingRepository.findById(gradingId)
                .orElseThrow(() -> new RuntimeException("Grading not found."));
        return gradingMapper.toDto(grading);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradingDto> getAllGradings() {
        return gradingRepository.findAll()
                .stream()
                .map(gradingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradingDto> getNormalizedGradings() {
        return gradingRepository.findAllNormalizedGradings()
                .stream()
                .map(gradingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGrading(Long gradingId) {
        if (!gradingRepository.existsById(gradingId)) {
            throw new RuntimeException("Grading not found.");
        }
        gradingRepository.deleteById(gradingId);
    }

    @Override
    @Transactional(readOnly = true)
    public GradingStatusDto getGradingStatus(GradingLinkedEntityType entityType, Long entityId) {
        Grading grading = null;

        // Fetch the grading configuration based on the entity type
        switch (entityType) {
            case ASSIGNMENT:
                grading = gradingRepository.findByAssignment_AssignmentId(entityId)
                        .orElseThrow(() -> new RuntimeException("Grading configuration not found for assignment ID: " + entityId));
                break;
            case ASSESSMENT:
                grading = gradingRepository.findByAssessment_AssessmentId(entityId)
                        .orElseThrow(() -> new RuntimeException("Grading configuration not found for assessment ID: " + entityId));
                break;
            case QUESTION:
                grading = gradingRepository.findByQuestion_QuestionId(entityId)
                        .orElseThrow(() -> new RuntimeException("Grading configuration not found for question ID: " + entityId));
                break;
            default:
                throw new RuntimeException("Unsupported entity type: " + entityType);
        }

        // Create and return the GradingStatusDto
        return new GradingStatusDto(
                grading.getGradingPolicy(),
                grading.getTotalMarks(),
                grading.getAttemptPenalty(),
                grading.getLateSubmissionPolicy(),
                grading.getLateSubmissionPenaltyPercentage()
        );
    }

    @Override
    @Transactional
    public GradingResultDto overrideGrade(Long gradingId, GradingOverrideDto overrideDto) {
        Grading grading = gradingRepository.findById(gradingId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found for grading ID: " + gradingId));

        GradeBookRecord record = gradeBookRecordRepository.findByGradeBookItem(grading.getGradeBookItem())
                .orElseThrow(() -> new RuntimeException("GradeBookRecord not found for grading ID: " + gradingId));

        // Override the grade
        record.setScore(overrideDto.getNewScore());
        record.setFeedback(overrideDto.getFeedback());
        record.setFinalized(true);
        gradeBookRecordRepository.save(record);

        // Notify the student about the grade override
        notificationService.createAndSendNotification(
                record.getStudent().getUser().getUserId(),
                "Your grade for " + grading.getGradeBookItem().getName() + " has been overridden.",
                grading.getGradeBookItem().getItemId(),
                RelatedEntityType.GRADEBOOK_ITEM,
                "Grade overridden with new score: " + overrideDto.getNewScore()
        );

        // Record the grade change in history
        gradeBookHistoryService.recordGradeChange(
                record.getRecordId(),
                overrideDto.getPreviousScore(),
                overrideDto.getNewScore(),
                overrideDto.getChangeReason(),
                overrideDto.getChangedById()
        );

        return new GradingResultDto(overrideDto.getNewScore(), "Grade successfully overridden.");
    }

    /**
     * Calculates the score for a specific question based on the grading rubric and the submitted answer.
     *
     * @param grading The grading configuration for the question.
     * @param submittedAnswer The student's submitted answer for the question.
     * @return The calculated score for the question.
     */
    private Double calculateQuestionScore(Grading grading, String submittedAnswer) {
        if (grading.getGradingRubric() == null || grading.getGradingRubric().isEmpty()) {
            throw new RuntimeException("Grading rubric is missing for the question.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the grading rubric JSON into a map
            Map<String, Object> rubric = objectMapper.readValue(grading.getGradingRubric(), new TypeReference<Map<String, Object>>() {});

            // Fetch correct answer and full marks from the rubric
            String correctAnswer = (String) rubric.get("correctAnswer");
            Double fullMarks = rubric.containsKey("fullMarks") ? (Double) rubric.get("fullMarks") : grading.getTotalMarks().doubleValue();

            // Assign full marks if the submitted answer matches the correct answer
            if (correctAnswer != null && correctAnswer.equalsIgnoreCase(submittedAnswer)) {
                return fullMarks;
            }

            // Handle partial credit if defined in the rubric
            if (rubric.containsKey("partialMarks")) {
                @SuppressWarnings("unchecked")
                Map<String, Double> partialMarks = (Map<String, Double>) rubric.get("partialMarks");
                for (Map.Entry<String, Double> entry : partialMarks.entrySet()) {
                    if (submittedAnswer != null && submittedAnswer.contains(entry.getKey())) {
                        return entry.getValue();
                    }
                }
            }

            // Return zero if no match found
            return 0.0;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse grading rubric JSON.", e);
        }
    }


    /**
     * Calculates the score for an assessment based on auto-grading logic.
     *
     * @param questionMarks A map of question IDs to their respective marks.
     * @param submittedAnswers A map of submitted answers with question IDs as keys.
     * @return The total score for the assessment.
     */
    private Double calculateAutoScore(Map<Long, Integer> questionMarks, Map<String, String> submittedAnswers) {

        Double totalScore = 0.0;

        // Convert submitted answers to a compatible format (Map<Long, String>)
        Map<Long, String> processedAnswers = submittedAnswers.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.valueOf(entry.getKey()), // Convert keys to Long
                        Map.Entry::getValue // Keep values as String
                ));

        // Calculate the total score by iterating through questionMarks
        for (Map.Entry<Long, Integer> questionEntry : questionMarks.entrySet()) {
            Long questionId = questionEntry.getKey();
            Integer questionMark = questionEntry.getValue();

            // Check if the submitted answers contain the question ID
            if (processedAnswers.containsKey(questionId)) {
                String submittedAnswer = processedAnswers.get(questionId);

                // Example: Assign full marks if the submitted answer is "CORRECT"
                if ("CORRECT".equalsIgnoreCase(submittedAnswer)) {
                    totalScore += questionMark;
                }
            }
        }

        return totalScore;
    }


    /**
     * Grades an assessment and returns the grading result.
     *
     * @param assessmentId The ID of the assessment to grade.
     * @param submissionDto The submission data from the student.
     * @return The grading result as a DTO.
     */
    @Override
    public GradingResultDto gradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto) {
        // Fetch grading configuration
        Grading grading = gradingRepository.findByAssessment_AssessmentId(assessmentId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found for assessment ID: " + assessmentId));

        // Calculate the score using auto-grading logic
        Double score = calculateAutoScore(grading.getQuestionMarks(), submissionDto.getAnswers());

        // Save and return the grading result
        return saveGradingResult(grading, submissionDto.getStudentId(), score);
    }

    @Override
    public GradingResultDto gradeQuestion(Long questionId, GradingSubmissionDto submissionDto) {
        // Fetch grading configuration
        Grading grading = gradingRepository.findByQuestion_QuestionId(questionId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found for question ID: " + questionId));

        // Grade based on individual question correctness
        Double score = calculateQuestionScore(grading, submissionDto.getAnswers().get(questionId));
        return saveGradingResult(grading, submissionDto.getStudentId(), score);
    }


    @Override
    public GradingResultDto gradeAssignment(Long assignmentId, GradingSubmissionDto submissionDto) {
        // Fetch grading configuration
        Grading grading = gradingRepository.findByAssignment_AssignmentId(assignmentId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found for assignment ID: " + assignmentId));

        // Implement manual grading or rubric-based logic
        Double score = calculateRubricScore(grading.getGradingRubric(), submissionDto);
        return saveGradingResult(grading, submissionDto.getStudentId(), score);
    }


    @Override
    public GradingResultDto gradeByEntityType(GradingLinkedEntityType entityType, Long entityId, GradingSubmissionDto submissionDto) {
        Long gradingId;

        switch (entityType) {
            case ASSIGNMENT:
                Assignment assignment = assignmentRepository.findById(entityId)
                        .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + entityId));
                gradingId = assignment.getGrading().getGradingId();
                break;

            case ASSESSMENT:
                Assessment assessment = assessmentRepository.findById(entityId)
                        .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + entityId));
                gradingId = assessment.getGrading().getGradingId();
                break;

            case QUESTION:
                Question question = questionRepository.findById(entityId)
                        .orElseThrow(() -> new RuntimeException("Question not found with ID: " + entityId));
                gradingId = question.getGrading().getGradingId();
                break;

            default:
                throw new IllegalArgumentException("Unsupported entity type: " + entityType);
        }

        return gradeByGradingId(gradingId, submissionDto); // Delegate to the 2nd Method
    }


    /**
     * Grades an entity (assignment, assessment, etc.) and returns the grading result.
     *
     * @param gradingId The ID of the grading configuration.
     * @param submissionDto The submission details.
     * @return GradingResultDto containing the grading outcome.
     */
    @Override
    @Transactional
    public GradingResultDto gradeByGradingId(Long gradingId, GradingSubmissionDto submissionDto) {
        // Fetch grading configuration
        Grading grading = gradingRepository.findById(gradingId)
                .orElseThrow(() -> new RuntimeException("Grading configuration not found for ID: " + gradingId));

        // Calculate the rubric-based score
        Double score = calculateRubricScore(grading.getGradingRubric(), submissionDto);

        // Save the grading result and return it
        return saveGradingResult(grading, submissionDto.getStudentId(), score);
    }


    public GradingResultDto saveGradingResult(Grading grading, Long studentId, Double score) {
        // Save grading result in GradeBookRecord
        GradeBookRecord record = new GradeBookRecord();
        record.setGradeBookItem(grading.getGradeBookItem());
        record.setStudent(studentService.getStudentEntityById(studentId)); // Fetch the Student entity
        record.setScore(score);
        record.setFinalized(true);
        gradeBookRecordRepository.save(record);

        // Notify student of grade
        notificationService.createAndSendNotification(
                studentId,
                "Your grade for " + grading.getGradeBookItem().getName() + " is " + score,
                grading.getGradeBookItem().getItemId(),
                RelatedEntityType.GRADEBOOK_ITEM,
                "Grade has been finalized"
        );

        return new GradingResultDto(score, "Grade successfully recorded.");
    }


    // Calculate Rubric score
    public Double calculateRubricScore(String gradingRubric, GradingSubmissionDto submissionDto) {
        if (gradingRubric == null || gradingRubric.isEmpty()) {
            throw new IllegalArgumentException("Grading rubric is missing.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the grading rubric into a Map<Long, Double>
            Map<Long, Double> rubric = objectMapper.readValue(gradingRubric, new TypeReference<Map<Long, Double>>() {});

            // Parse answers into a Map<Long, String> to ensure consistent types
            Map<Long, String> answers = new HashMap<>();
            for (Map.Entry<String, String> entry : submissionDto.getAnswers().entrySet()) {
                Long questionId;
                try {
                    questionId = Long.valueOf(entry.getKey());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid question ID in answers: " + entry.getKey(), e);
                }
                answers.put(questionId, entry.getValue());
            }

            // Calculate the total score
            Double totalScore = 0.0;
            for (Map.Entry<Long, String> answerEntry : answers.entrySet()) {
                Long questionId = answerEntry.getKey();
                String answer = answerEntry.getValue();

                // Check if the rubric contains the question ID and calculate the score
                if (rubric.containsKey(questionId)) {
                    Double questionScore = rubric.get(questionId);
                    if ("CORRECT".equalsIgnoreCase(answer)) { // Example condition for correctness
                        totalScore += questionScore;
                    }
                }
            }

            return totalScore;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse grading rubric JSON.", e);
        }
    }

    @Override
    @Transactional
    public GradingResultDto gradeAssignmentSubmission(Long assignmentId, GradingSubmissionDto submissionDto) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        Grading grading = assignment.getGrading();
        if (grading == null) {
            throw new RuntimeException("No grading configuration available for this assignment.");
        }

        // Implement rubric-based grading
        Double score = calculateRubricScore(grading.getGradingRubric(), submissionDto);

        // Save the result and notify the student
        return saveGradingResult(grading, submissionDto.getStudentId(), score);
    }










}
