package learning_management_system.backend.controller;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.enums.CourseStatus;
import learning_management_system.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


// Build A Get All Course REST API
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // Build A Get Course By ID REST API
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    // Build A Create Course REST API
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.createCourse(courseDto));
    }

    // Build An Update Course REST API
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDto));
    }

    // Build A Delete Course REST API
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

// Combine both published and all-published methods
    @GetMapping("/published")
    public ResponseEntity<List<CourseDto>> getPublishedCourses(@RequestParam(required = false) Boolean isPublished) {
        if (isPublished == null) {
            // Fetch all published courses
            return ResponseEntity.ok(courseService.getPublishedCourses());
        } else {
            // Fetch courses filtered by published status
            return ResponseEntity.ok(courseService.getPublishedCourses(isPublished));
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseDto>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacherId(teacherId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseDto>> getCoursesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.getCoursesByStudentId(studentId));
    }


    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<CourseDto>> getCoursesByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(courseService.getCoursesByTenant(tenantId));
    }

    @GetMapping("/departmentId")
    public ResponseEntity<List<CourseDto>> getCourseByDepartment(@PathVariable Long departmentId){
        return ResponseEntity.ok(courseService.getCoursesByDepartment(departmentId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<CourseDto>> getActiveCourses(){
        return ResponseEntity.ok(courseService.getActiveCourses());
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<UserDto>> getEnrolledStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getEnrolledStudents(courseId));
    }

    @GetMapping("/courses/created-by/{userId}")
    public ResponseEntity<List<CourseDto>> getCoursesCreatedByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(courseService.getCoursesCreatedByUser(userId));
    }


    /**
     * Count courses for a specific tenant.
     *
     * @param tenantId The ID of the tenant.
     * @return The total number of courses for the tenant.
     */
    @GetMapping("/count-by-tenant/{tenantId}")
    public ResponseEntity<Long> countCoursesByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(courseService.countCoursesByTenantId(tenantId));
    }

    /**
     * Find courses by status.
     *
     * @param status The status of the courses to retrieve.
     * @return A list of courses with the given status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CourseDto>> findByCourseStatus(@PathVariable CourseStatus status) {
        return ResponseEntity.ok(courseService.findByCourseStatus(status));
    }

    //    @GetMapping("/published")
//    public ResponseEntity<List<CourseDto>> getPublishedCourses(@RequestParam Boolean isPublished) {
//        return ResponseEntity.ok(courseService.getPublishedCourses(isPublished));
//    }

//    @GetMapping("/all-published")
//    public ResponseEntity<List<CourseDto>> getPublishedCourses() {
//        return ResponseEntity.ok(courseService.getPublishedCourses());
//    }

}
