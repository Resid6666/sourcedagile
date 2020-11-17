package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcServiceRelation extends CoreEntity {

    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";
    public static String FK_SERVICE_GROUP_ID = "fkServiceGroupId";
    private String fkServiceGroupId = "";
    public static String FK_SERVICE_ID = "fkServiceId";
    private String fkServiceId = "";

    public String getFkBcId() {
        return fkBcId;
    }

    public void setFkBcId(String fkBcId) {
        this.fkBcId = fkBcId;
    }

    public String getFkServiceGroupId() {
        return fkServiceGroupId;
    }

    public void setFkServiceGroupId(String fkServiceGroupId) {
        this.fkServiceGroupId = fkServiceGroupId;
    }

    public String getFkServiceId() {
        return fkServiceId;
    }

    public void setFkServiceId(String fkServiceId) {
        this.fkServiceId = fkServiceId;
    }

}
