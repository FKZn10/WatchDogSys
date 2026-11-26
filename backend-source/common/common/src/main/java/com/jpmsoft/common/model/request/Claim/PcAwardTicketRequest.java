package com.jpmsoft.common.model.request.Claim;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖罚单
 */
public class PcAwardTicketRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String atId;
    private String pId;
    private String conId;
    private String atCId;
    private BigDecimal atMoney;
    private String atReason;
    private String atContent;
    private Integer atType;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String atCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String atCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date atCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer atState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date atStateTime;
    private String payId;
    private Date atLssueTime;
    private Date atDeductTime;
    private String atRectification;
    private String atRemark;
    private String atCode;


    private String pName;
    private String conName;
    //合同支付选择
    private boolean paySelect;
    private String atCName;
    /**
     * 流程编码
     */
    private String processNumber;

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public String getAtId() {
        return atId;
    }

    public void setAtId(String atId) {
        this.atId = atId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getAtCId() {
        return atCId;
    }

    public void setAtCId(String atCId) {
        this.atCId = atCId;
    }

    public BigDecimal getAtMoney() {
        return atMoney;
    }

    public void setAtMoney(BigDecimal atMoney) {
        this.atMoney = atMoney;
    }

    public String getAtReason() {
        return atReason;
    }

    public void setAtReason(String atReason) {
        this.atReason = atReason;
    }

    public String getAtContent() {
        return atContent;
    }

    public void setAtContent(String atContent) {
        this.atContent = atContent;
    }

    public Integer getAtType() {
        return atType;
    }

    public void setAtType(Integer atType) {
        this.atType = atType;
    }

    public String getAtCreateUid() {
        return atCreateUid;
    }

    public void setAtCreateUid(String atCreateUid) {
        this.atCreateUid = atCreateUid;
    }

    public String getAtCreateUname() {
        return atCreateUname;
    }

    public void setAtCreateUname(String atCreateUname) {
        this.atCreateUname = atCreateUname;
    }

    public Date getAtCreateTime() {
        return atCreateTime;
    }

    public void setAtCreateTime(Date atCreateTime) {
        this.atCreateTime = atCreateTime;
    }

    public Integer getAtState() {
        return atState;
    }

    public void setAtState(Integer atState) {
        this.atState = atState;
    }

    public Date getAtStateTime() {
        return atStateTime;
    }

    public void setAtStateTime(Date atStateTime) {
        this.atStateTime = atStateTime;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Date getAtLssueTime() {
        return atLssueTime;
    }

    public void setAtLssueTime(Date atLssueTime) {
        this.atLssueTime = atLssueTime;
    }

    public Date getAtDeductTime() {
        return atDeductTime;
    }

    public void setAtDeductTime(Date atDeductTime) {
        this.atDeductTime = atDeductTime;
    }

    public String getAtRectification() {
        return atRectification;
    }

    public void setAtRectification(String atRectification) {
        this.atRectification = atRectification;
    }

    public String getAtRemark() {
        return atRemark;
    }

    public void setAtRemark(String atRemark) {
        this.atRemark = atRemark;
    }

    public String getAtCode() {
        return atCode;
    }

    public void setAtCode(String atCode) {
        this.atCode = atCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public boolean isPaySelect() {
        return paySelect;
    }

    public void setPaySelect(boolean paySelect) {
        this.paySelect = paySelect;
    }

    public String getAtCName() {
        return atCName;
    }

    public void setAtCName(String atCName) {
        this.atCName = atCName;
    }
}
