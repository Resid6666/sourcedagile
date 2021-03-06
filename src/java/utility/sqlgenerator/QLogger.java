package utility.sqlgenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.GeneralProperties;
import utility.SessionManager;

public class QLogger {

    private static Logger sqlLogger = LogManager.getLogger("sqlLogger");
    private static Logger serviceLogger = LogManager.getLogger("serviceLogger");
    private static Logger logger = LogManager.getLogger();

    public static void saveExceptions(String classname, String methodname, String log) {
        String username = SessionManager.getCurrentUsername();//SessionManager.getUserByThreadId(Thread.currentThread().getId());
        Date dt = new Date();
        logger.error("-----------------------------------------------------------------------------------------");
        logger.error("##");
        logger.error(" -->" + dt + "::" + username + "::");
        logger.error(" " + classname + ":: " + methodname + "::");
        logger.error(log);
        logger.error("-----------------------------------------------------------------------------------------");
    }

    public static void saveSMSLog(String[] numbers, String message, String output) {
        String username = SessionManager.getCurrentUsername();//SessionManager.getUserByThreadId(Thread.currentThread().getId());
        Date dt = new Date();
        String numbersLine = "";

        for (String number : numbers) {
            numbersLine = numbersLine + number + ",";
        }

        logger.info("-----------------------------------------------------------------------------------------");
        logger.info("##");
        logger.info(" --&gt" + dt + "::" + username + "::");
        logger.info(" numbers->" + numbersLine);
        logger.info("::");
        logger.info(" message->" + message + "::");
        logger.info(" output-->" + output + ";");
        logger.info("-----------------------------------------------------------------------------------------");
    }

    public static void saveLogToFile(String log) {
        saveLogToFile(log, new ArrayList());
    }

    public static void saveLogToFile(String log, ArrayList values) {
        try {
            String line = "";
            for (int idx = 1; idx <= values.size(); idx++) {
                line = line + idx + "->" + values.get(idx - 1) + ";";
            }
            saveLogToFile(log, line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveLogToFile(String log, String values) {
        String username = SessionManager.getCurrentUsername();
        Date dt = new Date();
        try {
            sqlLogger.debug("##");
            sqlLogger.debug(" -->" + dt + "::" + username + "::");
            sqlLogger.debug(" " + log + "::");
            sqlLogger.debug(" " + values);

//            System.out.println("query 1 >>> "+log);
//             System.out.println("query values >>> "+values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveServiceLog(String service) {

//        try { 
//            GeneralProperties prop = new GeneralProperties();
//            String filename = prop.getWorkingDir() + "../log/services.txt";
//
//            BufferedReader br = new BufferedReader(new FileReader(filename));
//            String ln = ""; 
//            boolean f = true;
//            while ((ln = br.readLine()) != null) {
////                System.out.println("ln=" + ln);
////                System.out.println("service=" + service);
//                if (ln.equals(service)) {
//                    f = false;
//                    break;
//                }
//
//            }
//
//            if (f) {
//                BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
//                bw.newLine();
//                bw.append(service);
//                bw.close();
//            }
//
//            br.close();
//
//        } catch (UnsupportedEncodingException ex) {
//            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void savePageLog(String page) {
//        try {
//             
//            GeneralProperties prop = new GeneralProperties();
//            String filename = prop.getWorkingDir() + "../log/pages.txt";
//
//            BufferedReader br = new BufferedReader(new FileReader(filename));
//            String ln = "";
//            boolean f = true;
//            while ((ln = br.readLine()) != null) {
////                System.out.println("ln=" + ln);
////                System.out.println("page=" + page);
//                if (ln.equals(page)) {
//                    f = false;
//                    break;
//                }
//
//            }
//
//            if (f) {
//                BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
//                bw.newLine();
//                bw.append(page);
//                bw.close();
//            }
//
//            br.close();
//
//        } catch (UnsupportedEncodingException ex) {
//            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public static void saveLabelLog(String page) {
        try {

            GeneralProperties prop = new GeneralProperties();
            String filename = prop.getWorkingDir() + "../log/label.txt";

            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String ln = "";
                boolean f = true;
                while ((ln = br.readLine()) != null) {
//                System.out.println("ln=" + ln);
//                System.out.println("page=" + page);
                    if (ln.trim().startsWith(page.trim())) {
                        f = false;
                        break;
                    }
                }
                if (f) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
                    bw.newLine();
                    bw.append(page + "\t" + SQLGenerator.addUnderscoreToFieldName(page) + "\t" + SessionManager.getCurrentLang());
                    bw.close();
                }
            }

        } catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(QLogger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
