package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskLabelListForTask extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String NAME = "name";
    private String name = "";
    public static String BACKLOG_COUNT = "backlogCount";
    private String backlogCount = "";
    public static String IS_MENU = "isMenu";
    private String isMenu = "";
    public static String COLOR = "color";
    private String color = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBacklogCount() {
        return backlogCount;
    }

    public void setBacklogCount(String backlogCount) {
        this.backlogCount = backlogCount;
    }

    public String getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    

}
