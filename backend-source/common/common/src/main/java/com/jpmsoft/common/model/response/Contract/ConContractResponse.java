package com.jpmsoft.common.model.response.Contract;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Contract.ConSecondMemberRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsModel;
import com.jpmsoft.common.model.response.pay.ConPayIntervalResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
public class ConContractResponse extends ProjectCommonRes {

    //合同ID
    private String conId;
    //合同名称
    @ClassInfo(title = "合同名称",type="string",value = "conName")
    private String conName;
    //合同文本（word版）
    private String conWord;
    //所属项目ID
    private String conProjectId;
    //所属代建合同ID
    private String conAgentConId;
    //所属标段ID
    private String conSectionId;
    private String secName;
    //合同编号
    @ClassInfo(title = "合同编号",type="string",value = "conCode")
    private String conCode;
    //合同类型编码
    private String conType;
    //【进度款】应用方式
    private String conProgressWay;
    //甲方单位ID
    private String conPartyAId;
    private String conPartyAName;
    //其他甲方ID
    private String conPartyAOtherId;
    private String conPartyAOtherName;
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
    private String conPartyB1Name;
    //其他乙方2
    private String conPartyB2Name;
    //其他乙方3
    private String conPartyB3Name;
    //其他乙方4
    private String conPartyB4Name;
    //其他乙方5
    private String conPartyB5Name;
    //是否多方主体合同
    private String conMultiSubject;
    //丙方单位ID
    private String conPartyCId;
    private String conPartyCName;
    //丁方单位ID
    private String conPartyDId;
    private String conPartyDName;
    //【补充合同】是否补充合同
    private String conSup;
    //【补充合同】是否需更改金额
    private String conSupIsChangeMoney;
    //【补充合同】是否需更改工期
    private String conSupIsChangeDuration;
    //【补充合同】的主合同ID
    private String conMasterConId;
    private String conMasterConName;
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
    private String conParentConName;
    //合同种类
    private String conCategory;
    //创建人Id
    private String conCreateUserId;
    //创建人姓名
    private String conCreateUserName;
    //计划开工日期
    private Date conPlanStartDate;
    //计划竣工日期
    private Date conPlanEndDate;
    //合同签订日期
    private Date conDate;
    //正式开工日期
    private Date conStartDate;
    //创建时间
    private Date conCreateTime;
    //审批时间
    private Date conApprovalTime;
    //【质量保证金】退回时间
    private Date conRmBackDate;
    //【补充合同】更改的工期
    private Integer conSupChangeDuration;
    private String conSupIsChangePartyB;
    //【补充合同】更改后的乙方单位ID
    private String conSupChangePartyBId;
    //【补充合同】更改后的乙方单位名称
    private String conSupChangePartyBName;
    //总工期（日历天）
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
    //暂定合同总价（含税）【P】
    @ClassInfo(title = "合同金额",type="string",value = "conMoneyTotal")
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
    //【进度款】支付比例
    private Float conProgressRatio;
    //合同增值税税率
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
    private String conPartyBName;
    private String conProjectName;
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
    private String conAcClientName;
    //【代建合同】代建投标主体
    private String conAcBidSubjectId;
    private String conAcBidSubjectName;
    //【代建合同】代建实施主体
    private String conAcImplementId;
    private String conAcImplementName;
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
    //大合同ID
    private String conParentId;
    private Integer conIsHistory;
    private String conParentName;
    private Integer conYear;
    // 【履约保函】履约保证/保函金额
    private BigDecimal ConPbMoney;
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
    private String conRelieveConName;
    private String conRelieveConCode;
    private String conPartyBPmTel;
    private String conRemark;

    private String isUploaded;
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
    private BigDecimal conProgressDeadRatio;
    private BigDecimal conSettlementRatio;
    private String conDepartment;
    private String conDepName;
    private String conEvaluateId;
    private String conEvaluateName;
    private String conEvaluateCode;
    private int conEvaluateCount;
    private BigDecimal conEvaluateAmt;
    private BigDecimal conMasterAmt;
    private int conMasterCount;
    private BigDecimal conEvaluatePeAmt;
    private BigDecimal conEvaluateMoneyTotal;
    private String conEvaluateBName;
    private String  conEvaluateAName;
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

    private String conPartyA1Name;
    private String conPartyA2Name;
    private String conPartyA3Name;
    private String conPartyA4Name;
    private String conPartyA5Name;
    //单位简称
    private String conPartyBAbbName;
    private String conPartyAAbbName;
    private String conPartyAOtherAbbName;
    private String conPartyB1AbbName;
    private String conPartyB2AbbName;
    private String conPartyB3AbbName;
    private String conPartyB4AbbName;
    private String conPartyB5AbbName;
    private String conPartyCAbbName;
    private String conPartyDAbbName;
    private String conAcClientAbbName;
    private String conAcBidSubjectAbbName;
    private String conAcImplementAbbName;
    private String conPbBeneficiaryName;
    /**
     * 审批通过的合同结算申请 决算审定金额合计
     */
    private BigDecimal saApprovedAmtSum;
    private List<ConPayeeDtlModel> conPayeeDtls;
    private List<ConContractPayPlanModel> payPlanModels;

    /**
     * 合同概算表
    **/
    private List<ConEstimateModel> conEstimate;

    //中标合同价---清标合同价
    private BigDecimal conBidAmt;
    private BigDecimal conBrightAmt;

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
    /**
     * 义乌限定：施工合同关联监理合同ID
     */
    private String conJlId;
    /**
     * 义乌限定：施工合同关联监理合同ID
     */
    private String conJlName;

    //签订确认时间
    private Date conConfirmDate;

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

    private String category;

    private String pCompetentDepartment;

    private String secBiddingType;

    private String conSubPId;

    private String conSubPName;


    //合同支付金额
    private  BigDecimal conPayApplySum;

    //实时合同价
    private BigDecimal invSettlementSum;
    private String conPartyEId;
    private String conPartyFId;
    private String conPartyEName;
    private String conPartyFName;

    //河北项目乙方单位资金分配
    private List<conConsortiumModel> conConsortiumDtl;
    /**
     * 合同收款单位明细
     */
    private List<ConPayeeDtlModel> payeeDtlModels;

    @JsonProperty("faId")
    private String faId;
    private String faApprovalUnitName;
    private BigDecimal faTotalContract;
    private BigDecimal faTotalApproved;
    private BigDecimal faSubmitCost;
    private String faRemark;
    private String faCreateUid;
    private String faCreateUname;
    private Date faCreateTime;
    private Integer faState;
    private Date faStateTime;
    private BigDecimal faApprovedAmt;
    private Date faApprovedTime;
    private String faApprovalTitle;
    private String faApprovalNum;
    private Date faApprovalTime;
    private String faApprovalFile;
    private String faApprovalUnit;

    private String conPartyAAgentId;

    private String conPartyAAgent;

    private BigDecimal paExamineApprove;

    private BigDecimal asReviewTheAmount;
    private BigDecimal asAuditedAmount;
    private BigDecimal asDeductionAmount;
    private BigDecimal asReductionRate;
    private BigDecimal bpPayable;

    private BigDecimal bpActualApplication;

    private Date bpApplicationDate;

    private String bpCreateUname;

    private Integer bpState;

    private String paId;

    private String paState;

    private String asId;

    private String conPayeeName;

    private String conPayeeId;
    private Integer contractYear;

    private Date tenanceDate;

    private String conPartyEPm;
    private String conPartyFPm;

    @JsonProperty("pCampus")
    private String pCampus;

    private String partyBName;

    private String fsCostCode;

    private Date completeDate;

    private Date endTime;

    private String auditReport;

    private String ccmId;

    private List<ProSourcesFundsModel> proSourcesFunds;

    private Date warrantyRefundTime;

    private BigDecimal conPayRate;

    // 竣工验收 - 开工时间
    private Date commencementTime;


    private BigDecimal payApprovalSum;

    private BigDecimal paidRatio;

    // 文教头 合同支付区间 上流程已支付
    private ConPayIntervalResponse conPayIntervalResponseClosedList;

    // 文教头 合同支付区间 上流程未支付
    private ConPayIntervalResponse conPayIntervalResponseOpenList;

    @JsonProperty("conPartyAOtherIdB")
    private String conPartyAOtherIdB;
    @JsonProperty("conPartyAOtherIdBName")
    private String conPartyAOtherIdBName;
    @JsonProperty("conPartyAOtherIdC")
    private String conPartyAOtherIdC;
    @JsonProperty("conPartyAOtherIdCName")
    private String conPartyAOtherIdCName;

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

    public List<ContractPricePaymentInfoDTO> getContractPricePaymentInfos() {
        return contractPricePaymentInfos;
    }

    public void setContractPricePaymentInfos(List<ContractPricePaymentInfoDTO> contractPricePaymentInfos) {
        this.contractPricePaymentInfos = contractPricePaymentInfos;
    }

    public String getConIsPayPlanModels() {
        return conIsPayPlanModels;
    }

    public void setConIsPayPlanModels(String conIsPayPlanModels) {
        this.conIsPayPlanModels = conIsPayPlanModels;
    }

    public String getConIsPayAccountModels() {
        return conIsPayAccountModels;
    }

    public void setConIsPayAccountModels(String conIsPayAccountModels) {
        this.conIsPayAccountModels = conIsPayAccountModels;
    }

    public String getConPartyAOtherIdB() {
        return conPartyAOtherIdB;
    }

    public void setConPartyAOtherIdB(String conPartyAOtherIdB) {
        this.conPartyAOtherIdB = conPartyAOtherIdB;
    }

    public String getConPartyAOtherIdBName() {
        return conPartyAOtherIdBName;
    }

    public void setConPartyAOtherIdBName(String conPartyAOtherIdBName) {
        this.conPartyAOtherIdBName = conPartyAOtherIdBName;
    }

    public String getConPartyAOtherIdC() {
        return conPartyAOtherIdC;
    }

    public void setConPartyAOtherIdC(String conPartyAOtherIdC) {
        this.conPartyAOtherIdC = conPartyAOtherIdC;
    }

    public String getConPartyAOtherIdCName() {
        return conPartyAOtherIdCName;
    }

    public void setConPartyAOtherIdCName(String conPartyAOtherIdCName) {
        this.conPartyAOtherIdCName = conPartyAOtherIdCName;
    }

    public ConPayIntervalResponse getConPayIntervalResponseOpenList() {
        return conPayIntervalResponseOpenList;
    }

    public BigDecimal getPaidRatio() {
        return paidRatio;
    }

    public void setPaidRatio(BigDecimal paidRatio) {
        this.paidRatio = paidRatio;
    }

    public ConPayIntervalResponse getConPayIntervalResponseClosedList() {
        return conPayIntervalResponseClosedList;
    }

    public void setConPayIntervalResponseClosedList(ConPayIntervalResponse conPayIntervalResponseClosedList) {
        this.conPayIntervalResponseClosedList = conPayIntervalResponseClosedList;
    }

