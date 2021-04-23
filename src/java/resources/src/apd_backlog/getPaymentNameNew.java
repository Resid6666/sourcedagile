package resources.src.apd_backlog;

import utility.Carrier;

import module.cr.entity.EntityCrUser;
import utility.sqlgenerator.EntityManager;public class getPaymentNameNew {

public static Carrier getPaymentNameNew(Carrier carrier) throws Exception {

Carrier cr = new Carrier();
 
EntityCrUser ent = new EntityCrUser();
cr = EntityManager.select(ent);


    
return cr;

}

}

