package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 定标报告
 *
 * @author 李文涛 aaron
 * @date 2022/07/08 09:31
 * @TableName CalibrationReport
 */
@TableName(value = "CalibrationReport")
@Data
public class CalibrationReport {

    /**
     *
     */
    @TableId(value = "id")
    private String id;

    @TableField(value = "P_Id")
    private String pId;
    /**
     *
     */
    @TableField(value = "Sec_Id")
    private String secId;

    /**
     *
     */
    @TableField(value = "Cr_Title")
    private String crTitle;

    /**
     *
     */
    @TableField(value = "Cr_Originator")
    private String crOriginator;

    /**
     *
     */
    @TableField(value = "Create_Uid")
    private String createUid;

    /**
     *
     */
    @TableField(value = "Create_Uname")
    private String createUname;

    /**
     *
     */
    @TableField(value = "Create_Time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "State_Time")
    private Date stateTime;

    /**
     *
     */
    @TableField(value = "State")
    private Integer state;

    /**
     *
     */
    @TableField(value = "Cr_Content")
    private String crContent;

    @TableField(value = "Cr_Code")
    private String crCode;

    @TableField(value = "Cr_Bid_Winner")
    private String crBidWinner;
    @TableField(value = "Cr_Pricing_Method")
    private String crPricingMethod;
    @TableField(value = "Cr_Money")
    private BigDecimal crMoney;
    @TableField(value = "Cr_Other_Valuation")
    private String crOtherValuation;
    private String teId;
    @TableField(value = "Cr_Bacm_Approve")
    private Boolean crBacmApprove;

    @TableField(value = "Cr_C_Id_One")
    private String crCIdOne;
    @TableField(value = "Cr_Offer_One")
    private String crOfferOne;
    @TableField(value = "Cr_Argument_One")
    private String crArgumentOne;
    @TableField(value = "Cr_Profile_One")
    private String crProfileOne;
    @TableField(value = "Cr_C_Id_Two")
    private String crCIdTwo;
    @TableField(value = "Cr_Offer_Two")
    private String crOfferTwo;
    @TableField(value = "Cr_Argument_Two")
    private String crArgumentTwo;
    @TableField(value = "Cr_Profile_Two")
    private String crProfileTwo;
    @TableField(value = "Cr_C_Id_Three")
    private String crCIdThree;
    @TableField(value = "Cr_Offer_Three")
    private String crOfferThree;
    @TableField(value = "Cr_Argument_Three")
    private String crArgumentThree;
    @TableField(value = "Cr_Profile_Three")
    private String crProfileThree;

    @TableField(value = "Cr_Is_Clarification")
    private String crIsClarification;

    @TableField(value = "Cr_Clarification_File")
    private String crClarificationFile;
    @TableField(exist = false)
    private String crClarificationFileIds;

    @TableField(exist = false)
    private String crProfileIdsOne;

    @TableField(exist = false)
    private String crProfileIdsTwo;

    @TableField(exist = false)
    private String crProfileIdsThree;
}