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

/**
 *
 * @author Anar
 */
public class SAFunction {

    static String Prefix = "@.";

    private static String MapList(String key) throws QException {
        Carrier crList = new Carrier();
        crList.set("map", "Map");
        crList.set("set", "Set");
        crList.set("deletekey", "DeleteKey");
        return crList.get(key);
    }

    public static Carrier ExecCommand(Carrier carrier) throws QException {

        String fnName = carrier.get("fnName");
        if (!IsCommand(fnName)) {
            return new Carrier();
        }

        Carrier crOut = new Carrier();
        carrier.copyTo(crOut);

        String command = GetCommandPart(fnName);
        String pairedFnName = MapList(command);

        try {
            String argLn = (fnName.length() > 2) ? fnName.split("\\(")[1].split("\\)")[0] : "";
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

    public static Carrier Map(Carrier carrier) throws QException {
        Carrier crTemp = new Carrier();
        String sourceKey = carrier.get("param1");
        String destinationKey = carrier.get("param2");
        crTemp.set(sourceKey, carrier.get(destinationKey));

        return crTemp;
    }

    public static Carrier Set(Carrier carrier) throws QException {
        Carrier crTemp = new Carrier();
        String key = carrier.get("param1");
        String value = carrier.get("param2");
        crTemp.set(key, value);
        return crTemp;
    }

    public static Carrier DeleteKey(Carrier carrier) throws QException {
        String key = carrier.get("param1");
        carrier.removeKey(key);
        return carrier;
    }

}
