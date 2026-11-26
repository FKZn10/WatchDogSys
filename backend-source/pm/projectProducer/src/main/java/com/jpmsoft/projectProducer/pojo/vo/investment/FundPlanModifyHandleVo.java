package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;

import java.math.BigDecimal;
import java.util.List;

public class FundPlanModifyHandleVo {
     public  Integer fpmYear;
     public FundPlanModifyDto formList;
    public  BigDecimal  yearCumulativeAmount;
    public  BigDecimal  cpafApplyDetailsCumulative;
    public  BigDecimal  remainingAmount;
    public List<InvestmentPlanVo> investmentPlans;


    public Integer getFpmYear() {
        return fpmYear;
    }

    public void setFpmYear(Integer fpmYear) {
        this.fpmYear = fpmYear;
    }



    public BigDecimal getYearCumulativeAmount() {
        return yearCumulativeAmount;
    }

    public void setYearCumulativeAmount(BigDecimal yearCumulativeAmount) {
        this.yearCumulativeAmount = yearCumulativeAmount;
    }

    public BigDecimal getCpafApplyDetailsCumulative() {
        return cpafApplyDetailsCumulative;
    }

    public void setCpafApplyDetailsCumulative(BigDecimal cpafApplyDetailsCumulative) {
        this.cpafApplyDetailsCumulative = cpafApplyDetailsCumulative;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public List<InvestmentPlanVo> getInvestmentPlans() {
        return investmentPlans;
    }

    public void setInvestmentPlans(List<InvestmentPlanVo> investmentPlans) {
        this.investmentPlans = investmentPlans;
    }

    public FundPlanModifyDto getFormList() {
        return formList;
    }

    public void setFormList(FundPlanModifyDto formList) {
        this.formList = formList;
    }

}



