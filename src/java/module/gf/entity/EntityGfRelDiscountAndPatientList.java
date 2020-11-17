package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfRelDiscountAndPatientList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DISCOUNT_ID = "fkDiscountId";
    public static String DISCOUNT_NAME = "discountName";
    public static String DISCOUNT_AMOUNT = "discountAmount";
    public static String VALID_DATE = "validDate";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String PATIENT_FULNAME = "patientFulname";
    public static String PATIENT_ID = "patientId";
    public static String PATIENT_BIRTH_DATE = "patientBirthDate";
    public static String PATIENT_MOBILE_1 = "patientMobile1";
    public static String DESCRIPTION = "description";

    private String fkDiscountId = "";
    private String discountName = "";
    private String discountAmount = "";
    private String validDate = "";
    private String fkPatientId = "";
    private String patientFulname = "";
    private String patientId = "";
    private String patientBirthDate = "";
    private String patientMobile1 = "";
    private String description = "";

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getPatientFulname() {
        return patientFulname;
    }

    public void setPatientFulname(String patientFulname) {
        this.patientFulname = patientFulname;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientMobile1() {
        return patientMobile1;
    }

    public void setPatientMobile1(String patientMobile1) {
        this.patientMobile1 = patientMobile1;
    }

    public String getFkDiscountId() {
        return fkDiscountId;
    }

    public void setFkDiscountId(String fkDiscountId) {
        this.fkDiscountId = fkDiscountId;
    }

    public String getFkPatientId() {
        return fkPatientId;
    }

    public void setFkPatientId(String fkPatientId) {
        this.fkPatientId = fkPatientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
