package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a JOIN a.assignedStudents u WHERE u.userId = :userId")
    List<Assignment> findAssignmentsByUserId(@Param("userId") Long userId);

    List<Assignment> findByCourse_CourseId(Long courseId);

//    List<Assignment> findByAssignedStudents_StudentId(Long studentId);

    List<Assignment> findByCreatedBy_UserId(Long userId);

    Optional<Assignment> findById(Long id);

}
