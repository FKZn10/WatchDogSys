package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同
 */
@Data
@TableName("Con_Contract")
public class ConContract {

    @TableId(type = IdType.INPUT)
    //合同ID
    private String conId;
    //合同名称
    private String conName;
    //合同文本（word版）
    private String conWord;
    //所属项目ID
    private String conProjectId;
    //所属代建合同ID
    private String conAgentConId;
    //所属标段ID
    private String conSectionId;
    //合同编号
    private String conCode;
    //合同类型编码
    private String conType;
    //【进度款】应用方式
    private String conProgressWay;
    //甲方单位ID
    private String conPartyAId;
    //其他甲方ID
    private String conPartyAOtherId;
    //乙方单位ID
    private String conPartyBId;
    //监理机构
    private String conSupervisionOrg;
    //总监理工程师
    private String conGeneralEngineer;
    //乙方经办人姓名
    private String conPartyBAgent;
    //乙方经办人电话
    private String conPartyBAgentTel;
    //乙方项目经理/总监
    private String conPartyBPm;
    //乙方项目经理身份证号
    private String conPartyBPmIdcard;
    //项目经理建造师执业印章号
    private String conPartyBPmConsYzh;
    //项目经理建造师注册号
    private String conPartyBPmConsNu;
    //乙方技术负责人
    private String conPartyBCto;
    //乙方安全负责人
    private String conPartyBSm;
    //是否联合体合同
    private String conConsortium;
    //其他乙方1
    private String conPartyB1;
    private String conPartyB1LabelName;
    //其他乙方2
    private String conPartyB2;
    private String conPartyB2LabelName;
    //其他乙方3
    private String conPartyB3;
    private String conPartyB3LabelName;
    //其他乙方4
    private String conPartyB4;
    private String conPartyB4LabelName;
    //其他乙方5
    private String conPartyB5;
    private String conPartyB5LabelName;
    //是否多方主体合同
    private String conMultiSubject;
    //丙方单位ID
    private String conPartyCId;
    //丁方单位ID
    private String conPartyDId;
    //【补充合同】是否补充合同
    private String conSup;
    //【补充合同】是否需更改金额
    private String conSupIsChangeMoney;
    //【补充合同】是否需更改工期
    private String conSupIsChangeDuration;
    //【补充合同】是否需要更改合同乙方单位（01是/02否）
    private String conSupIsChangePartyB;
    //【补充合同】的主合同ID
    private String conMasterConId;
    //【补充合同】签订原因
    private String conSupReason;
    //合同币种编码
    private String conCurrency;
    //合同状态编码
    private String conState;
    //合同执行情况
    private String conExecution;
    //签字盖章版合同扫描件
    private String conScan;
    //签字盖章版合同扫描件是否上传
    private Boolean conScanUp;
    //是否需要履约保函
    private String conPb;
    //履约保证方式
    private String conPbWay;
    //履约保函受益人（单位ID）
    private String conPbBeneficiaryId;
    //【预付款】扣回方式
    private String conAcBackWay;
    //【质量保证金】扣回方式
    private String conRmBackWay;
    //【安全文明措施】扣回方式
    private String conScBackWay;
    //是否需要预付款保函
    private String conApg;
    //预付款保函受益人（单位ID）
    private String conApgBeneficiaryId;
    //发票开具对象
    private String conInvoiceObject;
    //变更支付方式
    private String conChangePayWay;
    //工程概况
    private String conProjectOverview;
    //奖罚条款
    private String conAwardClause;
    //特殊约定
    private String conSpecialAgreement;
    //概算科目Id
    private String conBudgetId;
    //打包合同父合同ID
    private String conParentConId;
    //合同种类
    private String conCategory;
    //创建人Id
    private String conCreateUserId;
    //创建人姓名
    private String conCreateUserName;
    //【货物合同】运输方式
    private String conTransportMode;
    //计划开工日期
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date conPlanStartDate;
    //计划竣工日期
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date conPlanEndDate;
    //合同签订日期
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date conDate;
    //正式开工日期
    private Date conStartDate;
    //创建时间
    private Date conCreateTime;
    //审批时间
    private Date conApprovalTime;
    //【质量保证金】退回时间
    private Date conRmBackDate;
    //【货物合同】交货日期
    private Date conDeliveryDate;
    //【补充合同】更改的工期
    private Integer conSupChangeDuration;
    //【补充合同】更改后的乙方单位ID
    private String conSupChangePartyBId;
    //【补充合同】更改后的乙方单位名称
    private String conSupChangePartyBName;
    //总工期（日历天）
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer conTotalDuration;
    //【预付款】分几次扣完
    private Integer conAcBackTimes;
    //【预付款】起扣期
    private Integer conAcBackStartNu;
    //【质量保证金】起扣期
    private Integer conRmBackStartNu;
    //【安全文明措施】起扣期
    private Integer conScBackStartNu;
    //【安全文明措施】分几次扣完
    private Integer conScBackTimes;
    //审批状态
    private Integer conApprovalState;
    //【货物合同】供货期
    private Integer conDeliveryPeriod;
    //【货物合同】安装工期
    private Integer conInstallationPeriod;
    //暂定合同总价（含税）【P】
    private BigDecimal conMoneyTotal;
    //合同奖金金额
    private BigDecimal conMoneyAward;
    //【补充合同】更改的金额
    private BigDecimal conSupChangeMoney;
    //暂列金额
    private BigDecimal conMoneyTentative;
    //专业工程暂估价
    private BigDecimal conProvisionalPe;
    //材料和工程设备暂估价金额
    private BigDecimal conProvisionalM;
    //【预付款】支付额【P*Q】
    private BigDecimal conAcMoney;
    //【预付款】起扣点
    private BigDecimal conAcBackStart;
    //【预付款】扣结点
    private BigDecimal conAcBackEnd;
    //【质量保证金】金额【Z=P*K】
    private BigDecimal conRmMoney;
    //【质量保证金】起扣点
    private BigDecimal conRmBackStart;
    //【质量保证金】扣结点
    private BigDecimal conRmBackEnd;
    //【安全文明措施】金额
    private BigDecimal conScMoney;
    //【安全文明措施】起扣点
    private BigDecimal conScBackStart;
    //【安全文明措施】扣结点
    private BigDecimal conScBackEnd;
    //预付款保函金额
    private BigDecimal conApgMoney;
    //【货物合同】采购价款
    private BigDecimal conPurchasePrice;
    //【货物合同】安装价款
    private BigDecimal conInstallationCost;
    //【进度款】支付比例
    private Float conProgressRatio;
    //合同增值税税率
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float conVatRate;
    //履约保证/保函比例
    private Float conPbRate;
    //【预付款】支付比例【Q】
    private Float conAcPayRate;
    //【预付款】主要材料及构件所占比重【N】
    private Float conAcMaterialRate;
    //【预付款】扣回比例
    private Float conAcBackRate;
    //【质量保证金】比例【K】
    private Float conRmRate;
    //【质量保证金】扣回比例
    private Float conRmBackRate;
    //【安全文明措施】扣回比例
    private Float conScBackRate;
    //预付款保函比例
    private Float conApgRate;
    //变更支付比例
    private Float conChangePayRate;


