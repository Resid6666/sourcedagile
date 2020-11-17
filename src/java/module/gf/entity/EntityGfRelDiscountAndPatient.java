package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfRelDiscountAndPatient extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DISCOUNT_ID = "fkDiscountId";
    public static String FK_PATIENT_ID = "fkPatientId";
    public static String DESCRIPTION = "description";

    private String fkDiscountId = "";
    private String fkPatientId = "";
    private String description = "";

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
