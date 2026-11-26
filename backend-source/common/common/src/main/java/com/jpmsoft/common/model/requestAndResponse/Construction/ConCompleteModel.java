package com.jpmsoft.common.model.requestAndResponse.Construction;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 完工申请
 */
public class ConCompleteModel extends ProjectCommonRes {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String coId;
    private String pId;
    private String coTitle;
    private Date coStart;
    private Date coEnd;
    private String coRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String coCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String coCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date coCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer coState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date coStateTime;


    public String getCoId() {
        return coId;
    }

    public void setCoId(String coId) {
        this.coId = coId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getCoTitle() {
        return coTitle;
    }

    public void setCoTitle(String coTitle) {
        this.coTitle = coTitle;
    }

    public Date getCoStart() {
        return coStart;
    }

    public void setCoStart(Date coStart) {
        this.coStart = coStart;
    }

    public Date getCoEnd() {
        return coEnd;
    }

    public void setCoEnd(Date coEnd) {
        this.coEnd = coEnd;
    }

    public String getCoRemark() {
        return coRemark;
    }

    public void setCoRemark(String coRemark) {
        this.coRemark = coRemark;
    }

    public String getCoCreateUid() {
        return coCreateUid;
    }

    public void setCoCreateUid(String coCreateUid) {
        this.coCreateUid = coCreateUid;
    }

    public String getCoCreateUname() {
        return coCreateUname;
    }

    public void setCoCreateUname(String coCreateUname) {
        this.coCreateUname = coCreateUname;
    }

    public Date getCoCreateTime() {
        return coCreateTime;
    }

    public void setCoCreateTime(Date coCreateTime) {
        this.coCreateTime = coCreateTime;
    }

    public Integer getCoState() {
        return coState;
    }

    public void setCoState(Integer coState) {
        this.coState = coState;
    }

    public Date getCoStateTime() {
        return coStateTime;
    }

    public void setCoStateTime(Date coStateTime) {
        this.coStateTime = coStateTime;
    }

}
