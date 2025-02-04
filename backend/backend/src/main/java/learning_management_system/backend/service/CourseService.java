package learning_management_system.backend.service;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.enums.CourseStatus;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Long id);

    CourseDto createCourse(CourseDto courseDto);

    CourseDto updateCourse(Long id, CourseDto courseDto);

    void deleteCourse(Long id);

    List<CourseDto> getPublishedCourses(Boolean isPublished);

    List<CourseDto> getPublishedCourses();

    List<CourseDto> getCoursesByTeacherId(Long teacherId);

    List<CourseDto> getCoursesByStudentId(Long studentId);

    List<CourseDto> getCoursesByTenant(Long tenantId);

    List<CourseDto> getCoursesByDepartment(Long departmentId);

    List<CourseDto> getActiveCourses();

    List<CourseDto> getCoursesCreatedByUser(Long userId);

    List<UserDto> getEnrolledStudents(Long courseId);


    /**
     * Count courses for a specific tenant.
     *
     * @param tenantId The ID of the tenant.
     * @return The number of courses for the tenant.
     */
    long countCoursesByTenantId(Long tenantId);

    /**
     * Find courses by status.
     *
     * @param status The status of the courses to retrieve.
     * @return A list of courses with the given status.
     */
    List<CourseDto> findByCourseStatus(CourseStatus status);

}