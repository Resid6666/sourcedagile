package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProductRemainList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_ANBAR_ID = "fkAnbarId";
    public static String ANBAR_NAME = "anbarName";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String UNIT = "unit";
    public static String REMAIN_AMOUNT = "remainAmount";

    private String fkAnbarId = "";
    private String anbarName = "";
    private String fkProductId = "";
    private String productName = "";
    private String productCode = "";
    private String unit = "";
    private String remainAmount = "";

    public String getAnbarName() {
        return anbarName;
    }

    public void setAnbarName(String anbarName) {
        this.anbarName = anbarName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    
    public String getFkAnbarId() {
        return fkAnbarId;
    }

    public void setFkAnbarId(String fkAnbarId) {
        this.fkAnbarId = fkAnbarId;
    }

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

}
