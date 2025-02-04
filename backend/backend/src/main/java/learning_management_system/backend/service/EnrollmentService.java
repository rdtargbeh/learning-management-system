package learning_management_system.backend.service;

import learning_management_system.backend.dto.EnrollmentAnalyticsDto;
import learning_management_system.backend.dto.EnrollmentDto;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EnrollmentService {

    EnrollmentDto enrollStudentWithNotification(EnrollmentDto enrollmentDto, User loggedInUser);

//    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto, User loggedInUser);

    EnrollmentDto updateEnrollmentStatusWithNotification(Long courseId, Long studentId, EnrollmentStatus status, User loggedInUser);

    List<EnrollmentDto> getEnrollmentsByCourseId(Long courseId);

    List<EnrollmentDto> getEnrollmentsByStudentId(Long studentId);

    void unEnrollStudent(Long courseId, Long studentId);

    EnrollmentDto getEnrollmentDetails(Long courseId, Long studentId);

    Page<EnrollmentDto> getEnrollmentsByCourse(Long courseId, Pageable pageable);

    Page<EnrollmentDto> getEnrollmentsByStudent(Long studentId, Pageable pageable);

    List<EnrollmentDto> batchEnrollStudents(Long courseId, List<Long> studentIds, User adminUser);

    EnrollmentAnalyticsDto getEnrollmentAnalytics(Long courseId);




//    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto);

//    void updateEnrollmentStatus(Long enrollmentId, String status);

//    void deleteEnrollment(Long enrollmentId);


}