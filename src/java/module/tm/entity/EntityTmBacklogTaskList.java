package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogTaskList extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_TASK_TYPE_ID = "fkTaskTypeId";
    private String fkTaskTypeId = "";
    public static String TASK_TYPE_NAME = "taskTypeName";
    private String taskTypeName = "";
    public static String FK_ASSIGNEE_ID = "fkAssigneeId";
    private String fkAssigneeId = "";
    public static String ASSIGNEE_NAME = "assigneeName";
    private String assigneeName = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String CREATED_BY_NAME = "createdByName";
    private String createdByName = "";
    public static String CREATED_DATE = "createdDate";
    private String createdDate = "";
    public static String CREATED_TIME = "createdTime";
    private String createdTime = "";
    public static String ESTIMATED_HOURS = "estimatedHours";
    private String estimatedHours = "";
    public static String SPENT_HOURS = "spentHours";
    private String spentHours = "";
    public static String DEPENDENT_TASK_TYPE_1_ID = "dependentTaskType1Id";
    private String dependentTaskType1Id = "";
    public static String DEPENDENT_TASK_TYPE_2_ID = "dependentTaskType2Id";
    private String dependentTaskType2Id = "";
    public static String DEPENDENT_TASK_TYPE_1_NAME = "dependentTaskType1Name";
    private String dependentTaskType1Name = "";
    public static String DEPENDENT_TASK_TYPE_2_NAME = "dependentTaskType2Name";
    private String dependentTaskType2Name = "";
    public static String COMPLETED_DURATION = "completedDuration";
    private String completedDuration = "";
    public static String UPDATED_BY = "updatedBy";
    private String updatedBy = "";
    public static String UPDATED_BY_NAME = "updatedByName";
    private String updatedByName = "";
    public static String LAST_UPDATED_DATE = "lastUpdatedDate";
    private String lastUpdatedDate = "";
    public static String LAST_UPDATED_TIME = "lastUpdatedTime";
    private String lastUpdatedTime = "";
    public static String TASK_STATUS = "taskStatus";
    private String taskStatus = "";
    public static String IS_DETECTED_BUG = "isDetectedBug";
    private String isDetectedBug = "";
    public static String IS_UPDATE_REQUIRED = "isUpdateRequired";
    private String isUpdateRequired = "";
    public static String BUG_COUNT = "bugCount";
    private String bugCount = "";
    public static String UPDATE_COUNT = "updateCount";
    private String updateCount = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String IS_GENERAL = "isGeneral";
    private String isGeneral = "";
    public static String IS_NOTIFIED_BUG = "isNotifiedBug";
    private String isNotifiedBug = "";
    public static String ASSIGNEE_IMAGE_URL = "assigneeImageUrl";
    private String assigneeImageUrl = "";
    public static String START_DATE = "startDate";
    private String startDate = "";
    public static String START_TIME = "startTime";
    private String startTime = "";
    public static String START_TYPE = "startType";
    private String startType = "";
    public static String JIRA_ISSUE_ID = "jiraIssueId";
    private String jiraIssueId = "";
    public static String JIRA_ISSUE_KEY = "jiraIssueKey";
    private String jiraIssueKey = "";
    public static String ESTIMATED_COUNTER = "estimatedCounter";
    private String estimatedCounter = "";
    public static String EXECUTED_COUNTER = "executedCounter";
    private String executedCounter = "";
    public static String ESTIMATED_BUDGET = "estimatedBudget";
    private String estimatedBudget = "";
    public static String SPENT_BUDGET = "spentBudget";
    private String spentBudget = "";
    public static String TASK_DUE_DATE = "taskDueDate";
    private String taskDueDate = "";
    public static String TASK_PRIORITY = "taskPriority ";
    private String taskPriority = "";

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getEstimatedCounter() {
        return estimatedCounter;
    }

    public void setEstimatedCounter(String estimatedCounter) {
        this.estimatedCounter = estimatedCounter;
    }

    public String getExecutedCounter() {
        return executedCounter;
    }

    public void setExecutedCounter(String executedCounter) {
        this.executedCounter = executedCounter;
    }

    public String getEstimatedBudget() {
        return estimatedBudget;
    }

    public void setEstimatedBudget(String estimatedBudget) {
        this.estimatedBudget = estimatedBudget;
    }

    public String getSpentBudget() {
        return spentBudget;
    }

    public void setSpentBudget(String spentBudget) {
        this.spentBudget = spentBudget;
    }

    public String getJiraIssueId() {
        return jiraIssueId;
    }

    public void setJiraIssueId(String jiraIssueId) {
        this.jiraIssueId = jiraIssueId;
    }

    public String getJiraIssueKey() {
        return jiraIssueKey;
    }

    public void setJiraIssueKey(String jiraIssueKey) {
        this.jiraIssueKey = jiraIssueKey;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public String getAssigneeImageUrl() {
        return assigneeImageUrl;
    }

    public void setAssigneeImageUrl(String assigneeImageUrl) {
        this.assigneeImageUrl = assigneeImageUrl;
    }

    public String getIsNotifiedBug() {
        return isNotifiedBug;
    }

    public void setIsNotifiedBug(String isNotifiedBug) {
        this.isNotifiedBug = isNotifiedBug;
    }

    public String getIsGeneral() {
        return isGeneral;
    }

    public void setIsGeneral(String isGeneral) {
        this.isGeneral = isGeneral;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getBugCount() {
        return bugCount;
    }

    public void setBugCount(String bugCount) {
        this.bugCount = bugCount;
    }

    public String getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(String updateCount) {
        this.updateCount = updateCount;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getDependentTaskType1Name() {
        return dependentTaskType1Name;
    }

    public void setDependentTaskType1Name(String dependentTaskType1Name) {
        this.dependentTaskType1Name = dependentTaskType1Name;
    }

    public String getDependentTaskType2Name() {
        return dependentTaskType2Name;
    }

    public void setDependentTaskType2Name(String dependentTaskType2Name) {
        this.dependentTaskType2Name = dependentTaskType2Name;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkTaskTypeId() {
        return fkTaskTypeId;
    }

    public void setFkTaskTypeId(String fkTaskTypeId) {
        this.fkTaskTypeId = fkTaskTypeId;
    }

    public String getFkAssigneeId() {
        return fkAssigneeId;
    }

    public void setFkAssigneeId(String fkAssigneeId) {
        this.fkAssigneeId = fkAssigneeId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(String estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(String spentHours) {
        this.spentHours = spentHours;
    }

    public String getDependentTaskType1Id() {
        return dependentTaskType1Id;
    }

    public void setDependentTaskType1Id(String dependentTaskType1Id) {
        this.dependentTaskType1Id = dependentTaskType1Id;
    }

    public String getDependentTaskType2Id() {
        return dependentTaskType2Id;
    }

    public void setDependentTaskType2Id(String dependentTaskType2Id) {
        this.dependentTaskType2Id = dependentTaskType2Id;
    }

    public String getCompletedDuration() {
        return completedDuration;
    }

    public void setCompletedDuration(String completedDuration) {
        this.completedDuration = completedDuration;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getIsDetectedBug() {
        return isDetectedBug;
    }

    public void setIsDetectedBug(String isDetectedBug) {
        this.isDetectedBug = isDetectedBug;
    }

    public String getIsUpdateRequired() {
        return isUpdateRequired;
    }

    public void setIsUpdateRequired(String isUpdateRequired) {
        this.isUpdateRequired = isUpdateRequired;
    }

}
