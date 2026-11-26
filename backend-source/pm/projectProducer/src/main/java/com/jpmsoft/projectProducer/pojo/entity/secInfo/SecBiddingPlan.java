package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.utils.ValidateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标计划
 */
@Data
@TableName("Sec_Bidding_Plan")
public class SecBiddingPlan {

    @TableId(type = IdType.INPUT)
    private String bpId;
    //计划名称
    private String bpName;
    //年度
    private Integer bpYear;
    //月度
    private Integer bpMonth;
    //备注
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    //招标人Id
    private String bpTenderee;
    //批复文件标题
    private String bpApprovalTitle;
    //批复编号
    private String bpApprovalNum;
    //批复时间
    private Date bpApprovalTime;
    //批复文件
    private String bpApprovalFile;
    private String teId;

    //招标开始时间
    private Date bpSecTime;
    //申请单位
    private String bpCid;
    //申请部门
    private String bpDid;
    //单位性质
    private String bpCnature;
    //部门性质
    private String bpDnature;
    //申请日期
    private Date bpTime;
    //招标内容
    private String bpSecContent;
    //估算价
    private BigDecimal bpGsAmt;
    //招标方式
    private String bpBiddingType;
    //招标完成时间
    private Date bpEndTime;
    //标段名称
    private String bpSecName;
    //是否工程招标
    private Boolean bpGczb;
    //项目规模
    private String bpProScale;
    //项目总投资（万元）
    private BigDecimal bpProAmt;
    //资金来源
    private String bpFundSource;
    //项目范围及工程概况
    private String bpScopeOverview;
    //说明
    private String bpSm;
    //概算金额
    private BigDecimal bpEsAmt;
    //委托类型（JPMsoft_WEITUOLX）
    private String bpEntrustmentType;
    /**
     * 集团是否为见证方 1：是 0：否
     */
    private Integer bpGroupWitness;
    /**
     * 项目投资控制情况
     */
    private String bpInvControlSituation;

    //管理类别
    private String bpManagementCategory;

    /**
     * 数据完整性签名
     */
    private String bpSign;

    /**
     * 计划阶段
     */
    private String bpPlanPhase;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;

    private String isNew;

    private String responsibleLeader;

    /**
     * 是否储备项目申请
     */
    private String reserveItems;

    /**
     * 是否录入估算
     */
    private String enterEstimate;

    public String signContent() {
        StringBuilder sb=new StringBuilder();
        sb.append(this.bpName)
                .append(this.bpYear)
                .append(this.bpMonth)
                .append(this.bpRemark)
                .append(this.bpCreateUid)
                .append(this.bpCreateUname)
                .append(this.bpCid)
                .append(this.bpDid)
                .append(this.bpTenderee)
                .append(this.bpApprovalFile)
                .append(this.bpApprovalNum)
                .append(this.bpApprovalTitle)
                .append(this.bpCnature)
                .append(this.bpDnature)
                .append(this.bpSecContent)
                .append(this.bpBiddingType)
                .append(this.bpSecName)
                .append(this.bpProScale)
                .append(this.bpFundSource)
                .append(this.bpScopeOverview)
                .append(this.bpSm);
        return sb.toString();
    }

    /**
     * 修改用
     * @param old
     * @return
     */
    public String signContent(SecBiddingPlan old) {
        StringBuilder sb=new StringBuilder();
        sb.append(ValidateUtil.isEmpty(this.bpName)?old.getBpName():this.bpName)
                .append(ValidateUtil.isEmpty(this.bpYear)?old.getBpYear():this.bpYear)
                .append(ValidateUtil.isEmpty(this.bpMonth)?old.getBpMonth():this.bpMonth)
                .append(ValidateUtil.isEmpty(this.bpRemark)?old.getBpRemark():this.bpRemark)
                .append(ValidateUtil.isEmpty(this.bpCreateUid)?old.getBpCreateUid():this.bpCreateUid)
                .append(ValidateUtil.isEmpty(this.bpCreateUname)?old.getBpCreateUname():this.bpCreateUname)
                .append(ValidateUtil.isEmpty(this.bpCid)?old.getBpCid():this.bpCid)
                .append(ValidateUtil.isEmpty(this.bpDid)?old.getBpDid():this.bpDid)
                .append(ValidateUtil.isEmpty(this.bpTenderee)?old.getBpTenderee():this.bpTenderee)
                .append(ValidateUtil.isEmpty(this.bpApprovalFile)?old.getBpApprovalFile():this.bpApprovalFile)
                .append(ValidateUtil.isEmpty(this.bpApprovalNum)?old.getBpApprovalNum():this.bpApprovalNum)
                .append(ValidateUtil.isEmpty(this.bpApprovalTitle)?old.getBpApprovalTitle():this.bpApprovalTitle)
                .append(ValidateUtil.isEmpty(this.bpCnature)?old.getBpCnature():this.bpCnature)
                .append(ValidateUtil.isEmpty(this.bpDnature)?old.getBpDnature():this.bpDnature)
                .append(ValidateUtil.isEmpty(this.bpSecContent)?old.getBpSecContent():this.bpSecContent)
                .append(ValidateUtil.isEmpty(this.bpBiddingType)?old.getBpBiddingType():this.bpBiddingType)
                .append(ValidateUtil.isEmpty(this.bpSecName)?old.getBpSecName():this.bpSecName)
                .append(ValidateUtil.isEmpty(this.bpProScale)?old.getBpProScale():this.bpProScale)
                .append(ValidateUtil.isEmpty(this.bpFundSource)?old.getBpFundSource():this.bpFundSource)
                .append(ValidateUtil.isEmpty(this.bpScopeOverview)?old.getBpScopeOverview():this.bpScopeOverview)
                .append(ValidateUtil.isEmpty(this.bpSm)?old.getBpSm():this.bpSm);
        return sb.toString();
    }
}