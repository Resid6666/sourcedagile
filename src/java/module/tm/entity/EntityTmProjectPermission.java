package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProjectPermission extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    public static String FK_USER_ID = "fkUserId";
    public static String DESCRIPTION = "description";

    private String fkProjectId = "";
    private String fkUserId = "";
    private String description = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
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
