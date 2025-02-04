package learning_management_system.backend.service;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.utility.EngagementMetricsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing Announcement entities.
 */

public interface AnnouncementService {

    Page<AnnouncementDto> getAnnouncementsByCourseId(Long courseId, Pageable pageable);

    List<AnnouncementDto> getActiveAnnouncements();

    /**
     * Creates a new announcement.
     * @param announcementDto The details of the announcement.
     * @return The created AnnouncementDto.
     */
    AnnouncementDto createAnnouncement(AnnouncementDto announcementDto);

    void archiveAnnouncement(Long announcementId);


    /**
     * Updates an existing announcement.
     * @param announcementId The ID of the announcement to update.
     * @param announcementDto The updated details of the announcement.
     * @return The updated AnnouncementDto.
     */
    AnnouncementDto updateAnnouncement(Long announcementId, AnnouncementDto announcementDto);

    /**
     * Deletes an announcement by its ID.
     * @param announcementId The ID of the announcement to delete.
     */
    void deleteAnnouncement(Long announcementId);

    /**
     * Retrieves an announcement by its ID.
     * @param announcementId The ID of the announcement.
     * @return The corresponding AnnouncementDto.
     */
    AnnouncementDto getAnnouncementById(Long announcementId);

    /**
     * Retrieves all announcements.
     * @return A list of AnnouncementDto.
     */
    List<AnnouncementDto> getAllAnnouncements();

    /**
     * Retrieves announcements by category.
     * @param category The category to filter by.
     * @return A list of AnnouncementDto.
     */
    List<AnnouncementDto> getAnnouncementsByCategory(String category);

    /**
     * Retrieves pinned announcements.
     * @return A list of pinned AnnouncementDto.
     */
    List<AnnouncementDto> getPinnedAnnouncements();

    AnnouncementDto createAssessmentReminder(Long assessmentId, String reminderMessage, LocalDateTime reminderDate);

    AnnouncementDto createAssignmentReminder(Long assignmentId, String reminderMessage, LocalDateTime reminderDate);

    void sendAnnouncementReminders();

    void updateEngagementMetrics(Long announcementId, int newViewCount, int newReplyCount);

    AttachmentDto addAttachmentToAnnouncement(Long announcementId, AttachmentDto attachmentDto);

    CommentDto addCommentToAnnouncement(Long announcementId, CommentDto commentDto);

    List<CommentDto> getCommentsForAnnouncement(Long announcementId);

    CommentDto replyToAnnouncementComment(Long parentCommentId, CommentDto replyDto);

    AnnouncementDto getLocalizedAnnouncement(Long announcementId, String languageCode);

    void updateEngagementMetrics(Long announcementId, Long userId);

    EngagementMetricsDto getAnnouncementEngagementMetrics(Long announcementId);


    List<AnnouncementDto> searchAnnouncements(String keyword);

    void incrementAnnouncementViewCount(Long announcementId);

}
