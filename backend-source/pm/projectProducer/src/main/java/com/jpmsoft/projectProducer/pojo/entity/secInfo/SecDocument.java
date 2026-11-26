package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标文件
 */
@Data
@TableName("Sec_Document")
public class SecDocument {
    @TableId(type = IdType.INPUT)
    private String dId;
    private String secId;
    //招标范围
    private String dScope;
    //招标金额(招标控制价)
    private BigDecimal dAmount;
    //定标方式（	JPMsoft_DBFS）
    private String dDeterminType;
    //采用的计价模式（JPMsoft_JJMS）
    private String dPriceMode;
    //投标报价上限
    private BigDecimal dUpperLimit;
    //招标文件发布版
    private String dReleaseVersion;
    private String dCreateUid;
    private String dCreateUname;
    private Date dCreateTime;
    private Integer dState;
    private Date dStateTime;
    private String dRemark;
    //招标文件标段名称
    private String dFileName;
    //报审类型
    private String dApprovalType;
    //招标类别
    private String dCategory;
    //工期(天)
    private Integer dDuration;
    //招标形式
    private String dFormalization;
    //评标方法
    private String dFunction;
    //评标方法  其他
    private String dFunctionText;
    //是否评定分离
    private String dIsEvaluateSeparation;
    //开标时间
    private Date dBidTime;
    //开标地点
    private String dLocation;
    //招标人
    private String dPerson;
    //全过程咨询/项目管理单位
    private String dManageConsul;
    //招标代理单位
    private String dAgentUnit;
    //招标文件编号
    private String dFileCode;
    //批复文件标题
    private String dApplyTilte;
    //批复编号
    private String dApplyCode;
    //批复时间
    private Date dApplyDate;
    //批复文件
    private String dApplyFile;
    //其他附件上传
    private String dOtherFile;
    //工程总价·单位工程·措施费等清单计价合计汇总
    private String dListFile;
    //最新开标时间
    private Date dNewBidTime;
    //最新开标地点
    private String dNewLocation;
    //工程规模及特征
    private String dFeatures;
    //工期要求
    private String dExecutiveStandard;
    //投标人项目经理
    private String dBidderManager;
    //履约担保形式
    private String dPerformanceGuaranteeType;
    //履约担保金
    private BigDecimal dPerformanceBond;
    //资金来源
    private String dSourceFunds;
    //费率
    private BigDecimal dRate;
    //其他计价
    private String dOtherValuation;
    //资格审查方式
    private String dQualiReview;
    //投资人资格要求
    private String dInvestorsQuali;
    //招标方式
    private String dBiddingMethod;
    //履约保证金比例
    private Float dPerfBondRatio;
    //单位资质
    private String dQualification;
    //业绩
    private String dAchievement;
    //直接委托结果
    private String dEntrustedResult;
    //委托单位
    private String dEntrustedCpy;
    //委托金额
    private BigDecimal dEntrustedAmt;
    //项目经理
    private String dPm;
    //招标周期
    private Integer dPeriod;
    //开始时间
    private Date dBeginTime;
    //拟交易场所
    private String dTradingPlace;
    //投标人是否接收联合投标
    private String dReceiveJointBidd;
    //报价方式
    private String dQuotationMethod;
    //投标人项目经理资格要求
    private String dBidderManagerCon;
    //呈批事项
    private String dApprovalMatter;
    private String teId;
    //申请单位
    private String dCid;
    //申请部门
    private String dDid;
    //单位性质
    private String dCnature;
    //部门性质
    private String dDnature;
    //合同类型
    private String dConType;
    //专业委托分管负责人
    private String dHead;
    //标题
    private String dTitle;
    //项目经理领导
    private String dPmId;
    //结束时间
    private Date dEndTime;
    //递交标书截止时间
    private Date dBidDocEndTime;
    //递交标书地址
    private String dBidDocAddr;
    //开标室
    private String dBidRoom;
    //招标文件终稿
    private String dBidFinalFile;
    //采购方式
    private String dProcure;
    //公告地址
    private String dUrl;
    //定标人数
    private Integer dCalibrationPersonnelNum;


    private String pId;
    private String dPrequalification;//资格预审
    private BigDecimal dBidBudgetCost;//招标预算造价
    private BigDecimal dBidControlPrice;//招标控制价
    private BigDecimal dProvisionalSum;//暂列金额
    private BigDecimal dEstimatedPrice;//预估价
    private Date dReviewDate;//预计资格审查日期
    private String dBiddingPlatform;//招标平台
    private String dAgency;//代理机构
    private String dCostOrganization;//造价机构
    private String dPricingMethod;//计价方式
    private String dApprovalContent;//审批内容
    private String dSpecialInstructions;//特殊说明
    private String dPaymentTerms;//付款条件
    private String dCompilationUnit;//编标单位
    private String dBidReviewUnit;//审标单位
    private String dFileType;//文件类型
    private String dAcceptanceDep;//验收部门
    private String dBasePriceId;//标底测算

    //是否两阶段评标
    private String dIsTwoStage;

    // 公示日期
    private Date dBidPubDate;

    private String dCalibrationMethod;

    private String dType;


    private BigDecimal dTenderSumLimit;

    private BigDecimal dBiddingControlRate;

    private BigDecimal dConversionControlPrice;

    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    /**
     * 控制价盖章文件
     */
    private String controlPriceFile;
}