package learning_management_system.backend.service;


import learning_management_system.backend.dto.DiscussionAnalyticsDto;

public interface DiscussionAnalyticsService {

    DiscussionAnalyticsDto getAnalyticsByDiscussionId(Long discussionId);

    void incrementViewCount(Long discussionId);

    void incrementReplyCount(Long discussionId, Long userId);

    void updateUniqueParticipants(Long discussionId, Long userId);

    void recordActivity(Long discussionId, String details);
}
