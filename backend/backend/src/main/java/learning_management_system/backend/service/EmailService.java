package learning_management_system.backend.service;

import learning_management_system.backend.entity.LmsNotification;

public interface EmailService {
    void sendNotificationEmail(LmsNotification notification);

    void sendDefaultEmail(String recipient, String subject, String body);

    void sendEmailUsingUserCredentials(String recipient, String subject, String body, String senderEmail, String senderPassword);

     // Already implemented
}

