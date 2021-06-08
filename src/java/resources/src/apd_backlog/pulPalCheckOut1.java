package resources.src.apd_backlog;

import utility.Carrier;

import javax.xml.bind.DatatypeConverter ;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;public class pulPalCheckOut1 {

public static Carrier pulPalCheckOut1(Carrier carrier) throws Exception {

Carrier cr = new Carrier();
String json  = carrier.get("pureJson");
String jsonCore = "{\"kv\":"+json+ "}";
cr.fromJson(jsonCore);
cr.set("pureJson",json);
  
  

 String key = " ";
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    sha256_HMAC.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
    byte[] result = sha256_HMAC.doFinal("".getBytes());
    String shaCoded = DatatypeConverter.printHexBinary(result);

cr.set("encoded",shaCoded);
 
return cr;

}

}

