package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

import java.util.Date;

/**
 * 安全责任岗位职责明细数据传输对象。
 */
@Data
public class SafeResponsibilityPostDutyDTO {
    private String id;
    private String postId;
    private String dutyContent;
    private Integer sortNo;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
}
