package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmDatabase extends CoreEntity {

    public static String DB_NAME = "dbName";
    private String dbName = "";
    public static String DB_DESC = "dbDesc";
    private String dbDesc = "";

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbDesc() {
        return dbDesc;
    }

    public void setDbDesc(String dbDesc) {
        this.dbDesc = dbDesc;
    }

    
    
    
}
