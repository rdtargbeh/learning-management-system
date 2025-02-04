package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning_management_system.backend.dto.EnrollmentAnalyticsDto;
import learning_management_system.backend.dto.EnrollmentDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.mapper.EnrollmentMapper;
import learning_management_system.backend.repository.ActivityLogsRepository;
import learning_management_system.backend.repository.CourseRepository;
import learning_management_system.backend.repository.EnrollmentRepository;
import learning_management_system.backend.repository.StudentRepository;
import learning_management_system.backend.service.EnrollmentService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImplementation implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private EnrollmentMapper enrollmentMapper;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    @Autowired
    private LmsNotificationService notificationService;



    @Override
    @Transactional
    public EnrollmentDto enrollStudentWithNotification(EnrollmentDto enrollmentDto, User loggedInUser) {
        // Check for duplicate enrollment
        boolean exists = enrollmentRepository.existsByCourse_CourseIdAndStudent_StudentId(
                enrollmentDto.getCourseId(), enrollmentDto.getStudentId());
        if (exists) {
            throw new RuntimeException("Student is already enrolled in this course.");
        }

        // Validate course and student
        Course course = courseRepository.findById(enrollmentDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + enrollmentDto.getCourseId()));
        Student student = studentRepository.findById(enrollmentDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + enrollmentDto.getStudentId()));

        // Map DTO to entity and set relationships
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDto);
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        // Save enrollment
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Log the action
        logEnrollmentAction(
                savedEnrollment.getCourse().getCourseId(),
                savedEnrollment.getStudent().getStudentId(),
                "CREATED",
                "Enrolled student in course",
                loggedInUser
        );

        // Notify student
        notificationService.createAndSendNotification(
                enrollmentDto.getStudentId(),
                "You have been enrolled in the course: " + enrollmentDto.getCourseId(),
                enrollmentDto.getCourseId(), // Pass Long directly
                RelatedEntityType.COURSE, // Use the enum value
                null // Metadata (String), replace with actual metadata if needed
        );

        return enrollmentMapper.toDto(savedEnrollment);
    }


    @Override
    @Transactional
    public EnrollmentDto updateEnrollmentStatusWithNotification(Long courseId, Long studentId, EnrollmentStatus status, User loggedInUser) {
        // Fetch the enrollment
        Enrollment enrollment = enrollmentRepository.findByCourse_CourseIdAndStudent_StudentId(courseId, studentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));

        // Update the enrollment status
        enrollment.setStatus(status);
        if (status == EnrollmentStatus.COMPLETED) {
            enrollment.setCompletionDate(LocalDate.now());
        }

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

        // Log the status update
        logEnrollmentAction(
                enrollment.getCourse().getCourseId(),  // Extract courseId
                enrollment.getStudent().getStudentId(),  // Extract studentId
                "STATUS_UPDATED",
                "Enrollment status changed to " + status,
                loggedInUser
        );

        // Notify student
        notificationService.createAndSendNotification(
                studentId,
                "Your enrollment status has changed to " + status.name(),
                courseId, // Pass courseId directly as Long
                RelatedEntityType.COURSE, // Use the enum value
                null // Pass metadata or another argument if required
        );

        return enrollmentMapper.toDto(updatedEnrollment);
    }


    @Override
    public List<EnrollmentDto> getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourse_CourseId(courseId).stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<EnrollmentDto> getEnrollmentsByStudentId(Long studentId) {
        return enrollmentRepository.findByStudent_StudentId(studentId)
                .stream()
                .map(enrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void unEnrollStudent(Long courseId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourse_CourseIdAndStudent_StudentId(courseId, studentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public EnrollmentDto getEnrollmentDetails(Long courseId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourse_CourseIdAndStudent_StudentId(courseId, studentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found."));
        return enrollmentMapper.toDto(enrollment);
    }


    @Override
    public Page<EnrollmentDto> getEnrollmentsByCourse(Long courseId, Pageable pageable) {
        return enrollmentRepository.findByCourse_CourseId(courseId, pageable)
                .map(enrollmentMapper::toDto);
    }

    @Override
    public Page<EnrollmentDto> getEnrollmentsByStudent(Long studentId, Pageable pageable) {
        return enrollmentRepository.findByStudent_StudentId(studentId, pageable)
                .map(enrollmentMapper::toDto);
    }



    private void logEnrollmentAction(Long courseId, Long studentId, String action, String description, User loggedInUser) {
        ActivityLogs log = new ActivityLogs();
        log.setUser(loggedInUser);
        log.setActionCategory(ActionCategory.ENROLLMENT);
        log.setActionType(ActionType.valueOf(action));
        log.setResourceType("Enrollment");
        log.setResourceId(null); // Optional, since EnrollmentId is composite

        // Create metadata as a Map
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("courseId", courseId);
        metadata.put("studentId", studentId);
        metadata.put("description", description);

        log.setMetadata(metadata); // Pass metadata as a Map
        log.setStatus(true);
        log.setIpAddress("N/A"); // You can update this if IP is available
        activityLogsRepository.save(log);
    }


    // Enroll multiple students in a course at once.
    @Override
    @Transactional
    public List<EnrollmentDto> batchEnrollStudents(Long courseId, List<Long> studentIds, User adminUser) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found."));

        List<EnrollmentDto> enrollments = new ArrayList<>();

        for (Long studentId : studentIds) {
            if (!enrollmentRepository.existsByCourse_CourseIdAndStudent_StudentId(courseId, studentId)) {
                Enrollment enrollment = new Enrollment();
                enrollment.setCourse(course);
                enrollment.setStudent(studentRepository.findById(studentId)
                        .orElseThrow(() -> new RuntimeException("Student not found.")));
                enrollment.setEnrollmentDate(LocalDate.now());
                enrollment.setStatus(EnrollmentStatus.ACTIVE);

                Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
                enrollments.add(enrollmentMapper.toDto(savedEnrollment));

                // Prepare metadata for the notification
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("courseId", courseId);
                metadata.put("studentId", studentId);
                metadata.put("adminUser", adminUser.getUserName());
                metadata.put("action", "Batch Enrollment");
                metadata.put("timestamp", LocalDateTime.now());

                // Serialize metadata to JSON
                String serializedMetadata;
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    serializedMetadata = objectMapper.writeValueAsString(metadata);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to serialize metadata for notification", e);
                }

                // Send notification
                notificationService.createAndSendNotification(
                        studentId, // Long
                        "You have been enrolled in the course: " + courseId, // String
                        courseId, // Long
                        RelatedEntityType.COURSE, // Enum
                        serializedMetadata // Serialized metadata as String
                );
            }
        }
        return enrollments;
    }


    @Override
    public EnrollmentAnalyticsDto getEnrollmentAnalytics(Long courseId) {
        Long activeEnrollments = enrollmentRepository.countEnrollmentsByStatusAndCourse(EnrollmentStatus.ACTIVE, courseId);
        Long completedEnrollments = enrollmentRepository.countEnrollmentsByStatusAndCourse(EnrollmentStatus.COMPLETED, courseId);
        Long droppedEnrollments = enrollmentRepository.countEnrollmentsByStatusAndCourse(EnrollmentStatus.DROPPED, courseId);

        return new EnrollmentAnalyticsDto(activeEnrollments, completedEnrollments, droppedEnrollments);
    }




//    @Override
//    public void deleteEnrollment(Long enrollmentId) {
//        if (!enrollmentRepository.existsById(enrollmentId)) {
//            throw new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId);
//        }
//        enrollmentRepository.deleteById(enrollmentId);
//    }

//    @Override
//    public List<EnrollmentDto> getEnrollmentsByStudent(Long studentId) {
//        return enrollmentRepository.findByStudent_StudentId(studentId)
//                .stream()
//                .map(enrollmentMapper::toDto)
//                .collect(Collectors.toList());
//    }

    //    @Override
//    public void updateEnrollmentStatus(Long enrollmentId, String status) {
//        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
//                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found with ID: " + enrollmentId));
//        enrollment.setStatus(status);
//        enrollmentRepository.save(enrollment);
//    }



}
