package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmDocument extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String DOCUMENT_NAME = "documentName";
    private String documentName = "";
    public static String DOCUMENT_BODY = "documentBody";
    private String documentBody = "";
    public static String MARGIN_TOP = "marginTop";
    private String marginTop = "";
    public static String MARGIN_RIGHT = "marginRight";
    private String marginRight = "";
    public static String MARGIN_BOTTOM = "marginBottom";
    private String marginBottom = "";
    public static String MARGIN_LEFT = "marginLeft";
    private String marginLeft = "";
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    public static String CREATE_DATE = "createDate";
    private String createDate = "";
    public static String CREATE_TIME = "createTime";
    private String createTime = "";
    public static String UPDATED_BY = "updatedBy";
    private String updatedBy = "";
    public static String UPDATED_DATE = "updatedDate";
    private String updatedDate = "";
    public static String UPDATED_TIME = "updatedTime";
    private String updatedTime = "";
    public static String PAGE_SIZE = "pageSize";
    private String pageSize = "";
    public static String AUTO_SAVE_INTERVAL = "autoSaveInterval";
    private String autoSaveInterval = "";

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentBody() {
        return documentBody;
    }

    public void setDocumentBody(String documentBody) {
        this.documentBody = documentBody;
    }

    public String getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    public String getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    public String getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    public String getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getAutoSaveInterval() {
        return autoSaveInterval;
    }

    public void setAutoSaveInterval(String autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    
    
    
}
