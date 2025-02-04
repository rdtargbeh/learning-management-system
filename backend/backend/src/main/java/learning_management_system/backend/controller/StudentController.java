package learning_management_system.backend.controller;

import learning_management_system.backend.dto.StudentDto;
import learning_management_system.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    // Build A Get All Students REST API
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // Build A Get Student By ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // Build A Create Student REST API
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    // Build An Update Student  Profile REST API
    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

    // Build A Delete Student REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Build A Get Student By Course ID REST API
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<StudentDto>> getStudentsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.getStudentsByCourseId(courseId));
    }

    // Build A Get Active Student REST API
    @GetMapping("/active")
    public ResponseEntity<List<StudentDto>> getActiveStudents() {
        return ResponseEntity.ok(studentService.getActiveStudents());
    }

    // Build A Get Student By Major REST API
    @GetMapping("/major/{major}")
    public ResponseEntity<List<StudentDto>> getStudentsByMajor(@PathVariable String major) {
        return ResponseEntity.ok(studentService.getStudentsByMajor(major));
    }


    @GetMapping("/grade-level/{gradeLevel}")
    public ResponseEntity<List<StudentDto>> getStudentsByGradeLevel(@PathVariable String gradeLevel) {
        return ResponseEntity.ok(studentService.getStudentsByGradeLevel(gradeLevel));
    }
}