    //招标方式
    private String conBiddingType;
    //法人代表
    private String conPartyBLegal;
    //授权委托书
    private String conPartyBPoxy;
    //授权委托人社保号
    private String conPartyBPoxyNum;
    //【收款账号】专用账户
    private String conCaName;
    //【收款账号】专用账号
    private String conCa;
    //【收款账号】专用开户行
    private String conCaBank;
    //【收款账号】开户行行名
    private String conCaBankName;
    //【收款账号】开户行行号
    private String conCaBankNum;
    //【工资专用账户】专用账户
    private String conWaName;
    //【工资专用账户】专用账号
    private String conWa;
    //【工资专用账户】专用开户行
    private String conWaBank;
    //【工资专用账户】开户行行名
    private String conWaBankName;
    //【工资专用账户】开户行行号
    private String conWaBankNum;

    //【代建合同】委托单位
    private String conAcClientId;
    //【代建合同】代建投标主体
    private String conAcBidSubjectId;
    //【代建合同】代建实施主体
    private String conAcImplementId;
    //【代建合同】授权委托书
    private String conAcProxy;
    //【技术负责人】姓名
    private String conTdName;
    //【技术负责人】社保号
    private String conTdNum;
    //【技术负责人】手机号
    private String conTdMobileNumber;

    //【补充合同更改工期类型】
    private Integer conSupChangeDurType;
    //【补充合同更改的金额类型】
    private Integer conSupChangeMonType;
    //是否是大合同
    private Integer conIsParent;
    // 【履约保函】履约保证/保函金额
    private BigDecimal ConPbMoney;
    private Integer conIsHistory;
    private Integer conYear;
    private String conPayClause;
    private String conAcceptanceClause;
    private String conDefaultClause;
    private String conBasis;
    private String conOtherNote;
    private String conBasisFile;
    private String conPbAdded;
    private String conAcAdded;
    private String conRmAdded;
    private String conScAdded;
    private String conRelieveConId;
    private String conRelieveReason;
    private String conPartyBPmTel;
    private String conRemark;

