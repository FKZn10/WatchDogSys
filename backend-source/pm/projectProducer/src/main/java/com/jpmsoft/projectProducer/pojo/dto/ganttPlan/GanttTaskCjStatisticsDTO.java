package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/20 11:58
 */
@Data
public class GanttTaskCjStatisticsDTO {
    private int lqCount;
    private int yqCount;
    private int lcbLq;
    private int lcbYq;
    private int yjLq;
    private int yjYq;
    private int ejLq;
    private int ejYq;
}
