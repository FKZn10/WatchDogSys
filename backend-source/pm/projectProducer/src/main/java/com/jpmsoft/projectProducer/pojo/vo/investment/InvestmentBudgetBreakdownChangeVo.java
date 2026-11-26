package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentBudgetBreakdownChangeDetailsModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**

 */
@Data
public class InvestmentBudgetBreakdownChangeVo extends BaseFileDto {
    //估算表id
    private String ieId;
    private String projectId;
    private String originApplyId;
    private String projectName;
    private String pCompetentDepartment;
    private String pCompetentDepartmentName;
    //立项文件上传
    private String projectApplyFile;
    //概算批复文件上传
    private String projectBudgetFile;
    //概算总投资（万元）
    private BigDecimal projectBudgetInvestmentAmount;

    private BigDecimal pApproveBudget;
    private BigDecimal pRecordAmount;
    private BigDecimal pApprovalAmount;
    private BigDecimal pInvTotal;
    private String pProjectFormType;


    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private List<InvestmentBudgetBreakdownChangeDetailsModel> details;

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

    public BigDecimal getpRecordAmount() {
        return pRecordAmount;
    }

    public void setpRecordAmount(BigDecimal pRecordAmount) {
        this.pRecordAmount = pRecordAmount;
    }

    public BigDecimal getpApprovalAmount() {
        return pApprovalAmount;
    }

    public void setpApprovalAmount(BigDecimal pApprovalAmount) {
        this.pApprovalAmount = pApprovalAmount;
    }

    public BigDecimal getpInvTotal() {
        return pInvTotal;
    }

    public void setpInvTotal(BigDecimal pInvTotal) {
        this.pInvTotal = pInvTotal;
    }
}
