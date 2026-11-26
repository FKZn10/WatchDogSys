package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 大事记
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:14
 */
@Data
@TableName("memoir_info")
public class MemoirInfo extends BaseEntity {

    @TableField(value = "mi_title")
    private String miTitle;
    @TableField(value = "mi_content")
    private String miContent;
    @TableField(value = "mi_time")
    private Date miTime;
    @TableField(value = "mi_cpy")
    private String miCpy;
    @TableField(value = "p_id")
    private String pId;
    @TableField(value = "sec_id")
    private String secId;
    @TableField(value = "con_id")
    private String conId;
    @TableField(value = "task_id")
    private String taskId;
    @TableField(value = "mi_type")
    private String miType;
}
