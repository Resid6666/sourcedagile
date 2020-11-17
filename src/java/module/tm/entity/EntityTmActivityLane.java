package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmActivityLane extends CoreEntity {

    public static String COL_COUNT = "colCount";
    private String colCount = "";
    public static String FK_ACTIVITY_DIAGRAM_ID = "fkActivityDiagramId";
    private String fkActivityDiagramId = "";
    public static String FK_ACTIVITY_GROUP_ID = "fkActivityGroupId";
    private String fkActivityGroupId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String ROW_COUNT = "rowCount";
    private String rowCount = "";
    public static String LANE_NAME = "laneName";
    private String laneName = "";

    public String getColCount() {
        return colCount;
    }

    public void setColCount(String colCount) {
        this.colCount = colCount;
    }

    public String getFkActivityDiagramId() {
        return fkActivityDiagramId;
    }

    public void setFkActivityDiagramId(String fkActivityDiagramId) {
        this.fkActivityDiagramId = fkActivityDiagramId;
    }

    public String getFkActivityGroupId() {
        return fkActivityGroupId;
    }

    public void setFkActivityGroupId(String fkActivityGroupId) {
        this.fkActivityGroupId = fkActivityGroupId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

}
