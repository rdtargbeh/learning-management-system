package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.EnrollmentDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Enrollment;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.repository.CourseRepository;
import learning_management_system.backend.repository.StudentRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.utility.EnrollmentId;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public EnrollmentMapper(CourseRepository courseRepository, UserRepository userRepository,
                            StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;

    }

    /**
     * Convert Enrollment entity to EnrollmentDto.
     */
    public EnrollmentDto toDto(Enrollment enrollment) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setEnrollmentId(enrollment.getEnrollmentId() != null ? enrollment.getEnrollmentId().getStudentId() : null);
        dto.setCourseId(enrollment.getEnrollmentId() != null ? enrollment.getEnrollmentId().getCourseId() : null);
        dto.setStudentId(enrollment.getStudent() != null ? enrollment.getStudent().getUser().getUserId() : null);
        dto.setGrade(enrollment.getGrade());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setStatus(enrollment.getStatus());
        dto.setAttendancePercentage(enrollment.getAttendancePercentage());
        dto.setCompletionDate(enrollment.getCompletionDate());
        dto.setMetadata(enrollment.getMetadata());
        return dto;
    }

    /**
     * Convert EnrollmentDto to Enrollment entity.
     */
    public Enrollment toEntity(EnrollmentDto dto) {
        Enrollment enrollment = new Enrollment();
        EnrollmentId enrollmentId = new EnrollmentId(dto.getStudentId(), dto.getCourseId());
        enrollment.setEnrollmentId(enrollmentId);

        if (dto.getStudentId() != null) {
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + dto.getStudentId()));
            enrollment.setStudent(student);
        }

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));
            enrollment.setCourse(course);
        }

        enrollment.setGrade(dto.getGrade());
        enrollment.setEnrollmentDate(dto.getEnrollmentDate());
        enrollment.setStatus(dto.getStatus());
        enrollment.setAttendancePercentage(dto.getAttendancePercentage());
        enrollment.setCompletionDate(dto.getCompletionDate());
        enrollment.setMetadata(dto.getMetadata());
        return enrollment;
    }

}

