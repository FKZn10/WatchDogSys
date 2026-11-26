package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 流程审批通过需要生成打印报表的数据
 * <p>
 * 报表附件生成后删除
 *
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 16:44
 */
@Data
@TableName("Sys_Ji_Mu_Report")
public class SysJiMuReport {


    @TableId(type = IdType.INPUT)
    private String id;
    private String fileId;
    private String jmId;
    @JsonProperty("tCode")
    private String tCode;
}
