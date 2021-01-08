/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.io;

import controllerpool.ControllerPool;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import resources.config.Config;
import utility.Carrier;
import utility.QException;
import utility.SessionManager;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class IoModel {

    public static void main(String[] arg) throws Exception {
        Carrier cr = new Carrier();
        cr.set("mandelo","insertNewZad");
        SessionManager.setDomain(SessionManager.getCurrentThreadId(),"apd_backlog");
        coreMandelo(cr);
    }

    //////////////////////////////////////////////////////
    public static Carrier coreMandelo(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("mandelo", cp.hasValue(carrier, "mandelo"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String corePath = Config.getProperty("external.execute.command");
        corePath+=SessionManager.getCurrentDomain();
        corePath+=" "+carrier.get("mandelo");
        
        String ln = corePath;
        
        ln = "java -cp E:\\external_java_class\\lib\\*;E:\\external_java_class\\project\\SABackendDinamic\\build\\classes\\; "
                + " domain.apd_backlog.getPaymentList";
        String res = executeCommand(ln);
        System.out.println("res= bundan sonraki hiss \n"+res);
        
        carrier.fromJson(res);

        return carrier;
    }

    private static String executeCommand(String command) {
         String res = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            Scanner scanner = new Scanner(process.getInputStream(), "UTF-8");
           
            while (scanner.hasNextLine()) {
                res +=  scanner.nextLine() +"\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}
