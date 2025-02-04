package learning_management_system.backend.controller;

import learning_management_system.backend.dto.AnnouncementDto;
import learning_management_system.backend.dto.AttachmentDto;
import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.service.AnnouncementService;
import learning_management_system.backend.utility.EngagementMetricsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;


    /**
     * Retrieve all active announcements.
     *
     * @return List of active announcements.
     */
    @GetMapping("/active")
    public ResponseEntity<List<AnnouncementDto>> getActiveAnnouncements() {
        List<AnnouncementDto> activeAnnouncements = announcementService.getActiveAnnouncements();
        return ResponseEntity.ok(activeAnnouncements);
    }

    /**
     * Retrieve all announcements for a specific course.
     *
     * @param courseId ID of the course.
     * @return List of announcements for the course.
     */
//    @GetMapping("/course/{courseId}")
//    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByCourseId(@PathVariable Long courseId) {
//        List<AnnouncementDto> courseAnnouncements = announcementService.getAnnouncementsByCourseId(courseId);
//        return ResponseEntity.ok(courseAnnouncements);
//    }

    // Fetch announcements for a course with pagination
    @GetMapping("/course/{courseId}")
    public Page<AnnouncementDto> getAnnouncementsByCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateCreated") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        // Create the Pageable object
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        // Pass the Pageable to the service method
        return announcementService.getAnnouncementsByCourseId(courseId, pageable);
    }

    /**
     * Retrieve a specific announcement by ID.
     *
     * @param announcementId ID of the announcement.
     * @return The announcement.
     */
    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable Long announcementId) {
        AnnouncementDto announcement = announcementService.getAnnouncementById(announcementId);
        return ResponseEntity.ok(announcement);
    }

    /**
     * Create a new announcement.
     *
     * @param announcementDto Details of the new announcement.
     * @return The created announcement.
     */
    @PostMapping
    public ResponseEntity<AnnouncementDto> createAnnouncement(@RequestBody AnnouncementDto announcementDto) {
        AnnouncementDto createdAnnouncement = announcementService.createAnnouncement(announcementDto);
        return ResponseEntity.ok(createdAnnouncement);
    }

    /**
     * Update an existing announcement.
     *
     * @param announcementId  ID of the announcement to update.
     * @param announcementDto Updated details of the announcement.
     * @return The updated announcement.
     */
    @PutMapping("/{announcementId}")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody AnnouncementDto announcementDto) {
        AnnouncementDto updatedAnnouncement = announcementService.updateAnnouncement(announcementId, announcementDto);
        return ResponseEntity.ok(updatedAnnouncement);
    }

    /**
     * Archive an announcement by ID.
     *
     * @param announcementId ID of the announcement to archive.
     * @return Success message.
     */
    @PatchMapping("/{announcementId}/archive")
    public ResponseEntity<String> archiveAnnouncement(@PathVariable Long announcementId) {
        announcementService.archiveAnnouncement(announcementId);
        return ResponseEntity.ok("Announcement archived successfully.");
    }

    /**
     * Delete an announcement by ID.
     *
     * @param announcementId ID of the announcement to delete.
     * @return Success message.
     */
    @DeleteMapping("/{announcementId}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
        return ResponseEntity.ok("Announcement deleted successfully.");
    }


    /**
     * Update engagement metrics for a specific announcement.
     *
     * @param announcementId The ID of the announcement.
     * @return Success message.
     */
    @PutMapping("/{announcementId}/engagement")
    public ResponseEntity<Void> updateEngagementMetrics(
            @PathVariable Long announcementId,
            @RequestParam int viewCount,
            @RequestParam int replyCount) {
        announcementService.updateEngagementMetrics(announcementId, viewCount, replyCount);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all announcements.
     * @return A list of AnnouncementDto.
     */
    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    /**
     * Retrieves announcements by category.
     * @param category The category to filter by.
     * @return A list of AnnouncementDto.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(announcementService.getAnnouncementsByCategory(category));
    }

    /**
     * Retrieves pinned announcements.
     * @return A list of pinned AnnouncementDto.
     */
    @GetMapping("/pinned")
    public ResponseEntity<List<AnnouncementDto>> getPinnedAnnouncements() {
        return ResponseEntity.ok(announcementService.getPinnedAnnouncements());
    }

    @PostMapping("/assessments/{assessmentId}/reminder")
    public ResponseEntity<AnnouncementDto> createAssessmentReminder(
            @PathVariable Long assessmentId,
            @RequestParam String reminderMessage,
            @RequestParam LocalDateTime reminderDate) {
        return ResponseEntity.ok(announcementService.createAssessmentReminder(assessmentId, reminderMessage, reminderDate));
    }


    @PostMapping("/assignments/{assignmentId}/reminder")
    public ResponseEntity<AnnouncementDto> createAssignmentReminder(
            @PathVariable Long assignmentId,
            @RequestParam String reminderMessage,
            @RequestParam LocalDateTime reminderDate) {
        return ResponseEntity.ok(announcementService.createAssignmentReminder(assignmentId, reminderMessage, reminderDate));
    }

    @PostMapping("/{announcementId}/attachments")
    public ResponseEntity<AttachmentDto> addAttachmentToAnnouncement(
            @PathVariable Long announcementId,
            @RequestBody AttachmentDto attachmentDto) {
        return ResponseEntity.ok(announcementService.addAttachmentToAnnouncement(announcementId, attachmentDto));
    }


    @PostMapping("/{announcementId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long announcementId, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(announcementService.addCommentToAnnouncement(announcementId, commentDto));
    }

    @GetMapping("/{announcementId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long announcementId) {
        return ResponseEntity.ok(announcementService.getCommentsForAnnouncement(announcementId));
    }

    @PostMapping("/comments/{parentCommentId}/reply")
    public ResponseEntity<CommentDto> replyToComment(@PathVariable Long parentCommentId, @RequestBody CommentDto replyDto) {
        return ResponseEntity.ok(announcementService.replyToAnnouncementComment(parentCommentId, replyDto));
    }


    @GetMapping("/{id}/localized")
    public ResponseEntity<AnnouncementDto> getLocalizedAnnouncement(
            @PathVariable Long id,
            @RequestParam("language") String languageCode) {
        AnnouncementDto localizedAnnouncement = announcementService.getLocalizedAnnouncement(id, languageCode);
        return ResponseEntity.ok(localizedAnnouncement);
    }

    /**
     * Trigger sending announcement reminders.
     */
    @PostMapping("/reminders")
    public ResponseEntity<Void> sendAnnouncementReminders() {
        announcementService.sendAnnouncementReminders();
        return ResponseEntity.ok().build();
    }


    /**
     * Updates the engagement metrics for an announcement.
     *
     * @param announcementId the ID of the announcement
     * @param userId         the ID of the user interacting with the announcement
     * @return ResponseEntity indicating the status of the operation
     */
    @PostMapping("/{announcementId}/engagement")
    public ResponseEntity<String> updateEngagementMetrics(
            @PathVariable Long announcementId,
            @RequestParam Long userId
    ) {
        try {
            announcementService.updateEngagementMetrics(announcementId, userId);
            return ResponseEntity.ok("Engagement metrics updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating engagement metrics.");
        }
    }

    @GetMapping("/{announcementId}/engagement")
    public ResponseEntity<EngagementMetricsDto> getEngagementMetrics(@PathVariable Long announcementId) {
        EngagementMetricsDto metrics = announcementService.getAnnouncementEngagementMetrics(announcementId);
        return ResponseEntity.ok(metrics);
    }

    // Search announcements by keyword
    @GetMapping("/search")
    public List<AnnouncementDto> searchAnnouncements(@RequestParam String keyword) {
        return announcementService.searchAnnouncements(keyword);
    }

    /**
     * Increment the view count for a specific announcement.
     *
     * @param announcementId The ID of the announcement to update.
     * @return ResponseEntity indicating the result of the operation.
     */
    @PostMapping("/{announcementId}/increment-view-count")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long announcementId) {
        try {
            announcementService.incrementAnnouncementViewCount(announcementId);
            return ResponseEntity.ok().build(); // Return 200 OK if successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if announcement is not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 for unexpected errors
        }
    }


    /**
     * Automatically create an assignment reminder announcement.
     *
     * @param assignment The assignment details.
     * @return Success message.
     */
//    @PostMapping("/assignment-reminder")
//    public ResponseEntity<String> createAssignmentReminder(@RequestBody Assignment assignment) {
//        announcementService.createAssignmentReminder(assignment);
//        return ResponseEntity.ok("Assignment reminder created successfully.");
//    }
}

