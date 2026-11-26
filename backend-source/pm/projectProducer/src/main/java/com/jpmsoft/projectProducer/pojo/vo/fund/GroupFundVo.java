package com.jpmsoft.projectProducer.pojo.vo.fund;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupFundVo {
    // 工程总投资
    private BigDecimal pTotal;
    private String pApproveBudgetCode;// 批复编号立项文号
    private BigDecimal pInvTotal;// 估算批复金额 工程总投资
    private String bdApprovalNum;// 概算文号
    private BigDecimal pApproveBudget;// 概算批复金额
    // Gfp_Project_Progress 项目进展
    private String gfpProjectProgress;
    // Gfp_Arrived_Funds 项目已到位资金
    private BigDecimal gfpArrivedFunds;
    // Gfp_Arrange_Funds 集团已安排资金
    private BigDecimal gfpArrangeFunds;
    // Gfp_Balance_Funds 集团账面余额
    private BigDecimal gfpBalanceFunds;
    // Gfp_Member_Balance_Funds 成员企业账面余额
    private BigDecimal gfpMemberBalanceFunds;
}
