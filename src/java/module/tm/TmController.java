/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.tm;

import controllerpool.ControllerPool;
import module.cr.entity.*;
import module.tm.entity.EntityTmBacklog;
import module.tm.entity.EntityTmBacklogDependency;
import module.tm.entity.EntityTmBacklogTask;
import module.tm.entity.EntityTmInput;
import module.tm.entity.EntityTmInputDescription;
import module.tm.entity.EntityTmTaskLabel;
import module.tm.entity.EntityTmNetwork;
import module.tm.entity.EntityTmProgress;
import module.tm.entity.EntityTmProject;
import module.tm.entity.EntityTmProjectCanvasCard;
import module.tm.entity.EntityTmProjectCanvasZone;
import module.tm.entity.EntityTmProjectPermission;
import module.tm.entity.EntityTmTask;
import module.tm.entity.EntityTmTaskCategory;
import module.tm.entity.EntityTmTaskComment;
import module.tm.entity.EntityTmTaskPriority;
import module.tm.entity.EntityTmTaskSprint;
import module.tm.entity.EntityTmTaskStatus;
import module.tm.entity.EntityTmTaskType;
import module.tm.entity.EntityTmTestScenario;
import module.tm.entity.EntityTmTestTrial;
import utility.Carrier;
import utility.QException;

/**
 *
 * @author user
 */
public class TmController {

    private final String SPACE = " ";
    private final String COMMA = ",";

