package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 模板字段
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:06
 */
@Data
@Deprecated
@TableName("Sys_Pay_Field")
public class SysPayField {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String ptId;
    private String pfName;
    private String pfCode;
    private String pfType;
    private String pfFormula;
    private String pfVerification;
    private String pfRemark;
    private String pfSort;
    private String pfWrite;
    private String createUid;
    private String createUname;
    private Date createTime;

}
