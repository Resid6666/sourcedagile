package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcServiceGroup extends CoreEntity {

    public static String GROUP_NAME = "groupName";
    private String groupName = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String FK_OWNER_ID = "fkOwnerId";
    private String fkOwnerId = "";
    public static String DESCRIPTION = "description";
    private String description = "";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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
