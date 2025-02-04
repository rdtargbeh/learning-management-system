package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.PriorityUrgency;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Represents an announcement in the LMS system.
 * Used for sharing important information with students, staff, or other groups.
 */

@CrossOrigin("*")
@Entity
@Table(name = "announcements")
public class Announcement {

    /** Unique identifier for the announcement. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long announcementId;

    /** Title of the announcement. */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /** Text or multimedia content of the announcement. */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /** Rich text content for pre-rendered display. */
    @Column(name = "rich_text_content", columnDefinition = "TEXT")
    private String richTextContent;

    /** Classifies announcements (e.g., General, Assignment, Exam). */
    @Column(name = "category", nullable = true, length = 50)
    private String category;

    /** JSON field for specifying roles that can view the announcement (e.g., STUDENT, STAFF). */
    @Column(name = "target_roles", columnDefinition = "TEXT")
    private String targetRoles;

    /** JSON field for linking the announcement to specific entities (e.g., Course, Exam). */
    @Column(name = "target_entity_type", length = 50)
    private String targetEntityType; // e.g., "COURSE", "GROUP"

    @Column(name = "target_entity_id")
    private Long targetEntityId;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "reply_count")
    private int replyCount;

    /** Determines who can see the announcement. */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private VisibleTo visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    /** Priority level of the announcement. */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 20)
    private PriorityUrgency priority;

    /** List of attachments associated with the announcement. */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    /** URL or call-to-action link associated with the announcement. */
    @Column(name = "action_link", length = 255)
    private String actionLink;

    /** Indicates whether the announcement is pinned to the top of the feed. */
    @Column(name = "is_pinned", nullable = false)
    private Boolean isPinned = false;

    /** Marks the announcement as a draft (not yet published). */
    @Column(name = "is_draft", nullable = false)
    private Boolean isDraft = false;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived;

    /** Defines the recurrence of the announcement (e.g., daily, weekly). */
    @Column(name = "recurrence_pattern", length = 50)
    private String recurrencePattern;

    /** Tags or labels for categorizing the announcement. */
    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    /** Supports comments or replies related to the announcement. */
    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    /** Timestamp for when the announcement is scheduled to be published. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    /** Links to the user who created the announcement. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "announcement_views",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> recipients;


    /** Timestamp for when the announcement was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the announcement was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** Tracks the last engagement timestamp. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_engaged_at")
    private LocalDateTime lastEngagedAt;

    /** Supports localized content. */
    @Column(name = "content_language", length = 50)
    private String contentLanguage;

    // Add this field if it's missing
    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;

    // Add expiryDate if it's also missing
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;


    // Constructor
    public Announcement(){}
    public Announcement(Long announcementId, String title, String content, String richTextContent, String category, String targetRoles,
                        String targetEntityType, Long targetEntityId, int viewCount, int replyCount, VisibleTo visibility,
                        PriorityUrgency priority, List<Attachment> attachments, String actionLink, Boolean isPinned, Boolean isDraft,
                        boolean isArchived, String recurrencePattern, String tags, List<Comment> comments, LocalDateTime scheduledAt,
                        User createdBy, Set<User> recipients, Date dateCreated, Date dateUpdated, LocalDateTime lastEngagedAt,
                        String contentLanguage, Course course, LocalDateTime publishDate, LocalDateTime expiryDate) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.richTextContent = richTextContent;
        this.category = category;
        this.targetRoles = targetRoles;
        this.targetEntityType = targetEntityType;
        this.targetEntityId = targetEntityId;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.visibility = visibility;
        this.priority = priority;
        this.attachments = attachments;
        this.actionLink = actionLink;
        this.isPinned = isPinned;
        this.isDraft = isDraft;
        this.isArchived = isArchived;
        this.recurrencePattern = recurrencePattern;
        this.tags = tags;
        this.comments = comments;
        this.scheduledAt = scheduledAt;
        this.createdBy = createdBy;
        this.recipients = recipients;
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

    public String getTargetEntityType() {
        return targetEntityType;
    }

    public void setTargetEntityType(String targetEntityType) {
        this.targetEntityType = targetEntityType;
    }

    public Long getTargetEntityId() {
        return targetEntityId;
    }

    public void setTargetEntityId(Long targetEntityId) {
        this.targetEntityId = targetEntityId;
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

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
        this.visibility = visibility;
    }

    public PriorityUrgency getPriority() {
        return priority;
    }

    public void setPriority(PriorityUrgency priority) {
        this.priority = priority;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<User> recipients) {
        this.recipients = recipients;
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
