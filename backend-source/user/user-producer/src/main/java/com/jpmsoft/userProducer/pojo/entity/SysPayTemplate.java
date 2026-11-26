package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 支付模板
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:02
 */
@Data
@TableName("Sys_Pay_Template")
public class SysPayTemplate {

    @TableId(type = IdType.INPUT)
    private String id;
    private Boolean ptStatus;
    private String ptName;
    private String ptCode;
    private String ptPayType;
    private String ptConType;
    private String ptExplain;
    private String ptForm;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;

}
