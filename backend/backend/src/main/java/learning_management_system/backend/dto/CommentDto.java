package learning_management_system.backend.dto;

import learning_management_system.backend.enums.LinkedEntityType;

import java.util.Date;
import java.util.List;

public class CommentDto {

    private Long commentId;
    private String content;
    private String contentProcessed;
    private Long authorId;
    private List<Long> attachmentIds; // Added for handling attachments
    private String mentionUsers; // Added for storing mentioned users
    private Boolean isAnonymous;
    private Long parentCommentId;
    private LinkedEntityType linkedEntityType;
    private Long linkedEntityId;
    private String status;
    private Date lastReplyDate;
    private Integer numberOfReplies;
    private String visibility;
    private Long groupId;
    private Integer reactions;
    private Integer viewCount;
    private Boolean isEdited;
    private String editHistory;
    private Date dateDeleted;
    private String category;
    private String language;
    private Integer flaggedCount;
    private Date dateArchived;
    private String metadata;

    // Constructor

    public CommentDto() {}

    public CommentDto(Long commentId, String content, String contentProcessed, Long authorId, List<Long> attachmentIds, String mentionUsers,
                      Boolean isAnonymous, Long parentCommentId, LinkedEntityType linkedEntityType, Long linkedEntityId, String status,
                      Date lastReplyDate, Integer numberOfReplies, String visibility, Long groupId, Integer reactions, Integer viewCount,
                      Boolean isEdited, String editHistory, Date dateDeleted, String category, String language, Integer flaggedCount,
                      Date dateArchived, String metadata) {
        this.commentId = commentId;
        this.content = content;
        this.contentProcessed = contentProcessed;
        this.authorId = authorId;
        this.attachmentIds = attachmentIds;
        this.mentionUsers = mentionUsers;
        this.isAnonymous = isAnonymous;
        this.parentCommentId = parentCommentId;
        this.linkedEntityType = linkedEntityType;
        this.linkedEntityId = linkedEntityId;
        this.status = status;
        this.lastReplyDate = lastReplyDate;
        this.numberOfReplies = numberOfReplies;
        this.visibility = visibility;
        this.groupId = groupId;
        this.reactions = reactions;
        this.viewCount = viewCount;
        this.isEdited = isEdited;
        this.editHistory = editHistory;
        this.dateDeleted = dateDeleted;
        this.category = category;
        this.language = language;
        this.flaggedCount = flaggedCount;
        this.dateArchived = dateArchived;
        this.metadata = metadata;
    }

    // Getter and Setter

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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<Long> getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getMentionUsers() {
        return mentionUsers;
    }

    public void setMentionUsers(String mentionUsers) {
        this.mentionUsers = mentionUsers;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getReactions() {
        return reactions;
    }

    public void setReactions(Integer reactions) {
        this.reactions = reactions;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
