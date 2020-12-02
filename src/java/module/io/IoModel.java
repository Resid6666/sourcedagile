/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.io;

import controllerpool.ControllerPool;
import utility.Carrier;
import utility.QException;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class IoModel {

    public static void main(String[] arg) {
        String st = "1";
        for (int i = 1; i <= 100; i++) {
            st += "0";
        }
    }

    //////////////////////////////////////////////////////
    public static Carrier coreSelect(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entity", cp.hasValue(carrier, "entity"));
        carrier.addController("entityDb", cp.hasValue(carrier, "entityDb"));
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
                String val = cout.getValue(tn,0,colName).toString();
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
