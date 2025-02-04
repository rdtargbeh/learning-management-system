package learning_management_system.backend.service.implement;

import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.service.EmailService;
import learning_management_system.backend.service.NotificationHandler;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationHandler implements NotificationHandler {

    private final EmailService emailService;

    public EmailNotificationHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendNotification(LmsNotification notification) {
        // Validate the notification object
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null.");
        }

        // Call the EmailService to send the notification
        emailService.sendNotificationEmail(notification);
    }
}
