package resources.src.apd_backlog;

import utility.Carrier;
import utility.QException;

public class getCompanyList {

    public static Carrier getCompanyList(Carrier carrier) throws QException {

        String st = "Dede Dede Ay dede";
        carrier.set("name", st);
        carrier.set("surname", "myname");
        System.out.println("zad zad = " + carrier.get("surname"));
        return carrier;

    }
}
