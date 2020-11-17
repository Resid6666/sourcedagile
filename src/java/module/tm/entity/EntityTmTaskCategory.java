package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskCategory extends CoreEntity {

    public static String CATEGORY_CODE = "categoryCode";
    public static String CATEGORY_NAME = "categoryName";
    public static String DESCRIPTION = "description";

    private String categoryCode = "";
    private String categoryName = "";
    private String description = "";

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
