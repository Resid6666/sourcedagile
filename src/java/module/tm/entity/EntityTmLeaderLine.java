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
public class EntityTmLeaderLine extends CoreEntity {
    
    public static String COLOR = "color";
    private String color = "";
    
    public static String FK_PROCESS_ID = "fkProcessId";
    private String fkProcessId = "";
    
    public static String FROM_ID = "fromId";
    private String fromId = "";
    
    public static String LINE_TYPE = "lineType";
    private String lineType = "";
    
    public static String TEXT = "text";
    private String text = "";
    
    public static String TO_ID = "toId";
    private String toId = "";
    
    public static String CREATED_BY = "created_by";
    private String created_by = "";

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFkProcessId() {
        return fkProcessId;
    }

    public void setFkProcessId(String fkProcessId) {
        this.fkProcessId = fkProcessId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    
    
    
}
