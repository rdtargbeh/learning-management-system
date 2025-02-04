package learning_management_system.backend.service.implement;

import learning_management_system.backend.entity.LmsNotification;
import learning_management_system.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImplementation implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JavaMailSender defaultMailSender;

//    @Autowired
//    public EmailServiceImplementation(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    @Override
    public void sendNotificationEmail(LmsNotification notification) {
        // Validate the notification
        if (notification.getRecipientUser() == null || notification.getRecipientUser().getEmail() == null) {
            throw new IllegalArgumentException("Recipient email is not available for this notification.");
        }

        // Construct email details
        String recipientEmail = notification.getRecipientUser().getEmail();
        String subject = notification.getTitle();
        String body = notification.getMessage();

        try {
            // Create a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(body);

            // Send the email
            mailSender.send(mailMessage);
            System.out.printf("Email successfully sent to: %s%n", recipientEmail);
        } catch (Exception e) {
            System.err.printf("Failed to send email to: %s. Error: %s%n", recipientEmail, e.getMessage());
            throw new RuntimeException("Failed to send email notification", e);
        }
    }



    @Override
    public void sendDefaultEmail(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("default@example.com");
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        defaultMailSender.send(message);
    }

//    @Override
//    public void sendDefaultEmail(String recipient, String subject, String body) {
//        // Use default credentials configured in `application.properties`
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("default@example.com");
//        message.setTo(recipient);
//        message.setSubject(subject);
//        message.setText(body);
//
//        javaMailSender.send(message);
//    }

    @Override
    public void sendEmailUsingUserCredentials(String recipient, String subject, String body, String senderEmail, String senderPassword) {
        // Configure JavaMailSender with dynamic credentials
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.example.com");
        mailSender.setPort(587);
        mailSender.setUsername(senderEmail);
        mailSender.setPassword(senderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


}
