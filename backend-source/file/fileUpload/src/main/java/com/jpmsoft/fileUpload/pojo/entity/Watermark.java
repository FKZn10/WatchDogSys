package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 水印表
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 13:38
 */
@Data
@TableName("Watermark")
public class Watermark {

    @TableId(type = IdType.INPUT)
    private String id;
    @JsonProperty("wFile")
    private String wFile;
    @JsonProperty("wName")
    private String wName;
    @JsonProperty("wIsActivate")
    private String wIsActivate;

    @JsonProperty("wWidth")
    private float wWidth;
    @JsonProperty("wHeight")
    private float wHeight;
    @JsonProperty("wRotation")
    private float wRotation;
    @JsonProperty("wOpacity")
    private float wOpacity;

    @TableField(exist = false)
    private String path;
    //标记是否需要在水印添加结束后根据path删除附件（图片为调整透明度后的临时附件）
    @TableField(exist = false)
    private Boolean deletePath=false;
}
