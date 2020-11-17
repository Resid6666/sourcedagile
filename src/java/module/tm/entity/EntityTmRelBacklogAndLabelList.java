package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelBacklogAndLabelList extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String BACKLOG_NAME = "backlogName";
    private String backlogName = "";
    public static String FK_TASK_LABEL_ID = "fkTaskLabelId";
    private String fkTaskLabelId = "";
    public static String LABEL_NAME = "labelName";
    private String labelName = "";
    public static String LABEL_COLOR = "labelColor";
    private String labelColor = "";

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkTaskLabelId() {
        return fkTaskLabelId;
    }

    public void setFkTaskLabelId(String fkTaskLabelId) {
        this.fkTaskLabelId = fkTaskLabelId;
    }

}
