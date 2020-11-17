package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBacklogDescription extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String DESCRIPTION = "description";
    private String description = "";
    public static String COMMENT_TYPE = "commentType";
    private String commentType = "";

    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String COLORED_TYPE = "coloredType";
    private String coloredType = "";

    public static String FK_RELATED_API_ID = "fkRelatedApiId";
    private String fkRelatedApiId = "";
    public static String SHORT_DESC_FORR_API = "shortDescForApi";
    private String shortDescForApi = "";
    public static String FK_RELATED_SC_ID = "fkRelatedScId";
    private String fkRelatedScId = "";

    public String getFkRelatedScId() {
        return fkRelatedScId;
    }

    public void setFkRelatedScId(String fkRelatedScId) {
        this.fkRelatedScId = fkRelatedScId;
    }
 
    public String getFkRelatedApiId() {
        return fkRelatedApiId;
    }

    public void setFkRelatedApiId(String fkRelatedApiId) {
        this.fkRelatedApiId = fkRelatedApiId;
    }

    public String getShortDescForApi() {
        return shortDescForApi;
    }

    public void setShortDescForApi(String shortDescForApi) {
        this.shortDescForApi = shortDescForApi;
    }

    public String getColoredType() {
        return coloredType;
    }

    public void setColoredType(String coloredType) {
        this.coloredType = coloredType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

}
