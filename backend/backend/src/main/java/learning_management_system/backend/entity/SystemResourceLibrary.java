package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.ResourceType;
import learning_management_system.backend.enums.VisibleTo;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

/**
 * Represents a resource in the LMS resource library.
 */
@CrossOrigin("*")
@Entity
@Table(name = "resources")
public class SystemResourceLibrary {

    /** Unique identifier for the resource. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", nullable = false, updatable = false)
    private Long resourceId;

    /** Title of the resource. */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /** Detailed description of the resource. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Type of resource (e.g., eBook, Video, Document). */
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false, length = 50)
    private ResourceType resourceType;

    /** URL or path to access the resource. */
    @Column(name = "url", nullable = false, length = 255)
    private String url;

    /** Tags for categorization. */
    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    /** Visibility of the resource (PUBLIC, PRIVATE, TENANT_ONLY). */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false, length = 20)
    private VisibleTo visibility;

    /** The owner of the resource. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /** Metadata for extensibility. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the resource was created. */
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /** Timestamp for when the resource was last updated. */
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated = LocalDateTime.now();

    public SystemResourceLibrary() {
    }

    public SystemResourceLibrary(Long resourceId, String title, String description, ResourceType resourceType, String url, String tags, VisibleTo visibility, User owner, String metadata, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.resourceId = resourceId;
        this.title = title;
        this.description = description;
        this.resourceType = resourceType;
        this.url = url;
        this.tags = tags;
        this.visibility = visibility;
        this.owner = owner;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public VisibleTo getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibleTo visibility) {
        this.visibility = visibility;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}

