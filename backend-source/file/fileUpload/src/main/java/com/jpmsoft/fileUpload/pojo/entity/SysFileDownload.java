package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 附件下载
 * 用于生成下载地址的虚拟ID
 */
@Data
@TableName("Sys_File_Download")
public class SysFileDownload {

    @TableId(type = IdType.INPUT)
    private String downId;
    private String filePath;
    private String fileIds;
    private Date downCreateTime;
}
