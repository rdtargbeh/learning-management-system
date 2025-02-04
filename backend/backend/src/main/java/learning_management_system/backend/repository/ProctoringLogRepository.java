package learning_management_system.backend.repository;

import learning_management_system.backend.entity.ProctoringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProctoringLogRepository extends JpaRepository<ProctoringLog, Long> {

    @Query("SELECT p FROM ProctoringLog p WHERE p.assessment.assessmentId = :assessmentId AND p.student.studentId = :studentId AND p.endTime IS NULL")
    Optional<ProctoringLog> findActiveLog(@Param("assessmentId") Long assessmentId, @Param("studentId") Long studentId);

    @Query("SELECT p FROM ProctoringLog p WHERE p.assessment.assessmentId = :assessmentId AND p.student.studentId = :studentId")
    List<ProctoringLog> findAllLogsForStudentAndAssessment(@Param("assessmentId") Long assessmentId, @Param("studentId") Long studentId);

    List<ProctoringLog> findByStudent_StudentIdAndAssessment_AssessmentId(Long studentId, Long assessmentId);
}

