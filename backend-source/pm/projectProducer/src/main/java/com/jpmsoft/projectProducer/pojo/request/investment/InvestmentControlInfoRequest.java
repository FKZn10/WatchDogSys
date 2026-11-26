package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvestmentControlInfoRequest {
    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = " mn.p_name ")
    private List<String> pNameList;

    @ClassInfo(title = "项目管理单位",type="string",value = "cNameList",dbValue = " sc.c_name ")
    private List<String> cNameList;

    @ClassInfo(title = "估算总投资",type="money",value = "pApproveBudgetList",dbValue = " mn.P_Approve_budget ")
    private List<BigDecimal> pApproveBudgetList;

    @ClassInfo(title = "概算总投资",type="money",value = "pBudgetInvestmentAmountList",dbValue = " ibb.project_Budget_Investment_Amount ")
    private List<BigDecimal> pBudgetInvestmentAmountList;

    @ClassInfo(title = "当前总投资",type="money",value = "allInvestmentList",dbValue = " COALESCE(fp.allInvestment, 0) ")
    private List<BigDecimal> allInvestmentList;

    @ClassInfo(title = "实际支付总额",type="money",value = "actualPaymentAmtList",dbValue = " COALESCE(cp.payApplySum, 0) + COALESCE(te.travelExpensesSum, 0) ")
    private List<BigDecimal> actualPaymentAmtList;

}
