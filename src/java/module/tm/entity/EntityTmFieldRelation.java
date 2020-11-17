package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmFieldRelation extends CoreEntity {

    public static String FK_DB_ID = "fkDbId";
    private String fkDbId = "";
    public static String FROM_FIELD_ID = "fromFieldId";
    private String fromFieldId = "";
    public static String TO_FIELD_ID = "toFieldId";
    private String toFieldId = "";
    public static String REL_TYPE = "relType";
    private String relType = "";
    public static String DESCRIPTION = "description";
    private String description = "";

    public String getFkDbId() {
        return fkDbId;
    }

    public void setFkDbId(String fkDbId) {
        this.fkDbId = fkDbId;
    }

    public String getFromFieldId() {
        return fromFieldId;
    }

    public void setFromFieldId(String fromFieldId) {
        this.fromFieldId = fromFieldId;
    }

    public String getToFieldId() {
        return toFieldId;
    }

    public void setToFieldId(String toFieldId) {
        this.toFieldId = toFieldId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
