package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Pro_Image_Progress")
public class ProImageProgress {
    @TableId(type = IdType.INPUT)
    private String id;
    @JsonProperty("pId")
    private String pId;
    private String title;
    private String content;
    private String img;
    private Integer state;
    private Date createTime;
    private String createUname;
    private String createUid;
    private String teId;
}
