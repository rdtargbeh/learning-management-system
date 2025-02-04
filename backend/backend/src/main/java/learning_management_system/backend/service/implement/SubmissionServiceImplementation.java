package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.ProctoringDetailsDto;
import learning_management_system.backend.dto.ProctoringLogDto;
import learning_management_system.backend.dto.SubmissionDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.GradingStatus;
import learning_management_system.backend.enums.SubmissionType;
import learning_management_system.backend.mapper.SubmissionMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.SubmissionService;
import learning_management_system.backend.utility.PerformanceInsightsDto;
import learning_management_system.backend.utility.SubmissionGradingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Submission entities.
 */
@Service
public class SubmissionServiceImplementation implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private SubmissionMapper submissionMapper;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Lazy
    @Autowired
    private LmsNotificationRepository notificationRepository;
    @Autowired
    private ProctoringSessionRepository proctoringSessionRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private ProctoringLogRepository proctoringLogRepository;



    @Override
    @Transactional
    public SubmissionDto createSubmission(SubmissionDto submissionDto) {
        // Step 1: Validate mandatory fields
        if (submissionDto.getStudentId() == null || submissionDto.getStartTime() == null) {
            throw new IllegalArgumentException("Student ID and Start Time are mandatory.");
        }
        // Step 2: Validate referenced entities
        studentRepository.findById(submissionDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID: " + submissionDto.getStudentId()));

        if (submissionDto.getAssignmentId() != null) {
            assignmentRepository.findById(submissionDto.getAssignmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid assignment ID: " + submissionDto.getAssignmentId()));
        }
        Assessment assessment = null;
        if (submissionDto.getAssessmentId() != null) {
            assessment = assessmentRepository.findById(submissionDto.getAssessmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid assessment ID: " + submissionDto.getAssessmentId()));
            // Step 3: Validate submission constraints
            if (!isSubmissionWithinWindow(
                    submissionDto.getStartTime(),
                    submissionDto.getEndTime(),
                    assessment.getStartDate(),
                    assessment.getEndDate())) {
                throw new IllegalArgumentException("Submission is outside the assessment's availability window.");
            }
            if (!isSubmissionWithinAllowedAttempts(submissionDto.getStudentId(), submissionDto.getAssessmentId())) {
                throw new IllegalArgumentException("Submission exceeds allowed attempts.");
            }
        }
        // Step 4: Check for duplicate submissions
        if (submissionRepository.existsByStudent_UserIdAndAssessment_AssessmentId(
                submissionDto.getStudentId(), submissionDto.getAssessmentId())) {
            throw new IllegalArgumentException("Duplicate submission detected for the same student and assessment.");
        }
        // Step 5: Initialize missing fields
        Submission submission = submissionMapper.toEntity(submissionDto);
        submission.setSubmissionDate(new Date());
        submission.setGradingStatus(GradingStatus.PENDING); // Default grading status
        submission.setTimeTaken(calculateTimeTaken(submission.getStartTime(), submission.getEndTime()));

        // Step 6: Save the submission
        Submission savedSubmission = submissionRepository.save(submission);
        // Step 7: Return the saved submission as DTO
        return submissionMapper.toDto(savedSubmission);
    }
    // Helper to calculate time taken
    private Long calculateTimeTaken(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null && endTime != null) {
            return Duration.between(startTime, endTime).toSeconds();
        }
        return null;
    }

    @Override
    @Transactional
    public SubmissionDto updateSubmission(Long submissionId, SubmissionDto submissionDto) {
        // Step 1: Fetch the existing submission
        Submission existingSubmission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Step 2: Validate if the submission is already finalized
        if (existingSubmission.getFinalized()) {
            throw new IllegalStateException("Cannot update a finalized submission.");
        }
        // Step 3: Update selectively
        if (submissionDto.getMarksObtained() != null) {
            existingSubmission.setMarksObtained(submissionDto.getMarksObtained());
        }
        if (submissionDto.getFeedback() != null) {
            existingSubmission.setFeedback(submissionDto.getFeedback());
        }
        if (submissionDto.getGradingStatus() != null) {
            existingSubmission.setGradingStatus(GradingStatus.valueOf(submissionDto.getGradingStatus()));
        }
        if (submissionDto.getEndTime() != null) {
            existingSubmission.setEndTime(submissionDto.getEndTime());
            existingSubmission.setTimeTaken(calculateTimeTaken(existingSubmission.getStartTime(), submissionDto.getEndTime()));
        }
        // Step 4: Save and return
        Submission updatedSubmission = submissionRepository.save(existingSubmission);
        return submissionMapper.toDto(updatedSubmission);
    }

    @Override
    public Page<SubmissionDto> getSubmissionsByStudent(Long studentId, Pageable pageable) {
        // Validate that the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        // Fetch submissions and map to DTOs
        return submissionRepository.findByStudent_UserId(studentId, pageable)
                .map(submissionMapper::toDto);
    }

    @Override
    public List<SubmissionDto> getSubmissionsByAssignment(Long assignmentId) {
        // Validate that the assignment exists
        assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + assignmentId));

        // Fetch submissions and map to DTOs
        return submissionRepository.findByAssignment_AssignmentId(assignmentId).stream()
                .map(submissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageMarksByAssessment(Long assessmentId) {
        // Validate that the assessment exists
        assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assessment not found with ID: " + assessmentId));

        // Fetch the average marks
        return submissionRepository.findAverageMarksByAssessmentId(assessmentId);
    }


    @Override
    public List<SubmissionDto> getFlaggedSubmissions() {
        // Fetch flagged submissions and map to DTOs
        List<Submission> flaggedSubmissions = submissionRepository.findFlaggedSubmissions();

        // Validate if there are flagged submissions
        if (flaggedSubmissions.isEmpty()) {
            throw new IllegalStateException("No flagged submissions found.");
        }

        return flaggedSubmissions.stream()
                .map(submissionMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public SubmissionDto getLatestSubmission(Long studentId, Long entityId, SubmissionType type) {
        // Validate that the student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        Submission submission;

        if (type == SubmissionType.ASSESSMENT) {
            // Validate that the assessment exists
            assessmentRepository.findById(entityId)
                    .orElseThrow(() -> new IllegalArgumentException("Assessment not found with ID: " + entityId));

            // Fetch the latest submission for the assessment
            submission = submissionRepository.findLatestSubmissionByStudentAndAssessment(studentId, entityId);

        } else if (type == SubmissionType.ASSIGNMENT) {
            // Validate that the assignment exists
            assignmentRepository.findById(entityId)
                    .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + entityId));

            // Fetch the latest submission for the assignment
            submission = submissionRepository.findLatestSubmissionByStudentAndAssignment(studentId, entityId);

        } else {
            throw new IllegalArgumentException("Invalid submission type. Expected ASSESSMENT or ASSIGNMENT.");
        }

        // Validate that a submission exists
        if (submission == null) {
            throw new IllegalArgumentException(
                    String.format("No submissions found for the specified student and %s.", type.name().toLowerCase()));
        }

        return submissionMapper.toDto(submission);
    }


    @Override
    public void deleteSubmission(Long submissionId) {
        // Step 1: Fetch the submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Step 2: Check if the submission is finalized
        if (submission.getFinalized()) {
            throw new IllegalStateException("Cannot delete a finalized submission.");
        }
        // Step 3: Check if the submission is linked to an assessment or assignment
        if (submission.getAssessment() != null) {
            throw new IllegalStateException("Cannot delete a submission linked to an assessment.");
        }
        if (submission.getAssignment() != null) {
            throw new IllegalStateException("Cannot delete a submission linked to an assignment.");
        }
        // Step 4: Perform deletion
        submissionRepository.deleteById(submissionId);
    }


    @Override
    public void validateSubmission(Long submissionId) {
        // Fetch the submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Ensure the submission is linked to an assessment
        Assessment assessment = submission.getAssessment();
        if (assessment == null) {
            throw new IllegalArgumentException("Submission is not linked to any assessment.");
        }
        // Validate the submission window
        if (!isSubmissionWithinWindow(
                submission.getStartTime(),
                submission.getEndTime(),
                assessment.getStartDate(),
                assessment.getEndDate())) {
            throw new IllegalArgumentException("Submission is outside the assessment's availability window.");
        }
        // Validate time limit
        if (isSubmissionExceedingTimeLimit(
                submission.getStartTime(),
                submission.getEndTime(),
                assessment.getTimeLimit())) {
            throw new IllegalArgumentException("Submission exceeds the allowed time limit.");
        }
        // Validate allowed attempts
        if (!isSubmissionWithinAllowedAttempts(
                submission.getStudent().getUserId(),
                assessment.getAssessmentId())) {
            throw new IllegalArgumentException("Submission exceeds the allowed attempts.");
        }
    }


    @Override
    public boolean isSubmissionLate(Long submissionId, SubmissionType type) {
        // Fetch the submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        LocalDateTime startTime = submission.getStartTime();
        LocalDateTime endTime = submission.getEndTime();

        if (type == SubmissionType.ASSIGNMENT) {
            Assignment assignment = submission.getAssignment();
            if (assignment == null) {
                throw new IllegalArgumentException("Submission is not linked to any assignment.");
            }
            LocalDateTime availableUntil = assignment.getAvailableUntil();
            if (availableUntil == null) {
                throw new IllegalArgumentException("Assignment's availableUntil is not set.");
            }
            // Late if submission end time is after availableUntil
            return endTime != null && endTime.isAfter(availableUntil);
        } else if (type == SubmissionType.ASSESSMENT) {
            Assessment assessment = submission.getAssessment();
            if (assessment == null) {
                throw new IllegalArgumentException("Submission is not linked to any assessment.");
            }
            LocalDateTime assessmentEnd = assessment.getEndDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            // Late if submission end time is after assessmentEnd
            return endTime != null && endTime.isAfter(assessmentEnd);
        } else {
            throw new IllegalArgumentException("Unsupported submission type.");
        }
    }

    /** Check if Submission is Within Availability Window */
    private boolean isSubmissionWithinWindow(LocalDateTime startTime, LocalDateTime endTime, Date assessmentStartDate, Date assessmentEndDate) {
        if (startTime == null) {
            throw new IllegalArgumentException("Submission start time is required.");
        }

        // Convert assessment start and end dates to LocalDateTime
        LocalDateTime assessmentStart = assessmentStartDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime assessmentEnd = assessmentEndDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Validate the submission start time
        if (startTime.isBefore(assessmentStart)) {
            throw new IllegalArgumentException("Submission start time is before the assessment's allowed start date.");
        }
        if (startTime.isAfter(assessmentEnd)) {
            throw new IllegalArgumentException("Submission start time is after the assessment's allowed end date.");
        }
        // Validate the submission end time (if provided)
        if (endTime != null && endTime.isAfter(assessmentEnd)) {
            throw new IllegalArgumentException("Submission end time exceeds the assessment's allowed end date.");
        }
        // If all checks pass, the submission is within the window
        return true;
    }



    /** Check if Submission Exceeds Time Limit*/
    private boolean isSubmissionExceedingTimeLimit(LocalDateTime startTime, LocalDateTime endTime, Integer timeLimitMinutes) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Both start time and end time are required for time limit validation.");
        }
        // Calculate the time taken in minutes
        long timeTakenMinutes = Duration.between(startTime, endTime).toMinutes();
        // Validate against the time limit
        return timeLimitMinutes != null && timeTakenMinutes > timeLimitMinutes;
    }



    //  Validate Submission Attempts
    private boolean isSubmissionWithinAllowedAttempts(Long studentId, Long assessmentId) {
        long submissionCount = submissionRepository.countByStudent_UserIdAndAssessment_AssessmentId(studentId, assessmentId);

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assessment not found with ID: " + assessmentId));

        return submissionCount < assessment.getAllowedAttempts();
    }

    // Calculate max attempt allowed and set submission to final
    @Override
    @Transactional
    public void finalizeSubmissionIfMaxAttemptsReached(Long submissionId) {
        // Fetch the submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Calculate the maximum allowed attempts (initial attempt + retries)
        int maxAttempts = submission.getRetryCount() + 1;

        // Check if the submission has reached the maximum attempts
        if (submission.getAttemptNumber() >= maxAttempts) {
            submission.setFinalized(true); // Mark the submission as finalized
            submissionRepository.save(submission); // Persist the change
        }
    }


    // Update grading-related fields in the Submission.
    @Override
    @Transactional
    public void setGradingDetails(Long submissionId, SubmissionGradingDto gradingDto) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setFeedback(gradingDto.getFeedback());
        submission.setRubricDetails(gradingDto.getRubricDetails());
        submission.setGradeComments(gradingDto.getGradeComments());
        submission.setGradeLevel(gradingDto.getGradeLevel());

        submissionRepository.save(submission);
    }

    // Retrieves grading details for a submission
    @Override
    @Transactional(readOnly = true)
    public SubmissionGradingDto getGradingSummary(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        SubmissionGradingDto gradingDto = new SubmissionGradingDto();
        gradingDto.setFeedback(submission.getFeedback());
        gradingDto.setRubricDetails(submission.getRubricDetails());
        gradingDto.setGradeComments(submission.getGradeComments());
        gradingDto.setGradeLevel(submission.getGradeLevel());

        return gradingDto;
    }

    @Override
    @Transactional
    public void analyzeSubmissionPerformance(Long submissionId, String timePerQuestion, String scoreDistribution) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setTimePerQuestion(timePerQuestion);
        submission.setScoreDistribution(scoreDistribution);

        submissionRepository.save(submission);
    }


    @Override
    @Transactional
    public void setPlagiarismDetails(Long submissionId, Double plagiarismScore, String analysisReportUrl,
                                     Boolean isFlaggedForPlagiarism) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setPlagiarismScore(plagiarismScore);
        submission.setAnalysisReportUrl(analysisReportUrl);
        submission.setFlaggedForPlagiarism(isFlaggedForPlagiarism);

        submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public void markAsReviewed(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setReviewed(true);
        submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public void setAutoGradedStatus(Long submissionId, Boolean autoGraded) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setAutoGraded(autoGraded);
        submissionRepository.save(submission);
    }

    @Override
    @Transactional
    public void linkProctoringSession(Long submissionId, Long proctoringSessionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        ProctoringSession proctoringSession = proctoringSessionRepository.findById(proctoringSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Proctoring session not found with ID: " + proctoringSessionId));

        submission.setProctoringSession(proctoringSession);
        submissionRepository.save(submission);
    }


    @Override
    @Transactional
    public void addAttachments(Long submissionId, List<AttachmentDto> attachmentDtos) {
        // Validate the submission exists
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Map DTOs to Attachment entities
        List<Attachment> attachments = attachmentDtos.stream()
                .map(dto -> {
                    Attachment attachment = new Attachment();
                    attachment.setFileName(dto.getFileName());
                    attachment.setStorageUrl(dto.getStorageUrl());
                    attachment.setFileSize(dto.getFileSize());
                    attachment.setLinkedEntityId(submissionId); // Link to the submission
                    attachment.setLinkedEntityType("SUBMISSION"); // Specify the entity type
                    return attachment;
                })
                .collect(Collectors.toList());

        // Persist the attachments
        attachmentRepository.saveAll(attachments);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttachmentDto> getAttachments(Long submissionId) {
        // Validate the submission exists
        submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Fetch attachments linked to the submission
        List<Attachment> attachments = attachmentRepository.findByLinkedEntityIdAndLinkedEntityType( submissionId, "SUBMISSION");

        // Map attachments to DTOs
        return attachments.stream()
                .map(attachment -> {
                    AttachmentDto dto = new AttachmentDto();
                    dto.setFileName(attachment.getFileName());
                    dto.setStorageUrl(attachment.getStorageUrl());
                    dto.setFileSize(attachment.getFileSize());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    // Send notifications after a submission is graded or finalized
    @Transactional
    public void notifySubmissionStatus(Long submissionId, String message) {
        // Fetch submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Notify the student
        LmsNotification studentNotification = new LmsNotification();
        studentNotification.setRelatedEntityId(submissionId);
        studentNotification.setRelatedEntityType("SUBMISSION");
        studentNotification.setRecipientUser(submission.getStudent());
        studentNotification.setMessage(message);
        notificationRepository.save(studentNotification);

        // Notify the teacher if the submission is linked to an assessment
        if (submission.getAssessment() != null) {
            User teacher = submission.getAssessment().getCreatedBy(); // Ensure teacher is properly linked
            LmsNotification teacherNotification = new LmsNotification();
            teacherNotification.setRelatedEntityId(submissionId);
            teacherNotification.setRelatedEntityType("SUBMISSION");
            teacherNotification.setRecipientUser(teacher);
            teacherNotification.setMessage(message);
            notificationRepository.save(teacherNotification);
        }
    }

    // Fetch Plagiarism Details for a Submission
    @Transactional(readOnly = true)
    public Map<String, Object> getPlagiarismDetails(Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Return plagiarism details in a simple map
        Map<String, Object> plagiarismDetails = new HashMap<>();
        plagiarismDetails.put("plagiarismScore", submission.getPlagiarismScore());
        plagiarismDetails.put("isFlaggedForPlagiarism", submission.getFlaggedForPlagiarism());

        return plagiarismDetails;
    }

    // Notify Based on Plagiarism Detection
    @Transactional
    public void notifyPlagiarism(Long submissionId, String message, Double plagiarismThreshold) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Check if plagiarism exceeds the threshold or is flagged
        if (submission.getPlagiarismScore() != null && submission.getPlagiarismScore() > plagiarismThreshold
                || Boolean.TRUE.equals(submission.getFlaggedForPlagiarism())) {

            // Notify the student
            LmsNotification studentNotification = new LmsNotification();
            studentNotification.setRelatedEntityId(submissionId);
            studentNotification.setRelatedEntityType("SUBMISSION");
            studentNotification.setRecipientUser(submission.getStudent());
            studentNotification.setMessage("Plagiarism detected in your submission: " + message);
            notificationRepository.save(studentNotification);

            // Notify the teacher if linked to an assessment
            if (submission.getAssessment() != null) {
                LmsNotification teacherNotification = new LmsNotification();
                teacherNotification.setRelatedEntityId(submissionId);
                teacherNotification.setRelatedEntityType("SUBMISSION");
                teacherNotification.setRecipientUser(submission.getAssessment().getCreatedBy());
                teacherNotification.setMessage("Plagiarism detected in a submission: " + message);
                notificationRepository.save(teacherNotification);
            }
        }
    }


    @Override
    @Transactional(readOnly = true)
    public ProctoringDetailsDto getProctoringDetails(Long submissionId) {
        // Fetch the submission
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Fetch the linked proctoring session
        ProctoringSession session = submission.getProctoringSession();
        if (session == null) {
            throw new IllegalArgumentException("No proctoring session linked to this submission.");
        }

        // Fetch logs linked to the session (if applicable)
        List<ProctoringLog> logs = proctoringLogRepository.findByStudent_StudentIdAndAssessment_AssessmentId(
                submission.getStudent().getUserId(),
                session.getAssessment().getAssessmentId()
        );

        // Map logs to DTOs
        List<ProctoringLogDto> logDtos = logs.stream()
                .map(log -> new ProctoringLogDto(
                        log.getStatus(),
                        log.getViolations(),
                        log.getStartTime(),
                        log.getEndTime(),
                        log.getSuccess()
                ))
                .collect(Collectors.toList());

        // Build and return the ProctoringDetailsDto
        return new ProctoringDetailsDto(
                session.getStatus().name(),
                session.getStartTime(),
                session.getEndTime(),
                session.getMetadata(),
                logDtos
        );
    }


    // Update Plagiarism Details for a Submission
    //If you need to update these fields programmatically (e.g., based on a plagiarism detection API):
    @Override
    @Transactional
    public void updatePlagiarismDetails(Long submissionId, Double plagiarismScore, Boolean isFlagged) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        submission.setPlagiarismScore(plagiarismScore);
        submission.setFlaggedForPlagiarism(isFlagged);

        submissionRepository.save(submission);
    }


    @Transactional(readOnly = true)
    public PerformanceInsightsDto getSubmissionInsights(Long submissionId) {
        // Fetch the submission by ID
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with ID: " + submissionId));

        // Return a DTO containing performance insights
        return new PerformanceInsightsDto(
                submission.getTimePerQuestion(),  // JSON analytics for time per question
                submission.getScoreDistribution() // JSON trends for score distribution
        );
    }



}
