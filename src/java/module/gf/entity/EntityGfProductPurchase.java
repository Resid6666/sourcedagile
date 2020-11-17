package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProductPurchase extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String INVOICE_NO = "invoiceNo";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PURCHASE_DATE = "purchaseDate";
    public static String PURCHASE_TIME = "purchaseTime";
    public static String PURCHASE_AMOUNT = "purchaseAmount";
    public static String PURCHASE_PRICE = "purchasePrice";
    public static String CURRENCY = "currency";
    public static String EXTRA_COST_1 = "extraCost1";
    public static String EXTRA_COST_1_EXPLAIN = "extraCost1Explain";
    public static String EXTRA_COST_2 = "extraCost2";
    public static String EXTRA_COST_2_EXPLAIN = "extraCost2Explain";
    public static String EXTRA_COST_3 = "extraCost3";
    public static String EXTRA_COST_3_EXPLAIN = "extraCost3Explain";
    public static String EXTRA_COST_4 = "extraCost4";
    public static String EXTRA_COST_4_EXPLAIN = "extraCost4Explain";
    public static String EXTRA_COST_5 = "extraCost5";
    public static String EXTRA_COST_5_EXPLAIN = "extraCost5Explain";
    public static String EXTRA_COST_6 = "extraCost6";
    public static String EXTRA_COST_6_EXPLAIN = "extraCost6Explain";
    public static String EXTRA_COST_7 = "extraCost7";
    public static String EXTRA_COST_7_EXPLAIN = "extraCost7Explain";
    public static String EXTRA_COST_8 = "extraCost8";
    public static String EXTRA_COST_8_EXPLAIN = "extraCost8Explain";
    public static String DESCRIPTION = "description";
    public static String PARAM_1 = "param1";
    public static String PARAM_2 = "param2";
    public static String PARAM_3 = "param3";
    public static String PARAM_4 = "param4";
    public static String FK_EMPLOYEE_USER_ID = "fkEmployeeUserId";
    public static String PURCHASE_DISCOUNT = "purchaseDiscount";
    private String purchaseDiscount = "";
    public static String PURCHASE_TOTAL_AMOUNT = "purchaseTotalAmount";
    private String purchaseTotalAmount = "";
    public static String FK_ANBAR_ID = "fkAnbarId";
    private String fkAnbarId = "";

    private String fkEmployeeUserId = "";
    private String invoiceNo = "";
    private String fkProductId = "";
    private String purchaseDate = "";
    private String purchaseTime = "";
    private String purchaseAmount = "";
    private String purchasePrice = "";
    private String currency = "";
    private String extraCost1 = "";
    private String extraCost1Explain = "";
    private String extraCost2 = "";
    private String extraCost2Explain = "";
    private String extraCost3 = "";
    private String extraCost3Explain = "";
    private String extraCost4 = "";
    private String extraCost4Explain = "";
    private String extraCost5 = "";
    private String extraCost5Explain = "";
    private String extraCost6 = "";
    private String extraCost6Explain = "";
    private String extraCost7 = "";
    private String extraCost7Explain = "";
    private String extraCost8 = "";
    private String extraCost8Explain = "";
    private String description = "";
    private String param1 = "";
    private String param2 = "";
    private String param3 = "";
    private String param4 = "";

    public String getFkAnbarId() {
        return fkAnbarId;
    }

    public void setFkAnbarId(String fkAnbarId) {
        this.fkAnbarId = fkAnbarId;
    }

    public String getPurchaseTotalAmount() {
        return purchaseTotalAmount;
    }

    public void setPurchaseTotalAmount(String purchaseTotalAmount) {
        this.purchaseTotalAmount = purchaseTotalAmount;
    }

    public String getPurchaseDiscount() {
        return purchaseDiscount;
    }

    public void setPurchaseDiscount(String purchaseDiscount) {
        this.purchaseDiscount = purchaseDiscount;
    }

    public String getFkEmployeeUserId() {
        return fkEmployeeUserId;
    }

    public void setFkEmployeeUserId(String fkEmployeeUserId) {
        this.fkEmployeeUserId = fkEmployeeUserId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExtraCost1() {
        return extraCost1;
    }

    public void setExtraCost1(String extraCost1) {
        this.extraCost1 = extraCost1;
    }

    public String getExtraCost1Explain() {
        return extraCost1Explain;
    }

    public void setExtraCost1Explain(String extraCost1Explain) {
        this.extraCost1Explain = extraCost1Explain;
    }

    public String getExtraCost2() {
        return extraCost2;
    }

    public void setExtraCost2(String extraCost2) {
        this.extraCost2 = extraCost2;
    }

    public String getExtraCost2Explain() {
        return extraCost2Explain;
    }

    public void setExtraCost2Explain(String extraCost2Explain) {
        this.extraCost2Explain = extraCost2Explain;
    }

    public String getExtraCost3() {
        return extraCost3;
    }

    public void setExtraCost3(String extraCost3) {
        this.extraCost3 = extraCost3;
    }

    public String getExtraCost3Explain() {
        return extraCost3Explain;
    }

    public void setExtraCost3Explain(String extraCost3Explain) {
        this.extraCost3Explain = extraCost3Explain;
    }

    public String getExtraCost4() {
        return extraCost4;
    }

    public void setExtraCost4(String extraCost4) {
        this.extraCost4 = extraCost4;
    }

    public String getExtraCost4Explain() {
        return extraCost4Explain;
    }

    public void setExtraCost4Explain(String extraCost4Explain) {
        this.extraCost4Explain = extraCost4Explain;
    }

    public String getExtraCost5() {
        return extraCost5;
    }

    public void setExtraCost5(String extraCost5) {
        this.extraCost5 = extraCost5;
    }

    public String getExtraCost5Explain() {
        return extraCost5Explain;
    }

    public void setExtraCost5Explain(String extraCost5Explain) {
        this.extraCost5Explain = extraCost5Explain;
    }

    public String getExtraCost6() {
        return extraCost6;
    }

    public void setExtraCost6(String extraCost6) {
        this.extraCost6 = extraCost6;
    }

    public String getExtraCost6Explain() {
        return extraCost6Explain;
    }

    public void setExtraCost6Explain(String extraCost6Explain) {
        this.extraCost6Explain = extraCost6Explain;
    }

    public String getExtraCost7() {
        return extraCost7;
    }

    public void setExtraCost7(String extraCost7) {
        this.extraCost7 = extraCost7;
    }

    public String getExtraCost7Explain() {
        return extraCost7Explain;
    }

    public void setExtraCost7Explain(String extraCost7Explain) {
        this.extraCost7Explain = extraCost7Explain;
    }

    public String getExtraCost8() {
        return extraCost8;
    }

    public void setExtraCost8(String extraCost8) {
        this.extraCost8 = extraCost8;
    }

    public String getExtraCost8Explain() {
        return extraCost8Explain;
    }

    public void setExtraCost8Explain(String extraCost8Explain) {
        this.extraCost8Explain = extraCost8Explain;
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
