package learning_management_system.backend.service;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto);

    DepartmentDto getDepartmentById (Long departmentId);

    void deleteDepartment (Long departmentId);

    List<ActivityLogsDto> getDepartmentActivityLogs(Long staffId);

    List<DepartmentDto> getAllDepartments();

}
