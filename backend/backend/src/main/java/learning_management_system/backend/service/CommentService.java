package learning_management_system.backend.service;

import learning_management_system.backend.dto.CommentDto;
import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.enums.LinkedEntityType;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto);

    CommentDto updateComment(Long commentId, CommentDto commentDto);

    void deleteComment(Long commentId);

    CommentDto getCommentById(Long commentId);

    List<CommentDto> getCommentsForEntity(String linkedEntityType, Long linkedEntityId);

    List<CommentDto> getRepliesForComment(Long parentCommentId);

    void archiveComment(Long commentId);

    void flagComment(Long commentId);

    CommentDto replyToComment(Long parentCommentId, CommentDto replyDto);

    List<CommentDto> getCommentsByEntity(String linkedEntityType, Long linkedEntityId);

    int countComments(LinkedEntityType linkedEntityType, Long linkedEntityId);

    Discussion getLinkedDiscussion(Long commentId);


}

