package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelTaskAndLabel extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_BACKLOG_TASK_ID = "fkBacklogTaskId";
    private String fkBacklogTaskId = "";
    public static String FK_TASK_LABEL_ID = "fkTaskLabelId";
    private String fkTaskLabelId = "";

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

    public String getFkBacklogTaskId() {
        return fkBacklogTaskId;
    }

    public void setFkBacklogTaskId(String fkBacklogTaskId) {
        this.fkBacklogTaskId = fkBacklogTaskId;
    }

    public String getFkTaskLabelId() {
        return fkTaskLabelId;
    }

    public void setFkTaskLabelId(String fkTaskLabelId) {
        this.fkTaskLabelId = fkTaskLabelId;
    }

}
