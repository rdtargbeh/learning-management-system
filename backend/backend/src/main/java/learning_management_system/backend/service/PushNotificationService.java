package learning_management_system.backend.service;

import learning_management_system.backend.entity.LmsNotification;

public interface PushNotificationService {
    void sendPushNotification(LmsNotification notification);
}
