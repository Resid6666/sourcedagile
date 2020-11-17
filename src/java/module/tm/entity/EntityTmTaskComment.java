package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskComment extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    public static String FK_USER_ID = "fkUserId";
    public static String COMMENT = "comment";
    public static String COMMENT_DATE = "commentDate";
    public static String FK_PARENT_COMMENT_ID = "fkParentCommentiId";
    public static String COMMENT_TIME = "commentTime";
    public static String COMMENT_TYPE = "commentType";
    private String commentType = "";
    private String commentTime = "";
    private String fkBacklogId = "";
    private String fkUserId = "";
    private String comment = "";
    private String commentDate = "";
    private String fkParentCommentId = "";
    public static String FK_TASK_ID = "fkTaskId";
    private String fkTaskId = "";
    public static String IS_BUG = "isBug";
    private String isBug = "";
    public static String IS_REQUEST = "isRequest";
    private String isRequest = "";
    public static String IS_SUBTASK = "isSubtask";
    private String isSubtask = "";
    public static String IS_NOTIFIED_BUG = "isNotifiedBug";
    private String isNotifiedBug = "";
    public static String ESTIMATED_HOURS = "estimatedHours";
    private String estimatedHours = "";
    public static String SPENT_HOURS = "spentHours";
    private String spentHours = "";
    public static String UPDATED_BY = "updatedBy";
    private String updatedBy = "";
    public static String LAST_UPDATED_DATE = "lastUpdatedDate";
    private String lastUpdatedDate = "";
    public static String LAST_UPDATED_TIME = "lastUpdatedTime";
    private String lastUpdatedTime = "";
    public static String START_DATE = "startDate";
    private String startDate = "";
    public static String START_TIME = "startTime";
    private String startTime = "";
    public static String START_TYPE = "startType";
    private String startType = "";
    public static String COMMENT_STATUS = "commentStatus";
    private String commentStatus = "";
    public static String IS_NOTIFIED_REQUEST = "isNotifiedRequest";
    private String isNotifiedRequest = "";

    public static String COMMENT_JIRA_ID = "commentJiraId";
    private String commentJiraId = "";
    public static String COMMENT_JIRA_KEY = "commentJiraKey";
    private String commentJiraKey = "";

    public String getCommentJiraId() {
        return commentJiraId;
    }

    public void setCommentJiraId(String commentJiraId) {
        this.commentJiraId = commentJiraId;
    }

    public String getCommentJiraKey() {
        return commentJiraKey;
    }

    public void setCommentJiraKey(String commentJiraKey) {
        this.commentJiraKey = commentJiraKey;
    }

    public String getIsNotifiedRequest() {
        return isNotifiedRequest;
    }

    public void setIsNotifiedRequest(String isNotifiedRequest) {
        this.isNotifiedRequest = isNotifiedRequest;
    }

    public String getIsNotifiedBug() {
        return isNotifiedBug;
    }

    public void setIsNotifiedBug(String isNotifiedBug) {
        this.isNotifiedBug = isNotifiedBug;
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

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getIsBug() {
        return isBug;
    }

    public void setIsBug(String isBug) {
        this.isBug = isBug;
    }

    public String getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(String isRequest) {
        this.isRequest = isRequest;
    }

    public String getIsSubtask() {
        return isSubtask;
    }

    public void setIsSubtask(String isSubtask) {
        this.isSubtask = isSubtask;
    }

    public String getFkTaskId() {
        return fkTaskId;
    }

    public void setFkTaskId(String fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getFkParentCommentId() {
        return fkParentCommentId;
    }

    public void setFkParentCommentId(String fkParentCommentId) {
        this.fkParentCommentId = fkParentCommentId;
    }

}
