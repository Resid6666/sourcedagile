package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcKeyResource extends CoreEntity {

    public static String RESOURCE_NAME = "resourceName";
    private String resourceName = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFkBcId() {
        return fkBcId;
    }

    public void setFkBcId(String fkBcId) {
        this.fkBcId = fkBcId;
    }

}
