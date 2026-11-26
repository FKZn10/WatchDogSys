package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 进度计划
 */
@Data
@TableName("Gantt_Plan_Change")
public class GanttPlanChange {

    @TableId(type = IdType.INPUT)
    private String planId;
    private String planName;
    private String pId;
    private String conId;
    private String planDId;
    private String planType;
    private Date planStart;
    private Date planEnd;
    private String planCreateUid;
    private String planCreateUname;
    private Date planCreateTime;
    private Integer planState;
    private String planRemark;
    private String planGantt;
    private Date planStateTime;
    private Date planActualStart;
    private Date planActualEnd;
    private Integer planEnableState;
    private String planEndUid;
    private String planEndUname;
    private Integer planFinishState;
    private String ptId;

    private String secId;
    private String planPmUid;
    private String planPmUname;
    private String planApmUid;
    private String planApmUname;
    private String planCueUid;
    private String planCueUname;
    private String planSeUid;
    private String planSeUname;
    private String planPartyBId;
    private String planLeadUid;
    private String planLeadUname;
    private String planNumber;
    private String planSource;
    private String planLevel;
    private String teId;
    private Boolean planIsSec;

}
