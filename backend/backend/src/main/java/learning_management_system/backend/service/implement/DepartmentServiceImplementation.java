package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.DepartmentDto;
import learning_management_system.backend.entity.ActivityLogs;
import learning_management_system.backend.entity.Department;
import learning_management_system.backend.mapper.ActivityLogMappers;
import learning_management_system.backend.mapper.DepartmentMapper;
import learning_management_system.backend.repository.ActivityLogsRepository;
import learning_management_system.backend.repository.DepartmentRepository;
import learning_management_system.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation  implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    @Autowired
    private ActivityLogMappers activityLogMappers;



// Create Department Method
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        // Prevent creating duplicate Department for the same user
        if (departmentRepository.findById(departmentDto.getDepartmentId()).isPresent()) {
            throw new IllegalArgumentException("Department already exists with  ID: " + departmentDto.getDepartmentId());
        }

        Department department = DepartmentMapper.toEntity(departmentDto);

        Department saveDepartment = departmentRepository.save(department);
        return DepartmentMapper.toDto(saveDepartment);
    }

    // Update Department Method
    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + departmentId));

        // Update Department-specific fields
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentAddress(departmentDto.getDepartmentAddress());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        department.setDepartmentEmail(departmentDto.getDepartmentEmail());
        department.setDepartmentPhoneNumber(departmentDto.getDepartmentPhoneNumber());
        department.setDepartmentHead(departmentDto.getDepartmentHead());

        //Save updated Department entity
        Department updateDepartment = departmentRepository.save(department);

        // Convert to DTO and return
        return DepartmentMapper.toDto(updateDepartment);

    }

    // Get All Department Method
    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Department by ID
    @Override
    public DepartmentDto getDepartmentById (Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with id: " + departmentId));
        return DepartmentMapper.toDto(department);
    }

    // Deletes both Department and the associated User due to cascade
    @Override
    public void deleteDepartment (Long departmentId){
        Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));
        departmentRepository.delete(department); // Deletes both Staff and the associated User due to cascade

    }

    // Get Department Activity Logs
    @Override
    public List<ActivityLogsDto> getDepartmentActivityLogs(Long staffId) {
        List<ActivityLogs> logs = activityLogsRepository.findActivityLogsByStaffId(staffId);
        return logs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

}





