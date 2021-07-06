/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import label.CoreLabel;

/**
 *
 * @author Anar
 */
public class SAFunction {

    static String Prefix = "@.";
    static String FUNCTION_BODY = "functionBody";
    static String FUNCTION_NAME = "fnName";

    private static String MapList(String key) throws QException {
        Carrier crList = new Carrier();
        crList.set("map", "Map");
        crList.set("set", "Set");
        crList.set("deletekey", "DeleteKey");
        crList.set("callapi", "CallApi");
        crList.set("if", "If");
        crList.set("sum", "Sum");
        crList.set("inc", "Inc");
        crList.set("dec", "Dec");
        crList.set("concat", "Concat");
        crList.set("callfn", "CallFn");
        crList.set("ifhasvalue", "IfHasValue");
        crList.set("ifhasnotvalue", "IfHasNotValue");
        crList.set("settable", "SetTable");
        crList.set("settableobject", "SetTableObject");
        crList.set("gettable", "GetTable");
        crList.set("fortable", "ForTable");

        return crList.get(key);
    }

    public static Carrier ExecCommand(Carrier carrier) throws QException {

        String fnLine = carrier.get(FUNCTION_NAME);
        String functionBody = GetFunctionBody(fnLine);
        carrier.set(FUNCTION_BODY, functionBody);

        if (!IsCommand(fnLine)) {
            return new Carrier();
        }

        Carrier crOut = new Carrier();
        carrier.copyTo(crOut);

        String command = GetCommandPart(fnLine);
        String pairedFnName = MapList(command);

        try {
            String argLn = (fnLine.length() > 2) ? fnLine.split("\\(")[1].split("\\)")[0] : "";
            String artLnList[] = argLn.split(",");
            for (int i = 0; i < artLnList.length; i++) {
                crOut.set("param" + (i + 1), artLnList[i].trim());
            }

        } catch (Exception err) {
        }

        try {
            SAFunction model = new SAFunction();
            Method method = model.getClass().getMethod(pairedFnName, Carrier.class);
            crOut = (Carrier) method.invoke(model, crOut);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SAFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SAFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SAFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SAFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return crOut;
    }

    private static String GetCommandPart(String description) {
        String res = "";
        description = description.trim();
        description = description.trim().replaceAll(Prefix, "");
        description = description.toLowerCase();
        res = description.split("\\(")[0];
        return res;
    }

    public static boolean IsCommand(String fnName) {
        boolean f = false;
        String commandPart = GetCommandPart(fnName);
        try {
            f = (fnName.trim().startsWith(Prefix) && MapList(commandPart).length() > 0);
        } catch (Exception ee) {
        }
        return f;
    }

    public static boolean IsDeleteCommand(String fnName) {
        boolean f = false;
        String commandPart = GetCommandPart(fnName);
        try {
            f = (fnName.trim().startsWith(Prefix) && commandPart.toLowerCase().equals("deletekey"));
        } catch (Exception ee) {
        }
        return f;
    }

    public static Carrier Map(Carrier carrier) throws QException, Exception {
        Carrier crTemp = new Carrier();
        String sourceKey = carrier.get("param1");
        String destinationKey = carrier.get("param2");

        sourceKey = GetArgumentPureValue(sourceKey);
        destinationKey = GetArgumentPureValue(destinationKey);

        crTemp.set(sourceKey, carrier.get(destinationKey));

        return crTemp;
    }

    public static Carrier Set(Carrier carrier) throws QException, Exception {
        Carrier crTemp = new Carrier();
        String key = carrier.get("param1");
        String value = carrier.get("param2");

        value = GetArgumentPureValue(value);
        key = GetArgumentPureValue(key);

        crTemp.set(key, value);
        return crTemp;
    }

    public static Carrier DeleteKey(Carrier carrier) throws QException {
        String key = carrier.get("param1");
        carrier.removeKey(key);
        return carrier;
    }

    public static Carrier CallApi(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String apiId = carrier.get("param1");
        crTemp = BEAction.callApi(carrier);
        return crTemp;
    }

    public static Carrier Concat(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyName = carrier.get("param1");

        String keys[] = carrier.getKeys();
        String d = "";
        for (int i = 2; i < keys.length; i++) {
            try {
                String val = carrier.get("param" + i);
                val = GetArgumentValue(carrier, val, false);
                d += val;
            } catch (Exception e) {
            }
        }

        crTemp.set(keyName, d);
        return crTemp;
    }

    public static Carrier Sum(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyName = carrier.get("param1");

        String keys[] = carrier.getKeys();
        double d = 0;
        for (int i = 2; i < keys.length; i++) {
            try {
                String val = carrier.get("param" + i);
                val = GetArgumentValue(carrier, val);
                double td = Double.parseDouble(val);
                d += td;
            } catch (Exception e) {
            }
        }

        crTemp.set(keyName, d);
        return crTemp;
    }

    public static Carrier Inc(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyName = carrier.get("param1");

        String keys[] = carrier.getKeys();
        double d = 1;
        for (int i = 2; i < keys.length; i++) {
            try {
                String val = carrier.get("param" + i);
                val = GetArgumentValue(carrier, val);
                double td = Double.parseDouble(val);
                d *= td;
            } catch (Exception e) {
            }
        }

        crTemp.set(keyName, d);
        return crTemp;
    }

    public static Carrier Dec(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyName = carrier.get("param1");

        String keys[] = carrier.getKeys();
        double d = 1;
        for (int i = 2; i < keys.length; i++) {
            try {
                String val = carrier.get("param" + i);
                val = GetArgumentValue(carrier, val);
                double td = Double.parseDouble(val);
                d = d / td;
            } catch (Exception e) {
            }
        }

        crTemp.set(keyName, d);
        return crTemp;
    }

    public static String GetArgumentPureValue(String valueCore) throws Exception {
        String val = valueCore;
        try {
            valueCore = valueCore.trim();

            val = (valueCore.startsWith("'") && valueCore.endsWith("'"))
                    ? valueCore.substring(1, valueCore.length() - 1)
                    : valueCore.startsWith("\"") && valueCore.endsWith("\"")
                    ? valueCore.substring(1, valueCore.length() - 1)
                    : valueCore;

            val = val.trim();
        } catch (Exception err) {
        }
        return val;
    }

    public static String GetArgumentValue(Carrier carrier, String valueCore) throws Exception {
        return GetArgumentValue(carrier, valueCore, true);
    }

    public static String GetArgumentValue(Carrier carrier, String valueCore, boolean isTrimmed) throws Exception {
        String val = valueCore;
        try {
            valueCore = (isTrimmed) ? valueCore.trim() : valueCore;

            val = (valueCore.startsWith("'") && valueCore.endsWith("'"))
                    ? valueCore.substring(1, valueCore.length() - 1)
                    : valueCore.startsWith("\"") && valueCore.endsWith("\"")
                    ? valueCore.substring(1, valueCore.length() - 1)
                    : carrier.isKeyExist(valueCore) ? carrier.get(valueCore)
                    : "";

            val = (isTrimmed) ? val.trim() : val;

        } catch (Exception err) {
        }
        return val;
    }

    public static Carrier Function_If_Body_Statement(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();

        String commands[] = carrier.get(FUNCTION_BODY).split(";");
        for (int i = 0; i < commands.length; i++) {
            String cmd = commands[i];
            if (cmd.length() > 3) {
                Carrier crIn = new Carrier();
                crIn.set(FUNCTION_NAME, cmd);
                crIn = ExecCommand(crIn);
                crIn.copyTo(crOut);

            }
        }

        return crOut;

    }

    public static Carrier Function_For_Body_Statement(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();

        String commands[] = carrier.get(FUNCTION_BODY).split("::");
        for (int i = 0; i < commands.length; i++) {
            String cmd = commands[i];
            if (cmd.length() > 3) {
                Carrier crIn = new Carrier();
                carrier.copyTo(crIn);
                crIn.set(FUNCTION_NAME, cmd);

                crIn = ExecCommand(crIn);
                crIn.copyTo(crOut);
            }
        }

        return crOut;

    }

    public static String GetFunctionBody(String description) {
        String argLine = "";
        try {

            int startIndex = description.indexOf("{");
            int lastIndex = description.lastIndexOf('}');
            String cmd = description.substring(startIndex + 1, lastIndex);
            argLine = (description.length() > 1) ? cmd : "";
        } catch (Exception err) {
        }
        return argLine;
    }

    public static Carrier If(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyCore = carrier.get("param1");
        String operation = carrier.get("param2");
        String valueCore = carrier.get("param3");

        operation = GetArgumentPureValue(operation);
        operation = operation.replace(" ", "");
        operation = operation.toLowerCase();

        String value = GetArgumentValue(carrier, valueCore);
        String key = GetArgumentValue(carrier, keyCore);

        key = key.trim();
        value = value.trim();

        boolean operRes = false;

        if (operation.equals("=")) {
            operRes = (key.equals(value));
        } else if (operation.equals("!=")) {
            operRes = (!key.equals(value));
        } else if (operation.equals(">")) {
            operRes = (Double.parseDouble(key) > Double.parseDouble(value));
        } else if (operation.equals(">")) {
            operRes = (Double.parseDouble(key) > Double.parseDouble(value));
        } else if (operation.equals("<")) {
            operRes = (Double.parseDouble(key) < Double.parseDouble(value));
        } else if (operation.equals(">=") || operation.equals("=>")) {
            operRes = (Double.parseDouble(key) >= Double.parseDouble(value));
        } else if (operation.equals("<=") || operation.equals("=<")) {
            operRes = (Double.parseDouble(key) <= Double.parseDouble(value));
        } else if (operation.equals("in")) {
            operRes = (key.contains(value));
        } else if (operation.equals("notin")) {
            operRes = (key.contains(value));
        }

        if (operRes) {
            crTemp = Function_If_Body_Statement(carrier);
        }

        return crTemp;
    }

    public static Carrier IfHasValue(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyCore = carrier.get("param1");
        String key = GetArgumentValue(carrier, keyCore);

        if (carrier.get(key).length() > 0) {
            crTemp = Function_If_Body_Statement(carrier);
        }

        return crTemp;
    }

    public static Carrier IfHasNotValue(Carrier carrier) throws Exception {
        Carrier crTemp = new Carrier();
        String keyCore = carrier.get("param1");
        String key = GetArgumentValue(carrier, keyCore);

        if (carrier.get(key).length() == 0) {
            crTemp = Function_If_Body_Statement(carrier);
        }

        return crTemp;
    }

    public static Carrier SetTable(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();

        String row = carrier.get("param1");
        String col = carrier.get("param2");
        String val = carrier.get("param3");

        row = GetArgumentPureValue(row);
        col = GetArgumentPureValue(col);
        val = GetArgumentPureValue(val);

        int r = Integer.parseInt(row);
        crOut.setValue(CoreLabel.RESULT_SET, r, col, val);

        return crOut;
    }

    public static Carrier SetTableObject(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();

        String colName = carrier.get("param1");
        String keys[] = carrier.getKeys();
        for (int i = 2; i <= keys.length; i++) {
            String key = "param" + i;
            if (carrier.hasKey(key)) {
                String value = carrier.get(key);
                value = GetArgumentValue(carrier, value);
                crOut.setValue(CoreLabel.RESULT_SET, i - 2, colName, value);
            }
        }

        return crOut;
    }

    public static Carrier GetTable(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();

        String row = carrier.get("param1");
        String col = carrier.get("param2");
        String isDistict = carrier.get("param3");
        String separator = carrier.get("separator");

        row = GetArgumentPureValue(row);
        col = GetArgumentPureValue(col);
        isDistict = GetArgumentPureValue(isDistict);
        separator = GetArgumentPureValue(separator);

        String tn = carrier.getTableIndex(0);
        int rc = carrier.getTableRowCount(tn);
        String resLine = "";
        for (int i = 0; i < rc; i++) {
            String value = carrier.getValue(tn, i, col).toString();
            resLine += value + separator;
        }

        crOut.set(row, resLine);
        return crOut;
    }

    public static Carrier ForTable(Carrier carrier) throws Exception {
        Carrier crOut = new Carrier();
        carrier.copyKeys(crOut);

        String functionBody = carrier.get(FUNCTION_BODY);

        String tn = carrier.getTableIndex(0);
        int rc = carrier.getTableRowCount(tn);
        String resLine = "";
        String colName[] = carrier.getTableColumnNames(tn);
        for (int i = 0; i < rc; i++) {
            Carrier crLine = new Carrier();
            crOut.copyKeys(crLine);
            for (String cols : colName) {
                crLine.set(cols, carrier.getValue(tn, i, cols));
            }

            crLine.set(FUNCTION_BODY, functionBody);
            crLine = Function_For_Body_Statement(crLine);
            crLine.copyTo(crOut);

        }

        return crOut;
    }

}
