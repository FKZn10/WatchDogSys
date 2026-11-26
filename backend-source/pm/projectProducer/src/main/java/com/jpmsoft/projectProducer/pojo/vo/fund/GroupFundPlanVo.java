package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class GroupFundPlanVo {
    private String id;
    private String projectId;
    private String projectName;
    private String projectCompetentDepartmentId;
    private String projectCompetentDepartmentName;
    private BigDecimal projectTotal;// 工程总投资
    private String projectApproveBudgetCode;// 批复编号立项文号
    private BigDecimal projectInvTotal;// 估算批复金额 工程总投资
    private String bdApprovalNum;// 概算文号
    private BigDecimal projectApproveBudget;// 概算批复金额
    private Integer gfpMonth;
    private Integer gfpYear;
    private String gfpCode;
    // Gfp_Project_Progress 项目进展
    private String gfpProjectProgress;
    // Gfp_Arrived_Funds 项目已到位资金
    private BigDecimal gfpArrivedFunds;
    // Gfp_Arrange_Funds 集团已安排资金
    private BigDecimal gfpArrangeFunds;
    // Gfp_Balance_Funds 集团账面余额
    private BigDecimal gfpBalanceFunds;
    // Gfp_Fund_Demand_Amount 本期资金需求数
    private BigDecimal gfpFundDemandAmount;
    // Gfp_Member_Balance_Funds 成员企业账面余额
    private BigDecimal gfpMemberBalanceFunds;
    // Gfp_Next_Month_Funds 次月预计来款金额
    private BigDecimal gfpNextMonthFunds;
    // 次月预计总余额
    private BigDecimal gfpNextMonthFundsTotal;
    // Gfp_Fund_Apply_Amount 本期资金申请数
    private BigDecimal gfpFundApplyAmount;
    // Gfp_Fund_Apply_Amount_GC_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGCAudit;
    // Gfp_Fund_Apply_Amount_CW_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCWAudit;
    // Gfp_Remark
    private String gfpRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private List<GroupFundPlanDetailVo> detailDtos;
    private Integer isNew;
    @JsonProperty("pTotal")
    private BigDecimal pTotal;

    @JsonProperty("pApproveBudgetCode")
    private String pApproveBudgetCode;

    @JsonProperty("pInvTotal")
    private BigDecimal pInvTotal;

    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;

    private BigDecimal fpBookBalance;

    /**
     * 类型
     */
    private String groupType;

    /**
     * 用印id
     */
    private String usaId;

    /**
     * 资金申请对象
     */
    private String gfpApplicationCid;
    private String gfpApplicationCidName;

    /**
     * 申请依据
     */
    private String applicationBasis;

    /**
     * 资金申请函
     */
    private String fundApplicationLetter;

    /**
     * 是否集团用印
     */
    private String groupPrinting;

    /**
     * 历史版本id
     */
    private String groupId;

    /**
     * 是否历史版本
     */
    private String groupMainType;

    /**
     * 是否可修改
     */
    private Boolean canChange;

    /**
     * 事业部分支机构
     */
    private String projectDepartmentId;
    private String projectDepartmentName;
}
