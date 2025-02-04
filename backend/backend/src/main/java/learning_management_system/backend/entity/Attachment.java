package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.AccessLevel;
import learning_management_system.backend.enums.AttachmentCategory;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.Set;

/**
 * Represents an attachment in the LMS. Attachments can be associated with various
 * entities such as content, announcements, quizzes, or submissions.
 */

//@Data
@CrossOrigin("*")
@Entity
@Table(name = "attachments")
public class Attachment {

    /** Unique identifier for the attachment. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long attachmentId;

    /** MIME type of the file (e.g., application/pdf, image/jpeg). */
    @Column(name = "file_type", nullable = false, length = 50)
    private String fileType;

    /** Original file name of the attachment. */
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    /** URL or path where the file is stored. */
    @Column(name = "storage_url", nullable = false, length = 255)
    private String storageUrl;

    /** Size of the file in bytes. */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /** Type of storage backend (e.g., S3, LOCAL). */
    @Column(name = "storage_type", nullable = false, length = 20)
    private String storageType;

    /** External resource ID for cloud storage integrations. */
    @Column(name = "external_resource_id", length = 255)
    private String externalResourceId;

    /** Type of the associated entity (e.g., ANNOUNCEMENT, QUIZ). */
    @Column(name = "linked_entity_type", nullable = false, length = 50)
    private String linkedEntityType;

    /** ID of the associated entity (e.g., announcementId, quizId). */
    @Column(name = "linked_entity_id", nullable = false)
    private Long linkedEntityId;

    /** Access level for the attachment (e.g., STUDENT, TEACHER, ADMIN). */
    @Enumerated(EnumType.STRING)
    @Column(name = "access_level", nullable = false, length = 20)
    private AccessLevel accessLevel;

    /** Visibility of the attachment (e.g., PUBLIC, ENROLLED_ONLY). */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private VisibleTo visibility;

    /** Category of the attachment (e.g., Lecture Material, Quiz Attachment). */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private AttachmentCategory category;

    /** Tags for categorizing attachments. */
    @ElementCollection
    @CollectionTable(name = "attachment_tags", joinColumns = @JoinColumn(name = "attachment_id"))
    @Column(name = "tag", length = 50)
    private Set<String> tags;

    /** Timestamp for when the attachment was uploaded. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date uploadDate = new Date();

    /** User who uploaded the attachment. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    /** Version of the attachment. */
    @Column(name = "version", nullable = false)
    private Integer version = 1;

    /** Reference to the previous version of the attachment. */
    @Column(name = "previous_version_id")
    private Long previousVersionId;

    /** Indicates if the file is stored in a compressed format. */
    @Column(name = "is_compressed", nullable = false)
    private Boolean isCompressed = false;

    /** Type of compression used (e.g., ZIP, GZIP). */
    @Column(name = "compression_type", length = 20)
    private String compressionType;

    /** Number of times the attachment has been downloaded. */
    @Column(name = "download_count")
    private Integer downloadCount = 0;

    /** Timestamp of the last access. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_accessed")
    private Date lastAccessed;

    /** URL of the thumbnail preview. */
    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = true)
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_content_id", nullable = true)
    private ModuleContent moduleContent;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    // Constructor
    public Attachment(){}

    public Attachment(Long attachmentId, String fileType, String fileName, String storageUrl, Long fileSize, String storageType,
                      String externalResourceId, String linkedEntityType, Long linkedEntityId, AccessLevel accessLevel, VisibleTo visibility,
                      AttachmentCategory category, Set<String> tags, Date uploadDate, User uploadedBy, Integer version,
                      Long previousVersionId, Boolean isCompressed, String compressionType, Integer downloadCount, Date lastAccessed,
                      String thumbnailUrl, String metadata, Assignment assignment, ModuleContent moduleContent) {
        this.attachmentId = attachmentId;
        this.fileType = fileType;
        this.fileName = fileName;
        this.storageUrl = storageUrl;
        this.fileSize = fileSize;
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
        this.assignment = assignment;
        this.moduleContent = moduleContent;
    }

    // Getters and Setters
    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public String getExternalResourceId() {
        return externalResourceId;
    }

    public void setExternalResourceId(String externalResourceId) {
        this.externalResourceId = externalResourceId;
    }

    public String getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(String linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public Long getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(Long linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
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

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
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

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public ModuleContent getModuleContent() {
        return moduleContent;
    }

    public void setModuleContent(ModuleContent moduleContent) {
        this.moduleContent = moduleContent;
    }
}

