package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.parentClass.SecGeneralityModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标模块
 * 以项目分组查询
 */
public class SecModularsGroupByProjectModel extends SecGeneralityModel {

    private String pId;
    private String pName;
    private String pCode;
    private Integer secTotal;
    private BigDecimal secMoneyTotal;
    private String pCreateUserId;
    private String pCreateUserName;
    private Date pCreateTime;
    private String pCompetentDepartmentName;
    private String secCreateUname;

    /**
     * 招标相关数据集合List
     */
    private Object secModulars;


    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public Integer getSecTotal() {
        return secTotal;
    }

    public void setSecTotal(Integer secTotal) {
        this.secTotal = secTotal;
    }

    public BigDecimal getSecMoneyTotal() {
        return secMoneyTotal;
    }

    public void setSecMoneyTotal(BigDecimal secMoneyTotal) {
        this.secMoneyTotal = secMoneyTotal;
    }

    public String getpCreateUserId() {
        return pCreateUserId;
    }

    public void setpCreateUserId(String pCreateUserId) {
        this.pCreateUserId = pCreateUserId;
    }

    public String getpCreateUserName() {
        return pCreateUserName;
    }

    public void setpCreateUserName(String pCreateUserName) {
        this.pCreateUserName = pCreateUserName;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public String getSecCreateUname() {
        return secCreateUname;
    }

    public void setSecCreateUname(String secCreateUname) {
        this.secCreateUname = secCreateUname;
    }

    public Object getSecModulars() {
        return secModulars;
    }

    public void setSecModulars(Object secModulars) {
        this.secModulars = secModulars;
    }
}
