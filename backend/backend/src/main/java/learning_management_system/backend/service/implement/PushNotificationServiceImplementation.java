package learning_management_system.backend.service.implement;

import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.service.PushNotificationClient;
import learning_management_system.backend.service.PushNotificationService;
import learning_management_system.backend.utility.PushNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PushNotificationServiceImplementation implements PushNotificationService {

    private final PushNotificationClient pushNotificationClient; // Assume a client library for push notifications

    @Autowired
    public PushNotificationServiceImplementation(PushNotificationClient pushNotificationClient) {
        this.pushNotificationClient = pushNotificationClient;
    }

    @Override
    public void sendPushNotification(LmsNotification notification) {
        // Validate the notification
        if (notification.getRecipientUser() == null || notification.getRecipientUser().getUserId() == null) {
            throw new IllegalArgumentException("Recipient user ID is not available for this notification.");
        }

        // Construct push notification details
        Long recipientId = notification.getRecipientUser().getUserId();
        String title = notification.getTitle();
        String message = notification.getMessage();

        try {
            // Create and send the push notification
            PushNotificationRequest pushRequest = new PushNotificationRequest();
            pushRequest.setRecipientUserId(recipientId);
            pushRequest.setTitle(title);
            pushRequest.setMessage(message);

            // Simulated push notification sending
            pushNotificationClient.sendPushNotification(pushRequest);
            System.out.printf("Push notification successfully sent to user ID: %s%n", recipientId);
        } catch (Exception e) {
            System.err.printf("Failed to send push notification to user ID: %s. Error: %s%n", recipientId, e.getMessage());
            throw new RuntimeException("Failed to send push notification", e);
        }
    }
}
