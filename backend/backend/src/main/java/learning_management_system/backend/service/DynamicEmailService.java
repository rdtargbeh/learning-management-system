package learning_management_system.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class DynamicEmailService {

    @Autowired
    private JavaMailSender defaultMailSender; // For fallback use

    public void sendEmailUsingUserCredentials(String recipient, String subject, String body, String senderEmail, String senderPassword) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.example.com");
        mailSender.setPort(587);
        mailSender.setUsername(senderEmail);
        mailSender.setPassword(senderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);

        // Create and send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendFallbackEmail(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("default@example.com"); // Default sender email
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);

        defaultMailSender.send(message);
    }
}

