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
public class EntityTmFigureInside extends CoreEntity{
    
    public static String FIGURE_COLOR = "figureColor";
    private String figureColor = "";
    
    public static String FIGURE_NAME = "figureName";
    private String figureName = "";
    
    public static String FIGURE_TEXT = "figureText";
    private String figureText = "";   
    
    public static String ORDER_NO = "";
    private String orderNo = "";
    
    public static String COLUMN_NO = "";
    private String columnNo = "";
    
    public static String FK_LINE_ID = "";
    private String fkLineId = "";
    
    public static String FONT_SIZE_NEW = "";
    private String fontSizeNew = "";
    
    public static String STORY_CARD_ID = "";
    private String storyCardId = "";
    
    public static String CREATED_BY = "";
    private String createdBy = "";

    public String getFigureColor() {
        return figureColor;
    }

    public void setFigureColor(String figureColor) {
        this.figureColor = figureColor;
    }

    public String getFigureName() {
        return figureName;
    }

    public void setFigureName(String figureName) {
        this.figureName = figureName;
    }

    public String getFigureText() {
        return figureText;
    }

    public void setFigureText(String figureText) {
        this.figureText = figureText;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(String columnNo) {
        this.columnNo = columnNo;
    }

    public String getFkLineId() {
        return fkLineId;
    }

    public void setFkLineId(String fkLineId) {
        this.fkLineId = fkLineId;
    }

    public String getFontSizeNew() {
        return fontSizeNew;
    }

    public void setFontSizeNew(String fontSizeNew) {
        this.fontSizeNew = fontSizeNew;
    }

    public String getStoryCardId() {
        return storyCardId;
    }

    public void setStoryCardId(String storyCardId) {
        this.storyCardId = storyCardId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    
    
}
