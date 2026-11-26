package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 招标计划变更
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 16:10
 */
@Data
@TableName("Sec_Bidding_Plan_Change")
public class SecBiddingPlanChange {
    @TableId(type = IdType.INPUT)
    private String bpcId;
    //原招标计划Id
    private String bpId;
    //计划名称
    private String bpName;
    //年度
    private Integer bpYear;
    //月度
    private Integer bpMonth;
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    //招标人ID
    private String bpTenderee;
    //批复文件标题2
    private String bpApprovalTitle;
    //批复编号2
    private String bpApprovalNum;
    //批复时间2
    private Date bpApprovalTime;
    //批复文件2
    private String bpApprovalFile;
    private String teId;
    /**
     * 计划阶段
     */
    private String bpPlanPhase;

    /**
     * 原计划阶段
     */
    private String oldBpPlanPhase;

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
}
