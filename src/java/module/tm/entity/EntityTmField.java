package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmField extends CoreEntity {

    public static String FIELD_NAME = "fieldName";
    private String fieldName = "";
    public static String FK_TABLE_ID = "fkTableId";
    private String fkTableId = "";
    public static String FK_DB_ID = "fkDbId";
    private String fkDbId = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String FIELD_TYPE = "fieldType";
    private String fieldType = "";
    public static String FIELD_LENGTH = "fieldLength";
    private String fieldLength = "";
    public static String EXTRA_PARAM = "extraParam";
    private String extraParam = "";

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }

    
    
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFkTableId() {
        return fkTableId;
    }

    public void setFkTableId(String fkTableIf) {
        this.fkTableId = fkTableIf;
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
