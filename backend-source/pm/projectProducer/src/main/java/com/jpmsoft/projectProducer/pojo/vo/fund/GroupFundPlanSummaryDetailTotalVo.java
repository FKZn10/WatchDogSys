package com.jpmsoft.projectProducer.pojo.vo.fund;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroupFundPlanSummaryDetailTotalVo {
    private String id;
    private String gfpsId;
    private String gfpsProjectCompetentDepartmentId;
    private String gfpsProjectCompetentDepartmentName;
    // 工程总投资
    private BigDecimal projectTotal;
    // Gfp_Arrived_Funds 项目已到位资金
    private BigDecimal gfpArrivedFunds;
    // Gfp_Balance_Funds 集团账面余额
    private BigDecimal gfpBalanceFunds;
    // Gfp_Member_Balance_Funds 成员企业账面余额
    private BigDecimal gfpMemberBalanceFunds;
    // Gfp_Fund_Demand_Amount 本期资金需求数
    private BigDecimal gfpFundDemandAmount;
    // Gfp_Fund_Apply_Amount 本期资金申请数
    private BigDecimal gfpFundApplyAmount;
    // Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGcAudit;
    // Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCwAudit;
}
