package learning_management_system.backend.service.implement;

import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.Comment;
import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.mapper.CommentMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.CommentService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static learning_management_system.backend.utility.CommentUserMention.extractMentionsFromContent;

@Service
public class CommentServiceImplementation implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private CourseRepository courseRepository;




    /**
     * Create a new comment for a linked entity.
     *
     * @param commentDto Comment details.
     * @return Created comment DTO.
     */
    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto) {
        // Map DTO to entity
        Comment comment = commentMapper.toEntity(commentDto);
        // Validate the linked entity
        validateLinkedEntity(comment.getLinkedEntityType(), comment.getLinkedEntityId());

        // Handle author if not anonymous
        if (Boolean.FALSE.equals(commentDto.getAnonymous()) && commentDto.getAuthorId() != null) {
            User author = userRepository.findById(commentDto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with ID: " + commentDto.getAuthorId()));
            comment.setAuthor(author);
        }
        // Handle attachments
        if (commentDto.getAttachmentIds() != null && !commentDto.getAttachmentIds().isEmpty()) {
            List<Attachment> attachments = attachmentRepository.findAllById(commentDto.getAttachmentIds());
            attachments.forEach(attachment -> attachment.setLinkedEntityId(comment.getCommentId()));

            // Convert List to Set before assigning
            comment.setAttachments(new HashSet<>(attachments));
        }
        // Set additional properties
        comment.setDateCreated(new Date());
        comment.setLastReplyDate(new Date());
        // Save the comment
        Comment savedComment = commentRepository.save(comment);
        // Handle mentions
        handleMentions(savedComment);

        // Return the saved comment as a DTO
        return commentMapper.toDto(savedComment);
    }


    /**
     * Reply to an existing comment.
     *
     * @param parentCommentId ID of the parent comment.
     * @param replyDto        Reply details.
     * @return Created reply DTO.
     */
    @Override
    public CommentDto replyToComment(Long parentCommentId, CommentDto replyDto) {

        // Fetch the parent comment
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found with ID: " + parentCommentId));

        // Validate the linked entity
        validateLinkedEntity(parentComment.getLinkedEntityType(), parentComment.getLinkedEntityId());

        // Map DTO to entity and set parent comment
        Comment reply = commentMapper.toEntity(replyDto);
        reply.setParentComment(parentComment);

        // Update the number of replies for the parent comment
        parentComment.setNumberOfReplies(parentComment.getNumberOfReplies() + 1);
        parentComment.setLastReplyDate(new Date());
        commentRepository.save(parentComment);

        // Handle author if not anonymous
        if (Boolean.FALSE.equals(replyDto.getAnonymous()) && replyDto.getAuthorId() != null) {
            User author = userRepository.findById(replyDto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with ID: " + replyDto.getAuthorId()));
            reply.setAuthor(author);
        }

        // Set inherited properties
        reply.setLinkedEntityId(parentComment.getLinkedEntityId());
        reply.setLinkedEntityType(parentComment.getLinkedEntityType());
        reply.setVisibility(parentComment.getVisibility()); // Inherit visibility
        reply.setDateCreated(new Date());
        reply.setLastReplyDate(new Date());

        // Save and return
        return commentMapper.toDto(commentRepository.save(reply));
    }


    /**
     * Update an existing comment.
     *
     * @param commentId  ID of the comment to update.
     * @param commentDto Updated comment details.
     * @return Updated comment DTO.
     */
    @Override
    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));

        // Prevent updates on archived or deleted comments
        if (existingComment.getDateArchived() != null || existingComment.getDateDeleted() != null) {
            throw new RuntimeException("Cannot update archived or deleted comment.");
        }

        // Update content
        if (commentDto.getContent() != null) {
            existingComment.setContent(commentDto.getContent());
            existingComment.setEdited(true);
            existingComment.setEditHistory((existingComment.getEditHistory() == null ? "" : existingComment.getEditHistory() + "\n")
                    + "Edited at " + new Date() + ": " + commentDto.getContent());
        }

        // Save updated fields
        existingComment.setDateUpdated(new Date());
        return commentMapper.toDto(commentRepository.save(existingComment));
    }

    // Delete a comment
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // Get comment by ID
    @Override
    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));

        comment.setViewCount(comment.getViewCount() + 1);
        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    // Get comment by entity
    @Override
    public List<CommentDto> getCommentsForEntity(String linkedEntityType, Long linkedEntityId) {
        return commentRepository.findByLinkedEntityTypeAndLinkedEntityId(linkedEntityType, linkedEntityId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get reply comment
    @Override
    public List<CommentDto> getRepliesForComment(Long parentCommentId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        return commentRepository.findByParentComment(parentComment)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void archiveComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setDateArchived(new Date());
        commentRepository.save(comment);
    }

    @Override
    public void flagComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setFlaggedCount(comment.getFlaggedCount() + 1);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEntity(String linkedEntityType, Long linkedEntityId) {
        List<Comment> comments = commentRepository.findByLinkedEntityTypeAndLinkedEntityId(linkedEntityType, linkedEntityId);
        return comments.stream().map(commentMapper::toDto).collect(Collectors.toList());
    }


    // Add Reactions
    public void addReaction(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));
        comment.setReactions(comment.getReactions() + 1);
        commentRepository.save(comment);
    }

    // Remove Reaction
    public void removeReaction(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));
        comment.setReactions(Math.max(0, comment.getReactions() - 1));
        commentRepository.save(comment);
    }


    private void handleMentions(Comment comment) {

        // Extract mentions (e.g., @username) from comment content
        List<String> mentions = extractMentionsFromContent(comment.getContent());

        mentions.forEach(username -> {
            User mentionedUser = userRepository.findByUserName(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            notificationService.notifyUser(
                    mentionedUser.getUserId(),
                    "You were mentioned in a comment.",
                    comment.getLinkedEntityId(),
                    comment.getLinkedEntityType().name() // Convert LinkedEntityType to String
            );
        });

        comment.setMentionUsers(String.join(",", mentions));
        commentRepository.save(comment);
    }


    @Override
    public int countComments(LinkedEntityType linkedEntityType, Long linkedEntityId) {
        return commentRepository.countByLinkedEntityTypeAndLinkedEntityId(linkedEntityType, linkedEntityId);
    }

    @Override
    public Discussion getLinkedDiscussion(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (!LinkedEntityType.DISCUSSION.equals(comment.getLinkedEntityType())) {
            throw new IllegalArgumentException("This comment is not linked to a discussion.");
        }

        return discussionRepository.findById(comment.getLinkedEntityId())
                .orElseThrow(() -> new RuntimeException("Discussion not found."));
    }



    // Validate Linked Entity ID and Entity Type are valid or selected.
    private void validateLinkedEntity(LinkedEntityType  linkedEntityType, Long linkedEntityId) {
        switch (linkedEntityType) {
            case ANNOUNCEMENT:
                if (!announcementRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Invalid announcement ID: " + linkedEntityId);
                }
                break;
            case DISCUSSION:
                if (!discussionRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Invalid discussion ID: " + linkedEntityId);
                }
                break;
            case GROUP:
                if (!studentGroupRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Invalid group ID: " + linkedEntityId);
                }
                break;
            case FEEDBACK:
                if (!feedbackRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Invalid feedback ID: " + linkedEntityId);
                }
                break;
            case COURSE:
                if (!courseRepository.existsById(linkedEntityId)) {
                    throw new RuntimeException("Linked course not found.");
                }
                break;
            default:
                throw new RuntimeException("Unsupported linked entity type: " + linkedEntityType);
        }
    }

    //    private void handleMentions(Comment comment) {
//        // Extract mentions (e.g., @username) from comment content
//        List<String> mentions = extractMentionsFromContent(comment.getContent());
//
//        // Convert mentions to user IDs and set them
//        String mentionIds = mentions.stream()
//                .map(username -> userRepository.findByUsername(username).orElseThrow(() ->
//                        new RuntimeException("User not found: " + username)).getUserId().toString())
//                .collect(Collectors.joining(","));
//        comment.setMentionUsers(mentionIds);
//
//        // Notify mentioned users
//        mentions.forEach(username -> notificationService.notifyUser(username, "You were mentioned in a comment."));
//    }


}

