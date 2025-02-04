package learning_management_system.backend.dto;

import learning_management_system.backend.enums.AccessLevel;
import learning_management_system.backend.enums.AttachmentCategory;
import learning_management_system.backend.enums.VisibleTo;

import java.util.Date;
import java.util.Set;

public class AttachmentDto {

    private Long attachmentId;
    private String fileType;
    private String fileName;
    private String storageUrl;
    private Long fileSize;
    private String storageType;
    private String externalResourceId;
    private String linkedEntityType;
    private Long linkedEntityId;
    private AccessLevel accessLevel;
    private VisibleTo visibility;
    private AttachmentCategory category;
    private Set<String> tags;
    private Date uploadDate;
    private Long uploadedBy;
    private Integer version;
    private Long previousVersionId;
    private Boolean isCompressed;
    private String compressionType;
    private Integer downloadCount;
    private Date lastAccessed;
    private String thumbnailUrl;
    private String metadata;
    private Long assignmentId;  // Not user
    private Long contentId;   // not use



    // Constructors
    public AttachmentDto(){}

    public AttachmentDto(Long attachmentId, String fileName, String fileType, Long fileSize, String storageUrl, String storageType,
                         String externalResourceId, String linkedEntityType, Long linkedEntityId, AccessLevel accessLevel,
                         VisibleTo visibility, AttachmentCategory category, Set<String> tags, Date uploadDate, Long uploadedBy,
                         Integer version, Long previousVersionId, Boolean isCompressed, String compressionType, Integer downloadCount,
                         Date lastAccessed, String thumbnailUrl, String metadata, Long assignmentId, Long contentId) {
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.storageUrl = storageUrl;
        this.storageType = storageType;
        this.externalResourceId = externalResourceId;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.accessLevel = accessLevel;
        this.visibility = visibility;
        this.category = category;
        this.tags = tags;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.version = version;
        this.previousVersionId = previousVersionId;
        this.isCompressed = isCompressed;
        this.compressionType = compressionType;
        this.downloadCount = downloadCount;
        this.lastAccessed = lastAccessed;
        this.thumbnailUrl = thumbnailUrl;
        this.metadata = metadata;
        this.assignmentId = assignmentId;
        this.contentId = contentId;
    }


    // Getters and Setters
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public Long getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(Long linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
    }

    public String getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(String linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public String getExternalResourceId() {
        return externalResourceId;
    }

    public void setExternalResourceId(String externalResourceId) {
        this.externalResourceId = externalResourceId;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
        this.visibility = visibility;
    }

    public AttachmentCategory getCategory() {
        return category;
    }

    public void setCategory(AttachmentCategory category) {
        this.category = category;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Long getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Long uploadedBy) {
        this.uploadedBy = uploadedBy;
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

    public Boolean getCompressed() {
        return isCompressed;
    }

    public void setCompressed(Boolean compressed) {
        isCompressed = compressed;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Date getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Long getAssignment() {
        return assignmentId;
    }

    public void setAssignment(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getModuleContent() {
        return contentId;
    }

    public void setModuleContent(Long contentId) {
        this.contentId = contentId;
    }
}

