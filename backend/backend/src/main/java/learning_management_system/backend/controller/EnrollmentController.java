package learning_management_system.backend.controller;

import learning_management_system.backend.dto.EnrollmentAnalyticsDto;
import learning_management_system.backend.dto.EnrollmentDto;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private UserRepository userRepository;



    /**
     * Enroll a student in a course with notification.
     *
     * @param enrollmentDto The enrollment details.
     * @return The created enrollment details.
     */
    @PostMapping("/enroll-with-notification")
    public ResponseEntity<EnrollmentDto> enrollStudentWithNotification(
            @RequestBody EnrollmentDto enrollmentDto,
            @AuthenticationPrincipal User loggedInUser) {
        EnrollmentDto savedEnrollment = enrollmentService.enrollStudentWithNotification(enrollmentDto, loggedInUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }



    @GetMapping("/{courseId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourseId(courseId));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudentId(studentId));
    }


    @PutMapping("/status")
    public ResponseEntity<EnrollmentDto> updateEnrollmentStatusWithNotification(
            @RequestParam Long courseId,
            @RequestParam Long studentId,
            @RequestParam EnrollmentStatus status,
            @RequestParam(required = false) String loggedInUser) {

        // Placeholder: Replace with actual logged-in user logic
        User user = new User();
        user.setUserId(1L); // Replace with the logged-in user's ID
        user.setUserName(loggedInUser);

        EnrollmentDto updatedEnrollment = enrollmentService.updateEnrollmentStatusWithNotification(courseId, studentId, status, user);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @DeleteMapping("/{courseId}/{studentId}")
    public ResponseEntity<Void> unEnrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        enrollmentService.unEnrollStudent(courseId, studentId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{courseId}/{studentId}")
    public ResponseEntity<EnrollmentDto> getEnrollmentDetails(@PathVariable Long courseId, @PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentDetails(courseId, studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Page<EnrollmentDto>> getEnrollmentsByCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourse(courseId, pageable));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Page<EnrollmentDto>> getEnrollmentsByStudent(
            @PathVariable Long studentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId, pageable));
    }

    @PostMapping("/batch-enroll")
    public ResponseEntity<List<EnrollmentDto>> batchEnrollStudents(
            @RequestParam Long courseId,
            @RequestBody List<Long> studentIds,
            @RequestParam Long adminUserId // Add this parameter to pass admin information
    ) {
        User adminUser = userRepository.findById(adminUserId)
                .orElseThrow(() -> new RuntimeException("Admin user not found."));
        return ResponseEntity.ok(enrollmentService.batchEnrollStudents(courseId, studentIds, adminUser));
    }

    @GetMapping("/analytics/{courseId}")
    public ResponseEntity<EnrollmentAnalyticsDto> getEnrollmentAnalytics(@PathVariable Long courseId) {
        EnrollmentAnalyticsDto analytics = enrollmentService.getEnrollmentAnalytics(courseId);
        return ResponseEntity.ok(analytics);
    }




//    @DeleteMapping("/{enrollmentId}")
//    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
//        enrollmentService.deleteEnrollment(enrollmentId);
//        return ResponseEntity.ok().build();
//    }

//    @PatchMapping("/{enrollmentId}/status")
//    public ResponseEntity<Void> updateEnrollmentStatus(@PathVariable Long enrollmentId, @RequestParam String status) {
//        enrollmentService.updateEnrollmentStatus(enrollmentId, status);
//        return ResponseEntity.ok().build();
//    }

//    @PostMapping
//    public ResponseEntity<EnrollmentDto> enrollStudent(@RequestBody EnrollmentDto enrollmentDto) {
//        return ResponseEntity.ok(enrollmentService.enrollStudent(enrollmentDto));
//    }

//    @PatchMapping("/{courseId}/{studentId}")
//    public ResponseEntity<EnrollmentDto> updateEnrollmentStatus(
//            @PathVariable Long courseId, @PathVariable Long studentId, @RequestParam EnrollmentStatus status) {
//        return ResponseEntity.ok(enrollmentService.updateEnrollmentStatus(courseId, studentId, status));
//    }
}