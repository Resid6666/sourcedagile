package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmCommentFile extends CoreEntity {

    public static String FK_COMMENT_ID = "fkCommentId";
    private String fkCommentId = "";
    public static String FILE_NAME = "fileName";
    private String fileName = "";
    public static String UPLOAD_DATE = "uploadDate";
    private String uploadDate = "";
    public static String UPLOAD_TIME = "uploadTime";
    private String uploadTime = "";
    public static String FILE_TITLE = "fileTitle";
    private String fileTitle = "";
    public static String FILE_DESCRIPTION = "fileDescription";
    private String fileDescription = "";

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }
    
    

    public String getFkCommentId() {
        return fkCommentId;
    }

    public void setFkCommentId(String fkCommentId) {
        this.fkCommentId = fkCommentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

}