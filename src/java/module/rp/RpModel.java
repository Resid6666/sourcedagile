/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.rp;

import utility.Carrier;
import utility.QException;
import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import j2html.tags.Tag;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import label.CoreLabel;
import module.cr.CrModel;
import static module.cr.CrModel.getModuleList;
import static module.cr.CrModel.getPriceListList;
import module.cr.entity.EntityCrAppointment;
import module.cr.entity.EntityCrAppointmentList;
import module.cr.entity.EntityCrInspection;
import module.cr.entity.EntityCrInspectionList;
import module.cr.entity.EntityCrListItem;
import module.cr.entity.EntityCrPatient;
import module.cr.entity.EntityCrPriceListList;
import module.cr.entity.EntityCrPrivateAttribute;
import module.cr.entity.EntityCrPrivateSubmodule;
import module.cr.entity.EntityCrSubmodule;
import module.cr.entity.EntityCrSubmoduleAttribute;
import module.cr.entity.EntityCrSubmoduleAttributeList;
import module.rp.entity.EntityRpAppointmentListWithExpense;
import utility.CacheUtil;
import utility.QUtility;
import utility.SessionManager;
import utility.ValueTypeHtml;
import utility.sqlgenerator.EntityManager;

/**
 *
 * @author user
 */
public class RpModel {

    private static final String USER_CONTROLLER_TYPE_ENUB = "2";
    private static final String USER_CONTROLLER_TYPE_COMPONENT = "1";
    private static final String PREFIX_SUBMODULE_ATTRIBUTE = "sa_";
    private static final String PREFIX_SUBMODULE_ATTRIBUTE_HAS_OTHER = "ha_";

    public static final String VALUE_TYPE_INTEGER = "13";
    public static final String VALUE_TYPE_FLOAT = "7";
    public static final String VALUE_TYPE_POSITIVE_INTEGER = "11";
    public static final String VALUE_TYPE_POSITIVE_FLOAT = "6";
    public static final String VALUE_TYPE_TEXTAREA = "14";
    public static final String VALUE_TYPE_STRING = "12";
    public static final String VALUE_TYPE_RANGE_INTEGER = "10";
    public static final String VALUE_TYPE_RANGE_STRING = "9";
    public static final String VALUE_TYPE_RANGE_STRING_MULTI = "8";
    public static final String VALUE_TYPE_MIN_MAX_INTEGER = "5";
    public static final String VALUE_TYPE_MIN_MAX_FLOAT = "4";
    public static final String VALUE_TYPE_PICTURE = "3";
    public static final String VALUE_TYPE_PICTURE_URL = "16";
    public static final String VALUE_TYPE_VIDEO_UPLOAD = "2";
    public static final String VALUE_TYPE_VIDEO_URL = "1";
    public static final String VALUE_TYPE_RANGE_INTEGER_MULTI = "15";
    public static final String VALUE_TYPE_YOUTUBE_URL = "17";
    public static final String VALUE_TYPE_SOUND_URL = "18";
    public static final String VALUE_TYPE_SOUND_UPLOAD = "19";
    public static final String VALUE_TYPE_DATE = "20";
    public static final String RANGE_STRING_MULTI_MANUAL = "21";
    public static final String RANGE_STRING_MANUAL = "22";

    public static final String SUBMODULE_BUTTON_COLOR_IF_EMPTY = "#ABB2B9";
    public static final String SUBMODULE_BUTTON_COLOR_IF_FULL = "#00b289";
    public static final String SUBMODULE_BUTTON_COLOR_IF_HALF = "#EB984E";

    public static final String SUBMODULE_TYPE_ORDINARY = "1";
    public static final String SUBMODULE_TYPE_AGGREGATE = "2";
    public static final String SUBMODULE_TYPE_AVERAGE = "3";
    public static final String SUBMODULE_TYPE_TREATMENT = "4";
    public static final String SUBMODULE_TYPE_LAST_REMARK = "5";

    public static void main(String[] arg) throws QException {
        String id = "201705051736080211";
        String mid = "201706221522420346";
        String sid = "";
        
//        System.out.println(st);
    }

