package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Grading;
import learning_management_system.backend.enums.GradingLinkedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradingRepository extends JpaRepository<Grading, Long> {

    Optional<Grading> findByAssessment_AssessmentId(Long assessmentId);

    Optional<Grading> findByAssignment_AssignmentId(Long assignmentId);


    Optional<Grading> findByQuestion_QuestionId(Long questionId);

    @Query("SELECT g FROM Grading g WHERE g.enableNormalization = true")
    List<Grading> findAllNormalizedGradings();

    // Check if a grading configuration exists for a given entity
    boolean existsByLinkedEntityIdAndLinkedEntityType(Long linkedEntityId, GradingLinkedEntityType linkedEntityType);
}
