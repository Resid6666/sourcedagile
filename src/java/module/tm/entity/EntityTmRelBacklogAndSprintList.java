package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelBacklogAndSprintList extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String BACKLOG_NAME = "backlogName";
    private String backlogName = "";
    public static String FK_TASK_SPRINT_ID = "fkTaskLabelId";
    private String fkTaskSprintId = "";
    public static String SPRINT_NAME = "sprintName";
    private String sprintName = "";
    public static String SPRINT_COLOR = "sprintColor";
    private String sprintColor = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkTaskSprintId() {
        return fkTaskSprintId;
    }

    public void setFkTaskSprintId(String fkTaskSprintId) {
        this.fkTaskSprintId = fkTaskSprintId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public String getSprintName() {
        return sprintName;
    }

    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }

    public String getSprintColor() {
        return sprintColor;
    }

    public void setSprintColor(String sprintColor) {
        this.sprintColor = sprintColor;
    }

}
