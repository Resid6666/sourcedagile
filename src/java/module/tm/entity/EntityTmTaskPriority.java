package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskPriority extends CoreEntity {

    public static String PRIORITY_CODE = "priorityCode";
    public static String PRIORITY_NAME = "priorityName";
    public static String DESCRIPTION = "description";

    private String priorityCode = "";
    private String priorityName = "";
    private String description = "";

    public String getPriorityCode() {
        return priorityCode;
    }

    public void setPriorityCode(String priorityCode) {
        this.priorityCode = priorityCode;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
