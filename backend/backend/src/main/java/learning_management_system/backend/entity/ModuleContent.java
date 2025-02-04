package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.ContentAccessType;
import learning_management_system.backend.enums.ContentType;
import learning_management_system.backend.enums.InteractivityLevel;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Represents a content item within a module. Content includes learning materials,
 * resources, or activities such as videos, documents, quizzes, or external links.
 */

@CrossOrigin("*")
@Entity
@Table(name = "module_contents")
public class ModuleContent {

    /** Unique identifier for the content item. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long contentId;

    /** Title of the content item. */
    @Column(name = "title", nullable = false, length = 255)
    private String contentTitle;

    /** Detailed description of the content item. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String contentDescription;

    /** Type of the content (e.g., VIDEO, PDF, QUIZ, LINK). */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private ContentType contentType;

    /** URL or file path pointing to the content resource. */
    @Column(name = "url", nullable = false, length = 255)
    private String fileUrl;

    /** Order of the content item within the module. */
    @Column(name = "order", nullable = false)
    private Integer orderContent;

    /** Indicates if the content is published and visible to students. */
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    /** Completion criteria for the content (e.g., viewed, quiz passed). */
    @Column(name = "completion_criteria", columnDefinition = "TEXT")
    private String completionCriteria;

    /** Engagement score based on student interactions. */
    @Column(name = "engagement_score")
    private Double engagementScore;

    /** The module this content belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private CourseModule module;

    /** Version of the content for tracking updates. */
    @Column(name = "version", nullable = false)
    private Integer version = 1;

    /** Reference to the previous version of the content, if applicable. */
    @Column(name = "previous_version_id")
    private Long previousVersionId;

    /** Language code for the content (e.g., "en", "es"). */
    @Column(name = "language", length = 10)
    private String language = "en";

    /** Tags for categorizing content. */
    @ElementCollection
    @CollectionTable(name = "content_tags", joinColumns = @JoinColumn(name = "content_id"))
    @Column(name = "tag", length = 50)
    private Set<String> tags;

    /** Advanced analytics stored as JSON. */
    @Column(name = "analytics", columnDefinition = "TEXT")
    private String analytics;

    /** Access control settings for the content. */
    @Enumerated(EnumType.STRING)
    @Column(name = "access_type", nullable = false, length = 20)
    private ContentAccessType accessType = ContentAccessType.ENROLLED_ONLY;

    /** Availability start date for the content. */
    @Column(name = "availability_start")
    private LocalDate availabilityStart;

    /** Availability end date for the content. */
    @Column(name = "availability_end")
    private LocalDate availabilityEnd;

    /** Level of interactivity for the content (e.g., QUIZ, DISCUSSION). */
    @Enumerated(EnumType.STRING)
    @Column(name = "interactivity_level", length = 20)
    private InteractivityLevel interactivityLevel = InteractivityLevel.NONE;

    /** Prerequisite content required before accessing this content. */
    @Column(name = "prerequisite_content_id")
    private Long prerequisiteContentId;

    /** Attachments linked to this content. */
//    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "module_content_id", referencedColumnName = "content_id")
//    private List<Attachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "moduleContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moduleContent", nullable = false)
    private CourseModule courseModule;

    /** Timestamp for when the content was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    /** Timestamp for when the content was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt = new Date();

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "release_date")
    private LocalDateTime releaseDate; // When the content becomes available


    public boolean isReleased(Set<Long> completedContentIds, LocalDateTime currentTime) {
        if (!isPublished) {
            return false;
        }
        if (availabilityStart != null && currentTime.isBefore(availabilityStart.atStartOfDay())) {
            return false;
        }
        if (availabilityEnd != null && currentTime.isAfter(availabilityEnd.atStartOfDay())) {
            return false;
        }
        if (prerequisiteContentId != null && !completedContentIds.contains(prerequisiteContentId)) {
            return false;
        }
        return true;
    }

    public void incrementVersion(Long previousVersionId) {
        this.previousVersionId = previousVersionId;
        this.version += 1;
        this.updatedAt = new Date();
    }

    public boolean isComplete(Set<Long> completedContentIds) {
        return completedContentIds.contains(this.contentId);
    }

    public boolean isAvailable(LocalDateTime currentTime) {
        return (availabilityStart == null || !currentTime.isBefore(availabilityStart.atStartOfDay())) &&
                (availabilityEnd == null || !currentTime.isAfter(availabilityEnd.atStartOfDay()));
    }

    // Constructors
    public ModuleContent(){}

    public ModuleContent(Long contentId, String contentTitle, String contentDescription, ContentType contentType, String fileUrl,
                         Integer orderContent, Boolean isPublished, String completionCriteria, Double engagementScore, CourseModule module,
                         Integer version, Long previousVersionId, String language, Set<String> tags, String analytics, ContentAccessType accessType,
                         LocalDate availabilityStart, LocalDate availabilityEnd, InteractivityLevel interactivityLevel, Long prerequisiteContentId,
                         List<Attachment> attachments, Date createdAt, Date updatedAt, String metadata, LocalDateTime releaseDate, CourseModule courseModule) {
        this.contentId = contentId;
        this.contentTitle = contentTitle;
        this.contentDescription = contentDescription;
        this.contentType = contentType;
        this.fileUrl = fileUrl;
        this.orderContent = orderContent;
        this.isPublished = isPublished;
        this.completionCriteria = completionCriteria;
        this.engagementScore = engagementScore;
        this.module = module;
        this.version = version;
        this.previousVersionId = previousVersionId;
        this.language = language;
        this.tags = tags;
        this.analytics = analytics;
        this.accessType = accessType;
        this.availabilityStart = availabilityStart;
        this.availabilityEnd = availabilityEnd;
        this.interactivityLevel = interactivityLevel;
        this.prerequisiteContentId = prerequisiteContentId;
        this.attachments = attachments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.metadata = metadata;
        this.releaseDate = releaseDate;
        this.courseModule = courseModule;
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

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
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

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
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

    public ContentAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(ContentAccessType accessType) {
        this.accessType = accessType;
    }

    public LocalDate getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(LocalDate availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public LocalDate getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(LocalDate availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public InteractivityLevel getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(InteractivityLevel interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public Long getPrerequisiteContentId() {
        return prerequisiteContentId;
    }

    public void setPrerequisiteContentId(Long prerequisiteContentId) {
        this.prerequisiteContentId = prerequisiteContentId;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }
}

