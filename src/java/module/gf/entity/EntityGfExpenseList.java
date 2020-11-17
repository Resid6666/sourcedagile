package module.gf.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfExpenseList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String EXPENSE_DATE = "expenseDate";
    public static String EXPENSE_AMOUNT = "expenseAmount";
    public static String CURRENCY = "currency";
    public static String EXPENSE_PURPOSE = "expensePurpose";
    public static String FK_EXECUTOR_USER_ID = "fkExecutorUserId";
    public static String EXECUTOR_USER_FULNAME = "executorUserFulname";
    public static String FK_USER_ID = "fkUserId";
    public static String USER_FULNAME = "userFulname";
    public static String FK_PATIENT_SENDER_ID = "fkPatientSenderId";
    public static String PATIENT_SENDER_FULNAME = "patientSenderFulname";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String PATIENT_FULNAME = "patientFulname";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String PRODUCT_AMOUNT = "productAmount";
    public static String DESCRIPTION = "description";
    public static String DOCUMENT_NO = "documentNo";
    private String documentNo = "";

    private String expenseDate = "";
    private String expenseAmount = "";
    private String currency = "";
    private String expensePurpose = "";
    private String fkExecutorUserId = "";
    private String executorUserFulname = "";
    private String fkUserId = "";
    private String userFulname = "";
    private String fkPatientSenderId = "";
    private String patientSenderFulname = "";
    private String fkPatientId = "";
    private String patientFulname = "";
    private String fkProductId = "";
    private String productName = "";
    private String productCode = "";
    private String productAmount = "";
    private String description = "";

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
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

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExpensePurpose() {
        return expensePurpose;
    }

    public void setExpensePurpose(String expensePurpose) {
        this.expensePurpose = expensePurpose;
    }

    public String getFkExecutorUserId() {
        return fkExecutorUserId;
    }

    public void setFkExecutorUserId(String fkExecutorUserId) {
        this.fkExecutorUserId = fkExecutorUserId;
    }

    public String getExecutorUserFulname() {
        return executorUserFulname;
    }

    public void setExecutorUserFulname(String executorUserFulname) {
        this.executorUserFulname = executorUserFulname;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getUserFulname() {
        return userFulname;
    }

    public void setUserFulname(String userFulname) {
        this.userFulname = userFulname;
    }

    public String getFkPatientSenderId() {
        return fkPatientSenderId;
    }

    public void setFkPatientSenderId(String fkPatientSenderId) {
        this.fkPatientSenderId = fkPatientSenderId;
    }

    public String getPatientSenderFulname() {
        return patientSenderFulname;
    }

    public void setPatientSenderFulname(String patientSenderFulname) {
        this.patientSenderFulname = patientSenderFulname;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getPatientFulname() {
        return patientFulname;
    }

    public void setPatientFulname(String patientFulname) {
        this.patientFulname = patientFulname;
    }

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
