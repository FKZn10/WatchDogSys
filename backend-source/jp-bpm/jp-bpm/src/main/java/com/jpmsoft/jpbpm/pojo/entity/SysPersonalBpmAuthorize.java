package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * AB授权
 */
@Data
@TableName("SysPersonal_Bpm_Authorize")
public class SysPersonalBpmAuthorize {

    @TableId(type = IdType.INPUT)
    public String baId;
    public String baAuthorizerUid;
    public String baAuthorizerUname;
    public Date baStartTime;
    public Date baEndTime;
    public String baCreateUid;
    public String baCreateUname;
    public Date baCreateTime;
    public Integer baState;
    public Date baStateTime;
    public String baRemark;
    public String baTcode;
    private String teId;
}
