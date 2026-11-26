package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 22:30
 */
@Data
public class SysAuditLogVo implements Serializable {

    private static final long serialVersionUID = -8699848137224203140L;
    private String id;
    private String salContent;
    private String salType;
    private String salFormId;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String fileTitle;
}
