package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfAnbarHereketList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_EXECUTOR_USER_ID = "fkExecutorUserId";
    public static String EXECUTOR_USER_FULNAME = "executorUserFulname";
    public static String FK_SOURCE_ANBAR_ID = "fkSourceAnbarId";
    public static String SOURCE_ANBAR_NAME = "sourceAnbarName";
    public static String FK_DESTINATION_ANBAR_ID = "fkDestinationAnbarId";
    public static String DESTINATION_ANBAR_NAME = "destinationAnbarName";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String PRODUCT_UNIT = "productUnit";
    public static String OPERATION_TYPE = "operationType";
    public static String DOCUMENT_NO = "documentNo";
    public static String HEREKET_DATE = "hereketDate";
    public static String HEREKET_TIME = "hereketTime";
    public static String AMOUNT = "amount";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String PATIENT_FULNAME = "patientFulname";
    public static String FK_GENERAL_REL_ID = "fkGeneralRelId";
    public static String DESCRIPTION = "description";
    public static String SOURCE_REMAIN_AMOUNT = "sourceRemainAmount";
    private String sourceRemainAmount = "";
    public static String DESTINATION_REMAIN_AMOUNT = "destinationRemainAmount";
    private String destinationRemainAmount = "";
    private String fkExecutorUserId = "";
    private String executorUserFulname = "";
    private String fkSourceAnbarId = "";
    private String sourceAnbarName = "";
    private String fkDestinationAnbarId = "";
    private String destinationAnbarName = "";
    private String fkProductId = "";
    private String productName = "";
    private String productCode = "";
    private String productUnit = "";
    private String operationType = "";
    private String documentNo = "";
    private String hereketDate = "";
    private String hereketTime = "";
    private String amount = "";
    private String fkPatientId = "";
    private String patientFulname = "";
    private String fkGeneralRelId = "";
    private String description = "";

    public String getSourceRemainAmount() {
        return sourceRemainAmount;
    }

    public void setSourceRemainAmount(String sourceRemainAmount) {
        this.sourceRemainAmount = sourceRemainAmount;
    }

    public String getDestinationRemainAmount() {
        return destinationRemainAmount;
    }

    public void setDestinationRemainAmount(String destinationRemainAmount) {
        this.destinationRemainAmount = destinationRemainAmount;
    }

    public String getExecutorUserFulname() {
        return executorUserFulname;
    }

    public void setExecutorUserFulname(String executorUserFulname) {
        this.executorUserFulname = executorUserFulname;
    }

    public String getSourceAnbarName() {
        return sourceAnbarName;
    }

    public void setSourceAnbarName(String sourceAnbarName) {
        this.sourceAnbarName = sourceAnbarName;
    }

    public String getDestinationAnbarName() {
        return destinationAnbarName;
    }

    public void setDestinationAnbarName(String destinationAnbarName) {
        this.destinationAnbarName = destinationAnbarName;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getPatientFulname() {
        return patientFulname;
    }

    public void setPatientFulname(String patientFulname) {
        this.patientFulname = patientFulname;
    }

    public String getFkExecutorUserId() {
        return fkExecutorUserId;
    }

    public void setFkExecutorUserId(String fkExecutorUserId) {
        this.fkExecutorUserId = fkExecutorUserId;
    }

    public String getFkSourceAnbarId() {
        return fkSourceAnbarId;
    }

    public void setFkSourceAnbarId(String fkSourceAnbarId) {
        this.fkSourceAnbarId = fkSourceAnbarId;
    }

    public String getFkDestinationAnbarId() {
        return fkDestinationAnbarId;
    }

    public void setFkDestinationAnbarId(String fkDestinationAnbarId) {
        this.fkDestinationAnbarId = fkDestinationAnbarId;
    }

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getHereketDate() {
        return hereketDate;
    }

    public void setHereketDate(String hereketDate) {
        this.hereketDate = hereketDate;
    }

    public String getHereketTime() {
        return hereketTime;
    }

    public void setHereketTime(String hereketTime) {
        this.hereketTime = hereketTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getFkGeneralRelId() {
        return fkGeneralRelId;
    }

    public void setFkGeneralRelId(String fkGeneralRelId) {
        this.fkGeneralRelId = fkGeneralRelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
