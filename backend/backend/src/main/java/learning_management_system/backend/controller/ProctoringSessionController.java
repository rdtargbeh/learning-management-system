package learning_management_system.backend.controller;

import learning_management_system.backend.entity.ProctoringSession;
import learning_management_system.backend.enums.ProctoringStatus;
import learning_management_system.backend.service.ProctoringSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController

@RequestMapping("/api/proctoring-sessions")
public class ProctoringSessionController {

    @Autowired
    private ProctoringSessionService proctoringSessionService;


    /**
     * Create a new proctoring session.
     *
     * @param assessmentId ID of the assessment.
     * @param studentId    ID of the student.
     * @return Created ProctoringSession.
     */
    @PostMapping("/create")
    public ResponseEntity<ProctoringSession> createProctoringSession(
            @RequestParam Long assessmentId,
            @RequestParam Long studentId) {

        ProctoringSession session = proctoringSessionService.createProctoringSession(assessmentId, studentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    /**
     * Update the status of an existing proctoring session.
     *
     * @param sessionId ID of the proctoring session.
     * @param status    New status for the session.
     * @return Response indicating success.
     */
    @PatchMapping("/{sessionId}/update-status")
    public ResponseEntity<Void> updateProctoringStatus(
            @PathVariable Long sessionId,
            @RequestParam ProctoringStatus status) {

        proctoringSessionService.updateProctoringStatus(sessionId, status);
        return ResponseEntity.ok().build();
    }

    /**
     * Find active proctoring log for a student and assessment.
     *
     * @param assessmentId ID of the assessment.
     * @param studentId    ID of the student.
     * @return Active ProctoringSession if found.
     */
    @GetMapping("/active-log")
    public ResponseEntity<ProctoringSession> findActiveProctoringLog(
            @RequestParam Long assessmentId,
            @RequestParam Long studentId) {

        Optional<ProctoringSession> session = proctoringSessionService.findActiveLog(assessmentId, studentId);

        if (session.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(session.get());
    }


    /**
     * Retrieve a proctoring session by ID.
     *
     * @param sessionId ID of the proctoring session.
     * @return The ProctoringSession entity.
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<ProctoringSession> getProctoringSessionById(@PathVariable Long sessionId) {
        ProctoringSession session = proctoringSessionService.getProctoringSessionById(sessionId);
        return ResponseEntity.ok(session);
    }

}

