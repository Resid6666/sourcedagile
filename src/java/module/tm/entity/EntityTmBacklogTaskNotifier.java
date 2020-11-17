package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogTaskNotifier extends CoreEntity {

    public static String FK_BACKLOG_TASK_ID = "fkBacklogTaskId";
    private String fkBacklogTaskId = "";
    public static String FK_NOTIFIER_ID = "fkNotifierId";
    private String fkNotifierId = "";

    public String getFkBacklogTaskId() {
        return fkBacklogTaskId;
    }

    public void setFkBacklogTaskId(String fkBacklogTaskId) {
        this.fkBacklogTaskId = fkBacklogTaskId;
    }

    public String getFkNotifierId() {
        return fkNotifierId;
    }

    public void setFkNotifierId(String fkNotifierId) {
        this.fkNotifierId = fkNotifierId;
    }

    
}
