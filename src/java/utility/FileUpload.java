package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import label.CoreLabel;
import org.apache.commons.codec.binary.Base64;
import resources.config.Config;
import utility.sqlgenerator.IdGenerator;

/**
 *
 * @author candide
 */
public class FileUpload {

    public synchronized String uploadImage(String base64String, String ext, String filename) throws QException {
//        System.out.println("1");
        String userId = "111111";
        String domainzad = "commonzad";
        try {
            userId = SessionManager.getCurrentUserId();
            domainzad = SessionManager.getCurrentDomain();
        } catch (Exception e) {
        }
        String downloadPath = this.getUploadPath() + "/" + domainzad  + "/";
        File theDir = new File(downloadPath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        
        String id = IdGenerator.getIdType3();
        long num = Long.parseLong(id) + 214521;
        String hexId = QUtility.convertDecimalToHex(num);
        String fileName = filename + "_" + hexId + "." + ext.replaceAll("^\"|\"$", "");
        FileOutputStream imageOutFile;
        try {
            // Decode String using Base64 Class
            byte[] imageByteArray = Base64.decodeBase64(base64String);

            // Write Image into File system - Make sure you update the path
            
            
            String fname = downloadPath +   fileName;
            imageOutFile = new FileOutputStream(fname);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
            return fileName;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return QDate.getCurrentDate() + QDate.getCurrentTime() + "temp_file.png";
    }

    public static String getUploadPath() {
        String file = "";
        try {
//            GeneralProperties prop = new GeneralProperties();
//            file = prop.coreFullPath() + Config.getUploadPath();
            file = Config.getUploadPath();
//            System.out.println("file uploaded------->" + file);

            return file;
        } catch (Exception ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public String getUploadPathPrivate() {
        String file = "";
        try {
//            GeneralProperties prop = new GeneralProperties();
//            file = prop.coreFullPath() + Config.getUploadPath();
            file = Config.getUploadPath() + "private/";
//            System.out.println("file uploaded------->" + file);

            return file;
        } catch (Exception ex) {
            Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        String currentDT = sdf.format(cal.getTime());
        currentDT += QDate.getCurrentMillisecond();
        currentDT = currentDT.replaceAll("/", "");
        currentDT = currentDT.replaceAll(" ", "");
        currentDT = currentDT.replaceAll(":", "");

        return currentDT;
    }
}
