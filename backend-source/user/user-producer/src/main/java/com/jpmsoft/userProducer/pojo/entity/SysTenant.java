package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 租户信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:32
 */
@Data
@TableName("SYS_Tenant")
public class SysTenant {

    @TableId(type = IdType.INPUT)
    private String teId;
    private String teName;
    private String teCode;
    private String teType;
    private String teNo;
    private String teBusinessLicense;
    private String teLogo;
    private String teIndustry;
    private String teSize;
    private String teAddress;
    private String teLegal;
    private String teContacts;
    private String teContactsTel;
    private String teChargeType;
    private Date teExpirationTime;
    private String teStatus;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;

}
