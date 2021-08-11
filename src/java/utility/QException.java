package utility;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import utility.sqlgenerator.QLogger;
import utility.sqlgenerator.SQLConnection;

public class QException extends Exception {

    private Exception excp;
    private String message;

    public QException(Exception e) {
        this.excp = e;
        this.message = "";
        QLogger.saveExceptions(e.getClass().toString(), e.getClass().toString(), message);
//         System.out.println(message);
    }

    public QException(String sourceClassname, Exception e) {
        this.excp = e;
        this.message = e.getMessage();
        QLogger.saveExceptions(sourceClassname, sourceClassname, message);
//         System.out.println(message);
    }

    public QException(String sourceClassname, String methodname, QException e) {
        this.excp = e;
        try {
            this.message = e.getExceptionMessage();
        } catch (Exception ex) {
            this.message = e.getMessage();
        }
        QLogger.saveExceptions(sourceClassname, methodname, e.getMessage());

    }

    public QException(String sourceClassname, String methodname, Exception e) {
        this.excp = e;

        try {
//            this.message =  ((QException) e).getLocalMessage();
            this.message = e.getMessage();
            e.printStackTrace();
        } catch (Exception ex) {
            this.message = e.getMessage();
        }

//        QLogger.saveExceptions(sourceClassname, methodname, this.message);
//          System.out.println(message);
    }

    public QException(String sourceClassname, String methodname, Throwable e) {
        this.excp = (Exception) e;

        try {
            this.message = ((QException) e).getLocalMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            this.message = e.getMessage();
        }

//        QLogger.saveExceptions(sourceClassname, methodname, this.message);
//          System.out.println(message);
    }

    public QException(String message) {
        this.message = message;
    }

    public QException() {
        this.excp = null;
        this.message = "";
    }

    public String getExceptionMessage() {
        return excp.getMessage();
    }

    public String getLocalMessage() {
        return message;
    }

    public void setException(Exception e) {
        this.excp = e;
    }

    public Throwable getException() {
        return excp;
    }

}
