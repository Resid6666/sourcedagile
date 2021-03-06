package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProjectList extends CoreEntity {

    public static String PROJECT_NAME = "projectName";
    public static String START_DATE = "startDate";
    public static String END_DATE = "endDate";
    public static String FK_NETWORK_ID = "fkNetworkId";
    public static String NETWORK_NAME = "networkName";
    public static String PURPOSE = "purpose";
    public static String DESCRIPTION = "description";
    public static String PROJECT_CODE = "projectCode";
    private String projectCode = "";

    private String projectName = "";
    private String startDate = "";
    private String endDate = "";
    private String fkNetworkId = "";
    private String networkName = "";
    private String purpose = "";
    private String description = "";

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFkNetworkId() {
        return fkNetworkId;
    }

    public void setFkNetworkId(String fkNetworkId) {
        this.fkNetworkId = fkNetworkId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
