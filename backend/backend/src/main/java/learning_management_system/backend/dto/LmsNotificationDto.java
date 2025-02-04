package learning_management_system.backend.dto;

import learning_management_system.backend.enums.NotificationDeliveryMethod;
import learning_management_system.backend.enums.NotificationStatus;
import learning_management_system.backend.enums.NotificationType;
import learning_management_system.backend.enums.PriorityUrgency;

import java.time.LocalDateTime;

public class LmsNotificationDto {

    private Long notificationId;
    private String title;
    private String message;
    private NotificationType type;
    private PriorityUrgency urgency;
    private NotificationDeliveryMethod deliveryMethod;
    private NotificationStatus status;
    private Boolean isRead;
    private LocalDateTime dateRead;
    private LocalDateTime dateSend;
    private LocalDateTime scheduledDate;
    private LocalDateTime expiresDate;
    private String recurrence;
    private String actionLink;
    private String metadata;
    private String attachments;
    private String localizedMessage;
    private Integer clicks;
    private LocalDateTime lastClickedAt;
    private Long relatedEntityId;
    private String relatedEntityType;
    private Long recipientUserId;
    private Long tenantId;
    private Long groupId;
    private Long templateId;


    // Constructors
    public LmsNotificationDto() {}

    public LmsNotificationDto(Long notificationId, String title, String message, NotificationType type, PriorityUrgency urgency, NotificationDeliveryMethod deliveryMethod, NotificationStatus status, Boolean isRead, LocalDateTime dateRead, LocalDateTime dateSend, LocalDateTime scheduledDate, LocalDateTime expiresDate, String recurrence, String actionLink, String metadata, String attachments, String localizedMessage, Integer clicks, LocalDateTime lastClickedAt, Long relatedEntityId, String relatedEntityType, Long recipientUserId, Long tenantId, Long groupId, Long templateId) {
        this.notificationId = notificationId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.urgency = urgency;
        this.deliveryMethod = deliveryMethod;
        this.status = status;
        this.isRead = isRead;
        this.dateRead = dateRead;
        this.dateSend = dateSend;
        this.scheduledDate = scheduledDate;
        this.expiresDate = expiresDate;
        this.recurrence = recurrence;
        this.actionLink = actionLink;
        this.metadata = metadata;
        this.attachments = attachments;
        this.localizedMessage = localizedMessage;
        this.clicks = clicks;
        this.lastClickedAt = lastClickedAt;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
        this.recipientUserId = recipientUserId;
        this.tenantId = tenantId;
        this.groupId = groupId;
        this.templateId = templateId;
    }

// Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public PriorityUrgency getUrgency() {
        return urgency;
    }

    public void setUrgency(PriorityUrgency urgency) {
        this.urgency = urgency;
    }

    public NotificationDeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(NotificationDeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public LocalDateTime getDateRead() {
        return dateRead;
    }

    public void setDateRead(LocalDateTime dateRead) {
        this.dateRead = dateRead;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDateTime getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(LocalDateTime expiresDate) {
        this.expiresDate = expiresDate;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getActionLink() {
        return actionLink;
    }

    public void setActionLink(String actionLink) {
        this.actionLink = actionLink;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public LocalDateTime getLastClickedAt() {
        return lastClickedAt;
    }

    public void setLastClickedAt(LocalDateTime lastClickedAt) {
        this.lastClickedAt = lastClickedAt;
    }

    public Long getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(Long relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
