/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.gf;

import controllerpool.ControllerPool;
import label.CoreLabel;
import module.gf.entity.EntityGfAnbar;
import module.gf.entity.EntityGfAnbarHereket;
import module.gf.entity.EntityGfAnbarProductOrder;
import module.gf.entity.EntityGfDiscount;
import module.gf.entity.EntityGfPatientSender;
import module.gf.entity.EntityGfPriceList;
import module.gf.entity.EntityGfPriceListRatioByDoctor;
import module.gf.entity.EntityGfPriceListRatioByDoctorList;
import module.gf.entity.EntityGfPriceListRatioByPatientSender;
import module.gf.entity.EntityGfProduct;
import module.gf.entity.EntityGfProductPriceList;
import module.gf.entity.EntityGfProductPurchase;
import module.gf.entity.EntityGfProductRemain;
import module.gf.entity.EntityGfProductUsage;
import module.gf.entity.EntityGfRelDiscountAndPatient;
import module.gf.entity.EntityGfRelPriceListAndSubmodule;
import utility.Carrier;
import utility.QException;
import utility.QUtility;

/**
 *
 * @author user
 */
public class GfController {

    private final String SPACE = " ";
    private final String COMMA = ",";
    private final String COMBO_EMPTY_ID = "__2__";

    public Carrier insertNewProduct(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfProduct.PRODUCT_CODE,
                cp.isNotExistInEntity(new EntityGfProduct(),
                        EntityGfProduct.PRODUCT_CODE,
                        carrier.getValue(EntityGfProduct.PRODUCT_CODE).toString()));
        carrier.addController(EntityGfProduct.PRODUCT_NAME,
                cp.isNotExistInEntity(new EntityGfProduct(),
                        EntityGfProduct.PRODUCT_NAME,
                        carrier.getValue(EntityGfProduct.PRODUCT_NAME).toString()));
        carrier.addController(EntityGfProduct.PRODUCT_NAME,
                cp.hasValue(carrier, EntityGfProduct.PRODUCT_NAME));
        carrier.addController(EntityGfProduct.PRODUCT_CODE,
                cp.hasValue(carrier, EntityGfProduct.PRODUCT_CODE));
        carrier.addController(EntityGfProduct.PACKADAKI_SAY,
                cp.isFloat(carrier, EntityGfProduct.PACKADAKI_SAY));
        carrier.addController(EntityGfProduct.QRAM,
                cp.isFloat(carrier, EntityGfProduct.QRAM));