    public Carrier insertNewCanvasCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmProjectCanvasCard.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectCanvasCard.FK_PROJECT_ID));
        carrier.addController(EntityTmProjectCanvasCard.FK_ZONE_ID,
                cp.hasValue(carrier, EntityTmProjectCanvasCard.FK_ZONE_ID));
        carrier.addController(EntityTmProjectCanvasCard.CARD_NAME,
                cp.hasValue(carrier, EntityTmProjectCanvasCard.CARD_NAME));
        return carrier;

    }

    public Carrier updateCanvasCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmProjectCanvasCard.CARD_NAME,
                cp.hasValue(carrier, EntityTmProjectCanvasCard.CARD_NAME));
        return carrier;

    }

    public Carrier assignLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController("fkLabelId", cp.hasValue(carrier, "fkLabelId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("assign", cp.hasValue(carrier, "assign"));

        return carrier;

    }

    public Carrier assignSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController("fkSprintId", cp.hasValue(carrier, "fkSprintId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("assign", cp.hasValue(carrier, "assign"));

        return carrier;

    }

    public Carrier deleteCanvasCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getCanvasCardList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmProjectCanvasCard.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectCanvasCard.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier addBacklogtoCanvasCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkCanvasCardId", cp.hasValue(carrier, "fkCanvasCardId"));
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));

        return carrier;
    }

    public Carrier insertNewCanvasZone(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmProjectCanvasZone.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.FK_PROJECT_ID));
        carrier.addController(EntityTmProjectCanvasZone.ZONE_NAME,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.ZONE_NAME));
        carrier.addController(EntityTmProjectCanvasZone.ZONE_COLOR,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.ZONE_COLOR));
        return carrier;

    }

    public Carrier updateCanvasZone(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmProjectCanvasZone.ZONE_NAME,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.ZONE_NAME));
        carrier.addController(EntityTmProjectCanvasZone.ZONE_COLOR,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.ZONE_COLOR));
        return carrier;

    }

    public Carrier deleteCanvasZone(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getCanvasZoneList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmProjectCanvasZone.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectCanvasZone.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier test(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewNetwork(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmNetwork.NETWORK_NAME,
                cp.hasValue(carrier, EntityTmNetwork.NETWORK_NAME));
        return carrier;

    }

    public Carrier updateNetwork(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmNetwork.NETWORK_NAME,
                cp.hasValue(carrier, EntityTmNetwork.NETWORK_NAME));
        return carrier;

    }

    public Carrier deleteNetwork(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getNetworkList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTaskType(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTaskType.TYPE_NAME,
                cp.hasValue(carrier, EntityTmTaskType.TYPE_NAME));
        return carrier;

    }

    public Carrier updateTaskType(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskType.TYPE_NAME,
                cp.hasValue(carrier, EntityTmTaskType.TYPE_NAME));
        return carrier;

    }

    public Carrier deleteTaskType(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTaskTypeList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmProject.PROJECT_NAME,
                cp.hasValue(carrier, EntityTmProject.PROJECT_NAME));
        carrier.addController(EntityTmProject.PROJECT_CODE,
                cp.hasValue(carrier, EntityTmProject.PROJECT_CODE));
        return carrier;

    }

    public Carrier updateProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmProject.PROJECT_NAME,
                cp.hasValue(carrier, EntityTmProject.PROJECT_NAME));
        return carrier;

    }

    public Carrier deleteProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getProjectList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getProjectList4Modal(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getBacklogTablenameList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier addTableNameToInputs(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputIds", cp.hasValue(carrier, "fkInputIds"));
        carrier.addController("tableName", cp.hasValue(carrier, "tableName"));
        return carrier;
    }
    
    

    public Carrier getProjectInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier insertNewProgress(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmProgress.PROGRESS_CODE,
                cp.hasValue(carrier, EntityTmProgress.PROGRESS_CODE));
        carrier.addController(EntityTmProgress.PROGRESS_NAME,
                cp.hasValue(carrier, EntityTmProgress.PROGRESS_NAME));
        return carrier;

    }

    public Carrier updateProgress(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmProgress.PROGRESS_CODE,
                cp.hasValue(carrier, EntityTmProgress.PROGRESS_CODE));
        carrier.addController(EntityTmProgress.PROGRESS_NAME,
                cp.hasValue(carrier, EntityTmProgress.PROGRESS_NAME));
        return carrier;

    }

    public Carrier deleteProgress(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getProgressList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTaskStatus(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTaskStatus.STATUS_CODE,
                cp.hasValue(carrier, EntityTmTaskStatus.STATUS_CODE));
        carrier.addController(EntityTmTaskStatus.STATUS_NAME,
                cp.hasValue(carrier, EntityTmTaskStatus.STATUS_NAME));
        return carrier;

    }

    public Carrier updateTaskStatus(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskStatus.STATUS_CODE,
                cp.hasValue(carrier, EntityTmTaskStatus.STATUS_CODE));
        carrier.addController(EntityTmTaskStatus.STATUS_NAME,
                cp.hasValue(carrier, EntityTmTaskStatus.STATUS_NAME));
        return carrier;

    }

    public Carrier deleteTaskStatus(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTaskStatusList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTaskPriority(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTaskPriority.PRIORITY_CODE,
                cp.hasValue(carrier, EntityTmTaskPriority.PRIORITY_CODE));
        carrier.addController(EntityTmTaskPriority.PRIORITY_NAME,
                cp.hasValue(carrier, EntityTmTaskPriority.PRIORITY_NAME));
        return carrier;

    }

    public Carrier updateTaskPriority(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskPriority.PRIORITY_CODE,
                cp.hasValue(carrier, EntityTmTaskPriority.PRIORITY_CODE));
        carrier.addController(EntityTmTaskPriority.PRIORITY_NAME,
                cp.hasValue(carrier, EntityTmTaskPriority.PRIORITY_NAME));
        return carrier;

    }

    public Carrier deleteTaskPriority(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTaskPriorityList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTaskCategory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTaskCategory.CATEGORY_CODE,
                cp.hasValue(carrier, EntityTmTaskCategory.CATEGORY_CODE));
        carrier.addController(EntityTmTaskCategory.CATEGORY_NAME,
                cp.hasValue(carrier, EntityTmTaskCategory.CATEGORY_NAME));
        return carrier;

    }

    public Carrier updateTaskCategory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskCategory.CATEGORY_CODE,
                cp.hasValue(carrier, EntityTmTaskCategory.CATEGORY_CODE));
        carrier.addController(EntityTmTaskCategory.CATEGORY_NAME,
                cp.hasValue(carrier, EntityTmTaskCategory.CATEGORY_NAME));
        return carrier;

    }

    public Carrier deleteTaskCategory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTaskCategoryList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTask.NAME,
                cp.hasValue(carrier, EntityTmTask.NAME));
        carrier.addController(EntityTmTask.FK_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmTask.FK_BACKLOG_ID));
        carrier.addController(EntityTmTask.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmTask.FK_PROJECT_ID));
        carrier.addController(EntityTmTask.FK_TASK_TYPE_ID,
                cp.hasValue(carrier, EntityTmTask.FK_TASK_TYPE_ID));
        carrier.addController(EntityTmTask.FK_TASK_STATUS_ID,
                cp.hasValue(carrier, EntityTmTask.FK_TASK_STATUS_ID));
        carrier.addController("fkTaskAssigneeId",
                cp.hasValue(carrier, "fkTaskAssigneeId"));
        return carrier;

    }

    public Carrier finishTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTask.ID, cp.hasValue(carrier, EntityTmTask.ID));
        carrier.addController(EntityTmTask.COMPLETED_DURATION,
                cp.hasValue(carrier, EntityTmTask.COMPLETED_DURATION));
        carrier.addController(EntityTmTask.COMPLETED_DURATION,
                cp.isFloat(carrier, EntityTmTask.COMPLETED_DURATION));
        return carrier;
    }

    public Carrier forwardTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTask.ID, cp.hasValue(carrier, EntityTmTask.ID));
        carrier.addController("fkTaskAssigneeId",
                cp.hasValue(carrier, "fkTaskAssigneeId"));
        carrier.addController("reason",
                cp.hasValue(carrier, "reason"));
        return carrier;
    }

    public Carrier addReporter(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTask.ID, cp.hasValue(carrier, EntityTmTask.ID));
        carrier.addController("fkTaskReporterId",
                cp.hasValue(carrier, "fkTaskReporterId"));
        return carrier;
    }

    public Carrier removeReporter(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTask.ID, cp.hasValue(carrier, EntityTmTask.ID));
        carrier.addController("fkTaskReporterId",
                cp.hasValue(carrier, "fkTaskReporterId"));
        return carrier;
    }

    public Carrier updateTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTask.NAME,
                cp.hasValue(carrier, EntityTmTask.NAME));
        carrier.addController(EntityTmTask.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmTask.FK_PROJECT_ID));
        carrier.addController(EntityTmTask.FK_TASK_TYPE_ID,
                cp.hasValue(carrier, EntityTmTask.FK_TASK_TYPE_ID));
        carrier.addController(EntityTmTask.FK_TASK_STATUS_ID,
                cp.hasValue(carrier, EntityTmTask.FK_TASK_STATUS_ID));
        carrier.addController(EntityTmTask.FK_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmTask.FK_BACKLOG_ID));

        carrier.addController("fkTaskAssigneeId",
                cp.hasValue(carrier, "fkTaskAssigneeId"));
        return carrier;

    }

    public Carrier deleteTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTaskList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewProjectPermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmProjectPermission.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectPermission.FK_PROJECT_ID));
        carrier.addController(EntityTmProjectPermission.FK_USER_ID,
                cp.hasValue(carrier, EntityTmProjectPermission.FK_USER_ID));
        return carrier;

    }

    public Carrier updateProjectPermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmProjectPermission.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmProjectPermission.FK_PROJECT_ID));
        carrier.addController(EntityTmProjectPermission.FK_USER_ID,
                cp.hasValue(carrier, EntityTmProjectPermission.FK_USER_ID));
        return carrier;

    }

    public Carrier deleteProjectPermission(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getProjectPermissionList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getProjectPermissionList4Report(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getAssigneeByProjectId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier getReporterByProjectId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier getReporterByTasktId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier singleUpdate(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "compId"));
        carrier.addController("general", cp.hasValue(carrier, "value"));
        carrier.addController("general", cp.hasValue(carrier, "taskId"));
        return carrier;
    }

    public Carrier insertNewUserStory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.BACKLOG_NAME,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_NAME));
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        carrier.addController(EntityTmBacklog.BACKLOG_STATUS,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_STATUS));
        return carrier;
    }

    public Carrier updateUserStsory4Status(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.ID,
                cp.hasValue(carrier, EntityTmBacklog.ID));
        carrier.addController("backlogNo", cp.hasValue(carrier, "backlogNo"));
        carrier.addController(EntityTmBacklog.BACKLOG_STATUS,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_STATUS));
        return carrier;
    }

    public Carrier updateTask4Status(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.ID,
                cp.hasValue(carrier, EntityTmBacklog.ID));
        carrier.addController("orderNo", cp.hasValue(carrier, "orderNo"));
        carrier.addController("taskStatus", cp.hasValue(carrier, "taskStatus"));
        return carrier;
    }

    public Carrier updateUserStsory4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.ID,
                cp.hasValue(carrier, EntityTmBacklog.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.hasValue(carrier, "value"));

        return carrier;
    }

    public Carrier selectUsersByProject4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));

        return carrier;
    }

    public Carrier updateTask4ShortChange(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.ID,
                cp.hasValue(carrier, EntityTmBacklog.ID));
        carrier.addController("type", cp.hasValue(carrier, "type"));
        carrier.addController("value", cp.hasValue(carrier, "value"));

        return carrier;
    }

    public Carrier insertNewBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.BACKLOG_NAME,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_NAME));
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        carrier.addController(EntityTmBacklog.PRIORITY,
                cp.hasValue(carrier, EntityTmBacklog.PRIORITY));
        return carrier;
    }

    public Carrier insertNewBacklogShort(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.BACKLOG_NAME,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_NAME));
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier insertNewTicket(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.BACKLOG_NAME,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_NAME));
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        carrier.addController(EntityTmBacklog.PRIORITY,
                cp.hasValue(carrier, EntityTmBacklog.PRIORITY));
        return carrier;
    }

    public Carrier updateLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskLabel.NAME, cp.hasValue(carrier, EntityTmTaskLabel.NAME));
        carrier.addController(EntityTmTaskLabel.COLOR, cp.hasValue(carrier, EntityTmTaskLabel.COLOR));

        return carrier;

    }

    public Carrier deleteLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getLabelList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskLabel.NAME, cp.hasValue(carrier, EntityTmTaskLabel.NAME));
        carrier.addController(EntityTmTaskLabel.COLOR, cp.hasValue(carrier, EntityTmTaskLabel.COLOR));
        return carrier;
    }

    public Carrier updateBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmBacklog.BACKLOG_NAME,
                cp.hasValue(carrier, EntityTmBacklog.BACKLOG_NAME));

        return carrier;

    }

    public static Carrier updateBacklogByDesc(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public static Carrier updateBacklogBySourced(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public static Carrier bindTicketToSUS(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityCrListItem.ID, cp.hasValue("assignee"));
        return carrier;
    }

    public static Carrier notifyTicketAsBug(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("general", cp.hasValue(carrier, "fkSourcedId"));
        return carrier;
    }

    public Carrier deleteBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getBacklogList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getBacklogList4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getBacklogListByListView(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getBacklogListByListView4Pivot(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getSourcedBacklogListWithTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getSourcedBacklogList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getSourcedBacklogList4Combo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getSourcedBacklogListWithSection4Combo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getBacklogList4Combo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklog.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier getBacklogInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getBacklogCoreInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier insertNewSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskSprint.SPRINT_NAME, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_NAME));
        carrier.addController(EntityTmTaskSprint.SPRINT_START_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_START_DATE));
        carrier.addController(EntityTmTaskSprint.SPRINT_END_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_END_DATE));
        carrier.addController(EntityTmTaskSprint.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmTaskSprint.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier updateSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskSprint.SPRINT_NAME, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_NAME));
        carrier.addController(EntityTmTaskSprint.SPRINT_START_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_START_DATE));
        carrier.addController(EntityTmTaskSprint.SPRINT_END_DATE, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_END_DATE));
