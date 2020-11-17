/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import module.tm.entity.EntityTmJiraIntegration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author nikli
 */
public class JIRA {

    private HttpURLConnection connection;
    private String inputQuery;
    private String projectCode;
    private String HTTPType = "POST";
    private String atlassianId;
    private String version;
    private String username;
    private String password;
    private String baseUrl;
    private String issue;

    public JIRA(String projectCode) {
        this.projectCode = projectCode;
    }

    public JIRA() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getAtlassianId() {
        return atlassianId;
    }

    public void setAtlassianId(String atlassianId) {
        this.atlassianId = atlassianId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl.length() == 0
                ? "rest/api/" + this.getVersion().replaceAll("\"", "'") + "/"
                : baseUrl;
    }

    public String getHTTPType() {
        return HTTPType;
    }

    public void setHTTPType(String HTTPType) {
        this.HTTPType = HTTPType;
    }

    public String getVersion() {
        return version.replaceAll("\"", "'");
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectCode() {
        return projectCode.replaceAll("\"", "'");
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpURLConnection connection) {
        this.connection = connection;
    }

    public String getInputQuery() {
        return inputQuery;
    }

    public void setInputQuery(String inputQuery) {
        this.inputQuery = inputQuery;
    }

    public int getIssueCountOfProject() throws QException {
        try {

            int rc = 0;

            this.setConnectionBody4Search();
            this.executeCommandInJira(this.getInputQuery4IssueCountOfProject());
            String outputJson = this.getConnectionOutputLines();

            try {
                JSONObject o = new JSONObject(outputJson);
                rc = o.getInt("total");
            } catch (Exception ex) {
            }

            return rc;
        } catch (IOException ex) {
            throw new QException(ex);
        }
    }

    public Carrier getIssueListOfProject() throws QException {
        try {
            int issueCount = this.getIssueCountOfProject();
            Carrier cr = new Carrier();
            int idx = 0;
            for (int j = 0; j < issueCount; j += 50) {
                String startAt = String.valueOf(j);
                String maxResult = "50";

                this.setConnectionBody4Search();
                this.executeCommandInJira(this.getInputQuery4IssueListOfProject(startAt, maxResult));
                String outputJson = this.getConnectionOutputLines();

                JSONObject object = new JSONObject(outputJson);
                JSONArray issuesArray = new JSONArray(object.getJSONArray("issues").toString());
                int issuesCount = issuesArray.length();
                for (int i = 0; i < issuesCount; i++) {
                    JSONObject issueObject = (JSONObject) issuesArray.get(i);
                    
                    double spentHours = this.getOjbectValueOfFieldsAsFloat(issueObject, "timespent");
                    double estHours = this.getOjbectValueOfFieldsAsFloat(issueObject, "timeestimate");
                    
                    cr.setValue("jiraTable", idx, "issueId", this.getOjbectValue(issueObject, "id"));
                    cr.setValue("jiraTable", idx, "issueKey", this.getOjbectValue(issueObject, "key"));
                    cr.setValue("jiraTable", idx, "issueType", this.getOjbectValueOfIssuetypeFields(issueObject, "name"));
                    cr.setValue("jiraTable", idx, "issueStatus", this.getOjbectValueOfStatusFields(issueObject, "name"));
                    cr.setValue("jiraTable", idx, "spentHours", spentHours);
                    cr.setValue("jiraTable", idx, "estimatedHours", estHours);
                    cr.setValue("jiraTable", idx, "description", this.getOjbectValueOfFields(issueObject, "description"));
                    cr.setValue("jiraTable", idx, "parentIssueId", this.getOjbectValueOfParentFields(issueObject, "id"));
                    cr.setValue("jiraTable", idx, "parentIssueKey", this.getOjbectValueOfParentFields(issueObject, "key"));
                    cr.setValue("jiraTable", idx, "parentIssueType", this.getOjbectValueOfIssueTypeOfParentFields(issueObject, "name"));
                    cr.setValue("jiraTable", idx, "assigneeAccountId", this.getOjbectValueOfAssigneeFields(issueObject, "accountId"));
                    cr.setValue("jiraTable", idx, "reporterAccountId", this.getOjbectValueOfReporterFields(issueObject, "accountId"));
                    idx++;

                }
            }
            return cr;
        } catch (IOException ex) {
            throw new QException(ex);
        } catch (JSONException ex) {
            throw new QException(ex);
        }
    }

    private String getOjbectValue(JSONObject object, String key) {
        try {
            return (String) object.get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }

    private String getOjbectValueOfFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }
     private Double getOjbectValueOfFieldsAsFloat(JSONObject object, String key) {
        try {
            double a =  (Double.valueOf(String.valueOf(object.getJSONObject("fields").get(key))))/3600;
            DecimalFormat df = new DecimalFormat("#.#");
            a = Double.parseDouble(df.format(a));
            return a;
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return 0.0;
        }
    }

    private String getOjbectValueOfAssigneeFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").getJSONObject("assignee").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }
    
     private String getOjbectValueOfReporterFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").getJSONObject("reporter").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }
    private String getOjbectValueOfIssuetypeFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").getJSONObject("issuetype").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }
    
     private String getOjbectValueOfStatusFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").getJSONObject("status").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }

    private String getOjbectValueOfParentFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields").getJSONObject("parent").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }

    private String getOjbectValueOfIssueTypeOfParentFields(JSONObject object, String key) {
        try {
            return (String) object.getJSONObject("fields")
                    .getJSONObject("parent")
                    .getJSONObject("fields")
                    .getJSONObject("issuetype").get(key);
        } catch (Exception e) {
            System.out.println("Get Object Key=> " + e.getMessage());
            return "";
        }
    }

    public Carrier converJiraJSONToCarrier(String JSONString) {
        Carrier cout = new Carrier();
        return cout;
    }

    public String getConnectionOutputLines() throws QException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (this.getConnection().getInputStream())));
        String output = "";
        String rline = "";
        while ((output = br.readLine()) != null) {
            rline = rline + output;
        }
        return rline;
    }

    public void executeCommandInJira(String inputQuery) throws IOException {
        OutputStream os = this.getConnection().getOutputStream();
        os.write(inputQuery.getBytes());
        os.flush();

        this.getConnection().getResponseCode();
        this.getConnection().getResponseMessage();
    }

    public void setInitialization() throws QException {
        EntityTmJiraIntegration ent = new EntityTmJiraIntegration();
        ent.setStartLimit(0);
        ent.setEndLimit(1);
        Carrier c = EntityManager.select(ent);
        if (c.getTableRowCount(ent.toTableName()) > 0) {
            this.setAtlassianId(ent.getAtlassionId());
            this.setVersion(ent.getVersion());
            this.setUsername(ent.getUsername());
            this.setPassword(ent.getPassword());
        }
    }

    private String getCoreUrlWithIssue() {
        return "https://" + this.getAtlassianId() + ".atlassian.net/rest/api/"
                + this.getVersion() + "/issue";
    }

    private String getCoreUrlWithSearch() {
        return "https://" + this.getAtlassianId() + ".atlassian.net/rest/api/"
                + this.getVersion() + "/search";
    }

    public void setConnectionBody4Issue() throws QException, IOException {
        this.setInitialization();

        HttpURLConnection conn = null;
        String userpass = this.getUsername() + ":" + this.getPassword();
        String basicAuth = "Basic " + new String(Base64.getEncoder()
                .encode(userpass.getBytes()));

        URL url = new URL(this.getCoreUrlWithIssue());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(this.getHTTPType());
        conn.setRequestProperty("Content-Type", "application/json");
        this.setConnection(conn);
    }

    public void setConnectionBody4Search() throws QException, IOException {
        this.setInitialization();

        String userpass = this.getUsername() + ":" + this.getPassword();
        String basicAuth = "Basic " + new String(Base64.getEncoder()
                .encode(userpass.getBytes()));
        URL url = new URL(this.getCoreUrlWithSearch());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(this.getHTTPType());
        conn.setRequestProperty("Content-Type", "application/json");
        this.setConnection(conn);
    }

    private String getInputQuery4IssueCountOfProject() {
        return "{\"jql\":\"project = " + this.getProjectCode().replaceAll("\"", "'") + ""
                + "&issuetype != Epic\", \n"
                + "\"fields\":[\"total\"],\n"
                + "\"startAt\":0,\"maxResults\":5\n"
                + "}";
    }

    private String getInputQuery4IssueListOfProject(String startAt, String maxResult) {
        return " {\"jql\":\"project = " + this.getProjectCode().replaceAll("\"", "'") + ""
                + "&issuetype != Epic\",  \n"
                + "\"startAt\":" + startAt + ",\"maxResults\":" + maxResult + "\n"
                + "}";
    }

}
