package com.jpmsoft.common.model.requestAndResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class NoticeInfoModel extends BaseFileDto {
    private static final long serialVersionUID = -2216895585465398600L;
    private  String sourceId;
    private  String sourceCode;
    private  String noticeInfoCode;
    private  String noticeInfoContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date noticeInfoTime;
    private String supportingDocument;
    //证明文件ids
    private String supportingDocumentFileIds;
    // 是否历史记录
    private Boolean isHistory;
    // 闭环状态 unclosed:未闭环 closing:闭环中 closed:已闭环
    private String closeStatus;
    private String createUname;
    private String createUid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date stateTime;
}