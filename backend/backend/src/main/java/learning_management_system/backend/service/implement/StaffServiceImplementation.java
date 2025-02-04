package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.StaffDto;
import learning_management_system.backend.entity.ActivityLogs;
import learning_management_system.backend.entity.Department;
import learning_management_system.backend.entity.Staff;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.mapper.ActivityLogMappers;
import learning_management_system.backend.mapper.StaffMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StaffServiceImplementation implements StaffService {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModuleContentRepository moduleContentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ActivityLogMappers activityLogMappers;


    // Create Staff  Method
    @Override
    public StaffDto createStaff(StaffDto staffDto) {

        // Ensure the User exists
        User user = userRepository.findById(staffDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + staffDto.getUserId()));

        // Prevent creating duplicate staff for the same user
        if (staffRepository.existsById(staffDto.getUserId())) {
            throw new IllegalArgumentException("Staff already exists with User ID: " + staffDto.getUserId());
        }

        Staff staff = StaffMapper.toEntity(staffDto);

        Staff savedStaff = staffRepository.save(staff);
        return StaffMapper.toDto(savedStaff);
    }


    // Update Staff Method
    @Override
    public StaffDto updateStaff(Long staffId, StaffDto staffDto) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with id: " + staffId));


        // Updating field in User entity field through staff entity

        // Fetch the associated User entity
        User user = staff.getUser();

        // Update User fields
        if (staffDto.getUserName() != null) {
            user.setUserName(staffDto.getUserName());
        }
        if (staffDto.getFirstName() != null) {
            user.setFirstName(staffDto.getFirstName());
        }
        if (staffDto.getLastName() != null) {
            user.setLastName(staffDto.getLastName());
        }
        if (staffDto.getEmail() != null) {
            user.setEmail(staffDto.getEmail());
        }

        // Save updated User entity
        userRepository.save(user);

         // Update Staff-specific fields
         // We can follow user pattern above to update staff specific field as commented below
        staff.setJobTitle(staffDto.getJobTitle());
        staff.setSubjectSpecialization(staffDto.getSubjectSpecialization());
        staff.setDateHired(staffDto.getDateHired());
        staff.setEmploymentStatus(staffDto.getEmploymentStatus());
        staff.setCurrentWorkload(staffDto.getCurrentWorkload());
        staff.setMaxWorkload(staffDto.getMaxWorkload());
        staff.setAvailabilityStatus(staffDto.getAvailabilityStatus());
        staff.setOfficeLocation(staffDto.getOfficeLocation());
        staff.setExternalReferenceId(staffDto.getExternalReferenceId());
        staff.setWorkSchedule(staffDto.getWorkSchedule());
        staff.setAccessibilityPreferences(staffDto.getAccessibilityPreferences());
        staff.setStaffType(staffDto.getStaffType());
        staff.setSkills(staff.getSkills());

        // Update department
        if (staffDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(staffDto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + staffDto.getDepartmentId()));
            staff.setDepartment(department);
        } else {
            staff.setDepartment(null); // Clear the department if no ID is provided
        }

        // Update assigned teachers
        if (staffDto.getAssignedTeacherIds() != null && !staffDto.getAssignedTeacherIds().isEmpty()) {
            Set<Staff> assignedTeachers = new HashSet<>(staffRepository.findAllById(staffDto.getAssignedTeacherIds()));
            if (assignedTeachers.size() != staffDto.getAssignedTeacherIds().size()) {
                throw new RuntimeException("Some assigned teachers do not exist");
            }
            staff.setAssignedTeachers(assignedTeachers);
        } else {
            staff.setAssignedTeachers(new HashSet<>());
        }

        // Save updated Staff entity
        Staff updatedStaff = staffRepository.save(staff);

        // Convert to DTO and return
        return StaffMapper.toDto(updatedStaff);
    }


    // Get All Staff Method
    @Override
    public List<StaffDto> getAllStaff() {
        return staffRepository.findAll()
                .stream()
                .map(StaffMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Staff by ID
    @Override
    public StaffDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with id: " + id));
        return StaffMapper.toDto(staff);
    }

    // Deletes both Staff and the associated User due to cascade
    @Override
    public void deleteStaff(Long staffId) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));
        staffRepository.delete(staff); // Deletes both Staff and the associated User due to cascade
    }

    // Get Staff by Departments
    @Override
    public List<StaffDto> getStaffByDepartment(String department) {
        return staffRepository.findByDepartment(department)
                .stream()
                .map(StaffMapper::toDto)
                .collect(Collectors.toList());
    }

    // Fetch Staff Activity Logs
    @Override
    public List<StaffDto> getActiveStaff() {
        return staffRepository.findByUser_IsActive(true).stream()
                .map(StaffMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Staff Activity Logs
    @Override
    public List<ActivityLogsDto> getStaffActivityLogs(Long staffId) {
        List<ActivityLogs> logs = activityLogsRepository.findActivityLogsByStaffId(staffId);
        return logs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

}