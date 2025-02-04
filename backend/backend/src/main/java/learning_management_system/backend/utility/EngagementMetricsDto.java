package learning_management_system.backend.utility;

import java.time.LocalDateTime;

public class EngagementMetricsDto {

    private int viewCount;
    private int replyCount;
    private LocalDateTime lastEngagedAt;

    // Constructors
    public EngagementMetricsDto() {
    }

    public EngagementMetricsDto(int viewCount, int replyCount, LocalDateTime lastEngagedAt) {
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.lastEngagedAt = lastEngagedAt;
    }

    // Getters and Setters
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

    public LocalDateTime getLastEngagedAt() {
        return lastEngagedAt;
    }

    public void setLastEngagedAt(LocalDateTime lastEngagedAt) {
        this.lastEngagedAt = lastEngagedAt;
    }
}
