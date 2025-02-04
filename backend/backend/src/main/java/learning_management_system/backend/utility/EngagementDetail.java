package learning_management_system.backend.utility;

import java.time.LocalDateTime;

/**
 * Represents individual engagement actions in a discussion.
 */
public class EngagementDetail {
    private Long userId;
    private String actionType; // e.g., VIEW, REPLY
    private LocalDateTime timestamp;

    // Constructor
    public EngagementDetail(Long userId, String actionType, LocalDateTime timestamp) {
        this.userId = userId;
        this.actionType = actionType;
        this.timestamp = timestamp;
    }

    // Getter and Setter

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
