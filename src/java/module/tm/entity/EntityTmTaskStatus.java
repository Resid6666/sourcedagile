package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskStatus extends CoreEntity {

    public static String STATUS_CODE = "statusCode";
    public static String STATUS_NAME = "statusName";
    public static String DESCRIPTION = "description";

    private String statusCode = "";
    private String statusName = "";
    private String description = "";

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
