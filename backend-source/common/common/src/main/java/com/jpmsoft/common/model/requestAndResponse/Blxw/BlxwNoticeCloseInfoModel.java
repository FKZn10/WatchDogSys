package com.jpmsoft.common.model.requestAndResponse.Blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class BlxwNoticeCloseInfoModel extends BlxwNoticeInfoModel {
    private static final long serialVersionUID = 4356985530842266339L;
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
    //证明文件ids
    private String supportingDocumentFileIds;
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