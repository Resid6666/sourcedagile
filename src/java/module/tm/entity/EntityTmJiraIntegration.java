package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmJiraIntegration extends CoreEntity {

    public static String ATLASSSION_ID = "atlassionId";
    private String atlassionId = "";
    public static String USERNAME = "username";
    private String username = "";
    public static String PASSWORD = "password";
    private String password = "";
    public static String VERSION = "version";
    private String version = "";
    public static String PARAM_1 = "param1";
    private String param1 = "";
    public static String PARAM_2 = "param2";
    private String param2 = "";
    public static String PARAM_3 = "param3";
    private String param3 = "";
    public static String PARAM_4 = "param4 ";
    private String param4 = "";
    public static String DESCRIPTION = "description  ";
    private String description = "";

    public String getAtlassionId() {
        return atlassionId;
    }

    public void setAtlassionId(String atlassionId) {
        this.atlassionId = atlassionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
