package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Sec_Bidding_Negotiation")
public class SecBiddingNegotiation {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String pId;

    private String secId;

    private String bnQualiReview;

    private String bnFunctionText;

    private String bnRemark;

    private String bnReceiveJointBidd;


    //邀请发布日期
    private Date bnInvitePublishDate;
    //计划开标时间
    private Date bnPlanOpenTime;
    //计划开标地点
    private String bnPlanOpenLocation;
    private String tendereeName;
    private String bnType;
    private BigDecimal bnTenderSumLimit;
    private BigDecimal bnStandardFee;
    private BigDecimal bnBiddingControlRate;
    private BigDecimal bnConversionControlPrice;
    private String bnReferenceStandards;
    private String bnOverviewExplanation;
    private String bnAssociatedDrawings;
    private String bnStandardFeeType;
    private Date bnInvitationTime;
    private Date bnPlanOpeningTime;
    private String bnPlanBidOpenLocation;
    private String inAssociatedDrawings;

    private String bnFunctionTextOther;

    private String bnIsEvaluateSeparation;

    private String bnIsTwoStage;

    private String bnCalibrationMethod;

    private String bnFileType;

    private String bnConFile;

    /**
     * 控制价盖章文件
     */
    private String bnControlPriceFile;

    private String bnUrl;
}
