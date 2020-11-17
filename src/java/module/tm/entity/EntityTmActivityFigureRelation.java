package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmActivityFigureRelation extends CoreEntity {

    public static String FK_FROM_FIGURE_ID = "fkFromFigureId";
    private String fkFromFigureId = "";
    public static String FK_TO_FIGURE_ID = "fkToFigureId";
    private String fkToFigureId = "";
    public static String RELATION_NAME = "relationName";
    private String relationName = "";
    public static String RELATION_COLOR = "relationColor";
    private String relationColor = "";

    public String getFkFromFigureId() {
        return fkFromFigureId;
    }

    public void setFkFromFigureId(String fkFromFigureId) {
        this.fkFromFigureId = fkFromFigureId;
    }

    public String getFkToFigureId() {
        return fkToFigureId;
    }

    public void setFkToFigureId(String fkToFigureId) {
        this.fkToFigureId = fkToFigureId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationColor() {
        return relationColor;
    }

    public void setRelationColor(String relationColor) {
        this.relationColor = relationColor;
    }

    
    
}
