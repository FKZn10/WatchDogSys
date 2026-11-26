package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 年度申报计划选择清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:10
 */
@Data
@TableName("Pro_Year_Plan_Select")
public class ProYearPlanSelect {
    @TableId(type = IdType.INPUT)
    private String id;
    private String cplId;
    private String ycpId;

}
