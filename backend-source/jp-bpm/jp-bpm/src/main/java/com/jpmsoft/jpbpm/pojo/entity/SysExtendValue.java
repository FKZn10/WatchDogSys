package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 扩展配置值
 *
 * @Author: 张程磊 zhangchenglei
 * @date: 2023/5/11 10:46
 */
@Data
@TableName("Sys_Extend_Value")
public class SysExtendValue {
    @TableId(type = IdType.INPUT)
    private String id;
    private String fileid;
    private String ecId;
    private String evValue;
    private String teId;
}
