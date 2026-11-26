package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 概算申报
 */
@Data
@TableName("Inv_Budget_Declare")
public class InvBudgetDeclare {

    @TableId(type = IdType.INPUT)
    private String bdId;
    //概算编制单位ID
    private String bdPreparedBy;
    //概算审核单位ID
    private String bdAuditUnit;
    //申报概算金额
    private BigDecimal bdMoney;
    private String bdRemark;
    private String bdCreateUid;
    private String bdCreateUname;
    private Date bdCreateTime;
    private Integer bdState;
    private Date bdStateTime;
    //项目id
    private String pId;
    //概算审定金额
    private BigDecimal bdAuditAmt;
    //拆分来源（项目id）
    private String bdSplitForm;
    //是否拆分
    private Integer bdSplitType;
    //批复文件标题
    private String bdApprovalTitle;
    //批复编号
    private String bdApprovalNum;
    //批复时间
    private Date bdApprovalTime;
    //批复文件
    private String bdApprovalFile;
    //立项审批单位
    private String bdApprovalUnit;
    private String teId;
    //占地面积
    private BigDecimal bdCoverArea;
    //建筑面积
    private BigDecimal bdBuildArea;

    private String bdType;

    //送审概算文件
    private String bdReviewFile;
    //审定概算文件
    private String bdAuthorizeFile;

    // 工程建安费
    private BigDecimal engineeringConstructionSafetyFeeAmount;
    // 工程建设其他费
    private BigDecimal engineeringConstructionOtherFeeAmount;
    // 其中土地费
    private BigDecimal landFeeAmount;
    // 建设期贷款利息费用
    private BigDecimal constructionPeriodLoanInterestAmount;
    // 预备费
    private BigDecimal reserveFeeAmount;

}
