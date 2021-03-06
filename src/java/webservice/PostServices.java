/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import auth.SessionHandler;
import module.cr.entity.EntityCrUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import utility.CallDispatcher;

import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import module.cr.CrModel;
import module.cr.entity.EntityCrCompany;
import module.tm.entity.EntityTmBacklog;
import org.apache.commons.lang.ArrayUtils;
import org.jose4j.lang.JoseException;
import resources.config.Config;
import utility.Carrier;
import utility.FileUpload;
import utility.ImageResizer;
import utility.QDate;
import utility.QException;
import utility.QUtility;
import utility.SessionManager;
import utility.UserController;
import utility.sqlgenerator.DBConnection;
import utility.sqlgenerator.EntityManager;
import utility.sqlgenerator.QLogger;

//import smssender.Config;
//import smssender.SMSSender;

/**
 * REST Web Service
 *
 * @author nikli
 */
@Path("post")
public class PostServices {

    @Context
    private UriInfo context;
    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();
//    SMSSender smsSender = new SMSSender();

    /**
     * Creates a new instance of AndroidWS
     */
    public PostServices() {
        //Config.loadConfig();
    }

    @POST
    @Path(value = "login")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void doPostRequestForLogin(@Suspended final AsyncResponse asyncResponse, @Context final HttpHeaders headers, final String jsonData) {
        executorService.submit(() -> {
            asyncResponse.resume(doDoPostRequestForLogin(headers, jsonData));
        });
    }

