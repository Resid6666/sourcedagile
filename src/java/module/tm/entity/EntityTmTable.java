package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTable extends CoreEntity {

    public static String TABLE_NAME = "tableName";
    private String tableName = "";
    public static String FK_DB_ID = "fkDbId";
    private String fkDbId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String DESCRIPTION = "description";
    private String description = "";

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFkDbId() {
        return fkDbId;
    }

    public void setFkDbId(String fkDbId) {
        this.fkDbId = fkDbId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
