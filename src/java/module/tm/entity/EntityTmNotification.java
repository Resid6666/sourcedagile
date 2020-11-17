package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmNotification extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_BACKLOG_HISTORY_ID = "fkBacklogHistoryId";
    private String fkBacklogHistoryId = "";
    public static String FK_USER_ID = "fkUserId";
    private String fkUserId = "";
    public static String NOTIFICATION_DATE = "notificationDate";
    private String notificationDate = "";
    public static String NOTIFICATION_TIME = "notificationTime";
    private String notificationTime = "";
    public static String REVIEW_DATE = "reviewDate";
    private String reviewDate = "";
    public static String REVIEW_TIME = "reviewTime";
    private String reviewTime = "";
    public static String IS_REVIEWED = "isReviewed";
    private String isReviewed = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkBacklogHistoryId() {
        return fkBacklogHistoryId;
    }

    public void setFkBacklogHistoryId(String fkBacklogHistoryId) {
        this.fkBacklogHistoryId = fkBacklogHistoryId;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(String isReviewed) {
        this.isReviewed = isReviewed;
    }

}
