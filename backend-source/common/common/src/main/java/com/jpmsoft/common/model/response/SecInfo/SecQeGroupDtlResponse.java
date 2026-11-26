package com.jpmsoft.common.model.response.SecInfo;


import java.util.Date;

/**
 * 资格审查小组清单
 */

public class SecQeGroupDtlResponse {
    private String qegdId;
    private String qegId;
    private String qegdUid;
    private String qegdCompanyName;
    private String qegdUname;
    private Date qegdCreateTime;
    private Integer qegdSort;
    private String qegdUserName;
    private String qegdCid;
    private String qegdRemark;

    private String qegdCname;
    private String qegdMobilePhone;
    //单位简称
    private String qegdCompAbbName;


    public String getQegdId() {
        return qegdId;
    }

    public void setQegdId(String qegdId) {
        this.qegdId = qegdId;
    }

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

    public Date getQegdCreateTime() {
        return qegdCreateTime;
    }

    public void setQegdCreateTime(Date qegdCreateTime) {
        this.qegdCreateTime = qegdCreateTime;
    }

    public Integer getQegdSort() {
        return qegdSort;
    }

    public void setQegdSort(Integer qegdSort) {
        this.qegdSort = qegdSort;
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

    public String getQegdRemark() {
        return qegdRemark;
    }

    public void setQegdRemark(String qegdRemark) {
        this.qegdRemark = qegdRemark;
    }

    public String getQegdCname() {
        return qegdCname;
    }

    public void setQegdCname(String qegdCname) {
        this.qegdCname = qegdCname;
    }

    public String getQegdCompanyName() {
        return qegdCompanyName;
    }

    public void setQegdCompanyName(String qegdCompanyName) {
        this.qegdCompanyName = qegdCompanyName;
    }

    public String getQegdUname() {
        return qegdUname;
    }

    public void setQegdUname(String qegdUname) {
        this.qegdUname = qegdUname;
    }

    public String getQegdMobilePhone() {
        return qegdMobilePhone;
    }

    public void setQegdMobilePhone(String qegdMobilePhone) {
        this.qegdMobilePhone = qegdMobilePhone;
    }

    public String getQegdCompAbbName() {
        return qegdCompAbbName;
    }

    public void setQegdCompAbbName(String qegdCompAbbName) {
        this.qegdCompAbbName = qegdCompAbbName;
    }
}
