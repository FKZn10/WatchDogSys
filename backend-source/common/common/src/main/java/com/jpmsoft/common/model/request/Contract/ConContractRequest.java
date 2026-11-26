package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@Data
public class ConContractRequest extends BaseFileRequest {

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
    private String conPartyB2;
    private String conPartyB3;
    private String conPartyB4;
    private String conPartyB5;
    private String conMultiSubject;
    private String conPartyCId;
    private String conPartyDId;
    private String conSup;
    private String conSupIsChangeMoney;
    private String conSupIsChangeDuration;
    //【补充合同】是否需要更改合同乙方单位（01是/02否）
    private String conSupIsChangePartyB;
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
    private String conUsingType;

    private Date conPlanStartDate;
    private Date conPlanEndDate;
    private Date conDate;
    private Date conStartDate;
    private Date conCreateTime;
    private Date conApprovalTime;
    private Date conRmBackDate;

    private Integer conSupChangeDuration;
    //【补充合同】更改后的乙方单位ID
    private String conSupChangePartyBId;
    //【补充合同】更改后的乙方单位名称
    private String conSupChangePartyBName;
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

    private String isUploaded;

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
    private BigDecimal conProgressDeadRatio;
    private BigDecimal conSettlementRatio;
    private String conDepartment;
    private String conDepName;
    private String conEvaluateId;
    private Boolean conIsSupply;
    private String conPurchaseType;
    private String conSupplyTxt;
    private String conPartyA1Id;
    private String conPartyA2Id;
    private String conPartyA3Id;
    private String conPartyA4Id;
    private String conPartyA5Id;
    private String conTerm;
    private String conWarrantyPeriod;
    private String conEnterpriseType;
    private Boolean conUseTemp;
    private BigDecimal conBudgetRate;
    private BigDecimal conDeclareMoney;
    private String conBudgetFile;
    private String conBudgetTxt;
    private Boolean conInvolveFunds;
    /****************************/
    private String conEvaluateName;
    private String conEvaluateCode;
    /**
     * 是否过滤为合同结算申请通过的项目
     */
    private Boolean filterSettlement;
    /**
     * 是否过滤掉Con_Sup_Change_Money（本补充合同调整金额）为空的数据
     */
    private Boolean filterSubChangeAmt;
    private String conBudgetFileIds;
    private String conStartsToIds;
    private String conBasisFileIds;
    private String conProjectName;
    private String secName;
    private String conPartyAName;
    private String conPartyAOtherName;
    private String conPartyBName;
    @ClassInfo(title = "签约合同价", type = "string", value = "conMoneyTotals", dbValue = "mn.con_money_total", no = 6)
    private List<String> conMoneyTotals;
    @ClassInfo(title = "签订时间", type = "date", value = "conDates", dbValue = "mn.con_date", no = 8)
    private List<String> conDates;
    private List<String> conTotalDurations;
    private List<String> conPlanStartDates;
    private List<String> conPlanEndDates;
    @ClassInfo(title = "创建时间", type = "date", value = "conCreateTimes", dbValue = "mn.con_create_time", no = 9)
    private List<String> conCreateTimes;
    @ClassInfo(title = "合同类型", type = "dictionary", value = "conTypes", dbValue = "mn.con_type", no = 4, dictionary = "JPMsoft_HTLX_NEW")
    private List<String> conTypes;

    @ClassInfo(title = "主合同", type = "string", value = "conSups", dbValue = "case when mn.Con_Sup = '是' then '否' when mn.Con_Sup = '否' then '是' else mn.Con_Sup end", no = 5)
    private List<String> conSups;
    private List<String> conIds;
    /**
     * 查询条件
     * 包含的状态
     */
    private List<String> containState;

    /*************北京工行对接所需字段************/
    /**
     * 并联查询
     * 同时查询项目名称，编码,合同名称，编码
     */
    private String blSearch;
    private BigDecimal payManagementExpense;
    private BigDecimal paySporadicExpenditure;
    private BigDecimal payOtherExpensesPayable;
    private BigDecimal payTotalP;
    private String conPayType;
    @JsonProperty("bUnitId")
    private String bUnitId;

    @JsonProperty("pAdvanceFund")
    private Boolean pAdvanceFund;
    @JsonProperty("pCorporateRisk")
    private Boolean pCorporateRisk;

