package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.LmsNotificationDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.*;
import learning_management_system.backend.mapper.LmsNotificationMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class LmsLmsNotificationServiceImplementation implements LmsNotificationService {
    @Autowired
    private LmsNotificationRepository lmsNotificationRepository;
    @Lazy
    @Autowired
    private  LmsNotificationService lmsNotificationService;
    @Autowired
    private LmsNotificationMapper lmsNotificationMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private  GradingRepository gradingRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PushNotificationService pushNotificationService;
    @Autowired
    private InAppNotificationService inAppNotificationService;
    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;
    @Autowired
    private Map<NotificationDeliveryMethod, NotificationHandler> notificationHandlers;
    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    @Autowired
    private  StudentRepository studentRepository;



    public LmsNotificationDto createNotification(LmsNotificationDto notificationDto) {
        // Validate recipient
        User recipient = validateRecipient(notificationDto.getRecipientUserId());
        validateLinkedEntity(notificationDto.getRelatedEntityType(), notificationDto.getRelatedEntityId());

        // Map DTO to entity
        LmsNotification notification = lmsNotificationMapper.toEntity(notificationDto);
        notification.setRecipientUser(recipient);
        notification.setDateSend(LocalDateTime.now());

        // Dynamic metadata
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("message", notificationDto.getMessage());
        metadata.put("relatedEntityId", notificationDto.getRelatedEntityId());
        metadata.put("timestamp", LocalDateTime.now());

        try {
            notification.setMetadata(new ObjectMapper().writeValueAsString(metadata));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize metadata", e);
        }

        // Handle delivery
        NotificationHandler handler = notificationHandlers.get(notification.getDeliveryMethod());
        if (handler != null) {
            try {
                handler.sendNotification(notification);
                notification.setStatus(NotificationStatus.SENT);
            } catch (Exception e) {
                notification.setStatus(NotificationStatus.FAILED);
                notification.setFailureReason(e.getMessage());
            }
        } else {
            throw new UnsupportedOperationException("Delivery method not supported: " + notification.getDeliveryMethod());
        }

        // Save and log
        lmsNotificationRepository.save(notification);

        logNotificationAction(
                // Access user ID from recipientUser
                notification.getRecipientUser().getUserId(),
                "CREATED",
                "Notification created for entity: " + notification.getRelatedEntityId()
        );

        return lmsNotificationMapper.toDto(notification);
    }


    // Validate Recipient
    private User validateRecipient(Long recipientUserId) {
        return userRepository.findById(recipientUserId)
                .orElseThrow(() -> new RuntimeException("Recipient not found with ID: " + recipientUserId));
    }

    // Validate entity type been linked to eg. discussion, announcement, etc.
    private void validateLinkedEntity(Object entityType, Long entityId) {
        if (entityType == null || entityId == null) {
            throw new IllegalArgumentException("Entity type or ID cannot be null.");
        }

        LinkedEntityType linkedEntityType;

        if (entityType instanceof String) {
            try {
                // Convert String to LinkedEntityType enum
                linkedEntityType = LinkedEntityType.valueOf(((String) entityType).toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unsupported or invalid entity type: " + entityType, e);
            }
        } else if (entityType instanceof LinkedEntityType) {
            linkedEntityType = (LinkedEntityType) entityType;
        } else {
            throw new IllegalArgumentException("Entity type must be a String or LinkedEntityType.");
        }

        // Perform validation based on LinkedEntityType
        switch (linkedEntityType) {
            case COURSE:
                if (!courseRepository.existsById(entityId)) {
                    throw new RuntimeException("Course not found with ID: " + entityId);
                }
                break;
            case ASSIGNMENT:
                if (!assignmentRepository.existsById(entityId)) {
                    throw new RuntimeException("Assignment not found with ID: " + entityId);
                }
                break;
            case COMMENT:
                if (!commentRepository.existsById(entityId)) {
                    throw new RuntimeException("Comment not found with ID: " + entityId);
                }
                break;
            case GRADING:
                if (!gradingRepository.existsById(entityId)) {
                    throw new RuntimeException("Grading not found with ID: " + entityId);
                }
                break;
            default:
                throw new RuntimeException("Unsupported entity type: " + linkedEntityType);
        }
    }


    private void logNotificationAction(Long userId, String action, String description) {
        ActivityLogs log = new ActivityLogs();
        log.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId)));
        log.setActionCategory(ActionCategory.NOTIFICATION);
        log.setActionType(ActionType.valueOf(action)); // Ensure ActionType enum contains the required value
        log.setResourceType("NOTIFICATION");
        log.setResourceId(null); // Optional

        // Create metadata as a Map
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("description", description);
        log.setMetadata(metadata); // Set the metadata map

        log.setStatus(true);
        log.setIpAddress("N/A"); // Update with actual IP if needed
        log.setTimestamp(new Date());
        activityLogsRepository.save(log);
    }


    @Override
    @Transactional
    public void createAndSendNotification(
            Long userId,
            String message,
            Long entityId,
            RelatedEntityType entityType,
            String metadata
    ) {
        // Fetch recipient user
        User recipient = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create a new notification
        LmsNotification notification = new LmsNotification();
        notification.setRecipientUser(recipient); // Associate recipient user
        notification.setMessage(message); // Set notification message
        notification.setRelatedEntityId(entityId); // Associate related entity ID
        notification.setRelatedEntityType(entityType.name()); // Convert enum to string
        notification.setMetadata(metadata); // Attach additional metadata
        notification.setDateSend(LocalDateTime.now()); // Timestamp the notification
        notification.setStatus(NotificationStatus.PENDING); // Initial status
        notification.setDeliveryMethod(NotificationDeliveryMethod.IN_APP); // Default delivery method

        // Save notification to the repository
        lmsNotificationRepository.save(notification);

        // Determine the appropriate delivery method and send
        if (notification.getDeliveryMethod() == NotificationDeliveryMethod.IN_APP) {
            // Handle in-app notifications (no additional action required)
            notification.setStatus(NotificationStatus.SENT);
        } else if (notification.getDeliveryMethod() == NotificationDeliveryMethod.EMAIL) {
            emailService.sendNotificationEmail(notification);
            notification.setStatus(NotificationStatus.SENT);
        } else if (notification.getDeliveryMethod() == NotificationDeliveryMethod.PUSH) {
            pushNotificationService.sendPushNotification(notification);
            notification.setStatus(NotificationStatus.SENT);
        } else {
            throw new UnsupportedOperationException("Unsupported delivery method: " + notification.getDeliveryMethod());
        }

        // Update notification status
        lmsNotificationRepository.save(notification);

        // Log the notification action
        logNotificationAction(userId, "NOTIFICATION_SENT", "Notification sent for entity ID: " + entityId);
    }


    @Override
    public List<LmsNotificationDto> getNotificationsByUserId(Long userId) {
        List<LmsNotification> notifications = lmsNotificationRepository.findByRecipient_UserId(userId);
        return notifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get unread notifications for a user
    @Override
    public List<LmsNotificationDto> getUnreadNotificationsByUser(Long userId) {
        List<LmsNotification> unreadNotifications = lmsNotificationRepository.findByRecipient_UserIdAndIsRead(userId, false);
        return unreadNotifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<LmsNotificationDto> getNotificationsByTenant(Long tenantId) {
        return lmsNotificationRepository.findByTenantTenantId(tenantId).stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void deleteNotification(Long notificationId) {
        if (!lmsNotificationRepository.existsById(notificationId)) {
            throw new IllegalArgumentException("Notification not found with ID: " + notificationId);
        }
        lmsNotificationRepository.deleteById(notificationId);
    }


    @Override
    public List<LmsNotificationDto> getNotificationsForStaff(Long staffId) {
        List<LmsNotification> notifications = lmsNotificationRepository.findNotificationsForStaff(staffId);
        return notifications.stream()
                .map(lmsNotificationMapper::toDto) // Convert to DTO
                .collect(Collectors.toList());
    }

    public List<LmsNotificationDto> getNotificationsForStudent(Long studentId) {
        List<LmsNotification> notifications = lmsNotificationRepository.findNotificationsForStudent(studentId);
        return notifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<LmsNotificationDto> getNotificationsForAdmin(Long adminId) {
        List<LmsNotification> notifications = lmsNotificationRepository.findNotificationsForAdmin(adminId);
        return notifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get notifications linked to a specific entity
    public List<LmsNotificationDto> getNotificationsForEntity(Long entityId, String entityType) {
        List<LmsNotification> entityNotifications = lmsNotificationRepository.findByRelatedEntityIdAndRelatedEntityType(entityId, entityType);
        return entityNotifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get notifications of a specific type
    public List<LmsNotificationDto> getNotificationsByType(String type) {
        List<LmsNotification> typeNotifications = lmsNotificationRepository.findByType(type);
        return typeNotifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void sendBulkNotifications(List<LmsNotificationDto> notifications) {
        notifications.forEach(notificationDto -> {
            LmsNotification notification = lmsNotificationMapper.toEntity(notificationDto);
            notification.setDateSend(LocalDateTime.now());
            notification.setStatus(NotificationStatus.SENT);
            lmsNotificationRepository.save(notification);

            // Send notifications via preferred delivery methods
            if (notification.getDeliveryMethod() == NotificationDeliveryMethod.EMAIL) {
                emailService.sendNotificationEmail(notification);
            } else if (notification.getDeliveryMethod() == NotificationDeliveryMethod.PUSH) {
                pushNotificationService.sendPushNotification(notification);
            }
        });
    }

    /**
     * Sends a notification to a user.
     *
     * @param linkedEntityType the username of the recipient.
     * @param message  the notification message.
     */
    public void notifyUser(Long userId, String message, Long linkedEntityId, String linkedEntityType) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Create a new notification
        LmsNotification notification = new LmsNotification();
        notification.setRecipientUser(user);
        notification.setMessage(message);
        notification.setRelatedEntityId(linkedEntityId);
        notification.setRelatedEntityType(linkedEntityType);
        notification.setTitle("New Notification");
        notification.setDateSend(LocalDateTime.now());
        notification.setStatus(NotificationStatus.SENT);
        notification.setDeliveryMethod(NotificationDeliveryMethod.IN_APP);

        // Save and send the notification
        lmsNotificationRepository.save(notification);

        // Send additional delivery if required (e.g., email, push notification)
        if (notification.getDeliveryMethod() == NotificationDeliveryMethod.EMAIL) {
            emailService.sendNotificationEmail(notification); // Ensure emailService is implemented
        } else if (notification.getDeliveryMethod() == NotificationDeliveryMethod.PUSH) {
            pushNotificationService.sendPushNotification(notification); // Ensure pushNotificationService is implemented
        }
    }


    // Send Recurring Notification
    @Override
    public void scheduleRecurringNotifications() {
        List<LmsNotification> recurringNotifications = lmsNotificationRepository.findByRecurrenceIsNotNull();
        recurringNotifications.forEach(notification -> {
            if ("DAILY".equalsIgnoreCase(notification.getRecurrence())) {
                notification.setScheduledDate(notification.getScheduledDate().plusDays(1));
            } else if ("WEEKLY".equalsIgnoreCase(notification.getRecurrence())) {
                notification.setScheduledDate(notification.getScheduledDate().plusWeeks(1));
            }
            lmsNotificationRepository.save(notification);
        });
    }


    // Send schedule notification
    @Override
    public void sendScheduledNotifications() {
        List<LmsNotification> scheduledNotifications = lmsNotificationRepository.findByScheduledDateBefore(LocalDateTime.now());
        scheduledNotifications.forEach(notification -> {
            notification.setStatus(NotificationStatus.SENT);
            notification.setDateSend(LocalDateTime.now());
            lmsNotificationRepository.save(notification);
        });
    }


    // Track notification being click on
    @Override
    public void trackNotificationClick(Long notificationId) {
        LmsNotification notification = lmsNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
        notification.setClicks(notification.getClicks() + 1);
        notification.setLastClickedAt(LocalDateTime.now());
        lmsNotificationRepository.save(notification);
    }

    // Clean up expired notification
    @Override
    public void cleanUpExpiredNotifications() {
        List<LmsNotification> expiredNotifications = lmsNotificationRepository.findByExpiresDateBefore(LocalDateTime.now());
        expiredNotifications.forEach(notification -> {
            notification.setStatus(NotificationStatus.EXPIRED);
            lmsNotificationRepository.save(notification);
        });
    }

    // Apply New notification template
    @Override
    public void applyTemplateToNotification(Long notificationId) {
        LmsNotification notification = lmsNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));

        NotificationTemplate template = notificationTemplateRepository.findById(notification.getTemplateId())
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + notification.getTemplateId()));

        notification.setMessage(template.getContent());
        lmsNotificationRepository.save(notification);
    }

    // Create A New Notification Template
    @Override
    @Transactional
    public LmsNotificationDto createNotificationFromTemplate(String templateName, Map<String, String> placeholders,
                                                             Long recipientId) {
        // Fetch the template
        NotificationTemplate template = notificationTemplateRepository.findByName(templateName)
                .orElseThrow(() -> new RuntimeException("Template not found: " + templateName));

        // Replace placeholders
        String message = replacePlaceholders(template.getContent(), placeholders);

        // Create notification
        LmsNotification notification = new LmsNotification();
        notification.setTitle(templateName); // Use template name as title
        notification.setMessage(message);
        notification.setRecipientUser(userRepository.findById(recipientId)
                .orElseThrow(() -> new RuntimeException("Recipient not found with ID: " + recipientId)));
        notification.setDateSend(LocalDateTime.now());
        notification.setStatus(NotificationStatus.PENDING);

        // Save and return the notification
        return lmsNotificationMapper.toDto(lmsNotificationRepository.save(notification));
    }

    private String replacePlaceholders(String content, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            content = content.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return content;
    }

// Get Notification base on location preference
    @Override
    public List<LmsNotificationDto> getLocalizedNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        String userLanguage = user.getPreferredLanguage();

        List<LmsNotification> notifications = lmsNotificationRepository.findByRecipient_UserId(userId);
        notifications.forEach(notification -> {
            if (notification.getLocalizedMessage() != null) {
                notification.setMessage(getLocalizedMessage(notification.getLocalizedMessage(), userLanguage));
            }
        });

        return notifications.stream()
                .map(lmsNotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get Local message
    private String getLocalizedMessage(String localizedMessagesJson, String language) {
        // Assume JSON contains key-value pairs for language -> message
        Map<String, String> localizedMessages = new Gson().fromJson(localizedMessagesJson, new TypeToken<Map<String, String>>() {}.getType());
        return localizedMessages.getOrDefault(language, localizedMessages.get("en"));
    }

    // Mark notification as read
    @Override
    public void markNotificationAsRead(Long notificationId) {
        LmsNotification notification = lmsNotificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
        notification.setRead(true);
        notification.setDateRead(LocalDateTime.now());
        lmsNotificationRepository.save(notification);
    }


    // Find all notifications for a specific user
    @Override
    public void markAllNotificationsAsRead(Long userId) {
        List<LmsNotification> notifications = lmsNotificationRepository.findByRecipient_UserId(userId);
        notifications.forEach(notification -> notification.setRead(true));
        lmsNotificationRepository.saveAll(notifications);
    }

    @Override
    public List<LmsNotificationDto> filterNotifications(String type, NotificationStatus status) {
        List<LmsNotification> notifications = lmsNotificationRepository.findByTypeAndStatus(type, status);
        return notifications.stream().map(lmsNotificationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void notifyStudent(Long studentId, String subject, String body, String senderEmail, String senderPassword) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        emailService.sendEmailUsingUserCredentials(
                student.getUser().getEmail(),
                subject,
                body,
                senderEmail,
                senderPassword
        );
    }

    /**
     * Notify all administrators about a specific message.
     */
    public void notifyAdmins(String message) {
        // Fetch all admin users
        List<User> admins = userRepository.findUsersByRoleName("ADMIN");

        // Create notifications for each admin
        admins.forEach(admin -> {
            LmsNotification notification = new LmsNotification();
            notification.setRecipientUser(admin);
            notification.setMessage(message);
            notification.setType(NotificationType.SYSTEM_ALERT);
            lmsNotificationRepository.save(notification);
        });

        // Optionally log the notification event
        System.out.println("Notification sent to admins: " + message);
    }


//    public void notifyAdmin(String subject, String body) {
//        emailService.sendDefaultEmail("admin@example.com", subject, body);
//    }




//    @Override
//    public void createAndSendNotification(Long recipientId, String title, String message,
//                                          RelatedEntityType entityType, Long entityId) {
//        // Validate recipient
//        User recipient = userRepository.findById(recipientId)
//                .orElseThrow(() -> new RuntimeException("Recipient not found with ID: " + recipientId));
//
//        // Validate related entity
//        validateLinkedEntity(entityType.name(), entityId);
//
//        // Create notification
//        LmsNotification notification = new LmsNotification();
//        notification.setRecipientUser(recipient);
//        notification.setTitle(title);
//        notification.setMessage(message);
//        notification.setRelatedEntityType(entityType.name()); // Convert enum to string
//        notification.setRelatedEntityId(entityId);
//        notification.setDateSend(LocalDateTime.now());
//        notification.setStatus(NotificationStatus.SENT);
//        notification.setDeliveryMethod(NotificationDeliveryMethod.IN_APP);
//
//
//        // Send notification via appropriate channels
//        lmsNotificationRepository.save(notification);
//        inAppNotificationService.sendNotification(notification); // Example service call
//    }


//    @Override
//    public LmsNotificationDto updateNotification(Long id, LmsNotificationDto notificationDto) {
//        LmsNotification existingNotification = lmsNotificationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
//
//        lmsNotificationMapper.toEntity(notificationDto);
//        return lmsNotificationMapper.toDto(lmsNotificationRepository.save(existingNotification));
//    }


//    @Override
//    public void sendNotifications(Announcement announcement, Set<User> recipients) {
//        if (recipients == null || recipients.isEmpty()) {
//            throw new IllegalArgumentException("No recipients to notify.");
//        }
//
//        for (User recipient : recipients) {
//            LmsNotification notification = new LmsNotification();
//            notification.setRecipientUser(recipient);
//            notification.setType(NotificationType.valueOf("Announcement"));
//            notification.setMessage("New announcement: " + announcement.getTitle());
//            notification.setRelatedEntityId(announcement.getAnnouncementId());
//            notification.setRelatedEntityType("Announcement");
//            notification.setDeliveryMethod(NotificationDeliveryMethod.valueOf("InApp")); // Customize as per your delivery method
//            notification.setIsRead(false);
//            notification.setDateSend(LocalDateTime.now());
//            lmsNotificationRepository.save(notification);
//        }
//    }



    //    @Override
//    public LmsNotificationDto createNotification(LmsNotificationDto notificationDto) {
//        // Validate recipient user
//        User recipient = userRepository.findById(notificationDto.getRecipientUserId())
//                .orElseThrow(() -> new RuntimeException("Recipient not found with ID: " + notificationDto.getRecipientUserId()));
//
//        // Validate related entity
//        validateLinkedEntity(notificationDto.getRelatedEntityType(), notificationDto.getRelatedEntityId());
//
//        // Map DTO to entity
//        LmsNotification notification = lmsNotificationMapper.toEntity(notificationDto);
//        notification.setRecipientUser(recipient);
//        notification.setDateSend(LocalDateTime.now());
//        notification.setStatus(NotificationStatus.PENDING);
//
//
//        // Handle delivery logic for EMAIL, SMS, etc.
//        if (notification.getDeliveryMethod() == NotificationDeliveryMethod.EMAIL) {
//            emailService.sendNotificationEmail(notification);
//        } else if (notification.getDeliveryMethod() == NotificationDeliveryMethod.PUSH) {
//            pushNotificationService.sendPushNotification(notification);
//        }
//
//        // Save and return notification
//        return lmsNotificationMapper.toDto(lmsNotificationRepository.save(notification));
//    }




}
