package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AdminDto;
import learning_management_system.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{userId}")
    public ResponseEntity<AdminDto> getAdminByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getAdminByUserId(userId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<AdminDto>> getAdminsByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(adminService.getAdminsByTenant(tenantId));
    }

    @GetMapping("/level/{adminLevel}")
    public ResponseEntity<List<AdminDto>> getAdminsByLevel(@PathVariable String adminLevel) {
        List<AdminDto> admins = adminService.getAdminsByLevel(adminLevel);
        return ResponseEntity.ok(admins);
    }

    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(adminService.createAdmin(adminDto));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long adminId, @RequestBody AdminDto adminDto) {
        return ResponseEntity.ok(adminService.updateAdmin(adminId, adminDto));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<AdminDto>> getAdminsByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(adminService.getAdminsByDepartment(departmentId));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.noContent().build();
    }


//    @PostMapping
//    public ResponseEntity<AdminDto> createAdmin(
//            @RequestBody AdminDto adminDto,
//            @RequestParam Long userId,
//            @RequestParam(required = false) Long tenantId) {
//        return ResponseEntity.ok(adminService.createAdmin(adminDto, userId, tenantId));
//    }
}

