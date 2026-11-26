package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import lombok.Data;

import java.util.Date;


@Data
public class OutProjectVo {
    private String competentDepartmentName; // 责任单位
    private String projectName;             // 项目名称
    private Date planDate;                  // 计划完成日期
    private Date actualDate;                // 实际完成日期
}
