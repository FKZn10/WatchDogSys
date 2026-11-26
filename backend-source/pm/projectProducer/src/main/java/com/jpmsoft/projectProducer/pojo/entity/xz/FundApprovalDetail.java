package com.jpmsoft.projectProducer.pojo.entity.xz;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:48
 * @Description: 资金批复详情表
 * @Version: 1.0
 */
@TableName("fund_approval_detail")
@Data
public class FundApprovalDetail {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 资金批复表id
     */
    private String fundApprovalId;

    /**
     * 项目申报表id
     */
    private String projectApplicationId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
