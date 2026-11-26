package com.jpmsoft.userProducer.pojo.vo;

public class SysToolTableRelationshipVo {
    private String id;
    private String sttrMainTable;
    private String sttrMainDescribe;
    private String sttrMainKey;
    private String sttrAssociationTable;
    private String sttrAssociationKey;
    private String sttrAssociationDescribe;
    private String sttrOwner;
    public Integer orderNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSttrMainTable() {
        return sttrMainTable;
    }

    public void setSttrMainTable(String sttrMainTable) {
        this.sttrMainTable = sttrMainTable;
    }

    public String getSttrMainDescribe() {
        return sttrMainDescribe;
    }

    public void setSttrMainDescribe(String sttrMainDescribe) {
        this.sttrMainDescribe = sttrMainDescribe;
    }

    public String getSttrMainKey() {
        return sttrMainKey;
    }

    public void setSttrMainKey(String sttrMainKey) {
        this.sttrMainKey = sttrMainKey;
    }

    public String getSttrAssociationTable() {
        return sttrAssociationTable;
    }

    public void setSttrAssociationTable(String sttrAssociationTable) {
        this.sttrAssociationTable = sttrAssociationTable;
    }

    public String getSttrAssociationKey() {
        return sttrAssociationKey;
    }

    public void setSttrAssociationKey(String sttrAssociationKey) {
        this.sttrAssociationKey = sttrAssociationKey;
    }

    public String getSttrAssociationDescribe() {
        return sttrAssociationDescribe;
    }

    public void setSttrAssociationDescribe(String sttrAssociationDescribe) {
        this.sttrAssociationDescribe = sttrAssociationDescribe;
    }

    public String getSttrOwner() {
        return sttrOwner;
    }

    public void setSttrOwner(String sttrOwner) {
        this.sttrOwner = sttrOwner;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
