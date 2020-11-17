package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmServiceProcess extends CoreEntity {

    public static String FK_SERVICE_ID = "fkServiceId";
    private String fkServiceId = "";
    public static String FK_SERVICE_GROUP_ID = "fkServiceGroupId";
    private String fkServiceGroupId = "";
    public static String PROCESS_DESC = "processDesc";
    private String processDesc = "";
    public static String PROCESS_NAME = "processName";
    private String processName = "";

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
    
    

    public String getFkServiceId() {
        return fkServiceId;
    }

    public void setFkServiceId(String fkServiceId) {
        this.fkServiceId = fkServiceId;
    }

    public String getFkServiceGroupId() {
        return fkServiceGroupId;
    }

    public void setFkServiceGroupId(String fkServiceGroupId) {
        this.fkServiceGroupId = fkServiceGroupId;
    }

    public String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

}
