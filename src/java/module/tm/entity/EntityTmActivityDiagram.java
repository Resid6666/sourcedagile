//COMMENT BY RESID
package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmActivityDiagram extends CoreEntity {

    public static String DIAGRAM_NAME = "diagramName";
    private String diagramName = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_ACTIVITY_GROUP_ID = "fkActivityGroupId";
    private String fkActivityGroupId = "";
    public static String DESCRIPTION = "description";
    private String description = "";

    public String getDiagramName() {
        return diagramName;
    }

    public void setDiagramName(String diagramName) {
        this.diagramName = diagramName;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkActivityGroupId() {
        return fkActivityGroupId;
    }

    public void setFkActivityGroupId(String fkActivityGroupId) {
        this.fkActivityGroupId = fkActivityGroupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
