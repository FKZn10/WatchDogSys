package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("Group_Fund_Plan")
public class GroupFundPlan extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8819836077674038158L;
    private String id;
    private String projectId;
    private Integer gfpMonth;
    private Integer gfpYear;
    private String gfpCode;
    private BigDecimal projectTotal;// 工程总投资
    private String projectApproveBudgetCode;// 批复编号立项文号
    private BigDecimal projectInvTotal;// 估算批复金额 工程总投资
    private String bdApprovalNum;// 概算文号
    private BigDecimal projectApproveBudget;// 概算批复金额
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
    // Gfp_Fund_Apply_Amount_Gc_Audit 资金申请工程部审核数
    private BigDecimal gfpFundApplyAmountGcAudit;
    // Gfp_Fund_Apply_Amount_Cw_Audit 资金申请财务部审核数
    private BigDecimal gfpFundApplyAmountCwAudit;
    // Gfp_Remark
    private String gfpRemark;

    @TableField("P_Total")
    private BigDecimal pTotal;

    @TableField("P_Approve_Budget_Code")
    private String pApproveBudgetCode;

    @TableField("P_Inv_Total")
    private BigDecimal pInvTotal;

    @TableField("P_Approve_Budget")
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

    @TableLogic
    private Integer delFlag;
}
