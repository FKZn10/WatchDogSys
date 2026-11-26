package com.jpmsoft.projectProducer.pojo.vo.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GroupFundPlanSummaryDetailVo {
    private String id;
    private String gfpsId;
    private String gfpsSummaryId;
    private String gfpId;
    private Integer gfpMonth;
    private Integer gfpYear;
    private String projectId;
    private String pname;
    private String projectName;
    private Date createTime;
    private BigDecimal pInvTotal;// 估算批复金额 工程总投资
    private BigDecimal pApproveBudget;// 概算批复金额
    private BigDecimal projectTotal;// 工程总投资
    private String gfpsProjectCompetentDepartmentId;
    private String gfpsProjectCompetentDepartmentName;
    private String gfpsProjectDepartmentId;
    private String gfpsProjectDepartmentName;
    private String gfpProjectProgress;
    // Gfp_Arrived_Funds 项目已到位资金
    private BigDecimal gfpArrivedFunds;
    // Gfp_Arrange_Funds 集团已安排资金
    private BigDecimal gfpArrangeFunds;
    // Gfp_Balance_Funds 集团账面余额
    private BigDecimal gfpBalanceFunds;
    // Gfp_Member_Balance_Funds 成员企业账面余额
    private BigDecimal gfpMemberBalanceFunds;
    // Gfp_Next_Month_Funds 次月预计来款金额
    private BigDecimal gfpNextMonthFunds;
    // Gfp_Fund_Demand_Amount 本期资金需求数
    private BigDecimal gfpFundDemandAmount;
    // Gfp_Fund_Apply_Amount 本期资金申请数
    private BigDecimal gfpFundApplyAmount;
    // Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGcAudit;
    // Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCwAudit;
    private Integer status;
    private Integer delFlag;
    private Integer isNew;

    private BigDecimal gfpNextMonthFundsTotal;
    private String remarkContext;
    private String gfpsdId;

    private List<GroupFundPlanSummaryDetailVo> children;

    /**
     * 历史版本id
     */
    private String groupId;

    /**
     * 是否历史版本
     */
    private String groupMainType;

    /**
     * 版本数量
     */
    private Integer groupCount;
}
