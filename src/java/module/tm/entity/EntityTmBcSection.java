package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcSection extends CoreEntity {

    public static String SECTION_NAME = "sectionName";
    private String sectionName = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String GRID_NO = "gridNo";
    private String gridNo = "";
    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public String getGridNo() {
        return gridNo;
    }

    public void setGridNo(String gridNo) {
        this.gridNo = gridNo;
    }

    public String getFkBcId() {
        return fkBcId;
    }

    public void setFkBcId(String fkBcId) {
        this.fkBcId = fkBcId;
    }

    
    
}
