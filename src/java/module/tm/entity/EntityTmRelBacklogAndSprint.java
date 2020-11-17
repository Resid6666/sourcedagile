package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelBacklogAndSprint extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_TASK_SPRINT_ID = "fkTaskSprintId";
    private String fkTaskSprintId = "";
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

    public String getFkTaskSprintId() {
        return fkTaskSprintId;
    }

    public void setFkTaskSprintId(String fkTaskSprintId) {
        this.fkTaskSprintId = fkTaskSprintId;
    }

}
