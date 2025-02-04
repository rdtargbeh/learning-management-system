package learning_management_system.backend.controller;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.dto.DiscussionDto;
import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.service.DiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discussions")
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;

    // Create a new discussion
    @PostMapping
    public ResponseEntity<DiscussionDto> createDiscussion(@RequestBody DiscussionDto discussionDto) {
        DiscussionDto createdDiscussion = discussionService.createDiscussion(discussionDto);
        return new ResponseEntity<>(createdDiscussion, HttpStatus.CREATED);
    }

    // Update a discussion
    @PutMapping("/{discussionId}")
    public ResponseEntity<DiscussionDto> updateDiscussion(@PathVariable Long discussionId, @RequestBody DiscussionDto discussionDto) {
        DiscussionDto updatedDiscussion = discussionService.updateDiscussion(discussionId, discussionDto);
        return ResponseEntity.ok(updatedDiscussion);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<DiscussionDto>> getDiscussionsByCourseId(@PathVariable Long courseId) {
        List<DiscussionDto> discussions = discussionService.getDiscussionsByCourseId(courseId);
        return ResponseEntity.ok(discussions);
    }

    // Get discussions by course
    @GetMapping("/courseId")
    public ResponseEntity<List<DiscussionDto>> getDiscussionsByCourse(@PathVariable Long courseId) {
        List<DiscussionDto> discussions = discussionService.getDiscussionsByCourseId(courseId);
        return ResponseEntity.ok(discussions);
    }

    // Get pinned discussions for a course
    @GetMapping("/course/{courseId}/pinned")
    public ResponseEntity<List<DiscussionDto>> getPinnedDiscussions(@PathVariable Long courseId) {
        List<DiscussionDto> pinnedDiscussions = discussionService.getPinnedDiscussions(courseId);
        return ResponseEntity.ok(pinnedDiscussions);
    }

    // Add a comment to a discussion
    @PostMapping("/{discussionId}/comments")
    public ResponseEntity<CommentDto> addComment(@PathVariable Long discussionId, @RequestBody CommentDto commentDto) {
        CommentDto addedComment = discussionService.addCommentToDiscussion(discussionId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedComment);
    }


    @GetMapping("/{discussionId}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long discussionId) {
        return ResponseEntity.ok(discussionService.getCommentsForDiscussion(discussionId));
    }


    // Reply to a discussion comment
    @PostMapping("/comments/{parentCommentId}/reply")
    public ResponseEntity<CommentDto> replyToComment(@PathVariable Long parentCommentId, @RequestBody CommentDto replyDto) {
        CommentDto reply = discussionService.replyToDiscussionComment(parentCommentId, replyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    // Toggle pinning a discussion
    @PatchMapping("/{discussionId}/pin")
    public ResponseEntity<Void> togglePin(@PathVariable Long discussionId) {
        discussionService.togglePinDiscussion(discussionId);
        return ResponseEntity.ok().build();
    }

    // Toggle locking a discussion
    @PatchMapping("/{discussionId}/lock")
    public ResponseEntity<Void> toggleLock(@PathVariable Long discussionId) {
        discussionService.toggleLockDiscussion(discussionId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to fetch public discussions for a specific course with pagination.
     *
     * @param courseId The ID of the course.
     * @param page     The page number (starting from 0).
     * @param size     The size of the page.
     * @return A paginated list of public discussions for the course.
     */
    @GetMapping("/courses/{courseId}/discussions/public")
    public ResponseEntity<Page<DiscussionDto>> getPublicDiscussions(
            @PathVariable Long courseId,
            @RequestParam int page,
            @RequestParam int size) {
        Page<DiscussionDto> discussions = discussionService.getPublicDiscussionsByCourse(courseId, page, size);
        return ResponseEntity.ok(discussions);
    }



    /**
     * Delete a comment by its ID.
     *
     * @param commentId ID of the comment to delete.
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        discussionService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update the status of a discussion.
     *
     * @param discussionId ID of the discussion.
     * @param status       New status to set.
     */
    @PutMapping("/{discussionId}/status")
    public ResponseEntity<Void> updateDiscussionStatus(
            @PathVariable Long discussionId,
            @RequestParam DiscussionStatus status) {
        discussionService.updateDiscussionStatus(discussionId, status);
        return ResponseEntity.ok().build();
    }

    /**
     * Get discussions by status within a course.
     *
     * @param courseId ID of the course.
     * @param status   Status of the discussions to filter.
     * @return List of discussions with the given status.
     */
    @GetMapping("/course/{courseId}/status")
    public ResponseEntity<List<DiscussionDto>> getDiscussionsByStatus(
            @PathVariable Long courseId,
            @RequestParam DiscussionStatus status) {
        List<DiscussionDto> discussions = discussionService.getDiscussionsByStatus(courseId, status);
        return ResponseEntity.ok(discussions);
    }

    /**
     * Get discussions by visibility.
     *
     * @param visibility Visibility filter for discussions.
     * @return List of discussions with the given visibility.
     */
    @GetMapping("/visibility")
    public ResponseEntity<List<DiscussionDto>> getDiscussionsByVisibility(
            @RequestParam VisibleTo visibility) {
        List<DiscussionDto> discussions = discussionService.getDiscussionsByVisibility(visibility);
        return ResponseEntity.ok(discussions);
    }


    /**
     * Get discussions by author ID.
     *
     * @param authorId ID of the author.
     * @return List of discussions created by the author.
     */
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<DiscussionDto>> getDiscussionsByAuthor(@PathVariable Long authorId) {
        List<DiscussionDto> discussions = discussionService.getDiscussionsByAuthor(authorId);
        return ResponseEntity.ok(discussions);
    }


    /**
     * Reply to a discussion thread.
     *
     * @param parentDiscussionId ID of the parent discussion.
     * @param replyDto           Data for the reply.
     * @return The created reply discussion.
     */
//    @PostMapping("/{parentDiscussionId}/reply")
//    public ResponseEntity<DiscussionDto> replyToDiscussion(
//            @PathVariable Long parentDiscussionId,
//            @RequestBody DiscussionDto replyDto) {
//        DiscussionDto reply = discussionService.replyToDiscussion(parentDiscussionId, replyDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
//    }


}

