package resources.src.apd_backlog;

import utility.Carrier;

import module.tm.entity.EntityTmBacklog;
import utility.sqlgenerator.EntityManager;public class publishName {

public static Carrier publishName(Carrier carrier) throws Exception {

Carrier cr = new Carrier();  
cr.set("odenishMeblegi","55");
cr.set("idDeger",carrier.get("id"));
cr.set("nameello","kelbetino");
cr.set("name111","1111111");

EntityTmBacklog ent = new EntityTmBacklog();
cr = EntityManager.select(ent);


return cr;

} }

