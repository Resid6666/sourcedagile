package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTestCase extends CoreEntity {

    public static String TEST_CASE_NO = "testCaseNo";
    private String testCaseNo = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String CREATED_TIME = "createdTime";
    private String createdTime = "";
    public static String CREATED_DATE = "createdDate";
    private String createdDate = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String PRIORITY = "priority";
    private String priority = "";
    public static String TESTING_ENVIRONMENT = "testingEnvironment";
    private String testingEnvironment = "";
    public static String TEST_CASE_NAME = "testCaseName";
    private String testCaseName = "";
    public static String TEST_CASE_SCENARIO = "testCaseScenario";
    private String testCaseScenario = "";
    public static String GENERAL_DESCRIPTION = "generalDescription";
    private String generalDescription = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";

    public String getTestCaseNo() {
        return testCaseNo;
    }

    public void setTestCaseNo(String testCaseNo) {
        this.testCaseNo = testCaseNo;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTestingEnvironment() {
        return testingEnvironment;
    }

    public void setTestingEnvironment(String testingEnvironment) {
        this.testingEnvironment = testingEnvironment;
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

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }
    
    
    

}
