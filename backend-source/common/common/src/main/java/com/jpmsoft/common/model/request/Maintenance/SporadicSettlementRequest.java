package com.jpmsoft.common.model.request.Maintenance;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SporadicSettlementRequest extends BaseFileRequest {

    private String id; // 主键ID
    private String createUid; // 创建人ID
    private String createUname; // 创建人姓名
    private Date createTime; // 创建时间
    private Integer state; // 状态
    private Date stateTime; // 状态更新时间
    private String seNumber; // 结算单号
    private String sePName; // 项目名称
    private String sePNumber; // 项目编号
    private String seUnitId; // 单位ID
    private BigDecimal seSubmitPrice; // 提交金额
    private String seSubmitName; // 提交人姓名
    private String seSubmitUserId; // 提交人ID
    private String seSubmitPhone; // 提交人电话
    private String seDepartment; // 部门ID
    private BigDecimal sePrice; // 结算金额
    private String seName; // 结算人姓名
    private String seUserId; // 结算人ID
    private String sePhone; // 结算人电话
    private BigDecimal seConAmount; // 合同金额
    private BigDecimal seSum; // 总结算金额
    private Date seDate; // 结算日期
    private String seSituation; // 结算情况说明
    private Date seAuditTime;//审核日期

    private String seUnitName;

    // 排除掉已经在 零星结算 流程中 或 已结束
    private Boolean excludePiecemealPayFlow;

    private Boolean onlyCompletedFlow;

    private List<String> sePrices;

    private List<String> seAuditTimes;

    private List<String> mrIds;

    private String mrCategory;

    public String getMrCategory() {
        return mrCategory;
    }

    public void setMrCategory(String mrCategory) {
        this.mrCategory = mrCategory;
    }

    public List<String> getMrIds() {
        return mrIds;
    }

    public void setMrIds(List<String> mrIds) {
        this.mrIds = mrIds;
    }

    public Boolean getOnlyCompletedFlow() {
        return onlyCompletedFlow;
    }

    public void setOnlyCompletedFlow(Boolean onlyCompletedFlow) {
        this.onlyCompletedFlow = onlyCompletedFlow;
    }

    public Boolean getExcludePiecemealPayFlow() {
        return excludePiecemealPayFlow;
    }

    public void setExcludePiecemealPayFlow(Boolean excludePiecemealPayFlow) {
        this.excludePiecemealPayFlow = excludePiecemealPayFlow;
    }

    public List<String> getSeAuditTimes() {
        return seAuditTimes;
    }

    public void setSeAuditTimes(List<String> seAuditTimes) {
        this.seAuditTimes = seAuditTimes;
    }

    public List<String> getSePrices() {
        return sePrices;
    }

    public void setSePrices(List<String> sePrices) {
        this.sePrices = sePrices;
    }

    public String getSeUnitName() {
        return seUnitName;
    }

    public void setSeUnitName(String seUnitName) {
        this.seUnitName = seUnitName;
    }

    public Date getSeAuditTime() {
        return seAuditTime;
    }

    public void setSeAuditTime(Date seAuditTime) {
        this.seAuditTime = seAuditTime;
    }

    public String getSePhone() {
        return sePhone;
    }

    public void setSePhone(String sePhone) {
        this.sePhone = sePhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getSeNumber() {
        return seNumber;
    }

    public void setSeNumber(String seNumber) {
        this.seNumber = seNumber;
    }

    public String getSePName() {
        return sePName;
    }

    public void setSePName(String sePName) {
        this.sePName = sePName;
    }

    public String getSePNumber() {
        return sePNumber;
    }

    public void setSePNumber(String sePNumber) {
        this.sePNumber = sePNumber;
    }

    public String getSeUnitId() {
        return seUnitId;
    }

    public void setSeUnitId(String seUnitId) {
        this.seUnitId = seUnitId;
    }

    public BigDecimal getSeSubmitPrice() {
        return seSubmitPrice;
    }

    public void setSeSubmitPrice(BigDecimal seSubmitPrice) {
        this.seSubmitPrice = seSubmitPrice;
    }

    public String getSeSubmitName() {
        return seSubmitName;
    }

    public void setSeSubmitName(String seSubmitName) {
        this.seSubmitName = seSubmitName;
    }

    public String getSeSubmitUserId() {
        return seSubmitUserId;
    }

    public void setSeSubmitUserId(String seSubmitUserId) {
        this.seSubmitUserId = seSubmitUserId;
    }

    public String getSeSubmitPhone() {
        return seSubmitPhone;
    }

    public void setSeSubmitPhone(String seSubmitPhone) {
        this.seSubmitPhone = seSubmitPhone;
    }

    public String getSeDepartment() {
        return seDepartment;
    }

    public void setSeDepartment(String seDepartment) {
        this.seDepartment = seDepartment;
    }

    public BigDecimal getSePrice() {
        return sePrice;
    }

    public void setSePrice(BigDecimal sePrice) {
        this.sePrice = sePrice;
    }

    public String getSeName() {
        return seName;
    }

    public void setSeName(String seName) {
        this.seName = seName;
    }

    public String getSeUserId() {
        return seUserId;
    }

    public void setSeUserId(String seUserId) {
        this.seUserId = seUserId;
    }

    public BigDecimal getSeConAmount() {
        return seConAmount;
    }

    public void setSeConAmount(BigDecimal seConAmount) {
        this.seConAmount = seConAmount;
    }

    public BigDecimal getSeSum() {
        return seSum;
    }

    public void setSeSum(BigDecimal seSum) {
        this.seSum = seSum;
    }

    public Date getSeDate() {
        return seDate;
    }

    public void setSeDate(Date seDate) {
        this.seDate = seDate;
    }

    public String getSeSituation() {
        return seSituation;
    }

    public void setSeSituation(String seSituation) {
        this.seSituation = seSituation;
    }

}
