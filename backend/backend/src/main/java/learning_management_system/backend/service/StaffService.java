package learning_management_system.backend.service;


import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.StaffDto;

import java.util.List;

public interface StaffService {
    List<StaffDto> getAllStaff();

    StaffDto getStaffById(Long id);

    StaffDto createStaff(StaffDto staffDto);

    StaffDto updateStaff(Long id, StaffDto staffDto);

    void deleteStaff(Long id);

    List<StaffDto> getStaffByDepartment(String department);

    List<StaffDto> getActiveStaff();

    List<ActivityLogsDto> getStaffActivityLogs(Long staffId);


}