    private String conMoneyType;
    private String conMoneyFlow;
    private Float conRate;
    private Float conLowerRate;
    private Date conValidityStart;
    private Date conValidityEnd;
    private Integer conDefectsLiability;
    private Boolean conIsBidding;

    private String conSsName;
    private String conSs;
    private String conSsBank;
    private String conSsBankName;
    private String conSsBankNum;

    private String conPricingMethod;
    private String conOtherValuation;

    private String conIsTemplate;
    private String conTemplateId;
    private String conTemplateVersion;
    private String conProfessionalType;
    private Date conUsingDate;
    private Integer conUsingCopies;
    private String conStartsTo;
    private String conUsingType;
    private String conChangeClause;
    private String conSettlement;
    private BigDecimal conMainAmtTotal;
    private Date conBasisTime;
    private BigDecimal conTimeConsuming;
    private String teId;
    private BigDecimal conProgressDeadRatio;
    private BigDecimal conSettlementRatio;
    private String conDepartment;
    private String conEvaluateId;

    private Boolean conIsSupply;
    private String conPurchaseType;
    private String conSupplyTxt;


    private String conPartyA1Id;
    private String conPartyA2Id;
    private String conPartyA3Id;
    private String conPartyA4Id;
    private String conPartyA5Id;
    /**
     * 苏相对接
     * 合同所属单位
     */
    private String conAffiliationUnit;

    private String conSerialNumber;
    private Boolean conOpenBidding;
    private String conPayCategory;
    private String conOriginalCode;

    private String conTerm;
    private String conWarrantyPeriod;
    private String conEnterpriseType;
    private Boolean conUseTemp;
    private BigDecimal conBudgetRate;

    private BigDecimal conDeclareMoney;


    private String conBudgetFile;
    private String conBudgetTxt;
    private Boolean conInvolveFunds;

    //中标合同价---清标合同价
    private BigDecimal conBidAmt;
    private BigDecimal conBrightAmt;

    //预留金
    private BigDecimal conReserveMoney;

    /**
     * 云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     *
     */
    private String thirdRequestId;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    /**
     * 内容摘要
     */
    private String conContentSummary;

    /**
     * 呈批事项
     */
    private String conApprovalMatters;

    /**
     * 中标通知书与询价情况报告
     */
    private String conNoticeReport;

    /**
     * 【进度款】支付额【P*Q】
     */
    private BigDecimal conProgressMoney;

    /**
     * 合同金额不含税
     */
    private BigDecimal conMoneyTotalNoTax;

    /**
     * 印花税税率
     */
    private BigDecimal conStampDutyRate;

    /**
     * 印花税税额
     */
    private BigDecimal conStampDutyAmount;

