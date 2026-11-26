package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 年度计划变更
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:00
 */
@Data
@TableName("Oa_Annual_Plan_Change")
public class OaAnnualPlanChange extends BaseEntity {
    private String apId;
    private String apTitle;
    private String apYear;
    private String apRemark;
    private String teId;
}
