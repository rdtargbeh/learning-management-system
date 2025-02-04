package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;

/**
 * Represents a badge in the LMS system, awarded for specific achievements or milestones.
 */

@CrossOrigin("*")
@Entity
@Table(name = "badges")
public class Badge {

    /** Unique identifier for the badge. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;

    /** Name of the badge. */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** Detailed description of the badge. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** URL to the badge icon/image. */
    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    /** JSON field defining the criteria for earning the badge. */
    @Column(name = "criteria", columnDefinition = "TEXT")
    private String criteria;

    /** Optional relationship to a tenant for tenant-specific badges. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    /** User or system that created the badge. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** Indicates if the badge is active. */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** JSON field for extensibility, such as tags or categories. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** Timestamp for when the badge was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for the last update to the badge. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();


    // Constructor
    public Badge(){}
    public Badge(Long badgeId, String name, String iconUrl, String description, String criteria, User createdBy, Tenant tenant, Boolean isActive, String metadata, Date dateCreated, Date dateUpdated) {
        this.badgeId = badgeId;
        this.name = name;
        this.iconUrl = iconUrl;
        this.description = description;
        this.criteria = criteria;
        this.createdBy = createdBy;
        this.tenant = tenant;
        this.isActive = isActive;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and setters omitted for brevity.
    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

