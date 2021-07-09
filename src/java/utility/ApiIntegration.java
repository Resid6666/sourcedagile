/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
package utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import resources.config.Config;
import utility.sqlgenerator.QLogger;

public class ApiIntegration {

    private String method;
    private String url;
    private String contentType;
    private String content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Carrier send() {
        Carrier cr = new Carrier();

        try {
            String url1 = this.getUrl();

            HttpURLConnection conn = null;
            String output = "";

            URL url = new URL(url1);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(this.getMethod());
            conn.setRequestProperty("Content-Type", "application/" + this.getContentType());

            String input = "";
//                System.out.println("input xml"+input);
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
//                System.out.println("respcode=" + conn.getResponseCode());

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {

                String err = "Failed : HTTP error code :"
                        + String.valueOf(conn.getResponseCode());

                cr.set("err", err);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

//            System.out.println("Output from Server .... \n");
            String rline = "";
            while ((output = br.readLine()) != null) {
                rline = rline + output;
            }

            cr.set("response", rline);

        } catch (Exception ex) {

        }

        return cr;
    }

}
