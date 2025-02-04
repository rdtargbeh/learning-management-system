package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    List<Assessment> findByCourseCourseId(Long courseId);

    List<Assessment> findByCreatedByUserId(Long userId);

    List<Assessment> findByIsPublishedTrue();

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assessment.assessmentId = :assessmentId AND s.student.userId = :studentId")
    long countByAssessmentAndStudent(@Param("assessmentId") Long assessmentId, @Param("studentId") Long studentId);

}
