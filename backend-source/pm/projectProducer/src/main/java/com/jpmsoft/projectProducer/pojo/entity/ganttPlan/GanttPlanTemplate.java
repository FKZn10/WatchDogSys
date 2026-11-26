package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 进度计划模板
 */
@Data
@TableName("Gantt_Plan_Template")
public class GanttPlanTemplate {

    @TableId(type = IdType.INPUT)
    private String ptId;
    private String ptName;
    private String ptPType;
    private String ptPlanType;
    private String ptFile;
    private String ptGantt;
    private String ptRemark;
    private Integer ptState;
    private String ptCreateUid;
    private String ptCreateUname;
    private Date ptCreateTime;
    private String ptEditUid;
    private String ptEditUname;
    private Date ptEditTime;
    private Integer ptAutoPlan;
    private String ptTemplateType;
    private String teId;
}
