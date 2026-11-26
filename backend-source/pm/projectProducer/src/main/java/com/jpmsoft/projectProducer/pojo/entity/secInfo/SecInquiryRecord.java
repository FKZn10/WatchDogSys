package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 询价记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:46
 */
@Data
@TableName("Sec_Inquiry_Record")
public class SecInquiryRecord extends BaseEntity {


    private String secId;
    /**
     * 开始时间
     */
    private Date sirBeginTime;
    /**
     * 招标周期
     */
    private Integer sirPeriod;
    /**
     * 招标范围
     */
    private String sirScope;
    /**
     * 资格条件
     */
    private String sirPrerequisites;
    /**
     * 合同金额
     */
    private BigDecimal sirConMoney;
    private String sirRemark;
    /**
     * 意向单位
     */
    private String sirIntendedUnit;
    private String teId;
    /**
     * 询价事由
     */
    private String sirReason;
    /**
     * 询价认价内容
     */
    private String sirPriceContent;
    /**
     * 询价形式
     */
    private String sirModality;
    /**
     * 参加人员
     */
    private String sirParticipants;

    /**
     * 询价依据
     */
    private String sirInquiryBasis;

    /**
     * 评议时间
     */
    private Date sirEvaluationTime;
    /**
     * 中标时间
     */
    private Date sirWinningBidTime;

    //评标方法
    private String sirFunction;

    //是否接受联合体
    private String sirReceiveJointBidd;

    //资格审查方式
    private String sirQualiReview;
    // 邀请发布日期
    private Date sirInviteDate;

    //计划开标时间
    private Date sirPlanOpenTime;

    //计划开标地点
    private String sirPlanOpenPlace;

    private String pId;

    /**
     * 招标人名称
     */
    private String tendereeName;

    /**
     * 询价类型
     */
    private String sirType;

    /**
     * 投标总价限额
     */
    private BigDecimal sirTenderSumLimit;

    /**
     * 标准费用
     */
    private BigDecimal sirStandardFee;

    /**
     * 投标控制费率
     */
    private BigDecimal sirBiddingControlRate;

    /**
     * 折算控制价
     */
    private BigDecimal sirConversionControlPrice;

    /**
     * 参考标准
     */
    private String sirReferenceStandards;

    /**
     * 概况说明
     */
    private String sirOverviewExplanation;

    /**
     * 关联图纸
     */
    private String sirAssociatedDrawings;

    /**
     * 标准费用类型
     */
    private String sirStandardFeeType;

    /**
     * 文件类型
     */
    private String sirFileType;

}
