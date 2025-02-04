package learning_management_system.backend.dto;

import java.util.Date;

/**
 * Data Transfer Object for ActivityLog entity.
 * Used to transfer activity log data between layers.
 */
public class ActivityLogsDto {

    /** Unique identifier for the activity log. */
    private Long logId;

    /** ID of the user who performed the action. */
    private Long userId;

    /** Username of the user who performed the action. */
    private String userName;

    /** Category of the action performed. */
    private String actionCategory;

    /** Specific type of action performed. */
    private String actionType;

    /** IP address from which the action was performed. */
    private String ipAddress;

    /** Status indicating success or failure of the action. */
    private Boolean status;

    /** Type of resource affected by the action. */
    private String resourceType;

    /** ID of the affected resource. */
    private Long resourceId;

    /** Metadata or additional details about the action. */
    private String metadata;

    /** Timestamp when the action occurred. */
    private Date timestamp;



    // Constructor
    public ActivityLogsDto(){}

    public ActivityLogsDto(Long logId, Long userId, String userName, String actionCategory, String actionType, String ipAddress,
                          Boolean status, Long resourceId, String resourceType, String metadata, Date timestamp) {
        this.logId = logId;
        this.userId = userId;
        this.userName = userName;
        this.actionCategory = actionCategory;
        this.actionType = actionType;
        this.ipAddress = ipAddress;
        this.status = status;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.metadata = metadata;
        this.timestamp = timestamp;
    }

    // Getter and Setter
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActionCategory() {
        return actionCategory;
    }

    public void setActionCategory(String actionCategory) {
        this.actionCategory = actionCategory;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
