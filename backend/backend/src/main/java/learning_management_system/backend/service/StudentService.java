package learning_management_system.backend.service;

import learning_management_system.backend.dto.StudentDto;
import learning_management_system.backend.entity.Student;

import java.util.List;

public interface StudentService {

    // Get all students
    List<StudentDto> getAllStudents();

    // Get a student by ID
    StudentDto getStudentById(Long id);

    // Create a student
    StudentDto createStudent(StudentDto studentDto);

    // Update a student
    StudentDto updateStudent(Long id, StudentDto studentDto);

    // Delete a student
    void deleteStudent(Long studentId);

    // Get students by course
    List<StudentDto> getStudentsByCourseId(Long courseId);

    // Get active students
    List<StudentDto> getActiveStudents();

    // Get students by major
    List<StudentDto> getStudentsByMajor(String major);

    List<StudentDto> getStudentsByGradeLevel(String gradeLevel);

    Student getStudentEntityById(Long id);


}
