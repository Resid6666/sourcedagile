package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTask extends CoreEntity {

    public static String NAME = "name";
    public static String FK_PARENT_TASK_ID = "fkParentTaskId";
    public static String CREATED_BY = "createdBy";
    public static String CREATED_DATE = "createdDate";
    public static String CREATED_TIME = "createdTime";
    public static String START_DATE = "startDate";
    public static String START_TIME = "startTime";
    public static String END_DATE = "endDate";
    public static String END_TIME = "endTime";
    public static String FINISH_DATE = "finishDate";
    public static String FINISH_TIME = "finishTime";
    public static String COMPLETED_DURATION = "completedDuration";
    public static String DESCRIPTION = "description";
    public static String FK_TASK_TYPE_ID = "fkTaskTypeId";
    public static String FK_TASK_STATUS_ID = "fkTaskStatusId";
    public static String FK_PROJECT_ID = "fkProjectId";
    public static String UPDATED_BY = "updatedBy";
    public static String LAST_UPDATED_DATE = "lastUpdatedDate";
    public static String LAST_UPDATED_TIME = "lastUpdatedTime";
    public static String ORDER_NO = "orderNo";
    public static String FK_PRIORITY_ID = "fkPriorityId";
    public static String FK_PROGRESS_ID = "fkProgressId";
    public static String FK_TASK_CATEGORY_ID = "fkTaskCategoryId";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";

    private String name = "";
    private String fkParentTaskId = "";
    private String createdBy = "";
    private String createdDate = "";
    private String createdTime = "";
    private String startDate = "";
    private String startTime = "";
    private String endDate = "";
    private String endTime = "";
    private String finishDate = "";
    private String finishTime = "";
    private String completedDuration = "";
    private String description = "";
    private String fkTaskTypeId = "";
    private String fkTaskStatusId = "";
    private String fkProjectId = "";
    private String updatedBy = "";
    private String lastUpdatedDate = "";
    private String lastUpdatedTime = "";
    private String orderNo = "";
    private String fkPriorityId = "";
    private String fkProgressId = "";
    private String fkTaskCategoryId = "";

    public static String ESTIMATED_COUNTER = "estimatedCounter";
    private String estimatedCounter = "";
    public static String EXECUTED_COUNTER = "executedCounter";
    private String executedCounter = "";
    public static String ESTIMATED_BUDGET = "estimatedBudget";
    private String estimatedBudget = "";
    public static String SPENT_BUDGET = "spentBudget";
    private String spentBudget = "";

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFkParentTaskId() {
        return fkParentTaskId;
    }

    public void setFkParentTaskId(String fkParentTaskId) {
        this.fkParentTaskId = fkParentTaskId;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCompletedDuration() {
        return completedDuration;
    }

    public void setCompletedDuration(String completedDuration) {
        this.completedDuration = completedDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFkTaskTypeId() {
        return fkTaskTypeId;
    }

    public void setFkTaskTypeId(String fkTaskTypeId) {
        this.fkTaskTypeId = fkTaskTypeId;
    }

    public String getFkTaskStatusId() {
        return fkTaskStatusId;
    }

    public void setFkTaskStatusId(String fkTaskStatusId) {
        this.fkTaskStatusId = fkTaskStatusId;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFkPriorityId() {
        return fkPriorityId;
    }

    public void setFkPriorityId(String fkPriorityId) {
        this.fkPriorityId = fkPriorityId;
    }

    public String getFkProgressId() {
        return fkProgressId;
    }

    public void setFkProgressId(String fkProgressId) {
        this.fkProgressId = fkProgressId;
    }

    public String getFkTaskCategoryId() {
        return fkTaskCategoryId;
    }

    public void setFkTaskCategoryId(String fkTaskCategoryId) {
        this.fkTaskCategoryId = fkTaskCategoryId;
    }

}
