package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 16:46
 */
@Data
public class InvestmentEstimateBreakdownVo extends BaseFileDto {
    private String projectId;
    private String projectName;
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    //立项文件上传
    private String projectApplyFile;
    //投资估算
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    @JsonProperty("pRecordAmount")
    private BigDecimal pRecordAmount;
    @JsonProperty("pApprovalAmount")
    private BigDecimal pApprovalAmount;
    @JsonProperty("pInvTotal")
    private BigDecimal pInvTotal;
    @JsonProperty("pProjectFormType")
    private String pProjectFormType;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;

    private List<InvestmentEstimateBreakdownDetailsModel> details;
    // 概算总金额
    private BigDecimal totalInvestment;
    // 是否需要概算审核  01 是  02 否
    private String estReview;


    private String investMentFile;




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

    public String getpProjectFormType() {
        return pProjectFormType;
    }

    public void setpProjectFormType(String pProjectFormType) {
        this.pProjectFormType = pProjectFormType;
    }
}
