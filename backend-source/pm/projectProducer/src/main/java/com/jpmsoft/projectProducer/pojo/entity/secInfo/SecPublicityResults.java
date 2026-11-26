package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 中标结果公示
 */
@Data
@TableName("Sec_Publicity_Results")
public class SecPublicityResults {

    @TableId(type = IdType.INPUT)
    private String prId;
    //标段id
    private String secId;
    //公示开始时间
    private Date prStartTime;
    //公示结束时间
    private Date prEndTime;
    //招标人
    private String prTenderee;
    //招标人联系电话
    private String prTendereeTelephone;
    //招标人联系地址
    private String prTendereeAddress;
    //中标单位ID
    private String prBidWinner;
    //中标金额
    private BigDecimal prBidAmount;
    //中标结果公示登记表
    private String prResultRegistration;
    //评标专家抽取申请表
    private String prArmorApplication;
    private Date prCreateTime;
    private String prCreateUid;
    private String prCreateUname;
    private Integer prState;
    private Date prStateTime;
    //费率
    private BigDecimal prRate;
    //其他计价
    private String prOtherValuation;
    //项目经理
    private String prPm;
    //中标工期
    private Integer prBiddingPeriod;
    //中标基本情况
    private String prBidWinning;
    private String teId;
    //公告标题
    private String prTitle;
    //发布时间
    private Date prTime;
    //公告内容附件
    private String prContentFile;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;
}