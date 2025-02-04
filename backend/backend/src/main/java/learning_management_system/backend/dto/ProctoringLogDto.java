package learning_management_system.backend.dto;

import java.util.Date;

public class ProctoringLogDto {

    private String status;
    private String violations;
    private Date startTime;
    private Date endTime;
    private Boolean success;

    // Constructor
    public ProctoringLogDto(String status, String violations, Date startTime, Date endTime, Boolean success) {
        this.status = status;
        this.violations = violations;
        this.startTime = startTime;
        this.endTime = endTime;
        this.success = success;
    }

    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(String violations) {
        this.violations = violations;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
