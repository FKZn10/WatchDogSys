package com.jpmsoft.projectConsumer.pojo.dto.contract;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ConPayApplyFlowVo implements Serializable {

    private static final long serialVersionUID = -1856309551211664234L;
    private String pName; // 项目名称
    private String conName; // 合同名称
    private String conCode; // 合同编号
    private String pId; // 项目ID
    private String conId; // 合同ID
    private String payLJAP; // 已支付金额
    private Date shippingTime; // 发货时间
    private String conType; // 合同类型
    private String conPartyAName; // 合同甲方名称
    private String cpafPayee; // 收款人
    private String payId; // 支付ID
    private String cpafCreateUname; // 创建人用户名
    private String payApproval; // 支付审批状态
    private String cpafApplication; // 申请描述
    private BigDecimal cpafApplyProportion; // 申请比例
    private BigDecimal cpafRemainingAmount; // 剩余金额
    private String cpafId; // 付款申请记录ID
    private String cpafCreateUid; // 创建人ID
    private Date cpafCreateTime; // 创建时间
    private Integer state; // 状态
    private Date stateTime; // 状态更新时间
    private String payInvoice; // 发票信息
    private String payInvoiceNo; // 发票号码

    private Integer page;

    private Integer size;


    private List<String> stateTimes;

    private String dateStr;

    private String dateEnd;


    public ConPayApplyFlowVo() {
    }

    public ConPayApplyFlowVo(String pName, String conName, String conCode, String pId, String conId, String payLJAP, Date shippingTime, String conType, String conPartyAName, String cpafPayee, String payId, String cpafCreateUname, String payApproval, String cpafApplication, BigDecimal cpafApplyProportion, BigDecimal cpafRemainingAmount, String cpafId, String cpafCreateUid, Date cpafCreateTime, Integer state, Date stateTime, String payInvoice, String payInvoiceNo, Integer page, Integer size, List<String> stateTimes, String dateStr, String dateEnd) {
        this.pName = pName;
        this.conName = conName;
        this.conCode = conCode;
        this.pId = pId;
        this.conId = conId;
        this.payLJAP = payLJAP;
        this.shippingTime = shippingTime;
        this.conType = conType;
        this.conPartyAName = conPartyAName;
        this.cpafPayee = cpafPayee;
        this.payId = payId;
        this.cpafCreateUname = cpafCreateUname;
        this.payApproval = payApproval;
        this.cpafApplication = cpafApplication;
        this.cpafApplyProportion = cpafApplyProportion;
        this.cpafRemainingAmount = cpafRemainingAmount;
        this.cpafId = cpafId;
        this.cpafCreateUid = cpafCreateUid;
        this.cpafCreateTime = cpafCreateTime;
        this.state = state;
        this.stateTime = stateTime;
        this.payInvoice = payInvoice;
        this.payInvoiceNo = payInvoiceNo;
        this.page = page;
        this.size = size;
        this.stateTimes = stateTimes;
        this.dateStr = dateStr;
        this.dateEnd = dateEnd;
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

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
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

    public String getPayLJAP() {
        return payLJAP;
    }

    public void setPayLJAP(String payLJAP) {
        this.payLJAP = payLJAP;
    }

    public Date getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getConPartyAName() {
        return conPartyAName;
    }

    public void setConPartyAName(String conPartyAName) {
        this.conPartyAName = conPartyAName;
    }

    public String getCpafPayee() {
        return cpafPayee;
    }

    public void setCpafPayee(String cpafPayee) {
        this.cpafPayee = cpafPayee;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getCpafCreateUname() {
        return cpafCreateUname;
    }

    public void setCpafCreateUname(String cpafCreateUname) {
        this.cpafCreateUname = cpafCreateUname;
    }

    public String getPayApproval() {
        return payApproval;
    }

    public void setPayApproval(String payApproval) {
        this.payApproval = payApproval;
    }

    public String getCpafApplication() {
        return cpafApplication;
    }

    public void setCpafApplication(String cpafApplication) {
        this.cpafApplication = cpafApplication;
    }

    public BigDecimal getCpafApplyProportion() {
        return cpafApplyProportion;
    }

    public void setCpafApplyProportion(BigDecimal cpafApplyProportion) {
        this.cpafApplyProportion = cpafApplyProportion;
    }

    public BigDecimal getCpafRemainingAmount() {
        return cpafRemainingAmount;
    }

    public void setCpafRemainingAmount(BigDecimal cpafRemainingAmount) {
        this.cpafRemainingAmount = cpafRemainingAmount;
    }

    public String getCpafId() {
        return cpafId;
    }

    public void setCpafId(String cpafId) {
        this.cpafId = cpafId;
    }

    public String getCpafCreateUid() {
        return cpafCreateUid;
    }

    public void setCpafCreateUid(String cpafCreateUid) {
        this.cpafCreateUid = cpafCreateUid;
    }

    public Date getCpafCreateTime() {
        return cpafCreateTime;
    }

    public void setCpafCreateTime(Date cpafCreateTime) {
        this.cpafCreateTime = cpafCreateTime;
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

    public String getPayInvoice() {
        return payInvoice;
    }

    public void setPayInvoice(String payInvoice) {
        this.payInvoice = payInvoice;
    }

    public String getPayInvoiceNo() {
        return payInvoiceNo;
    }

    public void setPayInvoiceNo(String payInvoiceNo) {
        this.payInvoiceNo = payInvoiceNo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<String> getStateTimes() {
        return stateTimes;
    }

    public void setStateTimes(List<String> stateTimes) {
        this.stateTimes = stateTimes;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
