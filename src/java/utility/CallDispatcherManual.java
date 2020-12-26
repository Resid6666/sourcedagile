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
    
     public static void main(String[] arg) {
//         Carrier cr = new Carrier();
         System.out.println("zad shey olmadi amma oldu");
         String json = "{kv:{'name':'Anar'}}";
         try{
            CallDispatcher.callService(json);
         }catch(Exception e){
         }
    }
    
}
