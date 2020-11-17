/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.io;

import module.io.*;
import com.google.gson.JsonObject;
import controllerpool.ControllerPool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import label.CoreLabel;
import module.cr.entity.EntityCrUser;
import module.cr.entity.EntityCrUserList;
import module.tm.entity.EntityTmApiRelSetting;
import module.tm.entity.EntityTmBacklog;
import module.tm.entity.EntityTmBacklogDependency;
import module.tm.entity.EntityTmBacklogDependencyList;
import module.tm.entity.EntityTmBacklogHistory;
import module.tm.entity.EntityTmBacklogHistoryList;
import module.tm.entity.EntityTmBacklogList;
import module.tm.entity.EntityTmBacklogListWithTask;
import module.tm.entity.EntityTmBacklogTask;
import module.tm.entity.EntityTmBacklogTaskList;
import module.tm.entity.EntityTmBacklogTaskNotifier;
import module.tm.entity.EntityTmChangeReqLabel;
import module.tm.entity.EntityTmCommentFile;
import module.tm.entity.EntityTmInput;
import module.tm.entity.EntityTmInputDescription;
import module.tm.entity.EntityTmJiraIntegration;
import module.tm.entity.EntityTmTaskLabel;
import module.tm.entity.EntityTmNetwork;
import module.tm.entity.EntityTmNotification;
import module.tm.entity.EntityTmProgress;
import module.tm.entity.EntityTmProject;
import module.tm.entity.EntityTmProjectCountList;
import module.tm.entity.EntityTmProjectList;
import module.tm.entity.EntityTmProjectPermission;
import module.tm.entity.EntityTmProjectPermissionList;
import module.tm.entity.EntityTmBacklogDescription;
import module.tm.entity.EntityTmBcKeyPartner;
import module.tm.entity.EntityTmBcKeyResource;
import module.tm.entity.EntityTmBcSection;
import module.tm.entity.EntityTmBcSectionRel;
import module.tm.entity.EntityTmBcService;
import module.tm.entity.EntityTmBcServiceGroup;
import module.tm.entity.EntityTmBcServiceRelation;
import module.tm.entity.EntityTmBusinessCase;
import module.tm.entity.EntityTmDatabase;
import module.tm.entity.EntityTmDocument;
import module.tm.entity.EntityTmField;
import module.tm.entity.EntityTmFieldRelation;
import module.tm.entity.EntityTmInputTabComp;
import module.tm.entity.EntityTmInputTableComp;
import module.tm.entity.EntityTmProblemStatement;
import module.tm.entity.EntityTmProjectCanvasCard;
import module.tm.entity.EntityTmProjectCanvasZone;
import module.tm.entity.EntityTmRelBacklogAndLabel;
import module.tm.entity.EntityTmRelBacklogAndLabelList;
import module.tm.entity.EntityTmRelBacklogAndSprint;
import module.tm.entity.EntityTmRelBacklogAndSprintList;
import module.tm.entity.EntityTmRelTabBacklog;
import module.tm.entity.EntityTmRelTableInput;
import module.tm.entity.EntityTmRelTaskAndLabel;
import module.tm.entity.EntityTmRelTaskAndSprint;
import module.tm.entity.EntityTmServiceProcess;
import module.tm.entity.EntityTmServiceProcessAndStoryCard;
import module.tm.entity.EntityTmTable;
import module.tm.entity.EntityTmTask;
import module.tm.entity.EntityTmTaskAssignee;
import module.tm.entity.EntityTmTaskCategory;
import module.tm.entity.EntityTmTaskComment;
import module.tm.entity.EntityTmTaskCommentList;
import module.tm.entity.EntityTmTaskFile;
import module.tm.entity.EntityTmTaskLabelList;
import module.tm.entity.EntityTmTaskLabelListForTask;
import module.tm.entity.EntityTmTaskList;
import module.tm.entity.EntityTmTaskPriority;
import module.tm.entity.EntityTmTaskReporter;
import module.tm.entity.EntityTmTaskSprint;
import module.tm.entity.EntityTmTaskSprintList;
import module.tm.entity.EntityTmTaskSprintListForTask;
import module.tm.entity.EntityTmTaskStatus;
import module.tm.entity.EntityTmTaskType;
import module.tm.entity.EntityTmTestCase;
import module.tm.entity.EntityTmTestCaseStep;
import module.tm.entity.EntityTmTestCaseStepTrial;
import module.tm.entity.EntityTmTestCaseTrial;
import module.tm.entity.EntityTmTestScenario;
import module.tm.entity.EntityTmTestTrial;
import module.tm.entity.EntityTmTestTrialList;
import org.json.JSONException;
import org.json.JSONObject;
import resources.config.Config;
import utility.Carrier;
import utility.JIRA;
import utility.MailSender;
import utility.QDate;
import utility.QException;
import utility.SessionManager;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class IoModel {

    public static void main(String[] arg) {
        String st = "1";
        for (int i = 1; i <= 100; i++) {
            st += "0";
        }
    }

    //////////////////////////////////////////////////////
    public static Carrier coreSelect(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entity", cp.isKeyExist(carrier, "entity"));
        carrier.addController("entityDb", cp.isKeyExist(carrier, "entityDb"));
        if (carrier.hasError()) {
            return carrier;
        }

        Carrier cout = EntityManager.select(carrier);

        String tn = carrier.getEntityFullname();
        int rc = cout.getTableRowCount(tn);
        if (rc == 1) {
            String cols[] = cout.getTableColumnNames(tn);
            for (int i = 0; i < cols.length; i++) {
                String colName = cols[i];
                String val = cout.getValue(tn,0,colName).toString();
                cout.set(colName, val);
            }
        }

        return cout;
    }

    public static Carrier coreInsert(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entity", cp.isKeyExist(carrier, "entity"));
        carrier.addController("entityDb", cp.isKeyExist(carrier, "entityDb"));
        if (carrier.hasError()) {
            return carrier;
        }

        carrier = EntityManager.insert(carrier);
        return carrier;
    }

    public static Carrier coreDelete(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("entityDb", cp.isKeyExist(carrier, "entityDb"));
        carrier.addController("entity", cp.isKeyExist(carrier, "entity"));
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }

        EntityManager.delete(carrier);
        return carrier;
    }

    public static Carrier coreUpdate(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("updatedField", cp.isKeyExist(carrier, "updatedField"));
        carrier.addController("entityDb", cp.isKeyExist(carrier, "entityDb"));
        carrier.addController("entity", cp.isKeyExist(carrier, "entity"));
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }

        EntityManager.update(carrier);
        return carrier;
    }

}
