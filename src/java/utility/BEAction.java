package utility;

import controllerpool.ControllerPool;
import java.util.Arrays;
import java.util.Locale;
import label.CoreLabel;
import module.io.IoModel;
import module.tm.entity.EntityTmBacklog;
import module.tm.entity.EntityTmBacklogDescription;
import module.tm.entity.EntityTmDatabase;
import module.tm.entity.EntityTmField;
import module.tm.entity.EntityTmInput;
import module.tm.entity.EntityTmInputDescription;
import module.tm.entity.EntityTmJsCode;
import module.tm.entity.EntityTmTable;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author Anar
 */
public class BEAction {

    public static Carrier callApi(Carrier carrier) throws Exception {
        ControllerPool cp = new ControllerPool();
        carrier.addController("apiId", cp.hasValue(carrier, "apiId"));
        if (carrier.hasError()) {
            return carrier;
        }

        String apiId = carrier.get("apiId");

        EntityTmBacklog ent = new EntityTmBacklog();
        ent.setId(apiId);
        ent.setStartLimit("0");
        ent.setEndLimit("0");
        EntityManager.select(ent);

        if (!ent.getIsApi().equals("1")) {
            return new Carrier();
        }

        String backlogName = ent.getBacklogName();

//        be.ValidateApiOnInput(apiId, data);
        String actionType = ent.getApiAction();
        if (actionType.equals("C")) {
            carrier = callInsertAPI(carrier);
        } else if (actionType.equals("R")) {
            carrier = callSelectAPI(carrier);
        } else if (actionType.equals("U")) {
            carrier = callUpdateAPI(carrier);
        } else if (actionType.equals("D")) {
            carrier = callDeleteAPI(carrier);

        } else {
            carrier = callContainerAPI(carrier);
            carrier = mergeContainerOutput(apiId, carrier);
        }
        
        if (carrier.hasError()){
            Carrier crErr = new Carrier();
            carrier.copyTable("USER_ERROR_TABLE", crErr);
            carrier = crErr;
        }

        return carrier;
    }

    public static Carrier mergeContainerOutput(String apiId, Carrier carrier) throws Exception {
        Carrier crOutput = getOutputList(apiId);
//        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
//        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        Carrier crOut = new Carrier();
        String tn1 = carrier.getTableIndex(0);
        carrier.copyTable(tn1, crOut);
        carrier.copyTable("USER_ERROR_TABLE", crOut);

        String[] outputList = crOutput.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        for (String k : outputList) {
            if (k.length() == 0) {
                continue;
            }
            crOut.set(k, carrier.get(k));
        }

        String tn = crOut.getTableIndex(0);
        String cols[] = crOut.getTableColumnNames(tn);
        for (String col : cols) {
            if (col.length() == 0) {
                continue;
            }

            if (col.equals("code") || col.equals("message")) {
                continue;
            }

            if (!Arrays.asList(outputList).contains(col)) {
                crOut.removeColoumn(tn, col);
            }
        }

        return crOut;
    }

    public static Carrier callInsertAPI(Carrier carrier) throws Exception {
        String apiId = carrier.get("apiId");
        Carrier crInList = getInputList(apiId);
        Carrier crInKV = crInList.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        String[] inputList = crInList.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        String inputListIds = crInList.getValueLine(CoreLabel.RESULT_SET, "id");

        Carrier crIn = setInputValuesOnStoryCard(carrier, inputList);

        crIn = setControllerMandatory(crIn, crInKV, inputListIds);
        if (crIn.hasError()) {
            return crIn;
        }

        crIn.set("apiId", apiId);
        crIn = setInputDefaultValue(crIn, crInKV, inputListIds);

        crIn = callExternalApiServices(crIn);
        if (crIn.hasError()) {
            return crIn;
        }

        carrier = insertObjectsToDB(crIn);
        if (carrier.hasError()) {
            return carrier;
        }

        return carrier;
    }