    /**
     * 乙方单位负责人
     */
    private String conPartyBHead;
    /**
     * 其他乙方1项目负责人
     */
    private String conPartyB1Pm;
    /**
     * 其他乙方2项目负责人
     */
    private String conPartyB2Pm;
    /**
     * 其他乙方3项目负责人
     */
    private String conPartyB3Pm;
    /**
     * 其他乙方4项目负责人
     */
    private String conPartyB4Pm;
    /**
     * 其他乙方5项目负责人
     */
    private String conPartyB5Pm;
    /**
     * 丙方单位项目负责人
     */
    private String conPartyCPm;
    /**
     * 丁方单位项目负责人
     */
    private String conPartyDPm;

    private String conBidDocument;
    private String conMoneyRate;
    private BigDecimal conUnitPrice;
    private String conUnit;
    private String conDeclaration;
    private Date conFilingsDate;
    private BigDecimal conPerformancePeriod;
    private String conFollowAudit;
    private String conIsSupervisor;
    private BigDecimal conFeeRatio;
    private BigDecimal conTaxRatio;
    private String conPaymentMethod;
    private String conAcceptDepId;
    private String conIsDisciplineApproved;
    private Date conPbPayDate;
    private Date conPbDueDate;

    private BigDecimal conBidControlPrice;
    private BigDecimal conBidControlRate;
    private String conPartyB6;

    private String conPartyB6LabelName;
    private String conPartyB7;

    private String conPartyB7LabelName;
    private String conPartyB8;

    private String conPartyB8LabelName;
    private String conPartyB9;

    private String conPartyB9LabelName;
    private String conPartyB10;

    private String conPartyB10LabelName;
    /**
     * 义乌限定：施工合同关联监理合同ID
     */
    private String conJlId;

    private String conPartyOther1Id;
    private String conPartyOther1LabelName;
    private String conPartyOther2Id;
    private String conPartyOther2LabelName;
    private String conPartyOther3Id;
    private String conPartyOther3LabelName;
    private String conPartyOther4Id;
    private String conPartyOther4LabelName;
    private String conPartyOther5Id;
    private String conPartyOther5LabelName;
    private String conPartyOther6Id;
    private String conPartyOther6LabelName;
    private String conPartyOther7Id;
    private String conPartyOther7LabelName;
    private String conPartyOther8Id;
    private String conPartyOther8LabelName;
    private String conPartyOther9Id;
    private String conPartyOther9LabelName;
    private String conPartyOther10Id;
    private String conPartyOther10LabelName;

    // 类目
    private String category;
    //签订确认时间
    private Date conConfirmDate;

    private String isUploaded;

    private String conSubPId;

    private String conPartyEId;
    private String conPartyFId;

    private Date conAuditDate;

    private Date conEndDate;

    private Date tenanceDate;

    private String conPartyEPm;
    private String conPartyFPm;

    private String ccmId;

    private BigDecimal conPayRate;

    private String conPartyAOtherIdB;
    private String conPartyAOtherIdC;

    /**
     * 是否修改合同支付条款
     */
    private String conIsPayPlanModels;

    /**
     * 是否修改合同收款账号
     */
    private String conIsPayAccountModels;

    //甲方开户银行
    private String conPartyABank;

    //甲方开户银行名称
    private String conPartyABankName;

    //乙方统一社会信用代码
    private String conPartyBOic;

    //乙方收款账号
    private String conPartyBPqAccount;

    //乙方开户银行
    private String conPartyBBank;

    //乙方开户银行名称
    private String conPartyBBankName;

    //丙方收款账号
    private String conPartyCPqAccount;

    //甲方项目经理身份证号
    private String conPartyAPmIdcard;

    //丙方经办人电话
    private String conPartyCAgentTel;

    /**
     * 是否需要法制办审核。
     */
    private String htjkzffs;

    /**
     * 是否需要基建管理科审核。
     */
    private String conjgl;

    /**
     * 合同内容。
     */
    private String conContent;
}
