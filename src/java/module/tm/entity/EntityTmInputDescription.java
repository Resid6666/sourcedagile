package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmInputDescription extends CoreEntity {

    public static String FK_INPUT_ID = "fkInputId";
    private String fkInputId = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String COLORED = "colored";
    private String colored = "";

    public String getColored() {
        return colored;
    }

    public void setColored(String colored) {
        this.colored = colored;
    }

    
    
    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkInputId() {
        return fkInputId;
    }

    public void setFkInputId(String fkInputId) {
        this.fkInputId = fkInputId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