    //合同支付金额，国资合同信息接入使用
    private BigDecimal payAmount;
    /**
     * 查询时排除种类为7的合同
     */
    private Boolean excludeSeven;
    /**
     * 苏相对接
     * 合同所属单位
     */
    private String conAffiliationUnit;
    private String conSerialNumber;
    private Boolean conOpenBidding;
    private String conPayCategory;
    private String conOriginalCode;
    private List<ConContractPayPlanModel> payPlanModels;

    //中标合同价---清标合同价
    private BigDecimal conBidAmt;
    private BigDecimal conBrightAmt;

    //河北项目乙方单位资金分配
    private List<conConsortiumModel> conConsortiumDtl;


    /**
     * 合同概算表
     **/
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
    private String ConPartyB6;
    private String ConPartyB7;
    private String ConPartyB8;
    private String ConPartyB9;
    private String ConPartyB10;
    private List<ConSecondMemberRequest> conSecondMember;
    /**
     * 义乌限定：施工合同关联监理合同ID
     */
    private String conJlId;

    //签订确认时间
    private Date conConfirmDate;

    private Integer isCounted;

    private String conSubPId;
    private String conPartyEId;
    private String conPartyFId;
    /**
     * 合同收款单位明细
     */
    private List<ConPayeeDtlModel> payeeDtlModels;

    private Date tenanceDate;

    private String conPartyEPm;
    private String conPartyEName;
    private String conPartyFPm;
    private String conPartyFName;

    private String ccmId;

    private Boolean skipApproval;

    private String excludeConMulti;

    private String fsCostCode;
    @JsonProperty("pCampus")
    private String pCampus;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("pBuildingScale")
    private String pBuildingScale;
    private List<String> conStartDates;
    private List<String> completeDates;
    private List<String> endTimes;

    private BigDecimal conPayRate;

    /**
     * 标识是否是广东工业大学多项目合同，如果是就不用判断编码是否重复
     * true ：是多项目合同
     * false null ：不是多项目合同
     */
    private Boolean skipCodeCheck;

    private String type;

    @JsonProperty("conPartyAOtherIdB")
    private String conPartyAOtherIdB;
    @JsonProperty("conPartyAOtherIdBName")
    private String conPartyAOtherIdBName;
    @JsonProperty("conPartyAOtherIdC")
    private String conPartyAOtherIdC;
    @JsonProperty("conPartyAOtherIdCName")
    private String conPartyAOtherIdCName;

    private List<String> excludeConIds;

    /**
     * 排除的合同类型列表
     */
    private List<String> excludeConTypes;

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

    /**
     * 排除的合同类别字符串
     */
    private String excludeCategoryStr;

    /**
     * 包含的合同类别字符串
     */
    private String includeCategoryStr;

    @ClassInfo(title = "合同名称", type = "string", value = "conNames", dbValue = "mn.con_name", no = 1)
    private List<String> conNames;

    @ClassInfo(title = "合同编码", type = "string", value = "conCodes", dbValue = "mn.con_code", no = 2)
    private List<String> conCodes;

    @JsonProperty("pNames")
    @ClassInfo(title = "项目名称", type = "string", value = "pNames", dbValue = "pro.p_name", no = 3)
    private List<String> pNames;

    /**
     * 项目名称（模糊查询，与 pNames 多选筛选互不影响）
     */
    @JsonProperty("pName")
    private String pName;

    @ClassInfo(title = "甲方单位", type = "string", value = "conPartyANames", dbValue = "c1.c_name", no = 6)
    private List<String> conPartyANames;

    @ClassInfo(title = "乙方单位", type = "string", value = "conPartyBNames", dbValue = "c.c_name", no = 7)
    private List<String> conPartyBNames;

    @ClassInfo(title = "创建人", type = "string", value = "conCreateUserNames", dbValue = "mn.Con_Create_User_Name", no = 10)
    private List<String> conCreateUserNames;

    @ClassInfo(title = "审批状态", type = "dictionary", value = "conApprovalStates", dbValue = "mn.con_approval_state", no = 11, dictionary = "JPMsoft_LCZT")
    private List<String> conApprovalStates;


    /**
     * 东南五版筛选
     */
    private String conCategoryDn;
}
