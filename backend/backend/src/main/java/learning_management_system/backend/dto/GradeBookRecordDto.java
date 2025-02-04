package learning_management_system.backend.dto;

import learning_management_system.backend.entity.User;

import java.time.LocalDateTime;

public class GradeBookRecordDto {
    private Long recordId;
    private Long gradeBookItemId;
    private Long studentId;
    private String studentName; // Optional for display purposes
    private Double score;
    private String feedback;
    private Boolean isFinalized;
    private Boolean isVerified;
    private User verifiedBy;
    private Boolean isLocked;
    private String metadata;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    // Constructor

    public GradeBookRecordDto() {}

    public GradeBookRecordDto(Long recordId, Long gradeBookItemId, Long studentId, String studentName, Double score, String feedback, Boolean isFinalized, Boolean isVerified, User verifiedBy, Boolean isLocked, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.recordId = recordId;
        this.gradeBookItemId = gradeBookItemId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.score = score;
        this.feedback = feedback;
        this.isFinalized = isFinalized;
        this.isVerified = isVerified;
        this.verifiedBy = verifiedBy;
        this.isLocked = isLocked;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

// Getter and Setter

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getGradeBookItemId() {
        return gradeBookItemId;
    }

    public void setGradeBookItemId(Long gradeBookItemId) {
        this.gradeBookItemId = gradeBookItemId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
