package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 人防行政许可
 */
@Data
@TableName("Administrative_Licensing")
public class AdministrativeLicensing {

    @TableId(type = IdType.INPUT)
    private String uuid;

    @TableField("Product_Id")
    private String productId;
    @TableField("Product_Name")
    private String productName;

    //面积
    @TableField("Area")
    private String area;

    @TableField("Remarks")
    private String remarks;

    @TableField("Create_Uid")
    private String createId;
    @TableField("Approve_Status")
    private String approveStatus;

    @TableField("Approve_Time")
    private Date approveTime;


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

    private String teId;
}
