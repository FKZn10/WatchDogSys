package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 指挥部架构
 */

public class PreCommandPostModel extends ProjectCommonRes {

    private String comId;
    private String pId;
    private String comOverview;
    private BigDecimal comInvestment;
    private String comCreateCid;
    private String comCreateUid;
    private String comCreateUname;
    private Date comCreateTime;
    private Integer comState;
    private Date comStateTime;

    private String pCode;
    private String comCreateCName;
    private String comCreateCNameHistory;
    private String comCreateCAbbName;
    private List<PreCommandPostDtlModel> preCommandPostDtls;
    //单位简称
    private List<String> comCreateTimes;

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getComOverview() {
        return comOverview;
    }

    public void setComOverview(String comOverview) {
        this.comOverview = comOverview;
    }

    public BigDecimal getComInvestment() {
        return comInvestment;
    }

    public void setComInvestment(BigDecimal comInvestment) {
        this.comInvestment = comInvestment;
    }

    public String getComCreateCid() {
        return comCreateCid;
    }

    public void setComCreateCid(String comCreateCid) {
        this.comCreateCid = comCreateCid;
    }

    public String getComCreateUid() {
        return comCreateUid;
    }

    public void setComCreateUid(String comCreateUid) {
        this.comCreateUid = comCreateUid;
    }

    public String getComCreateUname() {
        return comCreateUname;
    }

    public void setComCreateUname(String comCreateUname) {
        this.comCreateUname = comCreateUname;
    }

    public Date getComCreateTime() {
        return comCreateTime;
    }

    public void setComCreateTime(Date comCreateTime) {
        this.comCreateTime = comCreateTime;
    }

    public Integer getComState() {
        return comState;
    }

    public void setComState(Integer comState) {
        this.comState = comState;
    }

    public Date getComStateTime() {
        return comStateTime;
    }

    public void setComStateTime(Date comStateTime) {
        this.comStateTime = comStateTime;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getComCreateCName() {
        return comCreateCName;
    }

    public void setComCreateCName(String comCreateCName) {
        this.comCreateCName = comCreateCName;
    }

    public String getComCreateCNameHistory() {
        return comCreateCNameHistory;
    }

    public void setComCreateCNameHistory(String comCreateCNameHistory) {
        this.comCreateCNameHistory = comCreateCNameHistory;
    }

    public List<PreCommandPostDtlModel> getPreCommandPostDtls() {
        return preCommandPostDtls;
    }

    public void setPreCommandPostDtls(List<PreCommandPostDtlModel> preCommandPostDtls) {
        this.preCommandPostDtls = preCommandPostDtls;
    }

    public String getComCreateCAbbName() {
        return comCreateCAbbName;
    }

    public void setComCreateCAbbName(String comCreateCAbbName) {
        this.comCreateCAbbName = comCreateCAbbName;
    }

    public List<String> getComCreateTimes() {
        return comCreateTimes;
    }

    public void setComCreateTimes(List<String> comCreateTimes) {
        this.comCreateTimes = comCreateTimes;
    }
}
