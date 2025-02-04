package learning_management_system.backend.controller;

import learning_management_system.backend.dto.DiscussionAnalyticsDto;
import learning_management_system.backend.service.DiscussionAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/discussions/analytics")
public class DiscussionAnalyticsController {

    @Autowired
    private DiscussionAnalyticsService analyticsService;


    /**
     * Get analytics for a specific discussion.
     */
    @GetMapping("/{discussionId}")
    public ResponseEntity<DiscussionAnalyticsDto> getAnalytics(@PathVariable Long discussionId) {
        DiscussionAnalyticsDto analytics = analyticsService.getAnalyticsByDiscussionId(discussionId);
        return ResponseEntity.ok(analytics);
    }

    /**
     * Increment the view count for a discussion.
     */
    @PostMapping("/{discussionId}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long discussionId) {
        analyticsService.incrementViewCount(discussionId);
        return ResponseEntity.ok().build();
    }

    /**
     * Increment the reply count for a discussion and track participant.
     */
    @PostMapping("/{discussionId}/reply")
    public ResponseEntity<Void> incrementReplyCount(@PathVariable Long discussionId, @RequestParam Long userId) {
        analyticsService.incrementReplyCount(discussionId, userId);
        return ResponseEntity.ok().build();
    }


    /**
     * Update unique participants for a discussion.
     */
    @PostMapping("/{discussionId}/participant")
    public ResponseEntity<Void> updateUniqueParticipants(@PathVariable Long discussionId,
                                                         @RequestParam Long userId) {
        analyticsService.updateUniqueParticipants(discussionId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Record custom activity details for a discussion.
     */
    @PostMapping("/{discussionId}/activity")
    public ResponseEntity<Void> recordActivity(
            @PathVariable Long discussionId,
            @RequestBody String details) {
        analyticsService.recordActivity(discussionId, details);
        return ResponseEntity.ok().build();
    }
}

