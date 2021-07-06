package resources.src.apd_backlog;

import utility.Carrier;

public class CalculateZadInJava {

public static Carrier CalculateZadInJava(Carrier carrier) throws Exception {

String st = carrier.get("st");
String mebleg = carrier.get("mebleg");

st = st + " - " +mebleg;
carrier.set("st",st);

return carrier;

}

}

