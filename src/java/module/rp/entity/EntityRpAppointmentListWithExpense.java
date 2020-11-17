package module.rp.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityRpAppointmentListWithExpense extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_MODULE_ID = "fkModuleId";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String SESSION_NO = "sessionNo";
    public static String PAYMENT_STATUS = "paymentStatus";
    public static String PATIENT_FULNAME = "patientFulname";
    public static String PATIENT_BIRTH_DATE = "patientBirthDate";
    public static String PATIENT_SENDER_FULNAME = "patientSenderFulname";
    public static String DOCTOR_FULLNAME = "doctorFullname";
    public static String APPOINTMENT_DATE = "appointmentDate";
    public static String APPOINTMENT_STATUS = "appointmentStatus";
    public static String DOCTOR_EXPENSE = "doctorExpense";
    public static String PATIENT_SENDER_EXPENSE = "patientSenderExpense";
    public static String PAYMENT_AMOUNT = "paymentAmount";
    public static String PAYMENT_DISCOUNT = "paymentDiscount";
    public static String PAYMENT_DESCRIPTION = "paymentDescription";
    public static String PRICE_LIST_PRICE = "priceListPrice";
    public static String DESCRIPTION = "description";
    public static String PURPOSE = "purpose";
    private String purpose = "";

    private String fkModuleId = "";
    private String fkPriceListId = "";
    private String sessionNo = "";
    private String paymentStatus = "";
    private String patientFulname = "";
    private String patientBirthDate = "";
    private String patientSenderFulname = "";
    private String doctorFullname = "";
    private String appointmentDate = "";
    private String appointmentStatus = "";
    private String doctorExpense = "";
    private String patientSenderExpense = "";
    private String paymentAmount = "";
    private String paymentDiscount = "";
    private String paymentDescription = "";
    private String priceListPrice = "";
    private String description = "";

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDiscount() {
        return paymentDiscount;
    }

    public void setPaymentDiscount(String paymentDiscount) {
        this.paymentDiscount = paymentDiscount;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPriceListPrice() {
        return priceListPrice;
    }

    public void setPriceListPrice(String priceListPrice) {
        this.priceListPrice = priceListPrice;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getFkModuleId() {
        return fkModuleId;
    }

    public void setFkModuleId(String fkModuleId) {
        this.fkModuleId = fkModuleId;
    }

    public String getFkPriceListId() {
        return fkPriceListId;
    }

    public void setFkPriceListId(String fkPriceListId) {
        this.fkPriceListId = fkPriceListId;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPatientFulname() {
        return patientFulname;
    }

    public void setPatientFulname(String patientFulname) {
        this.patientFulname = patientFulname;
    }

    public String getPatientSenderFulname() {
        return patientSenderFulname;
    }

    public void setPatientSenderFulname(String patientSenderFulname) {
        this.patientSenderFulname = patientSenderFulname;
    }

    public String getDoctorFullname() {
        return doctorFullname;
    }

    public void setDoctorFullname(String doctorFullname) {
        this.doctorFullname = doctorFullname;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getDoctorExpense() {
        return doctorExpense;
    }

    public void setDoctorExpense(String doctorExpense) {
        this.doctorExpense = doctorExpense;
    }

    public String getPatientSenderExpense() {
        return patientSenderExpense;
    }

    public void setPatientSenderExpense(String patientSenderExpense) {
        this.patientSenderExpense = patientSenderExpense;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
