package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogListWithTask extends CoreEntity {

    public static String BACKLOG_NAME = "backlogName";
    private String backlogName = "";
    public static String BACKLOG_BECAUSE = "backlogBecause";
    private String backlogBecause = "";
    public static String BACKLOG_STATUS = "backlogStatus";
    private String backlogStatus = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String CREATED_BY_NAME = "createdByName";
    private String createdByName = "";
    public static String CREATED_DATE = "createdDate";
    private String createdDate = "";
    public static String CREATED_TIME = "createdTime";
    private String createdTime = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String PROJECT_NAME = "projectName";
    private String projectName = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String PRIORITY = "priority";
    private String priority = "";
    public static String IS_SOURCED = "isSourced";
    private String isSourced = "";
    public static String DESCRIPTION_SOURCED = "descriptionSourced";
    private String descriptionSourced = "";
    public static String FK_SOURCED_ID = "fkSourcedId";
    private String fkSourcedId = "";
    public static String ESTIMATED_HOURS = "estimatedHours";
    private String estimatedHours = "";
    public static String SPENT_HOURS = "spentHours";
    private String spentHours = "";
    public static String SOURCED_NAME = "sourcedName";
    private String sourcedName = "";
    public static String IS_BOUNDED = "isBounded";
    private String isBounded = "";
    public static String ASSIGNEE_NAME = "assigneeName";
    private String assigneeName = "";
    public static String SPRINT_NAME = "sprintName";
    private String sprintName = "";
    public static String LABEL_NAME = "labelName";
    private String labelName = "";
    public static String IS_INITIAL = "isInitial";
    private String isInitial = "";
    public static String TASK_COUNT = "taskCount";
    private String taskCount = "";
    public static String INPUT_COUNT = "inputCount";
    private String inputCount = "";
    public static String COMMENT_COUNT = "commentCount";
    private String commentCount = "";
    public static String BUG_COUNT = "bugCount";
    private String bugCount = "";
    public static String UPDATE_COUNT = "updateCount";
    private String updateCount = "";
    public static String TASK_TYPE_NAME = "taskTypeName";
    private String taskTypeName = "";
    public static String FK_ASSIGNEE_ID = "fkAssigneeId";
    public static String FK_TASK_TYPE_ID = "fkTaskTypeId";
    private String fkTaskTypeId = "";
    public static String TASK_STATUS = "taskStatus";
    private String taskStatus = "";

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
    private String fkAssigneeId = "";

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
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

    public String getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(String taskCount) {
        this.taskCount = taskCount;
    }

    public String getInputCount() {
        return inputCount;
    }

    public void setInputCount(String inputCount) {
        this.inputCount = inputCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getIsInitial() {
        return isInitial;
    }

    public void setIsInitial(String isInitial) {
        this.isInitial = isInitial;
    }

    public static String IS_FROM_CUSTOMER = "isFromCustomer";
    private String isFromCustomer = "";

    public String getIsFromCustomer() {
        return isFromCustomer;
    }

    public void setIsFromCustomer(String isFromCustomer) {
        this.isFromCustomer = isFromCustomer;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getIsBounded() {
        return isBounded;
    }

    public void setIsBounded(String isBounded) {
        this.isBounded = isBounded;
    }

    public String getSourcedName() {
        return sourcedName;
    }

    public void setSourcedName(String sourcedName) {
        this.sourcedName = sourcedName;
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

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFkSourcedId() {
        return fkSourcedId;
    }

    public void setFkSourcedId(String fkSourcedId) {
        this.fkSourcedId = fkSourcedId;
    }

    public String getDescriptionSourced() {
        return descriptionSourced;
    }

    public void setDescriptionSourced(String descriptionSourced) {
        this.descriptionSourced = descriptionSourced;
    }

    public String getIsSourced() {
        return isSourced;
    }

    public void setIsSourced(String isSourced) {
        this.isSourced = isSourced;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

//    public String getFkProjectId() {
//        return fkProjectId;
//    }
//
//    public void setFkProjectId(String fkProjectId) {
//        this.fkProjectId = fkProjectId;
//    }
//
//    public String getProjectName() {
//        return projectName;
//    }
//
//    public void setProjectName(String projectName) {
//        this.projectName = projectName;
//    }
    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public String getBacklogBecause() {
        return backlogBecause;
    }

    public void setBacklogBecause(String backlogBecause) {
        this.backlogBecause = backlogBecause;
    }

    public String getBacklogStatus() {
        return backlogStatus;
    }

    public void setBacklogStatus(String backlogStatus) {
        this.backlogStatus = backlogStatus;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
