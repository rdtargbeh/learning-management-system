package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.CourseDto;
import learning_management_system.backend.dto.UserDto;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Department;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.CourseStatus;
import learning_management_system.backend.mapper.CourseMapper;
import learning_management_system.backend.mapper.UserMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImplementation implements CourseService {

    @Autowired
    private ModuleContentRepository moduleContentRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentRepository departmentRepository;



    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Tenant tenant = tenantRepository.findById(courseDto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + courseDto.getTenantId()));

        User createdBy = userRepository.findById(courseDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + courseDto.getCreatedBy()));

        // Fetch the department (optional)
        Department department = courseDto.getDepartmentId() != null
                ? departmentRepository.findById(courseDto.getDepartmentId()).orElse(null)
                : null;

        Course course = courseMapper.toEntity(courseDto);
        course.setTenant(tenant);
        course.setCreatedBy(createdBy);
        course.setDepartment(department);

        return courseMapper.toDto(courseRepository.save(course));
    }

    @Override
    public CourseDto updateCourse(Long courseId, CourseDto courseDto) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        if (courseDto.getCourseTitle() != null) {
            existingCourse.setCourseTitle(courseDto.getCourseTitle());
        }
        if (courseDto.getCourseDescription() != null) {
            existingCourse.setCourseDescription(courseDto.getCourseDescription());
        }

        // Update other fields...
        existingCourse.setCourseStatus(courseDto.getCourseStatus());
        existingCourse.setCourseCode(courseDto.getCourseCode());
        existingCourse.setArchived(courseDto.isArchived());
        existingCourse.setEnrollmentStart(courseDto.getEnrollmentStart());
        existingCourse.setEnrollmentEnd(courseDto.getEnrollmentEnd());
        existingCourse.setPublished(courseDto.getPublished());
        existingCourse.setStartDate(courseDto.getStartDate());
        existingCourse.setEndDate(courseDto.getEndDate());
        existingCourse.setMaxEnrollments(courseDto.getMaxEnrollments());
        existingCourse.setVisibility(courseDto.getVisibility());

        return courseMapper.toDto(courseRepository.save(existingCourse));
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found."));
        return courseMapper.toDto(course);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!moduleContentRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        moduleContentRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getPublishedCourses(Boolean isPublished) {
        return courseRepository.findByIsPublished(isPublished)
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getPublishedCourses() {
        return courseRepository.findByIsPublished(true).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByStaff_StaffId(teacherId).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByStudentId(Long studentId) {
        return courseRepository.findByStudentId(studentId).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByTenant(Long tenantId) {
        return courseRepository.findByTenant_TenantId(tenantId).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesByDepartment(Long departmentId) {
        return courseRepository.findByDepartment_DepartmentId(departmentId).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getActiveCourses() {
        LocalDate today = LocalDate.now();
        return courseRepository.findActiveCourses(today).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCoursesCreatedByUser(Long userId) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Fetch courses created by the user
        List<Course> courses = courseRepository.findByCreatedBy(user);

        // Map entities to DTOs
        return courses.stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getEnrolledStudents(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        return course.getStudents().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long countCoursesByTenantId(Long tenantId) {
        return courseRepository.countCoursesByTenantId(tenantId);
    }

    @Override
    public List<CourseDto> findByCourseStatus(CourseStatus status) {
        return courseRepository.findByCourseStatus(status).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }



//    @Override
//    public CourseDto createCourse(CourseDto courseDto) {
//        // Fetch Tenant
//        Tenant tenant = tenantRepository.findById(courseDto.getTenantId())
//                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + courseDto.getTenantId()));
//
//        // Fetch Teacher
//        Staff teacher = staffRepository.findById(courseDto.getStaffIds())
//                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + courseDto.getTeacherId()));
//
//        // Fetch Students
//        Set<Student> enrolledStudents = new HashSet<>();
//        if (courseDto.getStudentIds() != null && !courseDto.getStudentIds().isEmpty()) {
//            enrolledStudents = courseDto.getStudentIds().stream()
//                    .map(studentId -> studentRepository.findById(studentId)
//                            .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId)))
//                    .collect(Collectors.toSet());
//        }
//
//        // Map to Course Entity (Correct Order of Parameters)
//        Course course = courseMapper.toEntity(courseDto, teacher,  enrolledStudents, tenant);
//
//        // Save Course
//        Course savedCourse = courseRepository.save(course);
//
//        return courseMapper.toDto(savedCourse);
//    }


//    @Override
//    public CourseDto updateCourse(Long id, CourseDto courseDto) {
//        Course course = courseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
//
//        course.setTitle(courseDto.getTitle());
//        course.setDescription(courseDto.getDescription());
//        course.setStartDate(courseDto.getStartDate());
//        course.setEndDate(courseDto.getEndDate());
//        course.setPublished(courseDto.getPublished());
//        course.setTags(courseDto.getTags());
//
//        Course updatedCourse = courseRepository.save(course);
//        return CourseMapper.toDto(updatedCourse);
//    }



}
