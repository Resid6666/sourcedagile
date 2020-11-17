package module.gf.entity;

import module.cr.entity.*;
import utility.CoreEntity;

/**
 *
 * @author user
 */
public class EntityGfAnbarProductOrderList extends CoreEntity {

    public static String ID = "id";
    public static String STATUS = "status";
    public static String INSERT_DATE = "insertDate";
    public static String MODIFICATION_DATE = "modificationDate";
    public static String FK_EXECUTOR_USER_ID = "fkExecutorUserId";
    public static String EXECUTOR_USER_FULNAME = "executorUserFulname";
    public static String SOURCE_ANBAR_NAME = "sourceAnbarName";
    public static String FK_SOURCE_ANBAR_ID = "fkSourceAnbarId";
    public static String FK_SUPPLIER_USER_ID = "fkSupplierUserId";
    public static String SUPPLIER_USER_FULNAME = "supplierUserFulname";
    public static String FK_DESTINATION_ANBAR_ID = "fkDestinationAnbarId";
    public static String DESTINATION_ANBAR_NAME = "destinationAnbarName";
    public static String FK_PRODUCT_ID = "fkProductId";
    public static String PRODUCT_NAME = "productName";
    public static String PRODUCT_CODE = "productCode";
    public static String PRODUCT_UNIT = "productUnit";
    public static String ORDER_DATE = "orderDate";
    public static String ORDER_TIME = "orderTime";
    public static String APPROVED_DATE = "approvedDate";
    public static String APPROVED_TIME = "approvedTime";
    public static String AMOUNT = "amount";
    public static String ORDER_STATUS = "orderStatus";
    public static String DESCRIPTION = "description";

    private String fkExecutorUserId = "";
    private String executorUserFulname = "";
    private String sourceAnbarName = "";
    private String fkSourceAnbarId = "";
    private String fkSupplierUserId = "";
    private String supplierUserFulname = "";
    private String fkDestinationAnbarId = "";
    private String destinationAnbarName = "";
    private String fkProductId = "";
    private String productName = "";
    private String productCode = "";
    private String productUnit = "";
    private String orderDate = "";
    private String orderTime = "";
    private String approvedDate = "";
    private String approvedTime = "";
    private String amount = "";
    private String orderStatus = "";
    private String description = "";

    public String getSourceAnbarName() {
        return sourceAnbarName;
    }

    public void setSourceAnbarName(String sourceAnbarName) {
        this.sourceAnbarName = sourceAnbarName;
    }

    public String getExecutorUserFulname() {
        return executorUserFulname;
    }

    public void setExecutorUserFulname(String executorUserFulname) {
        this.executorUserFulname = executorUserFulname;
    }

     

    public String getSupplierUserFulname() {
        return supplierUserFulname;
    }

    public void setSupplierUserFulname(String supplierUserFulname) {
        this.supplierUserFulname = supplierUserFulname;
    }

    public String getDestinationAnbarName() {
        return destinationAnbarName;
    }

    public void setDestinationAnbarName(String destinationAnbarName) {
        this.destinationAnbarName = destinationAnbarName;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getFkExecutorUserId() {
        return fkExecutorUserId;
    }

    public void setFkExecutorUserId(String fkExecutorUserId) {
        this.fkExecutorUserId = fkExecutorUserId;
    }

    public String getFkSourceAnbarId() {
        return fkSourceAnbarId;
    }

    public void setFkSourceAnbarId(String fkSourceAnbarId) {
        this.fkSourceAnbarId = fkSourceAnbarId;
    }

    public String getFkSupplierUserId() {
        return fkSupplierUserId;
    }

    public void setFkSupplierUserId(String fkSupplierUserId) {
        this.fkSupplierUserId = fkSupplierUserId;
    }

    public String getFkDestinationAnbarId() {
        return fkDestinationAnbarId;
    }

    public void setFkDestinationAnbarId(String fkDestinationAnbarId) {
        this.fkDestinationAnbarId = fkDestinationAnbarId;
    }

    public String getFkProductId() {
        return fkProductId;
    }

    public void setFkProductId(String fkProductId) {
        this.fkProductId = fkProductId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
