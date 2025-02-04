package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.ActivityLogsDto;
import learning_management_system.backend.entity.ActivityLogs;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between ActivityLog entities and ActivityLog DTOs.
 */

@Component
public class ActivityLogMappers {

    /**
     * Converts an ActivityLog entity to an ActivityLogDto.
     * @param log The ActivityLog entity.
     * @return The corresponding ActivityLogDto.
     */
    public ActivityLogsDto toDto(ActivityLogs log) {
        ActivityLogsDto dto = new ActivityLogsDto();
        dto.setLogId(log.getLogId());
        dto.setUserId(log.getUser().getUserId());
        dto.setUserName(log.getUser().getUserName());
        dto.setActionCategory(log.getActionCategory().name());
        dto.setActionType(log.getActionType().name());
        dto.setIpAddress(log.getIpAddress());
        dto.setStatus(log.getStatus());
        dto.setResourceType(log.getResourceType());
        dto.setResourceId(log.getResourceId());
        dto.setMetadata(log.getMetadata());
        dto.setTimestamp(log.getTimestamp());
        return dto;
    }

    /**
     * Converts an ActivityLogDto to an ActivityLog entity.
     * @param dto The ActivityLogDto.
     * @return The corresponding ActivityLog entity.
     */
    public ActivityLogs toEntity(ActivityLogsDto dto) {
        ActivityLogs log = new ActivityLogs();
        log.setIpAddress(dto.getIpAddress());
        log.setStatus(dto.getStatus());
        log.setResourceType(dto.getResourceType());
        log.setResourceId(dto.getResourceId());
        log.setMetadata(dto.getMetadata());
        return log;
    }


}

