package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 短信配置
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 9:50
 */
@Data
@TableName("Sys_Sms_Conf")
public class SysSmsConf {

    @TableId(type = IdType.ASSIGN_UUID)
    public String id;
    public String sscType;
    public String sscPlatform;
    public String sscUrl;
    public String sscTempCode;
    public String sscSign;
    public String sscAccess;
    public String sscSecret;
    public String sscRegion;
    public String sscRemark;
    public Integer sscStatus;
    public String createUname;
    public Date createTime;
    public String createUid;
    private String sscJson;
    private String sscContent;
    private String sscTenant;
    private String sscSubmiter;

}