    @POST
    @Path("zdupload/{domain}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response doPostRequestUploadWithoutAuth(@Context HttpHeaders headers,
                                                          @PathParam(value = "domain") final String domain,
                                                          String jsonData) throws Exception {

        if (domain.trim().length() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Connection conn = null;

        try {
            conn = new DBConnection().getConnection();

            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain(domain);
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "ENG");
//            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
            SessionManager.setDomain(Thread.currentThread().getId(), "commonzad");
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            Carrier carrier = new Carrier();
            carrier.fromJson(jsonData);

            String resize = carrier.getValue("resize").toString();
            String scaleWidth = carrier.getValue("scaleWidth").toString();
            String scaleHeight = carrier.getValue("scaleHeight").toString();
            String file_type = carrier.getValue("file_type").toString();
            String file_extension = carrier.getValue("file_extension").toString();
            String fileName = carrier.getValue("file_name").toString();
//        System.out.println("fileName-" + fileName);
            String msg = "";
            String type = "";

            if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("image")) {
                type = "image";
            } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("video")) {
                type = "video";
            } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("audio")) {
                type = "audio";
            } else {
                type = "general";
            }

            if (type.trim().length() > 0) {
                String img_type[] = Config.getProperty("upload.type." + type).split(",");
                boolean f = false;
                if (type.equals("general")) {
                    f = true;
                } else {
                    for (String t : img_type) {
                        if (file_extension.trim().toLowerCase(Locale.ENGLISH).equals(t.trim().toLowerCase(Locale.ENGLISH))) {
                            f = true;
                        }
                    }
                }
                if (f) {
                    FileUpload fileUpload = new FileUpload();
//                System.out.println("file context"+ carrier.getValue("file_base_64").toString());
                    fileName = fileUpload.uploadImage(carrier.getValue("file_base_64").toString(),
                            file_extension.trim().toLowerCase(Locale.ENGLISH), fileName);

                    System.out.println("---------------------------------------------");
                    System.out.println("type=" + type);
                    System.out.println("resize=" + resize);
                    System.out.println("fname=" + fileUpload.getUploadPath() + fileName);
                    System.out.println("-------------------------------------------------");

                    if (type.equals("image") && resize.equals("1")) {
                        String fname = fileUpload.getUploadPath() + fileName;

                        try {
                            ImageResizer.resize(fname, fname, Integer.parseInt(scaleWidth), Integer.parseInt(scaleHeight));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (String t : img_type) {
                        msg += t + ", ";
                    }
                    msg = msg.substring(0, msg.length() - 2);
                }
            } else {
                msg = "Incorrent File Type";
            }

            Carrier c = new Carrier();

            c.setValue("msg", msg);
            c.setValue("uploaded_file_name", fileName);

            SessionManager.cleanSessionThread();
            return Response.status(Response.Status.OK)
                    .entity(c.getJson()).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    @POST
    @Path("uploadd/{domain}")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response doPostRequestUploadd(@Context HttpHeaders headers,
                                                String jsonData) throws Exception {

        Connection conn = null;
//
        try {
            conn = new DBConnection().getConnection();

            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain("backlog");
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Carrier carrier = new Carrier();
            carrier.fromJson(jsonData);

            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "-1");
            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
//        SessionManager.setDomain(Thread.currentThread().getId(), "apd_backlog");
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            String resize = carrier.getValue("resize").toString();
            String scaleWidth = carrier.getValue("scaleWidth").toString();
            String scaleHeight = carrier.getValue("scaleHeight").toString();
            String file_type = carrier.getValue("file_type").toString();
            String file_extension = carrier.getValue("file_extension").toString();
            String fileName = carrier.getValue("file_name").toString();
//        System.out.println("fileName-" + fileName);
            String msg = "";
            String type = "";

            if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("image")) {
                type = "image";
            } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("video")) {
                type = "video";
            } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("audio")) {
                type = "audio";
            } else {
                type = "general";
            }

            if (type.trim().length() > 0) {
                String img_type[] = Config.getProperty("upload.type." + type).split(",");
                boolean f = false;
                if (type.equals("general")) {
                    f = true;
                } else {
                    for (String t : img_type) {
                        if (file_extension.trim().toLowerCase(Locale.ENGLISH).equals(t.trim().toLowerCase(Locale.ENGLISH))) {
                            f = true;
                        }
                    }
                }
                if (f) {
                    FileUpload fileUpload = new FileUpload();
//                System.out.println("file context"+ carrier.getValue("file_base_64").toString());
                    fileName = fileUpload.uploadImage(carrier.getValue("file_base_64").toString(),
                            file_extension.trim().toLowerCase(Locale.ENGLISH), fileName);

                    System.out.println("---------------------------------------------");
                    System.out.println("type=" + type);
                    System.out.println("resize=" + resize);
                    System.out.println("fname=" + fileUpload.getUploadPath() + fileName);
                    System.out.println("-------------------------------------------------");

                    if (type.equals("image") && resize.equals("1")) {
                        String fname = fileUpload.getUploadPath() + fileName;
                        String fname1 = fileUpload.getUploadPath() + "TEST_" + fileName;

                        try {
                            ImageResizer.resize(fname, fname, Integer.parseInt(scaleWidth), Integer.parseInt(scaleHeight));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    for (String t : img_type) {
                        msg += t + ", ";
                    }
                    msg = msg.substring(0, msg.length() - 2);
                }
            } else {
                msg = "Incorrent File Type";
            }

            Carrier c = new Carrier();

            c.setValue("msg", msg);
            c.setValue("uploaded_file_name", fileName);

            SessionManager.cleanSessionThread();

            return Response.status(Response.Status.OK)
                    .entity(c.getJson()).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
        }
    }

    @POST
    @Path("upload")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response doPostRequestUpload(@Context HttpHeaders headers, String jsonData) throws Exception {
        if (!SessionHandler.checkPermission(headers, jsonData)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Carrier carrier = new Carrier();
        carrier.fromJson(jsonData);
        String cs = SessionHandler.getTokenAsString(headers, jsonData);

        try {
            Cookie cookie = headers.getCookies().get("apdtok");
            cs = cookie.getValue();
        } catch (Exception e) {
            cs = carrier.getValue("cookie").toString().replace("apdtok=", "");
        }

        EntityCrUser user = null;

        user = SessionHandler.getTokenFromCookie(cs);

        SessionManager.setUserName(Thread.currentThread().getId(), user.getUsername());
        SessionManager.setLang(Thread.currentThread().getId(), user.selectLang());
        SessionManager.setDomain(Thread.currentThread().getId(), user.selectDomain());
        SessionManager.setUserId(Thread.currentThread().getId(), user.getId());
        SessionManager.setCompanyId(Thread.currentThread().getId(), user.selectCompanyId());

        String resize = carrier.getValue("resize").toString();
        String scaleWidth = carrier.getValue("scaleWidth").toString();
        String scaleHeight = carrier.getValue("scaleHeight").toString();
        String file_type = carrier.getValue("file_type").toString();
        String file_extension = carrier.getValue("file_extension").toString();
        String fileName = carrier.getValue("file_name").toString();
//        System.out.println("fileName-" + fileName);
        String msg = "";
        String type = "";

        if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("image")) {
            type = "image";
        } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("video")) {
            type = "video";
        } else if (file_type.trim().toLowerCase(Locale.ENGLISH).equals("audio")) {
            type = "audio";
        } else {
            type = "general";
        }

        if (type.trim().length() > 0) {
            String img_type[] = Config.getProperty("upload.type." + type).split(",");
            boolean f = false;
            if (type.equals("general")) {
                f = true;
            } else {
                for (String t : img_type) {
                    if (file_extension.trim().toLowerCase(Locale.ENGLISH).equals(t.trim().toLowerCase(Locale.ENGLISH))) {
                        f = true;
                    }
                }
            }
            if (f) {
                FileUpload fileUpload = new FileUpload();
//                System.out.println("file context"+ carrier.getValue("file_base_64").toString());
                fileName = fileUpload.uploadImage(carrier.getValue("file_base_64").toString(),
                        file_extension.trim().toLowerCase(Locale.ENGLISH), fileName);

                System.out.println("---------------------------------------------");
                System.out.println("type=" + type);
                System.out.println("resize=" + resize);
                System.out.println("fname=" + fileUpload.getUploadPath() + fileName);
                System.out.println("-------------------------------------------------");

                if (type.equals("image") && resize.equals("1")) {
                    String fname = fileUpload.getUploadPath() + fileName;
                    String fname1 = fileUpload.getUploadPath() + "TEST_" + fileName;

                    try {
                        ImageResizer.resize(fname, fname, Integer.parseInt(scaleWidth), Integer.parseInt(scaleHeight));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (String t : img_type) {
                    msg += t + ", ";
                }
                msg = msg.substring(0, msg.length() - 2);
            }
        } else {
            msg = "Incorrent File Type";
        }

        Carrier c = new Carrier();

        c.setValue("msg", msg);
        c.setValue("uploaded_file_name", fileName);

        return Response.status(Response.Status.OK)
                .entity(c.getJson()).build();
    }

    /**
     * PUT method for updating or creating an instance of GetServices
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    private Response doDoPostRequestForLogin(@Context HttpHeaders headers, String jsonData) {
        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);

            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            Carrier carrier = new Carrier();
            carrier.fromJson(jsonData);

            String usename = carrier.getValue("username").toString();
            String password = carrier.getValue("password").toString();
            String domain = carrier.getValue("domain").toString();
            String lang = carrier.getValue("lang").toString();

            lang = SessionHandler.isLangAvailable(lang) ? lang : "ENG";
            EntityCrUser user = SessionHandler.checkLogin(usename, password, domain);
            user.setLang(lang);
            user.setDomain(user.selectDbname());

            String token = SessionHandler.encryptUser(user);
            String fullname = user.getUserPersonName();
            String img = user.getUserImage();
            String id = "";
//           
            SessionManager.setUserName(Thread.currentThread().getId(), user.getUsername());
            SessionManager.setLang(Thread.currentThread().getId(), lang);
            SessionManager.setUserId(Thread.currentThread().getId(), user.getId());
            SessionManager.setCompanyId(Thread.currentThread().getId(), user.selectCompanyId());

            String entity = "{\"fullname\":\"" + fullname + "\",\"img\":\"" + img + "\",\"token\":\"" + token + "\"}";

//            System.out.println("filpagebody");
//            CrModel.fillPageBody();
            conn.commit();
            conn.close();

            //return Response.status(Response.Status.OK).cookie(new NewCookie("apdtok", token, "/", "", "comment", 2_592_000, true)).entity(entity).build();
            return Response.status(Response.Status.OK).cookie(new NewCookie("apdtok", token, "/", "", "comment", 2_592_000, false)).entity(entity).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            System.err.println("Username Or Password is incorrect!!-");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

    @POST
    @Compress
    @Path(value = "cb/{servicename}/{key}/{value}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callServiceAsCombobox(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                                      @PathParam(value = "servicename") final String servicename, @PathParam(value = "key") final String key,
                                      @PathParam(value = "value") final String value, final String json) {
        executorService.submit(() -> {
            String jsonNew = "";
            jsonNew += "{\"b\": {\n";
            jsonNew += "\"includedFields\":\"" + key + "," + value + "\",\n";
            jsonNew += "\"asc\":\"" + value + "\"\n";
            jsonNew += "}}";
//                System.out.println("servicename=" + servicename);
//                System.out.println("json of combo=" + jsonNew);
            asyncResponse.resume(doCallDispatcher(headers, servicename, jsonNew));
        });
    }

    @POST
    @Compress
    @Path(value = "li/{code}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callListItem(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                             @PathParam(value = "code") final String itemCode, final String json) {
        executorService.submit(() -> {
//            System.out.println("json->" + json);

            Carrier carrier = new Carrier();
            carrier.fromJson(json);
            carrier.setValue("itemCode", itemCode);
            carrier.setValue("asc", "itemValue");

            String jsonNew = "";
            try {
                jsonNew = carrier.getJson();
            } catch (QException ex) {

            }

//            jsonNew += "{\"b\": {";
//            jsonNew += "\"itemCode\":\"" + itemCode + "\",";
//            jsonNew += "\"asc\":\"itemValue\"";
//            jsonNew += "}}";
            String servicename = "serviceCrGetListItemByCode";
            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PostServices.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            asyncResponse.resume(doCallDispatcher(headers, servicename, jsonNew));
        });
    }

    @POST
    @Compress
    @Path(value = "nali/{code}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callNaListItem(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                               @PathParam(value = "code") final String itemCode, final String json) {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                String srv[] = new String[]{"sex",
                        "country",
                        "currency",
                        "timezone"
                };

                if (!ArrayUtils.contains(srv, "")) {
                    Response resp = Response.status(Response.Status.FORBIDDEN).build();
                    asyncResponse.resume(resp);
                }

//                System.out.println("json->" + json);
                Carrier carrier = new Carrier();
                carrier.fromJson(json);
                carrier.setValue("itemCode", itemCode);
                carrier.setValue("asc", "itemValue");

                String jsonNew = "";
                try {
                    jsonNew = carrier.getJson();
                } catch (QException ex) {

                }

                String servicename = "serviceCrGetListItemList4ComboNali";

                asyncResponse.resume(doCallDispatcherNoToken(headers, servicename, jsonNew));
            }
        });
    }

    @POST
    @Path(value = "srv/{servicename}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callDispatcher(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "servicename") final String servicename, final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    @POST
    @Path(value = "zdfn/{domain}/{function}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void runFunction(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                            @PathParam(value = "servicename") final String servicename,
                            @PathParam("domain") String domain,
                            @PathParam(value = "function") final String fname,
                            final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallFunction(headers, domain, fname, json));
        });
    }

    @POST
    @Path(value = "zdfna/{domain}/{function}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void runFunctionDirect(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                                  @PathParam(value = "servicename") final String servicename,
                                  @PathParam("domain") String domain,
                                  @PathParam(value = "function") final String fname,
                                  final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallFunctionDirect(headers, domain, fname, json));
        });
    }

    @POST
    @Path(value = "zd/{domain}/{api}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callApi(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                        @PathParam(value = "servicename") final String servicename,
                        @PathParam(value = "domain") final String domain,
                        @PathParam(value = "api") final String api,
                        final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallApi(headers, domain, api, json));
        });
    }

    @POST
    @Path(value = "cl/{domain}/{api}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callApiBackend(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                               @PathParam(value = "servicename") final String servicename,
                               @PathParam(value = "domain") final String domain,
                               @PathParam(value = "api") final String api,
                               final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallApiBackend(headers, domain, api, json));
        });
    }

    @POST
    @Path(value = "nasrv/{servicename}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callNaDispatcher(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "servicename") final String servicename, final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallDispatcherNoToken(headers, servicename, json));

            // asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    @POST
    @Path(value = "register")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void call4Register(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "servicename") final String servicename, final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallDispatcherNoToken4Register(headers, servicename, json));

            // asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    @POST
    @Path(value = "activation")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void call4Activation(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "servicename") final String servicename, final String json) {
        executorService.submit(() -> {
            asyncResponse.resume(doCallDispatcherNoToken4Activation(headers, servicename, json));

            // asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    @GET
    @Path(value = "signup/activate/{activationId}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void activateCompany(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "activationId") final String activationId, final String json) {
        executorService.submit(() -> {

            Carrier carrier = new Carrier();
            carrier.fromJson(json);
            carrier.setValue("activationId", activationId);

            String jsonNew = "";
            try {
                jsonNew = carrier.getJson();
            } catch (QException ex) {

            }

            asyncResponse.resume(doCallDispatcherNoToken(headers, "serviceCrActivateCompany", jsonNew));

            // asyncResponse.resume(doCallDispatcher(headers, servicename, json));
        });
    }

    @GET
    @Path(value = "signup/resend/{userId}")
    @Compress
    @Produces(value = MediaType.APPLICATION_JSON)
    public void resendEmail(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse, @PathParam(value = "userId") final String userId, final String json) {
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

    @POST
    @Compress
    @Path(value = "li/{code}/{sortbykey}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public void callListItemBySort(@Context HttpHeaders headers, @Suspended final AsyncResponse asyncResponse,
                                   @PathParam(value = "code") final String itemCode, @PathParam(value = "sortbykey") final String sortbykey, final String json) {
        executorService.submit(() -> {
            String jsonNew = "";
            jsonNew += "{\"b\": {";
            jsonNew += "\"itemCode\":\"" + itemCode + "\",";
            jsonNew += "\"asc\":\"itemKey\"";
            jsonNew += "}}";

            String servicename = "serviceCrGetListItemList";

            asyncResponse.resume(doCallDispatcher(headers, servicename, jsonNew));
        });
    }

    private Response doCallFunctionDirect(@Context HttpHeaders headers,
                                          @PathParam("domain") String domain,
                                          @PathParam("function") String fname,
                                          String json) {

        String jsonCore = "{\"kv\":{"
                + "\"pureJson\":'" + json + "'"
                + "}}";
        if (fname.trim().length() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Connection conn = null;
        try {

            String servicename = "serviceIoRunFunction";

            Carrier c = new Carrier();
            c.setServiceName(servicename);
            c.set("fnName", fname);
            c.fromJson(jsonCore);

            System.out.println("servicename:" + servicename + ", json=  " + jsonCore);
            QLogger.saveServiceLog(servicename);

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain(domain);
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "ENG");
            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            Response res = CallDispatcher.callServiceWithPureJson(c);

            conn.commit();
            //conn.close();

            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }

    }

    private Response doCallFunction(@Context HttpHeaders headers,
                                    @PathParam("domain") String domain,
                                    @PathParam("function") String fname,
                                    String json) {

        if (fname.trim().length() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Connection conn = null;
        try {

            String servicename = "serviceIoRunFunction";

            Carrier c = new Carrier();
            c.setServiceName(servicename);
            c.set("fnName", fname);
            c.fromJson(json);

//            System.out.println("ok 2 - 1"+"  "+servicename);
            QLogger.saveServiceLog(servicename);

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain(domain);
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "ENG");
            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            Response res = CallDispatcher.callService(c);
            conn.commit();
            //conn.close();

            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }

    }

    private Response doCallApi(@Context HttpHeaders headers,
                               @PathParam("domain") String domain,
                               @PathParam("api") String api, String json) {

        if (domain.trim().length() == 0 || api.length() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Connection conn = null;
        try {

            String servicename = "serviceIoCallApi";

            Carrier c = new Carrier();
            c.setServiceName(servicename);
            c.set("api", api);
            c.fromJson(json);

//            System.out.println("ok 2 - 1"+"  "+servicename);
            QLogger.saveServiceLog(servicename);

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain(domain);
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "ENG");
            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            Response res = CallDispatcher.callService(c);
            conn.commit();
            //conn.close();

            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }

    }

    private Response doCallApiBackend(@Context HttpHeaders headers,
                                      @PathParam("domain") String domain,
                                      @PathParam("api") String api, String json) {

        if (domain.trim().length() == 0 || api.trim().length() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Connection conn = null;
        try {

            String servicename = "serviceIoCallActionApi";

//            System.out.println("ok 2 - 1"+"  "+servicename);
            QLogger.saveServiceLog(servicename);

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
            SessionManager.setDomain(Thread.currentThread().getId(), "backlog");
            EntityCrCompany entComp = new EntityCrCompany();
            entComp.setCompanyDomain(domain);
            entComp.setEndLimit(0);
            EntityManager.select(entComp);

            if (entComp.getCompanyDb().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SessionManager.setUserName(Thread.currentThread().getId(), "-1");
            SessionManager.setLang(Thread.currentThread().getId(), "ENG");
            SessionManager.setDomain(Thread.currentThread().getId(), entComp.getCompanyDb());
            SessionManager.setUserId(Thread.currentThread().getId(), "-1");
            SessionManager.setCompanyId(Thread.currentThread().getId(), "-1");

            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setBacklogName(api);
            ent.setEndLimit(0);
            EntityManager.select(ent);

            if (ent.getId().trim().length() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            Carrier c = new Carrier();
            c.setServiceName(servicename);
            c.set("apiId", ent.getId());
            c.fromJson(json);

            Response res = CallDispatcher.callService(c);
            conn.commit();
            //conn.close();

            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }

    }

    private Response doCallDispatcher(@Context HttpHeaders headers,
                                      @PathParam("servicename") String servicename, String json) {

        if (!SessionHandler.checkPermission(headers, json)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        Connection conn = null;
        try {

            Carrier c = new Carrier();
            c.setServiceName(servicename);
            c.fromJson(json);
            String cs = SessionHandler.getTokenAsString(headers, json);

//            System.out.println("ok 2 - 1"+"  "+servicename);
            QLogger.saveServiceLog(servicename);

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            long serviceTime = System.currentTimeMillis();

            try {
                System.out.println("json srv->" + json);
                Cookie cookie = headers.getCookies().get("apdtok");
                cs = cookie.getValue();
            } catch (Exception e) {
                cs = c.getValue("cookie").toString().replace("apdtok=", "");
            }

            EntityCrUser user = null;
            user = SessionHandler.getTokenFromCookie(cs);
            SessionManager.setUserName(Thread.currentThread().getId(), user.getUsername());
            SessionManager.setLang(Thread.currentThread().getId(), user.selectLang());
            SessionManager.setDomain(Thread.currentThread().getId(), user.selectDomain());
            SessionManager.setUserId(Thread.currentThread().getId(), user.getId());
            SessionManager.setCompanyId(Thread.currentThread().getId(), user.selectCompanyId());

            if (!QUtility.hasPermission(servicename)) {
                System.out.println(">>> is forbidden>>" + servicename);
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            System.out.println("Start-" + SessionManager.getCurrentUsername() + ":" + QDate.getCurrentDate() + ":" + QDate.getCurrentTime() + ":" + servicename);

            if (servicename.trim().equals("serviceCrChangeLang")) {
                return changeLang(headers, c);

            }

            Response res = CallDispatcher.callService(c);
            System.out.println(servicename + " | - Service Executing Time: " + (System.currentTimeMillis() - serviceTime));
            conn.commit();
            //conn.close();

            return res;
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, "loginException", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, json, ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }

    }

    private Response changeLang(@Context HttpHeaders headers, Carrier carrier) throws QException {
        EntityCrUser ent = new EntityCrUser();
        ent.setDeepWhere(false);
        ent.setUsername(SessionManager.getCurrentUsername());
        ent.setDbname(SessionManager.getCurrentDomain());
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);

        EntityCrCompany entComp = new EntityCrCompany();
        entComp.setDeepWhere(false);
        entComp.setCompanyDb(SessionManager.getCurrentDomain());
        entComp.setStartLimit(0);
        entComp.setEndLimit(0);
        EntityManager.select(entComp);

        System.out.println("username=" + ent.getUsername());

        carrier.setValue("username", SessionManager.getCurrentUsername());
        carrier.setValue("password", ent.getPassword());
        carrier.setValue("domain", entComp.getCompanyDomain());

        System.out.println("json=" + carrier.getJson());
        return doDoPostRequestForLogin(headers, carrier.getJson());

    }

    private Response doCallDispatcherNoToken(@Context HttpHeaders headers,
                                             @PathParam("servicename") String servicename, String json) {
        String srv[] = new String[]{"serviceCrSignupPersonal",
                "serviceCrForgetPassword",
                "frgtpwd.html",
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
                "serviceTmGetBacklogHistoryByDate",
                "serviceTmGetAssignedLabelByDates",
                "serviceTmGetHistoryTimesByDate",
                "serviceCrGetListItemList4ComboNali",
                "serviceTmGetUserStoryInfoById",
                "serviceTmGetBacklogTaskList",
                "serviceTmLoadAssignedLabel",
                "serviceTmGetBacklogCoreInfoById",
                "serviceTmGetInputList",
                "serviceTmSaveFormAction",
                "serviceTmDeleteFromTable",
                "serviceTmGetAssignedLabelById",
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
            SessionManager.setDomain(Thread.currentThread().getId(), "apd_" + c.get("domain"));

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

    private Response doCallDispatcherNoToken4Register(@Context HttpHeaders headers,
                                                      String servicename, String json) {

        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            Carrier c = new Carrier();
            c.fromJson(json);
            c = CrModel.register(c);

            Response res = Response.status(Response.Status.OK).entity(c.getJson()).build();
            conn.commit();

            return res;
//        urn Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, json, ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

    private Response doCallDispatcherNoToken4Activation(@Context HttpHeaders headers,
                                                        String servicename, String json) {
//        System.out.println("servicename="+servicename);
        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            Carrier c = new Carrier();
            c.fromJson(json);
            c = CrModel.activateCompany(c);

            Response res = Response.status(Response.Status.OK).entity(c.getJson()).build();
            conn.commit();

            return res;
//        urn Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions(servicename, json, ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

    @GET
    @Path(value = "getContent")
    @Compress
    @Produces(value = MediaType.TEXT_HTML)
    public Response getContent(@Context HttpHeaders headers) {

        Connection conn = null;
        try {
//            System.out.println("connectin yaradilir");
            conn = new DBConnection().getConnection();
//            System.out.println("setAutoCommit false edilir");
            conn.setAutoCommit(false);
//            System.out.println("connectin yaradildi");
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
//            System.out.println("connectin artiq movcuddur");

            Cookie cookie = headers.getCookies().get("apdtok");
            String cs = cookie.getValue();

            EntityCrUser user = null;
            long startTime = System.currentTimeMillis();
            user = SessionHandler.getTokenFromCookie(cs);
            SessionManager.setUserName(Thread.currentThread().getId(), user.getUsername());
            SessionManager.setLang(Thread.currentThread().getId(), user.selectLang());
            SessionManager.setDomain(Thread.currentThread().getId(), user.selectDomain());
            SessionManager.setUserId(Thread.currentThread().getId(), user.getId());
            SessionManager.setCompanyId(Thread.currentThread().getId(), user.selectCompanyId());

//            System.out.println("Getting User: " + (System.currentTimeMillis() - startTime));
//         EntityCrUser user = new EntityCrUser();
//         user.setUsername("admin1");
            startTime = System.currentTimeMillis();
            UserController uc = new UserController();
            String content = uc.filterText(user.getUsername());
//            System.out.println("Filter HTML: " + (System.currentTimeMillis() - startTime));
            conn.commit();
            //conn.close();
            return Response.status(Response.Status.OK).entity(content).build();
        } catch (QException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions("getContent", "createConnection", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException | IOException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions("getContent", "getContent", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (JoseException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions("getContent", "getToken", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

    @POST
    @Path(value = "signup/{type}")
    @Compress
    @Produces(value = MediaType.TEXT_HTML)
    public Response signup(@Context HttpHeaders headers, @PathParam(value = "type") final String type, final String json) throws QException {

        Connection conn = null;
        try {

            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);

            System.out.println("json>>>" + json);
            Carrier carrier = new Carrier();

            carrier.fromJson(json);
            String lang = carrier.getValue("lang").toString();
            System.out.println("SessionHandler.isLangAvailable(lang) ? lang : \"ENG\";  >>>");
            lang = SessionHandler.isLangAvailable(lang) ? lang : "ENG";
            SessionManager.setLang(Thread.currentThread().getId(), lang);

            long startTime = System.currentTimeMillis();
            UserController uc;
            if ("company".equals(type)) {
                System.out.println("     uc = new UserController(\"page_signup_company.html\"); >>>");

                uc = new UserController("page_signup_company.html");
            } else if ("personal".equals(type)) {
                System.out.println("        uc = new UserController(\"page_signup_personal.html\"); >>>");
                uc = new UserController("page_signup_personal.html");
            } else {
                conn.commit();
                return Response.status(Response.Status.BAD_REQUEST).entity("").build();

            }
            System.out.println("   uc filter text >>>");
            String content = uc.filterText("__singup__");
            System.out.println("Filter HTML: " + (System.currentTimeMillis() - startTime));
            conn.commit();

            return Response.status(Response.Status.OK).entity(content).build();
        } catch (QException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions("signup", "createConnection", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException | IOException ex) {
            DBConnection.rollbackConnection(conn);
            QLogger.saveExceptions("signup", "getContent", ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            DBConnection.closeConnection(conn);
            SessionManager.cleanSessionThread();
        }
    }

}
