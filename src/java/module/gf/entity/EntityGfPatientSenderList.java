package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfPatientSenderList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String IS_LOCAL_DOCTOR = "isLocalDoctor";
    public static String FK_DOCTOR_USER_ID = "fkDoctorUserId";
    public static String SENDER_FULNAME = "senderFulname";
    public static String DESCRIPTION = "description";
    public static String ADDRESS = "address";
    public static String SENDER_FULNAME_FULL = "senderFulnameFull";
    private String senderFulnameFull = "";

    private String address = "";
    private String isLocalDoctor = "";
    private String fkDoctorUserId = "";
    private String senderFulname = "";
    private String description = "";

    public String getSenderFulnameFull() {
        return senderFulnameFull;
    }

    public void setSenderFulnameFull(String senderFulnameFull) {
        this.senderFulnameFull = senderFulnameFull;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsLocalDoctor() {
        return isLocalDoctor;
    }

    public void setIsLocalDoctor(String isLocalDoctor) {
        this.isLocalDoctor = isLocalDoctor;
    }

    public String getFkDoctorUserId() {
        return fkDoctorUserId;
    }

    public void setFkDoctorUserId(String fkDoctorUserId) {
        this.fkDoctorUserId = fkDoctorUserId;
    }

    public String getSenderFulname() {
        return senderFulname;
    }

    public void setSenderFulname(String senderFulname) {
        this.senderFulname = senderFulname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
