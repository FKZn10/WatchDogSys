package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标采购备案
 */
@Data
@TableName("Sec_Procurement_Filing")
public class SecProcurementFiling {
    @TableId(type = IdType.INPUT)
    private String pfId;
    //项目id
    private String pId;
    //计划总投资
    private BigDecimal pfPlannInvestment;
    //本次报备金额
    private BigDecimal pfAmount;
    //资质要求
    private String pfQualification;
    //集团许可类型（报批项目/报备项目/其他）
    private String pfPermit;
    //集团许可情况
    private String pfPermitDesc;
    //项目负责人姓名
    private String pfPmName;
    //联系电话
    private String pfTelephone;
    //计划开工日期
    private Date pfPlanStart;
    //计划竣工日期
    private Date pfPlanEnd;
    //项目类型
    private String pType;
    //招标采购情况
    private String pfRemark;
    //意向承包商
    private String pfIntendedContractor;
    //推荐原因
    private String pfReason;
    private String pfCreateUid;
    private String pfCreateUname;
    private Date pfCreateTime;
    private Integer pfState;
    private Date pfStateTime;
    //下浮率
    private Float pfLower;
    //招标方式
    private String pfBidding;
    //定标方式
    private String pfCalibration;
    //定标方案附件
    private String pfCalibrationFile;
    private String teId;
    //标段名称
    private String pfSecName;
    //邀请投标单位及投标报价
    private String pfBidQuotation;
    //会议纪要（企业）
    private String pfMeetingFile;
    //招标采购方案呈批表
    private String pfProcurementFile;
    //报集团请示材料
    private String pfInstructionsFile;
    //招标文件及法律意见书
    private String pfLawFile;
    //招标公告
    private String pfNoticeFile;
    //资质承揽范围说明
    private String pfScopeFile;
    //市同类项目平均下浮率情况
    private String pfLowerFile;
    //招标控制价计算书
    private String pfControlFile;
    //工期计算书
    private String pfDurationFile;
    //邀标函
    private String pfBidLetterFile;
    //审批表
    private String pfApproveFile;
    //发包公示
    private String pfAwardFile;
    //会议纪要（集团）
    private String pfMeetingJtFile;
    //是否同意改变招标方式
    private Boolean pfAgreeChange;
}
