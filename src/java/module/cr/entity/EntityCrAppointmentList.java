package module.cr.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityCrAppointmentList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DOCTOR_USER_ID = "fkDoctorUserId";
    public static String DOCTOR_FULLNAME = "doctorFullname";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String APPOINTMENT_DATE = "appointmentDate";
    public static String APPOINTMENT_TIME_1 = "appointmentTime1";
    public static String APPOINTMENT_TIME_2 = "appointmentTime2";
    public static String APPOINTMENT_STATUS = "appointmentStatus";
    public static String DESCRIPTION = "description";
    public static String PATIENT_ID = "patientId";
    public static String PATIENT_IMAGE = "patientImage";
    public static String PATIENT_NAME = "patientName";
    public static String PATIENT_SURNAME = "patientSurname";
    public static String PATIENT_MIDDLE_NAME = "patientMiddleName";
    public static String PATIENT_BIRTH_DATE = "patientBirthDate";
    public static String PATIENT_BIRTH_PLACE = "patientBirthPlace";
    public static String INSPECTION_CODE = "inspectionCode";
    public static String FK_OWNER_USER_ID = "fkOwnerUserId";
    public static String PAYMENT_STATUS = "paymentStatus";
    public static String SEX = "sex";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String FK_MODULE_ID = "fkModuleId";
    public static String SESSION_NO = "sessionNo";
    public static String FK_PATIENT_SENDER_ID = "fkPatientSenderId";
    private String fkPatientSenderId = "";
    public static String PATIENT_SENDER_FULNAME = "patientSenderFulname";
    private String patientSenderFulname = "";
    public static String MOBILE_1 = "mobile1";
    private String mobile1 = "";
    public static String FK_EXECUTOR_USER_ID = "fkExecutorUserId";
    private String fkExecutorUserId = "";
    public static String EXECUTOR_FULNAME = "executorFulname";
    private String executorFulname = "";
    public static String FK_DISCOUNT_ID = "fkDiscountId";
    private String fkDiscountId = "";
    public static String DISCOUNT_NAME = "discountName";
    private String discountName = "";
    public static String PURPOSE = "purpose";
    private String purpose = "";

    private String sessionNo = "";
    private String fkModuleId = "";
    private String fkPriceListId = "";
    private String id = "";
    private String status = "";
    private String insertDate = "";
    private String modificationDate = "";
    private String fkDoctorUserId = "";
    private String doctorFullname = "";
    private String fkPatientId = "";
    private String appointmentDate = "";
    private String appointmentTime1 = "";
    private String appointmentTime2 = "";
    private String appointmentStatus = "";
    private String description = "";
    private String patientId = "";
    private String patientImage = "";
    private String patientName = "";
    private String patientSurname = "";
    private String patientMiddleName = "";
    private String patientBirthDate = "";
    private String patientBirthPlace = "";
    private String inspectionCode = "";
    private String fkOwnerUserId = "";
    private String paymentStatus = "";
    private String sex = "";

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getFkDiscountId() {
        return fkDiscountId;
    }

    public void setFkDiscountId(String fkDiscountId) {
        this.fkDiscountId = fkDiscountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getFkExecutorUserId() {
        return fkExecutorUserId;
    }

    public void setFkExecutorUserId(String fkExecutorUserId) {
        this.fkExecutorUserId = fkExecutorUserId;
    }

    public String getExecutorFulname() {
        return executorFulname;
    }

    public void setExecutorFulname(String executorFulname) {
        this.executorFulname = executorFulname;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
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

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getFkDoctorUserId() {
        return fkDoctorUserId;
    }

    public void setFkDoctorUserId(String fkDoctorUserId) {
        this.fkDoctorUserId = fkDoctorUserId;
    }

    public String getDoctorFullname() {
        return doctorFullname;
    }

    public void setDoctorFullname(String doctorFullname) {
        this.doctorFullname = doctorFullname;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime1() {
        return appointmentTime1;
    }

    public void setAppointmentTime1(String appointmentTime1) {
        this.appointmentTime1 = appointmentTime1;
    }

    public String getAppointmentTime2() {
        return appointmentTime2;
    }

    public void setAppointmentTime2(String appointmentTime2) {
        this.appointmentTime2 = appointmentTime2;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    public String getPatientMiddleName() {
        return patientMiddleName;
    }

    public void setPatientMiddleName(String patientMiddleName) {
        this.patientMiddleName = patientMiddleName;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientBirthPlace() {
        return patientBirthPlace;
    }

    public void setPatientBirthPlace(String patientBirthPlace) {
        this.patientBirthPlace = patientBirthPlace;
    }

    public String getInspectionCode() {
        return inspectionCode;
    }

    public void setInspectionCode(String inspectionCode) {
        this.inspectionCode = inspectionCode;
    }

    public String getFkOwnerUserId() {
        return fkOwnerUserId;
    }

    public void setFkOwnerUserId(String fkOwnerUserId) {
        this.fkOwnerUserId = fkOwnerUserId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
