package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投资计划表
 */
@Data
public class InvPlanTableModel extends BaseModeRequest {
    //序号
    private String num;
    private String conId;
    private String conName;
    //费用项目
    private String costName;
    //概算ID
    private String budgetId;
    //父节点ID
    private String budgetParentId;
    //概算金额
    private BigDecimal budgetAmount;
    //招标控制价
    private BigDecimal tenderAmount;
    //合同价款
    private BigDecimal conAmount;
    //预留金
    private BigDecimal reserveAmount;
    //已完成产值
    private BigDecimal completedValue;
    //变更签证累计金额
    private BigDecimal changeVisaAmount;
    //预计调整金额
    private BigDecimal planAdjustAmount;
    //合同结算金额
    private BigDecimal conSettlementAmount;
    //超合同金额
    private BigDecimal overConAmount;
    //超合同比例
    private BigDecimal overConRate;
    //超概算金额
    private BigDecimal overInvAmount;
    //超概算比例
    private BigDecimal overInvRate;
    //概算动态管控金额
    private BigDecimal dynamicControlAmount;
    //备注
    private BigDecimal remark;

    private String leafPath;

    //是否是多科目合计
    private String ifManyBudget;

    //科目合计信息
    private List<InvPlanTableModel> ManyBudget;


    //节点说明
    private String NodeDes;

    //排序
    private Integer order;

    //科目合计信息
    private List<InvPlanTableModel> childData;

}
