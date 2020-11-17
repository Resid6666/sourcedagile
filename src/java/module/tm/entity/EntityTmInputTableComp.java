package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmInputTableComp extends CoreEntity {

    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String TABLE_NAME = "tableName";
    private String tableName = "";
    public static String TABLE_CSS = "tableCss";
    private String tableCss = "";
    public static String HEADER_CSS = "headerCss";
    private String headerCss = "";
    public static String BODY_CSS = "bodyCss";
    private String bodyCss = "";
    public static String FOOTER_CSS = "footerCss";
    private String footerCss = "";
    public static String TR_CSS = "trCss";
    private String trCss = "";
    public static String TD_CSS = "tdCss";
    private String tdCss = "";
    public static String HAS_NO = "hasNo";
    private String hasNo = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String ROW_COUNT = "rowCount";
    private String rowCount = "";
    public static String READ_CONTENT = "readContent";
    private String readContent = "";

    public String getReadContent() {
        return readContent;
    }

    public void setReadContent(String readContent) {
        this.readContent = readContent;
    }

    
    
    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCss() {
        return tableCss;
    }

    public void setTableCss(String tableCss) {
        this.tableCss = tableCss;
    }

    public String getHeaderCss() {
        return headerCss;
    }

    public void setHeaderCss(String headerCss) {
        this.headerCss = headerCss;
    }

    public String getBodyCss() {
        return bodyCss;
    }

    public void setBodyCss(String bodyCss) {
        this.bodyCss = bodyCss;
    }

    public String getFooterCss() {
        return footerCss;
    }

    public void setFooterCss(String footerCss) {
        this.footerCss = footerCss;
    }

    public String getTrCss() {
        return trCss;
    }

    public void setTrCss(String trCss) {
        this.trCss = trCss;
    }

    public String getTdCss() {
        return tdCss;
    }

    public void setTdCss(String tdCss) {
        this.tdCss = tdCss;
    }

    public String getHasNo() {
        return hasNo;
    }

    public void setHasNo(String hasNo) {
        this.hasNo = hasNo;
    }

}
