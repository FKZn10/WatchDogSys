package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_config_base")
public class SysConfigBase {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private String updateUid;

    /**
     * 更新人名称
     */
    private String updateUname;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型
     */
    private String confType;

    /**
     * 分类
     */
    private String confCategory;

    /**
     * 编码
     */
    private String confCode;

    /**
     * 配置内容
     */
    private String confContent;

    /**
     * 状态
     */
    private Integer status;
}