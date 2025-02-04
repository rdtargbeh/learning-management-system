package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find active students

    @Query(value = "SELECT s.* FROM students s JOIN users u ON s.user_id = u.user_id WHERE u.is_active = :isActive", nativeQuery = true)
    List<Student> findByIsActive(@Param("isActive") Boolean isActive);

    // Find students by major
    List<Student> findByMajor(String major);

    // Find students enrolled in a specific course
    @Query("SELECT s FROM Student s JOIN s.enrolledCourses c WHERE c.courseId = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);

    // Search by grade level
    List<Student> findByGradeLevel(String gradeLevel);
}