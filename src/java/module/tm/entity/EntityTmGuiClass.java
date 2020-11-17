package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmGuiClass extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String CLASS_NAME = "className";
    private String className = "";
    public static String CLASS_BODY = "classBody";
    private String classBody = "";
    public static String IS_GLOBAL = "isGlobal";
    private String isGlobal = "";

    public String getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(String isGlobal) {
        this.isGlobal = isGlobal;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassBody() {
        return classBody;
    }

    public void setClassBody(String classBody) {
        this.classBody = classBody;
    }
    
    

}
