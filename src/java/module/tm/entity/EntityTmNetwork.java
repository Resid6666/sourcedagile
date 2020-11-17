package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmNetwork extends CoreEntity {


    public static String ID="id";
public static String STATUS="status";
public static String INSERT_DATE="insertDate";
public static String MODIFICATION_DATE="modificationDate";
public static String NETWORK_NAME="networkName";
public static String NETWORK_STATUS="networkStatus";
public static String CREATED_BY="createdBy";
public static String CREATED_DATE="createdDate";
public static String CREATED_TIME="createdTime";
public static String DESCRIPTION="description";

private String networkName="";
private String networkStatus="";
private String createdBy="";
private String createdDate="";
private String createdTime="";
private String description="";

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
