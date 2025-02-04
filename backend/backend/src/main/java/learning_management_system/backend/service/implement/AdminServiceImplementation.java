package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AdminDto;
import learning_management_system.backend.entity.Admin;
import learning_management_system.backend.entity.Tenant;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.mapper.AdminMapper;
import learning_management_system.backend.repository.AdminRepository;
import learning_management_system.backend.repository.TenantRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private AdminMapper adminMapper;


    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        User user = userRepository.findById(adminDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + adminDto.getUserId()));
        Tenant tenant = tenantRepository.findById(adminDto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + adminDto.getTenantId()));

        Admin admin = adminMapper.toEntity(adminDto);
        admin.setUser(user);
        admin.setTenant(tenant);

        return adminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public AdminDto updateAdmin(Long adminId, AdminDto adminDto) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with ID: " + adminId));

        adminMapper.toEntity(adminDto);
        return adminMapper.toDto(adminRepository.save(admin));
    }

    @Override
    public AdminDto getAdminByUserId(Long userId) {
        Admin admin = adminRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found for user ID: " + userId));
        return adminMapper.toDto(admin);
    }

    @Override
    public List<AdminDto> getAdminsByTenant(Long tenantId) {
        List<Admin> admins = adminRepository.findByTenant_TenantId(tenantId);
        return admins.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AdminDto> getAdminsByLevel(String adminLevel) {
        List<Admin> admins = adminRepository.findByAdminLevel(adminLevel);
        return admins.stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminDto> getAdminsByDepartment(Long departmentId) {
        return adminRepository.findByDepartmentDepartmentId(departmentId).stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteAdmin(Long adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new RuntimeException("Admin not found with ID: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }


//    @Override
//    public AdminDto createAdmin(AdminDto adminDto, Long userId, Long tenantId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
//
//        Tenant tenant = null;
//        if (tenantId != null) {
//            tenant = tenantRepository.findById(tenantId)
//                    .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));
//        }
//
//        Admin admin = adminMapper.toEntity(adminDto, user, tenant);
//        Admin savedAdmin = adminRepository.save(admin);
//        return adminMapper.toDto(savedAdmin);
//    }


}
