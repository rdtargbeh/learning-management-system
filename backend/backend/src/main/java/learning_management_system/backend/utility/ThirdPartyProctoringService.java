package learning_management_system.backend.utility;


import learning_management_system.backend.entity.ProctoringSession;

public interface ThirdPartyProctoringService {
    boolean validateSession(ProctoringSession session);
}

