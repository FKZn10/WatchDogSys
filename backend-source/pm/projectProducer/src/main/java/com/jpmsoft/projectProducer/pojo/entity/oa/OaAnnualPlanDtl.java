package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 年度计划清单
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:01
 */
@Data
@TableName("Oa_Annual_Plan_Dtl")
public class OaAnnualPlanDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String apId;
    private String pId;
    private String apdType;
    private Integer apdSort;
    private String apdCheckType;
}
