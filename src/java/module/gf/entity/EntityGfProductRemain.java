package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProductRemain extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_ANBAR_ID = "fkAnbarId";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String REMAIN_AMOUNT = "remainAmount";

    private String fkAnbarId = "";
    private String fkProductId = "";
    private String remainAmount = "";

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