//        carrier.addController(EntityTmTaskSprint.SPRINT_COLOR, cp.hasValue(carrier, EntityTmTaskSprint.SPRINT_COLOR));
//        carrier.addController(EntityTmTaskSprint.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmTaskSprint.FK_PROJECT_ID));

        return carrier;

    }

    public Carrier deleteSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getSprintList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getStoryInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklog.ID, cp.hasValue(carrier, EntityTmBacklog.ID));
        return carrier;
    }

    public Carrier insertNewComment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskComment.COMMENT, cp.hasValue(carrier, EntityTmTaskComment.COMMENT));
//        carrier.addController(EntityTmTaskComment.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmTaskComment.FK_BACKLOG_ID));
        return carrier;
    }

    public Carrier updateComment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTaskComment.COMMENT, cp.hasValue(carrier, EntityTmTaskComment.COMMENT));
        carrier.addController(EntityTmTaskComment.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmTaskComment.FK_BACKLOG_ID));

        return carrier;

    }

    public Carrier deleteComment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getCommentList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getCommentListByBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskComment.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmTaskComment.FK_BACKLOG_ID));
        return carrier;
    }

    public Carrier getCommentListByTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTaskComment.FK_TASK_ID, cp.hasValue(carrier, EntityTmTaskComment.FK_TASK_ID));
        return carrier;
    }

    public Carrier insertNewInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController(EntityTmInput.INPUT_NAME, cp.hasValue(carrier, EntityTmInput.INPUT_NAME));
        carrier.addController(EntityTmInput.INPUT_TYPE, cp.hasValue(carrier, EntityTmInput.INPUT_TYPE));
        return carrier;
    }

    public Carrier addTableAsInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController(EntityTmInput.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmInput.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier addTabAsInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController(EntityTmInput.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmInput.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier insertNewInput4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController(EntityTmInput.INPUT_NAME, cp.hasValue(carrier, EntityTmInput.INPUT_NAME));
        carrier.addController(EntityTmInput.INPUT_TYPE, cp.hasValue(carrier, EntityTmInput.INPUT_TYPE));
        return carrier;
    }

    public Carrier addColumnsAsInputToTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkInputTableId", cp.hasValue(carrier, "fkInputTableId"));
        return carrier;
    }
    public Carrier removeInputTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.hasValue(carrier, "fkInputId"));
        carrier.addController("fkInputTableId", cp.hasValue(carrier, "fkInputTableId"));
        return carrier;
    }
    
    

    public Carrier updateRowCountInputTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("rowCount", cp.hasValue(carrier, "rowCount"));
        carrier.addController("fkInputTableId", cp.hasValue(carrier, "fkInputTableId"));
        return carrier;
    }

    public Carrier setInputTableReadFromContent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputTableId", cp.hasValue(carrier, "fkInputTableId"));
        return carrier;
    }
    
    public Carrier showInputTableColumnComponent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputTableId", cp.hasValue(carrier, "fkInputTableId"));
        carrier.addController("fkInputId", cp.hasValue(carrier, "fkInputId"));
        return carrier;
    }

    public Carrier supplementOfInsertNewInput4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.hasValue(carrier, "fkInputId"));
        return carrier;
    }

    public Carrier addInputDescToTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputDescriptionId", cp.hasValue(carrier, "fkInputDescriptionId"));
        return carrier;
    }

    public Carrier updateInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmInput.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_BACKLOG_ID));
        carrier.addController(EntityTmInput.INPUT_NAME, cp.hasValue(carrier, EntityTmInput.INPUT_NAME));
        carrier.addController(EntityTmInput.INPUT_TYPE, cp.hasValue(carrier, EntityTmInput.INPUT_TYPE));
        return carrier;
    }

    public Carrier updateInputByComponentType(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmInput.COMPONENT_TYPE, cp.hasValue(carrier, EntityTmInput.COMPONENT_TYPE));
        return carrier;
    }

    public Carrier updateInputByEvent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("event", cp.hasValue(carrier, "event"));
        return carrier;
    }

    public Carrier updateInputByAction(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("eveactionnt", cp.hasValue(carrier, "action"));
        return carrier;
    }

    public Carrier updateInputBySection(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("section", cp.hasValue(carrier, "section"));
        return carrier;
    }

    public Carrier updateInputByComponentCellNo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("general", cp.hasValue(carrier, EntityTmInput.CELL_NO));
        return carrier;
    }

    public Carrier updateInputByComponentOrderNo(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("general", cp.hasValue(carrier, "orderNo"));
        return carrier;
    }

    public Carrier updateInputByContent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmInput.INPUT_CONTENT, cp.hasValue(carrier, EntityTmInput.INPUT_CONTENT));
        return carrier;
    }

    public Carrier updateInputByParam1(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmInput.INPUT_CONTENT, cp.hasValue(carrier, EntityTmInput.PARAM_1));
        return carrier;
    }

    public Carrier updateInputBySectionBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmInput.INPUT_CONTENT, cp.hasValue(carrier, EntityTmInput.PARAM_1));
        return carrier;
    }

    public Carrier updateInputByTableName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier updateInputByInputName(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmInput.INPUT_NAME, cp.hasValue(carrier, EntityTmInput.INPUT_NAME));
        return carrier;
    }

    public Carrier updateInputByDependentBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmInput.FK_DEPENDENT_BACKLOG_ID, cp.hasValue(carrier, EntityTmInput.FK_DEPENDENT_BACKLOG_ID));
        return carrier;
    }

    public Carrier updateInputByDependentBacklogOutput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmInput.FK_DEPENDENT_OUTPUT_ID, cp.hasValue(carrier, EntityTmInput.FK_DEPENDENT_OUTPUT_ID));
