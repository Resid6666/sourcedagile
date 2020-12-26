/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.util.IOUtils;
import org.jose4j.lang.JoseException;
import utility.CallDispatcher;
import utility.Carrier;
import utility.FileUpload;
import utility.QDate;
import utility.QException;
import utility.SessionManager;
import utility.sqlgenerator.DBConnection;
import utility.sqlgenerator.QLogger;
//import smssender.Config;
//import smssender.SMSSender;

/**
 * REST Web Service
 *
 * @author nikli
 */
@Path("get")
public class GetServices {

    @Context
    private UriInfo context;

//    SMSSender smsSender = new SMSSender();
    /**
     * Creates a new instance of AndroidWS
     */
    public GetServices() {
        //Config.loadConfig();
    }

    /**
     * Retrieves representation of an instance of com.andr.GetServices
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path(value = "testService")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response doGetRequest() {
        return Response.status(Response.Status.OK).entity("asldfkj aslkdfj ;aslkfj;alskdf j").build();
    }

    @GET
    @Path(value = "sendsms")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response sendSms() {

        return Response.status(Response.Status.OK).entity("SMS gonderildi").build();
    }

    @GET
    @Path(value = "file1/{msg}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response downloadFile() {

        return Response.status(Response.Status.OK).entity("SMS gonderildi").build();
    }

    @GET
    @Path(value = "payment")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response payment() {
        return Response.status(Response.Status.OK).entity("sorgu gonderildi").build();
    }

    @GET
    @Path(value = "sms/{msg}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response sms(@PathParam("msg") String msg) {

        return Response.status(Response.Status.OK).entity(msg + " gonderildi").build();
    }

    @POST
    @Path(value = "postsms")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response postsms(String msg) {
        return Response.status(Response.Status.OK).entity(msg + " gonderildi").build();
    }

    /**
     * PUT method for updating or creating an instance of GetServices
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    @GET
    @Path("/filesd/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public StreamingOutput helloWorldZips(@PathParam(value = "filename")
            final String filename) throws Exception {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                BufferedOutputStream bus = new BufferedOutputStream(arg0);

                try {
                    //ByteArrayInputStream reader = (ByteArrayInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream();     
                    //byte[] input = new byte[2048];  
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname = getFullname(filename);//new FileUpload().getUploadPath() + filename;
                    System.out.println("fname=" + fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

//            @Override
//            public void write(OutputStream output) throws IOException, WebApplicationException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
        };
    }

    @GET
    @Path("/files/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response helloWorldZipsDirect(@PathParam(value = "filename")
            final String filename) throws Exception {

        StreamingOutput sout = new StreamingOutput() {
 
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                System.out.println("fname= will be upload started");
                BufferedOutputStream bus = new BufferedOutputStream(arg0);
                System.out.println("fname= will be uploaded");
                try {
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname =  getFullname(filename);//new FileUpload().getUploadPath() + filename;
                    System.out.println("fname=" + fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        return Response.ok(sout).header("Content-Disposition", "inline;filename=\"" + filename + "")
                .header("Content-Type", "image/png").build();
    }

    @GET
    @Path("/filev/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadVideoStream(@PathParam(value = "filename")
            final String filename) throws Exception {

        StreamingOutput sout = new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                BufferedOutputStream bus = new BufferedOutputStream(arg0);

                try {
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname = getFullname(filename);// new FileUpload().getUploadPath() + filename;
                    System.out.println("fname=" + fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        return Response.ok(sout).header("Content-Disposition", "inline;filename=\"" + filename + "")
                .header("Content-Type", "video/mp4").build();
    }

    @GET
    @Path("/filepdf/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadPdfStream(@PathParam(value = "filename")
            final String filename) throws Exception {

        StreamingOutput sout = new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                BufferedOutputStream bus = new BufferedOutputStream(arg0);

                try {
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname =  getFullname(filename);//new FileUpload().getUploadPath() + filename;
                    System.out.println("fname=" + fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        return Response.ok(sout).header("Content-Disposition", "inline;filename=\"" + filename + "")
                .header("Content-Type", "application/pdf").build();
    }

    @GET
    @Path("/filem/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public StreamingOutput helloWorldZip(@PathParam(value = "filename")
            final String filename) throws Exception {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                BufferedOutputStream bus = new BufferedOutputStream(arg0);
                try {
                    //ByteArrayInputStream reader = (ByteArrayInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream();     
                    //byte[] input = new byte[2048];  
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname =  getFullname(filename);//new FileUpload().getUploadPath() + filename;
                    System.out.println("fname=" + fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

//            @Override
//            public void write(OutputStream output) throws IOException, WebApplicationException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
        };
    }

    @GET
    @Path("/filed/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public StreamingOutput helloWorldZipNew(@PathParam(value = "filename")
            final String filename) throws Exception {
        return new StreamingOutput() {
            @Override
            public void write(OutputStream arg0) throws IOException, WebApplicationException {
                // TODO Auto-generated method stub
                BufferedOutputStream bus = new BufferedOutputStream(arg0);
                try {
                    //ByteArrayInputStream reader = (ByteArrayInputStream) Thread.currentThread().getContextClassLoader().getResourceAsStream();     
                    //byte[] input = new byte[2048];  
                    java.net.URL uri = Thread.currentThread().getContextClassLoader().getResource("");
                    String fname =  getFullname(filename);//new FileUpload().getUploadPathPrivate() + filename;
//                    System.out.println("fname="+fname);
                    File file = new File(fname);
//                    File file = new File("D:\\"+filename);
                    FileInputStream fizip = new FileInputStream(file);
                    byte[] buffer2 = IOUtils.toByteArray(fizip);
                    bus.write(buffer2);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

//            @Override
//            public void write(OutputStream output) throws IOException, WebApplicationException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
        };
    }

    @GET
    @Path(value = "file/{filename}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void resendEmail(@Context HttpHeaders headers, @Suspended
            final AsyncResponse asyncResponse, @PathParam(value = "filename")
            final String userId, final String json) {
        executorService.submit(() -> {

            Carrier carrier = new Carrier();
            carrier.fromJson(json);
            carrier.setValue("userId", userId);

            String jsonNew = "";
            try {
                jsonNew = carrier.getJson();
            } catch (QException ex) {

            }

            asyncResponse.resume(doCallDispatcherNoToken(headers, "serviceCrResendEmail", jsonNew));

            // asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    private String getFullname(String filename) {
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

    private Response doCallDispatcherNoToken(@Context HttpHeaders headers,
            @PathParam("servicename") String servicename, String json) {
        String srv[] = new String[]{"serviceCrSignupPersonal",
            "serviceCrSignupCompany",
            "serviceCrSignupCompany",
            "serviceCrActivateCompany",
            "serviceCrGetMessageText",
            "serviceCrGetModuleList4ComboNali",
            "serviceCrIsCompanyDomainAvailable",
            "serviceCrIsFieldValid",
            "serviceCrGetTermPage",
            "serviceCrIsPersonalUsernameExist",
            "serviceCrGetListItemList4ComboNali",
            "serviceCrGetLabel"};

        if (!ArrayUtils.contains(srv, servicename)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            long serviceTime = System.currentTimeMillis();
            System.out.println("json srv->" + json);
            System.out.println("Start-" + SessionManager.getCurrentUsername()
                    + ":" + QDate.getCurrentDate() + ":" + QDate.getCurrentTime() + ":" + servicename);

//            System.out.println("json->" + json);
            Carrier c = new Carrier();
            c.setServiceName(servicename);
//            System.out.println("json->" + json);
            c.fromJson(json);
            Response res = CallDispatcher.callService(c);
            System.out.println(servicename + " | - Service Executing Time: " + (System.currentTimeMillis() - serviceTime));
            conn.commit();
            //conn.close();

//            if (servicename.equals("serviceCrSignupPersonal")
//                    || servicename.equals("serviceCrSignupCompany")) {
//                return Response.temporaryRedirect(new URI("/apd/activation.html?id=" + c.getValue(EntityCrUser.ID).toString())).build();
//            }
//
//            if (servicename.equals("serviceCrActivateCompany")) {
//                return Response.temporaryRedirect(new URI("/apd/activated.html")).build();
//            }
            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, "loginException", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, json, ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

    private final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    @GET
    @Path(value = "srv/{servicename}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callDispatcher(@Suspended
            final AsyncResponse asyncResponse, @PathParam(value = "servicename")
            final String servicename, final String json) {
        Future<?> submit = executorService.submit(() -> {
            try {
                asyncResponse.resume(doCallDispatcher(servicename, json));

            } catch (IllegalAccessException ex) {
                Logger.getLogger(GetServices.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GetServices.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GetServices.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (InvocationTargetException ex) {
                Logger.getLogger(GetServices.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (Exception ex) {
                Logger.getLogger(GetServices.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private Response doCallDispatcher(@PathParam("servicename") String servicename, String json) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, Exception {
//        System.out.println(servicename);
//        System.out.println(json);

        Carrier c = new Carrier();
        c.setServiceName(servicename);
        c.fromJson(json);
        return CallDispatcher.callService(c);
    }
}
