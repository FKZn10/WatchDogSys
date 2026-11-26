package com.jpmsoft.projectProducer.pojo.entity.xz;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:46
 * @Description:
 * @Version: 1.0
 */
@TableName("fund_application")
@Data
public class FundApplication {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 计划年度
     */
    private String planYear;

    /**
     * 申报批次(JPMSoft_ZJSBPC)
     */
    private String batchNum;

    /**
     * 附件
     */
    private String fileRelationId;


    private String approveStatus;

    private Date approveTime;

    private String createId;

    private String createName;


    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
