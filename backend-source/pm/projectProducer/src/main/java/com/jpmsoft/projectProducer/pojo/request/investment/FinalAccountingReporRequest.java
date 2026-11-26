package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FinalAccountingReporRequest {
    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = "p_name")
    private List<String> pNameList;

    @ClassInfo(title = "项目管理单位",type="string",value = "cNameList",dbValue = "c_name")
    private List<String> cNameList;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "state",dictionary = "JPMsoft_LCZT")
    private List<Integer> stateList;
    @ClassInfo(title = "概算批复金额",type="money",value = "approvedBudgetAmountList",dbValue = "approved_Budget_Amount")
    private List<BigDecimal> approvedBudgetAmountList;
    @ClassInfo(title = "送审金额",type="money",value = "auditContractMoneyList",dbValue = "amount_Submitted")
    private List<BigDecimal> auditContractMoneyList;
    @ClassInfo(title = "创建人",type="string",value = "createUnameList",dbValue = "create_Uname")
    private List<String> createUnameList;
}
