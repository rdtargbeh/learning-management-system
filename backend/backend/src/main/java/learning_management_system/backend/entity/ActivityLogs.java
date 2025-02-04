package learning_management_system.backend.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import learning_management_system.backend.enums.ActionCategory;
import learning_management_system.backend.enums.ActionType;

import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "activity_logs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class ActivityLogs {

    /** Unique identifier for the activity log. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false, updatable = false)
    private Long logId;

    /** The user who performed the action. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The category of the action (e.g., "LOGIN", "COURSE"). */
    @Enumerated(EnumType.STRING)
    @Column(name = "action_category", nullable = false, length = 50)
    private ActionCategory actionCategory;

    /** The specific type of action performed (e.g., "LOGGED_IN", "COURSE_CREATED"). */
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 50)
    private ActionType actionType;

    /** The IP address from which the action was performed. */
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    /** Indicates whether the action was successful (e.g., login success or failure). */
    @Column(name = "status", nullable = false)
    private Boolean status;

    /** The type of resource affected (e.g., "COURSE", "USER"). */
    @Column(name = "resource_type", length = 100)
    private String resourceType;

    /** The ID of the resource affected, if applicable. */
    @Column(name = "resource_id")
    private Long resourceId;


    /** Additional metadata or details about the action. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp when the action occurred. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date timestamp;


    /** Helper method to populate metadata in JSON format. */
    public void setMetadata(Map<String, Object> metadataMap) {
        try {
            this.metadata = new ObjectMapper().writeValueAsString(metadataMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing metadata to JSON", e);
        }
    }


    public Map<String, Object> getMetadataAsMap() {
        try {
            return new ObjectMapper().readValue(this.metadata, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing metadata from JSON", e);
        }
    }

    // Constructor
    public ActivityLogs(){}
    public ActivityLogs(Long logId, User user, ActionCategory actionCategory, ActionType actionType, String ipAddress,
                        Boolean status, String resourceType, Long resourceId, String metadata, Date timestamp) {
        this.logId = logId;
        this.user = user;
        this.actionCategory = actionCategory;
        this.actionType = actionType;
        this.ipAddress = ipAddress;
        this.status = status;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActionCategory getActionCategory() {
        return actionCategory;
    }

    public void setActionCategory(ActionCategory actionCategory) {
        this.actionCategory = actionCategory;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
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