//        carrier.addController(EntityTmInput.FK_DEPENDENT_BACKLOG_ID , cp.hasValue(carrier, EntityTmInput.FK_DEPENDENT_BACKLOG_ID));
        return carrier;
    }

    public Carrier deleteInputByDependentBacklogOutput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier deleteInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getInputList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
//        carrier.addController("fkBacklogId", cp.hasValue(carrier,"fkBacklogId"));
        return carrier;
    }

    public Carrier getInputList4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier getInputDescriptionList4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier getInputChildDependenceDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkInputId", cp.hasValue(carrier, "fkInputId"));
        return carrier;
    }

    public Carrier insertNewInputDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmInputDescription.FK_INPUT_ID, cp.hasValue(carrier, EntityTmInputDescription.FK_INPUT_ID));
        carrier.addController(EntityTmInputDescription.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmInputDescription.FK_PROJECT_ID));
        carrier.addController(EntityTmInputDescription.DESCRIPTION, cp.isKeyExist(carrier, EntityTmInputDescription.DESCRIPTION));
        return carrier;
    }

    public Carrier updateInputDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmInputDescription.DESCRIPTION, cp.isKeyExist(carrier, EntityTmInputDescription.DESCRIPTION));
        return carrier;
    }

    public Carrier deleteInputDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getInputDescriptionList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, "fkInputId"));
        return carrier;
    }

    public Carrier insertNewBacklogTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklogTask.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_BACKLOG_ID));
        carrier.addController(EntityTmBacklogTask.FK_TASK_TYPE_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_TASK_TYPE_ID));
        carrier.addController(EntityTmBacklogTask.FK_ASSIGNEE_ID, cp.isKeyExist(carrier, EntityTmBacklogTask.FK_ASSIGNEE_ID));
        carrier.addController(EntityTmBacklogTask.ESTIMATED_HOURS, cp.isKeyExist(carrier, EntityTmBacklogTask.ESTIMATED_HOURS));
        return carrier;
    }

    public Carrier insertNewBacklogTask4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklogTask.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_PROJECT_ID));
        carrier.addController(EntityTmBacklogTask.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_BACKLOG_ID));
        carrier.addController("taskName", cp.hasValue(carrier, "taskName"));
        return carrier;
    }

    public Carrier getTaskList4Short(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmBacklogTask.FK_PROJECT_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_PROJECT_ID));
        return carrier;
    }

    public Carrier updateBacklogTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmBacklogTask.FK_TASK_TYPE_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_TASK_TYPE_ID));
        carrier.addController(EntityTmBacklogTask.FK_ASSIGNEE_ID, cp.isKeyExist(carrier, EntityTmBacklogTask.FK_ASSIGNEE_ID));
        carrier.addController(EntityTmBacklogTask.ESTIMATED_HOURS, cp.isKeyExist(carrier, EntityTmBacklogTask.ESTIMATED_HOURS));
        carrier.addController(EntityTmBacklogTask.SPENT_HOURS, cp.isKeyExist(carrier, EntityTmBacklogTask.SPENT_HOURS));
        return carrier;
    }

    public Carrier cloneBacklogTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmBacklogTask.FK_BACKLOG_ID, cp.hasValue(carrier, EntityTmBacklogTask.FK_BACKLOG_ID));
        return carrier;
    }

    public Carrier updateBacklogTaskOnStatusOngoing(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
//        carrier.addController(EntityTmBacklogTask.TASK_STATUS, cp.isKeyExist(carrier, EntityTmBacklogTask.TASK_STATUS));
        return carrier;
    }

    public Carrier updateBacklogTaskOnCloseTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmBacklogTask.SPENT_HOURS, cp.isKeyExist(carrier, EntityTmBacklogTask.SPENT_HOURS));
        carrier.addController(EntityTmBacklogTask.SPENT_HOURS, cp.isFloat(carrier, EntityTmBacklogTask.SPENT_HOURS));
        return carrier;
    }

    public Carrier notifyBug(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("general", cp.isKeyExist(carrier, "description"));
        return carrier;
    }

    public Carrier getBoundedUserStories(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getProjectCountList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier notifyUpdate(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController("general", cp.isKeyExist(carrier, "description"));
        return carrier;
    }

    public Carrier deleteBacklogTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;
    }

    public Carrier getBacklogHistoryGroupList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier getBacklogTaskList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getBacklogHistoryList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier insertNewBacklogLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.isKeyExist(carrier, "fkBacklogId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkProjectId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkLabelId"));
        return carrier;
    }

    public Carrier deleteBacklogLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.isKeyExist(carrier, "fkBacklogId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkLabelId"));
        return carrier;
    }

    public Carrier insertNewBacklogSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.isKeyExist(carrier, "fkBacklogId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkProjectId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkSprintId"));
        return carrier;
    }

    public Carrier deleteBacklogSprint(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.isKeyExist(carrier, "fkBacklogId"));
        carrier.addController("general", cp.isKeyExist(carrier, "fkSprintId"));
        return carrier;
    }

    public Carrier getNotificationCountByUser(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier getNotificationListByUser(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public static Carrier getProjectReport(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.isKeyExist(carrier, "project"));
        return carrier;
    }

    public static Carrier getSpentHoursReport(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier insertNewBacklogDependency(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmBacklogDependency.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmBacklogDependency.FK_PROJECT_ID));
        carrier.addController(EntityTmBacklogDependency.FK_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmBacklogDependency.FK_BACKLOG_ID));
        carrier.addController(EntityTmBacklogDependency.FK_PARENT_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmBacklogDependency.FK_PARENT_BACKLOG_ID));
        return carrier;

    }

    public Carrier deleteBacklogDependency(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier deleteTaskCommentFile(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "filename"));
        return carrier;

    }

    public Carrier getBacklogDependencyList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getBacklogDependencyList4Select(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier getBacklogDependencyList4Gui(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier toggleCommentAsSubtask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier toggleCommentAsBug(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier toggleCommentAsRequest(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier getBakclogListByLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkLabelId"));
        return carrier;
    }

    public Carrier getAllBakclogListByLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier insertNewTestScenario(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTestScenario.TEST_CASE,
                cp.hasValue(carrier, EntityTmTestScenario.TEST_CASE));
        carrier.addController(EntityTmTestScenario.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmTestScenario.FK_PROJECT_ID));
        carrier.addController(EntityTmTestScenario.FK_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmTestScenario.FK_BACKLOG_ID));
        carrier.addController(EntityTmTestScenario.SCENARIO_NAME,
                cp.hasValue(carrier, EntityTmTestScenario.SCENARIO_NAME));
        return carrier;

    }

    public Carrier updateTestScenario(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTestScenario.TEST_CASE,
                cp.hasValue(carrier, EntityTmTestScenario.TEST_CASE));
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        carrier.addController(EntityTmTestScenario.FK_PROJECT_ID,
                cp.hasValue(carrier, EntityTmTestScenario.FK_PROJECT_ID));
        carrier.addController(EntityTmTestScenario.FK_BACKLOG_ID,
                cp.hasValue(carrier, EntityTmTestScenario.FK_BACKLOG_ID));
        carrier.addController(EntityTmTestScenario.SCENARIO_NAME,
                cp.hasValue(carrier, EntityTmTestScenario.SCENARIO_NAME));
        return carrier;

    }

    public Carrier deleteTestScenario(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityCrListItem.ID, cp.hasValue(carrier, EntityCrListItem.ID));
        return carrier;

    }

    public Carrier getTestScenarioList(Carrier carrier) throws QException {
        return carrier;
    }

    public Carrier insertNewTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTestTrial.FK_SCENARIO_ID,
                cp.hasValue(carrier, EntityTmTestTrial.FK_SCENARIO_ID));
        carrier.addController(EntityTmTestTrial.ACTUAL_RESULT,
                cp.hasValue(carrier, EntityTmTestTrial.ACTUAL_RESULT));
        carrier.addController(EntityTmTestTrial.TRIAL_STATUS,
                cp.hasValue(carrier, EntityTmTestTrial.TRIAL_STATUS));
        return carrier;

    }

    public Carrier updateTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTestTrial.ID,
                cp.hasValue(carrier, EntityTmTestTrial.ID));
        carrier.addController(EntityTmTestTrial.ACTUAL_RESULT,
                cp.hasValue(carrier, EntityTmTestTrial.ACTUAL_RESULT));
        carrier.addController(EntityTmTestTrial.TRIAL_STATUS,
                cp.hasValue(carrier, EntityTmTestTrial.TRIAL_STATUS));
        return carrier;

    }

    public Carrier hasNotOKTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTestTrial.FK_SCENARIO_ID,
                cp.hasValue(carrier, EntityTmTestTrial.FK_SCENARIO_ID));
        return carrier;

    }

    public Carrier deleteTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityTmTestTrial.ID,
                cp.hasValue(carrier, EntityTmTestTrial.ID));
        return carrier;

    }

    public Carrier getTrialListByScenario(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTestTrial.FK_SCENARIO_ID,
                cp.hasValue(carrier, EntityTmTestTrial.FK_SCENARIO_ID));
        return carrier;
    }

    public Carrier getTrialListById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTestTrial.ID,
                cp.hasValue(carrier, EntityTmTestTrial.ID));
        return carrier;
    }

    public Carrier deleteTrialFile(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityTmTestTrial.ID,
                cp.hasValue(carrier, "filename"));
        carrier.addController(EntityTmTestTrial.ID,
                cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier getTaskListByTrialId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkTrialId"));
        return carrier;
    }

    public Carrier getTaskListByBacklogId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier notifyTrialAsBug(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkTrialId"));
        carrier.addController("general",
                cp.hasValue(carrier, "fkTaskId"));
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier closeBug4TestTrial(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkCommentId"));

        return carrier;

    }

    public Carrier showNotifiedInfoByTrialId(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "id"));

        return carrier;
    }

    public Carrier dublicateUserStories(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("general", cp.hasValue(carrier, "action"));
        carrier.addController("general", cp.hasValue(carrier, "currentProjectId"));
        carrier.addController("general", cp.hasValue(carrier, "destProjectId"));

        return carrier;
    }

    public Carrier assignPriorityToUserStory(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("general",
                cp.hasValue(carrier, "priority"));

        return carrier;
    }

    public Carrier getBacklogDetailedInputInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));

        return carrier;
    }

    public Carrier getUserStoryInfoById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier getUserStoryInfo4HistoryDateAndLabelById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier copyInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkInputId"));
        carrier.addController("general", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("general", cp.hasValue(carrier, "fkDestinationBacklogId"));
        return carrier;
    }

    public Carrier moveInput(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkInputId"));
        carrier.addController("general", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("general", cp.hasValue(carrier, "fkDestinationBacklogId"));
        return carrier;
    }

    public Carrier deleteInputs(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkInputId"));
        return carrier;
    }

    public Carrier notifyAsChangeRequest(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "fkHistoryId"));
        carrier.addController("general",
                cp.hasValue(carrier, "fkTaskId"));
        carrier.addController("general",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier startTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier stopTask(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier getDateDiffInHours(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fromDate"));
        carrier.addController("general", cp.hasValue(carrier, "toDate"));
        carrier.addController("general", cp.hasValue(carrier, "fromTime"));
        carrier.addController("general", cp.hasValue(carrier, "toTime"));

        return carrier;
    }

    public Carrier getBacklogListWithInputs(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general", cp.hasValue(carrier, "fkProjectId"));

        return carrier;
    }

    public Carrier addTestCaseLink(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        return carrier;
    }

    public Carrier loadLinkedTestCases(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        return carrier;
    }

    public Carrier deleteTestCaseLink(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkTestCaseId", cp.hasValue(carrier, "fkTestCaseId"));
        return carrier;
    }

    public Carrier setStatusOngoing4Comment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier setStatusNew4Comment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier setEstimatedHours4Comment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("estimatedHours", cp.hasValue(carrier, "estimatedHours"));
        return carrier;
    }

    public Carrier setSpentHours4Comment(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("spentHours", cp.hasValue(carrier, "spentHours"));
        return carrier;
    }

    public Carrier getBacklogHistoryByDate(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
//        carrier.addController("startDate", cp.hasValue(carrier, "startDate"));
//        carrier.addController("startTime", cp.hasValue(carrier, "startTime"));
//        carrier.addController("endDate", cp.hasValue(carrier, "endDate"));
//        carrier.addController("endTime", cp.hasValue(carrier, "endTime"));
        return carrier;
    }

    public Carrier getHistoryTimesByDate(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("date", cp.hasValue(carrier, "date"));
        return carrier;
    }

    public Carrier setLabelAsChangeRequest(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        carrier.addController("fkLabelId", cp.isKeyExist(carrier, "fkLabelId"));
//        carrier.addController("startDate", cp.hasValue(carrier, "startDate"));
//        carrier.addController("startTime", cp.hasValue(carrier, "startTime"));
//        carrier.addController("endDate", cp.hasValue(carrier, "endDate"));
//        carrier.addController("endTime", cp.hasValue(carrier, "endTime"));
//        carrier.addController("description", cp.hasValue(carrier, "description"));
        return carrier;
    }

    public Carrier loadAssignedLabel(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier loadAssignedLabelByProject(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier getAssignedLabelById(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier getAssignedLabelByDates(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("startDate", cp.isKeyExist(carrier, "startDate"));
        carrier.addController("startTime", cp.isKeyExist(carrier, "startTime"));
        carrier.addController("endDate", cp.isKeyExist(carrier, "endDate"));
        carrier.addController("endTime", cp.isKeyExist(carrier, "endTime"));
        return carrier;
    }

    public Carrier notifyLabelAsChangeRequest(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkLabelId",
                cp.isKeyExist(carrier, "fkLabelId"));
        carrier.addController("fkAssigneeId",
                cp.hasValue(carrier, "fkAssigneeId"));
        carrier.addController("fkBacklogId",
                cp.hasValue(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier getAnalyticalReport(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("lastPeriod", cp.hasValue(carrier, "lastPeriod"));
        carrier.addController("lastDay", cp.hasValue(carrier, "lastDay"));
        carrier.addController("projectList", cp.hasValue(carrier, "projectList"));
        carrier.addController("assigneeList", cp.isKeyExist(carrier, "assigneeList"));
        carrier.addController("sprintList", cp.isKeyExist(carrier, "sprintList"));
        carrier.addController("taskTypeList", cp.isKeyExist(carrier, "taskTypeList"));
        carrier.addController("priorityList", cp.isKeyExist(carrier, "priorityList"));
        carrier.addController("groupBy", cp.isKeyExist(carrier, "groupBy"));
        carrier.addController("labelList", cp.isKeyExist(carrier, "labelList"));

        return carrier;
    }

    public Carrier setUserStoryAsAPIFeature(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.isKeyExist(carrier, "fkBacklogId"));
        carrier.addController("isApi", cp.isKeyExist(carrier, "isApi"));
        return carrier;
    }

    public Carrier createEpic(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.isKeyExist(carrier, "fkBacklogId"));
        return carrier;
    }

    public Carrier syncWithJira(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier updateBacklogByCanvasContent(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        return carrier;
    }

    public Carrier getBacklogList4AllGui(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier saveFormAction(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        return carrier;
    }

    public Carrier deleteFromTable(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("index", cp.isKeyExist(carrier, "index"));
        return carrier;
    }

    public Carrier addInputDescCriterias(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("checkedInputIds", cp.isKeyExist(carrier, "checkedInputIds"));
        return carrier;
    }

    public Carrier deleteBacklogFile(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        return carrier;
    }

    public Carrier pinImageToStoryCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        return carrier;
    }

    public Carrier unpinImageToStoryCard(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.isKeyExist(carrier, "id"));
        return carrier;
    }

    public Carrier getBakclogLabelList4Select(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkProjectId", cp.isKeyExist(carrier, "fkProjectId"));
        return carrier;
    }

    public Carrier addFileToBacklog(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("fileUrl", cp.hasValue(carrier, "fileUrl"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));

        return carrier;
    }

    public Carrier insertNewBacklogDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));
        carrier.addController("description", cp.hasValue(carrier, "description"));
        carrier.addController("fkProjectId", cp.hasValue(carrier, "fkProjectId"));

        return carrier;
    }

    public Carrier colorBacklogDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier colorInputDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

    public Carrier getBacklogDescriptionList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkBacklogId", cp.hasValue(carrier, "fkBacklogId"));

        return carrier;
    }

    public Carrier updatetBacklogDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        carrier.addController("description", cp.hasValue(carrier, "description"));
        return carrier;
    }

    public Carrier deleteBacklogDescription(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("id", cp.hasValue(carrier, "id"));
        return carrier;
    }

}
