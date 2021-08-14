/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package module.tm.entity;

import utility.CoreEntity;

/**
 *
 * @author resid
 */
public class EntityTmProcessList extends CoreEntity {
    
    public static String PROCESS_NAME = "processName";
    private String processName = "";
    
    public static String CREATED_BY = "createdBy";
    private String createdBy = "";

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    
}
