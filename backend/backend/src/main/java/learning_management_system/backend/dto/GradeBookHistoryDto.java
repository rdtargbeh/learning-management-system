package learning_management_system.backend.dto;

import java.time.LocalDateTime;

public class GradeBookHistoryDto {

    private Long historyId;
    private Long recordId;
    private Double previousScore;
    private Double newScore;
    private Long changedByUserId;
    private String changeReason;
    private String metadata;
    private LocalDateTime dateChanged;


    // Constructor

    public GradeBookHistoryDto() {}

    public GradeBookHistoryDto(Long historyId, Long recordId, Double previousScore, Double newScore, Long changedByUserId, String changeReason, String metadata, LocalDateTime dateChanged) {
        this.historyId = historyId;
        this.recordId = recordId;
        this.previousScore = previousScore;
        this.newScore = newScore;
        this.changedByUserId = changedByUserId;
        this.changeReason = changeReason;
        this.metadata = metadata;
        this.dateChanged = dateChanged;
    }

    // Getters and setters
    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Double getPreviousScore() {
        return previousScore;
    }

    public void setPreviousScore(Double previousScore) {
        this.previousScore = previousScore;
    }

    public Double getNewScore() {
        return newScore;
    }

    public void setNewScore(Double newScore) {
        this.newScore = newScore;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(Long changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(LocalDateTime dateChanged) {
        this.dateChanged = dateChanged;
    }
}

