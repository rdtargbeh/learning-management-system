package learning_management_system.backend.repository;

import learning_management_system.backend.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByRecipient_UserId(Long recipientId);

    List<Feedback> findByLinkedEntityTypeAndLinkedEntityId(String relatedEntityType, Long relatedEntityId);

    List<Feedback> findByCreator_UserId(Long userId);

    List<Feedback> findByVisibility(String visibility);

    List<Feedback> findByLinkedEntityIdAndLinkedEntityType(Long linkedEntityId, String linkedEntityType);

    @Query("SELECT f FROM Feedback f WHERE f.parentFeedback.feedbackId = :parentFeedbackId")
    List<Feedback> findByParentFeedbackId(@Param("parentFeedbackId") Long parentFeedbackId);

    @Query("SELECT f FROM Feedback f WHERE f.creator.userId = :creatorId AND f.status = :status")
    List<Feedback> findByCreatorAndStatus(@Param("creatorId") Long creatorId, @Param("status") String status);

    @Query("SELECT f FROM Feedback f WHERE f.visibility = 'PEER' AND f.isPeerReview = true")
    List<Feedback> findAllPeerReviews();

}

