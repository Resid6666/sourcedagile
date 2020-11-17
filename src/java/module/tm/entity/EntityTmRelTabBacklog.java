package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelTabBacklog extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_TAB_ID = "fkTabId";
    private String fkTabId = "";
    public static String FK_RELATED_BACKLOG_ID = "fkRelatedBacklogId";
    private String fkRelatedBacklogId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String BACKLOG_STATUS = "backlogStatus";
    private String backlogStatus = "";

    public String getBacklogStatus() {
        return backlogStatus;
    }

    public void setBacklogStatus(String backlogStatus) {
        this.backlogStatus = backlogStatus;
    }
    
    

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkTabId() {
        return fkTabId;
    }

    public void setFkTabId(String fkTabId) {
        this.fkTabId = fkTabId;
    }

    public String getFkRelatedBacklogId() {
        return fkRelatedBacklogId;
    }

    public void setFkRelatedBacklogId(String fkRelatedBacklogId) {
        this.fkRelatedBacklogId = fkRelatedBacklogId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    
    
}
