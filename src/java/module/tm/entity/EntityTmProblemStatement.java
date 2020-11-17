package module.tm.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityTmProblemStatement extends CoreEntity {

    public static String PROBLEM_DESC = "problemDesc";
    private String problemDesc = "";
    public static String COUNT_POTENTIAL_CUSTOMER = "countPotentialCustomer";
    private String countPotentialCustomer = "";
    public static String COUNT_REAL_CUSTOMER = "countRealCustomer";
    private String countRealCustomer = "";
    public static String ORDER_NO = "orderNo";
    private String orderNo = "";
    public static String FK_BC_ID = "fkBcId";
    private String fkBcId = "";

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getCountPotentialCustomer() {
        return countPotentialCustomer;
    }

    public void setCountPotentialCustomer(String countPotentialCustomer) {
        this.countPotentialCustomer = countPotentialCustomer;
    }

    public String getCountRealCustomer() {
        return countRealCustomer;
    }

    public void setCountRealCustomer(String countRealCustomer) {
        this.countRealCustomer = countRealCustomer;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFkBcId() {
        return fkBcId;
    }

    public void setFkBcId(String fkBcId) {
        this.fkBcId = fkBcId;
    }

    
    
}
