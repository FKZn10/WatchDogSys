package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:23
 */
@Data
@TableName("Gant_Section_Task_Change")
public class GantSectionTaskChange {

    @TableId(type = IdType.INPUT)
    private String id;
    private String planId;
    private String taskId;
    private String gstSection;
    private String gstSource;
}
