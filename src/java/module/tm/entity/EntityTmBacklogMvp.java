package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogMvp extends CoreEntity {

    public static String FK_BACKLOG_ID = "fkBacklogId";
    public static String ACTION_TYPE = "actionType";
    public static String ACTION_NATURE = "actionNature";
    public static String DB_NAME = "dbName";
    public static String DB_ID = "dbId";
    public static String TABLE_ID = "tableId";
    public static String TABLE_NAME = "tableName";
    public static String API_ID = "apiId";
    public static String API_NAME = "apiName";

    public String fkBacklogId = "";
    public String actionType = "";
    public String actionNature = "";
    public String dbName = "";
    public String dbId = "";
    public String tableId = "";
    public String tableName = "";
    public String apiId = "";
    public String apiName = "";

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getActionNature() {
        return actionNature;
    }

    public void setActionNature(String actionNature) {
        this.actionNature = actionNature;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
    
    
}
