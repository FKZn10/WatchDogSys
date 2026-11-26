package com.jpmsoft.jpbpm.pojo.vo;

import java.util.Date;

public  class ActityinfoVo{
    private  String ActionId;
    private  String ActionName;
    private  String OperationType;
    private Date Dotime;
    private  String OperationMan;
    private  String RelationDd;


    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public String getOperationType() {
        return OperationType;
    }

    public void setOperationType(String operationType) {
        OperationType = operationType;
    }

    public Date getDotime() {
        return Dotime;
    }

    public void setDotime(Date dotime) {
        Dotime = dotime;
    }

    public String getOperationMan() {
        return OperationMan;
    }

    public void setOperationMan(String operationMan) {
        OperationMan = operationMan;
    }

    public String getRelationDd() {
        return RelationDd;
    }

    public void setRelationDd(String relationDd) {
        RelationDd = relationDd;
    }

    public String getActionId() {
        return ActionId;
    }

    public void setActionId(String actionId) {
        ActionId = actionId;
    }
}