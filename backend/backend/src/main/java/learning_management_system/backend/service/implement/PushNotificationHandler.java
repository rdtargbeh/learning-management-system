package learning_management_system.backend.service.implement;

import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.service.NotificationHandler;
import learning_management_system.backend.service.PushNotificationService;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationHandler implements NotificationHandler {

    private final PushNotificationService pushNotificationService;

    public PushNotificationHandler(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @Override
    public void sendNotification(LmsNotification notification) {
        // Validate the notification object
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null.");
        }

        // Call the PushNotificationService to send the notification
        pushNotificationService.sendPushNotification(notification);
    }
}

