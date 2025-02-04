package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.enums.PriorityUrgency;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.mapper.AnnouncementMapper;
import learning_management_system.backend.mapper.AttachmentMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.AnnouncementService;
import learning_management_system.backend.service.CommentService;
import learning_management_system.backend.service.LmsNotificationService;
import learning_management_system.backend.utility.EngagementMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImplementation implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private UserRepository userRepository;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AnnouncementMapper announcementMapper;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Lazy
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnnouncementViewsRepository announcementViewsRepository;

    private static final Logger log = Logger.getLogger(AnnouncementServiceImplementation.class.getName());


    /**
     * Create a new announcement.
     */
    @Override
    public AnnouncementDto createAnnouncement(AnnouncementDto announcementDto) {
        // Fetch the sender (createdBy)
        User sender = userRepository.findById(announcementDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + announcementDto.getCreatedBy()));

        // Fetch recipients
        Set<User> recipients = announcementDto.getRecipientIds() != null
                ? new HashSet<>(userRepository.findAllById(announcementDto.getRecipientIds()))
                : new HashSet<>();

        // Validate associated entity
        String targetType = announcementDto.getTargetEntityType();
        Long targetId = announcementDto.getTargetEntityId();
        validateTargetEntity(targetType, targetId);

        // Map DTO to entity
        Announcement announcement = announcementMapper.toEntity(announcementDto);
        announcement.setCreatedBy(sender);
        announcement.setRecipients(recipients);

        // Save the announcement
        return announcementMapper.toDto(announcementRepository.save(announcement));
    }


// Helper method to validate target entity like assignment, exam for announcement is true
    private void validateTargetEntity(String targetType, Long targetId) {
        switch (targetType.toUpperCase()) {
            case "COURSE":
                if (!courseRepository.existsById(targetId)) {
                    throw new RuntimeException("Target Course not found with ID: " + targetId);
                }
                break;
            case "GROUP":
                if (!studentGroupRepository.existsById(targetId)) {
                    throw new RuntimeException("Target Group not found with ID: " + targetId);
                }
                break;
            default:
                throw new RuntimeException("Invalid target entity type: " + targetType);
        }
    }


    /**
     * Retrieve announcements for a specific course.
     */
