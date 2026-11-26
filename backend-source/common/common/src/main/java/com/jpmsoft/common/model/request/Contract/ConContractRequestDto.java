package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@Data
public class ConContractRequestDto extends BaseFileRequest {

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
    private String conMasterConId;
    private String conSupReason;
    private String conCurrency;
    private String conState;
    private String conExecution;
    private String conScan;
    private String conScanIds;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
    private String conBudgetFile;
    private String conBudgetTxt;
    /****************************/
    /**
     * 是否过滤为合同结算申请通过的项目
     */
    private Boolean filterSettlement;
    /**
     * 是否过滤掉Con_Sup_Change_Money（本补充合同调整金额）为空的数据
     */
    private Boolean filterSubChangeAmt;
    private String conStartsToIds;
    private String conBudgetFileIds;
    private String conBasisFileIds;
    private String conProjectName;
    private String secName;
    private String conPartyAName;
    private String conPartyAOtherName;
    private String conPartyBName;
    private List<String> conMoneyTotals;
    private List<String> conDates;
    private List<String> conTotalDurations;
    private List<String> conPlanStartDates;
    private List<String> conPlanEndDates;
    private List<String> conCreateTimes;
    private List<String> conTypes;
    /**
     * 查询条件
     * 包含的状态
     */
    private List<String> containState;

    /*************北京工行对接所需字段************/
    private BigDecimal payManagementExpense;
    private BigDecimal paySporadicExpenditure;
    private BigDecimal payOtherExpensesPayable;
    private BigDecimal payTotalP;
    private String conPayType;
    @JsonProperty("bUnitId")
    private String bUnitId;

    //合同支付金额，国资合同信息接入使用
    private BigDecimal payAmount;

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
}
