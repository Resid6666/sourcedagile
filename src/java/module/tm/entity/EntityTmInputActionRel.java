package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmInputActionRel extends CoreEntity {

    public static String FK_INPUT_ID = "fkInputId";
    private String fkInputId = "";
    public static String FK_API_ID = "fkApiId";
    private String fkApiId = "";
    public static String ACTION_TYPE = "actionType";
    private String actionType = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    
    
    public String getFkInputId() {
        return fkInputId;
    }

    public void setFkInputId(String fkInputId) {
        this.fkInputId = fkInputId;
    }

    public String getFkApiId() {
        return fkApiId;
    }

    public void setFkApiId(String fkApiId) {
        this.fkApiId = fkApiId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

}
