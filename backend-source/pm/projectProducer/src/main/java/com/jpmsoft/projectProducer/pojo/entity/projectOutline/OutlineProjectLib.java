package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目管理大纲体系目录节点
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_Project_Lib")
public class OutlineProjectLib {
    @TableId(type = IdType.INPUT)
    private String id;
    private String opmId;
    private String oplParentId;
    private String oplName;
    private String oplCode;
    private String oplRemark;
    private String oplStatus;
    private Integer oplSort;
    private String oplContent;
    private Boolean oplBussnis;
    private String opnContent;
    private Date createTime;
    private String createUid;
    private String createUname;
    private BigDecimal oplScore;
}