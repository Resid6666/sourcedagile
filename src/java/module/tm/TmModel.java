/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.tm;

import com.google.gson.JsonObject;
import controllerpool.ControllerPool;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
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
import javax.persistence.criteria.Path;
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
import utility.GeneralProperties;
import utility.JIRA;
import utility.MailSender;
import utility.QDate;
import utility.QException;
import utility.SessionManager;
import utility.sqlgenerator.EntityManager;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Locale;
import module.tm.entity.EntityTmGuiClass;
import module.tm.entity.EntityTmInputActionRel;
import module.tm.entity.EntityTmInputAttributes;
import module.tm.entity.EntityTmInputClassRelation;
import module.tm.entity.EntityTmJsCode;
import module.tm.entity.EntityTmRole;
import module.tm.entity.EntityTmRolePermission;
import module.tm.entity.EntityTmUserPermission;
import utility.QUtility;
import utility.sqlgenerator.SQLConnection;
import utility.sqlgenerator.SQLGenerator;

/**
 *
 * @author user
 */
public class TmModel {
    
    private static String BACKLOG_STATUS_NEW = "new";
    private static String BACKLOG_STATUS_ONGOING = "ongoing";
    private static String BACKLOG_STATUS_CLOSED = "closed";
    private static String BACKLOG_STATUS_RESOVLEd = "resolved";
    
    private static String BACKLOG_PRIORITY_HIGH = "high";
    private static String BACKLOG_PRIORITY_LOW = "low";
    private static String BACKLOG_PRIORITY_MEDIUM = "medium";
    private static String BACKLOG_PRIORITY_URGENT = "urgent";
    
    private static String BACKLOG_HISTORY_TYPE_INPUT_NEW = "input_new";
    private static String BACKLOG_HISTORY_TYPE_INPUT_UPDATE = "input_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_DELETE = "input_delete";
    private static String BACKLOG_HISTORY_TYPE_PROCESS_DESC_UPDATE = "process_desc_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_NEW = "input_desc_new";
    private static String BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE = "input_desc_delete";
    private static String BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_UPDATE = "input_desc_update";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_NEW = "task_type_new";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_DELETE = "task_type_delete";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_UPDATE = "task_type_update";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_SET_AS_ONGOING = "task_type_set_as_ongoing";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_CLOSE_TASK = "task_type_close_task";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_NOTIFY_BUG = "task_type_notify_bug";
    private static String BACKLOG_HISTORY_TYPE_TASK_TYPE_NOTIFY_UPDATE = "task_type_notify_update";
    private static String BACKLOG_HISTORY_TYPE_COMMENT_NEW = "task_type_comment_new";
    private static String BACKLOG_HISTORY_TYPE_STATUS_CHANGE = "task_type_status_change";
    private static String BACKLOG_HISTORY_TYPE_INPUT_TYPE_UPDATE = "input_type_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_ORDER_NO_UPDATE = "input_order_no_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_CELL_UPDATE = "input_cell_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_CSS_UPDATE = "input_css_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_CONTAINER_CSS_UPDATE = "input_container_css_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_CONTENT_UPDATE = "input_content_update";
    private static String BACKLOG_HISTORY_TYPE_INPUT_RELATION_ADDED = "input_relation_added";
    private static String BACKLOG_HISTORY_TYPE_INPUT_RELATION_DELETED = "input_relation_deleted";
    
    public static void main(String[] arg) {
        String st = "1";
        for (int i = 1; i <= 100; i++) {
            st += "0";
        }
    }

    //////////////////////////////////////////////////////
    /////////////////////////////////////////
    //// type code here
    ///////////////////////////////////////////////
    public Carrier getSprintNamesByTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogTaskId", cp.hasValue(carrier, "fkBacklogTaskId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTaskAndSprint ent = new EntityTmRelTaskAndSprint();
        ent.setFkBacklogTaskId(carrier.get("fkBacklogTaskId"));
        String sprintIds = EntityManager.select(ent).getValueLine(ent.toTableName(),
                EntityTmRelTaskAndSprint.FK_TASK_SPRINT_ID);
        
        if (sprintIds.length() > 6) {
            EntityTmTaskSprint entSp = new EntityTmTaskSprint();
            entSp.setId(sprintIds);
            carrier = EntityManager.select(entSp);
        }
        
        return carrier;
    }
    
    public Carrier getInputOutputListByBacklogId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setInputType("IN%IN%OUT");
        ent.addSortBy("inputName");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getApiList4Zad(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog entIn = new EntityTmBacklog();
        entIn.setFkProjectId(carrier.get("fkProjectId"));
        entIn.setIsApi("1");
        entIn.addSortBy("backlogName");
        Carrier cout = EntityManager.select(entIn);
        
        return cout;
    }
    
    public static Carrier getBacklogListBySendToApi(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputActionRel entIn = new EntityTmInputActionRel();
        entIn.setFkApiId(carrier.get("fkBacklogId"));
        Carrier cout = EntityManager.select(entIn);
        
        return cout;
    }
    
    public static Carrier getInputList4Relation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkBacklogId(carrier.get("fkBacklogId"));
        entIn.addSortBy(EntityTmInput.ORDER_NO);
        Carrier cout = EntityManager.select(entIn);
        
        return cout;
    }
    
    public static Carrier getParentTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTaskId", cp.hasValue(carrier, "fkTaskId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(carrier.get("fkTaskId"));
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        EntityTmBacklogTask ent1 = new EntityTmBacklogTask();
        if (ent.getFkParentTaskId().length() > 1) {
            ent1.setId(ent.getFkParentTaskId());
            ent1.setEndLimit(0);
            EntityManager.select(ent1);
            EntityManager.mapEntityToCarrier(ent1, carrier, true);
        }
        
        return carrier;
    }
    
    public static Carrier getChildTaskList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTaskId", cp.hasValue(carrier, "fkTaskId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkParentTaskId(carrier.get("fkTaskId"));
        ent.addSortBy("taskName");
        
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier createChildTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTaskId", cp.hasValue(carrier, "fkTaskId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(carrier.get("fkTaskId"));
        EntityManager.select(ent);
        
        EntityTmBacklogTask entNew = new EntityTmBacklogTask();
        entNew.setTaskName(ent.getTaskName());
        entNew.setCreatedBy(SessionManager.getCurrentUserId());
        entNew.setCreatedDate(QDate.getCurrentDate());
        entNew.setCreatedTime(QDate.getCurrentTime());
        entNew.setFkAssigneeId("");
        entNew.setFkBacklogId(ent.getFkBacklogId());
        entNew.setFkProjectId(ent.getFkProjectId());
        entNew.setFkTaskTypeId(ent.getFkTaskTypeId());
        entNew.setTaskNature(ent.getTaskNature());
        entNew.setTaskStatus("new");
        entNew.setTaskOrderNo("11");
        entNew.setFkParentTaskId(ent.getId());
        entNew.setOrderNoSeq(nextTaskOrderNoSeq(ent.getFkProjectId()));
        EntityManager.insert(entNew);
        
        EntityTmTaskComment entCmt = new EntityTmTaskComment();
        entCmt.setFkTaskId(ent.getId());
        Carrier cr = EntityManager.select(entCmt);
        String tn = entCmt.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, entCmt);
            String oldCommentId = entCmt.getId();
            entCmt.setFkTaskId(entNew.getId());
            EntityManager.insert(entCmt);
            
            EntityTmCommentFile entCmtFile = new EntityTmCommentFile();
            entCmtFile.setFkCommentId(oldCommentId);
            Carrier crFile = EntityManager.select(entCmtFile);
            String tnT = entCmtFile.toTableName();
            int rc1 = crFile.getTableRowCount(tnT);
            for (int j = 0; j < rc1; j++) {
                EntityManager.mapCarrierToEntity(crFile, tnT, j, entCmtFile);
                entCmtFile.setFkCommentId(entCmt.getId());
                EntityManager.insert(entCmtFile);
                
            }
            
        }

        //commentleride copy paste olmalidir
        getTaskList4Short(entNew.getId()).copyTo(carrier);
        
        sendMailNotificationOnDublicate(ent.getId(), ent.getFkBacklogId());
        
        carrier.set("fkTaskId", entNew.getId());
        
        return carrier;
    }
    
    public Carrier copyClassCodeAction(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fnList", cp.hasValue(carrier, "fnList"));
        carrier.addController("projectList", cp.hasValue(carrier, "projectList"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String fnList[] = carrier.get("fnList").split(",");
        String projectList[] = carrier.get("projectList").split(",");
        
        for (String fn : fnList) {
            if (fn.length() == 0) {
                continue;
            }
            
            EntityTmGuiClass ent = new EntityTmGuiClass();
            ent.setId(fn);
            EntityManager.select(ent);
            
            for (String pro : projectList) {
                if (pro.length() == 0) {
                    continue;
                }
                
                ent.setFkProjectId(pro);
                EntityManager.insert(ent);
            }
        }
        
        return carrier;
    }
    
    public Carrier copyJSCodeAction(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fnList", cp.hasValue(carrier, "fnList"));
        carrier.addController("projectList", cp.hasValue(carrier, "projectList"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String fnList[] = carrier.get("fnList").split(",");
        String projectList[] = carrier.get("projectList").split(",");
        
        for (String fn : fnList) {
            if (fn.length() == 0) {
                continue;
            }
            
            EntityTmJsCode ent = new EntityTmJsCode();
            ent.setId(fn);
            EntityManager.select(ent);
            
            for (String pro : projectList) {
                if (pro.length() == 0) {
                    continue;
                }
                
                ent.setFkProjectId(pro);
                EntityManager.insert(ent);
            }
        }
        
        return carrier;
    }
    
    public Carrier getModulePermissionListByOwn(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId("-1");
        ent.setPermissionType("mdl");
        ent.setFkUserId(SessionManager.getCurrentUserId());
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier changeProjectTriggerStoryCardInMenu(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("backlogId", cp.hasValue(carrier, "backlogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProject entRole = new EntityTmProject();
        entRole.setId(carrier.get("id"));
        EntityManager.select(entRole);
        
        entRole.setFkTriggerBacklogId(carrier.get("backlogId"));
        EntityManager.update(entRole);
        
        return carrier;
    }
    
    public Carrier changeProjectShowInMenu(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("showValue", cp.hasValue(carrier, "showValue"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProject entRole = new EntityTmProject();
        entRole.setId(carrier.get("id"));
        EntityManager.select(entRole);
        
        entRole.setShowInMenu(carrier.get("showValue"));
        EntityManager.update(entRole);
        
        return carrier;
    }
    
    public Carrier changeProjectIconInMenu(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("icon", cp.hasValue(carrier, "icon"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProject entRole = new EntityTmProject();
        entRole.setId(carrier.get("id"));
        EntityManager.select(entRole);
        
        entRole.setMenuIcon(carrier.get("icon"));
        EntityManager.update(entRole);
        
        return carrier;
    }
    
    public Carrier addRolePermissionToUser(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkRoleId", cp.hasValue(carrier, "fkRoleId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRole entRole = new EntityTmRole();
        entRole.setId(carrier.get("fkRoleId"));
        EntityManager.select(entRole);
        
        EntityTmUserPermission entUser = new EntityTmUserPermission();
        entUser.setFkProjectId(entRole.getFkProjectId());
        entUser.setFkUserId(carrier.get("fkUserId"));
        entUser.setPermissionType("sc");
        String ids = EntityManager.select(entUser).getValueLine(entUser.toTableName(), "id");
        if (ids.length() > 2) {
            entUser.setId(ids);
            EntityManager.delete(entUser);
        }
        
        EntityTmRolePermission ent = new EntityTmRolePermission();
        ent.setFkRoleId(carrier.get("fkRoleId"));
        Carrier crt = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = crt.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crt, tn, i, entUser);
            entUser.setFkUserId(carrier.get("fkUserId"));
            EntityManager.insert(entUser);
        }
        
        return carrier;
    }
    
    public Carrier addRolePermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkRoleId", cp.hasValue(carrier, "fkRoleId"));
        carrier.addController("relationId", cp.hasValue(carrier, "relationId"));
        carrier.addController("permissionType", cp.hasValue(carrier, "permissionType"));
        carrier.addController("accessType", cp.hasValue(carrier, "accessType"));
//        carrier.addController("exceptInputs", cp.hasValue(carrier, "exceptInputs"));

        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRolePermission ent = new EntityTmRolePermission();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setExceptInputs(carrier.get("exceptInputs"));
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    public Carrier deletePermissionRole(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRole ent = new EntityTmRole();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public Carrier updatePermissionRole(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRole ent = new EntityTmRole();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public Carrier addPermissionRole(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("roleName", cp.hasValue(carrier, "roleName"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRole ent = new EntityTmRole();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setRoleName(carrier.get("roleName"));
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    public Carrier getPermissionRoleByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRole ent = new EntityTmRole();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy("roleName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier addBacklogExceptInputPermissionById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkInputId", cp.hasValue(carrier, "fkInputId"));
        carrier.addController("accessType", cp.hasValue(carrier, "accessType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setRelationId(carrier.get("fkBacklogId"));
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            ent.setPermissionType("sc");
            ent.setAccessType("y");
            EntityManager.insert(ent);
        }
        
        if (ent.getId().length() > 0) {
            String ids = ent.getExceptInputs();
            
            if (carrier.get("accessType").equals("n")) {
                ids = ids.contains(carrier.get("fkInputId"))
                        ? ids
                        : ids + "," + carrier.get("fkInputId");
            } else if (carrier.get("accessType").equals("y")) {
                ids = ids.replaceAll(carrier.get("fkInputId"), "");
            }
            ent.setExceptInputs(ids);
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier getBacklogPermissionById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setRelationId(carrier.get("fkBacklogId"));
        ent.setEndLimit(0);
        carrier = EntityManager.select(ent);
        EntityManager.mapEntityToCarrier(ent, carrier, true);
        
        return carrier;
    }
    
    public Carrier getInputListByBacklogId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setInputType("IN");
        ent.addSortBy("inputName");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier addApiPermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("relationId", cp.hasValue(carrier, "relationId"));
        carrier.addController("accessType", cp.hasValue(carrier, "accessType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setRelationId(carrier.get("relationId"));
        ent.setPermissionType("api");
        ent.setEndLimit("0");
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.insert(ent);
        } else {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier addBacklogPermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("relationId", cp.hasValue(carrier, "relationId"));
        carrier.addController("accessType", cp.hasValue(carrier, "accessType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setRelationId(carrier.get("relationId"));
        ent.setPermissionType("sc");
        ent.setEndLimit("0");
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.insert(ent);
        } else {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier getApiPermissionList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setPermissionType("api");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier getBacklogPermissionList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setPermissionType("sc");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier getModulePermissionList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId("-1");
        ent.setPermissionType("mdl");
        ent.setFkUserId(carrier.get("fkUserId"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier addModulePermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkUserId", cp.hasValue(carrier, "fkUserId"));
        carrier.addController("relationId", cp.hasValue(carrier, "relationId"));
        carrier.addController("accessType", cp.hasValue(carrier, "accessType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmUserPermission ent = new EntityTmUserPermission();
        ent.setFkProjectId("-1");
        ent.setFkUserId(carrier.get("fkUserId"));
        ent.setRelationId(carrier.get("relationId"));
        ent.setPermissionType("mdl");
        ent.setEndLimit("0");
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.insert(ent);
        } else {
            ent.setAccessType(carrier.get("accessType"));
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier showInputTableColumnEntireComponent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputTableId", cp.isKeyExist(carrier, "fkInputTableId"));
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTableInput ent = new EntityTmRelTableInput();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setFkTableId(carrier.get("fkInputTableId"));
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        String showColumn = "0";
        if (ent.getShowColumn().equals("1")) {
            showColumn = "0";
        } else {
            showColumn = "1";
        }
        
        ent.setShowColumn(showColumn);
        EntityManager.update(ent);
        carrier.set("showColumn", showColumn);
        
        getTableListOfInput(ent.getFkTableId(), ent.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier showInputTableColumnItselfComponent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputTableId", cp.isKeyExist(carrier, "fkInputTableId"));
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTableInput ent = new EntityTmRelTableInput();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setFkTableId(carrier.get("fkInputTableId"));
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        String showColumnName = "0";
        if (ent.getShowColumnName().equals("1")) {
            showColumnName = "0";
        } else {
            showColumnName = "1";
        }
        
        ent.setShowColumnName(showColumnName);
        EntityManager.update(ent);
        carrier.set("showColumnName", showColumnName);
        
        getTableListOfInput(ent.getFkTableId(), ent.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier getGlobalFunctionNamesByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.addSortBy("fnDescription");
        ent.setFnType("core");
        ent.setSortByAsc(true);
        ent.setIsGlobal("1");
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier getFunctionNamesByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.addSortBy("fnDescription");
        ent.setFnType("core");
        ent.setSortByAsc(true);
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier getProjectDescriptionByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.addSortBy("orderNo");
        ent.setSortByAsc(true);
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier updateOrderNoBacklogDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("sourcedId", cp.isKeyExist(carrier, "sourcedId"));
        carrier.addController("targetId", cp.isKeyExist(carrier, "targetId"));
        carrier.addController("oldOrderNo", cp.isKeyExist(carrier, "oldOrderNo"));
        carrier.addController("newOrderNo", cp.isKeyExist(carrier, "newOrderNo"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("sourcedId"));
        EntityManager.select(ent);
        ent.setOrderNo(carrier.get("newOrderNo"));
        EntityManager.update(ent);
        
        EntityTmBacklogDescription ent1 = new EntityTmBacklogDescription();
        ent1.setId(carrier.get("targetId"));
        EntityManager.select(ent1);
        ent1.setOrderNo(carrier.get("oldOrderNo"));
        EntityManager.update(ent1);
        
        return carrier;
    }
    
    public static Carrier getInputActionRelList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputActionRel ent = new EntityTmInputActionRel();
        ent.setFkInputId(carrier.get("fkInputId"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getInputActionRelListByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputActionRel ent = new EntityTmInputActionRel();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier deleteInputActionRel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputActionRel ent = new EntityTmInputActionRel();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier insertNewInputActionRel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        carrier.addController("fkApiId", cp.isKeyExist(carrier, "fkApiId"));
        carrier.addController("actionType", cp.isKeyExist(carrier, "actionType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputActionRel ent = new EntityTmInputActionRel();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setActionType(carrier.get("actionType"));
        EntityManager.select(ent);
        if (ent.getFkApiId().length() > 1) {
            ent.setFkApiId(carrier.get("fkApiId"));
            EntityManager.update(ent);
        } else {
            ent.setFkApiId(carrier.get("fkApiId"));
            EntityManager.insert(ent);
        }
        
        return carrier;
    }
    
    public static Carrier getInputClassRelByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputClassRelation ent = new EntityTmInputClassRelation();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier insertNewJsCode(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fnDescription", cp.isKeyExist(carrier, "fnDescription"));
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFnDescription(carrier.get("fnDescription"));
        ent.setFnType("core");
        ent.setIsActive("A");
        ent.setFnCoreName(carrier.get("fnCoreName"));
        ent.setFnCoreInput(carrier.get("fnCoreInput"));
        ent.setFnBody(carrier.get("fnBody"));
        
        EntityManager.insert(ent);
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier getJsCodeList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy(EntityTmJsCode.FN_DESCRIPTION);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getJsGlobalCodeList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setIsGlobal("1");
        ent.addSortBy(EntityTmJsCode.FN_DESCRIPTION);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getJsCodeById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setId(carrier.get("id"));
        ent.addSortBy(EntityTmJsCode.FN_DESCRIPTION);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        EntityManager.mapEntityToCarrier(ent, carrier, true);
        
        return carrier;
    }
    
    public static Carrier updateJSCode4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier deleteJsCode(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier deleteGuiClass(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier updateGuiClassBody(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        carrier.addController("classBody", cp.isKeyExist(carrier, "classBody"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setClassBody(carrier.get("classBody"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier updateGuiClassName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        carrier.addController("className", cp.isKeyExist(carrier, "className"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setClassName(carrier.get("className"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier removeInputClassRel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputClassRelation ent = new EntityTmInputClassRelation();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier getInputCompClassList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        carrier.addController("relType", cp.isKeyExist(carrier, "relType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputClassRelation ent = new EntityTmInputClassRelation();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setRelType(carrier.get("relType"));
        Carrier cr = EntityManager.select(ent);
        
        String classIds = cr.getValueLine(ent.toTableName(), "fkClassId");
        if (classIds.length() > 1) {
            EntityTmGuiClass entCls = new EntityTmGuiClass();
            entCls.setId(classIds);
            Carrier crCls = EntityManager.select(entCls);
            cr.mergeCarrier(ent.toTableName(), "fkClassId", crCls, entCls.toTableName(), "id", new String[]{"className"});
            cr.mergeCarrier(ent.toTableName(), "fkClassId", crCls, entCls.toTableName(), "id", new String[]{"classBody"});
        }
        
        return cr;
    }
    
    public static Carrier getGuiClassList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        Carrier cr = EntityManager.select(ent);
        
        return cr;
    }
    
    public static Carrier getGuiClassListByProject4Combo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setClassName(".%%");
        Carrier cr = EntityManager.select(ent);
        
        Carrier crOut = new Carrier();
        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            if (ent.getClassName().trim().contains(" ")) {
                continue;
            }
            String className = ent.getClassName().replaceAll("\\.", "");
            ent.setClassName(className);
            EntityManager.mapEntityToCarrier(ent, crOut, tn, true);
        }
        
        return crOut;
    }
    
    public static Carrier getAllGuiClassByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setSortByAsc(true);
        ent.addSortBy("className");
        Carrier cr = EntityManager.select(ent);
        
        return cr;
    }
    
    public static Carrier getGuiClassById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setId(carrier.get("id"));
        Carrier cr = EntityManager.select(ent);
        EntityManager.mapEntityToCarrier(ent, cr, true);
        return cr;
    }
    
    public static Carrier addGuiClassToInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        carrier.addController("fkClassId", cp.isKeyExist(carrier, "fkClassId"));
        carrier.addController("relType", cp.isKeyExist(carrier, "relType"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputClassRelation ent = new EntityTmInputClassRelation();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setFkClassId(carrier.get("fkClassId"));
        ent.setRelType(carrier.get("relType"));
        Carrier cr = EntityManager.select(ent);
        
        if (cr.getTableRowCount(ent.toTableName()) == 0) {
            EntityManager.insert(ent);
        }
        
        return carrier;
    }
    
    public static Carrier insertNewGuiClass(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("className", cp.isKeyExist(carrier, "className"));
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmGuiClass ent = new EntityTmGuiClass();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setClassName(carrier.get("className"));
        Carrier cr = EntityManager.select(ent);
        
        if (cr.getTableRowCount(ent.toTableName()) == 0) {
            EntityManager.insert(ent);
        }
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier getInputAttributeListByBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.isKeyExist(carrier, "fkBacklogId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setAttrType("comp");
        ent.setAttrName("sa-selectedfield");
        Carrier cr = EntityManager.select(ent);
        
        return cr;
    }
    
    public static Carrier getInputAttributeListByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        Carrier cr = EntityManager.select(ent);
        
        return cr;
    }
    
    public static Carrier getInputAttributeList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setAttrType(carrier.get("attrType"));
        Carrier cr = EntityManager.select(ent);
        
        return cr;
    }
    
    public static Carrier deleteInputAttribute(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier insertNewInputAttribute(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.isKeyExist(carrier, "fkInputId"));
        carrier.addController("attrName", cp.isKeyExist(carrier, "attrName"));
        carrier.addController("attrValue", cp.isKeyExist(carrier, "attrValue"));
        carrier.addController("attrType", cp.isKeyExist(carrier, "attrType"));
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        carrier.addController("fkBacklogId", cp.isKeyExist(carrier, "fkBacklogId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setAttrName(carrier.get("attrName"));
        Carrier cr = EntityManager.select(ent);
        
        if (cr.getTableRowCount(ent.toTableName()) == 0) {
            ent.setAttrValue(carrier.get("attrValue"));
            ent.setAttrType(carrier.get("attrType"));
            ent.setFkProjectId(carrier.get("fkProjectId"));
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            EntityManager.insert(ent);
        } else {
            String st = ent.getAttrValue();
            ent.setAttrValue(st + "," + carrier.get("attrValue"));
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public static Carrier addFieldToServer(Carrier carrier) throws QException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField entField = new EntityTmField();
        entField.setId(carrier.get("id"));
        entField.setEndLimit("1");
        EntityManager.select(entField);
        
        EntityTmTable ent = new EntityTmTable();
        ent.setId(entField.getFkTableId());
        ent.setEndLimit("1");
        EntityManager.select(ent);
        
        EntityTmDatabase entDb = new EntityTmDatabase();
        entDb.setId(entField.getFkDbId());
        entDb.setEndLimit(1);
        EntityManager.select(entDb);
        
        String db = entDb.getDbName();
        String tn = ent.getTableName();
        
        String coreTn = db.toLowerCase() + "_" + tn.toLowerCase();
        coreTn = coreTn.toLowerCase(Locale.ENGLISH);
        
        String ln = " " + entField.getFieldName();
        ln += " " + entField.getFieldType();
        ln += (entField.getFieldLength().trim().length() > 0)
                ? " (" + entField.getFieldLength() + ")"
                : "";
        ln += " " + entField.getExtraParam();
        
        String fileLn = entField.getFieldName() + "::";
        fileLn += (entField.getFieldType().trim().length() > 0)
                ? entField.getFieldType() + "::"
                : "-::";
        fileLn += (entField.getFieldLength().trim().length() > 0)
                ? entField.getFieldLength() + "::"
                : "-::";
        fileLn += (entField.getExtraParam().trim().length() > 0)
                ? entField.getExtraParam() + "::"
                : "-::";
        fileLn += "\n";
        
        String lnFinal = "ALTER TABLE  " + coreTn + "  ADD  " + ln + ";";
        
        try {
            EntityManager.executeUpdateByQuery(lnFinal);
            
            updateFileContextOfEntity(entField.getFkTableId());
        } catch (Exception e) {
            carrier.set("err", e.getMessage());
        }
        return carrier;
    }
    
    public static Carrier alterFieldOnServer(Carrier carrier) throws QException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField entField = new EntityTmField();
        entField.setId(carrier.get("id"));
        entField.setEndLimit("1");
        EntityManager.select(entField);
        
        EntityTmTable ent = new EntityTmTable();
        ent.setId(entField.getFkTableId());
        ent.setEndLimit("1");
        EntityManager.select(ent);
        
        EntityTmDatabase entDb = new EntityTmDatabase();
        entDb.setId(entField.getFkDbId());
        entDb.setEndLimit(1);
        EntityManager.select(entDb);
        
        String db = entDb.getDbName();
        String tn = ent.getTableName();
        
        String coreTn = db.toLowerCase() + "_" + tn.toLowerCase();
        coreTn = coreTn.toLowerCase(Locale.ENGLISH);
        
        String ln = " " + entField.getFieldName();
        ln += " " + entField.getFieldType();
        ln += (entField.getFieldLength().trim().length() > 0)
                ? " (" + entField.getFieldLength() + ")"
                : "";
        ln += " " + entField.getExtraParam();
        
        String fileLn = entField.getFieldName() + "::";
        fileLn += (entField.getFieldType().trim().length() > 0)
                ? entField.getFieldType() + "::"
                : "-::";
        fileLn += (entField.getFieldLength().trim().length() > 0)
                ? entField.getFieldLength() + "::"
                : "-::";
        fileLn += (entField.getExtraParam().trim().length() > 0)
                ? entField.getExtraParam() + "::"
                : "-::";
        fileLn += "\n";
        
        String lnFinal = "ALTER TABLE  " + coreTn + "   MODIFY  COLUMN  " + ln + ";";
        
        try {
            EntityManager.executeUpdateByQuery(lnFinal);
            
            updateFileContextOfEntity(entField.getFkTableId());
        } catch (Exception e) {
            carrier.set("err", e.getMessage());
        }
        return carrier;
    }
    
    private static void updateFileContextOfEntity(String tableId) throws QException, UnsupportedEncodingException {
        if (tableId.length() == 0) {
            return;
        }
        
        EntityTmTable ent = new EntityTmTable();
        ent.setId(tableId);
        EntityManager.select(ent);
        
        EntityTmDatabase entDb = new EntityTmDatabase();
        entDb.setId(ent.getFkDbId());
        entDb.setEndLimit(1);
        EntityManager.select(entDb);
        
        EntityTmField entField = new EntityTmField();
        entField.setFkTableId(ent.getId());
        Carrier cr = EntityManager.select(entField);
        
        String db = entDb.getDbName();
        String tn = ent.getTableName();
        
        String coreTn = db + "_" + tn;
        coreTn = coreTn.toLowerCase(Locale.ENGLISH);
        
        int rc = cr.getTableRowCount(entField.toTableName());
        String fileLn = "";
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, entField.toTableName(), i, entField);
            
            fileLn += entField.getFieldName() + "::";
            fileLn += (entField.getFieldType().trim().length() > 0)
                    ? entField.getFieldType() + "::"
                    : "-::";
            fileLn += (entField.getFieldLength().trim().length() > 0)
                    ? entField.getFieldLength() + "::"
                    : "-::";
            fileLn += (entField.getExtraParam().trim().length() > 0)
                    ? entField.getExtraParam() + "::"
                    : "-::";
            fileLn += "\n";
        }
        
        try {
            
            String filename = Config.getProperty("entity.path") + SessionManager.getCurrentDomain() + "/" + db.toLowerCase() + "/" + tn.toLowerCase();
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.append(fileLn);
            bw.close();
        } catch (Exception e) {
        }
        
    }
    
    public static Carrier createTableOnServer(Carrier carrier) throws QException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("tableId", cp.isKeyExist(carrier, "tableId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        ent.setId(carrier.get("tableId"));
        EntityManager.select(ent);
        
        EntityTmDatabase entDb = new EntityTmDatabase();
        entDb.setId(ent.getFkDbId());
        entDb.setEndLimit(1);
        EntityManager.select(entDb);
        
        EntityTmField entField = new EntityTmField();
        entField.setFkTableId(ent.getId());
        Carrier cr = EntityManager.select(entField);
        
        String db = entDb.getDbName();
        String tn = ent.getTableName();
        
        String coreTn = db + "_" + tn;
        coreTn = coreTn.toLowerCase(Locale.ENGLISH);
        
        String ln = "CREATE TABLE IF NOT EXISTS " + coreTn + " (";
        
        int rc = cr.getTableRowCount(entField.toTableName());
        String fileLn = "";
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, entField.toTableName(), i, entField);
            ln += " " + entField.getFieldName();
            ln += " " + entField.getFieldType();
            ln += (entField.getFieldLength().trim().length() > 0)
                    ? " (" + entField.getFieldLength() + ")"
                    : "";
            ln += " " + entField.getExtraParam();
            ln += " , \n";
            
            fileLn += entField.getFieldName() + "::";
            fileLn += (entField.getFieldType().trim().length() > 0)
                    ? entField.getFieldType() + "::"
                    : "-::";
            fileLn += (entField.getFieldLength().trim().length() > 0)
                    ? entField.getFieldLength() + "::"
                    : "-::";
            fileLn += (entField.getExtraParam().trim().length() > 0)
                    ? entField.getExtraParam() + "::"
                    : "-::";
            fileLn += "\n";
        }
        ln += "  PRIMARY KEY (ID)";
        ln += " ) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci; ";
        
        try {
            EntityManager.executeUpdateByQuery(ln);
            
            GeneralProperties prop = new GeneralProperties();
            String filename = Config.getProperty("entity.path") + SessionManager.getCurrentDomain() + "/" + db.toLowerCase() + "/" + tn.toLowerCase();
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.append(fileLn);
            bw.close();
            
        } catch (Exception e) {
            carrier.set("err", e.getMessage());
        }
        
        return carrier;
    }
    
    public static Carrier commitDatabaseOnServer(Carrier carrier) throws QException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbname", cp.isKeyExist(carrier, "dbname"));
        carrier.addController("dbid", cp.isKeyExist(carrier, "dbid"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String filename = Config.getProperty("entity.path") + SessionManager.getCurrentDomain() + "/" + carrier.get("dbname") + "/";
        filename = filename.trim().toLowerCase().replaceAll(" ", "");
        File theDir = new File(filename);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        
        return carrier;
    }
    
    public static Carrier assignBacklogTaskTo(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("taskId", cp.isKeyExist(carrier, "taskId"));
        carrier.addController("assigneeList", cp.isKeyExist(carrier, "assigneeList"));
        carrier.addController("taskComment", cp.isKeyExist(carrier, "taskComment"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String assineeList = carrier.get("assigneeList");
        String taskId = carrier.get("taskId");
        String comment = carrier.get("taskComment");
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(taskId);
        EntityManager.select(ent);
        
        String orderNo = nextTaskLOrderNo(ent.getFkProjectId(), ent.getFkBacklogId());
        String status = "new";
        
        if (assineeList.trim().length() > 3) {
            String[] assignees = assineeList.split("\\|");
            for (int k = 0; k < assignees.length; k++) {
                String[] assignedIds = assignees[k].split("\\:");
                String assigneeId = assignedIds[0];
                String tasktypeId = assignedIds[1];
                
                EntityTmBacklogTask entNew = new EntityTmBacklogTask();
                entNew.setTaskName(ent.getTaskName());
                entNew.setFkParentTaskId(taskId);
                entNew.setCreatedBy(SessionManager.getCurrentUserId());
                entNew.setCreatedDate(QDate.getCurrentDate());
                entNew.setCreatedTime(QDate.getCurrentTime());
                entNew.setFkAssigneeId(assigneeId);
                entNew.setFkBacklogId(ent.getFkBacklogId());
                entNew.setFkProjectId(ent.getFkProjectId());
                entNew.setFkTaskTypeId(tasktypeId);
                entNew.setTaskNature(ent.getTaskNature());
                entNew.setTaskStatus(status);
                entNew.setTaskOrderNo(orderNo);
                entNew.setOrderNoSeq(nextTaskOrderNoSeq(ent.getFkProjectId()));
                EntityManager.insert(entNew);
                
                EntityTmTaskComment entCmt = new EntityTmTaskComment();
                entCmt.setFkTaskId(ent.getId());
                Carrier cr = EntityManager.select(entCmt);
                
                if (comment.trim().length() > 0) {
                    Carrier c = new Carrier();
                    c.setValue(EntityTmTaskComment.FK_TASK_ID, entNew.getId());
                    c.setValue(EntityTmTaskComment.COMMENT, comment);
                    c.setValue(EntityTmTaskComment.FK_BACKLOG_ID, ent.getFkBacklogId());
                    c.set("commentType", "stask");
                    insertNewComment(c);
                }
                
                String tn = entCmt.toTableName();
                int rc = cr.getTableRowCount(tn);
                for (int i = 0; i < rc; i++) {
                    EntityManager.mapCarrierToEntity(cr, tn, i, entCmt);
                    String oldCommentId = entCmt.getId();
                    entCmt.setFkTaskId(entNew.getId());
                    EntityManager.insert(entCmt);
                    
                    EntityTmCommentFile entCmtFile = new EntityTmCommentFile();
                    entCmtFile.setFkCommentId(oldCommentId);
                    Carrier crFile = EntityManager.select(entCmtFile);
                    String tnT = entCmtFile.toTableName();
                    int rc1 = crFile.getTableRowCount(tnT);
                    for (int j = 0; j < rc1; j++) {
                        EntityManager.mapCarrierToEntity(crFile, tnT, j, entCmtFile);
                        entCmtFile.setFkCommentId(entCmt.getId());
                        EntityManager.insert(entCmtFile);
                        
                    }
                }
                
            }
        }

        //commentleride copy paste olmalidir
//            getTaskList4Short(entNew.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getInputDescHistoryBeforeLastChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputDescriptionId", cp.isKeyExist(carrier, "fkInputDescriptionId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
        ent.setRelationId(carrier.get("fkInputDescriptionId"));
        ent.setEndLimit(1);
        Carrier cr = EntityManager.select(ent);
        
        carrier.set("lastValue", cr.getValue(ent.toTableName(), 1, EntityTmBacklogHistory.PARAM_2));
        
        return carrier;
    }
    
    public Carrier addNewDetailedTaskAction(Carrier carrier) throws QException, Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("taskName", cp.isKeyExist(carrier, "taskName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String fkProjectId = carrier.get("fkProjectId");
        String fkBacklogId = carrier.get("fkBacklogId");
        String taskName = carrier.get("taskName");
        String taskNature = carrier.get("taskNature");
        String taskComment = carrier.get("taskComment");
        String assineeList = carrier.get("assineeList");
        String fileList = carrier.get("fileList");
        
        if (assineeList.trim().length() > 3) {
            String[] assignees = assineeList.split("\\|");
            for (int i = 0; i < assignees.length; i++) {
                String[] assignedIds = assignees[i].split("\\:");
                String assigneeId = assignedIds[0];
                String tasktypeId = assignedIds[1];
                
                String orderNo = nextTaskLOrderNo(fkProjectId, fkBacklogId);
                String status = "new";
                
                EntityTmBacklogTask ent = new EntityTmBacklogTask();
                ent.setTaskName(taskName);
                ent.setTaskStatus(status);
                ent.setFkBacklogId(fkBacklogId);
                ent.setFkProjectId(fkProjectId);
                ent.setTaskOrderNo(orderNo);
                ent.setFkTaskTypeId(tasktypeId);
                ent.setFkAssigneeId(assigneeId);
                ent.setSpentHours("0");
                ent.setCompletedDuration("0");
                ent.setCreatedBy(SessionManager.getCurrentUserId());
                ent.setCreatedDate(QDate.getCurrentDate());
                ent.setCreatedTime(QDate.getCurrentTime());
                ent.setTaskNature(taskNature);
                ent.setOrderNoSeq(nextTaskOrderNoSeq(fkProjectId));
                EntityManager.insert(ent);
                carrier.setValue("id", ent.getId());
                
                if (taskComment.trim().length() > 0) {
                    Carrier c = new Carrier();
                    String desc = taskComment;
                    c.setValue(EntityTmTaskComment.FK_TASK_ID, ent.getId());
                    c.setValue(EntityTmTaskComment.COMMENT, desc);
                    c.setValue(EntityTmTaskComment.FK_BACKLOG_ID, fkBacklogId);
                    c.setValue("fileName", fileList);
                    insertNewComment(c);
                }
                
                setBacklogStatus(ent.getFkBacklogId());
                
                sendMailNotificationOnCreate(ent.getId(), ent.getFkBacklogId());
            }
        } else {
            
            String orderNo = nextTaskLOrderNo(fkProjectId, fkBacklogId);
            String status = "new";
            
            EntityTmBacklogTask ent = new EntityTmBacklogTask();
            ent.setTaskName(taskName);
            ent.setTaskStatus(status);
            ent.setFkBacklogId(fkBacklogId);
            ent.setFkProjectId(fkProjectId);
            ent.setTaskOrderNo(orderNo);
            ent.setSpentHours("0");
            ent.setCompletedDuration("0");
            ent.setCreatedBy(SessionManager.getCurrentUserId());
            ent.setCreatedDate(QDate.getCurrentDate());
            ent.setCreatedTime(QDate.getCurrentTime());
            ent.setTaskNature(taskNature);
            ent.setOrderNoSeq(nextTaskOrderNoSeq(fkProjectId));
            EntityManager.insert(ent);
            carrier.setValue("id", ent.getId());
            
            if (taskComment.trim().length() > 0) {
                Carrier c = new Carrier();
                String desc = taskComment;
                c.setValue(EntityTmTaskComment.FK_TASK_ID, ent.getId());
                c.setValue(EntityTmTaskComment.COMMENT, desc);
                c.setValue(EntityTmTaskComment.FK_BACKLOG_ID, fkBacklogId);
                c.setValue("fileName", fileList);
                insertNewComment(c);
            }
            
            setBacklogStatus(ent.getFkBacklogId());
            sendMailNotificationOnCreate(ent.getId(), ent.getFkBacklogId());
        }

//         getTaskList4Short(ent.getId()).copyTo(carrier);
//            getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
//            assigneSprintToTask(ent.getId(), carrier.get("sprintList"));
        return carrier;
    }
    
    public static Carrier updateTableField4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier insertNewServiceProcessAndBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceProcessId", cp.hasValue(carrier, "fkServiceProcessId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcessAndStoryCard ent = new EntityTmServiceProcessAndStoryCard();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkServiceProcessId(carrier.get("fkServiceProcessId"));
        EntityManager.select(ent);
        if (ent.getId().length() == 0) {
            EntityManager.insert(ent);
        }
        
        return carrier;
    }
    
    public static Carrier getServiceProcessAndBacklogList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceProcessId", cp.hasValue(carrier, "fkServiceProcessId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String ln = "SELECT\n"
                + "    FK_PROJECT_ID,\n"
                + "    (SELECT PROJECT_NAME FROM " + SessionManager.getCurrentDomain() + ".TM_PROJECT WHERE STATUS='A' AND ID=T.FK_PROJECT_ID LIMIT 0,1) PROJECT_NAME,\n"
                + "    FK_BACKLOG_ID,\n"
                + "    (SELECT BACKLOG_NAME FROM  " + SessionManager.getCurrentDomain() + ".TM_BACKLOG WHERE STATUS='A' AND ID=T.FK_BACKLOG_ID LIMIT 0,1) BACKLOG_NAME,\n"
                + "    ID,\n"
                + "    FK_SERVICE_PROCESS_ID\n"
                + " FROM  " + SessionManager.getCurrentDomain() + ".TM_SERVICE_PROCESS_AND_STORY_CARD T"
                + " WHERE "
                + " T.STATUS='A' "
                + " AND T.FK_SERVICE_PROCESS_ID = ?";

//        EntityTmServiceProcessAndStoryCard ent = new EntityTmServiceProcessAndStoryCard();
//        ent.setFkServiceProcessId(carrier.get("fkServiceProcessId"));
        carrier = EntityManager.selectBySql(ln, new String[]{carrier.get("fkServiceProcessId")});
        
        return carrier;
    }
    
    public static Carrier deleteServiceProcessAndBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcessAndStoryCard ent = new EntityTmServiceProcessAndStoryCard();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier getServiceProcessBody(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String table = "TM_SERVICE_PROCESS";
        String field = "PROCESS_DESC";
        String id = carrier.get("id");
        String sectionBody = EntityManager.readBlobFieldGenerik(table, id, field);
        
        carrier.set("processBody", sectionBody);
        
        return carrier;
    }
    
    public static Carrier addServiceProcessBody(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
//        carrier.addController("processBody", cp.hasValue(carrier, "processBody"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcess ent = new EntityTmServiceProcess();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        if (ent.getId().length() > 1) {
            String table = "TM_SERVICE_PROCESS";
            String field = "PROCESS_DESC";
            String body = carrier.get("processBody");
            String id = ent.getId();
            
            EntityManager.updateBlobFieldGeneric(table, id, field, body);
        }
        
        return carrier;
    }
    
    public static Carrier insertNewServiceProcess(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceId", cp.hasValue(carrier, "fkServiceId"));
        carrier.addController("fkServiceGroupId", cp.hasValue(carrier, "fkServiceGroupId"));
        carrier.addController("processName", cp.hasValue(carrier, "processName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcess ent = new EntityTmServiceProcess();
        ent.setFkServiceId(carrier.get("fkServiceId"));
        ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
        ent.setProcessName(carrier.get("processName"));
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    public static Carrier getServiceProcessList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceId", cp.hasValue(carrier, "fkServiceId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcess ent = new EntityTmServiceProcess();
        ent.setFkServiceId(carrier.get("fkServiceId"));
        ent.addSortBy("processName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier deleteServiceProcess(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcess ent = new EntityTmServiceProcess();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        return carrier;
    }
    
    public static Carrier updateServiceProcessName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("processName", cp.hasValue(carrier, "processName"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmServiceProcess ent = new EntityTmServiceProcess();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setProcessName(carrier.get("processName"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier getCaseListByService(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceId", cp.hasValue(carrier, "fkServiceId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceRelation ent = new EntityTmBcServiceRelation();
        ent.setFkServiceId(carrier.get("fkServiceId"));
        String bcIds = EntityManager.select(ent).getValueLine(ent.toTableName(), "fkBcId");
        
        if (bcIds.length() > 5) {
            EntityTmBusinessCase entCase = new EntityTmBusinessCase();
            entCase.setId(bcIds);
            carrier = EntityManager.select(entCase);
        }
        return carrier;
    }
    
    public static Carrier getBacklogDetailsById(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("fkBacklogId"));
        EntityManager.select(ent);
        EntityManager.mapEntityToCarrier(ent, carrier, true);
        
        return carrier;
    }
    
    public static Carrier getProjectIdOfBacklog(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("fkBacklogId"));
        EntityManager.select(ent);
        carrier.set("fkProjectId", ent.getFkProjectId());
        
        return carrier;
    }
    
    public static Carrier addBcSectionRel(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("fkBcSectionId", cp.hasValue(carrier, "fkBcSectionId"));
        carrier.addController("sectionBody", cp.hasValue(carrier, "sectionBody"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSectionRel ent = new EntityTmBcSectionRel();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setFkBcSectionId(carrier.get("fkBcSectionId"));
        EntityManager.select(ent);
        if (ent.getId().length() > 1) {
            String table = "tm_bc_section_rel";
            String field = "section_body";
            String body = carrier.get("sectionBody");
            String id = ent.getId();
            
            EntityManager.updateBlobFieldGeneric(table, id, field, body);
        } else {
            EntityManager.insert(ent);
            
            String table = "tm_bc_section_rel";
            String field = "section_body";
            String body = carrier.get("sectionBody");
            String id = ent.getId();
            
            EntityManager.updateBlobField(table, id, field, body);
        }
        
        return carrier;
    }
    
    public static Carrier getBcSectionRel(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("fkBcSectionId", cp.hasValue(carrier, "fkBcSectionId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSectionRel ent = new EntityTmBcSectionRel();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setFkBcSectionId(carrier.get("fkBcSectionId"));
        EntityManager.select(ent);
        
        String sectionBody = EntityManager.readBlobFieldGenerik("tm_bc_section_rel", ent.getId(), "section_body");
        
        carrier.set("sectionBody", sectionBody);
        
        return carrier;
    }
    
    public static Carrier updateCaseSection4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSection ent = new EntityTmBcSection();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier addBcSection(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("sectionName", cp.hasValue(carrier, "sectionName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSection ent = new EntityTmBcSection();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setSectionName(carrier.get("sectionName"));
        ent.setOrderNo(getBCSectionNextOrderNo(carrier.get("fkBcId")));
        ent.setGridNo("12");
        EntityManager.insert(ent);
        carrier.set("id", ent.getId());
        
        return carrier;
    }
    
    private static String getBCSectionNextOrderNo(String bcId) throws QException {
        String res = "1";
        
        if (bcId.trim().length() == 0) {
            return res;
        }
        EntityTmBcSection ent = new EntityTmBcSection();
        ent.setFkBcId(bcId);
        ent.addSortBy(EntityTmProjectCanvasCard.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier getCaseSection(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSection ent = new EntityTmBcSection();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setSortByAsc(true);
        ent.addSortBy("orderNo");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier deleteCaseSection(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcSection ent = new EntityTmBcSection();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier addCaseKeyResource(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("partnerName", cp.hasValue(carrier, "partnerName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyResource ent = new EntityTmBcKeyResource();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setResourceName(carrier.get("partnerName"));
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    public static Carrier getCaseKeyResource(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyResource ent = new EntityTmBcKeyResource();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setSortByAsc(true);
        ent.addSortBy("orderNo");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier deleteCaseKeyResource(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyResource ent = new EntityTmBcKeyResource();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier updateCaseKeyResourceDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("desc", cp.hasValue(carrier, "desc"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyResource ent = new EntityTmBcKeyResource();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setDescription(carrier.get("desc"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier updateCaseKeyResourceName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("partnerName", cp.hasValue(carrier, "partnerName"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyResource ent = new EntityTmBcKeyResource();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setResourceName(carrier.get("partnerName"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier addCaseKeyPartner(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("partnerName", cp.hasValue(carrier, "partnerName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyPartner ent = new EntityTmBcKeyPartner();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setPartnerName(carrier.get("partnerName"));
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    public static Carrier getCaseKeyPartner(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyPartner ent = new EntityTmBcKeyPartner();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setSortByAsc(true);
        ent.addSortBy("orderNo");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier deleteCaseKeyPartner(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyPartner ent = new EntityTmBcKeyPartner();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier updateCaseKeyPartnerDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("desc", cp.hasValue(carrier, "desc"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyPartner ent = new EntityTmBcKeyPartner();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setDescription(carrier.get("desc"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier updateCaseKeyPartnerName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("partnerName", cp.hasValue(carrier, "partnerName"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcKeyPartner ent = new EntityTmBcKeyPartner();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setPartnerName(carrier.get("partnerName"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier addBcServiceRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("fkServiceGroupId", cp.hasValue(carrier, "fkServiceGroupId"));
        carrier.addController("fkServiceId", cp.hasValue(carrier, "fkServiceId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceRelation ent = new EntityTmBcServiceRelation();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setFkServiceId(carrier.get("fkServiceId"));
        EntityManager.select(ent);
        if (ent.getId().length() > 0) {
            ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
            EntityManager.update(ent);
        } else {
            ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
            EntityManager.insert(ent);
        }
        return carrier;
    }
    
    public static Carrier deleteBcServiceRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceRelation ent = new EntityTmBcServiceRelation();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        return carrier;
    }
    
    public static Carrier getBcServiceRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        if (carrier.hasError()) {
            return carrier;
        }
        String domain = SessionManager.getCurrentDomain();
        
        String st = "SELECT "
                + "    ID, "
                + "    FK_BC_ID,"
                + "    FK_SERVICE_GROUP_ID,    "
                + "    (SELECT A.GROUP_NAME FROM  " + domain + ".TM_BC_SERVICE_GROUP A WHERE A.STATUS='A' AND A.ID=T.FK_SERVICE_GROUP_ID) AS GROUP_NAME, "
                + "    FK_SERVICE_ID, "
                + "    (SELECT A.SERVICE_NAME FROM  " + domain + ".TM_BC_SERVICE  A WHERE A.STATUS='A' AND A.ID=T.FK_SERVICE_ID) AS SERVICE_NAME  "
                + "FROM " + domain + ".TM_BC_SERVICE_RELATION T "
                + "WHERE T.STATUS='A'"
                + " AND T.FK_BC_ID = '" + carrier.get("fkBcId") + "'";
        
        carrier = EntityManager.selectBySql(st);
        return carrier;
    }
    
    public static Carrier insertNewCaseService(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("serviceName", cp.hasValue(carrier, "serviceName"));
        carrier.addController("fkServiceGroupId", cp.hasValue(carrier, "fkServiceGroupId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcService ent = new EntityTmBcService();
        ent.setServiceName(carrier.get("serviceName"));
        ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
        EntityManager.insert(ent);
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier updateCaseService(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("serviceName", cp.hasValue(carrier, "serviceName"));
        carrier.addController("fkServiceGroupId", cp.hasValue(carrier, "fkServiceGroupId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcService ent = new EntityTmBcService();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setServiceName(carrier.get("serviceName"));
        ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier getCaseServiceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkServiceGroupId", cp.hasValue(carrier, "fkServiceGroupId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcService ent = new EntityTmBcService();
        ent.setId(carrier.get("id"));
        ent.setServiceName(carrier.get("serviceName"));
        ent.setFkServiceGroupId(carrier.get("fkServiceGroupId"));
        ent.addSortBy("serviceName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        String sids = carrier.getValueLine(ent.toTableName());
        
        EntityTmServiceProcess entPro = new EntityTmServiceProcess();
//        entPro.setFkServiceId(sids);
//        Carrier cr = EntityManager.select(entPro);
        Carrier cr = getServiceProcess(sids);
        Carrier crId = cr.getKVPairListFromTable(CoreLabel.RESULT_SET, "fkServiceId", "id");
        Carrier crName = cr.getKVPairListFromTable(CoreLabel.RESULT_SET, "fkServiceId", "processName");
        Carrier crCount = cr.getKVPairListFromTable(CoreLabel.RESULT_SET, "fkServiceId", "storyCardCount");
        
        String tn = ent.toTableName();
        int rc = carrier.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(carrier, tn, i, ent);
            carrier.setValue(tn, i, "processId", crId.get(ent.getId()));
            carrier.setValue(tn, i, "processName", crName.get(ent.getId()));
            carrier.setValue(tn, i, "storyCardCount", crCount.get(ent.getId()));
            
        }
        return carrier;
    }
    
    private static Carrier getServiceProcess(String serviceIds) throws QException {
        if (serviceIds.trim().length() == 0) {
            return new Carrier();
        }
        String domain = SessionManager.getCurrentDomain();
        serviceIds = serviceIds.replaceAll(CoreLabel.IN, ",");
        
        String ln = "SELECT\n"
                + "    ID,\n"
                + "    FK_SERVICE_ID, "
                + "    PROCESS_NAME,\n"
                + "    (SELECT COUNT(ID) FROM   " + domain + ".TM_SERVICE_PROCESS_AND_STORY_CARD A  WHERE A.STATUS='A' AND A.FK_SERVICE_PROCESS_ID = T.ID) STORY_CARD_COUNT\n"
                + " FROM   " + domain + ".TM_SERVICE_PROCESS T"
                + " WHERE T.STATUS='A' "
                + " AND T.FK_SERVICE_ID IN (" + serviceIds + ")";
        
        Carrier carrier = EntityManager.selectBySql(ln);
        return carrier;
    }
    
    public static Carrier deleteCaseService(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcService ent = new EntityTmBcService();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        return carrier;
    }
    
    public static Carrier insertNewCaseServiceGroup(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("groupName", cp.hasValue(carrier, "groupName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        if (carrier.get("currentId").length() > 1) {
            
            EntityTmBcServiceGroup ent = new EntityTmBcServiceGroup();
            ent.setId(carrier.get("currentId"));
            EntityManager.select(ent);
            ent.setGroupName(carrier.get("groupName"));
            EntityManager.update(ent);
            carrier.set("id", ent.getId());
        } else {
            EntityTmBcServiceGroup ent = new EntityTmBcServiceGroup();
            ent.setGroupName(carrier.get("groupName"));
            EntityManager.insert(ent);
            carrier.set("id", ent.getId());
        }
        
        return carrier;
    }
    
    public static Carrier updateCaseServiceGroup(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("groupName", cp.hasValue(carrier, "groupName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceGroup ent = new EntityTmBcServiceGroup();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setGroupName(carrier.get("groupName"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier getCaseServiceGroupList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceGroup ent = new EntityTmBcServiceGroup();
        ent.addSortBy("groupName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier deleteCaseServiceGroup(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBcServiceGroup ent = new EntityTmBcServiceGroup();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        return carrier;
    }
    
    public static Carrier updateCaseProblemStat4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier insertNewCaseProblemStatement(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        carrier.addController("problemDesc", cp.hasValue(carrier, "problemDesc"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String orderNo = getBCProblemStatementOrderNo(carrier.get("fkBcId"));
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.setProblemDesc(carrier.get("problemDesc"));
        ent.setOrderNo(orderNo);
        
        EntityManager.insert(ent);
        return carrier;
    }
    
    public static Carrier getCaseProblemStatementList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBcId", cp.hasValue(carrier, "fkBcId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setFkBcId(carrier.get("fkBcId"));
        ent.addSortBy("orderNo");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        EntityTmBusinessCase entBC = new EntityTmBusinessCase();
        entBC.setId(ent.getFkBcId());
        EntityManager.select(entBC);
        carrier.set("caseDesc", entBC.getCaseDescription());
        return carrier;
    }
    
    public static Carrier updateCaseProblemStatementPotentialCustomer(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("potentialCount", cp.hasValue(carrier, "potentialCount"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setCountPotentialCustomer(carrier.get("potentialCount"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier updateCaseProblemStatementRealCustomer(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("realCount", cp.hasValue(carrier, "realCount"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setCountRealCustomer(carrier.get("realCount"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier deleteCaseProblemStatement(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    private static String getBCProblemStatementOrderNo(String dbId) throws QException {
        String res = "1";
        
        if (dbId.trim().length() == 0) {
            return res;
        }
        EntityTmProblemStatement ent = new EntityTmProblemStatement();
        ent.setFkBcId(dbId);
        ent.addSortBy(EntityTmProjectCanvasCard.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier getApiRelSetting(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkOwnerId", cp.hasValue(carrier, "fkOwnerId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("relType", cp.hasValue(carrier, "relType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmApiRelSetting ent = new EntityTmApiRelSetting();
        ent.setFkOwnerId(carrier.get("fkOwnerId"));
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setRelType(carrier.get("relType"));
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        carrier = EntityManager.select(ent);
        EntityManager.mapEntityToCarrier(ent, carrier, true);
        
        return carrier;
    }
    
    public static Carrier addAPIRelSetting(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkOwnerId", cp.hasValue(carrier, "fkOwnerId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("relType", cp.hasValue(carrier, "relType"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmApiRelSetting ent = new EntityTmApiRelSetting();
        ent.setFkOwnerId(carrier.get("fkOwnerId"));
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setRelType(carrier.get("relType"));
        EntityManager.select(ent);
        
        if (ent.getId().trim().length() > 1) {
            EntityManager.mapCarrierToEntity(carrier, ent);
            EntityManager.update(ent);
        } else {
            EntityManager.mapCarrierToEntity(carrier, ent);
            EntityManager.insert(ent);
        }
        
        return carrier;
    }
    
    public static Carrier getBusinessCaseList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier updateBusinessCaseName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBusinessCase.ID, cp.hasValue(carrier, EntityTmBusinessCase.ID));
        carrier.addController(EntityTmBusinessCase.CASE_NAME, cp.hasValue(carrier, EntityTmBusinessCase.CASE_NAME));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setCaseName(carrier.get("caseName"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier updateBusinessCaseDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBusinessCase.ID, cp.hasValue(carrier, EntityTmBusinessCase.ID));
        carrier.addController(EntityTmBusinessCase.CASE_DESCRIPTION, cp.hasValue(carrier, EntityTmBusinessCase.CASE_DESCRIPTION));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setCaseDescription(carrier.get("caseDescription"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier deleteBusinessCase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBusinessCase.ID, cp.hasValue(carrier, EntityTmBusinessCase.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        return carrier;
    }
    
    public static Carrier insertNewBusinessCase(Carrier carrier) throws QException, UnsupportedEncodingException, SQLException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBusinessCase.CASE_NAME, cp.hasValue(carrier, EntityTmBusinessCase.CASE_NAME));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.setCaseName(carrier.get("caseName"));
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCaseNo(GetBCaseNextNo());
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        addSectionsToNewBusinessCase(ent.getId());
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    private static void addSectionsToNewBusinessCase(String caseId) throws QException, UnsupportedEncodingException, SQLException {
        String sections[] = Config.getProperty("getBusinessCaseSection").split(",");
        for (String s : sections) {
            if (s.trim().length() == 0) {
                continue;
            }
            
            Carrier cr = new Carrier();
            cr.set("fkBcId", caseId);
            cr.set("sectionName", s);
            Carrier crTemp = addBcSection(cr);
            
            EntityTmBcSectionRel ent = new EntityTmBcSectionRel();
            ent.setFkBcId(caseId);
            ent.setFkBcSectionId(crTemp.get("id"));
            EntityManager.insert(ent);
        }
    }
    
    private static String GetBCaseNextNo() throws QException {
        String format = "200001";
        String res = "200001";
        
        EntityTmBusinessCase ent = new EntityTmBusinessCase();
        ent.addSortBy(EntityTmBusinessCase.CASE_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            String caseNo = ent.getCaseNo();
            caseNo = caseNo.substring(2, caseNo.length());
            res = String.valueOf(Integer.parseInt(caseNo) + 1);
        } catch (Exception e) {
        }
        
        res = QDate.getCurrentYear().substring(2, 4) + res.substring(2, res.length());
        res = "BC" + res;
        return res;
    }
    
    public static Carrier insertNewSprint4Task(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskSprint.SPRINT_NAME, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_NAME));
        carrier.addController(EntityTmTaskSprint.SPRINT_START_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_START_DATE));
        carrier.addController(EntityTmTaskSprint.SPRINT_END_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_END_DATE));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTaskSprint ent = new EntityTmTaskSprint();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkProjectId(carrier.get("fkProjectId"));
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier getLabelList4Task(Carrier carrier) throws QException {
        
        EntityTmTaskLabelListForTask ent = new EntityTmTaskLabelListForTask();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.addSortBy(EntityTmTaskSprintList.FK_PROJECT_ID);
        ent.addSortBy(EntityTmTaskLabelListForTask.NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        String labelIds = carrier.getValueLine(CoreLabel.RESULT_SET, "id");
        EntityTmRelTaskAndLabel entTask = new EntityTmRelTaskAndLabel();
        entTask.setFkTaskLabelId(labelIds);
        Carrier crPair = EntityManager.select(entTask).getKVPairListFromTable(
                entTask.toTableName(), EntityTmRelTaskAndLabel.FK_TASK_LABEL_ID, EntityTmRelTaskAndLabel.FK_BACKLOG_TASK_ID);
        
        String tn = CoreLabel.RESULT_SET;
        int rc = carrier.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String labelId = carrier.getValue(tn, i, "id").toString();
            carrier.setValue(tn, i, "labelTaskIds", crPair.get(labelId));
        }
        
        return carrier;
    }
    
    public static Carrier getSprintList4Task(Carrier carrier) throws QException {
        
        EntityTmTaskSprintListForTask ent = new EntityTmTaskSprintListForTask();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.addSortBy(EntityTmTaskSprintList.FK_PROJECT_ID);
        ent.addSortBy(EntityTmTaskSprintList.SPRINT_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        String sprintIds = carrier.getValueLine(CoreLabel.RESULT_SET, "id");
        EntityTmRelTaskAndSprint entTask = new EntityTmRelTaskAndSprint();
        entTask.setFkTaskSprintId(sprintIds);
        Carrier crPair = EntityManager.select(entTask).getKVPairListFromTable(
                entTask.toTableName(), EntityTmRelTaskAndSprint.FK_TASK_SPRINT_ID, EntityTmRelTaskAndSprint.FK_BACKLOG_TASK_ID);
        
        String tn = CoreLabel.RESULT_SET;
        int rc = carrier.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String sprintId = carrier.getValue(tn, i, "id").toString();
            carrier.setValue(tn, i, "sprintTaskIds", crPair.get(sprintId));
        }
        
        return carrier;
    }
    
    public static Carrier addRelatedApiToBacklogDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("apiId", cp.hasValue(carrier, "apiId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setFkRelatedApiId(carrier.get("apiId"));
        ent.setShortDescForApi(carrier.get("shortDesc"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier addRelatedFunctionToBacklogDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("fkFunctionId", cp.hasValue(carrier, "fkFunctionId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setFkRelatedScId(carrier.get("fkFunctionId"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier removeRelatedApiToBacklogDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setFkRelatedApiId("");
        ent.setShortDescForApi("");
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier removeRelatedFunctionToBacklogDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setFkRelatedScId("");
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier insertNewTestCaseTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCase entMain = new EntityTmTestCase();
        entMain.setId(carrier.get("fkTestCaseId"));
        EntityManager.select(entMain);
        
        EntityTmTestCaseTrial ent = new EntityTmTestCaseTrial();
        ent.setFkTestCaseId(entMain.getId());
        ent.setTestCaseName(entMain.getTestCaseName());
        ent.setTestCaseScenario(entMain.getTestCaseScenario());
        ent.setGeneralDescription(entMain.getGeneralDescription());
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setTrialStatus("incomplete");
        ent.setIsSolved("no");
        EntityManager.insert(ent);

        //create test case steps
        EntityTmTestCaseStep entTrMain = new EntityTmTestCaseStep();
        entTrMain.setFkTestCaseId(carrier.get("fkTestCaseId"));
        Carrier cr = EntityManager.select(entTrMain);
        
        String tn = entTrMain.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i <= rc; i++) {
            EntityTmTestCaseStepTrial entTr = new EntityTmTestCaseStepTrial();
            EntityManager.mapCarrierToEntity(cr, tn, rc - i, entTr);
            entTr.setFkTestCaseTrialId(ent.getId());
            entTr.setCreatedBy(SessionManager.getCurrentUserId());
            entTr.setCreatedDate(QDate.getCurrentDate());
            entTr.setCreatedTime(QDate.getCurrentTime());
            EntityManager.insert(entTr);
        }
        
        return carrier;
    }
    
    public static Carrier insertNewTestCase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("testCaseName", cp.hasValue(carrier, "testCaseName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCase ent = new EntityTmTestCase();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setTestCaseName(carrier.get("testCaseName"));
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setTestCaseNo(nextTestCaseNo(carrier.get("fkProjectId")));
        ent.setPriority("1");
        EntityManager.insert(ent);
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier dublicateTestCase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCase ent = new EntityTmTestCase();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        EntityManager.insert(ent);
        
        EntityTmTestCaseStep ent1 = new EntityTmTestCaseStep();
        ent1.setFkTestCaseId(carrier.get("id"));
        Carrier cr = EntityManager.select(ent1);
        
        int rc = cr.getTableRowCount(ent1.toTableName());
        String tn = ent1.toTableName();
        for (int i = 0; i <= rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, rc - i, ent1);
            ent1.setFkTestCaseId(ent.getId());
            ent1.setCreatedBy(SessionManager.getCurrentUserId());
            ent1.setCreatedDate(QDate.getCurrentDate());
            ent1.setCreatedTime(QDate.getCurrentTime());
            EntityManager.insert(ent1);
        }
        
        return carrier;
    }
    
    public static Carrier deleteTestCaseStep(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseStep ent1 = new EntityTmTestCaseStep();
        ent1.setId(carrier.get("id"));
        EntityManager.delete(ent1);
        
        return carrier;
    }
    
    public static Carrier deleteTestCase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCase ent = new EntityTmTestCase();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        EntityTmTestCaseStep ent1 = new EntityTmTestCaseStep();
        ent1.setFkTestCaseId(carrier.get("id"));
        String ids = EntityManager.select(ent1).getValueLine(ent1.toTableName());
        ent1.setId(ids);
        if (ids.length() > 4) {
            EntityManager.delete(ent1);
        }
        
        return carrier;
    }
    
    public static Carrier deleteTestCaseTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseTrial ent = new EntityTmTestCaseTrial();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        EntityTmTestCaseStepTrial ent1 = new EntityTmTestCaseStepTrial();
        ent1.setFkTestCaseTrialId(carrier.get("id"));
        String ids = EntityManager.select(ent1).getValueLine(ent1.toTableName());
        ent1.setId(ids);
        if (ids.length() > 4) {
            EntityManager.delete(ent1);
        }
        
        return carrier;
    }
    
    public static Carrier getTestCaseStepList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseStep ent = new EntityTmTestCaseStep();
        ent.setFkTestCaseId(carrier.get("fkTestCaseId"));
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getTestCaseStepTrialList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("trialId", cp.hasValue(carrier, "trialId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseTrial entTr = new EntityTmTestCaseTrial();
        entTr.setId(carrier.get("trialId"));
        entTr.setEndLimit(0);
        EntityManager.select(entTr);
        
        EntityTmTestCase entMain = new EntityTmTestCase();
        entMain.setId(entTr.getFkTestCaseId());
        entMain.setEndLimit(0);
        EntityManager.select(entMain);
        
        EntityTmTestCaseStepTrial ent = new EntityTmTestCaseStepTrial();
        ent.setFkTestCaseTrialId(carrier.get("trialId"));
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        EntityManager.mapEntityToCarrier(entMain, carrier, true);
        EntityManager.mapEntityToCarrier(entTr, carrier, false);
        
        return carrier;
    }
    
    public static Carrier insertNewTestCaseStep(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        carrier.addController("stepType", cp.hasValue(carrier, "stepType"));
        carrier.addController("stepName", cp.hasValue(carrier, "stepName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseStep ent = new EntityTmTestCaseStep();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkTestCaseId(carrier.get("fkTestCaseId"));
        ent.setStepType(carrier.get("stepType"));
        ent.setStepName(carrier.get("stepName"));
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        EntityManager.insert(ent);
        
        EntityTmTestCaseStep ent1 = new EntityTmTestCaseStep();
        ent1.setFkTestCaseId(carrier.get("fkTestCaseId"));
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent1);
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier addImageToTestCaseTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("imgUrl", cp.hasValue(carrier, "imgUrl"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseTrial ent = new EntityTmTestCaseTrial();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setImgUrl(ent.getImgUrl() + "|" + carrier.get("imgUrl"));
        EntityManager.update(ent);
        carrier.set("imgUrl", ent.getImgUrl());
        return carrier;
    }
    
    public static Carrier updateTestCaseTrial4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseTrial ent = new EntityTmTestCaseTrial();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier updateTestCase4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCase ent = new EntityTmTestCase();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier updateTestCaseStepTrial4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseStepTrial ent = new EntityTmTestCaseStepTrial();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier updateTestCaseStep4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTestCaseStep ent = new EntityTmTestCaseStep();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier getTestCaseList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String searchText = (carrier.get("searchText").trim().length() > 0)
                ? "%" + carrier.get("searchText") + "%"
                : "";
        
        int startLimit = (Integer.valueOf(carrier.get("pageNo")) - 1) * Integer.valueOf(carrier.get("tableLimit"));
        int endLimit = (Integer.valueOf(carrier.get("tableLimit")) > 300) ? 50 : Integer.valueOf(carrier.get("tableLimit")) - 1;
        
        EntityTmTestCase ent1 = new EntityTmTestCase();
        ent1.setFkProjectId(carrier.get("fkProjectId"));
        ent1.setFkBacklogId(carrier.get("fkBacklogId"));
        ent1.setCreatedBy(carrier.get("createBy"));
        ent1.setPriority(carrier.get("priority"));
        ent1.setTestCaseName(searchText);
        ent1.setTestCaseScenario(searchText);
        ent1.addSortBy("id");
        ent1.setSortByAsc(false);
        ent1.setStartLimit(startLimit);
        ent1.setEndLimit(startLimit + endLimit);
        ent1.addDeepWhereStatementField("testCaseName");
        ent1.addDeepWhereStatementField("testCaseScenario");
        ent1.addAndOrStatementField("testCaseName");
        ent1.addAndOrStatementField("testCaseScenario");
        carrier = EntityManager.select(ent1);
        
        String testCaseIds = carrier.getValueLine(ent1.toTableName());
        getCaseTrialsByCaseId(testCaseIds).copyTo(carrier);
        
        carrier.set("tableCount", EntityManager.getRowCount(ent1));
        carrier.set("limit", endLimit - 1);
        return carrier;
    }
    
    private static Carrier getCaseTrialsByCaseId(String testCaseIds) throws QException {
        EntityTmTestCaseTrial entTr = new EntityTmTestCaseTrial();
        entTr.setFkTestCaseId(testCaseIds);
        Carrier crTr = EntityManager.select(entTr);
        crTr.renameTableName(entTr.toTableName(), "trialListTable");
        return crTr;
    }

    ///statistics
    public static Carrier getBacklogListByStatsGroup(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("actionType", cp.hasValue(carrier, "actionType"));
        carrier.addController("statusType", cp.hasValue(carrier, "statusType"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String projectId = carrier.get("fkProjectId");
        String actionType = carrier.get("actionType");
        String statusType = carrier.get("statusType");
        
        String projectList = carrier.get("projectList");
        String sprintList = carrier.get("sprintList");
        String labelList = carrier.get("labelList");
        
        Carrier cout = new Carrier();
        
        if (actionType.equals("overall")) {
            cout = getBacklogListByStatsGroup_overal(projectId, statusType);
        } else if (actionType.equals("inaction")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardInAction4Select(projectId, statusType);
        } else if (actionType.equals("initial")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardInInitial4Select(projectId, statusType);
        } else if (actionType.equals("withbugs")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardWithBug4Select(projectId, statusType);
        } else if (actionType.equals("withchanges")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardWithChange4Select(projectId, statusType);
        } else if (actionType.equals("withnews")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardWithNew4Select(projectId, statusType);
        } else if (actionType.equals("createdstory")) {
            cout = DashboardStatistics.getProjectSummary_StoryCardByOwner4Select(projectId, statusType, projectList, sprintList, labelList);
        }
        
        return cout;
        
    }
    
    public static Carrier getUserListByProjects(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        ent.setFkProjectId(getMyProjects());
        ent.addDistinctField(EntityTmProjectPermissionList.FK_USER_ID);
        ent.addDistinctField(EntityTmProjectPermissionList.USER_NAME);
        ent.addSortBy("userName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier loadApiByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addDistinctField(EntityTmBacklog.ID);
        ent.addDistinctField(EntityTmBacklog.BACKLOG_NAME);
        ent.setIsApi("1");
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier loadStoryCardByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addDistinctField(EntityTmBacklog.ID);
        ent.addDistinctField(EntityTmBacklog.BACKLOG_NAME);
        ent.setIsApi(CoreLabel.NE + "1");
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier loadAssigneeByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
//        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addDistinctField(EntityTmProjectPermissionList.FK_USER_ID);
        ent.addDistinctField(EntityTmProjectPermissionList.USER_NAME);
        ent.addSortBy(EntityTmProjectPermissionList.USER_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public static Carrier getTaskList4Table(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        if (carrier.hasError()) {
            return carrier;
        }
        
        String limit = (carrier.get("searchLimit").trim().length() > 0
                && Float.parseFloat(carrier.get("searchLimit")) <= 300)
                ? carrier.get("searchLimit")
                : " 50 ";
        
        String filterSection = getTaskList4TableFilterSection(carrier);
        String limitLine = getTaskList4TableLimitLine(carrier);
        
        String st = getTaskList4TableSelectpart()
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task t\n"
                + " where t.status='A'"
                + filterSection
                + " order by id desc"
                + limitLine;
        
        String stCount = getTaskList4TableSelectpartByCount()
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task t\n"
                + " where t.status='A'"
                + filterSection
                + " order by id desc";
        
        carrier = EntityManager.selectBySql(st);
        carrier.renameTableName(CoreLabel.RESULT_SET, "taskListTable");
        
        Carrier carrier1 = EntityManager.selectBySql(stCount);
        carrier.set("tableCount", carrier1.getValue(CoreLabel.RESULT_SET, 0, "tableCount"));
        carrier.set("limit", limit);
        
        return carrier;
    }
    
    private static String getTaskList4TableLimitLine(Carrier carrier) throws QException {
        String limit = (carrier.get("searchLimit").trim().length() > 0
                && Float.parseFloat(carrier.get("searchLimit")) <= 300)
                ? carrier.get("searchLimit")
                : " 50 ";
        
        String pageNo = (carrier.get("pageNo").trim().length() > 0)
                ? carrier.get("pageNo")
                : "1";
        
        return " limit  " + ((Integer.parseInt(pageNo) - 1) * Integer.parseInt(limit)) + " , " + limit + ";";
    }
    
    private static String getTaskList4TableFilterSection(Carrier carrier) throws QException {
        
        String showChildTask = (carrier.get("showChildTask").length() > 0
                && carrier.get("showChildTask").equals("0"))
                ? " and  fk_parent_task_id =''"
                : " ";
        
        String sprintLine = (carrier.get("sprintId").length() > 5)
                ? " and  t.id in (select fk_backlog_task_id from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_sprint k"
                + " where k.status='A' and k.FK_TASK_SPRINT_ID in  (" + carrier.get("sprintId") + ")) "
                : " ";
        
        String labelLine = (carrier.get("labelId").length() > 5)
                ? " and  t.id in (select fk_backlog_task_id from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_label k"
                + " where k.status='A' and k.FK_TASK_LABEL_ID in  (" + carrier.get("labelId") + ")) "
                : " ";
        
        String projectLine = (carrier.get("fkProjectId").length() > 5)
                ? " and t.fk_project_id in (" + carrier.get("fkProjectId") + ") "
                : " and  t.fk_project_id in (" + getMyProjects4Bug() + ") ";
        
        String assingeeLine = (carrier.get("fkAssigneeId").length() > 5)
                ? " and  t.fk_assignee_id in (" + carrier.get("fkAssigneeId") + ") "
                : " ";
        
        String createByLine = (carrier.get("createdBy").length() > 5)
                ? " and  t.created_by in (" + carrier.get("createdBy") + ") "
                : " ";
        
        String storyCardLine = (carrier.get("fkBackogId").length() > 5)
                ? " and  t.fk_backlog_id in (" + carrier.get("fkBackogId") + ") "
                : " ";
        
        String statusLine = (carrier.get("taskStatus").length() > 1)
                ? " and  t.task_status in (" + carrier.get("taskStatus") + ") "
                : " ";
        
        String taskId = (carrier.get("fkTaskId").length() > 1)
                ? " and  t.id in (" + carrier.get("fkTaskId") + ") "
                : " ";
        
        String priority = (carrier.get("priority").length() > 1)
                ? " and  t.task_priority in (" + carrier.get("priority") + ") "
                : " ";
        
        String taskNatureLine = (carrier.get("taskNature").length() > 1)
                ? " and  t.task_nature in (" + carrier.get("taskNature") + ") "
                : " ";
        
        String searchText = (carrier.get("searchText").length() > 0)
                ? " and ( t.task_name like '%" + carrier.get("searchText") + "%' or "
                + " t.order_no_seq like '%" + carrier.get("searchText") + "%' "
                + ")"
                : " ";
        
        String st = projectLine
                + assingeeLine
                + createByLine
                + storyCardLine
                + statusLine
                + taskNatureLine
                + sprintLine
                + labelLine
                + priority
                + taskId
                + showChildTask
                + searchText;
        return st;
    }
    
    private static String getTaskList4TableSelectpart() {
        String st = "select\n"
                + "    t.id,\n"
                + "    t.order_no_seq,"
                + "    t.task_priority,"
                + "    (select p.project_code from  " + SessionManager.getCurrentDomain() + ".tm_project p where p.id = t.fk_project_id and p.status='A') project_code,"
                + "    t.task_name,\n"
                + "   (select u.user_image from " + SessionManager.getCurrentDomain() + ".cr_user u where u.id = t.created_by and u.status='A') create_by_image,\n"
                + "    (select u.user_person_name from " + SessionManager.getCurrentDomain() + ".cr_user u where u.id = t.created_by and u.status='A') create_by_name,\n"
                + "    t.task_status,\n"
                + "    t.task_nature,\n"
                + "    t.fk_task_type_id,\n"
                + "    (select u.type_name from " + SessionManager.getCurrentDomain() + ".tm_task_type u where u.id = t.fk_task_type_id and u.status='A') task_type_name,\n"
                + "    t.fk_assignee_id,\n"
                + "    (select u.user_image from " + SessionManager.getCurrentDomain() + ".cr_user u where u.id = t.fk_assignee_id and u.status='A') user_image,\n"
                + "    (select u.user_person_name from " + SessionManager.getCurrentDomain() + ".cr_user u where u.id = t.fk_assignee_id and u.status='A') user_name,\n"
                + "    (select p.project_name from  " + SessionManager.getCurrentDomain() + ".tm_project p where p.id = t.fk_project_id and p.status='A') project_name,"
                + "    t.fk_project_id,\n"
                + "    t.fk_backlog_id,\n"
                + "    (select b.backlog_name from " + SessionManager.getCurrentDomain() + ".tm_backlog b where b.id = t.fk_backlog_id and b.status='A') backlog_name,"
                + "    t.fk_backlog_id,\n"
                + "    t.created_by,\n"
                + "    t.created_date,\n"
                + "    t.last_updated_date,\n"
                + "    t.estimated_hours,\n"
                + "    t.spent_hours,\n"
                + "    t.estimated_budget,\n"
                + "    t.spent_budget,\n"
                + "    t.estimated_counter,\n"
                + "    t.executed_counter,\n"
                + "    t.fk_parent_task_id\n";
        return st;
    }
    
    private static String getTaskList4TableSelectpartByCount() {
        String st = "select count(id) table_count";
        return st;
    }
    
    public static String getMyProjects4Bug() throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkUserId(SessionManager.getCurrentUserId());
        return "-1," + EntityManager.select(ent).getValueLine(ent.toTableName(), "fkProjectId", ",");
    }
    
    public static Carrier deleteFieldRel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String id = carrier.get("id");
        
        EntityTmFieldRelation ent = new EntityTmFieldRelation();
        ent.setId(id);
        EntityManager.delete(ent);
        
        return carrier;
        
    }
    
    public static Carrier getFieldLink(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String id = carrier.get("id");
        
        EntityTmFieldRelation ent = new EntityTmFieldRelation();
        ent.setFromFieldId(id);
        Carrier cr1 = EntityManager.select(ent);
        cr1.renameTableName(ent.toTableName(), "fromTable");
        cr1.copyTo(carrier);
        
        EntityTmFieldRelation ent2 = new EntityTmFieldRelation();
        ent2.setToFieldId(id);
        Carrier cr2 = EntityManager.select(ent2);
        cr2.renameTableName(ent2.toTableName(), "toTable");
        cr2.copyTo(carrier);
        
        return carrier;
        
    }
    
    public static Carrier addFieldRel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
//        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkDbId", cp.hasValue(carrier, "fkDbId"));
        carrier.addController("fromFieldId", cp.hasValue(carrier, "fromFieldId"));
        carrier.addController("toFieldId", cp.hasValue(carrier, "toFieldId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmFieldRelation ent = new EntityTmFieldRelation();
        ent.setFkDbId(carrier.get("fkDbId"));
        ent.setFromFieldId(carrier.get("fromFieldId"));
        ent.setToFieldId(carrier.get("toFieldId"));
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            EntityManager.insert(ent);
        }
        
        return carrier;
        
    }
    
    public static Carrier getBacklogListByStatsGroupByAssignee(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
//        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkAssigneeId", cp.hasValue(carrier, "fkAssigneeId"));
        carrier.addController("actionType", cp.hasValue(carrier, "actionType"));
        carrier.addController("statusType", cp.hasValue(carrier, "statusType"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String assigneeId = carrier.get("fkAssigneeId");
        String projectId = carrier.get("fkProjectId");
        String actionType = carrier.get("actionType");
        String statusType = carrier.get("statusType");
        
        String labelId = carrier.get("fkLabelId");
        String sprintId = carrier.get("fkSprintId");
        
        Carrier cout = new Carrier();
        
        if (actionType.equals("overall")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeOverall4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("new")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeNew4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("change")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeChange4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("bug")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeBug4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("created")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeCreated4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("createdbug")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeCreatedBug4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("creatednew")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeCreatedNew4Select(assigneeId, projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("createdchange")) {
            cout = DashboardStatistics.getProjectSummary_AssigneeCreatedChange4Select(assigneeId, projectId, statusType, labelId, sprintId);
        }
        
        return cout;
        
    }
    
    public static Carrier getBacklogListByStatsGroupByTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("actionType", cp.hasValue(carrier, "actionType"));
        carrier.addController("statusType", cp.hasValue(carrier, "statusType"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String projectId = carrier.get("fkProjectId");
        String actionType = carrier.get("actionType");
        String statusType = carrier.get("statusType");
        
        String labelId = carrier.get("fkLabelId");
        String sprintId = carrier.get("fkSprintId");
        
        Carrier cout = new Carrier();
        
        if (actionType.equals("overall")) {
            cout = DashboardStatistics.getProjectSummary_TaskOverall4Select(projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("new")) {
            cout = DashboardStatistics.getProjectSummary_New4Select(projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("change")) {
            cout = DashboardStatistics.getProjectSummary_Change4Select(projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("bug")) {
            cout = DashboardStatistics.getProjectSummary_Bug4Select(projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("unassigned")) {
            cout = DashboardStatistics.getProjectSummary_TaskUnassigned4Select(projectId, statusType, labelId, sprintId);
        } else if (actionType.equals("nostorycard")) {
            cout = DashboardStatistics.getProjectSummary_TaskNostorycard4Select(projectId, statusType, labelId, sprintId);
        }
        
        return cout;
    }
    
    public static Carrier getBacklogListByStatsGroupByTask4SC(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("actionType", cp.hasValue(carrier, "actionType"));
        carrier.addController("statusType", cp.hasValue(carrier, "statusType"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        String backlogId = carrier.get("fkBacklogId");
        String actionType = carrier.get("actionType");
        String statusType = carrier.get("statusType");
        
        Carrier cout = new Carrier();
        
        if (actionType.equals("overall")) {
            cout = DashboardStatistics.getProjectSummary_TaskOverall4Select4SC(backlogId, statusType);
        } else if (actionType.equals("bug")) {
            cout = DashboardStatistics.getProjectSummary_TaskBug4Select4SC(backlogId, statusType);
        } else if (actionType.equals("new")) {
            cout = DashboardStatistics.getProjectSummary_TaskNew4Select4SC(backlogId, statusType);
        } else if (actionType.equals("change")) {
            cout = DashboardStatistics.getProjectSummary_TaskChange4Select4SC(backlogId, statusType);
        }
//        else if (actionType.equals("new")) {
//            cout = DashboardStatistics.getProjectSummary_New4Select(projectId, statusType, labelId, sprintId);
//        } else if (actionType.equals("change")) {
//            cout = DashboardStatistics.getProjectSummary_Change4Select(projectId, statusType, labelId, sprintId);
//        } else if (actionType.equals("bug")) {
//            cout = DashboardStatistics.getProjectSummary_Bug4Select(projectId, statusType, labelId, sprintId);
//        }  

        return cout;
    }
    
    private static Carrier getBacklogListByStatsGroup_inaction(String projectId, String status) throws QException {
        Carrier cout = new Carrier();
        
        return cout;
        
    }
    
    private static Carrier getBacklogListByStatsGroup_overal(String projectId, String status) throws QException {
        Carrier cout = new Carrier();
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        ent.setFkProjectId(projectId);
        if (!status.equals("total")) {
            ent.setBacklogStatus(status);
        }
//        ent.addDistinctField("id");

        cout = EntityManager.select(ent);
        
        return cout;
        
    }
    
    public static Carrier getGeneralStatisticsByUserStory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        Carrier cout = DashboardStatistics.getStoryCardSummary(carrier);
        return cout;
    }
    
    public static Carrier getGeneralStatistics(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        Carrier cout = DashboardStatistics.getProjectSummary(carrier);
        return cout;
    }
    
    public static Carrier getGeneralStatisticsByUser(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = DashboardStatistics.getProjectSummaryByUser(carrier);
        return cout;
        
    }
    
    public static Carrier getGeneralStatisticsByAssignee(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = DashboardStatistics.getProjectSummaryByAssignee(carrier);
        return cout;
        
    }
    
    public static Carrier getGeneralStatisticsByTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = DashboardStatistics.getProjectSummaryByTask(carrier);
        return cout;
        
    }

    ///statistics
    public static Carrier getSendToSelectedList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("storyCardList", cp.hasValue(carrier, "storyCardList"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setSendToBacklogId(carrier.get("storyCardList"));
        Carrier c = EntityManager.select(ent);
        String idLine = c.getValueLine(ent.toTableName(), EntityTmInput.FK_BACKLOG_ID);
        c.set("fkBacklogId", idLine);
        return c;
        
    }
    
    public static Carrier getBacklogListByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), "userStoryTable");
        return c;
        
    }
    
    public static Carrier getBacklogListByProjectId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), "userStoryTable");
        return c;
        
    }
    
    public static Carrier getDBStructure4Select(Carrier carrier) throws QException {
        Carrier cout = new Carrier();
        
        getDBStructureList4Select(carrier).copyTo(cout);
        getTableStructureList4Select(carrier).copyTo(cout);
        getFieldStructureList4Select(carrier).copyTo(cout);
        getFieldRelStructureList4Select(carrier).copyTo(cout);
        
        return cout;
    }
    
    public static Carrier getDBStructureList4Select(Carrier carrier) throws QException {
        EntityTmDatabase ent = new EntityTmDatabase();
        ent.setId(carrier.get("id"));
        ent.addSortBy(EntityTmDatabase.DB_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), "dbList");
        
        return carrier;
    }
    
    public static Carrier getTableStructureList4Select(Carrier carrier) throws QException {
        EntityTmTable entTbl = new EntityTmTable();
        entTbl.setId(carrier.get("id"));
        entTbl.addSortBy(EntityTmTable.TABLE_NAME);
        entTbl.setSortByAsc(true);
        carrier = EntityManager.select(entTbl);
        carrier.renameTableName(entTbl.toTableName(), "tableList");
        
        return carrier;
    }
    
    public static Carrier getFieldStructureList4Select(Carrier carrier) throws QException {
        EntityTmField entF = new EntityTmField();
        entF.setId(carrier.get("id"));
        entF.addSortBy(EntityTmField.FIELD_NAME);
        entF.setSortByAsc(true);
        carrier = EntityManager.select(entF);
        carrier.renameTableName(entF.toTableName(), "fieldList");
        
        return carrier;
    }
    
    public static Carrier getFieldRelStructureList4Select(Carrier carrier) throws QException {
        EntityTmFieldRelation entF = new EntityTmFieldRelation();
        entF.setFkDbId(carrier.get("dbId"));
        carrier = EntityManager.select(entF);
        carrier.renameTableName(entF.toTableName(), "fieldRelList");
        
        return carrier;
    }
    
    public static Carrier getDbList(Carrier carrier) throws QException {
        EntityTmDatabase ent = new EntityTmDatabase();
        ent.addSortBy(EntityTmDatabase.DB_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier getFieldsByTableId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        ent.setFkDbId(carrier.get("dbId"));
        ent.addSortBy(EntityTmField.ORDER_NO);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), "coreFields");
        
        getSelectFromStoryCardRelatedEntity().copyTo(carrier);
        carrier.renameTableName(CoreLabel.RESULT_SET, "selectFields");
        
        getSaveToStoryCardRelatedEntity().copyTo(carrier);
        carrier.renameTableName(CoreLabel.RESULT_SET, "saveToFields");
        
        getinputDescOfInputs4Entity().copyTo(carrier);
        carrier.renameTableName(CoreLabel.RESULT_SET, "inputDesc");
        
        return carrier;
    }
    
    public static Carrier getSelectFromStoryCardRelatedEntity() throws QException {
        String domain = SessionManager.getCurrentDomain();
        
        String line = "SELECT\n"
                + "    SELECT_FROM_DB_ID,\n"
                + "    SELECT_FROM_TABLE_ID,\n"
                + "    SELECT_FROM_FIELD_ID,\n"
                + "    ID,\n"
                + "    INPUT_NAME,\n"
                + "    FK_BACKLOG_ID,\n"
                + "    (SELECT BACKLOG_NAME FROM  " + domain + ".TM_BACKLOG WHERE STATUS='A' AND ID=T.FK_BACKLOG_ID LIMIT 0,1) AS BACKLOG_NAME,\n"
                + "    FK_PROJECT_ID,\n"
                + "    (SELECT PROJECT_NAME FROM  " + domain + ".TM_PROJECT WHERE STATUS='A' AND ID=T.FK_PROJECT_ID LIMIT 0,1) AS PROJECT_NAME\n"
                + "FROM " + domain + ".TM_INPUT T\n"
                + "WHERE T.STATUS = 'A'\n"
                + "AND T.SELECT_FROM_FIELD_ID <>'' ";
        Carrier carrier = EntityManager.selectBySql(line);
        return carrier;
    }
    
    public static Carrier getSaveToStoryCardRelatedEntity() throws QException {
        String domain = SessionManager.getCurrentDomain();
        
        String line = "SELECT\n"
                + "    SEND_TO_DB_ID,\n"
                + "    SEND_TO_TABLE_ID,\n"
                + "    SEND_TO_FIELD_ID,\n"
                + "    ID,\n"
                + "    INPUT_NAME,\n"
                + "    FK_BACKLOG_ID,\n"
                + "    (SELECT BACKLOG_NAME FROM  " + domain + ".TM_BACKLOG WHERE STATUS='A' AND ID=T.FK_BACKLOG_ID LIMIT 0,1) AS BACKLOG_NAME,\n"
                + "    FK_PROJECT_ID,\n"
                + "    (SELECT PROJECT_NAME FROM  " + domain + ".TM_PROJECT WHERE STATUS='A' AND ID=T.FK_PROJECT_ID LIMIT 0,1) AS PROJECT_NAME\n"
                + "FROM " + domain + ".TM_INPUT T\n"
                + "WHERE T.STATUS = 'A'\n"
                + "AND T.SEND_TO_FIELD_ID <>'' ";
        Carrier carrier = EntityManager.selectBySql(line);
        return carrier;
    }
    
    public static Carrier getinputDescOfInputs4Entity() throws QException {
        String domain = SessionManager.getCurrentDomain();
        
        String line = "SELECT \n"
                + "    ID,\n"
                + "    FK_INPUT_ID,\n"
                + "    DESCRIPTION\n"
                + "FROM " + domain + ".TM_INPUT_DESCRIPTION D\n"
                + "WHERE D.STATUS='A'\n"
                + "AND FK_INPUT_ID IN (SELECT ID FROM  " + domain + ".TM_INPUT WHERE STATUS='A' AND ( SELECT_FROM_FIELD_ID <>'' OR SEND_TO_FIELD_ID <>'') )\n"
                + "ORDER BY ID;";
        Carrier carrier = EntityManager.selectBySql(line);
        return carrier;
    }
    
    public static Carrier getDBTableList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbId", cp.hasValue(carrier, "dbId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        ent.setFkDbId(carrier.get("dbId"));
        ent.addSortBy(EntityTmTable.TABLE_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier getDBTableByDbId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbId", cp.hasValue(carrier, "dbId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        getDBTableList(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getDBTableAndFields(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbId", cp.hasValue(carrier, "dbId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        Carrier cout = new Carrier();
        Carrier cIn = new Carrier();
        cIn.set("id", carrier.get("dbId"));
        getDBStructureList4Select(cIn).copyTo(cout);
        
        getDBTableList(carrier).copyTo(cout);
        cout.renameTableName(CoreLabel.RESULT_SET, "tableList");
        
        getDBFieldListByDbId(carrier).copyTo(cout);
        cout.renameTableName(CoreLabel.RESULT_SET, "fieldList");
        
        return cout;
    }
    
    public static Carrier getDBFieldList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        ent.setFkTableId(carrier.get("tableId"));
        ent.addSortBy(EntityTmField.FIELD_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier getDBFieldListByDbId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbId", cp.hasValue(carrier, "dbId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        ent.setFkDbId(carrier.get("dbId"));
        ent.addSortBy(EntityTmField.ORDER_NO);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier insertNewDb(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbName", cp.hasValue(carrier, "dbName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDatabase ent = new EntityTmDatabase();
        
        String id = carrier.get("id");
        if (id.trim().length() == 0) {
            ent.setDbName(carrier.get("dbName"));
            EntityManager.insert(ent);
        } else {
            ent.setId(id);
            EntityManager.select(ent);
            ent.setDbName(carrier.get("dbName"));
            EntityManager.update(ent);
        }
        
        carrier.set("id", ent.getId());
        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier deleteDbTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.delete(ent);
        
        carrier.set("id", ent.getId());
//        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateDbFieldOrderNo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("orderNo", cp.hasValue(carrier, "orderNo"));
        carrier.addController("fkTableId", cp.hasValue(carrier, "fkTableId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.select(ent);
        ent.setOrderNo(carrier.get("orderNo"));
        ent.setFkTableId(carrier.get("fkTableId"));
        EntityManager.update(ent);
        
        carrier.set("id", ent.getId());
        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateDbField(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("fieldName", cp.hasValue(carrier, "fieldName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.select(ent);
        ent.setFieldName(carrier.get("fieldName"));
        EntityManager.update(ent);
        
        carrier.set("id", ent.getId());
        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier deleteDbField(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmField ent = new EntityTmField();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.delete(ent);
        
        carrier.set("id", ent.getId());
        return carrier;
    }
    
    public static Carrier updateDbTableDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("description", cp.hasValue(carrier, "description"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.select(ent);
        ent.setDescription(carrier.get("description"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier updateDbTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("tableName", cp.hasValue(carrier, "tableName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.select(ent);
        String oldName = ent.getTableName();
        ent.setTableName(carrier.get("tableName"));
        EntityManager.update(ent);
        
        EntityTmDatabase entDb = new EntityTmDatabase();
        entDb.setId(ent.getFkDbId());
        entDb.setEndLimit(1);
        EntityManager.select(entDb);
        
        try {
            String tnOld = entDb.getDbName().toLowerCase() + "_" + oldName;
            String tnNew = entDb.getDbName().toLowerCase() + "_" + ent.getTableName().toLowerCase();
            String ln = "RENAME TABLE " + tnOld + " to " + tnNew + ";";
            EntityManager.executeUpdateByQuery(ln);
            
            String filename = Config.getProperty("entity.path") + SessionManager.getCurrentDomain() + "/" + entDb.getDbName().toLowerCase() + "/" + oldName.toLowerCase();
            String filenameNew = Config.getProperty("entity.path") + SessionManager.getCurrentDomain() + "/" + entDb.getDbName().toLowerCase() + "/" + ent.getTableName().toLowerCase();
            
            File file = new File(filename);
            File file2 = new File(filenameNew);
            
            if (!file2.exists()) {
                file.renameTo(file2);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        carrier.set("id", ent.getId());
        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateDbTableOrderNo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("orderNo", cp.hasValue(carrier, "orderNo"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable ent = new EntityTmTable();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.select(ent);
        ent.setOrderNo(carrier.get("orderNo"));
        EntityManager.update(ent);
        
        carrier.set("id", ent.getId());
        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier dropDatabase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDatabase ent = new EntityTmDatabase();
        String id = carrier.get("id");
        ent.setId(id);
        EntityManager.delete(ent);
        
        carrier.set("id", ent.getId());
//        getDBStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier insertNewTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbid", cp.hasValue(carrier, "dbid"));
        carrier.addController("tableName", cp.hasValue(carrier, "tableName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String orderNo = carrier.get("orderNo").trim().length() == 0
                ? getDbTableNextOrderNo(carrier.get("dbid"))
                : carrier.get("orderNo");
        
        EntityTmTable ent = new EntityTmTable();
        ent.setTableName(carrier.get("tableName"));
        ent.setFkDbId(carrier.get("dbid"));
        ent.setOrderNo(orderNo);
        EntityManager.insert(ent);
        carrier.set("id", ent.getId());
        getTableStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    private static String getDbTableNextOrderNo(String dbId) throws QException {
        String res = "1";
        
        if (dbId.trim().length() == 0) {
            return res;
        }
        EntityTmTable ent = new EntityTmTable();
        ent.setFkDbId(dbId);
        ent.addSortBy(EntityTmProjectCanvasCard.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    private static String getDbFieldNextOrderNo(String tableId) throws QException {
        String res = "1";
        
        if (tableId.trim().length() == 0) {
            return res;
        }
        EntityTmField ent = new EntityTmField();
        ent.setFkTableId(tableId);
        ent.addSortBy(EntityTmProjectCanvasCard.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier addTableFieldsAsLine(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbid", cp.hasValue(carrier, "dbid"));
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        carrier.addController("fieldName", cp.hasValue(carrier, "fieldName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String fields = carrier.get("fieldName");
        for (String line : fields.split("\\n")) {
            String orderNo = carrier.get("orderNo").trim().length() == 0
                    ? getDbFieldNextOrderNo(carrier.get("tableId"))
                    : carrier.get("orderNo");
            
            EntityTmField ent = new EntityTmField();
            ent.setFieldName(line);
            ent.setFkTableId(carrier.get("tableId"));
            ent.setFkDbId(carrier.get("dbid"));
            ent.setOrderNo(orderNo);
            EntityManager.insert(ent);
        }
        
        return carrier;
        
    }
    
    public static Carrier insertNewField(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("dbid", cp.hasValue(carrier, "dbid"));
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        carrier.addController("fieldName", cp.hasValue(carrier, "fieldName"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String orderNo = carrier.get("orderNo").trim().length() == 0
                ? getDbFieldNextOrderNo(carrier.get("tableId"))
                : carrier.get("orderNo");
        
        EntityTmField ent = new EntityTmField();
        ent.setFieldName(carrier.get("fieldName"));
        ent.setFkTableId(carrier.get("tableId"));
        ent.setFkDbId(carrier.get("dbid"));
        ent.setOrderNo(orderNo);
        EntityManager.insert(ent);
        
        carrier.set("id", ent.getId());
        carrier.set("orderNo", ent.getOrderNo());
        getFieldStructureList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getFieldByTableId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTable entTbl = new EntityTmTable();
        entTbl.setId(carrier.get("tableId"));
        EntityManager.select(entTbl);
        
        EntityTmField ent = new EntityTmField();
        ent.setFkTableId(carrier.get("tableId"));
        ent.setSortByAsc(true);
        ent.addSortBy("orderNo");
        carrier = EntityManager.select(ent);
        
        carrier.set("tableDescription", entTbl.getDescription());
        
        return carrier;
    }
    
    public static Carrier updateInput4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.isKeyExist(carrier, "value"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier addDatabaseRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("dbId", cp.hasValue(carrier, "dbId"));
        carrier.addController("action", cp.hasValue(carrier, "action"));
        carrier.addController("tableId", cp.hasValue(carrier, "tableId"));
        carrier.addController("fieldId", cp.hasValue(carrier, "fieldId"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        if (carrier.get("action").equals("select")) {
            ent.setSelectFromDbId(carrier.get("dbId"));
            ent.setSelectFromTableId(carrier.get("tableId"));
            ent.setSelectFromFieldId(carrier.get("fieldId"));
            
            EntityTmField entTm = new EntityTmField();
            entTm.setId(carrier.get("fieldId"));
            EntityManager.select(entTm);
            
            Carrier cr = new Carrier();
            cr.set("fkInputId", ent.getId());
            cr.set("attrName", "sa-selectedfield");
            cr.set("attrValue", entTm.getFieldName());
            cr.set("attrType", "comp");
            cr.set("fkProjectId", ent.getFkProjectId());
            cr.set("fkBacklogId", ent.getFkBacklogId());
            insertNewInputAttribute(cr);
            
        } else if (carrier.get("action").equals("send")) {
            ent.setSendToDbId(carrier.get("dbId"));
            ent.setSendToTableId(carrier.get("tableId"));
            ent.setSendToFieldId(carrier.get("fieldId"));
            
            EntityTmField entTm = new EntityTmField();
            entTm.setId(carrier.get("fieldId"));
            EntityManager.select(entTm);
            
            Carrier cr = new Carrier();
            cr.set("fkInputId", ent.getId());
            cr.set("attrName", "sa-selectedfield");
            cr.set("attrValue", entTm.getFieldName());
            cr.set("attrType", "comp");
            cr.set("fkProjectId", ent.getFkProjectId());
            cr.set("fkBacklogId", ent.getFkBacklogId());
            insertNewInputAttribute(cr);
        }
        
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputSendDataTo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("sendToInputId", cp.hasValue(carrier, "sendToInputId"));
        carrier.addController("sendToBacklogId", cp.hasValue(carrier, "sendToBacklogId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setSelectFromProjectId("");
        ent.setSendToBacklogId(carrier.get("sendToBacklogId"));
        ent.setSendToInputId(carrier.get("sendToInputId"));
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputSelectFrom(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("selectFromBacklogId", cp.hasValue(carrier, "selectFromBacklogId"));
        carrier.addController("action", cp.hasValue(carrier, "action"));
        carrier.addController("selectFromInputId", cp.hasValue(carrier, "selectFromInputId"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        if (carrier.get("action").equals("select")) {
            ent.setSelectFromProjectId("");
            ent.setSelectFromBacklogId(carrier.get("selectFromBacklogId"));
            ent.setSelectFromInputId(carrier.get("selectFromInputId"));
            
            EntityTmInput entTm = new EntityTmInput();
            entTm.setId(carrier.get("selectFromInputId"));
            EntityManager.select(entTm);
            
            Carrier cr = new Carrier();
            cr.set("fkInputId", ent.getId());
            cr.set("attrName", "sa-selectedfield");
            cr.set("attrValue", entTm.getInputName());
            cr.set("attrType", "comp");
            cr.set("fkProjectId", ent.getFkProjectId());
            cr.set("fkBacklogId", ent.getFkBacklogId());
            insertNewInputAttribute(cr);
            
        } else if (carrier.get("action").equals("send")) {
            ent.setSendToProjectId("");
            ent.setSendToBacklogId(carrier.get("selectFromBacklogId"));
            ent.setSendToInputId(carrier.get("selectFromInputId"));
            
            EntityTmInput entTm = new EntityTmInput();
            entTm.setId(carrier.get("selectFromInputId"));
            EntityManager.select(entTm);
            
            Carrier cr = new Carrier();
            cr.set("fkInputId", ent.getId());
            cr.set("attrName", "sa-selectedfield");
            cr.set("attrValue", entTm.getInputName());
            cr.set("attrType", "comp");
            cr.set("fkProjectId", ent.getFkProjectId());
            cr.set("fkBacklogId", ent.getFkBacklogId());
            insertNewInputAttribute(cr);
        }
        
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier removeSendSaveTo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setSendToProjectId("");
        ent.setSendToBacklogId("");
        ent.setSendToInputId("");
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier removeSendDBRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setSendToDbId("");
        ent.setSendToTableId("");
        ent.setSendToFieldId("");
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier removeDBRelation(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setSelectFromDbId("");
        ent.setSelectFromTableId("");
        ent.setSelectFromFieldId("");
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier removeRelationSource(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setSelectFromProjectId("");
        ent.setSelectFromBacklogId("");
        ent.setSelectFromInputId("");
        EntityManager.update(ent);
        
        getInputList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier getDeletedDocument(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmDocument.FK_PROJECT_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy("documentName");
        ent.setSortByAsc(true);
        ent.setStatus("D");
        carrier = EntityManager.select(ent);
        carrier.removeColoumn(ent.toTableName(), "documentBody");
        
        return carrier;
    }
    
    public Carrier getDeletedStoryCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmDocument.FK_PROJECT_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setStatus("D");
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier deleteForeverStoryCard(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("id"));
        ent.setStatus("D");
        EntityManager.select(ent);
        ent.setStatus("C");
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public Carrier deleteForeverDocument(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setId(carrier.get("id"));
        ent.setStatus("D");
        EntityManager.select(ent);
        ent.setStatus("C");
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public Carrier sendBackStoryCard(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("id"));
        ent.setStatus("D");
        EntityManager.select(ent);
        ent.setStatus("A");
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public Carrier sendBackDocument(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        String body = getDocumentBody(carrier.get("id"));
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setId(carrier.get("id"));
        ent.setStatus("D");
        EntityManager.select(ent);
        ent.setStatus("A");
        EntityManager.update(ent);
        
        updateDocumentBody(carrier.get("id"), body);
        
        return carrier;
    }
    
    public Carrier deleteDocument(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public Carrier getGeneralStatsCountNGB(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmDocument.FK_PROJECT_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        Carrier crOut = EntityManager.select(ent);
        
        return crOut;
    }
    
    public Carrier insertNewDocument(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmDocument.FK_PROJECT_ID));
        carrier.addController(EntityTmDocument.DOCUMENT_NAME, cp.hasValue(carrier, EntityTmDocument.DOCUMENT_NAME));
        carrier.addController(EntityTmDocument.DOCUMENT_BODY, cp.hasValue(carrier, EntityTmDocument.DOCUMENT_BODY));
        if (carrier.hasError()) {
            return carrier;
        }

//        System.out.println("-------------------------------------------------------------");
//        System.out.println(carrier.get("documentBody"));
//        System.out.println("-------------------------------------------------------------");
        if (carrier.get("id").length() == 0) {
            EntityTmDocument ent = new EntityTmDocument();
            ent.setFkProjectId(carrier.get("fkProjectId"));
//            ent.setDocumentBody(carrier.get("documentBody"));
            ent.setDocumentName(carrier.get("documentName"));
            ent.setCreatedBy(SessionManager.getCurrentUserId());
            ent.setCreateDate(QDate.getCurrentDate());
            ent.setCreateTime(QDate.getCurrentTime());
            EntityManager.insert(ent);
            
            updateDocumentBody(ent.getId(), carrier.get("documentBody"));
            
            carrier.set("id", ent.getId());
        } else {
            EntityTmDocument ent = new EntityTmDocument();
            ent.addExcludedField("documentBody");
            ent.setId(carrier.get("id"));
            EntityManager.select(ent);
            ent.setDocumentName(carrier.get("documentName"));
            ent.setUpdatedBy(SessionManager.getCurrentUserId());
            ent.setUpdatedDate(QDate.getCurrentDate());
            ent.setUpdatedTime(QDate.getCurrentTime());
            EntityManager.update(ent);
            
            updateDocumentBody(ent.getId(), carrier.get("documentBody"));
            carrier.set("id", ent.getId());
            
        }
        
        return carrier;
    }
    
    private void updateDocumentBody(String id, String body) throws QException, SQLException, UnsupportedEncodingException {
        String table = "tm_document";
        String field = "document_body";
        
        EntityManager.updateBlobField(table, id, field, body);
    }
    
    public Carrier getDocumentListByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmDocument.FK_PROJECT_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        carrier.removeColoumn(ent.toTableName(), "documentBody");
        
        return carrier;
    }
    
    public Carrier updateDocumentName(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        carrier.addController("name", cp.hasValue(carrier, "name"));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setDocumentName(carrier.get("name"));
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public Carrier getDocument(Carrier carrier) throws QException, SQLException, UnsupportedEncodingException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmDocument.ID, cp.hasValue(carrier, EntityTmDocument.ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmDocument ent = new EntityTmDocument();
        ent.setId(carrier.get("id"));
        carrier = EntityManager.select(ent);
        carrier.set("documentBody", getDocumentBody(ent.getId()));
        carrier.set("documentName", ent.getDocumentName());
        
        return carrier;
    }
    
    private static String getDocumentBody(String id) throws QException, SQLException, UnsupportedEncodingException {
        return EntityManager.readBlobField("TM_document", id, "document_body");
    }
    
    public static Carrier insertNewCanvasCard(Carrier carrier) throws QException {
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        EntityManager.mapCarrierToEntity(carrier, ent);
        if (carrier.get("orderNo").trim().length() > 0) {
            ent.setOrderNo(getNextCanvasCardOrderNo(carrier.get("fkProjectId")));
        }
        EntityManager.insert(ent);
        return carrier;
    }
    
    private static String getNextCanvasCardOrderNo(String fkProjectId) throws QException {
        String res = "1";
        
        if (fkProjectId.trim().length() == 0) {
            return res;
        }
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy(EntityTmProjectCanvasCard.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier updatetCanvasCard(Carrier carrier) throws QException {
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier deleteCanvasCard(Carrier carrier) throws QException {
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier getCanvasCardList(Carrier carrier) throws QException {
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public Carrier addBacklogtoCanvasCard(Carrier carrier) throws QException {
        EntityTmProjectCanvasCard ent = new EntityTmProjectCanvasCard();
        ent.setId(carrier.get("fkCanvasCardId"));
        EntityManager.select(ent);
        ent.setFkRelatedBacklogId(carrier.get("fkBacklogId"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier insertNewNetwork(Carrier carrier) throws QException {
        
        EntityTmNetwork ent = new EntityTmNetwork();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier insertNewBacklogDescription(Carrier carrier) throws QException {
        
        String orderNo = carrier.get("relatedDescId").trim().length() == 0
                ? getNextBacklogDescriptionOrderNo(carrier.get("fkProjectId"))
                : getPreviousBacklogDescriptionOrderNo(carrier.get("relatedDescId"));
        
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setDescription(carrier.get("description"));
        ent.setOrderNo(orderNo);
        EntityManager.insert(ent);
        
        return carrier;
    }
    
    private static String getNextBacklogDescriptionOrderNo(String fkProjectId) throws QException {
        String res = "11";
        
        if (fkProjectId.trim().length() == 0) {
            return res;
        }
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy(EntityTmBacklogDescription.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Float.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    private static String getPreviousBacklogDescriptionOrderNo(String relatedDescId) throws QException {
        String res = "1.01";
        
        if (relatedDescId.trim().length() == 0) {
            return res;
        }
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(relatedDescId);
        ent.addSortBy(EntityTmBacklogDescription.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Float.valueOf(ent.getOrderNo()) + 0.001);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier updatetBacklogDescription(Carrier carrier) throws QException {
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setDescription(carrier.get("description"));
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier deleteBacklogDescription(Carrier carrier) throws QException {
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public Carrier colorBacklogDescription(Carrier carrier) throws QException {
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setId(carrier.get("id"));
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            ent.setColoredType(carrier.get("color"));
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier colorInputDescription(Carrier carrier) throws QException {
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setId(carrier.get("id"));
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            ent.setColored(carrier.get("color"));
            EntityManager.update(ent);
        }
        
        getInputDescriptionList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getBacklogDescriptionList(Carrier carrier) throws QException {
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.addSortBy("orderNo");
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier insertNewCanvasZone(Carrier carrier) throws QException {
        EntityTmProjectCanvasZone ent = new EntityTmProjectCanvasZone();
        EntityManager.mapCarrierToEntity(carrier, ent);
        if (carrier.get("orderNo").trim().length() > 0) {
            ent.setOrderNo(getNextCanvasOrderNo(carrier.get("fkProjectId")));
        }
        EntityManager.insert(ent);
        return carrier;
    }
    
    private static String getNextCanvasOrderNo(String fkProjectId) throws QException {
        String res = "1";
        
        if (fkProjectId.trim().length() == 0) {
            return res;
        }
        EntityTmProjectCanvasZone ent = new EntityTmProjectCanvasZone();
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy(EntityTmProjectCanvasZone.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public static Carrier updatetCanvasZone(Carrier carrier) throws QException {
        EntityTmProjectCanvasZone ent = new EntityTmProjectCanvasZone();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.update(ent);
        return carrier;
    }
    
    public static Carrier deleteCanvasZone(Carrier carrier) throws QException {
        EntityTmProjectCanvasZone ent = new EntityTmProjectCanvasZone();
        ent.setId(carrier.get("id"));
        EntityManager.delete(ent);
        
        return carrier;
    }
    
    public static Carrier getCanvasZoneList(Carrier carrier) throws QException {
        EntityTmProjectCanvasZone ent = new EntityTmProjectCanvasZone();
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public static Carrier updateNetwork(Carrier carrier) throws QException {
        EntityTmNetwork entity = new EntityTmNetwork();
        entity.setId(carrier.getValue(EntityTmNetwork.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteNetwork(Carrier carrier) throws QException {
        EntityTmNetwork entity = new EntityTmNetwork();
        entity.setId(carrier.getValue(EntityTmNetwork.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getNetworkList(Carrier carrier) throws QException {
        
        EntityTmNetwork ent = new EntityTmNetwork();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getNetworkList"));
        return carrier;
    }
    
    public static Carrier insertNewTaskType(Carrier carrier) throws QException {
        
        EntityTmTaskType ent = new EntityTmTaskType();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.setValue("id", ent.getId());
        return carrier;
        
    }
    
    public static Carrier updateTaskType(Carrier carrier) throws QException {
        EntityTmTaskType entity = new EntityTmTaskType();
        entity.setId(carrier.getValue(EntityTmTaskType.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTaskType(Carrier carrier) throws QException {
        EntityTmTaskType entity = new EntityTmTaskType();
        entity.setId(carrier.getValue(EntityTmTaskType.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTaskTypeList(Carrier carrier) throws QException {
        
        EntityTmTaskType ent = new EntityTmTaskType();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTaskTypeList"));
        return carrier;
    }
    
    public static Carrier insertNewProject(Carrier carrier) throws QException {
        
        EntityTmProject ent = new EntityTmProject();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkNetworkId(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        carrier.setValue("id", ent.getId());
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmProjectPermission entPer = new EntityTmProjectPermission();
        entPer.setFkProjectId(ent.getId());
        entPer.setFkUserId(SessionManager.getCurrentUserId());
        EntityManager.insert(entPer);
        
        return carrier;
        
    }
    
    public static Carrier updateProject(Carrier carrier) throws QException {
        EntityTmProject entity = new EntityTmProject();
        entity.setId(carrier.getValue(EntityTmProject.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteProject(Carrier carrier) throws QException {
        if (!isInMyProjectListWithPermission(carrier.getValueAsString("id"))) {
            return carrier;
        }
        
        EntityTmProject entity = new EntityTmProject();
        entity.setId(carrier.getValue(EntityTmProject.ID).toString());
        EntityManager.delete(entity);
        
        deleteRelatedProjectPermission(entity.getId());
        return carrier;
    }
    
    private static void deleteRelatedProjectPermission(String id) throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkProjectId(id);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            ent.setId(c.getValue(tn, i, "id").toString());
            EntityManager.delete(ent);
        }
        
    }
    
    private static String getMyProjectsWithPermission() throws QException {
        return SessionManager.isCurrentUserAdmin() || SessionManager.isCurrentUserModerator()
                ? ""
                : "-1" + CoreLabel.IN + getMyProjects();
    }
    
    public static String getMyProjects() throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkUserId(SessionManager.getCurrentUserId());
        return "-1" + CoreLabel.IN + EntityManager.select(ent).getValueLine(ent.toTableName(), "fkProjectId");
    }
    
    public static Carrier getProjectList(Carrier carrier) throws QException {
        EntityTmProject ent = new EntityTmProject();
        ent.setId(getMyProjects());
        ent.addSortBy("projectName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProjectList"));
        return carrier;
    }
    
    private static boolean isInMyProjectList(String id) throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkUserId(SessionManager.getCurrentUserId());
        ent.setFkProjectId(id);
        return EntityManager.select(ent).getTableRowCount(ent.toTableName()) > 0;
    }
    
    private static boolean isInMyProjectListWithPermission(String id) throws QException {
        if (SessionManager.isCurrentUserAdmin() || SessionManager.isCurrentUserModerator()) {
            return true;
        }
        EntityTmProject ent = new EntityTmProject();
        ent.setFkNetworkId(SessionManager.getCurrentUserId());
        ent.setId(id);
        return EntityManager.select(ent).getTableRowCount(ent.toTableName()) > 0;
    }
    
    public static Carrier getProjectInfoById(Carrier carrier) throws QException {
//        if (!isInMyProjectList(carrier.getValue("id").toString()))
        EntityTmProjectList ent = new EntityTmProjectList();
        ent.setId(carrier.getValue("id").toString());
        ent.addSortBy("projectName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProjectList"));
        return carrier;
    }
    
    private static String getProlectListByPermission() throws QException {
        return SessionManager.isCurrentUserAdmin() || SessionManager.isCurrentUserModerator()
                ? ""
                : getMyProjects();
    }
    
    private static String getAuthorizedAssignees() throws QException {
        return SessionManager.isCurrentUserAdmin() || SessionManager.isCurrentUserModerator()
                ? ""
                : SessionManager.getCurrentUserId();
    }
    
    public Carrier getBacklogTablenameList(Carrier carrier) throws QException {
        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.addDistinctField(EntityTmInput.TABLE_NAME);
        ent.addSortBy(EntityTmInput.TABLE_NAME);
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public Carrier assignLabelToTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkLabelId", cp.hasValue(carrier, "fkLabelId"));
        carrier.addController("fkBacklogTaskId", cp.hasValue(carrier, "fkBacklogTaskId"));
//        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("assign", cp.hasValue(carrier, "assign"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTaskAndLabel ent = new EntityTmRelTaskAndLabel();
        
        boolean isAssigned = carrier.get("assign").equals("1");
        
        if (isAssigned) {
            ent.setFkBacklogTaskId(carrier.get("fkBacklogTaskId"));
            ent.setFkTaskLabelId(carrier.get("fkLabelId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() == 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                ent.setFkBacklogId(carrier.get("fkBacklogId"));
                EntityManager.insert(ent);
            }
        } else {
            ent.setFkBacklogTaskId(carrier.get("fkBacklogTaskId"));
            ent.setFkTaskLabelId(carrier.get("fkLabelId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() > 0) {
                EntityManager.delete(ent);
            }
        }
        
        return carrier;
        
    }
    
    public static Carrier assignSprintToTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkSprintId", cp.hasValue(carrier, "fkSprintId"));
        carrier.addController("fkBacklogTaskId", cp.hasValue(carrier, "fkBacklogTaskId"));
//        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("assign", cp.hasValue(carrier, "assign"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTaskAndSprint ent = new EntityTmRelTaskAndSprint();
        
        boolean isAssigned = carrier.get("assign").equals("1");
        
        if (isAssigned) {
            ent.setFkBacklogTaskId(carrier.get("fkBacklogTaskId"));
            ent.setFkTaskSprintId(carrier.get("fkSprintId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() == 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                ent.setFkBacklogId(carrier.get("fkBacklogId"));
                EntityManager.insert(ent);
            }
        } else {
            ent.setFkBacklogTaskId(carrier.get("fkBacklogTaskId"));
            ent.setFkTaskSprintId(carrier.get("fkSprintId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() > 0) {
                EntityManager.delete(ent);
            }
        }
        
        return carrier;
        
    }
    
    public Carrier assignSprint(Carrier carrier) throws QException {
        EntityTmRelBacklogAndSprint ent = new EntityTmRelBacklogAndSprint();
        
        boolean isAssigned = carrier.get("assign").equals("1");
        
        if (isAssigned) {
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            ent.setFkTaskSprintId(carrier.get("fkSprintId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() == 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                EntityManager.insert(ent);
            }
        } else {
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            ent.setFkTaskSprintId(carrier.get("fkSprintId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() > 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                EntityManager.delete(ent);
            }
        }
        
        getBacklogList4Select(carrier.get("fkBacklogId")).copyTo(carrier);
        return carrier;
        
    }
    
    public Carrier assignLabel(Carrier carrier) throws QException {
        EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
        
        boolean isAssigned = carrier.get("assign").equals("1");
        
        if (isAssigned) {
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            ent.setFkTaskLabelId(carrier.get("fkLabelId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() == 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                EntityManager.insert(ent);
            }
        } else {
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            ent.setFkTaskLabelId(carrier.get("fkLabelId"));
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            if (ent.getId().trim().length() > 0) {
                ent.setFkProjectId(carrier.get("fkProjectId"));
                EntityManager.delete(ent);
            }
        }
        
        getBacklogList4Select(carrier.get("fkBacklogId")).copyTo(carrier);
        return carrier;
        
    }
    
    public Carrier addTableNameToInputs(Carrier carrier) throws QException {
        String[] inputIds = carrier.get("fkInputIds").split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String ids = "";
        String tname = carrier.get("tableName").equals("__empty__") ? "" : carrier.get("tableName");
        for (String id : inputIds) {
            if (id.trim().length() == 0) {
                continue;
            }
            ids += id + CoreLabel.IN;
            EntityTmInput ent = new EntityTmInput();
            ent.setId(id);
            EntityManager.select(ent);
            ent.setTableName(tname);
            EntityManager.update(ent);
        }
        
        if (ids.length() > 0) {
            getInputList4Select(ids).copyTo(carrier);
        }
        return carrier;
    }
    
    public static Carrier getProjectList4Modal(Carrier carrier) throws QException {
        String proIds = getProlectListByPermission();
        
        EntityTmProject ent = new EntityTmProject();
        ent.setId(proIds);
        ent.setProjectName(carrier.getValue("projectName").toString());
        ent.addSortBy("projectName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        String backlogId = carrier.getValueLine(ent.toTableName(), EntityTmProject.FK_TRIGGER_BACKLOG_ID);
        if (backlogId.length() > 6) {
            EntityTmBacklog entBL = new EntityTmBacklog();
            entBL.setId(backlogId);
            Carrier cr = EntityManager.select(entBL);
            carrier.mergeCarrier(ent.toTableName(), new String[]{EntityTmProject.FK_TRIGGER_BACKLOG_ID},
                    cr, entBL.toTableName(), new String[]{"id"}, new String[]{"backlogName"}, "", true);
        }
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProjectList"));
        return carrier;
    }
    
    private static Carrier getProjectPermission(Carrier carrier) throws QException {
        
        int rc = carrier.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            String id = carrier.getValue(CoreLabel.RESULT_SET, i, "id").toString();
            if (id.trim().length() == 0) {
                continue;
            }
            EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
            ent.setFkProjectId(id);
            ent.addSortBy(EntityTmProjectPermissionList.USER_NAME);
            ent.setSortByAsc(true);
            Carrier tc = EntityManager.select(ent);
            
        }
        
        return carrier;
    }
    
    public static Carrier insertNewProgress(Carrier carrier) throws QException {
        
        EntityTmProgress ent = new EntityTmProgress();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier updateProgress(Carrier carrier) throws QException {
        EntityTmProgress entity = new EntityTmProgress();
        entity.setId(carrier.getValue(EntityTmProgress.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteProgress(Carrier carrier) throws QException {
        EntityTmProgress entity = new EntityTmProgress();
        entity.setId(carrier.getValue(EntityTmProgress.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getProgressList(Carrier carrier) throws QException {
        
        EntityTmProgress ent = new EntityTmProgress();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProgressList"));
        return carrier;
    }
    
    public static Carrier insertNewTaskStatus(Carrier carrier) throws QException {
        
        EntityTmTaskStatus ent = new EntityTmTaskStatus();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier updateTaskStatus(Carrier carrier) throws QException {
        EntityTmTaskStatus entity = new EntityTmTaskStatus();
        entity.setId(carrier.getValue(EntityTmTaskStatus.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTaskStatus(Carrier carrier) throws QException {
        EntityTmTaskStatus entity = new EntityTmTaskStatus();
        entity.setId(carrier.getValue(EntityTmTaskStatus.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTaskStatusList(Carrier carrier) throws QException {
        
        EntityTmTaskStatus ent = new EntityTmTaskStatus();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTaskStatusList"));
        return carrier;
    }
    
    public static Carrier insertNewTaskPriority(Carrier carrier) throws QException {
        
        EntityTmTaskPriority ent = new EntityTmTaskPriority();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier updateTaskPriority(Carrier carrier) throws QException {
        EntityTmTaskPriority entity = new EntityTmTaskPriority();
        entity.setId(carrier.getValue(EntityTmTaskPriority.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTaskPriority(Carrier carrier) throws QException {
        EntityTmTaskPriority entity = new EntityTmTaskPriority();
        entity.setId(carrier.getValue(EntityTmTaskPriority.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTaskPriorityList(Carrier carrier) throws QException {
        
        EntityTmTaskPriority ent = new EntityTmTaskPriority();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTaskPriorityList"));
        return carrier;
    }
    
    public static Carrier insertNewTaskCategory(Carrier carrier) throws QException {
        
        EntityTmTaskCategory ent = new EntityTmTaskCategory();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier updateTaskCategory(Carrier carrier) throws QException {
        EntityTmTaskCategory entity = new EntityTmTaskCategory();
        entity.setId(carrier.getValue(EntityTmTaskCategory.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTaskCategory(Carrier carrier) throws QException {
        EntityTmTaskCategory entity = new EntityTmTaskCategory();
        entity.setId(carrier.getValue(EntityTmTaskCategory.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTaskCategoryList(Carrier carrier) throws QException {
        
        EntityTmTaskCategory ent = new EntityTmTaskCategory();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTaskCategoryList"));
        return carrier;
    }
    
    public static Carrier insertNewTask(Carrier carrier) throws QException {
        
        EntityTmTask ent = new EntityTmTask();
        EntityManager.mapCarrierToEntity(carrier, ent);
        
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setOrderNo(getOrderNo());
        
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        insertNewTaskAssignee(ent.getId(), carrier.getValue("fkTaskAssigneeId").toString());
        insertNewTaskReporter(ent.getId(), carrier.getValue("fkTaskReporterId").toString());
        insertNewTaskFile(ent.getId(), carrier);
        
        return carrier;
        
    }
    
    private static void insertNewTaskFile(String fkTaskId, Carrier carrier)
            throws QException {
        String[] fname = new String[]{"fileUrl1", "fileUrl2", "fileUrl3", "fileUrl4"};
        
        for (String fn : fname) {
            String fpath = carrier.getValue(fn).toString();
            if (fpath.trim().length() == 0) {
                continue;
            }
            EntityTmTaskFile ent = new EntityTmTaskFile();
            ent.setFkTaskId(fkTaskId);
            ent.setFileUrl(fpath);
            EntityManager.insert(ent);
        }
    }
    
    private static String getOrderNo() throws QException {
        String res = "";
        
        EntityTmTaskList ent = new EntityTmTaskList();
        res = String.valueOf(EntityManager.getRowCount(ent) + 1);
        
        return res;
    }
    
    private static void insertNewTaskAssignee(String fkTaskId, String fkUserId)
            throws QException {
        
        EntityTmTaskAssignee ent = new EntityTmTaskAssignee();
        ent.setFkTaskId(fkTaskId);
        ent.setFkUserId(fkUserId);
        EntityManager.insert(ent);
    }
    
    private static void insertNewTaskReporter(String fkTaskId, String fkReporterId)
            throws QException {
        String[] rid = fkReporterId.split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        
        for (String s : rid) {
            EntityTmTaskReporter ent = new EntityTmTaskReporter();
            ent.setFkTaskId(fkTaskId);
            ent.setFkUserId(s);
            EntityManager.insert(ent);
        }
        
    }
    
    public static Carrier removeReporter(Carrier carrier) throws QException {
//        EntityTmTaskReporter ent = new EntityTmTaskReporter();
//        ent.setFkTaskId(carrier.getValue("id").toString());
//        String ids = EntityManager.select(ent).getValueLine(ent.toString());

//        if (ids.trim().length() > 0) {
//            EntityTmTaskReporter ent1 = new EntityTmTaskReporter();
//            ent1.setId(ids);
//            EntityManager.delete(ent1);
//        }
        String[] rid = carrier.getValue("fkTaskReporterId").toString()
                .split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        
        for (String r : rid) {
            EntityTmTaskReporter ent2 = new EntityTmTaskReporter();
            ent2.setFkTaskId(carrier.getValue("id").toString());
            ent2.setFkUserId(r);
            String id2 = EntityManager.select(ent2).getValueLine(ent2.toTableName());
            
            if (id2.trim().length() > 0) {
                ent2.setId(id2);
                EntityManager.delete(ent2);
            }
        }
        
        return new Carrier();
    }
    
    public static Carrier updateTask(Carrier carrier) throws QException {
        EntityTmTask entity = new EntityTmTask();
        entity.setId(carrier.getValue(EntityTmTask.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier forwardTask(Carrier carrier) throws QException {
        EntityTmTaskAssignee entity = new EntityTmTaskAssignee();
        entity.setFkTaskId(carrier.getValue(EntityTmTask.ID).toString());
        EntityManager.select(entity);
        
        String newId = carrier.getValue("fkTaskAssigneeId").toString();
        if (!newId.trim().equals(entity.getFkUserId())) {
            entity.setFkUserId(newId);
            EntityManager.update(entity);
        }
        return new Carrier();
    }
    
    public static Carrier addReporter(Carrier carrier) throws QException {
        EntityTmTaskReporter entity = new EntityTmTaskReporter();
        entity.setFkTaskId(carrier.getValue(EntityTmTask.ID).toString());
        String ids = EntityManager.select(entity)
                .getValueLine(entity.toTableName(), EntityTmTaskReporter.FK_USER_ID);
        
        String newId = carrier.getValue("fkTaskReporterId").toString();
        
        if (!ids.contains(newId)) {
            entity.setFkUserId(newId);
            EntityManager.insert(entity);
        }
        return new Carrier();
    }
    
    public static Carrier deleteTask(Carrier carrier) throws QException {
        EntityTmTask entity = new EntityTmTask();
        entity.setId(carrier.getValue(EntityTmTask.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTaskList(Carrier carrier) throws QException {
        String taskId = carrier.getValue("id").toString();
        
        EntityTmTaskList ent = new EntityTmTaskList();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        if (taskId.trim().length() > 0) {
            carrier = addFile(carrier, ent.toTableName(), taskId);
            carrier = addReporter(carrier, ent.toTableName(), taskId);
            
        }
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTaskList"));
        return carrier;
    }
    
    private static Carrier addFile(Carrier carrier, String table, String id) throws QException {
        
        if (id.trim().length() == 0) {
            return carrier;
        }
        
        EntityTmTaskFile ent = new EntityTmTaskFile();
        ent.setFkTaskId(id);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int row = c.getTableRowCount(tn);
        for (int i = 0; i < row; i++) {
            carrier.setValue(table, 0, "fileUrl" + String.valueOf(i + 1),
                    c.getValue(tn, i, EntityTmTaskFile.FILE_URL));
        }
        return carrier;
    }
    
    public static Carrier addFileToBacklog(Carrier carrier) throws QException {
        String files[] = carrier.get("fileUrl").split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String fname : files) {
            if (fname.trim().length() == 0) {
                continue;
            }
            EntityTmTaskFile ent = new EntityTmTaskFile();
            ent.setFkBacklogId(carrier.get("fkBacklogId"));
            ent.setFileUrl(fname);
            ent.setFkProjectId(carrier.get("fkProjectId"));
            EntityManager.insert(ent);
        }
        getBacklogList4Select(carrier.get("fkBacklogId")).copyTo(carrier);
        return carrier;
    }
    
    private static Carrier addReporter(Carrier carrier, String table, String id) throws QException {
        
        if (id.trim().length() == 0) {
            return carrier;
        }
        
        EntityTmTaskReporter ent = new EntityTmTaskReporter();
        ent.setFkTaskId(id);
        String reporterIds = EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmTaskReporter.FK_USER_ID);
        
        if (reporterIds.trim().length() == 0) {
            return carrier;
        }
        
        EntityCrUserList entUs = new EntityCrUserList();
        entUs.setId(reporterIds);
        Carrier c = EntityManager.select(entUs);
        
        String tn = entUs.toTableName();
        int row = c.getTableRowCount(tn);
        String st = "";
        for (int i = 0; i < row; i++) {
            st += c.getValue(tn, i, EntityCrUserList.USER_PERSON_NAME);
            st += " ";
            st += c.getValue(tn, i, EntityCrUserList.USER_PERSON_SURNAME);
            st += " ";
            st += c.getValue(tn, i, EntityCrUserList.USER_PERSON_MIDDLENAME);
            st += i < row - 1 ? ", " : "";
        };
        
        carrier.setValue(table, 0, "reporterName", st);
        return carrier;
    }
    
    public static Carrier finishTask(Carrier carrier) throws QException {
        EntityTmTask entity = new EntityTmTask();
        entity.setId(carrier.getValue(EntityTmTask.ID).toString());
        EntityManager.select(entity);
        
        entity.setFinishDate(QDate.getCurrentDate());
        entity.setFinishTime(QDate.getCurrentTime());
        entity.setCompletedDuration(carrier.getValue(EntityTmTask.COMPLETED_DURATION).toString());
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier insertNewProjectPermission(Carrier carrier) throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        EntityManager.mapCarrierToEntity(carrier, ent);
        
        String fkUserId[] = carrier.getValue("fkUserId").toString()
                .split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String s : fkUserId) {
            ent.setFkUserId(s);
            EntityManager.insert(ent);
            carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        }
        return carrier;
        
    }
    
    public static Carrier updateProjectPermission(Carrier carrier) throws QException {
        EntityTmProjectPermission entity = new EntityTmProjectPermission();
        entity.setId(carrier.getValue(EntityTmProjectPermission.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteProjectPermission(Carrier carrier) throws QException {
        EntityTmProjectPermission entity = new EntityTmProjectPermission();
        entity.setId(carrier.getValue(EntityTmProjectPermission.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getProjectPermissionList(Carrier carrier) throws QException {
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
        
    }
    
    public static Carrier getProjectPermissionList4Report(Carrier carrier) throws QException {
//        String proIds = getProlectListByPermission();
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        EntityManager.mapCarrierToEntity(carrier, ent);
//        ent.setFkProjectId(proIds);
        ent.setFkUserId(getAuthorizedAssignees());
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier getAssigneeByProjectId(Carrier carrier) throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        String userIds = "-1" + CoreLabel.IN + EntityManager.select(ent)
                .getValueLine(ent.toTableName(), "fkUserId");
        
        EntityCrUserList entU = new EntityCrUserList();
        entU.setId(userIds);
        carrier = EntityManager.select(entU);
        return carrier;
    }
    
    public Carrier getReporterByProjectId(Carrier carrier) throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        String userIds = "-1" + CoreLabel.IN + EntityManager.select(ent)
                .getValueLine(ent.toTableName(), "fkUserId");
        
        EntityCrUserList entU = new EntityCrUserList();
        entU.setId(userIds);
        carrier = EntityManager.select(entU);
        return carrier;
    }
    
    public Carrier getReporterByTasktId(Carrier carrier) throws QException {
//        EntityTmTask entTask = new EntityTmTask();
//        entTask.setFkProjectId(carrier.getValue(EntityTmTask.FK_PROJECT_ID).toString());
//        String taskId = EntityManager.select(entTask).getValueLine(entTask.toTableName());

        String taskId = carrier.getValue(EntityTmTask.ID).toString();
        
        if (taskId.trim().length() == 0) {
            return carrier;
        }
        
        EntityTmTaskReporter ent = new EntityTmTaskReporter();
        ent.setFkTaskId(taskId);
        String userIds = "-1" + CoreLabel.IN + EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmTaskReporter.FK_USER_ID);
        
        EntityCrUserList entU = new EntityCrUserList();
        entU.setId(userIds);
        carrier = EntityManager.select(entU);
        return carrier;
    }
    
    public Carrier singleUpdate(Carrier carrier) throws QException {
        EntityTmTask ent = new EntityTmTask();
        ent.setId(carrier.getValue("taskId").toString());
        EntityManager.select(ent);
        
        String compId = carrier.getValue("compId").toString();
        String v = carrier.getValue("value").toString();
        EntityManager.setEntityValue(ent, compId, v);
        EntityManager.update(ent);
        
        return carrier;
    }
    
    private static String nextBKLOrderNo(String fkProjectId) throws QException {
        String st = "1";
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy("orderNo");
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            st = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        return st;
    }
    
    public static Carrier insertNewTicket(Carrier carrier) throws QException, Exception {
        carrier.setValue("isFromCustomer", "1");
        carrier = insertNewBacklog(carrier);
        if (carrier.getValue("filename").toString().trim().length() > 0
                || carrier.getValue("description").toString().trim().length() > 0) {
            insertComment4Ticket(carrier);
        }
        return carrier;
    }
    
    private static void insertComment4Ticket(Carrier carrier) throws QException, Exception {
        Carrier c = new Carrier();
        String desc = carrier.getValue("description").toString().trim().length() > 0
                ? carrier.getValue("description").toString()
                : carrier.getValue("backlogName").toString();
        c.setValue(EntityTmTaskComment.COMMENT, desc);
        c.setValue(EntityTmTaskComment.FK_BACKLOG_ID, carrier.getValue("id"));
        c.setValue("fileName", carrier.getValue("filename"));
        
        insertNewComment(c);
        
    }
    
    public static Carrier insertNewUserStory(Carrier carrier) throws QException {
        return insertNewBacklogShort(carrier);
    }
    
    public static Carrier updateUserStsory4Status(Carrier carrier) throws QException {
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("id"));
        
        EntityManager.select(ent);
        ent.setBacklogNo(carrier.get("backlogNo"));
        ent.setBacklogStatus(carrier.get("backlogStatus"));
        EntityManager.update(ent);
        
        getBacklogList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateTask4Status(Carrier carrier) throws QException {
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(carrier.get("id"));
        
        EntityManager.select(ent);
        ent.setTaskOrderNo(carrier.get("orderNo"));
        ent.setTaskStatus(carrier.get("taskStatus"));
        EntityManager.update(ent);
        
        setBacklogStatus(ent.getFkBacklogId());
        
        getTaskList4Short(ent.getId()).copyTo(carrier);
        getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier insertNewBacklogShort(Carrier carrier) throws QException {
        
        String fkProjectId = carrier.get("fkProjectId");
        String backlogName = carrier.get("backlogName");
        String backlogStatus = carrier.get("backlogStatus").length() == 0
                ? BACKLOG_STATUS_NEW
                : carrier.get("backlogStatus");
        
        String orderNo = nextBKLOrderNo(fkProjectId);
        String backlogNo = carrier.get("backlogNo").length() == 0
                ? orderNo
                : carrier.get("backlogNo");
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setIsApi(carrier.get("isApi"));
        ent.setFkProjectId(fkProjectId);
        ent.setBacklogName(backlogName);
        ent.setPriority("1");
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setBacklogStatus(backlogStatus);
        ent.setOrderNo(orderNo);
        ent.setBacklogNo(backlogNo);
        EntityManager.insert(ent);
        
        carrier.setValue("id", ent.getId());
        carrier.setValue("fkBacklogId", ent.getId());
        carrier.setValue("backlogNo", ent.getBacklogNo());
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        carrier.setValue("isNotAssign", false);
        getBacklogList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateUserStsory4ShortChange(Carrier carrier) throws QException {
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        if (ent.getBacklogNo().length() == 0) {
            ent.setBacklogNo("0");
        }
        EntityManager.update(ent);
        
        getBacklogList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public Carrier selectUsersByProject4Select(Carrier carrier) throws QException {
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy(EntityTmProjectPermissionList.USER_NAME);
        carrier = EntityManager.select(ent);
        
        String ids = carrier.getValueLine(ent.toTableName(), "fkUserId");
        if (ids.length() > 1) {
            Carrier crUser = new Carrier();
            crUser.set("id", ids);
            crUser = crUser.callService("serviceCrGetUserList");
            
            carrier.mergeCarrier(ent.toTableName(), "fkUserId", crUser, CoreLabel.RESULT_SET, "id", new String[]{"userImage"});
        }
        
        return carrier;
    }
    
    public static Carrier updateTask4ShortChange(Carrier carrier) throws QException {
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        String oldVal = EntityManager.getEntityValue(ent, carrier.get("type"));
        String newVal = carrier.get("value");
        
        EntityManager.setEntityValue(ent, carrier.get("type"), carrier.get("value"));
        ent.setUpdatedBy(SessionManager.getCurrentUserId());
        ent.setLastUpdatedDate(QDate.getCurrentDate());
        ent.setLastUpdatedTime(QDate.getCurrentTime());
        if (ent.getTaskOrderNo().length() == 0) {
            ent.setTaskOrderNo("0");
        }
        EntityManager.update(ent);
        
        try {
            setBacklogStatus(ent.getFkBacklogId());
        } catch (Exception e) {
        }
        
        getTaskList4Short(ent.getId()).copyTo(carrier);
        try {
            getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        } catch (Exception e) {
        }
        
        temporarySetStatusInYelo(ent.getId(), ent.getTaskStatus());
        
        String fieldName = getUpdatedFieldName(carrier.get("type"));
        sendMailNotificationOnChange(ent.getId(), ent.getFkBacklogId(), fieldName, oldVal, newVal);
        
        return carrier;
    }
    
    private static void temporarySetStatusInYelo(String taskId, String status) {
        if (taskId.length() <= 2 || status.length() == 0) {
            return;
        }
        
        String ln = "update " + SessionManager.getCurrentDomain() + ".customer_request_new_story_card set request_status='" + status + "' where fk_related_task_id='" + taskId + "'";
        try {
            EntityManager.executeUpdateByQuery(ln);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static String getUpdatedFieldName(String fieldType) {
        String field = "";
        if (fieldType.equals("taskStatus")) {
            field = "STATUS";
        } else if (fieldType.equals("taskNature")) {
            field = "TASK NATURE";
        } else if (fieldType.equals("taskPriority")) {
            field = "PRIORITY";
        } else if (fieldType.equals("fkProjectId")) {
            field = "PROJECT";
        } else if (fieldType.equals("fkBacklogId")) {
            field = "STORY CARD";
        } else if (fieldType.equals("fkAssigneeId")) {
            field = "ASSIGNEE";
        }
        return field;
    }
    
    public static Carrier insertNewBacklog(Carrier carrier) throws QException {
        String fkProjectId = carrier.getValue("fkProjectId").toString();
        
        EntityTmBacklog ent = new EntityTmBacklog();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setBacklogStatus(BACKLOG_STATUS_NEW);
        ent.setOrderNo(nextBKLOrderNo(fkProjectId));
        ent.setBacklogNo(nextBKLOrderNo(fkProjectId));
        if (SessionManager.isCurrentUserCustomer()) {
            ent.setIsFromCustomer("1");
            carrier.setValue("isFromCustomer", "1");
        }
        EntityManager.insert(ent);
        carrier.setValue("id", ent.getId());
        carrier.setValue("fkBacklogId", ent.getId());
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        carrier.setValue("isNotAssign", false);
        insertNewBacklogSprint(carrier);
        insertNewBacklogLabel(carrier);
        
        getBacklogList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier setUserStoryAsAPIFeature(Carrier carrier) throws QException {
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue("fkBacklogId").toString());
        EntityManager.select(entity);
        entity.setIsApi(carrier.getValueAsString("isApi"));
        EntityManager.update(entity);
        Carrier tc = EntityManager.select(entity);
        tc.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        getBacklogList4Select(entity.getId()).copyTo(tc);
        
        return tc;
    }
    
    public static Carrier updateBacklog(Carrier carrier) throws QException {
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        entity.setJiraKey(carrier.get("jiraIssueKey"));
        EntityManager.update(entity);
        Carrier tc = EntityManager.select(entity);
        tc.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        carrier.setValue("isNotAssign", false);
        insertNewBacklogSprint(carrier);
        insertNewBacklogLabel(carrier);
        
        getBacklogList4Select(entity.getId()).copyTo(tc);
        
        return tc;
    }
    
    public static Carrier updateBacklogByDesc(Carrier carrier) throws QException {
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        EntityManager.select(entity);
        entity.setDescriptionSourced(carrier.getValue("description").toString());
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        updateProcecessDesc(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        setNewBacklogHistory4ProcessDescUpdate(entity);
        
        getBacklogList4Select(entity.getId()).copyTo(carrier);
        return carrier;
    }
    
    private static void updateProcecessDesc(EntityTmBacklog ent) throws QException {
        EntityTmBacklogDescription entity = new EntityTmBacklogDescription();
        entity.setFkBacklogId(ent.getId());
        entity.setOrderNo("0");
        Carrier cr = EntityManager.select(entity);
        int rc = cr.getTableRowCount(entity.toTableName());
        
        if (rc > 0) {
            entity.setDescription(ent.getDescriptionSourced());
            EntityManager.update(entity);
        } else {
            entity.setFkProjectId(ent.getFkProjectId());
            entity.setFkBacklogId(ent.getId());
            entity.setDescription(ent.getDescriptionSourced());
            entity.setOrderNo("0");
            EntityManager.insert(entity);
        }
    }
    
    public static Carrier updateBacklogBySourced(Carrier carrier) throws QException {
        String status = carrier.isKeyExist(EntityTmBacklog.FK_SOURCED_ID) && carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString().length() > 0
                ? getBacklogStatusById(carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString())
                : BACKLOG_STATUS_NEW;
        
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        Carrier c = EntityManager.select(entity);
        
        if (entity.getIsSourced().equals("1")) {
            c.setValue("isSourced", entity.getIsSourced());
            c.setValue("backlogStatus", status);
            c.addController("general", "Sourced User Story can not be binded.");
            return c;
        }
        
        entity.setFkSourcedId(carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString());
        entity.setBacklogStatus(status);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        carrier.setValue("backlogStatus", status);
        carrier.setValue("isSourced", entity.getIsSourced());

        //gonderen backlog ticket-dirse, o zaman source US ucun general task type elave edilmelidir.        
        if (isBacklogTicket(carrier.getValueAsString("id"))) {
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setFkBacklogId(status);
        }
        
        return carrier;
    }
    
    public static Carrier bindTicketToSUS(Carrier carrier) throws QException, Exception {
        String sourcedId = "";
        String status = "";
        String id = carrier.getValueAsString("id");
        
        if (carrier.getValueAsString("fkSourcedId").length() == 0) {
            EntityTmBacklog entMain = new EntityTmBacklog();
            entMain.setId(carrier.getValueAsString("id"));
            EntityManager.select(entMain);
            
            entMain.setIsFromCustomer("");
            entMain.setBacklogStatus(BACKLOG_STATUS_NEW);
            entMain.setCreatedBy(SessionManager.getCurrentUserId());
            entMain.setCreatedDate(QDate.getCurrentDate());
            entMain.setCreatedTime(QDate.getCurrentTime());
            entMain.setIsSourced("1");
            entMain.setFkSourcedId("");
            entMain.setOrderNo(nextBKLOrderNo(entMain.getFkProjectId()));
            EntityManager.insert(entMain);
            
            sourcedId = entMain.getId();
            status = BACKLOG_STATUS_NEW;
        } else {
            sourcedId = carrier.getValueAsString("fkSourcedId");
            status = carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString().length() > 0
                    ? getBacklogStatusById(carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString())
                    : BACKLOG_STATUS_NEW;
        }

        //add new backlog task
        String assignees[] = carrier.getValueAsString("assignee").split("\\|");
        for (String ass : assignees) {
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setCreatedBy(SessionManager.getCurrentUserId());
            entTask.setCreatedDate(QDate.getCurrentDate());
            entTask.setCreatedTime(QDate.getCurrentTime());
            entTask.setEstimatedHours("0");
            entTask.setSpentHours("0");
            entTask.setCompletedDuration("0");
            entTask.setFkAssigneeId(ass);
            entTask.setFkBacklogId(sourcedId);
            entTask.setIsGeneral("1");
            entTask.setTaskStatus(BACKLOG_STATUS_NEW);
            
            EntityManager.insert(entTask);
        }
        
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        Carrier c = EntityManager.select(entity);
        
        if (entity.getIsSourced().equals("1")) {
            c.setValue("isSourced", entity.getIsSourced());
            c.setValue("backlogStatus", status);
            c.addController("general", "Sourced User Story can not be binded.");
            return c;
        }
        
        entity.setFkSourcedId(sourcedId);
        entity.setBacklogStatus(status);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        carrier.setValue("backlogStatus", status);
        carrier.setValue("isSourced", entity.getIsSourced());
        
        setBacklogStatus(sourcedId);

        ///add comment to sourced backlog
        String comment = getCommentsOfTicket4BugNotification(id);

        ///end update backlog task
        Carrier cComment = new Carrier();
        cComment.setValue(EntityTmTaskComment.COMMENT, comment);
        cComment.setValue("fileName", getCommentFilesOfTicket4BugNotification(id));
        cComment.setValue(EntityTmTaskComment.FK_BACKLOG_ID, sourcedId);
        cComment.setValue("commentType", "U");
        insertNewComment(cComment);

//        //gonderen backlog ticket-dirse, o zaman source US ucun general task type elave edilmelidir.        
//        if (isBacklogTicket(carrier.getValueAsString("id"))) {
//            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
//            entTask.setFkBacklogId(status);
//        }
        return carrier;
    }
    
    public Carrier getProjectCountList(Carrier carrier) throws QException {
        String ids = carrier.isKeyExist("id")
                ? carrier.getValueAsString("id") : getMyProjectsWithPermission();
        
        EntityTmProjectCountList ent = new EntityTmProjectCountList();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setId(ids);
        ent.addSortBy("projectName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier getBoundedUserStories(Carrier carrier) throws QException {
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setFkSourcedId(carrier.getValueAsString("id"));
        carrier = EntityManager.select(ent);
        String ids = carrier.getValueLine(ent.toTableName(), "id", "|");
        Carrier c = new Carrier();
        c.setValue("ids", ids);
        c.setValue("status", carrier.getValue(ent.toTableName(), 0, EntityTmBacklogList.BACKLOG_STATUS));
        
        return c;
    }
    
    public static Carrier notifyTicketAsBug(Carrier carrier) throws QException, Exception {
        String status = BACKLOG_STATUS_ONGOING;
        //update ticket informations
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        Carrier c = EntityManager.select(entity);
        
        if (entity.getIsSourced().equals("1")) {
            c.setValue("isSourced", entity.getIsSourced());
            c.setValue("backlogStatus", status);
            c.addController("general", "Sourced User Story can not be binded.");
            return c;
        }
        
        entity.setFkSourcedId(carrier.getValue(EntityTmBacklog.FK_SOURCED_ID).toString());
        entity.setBacklogStatus(status);
        EntityManager.update(entity);

        //update status of the sourced backlog because of the bug
        //in any bug status ongoing olur
        //update backlog task status
        EntityTmBacklogTaskList entTaskList = new EntityTmBacklogTaskList();
        entTaskList.setId(carrier.getValueAsString("fkBacklogTaskId"));
        EntityManager.select(entTaskList);
        
        EntityTmBacklogTask entTask = new EntityTmBacklogTask();
        entTask.setId(carrier.getValueAsString("fkBacklogTaskId"));
        EntityManager.select(entTask);
        entTask.setUpdatedBy(SessionManager.getCurrentUserId());
        entTask.setLastUpdatedDate(QDate.getCurrentDate());
        entTask.setLastUpdatedTime(QDate.getCurrentTime());
        entTask.setIsDetectedBug("1");
        entTask.setTaskStatus(BACKLOG_STATUS_ONGOING);
        EntityManager.update(entTask);

        //update status of sourced backlog
        setBacklogStatus(carrier.getValueAsString(EntityTmBacklog.FK_SOURCED_ID));
        
        String comment = getCommentsOfTicket4BugNotification(carrier.getValueAsString("id"));
        String desc = "(<i>Detected Bug on <b>" + entTaskList.getTaskTypeName() + "</b></i>)<br>" + comment;
        setNewBacklogHistory4TaskTypeNofityBug(entTaskList, desc);
        ///end update backlog task

        Carrier cComment = new Carrier();
        cComment.setValue(EntityTmTaskComment.COMMENT, comment);
        cComment.setValue("fileName", getCommentFilesOfTicket4BugNotification(carrier.getValueAsString("id")));
        cComment.setValue(EntityTmTaskComment.FK_BACKLOG_ID, carrier.getValue("fkSourcedId"));
        cComment.setValue("commentType", "B");
        insertNewComment(cComment);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        carrier.setValue("backlogStatus", status);
        carrier.setValue("isSourced", entity.getIsSourced());
        return carrier;
    }
    
    private static String getCommentFilesOfTicket4BugNotification(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return "";
        }
        EntityTmTaskCommentList ent = new EntityTmTaskCommentList();
        ent.setFkBacklogId(backlogId);
        String ids = "-1" + CoreLabel.IN + EntityManager.select(ent).getValueLine(ent.toTableName());
        
        EntityTmCommentFile entFile = new EntityTmCommentFile();
        entFile.setFkCommentId(ids);
        return EntityManager.select(entFile).getValueLine(entFile.toTableName(), EntityTmCommentFile.FILE_NAME, "|");
    }
    
    private static String getCommentsOfTicket4BugNotification(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return "";
        }
        EntityTmTaskCommentList ent = new EntityTmTaskCommentList();
        ent.setFkBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        String res = "";
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            res += "<b>" + ent.getUsername() + "</b>: " + ent.getComment() + "<br>";
        }
        return res;
    }
    
    private static String getBacklogStatusById(String id) throws QException {
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(id);
        EntityManager.select(entity);
        return entity.getBacklogStatus();
    }
    
    private static void setNewBacklogHistory4ProcessDescUpdate(EntityTmBacklog ent) throws QException {
        String body = "<b>Process Description Changed to</b>  " + ent.getDescriptionSourced();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getId(), body,
                BACKLOG_HISTORY_TYPE_PROCESS_DESC_UPDATE, ent.getId(), ent.getDescriptionSourced(), "", "");
    }
    
    private static void setNewBacklogHistory4InputDescUpdate(EntityTmBacklog ent) throws QException {
        String body = "<b>Process Description Changed to</b>  " + ent.getDescriptionSourced();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_UPDATE, ent.getId(), ent.getDescriptionSourced(), "", "");
    }
    
    public static Carrier deleteBacklog(Carrier carrier) throws QException {
        EntityTmBacklog entity = new EntityTmBacklog();
        entity.setId(carrier.getValue(EntityTmBacklog.ID).toString());
        EntityManager.delete(entity);
        
        try {
            EntityTmRelBacklogAndLabel entLbl = new EntityTmRelBacklogAndLabel();
            
            entLbl.setFkBacklogId(entity.getId());
            String lblIds = EntityManager.select(entLbl)
                    .getValueLine(entLbl.toTableName());
            entLbl.setFkBacklogId("");
            entLbl.setId(lblIds);
            EntityManager.delete(entLbl);
            
        } catch (Exception e) {
        }
        
        try {
            EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
            entSprint.setFkBacklogId(entity.getId());
            String sprintIds = EntityManager.select(entSprint)
                    .getValueLine(entSprint.toTableName());
            entSprint.setFkBacklogId("");
            entSprint.setId(sprintIds);
            EntityManager.delete(entSprint);
        } catch (Exception e) {
        }
        
        try {
            EntityTmBacklogDependency entDependency = new EntityTmBacklogDependency();
            entDependency.setFkBacklogId(entity.getId());
            String ids = EntityManager.select(entDependency)
                    .getValueLine(entDependency.toTableName());
            entDependency.setFkBacklogId("");
            entDependency.setId(ids);
            EntityManager.delete(entDependency);
        } catch (Exception e) {
        }
        
        try {
            EntityTmBacklogDependency entDependency = new EntityTmBacklogDependency();
            entDependency.setFkParentBacklogId(entity.getId());
            String ids = EntityManager.select(entDependency)
                    .getValueLine(entDependency.toTableName());
            entDependency.setFkBacklogId("");
            entDependency.setId(ids);
            EntityManager.delete(entDependency);
        } catch (Exception e) {
        }
        return carrier;
    }
    
    public static Carrier getBacklogList(Carrier carrier) throws QException {
        String fkBacklogId = "";
        if (carrier.isKeyExist("id")) {
            fkBacklogId = carrier.getValue("id").toString();
        } else {
            if (carrier.isKeyExist("fkSprintId")
                    && carrier.getValue("fkSprintId").toString().length() > 0) {
                EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
                entSprint.setFkTaskSprintId(carrier.getValue("fkSprintId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entSprint).getValueLine(entSprint.toTableName(),
                        EntityTmRelBacklogAndSprint.FK_BACKLOG_ID);
            }
            
            if (carrier.isKeyExist("fkLabelId")
                    && carrier.getValue("fkLabelId").toString().length() > 0) {
                EntityTmRelBacklogAndLabel entLabel = new EntityTmRelBacklogAndLabel();
                entLabel.setFkBacklogId(fkBacklogId);
                entLabel.setFkTaskLabelId(carrier.getValue("fkLabelId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entLabel).getValueLine(entLabel.toTableName(),
                        EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            }
        }
        
        String assignedToMe = "";
        String taskType = "";
        if (carrier.isKeyExist("userStoriesAssignedToMe")
                && carrier.getValue("userStoriesAssignedToMe").toString().trim().length() > 0) {
            assignedToMe = SessionManager.getCurrentUserId();
        }
        if (carrier.isKeyExist("taskType")
                && carrier.getValue("taskType").toString().trim().length() > 0) {
            taskType = carrier.getValue("taskType").toString();
        }
        
        if (assignedToMe.length() > 0 || taskType.length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(assignedToMe);
            entList.setFkTaskTypeId(taskType);
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
        }
        
        if (carrier.isKeyExist("assignee")
                && carrier.getValue("assignee").toString().trim().length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(carrier.getValue("assignee").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        if (carrier.isKeyExist("assignedLabel")
                && carrier.getValue("assignedLabel").toString().trim().length() > 0) {
            EntityTmChangeReqLabel entList = new EntityTmChangeReqLabel();
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkLabelId(carrier.getValue("assignedLabel").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setId(fkBacklogId);
        ent.addSortBy("backlogNo");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
//        carrier.addTableRowCount(CoreLabel.RESULT_SET,
//                EntityManager.getRowCount(ent));
        return carrier;
    }
    
    private static Carrier getBacklogList4Select(String[] backlogId) throws QException {
        String res = "";
        for (String id : backlogId) {
            if (id.trim().length() > 0) {
                res += id;
                res += CoreLabel.IN;
            }
        }
        return getBacklogList4Select(res);
    }
    
    private static Carrier getBacklogList4Select(String backlogId) throws QException {
        Carrier cr = new Carrier();
        cr.set("fkBacklogId", backlogId);
        cr = getBacklogList4Select(cr);
        cr.renameTableName(CoreLabel.RESULT_SET, "userStoryTable");
        return cr;
    }
    
    public static Carrier getBacklogList4Select(Carrier carrier) throws QException {
        EntityTmTaskFile entFile = new EntityTmTaskFile();
        entFile.setFkProjectId(carrier.getValue("fkProjectId").toString());
        entFile.setFkBacklogId(carrier.getValue("fkBacklogId").toString());
        entFile.setSortByAsc(true);
        Carrier crFile = EntityManager.select(entFile);
        Carrier crFileId = crFile.getKVPairListFromTable(entFile.toTableName(), "fkBacklogId",
                EntityTmTaskFile.ID);
        Carrier crFileIsPinned = crFile.getKVPairListFromTable(entFile.toTableName(), EntityTmTaskFile.ID,
                EntityTmTaskFile.IS_PINNED);
        crFile = crFile.getKVPairListFromTable(entFile.toTableName(), "fkBacklogId",
                EntityTmTaskFile.FILE_URL);
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkProjectId(carrier.getValue("fkProjectId").toString());
        entIn.setFkBacklogId(carrier.getValue("fkBacklogId").toString());
        entIn.addSortBy("orderNo");
        entIn.setSortByAsc(true);
        Carrier crIn = EntityManager.select(entIn);
        crIn = crIn.getKVPairListFromTable(entIn.toTableName(), "fkBacklogId",
                EntityTmBacklogTaskList.ID);
        
        EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
        entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
        entList.setFkBacklogId(carrier.getValue("fkBacklogId").toString());
        Carrier crList = EntityManager.select(entList);
        Carrier crKVList = crList.getKVPairListFromTable(entList.toTableName(), "fkBacklogId",
                EntityTmBacklogTaskList.FK_TASK_TYPE_ID);
        Carrier crAssigneeList = crList.getKVPairListFromTable(entList.toTableName(), "fkBacklogId",
                EntityTmBacklogTaskList.FK_ASSIGNEE_ID);
        
        EntityTmChangeReqLabel entChangeLabel = new EntityTmChangeReqLabel();
        entChangeLabel.setFkProjectId(carrier.get("fkProjectId"));
        entChangeLabel.setFkBacklogId(carrier.get("fkBacklogId"));
        Carrier crChangeLabelFull = EntityManager.select(entChangeLabel);
        Carrier crChangeLabel = crChangeLabelFull.getKVPairListFromTable(entChangeLabel.toTableName(), "fkBacklogId",
                EntityTmChangeReqLabel.FK_LABEL_ID);
        Carrier crNotifiedLabel = crChangeLabelFull.getKVPairListFromTable(entChangeLabel.toTableName(), "fkBacklogId",
                EntityTmChangeReqLabel.ID);
        
        EntityTmRelBacklogAndLabel entLabel = new EntityTmRelBacklogAndLabel();
        entLabel.setFkProjectId(carrier.get("fkProjectId"));
        entLabel.setFkBacklogId(carrier.get("fkBacklogId"));
        Carrier crLabel = EntityManager.select(entLabel);
        crLabel = crLabel.getKVPairListFromTable(entLabel.toTableName(), "fkBacklogId",
                EntityTmRelBacklogAndLabel.FK_TASK_LABEL_ID);
        
        EntityTmBacklogDescription entDesc = new EntityTmBacklogDescription();
        entDesc.setFkProjectId(carrier.get("fkProjectId"));
        entDesc.setFkBacklogId(carrier.get("fkBacklogId"));
        Carrier crDesc = EntityManager.select(entDesc);
        Carrier crDescNew = crDesc.getKVPairListFromTable(entDesc.toTableName(), "fkBacklogId",
                EntityTmBacklogDescription.FK_RELATED_API_ID);
        Carrier crDescName = crDesc.getKVPairListFromTable(entDesc.toTableName(), "fkBacklogId",
                EntityTmBacklogDescription.SHORT_DESC_FORR_API, "###");
        
        EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
        entSprint.setFkProjectId(carrier.get("fkProjectId"));
        entSprint.setFkBacklogId(carrier.get("fkBacklogId"));
        Carrier crSprint = EntityManager.select(entSprint);
        crSprint = crSprint.getKVPairListFromTable(entSprint.toTableName(), "fkBacklogId",
                EntityTmRelBacklogAndSprint.FK_TASK_SPRINT_ID);
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(carrier.get("fkBacklogId"));
        ent.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addSortBy("backlogNo");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        String tn = CoreLabel.RESULT_SET;
        int rc = carrier.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(carrier, tn, i, ent);
            
            carrier.setValue(tn, i, "inputIds", crIn.get(ent.getId()));
            carrier.setValue(tn, i, "taskTypeIds", crKVList.get(ent.getId()));
            carrier.setValue(tn, i, "assigneeIds", crAssigneeList.get(ent.getId()));
            carrier.setValue(tn, i, "assignedLabelIds", crChangeLabel.get(ent.getId()));
            carrier.setValue(tn, i, "notifiedLabelIds", crNotifiedLabel.get(ent.getId()));
            carrier.setValue(tn, i, "labelIds", crLabel.get(ent.getId()));
            carrier.setValue(tn, i, "sprintIds", crSprint.get(ent.getId()));
            carrier.setValue(tn, i, "fileUrl", crFile.get(ent.getId()));
            carrier.setValue(tn, i, "fileUrlIds", crFileId.get(ent.getId()));
            carrier.setValue(tn, i, "descRelatedId", crDescNew.get(ent.getId()));
            carrier.setValue(tn, i, "descRelatedNote", crDescName.get(ent.getId()));
            crFileIsPinned.copyTo(carrier);
            
        }
        
        return carrier;
    }
    
    public static Carrier getBacklogList4AllGui(Carrier carrier) throws QException {
        String fkBacklogId = "";
        if (carrier.isKeyExist("id")) {
            fkBacklogId = carrier.getValue("id").toString();
        } else {
            if (carrier.isKeyExist("fkSprintId")
                    && carrier.getValue("fkSprintId").toString().length() > 0) {
                EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
                entSprint.setFkTaskSprintId(carrier.getValue("fkSprintId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entSprint).getValueLine(entSprint.toTableName(),
                        EntityTmRelBacklogAndSprint.FK_BACKLOG_ID);
            }
            
            if (carrier.isKeyExist("fkLabelId")
                    && carrier.getValue("fkLabelId").toString().length() > 0) {
                EntityTmRelBacklogAndLabel entLabel = new EntityTmRelBacklogAndLabel();
                entLabel.setFkBacklogId(fkBacklogId);
                entLabel.setFkTaskLabelId(carrier.getValue("fkLabelId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entLabel).getValueLine(entLabel.toTableName(),
                        EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            }
        }
        
        String assignedToMe = "";
        String taskType = "";
        if (carrier.isKeyExist("userStoriesAssignedToMe")
                && carrier.getValue("userStoriesAssignedToMe").toString().trim().length() > 0) {
            assignedToMe = SessionManager.getCurrentUserId();
        }
        if (carrier.isKeyExist("taskType")
                && carrier.getValue("taskType").toString().trim().length() > 0) {
            taskType = carrier.getValue("taskType").toString();
        }
        
        if (assignedToMe.length() > 0 || taskType.length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(assignedToMe);
            entList.setFkTaskTypeId(taskType);
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
        }
        
        if (carrier.isKeyExist("assignee")
                && carrier.getValue("assignee").toString().trim().length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(carrier.getValue("assignee").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        if (carrier.isKeyExist("assignedLabel")
                && carrier.getValue("assignedLabel").toString().trim().length() > 0) {
            EntityTmChangeReqLabel entList = new EntityTmChangeReqLabel();
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkLabelId(carrier.getValue("assignedLabel").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setInputCount(CoreLabel.GE + "1");
        ent.setId(fkBacklogId);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        getInputList4AllGui(carrier);
//        carrier.addTableRowCount(CoreLabel.RESULT_SET,
//                EntityManager.getRowCount(ent));
        return carrier;
    }

//    ,String backlogId,String backlogName
    public static void getInputList4AllGui(Carrier carrier) throws QException {
        int rc = carrier.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            ArrayList<String> backlogList = new ArrayList<>(); //to check dependency for loop iteration
            String backlodId = carrier.getValue(CoreLabel.RESULT_SET, i, "id").toString();
            String outcome = getInputDetailedList4AllGui(backlogList, backlodId);
            carrier.setValue(CoreLabel.RESULT_SET, i, "inputList", outcome);
        }
        
    }
    
    public static Carrier getBacklogListByListView(Carrier carrier) throws QException {
        String fkBacklogId = "";
        if (carrier.isKeyExist("id")) {
            fkBacklogId = carrier.getValue("id").toString();
        } else {
            if (carrier.isKeyExist("fkSprintId")
                    && carrier.getValue("fkSprintId").toString().length() > 0) {
                EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
                entSprint.setFkTaskSprintId(carrier.getValue("fkSprintId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entSprint).getValueLine(entSprint.toTableName(),
                        EntityTmRelBacklogAndSprint.FK_BACKLOG_ID);
            }
            
            if (carrier.isKeyExist("fkLabelId")
                    && carrier.getValue("fkLabelId").toString().length() > 0) {
                EntityTmRelBacklogAndLabel entLabel = new EntityTmRelBacklogAndLabel();
                entLabel.setFkBacklogId(fkBacklogId);
                entLabel.setFkTaskLabelId(carrier.getValue("fkLabelId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entLabel).getValueLine(entLabel.toTableName(),
                        EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            }
        }
        
        String assignedToMe = "";
        String taskType = "";
        if (carrier.isKeyExist("userStoriesAssignedToMe")
                && carrier.getValue("userStoriesAssignedToMe").toString().trim().length() > 0) {
            assignedToMe = SessionManager.getCurrentUserId();
        }
        if (carrier.isKeyExist("taskType")
                && carrier.getValue("taskType").toString().trim().length() > 0) {
            taskType = carrier.getValue("taskType").toString();
        }
        
        if (assignedToMe.length() > 0 || taskType.length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(assignedToMe);
            entList.setFkTaskTypeId(taskType);
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
        }
        
        if (carrier.isKeyExist("assignedLabel")
                && carrier.getValue("assignedLabel").toString().trim().length() > 0) {
            EntityTmChangeReqLabel entList = new EntityTmChangeReqLabel();
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkLabelId(carrier.getValue("assignedLabel").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        EntityTmBacklogListWithTask ent = new EntityTmBacklogListWithTask();
        ent.setFkAssigneeId(carrier.getValue("assignee").toString());
        ent.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.addSortBy(EntityTmBacklogListWithTask.ASSIGNEE_NAME);
        ent.addSortBy(EntityTmBacklogListWithTask.SPRINT_NAME);
        ent.addSortBy(EntityTmBacklogListWithTask.BACKLOG_NAME);
        if (!carrier.isKeyExist("asc") || !carrier.isKeyExist("desc")) {
//            ent.setSortByAsc(true);
        }
//        ent.setSortByAsc();
        ent.setId(fkBacklogId);
//        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
//        carrier.addTableSequence(CoreLabel.RESULT_SET,
//                EntityManager.getListSequenceByKey("getBacklogList"));
        return carrier;
    }
    
    public static Carrier getBacklogListByListView4Pivot(Carrier carrier) throws QException {
        String fkBacklogId = "";
        if (carrier.isKeyExist("id")) {
            fkBacklogId = carrier.getValue("id").toString();
        } else {
            if (carrier.isKeyExist("fkSprintId")
                    && carrier.getValue("fkSprintId").toString().length() > 0) {
                EntityTmRelBacklogAndSprint entSprint = new EntityTmRelBacklogAndSprint();
                entSprint.setFkTaskSprintId(carrier.getValue("fkSprintId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entSprint).getValueLine(entSprint.toTableName(),
                        EntityTmRelBacklogAndSprint.FK_BACKLOG_ID);
            }
            
            if (carrier.isKeyExist("fkLabelId")
                    && carrier.getValue("fkLabelId").toString().length() > 0) {
                EntityTmRelBacklogAndLabel entLabel = new EntityTmRelBacklogAndLabel();
                entLabel.setFkBacklogId(fkBacklogId);
                entLabel.setFkTaskLabelId(carrier.getValue("fkLabelId").toString());
                fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entLabel).getValueLine(entLabel.toTableName(),
                        EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            }
        }
        
        String assignedToMe = "";
        String taskType = "";
        if (carrier.isKeyExist("userStoriesAssignedToMe")
                && carrier.getValue("userStoriesAssignedToMe").toString().trim().length() > 0) {
            assignedToMe = SessionManager.getCurrentUserId();
        }
        if (carrier.isKeyExist("taskType")
                && carrier.getValue("taskType").toString().trim().length() > 0) {
            taskType = carrier.getValue("taskType").toString();
        }
        
        if (assignedToMe.length() > 0 || taskType.length() > 0) {
            EntityTmBacklogTaskList entList = new EntityTmBacklogTaskList();
            entList.setFkProjectId(carrier.getValue("fkProjectId").toString());
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkAssigneeId(assignedToMe);
            entList.setFkTaskTypeId(taskType);
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
        }
        
        if (carrier.isKeyExist("assignedLabel")
                && carrier.getValue("assignedLabel").toString().trim().length() > 0) {
            EntityTmChangeReqLabel entList = new EntityTmChangeReqLabel();
            entList.setFkBacklogId(fkBacklogId);
            entList.setFkLabelId(carrier.getValue("assignedLabel").toString());
            fkBacklogId = "-1" + CoreLabel.IN + EntityManager.select(entList).getValueLine(entList.toTableName(),
                    EntityTmRelBacklogAndLabel.FK_BACKLOG_ID);
            
        }
        
        EntityTmBacklogTask entBTask = new EntityTmBacklogTask();
        entBTask.setFkAssigneeId(carrier.getValue("assignee").toString());
        entBTask.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        EntityManager.mapCarrierToEntity(carrier, entBTask);
        entBTask.addSortBy(EntityTmBacklogListWithTask.ASSIGNEE_NAME);
        entBTask.addSortBy(EntityTmBacklogListWithTask.SPRINT_NAME);
        entBTask.addSortBy(EntityTmBacklogListWithTask.BACKLOG_NAME);
        if (!carrier.isKeyExist("asc") || !carrier.isKeyExist("desc")) {
            entBTask.setSortByAsc(true);
        }
        entBTask.setId(fkBacklogId);
        Carrier cr = EntityManager.select(entBTask);
        
        EntityTmBacklog ent = new EntityTmBacklog();
//        ent.setFkAssigneeId(carrier.getValue("assignee").toString());
        ent.addAndStatementField(EntityTmBacklogList.BACKLOG_NAME);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.addSortBy(EntityTmBacklogListWithTask.ASSIGNEE_NAME);
        ent.addSortBy(EntityTmBacklogListWithTask.SPRINT_NAME);
        ent.addSortBy(EntityTmBacklogListWithTask.BACKLOG_NAME);
        if (!carrier.isKeyExist("asc") || !carrier.isKeyExist("desc")) {
            ent.setSortByAsc(true);
        }
        ent.setId(fkBacklogId);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
//        carrier.addTableSequence(CoreLabel.RESULT_SET,
//                EntityManager.getListSequenceByKey("getBacklogList"));
        return carrier;
    }
    
    public static Carrier getSourcedBacklogListWithTask(Carrier carrier) throws QException {
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setDeepWhere(false);
        ent.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        ent.setIsSourced("1");
        carrier = EntityManager.select(ent);
        
        EntityTmBacklogTask entTask = new EntityTmBacklogTask();
        entTask.setDeepWhere(false);
        entTask.setFkBacklogId("-1" + CoreLabel.IN + carrier.getValueLine(ent.toTableName()));
        carrier = EntityManager.select(entTask);
        
        EntityTmBacklog entFinal = new EntityTmBacklog();
        entFinal.setDeepWhere(false);
        entFinal.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        entFinal.setIsSourced("1");
        entFinal.setId("-1" + CoreLabel.IN + carrier.getValueLine(entTask.toTableName(), "fkBacklogId"));
        entFinal.addSortBy("backlogName");
        entFinal.setSortByAsc(true);
        carrier = EntityManager.select(entFinal);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getBacklogList"));
        return carrier;
    }
    
    public static Carrier getSourcedBacklogList(Carrier carrier) throws QException {
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setDeepWhere(true);
//        ent.addDeepWhereStatementField(EntityTmBacklogList.CREATED_DATE);
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        ent.addSortBy("backlogName");
        ent.setIsSourced("1");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getBacklogList"));
        return carrier;
    }
    
    public static Carrier getSourcedBacklogList4Combo(Carrier carrier) throws QException {
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        ent.addSortBy("backlogName");
        ent.setIsSourced("1");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier getSourcedBacklogListWithSection4Combo(Carrier carrier) throws QException {
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkProjectId(carrier.getValue("fkProjectId").toString());
//        entIn.setFkBacklogId(carrier.getValueLine(CoreLabel.RESULT_SET));
        entIn.setComponentType("sctn");
        Carrier crIn = EntityManager.select(entIn);
        Carrier crKVIn = crIn.getKVFromTable(entIn.toTableName(), "fkBacklogId", "id");
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        ent.addSortBy("backlogName");
        ent.setIsSourced("1");
        ent.setInputCount(CoreLabel.GT + '0');
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = carrier.getTableRowCount(tn);
        Carrier crOut = new Carrier();
        int idx = 0;
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(carrier, tn, i, ent);
            if (crKVIn.isKeyExist(ent.getId())) {
                crOut.setValue(CoreLabel.RESULT_SET, idx, "id", ent.getId());
                crOut.setValue(CoreLabel.RESULT_SET, idx, "backlogName", ent.getBacklogName());
                crOut.setValue(CoreLabel.RESULT_SET, idx, "orderNo", ent.getOrderNo());
                idx++;
            }
        }
//        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);

//        carrier.mergeCarrier(CoreLabel.RESULT_SET, "id", crIn, entIn.toTableName(), "fkBacklogId", new String[]{"inputName"});
        return crOut;
    }
    
    public static Carrier getBacklogList4Combo(Carrier carrier) throws QException {
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(carrier.getValue("fkProjectId").toString());
        ent.addSortBy("backlogName");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier getBacklogInfoById(Carrier carrier) throws QException {
        String backlogId = carrier.getValue("id").toString();
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(backlogId);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        Carrier tc = getLabelListByBacklogId(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkLabelId",
                tc.getValueLine(CoreLabel.RESULT_SET, "fkTaskLabelId", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "labelName",
                tc.getValueLine(CoreLabel.RESULT_SET, "labelName", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "labelColor",
                tc.getValueLine(CoreLabel.RESULT_SET, "labelColor", CoreLabel.SEPERATOR_VERTICAL_LINE));
        
        Carrier tc1 = getSprintListByBacklogId(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkSprintId",
                tc1.getValueLine(CoreLabel.RESULT_SET, "fkTaskSprintId", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "sprintName",
                tc1.getValueLine(CoreLabel.RESULT_SET, "sprintName", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "sprintColor",
                tc1.getValueLine(CoreLabel.RESULT_SET, "sprintColor", CoreLabel.SEPERATOR_VERTICAL_LINE));
        
        Carrier childBound = getChildBoundUserStories(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkChildBoundUserStoryId",
                childBound.getValueLine(CoreLabel.RESULT_SET, EntityTmBacklogList.ID, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkChildBoundUserStoryName",
                childBound.getValueLine(CoreLabel.RESULT_SET, EntityTmBacklogList.BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
        
        Carrier dependency = getUserStoryDependency(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyId",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.FK_PARENT_BACKLOG_ID, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyOrderNo",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, "parentBacklogOrderNo", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyName",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.PARENT_BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
        
        Carrier dependencyChild = getUserStoryChildDependency(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyId",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.FK_BACKLOG_ID, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyName",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyOrderNo",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, "backlogOrderNo", CoreLabel.SEPERATOR_VERTICAL_LINE));
        
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        return carrier;
    }
    
    public static Carrier getBacklogCoreInfoById(Carrier carrier) throws QException {
        String backlogId = carrier.getValue("id").toString();
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(backlogId);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        EntityManager.mapEntityToCarrier(ent, carrier, true);
        
        return carrier;
    }
    
    private static Carrier getChildBoundUserStories(String backlogId) throws QException {
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setFkSourcedId(backlogId);
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    private static Carrier getUserStoryDependency(String backlogId) throws QException {
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        ent.setFkBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String backlogIdIn = c.getValue(tn, i, EntityTmBacklogDependencyList.FK_PARENT_BACKLOG_ID).toString();
            if (backlogIdIn.trim().length() > 0) {
                EntityTmBacklog entBL = new EntityTmBacklog();
                entBL.setId(backlogIdIn);
                EntityManager.select(entBL);
                c.setValue(tn, i, "parentBacklogOrderNo", entBL.getOrderNo());
            } else {
                c.setValue(tn, i, "parentBacklogOrderNo", "");
            }
        }
        
        c.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    private static Carrier getUserStoryChildDependency(String backlogId) throws QException {
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        ent.setFkParentBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String backlogIdIn = c.getValue(tn, i, EntityTmBacklogDependencyList.FK_BACKLOG_ID).toString();
            if (backlogIdIn.trim().length() > 0) {
                EntityTmBacklog entBL = new EntityTmBacklog();
                entBL.setId(backlogIdIn);
                EntityManager.select(entBL);
                c.setValue(tn, i, "backlogOrderNo", entBL.getOrderNo());
            } else {
                c.setValue(tn, i, "backlogOrderNo", "");
            }
        }
        
        c.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    private static Carrier getLabelListByBacklogId(String backlogId) throws QException {
        EntityTmRelBacklogAndLabelList ent = new EntityTmRelBacklogAndLabelList();
        ent.setFkBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    private static Carrier getSprintListByBacklogId(String backlogId) throws QException {
        EntityTmRelBacklogAndSprintList ent = new EntityTmRelBacklogAndSprintList();
        ent.setFkBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    public static Carrier insertNewLabel(Carrier carrier) throws QException {
        
        EntityTmTaskLabel ent = new EntityTmTaskLabel();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier insertNewLabel4Task(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskLabel.NAME, cp.hasValue(carrier, EntityTmTaskLabel.NAME));
        carrier.addController(EntityTmTaskLabel.COLOR, cp.hasValue(carrier, EntityTmTaskLabel.COLOR));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmTaskLabel ent = new EntityTmTaskLabel();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier updateLabel(Carrier carrier) throws QException {
        EntityTmTaskLabel entity = new EntityTmTaskLabel();
        entity.setId(carrier.getValue(EntityTmTaskLabel.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteLabel(Carrier carrier) throws QException {
        EntityTmTaskLabel entity = new EntityTmTaskLabel();
        entity.setId(carrier.getValue(EntityTmTaskLabel.ID).toString());
        EntityManager.delete(entity);
        
        deleteRelatedLabel(entity.getId());
        return carrier;
    }
    
    private static void deleteRelatedLabel(String id) throws QException {
        EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
        ent.setFkTaskLabelId(id);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            ent.setId(c.getValue(tn, i, "id").toString());
            EntityManager.delete(ent);
        }
        
    }
    
    public static Carrier getLabelList(Carrier carrier) throws QException {
        
        EntityTmTaskLabelList ent = new EntityTmTaskLabelList();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier insertNewSprint(Carrier carrier) throws QException {
        
        EntityTmTaskSprint ent = new EntityTmTaskSprint();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier updateSprint(Carrier carrier) throws QException {
        EntityTmTaskSprint entity = new EntityTmTaskSprint();
        entity.setId(carrier.getValue(EntityTmTaskSprint.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteSprint(Carrier carrier) throws QException {
        EntityTmTaskSprint entity = new EntityTmTaskSprint();
        entity.setId(carrier.getValue(EntityTmTaskSprint.ID).toString());
        EntityManager.delete(entity);
        
        deleteRelatedSprint(entity.getId());
        return carrier;
    }
    
    private static void deleteRelatedSprint(String sprintId) throws QException {
        EntityTmRelBacklogAndSprint ent = new EntityTmRelBacklogAndSprint();
        ent.setFkTaskSprintId(sprintId);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            ent.setId(c.getValue(tn, i, "id").toString());
            EntityManager.delete(ent);
        }
        
    }
    
    public static Carrier getSprintList(Carrier carrier) throws QException {
        
        EntityTmTaskSprintList ent = new EntityTmTaskSprintList();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.addSortBy(EntityTmTaskSprintList.FK_PROJECT_ID);
        ent.addSortBy(EntityTmTaskSprintList.SPRINT_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier getStoryInfoById(Carrier carrier) throws QException {
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(carrier.getValue("id").toString());
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier addInputDescToTask(Carrier carrier) throws QException {
        String descId = carrier.get("fkInputDescriptionId");
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setId(descId);
        EntityManager.select(ent);
        
        if (ent.getFkInputId().length() > 0) {
            EntityTmInput entInput = new EntityTmInput();
            entInput.setId(ent.getFkInputId());
            EntityManager.select(entInput);
            
            if (entInput.getFkProjectId().length() > 1) {
                Carrier cr = new Carrier();
                cr.set("fkProjectId", entInput.getFkProjectId());
                cr.set("fkBacklogId", entInput.getFkBacklogId());
                cr.set("taskName", ent.getDescription());
                insertNewBacklogTask4Short(cr).copyTo(carrier);
            }
            
        }
        
        return carrier;
    }
    
    public static Carrier insertNewComment(Carrier carrier) throws QException, Exception {
        String commentType = carrier.getValueAsString("commentType");
        
        String fkbacklogId = carrier.get("fkBacklogId").length() > 0
                ? carrier.get("fkBacklogId")
                : "-1";
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkBacklogId(fkbacklogId);
        ent.setCommentDate(QDate.getCurrentDate());
        ent.setCommentTime(QDate.getCurrentTime());
        ent.setFkUserId(SessionManager.getCurrentUserId());
        ent.setCommentStatus("new");
        
        if (commentType.equals("bug")) {
            ent.setIsBug("1");
        } else if (commentType.equals("stask")) {
            ent.setIsSubtask("1");
        } else if (commentType.equals("creq")) {
            ent.setIsRequest("1");
        }
        ent.setEstimatedHours(ent.getEstimatedHours().length() > 0 ? ent.getEstimatedHours() : "0");
        ent.setSpentHours("0");
        
        EntityManager.insert(ent);
        
        insertNewCommentFile(carrier.getValue("fileName").toString(), ent.getId());
        
        String image = getLastImageFromCommentFile(carrier.get("fileName"));
        if (ent.getFkTaskId().length() > 2 && image.length() > 3) {
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setId(ent.getFkTaskId());
            EntityManager.select(entTask);
            entTask.setLastImage(image);
            EntityManager.update(entTask);
            
        }
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        getTaskList4Short(ent.getFkTaskId()).copyTo(carrier);
        
        sendMailNotificationOnNewcomment(ent.getFkTaskId(), ent.getFkBacklogId(), ent.getComment());
        
        return carrier;
    }
    
    private static void addChangeRequestToJira(String commentId, String backlogId) throws QException {
        if (commentId.length() == 0 || commentId.length() == 0) {
            return;
        }
        
        Carrier carrier = new Carrier();
        
        EntityTmBacklog entBL = new EntityTmBacklog();
        entBL.setId(backlogId);
        EntityManager.select(entBL);
        
        carrier.setValue("backlogName", entBL.getBacklogName());
        carrier.setValue("commentId", commentId);
        carrier.setValue("fkBacklogId", backlogId);
        carrier.setValue("fkProjectId", entBL.getFkProjectId());
        createOrReadEpic4AddingBugInJira(carrier, entBL.getJiraId(), entBL.getJiraKey());
        createChangeRequestInJira(carrier);
        
    }
    
    private static void addBugToJira(String commentId, String backlogId) throws QException {
        if (commentId.length() == 0 || commentId.length() == 0) {
            return;
        }
        
        Carrier carrier = new Carrier();
        
        EntityTmBacklog entBL = new EntityTmBacklog();
        entBL.setId(backlogId);
        EntityManager.select(entBL);
        
        carrier.setValue("backlogName", entBL.getBacklogName());
        carrier.setValue("commentId", commentId);
        carrier.setValue("fkBacklogId", backlogId);
        carrier.setValue("fkProjectId", entBL.getFkProjectId());
        createOrReadEpic4AddingBugInJira(carrier, entBL.getJiraId(), entBL.getJiraKey());
        createBugInJira(carrier);
        
    }
    
    private static void createOrReadEpic4AddingBugInJira(Carrier carrier, String backlogJiraId, String backlogJiraKey) throws QException {
        if (backlogJiraKey.trim().length() == 0) {
            Carrier c = createEpic(carrier);
            carrier.setValue("epicJiraId", c.getValue("jiraId"));
            carrier.setValue("epicJiraKey", c.getValue("jiraKey"));
        } else {
            carrier.setValue("epicJiraId", backlogJiraId);
            carrier.setValue("epicJiraKey", backlogJiraKey);
        }
    }
    
    private static void sendCommentToJira(String commentId) throws Exception {
        if (commentId.length() == 0) {
            return;
        }
        
        EntityTmTaskCommentList ent = new EntityTmTaskCommentList();
        ent.setId(commentId);
        EntityManager.select(ent);
        
        if (ent.getFkTaskId().length() > 0) {
            EntityTmBacklogTask entT = new EntityTmBacklogTask();
            entT.setId(ent.getFkTaskId());
            EntityManager.select(entT);
            if (entT.getJiraIssueId().length() > 1) {
                addJiraCommentIntegration(commentId, entT.getJiraIssueId(), ent.getComment());
            }
        }
        
    }
    
    private static void setNewBacklogHistory4Comment(EntityTmTaskComment ent) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Comment</b>: " + ent.getComment();
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_COMMENT_NEW, ent.getId());
    }
    
    private static String getLastImageFromCommentFile(String filename) throws QException {
        
        if (filename.trim().length() == 0) {
            return "";
        }
        
        String res = "";
        String[] fn = filename.split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String f : fn) {
            if (f.length() == 0) {
                continue;
            }
            try {
                String image[] = Config.getProperty("upload.type.image").split(",");
                String format[] = f.split("\\.");
                if (Arrays.asList(image).contains(format[1])) {
                    res = f;
                    break;
                }
            } catch (Exception e) {
                
            }
        }
        
        return res;
        
    }
    
    private static void insertNewCommentFile(String filename, String id) throws QException {
        if (filename.trim().length() <= 3 || id.trim().length() == 0) {
            return;
        }
        
        String[] fn = filename.split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String f : fn) {
            if (f.trim().length() == 0) {
                continue;
            }
            EntityTmCommentFile ent = new EntityTmCommentFile();
            ent.setFkCommentId(id);
            ent.setFileName(f);
            ent.setUploadDate(QDate.getCurrentDate());
            ent.setUploadTime(QDate.getCurrentTime());
            EntityManager.insert(ent);
        }
    }
    
    public static Carrier updateComment(Carrier carrier) throws QException {
        EntityTmTaskComment entity = new EntityTmTaskComment();
        entity.setId(carrier.getValue(EntityTmTaskComment.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteComment(Carrier carrier) throws QException {
        EntityTmTaskComment entity = new EntityTmTaskComment();
        entity.setId(carrier.getValue(EntityTmTaskComment.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getCommentList(Carrier carrier) throws QException {
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier getCommentListByBacklog(Carrier carrier) throws QException {
        
        EntityTmTaskCommentList ent = new EntityTmTaskCommentList();
        ent.setFkBacklogId(carrier.getValue("fkBacklogId").toString());
        ent.addSortBy(new String[]{EntityTmTaskComment.COMMENT_DATE, EntityTmTaskComment.COMMENT_TIME});
        ent.setSortByAsc(false);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        carrier = getCommenFileListtByBacklog(carrier);
        
        return carrier;
    }
    
    public static Carrier getCommentListByTask(Carrier carrier) throws QException {
        
        EntityTmTaskCommentList ent = new EntityTmTaskCommentList();
        ent.setFkTaskId(carrier.getValue("fkTaskId").toString());
        ent.addSortBy(new String[]{EntityTmTaskComment.COMMENT_DATE, EntityTmTaskComment.COMMENT_TIME});
        ent.setSortByAsc(false);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        carrier = getCommenFileListByTask(carrier);
        
        return carrier;
    }
    
    private static Carrier getCommenFileListByTask(Carrier c) throws QException {
        int rc = c.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            String id = c.getValue(CoreLabel.RESULT_SET, i, "id").toString();
            if (id.trim().length() == 0) {
                continue;
            }
            EntityTmCommentFile ent = new EntityTmCommentFile();
            ent.setFkCommentId(id);
            String st = EntityManager.select(ent).getValueLine(ent.toTableName(),
                    EntityTmCommentFile.FILE_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE);
            c.setValue(CoreLabel.RESULT_SET, i, "fileName", st);
        }
        return c;
    }
    
    private static Carrier getCommenFileListtByBacklog(Carrier c) throws QException {
        int rc = c.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            String id = c.getValue(CoreLabel.RESULT_SET, i, "id").toString();
            if (id.trim().length() == 0) {
                continue;
            }
            EntityTmCommentFile ent = new EntityTmCommentFile();
            ent.setFkCommentId(id);
            String st = EntityManager.select(ent).getValueLine(ent.toTableName(),
                    EntityTmCommentFile.FILE_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE);
            c.setValue(CoreLabel.RESULT_SET, i, "fileName", st);
        }
        return c;
    }
    
    private static String getInputOrderNo(String fkBacklogId) throws QException {
        String st = "select max(order_no) order_no from " + SessionManager.getCurrentDomain();
        st += ".tm_input where status='A' and fk_backlog_id=" + fkBacklogId;
        Carrier c = EntityManager.selectBySql(st);
        double idx = 1;
        try {
            idx = Double.valueOf(c.getValue(CoreLabel.RESULT_SET, 0, "orderNo").toString()) + 1;
        } catch (Exception e) {
            
        }
        return String.valueOf(idx);
//        EntityTmInput ent = new EntityTmInput();
//        ent.setFkBacklogId(fkBacklogId);
//        return String.valueOf(EntityManager.select(ent).getTableRowCount(ent.toTableName()) + 1);
    }
    
    public Carrier addTableAsInput(Carrier carrier) throws QException {
        EntityTmInputTableComp ent = new EntityTmInputTableComp();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setRowCount(carrier.get("rowCount"));
        EntityManager.insert(ent);
        
        Carrier crIn = new Carrier();
        crIn.set("fkBacklogId", carrier.get("fkBacklogId"));
        crIn.set("fkProjectId", carrier.get("fkProjectId"));
        crIn.set("inputName", "table");
        crIn.set("inputType", "TBL");
        crIn.set("cellNo", "12");
        crIn.set(EntityTmInput.FK_RELATED_COMP_ID, ent.getId());
        insertNewInput4Select(crIn).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier addTabAsInput(Carrier carrier) throws QException {
        EntityTmInputTabComp ent = new EntityTmInputTabComp();
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        EntityManager.insert(ent);
        
        Carrier crIn = new Carrier();
        crIn.set("fkBacklogId", carrier.get("fkBacklogId"));
        crIn.set("fkProjectId", carrier.get("fkProjectId"));
        crIn.set("inputName", "tab");
        crIn.set("inputType", "TAB");
        crIn.set("cellNo", "12");
        crIn.set(EntityTmInput.FK_RELATED_COMP_ID, ent.getId());
        insertNewInput4Select(crIn).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier updateRowCountInputTable(Carrier carrier) throws QException {
        EntityTmInputTableComp ent = new EntityTmInputTableComp();
        ent.setId(carrier.get("fkInputTableId"));
        EntityManager.select(ent);
        ent.setRowCount(carrier.get("rowCount"));
        EntityManager.update(ent);
        
        getTableListOfInput(ent.getId(), ent.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier removeBacklogFromTab(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmRelTabBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_PROJECT_ID));
        carrier.addController(EntityTmRelTabBacklog.FK_TAB_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_TAB_ID));
        carrier.addController(EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        EntityTmRelTabBacklog ent = new EntityTmRelTabBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkTabId(carrier.get("fkTabId"));
        ent.setFkRelatedBacklogId((carrier.get("fkRelatedBacklogId")));
        String ids = EntityManager.select(ent).getValueLine(ent.toTableName());
        if (ids.trim().length() > 6) {
            ent.setId(ids);
            EntityManager.delete(ent);
        }
        
        getTabListOfInput(carrier.get("fkTabId"), carrier.get("fkProjectId")).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier addUserStoryToTabList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmRelTabBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_PROJECT_ID));
        carrier.addController(EntityTmRelTabBacklog.FK_TAB_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_TAB_ID));
        carrier.addController(EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID, cp.hasValue(carrier, EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID));
        if (carrier.hasError()) {
            return carrier;
        }
        
        String fkBacklogId = carrier.get("fkBacklogId");
        
        EntityTmRelTabBacklog ent = new EntityTmRelTabBacklog();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setFkTabId(carrier.get("fkTabId"));
        ent.setFkRelatedBacklogId((carrier.get("fkRelatedBacklogId")));
        ent.setEndLimit(0);
        Carrier cr = EntityManager.select(ent);
        if (cr.getTableRowCount(ent.toTableName()) == 0) {
            ent.setOrderNo(getNextTabBacklogOrderNo(ent.getFkTabId()));
            EntityManager.insert(ent);
        }
        
        getTabListOfInputByBacklog(fkBacklogId, carrier.get("fkProjectId")).copyTo(carrier);
        
        return carrier;
    }
    
    private static String getNextTabBacklogOrderNo(String fkTabId) throws QException {
        String res = "1";
        
        if (fkTabId.trim().length() == 0) {
            return res;
        }
        EntityTmRelTabBacklog ent = new EntityTmRelTabBacklog();
        ent.setFkTabId(fkTabId);
        ent.addSortBy(EntityTmProjectCanvasZone.ORDER_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            res = String.valueOf(Integer.valueOf(ent.getOrderNo()) + 1);
        } catch (Exception e) {
        }
        
        return res;
    }
    
    public Carrier setInputTableReadFromContent(Carrier carrier) throws QException {
        EntityTmInputTableComp ent = new EntityTmInputTableComp();
        ent.setId(carrier.get("fkInputTableId"));
        EntityManager.select(ent);
        
        String readContent = "0";
        if (ent.getReadContent().equals("1")) {
            readContent = "0";
        } else {
            readContent = "1";
        }
        
        ent.setReadContent(readContent);
        EntityManager.update(ent);
        carrier.set("readContent", readContent);
        
        getTableListOfInput(ent.getId(), ent.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier showInputTableColumnComponent(Carrier carrier) throws QException {
        EntityTmRelTableInput ent = new EntityTmRelTableInput();
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setFkTableId(carrier.get("fkInputTableId"));
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        String showComponent = "0";
        if (ent.getShowComponent().equals("1")) {
            showComponent = "0";
        } else {
            showComponent = "1";
        }
        
        ent.setShowComponent(showComponent);
        EntityManager.update(ent);
        carrier.set("showComponent", showComponent);
        
        getTableListOfInput(ent.getFkTableId(), ent.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier addColumnsAsInputToTable(Carrier carrier) throws QException {
        String fkBacklogId = carrier.get("fkBacklogId");
        String inputIds = "";
        
        if (carrier.get("newInputName").trim().length() > 0) {
            Carrier crIn = new Carrier();
            crIn.set("fkBacklogId", carrier.get("fkBacklogId"));
            crIn.set("fkProjectId", carrier.get("fkProjectId"));
            crIn.set("inputName", carrier.get("newInputName"));
            crIn.set("inputType", "IN");
            crIn.set("componentType", "txt");
            crIn.set(EntityTmInput.FK_RELATED_COMP_ID, carrier.get("fkInputTableId"));
            Carrier crInput = insertNewInput4Fast(crIn);
            
            inputIds = crInput.get("id");
            
        }
        //do passive all input from input-table
        EntityTmRelTableInput entRel = new EntityTmRelTableInput();
        entRel.setFkTableId(carrier.get("fkInputTableId"));
        Carrier crTemp = EntityManager.select(entRel);
        String tn = entRel.toTableName();
        int rc = crTemp.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crTemp, tn, i, entRel);
            entRel.setInputStatus("P");
            EntityManager.update(entRel);
        }
        
        carrier.set("fkInputId", carrier.get("fkInputId") + "," + inputIds);
        String[] toBeAddedInputIds = carrier.get("fkInputId").split(",");
        for (String id : toBeAddedInputIds) {
            if (id.trim().length() == 0) {
                continue;
            }
            EntityTmRelTableInput ent = new EntityTmRelTableInput();
            ent.setFkProjectId(carrier.get("fkProjectId"));
            ent.setFkInputId(id);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            
            if (ent.getFkTableId().trim().length() > 0) {
                ent.setInputStatus("A");
                ent.setFkTableId(carrier.get("fkInputTableId"));
                EntityManager.update(ent);
            } else {
                ent.setInputStatus("A");
                ent.setFkTableId(carrier.get("fkInputTableId"));
                ent.setOrderNo(nextInputTableOrderNoNew(carrier.get("fkProjectId"),
                        carrier.get("fkInputTableId")));
                EntityManager.insert(ent);
            }
            
        }
        
        if (inputIds.trim().length() > 1) {
            getInputList4Select(inputIds).copyTo(carrier);
            getBacklogList4Select(fkBacklogId).copyTo(carrier);
        }
        getTableListOfInputByBacklog(fkBacklogId, carrier.get("fkProjectId")).copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier removeInputTable(Carrier carrier) throws QException {
        String inputTableId = carrier.get("fkInputTableId");
        String inputId = carrier.get("fkInputId");;
        
        EntityTmInputTableComp entTable = new EntityTmInputTableComp();
        entTable.setId(inputTableId);
        EntityManager.delete(entTable);
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setId(inputId);
        EntityManager.delete(entIn);
        
        EntityTmRelTableInput entRel = new EntityTmRelTableInput();
        entRel.setFkTableId(inputTableId);
        String ids = EntityManager.select(entRel).getValueLine(entRel.toTableName());
        if (ids.length() > 5) {
            entRel.setId(ids);
            EntityManager.delete(entRel);
        }
        
        getInputList4Select(inputId).copyTo(carrier);
        getBacklogList4Select(entIn.getFkBacklogId()).copyTo(carrier);
        return carrier;
    }
    
    public static String nextInputTableOrderNoNew(String fkProjectId, String fkTableId) throws QException {
        String st = "1";
        
        if (fkProjectId.trim().length() == 0 || fkTableId.trim().length() == 0) {
            return st;
        }
        
        EntityTmRelTableInput ent1 = new EntityTmRelTableInput();
        ent1.setFkProjectId(fkProjectId);
        ent1.setFkTableId(fkTableId);
        ent1.addSortBy("orderNo");
        ent1.setSortByAsc(true);
//        ent1.setStartLimit(0);
//        ent1.setEndLimit(0);
        Carrier c = EntityManager.select(ent1);
        
        try {
            st = String.valueOf(Integer.valueOf(c.getValue(ent1.toTableName(), 0, "orderNo").toString()) + 1);
        } catch (Exception e) {
        }
        return st;
    }
    
    public static Carrier insertNewInput(Carrier carrier) throws QException {
        
        EntityTmInput ent = new EntityTmInput();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setOrderNo(getInputOrderNo(carrier.get("fkBacklogId")));
        ent.setCellNo(ent.getTableName().length() > 0 ? "12" : "6");
        ent.setParam3(Config.getProperty("component.design"));
        EntityManager.insert(ent);
        
        increaseBacklogInputCount(ent.getFkBacklogId(), 1);
        
        Carrier cout = new Carrier();
        cout.set("fkBacklogId", carrier.get("fkBacklogId"));
        cout.set("asc", "orderNo");
        getInputList(cout).copyTo(cout);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputListTable");
        Carrier coutT = new Carrier();
        cout.copyTo(coutT);
        cout.renameTableName("inputListTable", "inputOutputList");
        coutT.copyTo(cout);
        
        cout.setValue("isSourced", isBacklogSourced(ent.getFkBacklogId()));
        cout.setValue("id", ent.getId());
        
        setNewBacklogHistory4InputNew(ent);
        
        getInputList4Select(ent.getId()).copyTo(cout);
        getBacklogList4Select(ent.getFkBacklogId()).copyTo(cout);
        return cout;
    }
    
    public static Carrier insertNewInput4Fast(Carrier carrier) throws QException {
        String cellNo = carrier.get("cellNo").length() > 0 ? carrier.get("cellNo") : "6";
        String orderNo = carrier.get("orderNo").length() > 0
                ? carrier.get("orderNo")
                : getInputOrderNo(carrier.get("fkBacklogId"));
        
        EntityTmInput ent = new EntityTmInput();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setOrderNo(orderNo);
        ent.setCellNo(cellNo);
        ent.setParam3(Config.getProperty("component.design"));
        EntityManager.insert(ent);
        
        Carrier cout = new Carrier();
        EntityManager.mapEntityToCarrier(ent, cout, true);
        return cout;
    }
    
    public static Carrier insertNewInput4Select(Carrier carrier) throws QException {
        String cellNo = carrier.get("cellNo").length() > 0 ? carrier.get("cellNo") : "6";
        String orderNo = carrier.get("orderNo").length() > 0
                ? carrier.get("orderNo")
                : getInputOrderNo(carrier.get("fkBacklogId"));
        
        EntityTmInput ent = new EntityTmInput();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setOrderNo(orderNo);
        ent.setCellNo(cellNo);
        ent.setParam3(Config.getProperty("component.design"));
        EntityManager.insert(ent);
        
        Carrier cout = new Carrier();
        EntityManager.mapEntityToCarrier(ent, cout, true);
        EntityManager.mapEntityToCarrier(ent, cout, "inputTable", true);
        
        getInputList4Select(ent.getId()).copyTo(cout);
        getBacklogList4Select(ent.getFkBacklogId()).copyTo(cout);
        return cout;
    }
    
    public static Carrier supplementOfInsertNewInput4Select(Carrier carrier) throws QException {
        Carrier cout = new Carrier();
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("fkInputId"));
        EntityManager.select(ent);
        try {
            increaseBacklogInputCount(ent.getFkBacklogId(), 1);
            
            cout.setValue("isSourced", isBacklogSourced(ent.getFkBacklogId()));
            cout.setValue("id", ent.getId());
            setNewBacklogHistory4InputNew(ent);
            
        } catch (Exception ex) {
            EntityManager.delete(ent);
            cout.set("hasError", "1");
            return cout;
        }
        
        cout.set("hasError", "0");
        return cout;
    }
    
    private static void setNewBacklogHistory4InputNew(EntityTmInput ent) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>:  " + ent.getInputName() + "; and  <b>Table Name</b>: " + ent.getTableName() + ";";
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_NEW, ent.getId(),
                ent.getInputName(), ent.getInputType(), ent.getTableName());
    }
    
    private static void setNewBacklogHistory4InputDescriptionNew(EntityTmInputDescription ent,
            EntityTmInput entInput) throws QException {
        
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(entInput.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Description</b>:  \"" + ent.getDescription() + "\""
                + " for <b>Input Name</b>:  " + entInput.getInputName() + ";";
        setNewBacklogHistory(entNew.getFkProjectId(), entNew.getId(),
                body, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_NEW, ent.getId(),
                entInput.getId(), ent.getDescription(), "");
    }
    
    private static void setNewBacklogHistory4InputDescriptionUpdate(EntityTmInputDescription ent,
            EntityTmInput entInput, String oldDescription) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(entInput.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Description</b>:   \"" + ent.getDescription() + "\""
                + " from <i>'" + oldDescription + "<i>' "
                + " for <b>Input Name</b>:  " + entInput.getInputName() + ";";
        setNewBacklogHistory(entNew.getFkProjectId(), entNew.getId(),
                body, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_UPDATE, ent.getId(),
                entInput.getId(), ent.getDescription(), "");
    }
    
    private static void setNewBacklogHistory4InputDescriptionDelete(EntityTmInputDescription ent,
            EntityTmInput entInput) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(entInput.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Description</b>:   \"" + ent.getDescription() + "\""
                + " for <b>Input Name</b>:  " + entInput.getInputName()
                + " <i><b style=\"color:red\"> is Deleted</b></i>;";
        setNewBacklogHistory(entNew.getFkProjectId(), entNew.getId(),
                body, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE, ent.getId(),
                entInput.getId(), ent.getDescription(), "");
    }
    
    private static void setNewBacklogHistory4StatusChange(String bid, String newStatus, String relatedId) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(bid);
        EntityManager.select(entNew);
        
        String body = "<b> Status changed to</b>:<div class=\"us-list-item   us-item-status-" + newStatus + "\">" + newStatus + "</div>";
        setNewBacklogHistory(entNew.getFkProjectId(), bid, body, BACKLOG_HISTORY_TYPE_STATUS_CHANGE, relatedId);
    }
    
    private static void setNewBacklogHistory(String projectId, String backlogId,
            String body, String htype, String relationId,
            String param1, String param2, String param3) throws QException {
        if (projectId.length() == 0 || backlogId.length() == 0 || body.length() == 0 || htype.length() == 0) {
            return;
        }
        
        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
        ent.setHistoryDate(QDate.getCurrentDate());
        ent.setHistoryTime(QDate.getCurrentTime());
        ent.setFkBacklogId(backlogId);
        ent.setFkProjectId(projectId);
        ent.setRelationId(relationId);
        ent.setHistoryType(htype);
        ent.setHistoryBody(body);
        ent.setParam1(param1);
        ent.setParam2(param2);
        ent.setParam3(param3);
        ent.setHistoryTellerId(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        
        setNotification(ent.getFkBacklogId(), ent.getId(), "");
    }
    
    private static void setNewBacklogHistory(String projectId, String backlogId, String body, String htype, String relationId) throws QException {
        setNewBacklogHistory(projectId, backlogId, body, htype, relationId, "", "", "");
    }
    
    private static void setNotification(String backlogId, String historyId, String newAssignee) throws QException {
        if (backlogId.length() == 0 || historyId.length() == 0) {
            return;
        }
        
        EntityTmBacklog entBl = new EntityTmBacklog();
        entBl.setId(backlogId);
        EntityManager.select(entBl);
        
        if (entBl.getFkProjectId().length() == 0) {
            return;
        }

//        String assigneeIds[] = getAssigneeOfBacklogById(backlogId, newAssignee, entBl.getCreatedBy());
        String assigneeIds[] = getParticipantsOfProjectById(entBl.getFkProjectId());
        for (String id : assigneeIds) {
            EntityTmNotification entNot = new EntityTmNotification();
            entNot.setFkBacklogId(backlogId);
            entNot.setFkUserId(id);
            entNot.setFkProjectId(entBl.getFkProjectId());
            entNot.setFkBacklogHistoryId(historyId);
            entNot.setNotificationDate(QDate.getCurrentDate());
            entNot.setNotificationTime(QDate.getCurrentTime());
            entNot.setIsReviewed("0");
            EntityManager.insert(entNot);
        }
        
        sentSendNotificationEmail(backlogId, historyId, assigneeIds);
    }
    
    private static void sentSendNotificationEmail(String backlogId, String historyId, String assignee[]) throws QException {
        if (backlogId.length() == 0 || historyId.length() == 0) {
            return;
        }
        
        EntityTmBacklogHistoryList entHistory = new EntityTmBacklogHistoryList();
        entHistory.setId(historyId);
        EntityManager.select(entHistory);
        
        EntityTmBacklog entB = new EntityTmBacklog();
        entB.setId(backlogId);
        EntityManager.select(entB);
        
        for (String asg : assignee) {
            if (asg.trim().length() == 0) {
                continue;
            }
            EntityCrUser entUser = new EntityCrUser();
            entUser.setId(asg);
            EntityManager.select(entUser);
            
            String msg = "Dear " + entUser.getUserPersonName() + "<br><br>";
            msg += "Find new notification below:";
            msg += "<br><br>";
            msg += "User Story: " + entB.getBacklogName() + "<br>";
            msg += "<b style=\"color:red!important;font-size:14px;\">" + getTaskTypeName(entHistory.getHistoryType()) + "</b> ";
            msg += " by <b>" + entHistory.getHistoryTellerName() + "</b>";
            msg += " <i>" + QDate.convertToDateString(entHistory.getHistoryDate()) + "</i>, ";
            msg += " <i>" + QDate.convertToTimeString(entHistory.getHistoryTime()) + "</i>, ";
            msg += "<br>";
            msg += entHistory.getHistoryBody();
            
            String subject = "New Notification on '" + entB.getBacklogName() + "'";
            
            try {
                if (Config.getProperty("mail.sendmail").equals("1")) {
                    MailSender.sendMail(entUser.getEmail1(), subject, msg, "");
                }
            } catch (Exception e) {
                System.out.println("Exception bas verdi---------------------------");
                e.printStackTrace();
            }
        }
        
    }
    
    private static String getTaskTypeName(String code) {
        Map<String, String> type = new HashMap<>();
        type.put("input_new", "New Input Inserted");
        type.put("input_update", "Input Updated");
        type.put("input_delete", "Input Deleted");
        type.put("input_desc_new", "New Input Description Inserted");
        type.put("input_desc_delete", "Input Description Deleted");
        type.put("input_desc_update", "Input Description Updated");
        type.put("task_type_new", "New Task Inserted");
        type.put("task_type_delete", "Task Deleted");
        type.put("task_type_update", "Task Updated");
        type.put("task_type_set_as_ongoing", "Task Set as Ongoing");
        type.put("task_type_close_task", "Task Closed");
        type.put("task_type_notify_bug", "Notified Bug");
        type.put("task_type_notify_update", "Notified Update");
        type.put("task_type_comment_new", "New Comment Added");
        type.put("task_type_status_change", "Status Changed");
        
        return type.get(code);
    }
    
    private static String[] getAssigneeOfBacklogById(String backlogId, String newAssignee, String createdBy) throws QException {
        if (backlogId.length() == 0) {
            return new String[]{};
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkBacklogId(backlogId);
        String st = EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmBacklogTask.FK_ASSIGNEE_ID, ",") + "," + newAssignee + "," + createdBy;
        return st.split(",");
    }
    
    private static String[] getParticipantsOfProjectById(String fkProjectId) throws QException {
        if (fkProjectId.length() == 0) {
            return new String[]{};
        }
        
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkProjectId(fkProjectId);
        String st = EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmProjectPermission.FK_USER_ID, ",");
        return st.split(",");
    }
    
    public static Carrier updateInput(Carrier carrier) throws QException {
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        setNewBacklogHistory4InputUpdate(entity);
        return carrier;
    }
    
    private static void setNewBacklogHistory4InputUpdate(EntityTmInput ent) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "New Values => <b>Input Name </b>:  " + ent.getInputName() + " and <b> Table Name </b>:  " + ent.getTableName() + ";";
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(),
                body, BACKLOG_HISTORY_TYPE_INPUT_UPDATE, ent.getId(),
                ent.getInputName(), ent.getInputType(), ent.getTableName());
    }
    
    public static Carrier updateInputByInputName(Carrier carrier) throws QException {
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        String oldName = entity.getInputName();
        entity.setInputName(carrier.getValue(EntityTmInput.INPUT_NAME).toString());
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        setNewBacklogHistory4InputUpdateName(entity, oldName);
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        return carrier;
    }
    
    private static void setNewBacklogHistory4InputUpdateName(EntityTmInput ent, String oldName) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Old Input Name</b>:  " + oldName + "and  <b>New Input Name </b>:  " + ent.getInputName();
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_UPDATE, ent.getId(),
                ent.getInputName(), ent.getInputType(), ent.getTableName());
    }
    
    public static Carrier updateInputByTableName(Carrier carrier) throws QException {
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        
        String oldTableName = entity.getTableName();
        entity.setTableName(carrier.getValue(EntityTmInput.TABLE_NAME).toString());
        entity.setCellNo("12");
        EntityManager.update(entity);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        setNewBacklogHistory4InputUpdateTableName(entity, oldTableName);
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        return carrier;
    }
    
    private static void setNewBacklogHistory4InputUpdateTableName(EntityTmInput ent, String oldTableName) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = " <b>Old Table Name</b>:  " + oldTableName + " and <b> New Table Name</b>: " + ent.getTableName();
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_UPDATE, ent.getId(), ent.getInputName(),
                ent.getInputType(), ent.getTableName());
    }
    
    public static Carrier updateInputByComponentType(Carrier carrier) throws QException {
        String currentInputId = carrier.get("id");
        String checkedInputIds[] = (currentInputId + "," + carrier.get("checkedInputIds")).split(",");
        String inputIds = "";
        for (String id : checkedInputIds) {
            if (id.trim().length() == 0) {
                continue;
            }
            inputIds += id + CoreLabel.IN;
            
            String cType = carrier.getValue(EntityTmInput.COMPONENT_TYPE).toString();
            String componentTypeName = carrier.getValue("componentTypeName").toString();
            String types = Config.getProperty("component.type.with.design");
            String inType = types.contains(cType)
                    ? "GUI" : "IN";
            
            EntityTmInput entity = new EntityTmInput();
            entity.setId(id);
            EntityManager.select(entity);
            entity.setComponentType(cType);
            entity.setInputType(inType);
            if (entity.getOrderNo().trim().length() == 0) {
                entity.setOrderNo("1");
            }
            EntityManager.update(entity);
//        carrier = EntityManager.select(entity);
//        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
            setNewBacklogHistory4InputUpdateComponentType(entity, componentTypeName);
        }
        getInputList4Select(inputIds).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputByEvent(Carrier carrier) throws QException {
        String event = carrier.getValue("event").toString();
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setInputEvent(event);
        if (entity.getOrderNo().trim().length() == 0) {
            entity.setOrderNo("1");
        }
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
//        setNewBacklogHistory4InputUpdateComponentType(entity, componentTypeName);
        return carrier;
    }
    
    public static Carrier updateInputByAction(Carrier carrier) throws QException {
        String action = carrier.getValue("action").toString();
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setAction(action);
        if (entity.getOrderNo().trim().length() == 0) {
            entity.setOrderNo("1");
        }
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
//        setNewBacklogHistory4InputUpdateComponentType(entity, componentTypeName);

        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputBySection(Carrier carrier) throws QException {
        String section = carrier.getValue("section").toString();
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setSection(section);
        if (entity.getOrderNo().trim().length() == 0) {
            entity.setOrderNo("1");
        }
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
//        setNewBacklogHistory4InputUpdateComponentType(entity, componentTypeName);

        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputByComponentOrderNo(Carrier carrier) throws QException {
        String orderNo = carrier.getValue("orderNo").toString();
        
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setOrderNo(orderNo);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        setNewBacklogHistory4InputUpdateOrderNo(entity, orderNo);
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateInputByComponentCellNo(Carrier carrier) throws QException {
        String cellNo = carrier.getValue("cellNo").toString();
        String currentInputId = carrier.get("id");
        String checkedInputIds[] = (currentInputId + "," + carrier.get("checkedInputIds")).split(",");
        String inputIds = "";
        for (String id : checkedInputIds) {
            if (id.trim().length() == 0) {
                continue;
            }
            inputIds += id + CoreLabel.IN;
            
            EntityTmInput entity = new EntityTmInput();
            entity.setId(id);
            EntityManager.select(entity);
            entity.setCellNo(cellNo);
            EntityManager.update(entity);
            carrier = EntityManager.select(entity);
            carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
//            setNewBacklogHistory4InputUpdateCellNo(entity, cellNo);
        }
        getInputList4Select(inputIds).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputByContent(Carrier carrier) throws QException {
        String content = carrier.getValue(EntityTmInput.INPUT_CONTENT).toString();
        String css = carrier.getValue("css").toString();
        String containerCss = carrier.getValue("containerStyle").toString();
        String manualStyle = carrier.getValue("manualStyle").toString();
        
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        
        String contentOld = entity.getInputContent();
        String manualStyleOld = entity.getParam3();
        String containerCssOld = entity.getParam2();
        
        entity.setInputContent(content);
        entity.setParam2(containerCss);
        entity.setParam3(manualStyle);
        entity.setParam4(css);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        if (!content.equals(contentOld)) {
            setNewBacklogHistory4InputContentUpdate(entity, content);
        }
        if (!manualStyle.equals(manualStyleOld)) {
            setNewBacklogHistory4InputComponentCSSUpdate(entity, manualStyle);
        }
        if (!containerCss.equals(containerCssOld)) {
            setNewBacklogHistory4InputContainerCSSUpdate(entity, containerCss);
        }
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateInputByParam1(Carrier carrier) throws QException {
        
        String projectId = carrier.getValueAsString("fkProjectId");
        String param = carrier.getValueAsString(EntityTmInput.PARAM_1);
        String backlogId = carrier.getValueAsString("fkBacklogId");
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        String oldParam = entity.getParam1();
        
        entity.setParam1(carrier.getValue(EntityTmInput.PARAM_1).toString());
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        if (entity.getComponentType().equals("sctn") || entity.getComponentType().equals("tab")) {
            if (param.trim().length() > 0) {
                addUserStoryDependency(projectId, param, backlogId);
            }
            deleteUserStoryDependency(projectId, oldParam, backlogId);
            
        }
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(backlogId).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier updateInputBySectionBacklog(Carrier carrier) throws QException {
        
        String sectionBacklogId = carrier.get("sectionBacklogId");
        String backlogId = carrier.get("fkBacklogId");
        
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setFkBacklogSectionId(sectionBacklogId);
        EntityManager.update(entity);
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkBacklogId(sectionBacklogId);
        entIn.setComponentType("sctn");
        carrier = EntityManager.select(entIn);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        
        return carrier;
    }
    
    private static void addUserStoryDependency(String projectId, String parentBacklogId, String childBacklogId) throws QException {
        EntityTmBacklogDependency ent = new EntityTmBacklogDependency();
        ent.setFkProjectId(projectId);
        ent.setFkBacklogId(childBacklogId);
        ent.setFkParentBacklogId(parentBacklogId);
        int rc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        if (rc == 0) {
            EntityManager.insert(ent);
        }
    }
    
    private static void deleteUserStoryDependency(String projectId, String parentBacklogId, String childBacklogId) throws QException {
        if (projectId.trim().length() == 0 || parentBacklogId.trim().length() == 0 || childBacklogId.trim().length() == 0) {
            return;
        }
        //if current backlog has other param dependency over other inputs
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(childBacklogId);
        entInput.setParam1(parentBacklogId);
        Carrier c = EntityManager.select(entInput);
        int rc1 = c.getTableRowCount(entInput.toTableName());

        //if current backlog has other i/o dependency over other inputs
        EntityTmInput entInputDesc = new EntityTmInput();
        entInputDesc.setFkBacklogId(childBacklogId);
        entInputDesc.setFkDependentBacklogId(parentBacklogId);
        Carrier c1 = EntityManager.select(entInputDesc);
        int rc2 = c1.getTableRowCount(entInputDesc.toTableName());
        
        if (rc1 == 0 && rc2 == 0) {
            EntityTmBacklogDependency ent = new EntityTmBacklogDependency();
            ent.setFkProjectId(projectId);
            ent.setFkBacklogId(childBacklogId);
            ent.setFkParentBacklogId(parentBacklogId);
            EntityManager.select(ent);
            try {
                EntityManager.delete(ent);
            } catch (Exception e) {
            }
        }
        
    }
    
    private static void setNewBacklogHistory4InputUpdateComponentType(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Component Type</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_TYPE_UPDATE, ent.getId(), ent.getComponentType(), "", "");
    }
    
    private static void setNewBacklogHistory4InputUpdateCellNo(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Cell No</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_CELL_UPDATE, ent.getId(), ent.getCellNo(), "", "");
    }
    
    private static void setNewBacklogHistory4InputContentUpdate(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Content</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_CONTENT_UPDATE, ent.getId());
    }
    
    private static void setNewBacklogHistory4InputComponentCSSUpdate(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Component CSS</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_CSS_UPDATE, ent.getId());
    }
    
    private static void setNewBacklogHistory4InputContainerCSSUpdate(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Container CSS</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_CONTAINER_CSS_UPDATE, ent.getId());
    }
    
    private static void setNewBacklogHistory4InputUpdateOrderNo(EntityTmInput ent, String arg) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Order No</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_ORDER_NO_UPDATE, ent.getId(), ent.getOrderNo(), "", "");
    }
    
    private static void setNewBacklogHistory4InputRelatedSUSUpdate(EntityTmInput ent,
            String arg, String relatedBacklogId, String relatedInputId) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> Old Relation</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_RELATION_ADDED, ent.getId(),
                relatedBacklogId, relatedInputId, "");
    }
    
    private static void setNewBacklogHistory4InputRelatedSUSDelete(EntityTmInput ent,
            String arg, String relatedBacklogId, String relatedInputId) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input Name</b>: " + ent.getInputName() + " and <b> New Relation</b>: " + arg;
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_RELATION_DELETED, ent.getId(),
                relatedBacklogId, relatedInputId, "");
    }
    
    public static Carrier updateInputByDependentBacklog(Carrier carrier) throws QException {
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        entity.setFkDependentBacklogId(carrier.getValue(EntityTmInput.FK_DEPENDENT_BACKLOG_ID).toString());
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier updateInputByDependentBacklogOutput(Carrier carrier) throws QException {
        String dependentOutputId = carrier.getValue(EntityTmInput.FK_DEPENDENT_OUTPUT_ID).toString();
        String projectId = carrier.getValueAsString("fkProjectId");
        String dependentBackklogId = carrier.getValue(EntityTmInput.FK_DEPENDENT_BACKLOG_ID).toString();
        
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        
        String oldDependentBacklogId = entity.getFkDependentBacklogId();
        
        entity.setFkDependentOutputId(carrier.getValue(EntityTmInput.FK_DEPENDENT_OUTPUT_ID).toString());
        entity.setFkDependentBacklogId(carrier.getValue(EntityTmInput.FK_DEPENDENT_BACKLOG_ID).toString());
        EntityManager.update(entity);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        addBacklogDependence(projectId, dependentBackklogId, entity.getFkBacklogId());

        //get message text
        EntityTmInput entity1 = new EntityTmInput();
        entity1.setId(entity.getFkDependentOutputId());
        EntityManager.select(entity1);
        
        EntityTmBacklog entity2 = new EntityTmBacklog();
        entity2.setId(entity.getFkDependentBacklogId());
        EntityManager.select(entity2);
        
        String msg = entity2.getBacklogName() + "->" + entity1.getInputName();
        setNewBacklogHistory4InputRelatedSUSUpdate(entity, msg,
                entity.getFkDependentBacklogId(), entity.getFkDependentOutputId());
        
        getInputList4Select(entity.getId()).copyTo(carrier);
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        
        return carrier;
    }
    
    public static Carrier deleteInputByDependentBacklogOutput(Carrier carrier) throws QException {
        String projectId = carrier.getValueAsString("fkProjectId");
        String inputId4Select = "";
        String backlogId4Select = "";
        
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        inputId4Select += entity.getId() + CoreLabel.IN;
        backlogId4Select += entity.getFkBacklogId() + CoreLabel.IN;
        
        String oldDependentBacklogId = entity.getFkDependentBacklogId();
        String oldDependentOutputId = entity.getFkDependentOutputId();
        
        inputId4Select += oldDependentOutputId + CoreLabel.IN;
        backlogId4Select += oldDependentBacklogId + CoreLabel.IN;
        
        entity.setFkDependentOutputId("");
        entity.setFkDependentBacklogId("");
        EntityManager.update(entity);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmInput entity1 = new EntityTmInput();
        entity1.setFkDependentBacklogId(oldDependentBacklogId);
        entity1.setFkBacklogId(entity.getFkBacklogId());
        int rc = EntityManager.select(entity1).getTableRowCount(entity1.toTableName());
        if (rc == 0) {
            deleteBacklogDependence(
                    projectId,
                    oldDependentBacklogId,
                    entity.getFkBacklogId());
        }

        //get message text
        EntityTmInput entity11 = new EntityTmInput();
        entity11.setId(oldDependentOutputId);
        EntityManager.select(entity11);
        
        EntityTmBacklog entity2 = new EntityTmBacklog();
        entity2.setId(oldDependentBacklogId);
        EntityManager.select(entity2);
        
        String msg = entity2.getBacklogName() + "->" + entity11.getInputName();
        setNewBacklogHistory4InputRelatedSUSDelete(entity, msg,
                entity.getFkDependentBacklogId(), entity.getFkDependentOutputId());
        
        getInputList4Select(inputId4Select).copyTo(carrier);
        getBacklogList4Select(backlogId4Select).copyTo(carrier);
        
        return carrier;
    }
    
    private static void deleteBacklogDependence(String projectId, String parentBacklogId,
            String childBacklogId) throws QException {
        if (childBacklogId.trim().length() == 0) {
            return;
        }
        EntityTmBacklogDependency ent = new EntityTmBacklogDependency();
        ent.setFkProjectId(projectId);
        ent.setFkBacklogId(childBacklogId);
        ent.setFkParentBacklogId(parentBacklogId);
        Carrier c = EntityManager.select(ent);
        int rc = c.getTableRowCount(ent.toTableName());
        if (rc > 0) {
            EntityManager.delete(ent);
        }
    }
    
    private static void addBacklogDependence(String projectId, String parentBacklogId,
            String childBacklogId) throws QException {
        if (parentBacklogId.trim().length() == 0) {
            return;
        }
        EntityTmBacklogDependency ent = new EntityTmBacklogDependency();
        ent.setFkProjectId(projectId);
        ent.setFkBacklogId(childBacklogId);
        ent.setFkParentBacklogId(parentBacklogId);
        Carrier c = EntityManager.select(ent);
        int rc = c.getTableRowCount(ent.toTableName());
        if (rc == 0) {
            EntityManager.insert(ent);
        }
    }
    
    public static Carrier deleteInput(Carrier carrier) throws QException {
        EntityTmInput entity = new EntityTmInput();
        entity.setId(carrier.getValue(EntityTmInput.ID).toString());
        EntityManager.select(entity);
        EntityManager.delete(entity);
        
        EntityTmRelTableInput entTbl = new EntityTmRelTableInput();
        entTbl.setFkInputId(carrier.get("id"));
        String inputIds = EntityManager.select(entTbl).getValueLine(entTbl.toTableName());
        if (inputIds.length() > 1) {
            entTbl.setId(inputIds);
            EntityManager.delete(entTbl);
        }
        
        decreaseBacklogInputCount(entity.getFkBacklogId(), 1);
        
        carrier.setValue("isSourced", isBacklogSourced(entity.getFkBacklogId()));
        setNewBacklogHistory4InputDelete(entity);
        
        if (entity.getFkBacklogId().length() == 0) {
            return carrier;
        }
        
        Carrier cout = new Carrier();
        cout.set("fkBacklogId", entity.getFkBacklogId());
        cout.set("asc", "orderNo");
        cout = getInputList(cout);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputListTable");
        cout.copyTo(carrier);
        
        EntityTmInput ent1 = new EntityTmInput();
        ent1.setFkBacklogId(entity.getFkBacklogId());
        ent1.setInputType("OUT");
        Carrier cout1 = EntityManager.select(ent1);
        cout1.renameTableName(ent1.toTableName(), "inputOutputList");
        cout1.copyTo(carrier);
        
        getBacklogList4Select(entity.getFkBacklogId()).copyTo(carrier);
        getTableListOfInput(entity.getFkBacklogId(), entity.getFkProjectId()).copyTo(carrier);
        getTabListOfInput(entity.getFkBacklogId(), entity.getFkProjectId()).copyTo(carrier);
        return carrier;
    }
    
    private static void setNewBacklogHistory4InputDelete(EntityTmInput ent) throws QException {
        EntityTmBacklog entNew = new EntityTmBacklog();
        entNew.setId(ent.getFkBacklogId());
        EntityManager.select(entNew);
        
        String body = "<b>Input name </b>: " + ent.getInputName();
        setNewBacklogHistory(entNew.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_INPUT_DELETE, ent.getId(),
                ent.getInputName(), ent.getInputType(), ent.getTableName());
    }
    
    public static Carrier getInputList(Carrier carrier) throws QException {
        String backlogId = carrier.get("fkBacklogId");
        
        EntityTmBacklog entBl = new EntityTmBacklog();
        if (backlogId.length() > 0) {
            entBl.setId(backlogId);
            EntityManager.select(entBl);
        }
        
        EntityTmInput ent = new EntityTmInput();
        EntityManager.mapCarrierToEntity(carrier, ent);
        if (!carrier.isKeyExist("asc")) {
            ent.setSortByAsc(true);
        }
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        String inputIds = carrier.getValueLine(CoreLabel.RESULT_SET);
        
        String param1Ids = carrier.getValueLine(CoreLabel.RESULT_SET, EntityTmInput.PARAM_1);
        if (param1Ids.trim().length() > 0) {
            EntityTmBacklog entTm = new EntityTmBacklog();
            entTm.setId(param1Ids);
            Carrier c = EntityManager.select(entTm);
            carrier.mergeCarrier(CoreLabel.RESULT_SET, new String[]{EntityTmInput.PARAM_1},
                    c, entTm.toTableName(), new String[]{"id"}, new String[]{EntityTmBacklog.BACKLOG_NAME}, "", true);
        }
        
        carrier.setValue("dependenceBacklogId", ent.getFkDependentBacklogId());
        carrier.setValue("dependenceBacklogName", getBacklogNameById(ent.getFkDependentBacklogId()));
        carrier.setValue("dependenceInputId", ent.getFkDependentOutputId());
        carrier.setValue("dependenceInputName", getInputNameById(ent.getFkDependentOutputId()));
        carrier.setValue("backlogName", entBl.getBacklogName());
        
        Carrier cDesc = new Carrier();
        addInputListTable(cDesc, backlogId);
        carrier.mergeCarrier(CoreLabel.RESULT_SET, new String[]{"id"},
                cDesc, "inputDescListTable", new String[]{"fkInputId"}, new String[]{"description", "inputTable"}, " ", true);
        
        getInputList4Select(inputIds).copyTo(carrier);
        getTableListOfInputByBacklog(backlogId, entBl.getFkProjectId()).copyTo(carrier);
        getTabListOfInputByBacklog(backlogId, entBl.getFkProjectId()).copyTo(carrier);
        
        return carrier;
    }
    
    private static Carrier getInputList4Select(String inputId) throws QException {
        Carrier cr = new Carrier();
        if (inputId.trim().length() == 0) {
            return cr;
        }
        cr.set("fkInputId", inputId);
        cr = getInputList4Select(cr);
        cr.renameTableName(CoreLabel.RESULT_SET, "inputTable");
        return cr;
    }
    
    public static Carrier getInputList4Select(Carrier carrier) throws QException {
        String fkProjectId = carrier.get("fkProjectId");
        
        EntityTmInputDescription entInDesc = new EntityTmInputDescription();
        entInDesc.setFkProjectId(fkProjectId);
        entInDesc.setFkInputId(carrier.get("fkInputId"));
        entInDesc.addSortBy("id");
        entInDesc.setSortByAsc(true);
        Carrier crInDesc = EntityManager.select(entInDesc);
        crInDesc = crInDesc.getKVPairListFromTable(entInDesc.toTableName(),
                EntityTmInputDescription.FK_INPUT_ID,
                EntityTmInputDescription.ID);
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(carrier.get("fkInputId"));
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy("orderNo");
        ent.setSortByAsc(true);
        Carrier crIn = EntityManager.select(ent);
        Carrier crChildDependence = crIn.getKVPairListFromTable(ent.toTableName(),
                EntityTmInput.FK_DEPENDENT_OUTPUT_ID,
                EntityTmInputDescription.ID);
        crIn.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        String tn = CoreLabel.RESULT_SET;
        int rc = crIn.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crIn, tn, i, ent);
            crIn.setValue(tn, i, "inputDescriptionIds", crInDesc.get(ent.getId()));
            crIn.setValue(tn, i, "childDependenceId", crChildDependence.get(ent.getId()));
        }
        
        fkProjectId = (fkProjectId.length() == 0) ? ent.getFkProjectId() : fkProjectId;
        
        String tableId = carrier.get("fkInputId").trim().length() > 0
                ? ent.getFkRelatedCompId()
                : "";
        
        getTableListOfInput(tableId, fkProjectId).copyTo(crIn);
        getTabListOfInput(tableId, fkProjectId).copyTo(crIn);
        
        return crIn;
    }
    
    private static Carrier getTabListOfInput(String fkInputTabId, String fkProjectId) throws QException {
        if (fkProjectId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmRelTabBacklog entIn = new EntityTmRelTabBacklog();
        entIn.addSortBy("orderNo");
        entIn.setSortByAsc(true);
        entIn.setFkProjectId(fkProjectId);
        entIn.setFkTabId(fkInputTabId);
//        entIn.setBacklogStatus("A");
        Carrier crTemp = EntityManager.select(entIn);
        Carrier crIn = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTabId", EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID);
        Carrier crNo = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTabId", EntityTmRelTabBacklog.ORDER_NO);
        
        EntityTmInputTabComp ent = new EntityTmInputTabComp();
        ent.setId(fkInputTabId);
        ent.setFkProjectId(fkProjectId);
        ent.setSortByAsc(true);
        Carrier crOut = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = crOut.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crOut, tn, i, ent);
            crOut.setValue(tn, i, "fkRelatedBacklogId", crIn.get(ent.getId()));
            crOut.setValue(tn, i, "orderNo", crNo.get(ent.getId()));
        }
        crOut.renameTableName(tn, "inputTabCompList");
        return crOut;
    }
    
    private static Carrier getTableListOfInput(String fkInputTableId, String fkProjectId) throws QException {
        if (fkProjectId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmRelTableInput entIn = new EntityTmRelTableInput();
        entIn.addSortBy("orderNo");
        entIn.setSortByAsc(true);
        entIn.setFkProjectId(fkProjectId);
        entIn.setFkTableId(fkInputTableId);
        entIn.setInputStatus("A");
        Carrier crTemp = EntityManager.select(entIn);
        Carrier crIn = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.FK_INPUT_ID);
        Carrier crNo = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.ORDER_NO);
        Carrier crComp = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COMPONENT);
        Carrier crCol = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COLUMN);
        Carrier crColName = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COLUMN_NAME);
        
        EntityTmInputTableComp ent = new EntityTmInputTableComp();
        ent.setId(fkInputTableId);
        ent.setFkProjectId(fkProjectId);
        ent.setSortByAsc(true);
        Carrier crOut = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = crOut.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crOut, tn, i, ent);
            crOut.setValue(tn, i, "fkInputId", crIn.get(ent.getId()));
            crOut.setValue(tn, i, "orderNo", crNo.get(ent.getId()));
            crOut.setValue(tn, i, "showComponent", crComp.get(ent.getId()));
            crOut.setValue(tn, i, "showColumn", crCol.get(ent.getId()));
            crOut.setValue(tn, i, "showColumnName", crColName.get(ent.getId()));
        }
        crOut.renameTableName(tn, "inputTableCompList");
        return crOut;
    }
    
    private static Carrier getTabListOfInputByBacklog(String fkBacklogId, String fkProjectId) throws QException {
        if (fkProjectId.length() == 0 || fkBacklogId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmInputTabComp ent = new EntityTmInputTabComp();
        ent.setFkBacklogId(fkBacklogId);
        ent.setFkProjectId(fkProjectId);
        Carrier crOut = EntityManager.select(ent);
        String tabIds = crOut.getValueLine(ent.toTableName());
        
        EntityTmRelTabBacklog entIn = new EntityTmRelTabBacklog();
        entIn.setFkProjectId(fkProjectId);
        entIn.setFkTabId(tabIds);
        entIn.addSortBy("orderNo");
        entIn.setSortByAsc(true);
        Carrier crTemp = EntityManager.select(entIn);
        Carrier crIn = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTabId", EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID);
        Carrier crNo = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTabId", EntityTmRelTabBacklog.ORDER_NO);
        
        String tn = ent.toTableName();
        int rc = crOut.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crOut, tn, i, ent);
            crOut.setValue(tn, i, "fkRelatedBacklogId", crIn.get(ent.getId()));
            crOut.setValue(tn, i, "orderNo", crNo.get(ent.getId()));
        }
        crOut.renameTableName(tn, "inputTabCompList");
        return crOut;
    }
    
    private static Carrier getTableListOfInputByBacklog(String fkBacklogId, String fkProjectId) throws QException {
        if (fkProjectId.length() == 0 || fkBacklogId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmInputTableComp ent = new EntityTmInputTableComp();
        ent.setFkBacklogId(fkBacklogId);
        ent.setFkProjectId(fkProjectId);
        Carrier crOut = EntityManager.select(ent);
        String tableIds = crOut.getValueLine(ent.toTableName());
        
        EntityTmRelTableInput entIn = new EntityTmRelTableInput();
        entIn.setFkProjectId(fkProjectId);
        entIn.setFkTableId(tableIds);
        entIn.addSortBy("orderNo");
        entIn.setSortByAsc(true);
        entIn.setInputStatus("A");
        Carrier crTemp = EntityManager.select(entIn);
        Carrier crIn = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.FK_INPUT_ID);
        Carrier crNo = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.ORDER_NO);
        Carrier crComp = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COMPONENT);
        Carrier crCol = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COLUMN);
        Carrier crColName = crTemp.getKVPairListFromTable(entIn.toTableName(), "fkTableId", EntityTmRelTableInput.SHOW_COLUMN_NAME);
        
        String tn = ent.toTableName();
        int rc = crOut.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(crOut, tn, i, ent);
            crOut.setValue(tn, i, "fkInputId", crIn.get(ent.getId()));
            crOut.setValue(tn, i, "orderNo", crNo.get(ent.getId()));
            crOut.setValue(tn, i, "showComponent", crComp.get(ent.getId()));
            crOut.setValue(tn, i, "showColumn", crCol.get(ent.getId()));
            crOut.setValue(tn, i, "showColumnName", crColName.get(ent.getId()));
        }
        crOut.renameTableName(tn, "inputTableCompList");
        return crOut;
    }
    
    private static String getBacklogNameById(String id) throws QException {
        if (id.trim().length() == 0) {
            return "";
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(id);
        EntityManager.select(ent);
        
        return ent.getBacklogName();
    }
    
    private static String getInputNameById(String id) throws QException {
        if (id.trim().length() == 0) {
            return "";
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setId(id);
        EntityManager.select(ent);
        
        return ent.getInputName();
    }
    
    public static Carrier insertNewInputDescription(Carrier carrier) throws QException {
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setId(ent.getFkInputId());
        EntityManager.select(entInput);
        
        setNewBacklogHistory4InputDescriptionNew(ent, entInput);
        
        carrier.setValue("id", ent.getId());
        
        getInputList4Select(ent.getFkInputId()).copyTo(carrier);
        getInputDescriptionList4Select(ent.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier updateInputDescription(Carrier carrier) throws QException {
        
        EntityTmInputDescription entity = new EntityTmInputDescription();
        entity.setId(carrier.getValue(EntityTmInputDescription.ID).toString());
        EntityManager.select(entity);
        
        String oldDesc = entity.getDescription();
        
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setId(entity.getFkInputId());
        EntityManager.select(entInput);
        
        setNewBacklogHistory4InputDescriptionUpdate(entity, entInput, oldDesc);
        
        getInputDescriptionList4Select(entity.getId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier deleteInputDescription(Carrier carrier) throws QException {
        EntityTmInputDescription entity = new EntityTmInputDescription();
        entity.setId(carrier.getValue(EntityTmInputDescription.ID).toString());
        EntityManager.select(entity);
        EntityManager.delete(entity);
        
        if (entity.getFkInputId().length() == 0) {
            return carrier;
        }
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setId(entity.getFkInputId());
        EntityManager.select(entInput);
        
        setNewBacklogHistory4InputDescriptionDelete(entity, entInput);
        
        getInputList4Select(entity.getFkInputId()).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getInputDescriptionList(Carrier carrier) throws QException {
        EntityTmInput entIn = new EntityTmInput();
        entIn.setId(carrier.getValueAsString("fkInputId"));
        EntityManager.select(entIn);
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(carrier.getValueAsString("fkInputId"));
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        getInputChildDependenceDescription(entIn.getId())
                .copyTo(carrier);
        getInputDependenceDescription(entIn.getFkDependentOutputId())
                .copyTo(carrier);
        carrier.setValue("dependenceBacklogId", entIn.getFkDependentBacklogId());
        carrier.setValue("dependenceBacklogName", getBacklogNameById(entIn.getFkDependentBacklogId()));
        carrier.setValue("dependenceInputId", entIn.getFkDependentOutputId());
        carrier.setValue("dependenceInputName", getInputNameById(entIn.getFkDependentOutputId()));
        
        return carrier;
    }
    
    public static Carrier getInputDescriptionList4Select(String id) throws QException {
        Carrier cr = new Carrier();
        cr.set("fkInputDescriptionId", id);
        cr = getInputDescriptionList4Select(cr);
        cr.renameTableName(CoreLabel.RESULT_SET, "inputDescriptionTable");
        return cr;
    }
    
    public static Carrier getInputDescriptionList4Select(Carrier carrier) throws QException {
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setId(carrier.get("fkInputDescriptionId"));
        ent.setFkInputId(carrier.get("fkInputId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.addDistinctField(EntityTmInputDescription.ID);
        ent.addDistinctField(EntityTmInputDescription.DESCRIPTION);
        ent.addDistinctField(EntityTmInputDescription.COLORED);
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public static Carrier getInputChildDependenceDescription(Carrier carrier) throws QException {
        String inputId = carrier.get("fkInputId");
        return getInputChildDependenceDescription(inputId);
    }
    
    private static Carrier getInputChildDependenceDescription(String inputId) throws QException {
        if (inputId.trim().length() == 0) {
            return new Carrier();
        }
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkDependentOutputId(inputId);
        Carrier ct = EntityManager.select(entIn);
        String backlogIds = ct.getValueLine(entIn.toTableName(), EntityTmInput.FK_BACKLOG_ID);
        
        EntityTmBacklog entBl = new EntityTmBacklog();
        entBl.setId(backlogIds);
        Carrier cBl = EntityManager.select(entBl);
        
        ct.mergeCarrier(entIn.toTableName(), EntityTmInput.FK_BACKLOG_ID,
                cBl, entBl.toTableName(), "id", new String[]{"backlogName"});
        ct.renameTableName(entIn.toTableName(), "childDependenceInputListTable");
        
        int rc = ct.getTableRowCount("childDependenceInputListTable");
        for (int i = 0; i < rc; i++) {
            EntityTmInputDescription ent = new EntityTmInputDescription();
            ent.setFkInputId(ct.getValue("childDependenceInputListTable", i, "id").toString());
            ent.setSortByAsc(true);
            String desc = EntityManager.select(ent)
                    .getValueLine(ent.toTableName(), "description", ", \n ");
            ct.setValue("childDependenceInputListTable", i, "description", desc);
        }
        
        return ct;
    }
    
    private static Carrier getInputDependenceDescription(String inputId) throws QException {
        if (inputId.trim().length() == 0) {
            return new Carrier();
        }
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputId);
        ent.setSortByAsc(true);
        Carrier c = EntityManager.select(ent);
        c.renameTableName(ent.toTableName(), "dependenceInputListTable");
        return c;
    }
    
    private static String getInputDependenceOtherDescription(String inputId) throws QException {
        if (inputId.trim().length() == 0) {
            return "";
        }
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputId);
        ent.setSortByAsc(true);
        return EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmInputDescription.DESCRIPTION, ", \n");
        
    }
    
    private static boolean isBacklogFromCustomer(String id) throws QException {
        if (id.trim().length() == 0) {
            return false;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(id);
        ent.setIsFromCustomer("1");
        return EntityManager.select(ent).getTableRowCount(ent.toTableName()) > 0;
    }
    
    public static Carrier insertNewBacklogTask(Carrier carrier) throws QException {
//        if (isBacklogFromCustomer(carrier.getValue("fkBacklogId").toString())) {
//            return carrier;
//        }

        String comment = carrier.getValueAsString("comment");
        String commentType = carrier.getValueAsString("commentType");
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        EntityManager.mapCarrierToEntity(carrier, ent);
        if (carrier.get("taskStatus").length() > 0) {
            ent.setTaskStatus(carrier.get("taskStatus"));
        } else {
            ent.setTaskStatus(BACKLOG_STATUS_NEW);
        }
        ent.setSpentHours(ent.getSpentHours().length() == 0 ? "0" : ent.getSpentHours());
        ent.setCompletedDuration(ent.getCompletedDuration().length() == 0 ? "0" : ent.getCompletedDuration());
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setTaskOrderNo("11");
        ent.setTaskPriority("1");
        ent.setOrderNoSeq(nextTaskOrderNoSeq(carrier.get("fkProjectId")));
        EntityManager.insert(ent);
        carrier.setValue("id", ent.getId());
        
        addEstimatedHours(ent.getFkBacklogId(), ent.getEstimatedHours());
        
        EntityTmBacklogTaskList ent1 = new EntityTmBacklogTaskList();
        ent1.setId(ent.getId());
        EntityManager.select(ent1);
        
        carrier.setValue("isSourced", isBacklogSourced(ent.getFkBacklogId()));
        carrier.setValue("backlogStatus", setBacklogStatus(ent.getFkBacklogId()));
        
        setNewBacklogHistory4TaskTypeNew(ent1);
        addTaskCountBacklog(ent1.getFkBacklogId());
        if (carrier.get("add2jira").equals("1") && ent1.getJiraIssueId().length() == 0) {
            addTaskToJira(carrier);
        }
        
        if (comment.trim().length() > 0
                || carrier.getValueAsString("filename").trim().length() > 6) {
            Carrier cComment = new Carrier();
            cComment.setValue(EntityTmTaskComment.COMMENT, comment);
            cComment.setValue("fileName", carrier.getValueAsString("filename"));
            cComment.setValue("fkBacklogId", carrier.getValue("fkBacklogId"));
            cComment.setValue("commentType", "B");
            cComment.setValue("fkTaskId", ent.getId());
            cComment.setValue("estimatedHours", carrier.getValue("commentEstimationHours"));
            cComment.setValue("commentType", carrier.getValueAsString("commentType"));
            cComment.setValue("skipEHCalculation", "true");
            cComment.setValue("add2jira", carrier.get("add2jira"));
            
            try {
                insertNewComment(cComment);
            } catch (Exception ex) {
                throw new QException(ex);
            }
        }
        
        return carrier;
    }
    
    private static String nextTestCaseNo(String fkProjectId) throws QException {
        if (fkProjectId.length() == 0) {
            return "1";
        }
        
        String st = "1";
        
        EntityTmTestCase ent = new EntityTmTestCase();
        ent.setFkProjectId(fkProjectId);
        ent.addSortBy(EntityTmTestCase.TEST_CASE_NO);
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            st = String.valueOf(Integer.valueOf(ent.getTestCaseNo()) + 1);
        } catch (Exception e) {
        }
        return st;
    }
    
    private static String nextTaskLOrderNo(String fkProjectId, String fkBacklogId) throws QException {
        if (fkBacklogId.length() == 0) {
            return "1";
        }
        
        String st = "1";
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkProjectId(fkProjectId);
        ent.setFkBacklogId(fkBacklogId);
        ent.addSortBy("taskOrderNo");
        ent.setSortByAsc(false);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        try {
            st = String.valueOf(Integer.valueOf(ent.getTaskOrderNo()) + 1);
        } catch (Exception e) {
        }
        return st;
    }
    
    private static String nextTaskOrderNoSeq(String fkProjectId) throws QException {
        if (fkProjectId.length() == 0) {
            return "1";
        }
        
        String ln = "SELECT MAX(cast(ORDER_NO_seq as unsigned)) as order_no_seq FROM " + SessionManager.getCurrentDomain() + ".TM_BACKLOG_TASK WHERE STATUS='A' AND FK_PROJECT_ID=? ";
        Carrier cr = EntityManager.selectBySql(ln, new String[]{fkProjectId});
        
        String st = "1";
        
        try {
            st = cr.getValue(CoreLabel.RESULT_SET, 0, "orderNoSeq").toString();
            st = String.valueOf(Integer.parseInt(st) + 1);
        } catch (Exception e) {
            st = "1";
        }
        return st;
    }
    
    public static Carrier insertNewBacklogTask4Short(Carrier carrier) throws QException {
        String orderNo = carrier.get("orderNo").length() == 0
                ? nextTaskLOrderNo(carrier.get("fkProjectId"), carrier.get("fkBacklogId"))
                : carrier.get("orderNo");
        
        String orderNoSeq = nextTaskOrderNoSeq(carrier.get("fkProjectId"));
        
        String status = carrier.get("taskStatus").length() == 0
                ? "new"
                : carrier.get("taskStatus");
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setOrderNoSeq(orderNoSeq);
        ent.setTaskName(carrier.get("taskName"));
        ent.setTaskStatus(status);
        ent.setTaskPriority("1");
        ent.setFkBacklogId(carrier.get("fkBacklogId"));
        ent.setFkAssigneeId(carrier.get("fkAssigneeId"));
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setTaskOrderNo(orderNo);
        ent.setSpentHours(ent.getSpentHours().length() == 0 ? "0" : ent.getSpentHours());
        ent.setCompletedDuration(ent.getCompletedDuration().length() == 0 ? "0" : ent.getCompletedDuration());
        ent.setCreatedBy(SessionManager.getCurrentUserId());
        ent.setCreatedDate(QDate.getCurrentDate());
        ent.setCreatedTime(QDate.getCurrentTime());
        ent.setTaskNature("new");
        ent.setOrderNoSeq(nextTaskOrderNoSeq(carrier.get("fkProjectId")));
        EntityManager.insert(ent);
        carrier.setValue("id", ent.getId());
        
        setBacklogStatus(ent.getFkBacklogId());
        
        getTaskList4Short(ent.getId()).copyTo(carrier);
        getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        assigneSprintToTask(ent.getId(), carrier.get("sprintList"));
        
        sendMailNotificationOnCreate(ent.getId(), ent.getFkBacklogId());
        
        return carrier;
    }
    
    private static void sendMailNotificationOnCreate(String taskId, String backlogId) throws QException {
        if (backlogId.trim().length() < 4) {
            return;
        }
        
        if (taskId.length() == 0) {
            return;
        }
        
        EntityTmBacklog entBL = new EntityTmBacklog();
        entBL.setId(backlogId);
        EntityManager.select(entBL);
        
        EntityTmBacklogTask entTaskNew = new EntityTmBacklogTask();
        entTaskNew.setId(taskId);
        EntityManager.select(entTaskNew);
        
        EntityTmBacklogTask entTask = new EntityTmBacklogTask();
        entTask.setFkBacklogId(backlogId);
        Carrier cr = EntityManager.select(entTask);
        
        String userIds = entBL.getFkOwnerId() + CoreLabel.IN + entBL.getCreatedBy() + CoreLabel.IN;
        userIds += entTaskNew.getFkAssigneeId() + CoreLabel.IN
                + entTaskNew.getCreatedBy() + CoreLabel.IN
                + entTaskNew.getUpdatedBy() + CoreLabel.IN;
//        userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.FK_ASSIGNEE_ID) + CoreLabel.IN;
//        userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.CREATED_BY);

        if (userIds.length() > 6) {
            EntityCrUserList entUser = new EntityCrUserList();
            entUser.setId(userIds);
            Carrier crUser = EntityManager.select(entUser);
            
            String storyCardName = entBL.getBacklogName();
            String taskName = entTaskNew.getTaskName();
            String assigneeName = " No one";
            if (entTaskNew.getFkAssigneeId().length() > 1) {
                EntityCrUserList entUserAssingee = new EntityCrUserList();
                entUserAssingee.setId(entTaskNew.getFkAssigneeId());
                EntityManager.select(entUserAssingee);
                assigneeName = entUserAssingee.getUserPersonName();
            }
            
            String createdByName = "";
            if (entTaskNew.getCreatedBy().length() > 1) {
                EntityCrUserList entUserAssingee = new EntityCrUserList();
                entUserAssingee.setId(entTaskNew.getCreatedBy());
                EntityManager.select(entUserAssingee);
                createdByName = entUserAssingee.getUserPersonName();
            }
            
            String tn = entUser.toTableName();
            int rc = crUser.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                String url = Config.getProperty("task.notify.on.create.url");
                String mailBody = Config.getProperty("task.notify.on.create.body");
                String mailSubject = Config.getProperty("task.notify.on.create.subject");
                
                EntityManager.mapCarrierToEntity(crUser, tn, i, entUser);
                
                if (entUser.getId().equals(SessionManager.getCurrentUserId())) {
                    continue;
                }
                
                String userFullname = entUser.getUserPersonName();
                
                url = url.replaceAll("@backlogId", entBL.getId())
                        .replaceAll("@projectId", entBL.getFkProjectId());
                
                mailSubject = mailSubject.replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName);
                
                mailBody = mailBody.replaceAll("@userFullname", userFullname)
                        .replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName)
                        .replaceAll("@createdByName", createdByName)
                        .replaceAll("@storyCard", storyCardName)
                        .replaceAll("@url", url);
                
                String email = entUser.getEmail1();
                if (email.trim().length() > 3) {
                    MailSender.sendMailInThread(email, mailSubject, mailBody, "");
                }
                
            }
        }
        
    }
    
    private static void sendMailNotificationOnDublicate(String taskId, String backlogId) throws QException {
        
        if (taskId.length() == 0) {
            return;
        }
        
        EntityTmBacklogTask entTaskNew = new EntityTmBacklogTask();
        entTaskNew.setId(taskId);
        EntityManager.select(entTaskNew);
        
        String userIds = "";
        String backlogName = "";
        String backlogRelId = "";
        String fkProjectId = "";
        
        String assigneeName = "";
        if (entTaskNew.getFkAssigneeId().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getFkAssigneeId());
            EntityManager.select(entUserAssingee);
            assigneeName = entUserAssingee.getUserPersonName();
            
            userIds += entTaskNew.getFkAssigneeId() + CoreLabel.IN;
        }
        
        String createdByName = "";
        if (entTaskNew.getCreatedBy().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getCreatedBy());
            EntityManager.select(entUserAssingee);
            createdByName = entUserAssingee.getUserPersonName();
            
            userIds += entTaskNew.getCreatedBy() + CoreLabel.IN;
        }
        
        if (backlogId.length() > 4) {
            
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setFkBacklogId(backlogId);
            Carrier cr = EntityManager.select(entTask);
            
            EntityTmBacklog entBL = new EntityTmBacklog();
            
            entBL.setId(backlogId);
            EntityManager.select(entBL);
            backlogName = entBL.getBacklogName();
            backlogRelId = entBL.getId();
            fkProjectId = entBL.getFkProjectId();
            
            userIds = entBL.getFkOwnerId() + CoreLabel.IN + entBL.getCreatedBy() + CoreLabel.IN;
            userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.FK_ASSIGNEE_ID) + CoreLabel.IN;
            userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.CREATED_BY);
        }
        
        if (userIds.length() > 6) {
            EntityCrUserList entUser = new EntityCrUserList();
            entUser.setId(userIds);
            Carrier crUser = EntityManager.select(entUser);
            
            String url = Config.getProperty("task.notify.on.create.url");
            String mailBody = Config.getProperty("task.notify.on.create.body");
            String mailSubject = Config.getProperty("task.notify.on.create.subject");
            
            String storyCardName = backlogName;
            String taskName = entTaskNew.getTaskName();
            
            String tn = entUser.toTableName();
            int rc = crUser.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityManager.mapCarrierToEntity(crUser, tn, i, entUser);
                
                if (entUser.getId().equals(SessionManager.getCurrentUserId())) {
                    continue;
                }
                
                String userFullname = entUser.getUserPersonName();
                
                url = url.replaceAll("@backlogId", backlogRelId)
                        .replaceAll("@projectId", fkProjectId);
                
                mailSubject = mailSubject.replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName);
                
                mailBody = mailBody.replaceAll("@userFullname", userFullname)
                        .replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName)
                        .replaceAll("@createdByName", createdByName)
                        .replaceAll("@storyCard", storyCardName)
                        .replaceAll("@url", url);
                
                String email = entUser.getEmail1();
                if (email.trim().length() > 3) {
                    MailSender.sendMailInThread(email, mailSubject, mailBody, "");
                }
                
            }
        }
        
    }
    
    private static void sendMailNotificationOnDelete(String taskId, String backlogId) throws QException {
        
        if (taskId.length() == 0) {
            return;
        }
        
        EntityTmBacklogTask entTaskNew = new EntityTmBacklogTask();
        entTaskNew.setStatus("D");
        entTaskNew.setId(taskId);
        EntityManager.select(entTaskNew);
        
        String storyCardName = "";
        String taskName = entTaskNew.getTaskName();
        String assigneeName = " No one";
        String bid = "";
        String pid = "";
        
        String userIds = "";
        if (entTaskNew.getFkAssigneeId().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getFkAssigneeId());
            EntityManager.select(entUserAssingee);
            assigneeName = entUserAssingee.getUserPersonName();
            
            userIds += entTaskNew.getFkAssigneeId() + CoreLabel.IN;
        }
        
        String createdByName = "";
        if (entTaskNew.getUpdatedBy().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getUpdatedBy());
            EntityManager.select(entUserAssingee);
            createdByName = entUserAssingee.getUserPersonName();
            
            userIds += entTaskNew.getCreatedBy() + CoreLabel.IN;
        }
        
        if (backlogId.length() > 0) {
            EntityTmBacklog entBL = new EntityTmBacklog();
            entBL.setId(backlogId);
            EntityManager.select(entBL);
            
            storyCardName = entBL.getBacklogName();
            bid = entBL.getId();
            pid = entBL.getFkProjectId();
            
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setFkBacklogId(backlogId);
            Carrier cr = EntityManager.select(entTask);
            
            userIds += entBL.getFkOwnerId() + CoreLabel.IN + entBL.getCreatedBy() + CoreLabel.IN;
//            userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.FK_ASSIGNEE_ID) + CoreLabel.IN;
//            userIds += cr.getValueLine(entTask.toTableName(), EntityTmBacklogTask.CREATED_BY);

        }
        
        if (userIds.length() > 6) {
            EntityCrUserList entUser = new EntityCrUserList();
            entUser.setId(userIds);
            Carrier crUser = EntityManager.select(entUser);
            
            String tn = entUser.toTableName();
            int rc = crUser.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                String url = "No URL";
                String mailBody = Config.getProperty("task.notify.on.delete.body");
                String mailSubject = Config.getProperty("task.notify.on.delete.subject");
                
                url = Config.getProperty("task.notify.on.delete.url");
                url = url.replaceAll("@backlogId", bid)
                        .replaceAll("@projectId", pid);
                
                EntityManager.mapCarrierToEntity(crUser, tn, i, entUser);
                
                if (entUser.getId().equals(SessionManager.getCurrentUserId())) {
                    continue;
                }
                
                String userFullname = entUser.getUserPersonName();
                
                mailSubject = mailSubject.replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName);
                
                mailBody = mailBody.replaceAll("@userFullname", userFullname)
                        .replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName)
                        .replaceAll("@createdByName", createdByName)
                        .replaceAll("@storyCard", storyCardName)
                        .replaceAll("@url", url);
                
                String email = entUser.getEmail1();
                if (email.trim().length() > 3) {
                    MailSender.sendMailInThread(email, mailSubject, mailBody, "");
                }
                
            }
        }
        
    }
    
    private static void sendMailNotificationOnChange(String taskId, String backlogId, String realtedFieldName, String oldValue, String newValue) throws QException {
        
        if (taskId.length() == 0) {
            return;
        }
        
        if (realtedFieldName.length() == 0) {
            return;
        }
        
        EntityTmBacklogTask entTaskNew = new EntityTmBacklogTask();
        entTaskNew.setId(taskId);
        EntityManager.select(entTaskNew);
        
        String userIds = entTaskNew.getFkAssigneeId() + CoreLabel.IN
                + entTaskNew.getCreatedBy() + CoreLabel.IN
                + entTaskNew.getUpdatedBy() + CoreLabel.IN;
        
        String bid = "";
        String pid = "";
        
        String storyCardName = " No Story Card Attached";
        String taskName = entTaskNew.getTaskName();
        String assigneeName = " No one";
        String fieldName = realtedFieldName.toUpperCase() + " ( Changed from: "
                + oldValue.toUpperCase() + " to:" + newValue.toUpperCase() + ")";
        
        if (entTaskNew.getFkAssigneeId().length() > 2) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getFkAssigneeId());
            EntityManager.select(entUserAssingee);
            assigneeName = entUserAssingee.getUserPersonName();
        }
        
        String createdByName = "No one";
        if (entTaskNew.getUpdatedBy().length() > 2) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getUpdatedBy());
            EntityManager.select(entUserAssingee);
            createdByName = entUserAssingee.getUserPersonName();
        }
        
        if (backlogId.length() > 0) {
            EntityTmBacklog entBL = new EntityTmBacklog();
            entBL.setId(backlogId);
            EntityManager.select(entBL);
            
            bid = entBL.getId();
            pid = entBL.getFkProjectId();
            
            storyCardName = entBL.getBacklogName();
            
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setFkBacklogId(backlogId);
            Carrier cr = EntityManager.select(entTask);
            
            userIds += entBL.getFkOwnerId() + CoreLabel.IN + entBL.getCreatedBy() + CoreLabel.IN;
        }
        
        if (userIds.length() > 6) {
            EntityCrUserList entUser = new EntityCrUserList();
            entUser.setId(userIds);
            Carrier crUser = EntityManager.select(entUser);
            
            String tn = entUser.toTableName();
            int rc = crUser.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                
                String url = "No URL";
                String mailBody = Config.getProperty("task.notify.on.change.body");
                String mailSubject = Config.getProperty("task.notify.on.change.subject");
                
                url = Config.getProperty("task.notify.on.change.url");
                url = url.replaceAll("@backlogId", bid)
                        .replaceAll("@taskId", taskId)
                        .replaceAll("@projectId", pid);
                
                EntityManager.mapCarrierToEntity(crUser, tn, i, entUser);
                
                if (entUser.getId().equals(SessionManager.getCurrentUserId())) {
                    continue;
                }
                
                String userFullname = entUser.getUserPersonName();
                
                mailSubject = mailSubject.replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName);
                
                mailBody = mailBody.replaceAll("@userFullname", userFullname)
                        .replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName)
                        .replaceAll("@createdByName", createdByName)
                        .replaceAll("@storyCard", storyCardName)
                        .replaceAll("@field", fieldName)
                        .replaceAll("@url", url);
                
                String email = entUser.getEmail1();
                if (email.trim().length() > 3) {
                    MailSender.sendMailInThread(email, mailSubject, mailBody, "");
                }
                
            }
        }
        
    }
    
    private static void sendMailNotificationOnNewcomment(String taskId, String backlogId, String commentBody) throws QException {
        
        if (taskId.length() == 0) {
            return;
        }
        
        EntityTmBacklogTask entTaskNew = new EntityTmBacklogTask();
        entTaskNew.setId(taskId);
        EntityManager.select(entTaskNew);
        
        String storyCardName = "";
        String taskName = entTaskNew.getTaskName();
        String assigneeName = " No one";
        
        String backlogIdRel = "";
        String projectIdRel = "";
        
        String userIds = "";
        if (entTaskNew.getFkAssigneeId().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getFkAssigneeId());
            EntityManager.select(entUserAssingee);
            assigneeName = entUserAssingee.getUserPersonName();
            userIds += entTaskNew.getFkAssigneeId() + CoreLabel.IN;
        }
        
        String createdByName = "";
        if (entTaskNew.getCreatedBy().length() > 1) {
            EntityCrUserList entUserAssingee = new EntityCrUserList();
            entUserAssingee.setId(entTaskNew.getCreatedBy());
            EntityManager.select(entUserAssingee);
            createdByName = entUserAssingee.getUserPersonName();
            
            userIds += entTaskNew.getCreatedBy() + CoreLabel.IN;
        }
        
        if (backlogId.length() > 0) {
            EntityTmBacklog entBL = new EntityTmBacklog();
            entBL.setId(backlogId);
            EntityManager.select(entBL);
            
            backlogIdRel = entBL.getId();
            projectIdRel = entBL.getFkProjectId();
            
            storyCardName = entBL.getBacklogName();
            
            EntityTmBacklogTask entTask = new EntityTmBacklogTask();
            entTask.setFkBacklogId(backlogId);
            Carrier cr = EntityManager.select(entTask);
            
            userIds += entBL.getFkOwnerId() + CoreLabel.IN + entBL.getCreatedBy();
            
        }
        
        if (userIds.length() > 6) {
            EntityCrUserList entUser = new EntityCrUserList();
            entUser.setId(userIds);
            Carrier crUser = EntityManager.select(entUser);
            
            String tn = entUser.toTableName();
            int rc = crUser.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                String url = "No URL";
                String mailBody = Config.getProperty("task.notify.on.newcomment.body");
                String mailSubject = Config.getProperty("task.notify.on.newcomment.subject");
                
                url = Config.getProperty("task.notify.on.newcomment.url");
                url = url.replaceAll("@backlogId", backlogIdRel)
                        .replaceAll("@taskId", taskId)
                        .replaceAll("@projectId", projectIdRel);
                
                EntityManager.mapCarrierToEntity(crUser, tn, i, entUser);
                
                if (entUser.getId().equals(SessionManager.getCurrentUserId())) {
                    continue;
                }
                
                String userFullname = entUser.getUserPersonName();
                
                mailSubject = mailSubject.replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName);
                
                mailBody = mailBody.replaceAll("@userFullname", userFullname)
                        .replaceAll("@taskName", taskName)
                        .replaceAll("@assigneeName", assigneeName)
                        .replaceAll("@createdByName", createdByName)
                        .replaceAll("@commentBody", commentBody)
                        .replaceAll("@storyCard", storyCardName)
                        .replaceAll("@url", url);
                
                String email = "mail";
                String em = email.replace("mail", entUser.getEmail1().toLowerCase());
                if (email.trim().length() > 3) {
                    MailSender.sendMailInThread(em, mailSubject, mailBody, "");
                }
                
            }
        }
        
    }
    
    private static void assigneSprintToTask(String taskId, String sprintIdList) throws QException {
        if (taskId.trim().length() == 0) {
            return;
        }
        
        String[] sprintIds = sprintIdList.split(",");
        for (String id : sprintIds) {
            Carrier carrier = new Carrier();
            carrier.set("fkSprintId", id);
            carrier.set("fkBacklogTaskId", taskId);
            carrier.set("assign", "1");
            assignSprintToTask(carrier);
        }
        
    }
    
    public static Carrier getTaskList4Short(String id) throws QException {
        if (id.trim().length() == 0) {
            return new Carrier();
        }
        Carrier carrier = new Carrier();
        carrier.set("id", id);
        return getTaskList4Short(carrier);
    }
    
    public static Carrier getTaskList4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        
        if (carrier.hasError()) {
            return carrier;
        }
        
        return getTaskList4Short(carrier);
    }
    
    public static Carrier getTaskList4Short(Carrier carrier) throws QException {
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        ent.setId(carrier.get("id"));
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), "taskListTable");
        return carrier;
    }
    
    private static void addTaskToJira(Carrier carrier) throws QException {
        setJiraIntegration(carrier);
    }
    
    private static void setJiraIntegration(Carrier carrier) throws QException {
        if (carrier.getValue("fkBacklogId").toString().length() > 0) {
            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setId(carrier.getValue("fkBacklogId").toString());
            EntityManager.select(ent);
            
            carrier.setValue("backlogName", ent.getBacklogName());
            
            if (ent.getJiraId().trim().length() == 0) {
                Carrier c = createEpic(carrier);
                carrier.setValue("epicJiraId", c.getValue("jiraId"));
                carrier.setValue("epicJiraKey", c.getValue("jiraKey"));
            } else {
                carrier.setValue("epicJiraId", ent.getJiraId());
                carrier.setValue("epicJiraKey", ent.getJiraKey());
            }
        }
        createTaskInJira(carrier);
        
    }
    
    private static void addTaskCountBacklog(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        int cnt = ent.getTaskCount().length() > 0
                ? Integer.valueOf(ent.getTaskCount()) : 0;
        ent.setTaskCount(String.valueOf(cnt + 1));
        EntityManager.update(ent);
    }
    
    private static void decreaseTaskCountBacklog(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        int cnt = ent.getTaskCount().length() > 0
                ? Integer.valueOf(ent.getTaskCount()) - 1 : 0;
        cnt = cnt > 0 ? cnt : 0;
        ent.setTaskCount(String.valueOf(cnt));
        EntityManager.update(ent);
    }
    
    private static void setNewBacklogHistory4TaskTypeNew(EntityTmBacklogTaskList ent) throws QException {
        String body = "<b>Task Type Name </b>: " + ent.getTaskTypeName();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_NEW, ent.getId());
    }
    
    private static Carrier insertNewBacklogTaskNotifier(Carrier carrier, String taskId) throws QException {
        String[] st = carrier.getValue("fkNotifierId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String s : st) {
            System.out.println("s=" + s);
            if (s.trim().length() == 0) {
                continue;
            }
            EntityTmBacklogTaskNotifier ent = new EntityTmBacklogTaskNotifier();
            ent.setFkBacklogTaskId(taskId);
            ent.setFkNotifierId(s);
            EntityManager.insert(ent);
        }
        return carrier;
    }
    
    public static Carrier updateBacklogTask(Carrier carrier) throws QException {
        boolean isSpentHoursExists = carrier.hasKey("spentHours");
        String eh = carrier.getValueAsString(EntityTmBacklogTask.ESTIMATED_HOURS);
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        String ehOld = entity.getEstimatedHours().length() > 0
                ? entity.getEstimatedHours() : "0";
        
        String oldCompletedHours = entity.getCompletedDuration().length() > 0
                ? entity.getCompletedDuration() : "0";
        String newSpentHours = carrier.getValue("spentHours").toString();
        
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        entity.setLastUpdatedDate(QDate.getCurrentDate());
        entity.setLastUpdatedTime(QDate.getCurrentTime());
        entity.setSpentHours(newSpentHours);
        entity.setCompletedDuration(newSpentHours);
        EntityManager.update(entity);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmBacklogTaskList ent1 = new EntityTmBacklogTaskList();
        ent1.setId(entity.getId());
        EntityManager.select(ent1);
        
        setNewBacklogHistory4TaskTypeUpdate(ent1);
        carrier.setValue("backlogStatus", setBacklogStatus(entity.getFkBacklogId()));
        
        try {
            String ehRes = String.valueOf(Double.valueOf(eh) - Double.valueOf(ehOld));
            addEstimatedHours(ent1.getFkBacklogId(), ehRes);
            
            if (isSpentHoursExists) {
                String cd = String.valueOf(Double.parseDouble(newSpentHours)
                        - Double.parseDouble(oldCompletedHours));
                addSpentHours(ent1.getFkBacklogId(), cd);
            }
        } catch (Exception e) {
        }
        return carrier;
    }
    
    public static Carrier cloneBacklogTask(Carrier carrier) throws QException {
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(carrier.get(EntityTmBacklogTask.ID));
        EntityManager.select(ent);
        
        EntityTmBacklogTask entNew = new EntityTmBacklogTask();
        entNew.setTaskName(ent.getTaskName());
        entNew.setCreatedBy(SessionManager.getCurrentUserId());
        entNew.setCreatedDate(QDate.getCurrentDate());
        entNew.setCreatedTime(QDate.getCurrentTime());
        entNew.setFkAssigneeId(ent.getFkAssigneeId());
        entNew.setFkBacklogId(ent.getFkBacklogId());
        entNew.setFkProjectId(ent.getFkProjectId());
        entNew.setFkTaskTypeId(ent.getFkTaskTypeId());
        entNew.setTaskNature(ent.getTaskNature());
        entNew.setTaskStatus(ent.getTaskStatus());
        entNew.setTaskOrderNo("11");
        entNew.setOrderNoSeq(nextTaskOrderNoSeq(ent.getFkProjectId()));
        EntityManager.insert(entNew);
        
        EntityTmTaskComment entCmt = new EntityTmTaskComment();
        entCmt.setFkTaskId(ent.getId());
        Carrier cr = EntityManager.select(entCmt);
        String tn = entCmt.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, entCmt);
            String oldCommentId = entCmt.getId();
            entCmt.setFkTaskId(entNew.getId());
            EntityManager.insert(entCmt);
            
            EntityTmCommentFile entCmtFile = new EntityTmCommentFile();
            entCmtFile.setFkCommentId(oldCommentId);
            Carrier crFile = EntityManager.select(entCmtFile);
            String tnT = entCmtFile.toTableName();
            int rc1 = crFile.getTableRowCount(tnT);
            for (int j = 0; j < rc1; j++) {
                EntityManager.mapCarrierToEntity(crFile, tnT, j, entCmtFile);
                entCmtFile.setFkCommentId(entCmt.getId());
                EntityManager.insert(entCmtFile);
                
            }
            
        }

        //commentleride copy paste olmalidir
        getTaskList4Short(entNew.getId()).copyTo(carrier);
        
        sendMailNotificationOnDublicate(ent.getId(), ent.getFkBacklogId());
        
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeUpdate(EntityTmBacklogTaskList ent) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_UPDATE, ent.getId());
    }
    
    public static Carrier updateBacklogTaskOnStatusOngoing(Carrier carrier) throws QException {
//        if (!isBacklogTaskStatusNew(carrier.getValue("id").toString())) {
//            carrier.addController("general", "Status must be new.Task Status Cannot be changed");
//            return carrier;
//        }
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        
        entity.setTaskStatus(BACKLOG_STATUS_ONGOING);
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        entity.setLastUpdatedDate(QDate.getCurrentDate());
        entity.setLastUpdatedTime(QDate.getCurrentTime());
        EntityManager.update(entity);
        
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmBacklogTaskList ent1 = new EntityTmBacklogTaskList();
        ent1.setId(entity.getId());
        EntityManager.select(ent1);
        
        setNewBacklogHistory4TaskTypeSetAsOngoing(ent1);
        carrier.setValue("backlogStatus", setBacklogStatus(entity.getFkBacklogId()));
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeSetAsOngoing(EntityTmBacklogTaskList ent) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_SET_AS_ONGOING, ent.getId());
    }
    
    public static Carrier updateBacklogTaskOnCloseTask(Carrier carrier) throws QException {
//        if (isBacklogTaskStatusClosed(carrier.getValue("id").toString())) {
//            carrier.addController("general", "Status can not be closed.");
//            return carrier;
//        }

        String spentHours = carrier.getValue("spentHours").toString();
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        entity.setTaskStatus(BACKLOG_STATUS_CLOSED);
        entity.setSpentHours(carrier.getValue("spentHours").toString());
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        entity.setLastUpdatedDate(QDate.getCurrentDate());
        entity.setLastUpdatedTime(QDate.getCurrentTime());
        
        double compDuration = 0;
        try {
            compDuration = Double.parseDouble(entity.getCompletedDuration())
                    + Double.parseDouble(spentHours);
            DecimalFormat df = new DecimalFormat("#.#");
            compDuration = Double.parseDouble(df.format(compDuration));
        } catch (Exception e) {
        }
        
        entity.setCompletedDuration(String.valueOf(compDuration));
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmBacklogTaskList ent1 = new EntityTmBacklogTaskList();
        ent1.setId(entity.getId());
        EntityManager.select(ent1);
        
        addSpentHours(entity.getFkBacklogId(), spentHours);
        
        setNewBacklogHistory4TaskTypeCloseTask(ent1);
        carrier.setValue("backlogStatus", setBacklogStatus(entity.getFkBacklogId()));
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeCloseTask(EntityTmBacklogTaskList ent) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body,
                BACKLOG_HISTORY_TYPE_TASK_TYPE_CLOSE_TASK, ent.getId(), ent.getSpentHours(), "", "");
    }
    
    public static Carrier notifyBug(Carrier carrier) throws QException {
        
        String desc = carrier.getValue("description").toString();
        String desc1 = desc;
        
        EntityTmBacklogTaskList entityList = new EntityTmBacklogTaskList();
        entityList.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entityList);
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        entity.setLastUpdatedDate(QDate.getCurrentDate());
        entity.setLastUpdatedTime(QDate.getCurrentTime());
        entity.setIsDetectedBug("1");
        entity.setTaskStatus(BACKLOG_STATUS_ONGOING);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        desc = "(<i>Detected Bug on <b>" + entityList.getTaskTypeName() + "</b></i>)<br>" + desc;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkBacklogId(entity.getFkBacklogId());
        ent.setComment(desc);
        ent.setCommentType("B");
        ent.setCommentDate(QDate.getCurrentDate());
        ent.setCommentTime(QDate.getCurrentTime());
        ent.setFkUserId(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        
        setNewBacklogHistory4TaskTypeNofityBug(entityList, desc1);
        carrier.setValue("backlogStatus", setBacklogStatus(entity.getFkBacklogId()));
        
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeNofityBug(EntityTmBacklogTaskList ent, String desc) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        body += "<br><b> Message body </b>:  " + desc;
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_NOTIFY_BUG, ent.getId());
    }
    
    public static Carrier notifyUpdate(Carrier carrier) throws QException {
        
        String desc = carrier.getValue("description").toString();
        String desc1 = desc;
        
        EntityTmBacklogTaskList entityList = new EntityTmBacklogTaskList();
        entityList.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entityList);
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        entity.setLastUpdatedDate(QDate.getCurrentDate());
        entity.setLastUpdatedTime(QDate.getCurrentTime());
        entity.setIsUpdateRequired("1");
        entity.setTaskStatus(BACKLOG_STATUS_ONGOING);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        
        desc = "(<i>Notified Update on <b>" + entityList.getTaskTypeName() + "</b></i>)<br>" + desc;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkBacklogId(entity.getFkBacklogId());
        ent.setComment(desc);
        ent.setCommentType("U");
        ent.setCommentDate(QDate.getCurrentDate());
        ent.setCommentTime(QDate.getCurrentTime());
        ent.setFkUserId(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        
        setNewBacklogHistory4TaskTypeNofityUpdate(entityList, desc1);
        carrier.setValue("backlogStatus", setBacklogStatus(entity.getFkBacklogId()));
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeNofityUpdate(EntityTmBacklogTaskList ent, String desc) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        body += "<br><b> Message body </b>:  " + desc;
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_NOTIFY_UPDATE, ent.getId());
    }
    
    public static Carrier deleteBacklogTask(Carrier carrier) throws QException {
//        if (!isBacklogTaskStatusNew(carrier.getValue("id").toString())) {
//            carrier.addController("general", "Status is new.Task Cannot be deleted");
//            return carrier;
//        }

        EntityTmBacklogTaskList entityList = new EntityTmBacklogTaskList();
        entityList.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entityList);
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        
        entity.setUpdatedBy(SessionManager.getCurrentUserId());
        EntityManager.update(entity);
        
        EntityManager.delete(entity);
        
        carrier.setValue("isSourced", "");
        carrier.setValue("backlogStatus", "");
        
        try {
            carrier.setValue("isSourced", isBacklogSourced(entity.getFkBacklogId()));
            carrier.setValue("backlogStatus", setBacklogStatus(entityList.getFkBacklogId()));
            
            setBacklogStatus(entity.getFkBacklogId());
            deleteEstimatedHours(entity.getFkBacklogId(), entity.getEstimatedHours());
            deleteSpentHours(entity.getFkBacklogId(), entity.getSpentHours());
        } catch (Exception e) {
        }
        
        sendMailNotificationOnDelete(entity.getId(), entity.getFkBacklogId());
//        setNewBacklogHistory4TaskTypeDelete(entityList);
//
//        decreaseTaskCountBacklog(entity.getFkBacklogId());
        return carrier;
    }
    
    private static void setNewBacklogHistory4TaskTypeDelete(EntityTmBacklogTaskList ent) throws QException {
        String body = "<b>Task Type Name</b>: " + ent.getTaskTypeName();
        setNewBacklogHistory(ent.getFkProjectId(), ent.getFkBacklogId(), body, BACKLOG_HISTORY_TYPE_TASK_TYPE_DELETE, ent.getId());
    }
    
    private static boolean isBacklogTaskStatusNew(String id) throws QException {
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(id);
        EntityManager.select(entity);
//        System.out.println("status="+entity.get);
        return entity.getTaskStatus().equals(BACKLOG_STATUS_NEW);
    }
    
    private static boolean isBacklogTaskStatusClosed(String id) throws QException {
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(id);
        EntityManager.select(entity);
//        System.out.println("status="+entity.get);
        return entity.getTaskStatus().equals(BACKLOG_STATUS_CLOSED);
    }
    
    public static Carrier getBacklogTaskList(Carrier carrier) throws QException {
        EntityTmBacklogTaskList ent = new EntityTmBacklogTaskList();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);

//        carrier.setValue("currentDate", QDate.getCurrentDate());
        carrier.setValue("currentDate", "20191119");
        carrier.setValue("currentTime", QDate.getCurrentTime());
        
        return carrier;
    }
    
    private static Carrier getBacklogTaskNotifierList(Carrier carrier) throws QException {
        int rc = carrier.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            EntityTmBacklogTaskNotifier ent = new EntityTmBacklogTaskNotifier();
            ent.setFkBacklogTaskId(carrier.getValue("id").toString());
            String st = EntityManager.select(ent).getValueLine(ent.toTableName(), EntityTmBacklogTaskNotifier.FK_NOTIFIER_ID);
            carrier.setValue(CoreLabel.RESULT_SET, i, "fkNotifierId", st);
        }
        
        return carrier;
    }
    
    private static String setBacklogStatus(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return "";
        }
        
        Carrier cr = EntityManager.selectBySqlId("backlogStatusCount", new String[]{backlogId});
        int rcTotal = 0;
        try {
            rcTotal = Integer.parseInt(cr.getValue(CoreLabel.RESULT_SET, 0, "total").toString());
        } catch (Exception e) {
        }
        int rcNew = 0;
        try {
            rcNew = Integer.parseInt(cr.getValue(CoreLabel.RESULT_SET, 0, "statusNew").toString());
        } catch (Exception e) {
        }
        int rcOngoing = 0;
        try {
            rcOngoing = Integer.parseInt(cr.getValue(CoreLabel.RESULT_SET, 0, "statusOngoing").toString());
        } catch (Exception e) {
        }
        int rcClosed = 0;
        try {
            rcClosed = Integer.parseInt(cr.getValue(CoreLabel.RESULT_SET, 0, "statusClosed").toString());
        } catch (Exception e) {
        }
        
        String status = BACKLOG_STATUS_NEW;
        if (rcOngoing > 0) {
            status = BACKLOG_STATUS_ONGOING;
        } else if (rcClosed == rcTotal) {
            status = BACKLOG_STATUS_CLOSED;
        } else if (rcNew == rcTotal) {
            status = BACKLOG_STATUS_NEW;
        } else {
            status = BACKLOG_STATUS_ONGOING;
        }
        
        Carrier crHours = setHoursOfBacklog(backlogId);
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        ent.setBacklogStatus(status);
        ent.setSpentHours(crHours.get("spentHours"));
        ent.setEstimatedHours(crHours.get("estimatedHours"));
//        ent.setEstimatedCounter(crHours.get("estimatedCounter"));
        ent.setExecutedCounter(crHours.get("executedCounter"));
        ent.setEstimatedBudget(crHours.get("estimatedBudget"));
        ent.setSpentBudget(crHours.get("spentBudget"));
        EntityManager.update(ent);

//        if (!oldStatus.equals(status)) {
//            setNewBacklogHistory4StatusChange(ent.getId(), status, ent.getId());
//        }
        setBindedBacklogStatus(backlogId, status);
        return status;
    }
    
    private static int getBacklogTaskStatusCountForNew(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return 0;
        }
        
        int rc = 0;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkTaskId(taskId);
        ent.setCommentStatus("new");
        rc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        return rc;
    }
    
    private static int getBacklogTaskStatusCountForOngoing(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return 0;
        }
        
        int rc = 0;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkTaskId(taskId);
        ent.setCommentStatus("ongoing");
        rc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        return rc;
    }
    
    private static int getBacklogTaskStatusCount(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return 0;
        }
        
        int rc = 0;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkTaskId(taskId);
        rc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        return rc;
    }
    
    private static int getBacklogTaskStatusCountForClosed(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return 0;
        }
        
        int rc = 0;
        
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setFkTaskId(taskId);
        ent.setCommentStatus("closed");
        rc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        return rc;
    }
    
    private static String setBacklogTaskStatus(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return "";
        }
        
        int rcNew = getBacklogTaskStatusCountForNew(taskId);
        int rcOngoing = getBacklogTaskStatusCountForOngoing(taskId);
        int rcClosed = getBacklogTaskStatusCountForClosed(taskId);
        int rcTotal = getBacklogTaskStatusCount(taskId);
        
        String status = BACKLOG_STATUS_NEW;
        if (rcOngoing > 0) {
            status = BACKLOG_STATUS_ONGOING;
        } else if (rcClosed == rcTotal) {
            status = BACKLOG_STATUS_CLOSED;
        } else if (rcNew == rcTotal) {
            status = BACKLOG_STATUS_NEW;
        } else {
            status = BACKLOG_STATUS_ONGOING;
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(taskId);
        EntityManager.select(ent);
        String oldStatus = ent.getTaskStatus();
        ent.setTaskStatus(status);
        EntityManager.update(ent);
        
        return status;
    }
    
    private static String setBindedBacklogStatus(String backlogId, String status) throws QException {
        if (backlogId.trim().length() == 0) {
            return "";
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkSourcedId(backlogId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityTmBacklog entTemp = new EntityTmBacklog();
            entTemp.setId(c.getValue(tn, i, "id").toString());
            EntityManager.select(entTemp);
            String oldStatus = entTemp.getBacklogStatus();
            entTemp.setBacklogStatus(status);
            EntityManager.update(entTemp);
            
            if (!oldStatus.equals(status)) {
                setNewBacklogHistory4StatusChange(entTemp.getId(), status, entTemp.getId());
            }
            
        }
        
        return status;
    }
    
    private static Carrier setHoursOfBacklog(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }
        
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setFkBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        
        double eh = 0.0;
        double sh = 0.0;
        
        double escounter = 0.0;
        double excounter = 0.0;
        double ebudget = 0.0;
        double sbudget = 0.0;
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            try {
                eh = eh + Double.parseDouble(ent.getEstimatedHours());
            } catch (Exception e) {
                
            }
            try {
                sh = sh + Double.parseDouble(ent.getSpentHours());
            } catch (Exception e) {
                
            }
            
            try {
                escounter = escounter + Double.parseDouble(ent.getEstimatedCounter());
            } catch (Exception e) {
                
            }
            
            try {
                excounter = excounter + Double.parseDouble(ent.getExecutedCounter());
            } catch (Exception e) {
                
            }
            
            try {
                ebudget = ebudget + Double.parseDouble(ent.getEstimatedBudget());
            } catch (Exception e) {
                
            }
            
            try {
                sbudget = sbudget + Double.parseDouble(ent.getSpentBudget());
            } catch (Exception e) {
                
            }
        }
        
        Carrier cout = new Carrier();
        cout.set("estimatedHours", eh);
        cout.set("spentHours", sh);
        cout.set("estimatedCounter", escounter);
        cout.set("executedCounter", excounter);
        cout.set("estimatedBudget", ebudget);
        cout.set("spentBudget", sbudget);
        return cout;
    }
    
    private static boolean isBacklogSourced(String fkBacklogId) throws QException {
        if (fkBacklogId.trim().length() == 0) {
            return false;
        }
        boolean isSourced = false;
        
        EntityTmBacklogTask entTask = new EntityTmBacklogTask();
        entTask.setFkBacklogId(fkBacklogId);
        Carrier tc1 = EntityManager.select(entTask);
        
        if (tc1.getTableRowCount(entTask.toTableName()) > 0) {
            isSourced = true;
        } else {
            EntityTmInput entIn = new EntityTmInput();
            entIn.setFkBacklogId(fkBacklogId);
            Carrier tc2 = EntityManager.select(entIn);
            if (tc2.getTableRowCount(entIn.toTableName()) > 0) {
                isSourced = true;
            }
        }
        
        String sourcedVal = "0";
        if (isSourced) {
            sourcedVal = "1";
        }
        
        EntityTmBacklog entUs = new EntityTmBacklog();
        entUs.setId(fkBacklogId);
        EntityManager.select(entUs);
        entUs.setIsSourced(sourcedVal);
        if (sourcedVal.equals("1")) {
            entUs.setFkSourcedId("");
        }
        EntityManager.update(entUs);
        
        return isSourced;
    }
    
    private static boolean isBacklogTicket(String fkBacklogId) throws QException {
        if (fkBacklogId.trim().length() == 0) {
            return false;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(fkBacklogId);
        ent.setIsFromCustomer("1");
        Carrier tc1 = EntityManager.select(ent);
        
        return tc1.getTableRowCount(ent.toTableName()) > 0;
    }
    
    public static Carrier getBacklogHistoryList(Carrier carrier) throws QException {
        EntityTmBacklogHistoryList ent = new EntityTmBacklogHistoryList();
        ent.setDeepWhere(false);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);

//        Carrier groupRC = EntityManager.selectBySqlId("getBacklogHistoryTypeCount", new String[]{"A", ent.getFkBacklogId()});
//        groupRC.renameTableName(CoreLabel.RESULT_SET, "historyGroupTable");
//        groupRC.copyTo(carrier);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        return carrier;
    }
    
    public static Carrier getBacklogHistoryGroupList(Carrier carrier) throws QException {
        
        Carrier crUser = EntityManager.selectBySqlId("getBacklogHistoryUserCount",
                new String[]{"A", carrier.getValue("fkBacklogId").toString()});
        
        Carrier cr = EntityManager.selectBySqlId("getBacklogHistoryTypeCount",
                new String[]{"A", carrier.getValue("fkBacklogId").toString()});
        
        cr.getKeyValuesPairFromTable(CoreLabel.RESULT_SET, "historyType", "say").copyTo(cr);
        crUser.getKeyValuesPairFromTable(CoreLabel.RESULT_SET, "historyTellerId", "say").copyTo(cr);
        
        return cr;
    }
    
    private static ArrayList getLabelListDetailsByBacklogId(String id) throws QException {
        ArrayList<String> list = new ArrayList<>();
        
        if (id.trim().length() == 0) {
            return list;
        }
        
        EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
        ent.setFkBacklogId(id);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            list.add(c.getValue(tn, i, "fkTaskLabelId").toString());
        }
        
        return list;
    }
    
    public static Carrier insertNewBacklogLabel(Carrier carrier) throws QException {
        String[] backlogIds = carrier.getValue("fkBacklogId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String[] labelIds = carrier.getValue("fkLabelId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String projectId = carrier.get("fkProjectId");
        
        boolean isNotAssign = true;
        if (carrier.isKeyExist("isNotAssign")) {
            isNotAssign = false;
        }
        for (String b : backlogIds) {
            ArrayList<String> list = getLabelListDetailsByBacklogId(b);
            ArrayList<String> deletelist = (ArrayList<String>) list.clone();
            for (String l : labelIds) {
                boolean isNumber = true;
                try {
                    Float.parseFloat(l);
                    Float.parseFloat(b);
                } catch (Exception e) {
                    isNumber = false;
                }
                if (l.trim().length() == 0 || b.trim().length() == 0 || !isNumber) {
                    continue;
                }
                
                if (list.contains(l)) {
                    deletelist.remove(l);
                    continue;
                }
                
                EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
                ent.setFkBacklogId(b);
                ent.setFkTaskLabelId(l);
                ent.setFkProjectId(projectId);
                EntityManager.insert(ent);
            }

            //secilmeyen sprintlerin silinmesi
            if (!isNotAssign) {
                for (int i = 0; i < deletelist.size(); i++) {
                    EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
                    ent.setFkBacklogId(b);
                    ent.setFkTaskLabelId(deletelist.get(i));
                    ent.setStartLimit(0);
                    ent.setEndLimit(0);
                    EntityManager.select(ent);
                    EntityManager.delete(ent);
                }
            }
        }
        
        getBacklogList4Select(backlogIds).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier deleteBacklogLabel(Carrier carrier) throws QException {
        String[] backlogIds = carrier.getValue("fkBacklogId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String[] labelIds = carrier.getValue("fkLabelId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String b : backlogIds) {
            ArrayList<String> list = getLabelListDetailsByBacklogId(b);
            for (String l : labelIds) {
                boolean isNumber = true;
                try {
                    Float.parseFloat(l);
                    Float.parseFloat(b);
                } catch (Exception e) {
                    isNumber = false;
                }
                if (l.trim().length() == 0 || b.trim().length() == 0 || !isNumber) {
                    continue;
                }
                
                EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
                ent.setFkBacklogId(b);
                ent.setFkTaskLabelId(l);
                Carrier c = EntityManager.select(ent);
                if (c.getTableRowCount(ent.toTableName()) == 1) {
                    EntityManager.delete(ent);
                }
            }
            
        }
        getBacklogList4Select(backlogIds).copyTo(carrier);
        return carrier;
    }
    
    private static ArrayList getSpringListByBacklogId(String id) throws QException {
        ArrayList<String> sprintlist = new ArrayList<>();
        
        if (id.trim().length() == 0) {
            return sprintlist;
        }
        
        EntityTmRelBacklogAndSprint ent = new EntityTmRelBacklogAndSprint();
        ent.setFkBacklogId(id);
        Carrier c = EntityManager.select(ent);
        
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            sprintlist.add(c.getValue(tn, i, "fkTaskSprintId").toString());
        }
        
        return sprintlist;
    }
    
    public static Carrier insertNewBacklogSprint(Carrier carrier) throws QException {
        String[] backlogIds = carrier.getValue("fkBacklogId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String sprintIds = carrier.getValue("fkSprintId").toString();//.split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String projectId = carrier.get("fkProjectId");//.split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        boolean isNotAssign = true;
        if (carrier.isKeyExist("isNotAssign")) {
            isNotAssign = false;
        }
        
        for (String b : backlogIds) {
            ArrayList<String> sprintlist = getSpringListByBacklogId(b);
            ArrayList<String> deletelist = (ArrayList<String>) sprintlist.clone();
//            for (String s : sprintIds) {
            String s = sprintIds;
            boolean isNumber = true;
            try {
                Float.parseFloat(s);
                Float.parseFloat(b);
            } catch (Exception e) {
                isNumber = false;
            }
            if (s.trim().length() == 0 || b.trim().length() == 0 || !isNumber) {
                continue;
            }
            if (sprintlist.contains(s)) {
                deletelist.remove(s);
                
                continue;
            }
            
            EntityTmRelBacklogAndSprint ent = new EntityTmRelBacklogAndSprint();
            ent.setFkBacklogId(b);
            ent.setFkProjectId(projectId);
            ent.setFkTaskSprintId(s);
            EntityManager.insert(ent);

            //secilmeyen sprintlerin silinmesi
            if (!isNotAssign) {
                for (int i = 0; i < deletelist.size(); i++) {
                    EntityTmRelBacklogAndSprint ent1 = new EntityTmRelBacklogAndSprint();
                    ent1.setFkBacklogId(b);
                    ent1.setFkTaskSprintId(deletelist.get(i));
                    ent1.setStartLimit(0);
                    ent1.setEndLimit(0);
                    EntityManager.select(ent1);
                    EntityManager.delete(ent1);
                }
            }
        }
        
        getBacklogList4Select(backlogIds).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier deleteBacklogSprint(Carrier carrier) throws QException {
        String[] backlogIds = carrier.getValue("fkBacklogId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        String[] labelIds = carrier.getValue("fkSprintId").toString().split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        
        for (String b : backlogIds) {
            for (String l : labelIds) {
                boolean isNumber = true;
                try {
                    Float.parseFloat(l);
                    Float.parseFloat(b);
                } catch (Exception e) {
                    isNumber = false;
                }
                if (l.trim().length() == 0 || b.trim().length() == 0 || !isNumber) {
                    continue;
                }
                
                EntityTmRelBacklogAndSprint ent = new EntityTmRelBacklogAndSprint();
                ent.setFkBacklogId(b);
                ent.setFkTaskSprintId(l);
                EntityManager.select(ent);
                Carrier c = EntityManager.select(ent);
                if (c.getTableRowCount(ent.toTableName()) == 1) {
                    EntityManager.delete(ent);
                }
            }
            
        }
        getBacklogList4Select(backlogIds).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getNotificationCountByUser(Carrier carrier) throws QException {
//        carrier = EntityManager.selectBySqlId("notificationCountByUser", new String[]{SessionManager.getCurrentUserId()});
        return carrier;
    }
    
    private static boolean hasNotReviewedNotification() throws QException {
        EntityTmNotification entNoti = new EntityTmNotification();
        entNoti.setFkUserId(SessionManager.getCurrentUserId());
        entNoti.setStartLimit("0");
        entNoti.setEndLimit("100");
        entNoti.setIsReviewed("0");
        return EntityManager.select(entNoti).getTableRowCount(entNoti.toTableName()) > 0;
    }
    
    public static Carrier getNotificationListByUser(Carrier carrier) throws QException {
        EntityTmNotification entNoti = new EntityTmNotification();
        entNoti.setFkUserId(SessionManager.getCurrentUserId());
        entNoti.setStartLimit("0");
        entNoti.setEndLimit("100");
        entNoti.setSortByAsc(false);
        entNoti.setIsReviewed("0");
        entNoti.addSortBy(new String[]{EntityTmNotification.NOTIFICATION_DATE, EntityTmNotification.NOTIFICATION_TIME});
        Carrier c = EntityManager.select(entNoti);

//        if (hasNotReviewedNotification()) {
//            entNoti.setSortByAsc(true);
//            entNoti.addSortBy(new String[]{"isReviewed"});
//        } else {
//            entNoti.setSortByAsc(false);
//            entNoti.addSortBy(new String[]{EntityTmNotification.NOTIFICATION_DATE, EntityTmNotification.NOTIFICATION_TIME});
//        }
        EntityTmNotification entNoti1 = new EntityTmNotification();
        entNoti1.setFkUserId(SessionManager.getCurrentUserId());
        entNoti1.setStartLimit("0");
        entNoti1.setEndLimit("100");
        entNoti1.setSortByAsc(false);
        entNoti1.setIsReviewed("1");
        entNoti1.addSortBy(new String[]{EntityTmNotification.NOTIFICATION_DATE, EntityTmNotification.NOTIFICATION_TIME});
        Carrier c1 = EntityManager.select(entNoti1);
        
        c1.copyTo(c);
        
        String backlogIds = "-1" + CoreLabel.IN + c.getValueLine(entNoti.toTableName(), EntityTmNotification.FK_BACKLOG_ID);
        String projectId = "-1" + CoreLabel.IN + c.getValueLine(entNoti.toTableName(), EntityTmNotification.FK_PROJECT_ID);
        String historyIds = "-1" + CoreLabel.IN + c.getValueLine(entNoti.toTableName(), EntityTmNotification.FK_BACKLOG_HISTORY_ID);
        
        EntityTmProject entP = new EntityTmProject();
        entP.setId(projectId);
        Carrier cP = EntityManager.select(entP);
        
        EntityTmBacklogList entBL = new EntityTmBacklogList();
        entBL.setId(backlogIds);
        Carrier cBL = EntityManager.select(entBL);
        
        EntityTmBacklogHistoryList entBH = new EntityTmBacklogHistoryList();
        entBH.setId(historyIds);
        Carrier cBH = EntityManager.select(entBH);
        
        c.mergeCarrier(entNoti.toTableName(), EntityTmNotification.FK_PROJECT_ID,
                cP, entP.toTableName(), EntityTmProject.ID,
                new String[]{EntityTmProject.PROJECT_NAME});
        
        c.mergeCarrier(entNoti.toTableName(), EntityTmNotification.FK_BACKLOG_ID,
                cBL, entBL.toTableName(), EntityTmBacklogList.ID,
                new String[]{EntityTmBacklogList.BACKLOG_NAME,
                    EntityTmBacklogList.ASSIGNEE_NAME,
                    EntityTmBacklogList.BACKLOG_STATUS,
                    EntityTmBacklogList.COMMENT_COUNT,
                    EntityTmBacklogList.CREATED_BY_NAME,
                    EntityTmBacklogList.CREATED_DATE,
                    EntityTmBacklogList.CREATED_TIME,
                    EntityTmBacklogList.INPUT_COUNT,
                    EntityTmBacklogList.IS_BOUNDED,
                    EntityTmBacklogList.IS_FROM_CUSTOMER,
                    EntityTmBacklogList.IS_INITIAL,
                    EntityTmBacklogList.IS_SOURCED,
                    EntityTmBacklogList.PRIORITY,
                    EntityTmBacklogList.ORDER_NO,
                    EntityTmBacklogList.TASK_COUNT,
                    EntityTmBacklogList.BACKLOG_NAME
                });
        
        c.mergeCarrier(entNoti.toTableName(), EntityTmNotification.FK_BACKLOG_HISTORY_ID,
                cBH, entBH.toTableName(), EntityTmBacklogHistoryList.ID,
                new String[]{EntityTmBacklogHistoryList.HISTORY_BODY,
                    EntityTmBacklogHistoryList.HISTORY_DATE,
                    EntityTmBacklogHistoryList.HISTORY_TELLER_ID,
                    EntityTmBacklogHistoryList.HISTORY_TELLER_IMAGE,
                    EntityTmBacklogHistoryList.HISTORY_TELLER_NAME,
                    EntityTmBacklogHistoryList.HISTORY_TIME,
                    EntityTmBacklogHistoryList.HISTORY_TYPE
                });

        //update all notifications as is_reviewed by current user
        setAllNotificationsAsReviewByCurrentUser();

        //delete past 200 and above notificationss
        deleteNotificationsByCurrentUser();
        return c;
    }
    
    private static void setAllNotificationsAsReviewByCurrentUser() throws QException {
        EntityTmNotification entNoti = new EntityTmNotification();
        entNoti.setFkUserId(SessionManager.getCurrentUserId());
        entNoti.setIsReviewed("0");
        Carrier c = EntityManager.select(entNoti);
        String tn = entNoti.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, entNoti.toTableName(), i, entNoti);
            entNoti.setId(c.getValue(tn, i, "id").toString());
            entNoti.setIsReviewed("1");
            EntityManager.update(entNoti);
        }
    }
    
    private static void deleteNotificationsByCurrentUser() throws QException {
        EntityTmNotification entNoti = new EntityTmNotification();
        entNoti.setFkUserId(SessionManager.getCurrentUserId());
        entNoti.setStartLimit("200");
        entNoti.setEndLimit("10000");
        Carrier c = EntityManager.select(entNoti);
        String tn = entNoti.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            entNoti.setId(c.getValue(tn, i, "id").toString());
            EntityManager.delete(entNoti);
        }
    }
    
    public static Carrier getProjectReport(Carrier carrier) throws QException {
        String query = EntityManager.getQuerySqlId(carrier.getValueAsString("project"));
        String id = getMyProjectsWithPermission();
        if (id.trim().length() == 0) {
            query = query.replace("::partWhere", "");
        } else {
            query = query.replace("::partWhere", " AND pro.id in ('" + id.replace(CoreLabel.IN, "','") + "')");
        }
        carrier = EntityManager.selectBySql(query);
        return carrier;
    }
    
    public static Carrier getSpentHoursReport(Carrier carrier) throws QException {
        String query = EntityManager.getQuerySqlId("spentHours");
        query = carrier.getValueAsString("projectId").trim().length() > 0
                ? query.replace("::paramProject", " AND b.fk_Project_Id in ('" + carrier.getValueAsString("projectId").trim().replace(CoreLabel.IN, "','") + "')")
                : query.replace("::paramProject", "");
        
        query = carrier.getValueAsString("teller").trim().length() > 0
                ? query.replace("::paramTeller", " AND b.history_Teller_Id in ('" + carrier.getValueAsString("teller").trim().replace(CoreLabel.IN, "','") + "')")
                : query.replace("::paramTeller", "");
        
        if (carrier.getValueAsString("historyDate").trim().length() > 0) {
            String dateFrom = carrier.getValueAsString("historyDate").trim().split(CoreLabel.IN)[0];
            
            String dateTo = carrier.getValueAsString("historyDate").trim().split(CoreLabel.IN)[1];
            query = query.replace("::paramDate", " AND b.history_Date between  " + dateFrom + " and " + dateTo);
            
        } else {
            query = query.replace("::paramDate", "");
        }
        
        query = query.replace("::paramOrderBy", carrier.getValueAsString("orderBy").trim());
        query = carrier.getValueAsString("asc").trim().length() > 0
                ? query.replace("::paramAsc", carrier.getValueAsString("asc").trim())
                : query.replace("::paramAsc", "asc");
        
        carrier = EntityManager.selectBySql(query);
        return carrier;
    }
    
    public static Carrier insertNewBacklogDependency(Carrier carrier) throws QException {
        String projectId = carrier.getValueAsString("fkProjectId");
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String parentBacklogId = carrier.getValueAsString("fkParentBacklogId");
        
        boolean f = hasLoopOnBacklogDependency(projectId, backlogId, parentBacklogId);
        
        if (f) {
            carrier.setValue("error", "It's not allowed to add dependency for this User Story. "
                    + "There is a loop iteration for the selected User Story. Please remove dependencies that causes "
                    + " loop.");
            return carrier;
        }
        
        EntityTmBacklogDependency ent = new EntityTmBacklogDependency();
        EntityManager.mapCarrierToEntity(carrier, ent);
        int tc = EntityManager.select(ent).getTableRowCount(ent.toTableName());
        if (tc == 0) {
            EntityManager.insert(ent);
        }
        
        getBacklogDependencyList4Select(carrier).copyTo(carrier);
        return carrier;
        
    }
    
    private static boolean hasLoopOnBacklogDependency(String projectId, String backlogId, String parentBacklogId) throws QException {
        boolean f = false;
        ArrayList<String> backlogList = getAllChildTreeOfBacklogDependency(projectId, backlogId);
        return getAllParentTreeOfBacklogDependency(projectId, parentBacklogId, backlogList);
    }
    
    private static boolean getAllParentTreeOfBacklogDependency(String projectId, String parentBacklogId,
            ArrayList<String> backlogList) throws QException {
        
        ArrayList<String> parentBacklogList = new ArrayList<String>();
        parentBacklogList.add(parentBacklogId);
        return getAllParentTreeOfBacklog(projectId, parentBacklogId, backlogList);
        
    }
    
    private static boolean getAllParentTreeOfBacklog(String projectId, String parentBacklogId,
            ArrayList<String> backlogList) throws QException {
        
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        ent.setFkProjectId(projectId);
        ent.setFkBacklogId(parentBacklogId);
        Carrier c = EntityManager.select(ent);
        int rc = c.getTableRowCount(ent.toTableName());
        
        for (int i = 0; i < rc; i++) {
            String bid = c.getValue(ent.toTableName(), i,
                    EntityTmBacklogDependency.FK_PARENT_BACKLOG_ID).toString();
            
            if (backlogList.contains(bid)) {
                return true;
            }
            
            getAllParentTreeOfBacklog(projectId, bid, backlogList);
        }
        return false;
    }
    
    private static ArrayList<String> getAllChildTreeOfBacklogDependency(String projectId, String backlogId) throws QException {
        ArrayList<String> backlogList = new ArrayList<String>();
        backlogList.add(backlogId);
        getAllChildTreeOfBacklog(backlogList, projectId, backlogId);
        return backlogList;
    }
    
    private static void getAllChildTreeOfBacklog(ArrayList<String> backlogList,
            String projectId, String backlogId) throws QException {
        
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        ent.setFkProjectId(projectId);
        ent.setFkParentBacklogId(backlogId);
        Carrier c = EntityManager.select(ent);
        int rc = c.getTableRowCount(ent.toTableName());
        
        for (int i = 0; i < rc; i++) {
            String bid = c.getValue(ent.toTableName(), i,
                    EntityTmBacklogDependency.FK_BACKLOG_ID).toString();
            
            backlogList.add(bid);
            getAllChildTreeOfBacklog(backlogList, projectId, bid);
        }
        
    }
    
    public static Carrier deleteBacklogDependency(Carrier carrier) throws QException {
        EntityTmBacklogDependency entity = new EntityTmBacklogDependency();
        entity.setId(carrier.getValue(EntityTmBacklogDependency.ID).toString());
        EntityManager.delete(entity);
        
        getBacklogDependencyList4Select(carrier).copyTo(carrier);
        return carrier;
    }
    
    public static Carrier getBacklogDependencyList(Carrier carrier) throws QException {
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        carrier.copyTableColumn(ent.toTableName(), "fkBacklogId", "id");
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        getInputList4Dependency(carrier);
        
        return carrier;
    }
    
    public static Carrier getBacklogDependencyList4Select(Carrier carrier) throws QException {
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), "dependenceTable");
        return carrier;
    }
    
    public static Carrier getBacklogDependencyList4Gui(Carrier carrier) throws QException {
        EntityTmBacklogDependencyList ent = new EntityTmBacklogDependencyList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    private static String getInputDescriptionLineByInputId(String inputId) throws QException {
        String res = "";
        if (inputId.trim().length() == 0) {
            return res;
        }
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputId);
        res = EntityManager.select(ent).getValueLine(ent.toTableName(),
                EntityTmInputDescription.DESCRIPTION);
        
        return res;
    }
    
    private static String getOutputListDetails4Dependency(String backlogId) throws QException {
        if (backlogId.length() == 0) {
            return "";
        }
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(backlogId);
        entInput.setInputType("OUT");
        entInput.addSortBy(EntityTmInput.INPUT_NAME);
        entInput.setSortByAsc(false);
        Carrier cout = EntityManager.select(entInput);
        
        return cout.toJson(entInput.toTableName());
        
    }
    
    private static String getInputListDetails4Dependency(String backlogId) throws QException {
        if (backlogId.length() == 0) {
            return "";
        }
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(backlogId);
        entInput.setInputType("IN");
        entInput.addSortBy("orderNo");
        entInput.setSortByAsc(true);
        Carrier cout = EntityManager.select(entInput);
        
        String tn = entInput.toTableName();
        int rc1 = cout.getTableRowCount(tn);
        for (int j = 0; j < rc1; j++) {
            EntityManager.mapCarrierToEntity(cout, tn, j, entInput);
            
            String desc = getInputDescriptionLineByInputId(entInput.getId());
            cout.setValue(tn, j, "inputDescription", desc);
            
            if (entInput.getFkDependentOutputId().length() > 0) {
                EntityTmInput entInput1 = new EntityTmInput();
                entInput1.setId(entInput.getFkDependentOutputId());
                EntityManager.select(entInput1);
                cout.setValue(tn, j, "dependentInputName", entInput1.getInputName());
            } else {
                cout.setValue(tn, j, "dependentInputName", "");
            }
        }
        return cout.toJson(entInput.toTableName());
        
    }
    
    private static String getParentInputListDetails4Dependency(String backlogId) throws QException {
        if (backlogId.length() == 0) {
            return "";
        }
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(backlogId);
        entInput.setInputType("IN");
        entInput.addSortBy("orderNo");
        entInput.setSortByAsc(true);
        Carrier cout = EntityManager.select(entInput);
        
        String tn = entInput.toTableName();
        int rc1 = cout.getTableRowCount(tn);
        for (int j = 0; j < rc1; j++) {
            EntityManager.mapCarrierToEntity(cout, tn, j, entInput);
            
            String desc = getInputDescriptionLineByInputId(entInput.getId());
            cout.setValue(tn, j, "parentInputDescription", desc);
            
            if (entInput.getFkDependentOutputId().length() > 0) {
                EntityTmInput entInput1 = new EntityTmInput();
                entInput1.setId(entInput.getFkDependentOutputId());
                EntityManager.select(entInput1);
                cout.setValue(tn, j, "parentDependentInputName", entInput1.getInputName());
            } else {
                cout.setValue(tn, j, "parentDependentInputName", "");
            }
        }
        return cout.toJson(entInput.toTableName());
        
    }
    
    public static void getInputList4Dependency(Carrier carrier) throws QException {
        int rc = carrier.getTableRowCount(CoreLabel.RESULT_SET);
        for (int i = 0; i < rc; i++) {
            String backlodId = carrier.getValue(CoreLabel.RESULT_SET, i,
                    EntityTmBacklogDependencyList.FK_BACKLOG_ID).toString();
            
            if (backlodId.length() == 0) {
                continue;
            }
            
            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setId(backlodId);
            EntityManager.select(ent);
            carrier.setValue(CoreLabel.RESULT_SET, i, "backlogIsApi", ent.getIsApi());
            
            String parentBacklodId = carrier.getValue(CoreLabel.RESULT_SET, i,
                    EntityTmBacklogDependencyList.FK_PARENT_BACKLOG_ID).toString();
            
            if (parentBacklodId.length() == 0) {
                continue;
            }
            
            EntityTmBacklog entPr = new EntityTmBacklog();
            entPr.setId(parentBacklodId);
            EntityManager.select(entPr);
            carrier.setValue(CoreLabel.RESULT_SET, i, "parentBacklogIsApi", entPr.getIsApi());
            
            String outcome = getInputListDetails4Dependency(backlodId);
            carrier.setValue(CoreLabel.RESULT_SET, i, "inputList", outcome);
            
            String outcomeParent = getParentInputListDetails4Dependency(parentBacklodId);
            carrier.setValue(CoreLabel.RESULT_SET, i, "parentInputList", outcomeParent);
            
            String outline = getOutputListDetails4Dependency(backlodId);
            carrier.setValue(CoreLabel.RESULT_SET, i, "outputList", outline);
            
            String outlineParent = getOutputListDetails4Dependency(parentBacklodId);
            carrier.setValue(CoreLabel.RESULT_SET, i, "parentOutputList", outlineParent);
        }
    }
    
    public Carrier deleteTaskCommentFile(Carrier carrier) throws QException {
        String fname = carrier.getValue("filename").toString();
        
        EntityTmCommentFile ent = new EntityTmCommentFile();
        ent.setFileName(fname);
        ent.setStartLimit(0);
        ent.setEndLimit(1);
        Carrier c = EntityManager.select(ent);
        
        if (ent.getId().trim().length() > 0) {
            EntityManager.delete(ent);
        }
        
        return carrier;
        
    }
    
    public Carrier pinImageToStoryCard(Carrier carrier) throws QException {
        String id = carrier.get("id");
        
        EntityTmTaskFile ent = new EntityTmTaskFile();
        ent.setId(id);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        if (ent.getFkBacklogId().length() > 0) {
            ent.setIsPinned("1");
            EntityManager.update(ent);
            getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        }
        return carrier;
    }
    
    public Carrier unpinImageToStoryCard(Carrier carrier) throws QException {
        String id = carrier.get("id");
        
        EntityTmTaskFile ent = new EntityTmTaskFile();
        ent.setId(id);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        if (ent.getFkBacklogId().length() > 0) {
            ent.setIsPinned("0");
            EntityManager.update(ent);
            getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        }
        return carrier;
    }
    
    public Carrier deleteBacklogFile(Carrier carrier) throws QException {
        String id = carrier.get("id");
        
        EntityTmTaskFile ent = new EntityTmTaskFile();
        ent.setId(id);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        EntityManager.delete(ent);
        if (ent.getFkBacklogId().length() > 0) {
            getBacklogList4Select(ent.getFkBacklogId()).copyTo(carrier);
        }
        return carrier;
        
    }
    
    public Carrier toggleCommentAsSubtask(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        
        if (ent.getIsSubtask().equals("1")) {
            ent.setIsSubtask("0");
        } else {
            ent.setIsSubtask("1");
        }
        EntityManager.update(ent);
        carrier.setValue("isSubtask", ent.getIsSubtask());
        return carrier;
    }
    
    public Carrier toggleCommentAsBug(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        
        if (ent.getIsBug().equals("1")) {
            ent.setIsBug("0");
        } else {
            ent.setIsBug("1");
        }
        EntityManager.update(ent);
        carrier.setValue("isBug", ent.getIsBug());
        return carrier;
    }
    
    public Carrier toggleCommentAsRequest(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        
        if (ent.getIsRequest().equals("1")) {
            ent.setIsRequest("0");
        } else {
            ent.setIsRequest("1");
        }
        EntityManager.update(ent);
        carrier.setValue("isRequest", ent.getIsRequest());
        return carrier;
    }
    
    public Carrier getBakclogListByLabel(Carrier carrier) throws QException {
        String labelId = carrier.getValueAsString("fkLabelId");
        EntityTmRelBacklogAndLabelList ent = new EntityTmRelBacklogAndLabelList();
        ent.setFkTaskLabelId(labelId);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier getBakclogLabelList4Select(Carrier carrier) throws QException {
        EntityTmRelBacklogAndLabel ent = new EntityTmRelBacklogAndLabel();
        ent.setFkProjectId(carrier.get("fkProjectId"));
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        EntityTmTaskLabel entLbl = new EntityTmTaskLabel();
        entLbl.setFkProjectId(carrier.get("fkProjectId"));
        Carrier cr = EntityManager.select(entLbl);
        cr.renameTableName(entLbl.toTableName(), "labelList");
        cr.copyTo(carrier);
        
        EntityTmChangeReqLabel entChangeLabel = new EntityTmChangeReqLabel();
        entChangeLabel.setFkProjectId(carrier.get("fkProjectId"));
        Carrier crChangeLabel = EntityManager.select(entChangeLabel);
        crChangeLabel.renameTableName(entChangeLabel.toTableName(), "notifiedLabelList");
        crChangeLabel.copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier getAllBakclogListByLabel(Carrier carrier) throws QException {
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        ent.setInputCount(CoreLabel.GT + '0');
        ent.addSortBy(EntityTmBacklogList.BACKLOG_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.copyTableColumn(CoreLabel.RESULT_SET, EntityTmBacklogList.ID, "fkBacklogId");
        return carrier;
    }
    
    public static Carrier insertNewTestScenario(Carrier carrier) throws QException {
        
        EntityTmTestScenario ent = new EntityTmTestScenario();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setScenarioDate(QDate.getCurrentDate());
        ent.setScenarioTime(QDate.getCurrentTime());
        ent.setFkCreatedBy(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        
        if (carrier.getValueAsString("isActualResChecked").equals("1")
                && (carrier.getValueAsString("actualResult").trim().length() > 0
                || carrier.getValueAsString("fileName").trim().length() > 6)) {
            EntityTmTestTrial entT = new EntityTmTestTrial();
            EntityManager.mapCarrierToEntity(carrier, entT);
            entT.setFkScenarioId(ent.getId());
            entT.setTrialDate(QDate.getCurrentDate());
            entT.setTrialTime(QDate.getCurrentTime());
            entT.setFkCreatedBy(SessionManager.getCurrentUserId());
            EntityManager.insert(entT);
        }
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
        
    }
    
    public static Carrier updateTestScenario(Carrier carrier) throws QException {
        EntityTmTestScenario entity = new EntityTmTestScenario();
        entity.setId(carrier.getValue(EntityTmTestScenario.ID).toString());
        EntityManager.select(entity);
        EntityManager.mapCarrierToEntity(carrier, entity, false);
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTestScenario(Carrier carrier) throws QException {
        EntityTmTestScenario entity = new EntityTmTestScenario();
        entity.setId(carrier.getValue(EntityTmTestScenario.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public static Carrier getTestScenarioList(Carrier carrier) throws QException {
        
        EntityTmTestScenario ent = new EntityTmTestScenario();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        Carrier cout = EntityManager.select(ent);
        
        EntityTmTestScenario ent1 = new EntityTmTestScenario();
        ent1.setDeepWhere(true);
        ent1.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        ent1.setLinkId(carrier.getValueAsString(EntityTmTestScenario.FK_BACKLOG_ID));
        ent1.setSortByAsc(true);
        Carrier c1 = EntityManager.select(ent1);
        c1.copyTo(cout);
        
        String userIds = cout.getValueLine(ent1.toTableName(), EntityTmTestScenario.FK_CREATED_BY);
        
        if (userIds.trim().length() > 0) {
            EntityCrUser entUser = new EntityCrUser();
            entUser.setId(userIds);
            Carrier cUser = EntityManager.select(entUser);
            cout.mergeCarrier(ent1.toTableName(), EntityTmTestScenario.FK_CREATED_BY,
                    cUser, entUser.toTableName(), "id",
                    new String[]{EntityCrUser.USER_PERSON_NAME});
            cout.mergeCarrier(ent1.toTableName(), EntityTmTestScenario.FK_CREATED_BY,
                    cUser, entUser.toTableName(), "id",
                    new String[]{EntityCrUser.USER_IMAGE});
        }
        
        cout.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return cout;
    }
    
    public static Carrier getTrialList(Carrier carrier) throws QException {
        
        EntityTmTestScenario ent = new EntityTmTestScenario();
        ent.setDeepWhere(true);
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        carrier.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent));
        carrier.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getTestScenarioList"));
        return carrier;
    }
    
    private boolean hasNotOKTrial(String scenarioId) throws QException {
        EntityTmTestTrial ent = new EntityTmTestTrial();
        ent.setFkScenarioId(scenarioId);
        ent.setTrialStatus("nok");
        return EntityManager.select(ent)
                .getTableRowCount(ent.toTableName()) > 0;
        
    }
    
    public Carrier hasNotOKTrial(Carrier carrier) throws QException {
        EntityTmTestTrial ent = new EntityTmTestTrial();
        ent.setFkScenarioId(carrier.getValueAsString("fkScenarioId"));
        ent.setTrialStatus("nok");
        String res = EntityManager.select(ent)
                .getTableRowCount(ent.toTableName()) > 0 ? "1" : "0";
        carrier.setValue("result", res);
        return carrier;
    }
    
    public Carrier insertNewTrial(Carrier carrier) throws QException {
        
        if (hasNotOKTrial(carrier).getValueAsString("result").equals("1")
                && carrier.getValueAsString("trialStatus").equals("nok")) {
            carrier.addController("general", "ThereAreAvailableNotOKTrials");
            return carrier;
        }
        
        EntityTmTestTrial ent = new EntityTmTestTrial();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setTrialDate(QDate.getCurrentDate());
        ent.setTrialTime(QDate.getCurrentTime());
        ent.setFkCreatedBy(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);
        
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public Carrier updateTrial(Carrier carrier) throws QException {
        
        EntityTmTestTrial entity = new EntityTmTestTrial();
        entity.setId(carrier.getValue(EntityTmTestScenario.ID).toString());
        EntityManager.select(entity);
        
        EntityTmTestTrial ent = new EntityTmTestTrial();
        ent.setFkScenarioId(entity.getFkScenarioId());
        ent.setTrialStatus("nok");
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        if (hasNotOKTrial(entity.getFkScenarioId())
                && !ent.getId().equals(entity.getId())) {
            entity.setTrialStatus("ok");
        } else {
            entity.setTrialStatus(carrier.getValueAsString("trialStatus"));
        }
        
        entity.setActualResult(carrier.getValueAsString("actualResult"));
        EntityManager.update(entity);
        carrier = EntityManager.select(entity);
        
        carrier.renameTableName(entity.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public static Carrier deleteTrial(Carrier carrier) throws QException {
        EntityTmTestTrial entity = new EntityTmTestTrial();
        entity.setId(carrier.getValue(EntityTmTestScenario.ID).toString());
        EntityManager.delete(entity);
        return carrier;
    }
    
    public Carrier getTrialListByScenario(Carrier carrier) throws QException {
        
        EntityTmTestTrialList ent = new EntityTmTestTrialList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier getTrialListById(Carrier carrier) throws QException {
        EntityTmTestTrialList ent = new EntityTmTestTrialList();
        ent.setId(carrier.getValueAsString("id"));
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier deleteTrialFile(Carrier carrier) throws QException {
        EntityTmTestTrial ent = new EntityTmTestTrial();
        ent.setId(carrier.getValueAsString("id"));
        EntityManager.select(ent);
        ent.setFileName(ent.getFileName()
                .replace(carrier.getValueAsString("filename"), ""));
        EntityManager.update(ent);
        return carrier;
    }
    
    public Carrier getTaskListByTrialId(Carrier carrier) throws QException {
        String trialId = carrier.getValueAsString("fkTrialId");
        
        EntityTmTestTrial entTrial = new EntityTmTestTrial();
        entTrial.setId(trialId);
        EntityManager.select(entTrial);
        
        if (entTrial.getFkScenarioId().length() == 0) {
            return carrier;
        }
        
        EntityTmTestScenario entSc = new EntityTmTestScenario();
        entSc.setId(entTrial.getFkScenarioId());
        EntityManager.select(entSc);
        
        if (entSc.getFkBacklogId().length() == 0) {
            return carrier;
        }
        
        EntityTmBacklogTaskList entBT = new EntityTmBacklogTaskList();
        entBT.setFkBacklogId(entSc.getFkBacklogId());
        carrier = EntityManager.select(entBT);
        carrier.renameTableName(entBT.toTableName(), CoreLabel.RESULT_SET);
        
        return carrier;
    }
    
    public Carrier getTaskListByBacklogId(Carrier carrier) throws QException {
        EntityTmBacklogTaskList entBT = new EntityTmBacklogTaskList();
        entBT.setFkBacklogId(carrier.getValueAsString("fkBacklogId"));
        carrier = EntityManager.select(entBT);
        carrier.renameTableName(entBT.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier notifyTrialAsBug(Carrier carrier) throws QException {
        String trialId = carrier.getValueAsString("fkTrialId");
        String taskId[] = carrier.getValueAsString("fkTaskId").split(CoreLabel.IN);
        String backlogId = carrier.getValueAsString("fkBacklogId");
        
        EntityTmTestTrialList entTrialList = new EntityTmTestTrialList();
        entTrialList.setId(trialId);
        EntityManager.select(entTrialList);
        
        EntityTmTestTrial entTrial = new EntityTmTestTrial();
        entTrial.setId(trialId);
        EntityManager.select(entTrial);
        entTrial.setIsNotifiedAsBug("1");
        entTrial.setFkBacklogId(carrier.getValueAsString("fkBacklogId"));
        entTrial.setFkTaskId(carrier.getValueAsString("fkTaskId"));
        EntityManager.update(entTrial);
        
        EntityTmTestScenario entScenario = new EntityTmTestScenario();
        entScenario.setId(entTrial.getFkScenarioId());
        EntityManager.select(entScenario);
        
        for (String id : taskId) {
            
            EntityTmBacklogTask entBT = new EntityTmBacklogTask();
            entBT.setId(id);
            carrier = EntityManager.select(entBT);
            entBT.setIsNotifiedBug(trialId);
            entBT.setTaskStatus("ongoing");
            EntityManager.update(entBT);
            
            EntityTmTaskComment entComment = new EntityTmTaskComment();
            entComment.setFkBacklogId(entBT.getFkBacklogId());
            entComment.setFkTaskId(entBT.getId());
            entComment.setFkUserId(entTrial.getFkCreatedBy());
            entComment.setCommentDate(QDate.getCurrentDate());
            entComment.setCommentTime(QDate.getCurrentTime());
            entComment.setCommentStatus("new");
            entComment.setEstimatedHours("0");
            entComment.setSpentHours("0");
            entComment.setIsBug("1");
            entComment.setIsNotifiedBug(trialId);
            entComment.setComment(
                    "Sent By: " + entTrialList.getCreatedByName()
                    + " \n"
                    + "*TEST SCENARIO:* " + entScenario.getScenarioName()
                    + "  \n"
                    + "*EXPECTED RESULT:* " + entScenario.getExpectedResult()
                    + "  \n"
                    + "*ACTUAL RESULT:* " + entTrial.getActualResult());
            EntityManager.insert(entComment);
            
            String files[] = entTrial.getFileName().trim().split("\\|");
            for (String f : files) {
                if (f.trim().length() > 0) {
                    EntityTmCommentFile entFile = new EntityTmCommentFile();
                    entFile.setFkCommentId(entComment.getId());
                    entFile.setFileName(f);
                    entFile.setUploadDate(QDate.getCurrentDate());
                    entFile.setUploadTime(QDate.getCurrentTime());
                    EntityManager.insert(entFile);
                    System.out.println("5");
                }
            }
            
            setBacklogTaskStatus(id);
            addBugToJira(entComment.getId(), entComment.getFkBacklogId());
        }
        
        setBacklogStatus(backlogId);
        return carrier;
    }
    
    public static Carrier closeBug4TestTrial(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValueAsString("fkCommentId"));
        EntityManager.select(ent);
        ent.setCommentType("N");
        ent.setCommentStatus("closed");
        EntityManager.update(ent);
        
        EntityTmTaskComment ent1 = new EntityTmTaskComment();
        ent1.setIsNotifiedBug(ent.getIsNotifiedBug());
        int c = EntityManager.select(ent1).getTableRowCount(ent1.toTableName());
        
        EntityTmTaskComment ent2 = new EntityTmTaskComment();
        ent2.setIsNotifiedBug(ent.getIsNotifiedBug());
        ent2.setCommentType("N");
        int c1 = EntityManager.select(ent2).getTableRowCount(ent2.toTableName());
        
        if (c1 == c) {
            EntityTmTestTrial entTrial = new EntityTmTestTrial();
            entTrial.setId(ent.getIsNotifiedBug());
            EntityManager.select(entTrial);
            entTrial.setTrialStatus("ok");
            EntityManager.update(entTrial);
        }
        
        setBacklogTaskStatus(ent.getFkTaskId());
        setBacklogStatus(ent.getFkBacklogId());
        return carrier;
    }
    
    public Carrier showNotifiedInfoByTrialId(Carrier carrier) throws QException {
        String id = carrier.getValueAsString("id");
        
        EntityTmTestTrial entBT = new EntityTmTestTrial();
        entBT.setId(id);
        carrier = EntityManager.select(entBT);
        
        String baklogname = "";
        String backlogId = "";
        String tasks = "";
        
        if (entBT.getFkBacklogId().trim().length() > 0) {
            EntityTmBacklog entBL = new EntityTmBacklog();
            entBL.setId(entBT.getFkBacklogId());
            EntityManager.select(entBL);
            baklogname = entBL.getBacklogName();
            backlogId = entBL.getId();
        }
        
        if (entBT.getFkTaskId().trim().length() > 0) {
            EntityTmBacklogTaskList entBL = new EntityTmBacklogTaskList();
            entBL.setId(entBT.getFkTaskId());
            Carrier c = EntityManager.select(entBL);
            String tn = entBL.toTableName();
            int rc = c.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityManager.mapCarrierToEntity(c, tn, i, entBL);
                
                EntityTmTaskComment entCmmt = new EntityTmTaskComment();
                entCmmt.setFkTaskId(entBL.getId());
                entCmmt.setIsNotifiedBug(id);
                entCmmt.setStartLimit(0);
                entCmmt.setEndLimit(0);
                EntityManager.select(entCmmt);
                
                tasks += entBL.getAssigneeName() + " (" + entBL.getTaskTypeName();
                tasks += ") ";
                tasks += entCmmt.getCommentType().equals("N") ? " - Closed" : " - Ongoing";
                
                tasks += i < rc - 1 ? "<br> \n " : "";
            }
        }
        
        carrier.setValue("backlogName", baklogname);
        carrier.setValue("backlogId", backlogId);
        carrier.setValue("assigneeName", tasks);
        
        return carrier;
    }
    
    public Carrier dublicateUserStories(Carrier carrier) throws QException {
        String currentBaklodId = carrier.getValueAsString("currentProjectId");
        String destProjectId = carrier.getValueAsString("destProjectId");
        String action = carrier.getValueAsString("action");
        String prefix = "  ";
        String newBacklogId = "";
        
        String ids[] = carrier.getValueAsString("fkBacklogId").split("\\|");
        ControllerPool cp = new ControllerPool();
        
        for (String id : ids) {
            if (id.trim().length() == 0 || !cp.isFloat(id).equals("200")) {
                continue;
            }
            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setId(id);
            EntityManager.select(ent);
            
            if (!currentBaklodId.equals(destProjectId) && action.equals("move")) {
                EntityManager.delete(ent);
            }
            ent.setFkProjectId(destProjectId);
            ent.setBacklogName(prefix + ent.getBacklogName());
            ent.setOrderNo(nextBKLOrderNo(destProjectId));
            ent.setEstimatedHours("0");
            ent.setSpentHours("0");
            ent.setBacklogStatus("new");
            EntityManager.insert(ent);
            
            newBacklogId += ent.getId() + CoreLabel.IN;
            
            setNewBacklogHistory4ProcessDescUpdate(ent);

            //dublicate inputs  ofuser story
            copyInputsByBacklogId(id, ent.getId(), destProjectId);
            copyProcessDescriptionsByBacklogId(id, ent.getId(), destProjectId);
            copyTaskFilesByBacklogId(id, ent.getId(), destProjectId);
        }
        
        if (newBacklogId.length() > 0) {
            getBacklogList4Select(newBacklogId).copyTo(carrier);
        }
        
        return carrier;
    }
    
    private void copyTaskFilesByBacklogId(String backlogId, String newId, String projectId) throws QException {
        EntityTmTaskFile ent = new EntityTmTaskFile();
        ent.setFkBacklogId(backlogId);
        ent.addSortBy("id");
        ent.setSortByAsc(true);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            String oldId = ent.getId();
            ent.setFkBacklogId(newId);
            ent.setFkProjectId(projectId);
            EntityManager.insert(ent);
        }
    }
    
    private void copyProcessDescriptionsByBacklogId(String backlogId, String newId, String projectId) throws QException {
        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setFkBacklogId(backlogId);
        ent.addSortBy(EntityTmInput.ORDER_NO);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            String oldId = ent.getId();
            ent.setFkBacklogId(newId);
            ent.setFkProjectId(projectId);
            EntityManager.insert(ent);
        }
    }
    
    private void copyInputsByBacklogId(String backlogId, String newId, String projectId) throws QException {
        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(backlogId);
        ent.addSortBy(EntityTmInput.ORDER_NO);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            if (ent.getInputType().equals("TBL") || ent.getInputType().equals("TAB")) {
                continue;
//                if (ent.getFkRelatedCompId().length()>0){
//                    EntityTmInputTableComp entTC = new EntityTmInputTableComp();
//                    entTC.setId(ent.getFkRelatedCompId());
//                    entTC.setEndLimit(0);
//                    EntityManager.select(entTC);
////                    String oldInputTableId = entTC.getId();
//                    
//                    EntityManager.insert(entTC);
//                    ent.setFkRelatedCompId(entTC.getId());

//                    EntityTmRelTableInput entTI = new EntityTmRelTableInput();
//                    entTI.setFkTableId(oldInputTableId);
//                    Carrier crTI = EntityManager.select(entTI);
//                    
//                    String tnTI = entTI.toTableName();
//                    int rcTI = crTI.getTableRowCount(tnTI);
//                    for (int k=0;k<rcTI;k++){
//                        EntityManager.mapCarrierToEntity(crTI,tn,k, entTI);
//                        entTI.setFkTableId(entTC.getId());
//                        EntityManager.insert(entTI);
//                    }
//                }
//                 
//            }else if (ent.getInputType().equals("TAB") 
////                    || ent.getInputType().equals("TAB")
//                    ) {
//                if (ent.getFkRelatedCompId().length()>0){
//                    EntityTmInputTabComp entTC = new EntityTmInputTabComp();
//                    entTC.setId(ent.getFkRelatedCompId());
//                    entTC.setEndLimit(0);
//                    EntityManager.select(entTC);
////                    String oldInputTableId = entTC.getId();
//                    
//                    EntityManager.insert(entTC);
//                    ent.setFkRelatedCompId(entTC.getId());
//                }
//                 
            }
            String oldId = ent.getId();
            ent.setFkBacklogId(newId);
            ent.setFkProjectId(projectId);
            EntityManager.insert(ent);
            
            setNewBacklogHistory4InputNew(ent);
            
            copyInputAttributesByInputId(oldId, ent.getId(), ent.getFkBacklogId(), ent.getFkProjectId());
            copyInputRelatedEvenByInputId(oldId, ent.getId(), ent.getFkProjectId());
            copyInputClassesByInputId(oldId, ent.getId(), ent.getFkProjectId());
            copyInputDescriptionByBacklogId(oldId, ent.getId(), projectId);
            
        }
        increaseBacklogInputCount(newId, rc);
        
    }
    
    private void copyInputRelatedEvenByInputId(String inputId, String newId, String projectId) throws QException {
        if (inputId.trim().length() == 0) {
            return;
        }
        
        EntityTmInputActionRel ent = new EntityTmInputActionRel();
        ent.setFkInputId(inputId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 1; i <= rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, rc - i, ent);
            ent.setFkProjectId(projectId);
            ent.setFkInputId(newId);
            EntityManager.insert(ent);
        }
    }
    
    private void copyInputClassesByInputId(String inputId, String newId, String projectId) throws QException {
        if (inputId.trim().length() == 0) {
            return;
        }
        
        EntityTmInputClassRelation ent = new EntityTmInputClassRelation();
        ent.setFkInputId(inputId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 1; i <= rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, rc - i, ent);
            ent.setFkProjectId(projectId);
            ent.setFkInputId(newId);
            EntityManager.insert(ent);
        }
    }
    
    private void copyInputAttributesByInputId(String inputId, String newId, String backlogId, String projectId) throws QException {
        if (inputId.trim().length() == 0) {
            return;
        }
        
        EntityTmInputAttributes ent = new EntityTmInputAttributes();
        ent.setFkInputId(inputId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 1; i <= rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, rc - i, ent);
            ent.setFkProjectId(projectId);
            ent.setFkBacklogId(backlogId);
            ent.setFkInputId(newId);
            EntityManager.insert(ent);
        }
    }
    
    private void copyInputDescriptionByBacklogId(String inputId, String newId, String projectId) throws QException {
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 1; i <= rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, rc - i, ent);
            ent.setFkProjectId(projectId);
            ent.setFkInputId(newId);
            EntityManager.insert(ent);
            
            EntityTmInput entInput = new EntityTmInput();
            entInput.setId(ent.getFkInputId());
            EntityManager.select(entInput);
            
            setNewBacklogHistory4InputDescriptionNew(ent, entInput);
        }
    }
    
    public Carrier assignPriorityToUserStory(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkBacklogId").split("\\|");
        String priority = carrier.getValueAsString("priority");
        
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setId(id);
            EntityManager.select(ent);
            ent.setPriority(priority);
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier getBacklogDetailedInputInfoById(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(backlogId);
        entInput.setInputType("IN%IN%GUI%IN%TAB%IN%TBL");
        entInput.addSortBy("orderNo");
        entInput.setSortByAsc(true);
        Carrier cout = EntityManager.select(entInput);
        cout.renameTableName(entInput.toTableName(), "inputListTable");
        
        EntityTmInput entInputOut = new EntityTmInput();
        entInputOut.setFkBacklogId(backlogId);
        entInputOut.setInputType("OUT");
        entInputOut.addSortBy("orderNo");
        entInputOut.setSortByAsc(true);
        Carrier ct4Out = EntityManager.select(entInputOut);
        ct4Out.renameTableName(entInput.toTableName(), "inputOutputList");
        ct4Out.copyTo(cout);
        
        EntityTmBacklog entBacklog = new EntityTmBacklog();
        entBacklog.setId(backlogId);
        EntityManager.select(entBacklog);
        cout.setValue("backlogId", entBacklog.getId());
        cout.setValue("backlogDescription", entBacklog.getDescriptionSourced());
        cout.setValue("backlogName", entBacklog.getBacklogName());
        cout.setValue("backlogIsApi", entBacklog.getIsApi());
        cout.setValue("backlogParam1", entBacklog.getParam1());
        
        Carrier cDesc = new Carrier();
        addInputListTable(cDesc, backlogId);
        cout.mergeCarrier("inputListTable", new String[]{"id"},
                cDesc, "inputDescListTable", new String[]{"fkInputId"}, new String[]{"description", "inputTable"}, " ", true);
        
        return cout;
    }
    
    public Carrier getUserStoryInfoById(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(backlogId);
        Carrier cout = EntityManager.select(ent);
        cout.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        if (ent.getFkProjectId().trim().length() == 0) {
            return carrier;
        }
        
        String bid = backlogId + CoreLabel.IN + getRelatedTabBacklogListByBacklogId(backlogId)
                + CoreLabel.IN + getSectionBacklogListByBacklogId(backlogId);

//        cout.renameTableName(CoreLabel.RESULT_SET, "userStoryTable");
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkBacklogId(bid);
        Carrier ctTemp = EntityManager.select(entIn);
        String inputIds = ctTemp.getValueLine(entIn.toTableName());
        if (inputIds.length() > 4) {
            Carrier crIn = new Carrier();
            crIn.set("fkProjectId", ent.getFkProjectId());
            getInputList4Select(crIn).copyTo(cout);
            cout.renameTableName(CoreLabel.RESULT_SET, "inputTable");
            
            Carrier crt = new Carrier();
            crt.set("fkProjectId", ent.getFkProjectId());
            getInputDescriptionList4Select(crt).copyTo(cout);
            cout.renameTableName(CoreLabel.RESULT_SET, "inputDescriptionTable");
            
            getTabListOfInputByBacklog(bid, ent.getFkProjectId()).copyTo(cout);
        }
        
        Carrier crUS = new Carrier();
        crUS.set("fkProjectId", ent.getFkProjectId());
        getBacklogList4Select(crUS).copyTo(cout);
        cout.renameTableName(CoreLabel.RESULT_SET, "userStoryTable");
        
        addChildBoundStories(cout, backlogId);
        addDependency(cout, backlogId);
        addDependencyChild(cout, backlogId);
        getBacklogDetailedInputInfoById(carrier).copyTo(cout);
        addInputListTable(cout, backlogId);
        getBacklogFileList(backlogId).copyTo(cout);
        addBacklogDescriptionTable(backlogId).copyTo(cout);
        
        cout.set("relatedTableInputIds", getRelatedTableInputList(backlogId));
        
        getHistoryDatesAndTimes(cout, backlogId);
        return cout;
    }
    
    public Carrier getUserStoryInfoByIdOld(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(backlogId);
        Carrier cout = EntityManager.select(ent);
        cout.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        
        addChildBoundStories(cout, backlogId);
        addDependency(cout, backlogId);
        addDependencyChild(cout, backlogId);
        getBacklogDetailedInputInfoById(carrier).copyTo(cout);
        addInputListTable(cout, backlogId);
        getBacklogFileList(backlogId).copyTo(cout);
        addBacklogDescriptionTable(backlogId).copyTo(cout);
        
        cout.set("relatedTableInputIds", getRelatedTableInputList(backlogId));
        
        getHistoryDatesAndTimes(cout, backlogId);
        return cout;
    }
    
    private String getSectionBacklogListByBacklogId(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return "";
        }
        
        EntityTmInput ent = new EntityTmInput();
        ent.setFkBacklogId(backlogId);
        Carrier crOut = EntityManager.select(ent);
        String sectionId = crOut.getValueLine(ent.toTableName(), EntityTmInput.PARAM_1);
        
        return sectionId;
    }
    
    private String getRelatedTabBacklogListByBacklogId(String backlogId) throws QException {
        String res = "";
        if (backlogId.trim().length() == 0) {
            return res;
        }
        
        EntityTmInputTabComp ent = new EntityTmInputTabComp();
        ent.setFkBacklogId(backlogId);
        Carrier crOut = EntityManager.select(ent);
        String tabIds = crOut.getValueLine(ent.toTableName());
        
        if (tabIds.trim().length() > 1) {
            EntityTmRelTabBacklog entIn = new EntityTmRelTabBacklog();
            entIn.setFkTabId(tabIds);
            entIn.addSortBy("orderNo");
            entIn.setSortByAsc(true);
            res = EntityManager.select(entIn)
                    .getValueLine(entIn.toTableName(), EntityTmRelTabBacklog.FK_RELATED_BACKLOG_ID);
        }
        
        return res;
    }
    
    private String getRelatedTableInputList(String fkBacklogId) throws QException {
        String ids = "";
        
        if (fkBacklogId.trim().length() == 0) {
            return ids;
        }
        
        EntityTmInput entIn = new EntityTmInput();
        entIn.setFkBacklogId(fkBacklogId);
        entIn.setInputType("TBL");
        String tableIds = EntityManager.select(entIn).getValueLine(
                entIn.toTableName(), EntityTmInput.FK_RELATED_COMP_ID);
        
        if (tableIds.length() > 5) {
            EntityTmRelTableInput ent = new EntityTmRelTableInput();
            ent.setFkTableId(tableIds);
            ent.setInputStatus("A");
            ids = EntityManager.select(ent).getValueLine(ent.toTableName(),
                    EntityTmRelTableInput.FK_INPUT_ID);
        }
        
        return ids;
    }
    
    private Carrier getBacklogFileList(String backlogId) throws QException {
        if (backlogId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmTaskFile entFile = new EntityTmTaskFile();
        entFile.setFkBacklogId(backlogId);
        entFile.setSortByAsc(true);
        Carrier crFile = EntityManager.select(entFile);
        Carrier crFileId = crFile.getKVPairListFromTable(entFile.toTableName(), "fkBacklogId",
                EntityTmTaskFile.ID);
        Carrier crOutTr = crFile.getKVPairListFromTable(entFile.toTableName(), "fkBacklogId",
                EntityTmTaskFile.FILE_URL);
        
        Carrier cout = new Carrier();
        cout.set("fileUrl", crOutTr.get(backlogId));
        cout.set("fileUrlId", crFileId.get(backlogId));
        
        entFile.setIsPinned("1");
        Carrier crFileNew = EntityManager.select(entFile);
        String filePinnedImageId = crFileNew.getValueLine(entFile.toTableName(), "id", ",");
        cout.set("filePinnedImageId", filePinnedImageId);
        
        return cout;
    }
    
    public Carrier getUserStoryInfo4HistoryDateAndLabelById(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        
        EntityTmBacklogList ent = new EntityTmBacklogList();
        ent.setId(backlogId);
        Carrier cout = EntityManager.select(ent);
        cout.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);

//        addChildBoundStories(cout, backlogId);
//        addDependency(cout, backlogId);
//        addDependencyChild(cout, backlogId);
//        getBacklogDetailedInputInfoById(carrier).copyTo(cout);
//        addInputListTable(cout, backlogId);
        getHistoryDatesAndTimes(cout, backlogId);
        return cout;
    }
    
    private String getHistoryTypesForFilter() {
        String res = "";
        res += BACKLOG_HISTORY_TYPE_INPUT_NEW + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_INPUT_UPDATE + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_INPUT_DELETE + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_PROCESS_DESC_UPDATE + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_NEW + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE + CoreLabel.IN;
        res += BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_UPDATE + CoreLabel.IN;
//     res += BACKLOG_HISTORY_TYPE_INPUT_TYPE_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_ORDER_NO_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_CELL_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_CSS_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_CONTAINER_CSS_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_CONTENT_UPDATE ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_RELATION_ADDED ;
//     res += BACKLOG_HISTORY_TYPE_INPUT_RELATION_DELETED ;
        return res;
    }
    
    private void getHistoryDatesAndTimes(Carrier carrier, String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        
        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
        ent.setFkBacklogId(backlogId);
        ent.setHistoryType(getHistoryTypesForFilter());
        ent.addSortBy(EntityTmBacklogHistory.HISTORY_DATE);
        ent.addDistinctField(EntityTmBacklogHistory.HISTORY_DATE);
        String dates = EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmBacklogHistory.HISTORY_DATE);
        
        carrier.setValue("historyDates", dates);
        carrier.setValue("currentDate", QDate.getCurrentDate());
    }
    
    private static Carrier getInputList4TableByBacklogId(String backlogId) throws QException {
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(backlogId);
//        entInput.setInputType("IN%IN%GUI%IN%OUT");
//        entInput.addSortBy(new String[]{"inputType", "orderNo"});
        entInput.addSortBy(new String[]{"orderNo"});
        entInput.setSortByAsc(true);
        return EntityManager.select(entInput);
    }
    
    private static void addInputDesction4InputTable(Carrier cr, String tn, int i,
            String fkDependentBacklogId, String fkDependentOutputId) throws QException {
        EntityTmInputDescription entDescRel = new EntityTmInputDescription();
        
        entDescRel.setFkInputId(fkDependentOutputId);
        String descRel = EntityManager.select(entDescRel)
                .getValueLine(entDescRel.toTableName(), EntityTmInputDescription.DESCRIPTION, "%IN%");
        cr.setValue(tn, i, "descriptionRelated", descRel);
        cr.setValue(tn, i, "backlogNameRelated", getBacklogNameById(fkDependentBacklogId));
        cr.setValue(tn, i, "inputNameRelated", getInputNameById(fkDependentOutputId));
    }
    
    private static void setInputDescriptionDetail4InputTable(Carrier cr, String tn, int i, String inputId) throws QException {
        //add desctiption
        EntityTmInputDescription entDesc = new EntityTmInputDescription();
        entDesc.setFkInputId(inputId);
        Carrier crDesc = EntityManager.select(entDesc);
        String tnNew = entDesc.toTableName();
        int rcNew = crDesc.getTableRowCount(tnNew);
        String desc = "";
        String colored = "";
        for (int kk = 0; kk < rcNew; kk++) {
            EntityManager.mapCarrierToEntity(crDesc, tnNew, kk, entDesc);
            desc += entDesc.getDescription() + "%IN%";
            colored += entDesc.getColored().length() > 0
                    ? entDesc.getColored() + "%IN%"
                    : "undefined" + "%IN%";
        }
        
        cr.setValue(tn, i, "description", desc);
        cr.setValue(tn, i, "colored", colored);
        cr.setValue(tn, i, "fkInputId", inputId);
    }
    
    private static void addInputListTable(Carrier carrier, String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        Carrier cout = getInputList4TableByBacklogId(backlogId);
        
        EntityTmInput entInput = new EntityTmInput();
        String tn = entInput.toTableName();
        int rc = cout.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cout, tn, i, entInput);
            setInputDescriptionDetail4InputTable(cout, tn, i, entInput.getId());

            //add related SUS
            if (entInput.getFkDependentOutputId().length() > 0) {
                addInputDesction4InputTable(cout, tn, i, entInput.getFkDependentBacklogId(),
                        entInput.getFkDependentOutputId());
            }
            
            ArrayList<String> backlogList = new ArrayList<String>();
            backlogList.add(backlogId);
//            getInputMainParam1(cout,tn, backlogList);

            if ((entInput.getComponentType().equals("sctn")
                    || entInput.getComponentType().equals("tab"))
                    && entInput.getParam1().length() > 0) {
                String out = getInputDetailedList4AllGuiWithTabAndSection(backlogList, entInput.getParam1());
                cout.setValue(tn, i, "inputTable", out);
            }
        }
        
        cout.renameTableName(tn, "inputDescListTable");
        cout.copyTo(carrier);
        
    }
    
    private static Carrier addBacklogDescriptionTable(String backlogId) throws QException {
        if (backlogId.trim().length() == 0) {
            Carrier crOut = new Carrier();
            crOut.setValue("backlogDescColored", "");
            crOut.setValue("descColored", "");
            return crOut;
        }
        
        EntityTmBacklogDescription entDesc = new EntityTmBacklogDescription();
        Carrier crDesc = new Carrier();
        crDesc.set("fkBacklogId", backlogId);
        crDesc = getBacklogDescriptionList(crDesc);
        crDesc.renameTableName(entDesc.toTableName(), "backlogDescTableNew");
        
        String tn = "backlogDescTableNew";
        int rc = crDesc.getTableRowCount(tn);
        String desc = "";
        String colored = "";
        for (int kk = 0; kk < rc; kk++) {
            EntityManager.mapCarrierToEntity(crDesc, tn, kk, entDesc);
            desc += entDesc.getDescription() + "%IN%";
            colored += entDesc.getColoredType().length() > 0
                    ? entDesc.getColoredType() + "%IN%"
                    : "undefined" + "%IN%";
        }
        
        Carrier crOut = new Carrier();
        crOut.setValue("backlogDescColored", desc);
        crOut.setValue("descColored", colored);
        
        return crOut;
        
    }
    
    public static String getInputDetailedList4AllGui(ArrayList backlogList, String bid) throws QException {
        if (bid.length() == 0 || backlogList.contains(bid)) {
            return "";
        }
        if (backlogList.size() == 7) {
            return "";
        }
        backlogList.add(bid);
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(bid);
        entInput.setInputType("IN%IN%GUI");
        entInput.addSortBy("orderNo");
        entInput.setSortByAsc(true);
        Carrier cout = EntityManager.select(entInput);
        
        String tn = entInput.toTableName();
        int rc = cout.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cout, tn, i, entInput);
            if ((entInput.getComponentType().equals("sctn") //                    || entInput.getComponentType().equals("tab")
                    ) && entInput.getParam1().length() > 0) {
                String out = getInputDetailedList4AllGui(backlogList, entInput.getParam1());
                cout.setValue(tn, i, "inputTable", out);
            }
        }
        return cout.toJson(entInput.toTableName());
    }
    
    public static String getInputDetailedList4AllGuiWithTabAndSection(ArrayList backlogList, String bid) throws QException {
        if (bid.length() == 0 || backlogList.contains(bid)) {
            return "";
        }
        if (backlogList.size() == 15) {
            return "";
        }
        backlogList.add(bid);
        
        EntityTmBacklog entBL = new EntityTmBacklog();
        entBL.setId(bid);
        EntityManager.select(entBL);
        
        EntityTmInput entInput = new EntityTmInput();
        entInput.setFkBacklogId(bid);
        entInput.setInputType("IN%IN%GUI");
        entInput.addSortBy("orderNo");
        entInput.setSortByAsc(true);
        Carrier cout = EntityManager.select(entInput);
        
        String tn = entInput.toTableName();
        int rc = cout.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cout, tn, i, entInput);
            setInputDescriptionDetail4InputTable(cout, tn, i, entInput.getId());
            cout.setValue(tn, i, EntityTmBacklog.DESCRIPTION_SOURCED, entBL.getDescriptionSourced());
            
            if ((entInput.getComponentType().equals("sctn")
                    || entInput.getComponentType().equals("tab")) && entInput.getParam1().length() > 0) {
                String out = getInputDetailedList4AllGuiWithTabAndSection(backlogList, entInput.getParam1());
                cout.setValue(tn, i, "inputTable", out);
            }
        }
        return cout.toJson(entInput.toTableName());
    }
    
    private static void getInputMainParam1(Carrier cr, String tablename, ArrayList backlogList) throws QException {
        String tn = tablename;
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String compType = cr.getValue(tn, i, "componentType").toString();
            String param1 = cr.getValue(tn, i, "param1").toString();
            
            if ((compType.equals("sctn") //                    || compType.equals("tab")
                    ) && param1.length() > 0) {
                String out = getInputDetailedList4AllGui(backlogList, param1);
                cr.setValue(tn, i, "inputTable", out);
            }
        }
    }
    
    private void addChildBoundStories(Carrier carrier, String backlogId) throws QException {
        Carrier childBound = getChildBoundUserStories(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkChildBoundUserStoryId",
                childBound.getValueLine(CoreLabel.RESULT_SET, "id", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "fkChildBoundUserStoryName",
                childBound.getValueLine(CoreLabel.RESULT_SET, EntityTmBacklogList.BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
        
    }
    
    private void addDependency(Carrier carrier, String backlogId) throws QException {
        Carrier dependency = getUserStoryDependency(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyId",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.FK_PARENT_BACKLOG_ID, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyOrderNo",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, "parentBacklogOrderNo", CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "dependencyName",
                dependency.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.PARENT_BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
    }
    
    private void addDependencyChild(Carrier carrier, String backlogId) throws QException {
        Carrier dependencyChild = getUserStoryChildDependency(backlogId);
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyId",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.FK_BACKLOG_ID, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyName",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, EntityTmBacklogDependencyList.BACKLOG_NAME, CoreLabel.SEPERATOR_VERTICAL_LINE));
        carrier.setValue(CoreLabel.RESULT_SET, 0, "childDependencyOrderNo",
                dependencyChild.getValueLine(
                        CoreLabel.RESULT_SET, "backlogOrderNo", CoreLabel.SEPERATOR_VERTICAL_LINE));
    }
    
    public Carrier copyInput(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkInputId").split("\\|");
        String backlodId = carrier.getValueAsString("fkBacklogId");
        String fkDestinationBacklogId = carrier.getValueAsString("fkDestinationBacklogId");
        String inputIds = "";
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            
            inputIds += id + CoreLabel.IN;
            
            EntityTmInput ent = new EntityTmInput();
            ent.setId(id);
            EntityManager.select(ent);
            String oldId = ent.getId();
            ent.setFkBacklogId(fkDestinationBacklogId);
            EntityManager.insert(ent);
            
            copyInputAttributesByInputId(oldId, ent.getId(), ent.getFkBacklogId(), ent.getFkProjectId());
            copyInputRelatedEvenByInputId(oldId, ent.getId(), ent.getFkProjectId());
            copyInputClassesByInputId(oldId, ent.getId(), ent.getFkProjectId());
            copyInputDescription(id, ent.getId());
        }
        
        EntityTmBacklog entBacklog = new EntityTmBacklog();
        entBacklog.setId(fkDestinationBacklogId);
        EntityManager.select(entBacklog);
        entBacklog.setIsSourced("1");
        EntityManager.update(entBacklog);
        
        increaseBacklogInputCount(fkDestinationBacklogId, ids.length);
        
        getInputList4Select(inputIds).copyTo(carrier);
        getBacklogList4Select(fkDestinationBacklogId + CoreLabel.IN + backlodId)
                .copyTo(carrier);
        return carrier;
    }
    
    private void copyInputDescription(String sourceInputId, String destInputId) throws QException {
        
        if (sourceInputId.length() == 0 || destInputId.length() == 0) {
            return;
        }
        
        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(sourceInputId);
        Carrier c = EntityManager.select(ent);
        String tn = ent.toTableName();
        int rc = c.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(c, tn, i, ent);
            ent.setFkInputId(destInputId);
            EntityManager.insert(ent);
        }
        
    }
    
    public Carrier moveInput(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkInputId").split("\\|");
        String backlodId = carrier.getValueAsString("fkBacklogId");
        String fkDestinationBacklogId = carrier.getValueAsString("fkDestinationBacklogId");
        String inputIds = "";
        
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            inputIds += id + CoreLabel.IN;
            
            EntityTmInput ent = new EntityTmInput();
            ent.setId(id);
            EntityManager.select(ent);
            ent.setFkBacklogId(fkDestinationBacklogId);
            EntityManager.update(ent);
        }
        
        EntityTmBacklog entBacklog = new EntityTmBacklog();
        entBacklog.setId(fkDestinationBacklogId);
        EntityManager.select(entBacklog);
        entBacklog.setIsSourced("1");
        EntityManager.update(entBacklog);
        
        decreaseBacklogInputCount(backlodId, ids.length);
        increaseBacklogInputCount(fkDestinationBacklogId, ids.length);
        
        getInputList4Select(inputIds).copyTo(carrier);
        getBacklogList4Select(fkDestinationBacklogId + CoreLabel.IN + backlodId)
                .copyTo(carrier);
        
        return carrier;
    }
    
    public Carrier setAsInput(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkInputId").split("\\|");
        String backlodId = carrier.getValueAsString("fkBacklogId");
        
        String idLine = "";
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            
            idLine += id + CoreLabel.IN;
            
            EntityTmInput ent = new EntityTmInput();
            ent.setId(id);
            EntityManager.select(ent);
            ent.setInputType("IN");
            EntityManager.update(ent);
        }
        
        getBacklogList4Select(backlodId).copyTo(carrier);
        if (idLine.length() > 3) {
            getInputList4Select(idLine).copyTo(carrier);
        }
        return carrier;
    }
    
    public Carrier setAsOutput(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkInputId").split("\\|");
        String backlodId = carrier.getValueAsString("fkBacklogId");
        
        String idLine = "";
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            
            idLine += id + CoreLabel.IN;
            
            EntityTmInput ent = new EntityTmInput();
            ent.setId(id);
            EntityManager.select(ent);
            ent.setInputType("OUT");
            EntityManager.update(ent);
        }
        
        getBacklogList4Select(backlodId).copyTo(carrier);
        if (idLine.length() > 3) {
            getInputList4Select(idLine).copyTo(carrier);
        }
        return carrier;
    }
    
    public Carrier deleteInputs(Carrier carrier) throws QException {
        String ids[] = carrier.getValueAsString("fkInputId").split("\\|");
        String backlodId = carrier.getValueAsString("fkBacklogId");
        
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            
            Carrier cr = new Carrier();
            cr.set("id", id);
            carrier = deleteInput(cr);
        }
        
        getBacklogList4Select(backlodId).copyTo(carrier);
        return carrier;
    }
    
    public Carrier notifyAsChangeRequest(Carrier carrier) throws QException {
        String historyId = carrier.getValueAsString("fkHistoryId");
        String taskId[] = carrier.getValueAsString("fkTaskId").split(CoreLabel.IN);
        
        EntityTmBacklogHistoryList enthistoryList = new EntityTmBacklogHistoryList();
        enthistoryList.setId(historyId);
        EntityManager.select(enthistoryList);
        
        for (String id : taskId) {
            
            EntityTmBacklogTask entBT = new EntityTmBacklogTask();
            entBT.setId(id);
            carrier = EntityManager.select(entBT);
            entBT.setIsUpdateRequired(historyId);
            entBT.setTaskStatus("ongoing");
            EntityManager.update(entBT);
            
            EntityTmTaskComment entComment = new EntityTmTaskComment();
            entComment.setFkBacklogId(entBT.getFkBacklogId());
            entComment.setFkTaskId(entBT.getId());
            entComment.setFkUserId(SessionManager.getCurrentUserId());
            entComment.setCommentDate(QDate.getCurrentDate());
            entComment.setCommentTime(QDate.getCurrentTime());
            entComment.setIsRequest("1");
//            entComment.setIsNotifiedBug(historyId); 
            entComment.setComment(enthistoryList.getHistoryBody());
            
            EntityManager.insert(entComment);
        }
        
        return carrier;
    }
    
    public static void deleteEstimatedHours(String backlogId, String hr) throws QException {
        addEstimatedHours(backlogId, "-" + hr);
    }
    
    public static void addEstimatedHours(String backlogId, String hr) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        try {
            float eh = ent.getEstimatedHours().trim().length() > 0
                    ? Float.valueOf(ent.getEstimatedHours().trim())
                    : 0;
            double ehf = eh + Float.valueOf(hr);
            ehf = ehf < 0 ? 0 : ehf;
            DecimalFormat df = new DecimalFormat("#.##");
            ehf = Double.parseDouble(df.format(ehf));
            ent.setEstimatedHours(String.valueOf(ehf));
            EntityManager.update(ent);
        } catch (Exception e) {
            
        }
    }
    
    public static void deleteSpentHours(String backlogId, String hr) throws QException {
        addSpentHours(backlogId, "-" + hr);
    }
    
    public static void addSpentHours(String backlogId, String hr) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        try {
            float eh = ent.getSpentHours().trim().length() > 0
                    ? Float.valueOf(ent.getSpentHours().trim())
                    : 0;
            double ehf = eh + Float.valueOf(hr);
            ehf = ehf < 0 ? 0 : ehf;
            DecimalFormat df = new DecimalFormat("#.##");
            ehf = Double.parseDouble(df.format(ehf));
            ent.setSpentHours(String.valueOf(ehf));
            EntityManager.update(ent);
        } catch (Exception e) {
            
        }
    }
    
    private static void increaseBacklogInputCount(String backlogId, int val) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        try {
            double ic = ent.getInputCount().trim().length() > 0
                    ? Double.valueOf(ent.getInputCount().trim())
                    : 0;
            double ehf = ic + val;
            ehf = ehf < 0 ? 0 : ehf;
            DecimalFormat df = new DecimalFormat("#");
            ehf = Double.parseDouble(df.format(ehf));
            ent.setInputCount(String.valueOf(ehf));
            EntityManager.update(ent);
        } catch (Exception e) {
            
        }
    }
    
    private static void decreaseBacklogInputCount(String backlogId, int val) throws QException {
        if (backlogId.trim().length() == 0) {
            return;
        }
        
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(backlogId);
        EntityManager.select(ent);
        
        try {
            double ic = ent.getInputCount().trim().length() > 0
                    ? Double.valueOf(ent.getInputCount().trim())
                    : 0;
            double ehf = ic - val;
            ehf = ehf < 0 ? 0 : ehf;
            DecimalFormat df = new DecimalFormat("#");
            ehf = Double.parseDouble(df.format(ehf));
            ent.setInputCount(String.valueOf(ehf));
            EntityManager.update(ent);
        } catch (Exception e) {
            
        }
    }
    
    public static Carrier startTask(Carrier carrier) throws QException {
        String id = carrier.getValueAsString("id");
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(id);
        EntityManager.select(ent);
        ent.setStartDate(QDate.getCurrentDate());
        ent.setStartTime(QDate.getCurrentTime());
        ent.setStartType("P");
        ent.setTaskStatus("ongoing");
        EntityManager.update(ent);
        
        setBacklogStatus(ent.getFkBacklogId());
        
        return carrier;
    }
    
    public static void setBacklogTaskStatusAsClosed(String taskId) throws QException {
        if (taskId.trim().length() == 0) {
            return;
        }
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(taskId);
        EntityManager.select(ent);
        ent.setTaskStatus("closed");
        EntityManager.update(ent);
        setBacklogStatus(ent.getFkBacklogId());
    }
    
    public static Carrier stopTask(Carrier carrier) throws QException {
        String id = carrier.getValueAsString("id");
        EntityTmBacklogTask ent = new EntityTmBacklogTask();
        ent.setId(id);
        EntityManager.select(ent);
        String fromDate = ent.getStartDate();
        String fromTime = ent.getStartTime();
        
        double d = getDateDiffInHours(fromDate, fromTime);
        String dt = String.valueOf(d);
        
        try {
            ent.setCompletedDuration(String.valueOf(Double.valueOf(ent.getCompletedDuration()) + d));
            ent.setSpentHours(String.valueOf(Double.valueOf(ent.getSpentHours()) + d));
            addSpentHours(ent.getFkBacklogId(), dt);
            
        } catch (Exception e) {
            
        }
        ent.setStartDate(QDate.getCurrentDate());
        ent.setStartTime(QDate.getCurrentTime());
        ent.setStartType("S");
        EntityManager.update(ent);
        
        return carrier;
    }
    
    public static Carrier getDateDiffInHours(Carrier carrier) throws QException {
        String fromDate = carrier.getValueAsString("fromDate");
        String fromTime = carrier.getValueAsString("fromTime");
        
        double d = getDateDiffInHours(fromDate, fromTime);
        
        carrier.setValue("diff", d);
        return carrier;
    }
    
    public static double getDateDiffInHours(String fromDate, String fromTime) throws QException {
        if (fromDate.length() == 0 || fromTime.length() == 0) {
            return 0;
        }
        
        String toDate = QDate.getCurrentDate();
        String toTime = QDate.getCurrentTime();
        
        Date fromD = QDate.convertStringToDate(fromDate, fromTime);
        Date toD = QDate.convertStringToDate(toDate, toTime);
        
        double d = QDate.getDifferenceInMinutes(fromD, toD);
        d = d / 60;
        DecimalFormat df = new DecimalFormat("#.##");
        double dd = Double.parseDouble(df.format(d));
        return dd;
    }
    
    public Carrier getBacklogListWithInputs(Carrier carrier) throws QException {
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setIsSourced("1");
        ent.setInputCount(CoreLabel.GT + '0');
        ent.addSortBy(EntityTmBacklog.BACKLOG_NAME);
        ent.setSortByAsc(true);
        carrier = EntityManager.select(ent);
        carrier.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return carrier;
    }
    
    public Carrier addTestCaseLink(Carrier carrier) throws QException {
        EntityTmTestScenario ent = new EntityTmTestScenario();
        ent.setId(carrier.getValueAsString("fkTestCaseId"));
        EntityManager.select(ent);
        
        if (!ent.getLinkId().contains(carrier.getValueAsString("fkBacklogId"))) {
            String linkId = ent.getLinkId() + CoreLabel.IN
                    + carrier.getValueAsString("fkBacklogId");
            ent.setLinkId(linkId);
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier loadLinkedTestCases(Carrier carrier) throws QException {
        EntityTmTestScenario ent = new EntityTmTestScenario();
        ent.setId(carrier.getValueAsString("fkTestCaseId"));
        EntityManager.select(ent);
        
        if (ent.getLinkId().trim().length() == 0) {
            return carrier;
        }
        
        EntityTmBacklog entB = new EntityTmBacklog();
        entB.setId(ent.getLinkId());
        Carrier c = EntityManager.select(entB);
        c.renameTableName(entB.toTableName(), CoreLabel.RESULT_SET);
        return c;
    }
    
    public Carrier deleteTestCaseLink(Carrier carrier) throws QException {
        EntityTmTestScenario ent = new EntityTmTestScenario();
        ent.setId(carrier.getValueAsString("fkTestCaseId"));
        EntityManager.select(ent);
        
        if (ent.getLinkId().contains(carrier.getValueAsString("fkBacklogId"))) {
            String linkId = ent.getLinkId()
                    .replaceAll(carrier.getValueAsString("fkBacklogId"), "");
            ent.setLinkId(linkId);
            EntityManager.update(ent);
        }
        
        return carrier;
    }
    
    public Carrier setStatusOngoing4Comment(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValueAsString("id"));
        EntityManager.select(ent);
        
        ent.setCommentStatus("ongoing");
        EntityManager.update(ent);
        
        setBacklogTaskStatus(ent.getFkTaskId());
        setBacklogStatus(ent.getFkBacklogId());
        return carrier;
    }
    
    public Carrier setStatusNew4Comment(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValueAsString("id"));
        EntityManager.select(ent);
        
        ent.setCommentStatus("new");
        EntityManager.update(ent);
        
        setBacklogTaskStatus(ent.getFkTaskId());
        setBacklogStatus(ent.getFkBacklogId());
        return carrier;
    }
    
    public Carrier setEstimatedHours4Comment(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValueAsString("id"));
        EntityManager.select(ent);
        
        String eh = carrier.getValueAsString("estimatedHours");
        
        String ehOld = ent.getEstimatedHours().length() > 0
                ? ent.getEstimatedHours() : "0";
        String ehRes = String.valueOf(Double.valueOf(eh) - Double.valueOf(ehOld));
        
        eh = (Double.valueOf(eh) <= 0) ? "0" : eh;
        ent.setEstimatedHours(eh);
        EntityManager.update(ent);
        
        Carrier c = new Carrier();
        c.setValue("id", ent.getFkTaskId());
        c.setValue("estimatedHours", ehRes);
        updateBacklogTask4Comment(c);
        
        return carrier;
    }
    
    public static Carrier updateBacklogTask4Comment(Carrier carrier) throws QException {
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        
        String eh = carrier.getValueAsString(EntityTmBacklogTask.ESTIMATED_HOURS);
        String ehOld = entity.getEstimatedHours().length() > 0
                ? entity.getEstimatedHours() : "0";
        String ehRes = String.valueOf(Double.valueOf(eh) + Double.valueOf(ehOld));
        double ehf = 0;
        try {
            ehf = Float.valueOf(ehRes);
            ehf = ehf < 0 ? 0 : ehf;
            DecimalFormat df = new DecimalFormat("#.##");
            ehf = Double.parseDouble(df.format(ehf));
        } catch (Exception e) {
            
        }
        
        entity.setEstimatedHours(String.valueOf(ehf));
        EntityManager.update(entity);
        
        try {
            addEstimatedHours(entity.getFkBacklogId(), eh);
        } catch (Exception e) {
        }
        return carrier;
    }
    
    public Carrier setSpentHours4Comment(Carrier carrier) throws QException {
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(carrier.getValueAsString("id"));
        EntityManager.select(ent);
        
        String sh = carrier.getValueAsString("spentHours");
        
        String shOld = ent.getSpentHours().length() > 0
                ? ent.getSpentHours() : "0";
        String shRes = String.valueOf(Double.valueOf(sh) - Double.valueOf(shOld));
        
        sh = (Double.valueOf(sh) <= 0) ? "0" : sh;
        ent.setSpentHours(sh);
        ent.setCommentStatus("closed");
        EntityManager.update(ent);
        
        Carrier c = new Carrier();
        c.setValue("id", ent.getFkTaskId());
        c.setValue("spentHours", shRes);
        updateBacklogTask4CommentClose(c);
        
        setBacklogTaskStatus(ent.getFkTaskId());
        setBacklogStatus(ent.getFkBacklogId());
        
        return carrier;
    }
    
    public static Carrier updateBacklogTask4CommentClose(Carrier carrier) throws QException {
        
        EntityTmBacklogTask entity = new EntityTmBacklogTask();
        entity.setId(carrier.getValue(EntityTmBacklogTask.ID).toString());
        EntityManager.select(entity);
        
        String sh = carrier.getValueAsString(EntityTmBacklogTask.SPENT_HOURS);
        String shOld = entity.getCompletedDuration().length() > 0
                ? entity.getCompletedDuration() : "0";
        String shRes = String.valueOf(Double.valueOf(sh) + Double.valueOf(shOld));
        double shf = 0;
        try {
            shf = Float.valueOf(shRes);
            shf = shf < 0 ? 0 : shf;
            DecimalFormat df = new DecimalFormat("#.##");
            shf = Double.parseDouble(df.format(shf));
        } catch (Exception e) {
            
        }
        
        entity.setCompletedDuration(String.valueOf(shf));
        EntityManager.update(entity);
        
        try {
            addSpentHours(entity.getFkBacklogId(), sh);
        } catch (Exception e) {
        }
        return carrier;
    }
    
    public Carrier getBacklogHistoryByDate(Carrier carrier) throws QException {
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        
        if (sdate.trim().length() > 0 && edate.trim().length() > 0
                && (QDate.convertStringToDate(sdate).after(QDate.convertStringToDate(edate)))) {
            return carrier;
        }
        
        Carrier cout = getBacklogHistory4InputInsert(carrier);
        getBacklogHistory4InputDescriptionInsert(carrier).copyTo(cout);
        getBacklogHistory4ProcessDescriptionUpdate(carrier).copyTo(cout);
        getBacklogHistory4InputUpdate(carrier).copyTo(cout);
        getBacklogHistory4InputDelete(carrier).copyTo(cout);
        getBacklogHistory4InputDescriptionDelete(carrier).copyTo(cout);
        getBacklogHistory4InputDescriptionUpdate(carrier).copyTo(cout);
        return cout;
    }
    
    private Carrier getBacklogHistory4InputDescriptionUpdate(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_UPDATE);
////        ent.setHistoryDate(dateCombination(sdate, edate));
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent);
//        cout.renameTableName(ent.toTableName(), "inputDescriptionUpdateTable");
//
//        checkTimes(cout, "inputDescriptionUpdateTable", stime, etime);
        String dateClause = dateCombination(sdate, stime, edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_UPDATE, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputDescriptionUpdateTable");
        
        return cout;
    }
    
    private Carrier getBacklogHistory4InputDescriptionDelete(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }
//
//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE);
////        ent.setHistoryDate(dateCombination(sdate, edate));
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent);
//        cout.renameTableName(ent.toTableName(), "inputDescriptionDeleteTable");
//
//        checkTimes(cout, "inputDescriptionDeleteTable", stime, etime);
        String dateClause = dateCombination(sdate, stime, edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputDescriptionDeleteTable");
        return cout;
    }
    
    private String getClouse() {
        return "str_to_date(concat(substring(history_date,7,2),'/',"
                + "substring(history_date,5,2),'/',substring(history_date,1,4),' ',"
                + " substring(history_time,1,2),':',"
                + "substring(history_time,3,2),':',substring(history_time,5,2))"
                + ",'%d/%m/%Y %T' )";
    }
    
    private String getBacklogHistoryQuery(String backlogId, String historyType, String dateClause) {
        String q = "select * from ";
        q += SessionManager.getCurrentDomain() + ".tm_backlog_history";
        q += " where ";
        q += " fk_backlog_id='" + backlogId + "'";
        q += " and status='A' ";
        q += " and history_type='" + historyType + "'";
        q += " and " + dateClause;
        q += " order by history_date desc,history_time desc ";
        System.out.println("q=" + q);
        return q;
    }
    
    private Carrier execBacklogHistory(String backlogId, String historyType, String dateClause) throws QException {
        return EntityManager.selectBySql(getBacklogHistoryQuery(backlogId, historyType, dateClause));
    }
    
    private Carrier getBacklogHistory4InputInsert(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

        //get deleted userstory before the sstart date and time
        String deletedId = "";
        if (sdate.trim().length() > 0) {
            String dateClause = dateCombination4Delete(sdate, stime);
            Carrier c1 = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DELETE, dateClause);
            deletedId = c1.getValueLine(CoreLabel.RESULT_SET, EntityTmBacklogHistory.RELATION_ID);
        }
        
        String dateClause = dateCombination("", "", edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_NEW, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputNewTable");

        //remove deleted USer stories from the list
//        int rc = cout.getTableRowCount("inputNewTable");
        String tn = "inputNewTable";
        for (int i = 0; i < cout.getTableRowCount("inputNewTable"); i++) {
            if (deletedId.contains(cout.getValue(tn, i, EntityTmBacklogHistory.RELATION_ID).toString())) {
                cout.removeRow(tn, i);
                i--;
            }
        }
        
        if (cout.getTableRowCount(tn) == 0) {
            EntityTmInput entIn = new EntityTmInput();
            entIn.setFkBacklogId(backlogId);
            Carrier c1 = EntityManager.select(entIn);
            c1.renameTableName(entIn.toTableName(), tn);
            c1.copyTableColumn(tn, "id", "relationId");
            c1.copyTableColumn(tn, EntityTmInput.INPUT_NAME, "param1");
            c1.copyTableColumn(tn, EntityTmInput.INPUT_TYPE, "param2");
            c1.copyTableColumn(tn, EntityTmInput.TABLE_NAME, "param3");
            c1.copyTableColumn(tn, EntityTmInput.INSERT_DATE, "historyDate");
            c1.copyTableColumn(tn, EntityTmInput.INSERT_DATE, "historyTime");
            c1.copyTo(cout);
        }
        
        return cout;
    }
    
    private String mapDateToSQLDate(String date, String time) {
        if (date.trim().length() == 0) {
            return "";
        }
        String res = date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
        res += time.trim().length() == 0
                ? " 23:59:00"
                : " " + time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
        return res;
    }
    
    private String mapDateToSQLDate4Zero(String date, String time) {
        if (date.trim().length() == 0) {
            return "";
        }
        String res = date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
        res += time.trim().length() == 0
                ? " 00:00:00"
                : " " + time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
        return res;
    }
    
    private String dateCombination(String startDate, String startTime, String endDate, String endTime) throws QException {
        String res = "";
        if (startDate.trim().length() > 0 && endDate.trim().length() > 0) {
            res = getClouse() + " " + CoreLabel.COMMAND_GE
                    + "str_to_date('" + mapDateToSQLDate4Zero(startDate, startTime) + "','%d/%m/%Y %T' ) " + " AND  "
                    + getClouse() + " " + CoreLabel.COMMAND_LE
                    + "str_to_date('" + mapDateToSQLDate(endDate, endTime) + "','%d/%m/%Y %T' ) ";
        } else if (startDate.trim().length() == 0 && endDate.trim().length() > 0) {
            res = getClouse() + " " + CoreLabel.COMMAND_LE
                    + "str_to_date('" + mapDateToSQLDate(endDate, endTime) + "','%d/%m/%Y %T' ) ";
        } else if (startDate.trim().length() > 0 && endDate.trim().length() == 0) {
            res = getClouse() + " " + CoreLabel.COMMAND_GE
                    + "str_to_date('" + mapDateToSQLDate4Zero(startDate, startTime) + "','%d/%m/%Y %T' ) ";
        }
        return res;
    }
    
    private String dateCombination4Delete(String startDate, String startTime) throws QException {
        String res = "";
        if (startDate.trim().length() > 0) {
            res = getClouse() + " " + CoreLabel.COMMAND_LT
                    + "str_to_date('" + mapDateToSQLDate4Zero(startDate, startTime) + "','%d/%m/%Y %T' ) ";
        }
        return res;
    }
    
    private String timeCombination(String startTime, String endTime) throws QException {
        String res = "";
        if (startTime.trim().length() > 0 && endTime.trim().length() > 0) {
            res = startTime + CoreLabel.BN + endTime;
        } else if (startTime.trim().length() == 0 && endTime.trim().length() > 0) {
            res = CoreLabel.LE + endTime;
        } else if (startTime.trim().length() > 0 && endTime.trim().length() == 0) {
            res = CoreLabel.GE + startTime;
        }
        return res;
    }
    
    private Carrier getBacklogHistory4InputUpdate(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
////        ent.setHistoryDate(dateCombination(sdate, edate));
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_UPDATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent); 
        String dateClause = dateCombination(sdate, stime, edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_UPDATE, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputUpdateTable");

//        checkTimes(cout, "inputUpdateTable", stime, etime);
        return cout;
    }
    
    private void checkTimes(Carrier carrier,
            String table, String stime, String etime) throws QException {
        
        int rc = carrier.getTableRowCount(table);
        ArrayList<Integer> list = new ArrayList<>();
        
        for (int i = 0; i < carrier.getTableRowCount(table); i++) {
            String st = carrier.getValue(table, i, "historyTime").toString();
            
            if (stime.trim().length() > 0
                    && (QDate.convertStringToTime(st).before(QDate.convertStringToTime(stime)))) {
                carrier.removeRow(table, i);
                i--;
                continue;
//                list.add(i);
            }
            
            if (etime.trim().length() > 0
                    && (QDate.convertStringToTime(st).after(QDate.convertStringToTime(etime)))) {
                carrier.removeRow(table, i);
                i--;
                continue;
//                list.add(i);
            }
        }

//        for (int j=0;j<list.size();j++){
//            carrier.removeRow(table,list.get(j));
//        }
    }
    
    private Carrier getBacklogHistory4InputDelete(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_DELETE);
////        ent.setHistoryDate(dateCombination(sdate, edate));
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent);
//        cout.renameTableName(ent.toTableName(), "inputDeleteTable");
        String dateClause = dateCombination(sdate, stime, edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DELETE, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputDeleteTable");

//        checkTimes(cout, "inputDeleteTable", stime, etime);
        return cout;
    }
    
    private Carrier getBacklogHistory4InputDescriptionInsert(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

        //get deleted userstory before the sstart date and time
        String deletedId = "";
//        if (sdate.trim().length() > 0) {
//            EntityTmBacklogHistory ent1 = new EntityTmBacklogHistory();
//            ent1.setFkBacklogId(backlogId);
//            ent1.setFkProjectId(fkProjectId);
//            ent1.setHistoryDate(CoreLabel.LT + sdate);
//            ent1.setHistoryTime(CoreLabel.LT + stime);
//            ent1.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE);
//            deletedId = EntityManager.select(ent1)
//                    .getValueLine(ent1.toTableName(), EntityTmBacklogHistory.RELATION_ID);
//        }
//        
        if (sdate.trim().length() > 0) {
            String dateClause = dateCombination4Delete(sdate, stime);
            Carrier c1 = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_DELETE, dateClause);
            deletedId = c1.getValueLine(CoreLabel.RESULT_SET, EntityTmBacklogHistory.RELATION_ID);
        }

//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
//        ent.setHistoryDate(CoreLabel.LE + edate);
//        ent.setHistoryTime(CoreLabel.LE + etime);
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_NEW);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent);
//        cout.renameTableName(ent.toTableName(), "inputDescNewTable");
        String dateClause = dateCombination("", "", edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_INPUT_DESCRIPTION_NEW, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "inputDescNewTable");

//remove deleted USer stories from the list
        int rc = cout.getTableRowCount("inputDescNewTable");
        String tn = "inputDescNewTable";
        for (int i = 0; i < rc; i++) {
            if (deletedId.contains(cout.getValue(tn, i, EntityTmBacklogHistory.RELATION_ID).toString())) {
                cout.removeRow(tn, i);
            }
        }
        
        return cout;
    }
    
    private Carrier getBacklogHistory4ProcessDescriptionUpdate(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        String fkProjectId = carrier.getValueAsString("fkProjectId");
        String sdate = carrier.getValueAsString("startDate");
        String edate = carrier.getValueAsString("endDate");
        String stime = carrier.getValueAsString("startTime");
        String etime = carrier.getValueAsString("endTime");
        if (backlogId.trim().length() == 0) {
            return new Carrier();
        }

//        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
//        ent.setFkBacklogId(backlogId);
//        ent.setFkProjectId(fkProjectId);
//        ent.setHistoryType(BACKLOG_HISTORY_TYPE_PROCESS_DESC_UPDATE);
////        ent.setHistoryDate(dateCombination(sdate, edate));
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_DATE);
//        ent.addSortBy(EntityTmBacklogHistoryList.HISTORY_TIME);
//        ent.setSortByAsc(false);
//        Carrier cout = EntityManager.select(ent);
//        cout.renameTableName(ent.toTableName(), "proccessDescNewTable");
//
//        checkTimes(cout, "proccessDescNewTable", stime, etime);
        String dateClause = dateCombination(sdate, stime, edate, etime);
        Carrier cout = execBacklogHistory(backlogId, BACKLOG_HISTORY_TYPE_PROCESS_DESC_UPDATE, dateClause);
        cout.renameTableName(CoreLabel.RESULT_SET, "proccessDescNewTable");
        
        return cout;
    }
    
    public Carrier getHistoryTimesByDate(Carrier carrier) throws QException {
        String backlogId = carrier.getValueAsString("fkBacklogId");
        if (backlogId.trim().length() == 0) {
            return carrier;
        }
        
        EntityTmBacklogHistory ent = new EntityTmBacklogHistory();
        ent.setFkBacklogId(backlogId);
        ent.setHistoryDate(carrier.getValueAsString("date"));
        ent.addSortBy(EntityTmBacklogHistory.HISTORY_TIME);
        ent.addDistinctField(EntityTmBacklogHistory.HISTORY_TIME);
        String times = EntityManager.select(ent)
                .getValueLine(ent.toTableName(), EntityTmBacklogHistory.HISTORY_TIME);
        
        carrier.setValue("historyTimes", times);
        return carrier;
    }
    
    public Carrier setLabelAsChangeRequest(Carrier carrier) throws QException {
        if (carrier.getValueAsString("fkLabelId").length() == 0) {
            EntityTmChangeReqLabel ent = new EntityTmChangeReqLabel();
            EntityManager.mapCarrierToEntity(carrier, ent);
            ent.setFkLabelId("");
            Carrier c = EntityManager.select(ent);
            
            if (c.getTableRowCount(ent.toTableName()) > 0) {
                ent.setFkLabelId("");
                EntityManager.update(ent);
            }
            return carrier;
        }
        //eger Label is not empty;
        EntityTmChangeReqLabel ent1 = new EntityTmChangeReqLabel();
        ent1.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        ent1.setFkBacklogId(carrier.getValueAsString("fkBacklogId"));
        ent1.setFkLabelId(carrier.getValueAsString("fkLabelId"));
        Carrier c1 = EntityManager.select(ent1);
        
        if (c1.getTableRowCount(ent1.toTableName()) > 0) {
            //eger evvelki datalar varsa silsin daha sonra update edilsin
            try {
                EntityTmChangeReqLabel ent = new EntityTmChangeReqLabel();
                EntityManager.mapCarrierToEntity(carrier, ent);
                ent.setFkLabelId("");
                String ids = EntityManager.select(ent).getValueLine(ent1.toTableName());
                EntityTmChangeReqLabel ent2 = new EntityTmChangeReqLabel();
                ent2.setId(ids);
                EntityManager.delete(ent2);
            } catch (Exception e) {
                
            }
            
            EntityManager.mapCarrierToEntity(carrier, ent1);
            EntityManager.update(ent1);
        } else {
            EntityTmChangeReqLabel ent = new EntityTmChangeReqLabel();
            EntityManager.mapCarrierToEntity(carrier, ent);
            ent.setFkLabelId("");
            Carrier c = EntityManager.select(ent);
            
            if (c.getTableRowCount(ent.toTableName()) > 0) {
                ent.setFkLabelId(carrier.getValueAsString("fkLabelId"));
                EntityManager.update(ent);
            } else {
                ent.setFkLabelId(carrier.getValueAsString("fkLabelId"));
                EntityManager.mapCarrierToEntity(carrier, ent);
                EntityManager.insert(ent);
            }
        }
        
        return carrier;
    }
    
    public Carrier loadAssignedLabel(Carrier carrier) throws QException {
        EntityTmChangeReqLabel ent1 = new EntityTmChangeReqLabel();
        ent1.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        ent1.setFkBacklogId(carrier.getValueAsString("fkBacklogId"));
        Carrier c1 = EntityManager.select(ent1);
        
        String ids = c1.getValueLine(ent1.toTableName(), "fkLabelId");
        
        Carrier c = new Carrier();
        EntityTmTaskLabel ent = new EntityTmTaskLabel();
        ent.addSortBy(EntityTmTaskLabel.NAME);
        
        if (ids.length() > 0) {
            ent.setId(ids);
            c = EntityManager.select(ent);
        }
        
        c1.mergeCarrier(ent1.toTableName(), new String[]{"fkLabelId"}, c, ent.toTableName(), new String[]{"id"}, new String[]{"name"}, " ", false);
        
        return c1;
    }
    
    public Carrier loadAssignedLabelByProject(Carrier carrier) throws QException {
        EntityTmChangeReqLabel ent1 = new EntityTmChangeReqLabel();
        ent1.setFkProjectId(carrier.getValueAsString("fkProjectId"));
        Carrier c1 = EntityManager.select(ent1);
        
        String ids = c1.getValueLine(ent1.toTableName(), "fkLabelId");
        
        Carrier c = new Carrier();
        EntityTmTaskLabel ent = new EntityTmTaskLabel();
        ent.addSortBy(EntityTmTaskLabel.NAME);
        
        if (ids.length() > 0) {
            ent.setId(ids);
            c = EntityManager.select(ent);
        }
        
        c1.mergeCarrier(ent1.toTableName(), new String[]{"fkLabelId"}, c, ent.toTableName(), new String[]{"id"}, new String[]{"name"}, " ", false);
        
        return c1;
    }
    
    public Carrier getAssignedLabelById(Carrier carrier) throws QException {
        EntityTmChangeReqLabel ent = new EntityTmChangeReqLabel();
        ent.setId(carrier.getValueAsString("id"));
        carrier = EntityManager.select(ent);
        
        return carrier;
    }
    
    public Carrier getAssignedLabelByDates(Carrier carrier) throws QException {
        EntityTmChangeReqLabel ent = new EntityTmChangeReqLabel();
        EntityManager.mapCarrierToEntity(carrier, ent);
        carrier = EntityManager.select(ent);
        return carrier;
    }
    
    public Carrier notifyLabelAsChangeRequest(Carrier carrier) throws QException {
        String labelId = carrier.getValueAsString("fkLabelId");
        String taskId[] = carrier.getValueAsString("fkAssigneeId").split(CoreLabel.IN);
        
        EntityTmChangeReqLabel entLbl = new EntityTmChangeReqLabel();
        entLbl.setId(labelId);
        EntityManager.select(entLbl);
        
        if (entLbl.getFkLabelId().length() == 0) {
            return carrier;
        }
        
        EntityTmTaskLabel entLabel = new EntityTmTaskLabel();
        entLabel.setId(entLbl.getFkLabelId());
        EntityManager.select(entLabel);
        
        for (String id : taskId) {
            
            EntityTmBacklogTask entBT = new EntityTmBacklogTask();
            entBT.setId(id);
            carrier = EntityManager.select(entBT);
            entBT.setTaskStatus("ongoing");
            EntityManager.update(entBT);
            
            EntityTmTaskComment entComment = new EntityTmTaskComment();
            entComment.setFkBacklogId(entBT.getFkBacklogId());
            entComment.setFkTaskId(entBT.getId());
            entComment.setFkUserId(SessionManager.getCurrentUserId());
            entComment.setCommentDate(QDate.getCurrentDate());
            entComment.setCommentTime(QDate.getCurrentTime());
            entComment.setIsRequest("1");
            entComment.setCommentStatus("new");
            entComment.setIsNotifiedRequest(labelId);
            entComment.setEstimatedHours("0");
            entComment.setSpentHours("0");
            entComment.setComment("Change Request for Assigned Label:"
                    + "<a href='#' onclick=\"new UserStory().loadAssignedLabelData('" + labelId + "')\"> "
                    + entLabel.getName() + "</a>");
            EntityManager.insert(entComment);
            
            addChangeRequestToJira(entComment.getId(), entComment.getFkBacklogId());
        }
        
        return carrier;
    }
    
    private Date getLastDateOfFilter(String lastDayType) {
        Calendar c = Calendar.getInstance();
        if (lastDayType.equals("week")) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            c.add(Calendar.DAY_OF_MONTH, 7);
        } else if (lastDayType.equals("month")) {
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.add(Calendar.DATE, -1);
            
        } else if (lastDayType.equals("year")) {
            c.add(Calendar.YEAR, 1);
            c.set(Calendar.DAY_OF_YEAR, 1);
            c.add(Calendar.DATE, -1);
            
        }
        return c.getTime();
    }
    
    private Date getNextPeriodDate(Date lastDate, int period, String dayType, String periodType) {
        if (dayType.equals("week")) {
            if (periodType.equals("future")) {
                return QDate.addDay(lastDate, (period) * (7));
            } else if (periodType.equals("last")) {
                return QDate.addDay(lastDate, (period) * (-7));
            }
        } else if (dayType.equals("month")) {
            if (periodType.equals("future")) {
                return QDate.addMonth(lastDate, (period) * (1));
            } else if (periodType.equals("last")) {
                return QDate.addMonth(lastDate, (period) * (-1));
            }
        } else if (dayType.equals("year")) {
            if (periodType.equals("future")) {
                return QDate.addYear(lastDate, (period) * (1));
            } else if (periodType.equals("last")) {
                return QDate.addYear(lastDate, (period) * (-1));
            }
        }
        return lastDate;
    }
    
    private void setAnalyticalObject(Carrier carrier, Analytics an) throws QException {
        an.setProjectList(carrier.getValueAsString("projectList"));
        an.setAssigneeList(carrier.getValueAsString("assigneeList"));
        an.setLabelList(carrier.getValueAsString("labelList"));
        an.setSprintList(carrier.getValueAsString("sprintList"));
        an.setTaskTypeList(carrier.getValueAsString("taskTypeList"));
        an.setPriorityList(carrier.getValueAsString("priorityList"));
        an.setGroupBy(carrier.getValueAsString("groupBy"));
    }
    
    private String getFuturePeriondQuery(int futurePeriod, Date lastDate, Analytics an, String lastDay, Carrier carrier) throws QException {
        //for future period
        String q = "";
        for (int i = futurePeriod; i >= 1; i--) {
//            Date toDate = QDate.addDay(lastDate, (i) * 7);
            Date fromDate = getNextPeriodDate(lastDate, (i - 1), lastDay, "future");
            Date toDate = getNextPeriodDate(lastDate, i, lastDay, "future");
            an.setFromDate(QDate.convertDateToString(fromDate));
            an.setToDate(QDate.convertDateToString(toDate));
            q += an.getQuery();
            q += i > 1 ? " \n UNION \n" : "";
            
            int rc = carrier.getTableRowCount("fromToDatesTable");
            carrier.setValue("fromToDatesTable", rc, "fromDate", QDate.convertDateToString(fromDate));
            carrier.setValue("fromToDatesTable", rc, "toDate", QDate.convertDateToString(toDate));
        }
        return q;
    }
    
    private String getLastPeriondQuery(int lastPeriod, Date lastDate, Analytics an, String lastDay, Carrier carrier) throws QException {
        //for future period
        String q = "";
        for (int i = 1; i <= lastPeriod; i++) {
            
            Date toDate = getNextPeriodDate(lastDate, (i - 1), lastDay, "last");
            Date fromDate = getNextPeriodDate(lastDate, i, lastDay, "last");
            an.setFromDate(QDate.convertDateToString(fromDate));
            an.setToDate(QDate.convertDateToString(toDate));
            q += an.getQuery();
            q += i < lastPeriod ? " \n UNION \n" : "";
            
            int rc = carrier.getTableRowCount("fromToDatesTable");
            carrier.setValue("fromToDatesTable", rc, "fromDate", QDate.convertDateToString(fromDate));
            carrier.setValue("fromToDatesTable", rc, "toDate", QDate.convertDateToString(toDate));
        }
        return q;
    }
    
    public Carrier getAnalyticalReport(Carrier carrier) throws QException, Exception {
        EntityManager.executeUpdateByQuery("use " + SessionManager.getCurrentDomain() + ";");
        Carrier c = new Carrier();
        
        if (!(carrier.getValueAsString("lastPeriod").length() > 0 && carrier.getValueAsString("lastDay").length() > 0)) {
            return carrier;
        }
        
        int lastPeriod = Integer.parseInt(carrier.getValue("lastPeriod").toString());
        int futurePeriod = Integer.parseInt(carrier.getValue("futurePeriod").toString());
        String lastDay = carrier.getValueAsString("lastDay");
        Date lastDate = getLastDateOfFilter(lastDay);
        Analytics an = new Analytics();
        setAnalyticalObject(carrier, an);
        
        String q = getFuturePeriondQuery(futurePeriod, lastDate, an, lastDay, c);
        String q1 = getLastPeriondQuery(lastPeriod, lastDate, an, lastDay, c);
        
        String fq = (q.length() > 0 && q1.length() > 0) ? q + " \n \n Union \n \n" + q1 : q + " " + q1;

//    System.out.println(fq);
        carrier = EntityManager.selectBySql(fq);
        c.copyTo(carrier);
        return carrier;
    }
    
    public static Carrier createEpic(Carrier carrier) throws QException {
        try {
            EntityTmBacklog ent = new EntityTmBacklog();
            ent.setId(carrier.getValueAsString("fkBacklogId"));
            EntityManager.select(ent);
            
            EntityTmProject entPr = new EntityTmProject();
            if (ent.getFkProjectId().length() > 0) {
                entPr.setId(ent.getFkProjectId());
                EntityManager.select(entPr);
            }
            
            if (entPr.getProjectCode().length() > 0) {
                Carrier c = new Carrier();
                c.setValue("project", entPr.getProjectCode());
                c.setValue("summary", "US (" + ent.getOrderNo() + "): " + ent.getBacklogName());
                c.setValue("issueType", "Epic");
                c.set("backlogName", ent.getBacklogName());
                c.set("fkBacklogId", ent.getId());
                c.set("fkProjectId", ent.getFkProjectId());
                
                carrier = addJiraIntegration(c);
                try {
                    JSONObject jsonObject = new JSONObject(carrier.getValueAsString("response"));
                    ent.setJiraId(jsonObject.getString("id"));
                    ent.setJiraKey(jsonObject.getString("key"));
                    EntityManager.update(ent);
                    
                    carrier.setValue("jiraId", ent.getJiraId());
                    carrier.setValue("jiraKey", ent.getJiraKey());
                } catch (Exception err) {
                    carrier.setValue("msg", "There were some errors in adding Epic!");
                }
            } else {
                carrier.setValue("msg", "Project Code is empty.");
                return carrier;
            }
            carrier.setValue("msg", "Epic successfully added!");
            return carrier;
        } catch (Exception e) {
            carrier.setValue("msg", "There were some errors in adding Epic!");
            return carrier;
        }
    }
    
    public static Carrier createTaskInJira(Carrier carrier) throws QException {
        try {
            String epicKey = carrier.getValueAsString("epicJiraKey");
            String id = carrier.getValueAsString("id");
            EntityTmBacklogTaskList ent = new EntityTmBacklogTaskList();
            ent.setId(id);
            EntityManager.select(ent);
            
            EntityTmProject entPr = new EntityTmProject();
            if (ent.getFkProjectId().length() > 0) {
                entPr.setId(ent.getFkProjectId());
                EntityManager.select(entPr);
            }
            
            EntityTmBacklog entBl = new EntityTmBacklog();
            if (ent.getFkBacklogId().length() > 0) {
                entBl.setId(ent.getFkBacklogId());
                EntityManager.select(entBl);
            }
            
            if (entPr.getProjectCode().length() > 0) {
                Carrier c = new Carrier();
                c.setValue("project", entPr.getProjectCode());
                c.setValue("summary", ent.getAssigneeName() + " (" + entBl.getBacklogName() + ")");
                c.setValue("issueType", "Task");
                c.setValue("estimatedHours", ent.getEstimatedHours());
                c.setValue("fkBacklogId", ent.getFkBacklogId());
                c.setValue("backlogName", carrier.getValue("backlogName"));
                c.setValue("fkProjectId", entPr.getId());
                c.set("taskTypeName", ent.getTaskTypeName());
                
                carrier = addJiraIntegration(c);
                
                try {
                    JSONObject jsonObject = new JSONObject(carrier.getValueAsString("response"));
                    
                    EntityTmBacklogTask ent1 = new EntityTmBacklogTask();
                    ent1.setId(id);
                    EntityManager.select(ent1);
                    
                    ent1.setJiraIssueId(jsonObject.getString("id"));
                    ent1.setJiraIssueKey(jsonObject.getString("key"));
                    EntityManager.update(ent1);
                    
                    addJiraAssigneIntegration(ent1.getJiraIssueId(), ent1.getFkAssigneeId());
                    if (epicKey.length() > 0) {
                        addEpicToTaskInJira(ent1.getJiraIssueKey(), epicKey);
                    }
                } catch (Exception err) {
                    carrier.setValue("msg", "There were some errors in adding Epic!");
                }
            }
            
            return carrier;
        } catch (Exception e) {
            
            return carrier;
        }
    }
    
    private static void getTaskCommentInfo4CreateBugInJira(Carrier cr) throws QException {
        
        if (cr.get("commentId").length() == 0) {
            return;
        }
        EntityTmTaskComment ent = new EntityTmTaskComment();
        ent.setId(cr.get("commentId"));
        EntityManager.select(ent);
        cr.set("fkTaskId", ent.getFkTaskId());
        cr.set("fkCommentId", ent.getId());
        cr.set("estimatedHour", ent.getEstimatedHours());
        cr.set("label", ent.getIsNotifiedRequest());
        
    }
    
    private static void getProjectCodeInfo4CreateBugInJira(Carrier cr) throws QException {
        
        if (cr.get("fkProjectId").length() == 0) {
            return;
        }
        EntityTmProject ent = new EntityTmProject();
        ent.setId(cr.get("fkProjectId"));
        EntityManager.select(ent);
        cr.set("projectCode", ent.getProjectCode());
    }
    
    private static void getTaskInfo4CreateBugInJira(Carrier cr) throws QException {
        if (cr.get("fkTaskId").length() == 0) {
            return;
        }
        EntityTmBacklogTaskList entBT = new EntityTmBacklogTaskList();
        entBT.setId(cr.get("fkTaskId"));
        EntityManager.select(entBT);
        cr.set("taskJiraId", entBT.getJiraIssueId());
        cr.set("taskJiraKey", entBT.getJiraIssueKey());
        cr.set("taskTypeName", entBT.getTaskTypeName());
        cr.set("fkAssigneeId", entBT.getFkAssigneeId());
    }
    
    private static void getAssigneInfo4CreateBugInJira(Carrier cr) throws QException {
        if (cr.get("fkAssigneeId").length() == 0) {
            return;
        }
        EntityCrUser entUser = new EntityCrUser();
        entUser.setId(cr.get("fkAssigneeId"));
        EntityManager.select(entUser);
        cr.set("assigneeName", entUser.getUserPersonName());
        
    }
    
    public static Carrier createChangeRequestInJira(Carrier cr) throws QException {
        try {
            getTaskCommentInfo4CreateBugInJira(cr);
            getTaskInfo4CreateBugInJira(cr);
            getAssigneInfo4CreateBugInJira(cr);
            getProjectCodeInfo4CreateBugInJira(cr);
            
            if (cr.get("projectCode").length() > 0) {
                addJiraIntegration4ChangeRequest(cr);
                updateCommentJiraIdAndKeyInfos(cr);
            }
            
            return cr;
        } catch (Exception e) {
            
            return cr;
        }
    }
    
    public static Carrier createBugInJira(Carrier cr) throws QException {
        try {
            getTaskCommentInfo4CreateBugInJira(cr);
            getTaskInfo4CreateBugInJira(cr);
            getAssigneInfo4CreateBugInJira(cr);
            getProjectCodeInfo4CreateBugInJira(cr);
            
            if (cr.get("projectCode").length() > 0) {
                Carrier c = new Carrier();
                c.setValue("project", cr.get("projectCode"));
                c.setValue("assigneeJiraId", cr.get("fkAssigneeId"));
                c.setValue("summary", "BUG: " + cr.get("assigneeName") + " (" + cr.get("backlogName") + ")");
                c.setValue("issueType", "Bug");
                c.setValue("estimatedHours", cr.get("estimatedHours"));
                //US View-nu hazirlamaq uchun
                c.setValue("fkBacklogId", cr.get("fkBacklogId"));
                c.setValue("backlogName", cr.get("backlogName"));
                c.setValue("fkProjectId", cr.get("fkProjectId"));
                c.setValue("taskJiraKey", cr.get("taskJiraKey"));
                c.setValue("taskTypeName", cr.get("taskTypeName"));
                
                c = addJiraIntegration4Bug(c);
                c.copyTo(cr);
                updateCommentJiraIdAndKeyInfos(cr);
                
            }
            
            return cr;
        } catch (Exception e) {
            
            return cr;
        }
    }
    
    private static void updateCommentJiraIdAndKeyInfos(Carrier cr) {
        try {
            JSONObject jsonObject = new JSONObject(cr.getValueAsString("response"));
            if (cr.get("commentId").length() == 0) {
                return;
            }
            EntityTmTaskComment ent = new EntityTmTaskComment();
            ent.setId(cr.get("commentId"));
            EntityManager.select(ent);
            
            if (ent.getId().length() > 0) {
                ent.setCommentJiraId(jsonObject.getString("id"));
                ent.setCommentJiraKey(jsonObject.getString("key"));
                EntityManager.update(ent);
                
                addJiraAssigneIntegration(ent.getCommentJiraId(), cr.get("fkAssigneeId"));
                addJiraCommentIntegration(cr.get("commentId"), ent.getCommentJiraId(), ent.getComment());
                if (cr.get("epicJiraKey").length() > 0) {
                    addEpicToTaskInJira(ent.getCommentJiraKey(), cr.get("epicJiraKey"));
                }
            }
            
        } catch (Exception err) {
        }
    }
    
    private static String getJiraCommentBodyFileList(String commentId) throws QException {
        String st = "";
        if (commentId.length() > 0) {
            EntityTmCommentFile ent = new EntityTmCommentFile();
            ent.setFkCommentId(commentId);
            Carrier c = EntityManager.select(ent);
            
            String tn = ent.toTableName();
            int rc = c.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityManager.mapCarrierToEntity(c, tn, i, ent);
                st += "[" + ent.getFileName().replaceAll("\"", "'") + "|" + Config.getProperty("base_dw_url") + ent.getFileName().replaceAll("\"", "'") + "] \\n";
            }
            if (st.length() > 0) {
                st = "*ATTACHMENT(S):*\\n\\n" + st;
            }
        }
        return st;
    }
    
    private static String getJiraCommentBody(String commentId, String comment) throws QException {
        
        String st = "";
        String[] cer = comment.split("\\r?\\n");
        for (String cer1 : cer) {
            st += "" + cer1.replaceAll("\"", "'") + "\\n ";
        }
        st += "\\n" + getJiraCommentBodyFileList(commentId);
        return "{\"body\":\"" + st + "\"}";
    }
    
    public static Carrier addJiraCommentIntegration(String commentId, String issueId, String comment) throws QException, IOException {
        try {
            if (comment.trim().length() == 0 || issueId.trim().length() == 0) {
                return new Carrier();
            }
            
            HttpURLConnection conn = getJiraIntegrationCore("POST", "/" + issueId.replaceAll("\"", "'") + "/comment");
            String input = getJiraCommentBody(commentId, comment);
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            conn.getResponseCode();
            conn.getResponseMessage();
            Carrier carrier = new Carrier();
            carrier.setValue("response", getBufferResult(conn));
            return carrier;
        } catch (Exception e) {
            return new Carrier();
        }
    }
    
    private static String getBufferResult(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output = "";
        System.out.println("Output from Server .... \n");
        String rline = "";
        while ((output = br.readLine()) != null) {
            rline = rline + output;
        }
        return rline;
    }
    
    public static void addJiraAssigneIntegration(String issueId, String userId) throws QException, IOException {
        try {
            if (userId.trim().length() == 0 || issueId.trim().length() == 0) {
                return;
            }
            
            EntityCrUser entUser = new EntityCrUser();
            entUser.setId(userId);
            EntityManager.select(entUser);
            
            if (entUser.getTgUserId().trim().length() == 0) {
                return;
            }
            HttpURLConnection conn = getJiraIntegrationCore("PUT", "/" + issueId + "/assignee");
            
            String input = "{\"accountId\":\"" + entUser.getTgUserId() + "\"}";
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            conn.getResponseCode();
            conn.getResponseMessage();
            
            return;
        } catch (Exception e) {
            return;
        }
    }
    
    public static HttpURLConnection getJiraIntegrationCore(String intType, String ext2Issue) throws QException, IOException {
        return getJiraIntegrationCoreDefault(intType, "/issue" + ext2Issue);
    }
    
    public static HttpURLConnection getJiraIntegrationCoreDefault(String intType, String issue) throws QException, IOException {
        EntityTmJiraIntegration ent = new EntityTmJiraIntegration();
        ent.setStartLimit(0);
        ent.setEndLimit(1);
        Carrier c = EntityManager.select(ent);
        if (c.getTableRowCount(ent.toTableName()) == 0) {
            return null;
        }
        
        String url1 = "https://" + ent.getAtlassionId() + ".atlassian.net/rest/api/"
                + ent.getVersion() + issue;
//             System.out.println("url1 - >"+url1);
        HttpURLConnection conn = null;
        String userpass = ent.getUsername() + ":" + ent.getPassword();
        String basicAuth = "Basic " + new String(Base64.getEncoder()
                .encode(userpass.getBytes()));
        
        URL url = new URL(url1);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", basicAuth);
        conn.setDoOutput(true);
        conn.setRequestMethod(intType);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }
    
    public static void addEpicToTaskInJira(String taskId, String epicId) throws QException, IOException {
        if (taskId.length() == 0 || epicId.length() == 0) {
            return;
        }
        
        HttpURLConnection conn = getJiraIntegrationCore("PUT", "/" + taskId);
        
        String input = "{\n"
                + "   \"fields\": \n"
                + "    {\n"
                + "       \"parent\": \n"
                + "        {\n"
                + "              \"key\": \"" + epicId.replaceAll("\"", "'") + "\" \n"
                + "         }\n"
                + "    }\n"
                + "}";
        
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        System.out.println("respcode=" + conn.getResponseCode());
        System.out.println("respmessage=" + conn.getResponseMessage());
        
    }
    
    public static Carrier addJiraIntegration4ChangeRequest(Carrier carrier) throws QException, IOException {
        
        HttpURLConnection conn = getJiraIntegrationCore("POST", "");
        String input = getJiraIssueText4ChangeRequest(carrier);
        
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        conn.getResponseCode();
        conn.getResponseMessage();
        
        carrier.setValue("response", getBugIntegrationLine(conn));
        return carrier;
    }
    
    public static Carrier addJiraIntegration4Bug(Carrier carrier) throws QException, IOException {
        
        HttpURLConnection conn = getJiraIntegrationCore("POST", "");
        String input = getJiraIssueText4Bug(carrier);
        
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        conn.getResponseCode();
        conn.getResponseMessage();
        
        carrier.setValue("response", getBugIntegrationLine(conn));
        return carrier;
    }
    
    public static Carrier addJiraIntegration(Carrier carrier) throws QException, IOException {
        
        HttpURLConnection conn = getJiraIntegrationCore("POST", "");
        String input = getJiraIssueText(carrier);
//        System.out.println("addJiraIntegration->" + input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        conn.getResponseCode();
        conn.getResponseMessage();
        
        carrier.setValue("response", getBugIntegrationLine(conn));
        return carrier;
    }
    
    private static String getBugIntegrationLine(HttpURLConnection conn) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output = "";
        System.out.println("Output from Server .... \n");
        String rline = "";
        while ((output = br.readLine()) != null) {
            rline = rline + output;
        }
        return rline;
    }
    
    public static Carrier addBugIntegration(Carrier carrier) throws QException, IOException {
        
        HttpURLConnection conn = getJiraIntegrationCore("POST", "");
        String input = getJiraIssueText(carrier);
        
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        
        conn.getResponseCode();
        conn.getResponseMessage();
        
        carrier.setValue("response", getConnectionOutputLines(conn));
        return carrier;
    }
    
    public static String getConnectionOutputLines(HttpURLConnection conn) throws QException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output = "";
//        System.out.println("Output from Server .... \n");
        String rline = "";
        while ((output = br.readLine()) != null) {
            rline = rline + output;
        }
        return rline;
    }
    
    private static String getJiraDescription4Task(String pid, String bid, String lbl) {
        String burl = Config.getProperty("base_url");
        String domain = SessionManager.getCurrentDomain();
        domain = (domain.startsWith("apd_")) ? domain.substring(4, domain.length()) : domain;
        
        return burl + "?pid=" + pid + "&bid=" + bid + "&d=" + domain
                + "&lbl=" + lbl;
    }
    
    private static String getJiraIssueText4ChangeRequest(Carrier cr) throws QException {
        
        String summary = "Change Req: " + cr.get("assigneeName") + " (" + cr.get("backlogName").replaceAll("\"", "'") + ")";
        String bid = cr.get("fkBacklogId");
        String pid = cr.get("fkProjectId");
        String lid = cr.get("label");
        
        String url = getJiraDescription4Task(pid, bid, lid);
        
        String st = "{\"fields\": {\n"
                + "        \"summary\": \"" + summary.replaceAll("\"", "'") + "\",\n"
                + "        \"issuetype\": {\n"
                + "            \"name\": \"" + "Task" + "\"\n"
                + "        },\n"
                + "        \"description\":  \" Related User Story: [" + cr.get("backlogName").replaceAll("\"", "'") + " |" + url + "]\","
                + "        \"project\": {\n"
                + "            \"key\": \"" + cr.get("projectCode").replaceAll("\"", "'") + "\"\n"
                + "        }, \n"
                + "        \"labels\": [\"" + cr.get("taskTypeName").replaceAll(" ", "") + "\"]"
                + "    },\n"
                + "\"timetracking\": { \"originalEstimate\": \"" + cr.get("estimatedHours").replaceAll("\"", "'") + "h\", \"remainingEstimate\": \"\" } ";
        
        st += cr.get("taskJiraKey").length() > 0 ? ",\"update\":{\n"
                + "      \"issuelinks\":[\n"
                + "         {\n"
                + "            \"add\":{\n"
                + "               \"type\":{\n"
                + "                  \"name\":\"Relates\",\n"
                + "                  \"inward\":\"relates to\",\n"
                + "                  \"outward\":\"relates to\"\n"
                + "               },\n"
                + "               \"outwardIssue\":{\n"
                + "                  \"key\":\"" + cr.get("taskJiraKey").replaceAll("\"", "'") + "\"\n"
                + "               }\n"
                + "            }\n"
                + "         }\n"
                + "      ]"
                + "} " : "";
        st += "}";
        return st;
    }
    
    private static String getJiraIssueText4Bug(Carrier carrier) throws QException {
        String project = carrier.getValueAsString("project");
        String summary = carrier.getValueAsString("summary");
        String issueType = carrier.getValueAsString("issueType");
        String estimatedHours = carrier.getValueAsString("estimatedHours");
        String bid = carrier.getValueAsString("fkBacklogId");
        String bname = carrier.getValueAsString("backlogName");
        String pid = carrier.getValueAsString("fkProjectId");
        String url = getJiraDescription4Task(pid, bid, "");
        
        String st = "{\"fields\": {\n"
                + "        \"summary\": \"" + summary.replaceAll("\"", "'") + "\",\n"
                + "        \"issuetype\": {\n"
                + "            \"name\": \"" + issueType.replaceAll("\"", "'") + "\"\n"
                + "        },\n"
                + "        \"description\":  \" Related User Story: [" + bname.replaceAll("\"", "'") + " |" + url.replaceAll("\"", "'") + "]\","
                + "        \"project\": {\n"
                + "            \"key\": \"" + project.replaceAll("\"", "'") + "\"\n"
                + "        } \n"
                + "         ,\"labels\": [\"" + carrier.get("taskTypeName").replaceAll(" ", "") + "\"]"
                + "    },\n"
                + "\"timetracking\": { \"originalEstimate\": \"" + estimatedHours.replaceAll("\"", "'") + "h\", \"remainingEstimate\": \"\" } ";
        
        st += carrier.get("taskJiraKey").length() > 0
                ? ",\"update\":{\n"
                + "      \"issuelinks\":[\n"
                + "         {\n"
                + "            \"add\":{\n"
                + "               \"type\":{\n"
                + "                  \"name\":\"Relates\",\n"
                + "                  \"inward\":\"relates to\",\n"
                + "                  \"outward\":\"relates to\"\n"
                + "               },\n"
                + "               \"outwardIssue\":{\n"
                + "                  \"key\":\"" + carrier.get("taskJiraKey").replaceAll("\"", "'") + "\"\n"
                + "               }\n"
                + "            }\n"
                + "         }\n"
                + "      ]"
                + "} " : "";
        st += "}";
        return st;
    }
    
    private static String getJiraIssueText(Carrier carrier) throws QException {
        String project = carrier.getValueAsString("project");
        String summary = carrier.getValueAsString("summary");
        String issueType = carrier.getValueAsString("issueType");
        String estimatedHours = carrier.getValueAsString("estimatedHours");
        String bid = carrier.getValueAsString("fkBacklogId");
        String bname = carrier.getValueAsString("backlogName");
        String pid = carrier.getValueAsString("fkProjectId");
        String url = getJiraDescription4Task(pid, bid, "");
        
        return "{\"fields\": {\n"
                + "        \"summary\": \"" + summary.replaceAll("\"", "'") + "\",\n"
                + "        \"issuetype\": {\n"
                + "            \"name\": \"" + issueType.replaceAll("\"", "'") + "\"\n"
                + "        },\n"
                + "        \"description\":  \" Related User Story: [" + bname.replaceAll("\"", "'") + " |" + url.replaceAll("\"", "'") + "]\","
                + "        \"project\": {\n"
                + "            \"key\": \"" + project.replaceAll("\"", "'") + "\"\n"
                + "        } \n"
                + "         ,\"labels\": [ \"" + carrier.get("taskTypeName").replaceAll(" ", "") + "\" ] "
                + "    },\n"
                + "\"timetracking\": { \"originalEstimate\": \"" + estimatedHours + "h\", \"remainingEstimate\": \"\" }  "
                + "}";
        
    }
    
    private static String getProjectCodeById(String projectId) throws QException {
        EntityTmProject ent = new EntityTmProject();
        if (projectId.trim().length() > 0) {
            ent.setId(projectId);
            EntityManager.select(ent);
        }
        
        return ent.getProjectCode();
    }
    
    public static Carrier syncWithJira(Carrier c) throws QException, Exception {
        String projectCode = getProjectCodeById(c.get("fkProjectId"));
        Carrier cIssueList = getIssueListByProjectInJira(projectCode);
        Carrier cBacklogList = getBacklogListByProject(c.get("fkProjectId"));
        Carrier crTaskList = getBacklogTaskListByProject(c.get("fkProjectId"));
        Carrier crTaskComemntList = getTaskCommentListByProject(c.get("fkProjectId"));
        
        int rc = cIssueList.getTableRowCount("jiraTable");
        for (int i = 0; i < rc; i++) {
            String estimatedHours = cIssueList.getValue("jiraTable", i, "estimatedHours").toString();
            String spentHours = cIssueList.getValue("jiraTable", i, "spentHours").toString();
            String assigneeAccountId = cIssueList.getValue("jiraTable", i, "assigneeAccountId").toString();
            String reporterAccountId = cIssueList.getValue("jiraTable", i, "reporterAccountId").toString();
            String issueId = cIssueList.getValue("jiraTable", i, "issueId").toString();
            String issueKey = cIssueList.getValue("jiraTable", i, "issueKey").toString();
            String issueStatus = cIssueList.getValue("jiraTable", i, "issueStatus").toString();
            String issueType = cIssueList.getValue("jiraTable", i, "issueType").toString();
            String parentIssueId = cIssueList.getValue("jiraTable", i, "parentIssueId").toString();
            String parentIssueKey = cIssueList.getValue("jiraTable", i, "parentIssueKey").toString();
            String parentIssueType = cIssueList.getValue("jiraTable", i, "parentIssueType").toString();
            
            issueStatus = (issueStatus.equals("To Do") ? "new"
                    : issueStatus.equals("Done") ? "closed" : "ongoing");
            
            if (parentIssueType.equals("Epic")) {
//                System.out.println("Parent Epic olanlar=" + issueKey);
                if (cBacklogList.isKeyExist(parentIssueKey) && crTaskComemntList.isKeyExist(issueKey)) {
                    if (crTaskComemntList.get(issueKey).length() > 0) {
                        EntityTmTaskComment ent = new EntityTmTaskComment();
                        ent.setId(crTaskComemntList.get(issueKey));
                        EntityManager.select(ent);
                        ent.setSpentHours((spentHours));
                        if ((Double.parseDouble(estimatedHours) > 0)) {
                            ent.setEstimatedHours((estimatedHours));
                        }
                        ent.setCommentStatus(issueStatus);
                        EntityManager.update(ent);
                        
                        if (ent.getCommentStatus().equals("closed") && ent.getIsNotifiedBug().length() > 0) {
                            Carrier c1 = new Carrier();
                            c1.set("fkCommentId", ent.getId());
                            closeBug4TestTrial(c1);
                        }
                    }
                } else if (cBacklogList.isKeyExist(parentIssueKey) && !crTaskComemntList.isKeyExist(issueKey)
                        && !crTaskList.isKeyExist(issueKey)) {
//                    System.out.println("Parent Epic olub lakin SAg-da olmayanlar=" + issueKey);
                    Carrier crTask = new Carrier();
                    crTask.set("fkBacklogId", cBacklogList.get(parentIssueKey));
                    crTask.set("fkTaskTypeId", getTaskTypeIdByJiraIssueType(issueType));
                    crTask.set("fkAssigneeId",
                            getUserIdOrFirstUserIdByJiraAccountId(assigneeAccountId, reporterAccountId));
                    crTask.set("createdBy",
                            getUserIdOrFirstUserIdByJiraReporterAccountId(reporterAccountId));
                    crTask.set("estimatedHours", (estimatedHours));
                    crTask.set("spentHours", (spentHours));
                    crTask.set("completedDuration", (spentHours));
                    crTask.set("taskStatus", issueStatus);
                    
                    crTask.set(EntityTmBacklogTask.TASK_STATUS, issueStatus);
                    crTask.set(EntityTmBacklogTask.JIRA_ISSUE_ID, issueId);
                    crTask.set(EntityTmBacklogTask.JIRA_ISSUE_KEY, issueKey);
                    insertNewBacklogTask(crTask);
                }
                if (cBacklogList.isKeyExist(parentIssueKey) && !crTaskComemntList.isKeyExist(issueKey)
                        && crTaskList.isKeyExist(issueKey)) {
                    if (crTaskList.get(issueKey).length() > 0) {
                        EntityTmBacklogTask ent = new EntityTmBacklogTask();
                        ent.setId(crTaskList.get(issueKey));
                        EntityManager.select(ent);
                        ent.setSpentHours((spentHours));
                        if ((Double.parseDouble(estimatedHours) > 0)) {
                            ent.setEstimatedHours((estimatedHours));
                        }
                        ent.setCompletedDuration(spentHours);
                        ent.setTaskStatus(issueStatus);
                        EntityManager.update(ent);
                    }
                    
                }
            }
            setBacklogStatus(cBacklogList.get(parentIssueKey));
        }
        return c;
    }
    
    private static String getTaskTypeIdByJiraIssueType(String issueType) throws QException {
        if (issueType.length() == 0) {
            return "11111";//default JIRA Task Type
        }
        
        EntityTmTaskType ent = new EntityTmTaskType();
        ent.setTypeName(issueType);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);
        
        if (ent.getId().length() == 0) {
            ent.setTypeStatus("A");
            ent.setCreatedBy(SessionManager.getCurrentUserId());
            ent.setCreatedDate(QDate.getCurrentDate());
            ent.setCreatedTime(QDate.getCurrentTime());
            EntityManager.insert(ent);
        }
        
        return ent.getId();
        
    }
    
    private static String getNumbersFromString(String arg) {
        String out = "";
        for (int i = 0; i < arg.length(); i++) {
            char a = arg.charAt(i);
            try {
                int k = Integer.valueOf(a);
                out += String.valueOf(k);
            } catch (Exception e) {
                
            }
        }
        return out;
    }
    
    private static Carrier getBacklogListByProject(String projectId) throws QException {
        if (projectId.length() == 0) {
            return new Carrier();
        }
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setFkProjectId(projectId);
        Carrier c = EntityManager.select(ent);
        c = c.getKVFromTable(ent.toTableName(), EntityTmBacklog.JIRA_KEY, EntityTmBacklog.ID);
        return c;
        
    }
    
    private static Carrier getBacklogTaskListByProject(String projectId) throws QException {
        if (projectId.length() == 0) {
            return new Carrier();
        }
        EntityTmBacklogTaskList ent = new EntityTmBacklogTaskList();
        ent.setFkProjectId(projectId);
        Carrier c = EntityManager.select(ent);
        c = c.getKVFromTable(ent.toTableName(), EntityTmBacklogTaskList.JIRA_ISSUE_KEY, EntityTmBacklog.ID);
        return c;
    }
    
    private static Carrier getTaskCommentListByProject(String projectId) throws QException {
        if (projectId.length() == 0) {
            return new Carrier();
        }
        
        EntityTmBacklogTaskList ent = new EntityTmBacklogTaskList();
        ent.setFkProjectId(projectId);
        Carrier c = EntityManager.select(ent);
        String tid = c.getValueLine(ent.toTableName(), EntityTmBacklogTaskList.ID);
        
        EntityTmTaskComment ent1 = new EntityTmTaskComment();
        ent1.setFkTaskId(tid);
        Carrier c1 = EntityManager.select(ent1);
        c1 = c1.getKVFromTable(ent1.toTableName(), EntityTmTaskComment.COMMENT_JIRA_KEY, EntityTmBacklog.ID);
        return c1;
    }
    
    private static String getUserIdOrFirstUserIdByJiraReporterAccountId(String reporterAccountId) throws QException {
        String userId = "";
        if (reporterAccountId.length() > 0) {
            EntityCrUser ent = new EntityCrUser();
            ent.setTgUserId(reporterAccountId);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            userId = ent.getId();
        } else {
            
            userId = SessionManager.getCurrentUserId();
        }
        
        return userId;
    }
    
    private static String getUserIdOrFirstUserIdByJiraAccountId(String accountId,
            String reporterAccountId) throws QException {
        String userId = "";
        if (accountId.length() > 0) {
            EntityCrUser ent = new EntityCrUser();
            ent.setTgUserId(accountId);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            userId = ent.getId();
        } else {
            EntityCrUser ent = new EntityCrUser();
            ent.setTgUserId(reporterAccountId);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            EntityManager.select(ent);
            userId = ent.getId();
        }
        
        if (userId.length() == 0) {
            EntityCrUser ent1 = new EntityCrUser();
            ent1.setUserStatus("A");
            ent1.setStartLimit(0);
            ent1.setEndLimit(0);
            EntityManager.select(ent1);
            userId = ent1.getId();
        }
        return userId;
    }
    
    private static Carrier getIssueListByProjectInJira(String projectCode) throws QException {
        
        Carrier c = new JIRA(projectCode).getIssueListOfProject();
        return c;
    }
    
    private static Carrier getIssueFromJiraByProject(Carrier c) throws QException, IOException {
        
        HttpURLConnection conn = getJiraIntegrationCoreDefault("POST", "/search");
        String input = "{\"jql\":\"project = " + c.get("projectCode") + "&issuetype != Epic\"}";
        
        executeCommandInJira(conn, input);
        
        conn.getResponseCode();
        conn.getResponseMessage();
        
        c.setValue("response", getConnectionOutputLines(conn));
        return c;
    }
    
    private static void executeCommandInJira(HttpURLConnection conn, String query) throws IOException {
        
        OutputStream os = conn.getOutputStream();
        os.write(query.getBytes());
        os.flush();
        
    }
    
    public Carrier updateBacklogByCanvasContent(Carrier carrier) throws QException {
        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(carrier.get("id"));
        EntityManager.select(ent);
        ent.setParam1(carrier.get("canvasContent"));
        EntityManager.update(ent);
        
        getBacklogList4Select(ent.getId()).copyTo(carrier);
        
        return carrier;
        
    }
    
    public Carrier saveFormAction(Carrier carrier) throws QException {
        String keys[] = carrier.getKeys();
        String inputIds = "";
        for (String k : keys) {
            if (k.length() == 0) {
                continue;
            }
//            EntityTmInput ent = new EntityTmInput();
//            ent.setId(k);
//            EntityManager.select(ent);
//            ent.setInputContent(carrier.get(k));
//            EntityManager.update(ent);

            EntityTmInput entOut = new EntityTmInput();
            entOut.setFkDependentOutputId(k);
            Carrier c = EntityManager.select(entOut);
            String tn = entOut.toTableName();
            int rc = c.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityManager.mapCarrierToEntity(c, tn, i, entOut);
                inputIds += entOut.getId() + CoreLabel.IN;
                String cntnt = entOut.getInputContent() + "\n" + carrier.get(k);
                entOut.setInputContent(cntnt);
                EntityManager.update(entOut);
            }
        }
        
        if (inputIds.length() > 4) {
            getInputList4Select(inputIds).copyTo(carrier);
        }
        
        return carrier;
    }
    
    public Carrier deleteFromTable(Carrier carrier) throws QException {
        try {
            int index = Integer.parseInt(carrier.get("index"));
            String keys[] = carrier.getKeys();
            String inputIds = "";
            for (String k : keys) {
                if (k.equals("index") || k.length() == 0) {
                    continue;
                }
                
                inputIds += k + CoreLabel.IN;
                
                EntityTmInput ent = new EntityTmInput();
                ent.setId(k);
                EntityManager.select(ent);
                String[] txt = ent.getInputContent().split("\\n");
                String st = "";
                for (int i = 0; i < txt.length; i++) {
                    if (i == (index - 1)) {
                        continue;
                    }
                    st += txt[i] + "\n";
                }
                ent.setInputContent(st);
                EntityManager.update(ent);
                
            }
            
            getInputList4Select(inputIds).copyTo(carrier);
            return carrier;
        } catch (Exception e) {
            System.out.println("exception in delete table=" + e.getMessage());
            e.printStackTrace();
            return carrier;
        }
    }
    
    public Carrier addInputDescCriterias(Carrier carrier) throws QException {
        String currentInputId = carrier.get("fkInputId");
        String projectId = carrier.get("fkProjectId");
        String backlogId = carrier.get("fkBacklogId");
        String checkedInputIds[] = (currentInputId + "," + carrier.get("checkedInputIds")).split(",");
        String tn = "inputDescTable";
        String col = "criteriaLine";

//        if (currentInputId.trim().length() == 0) {
//            return carrier;
//        }
//        EntityTmInput entInput = new EntityTmInput();
//        entInput.setId(currentInputId);
//        EntityManager.select(entInput);
        String inputIds4Frontend = "";
        String inputDescIds4Frontend = "";
        
        int rc = carrier.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            String v = carrier.getValue(tn, i, col).toString();
            if (v.length() == 0) {
                continue;
            }
            for (String id : checkedInputIds) {
                if (id.trim().length() == 0) {
                    continue;
                }
                EntityTmInputDescription ent = new EntityTmInputDescription();
                ent.setFkInputId(id);
                ent.setFkProjectId(projectId);
                ent.setDescription(v);
                Carrier cr = EntityManager.select(ent);
                if (cr.getTableRowCount(ent.toTableName()) > 0) {
                    continue;
                }
                EntityManager.insert(ent);
                
                inputIds4Frontend += id + CoreLabel.IN;
                inputDescIds4Frontend += ent.getId() + CoreLabel.IN;
                
                EntityTmInput entInput = new EntityTmInput();
                entInput.setId(id);
                entInput.setFkBacklogId(backlogId);
                entInput.setInputName(v);
                
                setNewBacklogHistory4InputDescriptionNew(ent, entInput);
            }
        }
        
        getInputDescriptionList4Select(inputDescIds4Frontend).copyTo(carrier);
        getInputList4Select(inputIds4Frontend).copyTo(carrier);
        getBacklogDependencyList4Select(carrier).copyTo(carrier);
        return carrier;
        
    }
}

class Analytics {
    
    private String fromDate;
    private String toDate;
    private String projectList;
    private String assigneeList;
    private String sprintList;
    private String labelList;
    private String taskTypeList;
    private String groupBy;
    private String priorityList;
    
    public String getFromDate() {
        return fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    
    public String getToDate() {
        return toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
    public String getProjectList() {
        return projectList;
    }
    
    public void setProjectList(String projectList) {
        this.projectList = projectList;
    }
    
    public String getAssigneeList() {
        return assigneeList;
    }
    
    public void setAssigneeList(String assigneeList) {
        this.assigneeList = assigneeList;
    }
    
    public String getSprintList() {
        return sprintList;
    }
    
    public void setSprintList(String sprintList) {
        this.sprintList = sprintList;
    }
    
    public String getLabelList() {
        return labelList;
    }
    
    public void setLabelList(String labelList) {
        this.labelList = labelList;
    }
    
    public String getTaskTypeList() {
        return taskTypeList;
    }
    
    public void setTaskTypeList(String taskTypeList) {
        this.taskTypeList = taskTypeList;
    }
    
    public String getGroupBy() {
        return groupBy;
    }
    
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
    
    public String getPriorityList() {
        return priorityList;
    }
    
    public void setPriorityList(String priorityList) {
        this.priorityList = priorityList;
    }
    
    public String getQuery() {
        String st = "";
        
        st += " select ";
        st += "\n";
        st += this.genSelectPart_GroupBy();
        st += "\n";
        st += this.genSelectPart_GatherSumAndCountOnSelect();
//        st+="'a'";
        st += "\n";
        st += this.genSelectPart_FromPart();
        return st;
    }
    
    public String genSelectPart_GatherSumAndCountOnSelect() {
        String st = "";
        st += this.genSelectPart_NewUserStoryCount();
//        st += this.genSelectPart_BugCount();
//        st += this.genSelectPart_RequestCount();
        st += this.genSelectPart_SumEstimatedHours();
//        st += this.genSelectPart_SumEstimatedHours4Bug();
//        st += this.genSelectPart_SumEstimatedHours4Request();
        st += this.genSelectPart_SumSpentHours();
//        st += this.genSelectPart_SumSpentHours4Bug();
//        st += this.genSelectPart_SumSpentHours4Request();
        st += "'a' \n";
        return st;
    }
    
    public String genSelectPart_GroupBy() {
        StringBuilder res = new StringBuilder();
        res.append("distinct " + "\n");
        res.append("'" + this.getFromDate() + "' from_date ,\n");
        res.append("'" + this.getToDate() + "' to_date   ,\n");
        res.append(this.getGroupBy().contains("project") ? "b.fk_project_id,\n" : "" + "\n");
        res.append(this.getGroupBy().contains("priority") ? " b.priority,\n" : "" + "\n");
        res.append(this.getGroupBy().contains("assignee")
                ? " bt.fk_assignee_id,\n"
                : "");
        
        res.append(this.getGroupBy().contains("tasktype") ? " bt.fk_task_type_id\n," : "" + "\n");
        res.append(this.getGroupBy().contains("label") ? "lbl.fk_task_label_id,\n" : "" + "\n");
        res.append("(select GROUP_CONCAT(' ',s3.sprint_name) from tm_task_sprint s3  \n");
        res.append("    where s3.fk_project_id = b.fk_project_id and s3.sprint_end_date between \n");
        res.append("     '" + this.getFromDate() + "' and '" + this.getToDate());
        res.append("') sprint_name,\n");
        return res.toString();
    }
    
    public String genSelectPart_NewUserStoryCount() {
        StringBuilder res = new StringBuilder();
        res.append("(select count(b1.id) from tm_backlog b1" + "\n");
        res.append("where b1.status='A' " + "\n");
        res.append(this.getProjectList().length() > 6 ? "and b1.fk_project_id = b.fk_project_id " : "" + "\n");
        res.append(this.getGroupBy().contains("priority") || this.getPriorityList().length() > 6 ? "and b1.priority = b.priority " : "" + "\n");
        
        res.append(this.getGroupBy().contains("assignee") || this.getAssigneeList().length() > 6
                ? " and (case when (bt.fk_assignee_id !='' or bt.fk_assignee_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_assignee_id= bt.fk_assignee_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A') \n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("tasktype") || this.getTaskTypeList().length() > 6
                ? " and (case when (bt.fk_task_type_id !='' or bt.fk_task_type_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_task_type_id= bt.fk_task_type_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A' )\n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("label") || this.getLabelList().length() > 6 ? " and b1.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl1 where lbl1.status='A'  and lbl1.id=lbl.id)" : "" + "\n");
        res.append("and b1.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt1 where sprnt1.status='A'  and   sprnt1.fk_task_sprint_id in ");
        res.append("    (select id from tm_task_sprint where sprint_end_date between ");
        res.append("    '" + this.getFromDate() + "' and '" + this.getToDate() + "'))  " + "\n");
        res.append(")  new_userstory_count," + "\n");
        
        return res.toString();
    }
    
    public String genSelectPart_BugCount() {
        StringBuilder res = new StringBuilder();
        res.append(" (select count(tc1.id) from tm_task_comment tc1 " + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append("and is_bug='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append(" (select id from tm_backlog_task bt1 where bt1.status='A' AND bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2 where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A' and lbl2.id=lbl.id) " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A' and sprnt2.id=sprnt.id) ))" + "\n");
        res.append(" ) bug_count," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_RequestCount() {
        StringBuilder res = new StringBuilder();
        res.append(" (select count(tc1.id) from tm_task_comment tc1 " + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append("and is_request='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append(" (select id from tm_backlog_task bt1 where bt1.status='A' AND bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2 where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A' and lbl2.id=lbl.id) " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A' and sprnt2.id=sprnt.id) ))" + "\n");
        res.append(" ) request_count," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumEstimatedHours() {
        StringBuilder res = new StringBuilder();
        res.append("(select round(sum(b1.estimated_hours),2) from tm_backlog b1" + "\n");
        res.append("where b1.status='A' " + "\n");
        
        res.append(this.getProjectList().length() > 6 ? "and b1.fk_project_id = b.fk_project_id " : "" + "\n");
        res.append(this.getGroupBy().contains("priority") || this.getPriorityList().length() > 6 ? "and b1.priority = b.priority " : "" + "\n");
        
        res.append(this.getGroupBy().contains("assignee") || this.getAssigneeList().length() > 6
                ? " and (case when (bt.fk_assignee_id !='' or bt.fk_assignee_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_assignee_id= bt.fk_assignee_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A') \n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("tasktype") || this.getTaskTypeList().length() > 6
                ? " and (case when (bt.fk_task_type_id !='' or bt.fk_task_type_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_task_type_id= bt.fk_task_type_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A' )\n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("label") || this.getLabelList().length() > 6 ? " and b1.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl1 where lbl1.status='A'  and lbl1.id=lbl.id)" : "" + "\n");
        res.append("and b1.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt1 where sprnt1.status='A'  and   sprnt1.fk_task_sprint_id in ");
        res.append("    (select id from tm_task_sprint where sprint_end_date between ");
        res.append("    '" + this.getFromDate() + "' and '" + this.getToDate() + "'))  " + "\n");
        
        res.append(")  sum_estimated_hours," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumEstimatedHours4Bug() {
        StringBuilder res = new StringBuilder();
        res.append(" (select  ifnull(sum(tc1.estimated_hours),0) " + "\n");
        res.append(" from tm_task_comment tc1" + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append(" and is_bug='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append("(select id from tm_backlog_task bt1 where bt1.status='A' AND  bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2  where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A'  and lbl2.id=lbl.id)  " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A'  and sprnt2.id=sprnt.id)   ))" + "\n");
        res.append(")  sum_estimated_hours_bug," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumEstimatedHours4Request() {
        StringBuilder res = new StringBuilder();
        res.append(" (select  ifnull(sum(tc1.estimated_hours),0) " + "\n");
        res.append(" from tm_task_comment tc1" + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append(" and is_request='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append("(select id from tm_backlog_task bt1 where bt1.status='A' AND  bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2  where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A'  and lbl2.id=lbl.id)  " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A'  and sprnt2.id=sprnt.id)   ))" + "\n");
        res.append(")  sum_estimated_hours_request," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumSpentHours() {
        StringBuilder res = new StringBuilder();
        res.append("(select round(sum(b1.spent_hours),2) from tm_backlog b1" + "\n");
        res.append("where b1.status='A' " + "\n");
        
        res.append(this.getProjectList().length() > 6 ? "and b1.fk_project_id = b.fk_project_id " : "" + "\n");
        res.append(this.getGroupBy().contains("priority") || this.getPriorityList().length() > 6 ? "and b1.priority = b.priority " : "" + "\n");
        
        res.append(this.getGroupBy().contains("assignee") || this.getAssigneeList().length() > 6
                ? " and (case when (bt.fk_assignee_id !='' or bt.fk_assignee_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_assignee_id= bt.fk_assignee_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A') \n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("tasktype") || this.getTaskTypeList().length() > 6
                ? " and (case when (bt.fk_task_type_id !='' or bt.fk_task_type_id is not null) then b1.id in( "
                + "select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A'  and bt1.fk_task_type_id= bt.fk_task_type_id )\n"
                + "    else b1.id not in( select fk_backlog_id from tm_backlog_task bt1 where bt1.status='A' )\n"
                + "  end)"
                : "");
        
        res.append(this.getGroupBy().contains("label") || this.getLabelList().length() > 6 ? " and b1.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl1 where lbl1.status='A'  and lbl1.id=lbl.id)" : "" + "\n");
        res.append("and b1.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt1 where sprnt1.status='A'  and   sprnt1.fk_task_sprint_id in ");
        res.append("    (select id from tm_task_sprint where sprint_end_date between ");
        res.append("    '" + this.getFromDate() + "' and '" + this.getToDate() + "'))  " + "\n");
        
        res.append(" )  sum_spent_hours," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumSpentHours4Bug() {
        StringBuilder res = new StringBuilder();
        res.append(" (select  ifnull(round(sum(tc1.spent_hours),2),0) " + "\n");
        res.append(" from tm_task_comment tc1" + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append(" and is_bug='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append("(select id from tm_backlog_task bt1 where bt1.status='A' AND  bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2  where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A'  and lbl2.id=lbl.id)  " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A'  and sprnt2.id=sprnt.id)   ))" + "\n");
        res.append(")  sum_spent_hours_bug," + "\n");
        return res.toString();
    }
    
    public String genSelectPart_SumSpentHours4Request() {
        StringBuilder res = new StringBuilder();
        res.append(" (select  ifnull(sum(tc1.spent_hours),0) " + "\n");
        res.append(" from tm_task_comment tc1" + "\n");
        res.append("where tc1.status='A'" + "\n");
        res.append("and tc1.comment_date between '20191001' and '20200201'" + "\n");
        res.append(" and is_request='1'" + "\n");
        res.append(" and tc1.fk_task_id in " + "\n");
        res.append("(select id from tm_backlog_task bt1 where bt1.status='A' AND  bt1.fk_task_type_id=bt.fk_task_type_id and bt1.fk_assignee_id= bt.fk_assignee_id " + "\n");
        res.append(" AND bt1.fk_backlog_id in " + "\n");
        res.append(" (select b2.id from tm_backlog b2  where b2.status='A' and b2.fk_project_id = b.fk_project_id and b2.priority = b.priority" + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_label lbl2 where lbl2.status='A'  and lbl2.id=lbl.id)  " + "\n");
        res.append(" and b2.id in( select fk_backlog_id from tm_rel_backlog_and_sprint sprnt2 where sprnt2.status='A'  and sprnt2.id=sprnt.id)   ))" + "\n");
        res.append(")  sum_spent_hours_request" + "\n");
        return res.toString();
    }
    
    public String genSelectPart_FromPart() {
        StringBuilder res = new StringBuilder();
        
        res.append("from tm_backlog b" + "\n");
        
        res.append(this.getTaskTypeList().length() > 6 || this.getAssigneeList().length() > 6
                ? " inner " : " left ");
        res.append(" join tm_backlog_task bt on bt.fk_backlog_id = b.id and bt.status='A' " + "\n");
        res.append(this.getTaskTypeList().length() > 6 ? " and bt.fk_task_type_id in  ('" + this.getTaskTypeList().replaceAll("%IN%", "','") + "')" : " ");
        res.append(this.getAssigneeList().length() > 6 ? " and  bt.fk_assignee_id in  ('" + this.getAssigneeList().replaceAll("%IN%", "','") + "')" : " ");
        
        res.append(this.getLabelList().length() > 6 || this.getGroupBy().contains("label")
                ? "  inner join tm_rel_backlog_and_label lbl on lbl.fk_backlog_id = b.id and lbl.status='A' \n"
                : "");
        res.append(this.getLabelList().length() > 6 ? " and bt.fk_label_id in  ('" + this.getLabelList().replaceAll("%IN%", "','") + "')" : " ");
        
        res.append("inner join tm_rel_backlog_and_sprint sprnt on sprnt.fk_backlog_id = b.id and sprnt.status='A' " + "\n");
        res.append(" and sprnt.fk_task_sprint_id in (select id from tm_task_sprint where sprint_end_date");
        res.append(" between '" + this.getFromDate() + "' and '" + this.getToDate() + "') ");
        
        res.append("where b.status='A'" + "\n");
        
        res.append(this.getProjectList().length() > 8
                ? " and b.fk_project_id in ('" + this.getProjectList().replaceAll("%IN%", "','") + "')"
                : "");
        
        res.append(this.getPriorityList().length() > 6
                ? " and b.priority in ('" + this.getPriorityList().replaceAll("%IN%", "','") + "')"
                : "");
        
        return res.toString();
    }
    
}

class DashboardStatistics {
    
    public static String getMyProjects() throws QException {
        EntityTmProjectPermission ent = new EntityTmProjectPermission();
        ent.setFkUserId(SessionManager.getCurrentUserId());
        return "-1," + EntityManager.select(ent).getValueLine(ent.toTableName(), "fkProjectId", ",");
    }
    
    public static Carrier getProjectSummaryByUser(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = new Carrier();
        getProjectSummary_AssigneeOverall(carrier).copyTo(cout);
        getProjectSummary_AssigneeBug(carrier).copyTo(cout);
        getProjectSummary_AssigneeChange(carrier).copyTo(cout);
        getProjectSummary_AssigneeNew(carrier).copyTo(cout);
        
        getAssigneeList(carrier).copyTo(cout);
        
        return cout;
    }
    
    public static Carrier getProjectSummaryByAssignee(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = new Carrier();
        getProjectSummary_AssigneeCreatedStory(carrier).copyTo(cout);
        getProjectSummary_AssigneeOverall(carrier).copyTo(cout);
        getProjectSummary_AssigneeBug(carrier).copyTo(cout);
        getProjectSummary_AssigneeChange(carrier).copyTo(cout);
        getProjectSummary_AssigneeNew(carrier).copyTo(cout);
        getProjectSummary_AssigneeCreatedTask(carrier).copyTo(cout);
        getProjectSummary_AssigneeCreatedBug(carrier).copyTo(cout);
        getProjectSummary_AssigneeCreatedNew(carrier).copyTo(cout);
        getProjectSummary_AssigneeCreatedChange(carrier).copyTo(cout);
        
        getAssigneeList(carrier).copyTo(cout);
        
        return cout;
    }
    
    public static Carrier getProjectSummaryByTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = new Carrier();
        getProjectSummary_TaskOverallBug(carrier).copyTo(cout);
        getProjectSummary_Bug(carrier).copyTo(cout);
        getProjectSummary_Change(carrier).copyTo(cout);
        getProjectSummary_New(carrier).copyTo(cout);
        getProjectSummary_Unassigned(carrier).copyTo(cout);
        getProjectSummary_Nostorycard(carrier).copyTo(cout);
        getProjectList(carrier).copyTo(cout);
        
        return cout;
    }
    
    public static Carrier getStoryCardSummary(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = new Carrier();
        getProjectSummary_TaskOverall4SC(carrier).copyTo(cout);
        getProjectSummary_Bug4SC(carrier).copyTo(cout);
        getProjectSummary_Change4SC(carrier).copyTo(cout);
        getProjectSummary_New4SC(carrier).copyTo(cout);
        return cout;
    }
    
    public static Carrier getProjectSummary(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        
        Carrier cout = new Carrier();
        getProjectSummary_StoryCard(carrier).copyTo(cout);
        getProjectSummary_StoryCardInAction(carrier).copyTo(cout);
        getProjectSummary_StoryCardInInitial(carrier).copyTo(cout);
        getProjectSummary_StoryCardWithBug(carrier).copyTo(cout);
        getProjectSummary_StoryCardWithNew(carrier).copyTo(cout);
        getProjectSummary_StoryCardWithChange(carrier).copyTo(cout);
        getProjectList(carrier).copyTo(cout);
        
        return cout;
    }
    
    private static String getProjectListBySprintTask(Carrier carrier) throws QException {
        String res = "";
        
        if (carrier.get("fkSprintId").length() == 0) {
            return "";
        }
        
        String sprintIds = carrier.get("fkSprintId").replaceAll(",", CoreLabel.IN);
        
        EntityTmRelTaskAndSprint ent = new EntityTmRelTaskAndSprint();
        ent.setFkTaskSprintId(sprintIds);
        res = EntityManager.select(ent).getValueLine(ent.toTableName(), EntityTmRelTaskAndSprint.FK_PROJECT_ID);
        
        return res;
    }
    
    private static String getProjectListByLabelTask(Carrier carrier) throws QException {
        String res = "";
        
        if (carrier.get("fkLabelId").length() == 0) {
            return "";
        }
        
        String labelIds = carrier.get("fkLabelId").replaceAll(",", CoreLabel.IN);
        
        EntityTmRelTaskAndLabel ent = new EntityTmRelTaskAndLabel();
        ent.setFkTaskLabelId(labelIds);
        res = EntityManager.select(ent).getValueLine(ent.toTableName(), EntityTmRelTaskAndSprint.FK_PROJECT_ID);
        
        return res;
    }
    
    private static Carrier getProjectList(Carrier carrier) throws QException {
        String projectId = "";
        
        if (carrier.get("fkSprintId").length() > 0 || carrier.get("fkLabelId").length() > 0) {
            projectId = "-2%IN%" + getProjectListBySprintTask(carrier) + "%IN%" + getProjectListByLabelTask(carrier);
        } else {
            
            projectId = carrier.get("fkProjectId").length() > 1
                    ? carrier.get("fkProjectId")
                    : TmModel.getMyProjects();
        }
        
        Carrier cout = new Carrier();
        
        EntityTmProject ent = new EntityTmProject();
        ent.addSortBy("projectName");
        ent.setSortByAsc(true);
        
        ent.setId(projectId.replaceAll(",", CoreLabel.IN));
        Carrier cr = EntityManager.select(ent);
        cr.renameTableName(ent.toTableName(), "projectList");
        cr.copyTo(cout);
        return cout;
    }
    
    private static Carrier getAssigneeList(Carrier carrier) throws QException {
        String createdby = carrier.get("createdBy").replaceAll(",", CoreLabel.IN);
        String projectId = "";
        
        if (carrier.get("fkSprintId").length() > 0 || carrier.get("fkLabelId").length() > 0) {
            projectId = "-2%IN%" + getProjectListBySprintTask(carrier) + "%IN%" + getProjectListByLabelTask(carrier);
        } else {
            
            projectId = carrier.get("fkProjectId").length() > 1
                    ? carrier.get("fkProjectId")
                    : TmModel.getMyProjects();
            projectId = projectId.replaceAll(",", CoreLabel.IN);
        }
        
        Carrier cout = new Carrier();
        
        EntityTmProjectPermissionList ent = new EntityTmProjectPermissionList();
        ent.addSortBy(EntityTmProjectPermissionList.USER_NAME);
        ent.addDistinctField(EntityTmProjectPermissionList.USER_NAME);
        ent.addDistinctField(EntityTmProjectPermissionList.FK_USER_ID);
        ent.setSortByAsc(true);
        ent.setFkProjectId(projectId);
        ent.setFkUserId(createdby);
        Carrier cr = EntityManager.select(ent);
        cr.renameTableName(ent.toTableName(), "userList");
        cr.copyTo(cout);
        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardInAction4Select(String projectId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select id, backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + status + "  order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardByOwner4Select(String createdBy,
            String statusType, String selectedProjectList, String sprintList, String labelList) throws QException {
        String projectList = selectedProjectList.length() > 1
                ? selectedProjectList
                : getMyProjects();
        
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select id, backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.created_by in (" + createdBy + ")"
                + " and m.fk_project_id in (" + projectList + ")"
                //                + "and id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.created_by = m.created_by)"
                + status + "  order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCard(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.fk_project_id = m.fk_project_id and a.backlog_status='new' and  a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "overall");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCardInAction(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='new'   "
                + "and a.id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' "
                + "and a.id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' "
                + "and a.id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "inaction");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCardInInitial(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='new'   "
                + "and a.id not in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' "
                + "and a.id  not in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' "
                + "and a.id not in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id not in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "initial");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardInInitial4Select(String projectId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id, backlog_name "
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id not in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.fk_project_id = m.fk_project_id)"
                + status + ""
                + " order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCardWithBug(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='new'   "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='bug' and c.fk_project_id = m.fk_project_id)"
                + "and a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='bug'  and c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='bug' and  c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='bug' and c.fk_project_id = m.fk_project_id)"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "withbugs");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardWithBug4Select(String projectId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id, backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='bug' and c.fk_project_id = m.fk_project_id)"
                + status + " order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardWithChange4Select(String projectId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id, backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='change' and c.fk_project_id = m.fk_project_id)"
                + status + " order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCardWithChange(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='new'   "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='change' and c.fk_project_id = m.fk_project_id)"
                + "and a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='change'  and c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='change' and  c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='change' and c.fk_project_id = m.fk_project_id)"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "withchanges");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    public static Carrier getProjectSummary_StoryCardWithNew4Select(String projectId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.backlog_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id, backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='new' and c.fk_project_id = m.fk_project_id)"
                + status + " order by backlog_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    private static Carrier getProjectSummary_StoryCardWithNew(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='new'   "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='new' and c.fk_project_id = m.fk_project_id)"
                + "and a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='ongoing' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='new'  and c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a "
                + "where  a.fk_project_id = m.fk_project_id and a.backlog_status='closed' "
                + "and a.id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='new' and  c.fk_project_id = m.fk_project_id)"
                + "and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "and id  in (select c.fk_backlog_id from " + SessionManager.getCurrentDomain() + ".tm_backlog_task c where c.task_nature='new' and c.fk_project_id = m.fk_project_id)"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "withnews");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);

        return cout;
    }
    
    private static String getPrjectSummary_SprintWhere(String sprintId) {
        String sprint = (sprintId.length() > 1)
                ? " and id in  ("
                + " select a.FK_BACKLOG_TASK_ID from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_sprint a where a.status='A' "
                + " and a.FK_TASK_SPRINT_ID in (" + sprintId + ")"
                + ") "
                : "";
        return sprint;
    }
    
    private static String getPrjectSummary_LabelWhere(String labelId) {
        String label = (labelId.length() > 1)
                ? " and id in  ("
                + " select a.FK_BACKLOG_TASK_ID from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_label a where a.status='A' "
                + " and a.FK_TASK_label_ID in (" + labelId + ")"
                + ") "
                : "";
        return label;
    }
    
    public static Carrier getProjectSummary_TaskOverall4Select4SC(String backlogId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and  m.fk_backlog_id in (" + backlogId + ")"
                + status + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskBug4Select4SC(String backlogId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature = 'bug' and  m.fk_backlog_id in (" + backlogId + ")"
                + status + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskNew4Select4SC(String backlogId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature = 'new' and  m.fk_backlog_id in (" + backlogId + ")"
                + status + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskChange4Select4SC(String backlogId, String statusType) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature = 'change' and  m.fk_backlog_id in (" + backlogId + ")"
                + status + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskOverall4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeOverall4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.fk_assignee_id in (" + assigneeId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeBug4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.fk_assignee_id in (" + assigneeId + ")"
                + " and m.task_nature='bug' "
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeCreated4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.created_by in (" + assigneeId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeCreatedNew4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.created_by in (" + assigneeId + ")"
                + " and m.task_nature='new'"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeCreatedBug4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='bug'"
                + projectLine
                + " and  m.created_by in (" + assigneeId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeCreatedChange4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='change'"
                + projectLine
                + " and  m.created_by in (" + assigneeId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeChange4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.fk_assignee_id in (" + assigneeId + ")"
                + " and m.task_nature='change' "
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_AssigneeNew4Select(String assigneeId, String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String projectLine = (projectId.trim().length() == 0)
                ? " and  m.fk_project_id in (" + getMyProjects() + ")"
                : " and  m.fk_project_id in (" + projectId + ")";
        
        String line
                = "select \n"
                + "id, task_name  \n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + projectLine
                + " and  m.fk_assignee_id in (" + assigneeId + ")"
                + " and m.task_nature='new' "
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeCreatedStory(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "created_by as fk_project_id,\n"
                + "count(id) overall,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.created_by = m.created_by  and a.fk_project_id in (" + projectId + ") and a.backlog_status='new' and  a.status = 'A') as status_new,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.created_by = m.created_by  and a.fk_project_id in (" + projectId + ") and a.backlog_status='ongoing' and  a.status = 'A') as status_ongoing,\n"
                + "(select count(a.id) from " + SessionManager.getCurrentDomain() + ".tm_backlog a where  a.created_by = m.created_by  and a.fk_project_id in (" + projectId + ") and a.backlog_status='closed' and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog m\n"
                + "where m.status='A'\n"
                + " and m.fk_project_id in (" + projectId + ")"
                + "group by created_by;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "createdstory");
//        carrier.getKVPairListFromTable(ent.toTableName(), "fkProjectId", "backlogName").copyTo(cout);
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeOverall(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.fk_assignee_id as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where   a.fk_assignee_id=m.fk_assignee_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "  and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_assignee_id=m.fk_assignee_id    " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.fk_assignee_id=m.fk_assignee_id    " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.fk_assignee_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "overall");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeBug(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.fk_assignee_id as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='bug'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.fk_assignee_id=m.fk_assignee_id  and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='bug'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and   a.fk_assignee_id=m.fk_assignee_id  and a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='bug'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and  a.fk_assignee_id=m.fk_assignee_id  and a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='bug'"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.fk_assignee_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "bugs");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeChange(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.fk_assignee_id as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where     a.task_nature='change'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.fk_assignee_id=m.fk_assignee_id  and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='change' and   a.fk_assignee_id=m.fk_assignee_id    " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='change' and  a.fk_assignee_id=m.fk_assignee_id    " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and m.task_nature='change'"
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.fk_assignee_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "changes");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeNew(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.fk_assignee_id as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where     a.task_nature='new'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.fk_assignee_id=m.fk_assignee_id  and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='new'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and   a.fk_assignee_id=m.fk_assignee_id  and a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='new'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and  a.fk_assignee_id=m.fk_assignee_id  and a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='new'"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.fk_assignee_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "news");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeCreatedTask(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.created_by as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.created_by;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "created");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeCreatedBug(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.created_by as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.task_nature='bug' and a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.task_nature='bug'  and a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.task_nature='bug' and a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='bug' "
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.created_by;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "createdbug");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeCreatedNew(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.created_by as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where a.task_nature='new' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='new' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.task_nature='new' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='new'"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.created_by;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "creatednew");
        return cout;
    }
    
    private static Carrier getProjectSummary_AssigneeCreatedChange(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + " m.created_by as fk_project_id,\n"
                + " count(distinct m.id) overall,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where   a.task_nature='change' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' " + " and  a.fk_project_id in (" + projectId + ")" + " and    a.status = 'A') as status_new,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where   a.task_nature='change' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='ongoing' " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_ongoing,\n"
                + " (select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where   a.task_nature='change' and  a.created_by=m.created_by   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and    a.task_status='closed'   " + " and  a.fk_project_id in (" + projectId + ")" + "   and  a.status = 'A') as status_closed\n"
                + " from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + " where m.status='A'\n"
                + " and m.task_nature='change' "
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + " group by m.created_by;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "createdchange");
        return cout;
    }
    
    private static String getSprintLine4Task(Carrier carrier) throws QException {
        String res = "";
        
        res = (carrier.get("fkSprintId").length() > 1)
                ? " and id in  ("
                + " select a.FK_BACKLOG_TASK_ID from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_sprint a where a.status='A' "
                + " and a.FK_TASK_SPRINT_ID in (" + carrier.get("fkSprintId") + ")"
                + ")"
                : "";
        
        return res;
    }
    
    private static String getLabelLine4Task(Carrier carrier) throws QException {
        String res = "";
        
        res = (carrier.get("fkLabelId").length() > 1)
                ? " and id in  ("
                + " select a.FK_BACKLOG_TASK_ID from " + SessionManager.getCurrentDomain() + ".tm_rel_task_and_label a where a.status='A' "
                + " and a.FK_TASK_LABEL_ID in (" + carrier.get("fkLabelId") + ")"
                + ")"
                : "";
        
        return res;
    }
    
    private static Carrier getProjectSummary_Bug4SC(Carrier carrier) throws QException {
        String backlogId = carrier.get("fkBacklogId");
        
        String line
                = "select \n"
                + "fk_backlog_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='bug' and a.task_status='new'  " + " and  a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='bug' and a.task_status='ongoing'   " + "   and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='bug' and a.task_status='closed'   " + "   and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m "
                + "where m.status='A' "
                + " and     m.task_nature='bug' and  m.fk_backlog_id in (" + backlogId + ")"
                + " group by fk_backlog_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "bugs");
        return cout;
    }
    
    private static Carrier getProjectSummary_New4SC(Carrier carrier) throws QException {
        String backlogId = carrier.get("fkBacklogId");
        
        String line
                = "select \n"
                + "fk_backlog_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='new' and a.task_status='new'  " + " and  a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='new' and a.task_status='ongoing'   " + "   and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='new' and a.task_status='closed'   " + "   and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m "
                + "where m.status='A' "
                + " and     m.task_nature='new' and  m.fk_backlog_id in (" + backlogId + ")"
                + " group by fk_backlog_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "news");
        return cout;
    }
    
    private static Carrier getProjectSummary_Change4SC(Carrier carrier) throws QException {
        String backlogId = carrier.get("fkBacklogId");
        
        String line
                = "select \n"
                + "fk_backlog_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='change' and a.task_status='new'  " + " and  a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='change' and a.task_status='ongoing'   " + "   and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id   and a.task_nature='change' and a.task_status='closed'   " + "   and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m "
                + "where m.status='A' "
                + " and     m.task_nature='change' and  m.fk_backlog_id in (" + backlogId + ")"
                + " group by fk_backlog_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "changes");
        return cout;
    }
    
    private static Carrier getProjectSummary_TaskOverall4SC(Carrier carrier) throws QException {
        String backlogId = carrier.get("fkBacklogId");
        
        String line
                = "select \n"
                + "fk_backlog_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id and a.task_status='new'  " + " and  a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id and a.task_status='ongoing'   " + "   and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_backlog_id = m.fk_backlog_id and a.task_status='closed'   " + "   and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m "
                + "where m.status='A' "
                + " and  m.fk_backlog_id in (" + backlogId + ")"
                + " group by fk_backlog_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "overall");
        return cout;
    }
    
    private static Carrier getProjectSummary_TaskOverallBug(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id and a.task_status='new'  " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and  a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id and a.task_status='ongoing'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "   and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id and a.task_status='closed'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "   and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m "
                + "where m.status='A' "
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "overall");
        return cout;
    }
    
    private static Carrier getProjectSummary_Bug(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' and    a.task_nature='bug' and a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id and a.task_status='ongoing'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "  and a.task_nature='bug' and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id and a.task_status='closed'   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "  and a.task_nature='bug' and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and m.task_nature='bug' and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "bugs");
        return cout;
    }
    
    public static Carrier getProjectSummary_Bug4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select id, task_name  as backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature='bug' and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    public static Carrier getProjectSummary_Change4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select id, task_name as backlog_name  \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature='change' and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    public static Carrier getProjectSummary_New4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select id, task_name as backlog_name \n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and m.task_nature='new' and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        
        return cout;
    }
    
    private static Carrier getProjectSummary_Change(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + "  and a.task_status='new' and    a.task_nature='change' and a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing'  and a.task_nature='change' and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'  and a.task_nature='change' and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and m.task_nature='change' and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "changes");
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskUnassigned4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and  (fk_assignee_id is null or fk_assignee_id='' or fk_assignee_id ='-1') "
                + " and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    public static Carrier getProjectSummary_TaskNostorycard4Select(String projectId, String statusType, String labelId, String sprintId) throws QException {
        String status = (statusType.equals("total")) ? ""
                : " and m.task_status = '" + statusType + "'";
        
        String line
                = "select \n"
                + "id ,task_name as backlog_name\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + " and  (fk_backlog_id is null or fk_backlog_id='' or fk_backlog_id ='-1') "
                + " and  m.fk_project_id in (" + projectId + ")"
                + status + getPrjectSummary_SprintWhere(sprintId) + getPrjectSummary_LabelWhere(labelId) + " order by task_name;";
        
        Carrier cout = EntityManager.selectBySql(line);
        return cout;
    }
    
    private static Carrier getProjectSummary_Unassigned(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new'  and  (fk_assignee_id is null or fk_assignee_id='' or fk_assignee_id ='-1')  and a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing'   and  (fk_assignee_id is null or fk_assignee_id='' or fk_assignee_id ='-1')  and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'   and  (fk_assignee_id is null or fk_assignee_id='' or fk_assignee_id ='-1')  and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  (fk_assignee_id is null or fk_assignee_id='' or fk_assignee_id ='-1') "
                + "  and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "unassigned");
        return cout;
    }
    
    private static Carrier getProjectSummary_Nostorycard(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new'  and  (fk_backlog_id is null or fk_backlog_id='' or fk_backlog_id ='-1')  and a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing'   and  (fk_backlog_id is null or fk_backlog_id='' or fk_backlog_id ='-1')  and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'   and  (fk_backlog_id is null or fk_backlog_id='' or fk_backlog_id ='-1')  and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and  (fk_backlog_id is null or fk_backlog_id='' or fk_backlog_id ='-1') "
                + "  and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "nostorycard");
        return cout;
    }
    
    private static Carrier getProjectSummary_New(Carrier carrier) throws QException {
        String projectId = carrier.get("fkProjectId").length() > 1
                ? carrier.get("fkProjectId")
                : getMyProjects();
        
        String line
                = "select \n"
                + "fk_project_id,\n"
                + "count(distinct m.Id) overall,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='new' and    a.task_nature='new' and a.status = 'A') as status_new,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='ongoing'  and a.task_nature='new' and  a.status = 'A') as status_ongoing,\n"
                + "(select count(distinct a.Id) from " + SessionManager.getCurrentDomain() + ".tm_backlog_task a where  a.fk_project_id = m.fk_project_id   " + getSprintLine4Task(carrier) + getLabelLine4Task(carrier) + " and a.task_status='closed'  and a.task_nature='new' and  a.status = 'A') as status_closed\n"
                + "from " + SessionManager.getCurrentDomain() + ".tm_backlog_task m\n"
                + "where m.status='A'\n"
                + getSprintLine4Task(carrier) + getLabelLine4Task(carrier)
                + " and m.task_nature='new' and  m.fk_project_id in (" + projectId + ")"
                + "group by fk_project_id;";
        
        Carrier cout = EntityManager.selectBySql(line);
        cout.renameTableName(CoreLabel.RESULT_SET, "news");
        return cout;
    }
}
