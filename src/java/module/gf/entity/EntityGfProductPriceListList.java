package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProductPriceListList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String AVERAGE_COST = "averageCost";
    public static String SALE_PRICE = "salePrice";
    public static String DESCRIPTION = "description";
    public static String PARAM_1 = "param1";
    public static String PARAM_2 = "param2";
    public static String PARAM_3 = "param3";
    public static String PARAM_4 = "param4";
    public static String CURRENCY = "currency";


    private String currency = "";
    private String fkProductId = "";
    private String productName = "";
    private String productCode = "";
    private String averageCost = "";
    private String salePrice = "";
    private String description = "";
    private String param1 = "";
    private String param2 = "";
    private String param3 = "";
    private String param4 = "";

    

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(String averageCost) {
        this.averageCost = averageCost;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

}
