/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author resid
 */
public class EntityTmLaneTable extends CoreEntity{
    
    public static String COLUMN_COUNT = "columnCount";
    private String columnCount = "";
    
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";
    
    public static String LANE_NAME = "laneName";
    private String laneName =  "";
    
    public static String ORDER_NO = "orderNo";
    private String orderNo =  "";
    
    public static String PROCESS_ID = "processId";
    private String processId =  "";

    public String getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(String columnCount) {
        this.columnCount = columnCount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
