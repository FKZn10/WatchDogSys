package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlanAmount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class SecProcurementPlanDto extends BaseFileRequest {

    private String ppId;

    //项目id
    private String pId;

    //项目名称
    private String pName;

    //计划名称
    private String planName;

    //计划编码
    private String planCode;

    //计划类型
    private String planType;

    /**
     * 计划状态
     */
    private String planStatus;

    /**
     * 估算金额
     */
    private BigDecimal estimatedAmount;

    /**
     * 计划形式
     */
    private String planForm;

    /**
     * 招标方式
     */
    private String biddingMethod;

    /**
     * 计划开始时间
     */
    private Date planStartTime;

    /**
     * 工作内容
     */
    private String jobContent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String updateUid;

    private Date updateTime;

    private String teId;

    private List<SecProcurementPlanAmountDto> planAmountList;


    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public BigDecimal getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(BigDecimal estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getPlanForm() {
        return planForm;
    }

    public void setPlanForm(String planForm) {
        this.planForm = planForm;
    }

    public String getBiddingMethod() {
        return biddingMethod;
    }

    public void setBiddingMethod(String biddingMethod) {
        this.biddingMethod = biddingMethod;
    }

    public Date getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUpdateUid() {
        return updateUid;
    }

    public void setUpdateUid(String updateUid) {
        this.updateUid = updateUid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public List<SecProcurementPlanAmountDto> getPlanAmountList() {
        return planAmountList;
    }

    public void setPlanAmountList(List<SecProcurementPlanAmountDto> planAmountList) {
        this.planAmountList = planAmountList;
    }
}
