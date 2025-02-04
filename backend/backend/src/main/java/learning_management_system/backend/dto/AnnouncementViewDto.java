package learning_management_system.backend.dto;

import java.time.LocalDateTime;

public class AnnouncementViewDto {
    private Long announcementId;
    private Long userId;
    private LocalDateTime viewedAt;

    public AnnouncementViewDto(Long announcementId, Long userId, LocalDateTime viewedAt) {
        this.announcementId = announcementId;
        this.userId = userId;
        this.viewedAt = viewedAt;
    }

    // Getters and Setters

    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}
