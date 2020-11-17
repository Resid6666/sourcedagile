package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfProduct extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String COUNTRY = "country"; 
    public static String COMPANY = "company";  
    public static String HOW_TO_USE = "howToUse";
    public static String DESCRIPTION = "description";  
    public static String PARAM_1 = "param1";
    public static String PARAM_2 = "param2";
    public static String PARAM_3 = "param3";
    public static String PARAM_4 = "param4";
    public static String PARAM_5 = "param5";
    public static String PARAM_6 = "param6";
    public static String PRODUCT_TYPE = "productType";
    public static String PACKADAKI_SAY = "packadakiSay";
    public static String QRAM = "qram";
    public static String DOZA = "doza";
    public static String QUTUDAKI_SAYI = "qutudakiSayi";
    public static String UNIT = "unit";
    
    private String unit = "";
    private String productName = "";
    private String productCode = "";
    private String country = "";
    private String company = "";
    private String howToUse = "";
    private String description = "";
    private String param1 = "";
    private String param2 = "";
    private String param3 = "";
    private String param4 = ""; 
    private String param5 = "";
    private String param6 = "";
    private String productType = "";
    private String packadakiSay = "";
    private String qram = "";
    private String doza = "";
    private String qutudakiSayi = "";

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
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

    public String getParam5() {
        return param5;
    }

    public void setParam5(String param5) {
        this.param5 = param5;
    }

    public String getParam6() {
        return param6;
    }

    public void setParam6(String param6) {
        this.param6 = param6;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPackadakiSay() {
        return packadakiSay;
    }

    public void setPackadakiSay(String packadakiSay) {
        this.packadakiSay = packadakiSay;
    }

    public String getQram() {
        return qram;
    }

    public void setQram(String qram) {
        this.qram = qram;
    }

    public String getDoza() {
        return doza;
    }

    public void setDoza(String doza) {
        this.doza = doza;
    }

    public String getQutudakiSayi() {
        return qutudakiSayi;
    }

    public void setQutudakiSayi(String qutudakiSayi) {
        this.qutudakiSayi = qutudakiSayi;
    }

}
