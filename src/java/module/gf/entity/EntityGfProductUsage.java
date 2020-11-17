package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProductUsage extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String DOCUMENT_NO = "documentNo";
    public static String FK_EXECUTOR_USER_ID = "fkExecutorUserId";
    public static String FK_ANBAR_ID = "fkAnbarId";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String USAGE_DATE = "usageDate";
    public static String USAGE_TIME = "usageTime";
    public static String AMOUNT = "amount";
    public static String USAGE_PURPOSE = "usagePurpose";
    public static String DESCRIPTION = "description";
    public static String FK_PATIENT_ID = "fkPatientId";
    private String fkPatientId = "";
    public static String AVERAGE_COST = "averageCost";
    private String averageCost = "";
    public static String TOTAL_PROFIT = "totalProfit";
    private String totalProfit = "";

    private String documentNo = "";
    private String fkExecutorUserId = "";
    private String fkAnbarId = "";
    private String fkProductId = "";
    private String usageDate = "";
    private String usageTime = "";
    private String amount = "";
    private String usagePurpose = "";
    private String description = "";

    public String getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(String averageCost) {
        this.averageCost = averageCost;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getFkExecutorUserId() {
        return fkExecutorUserId;
    }

    public void setFkExecutorUserId(String fkExecutorUserId) {
        this.fkExecutorUserId = fkExecutorUserId;
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

    public String getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(String usageDate) {
        this.usageDate = usageDate;
    }

    public String getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(String usageTime) {
        this.usageTime = usageTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsagePurpose() {
        return usagePurpose;
    }

    public void setUsagePurpose(String usagePurpose) {
        this.usagePurpose = usagePurpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
