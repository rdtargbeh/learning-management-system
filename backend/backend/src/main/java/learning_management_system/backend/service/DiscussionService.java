package learning_management_system.backend.service;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.dto.DiscussionDto;
import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiscussionService {

    DiscussionDto createDiscussion(DiscussionDto discussionDto);

    DiscussionDto updateDiscussion(Long discussionId, DiscussionDto dto);

    List<DiscussionDto> getDiscussionsByCourseId(Long courseId);

    CommentDto addCommentToDiscussion(Long discussionId, CommentDto commentDto);

    List<CommentDto> getCommentsForDiscussion(Long discussionId);

    List<DiscussionDto> getDiscussionsByAuthor(Long authorId);

    void notifyMentionedUsers(Long discussionId, List<Long> mentionedUserIds);

    CommentDto replyToDiscussionComment(Long parentCommentId, CommentDto replyDto);

    void togglePinDiscussion(Long discussionId);

    List<DiscussionDto> getDiscussionsByVisibility(VisibleTo visibility);

    List<DiscussionDto> getPinnedDiscussions(Long courseId);

    void toggleLockDiscussion(Long discussionId);

    void updateDiscussionStatus(Long discussionId, DiscussionStatus status);

    List<DiscussionDto> getDiscussionsByStatus(Long courseId, DiscussionStatus status);

    void deleteComment(Long commentId);

    Page<DiscussionDto> getPublicDiscussionsByCourse(Long courseId, int page, int size);

//    Page<DiscussionDto> getPublicDiscussionsByCourse(Long courseId, Pageable pageable);

//    DiscussionDto replyToDiscussion(Long parentDiscussionId, DiscussionDto replyDto);

}
