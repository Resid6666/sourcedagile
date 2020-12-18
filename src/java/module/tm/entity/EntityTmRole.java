package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRole extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String ROLE_CODE = "roleCode";
    private String roleCode = "";
    public static String ROLE_NAME = "roleName";
    private String roleName = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
    
}
