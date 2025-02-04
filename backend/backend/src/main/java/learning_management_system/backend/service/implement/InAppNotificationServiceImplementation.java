package learning_management_system.backend.service.implement;


import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.repository.LmsNotificationRepository;
import learning_management_system.backend.service.InAppNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InAppNotificationServiceImplementation implements InAppNotificationService {

    @Autowired
    private LmsNotificationRepository lmsNotificationRepository;

    @Override
    public void sendNotification(LmsNotification notification) {

        // Create and send notification logic
        LmsNotification lmsNotification = new LmsNotification();
        lmsNotification.setRecipientUser(notification.getRecipientUser());
        lmsNotification.setRelatedEntityId(notification.getRelatedEntityId());
        lmsNotification.setRelatedEntityType(notification.getRelatedEntityType());
        lmsNotification.setDateSend(notification.getDateSend());

        notification.setMessage("You were mentioned in a discussion: " + notification.getMessage());

        // Save the notification to make it available in-app
        lmsNotificationRepository.save(notification);

        // Additional logic for sending real-time updates via WebSockets or similar
        sendRealTimeNotification(notification);
    }

    private void sendRealTimeNotification(LmsNotification notification) {
        // Placeholder for real-time notification logic, e.g., WebSocket broadcasting
        System.out.println("Real-time notification sent for: " + notification.getTitle());
    }
}
