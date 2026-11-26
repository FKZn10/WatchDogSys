package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_Project_Temp")
public class OutlineProjectTemp {
    @TableId(type = IdType.INPUT)
    private String id;
    private String oplId;
    private String tCode;
    private String tName;
    private Integer opdSort;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String preType;
}