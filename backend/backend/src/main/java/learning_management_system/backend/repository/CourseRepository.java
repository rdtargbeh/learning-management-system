package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find courses by publication status
    List<Course> findByIsPublished(Boolean isPublished);

    // Find courses by teacher's staff ID
    List<Course> findByStaff_StaffId(Long teacherId);

    // Find courses by enrolled student's ID
    @Query("SELECT e.course FROM Enrollment e WHERE e.student.studentId = :studentId")
    List<Course> findByStudentId(@Param("studentId") Long studentId);

    List<Course> findByTenant_TenantId(Long tenantId);

    List<Course> findByDepartment_DepartmentId(Long departmentId);

    @Query("SELECT c FROM Course c WHERE c.startDate <= :date AND c.endDate >= :date")
    List<Course> findActiveCourses(@Param("date") LocalDate date);

    List<Course> findByCreatedBy(User user);

    /**
     * Count the total number of courses for a specific tenant.
     *
     * @param tenantId The ID of the tenant.
     * @return The total number of courses.
     */
    @Query("SELECT COUNT(c) FROM Course c WHERE c.tenant.tenantId = :tenantId")
    long countCoursesByTenantId(@Param("tenantId") Long tenantId);

    /**
     * Find all courses with the specified status.
     *
     * @param status The status of the courses to retrieve.
     * @return A list of courses with the given status.
     */
    List<Course> findByCourseStatus(CourseStatus status);

    long countByTenant_TenantId(Long tenantId);

}
