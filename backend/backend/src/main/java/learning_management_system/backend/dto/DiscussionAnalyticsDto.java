package learning_management_system.backend.dto;

import learning_management_system.backend.utility.EngagementDetail;

import java.time.LocalDateTime;
import java.util.List;


public class DiscussionAnalyticsDto {

    private Long analyticsId;
    private Long discussionId; // References the discussion
    private int viewCount;
    private int replyCount;
    private Double averageResponseTime; // Optional for future use
    private int uniqueParticipants;
    private Long mostActiveParticipantId; // Optional for future use
    private LocalDateTime lastActivity;
    private List<EngagementDetail> engagementDetails; // User engagement details
    private Double sentimentScore; // Sentiment score

    // Constructors
    public DiscussionAnalyticsDto(){}
    public DiscussionAnalyticsDto(Long analyticsId, Long discussionId, int viewCount, int replyCount, int uniqueParticipants,
                                  Double averageResponseTime, Long mostActiveParticipantId, LocalDateTime lastActivity,
                                  List<EngagementDetail> engagementDetails, Double sentimentScore) {
        this.analyticsId = analyticsId;
        this.discussionId = discussionId;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.uniqueParticipants = uniqueParticipants;
        this.averageResponseTime = averageResponseTime;
        this.mostActiveParticipantId = mostActiveParticipantId;
        this.lastActivity = lastActivity;
        this.engagementDetails = engagementDetails;
        this.sentimentScore = sentimentScore;

    }

    // Getters and Setters
    public Long getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(Long analyticsId) {
        this.analyticsId = analyticsId;
    }

    public Long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Long discussionId) {
        this.discussionId = discussionId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getUniqueParticipants() {
        return uniqueParticipants;
    }

    public void setUniqueParticipants(int uniqueParticipants) {
        this.uniqueParticipants = uniqueParticipants;
    }

    public Double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(Double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public Long getMostActiveParticipantId() {
        return mostActiveParticipantId;
    }

    public void setMostActiveParticipantId(Long mostActiveParticipantId) {
        this.mostActiveParticipantId = mostActiveParticipantId;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }

    public List<EngagementDetail> getEngagementDetails() {
        return engagementDetails;
    }

    public void setEngagementDetails(List<EngagementDetail> engagementDetails) {
        this.engagementDetails = engagementDetails;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }
}
