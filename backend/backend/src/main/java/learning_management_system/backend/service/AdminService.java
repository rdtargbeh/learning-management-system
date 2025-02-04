package learning_management_system.backend.service;

import learning_management_system.backend.dto.AdminDto;

import java.util.List;

/**
 * Service interface for managing Admin entities.
 */

public interface AdminService {

    AdminDto getAdminByUserId(Long userId);

    List<AdminDto> getAdminsByTenant(Long tenantId);

    List<AdminDto> getAdminsByLevel(String adminLevel);

//    AdminDto createAdmin(AdminDto adminDto, Long userId, Long tenantId);

    AdminDto createAdmin(AdminDto adminDto);

    AdminDto updateAdmin(Long adminId, AdminDto adminDto);

    List<AdminDto> getAdminsByDepartment(Long departmentId);

    void deleteAdmin(Long adminId);


}
