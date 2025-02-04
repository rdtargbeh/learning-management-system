package learning_management_system.backend.controller;

import learning_management_system.backend.entity.ProctoringLog;
import learning_management_system.backend.service.ProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proctoring")
public class ProctoringController {

    @Autowired
    private ProctoringService proctoringService;

    @PostMapping("/start")
    public ResponseEntity<ProctoringLog> startProctoringSession(@RequestParam Long assessmentId, @RequestParam Long studentId) {
        ProctoringLog log = proctoringService.startProctoringSession(assessmentId, studentId);
        return ResponseEntity.ok(log);
    }

    @PatchMapping("/approve")
    public ResponseEntity<Void> approveProctoringSession(@RequestParam Long assessmentId, @RequestParam Long studentId) {
        proctoringService.approveProctoringSession(assessmentId, studentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/end")
    public ResponseEntity<Void> endProctoringSession(@RequestParam Long assessmentId, @RequestParam Long studentId,
                                                     @RequestParam boolean success, @RequestBody String violations) {
        proctoringService.endProctoringSession(assessmentId, studentId, success, violations);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to monitor a proctoring session.
     *
     * @param proctoringSessionId The ID of the proctoring session to monitor.
     * @return ResponseEntity indicating the monitoring result.
     */
    @PostMapping("/{proctoringSessionId}/monitor")
    public ResponseEntity<String> monitorProctoringSession(@PathVariable Long proctoringSessionId) {
        try {
            proctoringService.monitorProctoringSession(proctoringSessionId);
            return ResponseEntity.ok("Proctoring session monitoring successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Proctoring session monitoring failed: " + e.getMessage());
        }
    }
}

