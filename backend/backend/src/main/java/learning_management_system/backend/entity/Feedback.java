package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.FeedbackStatus;
import learning_management_system.backend.enums.FeedbackType;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;



@CrossOrigin("*")
@Entity
@Table(name = "feedbacks")
public class Feedback {

    /** Unique identifier for the feedback. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    /** ID of the entity the feedback is associated with. */
    @Column(name = "linked_entity_id", nullable = false)
    private Long linkedEntityId;

    /** Type of the entity (e.g., Assignment, Exam). */
    @Column(name = "linked_entity_type", nullable = false, length = 50)
    private String linkedEntityType;

    /** The main text of the feedback. */
    @Column(name = "feedback_text", columnDefinition = "TEXT")
    private String feedbackText;

    /** Type of feedback (e.g., Text, Rubric). */
    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_type", nullable = false)
    private FeedbackType feedbackType;

    /** Links to the parent feedback for nested or threaded responses. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_feedback_id")
    private Feedback parentFeedback;

    /** Defines who can see the feedback. */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private VisibleTo visibility;

    /** Indicates if the feedback is anonymous. */
    @Column(name = "anonymous", nullable = false)
    private Boolean anonymous = false;

    /** Indicates if the feedback is part of a peer review process. */
    @Column(name = "is_peer_review", nullable = false)
    private Boolean isPeerReview = false;

    /** User who created the feedback. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    /** User or group receiving the feedback. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    /** JSON field for rubric-based feedback. */
    @Column(name = "rubric_criteria", columnDefinition = "TEXT")
    private String rubricCriteria;

    /** Associated attachments for detailed feedback. */
    @OneToMany(mappedBy = "linkedEntityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments;

    /** Optional numeric rating or score. */
    @Column(name = "rating")
    private Double rating;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the recipient read the feedback. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_read")
    private Date dateRead;

    /** Timestamp for when the recipient responded to the feedback. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_responded")
    private Date dateResponded;

    /** Timestamp for when the feedback was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the feedback was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** Feedback tags for categorization (e.g., "Positive", "Needs Improvement"). */
    @ElementCollection
    @CollectionTable(name = "feedback_tags", joinColumns = @JoinColumn(name = "feedback_id"))
    @Column(name = "tag", length = 50)
    private List<String> tags;

    /** Feedback status (e.g., Draft, Published, Resolved). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FeedbackStatus status = FeedbackStatus.DRAFT;

    // Constructor
    public Feedback(){}

    public Feedback(Long feedbackId, Long linkedEntityId, String linkedEntityType, String feedbackText, FeedbackType feedbackType, Feedback parentFeedback, VisibleTo visibility, Boolean anonymous, Boolean isPeerReview, User creator, User recipient, String rubricCriteria, List<Attachment> attachments, Double rating, String metadata, Date dateRead, Date dateResponded, Date dateCreated, Date dateUpdated, List<String> tags, FeedbackStatus status) {
        this.feedbackId = feedbackId;
        this.linkedEntityId = linkedEntityId;
        this.linkedEntityType = linkedEntityType;
        this.feedbackText = feedbackText;
        this.feedbackType = feedbackType;
        this.parentFeedback = parentFeedback;
        this.visibility = visibility;
        this.anonymous = anonymous;
        this.isPeerReview = isPeerReview;
        this.creator = creator;
        this.recipient = recipient;
        this.rubricCriteria = rubricCriteria;
        this.attachments = attachments;
        this.rating = rating;
        this.metadata = metadata;
        this.dateRead = dateRead;
        this.dateResponded = dateResponded;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.tags = tags;
        this.status = status;
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

    public FeedbackType getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Feedback getParentFeedback() {
        return parentFeedback;
    }

    public void setParentFeedback(Feedback parentFeedback) {
        this.parentFeedback = parentFeedback;
    }

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getRubricCriteria() {
        return rubricCriteria;
    }

    public void setRubricCriteria(String rubricCriteria) {
        this.rubricCriteria = rubricCriteria;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public FeedbackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}

