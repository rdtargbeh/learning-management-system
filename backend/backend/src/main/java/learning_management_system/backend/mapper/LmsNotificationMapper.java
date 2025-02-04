package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.LmsNotificationDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.repository.NotificationTemplateRepository;
import learning_management_system.backend.repository.StudentGroupRepository;
import learning_management_system.backend.repository.TenantRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring", uses = {UserMapper.class, TenantMapper.class})

@Component
public class LmsNotificationMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private NotificationTemplateRepository templateRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    /**
     * Converts an LmsNotification entity to LmsNotificationDto.
     *
     * @param notification the LmsNotification entity
     * @return the corresponding LmsNotificationDto
     */
    public LmsNotificationDto toDto(LmsNotification notification) {
        if (notification == null) {
            return null;
        }

        LmsNotificationDto dto = new LmsNotificationDto();
        dto.setNotificationId(notification.getNotificationId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setUrgency(notification.getUrgency());
        dto.setDeliveryMethod(notification.getDeliveryMethod());
        dto.setStatus(notification.getStatus());
        dto.setRead(notification.getRead());
        dto.setDateRead(notification.getDateRead());
        dto.setDateSend(notification.getDateSend());
        dto.setScheduledDate(notification.getScheduledDate());
        dto.setExpiresDate(notification.getExpiresDate());
        dto.setRecurrence(notification.getRecurrence());
        dto.setActionLink(notification.getActionLink());
        dto.setMetadata(notification.getMetadata());
        dto.setAttachments(notification.getAttachments());
        dto.setLocalizedMessage(notification.getLocalizedMessage());
        dto.setClicks(notification.getClicks());
        dto.setLastClickedAt(notification.getLastClickedAt());
        dto.setRelatedEntityId(notification.getRelatedEntityId());
        dto.setRelatedEntityType(notification.getRelatedEntityType());
        dto.setRecipientUserId(notification.getRecipientUser() != null ? notification.getRecipientUser().getUserId() : null);
        dto.setTenantId(notification.getTenant() != null ? notification.getTenant().getTenantId() : null);
        dto.setGroupId(notification.getGroup().getGroupId());
        dto.setTemplateId(notification.getTemplateId());

        return dto;
    }

    /**
     * Converts an LmsNotificationDto to LmsNotification entity.
     *
     * @param dto the LmsNotificationDto
     * @return the corresponding LmsNotification entity
     */
    public LmsNotification toEntity(LmsNotificationDto dto) {
        if (dto == null) {
            return null;
        }

        LmsNotification notification = new LmsNotification();
        notification.setNotificationId(dto.getNotificationId());
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType());
        notification.setUrgency(dto.getUrgency());
        notification.setDeliveryMethod(dto.getDeliveryMethod());
        notification.setStatus(dto.getStatus());
        notification.setRead(dto.getRead());
        notification.setDateRead(dto.getDateRead());
        notification.setDateSend(dto.getDateSend());
        notification.setScheduledDate(dto.getScheduledDate());
        notification.setExpiresDate(dto.getExpiresDate());
        notification.setRecurrence(dto.getRecurrence());
        notification.setActionLink(dto.getActionLink());
        notification.setMetadata(dto.getMetadata());
        notification.setAttachments(dto.getAttachments());
        notification.setLocalizedMessage(dto.getLocalizedMessage());
        notification.setClicks(dto.getClicks());
        notification.setLastClickedAt(dto.getLastClickedAt());
        notification.setRelatedEntityId(dto.getRelatedEntityId());
        notification.setRelatedEntityType(dto.getRelatedEntityType());
        notification.setTemplateId(dto.getTemplateId());

        // Fetch and set the StudentGroup entity
        if (dto.getGroupId() != null) {
            StudentGroup group = studentGroupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("StudentGroup not found with ID: " + dto.getGroupId()));
            notification.setGroup(group);
        }

        // Fetch and set recipient user
        if (dto.getRecipientUserId() != null) {
            User recipient = userRepository.findById(dto.getRecipientUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getRecipientUserId()));
            notification.setRecipientUser(recipient);
        }

        // Fetch and set tenant
        if (dto.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + dto.getTenantId()));
            notification.setTenant(tenant);
        }

        // Fetch and set template if provided
        if (dto.getTemplateId() != null) {
            NotificationTemplate template = templateRepository.findById(dto.getTemplateId())
                    .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + dto.getTemplateId()));
            notification.setMetadata(template.getContent());
        }

        return notification;
    }

}

