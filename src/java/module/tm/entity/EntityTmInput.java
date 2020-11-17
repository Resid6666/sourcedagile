package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmInput extends CoreEntity {

    public static String INPUT_NAME = "inputName";
    private String inputName = "";
    public static String FK_BACKLOG_ID = "fkBacklogId";
    private String fkBacklogId = "";
    public static String FK_DEPENDENT_BACKLOG_ID = "fkDependentBacklogId";
    private String fkDependentBacklogId = "";
    public static String FK_DEPENDENT_OUTPUT_ID = "fkDependentOutputId";
    private String fkDependentOutputId = "";
    public static String TABLE_NAME = "tableName";
    private String tableName = "";
    public static String INPUT_TYPE = "inputType";
    private String inputType = "";
    public static String COMPONENT_TYPE = "componentType";
    private String componentType = "";
    public static String INPUT_CONTENT = "inputContent";
    private String inputContent = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String CELL_NO = "cellNo";
    private String cellNo = "";
    public static String ALIGN = "align";
    private String align = "";
    public static String CSS_STYLE = "cssStyle";
    private String cssStyle = "";
    public static String CSS_TEMPLATE_NAME = "cssTemplateName";
    private String cssTemplateName = "";
    public static String PARAM_1 = "param1";
    private String param1 = "";
    public static String PARAM_2 = "param2";
    private String param2 = "";
    public static String PARAM_3 = "param3";
    private String param3 = "";
    public static String PARAM_4 = "param4";
    private String param4 = "";
    public static String INPUT_EVENT = "inputEvent";
    private String inputEvent = "";
    public static String ACTION = "action ";
    private String action = "";
    public static String SECTION = "section";
    private String section = "";
    public static String INPUT_PARAM = "inputParam";
    private String inputParam = "";
    public static String FK_BACKLOG_SECTION_ID = "fkBacklogSectionId";
    private String fkBacklogSectionId = "";
    public static String FK_PROJECT_ID = "fkProjectId";
    private String fkProjectId = "";
    public static String FK_RELATED_COMP_ID = "fkRelatedCompId";
    private String fkRelatedCompId = "";
    public static String SELECT_FROM_PROJECT_ID = "selectFromProjectId";
    private String selectFromProjectId = "";
    public static String SELECT_FROM_BACKLOG_ID = "selectFromBacklogId";
    private String selectFromBacklogId = "";
    public static String SELECT_FROM_INPUT_ID = "selectFromInputId";
    private String selectFromInputId = "";
    public static String SEND_TO_INPUT_ID = "sendToInputId";
    private String sendToInputId = "";
    public static String SEND_TO_BACKLOG_ID = "sendToBacklogId";
    private String sendToBacklogId = "";
    public static String SEND_TO_PROJECT_ID = "sendToProjectId";
    private String sendToProjectId = "";
    public static String SELECT_FROM_DB_ID = "selectFromDbId";
    private String selectFromDbId = "";
    public static String SELECT_FROM_TABLE_ID = "selectFromTableId";
    private String selectFromTableId = "";
    public static String SELECT_FROM_FIELD_ID = "selectFromFieldId";
    private String selectFromFieldId = "";
    public static String SEND_TO_DB_ID = "sendToDbId";
    private String sendToDbId = "";
    public static String SEND_TO_TABLE_ID = "sendToTableId";
    private String sendToTableId = "";
    public static String SEND_TO_FIELD_ID = "sendToFieldId";
    private String sendToFieldId = "";

    public String getSelectFromDbId() {
        return selectFromDbId;
    }

    public void setSelectFromDbId(String selectFromDbId) {
        this.selectFromDbId = selectFromDbId;
    }

    public String getSelectFromTableId() {
        return selectFromTableId;
    }

    public void setSelectFromTableId(String selectFromTableId) {
        this.selectFromTableId = selectFromTableId;
    }

    public String getSelectFromFieldId() {
        return selectFromFieldId;
    }

    public void setSelectFromFieldId(String selectFromFieldId) {
        this.selectFromFieldId = selectFromFieldId;
    }

    public String getSendToDbId() {
        return sendToDbId;
    }

    public void setSendToDbId(String sendToDbId) {
        this.sendToDbId = sendToDbId;
    }

    public String getSendToTableId() {
        return sendToTableId;
    }

    public void setSendToTableId(String sendToTableId) {
        this.sendToTableId = sendToTableId;
    }

    public String getSendToFieldId() {
        return sendToFieldId;
    }

    public void setSendToFieldId(String sendToFieldId) {
        this.sendToFieldId = sendToFieldId;
    }

    
    
    
    public String getSendToInputId() {
        return sendToInputId;
    }

    public void setSendToInputId(String sendToInputId) {
        this.sendToInputId = sendToInputId;
    }

    public String getSendToBacklogId() {
        return sendToBacklogId;
    }

    public void setSendToBacklogId(String sendToBacklogId) {
        this.sendToBacklogId = sendToBacklogId;
    }

    public String getSendToProjectId() {
        return sendToProjectId;
    }

    public void setSendToProjectId(String sendToProjectId) {
        this.sendToProjectId = sendToProjectId;
    }

    public String getSelectFromProjectId() {
        return selectFromProjectId;
    }

    public void setSelectFromProjectId(String selectFromProjectId) {
        this.selectFromProjectId = selectFromProjectId;
    }

    public String getSelectFromBacklogId() {
        return selectFromBacklogId;
    }

    public void setSelectFromBacklogId(String selectFromBacklogId) {
        this.selectFromBacklogId = selectFromBacklogId;
    }

    public String getSelectFromInputId() {
        return selectFromInputId;
    }

    public void setSelectFromInputId(String selectFromInputId) {
        this.selectFromInputId = selectFromInputId;
    }

    public String getFkRelatedCompId() {
        return fkRelatedCompId;
    }

    public void setFkRelatedCompId(String fkRelatedCompId) {
        this.fkRelatedCompId = fkRelatedCompId;
    }

    public String getFkProjectId() {
        return fkProjectId;
    }

    public void setFkProjectId(String fkProjectId) {
        this.fkProjectId = fkProjectId;
    }

    public String getFkBacklogSectionId() {
        return fkBacklogSectionId;
    }

    public void setFkBacklogSectionId(String fkBacklogSectionId) {
        this.fkBacklogSectionId = fkBacklogSectionId;
    }

    public String getInputEvent() {
        return inputEvent;
    }

    public void setInputEvent(String inputEvent) {
        this.inputEvent = inputEvent;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInputParam() {
        return inputParam;
    }

    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCssTemplateName() {
        return cssTemplateName;
    }

    public void setCssTemplateName(String cssTemplateName) {
        this.cssTemplateName = cssTemplateName;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    public String getInputContent() {
        return inputContent;
    }

    public void setInputContent(String inputContent) {
        this.inputContent = inputContent;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getFkBacklogId() {
        return fkBacklogId;
    }

    public void setFkBacklogId(String fkBacklogId) {
        this.fkBacklogId = fkBacklogId;
    }

    public String getFkDependentBacklogId() {
        return fkDependentBacklogId;
    }

    public void setFkDependentBacklogId(String fkDependentBacklogId) {
        this.fkDependentBacklogId = fkDependentBacklogId;
    }

    public String getFkDependentOutputId() {
        return fkDependentOutputId;
    }

    public void setFkDependentOutputId(String fkDependentOutputId) {
        this.fkDependentOutputId = fkDependentOutputId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

}
