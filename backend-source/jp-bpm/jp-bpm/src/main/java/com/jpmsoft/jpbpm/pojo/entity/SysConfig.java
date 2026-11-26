package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_Config")
public class SysConfig {

    @TableId(type = IdType.INPUT)
    private String conId;
    private String conCode;
    private String conName;
    private Date conCreatetime;
    private String conDescription;
    private String conValue;
    private String conCreateuId;
    private String conCreateuName;
    private Integer conType;
    private String conCategory;
    /**
     * 启停状态 1 启用 0 停用
     */
    private Integer conStatus;
    private String teId;
    /**
     * 配置项类别(区别多租户与系统默认)
     * 1：系统默认
     */
    private Integer conMold;

}
