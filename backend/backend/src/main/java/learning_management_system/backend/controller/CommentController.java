package learning_management_system.backend.controller;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.dto.DiscussionDto;
import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.mapper.DiscussionMapper;
import learning_management_system.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")

public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private DiscussionMapper discussionMapper;


    /**
     * Create a new comment.
     *
     * @param commentDto Comment details.
     * @return Created comment DTO.
     */
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.createComment(commentDto));
    }

    /**
     * Reply to an existing comment.
     *
     * @param parentCommentId Parent comment ID.
     * @param replyDto        Reply details.
     * @return Created reply DTO.
     */
    @PostMapping("/{parentCommentId}/reply")
    public ResponseEntity<CommentDto> replyToComment(
            @PathVariable Long parentCommentId,
            @RequestBody CommentDto replyDto) {
        return ResponseEntity.ok(commentService.replyToComment(parentCommentId, replyDto));
    }

    /**
     * Update an existing comment.
     *
     * @param commentId  Comment ID to update.
     * @param commentDto Updated comment details.
     * @return Updated comment DTO.
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId,
                                                    @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @GetMapping("/entity")
    public ResponseEntity<List<CommentDto>> getCommentsForEntity(@RequestParam String linkedEntityType, @RequestParam Long linkedEntityId) {
        return ResponseEntity.ok(commentService.getCommentsForEntity(linkedEntityType, linkedEntityId));
    }

    @GetMapping("/replies/{parentCommentId}")
    public ResponseEntity<List<CommentDto>> getRepliesForComment(@PathVariable Long parentCommentId) {
        return ResponseEntity.ok(commentService.getRepliesForComment(parentCommentId));
    }

    @PostMapping("/{commentId}/archive")
    public ResponseEntity<Void> archiveComment(@PathVariable Long commentId) {
        commentService.archiveComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/flag")
    public ResponseEntity<Void> flagComment(@PathVariable Long commentId) {
        commentService.flagComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countComments(
            @RequestParam LinkedEntityType linkedEntityType,
            @RequestParam Long linkedEntityId) {
        int count = commentService.countComments(linkedEntityType, linkedEntityId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{commentId}/discussion")
    public ResponseEntity<DiscussionDto> getLinkedDiscussion(@PathVariable Long commentId) {
        Discussion discussion = commentService.getLinkedDiscussion(commentId);
        DiscussionDto discussionDto = discussionMapper.toDto(discussion);
        return ResponseEntity.ok(discussionDto);
    }
}