   public static Carrier appointmentListWithExpense(Carrier carrier)
            throws QException {
 
        String sl = carrier.getValue("startLimit").toString();
        String el = carrier.getValue("endLimit").toString();

        System.out.println("geldi");

        Carrier cprApptmntStts = QUtility.getListItem("appointmentStatus",
                carrier.getValue("appointmentStatusName").toString());
        String apptmntStts = CrModel.convertArrayToFilterLine(cprApptmntStts.getKeys());
        Carrier cprApptStatus = CrModel.getAppointmentStatusForAppoinment(carrier);
        String apptStatusKeys = carrier.getValue("appointmentStatusName").toString().length()>0
                ?CrModel.convertArrayToFilterLine(cprApptStatus.getKeys())
                :"";

//        carrier.setValue("paymentName", carrier.getValue("purpose"));
//        Carrier crpPriceList = CrModel.getPriceListList(carrier)
//                .getKVFromTable(CoreLabel.RESULT_SET, "id", EntityCrPriceListList.PAYMENT_NAME);
//        boolean hasPriceListVal = carrier.getValue("purpose").toString().length() > 0;
//        String priceListIds = hasPriceListVal
//                ? CrModel.convertArrayToFilterLine(crpPriceList.getKeys())
//                : "";

        boolean hasModuleVal = carrier.getValue("moduleName").toString().length() > 0;
        Carrier cModule = new Carrier();
        cModule.setValue("moduleName", carrier.getValue("moduleName"));
        cModule = hasModuleVal ? getModuleList(cModule)
                .getKVFromTable(CoreLabel.RESULT_SET, "id", "moduleName")
                : CacheUtil.getFromCache(CacheUtil.CACHE_KEY_MODULE);
        String fkModuleIds = hasModuleVal
                ? CrModel.convertArrayToFilterLine(cModule.getKeys())
                : "";

//        //eger user hekimdirse yalniz oz xestelerini gore biler
//        String fkDoctorUserId = SessionManager.isCurrentUserSimpleDoctor()
//                ? SessionManager.getCurrentUserId()
//                : "";

        EntityRpAppointmentListWithExpense ent = new EntityRpAppointmentListWithExpense();
        EntityManager.mapCarrierToEntity(carrier, ent);
        ent.setStatus("A");
//        ent.setFkPriceListId(priceListIds);
        ent.setFkModuleId(fkModuleIds);
        ent.setAppointmentStatus(apptmntStts);
        ent.setAppointmentStatus(apptStatusKeys);
        ent.setStartLimit(sl);
        ent.setEndLimit(el);
        if (!ent.hasSortBy()) {
            ent.addSortBy(EntityCrAppointment.APPOINTMENT_STATUS);
            ent.addSortBy(EntityCrAppointment.APPOINTMENT_DATE);
            ent.setSortByAsc(true);
        }
        Carrier crAppt = EntityManager.select(ent);
        String tnAppt = ent.toTableName();

//        Carrier cprPayment = getPaymentInfoForAppointment(carrier);
//        crAppt.mergeCarrier(tnAppt, "fkPriceListId", "purpose", crpPriceList, !hasPriceListVal);
        crAppt.mergeCarrier(tnAppt, "appointmentStatus",
                "appointmentStatusName", cprApptStatus);
//        crAppt.mergeCarrier(tnAppt,
//                new String[]{"fkPatientId", "fkDoctorUserId", "appointmentDate"},
//                "paymentStatus", cprPayment, true);

        String[] moduleField = hasModuleVal
                ? new String[]{"fkModuleId", ""}
                : new String[]{"fkModuleId", "LANG"};
        crAppt.mergeCarrier(tnAppt, moduleField,
                "moduleName", cModule, !hasModuleVal);

        crAppt.renameTableName(ent.toTableName(), CoreLabel.RESULT_SET);
        crAppt.addTableSequence(CoreLabel.RESULT_SET,
                EntityManager.getListSequenceByKey("appointmentListWithExpense"));

        EntityManager.mapCarrierToEntity(carrier, ent);
        crAppt.addTableRowCount(CoreLabel.RESULT_SET, EntityManager.getRowCount(ent));

        return crAppt;

    }

   
}
