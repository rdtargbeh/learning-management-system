package learning_management_system.backend.controller;

import learning_management_system.backend.dto.LmsNotificationDto;
import learning_management_system.backend.dto.NotificationRequest;
import learning_management_system.backend.enums.NotificationStatus;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class LmsNotificationController {

    @Autowired
    private LmsNotificationService notificationService;



    /**
     * Create a new notification.
     *
     * @param notificationDto the notification details.
     * @return the created notification.
     */
    @PostMapping
    public ResponseEntity<LmsNotificationDto> createNotification(@RequestBody LmsNotificationDto notificationDto) {
        LmsNotificationDto createdNotification = notificationService.createNotification(notificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }


    /**
     * API endpoint to create and send a notification.
     *
     * @param notificationRequest The request payload containing notification details.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/send")
    public ResponseEntity<String> createAndSendNotification(@RequestBody NotificationRequest notificationRequest) {
        try {
            // Call the service method
            notificationService.createAndSendNotification(
                    notificationRequest.getUserId(),
                    notificationRequest.getMessage(),
                    notificationRequest.getEntityId(),
                    notificationRequest.getEntityType(),
                    notificationRequest.getMetadata()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body("Notification sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send notification: " + e.getMessage());
        }
    }


    /**
     * Get notifications by user ID.
     * General endpoint for any user
     * @param userId the user ID.
     * @return the list of notifications.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsByUserId(@PathVariable Long userId) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications by user ID.
     *
     * @param userId the user ID.
     * @return the list of unread notifications.
     */
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<LmsNotificationDto>> getUnreadNotificationsByUserId(@PathVariable Long userId) {
        List<LmsNotificationDto> notifications = notificationService.getUnreadNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }


    /**
     * Get notifications for a specific tenant.
     *
     * @param tenantId the tenant ID.
     * @return the list of notifications.
     */
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsByTenant(@PathVariable Long tenantId) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsByTenant(tenantId);
        return ResponseEntity.ok(notifications);
    }


    /**
     * Mark a notification as read.
     *
     * @param notificationId the notification ID.
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark all notifications for a user as read.
     *
     * @param userId the user ID.
     */
    @PutMapping("/user/{userId}/mark-all-read")
    public ResponseEntity<Void> markAllNotificationsAsRead(@PathVariable Long userId) {
        notificationService.markAllNotificationsAsRead(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a notification.
     *
     * @param notificationId the notification ID.
     */
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get localized notifications for a user.
     *
     * @param userId the user ID.
     * @return the list of localized notifications.
     */
    @GetMapping("/user/{userId}/localized")
    public ResponseEntity<List<LmsNotificationDto>> getLocalizedNotifications(@PathVariable Long userId) {
        List<LmsNotificationDto> localizedNotifications = notificationService.getLocalizedNotifications(userId);
        return ResponseEntity.ok(localizedNotifications);
    }

    /**
     * Schedule recurring notifications.
     */
    @PostMapping("/schedule-recurring")
    public ResponseEntity<Void> scheduleRecurringNotifications() {
        notificationService.scheduleRecurringNotifications();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Track notification clicks.
     *
     * @param notificationId the notification ID.
     */
    @PutMapping("/{notificationId}/track-click")
    public ResponseEntity<Void> trackNotificationClick(@PathVariable Long notificationId) {
        notificationService.trackNotificationClick(notificationId);
        return ResponseEntity.noContent().build();
    }


    /**
     * Clean up expired notifications.
     */
    @DeleteMapping("/expired")
    public ResponseEntity<Void> cleanUpExpiredNotifications() {
        notificationService.cleanUpExpiredNotifications();
        return ResponseEntity.noContent().build();
    }


    /**
     * Create notification from a template.
     *
     * @param templateName the template name.
     * @param placeholders the placeholders for the template.
     * @param recipientId  the recipient ID.
     * @return the created notification.
     */
    @PostMapping("/from-template")
    public ResponseEntity<LmsNotificationDto> createNotificationFromTemplate(
            @RequestParam String templateName,
            @RequestBody Map<String, String> placeholders,
            @RequestParam Long recipientId) {
        LmsNotificationDto notification = notificationService.createNotificationFromTemplate(templateName, placeholders, recipientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<LmsNotificationDto>> getStaffNotifications(@PathVariable Long staffId) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsForStaff(staffId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsForStudent(@PathVariable Long studentId) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsForStudent(studentId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsForAdmin(@PathVariable Long adminId) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsForAdmin(adminId);
        return ResponseEntity.ok(notifications);
    }


    // Endpoint to get notifications linked to a specific entity
    @GetMapping("/entity/{entityId}/{entityType}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsForEntity(@PathVariable Long entityId, @PathVariable String entityType) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsForEntity(entityId, entityType);
        return ResponseEntity.ok(notifications);
    }

    // Endpoint to get notifications of a specific type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LmsNotificationDto>> getNotificationsByType(@PathVariable String type) {
        List<LmsNotificationDto> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Send bulk notifications.
     *
     * @param notifications List of notifications to send.
     */
    @PostMapping("/bulk-send")
    public ResponseEntity<Void> sendBulkNotifications(@RequestBody List<LmsNotificationDto> notifications) {
        notificationService.sendBulkNotifications(notifications);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Send scheduled notifications.
     */
    @PostMapping("/send-scheduled")
    public ResponseEntity<Void> sendScheduledNotifications() {
        notificationService.sendScheduledNotifications();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Apply a template to an existing notification.
     *
     * @param notificationId ID of the notification to apply the template.
     */
    @PutMapping("/{notificationId}/apply-template")
    public ResponseEntity<Void> applyTemplateToNotification(@PathVariable Long notificationId) {
        notificationService.applyTemplateToNotification(notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<LmsNotificationDto>> filterNotifications(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) NotificationStatus status) {
        List<LmsNotificationDto> notifications = notificationService.filterNotifications(type, status);
        return ResponseEntity.ok(notifications);
    }


    /**
     * Notify a student by sending an email.
     *
     * @param studentId    The ID of the student to notify.
     * @param subject      The subject of the email.
     * @param body         The body of the email.
     * @param senderEmail  The sender's email address.
     * @param senderPassword The sender's email password.
     * @return A response indicating the notification was sent.
     */
    @PostMapping("/student-notify")
    public ResponseEntity<String> notifyStudent(
            @RequestParam Long studentId,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam String senderEmail,
            @RequestParam String senderPassword) {

        // Call the service to send the notification
        notificationService.notifyStudent(studentId, subject, body, senderEmail, senderPassword);

        return ResponseEntity.ok("Notification sent successfully to student ID: " + studentId);
    }


    /**
     * Create and send a notification.
     *
     * @param recipientId the recipient ID.
     * @param title       the title of the notification.
     * @param message     the message content.
     * @param entityType  the related entity type.
     * @param entityId    the related entity ID.
     */
//    @PostMapping("/send")
//    public ResponseEntity<Void> createAndSendNotification(
//            @RequestParam Long recipientId,
//            @RequestParam String title,
//            @RequestParam String message,
//            @RequestParam RelatedEntityType entityType,
//            @RequestParam Long entityId) {
//        notificationService.createAndSendNotification(recipientId, title, message, entityType, entityId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

}