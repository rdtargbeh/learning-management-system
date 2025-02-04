package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.entity.Announcement;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.PriorityUrgency;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper for converting between Announcement entities and Announcement DTOs.
 */

@Component
public class AnnouncementMapper {

    /**
     * Converts an Announcement entity to an AnnouncementDto.
     *
     * @param announcement The Announcement entity.
     * @return The corresponding AnnouncementDto.
     */
    public AnnouncementDto toDto(Announcement announcement) {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setAnnouncementId(announcement.getAnnouncementId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setRichTextContent(announcement.getRichTextContent());
        dto.setCategory(announcement.getCategory());
        dto.setTargetRoles(announcement.getTargetRoles());
        dto.setTargetEntityId(announcement.getTargetEntityId());
        dto.setTargetEntityType(announcement.getTargetEntityType());
        dto.setRecipientIds(announcement.getRecipients().stream()
                .map(User::getUserId)
                .collect(Collectors.toList())); // Convert the Set<Long> to List<Long>

        dto.setVisibility(announcement.getVisibility().name());
        dto.setPriority(announcement.getPriority().name());
        dto.setActionLink(announcement.getActionLink());
        dto.setPinned(announcement.getPinned());
        dto.setDraft(announcement.getDraft());
        dto.setRecurrencePattern(announcement.getRecurrencePattern());
        dto.setTags(announcement.getTags());
        dto.setScheduledAt(announcement.getScheduledAt());
        dto.setCreatedBy(announcement.getCreatedBy().getUserId());
        dto.setDateCreated(announcement.getDateCreated());
        dto.setDateUpdated(announcement.getDateUpdated());
        dto.setViewCount(announcement.getViewCount());
        dto.setArchived(announcement.isArchived());
        dto.setReplyCount(announcement.getReplyCount());
        dto.setContentLanguage(announcement.getContentLanguage());
        dto.setLastEngagedAt(announcement.getLastEngagedAt());
        dto.setPublishDate(announcement.getPublishDate());
        dto.setExpiryDate(announcement.getExpiryDate());
        return dto;
    }

    /**
     * Converts an AnnouncementDto to an Announcement entity.
     *
     * @param dto The AnnouncementDto.
     * @return The corresponding Announcement entity.
     */
    public Announcement toEntity(AnnouncementDto dto) {
        Announcement announcement = new Announcement();
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setRichTextContent(dto.getRichTextContent());
        announcement.setCategory(dto.getCategory());
        announcement.setTargetRoles(dto.getTargetRoles());
        announcement.setTargetEntityId(dto.getTargetEntityId());
        announcement.setTargetEntityType(dto.getTargetEntityType());
        announcement.setVisibility(VisibleTo.valueOf(dto.getVisibility()));
        announcement.setPriority(PriorityUrgency.valueOf(dto.getPriority()));
        announcement.setActionLink(dto.getActionLink());
        announcement.setPinned(dto.getPinned());
        announcement.setDraft(dto.getDraft());
        announcement.setRecurrencePattern(dto.getRecurrencePattern());
        announcement.setTags(dto.getTags());
        announcement.setScheduledAt(dto.getScheduledAt());
        announcement.setViewCount(dto.getViewCount());
        announcement.setArchived(dto.isArchived());
        announcement.setReplyCount(dto.getReplyCount());
        announcement.setLastEngagedAt(dto.getLastEngagedAt());
        announcement.setContentLanguage(dto.getContentLanguage());
        announcement.setPublishDate(dto.getPublishDate());
        announcement.setExpiryDate(dto.getExpiryDate());

        return announcement;
    }
}
