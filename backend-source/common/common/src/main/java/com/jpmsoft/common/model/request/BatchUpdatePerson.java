package com.jpmsoft.common.model.request;

import java.util.List;

/**
 * 批量修改责任人
 */
public class BatchUpdatePerson {

    private List<String> allIds;
    private  String pId;
    private String personId;
    private String personName;
    private String cpId;

    public List<String> getAllIds() {
        return allIds;
    }

    public void setAllIds(List<String> allIds) {
        this.allIds = allIds;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }
}
