package learning_management_system.backend.service.implement;

import learning_management_system.backend.entity.Assessment;
import learning_management_system.backend.entity.ProctoringLog;
import learning_management_system.backend.entity.ProctoringSession;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.repository.AssessmentRepository;
import learning_management_system.backend.repository.ProctoringLogRepository;
import learning_management_system.backend.repository.ProctoringSessionRepository;
import learning_management_system.backend.repository.StudentRepository;
import learning_management_system.backend.service.ActivityLogsService;
import learning_management_system.backend.service.ProctoringService;
import learning_management_system.backend.utility.ThirdPartyProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class ProctoringServiceImplementation implements ProctoringService {

    @Autowired
    private ProctoringLogRepository proctoringLogRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ActivityLogsService activityLogsService;
    @Autowired
    private ProctoringSessionRepository proctoringSessionRepository;
    @Autowired
    private ThirdPartyProctoringService thirdPartyProctoringService;


    @Override
    @Transactional
    public ProctoringLog startProctoringSession(Long assessmentId, Long studentId) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        ProctoringLog log = new ProctoringLog();
        log.setAssessment(assessment);
        log.setStudent(student);
        log.setStatus("PENDING");
        log.setStartTime(new Date());

        return proctoringLogRepository.save(log);
    }

    @Override
    @Transactional
    public void approveProctoringSession(Long assessmentId, Long studentId) {
        ProctoringLog log = proctoringLogRepository.findActiveLog(assessmentId, studentId)
                .orElseThrow(() -> new RuntimeException("No active proctoring session found."));

        log.setStatus("APPROVED");
        proctoringLogRepository.save(log);
    }

    @Override
    @Transactional
    public void endProctoringSession(Long assessmentId, Long studentId, boolean success, String violations) {
        ProctoringLog log = proctoringLogRepository.findActiveLog(assessmentId, studentId)
                .orElseThrow(() -> new RuntimeException("No active proctoring session found."));

        log.setEndTime(new Date());
        log.setSuccess(success);
        log.setViolations(violations);
        proctoringLogRepository.save(log);
    }

    @Override
    @Transactional
    public void monitorProctoringSession(Long proctoringSessionId) {
        // Fetch the proctoring session
        ProctoringSession session = proctoringSessionRepository.findById(proctoringSessionId)
                .orElseThrow(() -> new RuntimeException("Proctoring session not found."));

        // Integrate with third-party APIs for validation
        boolean isValid = thirdPartyProctoringService.validateSession(session);
        if (!isValid) {
            // Log the invalid session
            activityLogsService.logAction(
                    session.getStudent().getUser(),
                    ActionCategory.PROCTORING,
                    ActionType.VALIDATION_FAILURE,
                    "ProctoringSession",
                    proctoringSessionId,
                    false,
                    null,
                    Map.of("reason", "Third-party validation failed")
            );

            throw new RuntimeException("Proctoring session invalid.");
        }

        // Log successful validation
        activityLogsService.logAction(
                session.getStudent().getUser(),
                ActionCategory.PROCTORING,
                ActionType.VALIDATION_SUCCESS,
                "ProctoringSession",
                proctoringSessionId,
                true,
                null,
                Map.of("status", "Valid session")
        );
    }

}
