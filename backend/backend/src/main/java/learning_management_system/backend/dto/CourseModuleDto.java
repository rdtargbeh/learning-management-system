package learning_management_system.backend.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for CourseModule.
 */
public class CourseModuleDto {

    private Long moduleId;
    private String moduleTitle;
    private String moduleDescription;
    private Long courseId; // Reference to the parent course
    private Integer order;
    private Boolean isPublished;
    private Long parentModuleId; // For hierarchical structures
    private Set<Long> prerequisiteModuleIds; // IDs of prerequisite modules
    private LocalDateTime releaseDate;
    private String releaseCondition;
    private String objectives;
    private Integer points;
    private String badgeUrl;
    private Double averageCompletionTime;
    private Double engagementScore;
    private Long createdByUserId;
    private Set<Long> subModuleIds;
    private String metadata;
    private String createdBy; // Minimal instructor details (e.g., name or ID)
    private Date dateCreated;
    private Date dateUpdated;

//    private List<SubModuleDTO> subModules; // Minimal details about submodules
//    private boolean hasPrerequisites; // Flag indicating if prerequisites exist


    // Constructors
    public CourseModuleDto(){}

    public CourseModuleDto(Long moduleId, String moduleTitle, String moduleDescription, Long courseId, Integer order, Boolean isPublished,
                           Long parentModuleId, Set<Long> prerequisiteModuleIds, LocalDateTime releaseDate, String releaseCondition,
                           String objectives, Integer points, String badgeUrl, Double averageCompletionTime, Double engagementScore,
                           Long createdByUserId, Set<Long> subModuleIds, String metadata, String createdBy, Date dateCreated, Date dateUpdated) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleDescription = moduleDescription;
        this.courseId = courseId;
        this.order = order;
        this.isPublished = isPublished;
        this.parentModuleId = parentModuleId;
        this.prerequisiteModuleIds = prerequisiteModuleIds;
        this.releaseDate = releaseDate;
        this.releaseCondition = releaseCondition;
        this.objectives = objectives;
        this.points = points;
        this.badgeUrl = badgeUrl;
        this.averageCompletionTime = averageCompletionTime;
        this.engagementScore = engagementScore;
        this.createdByUserId = createdByUserId;
        this.subModuleIds = subModuleIds;
        this.metadata = metadata;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    // Getters and Setters
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Long getParentModuleId() {
        return parentModuleId;
    }

    public void setParentModuleId(Long parentModuleId) {
        this.parentModuleId = parentModuleId;
    }

    public Set<Long> getPrerequisiteModuleIds() {
        return prerequisiteModuleIds;
    }

    public void setPrerequisiteModuleIds(Set<Long> prerequisiteModuleIds) {
        this.prerequisiteModuleIds = prerequisiteModuleIds;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseCondition() {
        return releaseCondition;
    }

    public void setReleaseCondition(String releaseCondition) {
        this.releaseCondition = releaseCondition;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        this.badgeUrl = badgeUrl;
    }

    public Double getAverageCompletionTime() {
        return averageCompletionTime;
    }

    public void setAverageCompletionTime(Double averageCompletionTime) {
        this.averageCompletionTime = averageCompletionTime;
    }

    public Double getEngagementScore() {
        return engagementScore;
    }

    public void setEngagementScore(Double engagementScore) {
        this.engagementScore = engagementScore;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Set<Long> getSubModuleIds() {
        return subModuleIds;
    }

    public void setSubModuleIds(Set<Long> subModuleIds) {
        this.subModuleIds = subModuleIds;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