        return carrier;
    }

    public Carrier updateProduct(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProduct.ID,
                cp.hasValue(carrier, EntityGfProduct.ID));

        carrier.addController(EntityGfProduct.PRODUCT_CODE,
                cp.isNotExistInEntityExcept(new EntityGfProduct(),
                        EntityGfProduct.PRODUCT_CODE,
                        carrier.getValue(EntityGfProduct.PRODUCT_CODE).toString(),
                        carrier.getValue(EntityGfProduct.ID).toString()
                ));
        carrier.addController(EntityGfProduct.PRODUCT_NAME,
                cp.isNotExistInEntityExcept(new EntityGfProduct(),
                        EntityGfProduct.PRODUCT_NAME,
                        carrier.getValue(EntityGfProduct.PRODUCT_NAME).toString(),
                        carrier.getValue(EntityGfProduct.ID).toString()
                ));
        carrier.addController(EntityGfProduct.PRODUCT_NAME,
                cp.hasValue(carrier, EntityGfProduct.PRODUCT_NAME));
        carrier.addController(EntityGfProduct.PRODUCT_CODE,
                cp.hasValue(carrier, EntityGfProduct.PRODUCT_CODE));
        carrier.addController(EntityGfProduct.PACKADAKI_SAY,
                cp.isFloat(carrier, EntityGfProduct.PACKADAKI_SAY));
        carrier.addController(EntityGfProduct.QRAM,
                cp.isFloat(carrier, EntityGfProduct.QRAM));

        return carrier;
    }

    public Carrier deleteProduct(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProduct.ID,
                cp.hasValue(carrier, EntityGfProduct.ID));

        return carrier;
    }

    public Carrier getProductAllList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewAnbar(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfAnbar.ANBAR_NAME,
                cp.hasValue(carrier, EntityGfAnbar.ANBAR_NAME));
        carrier.addController(EntityGfAnbar.ANBAR_NAME,
                cp.isNotExistInEntity(new EntityGfAnbar(),
                        EntityGfAnbar.ANBAR_NAME,
                        carrier.getValue(EntityGfAnbar.ANBAR_NAME).toString()));
        return carrier;
    }

    public Carrier updateAnbar(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfAnbar.ID,
                cp.hasValue(carrier, EntityGfAnbar.ID));

        carrier.addController(EntityGfAnbar.ANBAR_NAME,
                cp.isNotExistInEntityExcept(new EntityGfAnbar(),
                        EntityGfAnbar.ANBAR_NAME,
                        carrier.getValue(EntityGfAnbar.ANBAR_NAME).toString(),
                        carrier.getValue(EntityGfAnbar.ID).toString()
                ));

        return carrier;
    }

    public Carrier deleteAnbar(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfAnbar.ID,
                cp.hasValue(carrier, EntityGfAnbar.ID));

        return carrier;
    }

    public Carrier getAnbarList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewPriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceList.PAYMENT_NAME,
                cp.hasValue(carrier, EntityGfPriceList.PAYMENT_NAME));
        carrier.addController("fkModuleId",
                cp.hasValue(carrier, "fkModuleId"));
        carrier.addController(EntityGfPriceList.PRICE,
                cp.hasValue(carrier, EntityGfPriceList.PRICE));
        carrier.addController(EntityGfPriceList.LIST_STATUS,
                cp.hasValue(carrier, EntityGfPriceList.LIST_STATUS));
        carrier.addController(EntityGfPriceList.PRICE,
                cp.isFloat(carrier, EntityGfPriceList.PRICE));

        return carrier;
    }

    public Carrier updatePriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceList.ID,
                cp.hasValue(carrier, EntityGfPriceList.ID));
        carrier.addController("fkModuleId",
                cp.hasValue(carrier, "fkModuleId"));
        carrier.addController(EntityGfPriceList.PAYMENT_NAME,
                cp.hasValue(carrier, EntityGfPriceList.PAYMENT_NAME));
        carrier.addController(EntityGfPriceList.PRICE,
                cp.hasValue(carrier, EntityGfPriceList.PRICE));
        carrier.addController(EntityGfPriceList.LIST_STATUS,
                cp.hasValue(carrier, EntityGfPriceList.LIST_STATUS));
        carrier.addController(EntityGfPriceList.PRICE,
                cp.isFloat(carrier, EntityGfPriceList.PRICE));

        return carrier;
    }

    public Carrier deletePriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceList.ID,
                cp.hasValue(carrier, EntityGfPriceList.ID));

        return carrier;
    }

    public Carrier getPriceListList(Carrier carrier) {
        return carrier;
    }

    public Carrier getRelPriceListAndSubmoduleList(Carrier carrier) throws QException {
        //ControllerPool cp = new ControllerPool();
        //carrier.addController(EntityCrRelRuleAndPermission.FK_RULE_ID,
        //        cp.hasValue(carrier, EntityCrRelRuleAndPermission.FK_RULE_ID));
        //carrier.addController(EntityCrRelRuleAndPermission.FK_PERMISSION_ID,
        //        cp.hasValue(carrier, EntityCrRelRuleAndPermission.FK_PERMISSION_ID));
        return carrier;
    }

    public Carrier insertNewRelPriceListAndSubmodule(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfRelPriceListAndSubmodule.FK_PRICE_LIST_ID,
                cp.hasValue(carrier, EntityGfRelPriceListAndSubmodule.FK_PRICE_LIST_ID));
        carrier.addController(EntityGfRelPriceListAndSubmodule.FK_SUBMODULE_ID,
                cp.hasValue(carrier, EntityGfRelPriceListAndSubmodule.FK_SUBMODULE_ID));
        return carrier;
    }

    public Carrier deleteRelPriceListAndSubmodule(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfRelPriceListAndSubmodule.ID,
                cp.hasValue(carrier, EntityGfRelPriceListAndSubmodule.ID));
        return carrier;
    }

    public Carrier getSubmoduleListByPriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("fkPriceListId", cp.hasValue(carrier, "fkPriceListId"));
        return carrier;
    }

    public Carrier insertNewPatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        if (!carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString().equals(COMBO_EMPTY_ID)) {
            carrier.setValue(EntityGfPatientSender.SENDER_FULNAME, "");

            carrier.addController(EntityGfPatientSender.FK_DOCTOR_USER_ID,
                    cp.isNotExistInEntity(new EntityGfPatientSender(),
                            EntityGfPatientSender.FK_DOCTOR_USER_ID,
                            carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString()
                    ));
        }

        if (carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString().equals(COMBO_EMPTY_ID)
                && carrier.getValue(EntityGfPatientSender.SENDER_FULNAME).toString().length() == 0) {
            carrier.addController(EntityGfPatientSender.SENDER_FULNAME,
                    "fullnameIsEmpty");
        }

        carrier.addController(EntityGfPatientSender.FK_DOCTOR_USER_ID,
                cp.hasValue(carrier, EntityGfPatientSender.FK_DOCTOR_USER_ID));

        carrier.addController(EntityGfPatientSender.FK_DOCTOR_USER_ID,
                cp.hasValue(carrier, EntityGfPatientSender.FK_DOCTOR_USER_ID));

        return carrier;
    }

    public Carrier updatePatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPatientSender.ID,
                cp.hasValue(carrier, EntityGfPatientSender.ID));

        if (!carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString().equals(COMBO_EMPTY_ID)) {
            carrier.setValue(EntityGfPatientSender.SENDER_FULNAME, "");

            carrier.addController(EntityGfPatientSender.FK_DOCTOR_USER_ID,
                    cp.isNotExistInEntityExcept(new EntityGfPatientSender(),
                            EntityGfPatientSender.FK_DOCTOR_USER_ID,
                            carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString(),
                            carrier.getValue(EntityGfPatientSender.ID).toString()
                    ));
        }

        if (carrier.getValue(EntityGfPatientSender.FK_DOCTOR_USER_ID).toString().equals(COMBO_EMPTY_ID)
                && carrier.getValue(EntityGfPatientSender.SENDER_FULNAME).toString().length() == 0) {
            carrier.addController(EntityGfPatientSender.SENDER_FULNAME,
                    "fullnameIsEmpty");
        }

        carrier.addController(EntityGfPatientSender.FK_DOCTOR_USER_ID,
                cp.hasValue(carrier, EntityGfPatientSender.FK_DOCTOR_USER_ID));
        return carrier;
    }

    public Carrier deletePatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPatientSender.ID,
                cp.hasValue(carrier, EntityGfPatientSender.ID));

        return carrier;
    }

    public Carrier getPatientSenderList(Carrier carrier) {
        return carrier;
    }

    public Carrier getDoctorList4PatientSender(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewPriceListRatioByDoctor(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfPriceListRatioByDoctor.FK_PRICE_LIST_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.FK_PRICE_LIST_ID));

        carrier.addController(EntityGfPriceListRatioByDoctor.FK_DOCTOR_USER_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.FK_DOCTOR_USER_ID));

        carrier.addController(EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR));

        carrier.addController(EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR,
                cp.isFloat(carrier, EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR));

        return carrier;
    }

    public Carrier updatePriceListRatioByDoctor(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceListRatioByDoctor.ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.ID));

        carrier.addController(EntityGfPriceListRatioByDoctor.FK_PRICE_LIST_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.FK_PRICE_LIST_ID));

        carrier.addController(EntityGfPriceListRatioByDoctor.FK_DOCTOR_USER_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.FK_DOCTOR_USER_ID));

        carrier.addController(EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR));

        carrier.addController(EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR,
                cp.isFloat(carrier, EntityGfPriceListRatioByDoctor.RATIO_OF_DOCTOR));
        return carrier;
    }

    public Carrier deletePriceListRatioByDoctor(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceListRatioByDoctor.ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByDoctorList.ID));

        return carrier;
    }

    public Carrier getPriceListRatioByDoctorList(Carrier carrier) {
        return carrier;
    }

    public static Carrier getPriceList4RatioCombo(Carrier QException) throws QException {

        return QException;
    }

    public Carrier insertNewPriceListRatioByPatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfPriceListRatioByPatientSender.FK_PRICE_LIST_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.FK_PRICE_LIST_ID));

        carrier.addController(EntityGfPriceListRatioByPatientSender.FK_PATIENT_SENDER_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.FK_PATIENT_SENDER_ID));

        carrier.addController(EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER));

        carrier.addController(EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER,
                cp.isFloat(carrier, EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER));

        return carrier;
    }

    public Carrier updatePriceListRatioByPatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceListRatioByPatientSender.ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.ID));

        carrier.addController(EntityGfPriceListRatioByPatientSender.FK_PRICE_LIST_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.FK_PRICE_LIST_ID));

        carrier.addController(EntityGfPriceListRatioByPatientSender.FK_PATIENT_SENDER_ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.FK_PATIENT_SENDER_ID));

        carrier.addController(EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER));

        carrier.addController(EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER,
                cp.isFloat(carrier, EntityGfPriceListRatioByPatientSender.RATIO_OF_PATIENT_SENDER));

        return carrier;
    }

    public Carrier deletePriceListRatioByPatientSender(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfPriceListRatioByPatientSender.ID,
                cp.hasValue(carrier, EntityGfPriceListRatioByPatientSender.ID));

        return carrier;
    }

    public Carrier getPriceListRatioByPatientSenderList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewProductPriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfProductPriceList.FK_PRODUCT_ID,
                cp.isNotExistInEntity(new EntityGfProductPriceList(),
                        EntityGfProductPriceList.FK_PRODUCT_ID,
                        carrier.getValue(EntityGfProductPriceList.FK_PRODUCT_ID).toString()));

        carrier.addController(EntityGfProductPriceList.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductPriceList.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductPriceList.AVERAGE_COST,
                cp.hasValue(carrier, EntityGfProductPriceList.AVERAGE_COST));

        carrier.addController(EntityGfProductPriceList.SALE_PRICE,
                cp.hasValue(carrier, EntityGfProductPriceList.SALE_PRICE));

        carrier.addController(EntityGfProductPriceList.AVERAGE_COST,
                cp.isFloat(carrier, EntityGfProductPriceList.AVERAGE_COST));

        carrier.addController(EntityGfProductPriceList.SALE_PRICE,
                cp.isFloat(carrier, EntityGfProductPriceList.SALE_PRICE));
        return carrier;
    }

    public Carrier updateProductPriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductPriceList.ID,
                cp.hasValue(carrier, EntityGfProductPriceList.ID));

        carrier.addController(EntityGfProductPriceList.FK_PRODUCT_ID,
                cp.isNotExistInEntityExcept(new EntityGfProductPriceList(),
                        EntityGfProductPriceList.FK_PRODUCT_ID,
                        carrier.getValue(EntityGfProductPriceList.FK_PRODUCT_ID).toString(),
                        carrier.getValue(EntityGfProductPriceList.ID).toString()));

        carrier.addController(EntityGfProductPriceList.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductPriceList.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductPriceList.AVERAGE_COST,
                cp.hasValue(carrier, EntityGfProductPriceList.AVERAGE_COST));

        carrier.addController(EntityGfProductPriceList.SALE_PRICE,
                cp.hasValue(carrier, EntityGfProductPriceList.SALE_PRICE));

        carrier.addController(EntityGfProductPriceList.AVERAGE_COST,
                cp.isFloat(carrier, EntityGfProductPriceList.AVERAGE_COST));

        carrier.addController(EntityGfProductPriceList.SALE_PRICE,
                cp.isFloat(carrier, EntityGfProductPriceList.SALE_PRICE));

        return carrier;
    }

    public Carrier deleteProductPriceList(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductPriceList.ID,
                cp.hasValue(carrier, EntityGfProductPriceList.ID));

        return carrier;
    }

    public Carrier getProductPriceListList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewProductPurchase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfProductPurchase.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductPurchase.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductPurchase.FK_ANBAR_ID,
                cp.hasValue(carrier, EntityGfProductPurchase.FK_ANBAR_ID));

        carrier.addController(EntityGfProductPurchase.PURCHASE_DATE,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_DATE));

        carrier.addController(EntityGfProductPurchase.PURCHASE_AMOUNT,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_AMOUNT));

        carrier.addController(EntityGfProductPurchase.PURCHASE_PRICE,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_PRICE));

        carrier.addController(EntityGfProductPurchase.PURCHASE_PRICE,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_PRICE));
        carrier.addController(EntityGfProductPurchase.PURCHASE_DISCOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_DISCOUNT));
        carrier.addController(EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT));
        carrier.addController(EntityGfProductPurchase.PURCHASE_AMOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_AMOUNT));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_1,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_1));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_2,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_2));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_4,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_4));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_3,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_3));

        return carrier;
    }

    public Carrier updateProductPurchase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductPurchase.ID,
                cp.hasValue(carrier, EntityGfProductPurchase.ID));

        carrier.addController(EntityGfProductPurchase.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductPurchase.FK_PRODUCT_ID));
        carrier.addController(EntityGfProductPurchase.FK_ANBAR_ID,
                cp.hasValue(carrier, EntityGfProductPurchase.FK_ANBAR_ID));
        carrier.addController(EntityGfProductPurchase.PURCHASE_DATE,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_DATE));

        carrier.addController(EntityGfProductPurchase.PURCHASE_AMOUNT,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_AMOUNT));

        carrier.addController(EntityGfProductPurchase.PURCHASE_PRICE,
                cp.hasValue(carrier, EntityGfProductPurchase.PURCHASE_PRICE));

        carrier.addController(EntityGfProductPurchase.PURCHASE_PRICE,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_PRICE));
        carrier.addController(EntityGfProductPurchase.PURCHASE_DISCOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_DISCOUNT));
        carrier.addController(EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_TOTAL_AMOUNT));

        carrier.addController(EntityGfProductPurchase.PURCHASE_AMOUNT,
                cp.isFloat(carrier, EntityGfProductPurchase.PURCHASE_AMOUNT));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_1,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_1));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_2,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_2));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_4,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_4));
        carrier.addController(EntityGfProductPurchase.EXTRA_COST_3,
                cp.isFloat(carrier, EntityGfProductPurchase.EXTRA_COST_3));

        return carrier;
    }

    public Carrier deleteProductPurchase(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductPurchase.ID,
                cp.hasValue(carrier, EntityGfProductPurchase.ID));

        return carrier;
    }

    public Carrier getProductPurchaseList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewProductRemain(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfProductRemain.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductRemain.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductRemain.FK_ANBAR_ID,
                cp.hasValue(carrier, EntityGfProductRemain.FK_ANBAR_ID));

        carrier.addController(EntityGfProductRemain.REMAIN_AMOUNT,
                cp.hasValue(carrier, EntityGfProductRemain.REMAIN_AMOUNT));

        carrier.addController(EntityGfProductRemain.REMAIN_AMOUNT,
                cp.isFloat(carrier, EntityGfProductRemain.REMAIN_AMOUNT));

        return carrier;
    }

    public Carrier getProductRemainList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewAnbarHereket(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        String sourceId
                = carrier.getValue(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID).toString()
                + carrier.getValue(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID).toString()
                + carrier.getValue(EntityGfAnbarHereket.FK_PATIENT_ID).toString();

        if (sourceId.trim().length() == 0) {
            carrier.addController(EntityGfAnbarHereket.FK_PRODUCT_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
            carrier.addController(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
            carrier.addController(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
        }

        carrier.addController(EntityGfAnbarHereket.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfAnbarHereket.FK_PRODUCT_ID));

        carrier.addController(EntityGfAnbarHereket.OPERATION_TYPE,
                cp.hasValue(carrier, EntityGfAnbarHereket.OPERATION_TYPE));

        carrier.addController(EntityGfAnbarHereket.AMOUNT,
                cp.hasValue(carrier, EntityGfAnbarHereket.AMOUNT));
        carrier.addController(EntityGfAnbarHereket.AMOUNT,
                cp.isFloat(carrier, EntityGfAnbarHereket.AMOUNT));

        return carrier;
    }

    public Carrier updateAnbarHereket(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfAnbarHereket.ID,
                cp.hasValue(carrier, EntityGfAnbarHereket.ID));

        String sourceId
                = carrier.getValue(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID).toString()
                + carrier.getValue(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID).toString()
                + carrier.getValue(EntityGfAnbarHereket.FK_PATIENT_ID).toString();

        if (sourceId.trim().length() == 0) {
            carrier.addController(EntityGfAnbarHereket.FK_PRODUCT_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
            carrier.addController(EntityGfAnbarHereket.FK_SOURCE_ANBAR_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
            carrier.addController(EntityGfAnbarHereket.FK_DESTINATION_ANBAR_ID,
                    QUtility.getLabel("melumatlardanHerHansiBiriDaxilEdilmemisdir"));
        }

        carrier.addController(EntityGfAnbarHereket.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfAnbarHereket.FK_PRODUCT_ID));

        carrier.addController(EntityGfAnbarHereket.OPERATION_TYPE,
                cp.hasValue(carrier, EntityGfAnbarHereket.OPERATION_TYPE));

        carrier.addController(EntityGfAnbarHereket.AMOUNT,
                cp.hasValue(carrier, EntityGfAnbarHereket.AMOUNT));
        carrier.addController(EntityGfAnbarHereket.AMOUNT,
                cp.isFloat(carrier, EntityGfAnbarHereket.AMOUNT));

        return carrier;
    }

//    public Carrier deleteAnbarHereket(Carrier carrier) throws QException {
//        ControllerPool cp = new ControllerPool();
//        carrier.addController(EntityGfAnbarHereket.ID,
//                cp.hasValue(carrier, EntityGfAnbarHereket.ID));
//
//        return carrier;
//    }
    public Carrier getAnbarHereketList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewAnbarProductOrder(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfAnbarProductOrder.FK_SOURCE_ANBAR_ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.FK_SOURCE_ANBAR_ID));

        carrier.addController(EntityGfAnbarProductOrder.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.FK_PRODUCT_ID));

        carrier.addController(EntityGfAnbarProductOrder.AMOUNT,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.AMOUNT));
        carrier.addController(EntityGfAnbarProductOrder.AMOUNT,
                cp.isFloat(carrier, EntityGfAnbarProductOrder.AMOUNT));

        return carrier;
    }

    public static Carrier confirmAnbarProductOrder(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController("general",
                cp.hasValue(carrier, EntityGfAnbarProductOrder.ID));
        return carrier;
    }

    public Carrier updateAnbarProductOrder(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfAnbarProductOrder.ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.ID));

        carrier.addController(EntityGfAnbarProductOrder.FK_SOURCE_ANBAR_ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.FK_SOURCE_ANBAR_ID));

        carrier.addController(EntityGfAnbarProductOrder.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.FK_PRODUCT_ID));

        carrier.addController(EntityGfAnbarProductOrder.AMOUNT,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.AMOUNT));
        carrier.addController(EntityGfAnbarProductOrder.AMOUNT,
                cp.isFloat(carrier, EntityGfAnbarProductOrder.AMOUNT));

        return carrier;
    }

    public Carrier deleteAnbarProductOrder(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfAnbarProductOrder.ID,
                cp.hasValue(carrier, EntityGfAnbarProductOrder.ID));

        return carrier;
    }

    public Carrier getAnbarProductOrderList(Carrier carrier) {
        return carrier;
    }

    public Carrier getAnbarListWithPermission(Carrier carrier) {
        return carrier;
    }

    public Carrier getProductRemainListWithPermission(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewProductUsage(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfProductUsage.FK_PATIENT_ID,
                cp.hasValue(carrier, EntityGfProductUsage.FK_PATIENT_ID));

        carrier.addController(EntityGfProductUsage.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductUsage.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductUsage.USAGE_PURPOSE,
                cp.hasValue(carrier, EntityGfProductUsage.USAGE_PURPOSE));

        carrier.addController(EntityGfProductUsage.AMOUNT,
                cp.hasValue(carrier, EntityGfProductUsage.AMOUNT));
        carrier.addController(EntityGfProductUsage.AMOUNT,
                cp.isFloat(carrier, EntityGfProductUsage.AMOUNT));

        return carrier;
    }

    public Carrier updateProductUsage(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductUsage.ID,
                cp.hasValue(carrier, EntityGfProductUsage.ID));
        carrier.addController(EntityGfProductUsage.FK_PATIENT_ID,
                cp.hasValue(carrier, EntityGfProductUsage.FK_PATIENT_ID));

        carrier.addController(EntityGfProductUsage.FK_PRODUCT_ID,
                cp.hasValue(carrier, EntityGfProductUsage.FK_PRODUCT_ID));

        carrier.addController(EntityGfProductUsage.USAGE_PURPOSE,
                cp.hasValue(carrier, EntityGfProductUsage.USAGE_PURPOSE));

        carrier.addController(EntityGfProductUsage.AMOUNT,
                cp.hasValue(carrier, EntityGfProductUsage.AMOUNT));
        carrier.addController(EntityGfProductUsage.AMOUNT,
                cp.isFloat(carrier, EntityGfProductUsage.AMOUNT));

        return carrier;
    }

    public Carrier deleteProductUsage(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfProductUsage.ID,
                cp.hasValue(carrier, EntityGfProductUsage.ID));

        return carrier;
    }

    public Carrier getProductUsageList(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewDiscount(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfDiscount.DISCOUNT_NAME,
                cp.hasValue(carrier, EntityGfDiscount.DISCOUNT_NAME));

        carrier.addController(EntityGfDiscount.DISCOUNT_AMOUNT,
                cp.hasValue(carrier, EntityGfDiscount.DISCOUNT_AMOUNT));

        carrier.addController(EntityGfDiscount.VALID_DATE,
                cp.hasValue(carrier, EntityGfDiscount.VALID_DATE));

        carrier.addController(EntityGfDiscount.DISCOUNT_AMOUNT,
                cp.isFloat(carrier, EntityGfDiscount.DISCOUNT_AMOUNT));

        return carrier;
    }

    public Carrier updateDiscount(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfDiscount.ID,
                cp.hasValue(carrier, EntityGfDiscount.ID));

        carrier.addController(EntityGfDiscount.DISCOUNT_NAME,
                cp.hasValue(carrier, EntityGfDiscount.DISCOUNT_NAME));

        carrier.addController(EntityGfDiscount.DISCOUNT_AMOUNT,
                cp.hasValue(carrier, EntityGfDiscount.DISCOUNT_AMOUNT));

        carrier.addController(EntityGfDiscount.VALID_DATE,
                cp.hasValue(carrier, EntityGfDiscount.VALID_DATE));

        carrier.addController(EntityGfDiscount.DISCOUNT_AMOUNT,
                cp.isFloat(carrier, EntityGfDiscount.DISCOUNT_AMOUNT));

        return carrier;
    }

    public Carrier deleteDiscount(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfDiscount.ID,
                cp.hasValue(carrier, EntityGfDiscount.ID));

        return carrier;
    }

    public Carrier getDiscountList(Carrier carrier) {
        return carrier;
    }
    
    public Carrier getDiscountList4Combo(Carrier carrier) {
        return carrier;
    }

    public Carrier insertNewRelDiscountAndPatient(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();

        carrier.addController(EntityGfRelDiscountAndPatient.FK_DISCOUNT_ID,
                cp.hasValue(carrier, EntityGfRelDiscountAndPatient.FK_DISCOUNT_ID));

        carrier.addController(EntityGfRelDiscountAndPatient.FK_PATIENT_ID,
                cp.hasValue(carrier, EntityGfRelDiscountAndPatient.FK_PATIENT_ID));

        return carrier;
    }

    public Carrier deleteRelDiscountAndPatient(Carrier carrier) throws QException {
        ControllerPool cp = new ControllerPool();
        carrier.addController(EntityGfRelDiscountAndPatient.ID,
                cp.hasValue(carrier, EntityGfRelDiscountAndPatient.ID));

        return carrier;
    }

    public Carrier getRelDiscountAndPatientList(Carrier carrier) {
        return carrier;
    }

}
