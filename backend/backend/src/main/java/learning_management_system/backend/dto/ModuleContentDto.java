package learning_management_system.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * DTO for ModuleContent.
 */
public class ModuleContentDto {

    private Long contentId;
    private String contentTitle;
    private String contentDescription;
    private String contentType;
    private String fileUrl;
    private Integer orderContent;
    private Boolean isPublished;
    private String completionCriteria;
    private Double engagementScore;
    private Long moduleId;
    private Integer version;
    private Long previousVersionId;
    private String language;
    private Set<String> tags;
    private String analytics;
    private String accessType;
    private LocalDate availabilityStart;
    private List<Long> attachmentIds; // List of Attachment IDs associated with the Module content
//    private List<AttachmentDto> attachments; // Nested DTO for attachments
    private LocalDate availabilityEnd;
    private String interactivityLevel;
    private Long prerequisiteContentId;
    private LocalDateTime releaseDate;
    private Date createdAt;
    private Date updatedAt;
    private String metadata;



    // Constructors
    public ModuleContentDto() {}

    public ModuleContentDto(Long contentId, String contentTitle, String contentDescription, String contentType, String fileUrl, Integer orderContent, Boolean isPublished, String completionCriteria, Double engagementScore, Long moduleId, Integer version, Long previousVersionId, String language, Set<String> tags, String analytics, String accessType, LocalDate availabilityStart, List<Long> attachmentIds, LocalDate availabilityEnd, String interactivityLevel, Long prerequisiteContentId, LocalDateTime releaseDate, Date createdAt, Date updatedAt, String metadata) {
        this.contentId = contentId;
        this.contentTitle = contentTitle;
        this.contentDescription = contentDescription;
        this.contentType = contentType;
        this.fileUrl = fileUrl;
        this.orderContent = orderContent;
        this.isPublished = isPublished;
        this.completionCriteria = completionCriteria;
        this.engagementScore = engagementScore;
        this.moduleId = moduleId;
        this.version = version;
        this.previousVersionId = previousVersionId;
        this.language = language;
        this.tags = tags;
        this.analytics = analytics;
        this.accessType = accessType;
        this.availabilityStart = availabilityStart;
        this.attachmentIds = attachmentIds;
        this.availabilityEnd = availabilityEnd;
        this.interactivityLevel = interactivityLevel;
        this.prerequisiteContentId = prerequisiteContentId;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.metadata = metadata;
    }

    // Getters and Setters

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(Integer orderContent) {
        this.orderContent = orderContent;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public String getCompletionCriteria() {
        return completionCriteria;
    }

    public void setCompletionCriteria(String completionCriteria) {
        this.completionCriteria = completionCriteria;
    }

    public Double getEngagementScore() {
        return engagementScore;
    }

    public void setEngagementScore(Double engagementScore) {
        this.engagementScore = engagementScore;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getPreviousVersionId() {
        return previousVersionId;
    }

    public void setPreviousVersionId(Long previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getAnalytics() {
        return analytics;
    }

    public void setAnalytics(String analytics) {
        this.analytics = analytics;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public LocalDate getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(LocalDate availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public LocalDate getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(LocalDate availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public String getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(String interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public Long getPrerequisiteContentId() {
        return prerequisiteContentId;
    }

    public void setPrerequisiteContentId(Long prerequisiteContentId) {
        this.prerequisiteContentId = prerequisiteContentId;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}