    public static Carrier callContainerAPI(Carrier carrier) throws Exception {
        String apiId = carrier.get("apiId");
        Carrier crInList = getInputList(apiId);
        Carrier crInKV = crInList.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        String[] inputList = crInList.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        String inputListIds = crInList.getValueLine(CoreLabel.RESULT_SET, "id");

        Carrier crIn = setInputValuesOnStoryCard(carrier, inputList);

        crIn = setControllerMandatory(crIn, crInKV, inputListIds);
        if (crIn.hasError()) {
            return crIn;
        }

        crIn.set("apiId", apiId);
        crIn = setInputDefaultValue(crIn, crInKV, inputListIds);

        crIn = callExternalApiServices(crIn);
        if (crIn.hasError()) {
            return crIn;
        }

        carrier = containerObjectAction(crIn);
        if (carrier.hasError()) {
            return carrier;
        }

        return carrier;
    }

    public static Carrier callSelectAPI(Carrier carrier) throws Exception {
        String apiId = carrier.get("apiId");
        Carrier crInList = getInputList(apiId);
        Carrier crInKV = crInList.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        String[] inputList = crInList.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        String inputListIds = crInList.getValueLine(CoreLabel.RESULT_SET, "id");

        Carrier crIn = setInputValuesOnStoryCard(carrier, inputList);

        crIn = setControllerMandatory(crIn, crInKV, inputListIds);
        if (crIn.hasError()) {
            return crIn;
        }

        crIn.set("apiId", apiId);
        crIn = setInputDefaultValue(crIn, crInKV, inputListIds);

        crIn = callExternalApiServices(crIn);
        if (crIn.hasError()) {
            return crIn;
        }

        carrier = selectObjectsToDB(crIn);
        if (carrier.hasError()) {
            return carrier;
        }

        return carrier;
    }

    public static Carrier callUpdateAPI(Carrier carrier) throws Exception {
        String apiId = carrier.get("apiId");
        Carrier crInList = getInputList(apiId);
        Carrier crInKV = crInList.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        String[] inputList = crInList.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        String inputListIds = crInList.getValueLine(CoreLabel.RESULT_SET, "id");

        Carrier crIn = setInputValuesOnStoryCard(carrier, inputList);

        crIn = setControllerMandatory(crIn, crInKV, inputListIds);
        if (crIn.hasError()) {
            return crIn;
        }

        crIn.set("apiId", apiId);
        crIn = setInputDefaultValue(crIn, crInKV, inputListIds);

        crIn = callExternalApiServices(crIn);
        if (crIn.hasError()) {
            return crIn;
        }

        carrier = updateObjectsToDB(crIn);
        if (carrier.hasError()) {
            return carrier;
        }

        return carrier;
    }

    public static Carrier callDeleteAPI(Carrier carrier) throws Exception {
        String apiId = carrier.get("apiId");
        Carrier crInList = getInputList(apiId);
        Carrier crInKV = crInList.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        String[] inputList = crInList.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        String inputListIds = crInList.getValueLine(CoreLabel.RESULT_SET, "id");

        Carrier crIn = setInputValuesOnStoryCard(carrier, inputList);

        crIn = setControllerMandatory(crIn, crInKV, inputListIds);
        if (crIn.hasError()) {
            return crIn;
        }

        crIn.set("apiId", apiId);
        crIn = setInputDefaultValue(crIn, crInKV, inputListIds);

        crIn = callExternalApiServices(crIn);
        if (crIn.hasError()) {
            return crIn;
        }

        carrier = deleteObjectsToDB(crIn);
        if (carrier.hasError()) {
            return carrier;
        }

        return carrier;
    }

    public static Carrier getInputList(String apiId) throws QException {
        String sql = "select id,input_name from " + SessionManager.getCurrentDomain() + ".tm_input "
                + "where fk_backlog_id=? and status='A' AND input_type='IN';";
        Carrier crInput = EntityManager.selectBySql(sql, new String[]{apiId});
        return crInput;
    }

    public static Carrier getOutputList(String apiId) throws QException {
        String sql = "select id,input_name,select_from_db_id,select_from_table_id,select_from_field_id,"
                + " send_To_Db_Id,send_To_Table_Id,send_To_Field_Id,send_To_Backlog_Id "
                + " from " + SessionManager.getCurrentDomain() + ".tm_input "
                + "where fk_backlog_id=? and status='A' AND input_type='OUT';";
        Carrier crInput = EntityManager.selectBySql(sql, new String[]{apiId});

        return crInput;
    }

