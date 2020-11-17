package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmBcSectionRel extends CoreEntity {

    public static String FK_BC_SECTION_ID = "fkBcSectionId";
    private String fkBcSectionId = "";
    public static String SECTION_BODY = "sectionBody";
    private String sectionBody = "";
    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";

    public String getFkBcSectionId() {
        return fkBcSectionId;
    }

    public void setFkBcSectionId(String fkBcSectionId) {
        this.fkBcSectionId = fkBcSectionId;
    }

    public String getSectionBody() {
        return sectionBody;
    }

    public void setSectionBody(String sectionBody) {
        this.sectionBody = sectionBody;
    }

    public String getFkBcId() {
        return fkBcId;
    }

    public void setFkBcId(String fkBcId) {
        this.fkBcId = fkBcId;
    }

    
    
}
