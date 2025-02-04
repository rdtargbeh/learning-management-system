package learning_management_system.backend.service;

import learning_management_system.backend.entity.ProctoringLog;

public interface ProctoringService {

    ProctoringLog startProctoringSession(Long assessmentId, Long studentId);

    void approveProctoringSession(Long assessmentId, Long studentId);

    void endProctoringSession(Long assessmentId, Long studentId, boolean success, String violations);

    void monitorProctoringSession(Long proctoringSessionId);

}