    public static Carrier setInputValuesOnStoryCard(Carrier carrier, String[] inputList) throws QException {

        Carrier crIn = new Carrier();
        for (int i = 0; i < inputList.length; i++) {
            if (inputList[i].trim().length() > 0) {
                String inputName = inputList[i].trim();
                String val = (carrier.get(inputName).length() > 0) ? carrier.get(inputName) : "";
                crIn.set(inputName, val);
            }
        }

        return crIn;
    }

    private static String getDBNameByOutput4Select(Carrier outputList) throws Exception {
        String dbIdLines = outputList.getValueLine(CoreLabel.RESULT_SET, "selectFromDbId");
        String dbName = "";
        if (dbIdLines.length() > 2) {
            EntityTmDatabase entDB = new EntityTmDatabase();
            entDB.setId(dbIdLines);
            entDB.setStartLimit(0);
            entDB.setEndLimit(0);
            EntityManager.select(entDB);
            dbName = entDB.getDbName();
        }
        return dbName;
    }

    private static String getDBNameByOutput(Carrier outputList) throws Exception {
        String dbIdLines = outputList.getValueLine(CoreLabel.RESULT_SET, "sendToDbId");
        String dbName = "";
        if (dbIdLines.length() > 2) {
            EntityTmDatabase entDB = new EntityTmDatabase();
            entDB.setId(dbIdLines);
            entDB.setStartLimit(0);
            entDB.setEndLimit(0);
            EntityManager.select(entDB);
            dbName = entDB.getDbName();
        }
        return dbName;
    }

    private static String getTableNameByOutput4Select(Carrier outputList) throws Exception {
        String tableIdsLines = outputList.getValueLine(CoreLabel.RESULT_SET, "selectFromTableId");
        String dbName = "";
        if (tableIdsLines.length() > 2) {
            EntityTmTable entDB = new EntityTmTable();
            entDB.setId(tableIdsLines);
            entDB.setStartLimit(0);
            entDB.setEndLimit(0);
            EntityManager.select(entDB);
            dbName = entDB.getTableName();
        }
        return dbName;
    }

    private static String getTableNameByOutput(Carrier outputList) throws Exception {
        String tableIdsLines = outputList.getValueLine(CoreLabel.RESULT_SET, "sendToTableId");
        String dbName = "";
        if (tableIdsLines.length() > 2) {
            EntityTmTable entDB = new EntityTmTable();
            entDB.setId(tableIdsLines);
            entDB.setStartLimit(0);
            entDB.setEndLimit(0);
            EntityManager.select(entDB);
            dbName = entDB.getTableName();
        }
        return dbName;
    }

    private static Carrier convertOutputListToFieldKV(Carrier outputList, Carrier crOutputKV) throws Exception {
        String fieldIdLines = outputList.getValueLine(CoreLabel.RESULT_SET, "sendToFieldId");
        Carrier crInputField = new Carrier();

        if (fieldIdLines.length() > 2) {
            EntityTmField entDB = new EntityTmField();
            entDB.setId(fieldIdLines);
            Carrier crInputList = EntityManager.select(entDB);
            Carrier crInputListKV = crInputList.getKVFromTable(entDB.toTableName(), "id", EntityTmField.FIELD_NAME);
            String updatedField = "";

            String tn = CoreLabel.RESULT_SET;
            int rc = outputList.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityTmInput entIn = new EntityTmInput();
                EntityManager.mapCarrierToEntity(outputList, tn, i, entIn);
                String inputName = entIn.getInputName();
                String fieldName = crInputListKV.get(entIn.getSendToFieldId());

                String value = crOutputKV.get(inputName);
                fieldName = convertTableFieldNameToEntityfieldName(fieldName);
                crInputField.set(fieldName, value);
                updatedField += "," + fieldName;

            }

            crInputField.setUpdatedField(updatedField);
            crInputField.setSelectedField(updatedField);
        }

