package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmServiceProcessAndStoryCard extends CoreEntity {

    public static String FK_SERVICE_PROCESS_ID = "fkServiceProcessId";
    private String fkServiceProcessId = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";

    public String getFkServiceProcessId() {
        return fkServiceProcessId;
    }

    public void setFkServiceProcessId(String fkServiceProcessId) {
        this.fkServiceProcessId = fkServiceProcessId;
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

    
    
}
