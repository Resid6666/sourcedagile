package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProjectCanvasCard extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_ZONE_ID = "fkZoneId";
    private String fkZoneId = "";
    public static String CARD_NAME = "cardName";
    private String cardName = "";
    public static String CARD_BODY = "cardBody";
    private String cardBody = "";
    public static String FK_RELATED_BACKLOG_ID = "fkRelatedBacklogId";
    private String fkRelatedBacklogId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";

    public String getCardBody() {
        return cardBody;
    }

    public void setCardBody(String cardBody) {
        this.cardBody = cardBody;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkZoneId() {
        return fkZoneId;
    }

    public void setFkZoneId(String fkZoneId) {
        this.fkZoneId = fkZoneId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getFkRelatedBacklogId() {
        return fkRelatedBacklogId;
    }

    public void setFkRelatedBacklogId(String fkRelatedBacklogId) {
        this.fkRelatedBacklogId = fkRelatedBacklogId;
    }

}
