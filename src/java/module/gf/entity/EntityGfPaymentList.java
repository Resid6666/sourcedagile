package module.gf.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPaymentList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DISCOUNT_ID = "fkDiscountId";
    public static String DISCOUNT_NAME = "discountName";
    public static String PAYMENT_NO = "paymentNo";
    public static String FK_DOCTOR_USER_ID = "fkDoctorUserId";
    public static String DOCTOR_FULLNAME = "doctorFullname";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String PATIENT_ID = "patientId";
    public static String PATIENT_FULLNAME = "patientFullname";
    public static String PAYMENT_DATE = "paymentDate";
    public static String PAYMENT_TIME = "paymentTime";
    public static String PAYMENT_AMOUNT = "paymentAmount";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String PAYMENT_NAME = "paymentName";
    public static String PRICE = "price";
    public static String PAYMENT_CURRENCY = "paymentCurrency";
    public static String PAYMENT_DISCOUNT = "paymentDiscount";
    public static String PAYMENT_STATUS = "paymentStatus";
    public static String DESCRIPTION = "description";
    public static String SESSION_NO = "sessionNo";
    public static String FK_EXECUTOR_ID = "fkExecutorId";
    private String fkExecutorId = "";
    public static String EXECUTOR_NAME = "executorName";
    private String executorName = "";

    private String sessionNo = "";
    private String price = "";

    private String fkDiscountId = "";
    private String discountName = "";
    private String paymentNo = "";
    private String fkDoctorUserId = "";
    private String doctorFullname = "";
    private String fkPatientId = "";
    private String patientId = "";
    private String patientFullname = "";
    private String paymentDate = "";
    private String paymentTime = "";
    private String paymentAmount = "";
    private String fkPriceListId = "";
    private String paymentName = "";
    private String paymentCurrency = "";
    private String paymentDiscount = "";
    private String paymentStatus = "";
    private String description = "";

    public String getFkExecutorId() {
        return fkExecutorId;
    }

    public void setFkExecutorId(String fkExecutorId) {
        this.fkExecutorId = fkExecutorId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDoctorFullname() {
        return doctorFullname;
    }

    public void setDoctorFullname(String doctorFullname) {
        this.doctorFullname = doctorFullname;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientFullname() {
        return patientFullname;
    }

    public void setPatientFullname(String patientFullname) {
        this.patientFullname = patientFullname;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getFkDiscountId() {
        return fkDiscountId;
    }

    public void setFkDiscountId(String fkDiscountId) {
        this.fkDiscountId = fkDiscountId;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getFkDoctorUserId() {
        return fkDoctorUserId;
    }

    public void setFkDoctorUserId(String fkDoctorUserId) {
        this.fkDoctorUserId = fkDoctorUserId;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getFkPriceListId() {
        return fkPriceListId;
    }

    public void setFkPriceListId(String fkPriceListId) {
        this.fkPriceListId = fkPriceListId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentDiscount() {
        return paymentDiscount;
    }

    public void setPaymentDiscount(String paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
