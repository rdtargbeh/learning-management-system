package learning_management_system.backend.dto;

import learning_management_system.backend.entity.Course;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for Announcement entity.
 * Used to transfer announcement data between layers.
 */

public class AnnouncementDto {

    /** Unique identifier for the announcement. */
    private Long announcementId;

    /** Title of the announcement. */
    private String title;

    /** Text or multimedia content of the announcement. */
    private String content;

    /** Rich text content for pre-rendered display. */
    private String richTextContent;

    /** Classifies announcements (e.g., General, Assignment, Exam). */
    private String category;

    /** Roles that can view the announcement (e.g., STUDENT, STAFF). */
    private String targetRoles; // JSON string of roles

    /** Linked entities (e.g., Course, Exam). */
    private Long targetEntityId;

    /** Users who have viewed the announcement. */
    private List<Long> recipientIds;

    private String targetEntityType; // "COURSE", "GROUP"

    /** Determines who can see the announcement. */
    private String visibility;

    /** Priority level of the announcement. */
    private String priority;

    /** URL or call-to-action link associated with the announcement. */
    private String actionLink;

    /** Indicates whether the announcement is pinned to the top of the feed. */
    private Boolean isPinned;

    /** Marks the announcement as a draft (not yet published). */
    private Boolean isDraft;

    /** Defines the recurrence of the announcement (e.g., daily, weekly). */
    private String recurrencePattern;

    /** Tags or labels for categorizing the announcement. */
    private String tags;

    private int viewCount;

    private int replyCount;

    private boolean isArchived;

    /** List of comment IDs associated with the announcement. */
    private List<Long> commentIds;

    private Course course;

    /** Timestamp for when the announcement is scheduled to be published. */
    private LocalDateTime scheduledAt;

    /** ID of the user who created the announcement. */
    private Long createdBy;

    /** Timestamp for when the announcement was created. */
    private Date dateCreated;

    /** Timestamp for when the announcement was last updated. */
    private Date dateUpdated;

    private LocalDateTime lastEngagedAt;
    private String contentLanguage;
    private LocalDateTime expiryDate;
    private LocalDateTime publishDate;





    // Constructors
    public AnnouncementDto(){}

    public AnnouncementDto(Long announcementId, String title, String content, String richTextContent, String category,
                           String targetRoles, Long targetEntityId, List<Long> recipientIds, String targetEntityType,
                           String visibility, String priority, String actionLink, Boolean isPinned, Boolean isDraft,
                           String recurrencePattern, String tags, int viewCount, int replyCount, boolean isArchived,
                           List<Long> commentIds, LocalDateTime scheduledAt, Long createdBy, Date dateCreated,
                           Date dateUpdated, LocalDateTime lastEngagedAt, String contentLanguage, Course course, LocalDateTime publishDate, LocalDateTime expiryDate) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.richTextContent = richTextContent;
        this.category = category;
        this.targetRoles = targetRoles;
        this.targetEntityId = targetEntityId;
        this.recipientIds = recipientIds;
        this.targetEntityType = targetEntityType;
        this.visibility = visibility;
        this.priority = priority;
        this.actionLink = actionLink;
        this.isPinned = isPinned;
        this.isDraft = isDraft;
        this.recurrencePattern = recurrencePattern;
        this.tags = tags;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.isArchived = isArchived;
        this.commentIds = commentIds;
        this.scheduledAt = scheduledAt;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.lastEngagedAt = lastEngagedAt;
        this.contentLanguage = contentLanguage;
        this.course = course;
        this.publishDate = publishDate;
        this.expiryDate = expiryDate;
    }

// Getters and Setters


    public Long getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(Long announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRichTextContent() {
        return richTextContent;
    }

    public void setRichTextContent(String richTextContent) {
        this.richTextContent = richTextContent;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(String targetRoles) {
        this.targetRoles = targetRoles;
    }

    public Long getTargetEntityId() {
        return targetEntityId;
    }

    public void setTargetEntityId(Long targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    public List<Long> getRecipientIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<Long> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public String getTargetEntityType() {
        return targetEntityType;
    }

    public void setTargetEntityType(String targetEntityType) {
        this.targetEntityType = targetEntityType;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getActionLink() {
        return actionLink;
    }

    public void setActionLink(String actionLink) {
        this.actionLink = actionLink;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

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

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDateTime getLastEngagedAt() {
        return lastEngagedAt;
    }

    public void setLastEngagedAt(LocalDateTime lastEngagedAt) {
        this.lastEngagedAt = lastEngagedAt;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
