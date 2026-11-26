package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 年度计划申报管理
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:09
 */
@Data
@TableName("Pro_Year_Cons_Plan")
public class ProYearConsPlan extends BaseEntity {
    private Date ycpStartTime;
    private Date ycpEndTime;
    private String ycpRemark;
    private String teId;
}
