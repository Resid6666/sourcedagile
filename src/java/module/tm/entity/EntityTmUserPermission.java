package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmUserPermission extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_USER_ID = "fkUserId";
    private String fkUserId = "";
    public static String RELATION_ID = "relationId";
    private String relationId = "";
    public static String PERMISSION_TYPE = "permissionType";
    private String permissionType = "";
    public static String ACCESS_TYPE = "accessType";
    private String accessType = "";

     public static String EXCEPT_INPUTS = "exceptInputs";
    private String exceptInputs = "";

    public String getExceptInputs() {
        return exceptInputs;
    }

    public void setExceptInputs(String exceptInputs) {
        this.exceptInputs = exceptInputs;
    }
    
    

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

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
    
    
    

}
