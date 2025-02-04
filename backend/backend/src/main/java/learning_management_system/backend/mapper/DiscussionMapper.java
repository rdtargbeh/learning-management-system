package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.DiscussionDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.Course;
import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.repository.AttachmentRepository;
import learning_management_system.backend.repository.CourseRepository;
import learning_management_system.backend.repository.DiscussionRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscussionMapper {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    public DiscussionDto toDto(Discussion discussion) {
        DiscussionDto dto = new DiscussionDto();
        dto.setDiscussionId(discussion.getDiscussionId());
        dto.setTitle(discussion.getTitle());
        dto.setDescription(discussion.getDescription());
        dto.setCourseId(discussion.getCourse() != null ? discussion.getCourse().getCourseId() : null);
        dto.setAuthorId(discussion.getAuthor() != null ? discussion.getAuthor().getUserId() : null);
        dto.setParentDiscussionId(discussion.getParentDiscussion() != null ? discussion.getParentDiscussion().getDiscussionId() : null);
        dto.setCategory(discussion.getCategory());
        dto.setStatus(discussion.getStatus());
        dto.setVisibility(discussion.getVisibility());
        dto.setTags(discussion.getTags());
        dto.setPinned(discussion.getPinned());
        dto.setLocked(discussion.getLocked());
        dto.setAnonymousAllowed(discussion.getAnonymousAllowed());
        dto.setAllowReplies(discussion.getAllowReplies());
        dto.setCommentCount(discussion.getCommentCount());
        dto.setAttachmentIds(discussion.getAttachments() != null ?
                discussion.getAttachments().stream().map(Attachment::getAttachmentId).collect(Collectors.toList()) : null);

        // Handle mentioned users
        dto.setMentionedUserIds(discussion.getMentionedUsers() != null ?
                discussion.getMentionedUsers().stream().map(User::getUserId).collect(Collectors.toList()) : null);

        dto.setDateCreated(discussion.getDateCreated());
        dto.setDateUpdated(discussion.getDateUpdated());
        return dto;
    }

    public Discussion toEntity(DiscussionDto dto) {
        Discussion discussion = new Discussion();
        discussion.setTitle(dto.getTitle());
        discussion.setDescription(dto.getDescription());

        // Fetch and set course
        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + dto.getCourseId()));
            discussion.setCourse(course);
        }
        // Fetch and set author
        if (dto.getAuthorId() != null) {
            User author = userRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getAuthorId()));
            discussion.setAuthor(author);
        }
        // Fetch and set parent discussion
        if (dto.getParentDiscussionId() != null) {
            Discussion parentDiscussion = discussionRepository.findById(dto.getParentDiscussionId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent discussion not found with ID: " + dto.getParentDiscussionId()));
            discussion.setParentDiscussion(parentDiscussion);
        }

        discussion.setCategory(dto.getCategory());
        discussion.setStatus(dto.getStatus());
        discussion.setVisibility(dto.getVisibility());
        discussion.setTags(dto.getTags());
        discussion.setPinned(dto.getPinned());
        discussion.setLocked(dto.getLocked());
        discussion.setAnonymousAllowed(dto.getAnonymousAllowed());
        discussion.setAllowReplies(dto.getAllowReplies());
        discussion.setCommentCount(dto.getCommentCount());

        // Resolve and set attachments
        if (dto.getAttachmentIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(dto.getAttachmentIds());
            discussion.setAttachments(attachments); // Use the list directly
        }
        // Resolve and set mentioned users
        if (dto.getMentionedUserIds() != null) {
            List<User> mentionedUsers = userRepository.findAllById(dto.getMentionedUserIds());
            discussion.setMentionedUsers(new HashSet<>(mentionedUsers));
        }

        discussion.setDateCreated(dto.getDateCreated());
        discussion.setDateUpdated(dto.getDateUpdated());
        return discussion;
    }



}

