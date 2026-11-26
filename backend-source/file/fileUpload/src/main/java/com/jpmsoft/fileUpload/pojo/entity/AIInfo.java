package com.jpmsoft.fileUpload.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * AI配置信息
 */
@Data
@TableName("ai_info")
public class AIInfo {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String url;
    private String aiModel;
    private String apiKey;
    private String aiType;
    private String description;
    private String requestBody;
    private String status;
    private String instruction;
    private String createUid;
    private String createUname;
    private Date createTime;
}
