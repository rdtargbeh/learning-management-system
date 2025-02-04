package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.AdminDto;
import learning_management_system.backend.entity.Admin;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Admin entities and Admin DTOs.
 */

@Component
public class AdminMapper {

    /**
     * Converts an Admin entity to an AdminDto.
     * @param admin The Admin entity.
     * @return The corresponding AdminDto.
     */
    public AdminDto toDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setAdminId(admin.getAdminId());
        dto.setUserId(admin.getUser().getUserId());
        dto.setUserName(admin.getUser().getUserName());
        dto.setEmail(admin.getUser().getEmail());
        dto.setAdminLevel(admin.getAdminLevel().name());
        dto.setTenantId(admin.getTenant() != null ? admin.getTenant().getTenantId() : null);
        dto.setDepartmentId(admin.getDepartment() != null ? admin.getDepartment().getDepartmentId() : null);
        dto.setTitle(admin.getTitle());
        dto.setHireDate(admin.getHireDate());
        dto.setOfficeLocation(admin.getOfficeLocation());
        dto.setAccessibilityPreferences(admin.getAccessibilityPreferences());
        dto.setWorkSchedule(admin.getWorkSchedule());
        dto.setPreferences(admin.getPreferences());
        dto.setSuperAdmin(admin.getSuperAdmin());
        dto.setLastLogin(admin.getLastLogin());
        dto.setLastLoginIp(admin.getLastLoginIp());
        return dto;
    }

    /**
     * Converts an AdminDto to an Admin entity.
     * @param dto The AdminDto.
     * @return The corresponding Admin entity.
     */
    public Admin toEntity(AdminDto dto) {
        Admin admin = new Admin();
        admin.setTitle(dto.getTitle());
        admin.setHireDate(dto.getHireDate());
        admin.setOfficeLocation(dto.getOfficeLocation());
        admin.setAccessibilityPreferences(dto.getAccessibilityPreferences());
        admin.setWorkSchedule(dto.getWorkSchedule());
        admin.setPreferences(dto.getPreferences());
        admin.setSuperAdmin(dto.getSuperAdmin());
        return admin;
    }
}

