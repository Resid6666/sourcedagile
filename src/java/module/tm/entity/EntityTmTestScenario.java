package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTestScenario extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String SCENARIO_NAME = "scenarioName";
    private String scenarioName = "";
    public static String EXPECTED_RESULT = "expectedResult";
    private String expectedResult = "";
    public static String SCENARIO_STATUS = "scenarioStatus";
    private String scenarioStatus = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String SCENARIO_DATE = "scenarioDate";
    private String scenarioDate = "";
    public static String SCENARIO_TIME = "scenarioTime";
    private String scenarioTime = "";
    public static String FK_CREATED_BY = "fkCreatedBy";
    private String fkCreatedBy = "";
    public static String TEST_CASE = "testCase";
    private String testCase = "";
    public static String LINK_ID = "linkId";
    private String linkId = "";
    public static String FILE_URL = "fileUrl";
    private String fileUrl = "";
    public static String DATA_COMBINATION = "dataCombination";
    private String dataCombination = "";

    public String getDataCombination() {
        return dataCombination;
    }

    public void setDataCombination(String dataCombination) {
        this.dataCombination = dataCombination;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getFkCreatedBy() {
        return fkCreatedBy;
    }

    public void setFkCreatedBy(String fkCreatedBy) {
        this.fkCreatedBy = fkCreatedBy;
    }

    public String getScenarioDate() {
        return scenarioDate;
    }

    public void setScenarioDate(String scenarioDate) {
        this.scenarioDate = scenarioDate;
    }

    public String getScenarioTime() {
        return scenarioTime;
    }

    public void setScenarioTime(String scenarioTime) {
        this.scenarioTime = scenarioTime;
    }

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

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getScenarioStatus() {
        return scenarioStatus;
    }

    public void setScenarioStatus(String scenarioStatus) {
        this.scenarioStatus = scenarioStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