        return crInputField;
    }

    private static Carrier convertOutputListToFieldKV4Select(Carrier outputList, Carrier crInputKV, Carrier crOutputIdPair) throws Exception {
        String fieldIdLines = outputList.getValueLine(CoreLabel.RESULT_SET, "selectFromFieldId");
        Carrier crInputField = new Carrier();
        Carrier crFieldKV = new Carrier();
        Carrier crFieldKVVerse = new Carrier();

        if (fieldIdLines.length() > 2) {
            EntityTmField entDB = new EntityTmField();
            entDB.setId(fieldIdLines);
            Carrier crFieldtList = EntityManager.select(entDB);

            Carrier crInputListKV = crFieldtList.getKVFromTable(entDB.toTableName(), "id", EntityTmField.FIELD_NAME);
            String updatedField = "";
            String selectedFieldCore = "";

            String tn = CoreLabel.RESULT_SET;
            int rc = outputList.getTableRowCount(tn);
            for (int i = 0; i < rc; i++) {
                EntityTmInput entIn = new EntityTmInput();
                EntityManager.mapCarrierToEntity(outputList, tn, i, entIn);
                String inputName = entIn.getInputName();
                String fieldName = crInputListKV.get(entIn.getSelectFromFieldId());
                fieldName = convertTableFieldNameToEntityfieldName(fieldName);

                crFieldKV.set(fieldName, inputName);
                crFieldKVVerse.set(inputName, fieldName);

                String value = crInputKV.get(fieldName);

                crInputField.set(fieldName, value);
                updatedField += "," + fieldName;
                selectedFieldCore += "," + inputName;

            }

            crInputField.setSelectedField(updatedField);
            crInputField.set("selectedFieldCore", selectedFieldCore);
            crInputField.set("fieldKV", crFieldKV);
            crInputField.set("fieldKVVerse", crFieldKVVerse);
        }

        return crInputField;
    }

    private static Carrier mapValuesToOutputList(Carrier carrier, Carrier crOut) throws Exception {
        String[] key = crOut.getValueLine(CoreLabel.RESULT_SET, "inputName", ",").split(",");
        Carrier crT = new Carrier();
        for (String k : key) {
            String value = carrier.get(k);
            crT.set(k, value);
        }

        return crT;
    }

    public static Carrier updateObjectsToDB(Carrier carrier) throws QException, Exception {
        String apiId = carrier.get("apiId");
        Carrier crOutput = getOutputList(apiId);
        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        String outputIdLn = crOutput.getValueLine(CoreLabel.RESULT_SET, "id");

        String dbName = getDBNameByOutput(crOutput);
        String tableName = getTableNameByOutput(crOutput);
        Carrier crInputField = convertOutputListToFieldKV(crOutput, crOutputKV);
        crInputField.set("id", carrier.get("id"));

        crInputField = addDbDescriptionField(crInputField, crOutputIdPair, outputIdLn);
        crInputField.setEntityName(tableName);
        crInputField.setEntityDbName(dbName);
        EntityManager.update(crInputField);

        String sendToBacklogId = crOutput.getValueLine(CoreLabel.RESULT_SET, "sendToBacklogId");
        Carrier cr1 = sendToRelatedApi(crInputField, sendToBacklogId);
        cr1.copyTo(crInputField);

        return crInputField;
    }

    public static Carrier deleteObjectsToDB(Carrier carrier) throws QException, Exception {
        String apiId = carrier.get("apiId");
        Carrier crOutput = getOutputList(apiId);
        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        String outputIdLn = crOutput.getValueLine(CoreLabel.RESULT_SET, "id");

        String dbName = getDBNameByOutput(crOutput);
        String tableName = getTableNameByOutput(crOutput);
        Carrier crInputField = convertOutputListToFieldKV(crOutput, crOutputKV);
        crInputField.set("id", carrier.get("id"));

        crInputField = addDbDescriptionField(crInputField, crOutputIdPair, outputIdLn);
        crInputField.setEntityName(tableName);
        crInputField.setEntityDbName(dbName);
        EntityManager.delete(crInputField);

        String sendToBacklogId = crOutput.getValueLine(CoreLabel.RESULT_SET, "sendToBacklogId");
        Carrier cr1 = sendToRelatedApi(crInputField, sendToBacklogId);
        cr1.copyTo(crInputField);

        return crInputField;
    }

    public static Carrier containerObjectAction(Carrier carrier) throws QException, Exception {
        String apiId = carrier.get("apiId");
        Carrier crOutput = getOutputList(apiId);
        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        String sendToBacklogId = crOutput.getValueLine(CoreLabel.RESULT_SET, "sendToBacklogId");
        Carrier cr1 = sendToRelatedApi(crOutputKV, sendToBacklogId);
        cr1.copyTo(crOutputKV);

        String tableName = carrier.getTableIndex(0);
        carrier.copyTable(tableName, crOutputKV);

        return crOutputKV;
    }

    public static Carrier selectObjectsToDB(Carrier carrier) throws QException, Exception {
        Carrier crInputField = new Carrier();
        carrier.copyKeys(crInputField);

        String apiId = carrier.get("apiId");
        Carrier crOutput = getOutputList(apiId);
        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        String outputIdLn = crOutput.getValueLine(CoreLabel.RESULT_SET, "id");

        String dbName = getDBNameByOutput4Select(crOutput);
        String tableName = getTableNameByOutput4Select(crOutput);

        convertOutputListToFieldKV4Select(crOutput, crInputField, crOutputIdPair).copyTo(crInputField);

        crInputField = addDbDescriptionField4Select(crInputField, crOutputIdPair, outputIdLn);
        Carrier crFieldKV = (Carrier) crInputField.getValue("fieldKV");
        String selectedFieldCore = crInputField.get("selectedFieldCore");

        crInputField.setEntityName(tableName);
        crInputField.setEntityDbName(dbName);
        crInputField = EntityManager.select(crInputField);

        crInputField = renameColNameAfterSelect(crFieldKV, crInputField, dbName, tableName);
        crInputField.setSelectedField(selectedFieldCore);

        String sendToBacklogId = crOutput.getValueLine(CoreLabel.RESULT_SET, "sendToBacklogId");
        Carrier cr1 = sendToRelatedApi(crInputField, sendToBacklogId);
        cr1.copyTo(crInputField);

        return crInputField;
    }

    private static Carrier renameColNameAfterSelect(Carrier crFieldKV, Carrier carrier, String dbName, String tableName) throws QException {

        String[] fieldKeys = crFieldKV.getKeys();
        for (String kk : fieldKeys) {
            if (kk.trim().length() > 0) {
                String coln = crFieldKV.get(kk);
                if (!coln.equals(kk)) {
                    carrier.renameKey(kk, coln);
                    carrier.renameTableColumn(dbName + "_" + tableName, kk, coln);
                }
            }
        }
        return carrier;
    }

    public static Carrier insertObjectsToDB(Carrier carrier) throws QException, Exception {
        String apiId = carrier.get("apiId");
        Carrier crOutput = getOutputList(apiId);
        Carrier crOutputIdPair = crOutput.getKVFromTable(CoreLabel.RESULT_SET, "id", "inputName");
        Carrier crOutputKV = mapValuesToOutputList(carrier, crOutput);

        String outputIdLn = crOutput.getValueLine(CoreLabel.RESULT_SET, "id");

        String dbName = getDBNameByOutput(crOutput);
        String tableName = getTableNameByOutput(crOutput);
        Carrier crInputField = convertOutputListToFieldKV(crOutput, crOutputKV);

        crInputField = addDbDescriptionField(crInputField, crOutputIdPair, outputIdLn);
        crInputField.setEntityName(tableName);
        crInputField.setEntityDbName(dbName);
        Carrier crInputFieldNew = EntityManager.insert(crInputField);

        carrier.set("id", crInputFieldNew.get("id"));

        String sendToBacklogId = crOutput.getValueLine(CoreLabel.RESULT_SET, "sendToBacklogId");
        Carrier cr1 = sendToRelatedApi(carrier, sendToBacklogId);
        cr1.copyTo(carrier);

        Carrier crOutNew = new Carrier();
        crOutNew.set("id", crInputFieldNew.get("id"));
        return crOutNew;
    }

    private static Carrier sendToRelatedApi(Carrier carrier, String apiId) throws QException, Exception {
        if (apiId.length() < 3) {
            return new Carrier();
        }
        Carrier crOut = new Carrier();
        carrier.copyTo(crOut);
        crOut.set("apiId", apiId);
        crOut = BEAction.callApi(crOut);

        return crOut;
    }

    static String convertTableFieldNameToEntityfieldName(String arg) {
        String UNDERSCORE = "_";
        String st[] = arg.split(UNDERSCORE);
        String res = st[0].toLowerCase(Locale.ENGLISH);
        for (int i = 1; i <= st.length - 1; i++) {
            res = res + st[i].substring(0, 1).toUpperCase(Locale.ENGLISH) + st[i].substring(1, st[i].length()).toLowerCase(Locale.ENGLISH);
        }
        return res;
    }

    static Carrier callExternalApiServices(Carrier carrier) throws QException, Exception {
        String apiId = carrier.get("apiId");
        if (apiId.length() <= 3) {
            return new Carrier();
        }

        EntityTmBacklogDescription ent = new EntityTmBacklogDescription();
        ent.setFkBacklogId(apiId);
        ent.addSortBy(EntityTmBacklogDescription.ORDER_NO);
        ent.setSortByAsc(true);
        Carrier cr = EntityManager.select(ent);

        carrier.set("fnName", "");
        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            String fnName = ent.getDescription();
            if (SAFunction.IsDeleteCommand(fnName)) {
                carrier.set("fnName", fnName);

            } else if (SAFunction.IsCommand(fnName)) {
                carrier.set("fnName", fnName);
                Carrier crExec = SAFunction.ExecCommand(carrier);
                crExec.copyTo(carrier);
            } else if (ent.getFkRelatedScId().length() > 2) {
                Carrier crRel = new Carrier();
                carrier.copyTo(crRel);
                crRel = callFunction(crRel, ent.getFkRelatedScId());
                crRel.copyTo(carrier);
            } else if (ent.getFkRelatedApiId().length() > 2) {
                Carrier crRel = new Carrier();
                carrier.copyTo(crRel);
                crRel.set("apiId", ent.getFkRelatedApiId());
                crRel = BEAction.callApi(crRel);
                crRel.copyTo(carrier);
            }

            if (carrier.hasError()) {
                return carrier;
            }
        }

        return carrier;
    }

    private static Carrier callFunction(Carrier carrier, String functionId) throws Exception {
        if (functionId.length() < 3) {
            return new Carrier();
        }
        Carrier crOut = new Carrier();

        EntityTmJsCode ent = new EntityTmJsCode();
        ent.setId(functionId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        EntityManager.select(ent);

        if (ent.getFnType().equals("java")) {
            carrier.set("fnName", ent.getFnCoreName());
            crOut = IoModel.runFunction(carrier);
        }

        return crOut;
    }

    public static Carrier getInputDefaultValue(String inputIdLines) throws QException {
        if (inputIdLines.length() < 3) {
            return new Carrier();
        }

        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputIdLines);
        ent.setDescription("%%fn_(Defaultvalueis)%%");
        Carrier crOut = EntityManager.select(ent);

        crOut = crOut.getKVFromTable(ent.toTableName(), "fkInputId", "description");

        return crOut;
    }

    public static Carrier setInputDefaultValue(Carrier carrier, Carrier crInKV, String inputListIds) throws QException {
        Carrier crValues = getInputDefaultValue(inputListIds);
        String[] keys = crValues.getKeys();
        for (String k : keys) {
            String val = crValues.get(k);
            val = getParamFromFnline(val, "fn_(Defaultvalueis)", "defaultval");
            String inputName = crInKV.get(k);
            carrier.set(inputName, val);
        }

        return carrier;
    }

    public static Carrier setControllerMandatory(Carrier carrier, Carrier crInKV, String inputListIds) throws QException {
        ControllerPool cp = new ControllerPool();

        Carrier crValues = getControllerMandatory(inputListIds);
        String[] keys = crValues.getKeys();
        for (String k : keys) {
            String inputName = crInKV.get(k);
            carrier.addController(inputName, cp.hasValue(carrier, inputName));
        }

        return carrier;
    }

    public static Carrier getControllerMandatory(String inputListIds) throws QException {
        if (inputListIds.length() < 3) {
            return new Carrier();
        }

        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(inputListIds);
        ent.setDescription("%%fn_(ismandatory)%%");
        Carrier crOut = EntityManager.select(ent);

        crOut = crOut.getKVFromTable(ent.toTableName(), "fkInputId", "description");

        return crOut;
    }

    public static String getParamFromFnline(String fnline, String fn, String param) {
        String res = "";
        try {

            int n1 = fnline.indexOf(fn);
            int n2 = fnline.indexOf("fn_(", n1 + 1);
            n2 = (n2 == 0 || n2 == -1) ? fnline.length() : n2;
            String pureLine = fnline.substring(n1, (n2));
            pureLine = pureLine.replace("%IN%", "");

            String params = pureLine.split("\\?")[1];
            String fn_text = params.split("::")[0];
            try {
                String fn_paramlist = params.split("::")[1];
                String[] kv_list = fn_paramlist.split("&");
                for (int i = 0; i < kv_list.length; i++) {
                    String key = kv_list[i].split("=")[0];
                    String val = kv_list[i].split("=")[1];
                    if (key.contains(param)) {
                        res = val.trim();
                        return res;
                    }
                }
            } catch (Exception err) {
            }
        } catch (Exception err1) {

        }

        return res;
    }

    public static Carrier addDbDescriptionField4Select(Carrier crOutput, Carrier crOutputKV, String outputIdLines) throws QException {
        if (outputIdLines.length() < 3) {
            return crOutput;
        }

        Carrier crFieldKV = new Carrier();

        Carrier crFieldKVVerse = new Carrier();

        try {
            crFieldKV = (Carrier) crOutput.getValue("fieldKV");
        } catch (Exception err) {
        }

        try {
            crFieldKVVerse = (Carrier) crOutput.getValue("fieldKVVerse");
        } catch (Exception err) {
        }

        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(outputIdLines);
        Carrier cr = EntityManager.select(ent);

        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            String descBody = ent.getDescription();
            String inputName = crOutputKV.get(ent.getFkInputId());
            String groupByField = getGroupByNameString(descBody);

            String inputNameVerse = crFieldKVVerse.get(inputName);

            if (groupByField.length() > 0) {
                String arg = (crOutput.isKeyExist(groupByField))
                        ? crOutput.get(groupByField) + "," + inputNameVerse
                        : inputNameVerse;

                crOutput.set(groupByField, arg);
                crOutput.removeFromSelectedField(arg);

                String newField = getGroupByNameStringDomain(descBody)
                        + fcLetter(inputNameVerse);
                crFieldKV.set(newField, inputName);
            }

        }

        crOutput.setValue("fieldKV", crFieldKV);
        return crOutput;
    }

    public static String fcLetter(String arg) {
        String res = "";
        try {
                res = arg.substring(0, 1).toUpperCase(Locale.ENGLISH) + arg.substring(1, arg.length());
        } catch (Exception ex) {
           
        }
        return res;
    }

    private static String getGroupByNameString(String descBody) {
        String field = "";
        if (descBody.contains("fn_(iscurrentuser)")) {
            field = "currentUserField";

        } else if (descBody.contains("fn_(iscurrentdate)")) {
            field = "currentDateField";

        } else if (descBody.contains("fn_(iscurrenttime)")) {
            field = "currentTimeField";
        } else if (descBody.contains("fn_(ismaximumvalue)")) {
            field = "isMaximumField";
        } else if (descBody.contains("fn_(isminimumvalue)")) {
            field = "isMinimumField";
        } else if (descBody.contains("fn_(isrowcount)")) {
            field = "isCountField";
        } else if (descBody.contains("fn_(isaveragevalue)")) {
            field = "isAverageField";
        } else if (descBody.contains("fn_(issummary)")) {
            field = "isSumField";

        }
        return field;
    }

    private static String getGroupByNameStringDomain(String descBody) {
        String field = "";
        if (descBody.contains("fn_(ismaximumvalue)")) {
            field = "maximum";
        } else if (descBody.contains("fn_(isminimumvalue)")) {
            field = "minimum";
        } else if (descBody.contains("fn_(isrowcount)")) {
            field = "count";
        } else if (descBody.contains("fn_(isaveragevalue)")) {
            field = "average";
        } else if (descBody.contains("fn_(issummary)")) {
            field = "sum";

        }
        return field;
    }

    public static Carrier addDbDescriptionField(Carrier crOutput, Carrier crOutputKV, String outputIdLines) throws QException {
        if (outputIdLines.length() < 3) {
            return crOutput;
        }

        EntityTmInputDescription ent = new EntityTmInputDescription();
        ent.setFkInputId(outputIdLines);
        Carrier cr = EntityManager.select(ent);

        String tn = ent.toTableName();
        int rc = cr.getTableRowCount(tn);
        for (int i = 0; i < rc; i++) {
            EntityManager.mapCarrierToEntity(cr, tn, i, ent);
            String descBody = ent.getDescription();
            String inputName = crOutputKV.get(ent.getFkInputId());
            String field = getGroupByNameString(descBody);;

            String arg = (crOutput.isKeyExist(field))
                    ? crOutput.get(field) + "," + inputName
                    : inputName;

            crOutput.set(field, arg);

        }

        return crOutput;
    }

}
