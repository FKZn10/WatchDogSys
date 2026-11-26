package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 监理单位中标通知书
 */
@Data
@TableName("Supervision_Unit_Won")
public class SupervisionUnitWon {

    @TableId(type = IdType.INPUT)
    private String uuid;

    @TableField("Product_Id")
    private String productId;
    @TableField("Product_Name")
    private String productName;

    //施工单位名称
    @TableField("Construction_Control_Unit")
    private String constructionControlUnit;
    //中标金额
    @TableField("Winning_Amount")
    private BigDecimal winningAmount;
    @TableField("Remarks")
    private String remarks;

    @TableField("Create_Uid")
    private String createId;

    @TableField("Approve_Status")
    private String approveStatus;
    @TableField("Create_Name")
    private String createName;

    @TableField(value = "Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField("file_relation_id")
    private String fileRelationId;

    @TableField("Delete_State")
    @TableLogic(value = "0", delval = "1")
    private String deleteState;
    @TableField(exist = false)
    private String rteFileIds;

    @TableField("Approve_Time")
    private Date approveTime;
    private String teId;
}
