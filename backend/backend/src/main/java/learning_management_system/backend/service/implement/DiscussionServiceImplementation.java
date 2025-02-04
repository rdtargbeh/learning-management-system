package learning_management_system.backend.service.implement;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.dto.DiscussionDto;
import learning_management_system.backend.entity.*;
import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.enums.RelatedEntityType;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.mapper.DiscussionMapper;
import learning_management_system.backend.repository.*;
import learning_management_system.backend.service.CommentService;
import learning_management_system.backend.service.DiscussionService;
import learning_management_system.backend.service.LmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DiscussionServiceImplementation implements DiscussionService {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private  CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscussionMapper discussionMapper;
    @Lazy
    @Autowired
    private LmsNotificationService notificationService;
    @Autowired
    private AttachmentRepository attachmentRepository;


    // Create New Discussion
    @Override
    public DiscussionDto createDiscussion(DiscussionDto discussionDto) {
        // Validate course
        Course course = courseRepository.findById(discussionDto.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Validate author
        User author = userRepository.findById(discussionDto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        // Map DTO to entity
        Discussion discussion = discussionMapper.toEntity(discussionDto);
        discussion.setCourse(course);
        discussion.setAuthor(author);

        // Validate and attach files if provided
        if (discussionDto.getAttachmentIds() != null && !discussionDto.getAttachmentIds().isEmpty()) {
            List<Attachment> attachments = attachmentRepository.findAllById(discussionDto.getAttachmentIds());
            validateAttachmentsExist(attachments, discussionDto.getAttachmentIds());
            discussion.setAttachments(attachments);
        }

        // Save discussion
        Discussion savedDiscussion = discussionRepository.save(discussion);

        // Notify mentioned users
        if (discussionDto.getTags() != null) {
            List<Long> mentionedUserIds = extractMentionedUserIds(discussionDto.getTags());
            notifyMentionedUsers(savedDiscussion.getDiscussionId(), mentionedUserIds);
        }

        return discussionMapper.toDto(savedDiscussion);
    }

    @Override
    public DiscussionDto updateDiscussion(Long discussionId, DiscussionDto dto) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new IllegalArgumentException("Discussion not found"));
        discussion.setTitle(dto.getTitle());
        discussion.setDescription(dto.getDescription());
        discussion.setPinned(dto.getPinned());
        discussion.setLocked(dto.getLocked());
        discussion.setAllowReplies(dto.getAllowReplies());
        discussion.setTags(dto.getTags());
        discussion.setStatus(DiscussionStatus.valueOf(String.valueOf(dto.getStatus())));

        discussion = discussionRepository.save(discussion);

        return discussionMapper.toDto(discussion);
    }


    @Override
    public void notifyMentionedUsers(Long discussionId, List<Long> mentionedUserIds) {
        mentionedUserIds.forEach(userId -> {
            // Construct metadata
            String metadata = "Check out the discussion with ID: " + discussionId;

            // Pass correct parameters
            notificationService.createAndSendNotification(
                    userId,
                    "You have been mentioned in a discussion",
                    discussionId, // entityId should be Long
                    RelatedEntityType.DISCUSSION, // Enum
                    metadata // Metadata should be String
            );
        });
    }


    @Override
    public List<DiscussionDto> getDiscussionsByCourseId(Long courseId) {
        List<Discussion> discussions = discussionRepository.findDiscussionsByCourseId(courseId);
        return discussions.stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDto> getDiscussionsByAuthor(Long authorId) {
        return discussionRepository.findByAuthor(authorId).stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiscussionDto> getDiscussionsByVisibility(VisibleTo visibility) {
        List<Discussion> discussions = discussionRepository.findByVisibility(visibility);
        return discussions.stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void togglePinDiscussion(Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found."));
        discussion.setPinned(!discussion.getPinned());
        discussionRepository.save(discussion);
    }

    @Override
    public List<DiscussionDto> getPinnedDiscussions(Long courseId) {
        return discussionRepository.findPinnedDiscussions(courseId)
                .stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void toggleLockDiscussion(Long discussionId) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found."));
        discussion.setLocked(!discussion.getLocked());
        discussionRepository.save(discussion);
    }

    @Override
    public void updateDiscussionStatus(Long discussionId, DiscussionStatus status) {
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found."));
        discussion.setStatus(status);
        discussionRepository.save(discussion);
    }

    @Override
    public List<DiscussionDto> getDiscussionsByStatus(Long courseId, DiscussionStatus status) {
        return discussionRepository.findByStatusAndCourseId(status, courseId)
                .stream()
                .map(discussionMapper::toDto)
                .collect(Collectors.toList());
    }

    // Add a comment to a discussion
    @Override
    public CommentDto addCommentToDiscussion(Long discussionId, CommentDto commentDto) {
        // Validate discussion exists
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found."));

        // Set the linked entity for the comment
        commentDto.setLinkedEntityType(LinkedEntityType.DISCUSSION); // Use the enum value
        commentDto.setLinkedEntityId(discussionId);

        // Create the comment via the service
        CommentDto createdComment = commentService.createComment(commentDto);

        // Increment the comment count for the discussion
        discussion.setCommentCount(discussion.getCommentCount() + 1);
        discussionRepository.save(discussion);

        // Notify discussion author
        notificationService.notifyUser(
                discussion.getAuthor().getUserId(),
                "A new comment has been added to your discussion: " + discussion.getTitle(),
                discussionId,
                "DISCUSSION"
        );

        return createdComment;
    }

    // Fetch all comments for a discussion
    @Override
    public List<CommentDto> getCommentsForDiscussion(Long discussionId) {
        // Validate discussion exists
        if (!discussionRepository.existsById(discussionId)) {
            throw new RuntimeException("Discussion not found.");
        }

        return commentService.getCommentsByEntity("DISCUSSION", discussionId);
    }


    // Reply to a discussion comment
    @Override
    public CommentDto replyToDiscussionComment(Long parentCommentId, CommentDto replyDto) {
        // Validate parent comment exists
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found."));

        CommentDto createdReply = commentService.replyToComment(parentCommentId, replyDto);

        // Notify parent comment author
        notificationService.notifyUser(
                parentComment.getAuthor().getUserId(),
                "Your comment has a new reply.",
                parentComment.getLinkedEntityId(),
                parentComment.getLinkedEntityType().name()
        );

        return createdReply;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found."));

        if (!LinkedEntityType.DISCUSSION.equals(comment.getLinkedEntityType())) {
            throw new IllegalArgumentException("This comment is not linked to a discussion.");
        }

        Discussion discussion = discussionRepository.findById(comment.getLinkedEntityId())
                .orElseThrow(() -> new RuntimeException("Discussion not found."));

        discussion.setCommentCount(discussion.getCommentCount() - 1);
        discussionRepository.save(discussion);

        commentRepository.delete(comment);
    }



    private List<Long> extractMentionedUserIds(String tags) {
        // List to store user IDs of mentioned users
        List<Long> mentionedUserIds = new ArrayList<>();

        // Regex to match mentions (e.g., @username)
        Pattern mentionPattern = Pattern.compile("@(\\w+)");
        Matcher matcher = mentionPattern.matcher(tags);

        // Extract usernames from tags
        List<String> mentionedUsernames = new ArrayList<>();
        while (matcher.find()) {
            mentionedUsernames.add(matcher.group(1)); // Group 1 contains the username
        }

        // Lookup user IDs for the extracted usernames
        if (!mentionedUsernames.isEmpty()) {
            mentionedUserIds = userRepository.findUserIdsByUsernames(mentionedUsernames);
        }

        return mentionedUserIds;
    }


    private void validateAttachmentsExist(List<Attachment> attachments, List<Long> attachmentIds) {
        if (attachments.size() != attachmentIds.size()) {
            throw new RuntimeException("One or more attachments not found.");
        }
    }

    @Override
    public Page<DiscussionDto> getPublicDiscussionsByCourse(Long courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Discussion> discussions = discussionRepository.findDiscussionsByCourseIdAndVisibility(courseId, VisibleTo.PUBLIC, pageable);
        return discussions.map(discussionMapper::toDto);
    }


    // Reply to discussion
//    @Override
//    public DiscussionDto replyToDiscussion(Long parentDiscussionId, DiscussionDto replyDto) {
//        // Validate the parent discussion exists
//        Discussion parentDiscussion = discussionRepository.findById(parentDiscussionId)
//                .orElseThrow(() -> new RuntimeException("Parent discussion not found."));
//
//        // Map the reply DTO to a Discussion entity
//        Discussion reply = discussionMapper.toEntity(replyDto);
//        reply.setParentDiscussion(parentDiscussion); // Link to the parent discussion
//        reply.setCourse(parentDiscussion.getCourse()); // Inherit the course from the parent discussion
//
//        // Validate and set the author
//        reply.setAuthor(userRepository.findById(replyDto.getAuthorId())
//                .orElseThrow(() -> new RuntimeException("Author not found.")));
//
//        // Save the reply as a new discussion thread
//        Discussion savedReply = discussionRepository.save(reply);
//
//        // Optionally: Notify the original discussion's author or other users
//        notificationService.notifyUser(
//                parentDiscussion.getAuthor().getUserId(),
//                "Your discussion has a new reply: " + parentDiscussion.getTitle(),
//                parentDiscussion.getDiscussionId(),
//                "DISCUSSION"
//        );
//
//
//        return discussionMapper.toDto(savedReply);
//    }


}
