package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProject extends CoreEntity {

    public static String PROJECT_NAME = "projectName";
    public static String START_DATE = "startDate";
    public static String END_DATE = "endDate";
    public static String FK_NETWORK_ID = "fkNetworkId";
    public static String PURPOSE = "purpose";
    public static String DESCRIPTION = "description";
    public static String PROJECT_CODE = "projectCode";
    private String projectCode = "";
    public static String SHOW_IN_MENU = "showInMenu";
    private String showInMenu = "";
    public static String FK_TRIGGER_BACKLOG_ID = "fkTriggerBacklogId";
    private String fkTriggerBacklogId = "";
    public static String MENU_ICON = "menuIcon";
    private String menuIcon = "";

    public String getShowInMenu() {
        return showInMenu;
    }

    public void setShowInMenu(String showInMenu) {
        this.showInMenu = showInMenu;
    }

    public String getFkTriggerBacklogId() {
        return fkTriggerBacklogId;
    }

    public void setFkTriggerBacklogId(String fkTriggerBacklogId) {
        this.fkTriggerBacklogId = fkTriggerBacklogId;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    
    
    private String projectName = "";
    private String startDate = "";
    private String endDate = "";
    private String fkNetworkId = "";
    private String purpose = "";
    private String description = "";

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFkNetworkId() {
        return fkNetworkId;
    }

    public void setFkNetworkId(String fkNetworkId) {
        this.fkNetworkId = fkNetworkId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
