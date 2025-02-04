package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Comment;
import learning_management_system.backend.enums.CommentStatus;
import learning_management_system.backend.enums.LinkedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByLinkedEntityTypeAndLinkedEntityId(String linkedEntityType, Long linkedEntityId);

    List<Comment> findByParentComment(Comment parentComment);

    List<Comment> findByGroup_GroupId(Long groupId);

    @Query("SELECT c FROM Comment c WHERE c.status = :status")
    List<Comment> findByStatus(@Param("status") CommentStatus status);

    int countByLinkedEntityTypeAndLinkedEntityId(LinkedEntityType linkedEntityType, Long linkedEntityId);

    List<Comment> findByLinkedEntityTypeAndLinkedEntityId(LinkedEntityType linkedEntityType, Long linkedEntityId);
}

