package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Enrollment;
import learning_management_system.backend.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByCourse_CourseId(Long courseId);

    List<Enrollment> findByStudent_StudentId(Long studentId);

    Optional<Enrollment> findByCourse_CourseIdAndStudent_StudentId(Long courseId, Long studentId);

    boolean existsByCourse_CourseIdAndStudent_StudentId(Long courseId, Long studentId);

    Page<Enrollment> findByCourse_CourseId(Long courseId, Pageable pageable);

    Page<Enrollment> findByStudent_StudentId(Long studentId, Pageable pageable);

    @Query("SELECT e FROM Enrollment e WHERE e.status = :status")
    List<Enrollment> findByStatus(@Param("status") EnrollmentStatus status);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.status = :status AND e.course.courseId = :courseId")
    Long countEnrollmentsByStatusAndCourse(@Param("status") EnrollmentStatus status, @Param("courseId") Long courseId);

    @Query("SELECT e.course.courseId, COUNT(e) FROM Enrollment e GROUP BY e.course.courseId")
    List<Object[]> countEnrollmentsPerCourse();

    @Query("SELECT e.status, COUNT(e) FROM Enrollment e WHERE e.enrollmentDate BETWEEN :startDate AND :endDate GROUP BY e.status")
    List<Object[]> countEnrollmentsByStatusAndDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Enrollment e WHERE e.course.courseId = :courseId AND e.isDeleted = false")
    List<Enrollment> findActiveEnrollmentsByCourse(@Param("courseId") Long courseId);


}