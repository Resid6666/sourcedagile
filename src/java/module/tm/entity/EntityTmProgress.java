package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProgress extends CoreEntity {

    public static String PROGRESS_CODE = "progressCode";
    public static String PROGRESS_NAME = "progressName";
    public static String DESCRIPTION = "description";

    private String progressCode = "";
    private String progressName = "";
    private String description = "";

    public String getProgressCode() {
        return progressCode;
    }

    public void setProgressCode(String progressCode) {
        this.progressCode = progressCode;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
