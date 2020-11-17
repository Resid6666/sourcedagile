package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPriceListRatioByPatientSender extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_PATIENT_SENDER_ID = "fkPatientSenderId";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String RATIO_OF_PATIENT_SENDER = "ratioOfPatientSender";
    public static String DESCRIPTION = "description";

    private String fkPatientSenderId = "";
    private String fkPriceListId = "";
    private String ratioOfPatientSender = "";
    private String description = "";

    public String getFkPatientSenderId() {
        return fkPatientSenderId;
    }

    public void setFkPatientSenderId(String fkPatientSenderId) {
        this.fkPatientSenderId = fkPatientSenderId;
    }

    public String getFkPriceListId() {
        return fkPriceListId;
    }

    public void setFkPriceListId(String fkPriceListId) {
        this.fkPriceListId = fkPriceListId;
    }

    public String getRatioOfPatientSender() {
        return ratioOfPatientSender;
    }

    public void setRatioOfPatientSender(String ratioOfPatientSender) {
        this.ratioOfPatientSender = ratioOfPatientSender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
