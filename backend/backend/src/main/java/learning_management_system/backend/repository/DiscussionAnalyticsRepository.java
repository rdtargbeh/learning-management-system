package learning_management_system.backend.repository;

import learning_management_system.backend.entity.DiscussionAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscussionAnalyticsRepository extends JpaRepository<DiscussionAnalytics, Long> {

    Optional<DiscussionAnalytics> findByDiscussion_DiscussionId(Long discussionId);

    // Custom query to find analytics by discussion ID
//    DiscussionAnalytics findByDiscussion_DiscussionId(Long discussionId);
}

