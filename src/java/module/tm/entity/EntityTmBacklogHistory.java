package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogHistory extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String HISTORY_TYPE = "historyType";
    private String historyType = "";
    public static String HISTORY_DATE = "historyDate";
    private String historyDate = "";
    public static String HISTORY_TIME = "historyTime";
    private String historyTime = "";
    public static String HISTORY_TELLER_ID = "historyTellerId";
    private String historyTellerId = "";
    public static String HISTORY_BODY = "historyBody";
    private String historyBody = "";
    public static String RELATION_ID = "relationId";
    private String relationId = "";
    public static String PARAM_1 = "param1";
    private String param1 = "";
    public static String PARAM_2 = "param2";
    private String param2 = "";
    public static String PARAM_3 = "param3";
    private String param3 = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }

    public String getHistoryTellerId() {
        return historyTellerId;
    }

    public void setHistoryTellerId(String historyTellerId) {
        this.historyTellerId = historyTellerId;
    }

    public String getHistoryBody() {
        return historyBody;
    }

    public void setHistoryBody(String historyBody) {
        this.historyBody = historyBody;
    }

}