//    @Override
//    public List<AnnouncementDto> getAnnouncementsByCourseId(Long courseId) {
//        return announcementRepository.findByTargetCourse_CourseId(courseId).stream()
//                .map(announcementMapper::toDto)
//                .collect(Collectors.toList());
//    }

    @Override
    public Page<AnnouncementDto> getAnnouncementsByCourseId(Long courseId, Pageable pageable) {
        return announcementRepository.findByTargetCourse_CourseId(courseId, pageable)
                .map(announcementMapper::toDto);
    }

    /**
     * Retrieve an announcement by ID.
     */
    @Override
    public AnnouncementDto getAnnouncementById(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        return announcementMapper.toDto(announcement);
    }

    /**
     * Update an existing announcement.
     */
    @Override
    public AnnouncementDto updateAnnouncement(Long announcementId, AnnouncementDto announcementDto) {
        Announcement existingAnnouncement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));

        existingAnnouncement.setTitle(announcementDto.getTitle());
        existingAnnouncement.setContent(announcementDto.getContent());
        existingAnnouncement.setScheduledAt(announcementDto.getScheduledAt());
        existingAnnouncement.setDraft(announcementDto.getDraft());
        existingAnnouncement.setTargetRoles(announcementDto.getTargetRoles());
        existingAnnouncement.setCategory(announcementDto.getCategory());
        existingAnnouncement.setTargetEntityType(announcementDto.getTargetEntityType());
        existingAnnouncement.setPriority(PriorityUrgency.valueOf(announcementDto.getPriority()));
        existingAnnouncement.setVisibility(VisibleTo.valueOf(announcementDto.getVisibility()));
        existingAnnouncement.setActionLink(announcementDto.getActionLink());

        Announcement updatedAnnouncement = announcementRepository.save(existingAnnouncement);

        return announcementMapper.toDto(updatedAnnouncement);
    }


    /**
     * Delete an announcement.
     */
    @Override
    public void deleteAnnouncement(Long announcementId) {
        if (!announcementRepository.existsById(announcementId)) {
            throw new IllegalArgumentException("Announcement not found with ID: " + announcementId);
        }
        announcementRepository.deleteById(announcementId);
    }


    /**
     * Archive an announcement.
     */
    @Override
    public void archiveAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        announcement.setPinned(false); // Optionally unpin the announcement
        announcement.setArchived(true);
        announcementRepository.save(announcement);
    }

    /**
     * Retrieve all active announcements.
     */
    @Override
    public List<AnnouncementDto> getActiveAnnouncements() {
        return announcementRepository.findByIsArchivedFalse().stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }


    // Create schedule announcement reminder
    @Scheduled(cron = "0 0 * * * *") // Runs hourly
    public void sendAnnouncementReminders() {
        LocalDateTime now = LocalDateTime.now();

        // Fetch time-sensitive announcements
        List<Announcement> timeSensitiveAnnouncements = announcementRepository.findByPublishDateBeforeAndExpiryDateAfter(
                now.minusHours(1), now.plusHours(1)
        );

        // Fetch recurring announcements
        List<Announcement> recurringAnnouncements = announcementRepository.findRecurringAnnouncements();

        // Combine announcements
        List<Announcement> allAnnouncements = new ArrayList<>();
        allAnnouncements.addAll(timeSensitiveAnnouncements);
        allAnnouncements.addAll(recurringAnnouncements);

        // Send notifications to recipients
        for (Announcement announcement : allAnnouncements) {
            for (User recipient : announcement.getRecipients()) {
                try {
                    notificationService.createAndSendNotification(
                            recipient.getUserId(),
                            "Reminder: " + announcement.getTitle(),
                            announcement.getAnnouncementId(),
                            RelatedEntityType.ANNOUNCEMENT,
                            "{" +
                                    "\"title\":\"" + announcement.getTitle() + "\"," +
                                    "\"content\":\"" + announcement.getContent() + "\"" +
                                    "}"
                    );
                } catch (Exception e) {
                    log.severe("Failed to send notification for announcement ID: " + announcement.getAnnouncementId() +
                            " to user ID: " + recipient.getUserId() + ". Error: " + e.getMessage());
                }
            }
        }
    }


    // Get All announcements
    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get announcement by Category
    @Override
    public List<AnnouncementDto> getAnnouncementsByCategory(String category) {
        return announcementRepository.findByCategory(category).stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get pinned announcement
    @Override
    public List<AnnouncementDto> getPinnedAnnouncements() {
        return announcementRepository.findByIsPinnedTrue().stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }


    // Get Announcement engagement metrics
    @Override
    public void updateEngagementMetrics(Long announcementId, int newViewCount, int newReplyCount) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found with ID: " + announcementId));

        announcement.setViewCount(announcement.getViewCount() + newViewCount);
        announcement.setReplyCount(announcement.getReplyCount() + newReplyCount);

        announcementRepository.save(announcement);
    }


    // Create announcement reminder for Assessment
    @Override
    public AnnouncementDto createAssessmentReminder(Long assessmentId, String reminderMessage, LocalDateTime reminderDate) {
        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found with ID: " + assessmentId));

        Announcement announcement = new Announcement();
        announcement.setTitle("Assessment Reminder: " + assessment.getTitle());
        announcement.setContent(reminderMessage);
        announcement.setScheduledAt(reminderDate);
        announcement.setTargetEntityType("ASSESSMENT");
        announcement.setTargetEntityId(assessmentId);

        return announcementMapper.toDto(announcementRepository.save(announcement));
    }


    // Create announcement reminder for Assignment
    @Override
    public AnnouncementDto createAssignmentReminder(Long assignmentId, String reminderMessage, LocalDateTime reminderDate) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        Announcement announcement = new Announcement();
        announcement.setTitle("Assignment Reminder: " + assignment.getTitle());
        announcement.setContent(reminderMessage);
        announcement.setScheduledAt(reminderDate);
        announcement.setTargetEntityType("ASSIGNMENT");
        announcement.setPriority(PriorityUrgency.valueOf("High"));
        announcement.setTargetEntityId(assignmentId);

        return announcementMapper.toDto(announcementRepository.save(announcement));
    }


    @Override
    public AttachmentDto addAttachmentToAnnouncement(Long announcementId, AttachmentDto attachmentDto) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new RuntimeException("Announcement not found with ID: " + announcementId));

        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachment.setLinkedEntityType("ANNOUNCEMENT");
        attachment.setLinkedEntityId(announcementId);

        attachmentRepository.save(attachment);

        return attachmentMapper.toDto(attachment);
    }

    // Add a comment to a announcement
    @Override
    public CommentDto addCommentToAnnouncement(Long announcementId, CommentDto commentDto) {
        commentDto.setLinkedEntityType(LinkedEntityType.valueOf("ANNOUNCEMENT"));
        commentDto.setLinkedEntityId(announcementId);
        return commentService.createComment(commentDto);
    }

    // Fetch all comments for a announcement
    @Override
    public List<CommentDto> getCommentsForAnnouncement(Long announcementId) {
        return commentService.getCommentsByEntity("ANNOUNCEMENT", announcementId);
    }

    // Reply to a comment in a announcement
    @Override
    public CommentDto replyToAnnouncementComment(Long parentCommentId, CommentDto replyDto) {
        return commentService.replyToComment(parentCommentId, replyDto);
    }

    @Override
    public AnnouncementDto getLocalizedAnnouncement(Long announcementId, String languageCode) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));

        // Check for localization support
        if (!languageCode.equalsIgnoreCase(announcement.getContentLanguage())) {
            throw new IllegalArgumentException("Localized content not available for the requested language.");
        }

        return announcementMapper.toDto(announcement);
    }


    @Override
    public void updateEngagementMetrics(Long announcementId, Long userId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));

        // Increment view count
        announcement.setViewCount(announcement.getViewCount() + 1);
        // Update last engaged timestamp
        announcement.setLastEngagedAt(LocalDateTime.now());
        // Save engagement data
        announcementRepository.save(announcement);
        // Optionally log user-specific engagement (e.g., in a separate table)
    }


    @Override
    public EngagementMetricsDto getAnnouncementEngagementMetrics(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));

        // Example: Generate metrics based on viewCount, replyCount, etc.
        return new EngagementMetricsDto(
                announcement.getViewCount(),
                announcement.getReplyCount(),
                announcement.getDateUpdated() != null
                        ? announcement.getDateUpdated().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        : null // Fallback if dateUpdated is not set
        );
    }


    @Override
    public List<AnnouncementDto> searchAnnouncements(String keyword) {
        return announcementRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword)
                .stream()
                .map(announcementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementAnnouncementViewCount(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new IllegalArgumentException("Announcement not found with ID: " + announcementId));
        announcement.setViewCount(announcement.getViewCount() + 1);
        announcementRepository.save(announcement);
    }

}

