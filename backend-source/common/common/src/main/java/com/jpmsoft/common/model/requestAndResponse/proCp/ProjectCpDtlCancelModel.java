package com.jpmsoft.common.model.requestAndResponse.proCp;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 取消项目建设程序详情
 */
public class ProjectCpDtlCancelModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String cdcId;
    private String pId;
    private String cpdId;
    private String cdcRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String cdcCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String cdcCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date cdcCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;


    private String cpdName;
    private String cpdCode;


    public String getCdcId() {
        return cdcId;
    }

    public void setCdcId(String cdcId) {
        this.cdcId = cdcId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getCdcRemark() {
        return cdcRemark;
    }

    public void setCdcRemark(String cdcRemark) {
        this.cdcRemark = cdcRemark;
    }

    public String getCdcCreateUid() {
        return cdcCreateUid;
    }

    public void setCdcCreateUid(String cdcCreateUid) {
        this.cdcCreateUid = cdcCreateUid;
    }

    public String getCdcCreateUname() {
        return cdcCreateUname;
    }

    public void setCdcCreateUname(String cdcCreateUname) {
        this.cdcCreateUname = cdcCreateUname;
    }

    public Date getCdcCreateTime() {
        return cdcCreateTime;
    }

    public void setCdcCreateTime(Date cdcCreateTime) {
        this.cdcCreateTime = cdcCreateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getCpdName() {
        return cpdName;
    }

    public void setCpdName(String cpdName) {
        this.cpdName = cpdName;
    }

    public String getCpdCode() {
        return cpdCode;
    }

    public void setCpdCode(String cpdCode) {
        this.cpdCode = cpdCode;
    }
}
