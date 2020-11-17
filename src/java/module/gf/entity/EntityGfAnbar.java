package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfAnbar extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String ANBAR_NAME = "anbarName";
    public static String ANBAR_CODE = "anbarCode";
    public static String DESCRIPTION = "description";
    public static String ANBAR_TYPE = "anbarType";

    private String anbarName = "";
    private String anbarCode = "";
    private String description = "";
    private String anbarType = "";

    public String getAnbarName() {
        return anbarName;
    }

    public void setAnbarName(String anbarName) {
        this.anbarName = anbarName;
    }

    public String getAnbarCode() {
        return anbarCode;
    }

    public void setAnbarCode(String anbarCode) {
        this.anbarCode = anbarCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnbarType() {
        return anbarType;
    }

    public void setAnbarType(String anbarType) {
        this.anbarType = anbarType;
    }

}
