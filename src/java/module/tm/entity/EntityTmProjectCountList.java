package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProjectCountList extends CoreEntity {

    public static String PROJECT_NAME = "projectName";
    private String projectName = "";
    public static String OVERAL_COUNT = "overalCount";
    private String overalCount = "";
    public static String NEW_COUNT = "newCount";
    private String newCount = "";
    public static String ONGOING_COUNT = "ongoingCount";
    private String ongoingCount = "";
    public static String CLOSED_COUNT = "closedCount";
    private String closedCount = "";
    public static String TICKET_COUNT = "ticketCount";
    private String ticketCount = "";
    public static String SOURCED_COUNT = "sourcedCount";
    private String sourcedCount = "";
    public static String BOUND_COUNT = "boundCount";
    private String boundCount = "";
    public static String INITIAL_COUNT = "initialCount";
    private String initialCount = "";

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOveralCount() {
        return overalCount;
    }

    public void setOveralCount(String overalCount) {
        this.overalCount = overalCount;
    }

    public String getNewCount() {
        return newCount;
    }

    public void setNewCount(String newCount) {
        this.newCount = newCount;
    }

    public String getOngoingCount() {
        return ongoingCount;
    }

    public void setOngoingCount(String ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

    public String getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(String closedCount) {
        this.closedCount = closedCount;
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(String ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getSourcedCount() {
        return sourcedCount;
    }

    public void setSourcedCount(String sourcedCount) {
        this.sourcedCount = sourcedCount;
    }

    public String getBoundCount() {
        return boundCount;
    }

    public void setBoundCount(String boundCount) {
        this.boundCount = boundCount;
    }

    public String getInitialCount() {
        return initialCount;
    }

    public void setInitialCount(String initialCount) {
        this.initialCount = initialCount;
    }

}
