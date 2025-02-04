package learning_management_system.backend.service;


import learning_management_system.backend.dto.LmsNotificationDto;
import learning_management_system.backend.enums.NotificationStatus;
import learning_management_system.backend.enums.RelatedEntityType;

import java.util.List;
import java.util.Map;

public interface LmsNotificationService {

    LmsNotificationDto createNotification(LmsNotificationDto notificationDto);

//    void createAndSendNotification(Long recipientId, String title, String message,
//                                   RelatedEntityType entityType, Long entityId);

    void createAndSendNotification(Long userId, String message, Long entityId,
                                   RelatedEntityType entityType, String metadata
    );

    List<LmsNotificationDto> getNotificationsByUserId(Long userId);

    void markAllNotificationsAsRead(Long userId);

    void markNotificationAsRead(Long notificationId);

    void sendBulkNotifications(List<LmsNotificationDto> notifications);

    List<LmsNotificationDto> getNotificationsForEntity(Long entityId, String entityType);

    List<LmsNotificationDto> getNotificationsForStaff(Long staffId);

    List<LmsNotificationDto> getNotificationsForStudent(Long studentId);

    List<LmsNotificationDto> getNotificationsForAdmin(Long adminId);

    void deleteNotification(Long notificationId);

    List<LmsNotificationDto> getNotificationsByTenant(Long tenantId);

    List<LmsNotificationDto> getUnreadNotificationsByUser(Long userId);

    void notifyUser(Long userId, String message, Long linkedEntityId, String linkedEntityType);

    void scheduleRecurringNotifications();

    void sendScheduledNotifications();

    void trackNotificationClick(Long notificationId);

    void cleanUpExpiredNotifications();

    void applyTemplateToNotification(Long notificationId);

    List<LmsNotificationDto> getLocalizedNotifications(Long userId);

    LmsNotificationDto createNotificationFromTemplate(String templateName,
                                                      Map<String, String> placeholders, Long recipientId);

    List<LmsNotificationDto> getNotificationsByType(String type);

    List<LmsNotificationDto> filterNotifications(String type, NotificationStatus status);

    void notifyStudent(Long studentId, String subject, String body, String senderEmail, String senderPassword);

    void notifyAdmins(String message);

//    void sendNotifications(Announcement announcement, Set<User> recipients);
//    LmsNotificationDto updateNotification(Long id, LmsNotificationDto notificationDto);


}
