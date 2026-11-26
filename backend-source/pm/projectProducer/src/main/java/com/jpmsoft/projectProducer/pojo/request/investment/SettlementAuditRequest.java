package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SettlementAuditRequest {
    @ClassInfo(title = "项目名称",type="string",value = "projectNameList",dbValue = "p_name")
    private List<String> projectNameList;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "state",dictionary = "JPMsoft_LCZT")
    private List<Integer> stateList;
    @ClassInfo(title = "审定合同数量",type="money",value = "auditContractNumList",dbValue = "audit_Contract_Num")
    private List<Integer> auditContractNumList;
    @ClassInfo(title = "累计审定金额",type="money",value = "auditContractMoneyList",dbValue = "audit_Contract_Money")
    private List<BigDecimal> auditContractMoneyList;
    @ClassInfo(title = "创建人",type="string",value = "createUnameList",dbValue = "create_Uname")
    private List<String> createUnameList;
}
