package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPriceListRatioByPatientSenderList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_PATIENT_SENDER_ID = "fkPatientSenderId";
    public static String PATIENT_SENDER_FULNAME = "patientSenderFulname";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String PRICE_LIST_NAME = "priceListName";
    public static String PRICE_LIST_AMOUNT = "priceListAmount";
    public static String PRICE_LIST_CURRENCY = "priceListCurrency";
    public static String RATIO_OF_PATIENT_SENDER = "ratioOfPatientSender";
    public static String DESCRIPTION = "description";

    private String fkPatientSenderId = "";
    private String patientSenderFulname = "";
    private String fkPriceListId = "";
    private String priceListName = "";
    private String priceListAmount = "";
    private String priceListCurrency = "";
    private String ratioOfPatientSender = "";
    private String description = "";

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

    public String getFkPriceListId() {
        return fkPriceListId;
    }

    public void setFkPriceListId(String fkPriceListId) {
        this.fkPriceListId = fkPriceListId;
    }

    public String getPriceListName() {
        return priceListName;
    }

    public void setPriceListName(String priceListName) {
        this.priceListName = priceListName;
    }

    public String getPriceListAmount() {
        return priceListAmount;
    }

    public void setPriceListAmount(String priceListAmount) {
        this.priceListAmount = priceListAmount;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
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
