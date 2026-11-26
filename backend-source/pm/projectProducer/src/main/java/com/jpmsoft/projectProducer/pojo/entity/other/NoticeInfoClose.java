package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("notice_info_close")
public class NoticeInfoClose extends BaseEntity {
    private String id;
    private  String sourceId;
    private  String sourceCode;
    private  String noticeInfoId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date closeTime;
    //闭环情况反馈
    private String closeFeedback;
    //备注
    private String remark;
    private String supportingDocument;
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