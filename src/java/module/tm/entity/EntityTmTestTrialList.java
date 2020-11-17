package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTestTrialList extends CoreEntity {

    public static String FK_SCENARIO_ID = "fkScenarioId";
    private String fkScenarioId = "";
    public static String TRIAL_DATE = "trialDate";
    private String trialDate = "";
    public static String TRIAL_TIME = "trialTime";
    private String trialTime = "";
    public static String ACTUAL_RESULT = "actualResult";
    private String actualResult = "";
    public static String TRIAL_STATUS = "trialStatus";
    private String trialStatus = "";
    public static String FILE_NAME = "fileName";
    private String fileName = "";
    public static String IS_NOTIFIED_AS_BUG = "isNotifiedAsBug";
    private String isNotifiedAsBug = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String FK_CREATED_BY = "fkCreatedBy";
    private String fkCreatedBy = "";
    public static String CREATED_BY_NAME = "createdByName";
    private String createdByName = "";
    public static String CREATED_BY_AVATAR = "createdByAvatar";
    private String createdByAvatar = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_TASK_ID = "fkTaskId";
    private String fkTaskId = "";

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkTaskId() {
        return fkTaskId;
    }

    public void setFkTaskId(String fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    public String getCreatedByAvatar() {
        return createdByAvatar;
    }

    public void setCreatedByAvatar(String createdByAvatar) {
        this.createdByAvatar = createdByAvatar;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getFkCreatedBy() {
        return fkCreatedBy;
    }

    public void setFkCreatedBy(String fkCreatedBy) {
        this.fkCreatedBy = fkCreatedBy;
    }

    public String getFkScenarioId() {
        return fkScenarioId;
    }

    public void setFkScenarioId(String fkScenarioId) {
        this.fkScenarioId = fkScenarioId;
    }

    public String getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(String trialDate) {
        this.trialDate = trialDate;
    }

    public String getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(String trialTime) {
        this.trialTime = trialTime;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIsNotifiedAsBug() {
        return isNotifiedAsBug;
    }

    public void setIsNotifiedAsBug(String isNotifiedAsBug) {
        this.isNotifiedAsBug = isNotifiedAsBug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
