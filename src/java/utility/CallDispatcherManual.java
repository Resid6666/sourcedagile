/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author Anar
 */
public class CallDispatcherManual {

    public static void main(String[] arg) throws Exception {
        Carrier cr = new Carrier();
        String json = arg[0];

        cr.fromJson(json);
        cr.setServiceName(cr.get("serviceName"));

        String res = CallDispatcher.callServiceManual(cr);
        System.out.println(res);

    }

}
