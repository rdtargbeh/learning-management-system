package learning_management_system.backend.controller;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.dto.DepartmentDto;
import learning_management_system.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> updateStaff(@PathVariable Long departmentId, @RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, departmentDto));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) {
        try {
            departmentService.deleteDepartment(departmentId);
            return ResponseEntity.ok("Department has been deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Build A Get Staff Activity Logs REST API
    @GetMapping("/{departmentId}/activity-logs")
    public ResponseEntity<List<ActivityLogsDto>> getDepartmentActivityLogs(@PathVariable Long departmentId) {
        List<ActivityLogsDto> activityLogs = departmentService.getDepartmentActivityLogs(departmentId);
        return ResponseEntity.ok(activityLogs);
    }
}
