package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("outline_datasource")
public class OutlineDatasource implements Serializable {

    @TableId(type = IdType.INPUT)
    private String id;
    private String odName;
    private String odType;
    private String odSource;
    private String odStatus;
    private String odRemark;
    private String odAddress;
    private String odMethod;
    private String odBody;
    private String odDataType;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date updateTime;
    private String odAlias;
}
