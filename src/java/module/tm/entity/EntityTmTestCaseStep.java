package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTestCaseStep extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String CREATED_TIME = "createdTime";
    private String createdTime = "";
    public static String CREATED_DATE = "createdDate";
    private String createdDate = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String EXPECTED_RESULT = "expectedResult";
    private String expectedResult = "";
    public static String FK_TEST_CASE_ID = "fkTestCaseId";
    private String fkTestCaseId = "";
    public static String REQUIRED_DATA = "requiredData";
    private String requiredData = "";
    public static String STEP_STATUS = "stepStatus";
    private String stepStatus = "";
    public static String STEP_TYPE = "stepType";
    private String stepType = "";
    public static String STEP_NAME = "stepName";
    private String stepName = "";

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
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

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getFkTestCaseId() {
        return fkTestCaseId;
    }

    public void setFkTestCaseId(String fkTestCaseId) {
        this.fkTestCaseId = fkTestCaseId;
    }

    public String getRequiredData() {
        return requiredData;
    }

    public void setRequiredData(String requiredData) {
        this.requiredData = requiredData;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

}
