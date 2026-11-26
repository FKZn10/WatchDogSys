package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/28 20:37
 */
@Data
@TableName("gant_section")
public class GantSection {
    @TableId(type = IdType.INPUT)
    private String id;
    private String gsSection;
    private String gsDescribe;
    private String planId;
}
