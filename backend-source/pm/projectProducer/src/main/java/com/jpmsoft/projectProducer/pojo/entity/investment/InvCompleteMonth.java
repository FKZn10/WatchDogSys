package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 月完成情况
 */
@Data
@TableName("Inv_Complete_Month")
public class InvCompleteMonth {

    @TableId(type = IdType.INPUT)
    private String icmId;
    private String ipvId;
    /**
     * 项目id
     */
    private String pId;
    /**
     * 合同ID
     * */
    private String conId;
    /**
     * 填报单位
     */
    private String icmDId;

    /**
     * 月份
     */
    private String icmReportingMonth;

    /**
     * 月度完成投资
     */
    private BigDecimal icmMonthlyInvestment;

    /**
     * 月度完成比例
     */
    private Float icmMonthlyProportion;

    /**
     * 年度累计完成投资
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal icmAnnualInvestment;

    /**
     * 年度累计完成计划占比
     */
    private Float icmAnnualProportion;

    /**
     * 累计完成概算投资
     */
    private BigDecimal icmBudgetInvestment;

    /**
     * 概算投资完成比例
     */
    private Float icmBudgetProportion;

    /**
     * 工程形象进度
     */
    private String icmImgProgress;

    /**
     * 是否完成: 0-否；1-是
     */
    private String icmCompleted;

    /**
     * 未完成原因
     */
    private String icmIncompleteReason;

    /**
     * 建议推进的措施
     */
    private String icmMeasures;

    /**
     * 图片
     */
    private String icmImg;

    /**
     * 创建人Id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String icmCreateUid;

    /**
     * 创建人名称
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String icmCreateUname;

    /**
     * 创建时间
     */
    private Date icmCreateTime;

    /**
     * 审批状态
     */
    private Integer icmState;

    /**
     * 审批时间
     */
    private Date icmStateTime;

    /**
     * 年度
     */
    private String icmReportingYear;

    /**
     * 土地投资金额
     */
    private BigDecimal icmLanInvAmt;

    /**
     * 设备投资金额
     */
    private BigDecimal icmDeviceInvAmt;

    /**
     * 基建投资金额
     */
    private BigDecimal icmInfrastructureInvAmt;

    /**
     * 其他投资金额
     */
    private BigDecimal icmOtherInvAmt;

    /**
     * 土地支出金额
     */
    private BigDecimal icmLanExpAmt;

    /**
     * 设备支出金额
     */
    private BigDecimal icmDeviceExpAmt;

    /**
     * 基建支出金额
     */
    private BigDecimal icmInfrastructureExpAmt;

    /**
     * 其他支出金额
     */
    private BigDecimal icmOtherExpAmt;

    /**
     * 财务支付组成
     */
    private BigDecimal icmPayComposition;

    /**
     * 获奖情况
     */
    private String icmAwards;

    /**
     * 备注
     */
    private String icmRemark;

    /**
     * 简要分析报告
     */
    private String icmAnalysisReport;

    /**
     * 形象进度
     */
    private String icmImageProgress;

    /**
     * 项目产值
     */
    private BigDecimal icmOutputValue;

    /**
     * 总施工面积
     */
    private Float icmConstructionArea;

    /**
     * 新开工面积
     */
    private Float icmNewConsArea;

    /**
     * 人员数
     */
    private Integer icmPersonnelNum;

    /**
     * 竣工价值
     */
    private BigDecimal icmCompletionValue;

    /**
     * 已竣工面积
     */
    private Float icmCompletedArea;

    /**
     * 总施工面积单位
     */
    private String icmConstructionAreaUnit;

    /**
     * 新开工面积单位
     */
    private String icmNewConsAreaUnit;

    /**
     * 已竣工面积单位
     */
    private String icmCompletedAreaUnit;
    /**
     * 月实际完成投资
     */
    private BigDecimal icmActualCompletion;

    /**
     * 建筑工程
     */
    private BigDecimal icmBuildProject;

    /**
     * 安装工程
     */
    private BigDecimal icmInstallProject;

    /**
     * 设备工器具购置
     */
    private BigDecimal icmEquipmentPurchase;

    /**
     * 月其它费用
     */
    private BigDecimal icmOtherExpenses;

    /**
     * 实际上报
     */
    private BigDecimal icmActualReport;

    /**
     * 当月投资任务
     */
    private BigDecimal icmMonthInvestTask;

    /**
     * 滞后投资
     */
    private BigDecimal icmLagInvest;

    /**
     * 自开始累计完成投资
     */
    private BigDecimal icmCumulativeInvest;

    /**
     * 剩余未上报投资
     */
    private BigDecimal icmNotReported;

    /**
     * 租户id
     */
    private String teId;

    /**
     * 单据流转状态：01-第一节点未提交(第一节点可编辑)；02-被驳回到第一节点(第一节点可编辑)；03-提交到第二节点(第二节点可编辑)；04被驳回到第二节点(第二节点可编辑)；05-提交到第三节点(第三节点可编辑)；06完结(全部不可编辑)
     */
    private String transferStatus;

    /**
     * 当月是否开工: 0-否；1-是
     */
    private String currentMonthStart;

    /**
     * 累计是否开工: 0-否；1-是
     */
    private String cumulativeStart;

    /**
     * 计划开工年月
     */
    private Date planStartTime;

    /**
     * 开工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String planCompleteResult;

    /**
     * 当月是否完工: 0-否；1-是
     */
    private String currentMonthComplete;

    /**
     * 累计是否开完工: 0-否；1-是
     */
    private String cumulativeComplete;

    /**
     * 计划完工年月
     */
    private Date planEndTime;

    /**
     * 完工计划完成情况：01-滞后未完成；02-滞后完成；03-正常完成；04-提前完成
     */
    private String completeResult;

    /**
     * X月年度累计计划投资(单位元)
     */
    private BigDecimal monthCumulativeInv;

    /**
     * 投资计划完成率
     */
    private Float monthCumulativeInvProportion;

    /**
     * 实际开工年月
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date actualStartTime;

    /**
     * 实际完工年月
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date actualEndTime;


    private String icmProjectStage;

    private String pCompetentDepartments;

    private Date currentTimeStart;

    private Date currentTimeEnd;
}
