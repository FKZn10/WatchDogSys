package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("OnlyOffice_File_Changes")
public class OnlyOfficeFileChanges {
    @TableId(type = IdType.INPUT)
    private String id;
    private String fileId;
    private String path;
    private Date createTime;
}