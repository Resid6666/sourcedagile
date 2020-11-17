package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmActivityLaneFigure extends CoreEntity {

    public static String FK_FIGURE_ID = "fkFigureId";
    private String fkFigureId = "";
    public static String FK_LANE_ID = "fkLaneId";
    private String fkLaneId = "";
    public static String FK__SC_BACKLOG_ID = "fkScBacklogId";
    private String fkScBacklogId = "";
    public static String FK_SC_PROJECT_ID = "fkScProjectId";
    private String fkScProjectId = "";
    public static String GENERAL_CSS = "generalCss";
    private String generalCss = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";

    public String getFkFigureId() {
        return fkFigureId;
    }

    public void setFkFigureId(String fkFigureId) {
        this.fkFigureId = fkFigureId;
    }

    public String getFkLaneId() {
        return fkLaneId;
    }

    public void setFkLaneId(String fkLaneId) {
        this.fkLaneId = fkLaneId;
    }

    public String getFkScBacklogId() {
        return fkScBacklogId;
    }

    public void setFkScBacklogId(String fkScBacklogId) {
        this.fkScBacklogId = fkScBacklogId;
    }

    public String getFkScProjectId() {
        return fkScProjectId;
    }

    public void setFkScProjectId(String fkScProjectId) {
        this.fkScProjectId = fkScProjectId;
    }

    public String getGeneralCss() {
        return generalCss;
    }

    public void setGeneralCss(String generalCss) {
        this.generalCss = generalCss;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
