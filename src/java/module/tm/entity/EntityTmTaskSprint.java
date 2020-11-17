package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskSprint extends CoreEntity {

    public static String SPRINT_NAME = "sprintName";
    private String sprintName = "";
    public static String SPRINT_START_DATE = "sprintStartDate";
    private String sprintStartDate = "";
    public static String SPRINT_END_DATE = "sprintEndDate";
    private String sprintEndDate = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String SPRINT_COLOR = "sprintColor";
    private String sprintColor = "";
    public static String SPRINT_DESCRIPTION = "sprintDescription";
    private String sprintDescription = "";
    public static String SPRINT_STATUS = "sprintStatus";
    private String sprintStatus = "";

    public String getSprintStatus() {
        return sprintStatus;
    }

    public void setSprintStatus(String sprintStatus) {
        this.sprintStatus = sprintStatus;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getSprintStartDate() {
        return sprintStartDate;
    }

    public void setSprintStartDate(String sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    public String getSprintEndDate() {
        return sprintEndDate;
    }

    public void setSprintEndDate(String sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getSprintColor() {
        return sprintColor;
    }

    public void setSprintColor(String sprintColor) {
        this.sprintColor = sprintColor;
    }

    public String getSprintDescription() {
        return sprintDescription;
    }

    public void setSprintDescription(String sprintDescription) {
        this.sprintDescription = sprintDescription;
    }

}
