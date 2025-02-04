package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.utility.EngagementDetail;
import learning_management_system.backend.utility.EngagementDetailsConverter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@Entity
@Table(name = "discussion_analytics")
public class DiscussionAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Long analyticsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "reply_count", nullable = false)
    private int replyCount = 0;

    @Column(name = "unique_participants", nullable = false)
    private int uniqueParticipants = 0;

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @Column(name = "average_response_time", nullable = true)
    private Double averageResponseTime; // Time in minutes

    @Column(name = "most_active_participant", nullable = true)
    private Long mostActiveParticipantId;

    @Column(name = "sentiment_score", nullable = true)
    private Double sentimentScore; // -1.0 (negative) to 1.0 (positive)

//    @Column(name = "engagement_details", columnDefinition = "TEXT")
//    private String engagementDetails;

    @Column(name = "engagement_details", columnDefinition = "TEXT")
    @Convert(converter = EngagementDetailsConverter.class)
    private List<EngagementDetail> engagementDetails;

    // No `dateCreated` or `dateUpdated` since these are covered in Discussion.


    // Utility methods for updating metrics
    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementReplyCount() {
        this.replyCount++;
    }

    public void updateLastActivity(LocalDateTime activityTime) {
        this.lastActivity = activityTime;
    }



    // Constructor
    public DiscussionAnalytics(){}

    public DiscussionAnalytics(Long analyticsId, Discussion discussion, int viewCount, int replyCount, int uniqueParticipants, LocalDateTime lastActivity, Double averageResponseTime, Long mostActiveParticipantId, Double sentimentScore, List<EngagementDetail> engagementDetails) {
        this.analyticsId = analyticsId;
        this.discussion = discussion;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.uniqueParticipants = uniqueParticipants;
        this.lastActivity = lastActivity;
        this.averageResponseTime = averageResponseTime;
        this.mostActiveParticipantId = mostActiveParticipantId;
        this.sentimentScore = sentimentScore;
        this.engagementDetails = engagementDetails;
    }

    // Getter and Setter
    public Long getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(Long analyticsId) {
        this.analyticsId = analyticsId;
    }

    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
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

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
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

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public List<EngagementDetail> getEngagementDetails() {
        return engagementDetails;
    }

    public void setEngagementDetails(List<EngagementDetail> engagementDetails) {
        this.engagementDetails = engagementDetails;
    }
}