    public void setConPayIntervalResponseOpenList(ConPayIntervalResponse conPayIntervalResponseOpenList) {
        this.conPayIntervalResponseOpenList = conPayIntervalResponseOpenList;
    }



    public BigDecimal getPayApprovalSum() {
        return payApprovalSum;
    }

    public void setPayApprovalSum(BigDecimal payApprovalSum) {
        this.payApprovalSum = payApprovalSum;
    }

    public Date getCommencementTime() {
        return commencementTime;
    }

    public void setCommencementTime(Date commencementTime) {
        this.commencementTime = commencementTime;
    }

    public BigDecimal getConPayRate() {
        return conPayRate;
    }

    public void setConPayRate(BigDecimal conPayRate) {
        this.conPayRate = conPayRate;
    }

    public Date getWarrantyRefundTime() {
        return warrantyRefundTime;
    }

    public void setWarrantyRefundTime(Date warrantyRefundTime) {
        this.warrantyRefundTime = warrantyRefundTime;
    }

    public List<ProSourcesFundsModel> getProSourcesFunds() {
        return proSourcesFunds;
    }

    public void setProSourcesFunds(List<ProSourcesFundsModel> proSourcesFunds) {
        this.proSourcesFunds = proSourcesFunds;
    }

    public String getCcmId() {
        return ccmId;
    }

