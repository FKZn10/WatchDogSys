package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.request.Contract.ConSecondMemberRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@Data
public class ConContractModel extends BaseFileRequest {

    private String conId;
    private String conName;
    private String conWord;
    private String conWordIds;
    private String conProjectId;
    private String conAgentConId;
    private String conSectionId;
    private String conCode;
    private String conType;
    private String conProgressWay;
    private String conPartyAId;
    private String conPartyAOtherId;
    private String conPartyBId;
    private String conSupervisionOrg;
    private String conGeneralEngineer;
    private String conPartyBAgent;
    private String conPartyBAgentTel;
    private String conPartyBPm;
    private String conPartyBPmIdcard;
    private String conPartyBPmConsYzh;
    private String conPartyBPmConsNu;
    private String conPartyBCto;
    private String conPartyBSm;
    private String conConsortium;
    private String conPartyB1;
    private String conPartyB1LabelName;
    private String conPartyB2;
    private String conPartyB2LabelName;
    private String conPartyB3;
    private String conPartyB3LabelName;
    private String conPartyB4;
    private String conPartyB4LabelName;
    private String conPartyB5;
    private String conPartyB5LabelName;
    private String conMultiSubject;
    private String conPartyCId;
    private String conPartyDId;
    private String conSup;
    private String conSupIsChangeMoney;
    private String conSupIsChangeDuration;
    private String conMasterConId;
    private String conSupReason;
    private String conCurrency;
    private String conState;
    private String conExecution;
    private String conScan;
    private String conScanIds;
    private Boolean conScanUp;
    private String conPb;
    private String conPbWay;
    private String conPbBeneficiaryId;
    private String conAcBackWay;
    private String conRmBackWay;
    private String conScBackWay;
    private String conApg;
    private String conApgBeneficiaryId;
    private String conInvoiceObject;
    private String conChangePayWay;
    private String conProjectOverview;
    private String conAwardClause;
    private String conSpecialAgreement;
    private String conBudgetId;
    private String conParentConId;
    private String conCategory;
    private String conCreateUserId;
    private String conCreateUserName;
    private Date conPlanStartDate;
    private Date conPlanEndDate;
    private Date conDate;
    private Date conStartDate;
    private Date conCreateTime;
    private Date conApprovalTime;
    private Date conRmBackDate;
    private Integer conSupChangeDuration;
    private Integer conTotalDuration;
    private Integer conAcBackTimes;
    private Integer conAcBackStartNu;
    private Integer conRmBackStartNu;
    private Integer conScBackStartNu;
    private Integer conScBackTimes;
    private Integer conApprovalState;
    private Integer conIsHistory;
    private BigDecimal conAcBackStart;
    private BigDecimal conAcBackEnd;
    private BigDecimal conRmBackStart;
    private BigDecimal conRmBackEnd;
    private BigDecimal conScMoney;
    private BigDecimal conScBackStart;
    private BigDecimal conScBackEnd;
    private BigDecimal conApgMoney;
    private BigDecimal conRmMoney;
    private BigDecimal conAcMoney;
    private BigDecimal conMoneyTotal;
    private BigDecimal conMoneyAward;
    private BigDecimal conSupChangeMoney;
    private BigDecimal conMoneyTentative;
    private BigDecimal conProvisionalPe;
    private BigDecimal conProvisionalM;
    // 【履约保函】履约保证/保函金额
    private BigDecimal ConPbMoney;
    private Float conProgressRatio;
    private Float conVatRate;
    private Float conPbRate;
    private Float conAcPayRate;
    private Float conAcMaterialRate;
    private Float conAcBackRate;
    private Float conRmRate;
    private Float conRmBackRate;
    private Float conScBackRate;
    private Float conApgRate;
    private Float conChangePayRate;
    //【货物合同】运输方式
    private String conTransportMode;
    //【货物合同】交货日期
    private Date conDeliveryDate;
    //【货物合同】供货期
    private Integer conDeliveryPeriod;
    //【货物合同】安装工期
    private Integer conInstallationPeriod;
    //【货物合同】采购价款
    private BigDecimal conPurchasePrice;
    //【货物合同】安装价款
    private BigDecimal conInstallationCost;
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
    //文件IDs
    private String conAcProxyIds;
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
    private String conChangeClause;
    private String conSettlement;
    private BigDecimal conMainAmtTotal;
    private Date conBasisTime;
    private BigDecimal conTimeConsuming;
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
    /*******/
    private BigDecimal conEvaluateAmt;
    private String conEvaluateName;
    private String conEvaluateCode;
    private String conPartyB1Name;
    //其他乙方2
    private String conPartyB2Name;
    //其他乙方3
    private String conPartyB3Name;
    //其他乙方4
    private String conPartyB4Name;
    private String conPartyB5Name;
    private String conPartyA1Name;
    private String conPartyA2Name;
    private String conPartyA3Name;
    private String conPartyA4Name;
    private String conPartyA5Name;
    private String conPartyCName;
    private String conPartyDName;
    private String secName;
    private String conMasterConName;
    private String conProjectName;
    private String conPartyBName;
    private String conPartyAName;
    private String conPartyAOtherName;
    private String conRelieveConName;
    private String conRelieveConCode;
    @JsonProperty("bUnitId")
    private String bUnitId;
    private String conBudgetFileIds;
    private String conStartsToIds;
    private String conBasisFileIds;
    private List<String> conMoneyTotals;
    private List<String> conDates;
    private List<String> conTotalDurations;
    private List<String> conPlanStartDates;
    private List<String> conPlanEndDates;
    private List<String> conCreateTimes;

