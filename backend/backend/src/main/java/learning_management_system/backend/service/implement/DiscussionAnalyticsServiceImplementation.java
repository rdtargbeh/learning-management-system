package learning_management_system.backend.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import learning_management_system.backend.dto.DiscussionAnalyticsDto;
import learning_management_system.backend.entity.DiscussionAnalytics;
import learning_management_system.backend.repository.DiscussionAnalyticsRepository;
import learning_management_system.backend.repository.DiscussionRepository;
import learning_management_system.backend.service.DiscussionAnalyticsService;
import learning_management_system.backend.utility.EngagementDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiscussionAnalyticsServiceImplementation implements DiscussionAnalyticsService {

    @Autowired
    private DiscussionAnalyticsRepository analyticsRepository;
    private DiscussionRepository discussionRepository;


    @Override
    public DiscussionAnalyticsDto getAnalyticsByDiscussionId(Long discussionId) {
        DiscussionAnalytics analytics = analyticsRepository.findByDiscussion_DiscussionId(discussionId)
                .orElseThrow(() -> new RuntimeException("Analytics not found for discussion ID: " + discussionId));

        // Map engagement details to a format suitable for the DTO
        List<String> engagementDetailsAsStrings = analytics.getEngagementDetails()
                .stream()
                .map(EngagementDetail::toString) // Replace with proper mapping logic
                .toList();

        return new DiscussionAnalyticsDto(
                analytics.getAnalyticsId(),
                analytics.getDiscussion().getDiscussionId(),
                analytics.getViewCount(),
                analytics.getReplyCount(),
                analytics.getUniqueParticipants(),
                analytics.getAverageResponseTime(), // Retrieve average response time
                analytics.getMostActiveParticipantId(), // Retrieve most active participant ID
                analytics.getLastActivity(),
                analytics.getEngagementDetails(), // Pass engagement details
                analytics.getSentimentScore() // Retrieve sentiment score
        );
    }


    @Override
    @Transactional
    public void incrementViewCount(Long discussionId) {
        DiscussionAnalytics analytics = analyticsRepository.findByDiscussion_DiscussionId(discussionId)
                .orElseThrow(() -> new RuntimeException("Analytics not found for discussion ID: " + discussionId));
        analytics.incrementViewCount(); // Custom method in entity
        analytics.updateLastActivity(LocalDateTime.now()); // Custom method in entity
        analyticsRepository.save(analytics);
    }

    @Override
    @Transactional
    public void incrementReplyCount(Long discussionId, Long userId) {
        DiscussionAnalytics analytics = analyticsRepository.findByDiscussion_DiscussionId(discussionId)
                .orElseThrow(() -> new RuntimeException("Analytics not found for discussion ID: " + discussionId));

        // Increment reply count
        analytics.setReplyCount(analytics.getReplyCount() + 1);
        analytics.setLastActivity(LocalDateTime.now());

        // Update unique participants if the user is not already tracked
        if (analytics.getEngagementDetails().stream().noneMatch(detail -> detail.getUserId().equals(userId))) {
            analytics.setUniqueParticipants(analytics.getUniqueParticipants() + 1);
        }

        // Add a new engagement detail
        analytics.getEngagementDetails().add(new EngagementDetail(userId, "REPLY", LocalDateTime.now()));

        // Save the updated analytics
        analyticsRepository.save(analytics);
    }

    @Override
    @Transactional
    public void updateUniqueParticipants(Long discussionId, Long userId) {
        // Fetch the analytics for the discussion
        DiscussionAnalytics analytics = analyticsRepository.findByDiscussion_DiscussionId(discussionId)
                .orElseThrow(() -> new RuntimeException("Analytics not found for discussion ID: " + discussionId));

        // Use the List<EngagementDetail> directly
        List<EngagementDetail> engagementDetails = analytics.getEngagementDetails();

        // Check if the user is already a participant
        boolean isNewParticipant = engagementDetails.stream()
                .noneMatch(detail -> detail.getUserId().equals(userId));

        if (isNewParticipant) {
            // Add the new participant
            engagementDetails.add(new EngagementDetail(userId, "UNIQUE_PARTICIPATION", LocalDateTime.now()));

            // Update the analytics entity
            analytics.setUniqueParticipants(engagementDetails.size());
            analytics.setLastActivity(LocalDateTime.now());
            analyticsRepository.save(analytics);
        }
    }


    @Override
    @Transactional
    public void recordActivity(Long discussionId, String details) {
        DiscussionAnalytics analytics = analyticsRepository.findByDiscussion_DiscussionId(discussionId)
                .orElseThrow(() -> new RuntimeException("Analytics not found for discussion ID: " + discussionId));

        // Deserialize the String to a List<EngagementDetail>
        List<EngagementDetail> engagementDetails;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            engagementDetails = objectMapper.readValue(details, new TypeReference<List<EngagementDetail>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse engagement details", e);
        }

        // Set the engagement details and update the last activity
        analytics.setEngagementDetails(engagementDetails);
        analytics.setLastActivity(LocalDateTime.now());
        analyticsRepository.save(analytics);
    }


    private Set<Long> parseUniqueParticipants(String engagementDetails) {
        if (engagementDetails == null || engagementDetails.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(engagementDetails.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    private String serializeUniqueParticipants(Set<Long> participants) {
        return participants.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

// Add helper methods to handle the serialization and deserialization:
private String serializeEngagementDetails(List<EngagementDetail> engagementDetails) {
    try {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(engagementDetails);
    } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to serialize engagement details", e);
    }
}

    private List<EngagementDetail> deserializeEngagementDetails(String engagementDetails) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(engagementDetails, new TypeReference<List<EngagementDetail>>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>(); // Return an empty list if deserialization fails
        }
    }


}

