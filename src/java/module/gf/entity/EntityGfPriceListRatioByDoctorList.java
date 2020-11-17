package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPriceListRatioByDoctorList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_DOCTOR_USER_ID = "fkDoctorUserId";
    public static String DOCTOR_FULNAME = "doctorFulname";
    public static String FK_PRICE_LIST_ID = "fkPriceListId";
    public static String PRICE_LIST_NAME = "priceListName";
    public static String PRICE_LIST_AMOUNT = "priceListAmount";
    public static String PRICE_LIST_CURRENCY = "priceListCurrency";
    public static String RATIO_OF_DOCTOR = "ratioOfDoctor";
    public static String DESCRIPTION = "description";

    private String fkDoctorUserId = "";
    private String doctorFulname = "";
    private String fkPriceListId = "";
    private String priceListName = "";
    private String priceListAmount = "";
    private String priceListCurrency = "";
    private String ratioOfDoctor = "";
    private String description = "";

    public String getFkDoctorUserId() {
        return fkDoctorUserId;
    }

    public void setFkDoctorUserId(String fkDoctorUserId) {
        this.fkDoctorUserId = fkDoctorUserId;
    }

    public String getDoctorFulname() {
        return doctorFulname;
    }

    public void setDoctorFulname(String doctorFulname) {
        this.doctorFulname = doctorFulname;
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
