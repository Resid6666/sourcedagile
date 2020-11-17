/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.gf;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import label.CoreLabel;
import module.cr.CrModel;
import module.cr.entity.EntityCrAppointment;
import module.cr.entity.EntityCrUserList;
import module.gf.entity.EntityGfAnbar;
import module.gf.entity.EntityGfAnbarHereket;
import module.gf.entity.EntityGfAnbarHereketList;
import module.gf.entity.EntityGfAnbarProductOrder;
import module.gf.entity.EntityGfAnbarProductOrderList;
import module.gf.entity.EntityGfDiscount;
import module.gf.entity.EntityGfExpense;
import module.gf.entity.EntityGfPatientSender;
import module.gf.entity.EntityGfPatientSenderList;
import module.gf.entity.EntityGfPriceList;
import module.gf.entity.EntityGfPriceListRatioByDoctor;
import module.gf.entity.EntityGfPriceListRatioByDoctorList;
import module.gf.entity.EntityGfPriceListRatioByPatientSender;
import module.gf.entity.EntityGfPriceListRatioByPatientSenderList;
import module.gf.entity.EntityGfProduct;
import module.gf.entity.EntityGfProductPriceList;
import module.gf.entity.EntityGfProductPriceListList;
import module.gf.entity.EntityGfProductPurchase;
import module.gf.entity.EntityGfProductPurchaseList;
import module.gf.entity.EntityGfProductRemain;
import module.gf.entity.EntityGfProductRemainList;
import module.gf.entity.EntityGfProductUsage;
import module.gf.entity.EntityGfProductUsageList;
import module.gf.entity.EntityGfRelDiscountAndPatient;
import module.gf.entity.EntityGfRelDiscountAndPatientList;
import module.gf.entity.EntityGfRelPriceListAndSubmodule;
import module.gf.entity.EntityGfRelPriceListAndSubmoduleList;
import utility.CacheUtil;
import utility.Carrier;
import utility.DeepWhere;
import utility.QDate;
import utility.QException;
import utility.QUtility;
import utility.SessionManager;
import utility.sqlgenerator.DBConnection;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class GfModel {

    private static final String USER_CONTROLLER_TYPE_ENUB = "2";
    private static final String COMBO_EMPTY_ID = "__2__";

    private static final String HEREKET_OPERATION_TYPE_MEHSUL_SIFARISI = "MS";
    private static final String HEREKET_OPERATION_TYPE_MEHSUL_ALISI = "MA";
    private static final String HEREKET_OPERATION_TYPE_MEHSUL_ISTIFADESI = "MI";
    private static final String HEREKET_OPERATION_TYPE_STASIONAR_XIDMET = "SX";
    private static final String HEREKET_OPERATION_TYPE_EMELIYYAT_XERCLERI = "EX";

    private static final String EXPENSE_PURPOSE_MAASLAR = "1";
    private static final String EXPENSE_PURPOSE_DOCTOR = "2";
    private static final String EXPENSE_PURPOSE_PATIENT_SENDER = "3";
    private static final String EXPENSE_PURPOSE_TEMIZLIK = "4";
    private static final String EXPENSE_PURPOSE_DIGER = "5";
    private static final String EXPENSE_PURPOSE_MEHSUL_ALISI = "6";

    private static final String ORDER_STATUS_ACTIVE = "A";
    private static final String ORDER_STATUS_PASSIVE = "P";
    private static final String ORDER_STATUS_CANCELED = "C";

    public static void main(String[] arg) {

        Connection conn = null;
        try {
            conn = new DBConnection().getConnection();
            conn.setAutoCommit(false);
            SessionManager.setConnection(Thread.currentThread().getId(), conn);
            SessionManager.setDomain(SessionManager.getCurrentThreadId(), "apd_1lqliu3");
            SessionManager.setUserId(SessionManager.getCurrentThreadId(), "201710281154580751");
            SessionManager.setCompanyId(SessionManager.getCurrentThreadId(), "201710221851270308");

            String json = "{\"kv\":{\"submoduleName\":\"Compalint on Urology | Urlogiya sah?sind?ki ?ikay?tl?r | Urologiy genel Problemler\",\"submoduleUniqueId\":\"\",\"sortBy\":\"\",\"submoduleDescription\":\"\",\"undefined\":\"ENG\",\"fkModuleId\":\"201712051556230782\",\"liSubmoduleStatus\":\"A\",\"submoduleType\":\"1\",\"lang\":\"ENG\"},\"tbl\":[]}"
                    + "";

//            String servicename = "serviceCrGetTermPage";
            //
            Carrier c = new Carrier();
            c.fromJson(json);
            c.setValue("01", "Diagnosses");
            c.setValue("2", "Diagnosses");
            c.setValue("3", "Diagnosses");
            c.setValue("4", "Diagnosses");
            c.setValue("5", "Diagnosses");
            c.setValue("6", "Diagnosses");
//            getIdsForInspectionList(c, "\"Diagnos  4\"");

//
//            c.setServiceName(servicename);
//            c.fromJson(json);
////            System.out.println(c.getJson());
//            //            System.out.println(c.getJson());
//            Response callService = CallDispatcher.callService(c);
            conn.commit();
            conn.close();
        } catch (Exception ex) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException ex1) {
            }
        }
    }

    public static Carrier insertNewProduct(Carrier carrier) throws QException {
        EntityGfProduct ent = new EntityGfProduct();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updateProduct(Carrier carrier) throws QException {
        EntityGfProduct ent = new EntityGfProduct();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteProduct(Carrier carrier) throws QException {
        EntityGfProduct ent = new EntityGfProduct();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getProductAllList(Carrier carrier) throws QException {
        Carrier crUnit = QUtility.getListItem("unit",
                carrier.getValue("unitName").toString());
        String unit = convertArrayToFilterLine(crUnit.getKeys());

        Carrier crProductType = QUtility.getListItem("productType",
                carrier.getValue("productTypeName").toString());
        String productType = convertArrayToFilterLine(crProductType.getKeys());

        EntityGfProduct ent = new EntityGfProduct();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setProductType(productType);
        ent.setUnit(unit);
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();

        cr.mergeCarrier(tn, "productType", "productTypeName", crProductType);
        cr.mergeCarrier(tn, "unit", "unitName", crUnit, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProductList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    private static String convertArrayToFilterLine(String[] arr) {
        String st = "";
        for (String s : arr) {
            st += s + CoreLabel.IN;
        }
        return st;
    }

    public static Carrier insertNewAnbar(Carrier carrier) throws QException {
        EntityGfAnbar ent = new EntityGfAnbar();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updateAnbar(Carrier carrier) throws QException {
        EntityGfAnbar ent = new EntityGfAnbar();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteAnbar(Carrier carrier) throws QException {
        EntityGfAnbar ent = new EntityGfAnbar();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getAnbarList(Carrier carrier) throws QException {

        EntityGfAnbar ent = new EntityGfAnbar();
        EntityManager.mapCarrierToEntity(carrier, ent);
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getAnbarList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier insertNewPriceList(Carrier carrier) throws QException {
        EntityGfPriceList ent = new EntityGfPriceList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updatePriceList(Carrier carrier) throws QException {
        EntityGfPriceList ent = new EntityGfPriceList();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deletePriceList(Carrier carrier) throws QException {
        EntityGfPriceList ent = new EntityGfPriceList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    private static String getIdsForInspectionList(Carrier carrier, String filterValue) throws QException {

        String st = "";
        try {
            String keys[] = carrier.getKeys();
            System.out.println("filter value>>" + filterValue);
            for (String k : keys) {

                String val = carrier.getValue(k).toString();
                System.out.println("attr key-val>>" + k + "-" + val);
                if (val == null) {
                    continue;
                }

                if (new DeepWhere(val, filterValue).isMatched()) {
                    st += k.substring(0, k.length() - 3) + CoreLabel.IN;
                } else {
                    carrier.removeKey(k);
                }
            }
        } catch (Exception e) {

        }
        return st;
    }

    public static Carrier getPriceListList(Carrier carrier) throws QException {
        Carrier cModule = CacheUtil.getFromCache(CacheUtil.CACHE_KEY_MODULE);
        String fkModuleIds = carrier.getValue("moduleName").toString().length() > 0
                ? getIdsForInspectionList(cModule, carrier.getValue("moduleName").toString())
                : "";

        Carrier cprCrrncy = QUtility.getListItem("currency",
                carrier.getValue("currencyName").toString());
        String currency = carrier.getValue("currencyName").toString().length() > 0
                ? convertArrayToFilterLine(cprCrrncy.getKeys())
                : "";

        Carrier cprLstStts = QUtility.getListItem("pa",
                carrier.getValue("listStatusName").toString());
        String listStatus = carrier.getValue("listStatusName").toString().length() > 0
                ? convertArrayToFilterLine(cprLstStts.getKeys())
                : "";

        EntityGfPriceList ent = new EntityGfPriceList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkModuleId(fkModuleIds);
        ent.setCurrency(currency);
        ent.setListStatus(listStatus);
        Carrier c = EntityManager.select(ent);
        String tnPrclst = ent.toTableName();
        carrier.removeKey("startLimit");
        carrier.removeKey("endLimit");

        c.mergeCarrier(tnPrclst, "currency", "currencyName", cprCrrncy);
        c.mergeCarrier(tnPrclst, new String[]{"fkModuleId", "LANG"},
                "moduleName", cModule);
        c.mergeCarrier(tnPrclst, "listStatus", "listStatusName", cprLstStts);

        c.renameTableName(tnPrclst, CoreLabel.RESULT_SET);
        c.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getPriceList"));

        c.addTableRowCount(CoreLabel.RESULT_SET, EntityManager.getRowCount(ent) + 1);

        return c;
    }

    public static Carrier insertNewRelPriceListAndSubmodule(Carrier carrier) throws QException {
        String fkPriceListId = carrier.getValue("fkPriceListId").toString();;

        EntityGfRelPriceListAndSubmodule ent = new EntityGfRelPriceListAndSubmodule();
        ent.setDeepWhere(false);
        ent.setFkPriceListId(fkPriceListId);
        Carrier crRel = EntityManager.select(ent);

        if (crRel.getTableRowCount(ent.toTableName()) > 0) {
            String ids = crRel.getValueLine(ent.toTableName());
            ent.setId(ids);
            EntityManager.delete(ent);
        }

        String[] fkSubmoduleId = carrier.getValue("fkSubmoduleId").toString()
                .split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String p : fkSubmoduleId) {
            if (p.trim().length() > 0) {
                EntityGfRelPriceListAndSubmodule entNew = new EntityGfRelPriceListAndSubmodule();
                entNew.setDeepWhere(false);
                entNew.setFkSubmoduleId(p);
                entNew.setFkPriceListId(fkPriceListId);
                Carrier tc = EntityManager.select(entNew);
                if (tc.getTableRowCount(entNew.toTableName()) > 0) {
                    continue;
                }
                EntityManager.insert(entNew);
            }
        }
        return new Carrier();
    }

    public static Carrier getRelPriceListAndSubmoduleList(Carrier carrier) throws QException {
        boolean hasSModuleVal = carrier.getValue("submoduleName").toString().length() > 0;
        Carrier cSModule = new Carrier();
        cSModule.setValue("submoduleName", carrier.getValue("submoduleName"));
        cSModule = hasSModuleVal ? CrModel.getSubmoduleList(cSModule)
                .getKVFromTable(CoreLabel.RESULT_SET, "id", "submoduleName")
                : CacheUtil.getFromCache(CacheUtil.CACHE_KEY_SUBMODULE);
        String fkSModuleIds = hasSModuleVal
                ? convertArrayToFilterLine(cSModule.getKeys())
                : "";

        EntityGfRelPriceListAndSubmoduleList entM = new EntityGfRelPriceListAndSubmoduleList();
        EntityManager.mapCarrierToEntity(carrier, entM);
        entM.setFkSubmoduleId(fkSModuleIds);
        Carrier crOut = EntityManager.select(entM);
        String tnMain = entM.toTableName();

        String[] moduleField = hasSModuleVal
                ? new String[]{"fkSubmoduleId", ""}
                : new String[]{"fkSubmoduleId", "LANG"};
        crOut.mergeCarrier(tnMain, moduleField,
                "submoduleName", cSModule, !hasSModuleVal);

        if (carrier.hasKey("id")) {
            EntityGfRelPriceListAndSubmodule ent1 = new EntityGfRelPriceListAndSubmodule();
            ent1.setId(carrier.getValue("id").toString());
            EntityManager.select(ent1);

            if (ent1.getFkPriceListId().trim().length() > 0) {
                EntityGfRelPriceListAndSubmodule ent2 = new EntityGfRelPriceListAndSubmodule();
                ent2.setFkPriceListId(ent1.getFkPriceListId());
                Carrier tc1 = EntityManager.select(ent2);

                String res = "";
                String tn = ent2.toTableName();
                int rc = tc1.getTableRowCount(tn);
                for (int i = 0; i < rc; i++) {
                    res += tc1.getValue(tn, i, "fkSubmoduleId").toString()
                            + "|";
                }
                crOut.setValue(tnMain, 0, "fkSubmoduleId", res);
            }

        }

        crOut.renameTableName(tnMain, CoreLabel.RESULT_SET);

        crOut.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getRelPriceListAndSubmoduleList"));
        crOut.addTableRowCount("rowCount", EntityManager.getRowCount(entM));
        return crOut;

    }

    public Carrier deleteRelPriceListAndSubmodule(Carrier carrier) throws QException {
        EntityGfRelPriceListAndSubmodule ent = new EntityGfRelPriceListAndSubmodule();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getSubmoduleListByPriceList(Carrier carrier) throws QException {
        EntityGfPriceList entPL = new EntityGfPriceList();
        entPL.setId(carrier.getValue("fkPriceListId").toString());
        EntityManager.select(entPL);

        if (entPL.getFkModuleId().length() == 0) {
            return carrier;
        }

        Carrier cSModule = new Carrier();
        cSModule.setValue("fkModuleId", entPL.getFkModuleId());
        cSModule = CrModel.getSubmoduleList(cSModule);

        Carrier crPS = new Carrier();
        crPS.setValue("fkModuleId", entPL.getFkModuleId());
        crPS = CrModel.getPrivateSubmoduleList(crPS);

        Carrier crOut = new Carrier();
        String tn = CoreLabel.RESULT_SET;
        int rc = cSModule.getTableRowCount(tn);
        int rcPS = crPS.getTableRowCount(tn);

        for (int i = 0; i < rc; i++) {
            crOut.setValue(tn, i, "id", cSModule.getValue(tn, i, "id"));
            crOut.setValue(tn, i, "submoduleName", cSModule.getValue(tn, i, "submoduleName"));
        }

        int trc = crOut.getTableRowCount(tn);
        for (int i = 0; i < rcPS; i++) {
            crOut.setValue(tn, trc + i, "id", crPS.getValue(tn, i, "id"));
            crOut.setValue(tn, trc + i, "submoduleName", crPS.getValue(tn, i, "submoduleName"));
        }

        return crOut;
    }

    public static Carrier insertNewPatientSender(Carrier carrier) throws QException {
        EntityGfPatientSender ent = new EntityGfPatientSender();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updatePatientSender(Carrier carrier) throws QException {
        EntityGfPatientSender ent = new EntityGfPatientSender();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deletePatientSender(Carrier carrier) throws QException {
        EntityGfPatientSender ent = new EntityGfPatientSender();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static void deletePatientSenderByUserId(String fkUserId) throws QException {
        EntityGfPatientSender ent = new EntityGfPatientSender();
        ent.setFkDoctorUserId(fkUserId);
        String ids = EntityManager.select(ent).getValueLine(ent.toTableName());

        ent.setId(ids);
        EntityManager.delete(ent);
    }

    public static Carrier getPatientSenderList(Carrier carrier) throws QException {

        EntityGfPatientSenderList ent = new EntityGfPatientSenderList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        Carrier cr = EntityManager.select(ent);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getPatientSenderList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier getPriceList4RatioCombo(Carrier carrier) throws QException {
        Carrier c = getPriceListList(new Carrier());
        int rc = c.getTableRowCount(CoreLabel.RESULT_SET);
        c.setValue(CoreLabel.RESULT_SET, rc, "id", COMBO_EMPTY_ID);
        c.setValue(CoreLabel.RESULT_SET, rc, EntityGfPriceList.PAYMENT_NAME,
                QUtility.getLabel("all"));
        return c;
    }

    public Carrier getDoctorList4PatientSender(Carrier carrier) throws QException {
        Carrier cr = new Carrier();
        cr = CrModel.getDoctorList(cr);

        Carrier nrc = new Carrier();
        int rc = cr.getTableRowCount(CoreLabel.RESULT_SET);
        nrc.setValue(CoreLabel.RESULT_SET, 0, "id", COMBO_EMPTY_ID);
        nrc.setValue(CoreLabel.RESULT_SET, 0, "fulname", QUtility.getLabel("other"));
        for (int i = 0; i < rc; i++) {
            nrc.setValue(CoreLabel.RESULT_SET, i + 1, "id",
                    cr.getValue(CoreLabel.RESULT_SET, i, "id"));
            nrc.setValue(CoreLabel.RESULT_SET, i + 1, "fulname",
                    cr.getValue(CoreLabel.RESULT_SET, i, EntityCrUserList.USER_PERSON_NAME).toString() + " "
                    + cr.getValue(CoreLabel.RESULT_SET, i, EntityCrUserList.USER_PERSON_SURNAME).toString() + " "
                    + cr.getValue(CoreLabel.RESULT_SET, i, EntityCrUserList.USER_PERSON_MIDDLENAME).toString() + " ");
        }

        return nrc;
    }

    public static Carrier insertNewPriceListRatioByDoctor(Carrier carrier) throws QException {
        EntityGfPriceListRatioByDoctor ent = new EntityGfPriceListRatioByDoctor();
        ent.setDeepWhere(false);
        ent.setFkDoctorUserId(carrier.getValue("fkDoctorUserId").toString());
        ent.setFkPriceListId(carrier.getValue("fkPriceListId").toString());
        Carrier c = EntityManager.select(ent);

        if (c.getTableRowCount(ent.toTableName()) > 0) {
            carrier.addController("fkDoctorUserId", QUtility.getLabel("priceListIsExistForThisDoctor"));
            return carrier;
        }

        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updatePriceListRatioByDoctor(Carrier carrier) throws QException {
        EntityGfPriceListRatioByDoctor ent = new EntityGfPriceListRatioByDoctor();
        ent.setDeepWhere(false);
        ent.setFkDoctorUserId(carrier.getValue("fkDoctorUserId").toString());
        ent.setFkPriceListId(carrier.getValue("fkPriceListId").toString());
        Carrier c = EntityManager.select(ent);
        if (c.getTableRowCount(ent.toTableName()) > 0
                && !ent.getId().equals(carrier.getValue("id").toString())) {
            carrier.addController("fkDoctorUserId", QUtility.getLabel("priceListIsExistForThisDoctor"));
            return carrier;
        }

        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deletePriceListRatioByDoctor(Carrier carrier) throws QException {
        EntityGfPriceListRatioByDoctor ent = new EntityGfPriceListRatioByDoctor();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getPriceListRatioByDoctorList(Carrier carrier) throws QException {
        Carrier cprCrrncy = QUtility.getListItem("currency",
                carrier.getValue("priceListCurrencyName").toString());
        String currency = carrier.getValue("priceListCurrencyName").toString().length() > 0
                ? convertArrayToFilterLine(cprCrrncy.getKeys())
                : "";

        EntityGfPriceListRatioByDoctorList ent = new EntityGfPriceListRatioByDoctorList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setPriceListCurrency(currency);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "priceListCurrency", "priceListCurrencyName", cprCrrncy, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getPriceListRatioByDoctorList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier insertNewPriceListRatioByPatientSender(Carrier carrier) throws QException {
        EntityGfPriceListRatioByPatientSender ent = new EntityGfPriceListRatioByPatientSender();
        ent.setDeepWhere(false);
        ent.setFkPatientSenderId(carrier.getValue("fkPatientSenderId").toString());
        ent.setFkPriceListId(carrier.getValue("fkPriceListId").toString());
        Carrier c = EntityManager.select(ent);

        if (c.getTableRowCount(ent.toTableName()) > 0) {
            carrier.addController("fkPatientSenderId",
                    QUtility.getLabel("priceListIsExistForThisPatientSender"));
            return carrier;
        }

        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updatePriceListRatioByPatientSender(Carrier carrier) throws QException {
        EntityGfPriceListRatioByPatientSender ent = new EntityGfPriceListRatioByPatientSender();
        ent.setDeepWhere(false);
        ent.setFkPatientSenderId(carrier.getValue("fkPatientSenderId").toString());
        ent.setFkPriceListId(carrier.getValue("fkPriceListId").toString());
        Carrier c = EntityManager.select(ent);

        if (c.getTableRowCount(ent.toTableName()) > 0
                && !ent.getId().equals(carrier.getValue("id").toString())) {
            carrier.addController("fkPatientSenderId",
                    QUtility.getLabel("priceListIsExistForThisPatientSender"));
            return carrier;
        }

        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deletePriceListRatioByPatientSender(Carrier carrier) throws QException {
        EntityGfPriceListRatioByPatientSender ent = new EntityGfPriceListRatioByPatientSender();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getPriceListRatioByPatientSenderList(Carrier carrier) throws QException {
        Carrier cprCrrncy = QUtility.getListItem("currency",
                carrier.getValue("priceListCurrencyName").toString());
        String currency = carrier.getValue("priceListCurrencyName").toString().length() > 0
                ? convertArrayToFilterLine(cprCrrncy.getKeys())
                : "";

        EntityGfPriceListRatioByPatientSenderList ent = new EntityGfPriceListRatioByPatientSenderList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setPriceListCurrency(currency);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "priceListCurrency", "priceListCurrencyName", cprCrrncy, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getPriceListRatioByPatientSenderList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier insertNewProductPriceList(Carrier carrier) throws QException {
        EntityGfProductPriceList ent = new EntityGfProductPriceList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updateProductPriceList(Carrier carrier) throws QException {
        EntityGfProductPriceList ent = new EntityGfProductPriceList();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteProductPriceList(Carrier carrier) throws QException {
        EntityGfProductPriceList ent = new EntityGfProductPriceList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getProductPriceListList(Carrier carrier) throws QException {
        Carrier cprCrrncy = QUtility.getListItem("currency",
                carrier.getValue("currencyName").toString());
        String currency = carrier.getValue("currencyName").toString().length() > 0
                ? convertArrayToFilterLine(cprCrrncy.getKeys())
                : "";

        EntityGfProductPriceListList ent = new EntityGfProductPriceListList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setCurrency(currency);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "currency", "currencyName", cprCrrncy, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProductPriceListList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier insertNewProductPurchase(Carrier carrier) throws QException {
        double totalPrice = 0;
        Double amount = Double.parseDouble(
                carrier.getValue(EntityGfProductPurchase.PURCHASE_AMOUNT).toString());
        Double unitPrice = Double.parseDouble(
                carrier.getValue(EntityGfProductPurchase.PURCHASE_PRICE).toString());
        totalPrice = amount * unitPrice;
        try {

            Double discount = carrier.getValue(EntityGfProductPurchase.PURCHASE_DISCOUNT).toString().trim().length() > 0
                    ? Double.parseDouble(
                            carrier.getValue(EntityGfProductPurchase.PURCHASE_DISCOUNT).toString())
                    : 0;
            Double total = Double.parseDouble(
                    carrier.getValue(EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT).toString());

            totalPrice = totalPrice - totalPrice * discount / 100;
        } catch (Exception e) {
        }

        DecimalFormat df = new DecimalFormat("#.####");
        totalPrice = Double.valueOf(df.format(totalPrice));

        EntityGfProductPurchase ent = new EntityGfProductPurchase();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setPurchaseTotalAmount(String.valueOf(totalPrice));
        ent.setFkEmployeeUserId(SessionManager.getCurrentUserId());
        EntityManager.insert(ent);

        addProductRemain4MehsulAlisi(ent.getFkAnbarId(), ent.getFkProductId(), amount);
        addAnbarHereketleri4MehsulAlisi(ent);
        return carrier;
    }

    public Carrier updateProductPurchase(Carrier carrier) throws QException {
        double totalPrice = 0;
        Double amount = Double.parseDouble(
                carrier.getValue(EntityGfProductPurchase.PURCHASE_AMOUNT).toString());
        Double unitPrice = Double.parseDouble(
                carrier.getValue(EntityGfProductPurchase.PURCHASE_PRICE).toString());
        totalPrice = amount * unitPrice;
        try {

            Double discount = carrier.getValue(EntityGfProductPurchase.PURCHASE_DISCOUNT).toString().trim().length() > 0
                    ? Double.parseDouble(
                            carrier.getValue(EntityGfProductPurchase.PURCHASE_DISCOUNT).toString())
                    : 0;
            Double total = Double.parseDouble(
                    carrier.getValue(EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT).toString());

            totalPrice = totalPrice - totalPrice * discount / 100;
        } catch (Exception e) {
        }

        EntityGfProductPurchase ent = new EntityGfProductPurchase();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        ent.setFkEmployeeUserId(SessionManager.getCurrentUserId());
        ent.setPurchaseTotalAmount(String.valueOf(totalPrice));
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteProductPurchase(Carrier carrier) throws QException {
        EntityGfProductPurchase ent = new EntityGfProductPurchase();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getProductPurchaseList(Carrier carrier) throws QException {
        Carrier cprCrrncy = QUtility.getListItem("currency",
                carrier.getValue("currencyName").toString());
        String currency = carrier.getValue("currencyName").toString().length() > 0
                ? convertArrayToFilterLine(cprCrrncy.getKeys())
                : "";

        EntityGfProductPurchaseList ent = new EntityGfProductPurchaseList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setCurrency(currency);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "currency", "currencyName", cprCrrncy, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProductPurchaseList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    private static void addProductRemain4MehsulAlisi(String fkAnbarId, String fkProductId, double amount)
            throws QException {
        Carrier c = new Carrier();
        c.setValue("fkAnbarId", fkAnbarId);
        c.setValue("fkProductId", fkProductId);
        c.setValue("remainAmount", amount);
        insertNewProductRemain(c);
    }

    private static void addAnbarHereketleri4MehsulAlisi(EntityGfProductPurchase ent) throws QException {
        Carrier c = new Carrier();
        c.setValue(EntityGfAnbarHereket.DESCRIPTION, ent.getDescription());
        c.setValue(EntityGfAnbarHereket.DOCUMENT_NO, ent.getInvoiceNo());
        c.setValue(EntityGfAnbarHereket.FK_PRODUCT_ID, ent.getFkProductId());
        c.setValue(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID, ent.getFkAnbarId());
        c.setValue(EntityGfAnbarHereket.FK_GENERAL_REL_ID, ent.getId());
        c.setValue(EntityGfAnbarHereket.OPERATION_TYPE, HEREKET_OPERATION_TYPE_MEHSUL_ALISI);
        c.setValue(EntityGfAnbarHereket.AMOUNT, ent.getPurchaseAmount());

        insertNewAnbarHereket(c);

    }

    private static void addAnbarHereketleri4MehsulSifarisi(EntityGfAnbarProductOrder ent) throws QException {
        Carrier c = new Carrier();
        c.setValue(EntityGfAnbarHereket.DESCRIPTION, ent.getDescription());
        c.setValue(EntityGfAnbarHereket.FK_PRODUCT_ID, ent.getFkProductId());
        c.setValue(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID, ent.getFkSourceAnbarId());
        c.setValue(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID, ent.getFkDestinationAnbarId());
        c.setValue(EntityGfAnbarHereket.FK_GENERAL_REL_ID, ent.getId());
        c.setValue(EntityGfAnbarHereket.OPERATION_TYPE, HEREKET_OPERATION_TYPE_MEHSUL_SIFARISI);
        c.setValue(EntityGfAnbarHereket.AMOUNT, ent.getAmount());

        insertNewAnbarHereket(c);

    }

    private static double getRemainProductByAnbarIdAndProductId(String fkAnbarId, String fkProductId) throws QException {
        EntityGfProductRemain ent = new EntityGfProductRemain();
        ent.setFkAnbarId(fkAnbarId);
        ent.setFkProductId(fkProductId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier c = EntityManager.select(ent);
        return c.getTableRowCount(ent.toTableName()) == 0
                ? 0
                : Double.parseDouble(ent.getRemainAmount());
    }

    public static Carrier insertNewProductRemain(Carrier carrier) throws QException {

        EntityGfProductRemain ent = new EntityGfProductRemain();
        ent.setFkAnbarId(carrier.getValue(EntityGfProductRemain.FK_ANBAR_ID).toString());
        ent.setFkProductId(carrier.getValue(EntityGfProductRemain.FK_PRODUCT_ID).toString());
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier c = EntityManager.select(ent);
        if (c.getTableRowCount(ent.toTableName()) == 0) {
            ent.setRemainAmount(carrier.getValue("remainAmount").toString());
            EntityManager.insert(ent);
        } else {
            Double amount = Double.parseDouble(carrier.getValue("remainAmount").toString())
                    + Double.parseDouble(ent.getRemainAmount());

            DecimalFormat df = new DecimalFormat("#.####");
            amount = Double.valueOf(df.format(amount));

            ent.setRemainAmount(String.valueOf(amount));
            EntityManager.update(ent);
        }
        return carrier;
    }

    public static Carrier getProductRemainList(Carrier carrier) throws QException {
        Carrier cprUnit = QUtility.getListItem("unit",
                carrier.getValue("unitName").toString());
        String unit = carrier.getValue("unitName").toString().length() > 0
                ? convertArrayToFilterLine(cprUnit.getKeys())
                : "";

        EntityGfProductRemainList ent = new EntityGfProductRemainList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setUnit(unit);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "unit", "unitName", cprUnit, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProductRemainList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static Carrier insertNewAnbarHereket(Carrier carrier) throws QException {
        double srcRemn = 0;
        double destRemn = 0;
        if (carrier.getValue("fkSourceAnbarId").toString().length() > 0) {
            srcRemn = getRemainProductByAnbarIdAndProductId(
                    carrier.getValue("fkSourceAnbarId").toString(),
                    carrier.getValue("fkProductId").toString());
//            srcRemn -= Double.parseDouble(carrier.getValue("amount").toString());
        }
        if (carrier.getValue("fkDestinationAnbarId").toString().length() > 0) {
            destRemn = getRemainProductByAnbarIdAndProductId(
                    carrier.getValue("fkDestinationAnbarId").toString(),
                    carrier.getValue("fkProductId").toString());
//            destRemn += Double.parseDouble(carrier.getValue("amount").toString());
        }

        EntityGfAnbarHereket ent = new EntityGfAnbarHereket();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkExecutorUserId(SessionManager.getCurrentUserId());
        ent.setHereketDate(QDate.getCurrentDate());
        ent.setHereketTime(QDate.getCurrentTime());
        if (carrier.getValue("fkSourceAnbarId").toString().length() > 0) {
            ent.setSourceRemainAmount(String.valueOf(srcRemn));
        }
        if (carrier.getValue("fkDestinationAnbarId").toString().length() > 0) {
            ent.setDestinationRemainAmount(String.valueOf(destRemn));
        }
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updateAnbarHereket(Carrier carrier) throws QException {
        EntityGfAnbarHereket ent = new EntityGfAnbarHereket();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

//    public Carrier deleteAnbarHereket(Carrier carrier) throws QException {
//        EntityGfAnbarHereket ent = new EntityGfAnbarHereket();
//        EntityManager.mapCarrierToEntity(carrier, ent);
//        EntityManager.delete(ent);
//        return carrier;
//    }
    public static Carrier getAnbarHereketList(Carrier carrier) throws QException {
        Carrier cprOprType = QUtility.getListItem("hereketOperationType",
                carrier.getValue("operationTypeName").toString());
        String oprType = carrier.getValue("operationTypeName").toString().length() > 0
                ? convertArrayToFilterLine(cprOprType.getKeys())
                : "";

        Carrier cprUnit = QUtility.getListItem("unit",
                carrier.getValue("productUnitName").toString());
        String oprUnit = carrier.getValue("productUnitName").toString().length() > 0
                ? convertArrayToFilterLine(cprUnit.getKeys())
                : "";

        EntityGfAnbarHereketList ent = new EntityGfAnbarHereketList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setOperationType(oprType);
        ent.setProductUnit(oprUnit);
        Carrier cr = EntityManager.select(ent);

        cr.mergeCarrier(ent.toTableName(), "operationType", "operationTypeName", cprOprType, true);
        cr.mergeCarrier(ent.toTableName(), "productUnit", "productUnitName", cprUnit, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getAnbarHereketList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    public static void generateCost4DoctorOnInspection(String fkSessionId) throws QException {
        if (fkSessionId.trim().length() == 0) {
            return;
        }

        EntityCrAppointment ent = new EntityCrAppointment();
        ent.setId(fkSessionId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier c = EntityManager.select(ent);

        if (c.getTableRowCount(ent.toTableName()) == 0) {
            return;
        }

        double rat = getInspectionRatioByDoctor(ent.getFkDoctorUserId(), ent.getFkPriceListId());
        double price = getPriceOfPriceList(ent.getFkPriceListId());
        double res = price * rat / 100;

        if (res > 0) {
            addDoctorExpense4Inspection(ent, res);
        }
    }

    public static void generateCost4PatientSenderOnInspection(String fkSessionId) throws QException {
        if (fkSessionId.trim().length() == 0) {
            return;
        }

        EntityCrAppointment ent = new EntityCrAppointment();
        ent.setId(fkSessionId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier c = EntityManager.select(ent);

        if (c.getTableRowCount(ent.toTableName()) == 0) {
            return;
        }

        double rat = getInspectionRatioByPatientSender(ent.getFkPatientSenderId(), ent.getFkPriceListId());
        double price = getPriceOfPriceList(ent.getFkPriceListId());
        double res = price * rat / 100;

        if (res > 0) {
            addPatientSenderExpense4Inspection(ent, res);
        }
    }

    public static void addDoctorExpense4Inspection(EntityCrAppointment ent, double amout)
            throws QException {
        if (ent.getFkDoctorUserId().length() == 0) {
            return;
        }

        Carrier cr = new Carrier();
        cr.setValue(EntityGfExpense.CURRENCY, CrModel.getCurrencyOfCompany());
        cr.setValue(EntityGfExpense.DESCRIPTION, ent.getDescription());
        cr.setValue(EntityGfExpense.DOCUMENT_NO, ent.getSessionNo());
        cr.setValue(EntityGfExpense.EXPENSE_AMOUNT, amout);
        cr.setValue(EntityGfExpense.EXPENSE_DATE, QDate.getCurrentDate());
        cr.setValue(EntityGfExpense.EXPENSE_PURPOSE, EXPENSE_PURPOSE_DOCTOR);
        cr.setValue(EntityGfExpense.FK_PATIENT_ID, ent.getFkPatientId());
        cr.setValue(EntityGfExpense.FK_USER_ID, ent.getFkDoctorUserId());
        CrModel.insertNewExpense(cr);

    }

    public static void addPatientSenderExpense4Inspection(EntityCrAppointment ent, double amount)
            throws QException {

        if (ent.getFkPatientSenderId().length() == 0) {
            return;
        }

        Carrier cr = new Carrier();
        cr.setValue(EntityGfExpense.CURRENCY, CrModel.getCurrencyOfCompany());
        cr.setValue(EntityGfExpense.DESCRIPTION, ent.getDescription());
        cr.setValue(EntityGfExpense.DOCUMENT_NO, ent.getSessionNo());
        cr.setValue(EntityGfExpense.EXPENSE_AMOUNT, amount);
        cr.setValue(EntityGfExpense.EXPENSE_DATE, QDate.getCurrentDate());
        cr.setValue(EntityGfExpense.EXPENSE_PURPOSE, EXPENSE_PURPOSE_PATIENT_SENDER);
        cr.setValue(EntityGfExpense.FK_PATIENT_ID, ent.getFkPatientId());
        cr.setValue(EntityGfExpense.FK_PATIENT_SENDER_ID, ent.getFkPatientSenderId());
        CrModel.insertNewExpense(cr);

    }

    public static double getPriceOfPriceList(String fkPriceListId) throws QException {
        if (fkPriceListId.trim().length() == 0) {
            return 0;
        }

        EntityGfPriceList ent = new EntityGfPriceList();
        ent.setId(fkPriceListId);
        EntityManager.select(ent);
        return Double.parseDouble(ent.getPrice());

    }

    public static double getInspectionRatioByDoctor(String fkDoctorId, String fkPriceListId)
            throws QException {
        double res = 0;

        EntityGfPriceListRatioByDoctor ent = new EntityGfPriceListRatioByDoctor();
        ent.setFkDoctorUserId(fkDoctorId);
        ent.setFkPriceListId(fkPriceListId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier cr1 = EntityManager.select(ent);

        if (cr1.getTableRowCount(ent.toTableName()) > 0) {
            res = Double.parseDouble(ent.getRatioOfDoctor());
        } else {
            ent.setFkPriceListId(COMBO_EMPTY_ID);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            Carrier cr2 = EntityManager.select(ent);
            if (cr2.getTableRowCount(ent.toTableName()) > 0) {
                res = Double.parseDouble(ent.getRatioOfDoctor());
            }
        }

        return res;
    }

    public static double getInspectionRatioByPatientSender(String fkPatientSenderId, String fkPriceListId)
            throws QException {
        double res = 0;

        EntityGfPriceListRatioByPatientSender ent = new EntityGfPriceListRatioByPatientSender();
        ent.setFkPatientSenderId(fkPatientSenderId);
        ent.setFkPriceListId(fkPriceListId);
        ent.setStartLimit(0);
        ent.setEndLimit(0);
        Carrier cr1 = EntityManager.select(ent);

        if (cr1.getTableRowCount(ent.toTableName()) > 0) {
            res = Double.parseDouble(ent.getRatioOfPatientSender());
        } else {
            ent.setFkPriceListId(COMBO_EMPTY_ID);
            ent.setStartLimit(0);
            ent.setEndLimit(0);
            Carrier cr2 = EntityManager.select(ent);
            if (cr2.getTableRowCount(ent.toTableName()) > 0) {
                res = Double.parseDouble(ent.getRatioOfPatientSender());
            }
        }

        return res;
    }

    public static Carrier insertNewAnbarProductOrder(Carrier carrier) throws QException {
        EntityGfProductRemain ent1 = new EntityGfProductRemain();
        ent1.setId(carrier.getValue("fkProductId").toString());
        EntityManager.select(ent1);

        if (ent1.getFkProductId().length() == 0) {
            return carrier;
        }

        EntityGfAnbarProductOrder ent = new EntityGfAnbarProductOrder();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkProductId(ent1.getFkProductId());
        ent.setFkDestinationAnbarId(ent1.getFkAnbarId());
        ent.setFkExecutorUserId(SessionManager.getCurrentUserId());
        ent.setOrderDate(QDate.getCurrentDate());
        ent.setOrderTime(QDate.getCurrentTime());
        ent.setOrderStatus(ORDER_STATUS_ACTIVE);
        EntityManager.insert(ent);
        return carrier;
    }

    public static Carrier confirmAnbarProductOrder(Carrier carrier) throws QException {
        String ids[] = carrier.getValue("id").toString().split(",");
        for (String id : ids) {
            if (id.trim().length() == 0) {
                continue;
            }
            EntityGfAnbarProductOrder ent = new EntityGfAnbarProductOrder();
            ent.setId(id);
            EntityManager.select(ent);

            if (ent.getOrderStatus().equals(ORDER_STATUS_PASSIVE)) {
                continue;
            }

            ent.setApprovedDate(QDate.getCurrentDate());
            ent.setApprovedTime(QDate.getCurrentTime());
            ent.setFkSupplierUserId(SessionManager.getCurrentUserId());
            ent.setOrderStatus(ORDER_STATUS_PASSIVE);
            EntityManager.update(ent);

            addAnbarHereketleri4MehsulSifarisi(ent);
            addProductRemain4MehsulSifarisi(ent);
        }

        return carrier;
    }

    private static void addProductRemain4MehsulSifarisi(EntityGfAnbarProductOrder ent)
            throws QException {
        Carrier c = new Carrier();
        c.setValue("fkAnbarId", ent.getFkSourceAnbarId());
        c.setValue("fkProductId", ent.getFkProductId());
        c.setValue("remainAmount", Double.parseDouble(ent.getAmount()));
        insertNewProductRemain(c);

        Carrier c1 = new Carrier();
        c1.setValue("fkAnbarId", ent.getFkDestinationAnbarId());
        c1.setValue("fkProductId", ent.getFkProductId());
        c1.setValue("remainAmount", -1 * Double.parseDouble(ent.getAmount()));
        insertNewProductRemain(c1);
    }

    public Carrier updateAnbarProductOrder(Carrier carrier) throws QException {
        EntityGfAnbarProductOrder ent = new EntityGfAnbarProductOrder();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteAnbarProductOrder(Carrier carrier) throws QException {
        EntityGfAnbarProductOrder ent = new EntityGfAnbarProductOrder();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getAnbarProductOrderList(Carrier carrier) throws QException {

        Carrier cprUnit = QUtility.getListItem("unit",
                carrier.getValue("productUnitName").toString());
        String unit = carrier.getValue("productUnitName").toString().length() > 0
                ? convertArrayToFilterLine(cprUnit.getKeys())
                : "";

        Carrier cprOrderStatus = QUtility.getListItem("orderStatus",
                carrier.getValue("orderStatusName").toString());
        String orderStatus = carrier.getValue("orderStatus").toString().length() > 0
                ? convertArrayToFilterLine(cprOrderStatus.getKeys())
                : "";

        EntityGfAnbarProductOrderList ent = new EntityGfAnbarProductOrderList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setProductUnit(unit);
        ent.setOrderStatus(orderStatus);
        Carrier c = EntityManager.select(ent);
        String tnPrclst = ent.toTableName();

        c.mergeCarrier(tnPrclst, "productUnit", "productUnitName", cprUnit, true);
        c.mergeCarrier(tnPrclst, "orderStatus", "orderStatusName", cprOrderStatus, true);

        c.renameTableName(tnPrclst, CoreLabel.RESULT_SET);
        c.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getAnbarProductOrderList"));

        c.addTableRowCount(CoreLabel.RESULT_SET, EntityManager.getRowCount(ent) + 1);

        return c;
    }

    public Carrier getAnbarListWithPermission(Carrier carrier) throws QException {
        return getAnbarList(carrier);
    }

    public Carrier getProductRemainListWithPermission(Carrier carrier) throws QException {
        return getProductRemainList(carrier);
    }

    public static Carrier insertNewProductUsage(Carrier carrier) throws QException {
        EntityGfProductRemain ent1 = new EntityGfProductRemain();
        ent1.setId(carrier.getValue("fkProductId").toString());
        EntityManager.select(ent1);

        if (ent1.getFkProductId().length() == 0) {
            carrier.addController("general", QUtility.getLabel("productIsNotAvailable"));
            return carrier;
        }

        EntityGfProductPriceList ent2 = new EntityGfProductPriceList();
        ent2.setFkProductId(ent1.getFkProductId());
        EntityManager.select(ent2);

        double aveCost = 0;
        try {
            aveCost = Double.parseDouble(carrier.getValue("amount").toString())
                    * Double.parseDouble(ent2.getAverageCost());
        } catch (QException | NumberFormatException e) {
        }

        double totalProfit = 0;
        try {
            totalProfit = Double.parseDouble(carrier.getValue("amount").toString())
                    * Double.parseDouble(ent2.getSalePrice());
        } catch (QException | NumberFormatException e) {
        }

        EntityGfProductUsage ent = new EntityGfProductUsage();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setFkProductId(ent1.getFkProductId());
        ent.setFkAnbarId(ent1.getFkAnbarId());
        ent.setFkExecutorUserId(SessionManager.getCurrentUserId());
        ent.setUsageDate(QDate.getCurrentDate());
        ent.setUsageTime(QDate.getCurrentTime());
        ent.setAverageCost(String.valueOf(aveCost));
        ent.setTotalProfit(String.valueOf(totalProfit));
        EntityManager.insert(ent);

        addAnbarHereketleri4MehsulIstifadesi(ent);
        addProductRemain4MehsulIstifadesi(ent.getFkAnbarId(),
                ent.getFkProductId(), -1 * Double.parseDouble(ent.getAmount()));

        return carrier;

    }

    public Carrier updateProductUsage(Carrier carrier) throws QException {
        EntityGfProductRemain ent1 = new EntityGfProductRemain();
        ent1.setId(carrier.getValue("fkProductId").toString());
        EntityManager.select(ent1);

        if (ent1.getFkProductId().length() == 0) {
            carrier.addController("general", QUtility.getLabel("productIsNotAvailable"));
            return carrier;
        }

        EntityGfProductPriceList ent2 = new EntityGfProductPriceList();
        ent2.setFkProductId(ent1.getFkProductId());
        EntityManager.select(ent2);

        double aveCost = 0;
        try {
            aveCost = Double.parseDouble(carrier.getValue("amount").toString())
                    * Double.parseDouble(ent2.getAverageCost());
        } catch (Exception e) {
        }

        double totalProfit = 0;
        try {
            totalProfit = Double.parseDouble(carrier.getValue("amount").toString())
                    * Double.parseDouble(ent2.getSalePrice());
        } catch (Exception e) {
        }

        EntityGfProductUsage ent = new EntityGfProductUsage();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        ent.setFkProductId(ent1.getFkProductId());
        ent.setFkAnbarId(ent1.getFkAnbarId());
        ent.setAverageCost(String.valueOf(aveCost));
        ent.setTotalProfit(String.valueOf(totalProfit));
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteProductUsage(Carrier carrier) throws QException {
        EntityGfProductUsage ent = new EntityGfProductUsage();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getProductUsageList(Carrier carrier) throws QException {
        Carrier crUnit = QUtility.getListItem("unit",
                carrier.getValue("productUnitName").toString());
        String unit = carrier.getValue("productUnitName").toString().length() > 0
                ? convertArrayToFilterLine(crUnit.getKeys())
                : "";

        Carrier crUsage = QUtility.getListItem("productUsagePurpose",
                carrier.getValue("usagePurposeName").toString());
        String usage = carrier.getValue("usagePurposeName").toString().length() > 0
                ? convertArrayToFilterLine(crUsage.getKeys())
                : "";

        EntityGfProductUsageList ent = new EntityGfProductUsageList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setProductUnit(unit);
        ent.setUsagePurpose(usage);
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();

        cr.mergeCarrier(tn, "productUnit", "productUnitName", crUnit, true);
        cr.mergeCarrier(tn, "usagePurpose", "usagePurposeName", crUsage, true);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getProductUsageList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

    private static void addAnbarHereketleri4MehsulIstifadesi(EntityGfProductUsage ent) throws QException {

        String st = QUtility.getListItemValue("productUsagePurpose", ent.getUsagePurpose());

        Carrier c = new Carrier();
        c.setValue(EntityGfAnbarHereket.DESCRIPTION, st + ", " + ent.getDescription());
        c.setValue(EntityGfAnbarHereket.DOCUMENT_NO, ent.getDocumentNo());
        c.setValue(EntityGfAnbarHereket.FK_PRODUCT_ID, ent.getFkProductId());
        c.setValue(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID, ent.getFkAnbarId());
        c.setValue(EntityGfAnbarHereket.FK_GENERAL_REL_ID, ent.getId());
        c.setValue(EntityGfAnbarHereket.OPERATION_TYPE, HEREKET_OPERATION_TYPE_MEHSUL_ISTIFADESI);
        c.setValue(EntityGfAnbarHereket.AMOUNT, ent.getAmount());
        c.setValue(EntityGfAnbarHereket.FK_PATIENT_ID, ent.getFkPatientId());

        insertNewAnbarHereket(c);

    }

    private static void addProductRemain4MehsulIstifadesi(String fkAnbarId,
            String fkProductId, double amount)
            throws QException {
        Carrier c = new Carrier();
        c.setValue("fkAnbarId", fkAnbarId);
        c.setValue("fkProductId", fkProductId);
        c.setValue("remainAmount", amount);
        insertNewProductRemain(c);
    }

    public static Carrier insertNewDiscount(Carrier carrier) throws QException {
        EntityGfDiscount ent = new EntityGfDiscount();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.insert(ent);
        return carrier;
    }

    public Carrier updateDiscount(Carrier carrier) throws QException {
        EntityGfDiscount ent = new EntityGfDiscount();
        ent.setId(carrier.getValue("id").toString());
        EntityManager.select(ent);
        EntityManager.mapCarrierToEntity(carrier, ent, false);
        EntityManager.update(ent);
        return carrier;
    }

    public Carrier deleteDiscount(Carrier carrier) throws QException {
        EntityGfDiscount ent = new EntityGfDiscount();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getDiscountList(Carrier carrier) throws QException {
        EntityGfDiscount ent = new EntityGfDiscount();
        EntityManager.mapCarrierToEntity(carrier, ent);
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getDiscountList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }
    
    public static Carrier getDiscountList4Combo(Carrier carrier) throws QException {
        EntityGfDiscount ent = new EntityGfDiscount();
        ent.setDeepWhere(false);
        ent.setValidDate(CoreLabel.GE+QDate.getCurrentDate());
        Carrier cr = EntityManager.select(ent);

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        return cr;
    }

    public static Carrier insertNewRelDiscountAndPatient(Carrier carrier) throws QException {
        String patientIds[] = carrier.getValue("fkPatientId").toString().trim()
                .split(CoreLabel.SEPERATOR_VERTICAL_LINE);
        for (String id : patientIds) {
            EntityGfRelDiscountAndPatient ent = new EntityGfRelDiscountAndPatient();
            ent.setFkPatientId(id);
            ent.setFkDiscountId(carrier.getValue("fkDiscountId").toString());
            Carrier c= EntityManager.select(ent);
            if (c.getTableRowCount(ent.toTableName())>0){
                continue;
            }
            ent.setDescription(carrier.getValue("description").toString());
            EntityManager.insert(ent);
        }
        return carrier;
    }

    public Carrier deleteRelDiscountAndPatient(Carrier carrier) throws QException {
        EntityGfRelDiscountAndPatient ent = new EntityGfRelDiscountAndPatient();
        EntityManager.mapCarrierToEntity(carrier, ent);
        EntityManager.delete(ent);
        return carrier;
    }

    public static Carrier getRelDiscountAndPatientList(Carrier carrier) throws QException {
        EntityGfRelDiscountAndPatientList ent = new EntityGfRelDiscountAndPatientList();
        EntityManager.mapCarrierToEntity(carrier, ent);
        Carrier cr = EntityManager.select(ent);
        String tn = ent.toTableName();

        cr.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        cr.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("getRelDiscountAndPatientList"));

        cr.addTableRowCount(CoreLabel.RESULT_SET,
                EntityManager.getRowCount(ent) + 1);

        return cr;
    }

}
