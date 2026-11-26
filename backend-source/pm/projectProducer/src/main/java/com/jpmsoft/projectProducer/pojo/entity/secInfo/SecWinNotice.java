package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 中标通知书
 */
@Data
@TableName("Sec_Win_Notice")
public class SecWinNotice {
    @TableId(type = IdType.INPUT)
    private String wnId;
    //标段id
    private String secId;
    //中标金额
    private BigDecimal wnAmount;
    //中标金额单位
    private String wnAmountUnit;
    //下浮率
    private Float wnLower;
    private String wnRemark;
    private String wnCreateUid;
    private String wnCreateUname;
    private Date wnCreateTime;
    private Integer wnState;
    private Date wnStateTime;
    //是否有主要管理人员汇总表
    private Integer wnManagePerson;
    //中标单位类型
    private String wnCType;
    //费率
    private BigDecimal wnRate;
    //其他计价
    private String wnOtherValuation;
    //定标方式
    private String wnCalibrationMode;
    //中标单位
    private String wnWinUnit;
    /**
     * 中标通知书日期
     */
    private Date wnDate;
    //呈批事项
    private String wnApprovalMatter;
    private String teId;
    //中标通知书
    private String wnWinFile;
    //发包公示
    private String wnAwardFile;
    //投标文件
    private String wnBidFile;
    //定标过程资料
    private String wnCalibrateFile;
    //采购小组决策方式
    private String wnDecisionMethods;
    //经办人
    private String wnOperator;
    //经办人姓名
    private String wnOperatorName;
    //联系方式
    private String wnContactInformation;
    //申请时间
    private Date wnApplicationTime;

    private Date wnEvaluationTime;
    private BigDecimal wnBiddingPeriod;
    private String wnPm;
    private String wnExplanation;
    private Date wnStartTime;
    private Date wnEndTime;
    private String wnPublicityFile;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    private String tendereeId;
}