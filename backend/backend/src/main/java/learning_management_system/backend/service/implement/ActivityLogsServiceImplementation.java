package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.entity.ActivityLogs;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;
import learning_management_system.backend.mapper.ActivityLogMappers;
import learning_management_system.backend.repository.ActivityLogsRepository;
import learning_management_system.backend.repository.UserRepository;
import learning_management_system.backend.service.ActivityLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActivityLogsServiceImplementation implements ActivityLogsService {

    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    @Autowired
    private ActivityLogMappers activityLogMappers;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ActivityLogsDto createActivityLog(ActivityLogsDto activityLogDto) {
        User user = userRepository.findById(activityLogDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + activityLogDto.getUserId()));

        ActivityLogs activityLogs = activityLogMappers.toEntity(activityLogDto);
        ActivityLogs savedActivityLog = activityLogsRepository.save(activityLogs);
        return activityLogMappers.toDto(savedActivityLog);
    }

    @Override
    public List<ActivityLogsDto> getActivityLogsByUserId(Long userId) {
        List<ActivityLogs> activityLogs = activityLogsRepository.findByUser_UserId(userId);
        return activityLogs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityLogsDto> getActivityLogsByActionType(String actionType) {
        List<ActivityLogs> activityLogs = activityLogsRepository.findByActionType(actionType);
        return activityLogs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityLogsDto> getActivityLogsBetweenTimestamps(Date start, Date end) {
        List<ActivityLogs> activityLogs = activityLogsRepository.findByTimestampBetween(start, end);
        return activityLogs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteActivityLog(Long logId) {
        if (!activityLogsRepository.existsById(logId)) {
            throw new IllegalArgumentException("ActivityLog not found with ID: " + logId);
        }
        activityLogsRepository.deleteById(logId);
    }

    public List<ActivityLogsDto> getLogsForStudent(Long studentId) {
        List<ActivityLogs> logs = activityLogsRepository.findLogsForStudent(studentId);
        return logs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    // Get logs for a specific staff member
    public List<ActivityLogsDto> getLogsForStaff(Long staffId) {
        List<ActivityLogs> logs = activityLogsRepository.findLogsForStaff(staffId);
        return logs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }



    // Get logs for a specific admin
    public List<ActivityLogsDto> getLogsForAdmin(Long adminId) {
        List<ActivityLogs> logs = activityLogsRepository.findLogsForAdmin(adminId);
        return logs.stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves logs by category and date range.
     * @param category The category of the action.
     * @param start The start of the date range.
     * @param end The end of the date range.
     * @return A list of ActivityLogDto.
     */
    @Override
    public List<ActivityLogsDto> getLogsByCategoryAndDateRange(String category, Date start, Date end) {
        return activityLogsRepository.findByActionCategoryAndTimestampBetween(ActionCategory.valueOf(category), start, end).stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves logs for a specific resource type and ID.
     * @param resourceType The type of resource.
     * @param resourceId The ID of the resource.
     * @return A list of ActivityLogDto.
     */
    @Override
    public List<ActivityLogsDto> getLogsByResource(String resourceType, Long resourceId) {
        return activityLogsRepository.findByResourceTypeAndResourceId(resourceType, resourceId).stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves logs by IP address.
     * @param ipAddress The IP address to filter logs.
     * @return A list of ActivityLogDto.
     */
    @Override
    public List<ActivityLogsDto> getLogsByIpAddress(String ipAddress) {
        return activityLogsRepository.findByIpAddress(ipAddress).stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all activity logs in the system.
     * @return A list of ActivityLogDto.
     */
    @Override
    public List<ActivityLogsDto> getAllLogs() {
        return activityLogsRepository.findAll().stream()
                .map(activityLogMappers::toDto)
                .collect(Collectors.toList());
    }

    public List<ActivityLogsDto> getActivityLogsForEnrollment(Long enrollmentId) {
        List<ActivityLogs> logs = activityLogsRepository.findByResourceTypeAndResourceId("ENROLLMENT", enrollmentId);
        return logs.stream()
                .map(activityLog -> new ActivityLogsDto(
                        activityLog.getLogId(),
                        activityLog.getUser().getUserId(),
                        activityLog.getUser().getUserName(), // Fetch the username from the User entity
                        activityLog.getActionCategory().toString(),
                        activityLog.getActionType().toString(),
                        activityLog.getIpAddress(),
                        activityLog.getStatus(),
                        activityLog.getResourceId(),
                        activityLog.getResourceType(),
                        activityLog.getMetadata(),
                        activityLog.getTimestamp()
                ))
                .collect(Collectors.toList());
    }


    /**
     * Generates structured metadata for activity logs.
     *
     * @param action the action performed (e.g., "GRADE_LOCKED")
     * @param reason the reason for the action
     * @param additionalDetails any additional details for the action
     * @return serialized JSON metadata string
     */
    public String generateActionMetadata(String action, String reason, String additionalDetails) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("action", action);
        metadata.put("reason", reason);
        metadata.put("details", additionalDetails);
        try {
            return new ObjectMapper().writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize metadata", e);
        }
    }


    @Transactional
    public void logAction(User user, ActionCategory actionCategory, ActionType actionType,
                          String resourceType, Long resourceId, Boolean status,
                          String ipAddress, Map<String, Object> metadata) {
        ActivityLogs log = new ActivityLogs();
        log.setUser(user);
        log.setActionCategory(actionCategory);
        log.setActionType(actionType);
        log.setResourceType(resourceType);
        log.setResourceId(resourceId);
        log.setStatus(status);
        log.setIpAddress(ipAddress);
        log.setTimestamp(new Date());
        log.setMetadata(metadata != null ? metadata : new HashMap<>());
        activityLogsRepository.save(log);
    }


    // use this one below or one above later

//    @Transactional
//    public void logAction(User user, ActionCategory actionCategory, ActionType actionType,
//                          String resourceType, Long resourceId, Boolean status, String ipAddress,
//                          Map<String, Object> metadata, String customAction) {
//        ActivityLog log = new ActivityLog();
//
//        // User details
//        log.setUser(user);
//
//        // Action details
//        if (actionCategory != null) log.setActionCategory(actionCategory);
//        if (actionType != null) log.setActionType(actionType);
//        log.setActionType(ActionType.valueOf(customAction != null ? customAction : actionType != null ? actionType.name() : "UNSPECIFIED_ACTION"));
//
//        // Resource details
//        log.setResourceType(resourceType);
//        log.setResourceId(resourceId);
//
//        // Optional fields
//        log.setStatus(status != null ? status : true);
//        log.setIpAddress(ipAddress != null ? ipAddress : "N/A");
//
//        // Metadata
//        log.setMetadata(metadata != null ? metadata : new HashMap<>());
//
//        // Timestamp
//        log.setTimestamp(new Date());
//
//        // Save log entry
//        activityLogRepository.save(log);
//    }


}
