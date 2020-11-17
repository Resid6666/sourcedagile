package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmTaskFile extends CoreEntity {

    public static String FK_TASK_ID = "fkTaskId";
    public static String FK_COMMENT_ID = "fkCommentId";
    public static String FILE_URL = "fileUrl";
    public static String DESCRIPTION = "description";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String IS_PINNED = "isPinned";
    private String isPinned = "";

    private String fkTaskId = "";
    private String fkCommentId = "";
    private String fileUrl = "";
    private String description = "";

    public String getIsPinned() {
        return isPinned;
    }

    public void setIsPinned(String isPinned) {
        this.isPinned = isPinned;
    }
    
    

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkTaskId() {
        return fkTaskId;
    }

    public void setFkTaskId(String fkTaskId) {
        this.fkTaskId = fkTaskId;
    }

    public String getFkCommentId() {
        return fkCommentId;
    }

    public void setFkCommentId(String fkCommentId) {
        this.fkCommentId = fkCommentId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
