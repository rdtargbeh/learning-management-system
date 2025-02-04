package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.entity.Attachment;
import learning_management_system.backend.entity.Comment;
import learning_management_system.backend.entity.StudentGroup;
import learning_management_system.backend.entity.User;
import learning_management_system.backend.enums.CommentCategory;
import learning_management_system.backend.enums.CommentStatus;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.repository.AttachmentRepository;
import learning_management_system.backend.repository.CommentRepository;
import learning_management_system.backend.repository.StudentGroupRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", uses = {AttachmentMapper1.class})

@Component
public class CommentMapper {

    private final UserRepository userRepository; // For resolving User entities.
    private final CommentRepository commentRepository; // For resolving parent comments.
    private final AttachmentRepository attachmentRepository; // For handling attachments.
    private final StudentGroupRepository groupRepository; // For resolving Group entities.

    @Autowired
    public CommentMapper(UserRepository userRepository,
                         CommentRepository commentRepository,
                         AttachmentRepository attachmentRepository,
                         StudentGroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.attachmentRepository = attachmentRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Converts a `Comment` entity to a `CommentDto`.
     *
     * @param comment The `Comment` entity.
     * @return The corresponding `CommentDto`.
     */
    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto dto = new CommentDto();
        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setContentProcessed(comment.getContentProcessed());
        dto.setAuthorId(comment.getAuthor() != null ? comment.getAuthor().getUserId() : null);
        dto.setMentionUsers(comment.getMentionUsers());
        dto.setAnonymous(comment.getAnonymous());
        dto.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null);
        dto.setLinkedEntityType(comment.getLinkedEntityType());
        dto.setLinkedEntityId(comment.getLinkedEntityId());
        dto.setStatus(comment.getStatus() != null ? comment.getStatus().name() : null); // Convert Enum to String
        dto.setLastReplyDate(comment.getLastReplyDate());
        dto.setNumberOfReplies(comment.getNumberOfReplies());
        dto.setVisibility(comment.getVisibility() != null ? comment.getVisibility().name() : null); // Convert Enum to String
        dto.setGroupId(comment.getGroup() != null ? comment.getGroup().getGroupId() : null);
        dto.setReactions(comment.getReactions());
        dto.setViewCount(comment.getViewCount());
        dto.setEdited(comment.getEdited());
        dto.setEditHistory(comment.getEditHistory());
        dto.setDateDeleted(comment.getDateDeleted());
        dto.setCategory(comment.getCategory() != null ? comment.getCategory().name() : null); // Convert Enum to String
        dto.setLanguage(comment.getLanguage());
        dto.setFlaggedCount(comment.getFlaggedCount());
        dto.setDateArchived(comment.getDateArchived());
        dto.setMetadata(comment.getMetadata());

        // Map attachments to their IDs
        dto.setAttachmentIds(comment.getAttachments() != null
                ? comment.getAttachments().stream().map(Attachment::getAttachmentId).collect(Collectors.toList())
                : null);

        return dto;
    }

    /**
     * Converts a `CommentDto` to a `Comment` entity.
     *
     * @param dto The `CommentDto`.
     * @return The corresponding `Comment` entity.
     */
    public Comment toEntity(CommentDto dto) {
        if (dto == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCommentId(dto.getCommentId());
        comment.setContent(dto.getContent());
        comment.setContentProcessed(dto.getContentProcessed());
        comment.setMentionUsers(dto.getMentionUsers());
        comment.setAnonymous(dto.getAnonymous());
        comment.setLinkedEntityType(dto.getLinkedEntityType());
        comment.setLinkedEntityId(dto.getLinkedEntityId());
        comment.setStatus(dto.getStatus() != null ? CommentStatus.valueOf(dto.getStatus()) : null); // Convert String to Enum
        comment.setLastReplyDate(dto.getLastReplyDate());
        comment.setNumberOfReplies(dto.getNumberOfReplies());
        comment.setVisibility(dto.getVisibility() != null ? VisibleTo.valueOf(dto.getVisibility()) : null); // Convert String to Enum
        comment.setReactions(dto.getReactions());
        comment.setViewCount(dto.getViewCount());
        comment.setEdited(dto.getEdited());
        comment.setEditHistory(dto.getEditHistory());
        comment.setDateDeleted(dto.getDateDeleted());
        comment.setCategory(dto.getCategory() != null ? CommentCategory.valueOf(dto.getCategory()) : null); // Convert String to Enum
        comment.setLanguage(dto.getLanguage());
        comment.setFlaggedCount(dto.getFlaggedCount());
        comment.setDateArchived(dto.getDateArchived());
        comment.setMetadata(dto.getMetadata());

        // Resolve and set the author
        if (dto.getAuthorId() != null) {
            User author = userRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getAuthorId()));
            comment.setAuthor(author);
        }

        // Resolve and set the parent comment
        if (dto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found with ID: " + dto.getParentCommentId()));
            comment.setParentComment(parentComment);
        }

        // Resolve and set the group
        if (dto.getGroupId() != null) {
            StudentGroup group = groupRepository.findById(dto.getGroupId())
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + dto.getGroupId()));
            comment.setGroup(group);
        }

        // Resolve and set attachments
        if (dto.getAttachmentIds() != null) {
            List<Attachment> attachments = attachmentRepository.findAllById(dto.getAttachmentIds());
            comment.setAttachments(new HashSet<>(attachments)); // Convert List to Set
        }

        return comment;
    }


}

