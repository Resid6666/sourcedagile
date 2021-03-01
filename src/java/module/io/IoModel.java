/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.io;

import controllerpool.ControllerPool;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Scanner;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import module.tm.entity.EntityTmBacklog;
import module.tm.entity.EntityTmDatabase;
import module.tm.entity.EntityTmInput;
import module.tm.entity.EntityTmJsCode;
import module.tm.entity.EntityTmTable;
import resources.config.Config;
import utility.Carrier;
import utility.GeneralProperties;
import utility.QException;
import utility.SessionManager;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class IoModel {

    public static void main(String[] arg) throws Exception {

    }

    //////////////////////////////////////////////////////
    public static Carrier runFunction(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fnName", cp.hasValue(carrier, "fnName"));
        if (carrier.hasError()) {
            return carrier;
        }

        String fnName = carrier.get("fnName");

        Carrier cr = new Carrier();
        Class<?> c = Class.forName("resources.src." + SessionManager.getCurrentDomain() + "." + fnName);
        Method method = c.getDeclaredMethod(fnName, Carrier.class);
        carrier = (Carrier) method.invoke(c, carrier);

        return carrier;
    }

    public static Carrier compileJava(Carrier carrier) throws Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }

        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);

        try {
            String st = getJavaCodeLine(ent.getFnCoreName(), ent.getFnBody(), ent.getLibraryUrl());
            saveJavaCode(ent.getFnCoreName(), st);
        } catch (Exception e) {
            carrier.set("err", e.getMessage());

        }
        return carrier;
    }

    private static String getJavaCodeLine(String fnName, String fnBody, String library) throws QException, Exception {
        String st = "package resources.src." + SessionManager.getCurrentDomain() + ";" + "\n\n";
        st += "import utility.Carrier;" + "\n\n";
        st += library;
        st += "public class " + fnName + " {" + "\n\n";
        st += "public static Carrier " + fnName + "(Carrier carrier) throws Exception {" + "\n\n";
        st += fnBody + "\n\n";
        st += "} }" + "\n\n";

        return st;

    }

    private static void saveJavaCode(String className, String classBody) throws QException, Exception {
        GeneralProperties prop = new GeneralProperties();
        String fpath = prop.getWorkingDir() + "../src/" + SessionManager.getCurrentDomain();

        File theDir = new File(fpath);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String filename = fpath + "/" + className + ".java";

        File myObj1 = new File(filename);
        try {
            myObj1.delete();
        } catch (Exception ee) {
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.append(classBody);
        bw.close();

        String deleteClassName = fpath + "/" + className + ".class";
        File myObj = new File(deleteClassName);
        try {
            myObj.delete();
        } catch (Exception ee) {
        }

        String res = compileCodeZad(className);
        if (res.length() > 2) {
            throw new Exception(res);
        }

    }

    private static String compileCodeZad(String className) throws QException, Exception {

        String corePath = Config.getProperty("external.execute.command");
        String ln = corePath;
        ln += SessionManager.getCurrentDomain() + "/";

        ln += className + ".java\"";

        String res = executeCommand(ln);
        return res;
//        return "0";
    }

    public static Carrier coreMandelo(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("mandelo", cp.hasValue(carrier, "mandelo"));
        if (carrier.hasError()) {
            return carrier;
        }

        String corePath = Config.getProperty("external.execute.command");
        corePath += SessionManager.getCurrentDomain();
        corePath += " " + carrier.get("mandelo");

        String ln = corePath;

        ln = "java -cp E:\\external_java_class\\lib\\*;E:\\external_java_class\\project\\SABackendDinamic\\build\\classes\\; "
                + " domain.apd_backlog.getPaymentList";
        String res = executeCommand(ln);
        System.out.println("res= bundan sonraki hiss \n" + res);

        carrier.fromJson(res);

        return carrier;
    }

    private static String executeCommand(String command) throws IOException, InterruptedException {

        String res = "";

        Process process = Runtime.getRuntime().exec(command);
//                Process process = ProcessBuilder.exec(command);

        Scanner scanner = new Scanner(process.getErrorStream(), "UTF-8");

        while (scanner.hasNextLine()) {
            res += scanner.nextLine() + "\n";
        }

        return res;
    }

    public static Carrier coreSelect(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entity", cp.hasValue(carrier, "entity"));
        carrier.addController("entityDb", cp.hasValue(carrier, "entityDb"));
//        carrier.addController("selectedField", cp.hasValue(carrier, "selectedField"));
        if (carrier.hasError()) {
            return carrier;
        }

        Carrier cout = EntityManager.select(carrier);
//        cout.renameTableName(carrier.getEntityFullname(), CoreLabel.RESULT_SET);

        String tn = carrier.getEntityFullname();
        int rc = cout.getTableRowCount(tn);
        if (rc == 1) {
            String cols[] = cout.getTableColumnNames(tn);
            for (int i = 0; i < cols.length; i++) {
                String colName = cols[i];
                String val = cout.getValue(tn, 0, colName).toString();
                cout.set(colName, val);
            }
        }

        cout.set("startLimit", carrier.get("startLimit"));
        cout.set("endLimit", carrier.get("endLimit"));

        return cout;
    }

    public static Carrier coreInsert(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entity", cp.hasValue(carrier, "entity"));
        carrier.addController("entityDb", cp.hasValue(carrier, "entityDb"));
        if (carrier.hasError()) {
            return carrier;
        }

        carrier = EntityManager.insert(carrier);
        return carrier;
    }

    public static Carrier coreDelete(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entityDb", cp.hasValue(carrier, "entityDb"));
        carrier.addController("entity", cp.hasValue(carrier, "entity"));
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }

        EntityManager.delete(carrier);
        return carrier;
    }

    public static Carrier coreUpdate(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("updatedField", cp.hasValue(carrier, "updatedField"));
        carrier.addController("entityDb", cp.hasValue(carrier, "entityDb"));
        carrier.addController("entity", cp.hasValue(carrier, "entity"));
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }

        EntityManager.update(carrier);
        return carrier;
    }

    ///////////////////////////////////////////////
    ///////////////////////////////////////////////
    public static Carrier callApi(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("api", cp.hasValue(carrier, "api"));
        if (carrier.hasError()) {
            return carrier;
        }

        String apiId = getApiIdByName(carrier.get("api"));

        if (apiId.length() == 0) {
            return carrier;
        }

        String actionType = getApiActionType(apiId);

        if (actionType.equals("C")) {
            carrier = callInsertApi(apiId, carrier);
        } else if (actionType.equals("R")) {
            carrier = callSelectApi(apiId, carrier);
        } else if (actionType.equals("U")) {
            carrier = callUpdateApi(apiId, carrier);
        }

        return carrier;
    }

    private static String getApiIdByName(String name) throws Exception {
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setBacklogName(name);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        return ent.getId();

    }

    private static Carrier callInsertApi(String apiId, Carrier carrier) throws Exception {
        Carrier cr = carrier;
        cr.setEntityDbName(dbname4Insert(apiId));
        cr.setEntityName(dbTableName4Insert(apiId));
        cr = coreInsert(cr);

        return cr;
    }

    private static Carrier callUpdateApi(String apiId, Carrier carrier) throws Exception {
        Carrier cr = carrier;
        cr.setEntityDbName(dbname4Insert(apiId));
        cr.setEntityName(dbTableName4Insert(apiId));
        cr = coreUpdate(cr);

        return cr;
    }

    private static Carrier callSelectApi(String apiId, Carrier carrier) throws Exception {
        Carrier cr = carrier;
        cr.setEntityDbName(dbname4Read(apiId));
        cr.setEntityName(dbTableName4Read(apiId));
        cr.setSelectedField(getSelectedFields(apiId));
        cr = coreSelect(cr);

        return cr;
    }

    private static String getApiActionType(String apiId) throws QException {

        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(apiId);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        return ent.getApiAction();

    }

    private static String getSelectedFields(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return "";
        }

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        Carrier cr = EntityManager.select(ent);
        return cr.getValueLine(ent.toTableName(), EntityTmInput.INPUT_NAME, ",");
    }

    private static Carrier getInputList(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return new Carrier();
        }

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("IN");
        Carrier cr = EntityManager.select(ent);
        return cr;
    }

    private static Carrier getOutputList(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return new Carrier();
        }

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        Carrier cr = EntityManager.select(ent);
        return cr;
    }

    private static String dbname4Insert(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return "-1";
        }
        String dbname = "-1";

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        ent.setEndLimit(0);
        EntityManager.select(ent);

        if (ent.getSendToDbId().length() > 4) {
            EntityTmDatabase entDb = new EntityTmDatabase();
            entDb.setId(ent.getSendToDbId());
            EntityManager.select(entDb);
            dbname = entDb.getDbName();
        }
        return dbname;
    }

    private static String dbname4Read(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return "-1";
        }
        String dbname = "-1";

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        ent.setEndLimit(0);
        EntityManager.select(ent);

        if (ent.getSelectFromDbId().length() > 4) {
            EntityTmDatabase entDb = new EntityTmDatabase();
            entDb.setId(ent.getSelectFromDbId());
            EntityManager.select(entDb);
            dbname = entDb.getDbName();
        }
        return dbname;
    }

    private static String dbTableName4Insert(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return "-1";
        }
        String dbname = "-1";

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        ent.setEndLimit(0);
        EntityManager.select(ent);

        if (ent.getSendToTableId().length() > 4) {
            EntityTmTable entDb = new EntityTmTable();
            entDb.setId(ent.getSendToTableId());
            EntityManager.select(entDb);
            dbname = entDb.getTableName();
        }

        return dbname;
    }

    private static String dbTableName4Read(String apiId) throws QException {
        if (apiId.trim().length() == 0) {
            return "-1";
        }
        String dbname = "-1";

        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(apiId);
        ent.setInputType("OUT");
        ent.setEndLimit(0);
        EntityManager.select(ent);

        if (ent.getSelectFromTableId().length() > 4) {
            EntityTmTable entDb = new EntityTmTable();
            entDb.setId(ent.getSelectFromTableId());
            EntityManager.select(entDb);
            dbname = entDb.getTableName();
        }

        return dbname;
    }
}
