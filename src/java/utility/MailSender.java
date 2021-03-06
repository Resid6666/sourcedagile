/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.LogManager;
import resources.config.Config;

/**
 *
 * @author nikli
 */
public class MailSender {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static void send(String toEmail, String subject, String text) {
        try {
            final String sendgridApiKey = Config.getProperty(Config.MAIL_SENDER_SENDGRID_API_KEY);
//            final String sendgridSender = System.getenv("SENDGRID_SENDER");
            Email from = new Email("backlog <no-reply@backlog.com>");
            Email to = new Email(toEmail);
            Content content = new Content("text/html", text);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendgridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendMailInThread(String recipient, String subject, String body, String toCC) {

        ExecutorService emailExecutor = Executors.newCachedThreadPool();

        // from you getSalesUserData() method
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                boolean f = false;
                int idx = 1;
                do {
                    try {
                        idx++;
                        sendMail(recipient, subject, body, toCC);
                        f = false;
                    } catch (Exception e) {
                        f = idx <= 10;
                        e.printStackTrace();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            f = false;
                            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (idx > 10) {
                        break;
                    }
                } while (f);
            }
        });
    }

    public static void sendMail(String recipient, String subject, String body, String toCC) {
        sendMail(recipient, subject, body, toCC, "");
    }

    public static void sendMail(String recipient, String subject, String body, String toCC, String toBC) {
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", Config.getProperty("mail.TLS"));
        props.put("mail.smtp.host", Config.getProperty("mail.smtp.host"));
        props.put("mail.smtp.user", Config.getProperty("mail.username"));
        props.put("mail.smtp.password", Config.getProperty("mail.password"));
        props.put("mail.smtp.port", Config.getProperty("mail.port"));
        props.put("mail.smtp.auth", Config.getProperty("mail.AUTH"));

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(Config.getProperty("mail.sender"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            if (toCC.trim().length() > 0) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(toCC));
            }
            if (toBC.trim().length() > 0) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(toBC));
            }
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport transport = session.getTransport(Config.getProperty("mail.transport.type"));
            transport.connect(Config.getProperty("mail.smtp.host"), Config.getProperty("mail.from"), Config.getProperty("mail.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public static void sendMailWithAttach(String recipient, String subject, String body, String filename, String toCC, String toBC) {
        Properties props = System.getProperties();

        props.put("mail.smtp.starttls.enable", Config.getProperty("mail.TLS"));
        props.put("mail.smtp.host", Config.getProperty("mail.smtp.host"));
        props.put("mail.smtp.user", Config.getProperty("mail.username"));
        props.put("mail.smtp.password", Config.getProperty("mail.password"));
        props.put("mail.smtp.port", Config.getProperty("mail.port"));
        props.put("mail.smtp.auth", Config.getProperty("mail.AUTH"));

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(Config.getProperty("mail.sender"));

            String[] recipientList = recipient.split(",");
            for (String cc : recipientList) {
                if (cc.trim().length() == 0) {
                    continue;
                }
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(cc.trim()));
            }

            String[] toCCList = toCC.split(",");
            for (String cc : toCCList) {
                if (cc.trim().length() == 0) {
                    continue;
                }
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc.trim()));
            }

            String[] toBCList = toBC.split(",");
            for (String bc : toBCList) {
                if (bc.trim().length() == 0) {
                    continue;
                }
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bc.trim()));
            }

            message.setSubject(subject);

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(body, "text/html; charset=utf-8");
            
            emailContent.addBodyPart(textBodyPart);

            String[] filenameList = filename.split(",");
            for (String cc : filenameList) {
                if (cc.trim().length() == 0) {
                    continue;
                }

                MimeBodyPart jpgBodyPart = new MimeBodyPart();
                try {
                    String fnameFull = getFullname(cc);
                    System.out.println("attached file name=" + fnameFull);
                    jpgBodyPart.attachFile(fnameFull);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                emailContent.addBodyPart(jpgBodyPart);

            }

            

            message.setContent(emailContent, "text/html; charset=utf-8");

            // message.setContent(body, "text/html; charset=utf-8");
            Transport transport = session.getTransport(Config.getProperty("mail.transport.type"));
            transport.connect(Config.getProperty("mail.smtp.host"), Config.getProperty("mail.from"), Config.getProperty("mail.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private static String getFullname(String filename) {
        String res = "";
        try {
            String domainzad = SessionManager.getCurrentDomain();
            String downloadPath = FileUpload.getUploadPath() + domainzad + "/";
            String fname = downloadPath + filename;
            File f = new File(fname);
            if (f.exists() && !f.isDirectory()) {
                return fname;
            } else {
                String domainzad1 = "commonzad";
                String downloadPath1 = FileUpload.getUploadPath() + domainzad1 + "/";
                String fname1 = downloadPath1 + filename;
                File f1 = new File(fname1);
                if (f1.exists() && !f1.isDirectory()) {
                    return fname1;
                }
            }
        } catch (Exception e) {
            String domainzad1 = "commonzad";
            String downloadPath1 = FileUpload.getUploadPath() + domainzad1 + "/";
            String fname1 = downloadPath1 + filename;
            File f1 = new File(fname1);
            if (f1.exists() && !f1.isDirectory()) {
                return fname1;
            }
        }
        return res;
    }

    public static void sendMailGuarantee(String recipient, String subject, String body, String toCC) throws Exception {
        System.out.println("mail-qaranti-0");

//        Properties props = System.getProperties();
//        System.out.println("mail-qaranti-1");
//        props.put("mail.smtp.starttls.enable", Config.getProperty("mail.TLS"));
//        props.put("mail.smtp.host", Config.getProperty("mail.smtp.host"));
//        props.put("mail.smtp.user", Config.getProperty("mail.username"));
//        props.put("mail.smtp.password", Config.getProperty("mail.password"));
//        props.put("mail.smtp.port", Config.getProperty("mail.port"));
//        props.put("mail.smtp.auth", Config.getProperty("mail.AUTH"));
//
//        System.out.println("mail-qaranti-2");
//
//        Session session = Session.getDefaultInstance(props);
//        MimeMessage message = new MimeMessage(session);
//
//        message.setFrom(Config.getProperty("mail.sender"));
//        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
//        if (toCC.trim().length() > 0) {
//            message.addRecipient(Message.RecipientType.CC, new InternetAddress(toCC));
//        }
//        message.setSubject(subject);
//        message.setContent(body, "text/html; charset=utf-8");
//        Transport transport = session.getTransport(Config.getProperty("mail.transport.type"));
//        System.out.println("mail-qaranti-3");
//
//        transport.connect(Config.getProperty("mail.smtp.host"), Config.getProperty("mail.from"), Config.getProperty("mail.password"));
//        System.out.println("mail-qaranti-4");
//
//        transport.sendMessage(message, message.getAllRecipients());
//        System.out.println("mail-qaranti-5");
//
//        transport.close();
//
//        System.out.println("mail-qaranti-6");
    }

//    public static String getSubjectOfRegistration(){
//        String
//    }
}
