package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcService extends CoreEntity {

    public static String SERVICE_NAME = "serviceName";
    private String serviceName = "";
    public static String SERVICE_NO = "serviceNo";
    private String serviceNo = "";
    public static String FK_SERVICE_GROUP_ID = "fkServiceGroupId";
    private String fkServiceGroupId = "";
    public static String FK_OWNER_ID = "fkOwnerId";
    private String fkOwnerId = "";
    public static String DESCRIPTION = "description";
    private String description = "";

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public String getFkServiceGroupId() {
        return fkServiceGroupId;
    }

    public void setFkServiceGroupId(String fkServiceGroupId) {
        this.fkServiceGroupId = fkServiceGroupId;
    }

    public String getFkOwnerId() {
        return fkOwnerId;
    }

    public void setFkOwnerId(String fkOwnerId) {
        this.fkOwnerId = fkOwnerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
