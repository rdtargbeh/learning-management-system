package learning_management_system.backend.service;

import learning_management_system.backend.entity.Assessment;
import learning_management_system.backend.entity.ProctoringSession;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.enums.ProctoringStatus;
import learning_management_system.backend.repository.AssessmentRepository;
import learning_management_system.backend.repository.ProctoringSessionRepository;
import learning_management_system.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ProctoringSessionService {

    @Autowired
    private ProctoringSessionRepository proctoringSessionRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private StudentRepository studentRepository;


    public ProctoringSession createProctoringSession(Long assessmentId, Long studentId) {
        ProctoringSession session = new ProctoringSession();

        // Fetch the Assessment entity
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));
        session.setAssessment(assessment);

        // Fetch the Student entity
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        session.setStudent(student);

        session.setStatus(ProctoringStatus.INITIATED);
        session.setStartTime(new Date());

        return proctoringSessionRepository.save(session);
    }


    public void updateProctoringStatus(Long sessionId, ProctoringStatus status) {
        ProctoringSession session = proctoringSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Proctoring session not found with ID: " + sessionId));
        session.setStatus(status);
        proctoringSessionRepository.save(session);
    }

    public Optional<ProctoringSession> findActiveLog(Long assessmentId, Long studentId) {
        return proctoringSessionRepository.findActiveLog(assessmentId, studentId);
    }

    /**
     * Retrieves a ProctoringSession by its ID.
     *
     * @param sessionId The ID of the proctoring session.
     * @return The ProctoringSession entity.
     * @throws RuntimeException if the session is not found.
     */
    @Transactional(readOnly = true)
    public ProctoringSession getProctoringSessionById(Long sessionId) {
        return proctoringSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Proctoring session not found with ID: " + sessionId));
    }
}
