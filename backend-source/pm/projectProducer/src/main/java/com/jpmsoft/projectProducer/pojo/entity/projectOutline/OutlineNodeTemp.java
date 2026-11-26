package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板-关联业务功能
 *
 * @author tianhelin
 * @date 2025/6/16
 */
@Data
@TableName("Outline_Node_Temp")
public class OutlineNodeTemp {
    @TableId(type = IdType.INPUT)
    private String id;
    private String oslId;
    @JsonProperty("tCode")
    private String tCode;
    @JsonProperty("tName")
    private String tName;
    private Integer odtSort;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String preType;
}