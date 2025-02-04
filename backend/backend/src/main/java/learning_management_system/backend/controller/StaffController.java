package learning_management_system.backend.controller;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.StaffDto;
import learning_management_system.backend.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;


    // Build A Get All Staff REST API
    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    // Build A Get Staff By ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }

    // Build A Create Staff REST API
    @PostMapping
    public ResponseEntity<StaffDto> createStaff(@RequestBody StaffDto staffDto) {
        return ResponseEntity.ok(staffService.createStaff(staffDto));
    }

    // Build An Update Staff REST API
    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable Long id, @RequestBody StaffDto staffDto) {
        return ResponseEntity.ok(staffService.updateStaff(id, staffDto));
    }

    // Build A Get Staff By Department REST API
    @GetMapping("/department/{department}")
    public ResponseEntity<List<StaffDto>> getStaffByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(staffService.getStaffByDepartment(department));
    }

    // Build A Get Active Staff  REST API
    @GetMapping("/active")
    public ResponseEntity<List<StaffDto>> getActiveStaff() {
        return ResponseEntity.ok(staffService.getActiveStaff());
    }

    // Build A Get Staff Activity Logs REST API
    @GetMapping("/{staffId}/activity-logs")
    public ResponseEntity<List<ActivityLogsDto>> getStaffActivityLogs(@PathVariable Long staffId) {
        List<ActivityLogsDto> activityLogs = staffService.getStaffActivityLogs(staffId);
        return ResponseEntity.ok(activityLogs);
    }

    // Build A Delete Staff REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.ok("Staff and associated User deleted successfully.");
    }


    // Build A Delete Staff REST API
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
//        staffService.deleteStaff(id);
//        return ResponseEntity.noContent().build();
//    }

}
