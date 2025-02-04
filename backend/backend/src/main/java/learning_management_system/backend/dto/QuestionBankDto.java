package learning_management_system.backend.dto;

import learning_management_system.backend.enums.QuestionBankType;

import java.util.List;

public class QuestionBankDto {
    private Long questionBankId;
    private String name;
    private String description;
    private String tags;
    private QuestionBankType type;
    private Long parentBankId;
    private Integer version;
    private Long createdById;
    private String sharedWith;
    private List<Long> questionIds; // List of question IDs in this bank.
    private String linkedAssessments;
    private String statistics;
    private String accessLogs;
    private String randomizationRules;
    private String usageStatistics;
    private String hierarchicalTags;
    private String bulkOperationsLog;
    private String defaultDifficultyDistribution;
    private Boolean adaptiveQuestioningEnabled;


    // Constructor
    public QuestionBankDto(){}
    public QuestionBankDto(Long questionBankId, String name, String description, String tags, QuestionBankType type, Long parentBankId, Integer version, Long createdById, String sharedWith, List<Long> questionIds, String linkedAssessments, String statistics, String accessLogs, String randomizationRules, String usageStatistics, String hierarchicalTags, String bulkOperationsLog, String defaultDifficultyDistribution, Boolean adaptiveQuestioningEnabled) {
        this.questionBankId = questionBankId;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.type = type;
        this.parentBankId = parentBankId;
        this.version = version;
        this.createdById = createdById;
        this.sharedWith = sharedWith;
        this.questionIds = questionIds;
        this.linkedAssessments = linkedAssessments;
        this.statistics = statistics;
        this.accessLogs = accessLogs;
        this.randomizationRules = randomizationRules;
        this.usageStatistics = usageStatistics;
        this.hierarchicalTags = hierarchicalTags;
        this.bulkOperationsLog = bulkOperationsLog;
        this.defaultDifficultyDistribution = defaultDifficultyDistribution;
        this.adaptiveQuestioningEnabled = adaptiveQuestioningEnabled;
    }

    // Getters and setters

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

    public Long getParentBankId() {
        return parentBankId;
    }

    public void setParentBankId(Long parentBankId) {
        this.parentBankId = parentBankId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(String sharedWith) {
        this.sharedWith = sharedWith;
    }

    public List<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<Long> questionIds) {
        this.questionIds = questionIds;
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
