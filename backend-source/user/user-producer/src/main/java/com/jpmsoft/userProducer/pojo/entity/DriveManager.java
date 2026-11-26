package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("drive_manager")
public class DriveManager {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String driveName;
    private String driveType;
    private String driveStatus;
    private String driveFile;
    private Date createTime;
    private String createUid;
    private String createUname;

    @TableField(exist = false)
    private String driveFileIds;
    @TableField(exist = false)
    private Integer page;
    @TableField(exist = false)
    private Integer size;
    @TableField(exist = false)
    private String url;
    @TableField(exist = false)
    private String driveTypeName;
}
