package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning_management_system.backend.dto.AssessmentDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.GradingDto;
import learning_management_system.backend.dto.QuestionDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.enums.SubmissionStatus;
import learning_management_system.backend.mapper.AssessmentMapper;
import learning_management_system.backend.mapper.AttachmentMapper;
import learning_management_system.backend.mapper.GradingMapper;
import learning_management_system.backend.mapper.QuestionMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.*;
import learning_management_system.backend.utility.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImplementation implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssessmentMapper assessmentMapper;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Lazy
    @Autowired
    private GradingService gradingService;
    @Autowired
    private GradingMapper gradingMapper;
    @Autowired
    private GradingRepository gradingRepository;
    @Autowired
    private GradeBookRecordRepository gradeBookRecordRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private ProctoringLogRepository proctoringLogRepository;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private StudentRepository studentRepository;
//    @Autowired
//    private CustomQuestionRepository customQuestionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProctoringService proctoringService;
    @Autowired
    private ObjectMapper objectMapper;



    @Override
    public AssessmentDto createAssessment(AssessmentDto assessmentDto) {
        User creator = userRepository.findById(assessmentDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + assessmentDto.getCreatedBy()));

        Course course = courseRepository.findById(assessmentDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + assessmentDto.getCourseId()));

        Assessment assessment = assessmentMapper.toEntity(assessmentDto);
        assessment.setCreatedBy(creator);
        assessment.setCourse(course);

        return assessmentMapper.toDto(assessmentRepository.save(assessment));
    }

    @Override
    public AssessmentDto updateAssessment(Long assessmentId, AssessmentDto assessmentDto) {
        Assessment existingAssessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Assessment updatedAssessment = assessmentMapper.toEntity(assessmentDto);
        updatedAssessment.setAssessmentId(existingAssessment.getAssessmentId());
        updatedAssessment.setDateCreated(existingAssessment.getDateCreated());

        return assessmentMapper.toDto(assessmentRepository.save(updatedAssessment));
    }

    @Override
    public AssessmentDto getAssessmentById(Long assessmentId) {
        return assessmentRepository.findById(assessmentId)
                .map(assessmentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
    }

    @Override
    public List<AssessmentDto> getAssessmentsByCourseId(Long courseId) {
        return assessmentRepository.findByCourseCourseId(courseId).stream()
                .map(assessmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssessmentDto> getPublishedAssessments() {
        return assessmentRepository.findByIsPublishedTrue().stream()
                .map(assessmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssessmentDto> getAssessmentsByCreator(Long creatorId) {
        return assessmentRepository.findByCreatedByUserId(creatorId).stream()
                .map(assessmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAssessment(Long assessmentId) {
        if (!assessmentRepository.existsById(assessmentId)) {
            throw new RuntimeException("Assessment not found with ID: " + assessmentId);
        }
        assessmentRepository.deleteById(assessmentId);
    }


    @Override
    public AttachmentDto addAttachmentToAssessment(Long assessmentId, AttachmentDto attachmentDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachment.setLinkedEntityType("ASSESSMENT");
        attachment.setLinkedEntityId(assessmentId);

        attachmentRepository.save(attachment);

        return attachmentMapper.toDto(attachment);
    }

    @Override
    @Transactional
    public GradingResultDto gradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Grading grading = assessment.getGrading();
        if (grading == null) {
            throw new RuntimeException("No grading configuration found for this assessment.");
        }

        return gradingService.gradeByGradingId(grading.getGradingId(), submissionDto);
    }

    /**
     * Configure grading for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param gradingDto Grading configuration details.
     * @return Updated grading configuration as GradingDto.
     */
    @Override
    @Transactional
    public GradingDto configureGrading(Long assessmentId, GradingDto gradingDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Grading grading = gradingMapper.toEntity(gradingDto);
        grading.setAssessment(assessment);

        grading = gradingRepository.save(grading);
        assessment.setGrading(grading);

        assessmentRepository.save(assessment);
        return gradingMapper.toDto(grading);
    }

    /**
     * Retrieve grading configuration for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @return Grading configuration as GradingDto.
     */
    @Override
    @Transactional(readOnly = true)
    public GradingDto getGradingConfiguration(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        if (assessment.getGrading() == null) {
            throw new RuntimeException("No grading configuration found for this assessment.");
        }

        return gradingMapper.toDto(assessment.getGrading());
    }

    /**
     * Grade a student's submission for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param submissionDto Student's submission details.
     * @return Grading result as GradingResultDto.
     */
    @Override
    @Transactional
    public GradingResultDto gradeAssessmentSubmission(Long assessmentId, GradingSubmissionDto submissionDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Grading grading = assessment.getGrading();
        if (grading == null) {
            throw new RuntimeException("No grading configuration found for this assessment.");
        }

        return gradingService.gradeByGradingId(grading.getGradingId(), submissionDto);
    }

    /**
     * Fetch analytics for an assessment.
     *
     * @param assessmentId ID of the assessment.
     * @return Analytics data as GradingAnalyticsDto.
     */
    @Override
    @Transactional(readOnly = true)
    public AssessmentAnalyticsDto getAssessmentAnalytics(Long assessmentId) {
        // Step 1: Fetch the assessment
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Step 2: Fetch gradebook records associated with the assessment
        List<GradeBookRecord> records = gradeBookRecordRepository.findByGradeBookItem_AssessmentId(assessmentId);

        // Step 3: Calculate analytics
        Double averageScore = records.stream()
                .mapToDouble(record -> record.getScore() != null ? record.getScore() : 0.0)
                .average()
                .orElse(0.0);

        Long completedCount = records.stream()
                .filter(record -> record.getScore() != null)
                .count();

        // Step 4: Return analytics DTO
        return new AssessmentAnalyticsDto(
                assessment.getTitle(),
                averageScore,
                completedCount,
                assessment.getAllowedAttempts(),
                records.size()
        );
    }


    // Check Assessment Availability
    @Override
    @Transactional(readOnly = true)
    public void validateAssessmentAvailability(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Date now = new Date();
        if (now.before(assessment.getStartDate()) || now.after(assessment.getEndDate())) {
            throw new RuntimeException("Assessment is not currently available.");
        }
    }

    //Shuffle Questions
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getShuffledQuestions(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Map each Question to a QuestionDto
        List<QuestionDto> questions = assessment.getQuestions().stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());

        // Shuffle the questions if needed
        if (assessment.getShuffleQuestions()) {
            Collections.shuffle(questions);
        }

        return questions;
    }

    // Check Remaining Attempts
    @Override
    @Transactional(readOnly = true)
    public boolean validateRemainingAttempts(Long assessmentId, Long studentId) {
        // Fetch the assessment
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Fetch the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        // Count attempts
        long attemptCount = submissionRepository.countByAssessmentAndStudent(assessment, student);

        // Validate remaining attempts
        if (attemptCount >= assessment.getAllowedAttempts()) {
            throw new RuntimeException("No attempts left for this assessment.");
        }

        return true;
    }


    // Auto-Grade Assessment
    @Override
    @Transactional
    public GradingResultDto autoGradeAssessment(Long assessmentId, GradingSubmissionDto submissionDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        if (!assessment.getAutoGraded()) {
            throw new RuntimeException("This assessment is not configured for auto-grading.");
        }

        Grading grading = assessment.getGrading();
        return gradingService.gradeByGradingId(grading.getGradingId(), submissionDto);
    }

    //  Publish Assessment
    @Override
    @Transactional
    public void publishAssessment(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        assessment.setPublished(true);
        assessmentRepository.save(assessment);
    }

    //  Password Validation
    @Override
    @Transactional(readOnly = true)
    public void validateAssessmentPassword(Long assessmentId, String inputPassword) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        if (assessment.getRequiresPassword() && !assessment.getPassword().equals(inputPassword)) {
            throw new RuntimeException("Incorrect password for the assessment.");
        }
    }

    @Override
    @Transactional
    public void validateProctoring(Long assessmentId, Long studentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        if (assessment.getProctored()) {
            Optional<ProctoringLog> proctorLog = proctoringLogRepository.findActiveLog(assessmentId, studentId);

            if (proctorLog.isEmpty()) {
                throw new RuntimeException("No active proctoring session found for this assessment.");
            }

            if (!"APPROVED".equals(proctorLog.get().getStatus())) {
                throw new RuntimeException("Proctoring session not approved for this student.");
            }
        }
    }


    //
    @Override
    @Transactional
    public void startAssessment(Long assessmentId, Long studentId) {
        // Step 1: Validate assessment existence
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Step 2: Validate proctoring if required
        if (Boolean.TRUE.equals(assessment.getProctored())) {
            validateProctoring(assessmentId, studentId);
        }

        // Step 3: Validate remaining attempts for the student
        if (!validateRemainingAttempts(assessmentId, studentId)) {
            throw new RuntimeException("Student has no remaining attempts for this assessment.");
        }

        // Step 4: Check if the assessment is active
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(convertToLocalDateTime(assessment.getStartDate())) ||
                now.isAfter(convertToLocalDateTime(assessment.getEndDate()))) {
            throw new RuntimeException("Assessment is not currently active.");
        }

        // Step 5: Initialize a submission for the student
        Submission submission = new Submission();
        submission.setAssessment(assessment);
        submission.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId)).getUser());
        submission.setStartTime(now);
        submission.setStatus(SubmissionStatus.IN_PROGRESS);

        // Step 6: Save the submission
        submissionRepository.save(submission);

        // Step 7: Notify the student about the assessment start
        String notificationMessage = generateNotificationMessage(assessment);
        notificationService.createAndSendNotification(
                studentId,
                "You have started the " + assessment.getType().name().toLowerCase() + ": " + assessment.getTitle(),
                assessmentId,
                RelatedEntityType.ASSESSMENT,
                notificationMessage
        );

    }

    /**
     * Utility method to convert java.util.Date to java.time.LocalDateTime.
     *
     * @param date The Date to be converted.
     * @return LocalDateTime equivalent of the Date.
     */
    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // Dynamic notification After an Assessment begin
    private String generateNotificationMessage(Assessment assessment) {
        return switch (assessment.getType()) {
            case EXAM ->
                    "You have started your exam. Make sure to manage your time wisely and stay focused. Good luck!";
            case QUIZ -> "You have started your quiz. Answer the questions carefully and do your best. Good luck!";
            default -> "You have started the assessment. Best of luck!";
        };
    }


