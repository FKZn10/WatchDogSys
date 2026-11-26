package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("judge_extraction")
public class JudgeExtraction {
    @TableId(type = IdType.INPUT)
    private String id;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String secId;
    private String secName;
    private Date bidTime;
    private String bidLocation;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    private String contactPerson;
    private String contactPhone;
    private Integer insideCount;
    private Integer outCount;
    private String avoidUnit;
    private String avoidUnitName;
    private String remark;
    private String attachment;
    @TableField(exist = false)
    private String attachmentIds;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    /**
     * 区分是集团还是小城投
     * 集团 01
     * 小城投 02
     */
    private String jeCategory;

    private Integer externalCount;
}