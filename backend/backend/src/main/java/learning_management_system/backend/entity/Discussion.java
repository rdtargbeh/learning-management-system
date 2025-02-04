package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.LinkedEntityType;
import learning_management_system.backend.enums.VisibleTo;
import learning_management_system.backend.repository.CommentRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin("*")
@Entity
@Table(name = "discussions")
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private Long discussionId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_discussion_id")
    private Discussion parentDiscussion;

    @Column(name = "category", length = 50)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private DiscussionStatus status = DiscussionStatus.OPEN;

    @OneToOne(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private DiscussionAnalytics analytics;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private VisibleTo visibility;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    @Column(name = "is_pinned", nullable = false)
    private Boolean isPinned = false;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked = false;

    @Column(name = "is_anonymous_allowed", nullable = false)
    private Boolean isAnonymousAllowed = false;

    @Column(name = "allow_replies", nullable = false)
    private Boolean allowReplies = true;

    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0;

    @Column(name = "moderators", columnDefinition = "TEXT")
    private String moderators;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup group;

    @Column(name = "settings", columnDefinition = "TEXT")
    private String settings;

    @ManyToMany
    @JoinTable(
            name = "discussion_mentioned_users",
            joinColumns = @JoinColumn(name = "discussion_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> mentionedUsers = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();


//    /** One-to-Many relationship with the Notification entity for group notifications. */
//    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<LmsNotification> notifications = new ArrayList<>();

//    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;


    // Helper method to fetch comments for this discussion
    public List<Comment> getComments(CommentRepository commentRepository) {
        return commentRepository.findByLinkedEntityTypeAndLinkedEntityId(
                LinkedEntityType.DISCUSSION,
                this.discussionId
        );
    }


    // Constructor
    public Discussion() {}

    public Discussion(Long discussionId, String title, String description, Course course, User author, Discussion parentDiscussion,
                      String category, DiscussionStatus status, DiscussionAnalytics analytics, VisibleTo visibility, String tags,
                      Boolean isPinned, Boolean isLocked, Boolean isAnonymousAllowed, Boolean allowReplies, List<Attachment> attachments,
                      List<LmsNotification> notifications, List<Comment> comments, Integer commentCount, String moderators,
                      String settings, Date dateCreated, Date dateUpdated, Set<User> mentionedUsers, StudentGroup group) {
        this.discussionId = discussionId;
        this.title = title;
        this.description = description;
        this.course = course;
        this.author = author;
        this.parentDiscussion = parentDiscussion;
        this.category = category;
        this.status = status;
        this.analytics = analytics;
        this.visibility = visibility;
        this.tags = tags;
        this.isPinned = isPinned;
        this.isLocked = isLocked;
        this.isAnonymousAllowed = isAnonymousAllowed;
        this.allowReplies = allowReplies;
        this.attachments = attachments;
//        this.notifications = notifications;
//        this.comments = comments;
        this.commentCount = commentCount;
        this.moderators = moderators;
        this.settings = settings;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.mentionedUsers = mentionedUsers;
        this.group = group;
    }

    // Getters and setters omitted for brevity.
    public Long getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(Long discussionId) {
        this.discussionId = discussionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Discussion getParentDiscussion() {
        return parentDiscussion;
    }

    public void setParentDiscussion(Discussion parentDiscussion) {
        this.parentDiscussion = parentDiscussion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DiscussionStatus getStatus() {
        return status;
    }

    public void setStatus(DiscussionStatus status) {
        this.status = status;
    }

    public DiscussionAnalytics getAnalytics() {
        return analytics;
    }

    public void setAnalytics(DiscussionAnalytics analytics) {
        this.analytics = analytics;
    }

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
        this.visibility = visibility;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getAnonymousAllowed() {
        return isAnonymousAllowed;
    }

    public void setAnonymousAllowed(Boolean anonymousAllowed) {
        isAnonymousAllowed = anonymousAllowed;
    }

    public Boolean getAllowReplies() {
        return allowReplies;
    }

    public void setAllowReplies(Boolean allowReplies) {
        this.allowReplies = allowReplies;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getModerators() {
        return moderators;
    }

    public void setModerators(String moderators) {
        this.moderators = moderators;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
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

    public Set<User> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(Set<User> mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    //    public List<LmsNotification> getNotifications() {
//        return notifications;
//    }
//
//    public void setNotifications(List<LmsNotification> notifications) {
//        this.notifications = notifications;
//    }

//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }

}
