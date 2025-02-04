package learning_management_system.backend.dto;

import java.util.Date;

public class BadgeDto {

    private Long badgeId;
    private String name;
    private String description;
    private String iconUrl;
    private String criteria;
    private Long tenantId;
    private Long createdBy;
    private Boolean isActive;
    private String metadata;
    private Date dateCreated;
    private Date dateUpdated;


    // Constructors
    public BadgeDto(){}
    public BadgeDto(Long badgeId, String name, String description, String iconUrl, String criteria, Long tenantId, Long createdBy,
                    Boolean isActive, String metadata, Date dateCreated, Date dateUpdated) {
        this.badgeId = badgeId;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.criteria = criteria;
        this.tenantId = tenantId;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.metadata = metadata;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
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

