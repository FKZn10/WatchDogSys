package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 前端组件缓存
 * 归档目录选择/用户选择/字典选择等等
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:53
 */
@Data
@TableName("web_component_cache")
public class WebComponentCache {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 文档模板编码
     */
    private String wccCode;
    /**
     * 字段编码
     */
    private String wccField;
    /**
     * 缓存值
     */
    private String wccValue;
    /**
     * 唯一标识 通过 wccCode+":"+wccField 组合得出
     */
    private String wccKey;
    /**
     * 用户ID
     */
    private String createUid;
    private String createUname;
    private Date createTime;
}
