package learning_management_system.backend.entity;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a module within a course. A module groups content and activities
 * while supporting hierarchical relationships and gamification elements.
 */


@CrossOrigin("*")
@Entity
@Table(name = "course_modules")
public class CourseModule {

    /** Unique identifier for the module. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id", nullable = false, updatable = false)
    private Long moduleId;

    /** Title of the module. */
    @Column(name = "title", nullable = false, length = 255)
    private String moduleTitle;

    @Column(name = "module_description", columnDefinition = "TEXT")
    private String moduleDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /** Order of the module within the course's sequence. */
    @Column(name = "order", nullable = false)
    private Integer order;

    /** Indicates if the module is published and visible to students. */
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    /** Parent module, if this module is part of a nested structure. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_module_id")
    private CourseModule parentModule;

    /** Submodules linked to this module. */
    @OneToMany(mappedBy = "parentModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseModule> subModules = new HashSet<>();

    /** The course this module belongs to. */
    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ModuleContent> contents = new HashSet<>();

    /** Release date of the module. */
    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    /** Conditions required to release this module (e.g., prerequisites). */
    @Column(name = "release_condition", columnDefinition = "TEXT")
    private String releaseCondition;

    /** Learning objectives associated with the module. */
    @Column(name = "objectives", columnDefinition = "TEXT") // Updated field as String
    private String objectives;

    /** Points assigned to the module for progress tracking. */
    @Column(name = "points", nullable = true)
    private Integer points;

    /** URL of the badge awarded upon module completion. */
    @Column(name = "badge_url", nullable = true, length = 500)
    private String badgeUrl;

    /** Estimated time to complete the module. */
    @Column(name = "average_completion_time", nullable = true)
    private Double averageCompletionTime;

    /** Engagement score for the module based on learner interaction. */
    @Column(name = "engagement_score", nullable = true)
    private Double engagementScore;

    /** Timestamp for when the module was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    /** Timestamp for when the module was last updated. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    /** The user who created the module. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /** Metadata for extensibility and custom attributes. */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    /** List of prerequisite modules or conditions required to unlock this module. */
    @ManyToMany
    @JoinTable(
            name = "module_prerequisites",
            joinColumns = @JoinColumn(name = "module_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_module_id")
    )
    private Set<CourseModule> prerequisites = new HashSet<>();

    /** Indicates if the module requires prerequisites to be completed. */
    @Column(name = "requires_prerequisites", nullable = false)
    private Boolean requiresPrerequisites = false;


    @Column(name = "completion_criteria", columnDefinition = "TEXT")
    private String completionCriteria;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by")
    private User lastModifiedBy;



    // Additional Methods for Completion and Versioning
    public boolean arePrerequisitesMet(Set<Long> completedModuleIds) {
        return prerequisites.stream()
                .allMatch(prerequisite -> completedModuleIds.contains(prerequisite.getModuleId()));
    }

    public boolean isComplete(Set<Long> completedContentIds) {
        return contents.stream()
                .allMatch(content -> completedContentIds.contains(content.getContentId()));
    }

    // Check if the module is released based on prerequisites and custom conditions.
    public boolean isReleased(Set<Long> completedModuleIds, LocalDateTime currentTime) {
        if (requiresPrerequisites && !arePrerequisitesMet(completedModuleIds)) {
            return false;
        }
        if (releaseDate != null && currentTime.isBefore(releaseDate)) {
            return false;
        }
        if (releaseCondition != null && !evaluateReleaseCondition()) {
            return false;
        }
        return true;
    }

    private boolean evaluateReleaseCondition() {
        // Implement custom condition parsing and validation logic here.
        // For example, parse releaseCondition as JSON or a simple rule-based string.
        return true; // Placeholder for actual logic
    }


    // Constructor
    public CourseModule() {}

    public CourseModule(Long moduleId, String moduleTitle, String moduleDescription, Course course, Integer order, Boolean isPublished,
                        CourseModule parentModule, Set<CourseModule> subModules, Set<ModuleContent> contents, LocalDateTime releaseDate,
                        String releaseCondition, String objectives, Integer points, String badgeUrl, Double averageCompletionTime,
                        Double engagementScore, Date dateCreated, Date dateUpdated, User createdBy, String metadata,
                        Set<CourseModule> prerequisites, Boolean requiresPrerequisites, String completionCriteria,
                        Integer versionNumber, User lastModifiedBy) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleDescription = moduleDescription;
        this.course = course;
        this.order = order;
        this.isPublished = isPublished;
        this.parentModule = parentModule;
        this.subModules = subModules;
        this.contents = contents;
        this.releaseDate = releaseDate;
        this.releaseCondition = releaseCondition;
        this.objectives = objectives;
        this.points = points;
        this.badgeUrl = badgeUrl;
        this.averageCompletionTime = averageCompletionTime;
        this.engagementScore = engagementScore;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.createdBy = createdBy;
        this.metadata = metadata;
        this.prerequisites = prerequisites;
        this.requiresPrerequisites = requiresPrerequisites;
        this.completionCriteria = completionCriteria;
        this.versionNumber = versionNumber;
        this.lastModifiedBy = lastModifiedBy;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public CourseModule getParentModule() {
        return parentModule;
    }

    public void setParentModule(CourseModule parentModule) {
        this.parentModule = parentModule;
    }

    public Set<CourseModule> getSubModules() {
        return subModules;
    }

    public void setSubModules(Set<CourseModule> subModules) {
        this.subModules = subModules;
    }

    public Set<ModuleContent> getContents() {
        return contents;
    }

    public void setContents(Set<ModuleContent> contents) {
        this.contents = contents;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Set<CourseModule> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Set<CourseModule> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Boolean getRequiresPrerequisites() {
        return requiresPrerequisites;
    }

    public void setRequiresPrerequisites(Boolean requiresPrerequisites) {
        this.requiresPrerequisites = requiresPrerequisites;
    }

    public String getCompletionCriteria() {
        return completionCriteria;
    }

    public void setCompletionCriteria(String completionCriteria) {
        this.completionCriteria = completionCriteria;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}