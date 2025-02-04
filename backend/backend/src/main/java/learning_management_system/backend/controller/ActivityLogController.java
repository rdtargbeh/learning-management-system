package learning_management_system.backend.controller;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.service.ActivityLogsService;
import learning_management_system.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogsService activityLogsService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ActivityLogsDto> createActivityLog(@RequestBody ActivityLogsDto activityLogDto) {
        ActivityLogsDto createdLog = activityLogsService.createActivityLog(activityLogDto);
        return ResponseEntity.ok(createdLog);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLogsDto>> getActivityLogsByUserId(@PathVariable Long userId) {
        List<ActivityLogsDto> activityLogs = activityLogsService.getActivityLogsByUserId(userId);
        return ResponseEntity.ok(activityLogs);
    }

    @GetMapping("/type/{activityType}")
    public ResponseEntity<List<ActivityLogsDto>> getActivityLogsByActionType(@PathVariable String activityType) {
        List<ActivityLogsDto> activityLogs = activityLogsService.getActivityLogsByActionType(activityType);
        return ResponseEntity.ok(activityLogs);
    }

    @GetMapping("/timestamps")
    public ResponseEntity<List<ActivityLogsDto>> getActivityLogsBetweenTimestamps(
            @RequestParam Date start, @RequestParam Date end) {
        List<ActivityLogsDto> activityLogs = activityLogsService.getActivityLogsBetweenTimestamps(start, end);
        return ResponseEntity.ok(activityLogs);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long logId) {
        activityLogsService.deleteActivityLog(logId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ActivityLogsDto>> getLogsForStudent(@PathVariable Long studentId) {
        List<ActivityLogsDto> logs = activityLogsService.getLogsForStudent(studentId);
        return ResponseEntity.ok(logs);
    }

    // Endpoint to get logs for a staff member
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<ActivityLogsDto>> getLogsForStaff(@PathVariable Long staffId) {
        List<ActivityLogsDto> logs = activityLogsService.getLogsForStaff(staffId);
        return ResponseEntity.ok(logs);
    }

    // Endpoint to get logs for an admin
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ActivityLogsDto>> getLogsForAdmin(@PathVariable Long adminId) {
        List<ActivityLogsDto> logs = activityLogsService.getLogsForAdmin(adminId);
        return ResponseEntity.ok(logs);
    }


    /**
     * Retrieves logs by category and date range.
     * @param category The action category to filter by.
     * @param start The start of the date range.
     * @param end The end of the date range.
     * @return A list of ActivityLogDto matching the filters.
     */
    @GetMapping("/category")
    public ResponseEntity<List<ActivityLogsDto>> getLogsByCategoryAndDateRange(
            @RequestParam String category,
            @RequestParam Date start,
            @RequestParam Date end) {
        return ResponseEntity.ok(activityLogsService.getLogsByCategoryAndDateRange(category, start, end));
    }

    /**
     * Retrieves logs for a specific resource.
     * @param resourceType The type of the resource.
     * @param resourceId The ID of the resource.
     * @return A list of ActivityLogDto for the specified resource.
     */
    @GetMapping("/resource")
    public ResponseEntity<List<ActivityLogsDto>> getLogsByResource(
            @RequestParam String resourceType,
            @RequestParam Long resourceId) {
        return ResponseEntity.ok(activityLogsService.getLogsByResource(resourceType, resourceId));
    }

    /**
     * Retrieves logs filtered by IP address.
     * @param ipAddress The IP address to filter logs.
     * @return A list of ActivityLogDto for the specified IP address.
     */
    @GetMapping("/ip")
    public ResponseEntity<List<ActivityLogsDto>> getLogsByIpAddress(@RequestParam String ipAddress) {
        return ResponseEntity.ok(activityLogsService.getLogsByIpAddress(ipAddress));
    }


    /**
     * Retrieves all activity logs.
     * @return A list of all ActivityLogDto in the system.
     */
    @GetMapping
    public ResponseEntity<List<ActivityLogsDto>> getAllLogs() {
        return ResponseEntity.ok(activityLogsService.getAllLogs());
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<ActivityLogsDto>> getLogsForEnrollment(@PathVariable Long enrollmentId) {
        List<ActivityLogsDto> logs = activityLogsService.getActivityLogsForEnrollment(enrollmentId);
        return ResponseEntity.ok(logs);
    }

    /**
     * Endpoint to generate action metadata.
     *
     * @param action The action to log.
     * @param reason The reason for the action.
     * @param additionalDetails Any additional details.
     * @return A ResponseEntity containing the generated metadata.
     */
    @GetMapping("/generate")
    public ResponseEntity<String> generateActionMetadata(
            @RequestParam String action,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) String additionalDetails) {
        String metadata = activityLogsService.generateActionMetadata(action, reason, additionalDetails);
        return ResponseEntity.ok(metadata);
    }


//    /**
//     * Endpoint to log an action in the activity log.
//     *
//     * @param userId         The ID of the user performing the action.
//     * @param actionCategory The category of the action.
//     * @param actionType     The specific type of action.
//     * @param resourceType   The type of resource affected.
//     * @param resourceId     The ID of the resource affected.
//     * @param status         The success status of the action.
//     * @param ipAddress      The IP address from which the action was performed.
//     * @param customAction   Custom action name, if any.
//     * @param metadata       Additional metadata for the action.
//     * @return A ResponseEntity indicating the result of the operation.
//     */
//    @PostMapping("/log")
//    public ResponseEntity<String> logAction(
//            @RequestParam Long userId,
//            @RequestParam(required = false) ActionCategory actionCategory,
//            @RequestParam(required = false) ActionType actionType,
//            @RequestParam String resourceType,
//            @RequestParam(required = false) Long resourceId,
//            @RequestParam(required = false) Boolean status,
//            @RequestParam(required = false) String ipAddress,
//            @RequestParam(required = false) String customAction,
//            @RequestBody(required = false) Map<String, Object> metadata) {
//
//        // Retrieve the user using UserService
//        User user = userService.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        // Log the action using the service method
//        activityLogService.logAction(user, actionCategory, actionType, resourceType, resourceId, status, ipAddress, metadata, customAction);
//
//        return ResponseEntity.ok("Action logged successfully.");
//    }


}

