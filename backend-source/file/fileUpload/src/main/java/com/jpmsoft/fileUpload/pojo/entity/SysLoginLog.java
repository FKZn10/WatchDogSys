package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 登录日志
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:23
 */
@Data
@TableName("Sys_Login_Log")
public class SysLoginLog {

    @TableId(type = IdType.INPUT)
    private String sllId;
    private String uId;
    private String sllIp;
    private String sllLoginType;
    private Date sllLoginTime;
    private String appVersion;
    private String appDeviceType;
    /**
     * 01 登录
     * 02 登出
     */
    private String sllAction;
}
