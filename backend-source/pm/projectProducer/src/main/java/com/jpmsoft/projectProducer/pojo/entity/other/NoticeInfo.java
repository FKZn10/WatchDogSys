package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("Notice_Info")
public class NoticeInfo extends BaseEntity {
    private String id;
    private String sourceId;
    private String sourceCode;
    private String noticeInfoCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date noticeInfoTime;
    private String noticeInfoContent;
    private String supportingDocument;
    private Boolean isHistory;
    // 闭环状态 unclosed:未闭环 closing:闭环中 closed:已闭环
    private String closeStatus;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date stateTime;
    private String createUid;
    private String createUname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}