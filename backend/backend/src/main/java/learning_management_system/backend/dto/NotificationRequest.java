package learning_management_system.backend.dto;

import learning_management_system.backend.enums.RelatedEntityType;

public class NotificationRequest {
    private Long userId;
    private String message;
    private Long entityId;
    private RelatedEntityType entityType; // Enum
    private String metadata;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public RelatedEntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(RelatedEntityType entityType) {
        this.entityType = entityType;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
