package com.jpmsoft.common.model.request.SecInfo;


/**
 * 资格审查小组清单
 */

public class SecQeGroupDtlRequest {

    private String qegId;
    private String qegdUid;
    private Integer qegdSort;
    private String qegdMobilePhone;
    private String qegdUserName;
    private String qegdCid;
    private String qegdCname;
    private String qegdRemark;
    public String getQegId() {
        return qegId;
    }

    public void setQegId(String qegId) {
        this.qegId = qegId;
    }

    public String getQegdUid() {
        return qegdUid;
    }

    public void setQegdUid(String qegdUid) {
        this.qegdUid = qegdUid;
    }

    public Integer getQegdSort() {
        return qegdSort;
    }

    public void setQegdSort(Integer qegdSort) {
        this.qegdSort = qegdSort;
    }

    public String getQegdMobilePhone() {
        return qegdMobilePhone;
    }

    public void setQegdMobilePhone(String qegdMobilePhone) {
        this.qegdMobilePhone = qegdMobilePhone;
    }

    public String getQegdUserName() {
        return qegdUserName;
    }

    public void setQegdUserName(String qegdUserName) {
        this.qegdUserName = qegdUserName;
    }

    public String getQegdCid() {
        return qegdCid;
    }

    public void setQegdCid(String qegdCid) {
        this.qegdCid = qegdCid;
    }

    public String getQegdCname() {
        return qegdCname;
    }

    public void setQegdCname(String qegdCname) {
        this.qegdCname = qegdCname;
    }

    public String getQegdRemark() {
        return qegdRemark;
    }

    public void setQegdRemark(String qegdRemark) {
        this.qegdRemark = qegdRemark;
    }
}
