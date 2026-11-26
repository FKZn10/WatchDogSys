package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系管理
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Data
@TableName("Outline_Project_Manage")
public class OutlineProjectManage {
    @TableId(type = IdType.INPUT)
    private String id;
    private String pId;
    private String osmId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer state;
    private Date stateTime;
    private Boolean opmStatus;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String sortRules;
    private String opmCode;
    /**
     * 落款单位
     */
    private String opmUnit;
}