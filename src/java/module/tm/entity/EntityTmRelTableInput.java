package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmRelTableInput extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_TABLE_ID = "fkTableId";
    private String fkTableId = "";
    public static String FK_INPUT_ID = "fkInputId";
    private String fkInputId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String INPUT_STATUS = "inputStatus";
    private String inputStatus = "";
    public static String SHOW_COMPONENT = "showComponent";
    private String showComponent = "";

    public String getShowComponent() {
        return showComponent;
    }

    public void setShowComponent(String showComponent) {
        this.showComponent = showComponent;
    }
    
    

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkTableId() {
        return fkTableId;
    }

    public void setFkTableId(String fkTableId) {
        this.fkTableId = fkTableId;
    }

    public String getFkInputId() {
        return fkInputId;
    }

    public void setFkInputId(String fkInputId) {
        this.fkInputId = fkInputId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInputStatus() {
        return inputStatus;
    }

    public void setInputStatus(String inputStatus) {
        this.inputStatus = inputStatus;
    }

}
