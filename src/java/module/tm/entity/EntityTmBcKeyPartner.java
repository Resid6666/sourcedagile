package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcKeyPartner extends CoreEntity {

    public static String PARTNER_NAME = "partnerName";
    private String partnerName = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
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
