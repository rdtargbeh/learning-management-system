package learning_management_system.backend.config;

import learning_management_system.backend.enums.NotificationDeliveryMethod;
import learning_management_system.backend.service.NotificationHandler;
import learning_management_system.backend.service.implement.EmailNotificationHandler;
import learning_management_system.backend.service.implement.PushNotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NotificationHandlerConfig {

    @Bean
    public Map<NotificationDeliveryMethod, NotificationHandler> notificationHandlers(
            EmailNotificationHandler emailHandler,
            PushNotificationHandler pushHandler) {
        Map<NotificationDeliveryMethod, NotificationHandler> handlers = new HashMap<>();
        handlers.put(NotificationDeliveryMethod.EMAIL, emailHandler);
        handlers.put(NotificationDeliveryMethod.PUSH, pushHandler);
        return handlers;
    }
}


