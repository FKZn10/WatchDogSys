package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 标段信息变更
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 16:11
 */
@Data
@TableName("Sec_Info_Change")
public class SecInfoChange {

    @TableId(type = IdType.INPUT)
    private String sicId;
    //标段id
    private String secId;
    //招标计划变更表id
    private String bpcId;
    //标段名称
    private String secName;
    //标段编号
    private String secCode;
    //上级标段
    private String secParentId;
    //年度
    private Integer secYear;
    //招标方式
    private String secBiddingType;
    //合同类型
    private String secConType;
    //标段类型
    private String secType;
    //估算金额
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal secMoney;
    //开始时间
    private Date secStartTime;
    //结束时间
    private Date secEndTime;
    private String secRemark;
    private Date secFlowTime;
    private Integer secFlowState;
    private String secCreateUid;
    private String secCreateUname;
    private Date secCreateTime;
    //所属项目ID
    private String secPid;
    //招标人Id
    private String secTenderee;
    //工程特征
    private String secFeatures;
    //建设地点
    private String secPlace;
    //建设规模
    private String secScale;
    //费率
    private BigDecimal secRate;
    //其他计价
    private String secOtherValuation;
    //定标方式
    private String secDeterminType;
    //组织形式
    private String secOrganizeForm;
    //拟交易场所
    private String secTradingPlace;
    //招标周期
    private Integer secBiddingCycle;
    //批复文件标题
    private String secApprovalTitle;
    //批复编号
    private String secApprovalNum;
    //批复时间
    private Date secApprovalTime;
    //批复文件
    private String secApprovalFile;
    //计价方式
    private String secPricingMethod;
    //变更类型标识
    private String secChangeType;
    //采购类型
    private String secPurchaseType;
    //评标方法
    private String secEvalMethod;
    //是否评定分离
    private Boolean secEvaluateSeparation;
    //工期要求
    private Integer secDuration;
    //资格审查方式
    private String secQualiReview;
    //投资人资格要求
    private String secInvestorsQuali;
    //计价合计汇总附件(工程总价、单位工程、措施费等清单)
    private String secValuationFile;
    //开标时间
    private Date secBidTime;
    //开标地点
    private String secLoaction;
    //代理单位
    private String secAgency;
    //发包部门
    private String secContractingDepartment;
    //计划开标时间
    private Date secPlanOpenTime;

    //发包类别
    private String secContractingType;
    private String teId;

    //是否允许联合体投标
    private Boolean secConsortium;
    //是否允许分包
    private Boolean secSubpackage;
    //是否适宜中小企业提供
    private Boolean secEnterprise;
    //是否进口
    private Boolean secImport;
    //项目性质
    private String secProjectNature;
    //经费来源
    private String secSourceFund;
    //经费到账情况
    private String secFundsReceived;
    //申购人联系方式
    private String secSubscriber;
    //联系人(选人)
    private String secContacts;
    //联系人手机号
    private String secContactsPhone;
    //联系人座机
    private String secContactsTel;
    //联系人邮箱
    private String secContactsEmail;
    //用户授权代表
    private String secDelegate;
    //期望挂网天数
    private BigDecimal secInternetDay;
    //公布最高限价
    private Boolean secAnnounce;
    //签约时间要求
    private BigDecimal secSigningDay;
    //付款方式
    private String secPayMethod;
    //期望到货时间要求
    private BigDecimal secArrivalDay;
    //收货地点
    private String secReceAddr;
    //推荐供应商
    private String secSupplier;
    //推荐供应商联系人
    private String secSupplierName;
    //推荐供应商手机号
    private String secSupplierPhone;
    //推荐供应商固话
    private String secSupplierTel;
    //推荐供应商邮箱
    private String secSupplierEmail;
    //推荐供应商地址
    private String secSupplierAddr;
    //施工地点
    private String secConstrAddr;
    //施工周期
    private BigDecimal secConstrDay;
    //采购目录
    private String secCatalog;
    //项目类别
    private String secProCategory;
    //论证报告
    private String secProofFile;
    //空白合同文本
    private String secBlankFile;
    //其他相关附件资料
    private String secOtherFile;
    //其他相关图片资料
    private String secOtherImg;
    //期望到货(签约前|成交后)
    private String secArrival;
    //是否单一来源
    private Boolean secSingleSource;
    //招标内容
    private String secContent;
    //估算价
    private BigDecimal secGsAmt;
    //招标人名称
    private String secTendereeName;
    //代理机构名称
    private String secAgencyCname;
    //项目归属
    private String secProOwn;
    //申购入口末级编码
    private String secEntryCode;
    //申购入口全路径名称
    private String secEntryName;
    //采购目录名称
    private String secCatalogName;

    /**
     * 合同估算起始比例
     */
    private Double contractEvaluateAmountRateStart;

    /**
     * 合同估算截至比例
     */
    private Double contractEvaluateAmountRateEnd;

    /**
     * 编标时间
     */
    private Date makeBiddingTime;

    /**
     * 开标时间
     */
    private Date startBiddingTime;

    /**
     * 拟开标场所
     */
    private String startBiddingPlace;

    /**
     * 服务期
     */
    private String servicePeriod;

    /**
     * 采购方式说明
     */
    private String procurementDes;

    /**
     * 采购用途
     */
    private String procurementPurpose;

    /**
     * 是否列入年度采购计划
     */
    private String annualProcurementPlan;

    /**
     * 意向公开日期
     */
    private String intendedPublicDate;

    /**
     * 计划采购时间
     */
    private Date plannedProcurementTime;

    /**
     * 预算确定方式
     */
    private String budgetDeterminationMethod;

    /**
     * 资金落实情况
     */
    private String fundingImplementationStatus;

    /**
     * 采购需求
     */
    private Date procurementRequirements;

    /**
     * 重点项目自查
     */
    private String projectSelfInspection;

    /**
     * 项目补充说明
     */
    private String projectExplanation;

    /**
     * 采购需求一览表  原先存附件批次id 现：表示是否有这个附件1：有  0：没有 。下同
     */
    private String procurementListDoc;

    /**
     * 采购计划调整（追加）申报表
     */
    private String procurementPlanDoc;

    /**
     * 免意向公开说明
     */
    private String noIntentionDiscloseDoc;

    /**
     * 预算确定支持材料
     */
    private String supportMaterialsDoc;

    /**
     * 预算支出审批表
     */
    private String expenditureApprovalDoc;

    /**
     * 需求调查
     */
    private String demandSurveyDoc;

    /**
     * 其他附件
     */
    private String otherDoc;

    /**
     * 标段划分
     */
    private String secDivision;

    //计划进场时间
    private Date planEntryTime;

    //合同概算价
    private BigDecimal budgetMoney;

    //投资估算拆分表id
    private String ieId;

    //投资估算拆分详细表id
    private String ieDetailId;

    //投资概算拆分表id
    private String ibId;

    //投资概算拆分详细表id
    private String ibDetailId;

    //投资估算/概算 拆分类目
    private String isbdType;

    //投资估算/概算拆分排序
    private String isbdSort;

    /**
     * 序号
     */
    private String secOrderNumber;

    /**
     * 标段计划类型
     */
    private String secCostType;

    /**
     * 标段分类
     */
    private String secSectionClassify;

    /**
     * 排序
     */
    private String secSort;

    /**
     * 变更阶段
     */
    private String secChangePhase;

    /**
     * 变更原因
     */
    private String secInfoChangeReason;

    private String tenderName;

    private String secSubjectNameType;

    /**
     * 采购编号
     */
    private String purchaseNumber;

    /**
     * 标段类型（,分隔）
     */
    private String secTypeStr;
}
