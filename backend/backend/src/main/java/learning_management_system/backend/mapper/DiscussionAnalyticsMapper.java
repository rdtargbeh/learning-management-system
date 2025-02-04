package learning_management_system.backend.mapper;

import learning_management_system.backend.dto.DiscussionAnalyticsDto;
import learning_management_system.backend.entity.Discussion;
import learning_management_system.backend.entity.DiscussionAnalytics;
import learning_management_system.backend.repository.DiscussionRepository;
import learning_management_system.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring")

@Component
public class DiscussionAnalyticsMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiscussionRepository discussionRepository;

    /**
     * Converts a DiscussionAnalytics entity to a DiscussionAnalyticsDto.
     */
    public DiscussionAnalyticsDto toDto(DiscussionAnalytics analytics) {
        DiscussionAnalyticsDto dto = new DiscussionAnalyticsDto();
        dto.setAnalyticsId(analytics.getAnalyticsId());
        dto.setDiscussionId(analytics.getDiscussion() != null ? analytics.getDiscussion().getDiscussionId() : null);
        dto.setViewCount(analytics.getViewCount());
        dto.setReplyCount(analytics.getReplyCount());
        dto.setUniqueParticipants(analytics.getUniqueParticipants());
        dto.setMostActiveParticipantId(analytics.getMostActiveParticipantId()); // Directly mapped
        dto.setLastActivity(analytics.getLastActivity());
        dto.setEngagementDetails(analytics.getEngagementDetails());
        dto.setSentimentScore(analytics.getSentimentScore());
        return dto;
    }

    /**
     * Converts a DiscussionAnalyticsDto to a DiscussionAnalytics entity.
     */
    public DiscussionAnalytics toEntity(DiscussionAnalyticsDto dto) {
        DiscussionAnalytics analytics = new DiscussionAnalytics();

        // Map discussion reference
        if (dto.getDiscussionId() != null) {
            Discussion discussion = discussionRepository.findById(dto.getDiscussionId())
                    .orElseThrow(() -> new IllegalArgumentException("Discussion not found with ID: " + dto.getDiscussionId()));
            analytics.setDiscussion(discussion);
        }

        // Directly map scalar fields
        analytics.setViewCount(dto.getViewCount());
        analytics.setReplyCount(dto.getReplyCount());
        analytics.setUniqueParticipants(dto.getUniqueParticipants()); // Directly mapped
        analytics.setMostActiveParticipantId(dto.getMostActiveParticipantId()); // Directly mapped
        analytics.setLastActivity(dto.getLastActivity());
        analytics.setEngagementDetails(dto.getEngagementDetails());
        analytics.setSentimentScore(dto.getSentimentScore());

        return analytics;
    }


}

