package resources.src.apd_backlog;

import utility.Carrier;

public class AddFaizToName {

public static Carrier AddFaizToName(Carrier carrier) throws Exception {

String nm = carrier.get("name");
nm = "%%"+nm+"%%";
carrier.set("name",nm); 
return carrier;

}

}

