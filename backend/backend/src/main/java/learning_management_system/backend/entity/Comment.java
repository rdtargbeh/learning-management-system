package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.CommentCategory;
import learning_management_system.backend.enums.CommentStatus;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@Entity
@Table(name = "comments")
public class Comment {

    /** Unique identifier for the comment. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    /** Raw text or media content of the comment. */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    /** Pre-rendered content for optimized display. */
    @Column(name = "content_processed", columnDefinition = "TEXT")
    private String contentProcessed;

    /** User who created the comment. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = true) // Nullable if `isAnonymous` is true.
    private User author;

    /** Indicates if the comment is posted anonymously. */
    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = false;

    /** ID of the parent comment for threaded replies. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    /** Type of the associated entity (e.g., Announcement, Discussion). */
    @Enumerated(EnumType.STRING)
    @Column(name = "linked_entity_type", nullable = false, length = 50)
    private LinkedEntityType linkedEntityType;

    /** ID of the associated entity (e.g., announcementId, discussionId). */
    @Column(name = "linked_entity_id", nullable = false)
    private Long linkedEntityId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "discussion_id", nullable = false)
//    private Discussion discussion;

    /** Moderation status of the comment. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CommentStatus status = CommentStatus.APPROVED;

    /** Timestamp of the most recent reply to the comment. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_reply_date")
    private Date lastReplyDate;

    /** Total number of replies to the comment. */
    @Column(name = "number_of_replies", nullable = false)
    private Integer numberOfReplies = 0;

    /** JSON field storing mentioned users in the comment. */
    @Column(name = "mention_users", columnDefinition = "TEXT")
    private String mentionUsers;

    /** Access control for the comment. */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private VisibleTo visibility;

    /** Group restriction for the comment (if applicable). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private StudentGroup group;

    /** Count of reactions or likes on the comment. */
    @Column(name = "reactions", nullable = false)
    private Integer reactions = 0;

    /** Number of views for the comment. */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    /** Indicates if the comment has been edited. */
    @Column(name = "is_edited", nullable = false)
    private Boolean isEdited = false;

    /** JSON field storing the history of edits. */
    @Column(name = "edit_history", columnDefinition = "TEXT")
    private String editHistory;

    /** Timestamp for when the comment was deleted. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_deleted")
    private Date dateDeleted;

    /** Category of the comment (e.g., QUESTION, FEEDBACK). */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20)
    private CommentCategory category;

    /** Language of the comment (e.g., en, es). */
    @Column(name = "language", length = 10)
    private String language = "en";

    /** Number of times the comment has been flagged. */
    @Column(name = "flagged_count", nullable = false)
    private Integer flaggedCount = 0;

    /** Timestamp for when the comment was archived. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_archived")
    private Date dateArchived;

    /** Timestamp for when the comment was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the comment was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();


    /** List of attachments associated with the comment. */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;



    // Constructor
    public Comment(){}
    public Comment(Long commentId, String content, String contentProcessed, User author, Boolean isAnonymous, Comment parentComment,
                   LinkedEntityType linkedEntityType, Long linkedEntityId, CommentStatus status, Date lastReplyDate, Integer numberOfReplies,
                   String mentionUsers, VisibleTo visibility, StudentGroup group, Integer reactions, Integer viewCount, Boolean isEdited,
                   String editHistory, Date dateDeleted, CommentCategory category, String language, Integer flaggedCount, Date dateArchived,
                   Date dateCreated, Date dateUpdated, Set<Attachment> attachments, String metadata, Announcement announcement) {
        this.commentId = commentId;
        this.content = content;
        this.contentProcessed = contentProcessed;
        this.author = author;
        this.isAnonymous = isAnonymous;
        this.parentComment = parentComment;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.status = status;
        this.lastReplyDate = lastReplyDate;
        this.numberOfReplies = numberOfReplies;
        this.mentionUsers = mentionUsers;
        this.visibility = visibility;
        this.group = group;
        this.reactions = reactions;
        this.viewCount = viewCount;
        this.isEdited = isEdited;
        this.editHistory = editHistory;
        this.dateDeleted = dateDeleted;
        this.category = category;
        this.language = language;
        this.flaggedCount = flaggedCount;
        this.dateArchived = dateArchived;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.attachments = attachments;
        this.metadata = metadata;
        this.announcement = announcement;
    }

    // Getters and setters are omitted for brevity.
    public Integer getReactions() {
        return reactions;
    }

    public void setReactions(Integer reactions) {
        this.reactions = reactions;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentProcessed() {
        return contentProcessed;
    }

    public void setContentProcessed(String contentProcessed) {
        this.contentProcessed = contentProcessed;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public LinkedEntityType getLinkedEntityType() {
        return linkedEntityType;
    }

    public void setLinkedEntityType(LinkedEntityType linkedEntityType) {
        this.linkedEntityType = linkedEntityType;
    }

    public Long getLinkedEntityId() {
        return linkedEntityId;
    }

    public void setLinkedEntityId(Long linkedEntityId) {
        this.linkedEntityId = linkedEntityId;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }

    public Date getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(Date lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public Integer getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(Integer numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public String getMentionUsers() {
        return mentionUsers;
    }

    public void setMentionUsers(String mentionUsers) {
        this.mentionUsers = mentionUsers;
    }

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
        this.visibility = visibility;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Boolean getEdited() {
        return isEdited;
    }

    public void setEdited(Boolean edited) {
        isEdited = edited;
    }

    public String getEditHistory() {
        return editHistory;
    }

    public void setEditHistory(String editHistory) {
        this.editHistory = editHistory;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public CommentCategory getCategory() {
        return category;
    }

    public void setCategory(CommentCategory category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getFlaggedCount() {
        return flaggedCount;
    }

    public void setFlaggedCount(Integer flaggedCount) {
        this.flaggedCount = flaggedCount;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public void setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
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

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
