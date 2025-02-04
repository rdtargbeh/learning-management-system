package learning_management_system.backend.service;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service interface for managing activity logs.
 */

public interface ActivityLogsService {

    /**
     * Creates a new activity log.
     * @param activityLogDto The DTO containing log details.
     * @return The created ActivityLogDto.
     */
    ActivityLogsDto createActivityLog(ActivityLogsDto activityLogDto);

    /**
     * Retrieves logs for a specific user.
     * @param userId The ID of the user.
     * @return A list of ActivityLogDto.
     */
    List<ActivityLogsDto> getActivityLogsByUserId(Long userId);

    List<ActivityLogsDto> getActivityLogsByActionType(String activityType);

    List<ActivityLogsDto> getActivityLogsBetweenTimestamps(Date start, Date end);

    void deleteActivityLog(Long logId);

    List<ActivityLogsDto> getLogsForStudent(Long studentId);

    List<ActivityLogsDto> getLogsForAdmin(Long adminId);

    List<ActivityLogsDto> getLogsForStaff(Long staffId);

    /**
     * Retrieves logs by category and a date range.
     * @param category The category of the action.
     * @param start The start of the date range.
     * @param end The end of the date range.
     * @return A list of ActivityLogDto.
     */
    List<ActivityLogsDto> getLogsByCategoryAndDateRange(String category, Date start, Date end);

    /**
     * Retrieves logs for a specific resource.
     * @param resourceType The type of the resource.
     * @param resourceId The ID of the resource.
     * @return A list of ActivityLogDto.
     */
    List<ActivityLogsDto> getLogsByResource(String resourceType, Long resourceId);

    /**
     * Retrieves logs by IP address.
     * @param ipAddress The IP address to search for.
     * @return A list of ActivityLogDto.
     */
    List<ActivityLogsDto> getLogsByIpAddress(String ipAddress);

    /**
     * Retrieves all activity logs.
     * @return A list of ActivityLogDto.
     */
    List<ActivityLogsDto> getAllLogs();

    List<ActivityLogsDto> getActivityLogsForEnrollment(Long enrollmentId);

    String generateActionMetadata(String action, String reason, String additionalDetails);


    void logAction(User user, ActionCategory actionCategory, ActionType actionType,
                   String resourceType, Long resourceId, Boolean status,
                   String ipAddress, Map<String, Object> metadata);

}

