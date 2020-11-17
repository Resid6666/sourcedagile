package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogDependencyList extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String BACKLOG_NAME = "backlogName";
    private String backlogName = "";
    public static String FK_PARENT_BACKLOG_ID = "fkParentBacklogId";
    private String fkParentBacklogId = "";
    public static String PARENT_BACKLOG_NAME = "parentBacklogName";
    private String parentBacklogName = "";

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

    public String getBacklogName() {
        return backlogName;
    }

    public void setBacklogName(String backlogName) {
        this.backlogName = backlogName;
    }

    public String getFkParentBacklogId() {
        return fkParentBacklogId;
    }

    public void setFkParentBacklogId(String fkParentBacklogId) {
        this.fkParentBacklogId = fkParentBacklogId;
    }

    public String getParentBacklogName() {
        return parentBacklogName;
    }

    public void setParentBacklogName(String parentBacklogName) {
        this.parentBacklogName = parentBacklogName;
    }

     

}
