package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmApiRelSetting extends CoreEntity {

    public static String FK_OWNER_ID = "fkOwnerId";
    private String fkOwnerId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String REL_TYPE = "relType";
    private String relType = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String REQUEST_BODY = "requestBody";
    private String requestBody = "";
    public static String RESPONSE_BODY = "responseBody";
    private String responseBody = "";
    public static String ERROR_BODY = "errorBody";
    private String errorBody = "";
    public static String COOKEE = "cookee";
    private String cookee = "";
    public static String TOKEN = "token";
    private String token = "";
    public static String QUERY_PARAM = "queryParam";
    private String queryParam = "";
    public static String EXTRA_PARAM = "extraParam";
    private String extraParam = "";

    public String getFkOwnerId() {
        return fkOwnerId;
    }

    public void setFkOwnerId(String fkOwnerId) {
        this.fkOwnerId = fkOwnerId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }

    public String getCookee() {
        return cookee;
    }

    public void setCookee(String cookee) {
        this.cookee = cookee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }
    
    
    

}
