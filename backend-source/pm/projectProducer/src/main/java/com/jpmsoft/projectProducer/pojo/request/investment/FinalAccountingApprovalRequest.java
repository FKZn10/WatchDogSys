package com.jpmsoft.projectProducer.pojo.request.investment;

import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FinalAccountingApprovalRequest {
    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = "p_name")
    private List<String> pNameList;

    @ClassInfo(title = "决算批复日期",type="date",value = "faaApprovalTime",dbValue = "faa_Approval_Time")
    private List<Date> faaApprovalTime;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "state",dictionary = "JPMsoft_LCZT")
    private List<Integer> stateList;
    @ClassInfo(title = "决算投资",type="money",value = "faaApprovalInvestmentList",dbValue = "faa_Approval_Investment")
    private List<BigDecimal> faaApprovalInvestmentList;
    @ClassInfo(title = "创建人",type="string",value = "createUnameList",dbValue = "create_Uname")
    private List<String> createUnameList;
    @ClassInfo(title = "创建时间",type="date",value = "createTime",dbValue = "create_Time")
    private List<Date> createTime;
}