//    private String generateNotificationMessage(Assessment assessment) {
//        switch (assessment.getType()) {
//            case EXAM:
//                return "You have started your exam. Make sure to manage your time wisely and stay focused. Good luck!";
//            case QUIZ:
//                return "You have started your quiz. Answer the questions carefully and do your best. Good luck!";
//            default:
//                return "You have started the assessment. Best of luck!";
//        }
//    }

    // Implement later
    public boolean validateAccessibility(Long assessmentId, Long userId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found."));

        if (assessment.getAccommodations() != null) {
            // Custom logic to apply accommodations for the user.
            return true;
        }
        return false;
    }


    @Override
    @Transactional(readOnly = true)
    public AssessmentPreviewDto previewAssessment(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found."));

        // Map each Question to a QuestionDto using the mapper
        List<QuestionDto> questions = assessment.getQuestions().stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());

        return new AssessmentPreviewDto(
                assessment.getTitle(),
                assessment.getDescription(),
                assessment.getType(),
                questions
        );
    }

    @Override
    @Transactional
    public void startProctoredAssessment(Long assessmentId, Long studentId, Long proctoringSessionId) {
        // Step 1: Validate the proctoring session
        proctoringService.monitorProctoringSession(proctoringSessionId);

        // Step 2: Fetch the assessment
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Step 3: Validate remaining attempts for the student
        if (!validateRemainingAttempts(assessmentId, studentId)) {
            throw new RuntimeException("Student has no remaining attempts for this assessment.");
        }

        // Step 4: Check if the assessment is currently active
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(assessment.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) ||
                now.isAfter(assessment.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
            throw new RuntimeException("Assessment is not currently active.");
        }

        // Step 5: Initialize a submission for the student
        Submission submission = new Submission();
        submission.setAssessment(assessment);
        submission.setStudent(studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId)).getUser());
        submission.setStartTime(LocalDateTime.now());
        submission.setStatus(SubmissionStatus.IN_PROGRESS);

        // Step 6: Save the submission
        submissionRepository.save(submission);

        // Step 7: Notify the student
        notificationService.createAndSendNotification(
                studentId,
                "Your proctored assessment: " + assessment.getTitle() + " has started.",
                assessmentId,
                RelatedEntityType.ASSESSMENT,
                "Good luck on your proctored assessment!"
        );
    }

    // QUESTION RELATED METHODS
    @Override
    @Transactional
    public List<QuestionDto> addQuestionsToAssessment(Long assessmentId, List<QuestionDto> questionDtos) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        List<Question> questions = questionDtos.stream()
                .map(questionMapper::toEntity)
                .peek(question -> question.setAssessment(assessment))
                .collect(Collectors.toList());

        questionRepository.saveAll(questions);

        return questions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> generateAssessmentQuestions(Long assessmentId) {
        // Fetch the assessment entity
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        // Validate that pooling criteria exists
        if (assessment.getPoolingCriteria() == null || assessment.getPoolingCriteria().isEmpty()) {
            throw new RuntimeException("No pooling criteria defined for this assessment.");
        }

        try {
            // Parse pooling criteria
            ObjectMapper objectMapper = new ObjectMapper();
            QuestionFilterCriteria filterCriteria = objectMapper.readValue(
                    assessment.getPoolingCriteria(),
                    QuestionFilterCriteria.class
            );

            // Fetch questions using extracted criteria fields
            List<Question> questions = questionService.findQuestionsByCriteria(filterCriteria);

//            List<Question> questions = questionRepository.findQuestionsByCriteria(
//                    filterCriteria.getQuestionText(),
//                    filterCriteria.getQuestionType(),
//                    filterCriteria.getDifficulty(),
//                    filterCriteria.getAssessmentId(),
//                    filterCriteria.getQuestionBankId(),
//                    filterCriteria.getIsRandomized()
//            );

            // Map to DTOs
            return questions.stream()
                    .map(questionMapper::toDto)
                    .collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse pooling criteria for assessment ID: " + assessmentId, e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionsForAssessment(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        List<Question> questions = assessment.getQuestions();

        // Map each Question to a QuestionDto
        return questions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }


    // Service Method
    @Override
    @Transactional
    public void removeQuestionFromAssessment(Long assessmentId, Long questionId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionId));

        if (!assessment.getQuestions().remove(question)) {
            throw new RuntimeException("Question is not part of this assessment.");
        }

        assessmentRepository.save(assessment); // Persist changes
    }


    // Service Method
    @Override
    @Transactional
    public QuestionDto updateQuestionInAssessment(Long assessmentId, QuestionDto questionDto) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Question existingQuestion = questionRepository.findById(questionDto.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " + questionDto.getQuestionId()));

        if (!assessment.getQuestions().contains(existingQuestion)) {
            throw new RuntimeException("Question is not part of this assessment.");
        }

        // Update the entity using the QuestionMapper
        Question updatedQuestion = questionMapper.toEntity(questionDto);
        updatedQuestion.setAssessment(assessment); // Ensure the assessment link is maintained
        questionRepository.save(updatedQuestion);

        return questionMapper.toDto(updatedQuestion);
    }

    // Service Method
    @Override
    @Transactional
    public List<QuestionDto> shuffleQuestionsForAssessment(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        List<Question> questions = new ArrayList<>(assessment.getQuestions());
        Collections.shuffle(questions);

        assessment.setQuestions(questions); // Update order in-memory
        assessmentRepository.save(assessment);

        // Map each Question to a QuestionDto using the toDto method
        return questions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }


    // Service Method
    @Override
    @Transactional(readOnly = true)
    public boolean validatePoolingCriteria(Long assessmentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        String criteria = assessment.getPoolingCriteria();
        if (criteria == null || criteria.isEmpty()) {
            return false;
        }

        try {
            QuestionFilterCriteria filterCriteria = objectMapper.readValue(criteria, QuestionFilterCriteria.class);
            long matchingQuestions = questionService.countQuestionsByCriteria(filterCriteria);
            return matchingQuestions > 0;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse pooling criteria for assessment ID: " + assessmentId, e);
        }
    }


    // Service Method
    @Override
    @Transactional(readOnly = true)
    public List<QuestionDto> filterQuestionsForAssessment(Long assessmentId, QuestionFilterCriteria criteria) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        criteria.setAssessmentId(assessmentId); // Ensure filtering is scoped to this assessment
        List<Question> questions = questionService.findQuestionsByCriteria(criteria);

        // Map each Question to a QuestionDto using the toDto method
        return questions.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }

    // Service Method
    @Override
    @Transactional
    public void setQuestionOrder(Long assessmentId, List<Long> questionIdsInOrder) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        List<Question> questions = assessment.getQuestions();
        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getQuestionId, q -> q));

        List<Question> orderedQuestions = new ArrayList<>();
        for (Long questionId : questionIdsInOrder) {
            Question question = questionMap.get(questionId);
            if (question != null) {
                orderedQuestions.add(question);
            }
        }

        assessment.setQuestions(orderedQuestions);
        assessmentRepository.save(assessment);
    }


}