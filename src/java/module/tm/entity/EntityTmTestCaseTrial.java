package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTestCaseTrial extends CoreEntity {

    public static String CREATED_TIME = "createdTime";
    private String createdTime = "";
    public static String CREATED_DATE = "createdDate";
    private String createdDate = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String TEST_CASE_NAME = "testCaseName";
    private String testCaseName = "";
    public static String TEST_CASE_SCENARIO = "testCaseScenario";
    private String testCaseScenario = "";
    public static String GENERAL_DESCRIPTION = "generalDescription";
    private String generalDescription = "";
    public static String TRIAL_STATUS = "trialStatus";
    private String trialStatus = "";
    public static String IS_SOLVED = "isSolved";
    private String isSolved = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_TEST_CASE_ID = "fkTestCaseId";
    private String fkTestCaseId = "";
    public static String IMG_URL = "imgUrl";
    private String imgUrl = "";

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFkTestCaseId() {
        return fkTestCaseId;
    }

    public void setFkTestCaseId(String fkTestCaseId) {
        this.fkTestCaseId = fkTestCaseId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getTestCaseScenario() {
        return testCaseScenario;
    }

    public void setTestCaseScenario(String testCaseScenario) {
        this.testCaseScenario = testCaseScenario;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public String getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(String trialStatus) {
        this.trialStatus = trialStatus;
    }

    public String getIsSolved() {
        return isSolved;
    }

    public void setIsSolved(String isSolved) {
        this.isSolved = isSolved;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

}
