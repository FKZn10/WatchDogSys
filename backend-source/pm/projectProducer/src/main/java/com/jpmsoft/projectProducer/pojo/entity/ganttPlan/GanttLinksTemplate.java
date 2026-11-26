package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 任务关联线
 */
@Data
@TableName("Gantt_Links_Template")
public class GanttLinksTemplate {

    @TableId(type = IdType.INPUT)
    private String linkId;
    private String ptId;
    private String linkSource;
    private String linkTarget;
    private Integer linkType;

}
