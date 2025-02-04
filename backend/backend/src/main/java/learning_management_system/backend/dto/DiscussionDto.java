package learning_management_system.backend.dto;

import learning_management_system.backend.enums.DiscussionStatus;
import learning_management_system.backend.enums.VisibleTo;

import java.util.Date;
import java.util.List;

public class DiscussionDto {
    private Long discussionId;
    private String title;
    private String description;
    private Long courseId;
    private Long authorId;
    private Long parentDiscussionId;
    private String category;
    private DiscussionStatus status;
    private VisibleTo visibility;
    private String tags;
    private Boolean isPinned;
    private Boolean isLocked;
    private Boolean isAnonymousAllowed;
    private Boolean allowReplies;
    private Integer commentCount;
//    private List<AttachmentDto> attachments;
    private List<Long> attachmentIds;
    private List<CommentDto> comments;
    private List<Long> mentionedUserIds;
    private Date dateCreated;
    private Date dateUpdated;


    // Constructor
    public DiscussionDto(){}
    public DiscussionDto(Long discussionId, String title, String description, Long courseId, Long authorId, Long parentDiscussionId,
                         String category, DiscussionStatus status, VisibleTo visibility, String tags, Boolean isPinned,
                         Boolean isLocked, Boolean isAnonymousAllowed, Boolean allowReplies, Integer commentCount, List<Long> attachmentIds,
                         List<CommentDto> comments, List<Long> mentionedUserIds, Date dateCreated, Date dateUpdated) {
        this.discussionId = discussionId;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.authorId = authorId;
        this.parentDiscussionId = parentDiscussionId;
        this.category = category;
        this.status = status;
        this.visibility = visibility;
        this.tags = tags;
        this.isPinned = isPinned;
        this.isLocked = isLocked;
        this.isAnonymousAllowed = isAnonymousAllowed;
        this.allowReplies = allowReplies;
        this.commentCount = commentCount;
        this.attachmentIds = attachmentIds;
        this.comments = comments;
        this.mentionedUserIds = mentionedUserIds;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and setters...
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getParentDiscussionId() {
        return parentDiscussionId;
    }

    public void setParentDiscussionId(Long parentDiscussionId) {
        this.parentDiscussionId = parentDiscussionId;
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public List<Long> getMentionedUserIds() {
        return mentionedUserIds;
    }

    public void setMentionedUserIds(List<Long> mentionedUserIds) {
        this.mentionedUserIds = mentionedUserIds;
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
}

