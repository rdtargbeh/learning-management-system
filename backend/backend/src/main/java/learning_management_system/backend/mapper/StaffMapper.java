package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.StaffDto;
import learning_management_system.backend.entity.Staff;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StaffMapper {

    public static StaffDto toDto(Staff staff) {
        StaffDto dto = new StaffDto();
//        dto.setStaffId(staff.getStaffId());
        dto.setUserId(staff.getUser().getUserId());
        dto.setUserName(staff.getUser().getUserName());
        dto.setEmail(staff.getUser().getEmail());
        dto.setFirstName(staff.getUser().getFirstName());
        dto.setLastName(staff.getUser().getLastName());
        dto.setStaffType(staff.getStaffType());
        dto.setJobTitle(staff.getJobTitle());
        dto.setSubjectSpecialization(staff.getSubjectSpecialization());
        dto.setDateHired(staff.getDateHired());
        dto.setEmploymentStatus(staff.getEmploymentStatus());
        dto.setOfficeLocation(staff.getOfficeLocation());
        dto.setAccessibilityPreferences(staff.getAccessibilityPreferences());
        dto.setWorkSchedule(staff.getWorkSchedule());
        dto.setDepartmentId(staff.getDepartment() != null? staff.getDepartment().getDepartmentId() : null);
        dto.setDepartmentId(staff.getDepartment() != null ? staff.getDepartment().getDepartmentId() : null);
        dto.setTenantId(staff.getTenant() != null ? staff.getTenant().getTenantId() : null);

        dto.setLinkedTenantIds(staff.getLinkedTenants().stream()
                .map(t -> t.getTenantId())
                .collect(Collectors.toSet()));

        dto.setAssignedCourseIds(staff.getAssignedCourses().stream()
                .map(c -> c.getCourseId())
                .collect(Collectors.toList()));

        dto.setAssignedTeacherIds(staff.getAssignedTeachers().stream()
                .map(t -> t.getStaffId())
                .collect(Collectors.toSet()));

        dto.setSkills(staff.getSkills());
        dto.setCurrentWorkload(staff.getCurrentWorkload());
        dto.setMaxWorkload(staff.getMaxWorkload());
        dto.setAvailabilityStatus(staff.getAvailabilityStatus());
        dto.setEngagementStatus(staff.getEngagementStatus());
        dto.setExternalReferenceId(staff.getExternalReferenceId());
        dto.setMetadata(staff.getMetadata());
        dto.setLastUpdatedBy(staff.getLastUpdatedBy());
        dto.setLastUpdatedAt(staff.getLastUpdatedAt());

        //Additional relationships

//        dto.setNotifications(staff.getUser().getNotifications().stream()
//                .map(LmsNotificationMapper::toDto)
//                .collect(Collectors.toList()));
//
//        dto.setActivityLogs(staff.getUser().getActivityLogs().stream()
//                .map(ActivityLogMapper::toDto)
//                .collect(Collectors.toList()));
        return dto;
    }


    public static Staff toEntity(StaffDto dto) {
        Staff staff = new Staff();
        staff.setStaffType(dto.getStaffType());
        staff.setJobTitle(dto.getJobTitle());
        staff.setSubjectSpecialization(dto.getSubjectSpecialization());
        staff.setDateHired(dto.getDateHired());
        staff.setEmploymentStatus(dto.getEmploymentStatus());
        staff.setOfficeLocation(dto.getOfficeLocation());
        staff.setAccessibilityPreferences(dto.getAccessibilityPreferences());
        staff.setWorkSchedule(dto.getWorkSchedule());
        staff.setSkills(dto.getSkills());
        staff.setCurrentWorkload(dto.getCurrentWorkload());
        staff.setMaxWorkload(dto.getMaxWorkload());
        staff.setAvailabilityStatus(dto.getAvailabilityStatus());
        staff.setEngagementStatus(dto.getEngagementStatus());
        staff.setExternalReferenceId(dto.getExternalReferenceId());
        staff.setMetadata(dto.getMetadata());
        staff.setLastUpdatedBy(dto.getLastUpdatedBy());
        staff.setLastUpdatedAt(dto.getLastUpdatedAt());

        return staff;
    }

}
