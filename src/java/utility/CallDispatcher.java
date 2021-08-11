/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import label.CoreLabel;
import org.ehcache.Cache;
import org.ehcache.Status;
import org.json.JSONObject;

/**
 *
 * @author 02483577
 */
public class CallDispatcher {

    static String SERVICE = "Service";
    static String DISPATCHER_LABEL = "Dispatcher";

    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("zad shey oldu");

    }

    public static String callService(String json) {
        Carrier carrier = new Carrier();
        carrier.fromJson(json);
        System.out.println(carrier.toXML());

        return json;
    }

    public static String callServiceManual(Carrier carrier) throws Exception {

        String entity = "";
        String serviceName = carrier.getServiceName();
        String module = getModuleName(serviceName);
        createKeyValuePairInCarrier(carrier);
        carrier = executeDispatcher(module, carrier);
        entity = carrier.getJson();

        return entity;
    }

    public static Response callServiceWithPureJson(Carrier carrier) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, Exception {

        String entity = "";
        String serviceName = carrier.getServiceName();

        String module = getModuleName(serviceName);
        createKeyValuePairInCarrier(carrier);
        carrier = executeDispatcher(module, carrier);
        entity = carrier.get("pureJson");

        if (carrier.get("err").equals("400")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("").build();
        } else if (carrier.get("err").equals("401")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("").build();
        } else {
            return Response.status(Response.Status.OK).entity(entity).build();
        }

//        return Response.status(Response.Status.OK).entity(entity).build();
    }

    public static Response callService(Carrier carrier) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, Exception {
//        
//        Cache<String, String> serviceCache = CacheUtil.cacheManager
//                .getCache("serviceCache", String.class, String.class);
        //serviceCache.put("dd", "bb");
        String entity = "";
//        String cacheKey = carrier.getCacheKey();
        String serviceName = carrier.getServiceName();
//        boolean isServiceCachable = false;
        /*serviceName.equals("serviceCrGetAttributeList") 
                || serviceName.equals("serviceCrGetAttributeList")
                || serviceName.equals("serviceCrGetModuleList")
                || serviceName.equals("serviceCrGetSubmoduleList")
                || serviceName.equals("serviceCrGetEntityLabelList");*/

//        if (isServiceCachable && CacheUtil.cacheManager.getStatus()==Status.AVAILABLE 
//                && serviceCache.containsKey(cacheKey)) {
//            entity = serviceCache.get(cacheKey);
//        } else {
        String module = getModuleName(serviceName);
        createKeyValuePairInCarrier(carrier);
        carrier = executeDispatcher(module, carrier);
//        entity = carrier.getJson();
        entity = carrier.getJsonNew().toString();

//            if (isServiceCachable && CacheUtil.cacheManager.getStatus()==Status.AVAILABLE 
//                    && !carrier.hasError()) {
//                serviceCache.put(cacheKey, entity);
//            }
//        }
//        if (carrier.hasError()) {
//            entity = carrier.getErrorJson();
//        } else {
//            entity = carrier.getAllTableJsonString();
//        }
//        System.out.println("entity -> "+entity);
        return Response.status(Response.Status.OK).entity(entity).build();
//        return Response.status(Response.Status.OK).entity(carrier.getJsonNew()).build();
//       return  Response.ok(carrier.getJsonNew().toString(), MediaType.APPLICATION_JSON).build();
    }

    public static String getModuleName(String serviceName) {
        String res = serviceName.substring(SERVICE.length(), SERVICE.length() + 2);
        return res;
    }

    static Carrier executeDispatcher(String module, Carrier carrier) throws QException {
        try {
            ArrayList inc = new ArrayList();
            ArrayList exc = new ArrayList();
            getIncAndExc(inc, exc, carrier);

            String classname = "module." + module.toLowerCase(Locale.ENGLISH) + "." + QUtility.fcLetter(module) + DISPATCHER_LABEL;
            Class<?> c = Class.forName(classname);
            Object obj = c.newInstance();
            carrier = (Carrier) c.getMethod("callService", Carrier.class).invoke(obj, carrier);
            manageIncAndExcResult(inc, exc, carrier);
            return carrier;
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
            ex.getCause().printStackTrace();
            throw new QException(new Object() {

            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex.getCause());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new QException(new Object() {

            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex);
        }
    }

    private static void getIncAndExc(ArrayList inc, ArrayList exc, Carrier carrier) throws QException {
        try {
            String incLine = carrier.isKeyExist(CoreLabel.INCLUDED_FIELDS) ? carrier.getValue(CoreLabel.INCLUDED_FIELDS).toString() : "";
            String excLine = carrier.isKeyExist(CoreLabel.EXCLUDED_FIELDS) ? carrier.getValue(CoreLabel.EXCLUDED_FIELDS).toString() : "";

            String[] incList = incLine.split(CoreLabel.WS_INCLUDED_FIELDS_SEPERATOR);
            String[] excList = excLine.split(CoreLabel.WS_EXCLUDED_FIELDS_SEPERATOR);

            inc.addAll(Arrays.asList(incList));
            exc.addAll(Arrays.asList(excList));

        } catch (Exception ex) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex);
        }

    }

    private static void manageIncAndExcResult(ArrayList inc, ArrayList exc, Carrier carrier) throws QException {
        try {
            String[] cols = carrier.getTableColumnNames(CoreLabel.RESULT_SET);

            for (String colName : cols) {
                if ((inc.size() > 0) && (!inc.get(0).equals(""))) {
                    boolean f = true;
                    for (Object o : inc.toArray()) {
                        if (o.toString().trim().equals(colName)) {
                            f = false;
                        }
                    }
                    if (f) {
                        carrier.removeColoumn(CoreLabel.RESULT_SET, colName);
//                        System.out.println("remove field-->" + colName);
                    }
                }
                boolean f1 = false;
                for (Object o : exc.toArray()) {
                    if (o.toString().trim().equals(colName)) {
                        f1 = true;
                    }
                }
                if (f1) {
                    carrier.removeColoumn(CoreLabel.RESULT_SET, colName);
//                    System.out.println("remove field-->" + colName);

                }
            }
        } catch (Exception ex) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex);
        }

    }

    private static void createKeyValuePairInCarrier(Carrier carrier) throws QException {
        try {
            String cols[] = carrier.getTableColumnNames(CoreLabel.INPUT_TABLE);
            if (carrier.getTableRowCount(CoreLabel.INPUT_TABLE) == 1) {
                for (String col : cols) {
                    carrier.setValue(col, carrier.getValue(CoreLabel.INPUT_TABLE, 0, col).toString());
                }
            }

            String cols1[] = carrier.getTableColumnNames(CoreLabel.KEY_VALUE_TABLE);
            if (carrier.getTableRowCount(CoreLabel.INPUT_TABLE) == 1) {
                for (String cols11 : cols1) {
                    carrier.setValue(cols11, carrier.getValue(CoreLabel.INPUT_TABLE, 0, cols11).toString());
                }
            }
        } catch (Exception ex) {
            throw new QException(new Object() {
            }.getClass().getEnclosingClass().getName(),
                    new Object() {
                    }.getClass().getEnclosingMethod().getName(), ex);
        }

    }
}
