package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmJsCode extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FN_DESCRIPTION = "fnDescription";
    private String fnDescription = "";
    public static String FN_EVENT = "fnEvent";
    private String fnEvent = "";
    public static String FN_EVENT_OBJECT = "fnEventObject";
    private String fnEventObject = "";
    public static String FN_CORE_NAME = "fnCoreName";
    private String fnCoreName = "";
    public static String FN_CORE_INPUT = "fnCoreInput";
    private String fnCoreInput = "";
    public static String FN_BODY = "fnBody";
    private String fnBody = "";
    public static String IS_GLOBAL = "isGlobal";
    private String isGlobal = "";
    public static String FN_TYPE = "fnType";
    private String fnType = "";
    public static String IS_ACTIVE = "isActive";
    private String isActive = "";

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    
    
    public String getFnType() {
        return fnType;
    }

    public void setFnType(String fnType) {
        this.fnType = fnType;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFnDescription() {
        return fnDescription;
    }

    public void setFnDescription(String fnDescription) {
        this.fnDescription = fnDescription;
    }

    public String getFnEvent() {
        return fnEvent;
    }

    public void setFnEvent(String fnEvent) {
        this.fnEvent = fnEvent;
    }

    public String getFnEventObject() {
        return fnEventObject;
    }

    public void setFnEventObject(String fnEventObject) {
        this.fnEventObject = fnEventObject;
    }

    public String getFnCoreName() {
        return fnCoreName;
    }

    public void setFnCoreName(String fnCoreName) {
        this.fnCoreName = fnCoreName;
    }

    public String getFnCoreInput() {
        return fnCoreInput;
    }

    public void setFnCoreInput(String fnCoreInput) {
        this.fnCoreInput = fnCoreInput;
    }

    public String getFnBody() {
        return fnBody;
    }

    public void setFnBody(String fnBody) {
        this.fnBody = fnBody;
    }

    public String getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(String isGlobal) {
        this.isGlobal = isGlobal;
    }

}
