package learning_management_system.backend.utility;

public class PushNotificationRequest {

    private Long recipientUserId;
    private String title;
    private String message;

    // No-argument constructor
    public PushNotificationRequest() {}

    // Constructor with all arguments
    public PushNotificationRequest(Long recipientUserId, String title, String message) {
        this.recipientUserId = recipientUserId;
        this.title = title;
        this.message = message;
    }

    // Getters and Setters
    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

