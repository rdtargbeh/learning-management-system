package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.NotificationDeliveryMethod;
import learning_management_system.backend.enums.NotificationStatus;
import learning_management_system.backend.enums.NotificationType;
import learning_management_system.backend.enums.PriorityUrgency;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

/**
 * Represents a notification in the LMS system. This utility entity serves
 * diverse use cases such as system-wide alerts, course updates, assignment
 * reminders, and more. Notifications are dynamic, extensible, and track user
 * engagement and delivery statuses.
 */

@CrossOrigin("*")
@Entity
@Table (name = "notifications")
public class LmsNotification {

    /** Unique identifier for the notification. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false, updatable = false)
    private Long notificationId;

    /** Title or summary of the notification. */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /** Detailed message content of the notification. */
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    /** Type of the notification (e.g., SYSTEM, COURSE, USER, QUIZ, EXAM). */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    /** Urgency level of the notification (e.g., HIGH, MEDIUM, LOW). */
    @Enumerated(EnumType.STRING)
    @Column(name = "urgency", nullable = false, length = 20)
    private PriorityUrgency urgency;

    /** Delivery method (e.g., EMAIL, SMS, IN_APP, PUSH). */
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", nullable = false, length = 20)
    private NotificationDeliveryMethod deliveryMethod;

    /** Status of the notification (e.g., SENT, FAILED, PENDING). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationStatus status;

    /** Indicates whether the notification has been read by the user. */
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    /** Timestamp when the notification was read. */
    @Column(name = "date_read")
    private LocalDateTime dateRead;

    /** Timestamp when the notification was sent. */
    @Column(name = "date_sent", nullable = false)
    private LocalDateTime dateSend;

    /** Timestamp when the notification is scheduled to be sent. */
    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    /** Timestamp when the notification expires. */
    @Column(name = "expires_date")
    private LocalDateTime expiresDate;

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;

    /** Recurrence pattern for recurring notifications (e.g., DAILY, WEEKLY). */
    @Column(name = "recurrence", length = 50)
    private String recurrence;

    /** URL or route for taking action based on the notification. */
    @Column(name = "action_link", length = 255)
    private String actionLink;

    /** Metadata for extensibility and additional context. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Attachments associated with the notification (e.g., PDFs, images). */
    @Column(name = "attachments", columnDefinition = "TEXT")
    private String attachments;

    /** Localized message content for multi-language support. */
    @Column(name = "localized_message", columnDefinition = "JSON")
    private String localizedMessage;

    /** Number of clicks on the notification (e.g., in-app or email link). */
    @Column(name = "clicks", nullable = false)
    private Integer clicks = 0;

    /** Timestamp of the last click on the notification. */
    @Column(name = "last_clicked_at")
    private LocalDateTime lastClickedAt;

    /** ID of the related entity (e.g., Course, Assignment). */
    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    /** Type of the related entity (e.g., "COURSE", "ASSIGNMENT"). */
    @Column(name = "related_entity_type", length = 50)
    private String relatedEntityType;

    /** The recipient user for this notification. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User recipientUser;

    /** The tenant associated with this notification. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = true)
    private StudentGroup group;
//
//    /** ID of the group to which the notification is broadcast (e.g., course, role). */
//    @Column(name = "group_id")
//    private Long groupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    /** Template ID for notifications using pre-defined templates. */
    @Column(name = "template_id")
    private Long templateId;


    // Constructors
    public LmsNotification(){}

    public LmsNotification(Long notificationId, String title, String message, NotificationType type, PriorityUrgency urgency,
                           NotificationDeliveryMethod deliveryMethod, NotificationStatus status, Boolean isRead, LocalDateTime dateRead,
                           LocalDateTime dateSend, LocalDateTime scheduledDate, LocalDateTime expiresDate, String failureReason,
                           String recurrence, String actionLink, String metadata, String attachments, String localizedMessage,
                           Integer clicks, LocalDateTime lastClickedAt, Long relatedEntityId, String relatedEntityType,
                           User recipientUser, Tenant tenant, Long groupId, Long templateId, User recipient, StudentGroup group) {
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
        this.failureReason = failureReason;
        this.recurrence = recurrence;
        this.actionLink = actionLink;
        this.metadata = metadata;
        this.attachments = attachments;
        this.localizedMessage = localizedMessage;
        this.clicks = clicks;
        this.lastClickedAt = lastClickedAt;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
        this.recipientUser = recipientUser;
        this.tenant = tenant;
//        this.groupId = groupId;
        this.templateId = templateId;
        this.recipient = recipient;
        this.group = group;
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

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
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

    public User getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipientUser = recipientUser;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

//    public Long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Long groupId) {
//        this.groupId = groupId;
//    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }
}
