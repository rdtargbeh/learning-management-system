package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.StudentDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Student;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.EnrollmentStatus;
import learning_management_system.backend.mapper.StudentMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImplementation implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleContentRepository moduleContentRepository;

    @Autowired
    private LmsNotificationRepository lmsNotificationRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CourseRepository courseRepository;



    // Create New Student
    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        // Find User
        User user = userRepository.findById(studentDto.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + studentDto.getStudentId()));

        // Prevent creating duplicate staff for the same user
        if (studentRepository.existsById(studentDto.getStudentId())) {
            throw new IllegalArgumentException("Student already exists with User ID: " + studentDto.getStudentId());
        }
        // Find Enrolled Courses
        Set<Course> courses = studentDto.getEnrolledCourseIds().stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId)))
                .collect(Collectors.toSet());

        // Convert DTO to Entity
        Student student = StudentMapper.toEntity(studentDto, user, courses);


        // Validate the user's role
        boolean isStudent = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("Student"));
        if (!isStudent) {
            throw new IllegalArgumentException("User with ID: " + studentDto.getStudentId() + " is not assigned with the Student role.");
        }

        // Save Entity
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.toDto(savedStudent);
    }


// Update Student profile   ++++++++++++++++++++++++++++++++++++++++++++++++
@Override
public StudentDto updateStudent(Long studentId, StudentDto studentDto) {

        // Fetch the Student entity
    Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

    // Fetch the associated User entity
    User user = student.getUser();

    // Update basic user information
    if (studentDto.getFirstName() != null) {
        user.setFirstName(studentDto.getFirstName());
    }
    if (studentDto.getLastName() != null) {
        user.setLastName(studentDto.getLastName());
    }
    if (studentDto.getEmail() != null) {
        user.setEmail(studentDto.getEmail());
    }
    if (studentDto.getUserName() != null) {
        user.setUserName(studentDto.getUserName());
    }

    // Save updated User entity
    userRepository.save(user);

    // Update Student-specific fields
    Set<Course> courses = studentDto.getEnrolledCourseIds().stream()
            .map(courseId -> courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId)))
            .collect(Collectors.toSet());

    student.setGradeLevel(studentDto.getGradeLevel());
    student.setMajor(studentDto.getMajor());
    student.setBio(studentDto.getBio());
    student.setOverallProgress(studentDto.getOverallProgress());
    student.setLeaderboardPoints(studentDto.getLeaderboardPoints());
    student.setEnrollmentStatus(EnrollmentStatus.valueOf(studentDto.getEnrollmentStatus()));
    student.setBadges(student.getBadges());
    student.setCustomAttributes(studentDto.getCustomAttributes());
    student.setGraduationDate(studentDto.getGraduationDate());
    student.setEnrolledCourses(courses);

    // Save updated Student entity
    Student updatedStudent = studentRepository.save(student);
    return StudentMapper.toDto(updatedStudent);
}

    // Get All Students
    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Student By ID
    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        return StudentMapper.toDto(student);
    }


    // Method for internal use (returns Entity)
    @Override
    public Student getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public List<StudentDto> getActiveStudents() {
        return studentRepository.findByIsActive(true).stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major).stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getStudentsByCourseId(Long courseId) {
        List<Student> students = studentRepository.findStudentsByCourseId(courseId);
        return students.stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDto> getStudentsByGradeLevel(String gradeLevel) {
        List<Student> students = studentRepository.findByGradeLevel(gradeLevel);
        return students.stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
    }


    // Deletes both Student and the associated User due to cascade
    @Override
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        studentRepository.delete(student); // Deletes both Staff and the associated User due to cascade
    }

}
