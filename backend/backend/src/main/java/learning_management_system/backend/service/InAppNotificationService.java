package learning_management_system.backend.service;


import learning_management_system.backend.entity.LmsNotification;

public interface InAppNotificationService {
    void sendNotification(LmsNotification notification);
}

