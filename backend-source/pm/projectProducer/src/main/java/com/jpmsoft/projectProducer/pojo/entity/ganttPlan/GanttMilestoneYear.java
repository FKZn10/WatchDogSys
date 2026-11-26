package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author tianhelin
 * @date 2025/5/29
 */
@Data
public class GanttMilestoneYear {
    /**
     * gantt_milestone_statistics主键，子表主键
     */
    @TableId(type = IdType.INPUT)
    private String gmsId;

    /**
     * 年份
     */
    private String year;
}