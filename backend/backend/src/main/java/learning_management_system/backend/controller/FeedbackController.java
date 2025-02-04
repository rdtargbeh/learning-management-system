package learning_management_system.backend.controller;

import learning_management_system.backend.dto.FeedbackDto;
import learning_management_system.backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @PostMapping
    public ResponseEntity<FeedbackDto> createFeedback(@RequestBody FeedbackDto feedbackDto) {
        FeedbackDto createdFeedback = feedbackService.createFeedback(feedbackDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long feedbackId) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(feedbackId));
    }

    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByRecipient(@PathVariable Long recipientId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByRecipient(recipientId));
    }


//    ("/entity/{entityId}/{entityType}")
    @GetMapping("/entity")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByEntity(
            @PathVariable Long entityId,
            @PathVariable String entityType) {
        List<FeedbackDto> feedbackList = feedbackService.getFeedbackByEntity(entityType, entityId);
        return ResponseEntity.ok(feedbackList);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback deleted successfully.");
    }

    @GetMapping("/given-by/{userId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackGivenByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackGivenByUser(userId));
    }

    @GetMapping("/received-by/{userId}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackReceivedByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackReceivedByUser(userId));
    }

    // Endpoint to get feedback by visibility
    @GetMapping("/visibility/{visibility}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByVisibility(@PathVariable String visibility) {
        List<FeedbackDto> feedbacks = feedbackService.getFeedbackByVisibility(visibility);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/replies/{parentFeedbackId}")
    public ResponseEntity<List<FeedbackDto>> getReplies(@PathVariable Long parentFeedbackId) {
        List<FeedbackDto> replies = feedbackService.getReplies(parentFeedbackId);
        return ResponseEntity.ok(replies);
    }

    @PutMapping("/{feedbackId}/read")
    public ResponseEntity<Void> markFeedbackAsRead(@PathVariable Long feedbackId) {
        feedbackService.markFeedbackAsRead(feedbackId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/peer-reviews")
    public ResponseEntity<List<FeedbackDto>> getPeerReviews() {
        List<FeedbackDto> peerReviews = feedbackService.getPeerReviews();
        return ResponseEntity.ok(peerReviews);
    }

}