    private List<ConContractPayPlanModel> payPlanModels;
    private List<ConContractMoneyDtlModel> moneyDtlModels;
    private List<ConPayeeDtlModel> payeeDtlModels;
    //中标合同价---清标合同价
    private BigDecimal conBidAmt;
    private BigDecimal conBrightAmt;
    private List<ConEstimateModel> conEstimate;

    //预留金
    private BigDecimal conReserveMoney;
    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
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
     * 中标通知书与询价情况报告
     */
    private String conNoticeReportIds;

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
    private String conUnitC;
    private String conUnitC1;
    private String conUnitD;
    private String conUnitD1;
    private Date conPbPayDate;
    private Date conPbDueDate;

    private BigDecimal conBidControlPrice;
    private BigDecimal conBidControlRate;
    private String conPartyB6;
    private String conPartyB6Name;
    private String conPartyB6LabelName;
    private String conPartyB7;
    private String conPartyB7Name;
    private String conPartyB7LabelName;
    private String conPartyB8;
    private String conPartyB8Name;
    private String conPartyB8LabelName;
    private String conPartyB9;
    private String conPartyB9Name;
    private String conPartyB9LabelName;
    private String conPartyB10;
    private String conPartyB10Name;
    private String conPartyB10LabelName;
    private List<ConSecondMemberRequest> conSecondMember;
    private String conPartyOther1Id;
    private String conPartyOther1Name;
    private String conPartyOther1LabelName;
    private String conPartyOther2Id;
    private String conPartyOther2Name;
    private String conPartyOther2LabelName;
    private String conPartyOther3Id;
    private String conPartyOther3Name;
    private String conPartyOther3LabelName;
    private String conPartyOther4Id;
    private String conPartyOther4Name;
    private String conPartyOther4LabelName;
    private String conPartyOther5Id;
    private String conPartyOther5Name;
    private String conPartyOther5LabelName;
    private String conPartyOther6Id;
    private String conPartyOther6Name;
    private String conPartyOther6LabelName;
    private String conPartyOther7Id;
    private String conPartyOther7Name;
    private String conPartyOther7LabelName;
    private String conPartyOther8Id;
    private String conPartyOther8Name;
    private String conPartyOther8LabelName;
    private String conPartyOther9Id;
    private String conPartyOther9Name;
    private String conPartyOther9LabelName;
    private String conPartyOther10Id;
    private String conPartyOther10Name;
    private String conPartyOther10LabelName;

    // 类目
    private String category;

    private String pCompetentDepartment;

    private String secBiddingType;

    private Date tenanceDate;

    private String conPartyEId;
    private String conPartyFId;
    private String conPartyEPm;
    private String conPartyEName;
    private String conPartyFPm;
    private String conPartyFName;

    private String stateFilterType;

    /**
     * 是否修改合同支付条款
     */
    private String conIsPayPlanModels;

    /**
     * 是否修改合同收款账号
     */
    private String conIsPayAccountModels;

    /**
     * 合同价款及支付信息
     */
    private List<ContractPricePaymentInfoDTO> contractPricePaymentInfos;
}
