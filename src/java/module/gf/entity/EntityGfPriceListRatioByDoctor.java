package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPriceListRatioByDoctor extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DOCTOR_USER_ID = "fkDoctorUserId";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String RATIO_OF_DOCTOR = "ratioOfDoctor";
    public static String DESCRIPTION = "description";

    
 
    private String fkDoctorUserId = "";
    private String fkPriceListId = "";
    private String ratioOfDoctor = "";
    private String description = "";

    public String getFkDoctorUserId() {
        return fkDoctorUserId;
    }

    public void setFkDoctorUserId(String fkDoctorUserId) {
        this.fkDoctorUserId = fkDoctorUserId;
    }

    public String getFkPriceListId() {
        return fkPriceListId;
    }

    public void setFkPriceListId(String fkPriceListId) {
        this.fkPriceListId = fkPriceListId;
    }

    public String getRatioOfDoctor() {
        return ratioOfDoctor;
    }

    public void setRatioOfDoctor(String ratioOfDoctor) {
        this.ratioOfDoctor = ratioOfDoctor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
