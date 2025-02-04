package learning_management_system.backend.dto;

import java.util.Date;
import java.util.List;

public class FeedbackDto {
    private Long feedbackId;
    private Long linkedEntityId;
    private String linkedEntityType;
    private String feedbackText;
    private String feedbackType;
    private Long parentFeedbackId;
    private String visibility;
    private Boolean anonymous;
    private Boolean isPeerReview;
    private Long creatorId;
    private String creatorName;
    private Long recipientId;
    private String recipientName;
    private String rubricCriteria;
    private Double rating;
    private String metadata;
    private String status;
    private Date dateRead;
    private Date dateResponded;
    private Date dateCreated;
    private Date dateUpdated;
    private List<String> tags;


    // Constructors
    public FeedbackDto(){}
    public FeedbackDto(Long feedbackId, Long linkedEntityId, String linkedEntityType, String feedbackText, String feedbackType,
                       Long parentFeedbackId, String visibility, Boolean anonymous, Boolean isPeerReview, Long creatorId,
                       String creatorName, Long recipientId, String recipientName, String rubricCriteria, Double rating,
                       String metadata, String status, Date dateRead, Date dateResponded, Date dateCreated, Date dateUpdated,
                       List<String> tags) {
        this.feedbackId = feedbackId;
        this.linkedEntityId = linkedEntityId;
        this.linkedEntityType = linkedEntityType;
        this.feedbackText = feedbackText;
        this.feedbackType = feedbackType;
        this.parentFeedbackId = parentFeedbackId;
        this.visibility = visibility;
        this.anonymous = anonymous;
        this.isPeerReview = isPeerReview;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.rubricCriteria = rubricCriteria;
        this.rating = rating;
        this.metadata = metadata;
        this.status = status;
        this.dateRead = dateRead;
        this.dateResponded = dateResponded;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.tags = tags;
    }

    // Getters and Setters
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Long getParentFeedbackId() {
        return parentFeedbackId;
    }

    public void setParentFeedbackId(Long parentFeedbackId) {
        this.parentFeedbackId = parentFeedbackId;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Boolean getPeerReview() {
        return isPeerReview;
    }

    public void setPeerReview(Boolean peerReview) {
        isPeerReview = peerReview;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRubricCriteria() {
        return rubricCriteria;
    }

    public void setRubricCriteria(String rubricCriteria) {
        this.rubricCriteria = rubricCriteria;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateRead() {
        return dateRead;
    }

    public void setDateRead(Date dateRead) {
        this.dateRead = dateRead;
    }

    public Date getDateResponded() {
        return dateResponded;
    }

    public void setDateResponded(Date dateResponded) {
        this.dateResponded = dateResponded;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

