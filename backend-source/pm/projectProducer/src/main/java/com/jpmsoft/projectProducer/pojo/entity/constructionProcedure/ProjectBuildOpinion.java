package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 2.建设项目选址意见书
 */
@Data
@TableName("Project_Build_Opinion")
public class ProjectBuildOpinion {

    @TableId(type = IdType.INPUT)
    private String uuid;

    @TableField("Product_Id")
    private String productId;
    @TableField("Product_Name")
    private String productName;

    //需征求部门意见
    @TableField("Department_Opinion")
    private String departmentOpinion;

    @TableField("Remarks")
    private String remarks;


    @TableField("Approve_Status")
    private String approveStatus;

    @TableField("Create_Uid")
    private String createId;


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
