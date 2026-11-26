package com.jpmsoft.fileUpload.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Sys_Dictionary")
public class SysDictionary {

    @TableId(type = IdType.INPUT)
    private String dId;
    private String dName;
    private String dCode;
    private Integer dSort;
    private String dParentid;
    private String dRemark;
    private String dStatus;

    @TableField(exist = false)
    private String parentCode;
}
