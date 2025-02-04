package learning_management_system.backend.entity;

import jakarta.persistence.*;
import learning_management_system.backend.enums.QuestionBankType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@Entity
@Table(name = "question_bank")
public class QuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_bank_id")
    private Long questionBankId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags; // Supports single or hierarchical tags.

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private QuestionBankType type; // BANK, POOL, CUSTOM.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_bank_id")
    private QuestionBank parentBank; // Supports hierarchical organization of question banks.

    @Column(name = "version", nullable = false)
    private Integer version = 1; // Tracks version history.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "shared_with", columnDefinition = "TEXT")
    private String sharedWith; // Comma-separated list of users/groups.

    @OneToMany(mappedBy = "questionBank", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @Column(name = "linked_assessments", columnDefinition = "TEXT")
    private String linkedAssessments; // Stores IDs of linked assessments.

    @Column(name = "statistics", columnDefinition = "TEXT")
    private String statistics; // Aggregated performance data for questions in the bank.

    @Column(name = "access_logs", columnDefinition = "TEXT")
    private String accessLogs; // Logs for who accessed the bank and when.

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date dateCreated = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date dateUpdated = new Date();

    @Column(name = "randomization_rules", columnDefinition = "TEXT")
    private String randomizationRules; // JSON-based rules for randomizing questions.

    @Column(name = "usage_statistics", columnDefinition = "TEXT")
    private String usageStatistics; // Tracks how frequently questions are used and in what contexts.

    @Column(name = "hierarchical_tags", columnDefinition = "TEXT")
    private String hierarchicalTags; // JSON or comma-separated string for organizing tags into hierarchies.

    @Column(name = "bulk_operations_log", columnDefinition = "TEXT")
    private String bulkOperationsLog; // Logs details of bulk actions (e.g., adding/updating/removing questions).

    @Column(name = "default_difficulty_distribution", columnDefinition = "TEXT")
    private String defaultDifficultyDistribution; // JSON structure to ensure question difficulty balance in pools.

    @Column(name = "adaptive_questioning_enabled", nullable = false)
    private Boolean adaptiveQuestioningEnabled = false; // Flag for enabling adaptive questioning logic.

    public QuestionBank() {
    }

    public QuestionBank(Long questionBankId, String name, String description, String tags, QuestionBankType type, QuestionBank parentBank, Integer version, User createdBy, String sharedWith, List<Question> questions, String linkedAssessments, String statistics, String accessLogs, Date dateCreated, Date dateUpdated, String randomizationRules, String usageStatistics, String hierarchicalTags, String bulkOperationsLog, String defaultDifficultyDistribution, Boolean adaptiveQuestioningEnabled) {
        this.questionBankId = questionBankId;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.type = type;
        this.parentBank = parentBank;
        this.version = version;
        this.createdBy = createdBy;
        this.sharedWith = sharedWith;
        this.questions = questions;
        this.linkedAssessments = linkedAssessments;
        this.statistics = statistics;
        this.accessLogs = accessLogs;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.randomizationRules = randomizationRules;
        this.usageStatistics = usageStatistics;
        this.hierarchicalTags = hierarchicalTags;
        this.bulkOperationsLog = bulkOperationsLog;
        this.defaultDifficultyDistribution = defaultDifficultyDistribution;
        this.adaptiveQuestioningEnabled = adaptiveQuestioningEnabled;
    }

    // Getters and setters omitted for brevity.

    public Long getQuestionBankId() {
        return questionBankId;
    }

    public void setQuestionBankId(Long questionBankId) {
        this.questionBankId = questionBankId;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public QuestionBankType getType() {
        return type;
    }

    public void setType(QuestionBankType type) {
        this.type = type;
    }

    public QuestionBank getParentBank() {
        return parentBank;
    }

    public void setParentBank(QuestionBank parentBank) {
        this.parentBank = parentBank;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(String sharedWith) {
        this.sharedWith = sharedWith;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getLinkedAssessments() {
        return linkedAssessments;
    }

    public void setLinkedAssessments(String linkedAssessments) {
        this.linkedAssessments = linkedAssessments;
    }

    public String getStatistics() {
        return statistics;
    }

    public void setStatistics(String statistics) {
        this.statistics = statistics;
    }

    public String getAccessLogs() {
        return accessLogs;
    }

    public void setAccessLogs(String accessLogs) {
        this.accessLogs = accessLogs;
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

    public String getRandomizationRules() {
        return randomizationRules;
    }

    public void setRandomizationRules(String randomizationRules) {
        this.randomizationRules = randomizationRules;
    }

    public String getUsageStatistics() {
        return usageStatistics;
    }

    public void setUsageStatistics(String usageStatistics) {
        this.usageStatistics = usageStatistics;
    }

    public String getHierarchicalTags() {
        return hierarchicalTags;
    }

    public void setHierarchicalTags(String hierarchicalTags) {
        this.hierarchicalTags = hierarchicalTags;
    }

    public String getBulkOperationsLog() {
        return bulkOperationsLog;
    }

    public void setBulkOperationsLog(String bulkOperationsLog) {
        this.bulkOperationsLog = bulkOperationsLog;
    }

    public String getDefaultDifficultyDistribution() {
        return defaultDifficultyDistribution;
    }

    public void setDefaultDifficultyDistribution(String defaultDifficultyDistribution) {
        this.defaultDifficultyDistribution = defaultDifficultyDistribution;
    }

    public Boolean getAdaptiveQuestioningEnabled() {
        return adaptiveQuestioningEnabled;
    }

    public void setAdaptiveQuestioningEnabled(Boolean adaptiveQuestioningEnabled) {
        this.adaptiveQuestioningEnabled = adaptiveQuestioningEnabled;
    }
}
