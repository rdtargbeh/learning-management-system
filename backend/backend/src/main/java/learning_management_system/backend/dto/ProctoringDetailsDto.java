package learning_management_system.backend.dto;

import java.util.Date;
import java.util.List;

public class ProctoringDetailsDto {

    private String sessionStatus;
    private Date sessionStartTime;
    private Date sessionEndTime;
    private String metadata;
    private List<ProctoringLogDto> logs;

    // Constructor
    public ProctoringDetailsDto(String sessionStatus, Date sessionStartTime, Date sessionEndTime, String metadata, List<ProctoringLogDto> logs) {
        this.sessionStatus = sessionStatus;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.metadata = metadata;
        this.logs = logs;
    }

    // Getters and Setters

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public Date getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public Date getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(Date sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public List<ProctoringLogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<ProctoringLogDto> logs) {
        this.logs = logs;
    }
}
