package learning_management_system.backend.repository;

import learning_management_system.backend.entity.ProctoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProctoringSessionRepository extends JpaRepository<ProctoringSession, Long> {

    @Query("SELECT ps FROM ProctoringSession ps WHERE ps.assessment.assessmentId = :assessmentId AND ps.student.studentId = :studentId AND ps.status IN ('INITIATED', 'IN_PROGRESS')")
    Optional<ProctoringSession> findActiveLog(@Param("assessmentId") Long assessmentId, @Param("studentId") Long studentId);
}