    public void setCcmId(String ccmId) {
        this.ccmId = ccmId;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public String getPartyBName() {
        return partyBName;
    }

    public void setPartyBName(String partyBName) {
        this.partyBName = partyBName;
    }

    public String getFsCostCode() {
        return fsCostCode;
    }

    public void setFsCostCode(String fsCostCode) {
        this.fsCostCode = fsCostCode;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAuditReport() {
        return auditReport;
    }

    public void setAuditReport(String auditReport) {
        this.auditReport = auditReport;
    }

    public String getConPartyEPm() {
        return conPartyEPm;
    }

    public void setConPartyEPm(String conPartyEPm) {
        this.conPartyEPm = conPartyEPm;
    }

    public String getConPartyFPm() {
        return conPartyFPm;
    }

    public void setConPartyFPm(String conPartyFPm) {
        this.conPartyFPm = conPartyFPm;
    }

    public Date getTenanceDate() {
        return tenanceDate;
    }

    public void setTenanceDate(Date tenanceDate) {
        this.tenanceDate = tenanceDate;
    }

    public String getConPayeeName() {
        return conPayeeName;
    }

    public void setConPayeeName(String conPayeeName) {
        this.conPayeeName = conPayeeName;
    }

    public String getConPayeeId() {
        return conPayeeId;
    }

    public void setConPayeeId(String conPayeeId) {
        this.conPayeeId = conPayeeId;
    }

    public String getAsId() {
        return asId;
    }

    public void setAsId(String asId) {
        this.asId = asId;
    }

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getPaState() {
        return paState;
    }

    public void setPaState(String paState) {
        this.paState = paState;
    }

    public String getConPartyAAgent() {
        return conPartyAAgent;
    }

    public void setConPartyAAgent(String conPartyAAgent) {
        this.conPartyAAgent = conPartyAAgent;
    }

    public BigDecimal getBpPayable() {
        return bpPayable;
    }

    public void setBpPayable(BigDecimal bpPayable) {
        this.bpPayable = bpPayable;
    }

    public BigDecimal getBpActualApplication() {
        return bpActualApplication;
    }

    public void setBpActualApplication(BigDecimal bpActualApplication) {
        this.bpActualApplication = bpActualApplication;
    }

    public Date getBpApplicationDate() {
        return bpApplicationDate;
    }

    public void setBpApplicationDate(Date bpApplicationDate) {
        this.bpApplicationDate = bpApplicationDate;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Integer getBpState() {
        return bpState;
    }

    public void setBpState(Integer bpState) {
        this.bpState = bpState;
    }

    public BigDecimal getAsReviewTheAmount() {
        return asReviewTheAmount;
    }

    public void setAsReviewTheAmount(BigDecimal asReviewTheAmount) {
        this.asReviewTheAmount = asReviewTheAmount;
    }

    public BigDecimal getAsAuditedAmount() {
        return asAuditedAmount;
    }

    public void setAsAuditedAmount(BigDecimal asAuditedAmount) {
        this.asAuditedAmount = asAuditedAmount;
    }

    public BigDecimal getAsDeductionAmount() {
        return asDeductionAmount;
    }

    public void setAsDeductionAmount(BigDecimal asDeductionAmount) {
        this.asDeductionAmount = asDeductionAmount;
    }

    public BigDecimal getAsReductionRate() {
        return asReductionRate;
    }

    public void setAsReductionRate(BigDecimal asReductionRate) {
        this.asReductionRate = asReductionRate;
    }

    public String getConPartyAAgentId() {
        return conPartyAAgentId;
    }

    public void setConPartyAAgentId(String conPartyAAgentId) {
        this.conPartyAAgentId = conPartyAAgentId;
    }

    public BigDecimal getPaExamineApprove() {
        return paExamineApprove;
    }

    public void setPaExamineApprove(BigDecimal paExamineApprove) {
        this.paExamineApprove = paExamineApprove;
    }

    public String getFaId() {
        return faId;
    }

    public void setFaId(String faId) {
        this.faId = faId;
    }

    public String getFaApprovalUnitName() {
        return faApprovalUnitName;
    }

    public void setFaApprovalUnitName(String faApprovalUnitName) {
        this.faApprovalUnitName = faApprovalUnitName;
    }

    public BigDecimal getFaTotalContract() {
        return faTotalContract;
    }

    public void setFaTotalContract(BigDecimal faTotalContract) {
        this.faTotalContract = faTotalContract;
    }

    public BigDecimal getFaTotalApproved() {
        return faTotalApproved;
    }

    public void setFaTotalApproved(BigDecimal faTotalApproved) {
        this.faTotalApproved = faTotalApproved;
    }

    public BigDecimal getFaSubmitCost() {
        return faSubmitCost;
    }

    public void setFaSubmitCost(BigDecimal faSubmitCost) {
        this.faSubmitCost = faSubmitCost;
    }

    public String getFaRemark() {
        return faRemark;
    }

    public void setFaRemark(String faRemark) {
        this.faRemark = faRemark;
    }

    public String getFaCreateUid() {
        return faCreateUid;
    }

    public void setFaCreateUid(String faCreateUid) {
        this.faCreateUid = faCreateUid;
    }

    public String getFaCreateUname() {
        return faCreateUname;
    }

    public void setFaCreateUname(String faCreateUname) {
        this.faCreateUname = faCreateUname;
    }

    public Date getFaCreateTime() {
        return faCreateTime;
    }

    public void setFaCreateTime(Date faCreateTime) {
        this.faCreateTime = faCreateTime;
    }

    public Integer getFaState() {
        return faState;
    }

    public void setFaState(Integer faState) {
        this.faState = faState;
    }

    public Date getFaStateTime() {
        return faStateTime;
    }

    public void setFaStateTime(Date faStateTime) {
        this.faStateTime = faStateTime;
    }

    public BigDecimal getFaApprovedAmt() {
        return faApprovedAmt;
    }

    public void setFaApprovedAmt(BigDecimal faApprovedAmt) {
        this.faApprovedAmt = faApprovedAmt;
    }

    public Date getFaApprovedTime() {
        return faApprovedTime;
    }

    public void setFaApprovedTime(Date faApprovedTime) {
        this.faApprovedTime = faApprovedTime;
    }

    public String getFaApprovalTitle() {
        return faApprovalTitle;
    }

    public void setFaApprovalTitle(String faApprovalTitle) {
        this.faApprovalTitle = faApprovalTitle;
    }

    public String getFaApprovalNum() {
        return faApprovalNum;
    }

    public void setFaApprovalNum(String faApprovalNum) {
        this.faApprovalNum = faApprovalNum;
    }

    public Date getFaApprovalTime() {
        return faApprovalTime;
    }

    public void setFaApprovalTime(Date faApprovalTime) {
        this.faApprovalTime = faApprovalTime;
    }

    public String getFaApprovalFile() {
        return faApprovalFile;
    }

    public void setFaApprovalFile(String faApprovalFile) {
        this.faApprovalFile = faApprovalFile;
    }

    public String getFaApprovalUnit() {
        return faApprovalUnit;
    }

    public void setFaApprovalUnit(String faApprovalUnit) {
        this.faApprovalUnit = faApprovalUnit;
    }

    public String getConSubPId() {
        return conSubPId;
    }

    public void setConSubPId(String conSubPId) {
        this.conSubPId = conSubPId;
    }

    public String getConSubPName() {
        return conSubPName;
    }

    public void setConSubPName(String conSubPName) {
        this.conSubPName = conSubPName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getConPartyOther1Id() {
        return conPartyOther1Id;
    }

    public BigDecimal getInvSettlementSum() {
        return invSettlementSum;
    }

    public void setInvSettlementSum(BigDecimal invSettlementSum) {
        this.invSettlementSum = invSettlementSum;
    }

    public String getConPartyEId() {
        return conPartyEId;
    }

    public void setConPartyEId(String conPartyEId) {
        this.conPartyEId = conPartyEId;
    }

    public String getConPartyFId() {
        return conPartyFId;
    }

    public void setConPartyFId(String conPartyFId) {
        this.conPartyFId = conPartyFId;
    }

    public String getConPartyEName() {
        return conPartyEName;
    }

    public void setConPartyEName(String conPartyEName) {
        this.conPartyEName = conPartyEName;
    }

    public String getConPartyFName() {
        return conPartyFName;
    }

    public void setConPartyFName(String conPartyFName) {
        this.conPartyFName = conPartyFName;
    }

    public void setConPartyOther1Id(String conPartyOther1Id) {
        this.conPartyOther1Id = conPartyOther1Id;
    }

    public String getConPartyOther1Name() {
        return conPartyOther1Name;
    }

    public void setConPartyOther1Name(String conPartyOther1Name) {
        this.conPartyOther1Name = conPartyOther1Name;
    }

    public String getConPartyOther1LabelName() {
        return conPartyOther1LabelName;
    }

    public void setConPartyOther1LabelName(String conPartyOther1LabelName) {
        this.conPartyOther1LabelName = conPartyOther1LabelName;
    }

    public String getConPartyOther2Id() {
        return conPartyOther2Id;
    }

    public void setConPartyOther2Id(String conPartyOther2Id) {
        this.conPartyOther2Id = conPartyOther2Id;
    }

    public String getConPartyOther2Name() {
        return conPartyOther2Name;
    }

    public void setConPartyOther2Name(String conPartyOther2Name) {
        this.conPartyOther2Name = conPartyOther2Name;
    }

    public String getConPartyOther2LabelName() {
        return conPartyOther2LabelName;
    }

    public void setConPartyOther2LabelName(String conPartyOther2LabelName) {
        this.conPartyOther2LabelName = conPartyOther2LabelName;
    }

    public String getConPartyOther3Id() {
        return conPartyOther3Id;
    }

    public void setConPartyOther3Id(String conPartyOther3Id) {
        this.conPartyOther3Id = conPartyOther3Id;
    }

    public String getConPartyOther3Name() {
        return conPartyOther3Name;
    }

    public void setConPartyOther3Name(String conPartyOther3Name) {
        this.conPartyOther3Name = conPartyOther3Name;
    }

    public String getConPartyOther3LabelName() {
        return conPartyOther3LabelName;
    }

    public void setConPartyOther3LabelName(String conPartyOther3LabelName) {
        this.conPartyOther3LabelName = conPartyOther3LabelName;
    }

    public String getConPartyOther4Id() {
        return conPartyOther4Id;
    }

    public void setConPartyOther4Id(String conPartyOther4Id) {
        this.conPartyOther4Id = conPartyOther4Id;
    }

    public String getConPartyOther4Name() {
        return conPartyOther4Name;
    }

    public void setConPartyOther4Name(String conPartyOther4Name) {
        this.conPartyOther4Name = conPartyOther4Name;
    }

    public String getConPartyOther4LabelName() {
        return conPartyOther4LabelName;
    }

    public void setConPartyOther4LabelName(String conPartyOther4LabelName) {
        this.conPartyOther4LabelName = conPartyOther4LabelName;
    }

    public String getConPartyOther5Id() {
        return conPartyOther5Id;
    }

    public void setConPartyOther5Id(String conPartyOther5Id) {
        this.conPartyOther5Id = conPartyOther5Id;
    }

    public String getConPartyOther5Name() {
        return conPartyOther5Name;
    }

    public void setConPartyOther5Name(String conPartyOther5Name) {
        this.conPartyOther5Name = conPartyOther5Name;
    }

    public String getConPartyOther5LabelName() {
        return conPartyOther5LabelName;
    }

    public void setConPartyOther5LabelName(String conPartyOther5LabelName) {
        this.conPartyOther5LabelName = conPartyOther5LabelName;
    }

    public String getConPartyOther6Id() {
        return conPartyOther6Id;
    }

    public void setConPartyOther6Id(String conPartyOther6Id) {
        this.conPartyOther6Id = conPartyOther6Id;
    }

    public String getConPartyOther6Name() {
        return conPartyOther6Name;
    }

    public void setConPartyOther6Name(String conPartyOther6Name) {
        this.conPartyOther6Name = conPartyOther6Name;
    }

    public String getConPartyOther6LabelName() {
        return conPartyOther6LabelName;
    }

    public void setConPartyOther6LabelName(String conPartyOther6LabelName) {
        this.conPartyOther6LabelName = conPartyOther6LabelName;
    }

    public String getConPartyOther7Id() {
        return conPartyOther7Id;
    }

    public void setConPartyOther7Id(String conPartyOther7Id) {
        this.conPartyOther7Id = conPartyOther7Id;
    }

    public String getConPartyOther7Name() {
        return conPartyOther7Name;
    }

    public void setConPartyOther7Name(String conPartyOther7Name) {
        this.conPartyOther7Name = conPartyOther7Name;
    }

    public String getConPartyOther7LabelName() {
        return conPartyOther7LabelName;
    }

    public void setConPartyOther7LabelName(String conPartyOther7LabelName) {
        this.conPartyOther7LabelName = conPartyOther7LabelName;
    }

    public String getConPartyOther8Id() {
        return conPartyOther8Id;
    }

    public void setConPartyOther8Id(String conPartyOther8Id) {
        this.conPartyOther8Id = conPartyOther8Id;
    }

    public String getConPartyOther8Name() {
        return conPartyOther8Name;
    }

    public void setConPartyOther8Name(String conPartyOther8Name) {
        this.conPartyOther8Name = conPartyOther8Name;
    }

    public String getConPartyOther8LabelName() {
        return conPartyOther8LabelName;
    }

    public void setConPartyOther8LabelName(String conPartyOther8LabelName) {
        this.conPartyOther8LabelName = conPartyOther8LabelName;
    }

    public String getConPartyOther9Id() {
        return conPartyOther9Id;
    }

    public void setConPartyOther9Id(String conPartyOther9Id) {
        this.conPartyOther9Id = conPartyOther9Id;
    }

    public String getConPartyOther9Name() {
        return conPartyOther9Name;
    }

    public void setConPartyOther9Name(String conPartyOther9Name) {
        this.conPartyOther9Name = conPartyOther9Name;
    }

    public String getConPartyOther9LabelName() {
        return conPartyOther9LabelName;
    }

    public void setConPartyOther9LabelName(String conPartyOther9LabelName) {
        this.conPartyOther9LabelName = conPartyOther9LabelName;
    }

    public String getConPartyOther10Id() {
        return conPartyOther10Id;
    }

    public void setConPartyOther10Id(String conPartyOther10Id) {
        this.conPartyOther10Id = conPartyOther10Id;
    }

    public String getConPartyOther10Name() {
        return conPartyOther10Name;
    }

    public void setConPartyOther10Name(String conPartyOther10Name) {
        this.conPartyOther10Name = conPartyOther10Name;
    }

    public String getConPartyOther10LabelName() {
        return conPartyOther10LabelName;
    }

    public void setConPartyOther10LabelName(String conPartyOther10LabelName) {
        this.conPartyOther10LabelName = conPartyOther10LabelName;
    }

    public String getConPartyB6Name() {
        return conPartyB6Name;
    }

    public void setConPartyB6Name(String conPartyB6Name) {
        this.conPartyB6Name = conPartyB6Name;
    }

    public String getConPartyB7Name() {
        return conPartyB7Name;
    }

    public void setConPartyB7Name(String conPartyB7Name) {
        this.conPartyB7Name = conPartyB7Name;
    }

    public String getConPartyB8Name() {
        return conPartyB8Name;
    }

    public void setConPartyB8Name(String conPartyB8Name) {
        this.conPartyB8Name = conPartyB8Name;
    }

    public String getConPartyB9Name() {
        return conPartyB9Name;
    }

    public void setConPartyB9Name(String conPartyB9Name) {
        this.conPartyB9Name = conPartyB9Name;
    }

    public String getConPartyB10Name() {
        return conPartyB10Name;
    }

    public void setConPartyB10Name(String conPartyB10Name) {
        this.conPartyB10Name = conPartyB10Name;
    }

    public String getConPartyB1LabelName() {
        return conPartyB1LabelName;
    }

    public void setConPartyB1LabelName(String conPartyB1LabelName) {
        this.conPartyB1LabelName = conPartyB1LabelName;
    }

    public String getConPartyB2LabelName() {
        return conPartyB2LabelName;
    }

    public void setConPartyB2LabelName(String conPartyB2LabelName) {
        this.conPartyB2LabelName = conPartyB2LabelName;
    }

    public String getConPartyB3LabelName() {
        return conPartyB3LabelName;
    }

    public void setConPartyB3LabelName(String conPartyB3LabelName) {
        this.conPartyB3LabelName = conPartyB3LabelName;
    }

    public String getConPartyB4LabelName() {
        return conPartyB4LabelName;
    }

    public void setConPartyB4LabelName(String conPartyB4LabelName) {
        this.conPartyB4LabelName = conPartyB4LabelName;
    }

    public String getConPartyB5LabelName() {
        return conPartyB5LabelName;
    }

    public void setConPartyB5LabelName(String conPartyB5LabelName) {
        this.conPartyB5LabelName = conPartyB5LabelName;
    }

    public String getConPartyB6LabelName() {
        return conPartyB6LabelName;
    }

    public void setConPartyB6LabelName(String conPartyB6LabelName) {
        this.conPartyB6LabelName = conPartyB6LabelName;
    }

    public String getConPartyB7LabelName() {
        return conPartyB7LabelName;
    }

    public void setConPartyB7LabelName(String conPartyB7LabelName) {
        this.conPartyB7LabelName = conPartyB7LabelName;
    }

    public String getConPartyB8LabelName() {
        return conPartyB8LabelName;
    }

    public void setConPartyB8LabelName(String conPartyB8LabelName) {
        this.conPartyB8LabelName = conPartyB8LabelName;
    }

    public String getConPartyB9LabelName() {
        return conPartyB9LabelName;
    }

    public void setConPartyB9LabelName(String conPartyB9LabelName) {
        this.conPartyB9LabelName = conPartyB9LabelName;
    }

    public String getConPartyB10LabelName() {
        return conPartyB10LabelName;
    }

    public void setConPartyB10LabelName(String conPartyB10LabelName) {
        this.conPartyB10LabelName = conPartyB10LabelName;
    }

    public BigDecimal getConPayApplySum() {
        return conPayApplySum;
    }

    public void setConPayApplySum(BigDecimal conPayApplySum) {
        this.conPayApplySum = conPayApplySum;
    }

    public BigDecimal getConBidControlPrice() {
        return conBidControlPrice;
    }

    public void setConBidControlPrice(BigDecimal conBidControlPrice) {
        this.conBidControlPrice = conBidControlPrice;
    }

    public BigDecimal getConBidControlRate() {
        return conBidControlRate;
    }

    public void setConBidControlRate(BigDecimal conBidControlRate) {
        this.conBidControlRate = conBidControlRate;
    }

    public String getConPartyB6() {
        return conPartyB6;
    }

    public void setConPartyB6(String conPartyB6) {
        this.conPartyB6 = conPartyB6;
    }

    public String getConPartyB7() {
        return conPartyB7;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public void setConPartyB7(String conPartyB7) {
        this.conPartyB7 = conPartyB7;
    }

    public String getConPartyB8() {
        return conPartyB8;
    }

    public void setConPartyB8(String conPartyB8) {
        this.conPartyB8 = conPartyB8;
    }

    public String getConPartyB9() {
        return conPartyB9;
    }

    public void setConPartyB9(String conPartyB9) {
        this.conPartyB9 = conPartyB9;
    }

    public String getConPartyB10() {
        return conPartyB10;
    }

    public void setConPartyB10(String conPartyB10) {
        this.conPartyB10 = conPartyB10;
    }

    public List<ConSecondMemberRequest> getConSecondMember() {
        return conSecondMember;
    }

    public void setConSecondMember(List<ConSecondMemberRequest> conSecondMember) {
        this.conSecondMember = conSecondMember;
    }

    public String getConBidDocument() {
        return conBidDocument;
    }

    public void setConBidDocument(String conBidDocument) {
        this.conBidDocument = conBidDocument;
    }

    public String getConMoneyRate() {
        return conMoneyRate;
    }

    public void setConMoneyRate(String conMoneyRate) {
        this.conMoneyRate = conMoneyRate;
    }

    public BigDecimal getConUnitPrice() {
        return conUnitPrice;
    }

    public void setConUnitPrice(BigDecimal conUnitPrice) {
        this.conUnitPrice = conUnitPrice;
    }

    public String getConUnit() {
        return conUnit;
    }

    public void setConUnit(String conUnit) {
        this.conUnit = conUnit;
    }

    public String getConDeclaration() {
        return conDeclaration;
    }

    public void setConDeclaration(String conDeclaration) {
        this.conDeclaration = conDeclaration;
    }

    public Date getConFilingsDate() {
        return conFilingsDate;
    }

    public void setConFilingsDate(Date conFilingsDate) {
        this.conFilingsDate = conFilingsDate;
    }

    public BigDecimal getConPerformancePeriod() {
        return conPerformancePeriod;
    }

    public void setConPerformancePeriod(BigDecimal conPerformancePeriod) {
        this.conPerformancePeriod = conPerformancePeriod;
    }

    public String getConFollowAudit() {
        return conFollowAudit;
    }

    public void setConFollowAudit(String conFollowAudit) {
        this.conFollowAudit = conFollowAudit;
    }

    public String getConIsSupervisor() {
        return conIsSupervisor;
    }

    public void setConIsSupervisor(String conIsSupervisor) {
        this.conIsSupervisor = conIsSupervisor;
    }

    public BigDecimal getConFeeRatio() {
        return conFeeRatio;
    }

    public void setConFeeRatio(BigDecimal conFeeRatio) {
        this.conFeeRatio = conFeeRatio;
    }

    public BigDecimal getConTaxRatio() {
        return conTaxRatio;
    }

    public void setConTaxRatio(BigDecimal conTaxRatio) {
        this.conTaxRatio = conTaxRatio;
    }

    public String getConPaymentMethod() {
        return conPaymentMethod;
    }

    public void setConPaymentMethod(String conPaymentMethod) {
        this.conPaymentMethod = conPaymentMethod;
    }

    public String getConAcceptDepId() {
        return conAcceptDepId;
    }

    public void setConAcceptDepId(String conAcceptDepId) {
        this.conAcceptDepId = conAcceptDepId;
    }

    public String getConIsDisciplineApproved() {
        return conIsDisciplineApproved;
    }

    public void setConIsDisciplineApproved(String conIsDisciplineApproved) {
        this.conIsDisciplineApproved = conIsDisciplineApproved;
    }

    public String getConUnitC() {
        return conUnitC;
    }

    public void setConUnitC(String conUnitC) {
        this.conUnitC = conUnitC;
    }

    public String getConUnitC1() {
        return conUnitC1;
    }

    public void setConUnitC1(String conUnitC1) {
        this.conUnitC1 = conUnitC1;
    }

    public String getConUnitD() {
        return conUnitD;
    }

    public void setConUnitD(String conUnitD) {
        this.conUnitD = conUnitD;
    }

    public String getConUnitD1() {
        return conUnitD1;
    }

    public void setConUnitD1(String conUnitD1) {
        this.conUnitD1 = conUnitD1;
    }

    public Date getConPbPayDate() {
        return conPbPayDate;
    }

    public void setConPbPayDate(Date conPbPayDate) {
        this.conPbPayDate = conPbPayDate;
    }

    public Date getConPbDueDate() {
        return conPbDueDate;
    }

    public void setConPbDueDate(Date conPbDueDate) {
        this.conPbDueDate = conPbDueDate;
    }

    public BigDecimal getConMoneyTotalNoTax() {
        return conMoneyTotalNoTax;
    }

    public void setConMoneyTotalNoTax(BigDecimal conMoneyTotalNoTax) {
        this.conMoneyTotalNoTax = conMoneyTotalNoTax;
    }

    public BigDecimal getConStampDutyRate() {
        return conStampDutyRate;
    }

    public void setConStampDutyRate(BigDecimal conStampDutyRate) {
        this.conStampDutyRate = conStampDutyRate;
    }

    public BigDecimal getConStampDutyAmount() {
        return conStampDutyAmount;
    }

    public void setConStampDutyAmount(BigDecimal conStampDutyAmount) {
        this.conStampDutyAmount = conStampDutyAmount;
    }

    public String getConPartyBHead() {
        return conPartyBHead;
    }

    public void setConPartyBHead(String conPartyBHead) {
        this.conPartyBHead = conPartyBHead;
    }

    public String getConPartyB1Pm() {
        return conPartyB1Pm;
    }

    public void setConPartyB1Pm(String conPartyB1Pm) {
        this.conPartyB1Pm = conPartyB1Pm;
    }

    public String getConPartyB2Pm() {
        return conPartyB2Pm;
    }

    public void setConPartyB2Pm(String conPartyB2Pm) {
        this.conPartyB2Pm = conPartyB2Pm;
    }

    public String getConPartyB3Pm() {
        return conPartyB3Pm;
    }

    public void setConPartyB3Pm(String conPartyB3Pm) {
        this.conPartyB3Pm = conPartyB3Pm;
    }

    public String getConPartyB4Pm() {
        return conPartyB4Pm;
    }

    public void setConPartyB4Pm(String conPartyB4Pm) {
        this.conPartyB4Pm = conPartyB4Pm;
    }

    public String getConPartyB5Pm() {
        return conPartyB5Pm;
    }

    public void setConPartyB5Pm(String conPartyB5Pm) {
        this.conPartyB5Pm = conPartyB5Pm;
    }

    public String getConPartyCPm() {
        return conPartyCPm;
    }

    public void setConPartyCPm(String conPartyCPm) {
        this.conPartyCPm = conPartyCPm;
    }

    public String getConPartyDPm() {
        return conPartyDPm;
    }

    public void setConPartyDPm(String conPartyDPm) {
        this.conPartyDPm = conPartyDPm;
    }

    public BigDecimal getConProgressMoney() {
        return conProgressMoney;
    }

    public void setConProgressMoney(BigDecimal conProgressMoney) {
        this.conProgressMoney = conProgressMoney;
    }

    public String getConNoticeReport() {
        return conNoticeReport;
    }

    public void setConNoticeReport(String conNoticeReport) {
        this.conNoticeReport = conNoticeReport;
    }

    public BigDecimal getConReserveMoney() {
        return conReserveMoney;
    }

    public void setConReserveMoney(BigDecimal conReserveMoney) {
        this.conReserveMoney = conReserveMoney;
    }

    public String getThirdStatus() {
        return thirdStatus;
    }

    public void setThirdStatus(String thirdStatus) {
        this.thirdStatus = thirdStatus;
    }

    public String getThirdApprovalComment() {
        return thirdApprovalComment;
    }

    public void setThirdApprovalComment(String thirdApprovalComment) {
        this.thirdApprovalComment = thirdApprovalComment;
    }

    public String getThirdRequestId() {
        return thirdRequestId;
    }

    public void setThirdRequestId(String thirdRequestId) {
        this.thirdRequestId = thirdRequestId;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    public String getConContentSummary() {
        return conContentSummary;
    }

    public void setConContentSummary(String conContentSummary) {
        this.conContentSummary = conContentSummary;
    }

    public String getConApprovalMatters() {
        return conApprovalMatters;
    }

    public void setConApprovalMatters(String conApprovalMatters) {
        this.conApprovalMatters = conApprovalMatters;
    }

    public List<ConEstimateModel> getConEstimate() {
        return conEstimate;
    }

    public void setConEstimate(List<ConEstimateModel> conEstimate) {
        this.conEstimate = conEstimate;
    }

    public BigDecimal getConBidAmt() {
        return conBidAmt;
    }

    public BigDecimal getConBrightAmt() {
        return conBrightAmt;
    }
    public Integer getConSupChangeDurType() {
        return conSupChangeDurType;
    }

    public void setConSupChangeDurType(Integer conSupChangeDurType) {
        this.conSupChangeDurType = conSupChangeDurType;
    }

    public Integer getConSupChangeMonType() {
        return conSupChangeMonType;
    }

    public void setConSupChangeMonType(Integer conSupChangeMonType) {
        this.conSupChangeMonType = conSupChangeMonType;
    }

    public Integer getConIsParent() {
        return conIsParent;
    }

    public void setConIsParent(Integer conIsParent) {
        this.conIsParent = conIsParent;
    }

    public String getConParentId() {
        return conParentId;
    }

    public void setConParentId(String conParentId) {
        this.conParentId = conParentId;
    }

    public String getConParentName() {
        return conParentName;
    }

    public void setConParentName(String conParentName) {
        this.conParentName = conParentName;
    }

    public String getConTdName() {
        return conTdName;
    }

    public void setConTdName(String conTdName) {
        this.conTdName = conTdName;
    }

    public String getConTdNum() {
        return conTdNum;
    }

    public void setConTdNum(String conTdNum) {
        this.conTdNum = conTdNum;
    }

    public String getConTdMobileNumber() {
        return conTdMobileNumber;
    }

    public void setConTdMobileNumber(String conTdMobileNumber) {
        this.conTdMobileNumber = conTdMobileNumber;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConWord() {
        return conWord;
    }

    public void setConWord(String conWord) {
        this.conWord = conWord;
    }

    public String getConProjectId() {
        return conProjectId;
    }

    public void setConProjectId(String conProjectId) {
        this.conProjectId = conProjectId;
    }

    public String getConAgentConId() {
        return conAgentConId;
    }

    public void setConAgentConId(String conAgentConId) {
        this.conAgentConId = conAgentConId;
    }

    public String getConSectionId() {
        return conSectionId;
    }

    public void setConSectionId(String conSectionId) {
        this.conSectionId = conSectionId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public BigDecimal getConMoneyAward() {
        return conMoneyAward;
    }

    public void setConMoneyAward(BigDecimal conMoneyAward) {
        this.conMoneyAward = conMoneyAward;
    }

    public String getConProgressWay() {
        return conProgressWay;
    }

    public void setConProgressWay(String conProgressWay) {
        this.conProgressWay = conProgressWay;
    }

    public Float getConProgressRatio() {
        return conProgressRatio;
    }

    public void setConProgressRatio(Float conProgressRatio) {
        this.conProgressRatio = conProgressRatio;
    }

    public String getConPartyAId() {
        return conPartyAId;
    }

    public void setConPartyAId(String conPartyAId) {
        this.conPartyAId = conPartyAId;
    }

    public String getConPartyAOtherId() {
        return conPartyAOtherId;
    }

    public void setConPartyAOtherId(String conPartyAOtherId) {
        this.conPartyAOtherId = conPartyAOtherId;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConSupervisionOrg() {
        return conSupervisionOrg;
    }

    public void setConSupervisionOrg(String conSupervisionOrg) {
        this.conSupervisionOrg = conSupervisionOrg;
    }

    public String getConGeneralEngineer() {
        return conGeneralEngineer;
    }

    public void setConGeneralEngineer(String conGeneralEngineer) {
        this.conGeneralEngineer = conGeneralEngineer;
    }

    public String getConPartyBAgent() {
        return conPartyBAgent;
    }

    public void setConPartyBAgent(String conPartyBAgent) {
        this.conPartyBAgent = conPartyBAgent;
    }

    public String getConPartyBAgentTel() {
        return conPartyBAgentTel;
    }

    public void setConPartyBAgentTel(String conPartyBAgentTel) {
        this.conPartyBAgentTel = conPartyBAgentTel;
    }

    public String getConPartyBPm() {
        return conPartyBPm;
    }

    public void setConPartyBPm(String conPartyBPm) {
        this.conPartyBPm = conPartyBPm;
    }

    public String getConPartyBPmIdcard() {
        return conPartyBPmIdcard;
    }

    public void setConPartyBPmIdcard(String conPartyBPmIdcard) {
        this.conPartyBPmIdcard = conPartyBPmIdcard;
    }

    public String getConPartyBPmConsYzh() {
        return conPartyBPmConsYzh;
    }

    public void setConPartyBPmConsYzh(String conPartyBPmConsYzh) {
        this.conPartyBPmConsYzh = conPartyBPmConsYzh;
    }

    public String getConPartyBPmConsNu() {
        return conPartyBPmConsNu;
    }

    public void setConPartyBPmConsNu(String conPartyBPmConsNu) {
        this.conPartyBPmConsNu = conPartyBPmConsNu;
    }

    public String getConPartyBCto() {
        return conPartyBCto;
    }

    public void setConPartyBCto(String conPartyBCto) {
        this.conPartyBCto = conPartyBCto;
    }

    public String getConPartyBSm() {
        return conPartyBSm;
    }

    public void setConPartyBSm(String conPartyBSm) {
        this.conPartyBSm = conPartyBSm;
    }

    public String getConConsortium() {
        return conConsortium;
    }

    public void setConConsortium(String conConsortium) {
        this.conConsortium = conConsortium;
    }

    public String getConPartyB1() {
        return conPartyB1;
    }

    public void setConPartyB1(String conPartyB1) {
        this.conPartyB1 = conPartyB1;
    }

    public String getConPartyB2() {
        return conPartyB2;
    }

    public void setConPartyB2(String conPartyB2) {
        this.conPartyB2 = conPartyB2;
    }

    public String getConPartyB3() {
        return conPartyB3;
    }

    public void setConPartyB3(String conPartyB3) {
        this.conPartyB3 = conPartyB3;
    }

    public String getConPartyB4() {
        return conPartyB4;
    }

    public void setConPartyB4(String conPartyB4) {
        this.conPartyB4 = conPartyB4;
    }

    public String getConPartyB5() {
        return conPartyB5;
    }

    public void setConPartyB5(String conPartyB5) {
        this.conPartyB5 = conPartyB5;
    }

    public String getConMultiSubject() {
        return conMultiSubject;
    }

    public void setConMultiSubject(String conMultiSubject) {
        this.conMultiSubject = conMultiSubject;
    }

    public String getConPartyCId() {
        return conPartyCId;
    }

    public void setConPartyCId(String conPartyCId) {
        this.conPartyCId = conPartyCId;
    }

    public String getConPartyDId() {
        return conPartyDId;
    }

    public void setConPartyDId(String conPartyDId) {
        this.conPartyDId = conPartyDId;
    }

    public String getConSup() {
        return conSup;
    }

    public void setConSup(String conSup) {
        this.conSup = conSup;
    }

    public String getConSupIsChangeMoney() {
        return conSupIsChangeMoney;
    }

    public void setConSupIsChangeMoney(String conSupIsChangeMoney) {
        this.conSupIsChangeMoney = conSupIsChangeMoney;
    }

    public BigDecimal getConSupChangeMoney() {
        return conSupChangeMoney;
    }

    public void setConSupChangeMoney(BigDecimal conSupChangeMoney) {
        this.conSupChangeMoney = conSupChangeMoney;
    }

    public String getConSupIsChangeDuration() {
        return conSupIsChangeDuration;
    }

    public void setConSupIsChangeDuration(String conSupIsChangeDuration) {
        this.conSupIsChangeDuration = conSupIsChangeDuration;
    }

    public String getConSupIsChangePartyB() {
        return conSupIsChangePartyB;
    }

    public void setConSupIsChangePartyB(String conSupIsChangePartyB) {
        this.conSupIsChangePartyB = conSupIsChangePartyB;
    }

    public String getConSupChangePartyBId() {
        return conSupChangePartyBId;
    }

    public void setConSupChangePartyBId(String conSupChangePartyBId) {
        this.conSupChangePartyBId = conSupChangePartyBId;
    }

    public String getConSupChangePartyBName() {
        return conSupChangePartyBName;
    }

    public void setConSupChangePartyBName(String conSupChangePartyBName) {
        this.conSupChangePartyBName = conSupChangePartyBName;
    }

    public Integer getConSupChangeDuration() {
        return conSupChangeDuration;
    }

    public void setConSupChangeDuration(Integer conSupChangeDuration) {
        this.conSupChangeDuration = conSupChangeDuration;
    }

    public String getConMasterConId() {
        return conMasterConId;
    }

    public void setConMasterConId(String conMasterConId) {
        this.conMasterConId = conMasterConId;
    }

    public String getConSupReason() {
        return conSupReason;
    }

    public void setConSupReason(String conSupReason) {
        this.conSupReason = conSupReason;
    }

    public String getConCurrency() {
        return conCurrency;
    }

    public void setConCurrency(String conCurrency) {
        this.conCurrency = conCurrency;
    }

    public Integer getConTotalDuration() {
        return conTotalDuration;
    }

    public void setConTotalDuration(Integer conTotalDuration) {
        this.conTotalDuration = conTotalDuration;
    }

    public Date getConPlanStartDate() {
        return conPlanStartDate;
    }

    public void setConPlanStartDate(Date conPlanStartDate) {
        this.conPlanStartDate = conPlanStartDate;
    }

    public Date getConPlanEndDate() {
        return conPlanEndDate;
    }

    public void setConPlanEndDate(Date conPlanEndDate) {
        this.conPlanEndDate = conPlanEndDate;
    }

    public BigDecimal getConMoneyTentative() {
        return conMoneyTentative;
    }

    public void setConMoneyTentative(BigDecimal conMoneyTentative) {
        this.conMoneyTentative = conMoneyTentative;
    }

    public BigDecimal getConProvisionalPe() {
        return conProvisionalPe;
    }

    public void setConProvisionalPe(BigDecimal conProvisionalPe) {
        this.conProvisionalPe = conProvisionalPe;
    }

    public BigDecimal getConProvisionalM() {
        return conProvisionalM;
    }

    public void setConProvisionalM(BigDecimal conProvisionalM) {
        this.conProvisionalM = conProvisionalM;
    }

    public Float getConVatRate() {
        return conVatRate;
    }

    public void setConVatRate(Float conVatRate) {
        this.conVatRate = conVatRate;
    }

    public Date getConDate() {
        return conDate;
    }

    public void setConDate(Date conDate) {
        this.conDate = conDate;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public String getConState() {
        return conState;
    }

    public void setConState(String conState) {
        this.conState = conState;
    }

    public String getConExecution() {
        return conExecution;
    }

    public void setConExecution(String conExecution) {
        this.conExecution = conExecution;
    }

    public String getConScan() {
        return conScan;
    }

    public void setConScan(String conScan) {
        this.conScan = conScan;
    }

    public Boolean getConScanUp() {
        return conScanUp;
    }

    public void setConScanUp(Boolean conScanUp) {
        this.conScanUp = conScanUp;
    }

    public String getConPb() {
        return conPb;
    }

    public void setConPb(String conPb) {
        this.conPb = conPb;
    }

    public String getConPbWay() {
        return conPbWay;
    }

    public void setConPbWay(String conPbWay) {
        this.conPbWay = conPbWay;
    }

    public Float getConPbRate() {
        return conPbRate;
    }

    public void setConPbRate(Float conPbRate) {
        this.conPbRate = conPbRate;
    }

    public String getConPbBeneficiaryId() {
        return conPbBeneficiaryId;
    }

    public void setConPbBeneficiaryId(String conPbBeneficiaryId) {
        this.conPbBeneficiaryId = conPbBeneficiaryId;
    }

    public Float getConAcPayRate() {
        return conAcPayRate;
    }

    public void setConAcPayRate(Float conAcPayRate) {
        this.conAcPayRate = conAcPayRate;
    }

    public BigDecimal getConAcMoney() {
        return conAcMoney;
    }

    public void setConAcMoney(BigDecimal conAcMoney) {
        this.conAcMoney = conAcMoney;
    }

    public String getConAcBackWay() {
        return conAcBackWay;
    }

    public void setConAcBackWay(String conAcBackWay) {
        this.conAcBackWay = conAcBackWay;
    }

    public Float getConAcMaterialRate() {
        return conAcMaterialRate;
    }

    public void setConAcMaterialRate(Float conAcMaterialRate) {
        this.conAcMaterialRate = conAcMaterialRate;
    }

    public BigDecimal getConAcBackStart() {
        return conAcBackStart;
    }

    public void setConAcBackStart(BigDecimal conAcBackStart) {
        this.conAcBackStart = conAcBackStart;
    }

    public BigDecimal getConAcBackEnd() {
        return conAcBackEnd;
    }

    public void setConAcBackEnd(BigDecimal conAcBackEnd) {
        this.conAcBackEnd = conAcBackEnd;
    }

    public Integer getConAcBackTimes() {
        return conAcBackTimes;
    }

    public void setConAcBackTimes(Integer conAcBackTimes) {
        this.conAcBackTimes = conAcBackTimes;
    }


    public Float getConAcBackRate() {
        return conAcBackRate;
    }

    public void setConAcBackRate(Float conAcBackRate) {
        this.conAcBackRate = conAcBackRate;
    }

    public Integer getConAcBackStartNu() {
        return conAcBackStartNu;
    }

    public void setConAcBackStartNu(Integer conAcBackStartNu) {
        this.conAcBackStartNu = conAcBackStartNu;
    }

    public Float getConRmRate() {
        return conRmRate;
    }

    public void setConRmRate(Float conRmRate) {
        this.conRmRate = conRmRate;
    }

    public BigDecimal getConRmMoney() {
        return conRmMoney;
    }

    public void setConRmMoney(BigDecimal conRmMoney) {
        this.conRmMoney = conRmMoney;
    }

    public String getConRmBackWay() {
        return conRmBackWay;
    }

    public void setConRmBackWay(String conRmBackWay) {
        this.conRmBackWay = conRmBackWay;
    }

    public BigDecimal getConRmBackStart() {
        return conRmBackStart;
    }

    public void setConRmBackStart(BigDecimal conRmBackStart) {
        this.conRmBackStart = conRmBackStart;
    }

    public BigDecimal getConRmBackEnd() {
        return conRmBackEnd;
    }

    public void setConRmBackEnd(BigDecimal conRmBackEnd) {
        this.conRmBackEnd = conRmBackEnd;
    }

    public Float getConRmBackRate() {
        return conRmBackRate;
    }

    public void setConRmBackRate(Float conRmBackRate) {
        this.conRmBackRate = conRmBackRate;
    }

    public Integer getConRmBackStartNu() {
        return conRmBackStartNu;
    }

    public void setConRmBackStartNu(Integer conRmBackStartNu) {
        this.conRmBackStartNu = conRmBackStartNu;
    }

    public Date getConRmBackDate() {
        return conRmBackDate;
    }

    public void setConRmBackDate(Date conRmBackDate) {
        this.conRmBackDate = conRmBackDate;
    }

    public BigDecimal getConScMoney() {
        return conScMoney;
    }

    public void setConScMoney(BigDecimal conScMoney) {
        this.conScMoney = conScMoney;
    }

    public String getConScBackWay() {
        return conScBackWay;
    }

    public void setConScBackWay(String conScBackWay) {
        this.conScBackWay = conScBackWay;
    }

    public BigDecimal getConScBackStart() {
        return conScBackStart;
    }

    public void setConScBackStart(BigDecimal conScBackStart) {
        this.conScBackStart = conScBackStart;
    }

    public BigDecimal getConScBackEnd() {
        return conScBackEnd;
    }

    public void setConScBackEnd(BigDecimal conScBackEnd) {
        this.conScBackEnd = conScBackEnd;
    }

    public Float getConScBackRate() {
        return conScBackRate;
    }

    public void setConScBackRate(Float conScBackRate) {
        this.conScBackRate = conScBackRate;
    }

    public Integer getConScBackStartNu() {
        return conScBackStartNu;
    }

    public void setConScBackStartNu(Integer conScBackStartNu) {
        this.conScBackStartNu = conScBackStartNu;
    }

    public Integer getConScBackTimes() {
        return conScBackTimes;
    }

    public void setConScBackTimes(Integer conScBackTimes) {
        this.conScBackTimes = conScBackTimes;
    }

    public String getConApg() {
        return conApg;
    }

    public void setConApg(String conApg) {
        this.conApg = conApg;
    }

    public String getConApgBeneficiaryId() {
        return conApgBeneficiaryId;
    }

    public void setConApgBeneficiaryId(String conApgBeneficiaryId) {
        this.conApgBeneficiaryId = conApgBeneficiaryId;
    }

    public Float getConApgRate() {
        return conApgRate;
    }

    public void setConApgRate(Float conApgRate) {
        this.conApgRate = conApgRate;
    }

    public BigDecimal getConApgMoney() {
        return conApgMoney;
    }

    public void setConApgMoney(BigDecimal conApgMoney) {
        this.conApgMoney = conApgMoney;
    }

    public String getConInvoiceObject() {
        return conInvoiceObject;
    }

    public void setConInvoiceObject(String conInvoiceObject) {
        this.conInvoiceObject = conInvoiceObject;
    }

    public String getConChangePayWay() {
        return conChangePayWay;
    }

    public void setConChangePayWay(String conChangePayWay) {
        this.conChangePayWay = conChangePayWay;
    }

    public Float getConChangePayRate() {
        return conChangePayRate;
    }

    public void setConChangePayRate(Float conChangePayRate) {
        this.conChangePayRate = conChangePayRate;
    }

    public String getConProjectOverview() {
        return conProjectOverview;
    }

    public void setConProjectOverview(String conProjectOverview) {
        this.conProjectOverview = conProjectOverview;
    }

    public String getConAwardClause() {
        return conAwardClause;
    }

    public void setConAwardClause(String conAwardClause) {
        this.conAwardClause = conAwardClause;
    }

    public String getConSpecialAgreement() {
        return conSpecialAgreement;
    }

    public void setConSpecialAgreement(String conSpecialAgreement) {
        this.conSpecialAgreement = conSpecialAgreement;
    }

    public String getConBudgetId() {
        return conBudgetId;
    }

    public void setConBudgetId(String conBudgetId) {
        this.conBudgetId = conBudgetId;
    }

    public String getConParentConId() {
        return conParentConId;
    }

    public void setConParentConId(String conParentConId) {
        this.conParentConId = conParentConId;
    }

    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(String conCategory) {
        this.conCategory = conCategory;
    }

    public String getConCreateUserId() {
        return conCreateUserId;
    }

    public void setConCreateUserId(String conCreateUserId) {
        this.conCreateUserId = conCreateUserId;
    }

    public String getConCreateUserName() {
        return conCreateUserName;
    }

    public void setConCreateUserName(String conCreateUserName) {
        this.conCreateUserName = conCreateUserName;
    }

    public Date getConCreateTime() {
        return conCreateTime;
    }

    public void setConCreateTime(Date conCreateTime) {
        this.conCreateTime = conCreateTime;
    }

    public Integer getConApprovalState() {
        return conApprovalState;
    }

    public void setConApprovalState(Integer conApprovalState) {
        this.conApprovalState = conApprovalState;
    }

    public Date getConApprovalTime() {
        return conApprovalTime;
    }

    public void setConApprovalTime(Date conApprovalTime) {
        this.conApprovalTime = conApprovalTime;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getConProjectName() {
        return conProjectName;
    }

    public void setConProjectName(String conProjectName) {
        this.conProjectName = conProjectName;
    }

    public String getConPartyAName() {
        return conPartyAName;
    }

    public void setConPartyAName(String conPartyAName) {
        this.conPartyAName = conPartyAName;
    }

    public String getConPartyAOtherName() {
        return conPartyAOtherName;
    }

    public void setConPartyAOtherName(String conPartyAOtherName) {
        this.conPartyAOtherName = conPartyAOtherName;
    }

    public String getConPartyB1Name() {
        return conPartyB1Name;
    }

    public void setConPartyB1Name(String conPartyB1Name) {
        this.conPartyB1Name = conPartyB1Name;
    }

    public String getConPartyB2Name() {
        return conPartyB2Name;
    }

    public void setConPartyB2Name(String conPartyB2Name) {
        this.conPartyB2Name = conPartyB2Name;
    }

    public String getConPartyB3Name() {
        return conPartyB3Name;
    }

    public void setConPartyB3Name(String conPartyB3Name) {
        this.conPartyB3Name = conPartyB3Name;
    }

    public String getConPartyB4Name() {
        return conPartyB4Name;
    }

    public void setConPartyB4Name(String conPartyB4Name) {
        this.conPartyB4Name = conPartyB4Name;
    }

    public String getConPartyB5Name() {
        return conPartyB5Name;
    }

    public void setConPartyB5Name(String conPartyB5Name) {
        this.conPartyB5Name = conPartyB5Name;
    }

    public String getConPartyCName() {
        return conPartyCName;
    }

    public void setConPartyCName(String conPartyCName) {
        this.conPartyCName = conPartyCName;
    }

    public String getConPartyDName() {
        return conPartyDName;
    }

    public void setConPartyDName(String conPartyDName) {
        this.conPartyDName = conPartyDName;
    }

    public String getConMasterConName() {
        return conMasterConName;
    }

    public void setConMasterConName(String conMasterConName) {
        this.conMasterConName = conMasterConName;
    }

    public String getConParentConName() {
        return conParentConName;
    }

    public void setConParentConName(String conParentConName) {
        this.conParentConName = conParentConName;
    }

    public String getConTransportMode() {
        return conTransportMode;
    }

    public void setConTransportMode(String conTransportMode) {
        this.conTransportMode = conTransportMode;
    }

    public Date getConDeliveryDate() {
        return conDeliveryDate;
    }

    public void setConDeliveryDate(Date conDeliveryDate) {
        this.conDeliveryDate = conDeliveryDate;
    }

    public Integer getConDeliveryPeriod() {
        return conDeliveryPeriod;
    }

    public void setConDeliveryPeriod(Integer conDeliveryPeriod) {
        this.conDeliveryPeriod = conDeliveryPeriod;
    }

    public Integer getConInstallationPeriod() {
        return conInstallationPeriod;
    }

    public void setConInstallationPeriod(Integer conInstallationPeriod) {
        this.conInstallationPeriod = conInstallationPeriod;
    }

    public BigDecimal getConPurchasePrice() {
        return conPurchasePrice;
    }

    public void setConPurchasePrice(BigDecimal conPurchasePrice) {
        this.conPurchasePrice = conPurchasePrice;
    }

    public BigDecimal getConInstallationCost() {
        return conInstallationCost;
    }

    public void setConInstallationCost(BigDecimal conInstallationCost) {
        this.conInstallationCost = conInstallationCost;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }


    public String getConBiddingType() {
        return conBiddingType;
    }

    public void setConBiddingType(String conBiddingType) {
        this.conBiddingType = conBiddingType;
    }

    public String getConPartyBLegal() {
        return conPartyBLegal;
    }

    public void setConPartyBLegal(String conPartyBLegal) {
        this.conPartyBLegal = conPartyBLegal;
    }

    public String getConPartyBPoxy() {
        return conPartyBPoxy;
    }

    public void setConPartyBPoxy(String conPartyBPoxy) {
        this.conPartyBPoxy = conPartyBPoxy;
    }

    public String getConPartyBPoxyNum() {
        return conPartyBPoxyNum;
    }

    public void setConPartyBPoxyNum(String conPartyBPoxyNum) {
        this.conPartyBPoxyNum = conPartyBPoxyNum;
    }

    public String getConCaName() {
        return conCaName;
    }

    public void setConCaName(String conCaName) {
        this.conCaName = conCaName;
    }

    public String getConCa() {
        return conCa;
    }

    public void setConCa(String conCa) {
        this.conCa = conCa;
    }

    public String getConCaBank() {
        return conCaBank;
    }

    public void setConCaBank(String conCaBank) {
        this.conCaBank = conCaBank;
    }

    public String getConCaBankName() {
        return conCaBankName;
    }

    public void setConCaBankName(String conCaBankName) {
        this.conCaBankName = conCaBankName;
    }

    public String getConCaBankNum() {
        return conCaBankNum;
    }

    public void setConCaBankNum(String conCaBankNum) {
        this.conCaBankNum = conCaBankNum;
    }

    public String getConWaName() {
        return conWaName;
    }

    public void setConWaName(String conWaName) {
        this.conWaName = conWaName;
    }

    public String getConWa() {
        return conWa;
    }

    public void setConWa(String conWa) {
        this.conWa = conWa;
    }

    public String getConWaBank() {
        return conWaBank;
    }

    public void setConWaBank(String conWaBank) {
        this.conWaBank = conWaBank;
    }

    public String getConWaBankName() {
        return conWaBankName;
    }

    public void setConWaBankName(String conWaBankName) {
        this.conWaBankName = conWaBankName;
    }

    public String getConWaBankNum() {
        return conWaBankNum;
    }

    public void setConWaBankNum(String conWaBankNum) {
        this.conWaBankNum = conWaBankNum;
    }


    public String getConAcClientId() {
        return conAcClientId;
    }

    public void setConAcClientId(String conAcClientId) {
        this.conAcClientId = conAcClientId;
    }

    public String getConAcClientName() {
        return conAcClientName;
    }

    public void setConAcClientName(String conAcClientName) {
        this.conAcClientName = conAcClientName;
    }

    public String getConAcBidSubjectId() {
        return conAcBidSubjectId;
    }

    public void setConAcBidSubjectId(String conAcBidSubjectId) {
        this.conAcBidSubjectId = conAcBidSubjectId;
    }

    public String getConAcBidSubjectName() {
        return conAcBidSubjectName;
    }

    public void setConAcBidSubjectName(String conAcBidSubjectName) {
        this.conAcBidSubjectName = conAcBidSubjectName;
    }

    public String getConAcImplementId() {
        return conAcImplementId;
    }

    public void setConAcImplementId(String conAcImplementId) {
        this.conAcImplementId = conAcImplementId;
    }

    public String getConAcImplementName() {
        return conAcImplementName;
    }

    public void setConAcImplementName(String conAcImplementName) {
        this.conAcImplementName = conAcImplementName;
    }

    public String getConAcProxy() {
        return conAcProxy;
    }

    public void setConAcProxy(String conAcProxy) {
        this.conAcProxy = conAcProxy;
    }

    public Integer getConIsHistory() {
        return conIsHistory;
    }

    public void setConIsHistory(Integer conIsHistory) {
        this.conIsHistory = conIsHistory;
    }

    public Integer getConYear() {
        return conYear;
    }

    public void setConYear(Integer conYear) {
        this.conYear = conYear;
    }

    public BigDecimal getConPbMoney() {
        return ConPbMoney;
    }

    public void setConPbMoney(BigDecimal conPbMoney) {
        ConPbMoney = conPbMoney;
    }

    public String getConPayClause() {
        return conPayClause;
    }

    public void setConPayClause(String conPayClause) {
        this.conPayClause = conPayClause;
    }

    public String getConAcceptanceClause() {
        return conAcceptanceClause;
    }

    public void setConAcceptanceClause(String conAcceptanceClause) {
        this.conAcceptanceClause = conAcceptanceClause;
    }

    public String getConDefaultClause() {
        return conDefaultClause;
    }

    public void setConDefaultClause(String conDefaultClause) {
        this.conDefaultClause = conDefaultClause;
    }

    public String getConBasis() {
        return conBasis;
    }

    public void setConBasis(String conBasis) {
        this.conBasis = conBasis;
    }

    public String getConOtherNote() {
        return conOtherNote;
    }

    public void setConOtherNote(String conOtherNote) {
        this.conOtherNote = conOtherNote;
    }

    public String getConBasisFile() {
        return conBasisFile;
    }

    public void setConBasisFile(String conBasisFile) {
        this.conBasisFile = conBasisFile;
    }

    public String getConPbAdded() {
        return conPbAdded;
    }

    public void setConPbAdded(String conPbAdded) {
        this.conPbAdded = conPbAdded;
    }

    public String getConAcAdded() {
        return conAcAdded;
    }

    public void setConAcAdded(String conAcAdded) {
        this.conAcAdded = conAcAdded;
    }

    public String getConRmAdded() {
        return conRmAdded;
    }

    public void setConRmAdded(String conRmAdded) {
        this.conRmAdded = conRmAdded;
    }

    public String getConScAdded() {
        return conScAdded;
    }

    public void setConScAdded(String conScAdded) {
        this.conScAdded = conScAdded;
    }

    public String getConRelieveConId() {
        return conRelieveConId;
    }

    public void setConRelieveConId(String conRelieveConId) {
        this.conRelieveConId = conRelieveConId;
    }

    public String getConRelieveReason() {
        return conRelieveReason;
    }

    public void setConRelieveReason(String conRelieveReason) {
        this.conRelieveReason = conRelieveReason;
    }

    public String getConRelieveConName() {
        return conRelieveConName;
    }

    public void setConRelieveConName(String conRelieveConName) {
        this.conRelieveConName = conRelieveConName;
    }

    public String getConRelieveConCode() {
        return conRelieveConCode;
    }

    public void setConRelieveConCode(String conRelieveConCode) {
        this.conRelieveConCode = conRelieveConCode;
    }

    public String getConPartyBPmTel() {
        return conPartyBPmTel;
    }

    public void setConPartyBPmTel(String conPartyBPmTel) {
        this.conPartyBPmTel = conPartyBPmTel;
    }


    public String getConRemark() {
        return conRemark;
    }

    public void setConRemark(String conRemark) {
        this.conRemark = conRemark;
    }

    public String getConMoneyType() {
        return conMoneyType;
    }

    public void setConMoneyType(String conMoneyType) {
        this.conMoneyType = conMoneyType;
    }

    public String getConMoneyFlow() {
        return conMoneyFlow;
    }

    public void setConMoneyFlow(String conMoneyFlow) {
        this.conMoneyFlow = conMoneyFlow;
    }

    public Float getConRate() {
        return conRate;
    }

    public void setConRate(Float conRate) {
        this.conRate = conRate;
    }

    public Float getConLowerRate() {
        return conLowerRate;
    }

    public void setConLowerRate(Float conLowerRate) {
        this.conLowerRate = conLowerRate;
    }

    public Date getConValidityStart() {
        return conValidityStart;
    }

    public void setConValidityStart(Date conValidityStart) {
        this.conValidityStart = conValidityStart;
    }

    public Date getConValidityEnd() {
        return conValidityEnd;
    }

    public void setConValidityEnd(Date conValidityEnd) {
        this.conValidityEnd = conValidityEnd;
    }

    public Integer getConDefectsLiability() {
        return conDefectsLiability;
    }

    public void setConDefectsLiability(Integer conDefectsLiability) {
        this.conDefectsLiability = conDefectsLiability;
    }

    public Boolean getConIsBidding() {
        return conIsBidding;
    }

    public void setConIsBidding(Boolean conIsBidding) {
        this.conIsBidding = conIsBidding;
    }

    public String getConSsName() {
        return conSsName;
    }

    public void setConSsName(String conSsName) {
        this.conSsName = conSsName;
    }

    public String getConSs() {
        return conSs;
    }

    public void setConSs(String conSs) {
        this.conSs = conSs;
    }

    public String getConSsBank() {
        return conSsBank;
    }

    public void setConSsBank(String conSsBank) {
        this.conSsBank = conSsBank;
    }

    public String getConSsBankName() {
        return conSsBankName;
    }

    public void setConSsBankName(String conSsBankName) {
        this.conSsBankName = conSsBankName;
    }

    public String getConSsBankNum() {
        return conSsBankNum;
    }

    public void setConSsBankNum(String conSsBankNum) {
        this.conSsBankNum = conSsBankNum;
    }

    public String getConPricingMethod() {
        return conPricingMethod;
    }

    public void setConPricingMethod(String conPricingMethod) {
        this.conPricingMethod = conPricingMethod;
    }

    public String getConOtherValuation() {
        return conOtherValuation;
    }

    public void setConOtherValuation(String conOtherValuation) {
        this.conOtherValuation = conOtherValuation;
    }

    public String getConIsTemplate() {
        return conIsTemplate;
    }

    public void setConIsTemplate(String conIsTemplate) {
        this.conIsTemplate = conIsTemplate;
    }

    public String getConTemplateId() {
        return conTemplateId;
    }

    public void setConTemplateId(String conTemplateId) {
        this.conTemplateId = conTemplateId;
    }

    public String getConTemplateVersion() {
        return conTemplateVersion;
    }

    public void setConTemplateVersion(String conTemplateVersion) {
        this.conTemplateVersion = conTemplateVersion;
    }

    public String getConProfessionalType() {
        return conProfessionalType;
    }

    public void setConProfessionalType(String conProfessionalType) {
        this.conProfessionalType = conProfessionalType;
    }

    public Date getConUsingDate() {
        return conUsingDate;
    }

    public void setConUsingDate(Date conUsingDate) {
        this.conUsingDate = conUsingDate;
    }

    public Integer getConUsingCopies() {
        return conUsingCopies;
    }

    public void setConUsingCopies(Integer conUsingCopies) {
        this.conUsingCopies = conUsingCopies;
    }

    public String getConStartsTo() {
        return conStartsTo;
    }

    public void setConStartsTo(String conStartsTo) {
        this.conStartsTo = conStartsTo;
    }

    public String getConUsingType() {
        return conUsingType;
    }

    public void setConUsingType(String conUsingType) {
        this.conUsingType = conUsingType;
    }

    public String getConChangeClause() {
        return conChangeClause;
    }

    public void setConChangeClause(String conChangeClause) {
        this.conChangeClause = conChangeClause;
    }

    public String getConSettlement() {
        return conSettlement;
    }

    public void setConSettlement(String conSettlement) {
        this.conSettlement = conSettlement;
    }

    public BigDecimal getConMainAmtTotal() {
        return conMainAmtTotal;
    }

    public void setConMainAmtTotal(BigDecimal conMainAmtTotal) {
        this.conMainAmtTotal = conMainAmtTotal;
    }

    public Date getConBasisTime() {
        return conBasisTime;
    }

    public void setConBasisTime(Date conBasisTime) {
        this.conBasisTime = conBasisTime;
    }

    public BigDecimal getConTimeConsuming() {
        return conTimeConsuming;
    }

    public void setConTimeConsuming(BigDecimal conTimeConsuming) {
        this.conTimeConsuming = conTimeConsuming;
    }

    public BigDecimal getConProgressDeadRatio() {
        return conProgressDeadRatio;
    }

    public void setConProgressDeadRatio(BigDecimal conProgressDeadRatio) {
        this.conProgressDeadRatio = conProgressDeadRatio;
    }

    public BigDecimal getConSettlementRatio() {
        return conSettlementRatio;
    }

    public void setConSettlementRatio(BigDecimal conSettlementRatio) {
        this.conSettlementRatio = conSettlementRatio;
    }

    public String getConDepartment() {
        return conDepartment;
    }

    public void setConDepartment(String conDepartment) {
        this.conDepartment = conDepartment;
    }

    public String getConDepName() {
        return conDepName;
    }

    public void setConDepName(String conDepName) {
        this.conDepName = conDepName;
    }

    public String getConEvaluateId() {
        return conEvaluateId;
    }

    public void setConEvaluateId(String conEvaluateId) {
        this.conEvaluateId = conEvaluateId;
    }

    public String getConEvaluateName() {
        return conEvaluateName;
    }

    public void setConEvaluateName(String conEvaluateName) {
        this.conEvaluateName = conEvaluateName;
    }

    public String getConEvaluateCode() {
        return conEvaluateCode;
    }

    public void setConEvaluateCode(String conEvaluateCode) {
        this.conEvaluateCode = conEvaluateCode;
    }

    public int getConEvaluateCount() {
        return conEvaluateCount;
    }

    public void setConEvaluateCount(int conEvaluateCount) {
        this.conEvaluateCount = conEvaluateCount;
    }

    public BigDecimal getConEvaluateAmt() {
        return conEvaluateAmt;
    }

    public void setConEvaluateAmt(BigDecimal conEvaluateAmt) {
        this.conEvaluateAmt = conEvaluateAmt;
    }

    public String getConEvaluateBName() {
        return conEvaluateBName;
    }

    public void setConEvaluateBName(String conEvaluateBName) {
        this.conEvaluateBName = conEvaluateBName;
    }

    public String getConEvaluateAName() {
        return conEvaluateAName;
    }

    public void setConEvaluateAName(String conEvaluateAName) {
        this.conEvaluateAName = conEvaluateAName;
    }

    public Boolean getConIsSupply() {
        return conIsSupply;
    }

    public void setConIsSupply(Boolean conIsSupply) {
        this.conIsSupply = conIsSupply;
    }

    public String getConPurchaseType() {
        return conPurchaseType;
    }

    public void setConPurchaseType(String conPurchaseType) {
        this.conPurchaseType = conPurchaseType;
    }

    public String getConPartyA1Id() {
        return conPartyA1Id;
    }

    public void setConPartyA1Id(String conPartyA1Id) {
        this.conPartyA1Id = conPartyA1Id;
    }

    public String getConPartyA2Id() {
        return conPartyA2Id;
    }

    public void setConPartyA2Id(String conPartyA2Id) {
        this.conPartyA2Id = conPartyA2Id;
    }

    public String getConPartyA3Id() {
        return conPartyA3Id;
    }

    public void setConPartyA3Id(String conPartyA3Id) {
        this.conPartyA3Id = conPartyA3Id;
    }

    public String getConPartyA4Id() {
        return conPartyA4Id;
    }

    public void setConPartyA4Id(String conPartyA4Id) {
        this.conPartyA4Id = conPartyA4Id;
    }

    public String getConPartyA5Id() {
        return conPartyA5Id;
    }

    public void setConPartyA5Id(String conPartyA5Id) {
        this.conPartyA5Id = conPartyA5Id;
    }

    public String getConAffiliationUnit() {
        return conAffiliationUnit;
    }

    public void setConAffiliationUnit(String conAffiliationUnit) {
        this.conAffiliationUnit = conAffiliationUnit;
    }

    public String getConSerialNumber() {
        return conSerialNumber;
    }

    public void setConSerialNumber(String conSerialNumber) {
        this.conSerialNumber = conSerialNumber;
    }

    public Boolean getConOpenBidding() {
        return conOpenBidding;
    }

    public void setConOpenBidding(Boolean conOpenBidding) {
        this.conOpenBidding = conOpenBidding;
    }

    public String getConPayCategory() {
        return conPayCategory;
    }

    public void setConPayCategory(String conPayCategory) {
        this.conPayCategory = conPayCategory;
    }

    public String getConOriginalCode() {
        return conOriginalCode;
    }

    public void setConOriginalCode(String conOriginalCode) {
        this.conOriginalCode = conOriginalCode;
    }

    public String getConTerm() {
        return conTerm;
    }

    public void setConTerm(String conTerm) {
        this.conTerm = conTerm;
    }

    public String getConWarrantyPeriod() {
        return conWarrantyPeriod;
    }

    public void setConWarrantyPeriod(String conWarrantyPeriod) {
        this.conWarrantyPeriod = conWarrantyPeriod;
    }

    public String getConEnterpriseType() {
        return conEnterpriseType;
    }

    public void setConEnterpriseType(String conEnterpriseType) {
        this.conEnterpriseType = conEnterpriseType;
    }

    public Boolean getConUseTemp() {
        return conUseTemp;
    }

    public void setConUseTemp(Boolean conUseTemp) {
        this.conUseTemp = conUseTemp;
    }

    public BigDecimal getConBudgetRate() {
        return conBudgetRate;
    }

    public void setConBudgetRate(BigDecimal conBudgetRate) {
        this.conBudgetRate = conBudgetRate;
    }

    public BigDecimal getConDeclareMoney() {
        return conDeclareMoney;
    }

    public void setConDeclareMoney(BigDecimal conDeclareMoney) {
        this.conDeclareMoney = conDeclareMoney;
    }

    public String getConBudgetFile() {
        return conBudgetFile;
    }

    public void setConBudgetFile(String conBudgetFile) {
        this.conBudgetFile = conBudgetFile;
    }

    public String getConBudgetTxt() {
        return conBudgetTxt;
    }

    public void setConBudgetTxt(String conBudgetTxt) {
        this.conBudgetTxt = conBudgetTxt;
    }

    public Boolean getConInvolveFunds() {
        return conInvolveFunds;
    }

    public void setConInvolveFunds(Boolean conInvolveFunds) {
        this.conInvolveFunds = conInvolveFunds;
    }

    public String getConPartyA1Name() {
        return conPartyA1Name;
    }

    public void setConPartyA1Name(String conPartyA1Name) {
        this.conPartyA1Name = conPartyA1Name;
    }

    public String getConPartyA2Name() {
        return conPartyA2Name;
    }

    public void setConPartyA2Name(String conPartyA2Name) {
        this.conPartyA2Name = conPartyA2Name;
    }

    public String getConPartyA3Name() {
        return conPartyA3Name;
    }

    public void setConPartyA3Name(String conPartyA3Name) {
        this.conPartyA3Name = conPartyA3Name;
    }

    public String getConPartyA4Name() {
        return conPartyA4Name;
    }

    public void setConPartyA4Name(String conPartyA4Name) {
        this.conPartyA4Name = conPartyA4Name;
    }

    public String getConPartyA5Name() {
        return conPartyA5Name;
    }

    public void setConPartyA5Name(String conPartyA5Name) {
        this.conPartyA5Name = conPartyA5Name;
    }

    public String getConSupplyTxt() {
        return conSupplyTxt;
    }

    public void setConSupplyTxt(String conSupplyTxt) {
        this.conSupplyTxt = conSupplyTxt;
    }

    public BigDecimal getConMasterAmt() {
        return conMasterAmt;
    }

    public void setConMasterAmt(BigDecimal conMasterAmt) {
        this.conMasterAmt = conMasterAmt;
    }

    public int getConMasterCount() {
        return conMasterCount;
    }

    public void setConMasterCount(int conMasterCount) {
        this.conMasterCount = conMasterCount;
    }

    public BigDecimal getConEvaluatePeAmt() {
        return conEvaluatePeAmt;
    }

    public void setConEvaluatePeAmt(BigDecimal conEvaluatePeAmt) {
        this.conEvaluatePeAmt = conEvaluatePeAmt;
    }

    public BigDecimal getConEvaluateMoneyTotal() {
        return conEvaluateMoneyTotal;
    }

    public void setConEvaluateMoneyTotal(BigDecimal conEvaluateMoneyTotal) {
        this.conEvaluateMoneyTotal = conEvaluateMoneyTotal;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getConPartyAAbbName() {
        return conPartyAAbbName;
    }

    public void setConPartyAAbbName(String conPartyAAbbName) {
        this.conPartyAAbbName = conPartyAAbbName;
    }

    public String getConPartyAOtherAbbName() {
        return conPartyAOtherAbbName;
    }

    public void setConPartyAOtherAbbName(String conPartyAOtherAbbName) {
        this.conPartyAOtherAbbName = conPartyAOtherAbbName;
    }

    public String getConPartyB1AbbName() {
        return conPartyB1AbbName;
    }

    public void setConPartyB1AbbName(String conPartyB1AbbName) {
        this.conPartyB1AbbName = conPartyB1AbbName;
    }

    public String getConPartyB2AbbName() {
        return conPartyB2AbbName;
    }

    public void setConPartyB2AbbName(String conPartyB2AbbName) {
        this.conPartyB2AbbName = conPartyB2AbbName;
    }

    public String getConPartyB3AbbName() {
        return conPartyB3AbbName;
    }

    public void setConPartyB3AbbName(String conPartyB3AbbName) {
        this.conPartyB3AbbName = conPartyB3AbbName;
    }

    public String getConPartyB4AbbName() {
        return conPartyB4AbbName;
    }

    public void setConPartyB4AbbName(String conPartyB4AbbName) {
        this.conPartyB4AbbName = conPartyB4AbbName;
    }

    public String getConPartyB5AbbName() {
        return conPartyB5AbbName;
    }

    public void setConPartyB5AbbName(String conPartyB5AbbName) {
        this.conPartyB5AbbName = conPartyB5AbbName;
    }

    public String getConPartyCAbbName() {
        return conPartyCAbbName;
    }

    public void setConPartyCAbbName(String conPartyCAbbName) {
        this.conPartyCAbbName = conPartyCAbbName;
    }

    public String getConPartyDAbbName() {
        return conPartyDAbbName;
    }

    public void setConPartyDAbbName(String conPartyDAbbName) {
        this.conPartyDAbbName = conPartyDAbbName;
    }

    public String getConAcClientAbbName() {
        return conAcClientAbbName;
    }

    public void setConAcClientAbbName(String conAcClientAbbName) {
        this.conAcClientAbbName = conAcClientAbbName;
    }

    public String getConAcBidSubjectAbbName() {
        return conAcBidSubjectAbbName;
    }

    public void setConAcBidSubjectAbbName(String conAcBidSubjectAbbName) {
        this.conAcBidSubjectAbbName = conAcBidSubjectAbbName;
    }

    public String getConAcImplementAbbName() {
        return conAcImplementAbbName;
    }

    public void setConAcImplementAbbName(String conAcImplementAbbName) {
        this.conAcImplementAbbName = conAcImplementAbbName;
    }

    public String getConPbBeneficiaryName() {
        return conPbBeneficiaryName;
    }

    public void setConPbBeneficiaryName(String conPbBeneficiaryName) {
        this.conPbBeneficiaryName = conPbBeneficiaryName;
    }

    public BigDecimal getSaApprovedAmtSum() {
        return saApprovedAmtSum;
    }

    public void setSaApprovedAmtSum(BigDecimal saApprovedAmtSum) {
        this.saApprovedAmtSum = saApprovedAmtSum;
    }

    public List<ConPayeeDtlModel> getConPayeeDtls() {
        return conPayeeDtls;
    }

    public void setConPayeeDtls(List<ConPayeeDtlModel> conPayeeDtls) {
        this.conPayeeDtls = conPayeeDtls;
    }

    public List<ConContractPayPlanModel> getPayPlanModels() {
        return payPlanModels;
    }

    public void setPayPlanModels(List<ConContractPayPlanModel> payPlanModels) {
        this.payPlanModels = payPlanModels;
    }

    public void setConBidAmt(BigDecimal conBidAmt) {
        this.conBidAmt = conBidAmt;
    }

    public void setConBrightAmt(BigDecimal conBrightAmt) {
        this.conBrightAmt = conBrightAmt;
    }

    public String getConJlId() {
        return conJlId;
    }

    public void setConJlId(String conJlId) {
        this.conJlId = conJlId;
    }

    public String getConJlName() {
        return conJlName;
    }

    public void setConJlName(String conJlName) {
        this.conJlName = conJlName;
    }

    public Date getConConfirmDate() {
        return conConfirmDate;
    }

    public void setConConfirmDate(Date conConfirmDate) {
        this.conConfirmDate = conConfirmDate;
    }

    public List<conConsortiumModel> getConConsortiumDtl() {
        return conConsortiumDtl;
    }

    public void setConConsortiumDtl(List<conConsortiumModel> conConsortiumDtl) {
        this.conConsortiumDtl = conConsortiumDtl;
    }

    public List<ConPayeeDtlModel> getPayeeDtlModels() {
        return payeeDtlModels;
    }

    public void setPayeeDtlModels(List<ConPayeeDtlModel> payeeDtlModels) {
        this.payeeDtlModels = payeeDtlModels;
    }

    public Integer getContractYear() {
        return contractYear;
    }

    public void setContractYear(Integer contractYear) {
        this.contractYear = contractYear;
    }
}
