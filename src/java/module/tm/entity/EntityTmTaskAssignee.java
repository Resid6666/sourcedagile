package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskAssignee extends CoreEntity {

    public static String FK_TASK_ID = "fkTaskId";
    public static String FK_USER_ID = "fkUserId";
    public static String DESCRIPTION = "description";

    private String fkTaskId = "";
    private String fkUserId = "";
    private String description = "";

    public String getFkTaskId() {
        return fkTaskId;
    }

    public void setFkTaskId(String fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
