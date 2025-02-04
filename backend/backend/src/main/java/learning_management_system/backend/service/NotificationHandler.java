package learning_management_system.backend.service;


import learning_management_system.backend.entity.LmsNotification;

public interface NotificationHandler {
    void sendNotification(LmsNotification notification);
}

