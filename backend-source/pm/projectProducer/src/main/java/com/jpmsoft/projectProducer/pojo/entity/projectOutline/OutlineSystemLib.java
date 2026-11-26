package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板-目录节点
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_System_Lib")
public class OutlineSystemLib {
    @TableId(type = IdType.INPUT)
    private String id;
    private String osmId;
    private String oslParentId;
    private String oslName;
    private String oslCode;
    private String oslRemark;
    private String oslStatus;
    private Integer oslSort;
    private String oslContent;
    private Boolean oslBussnis;
    private Date createTime;
    private String createUid;
    private String createUname;
}