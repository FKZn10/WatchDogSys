package com.jpmsoft.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 审计日志
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/25 20:53
 */
@Data
public class SysAuditLogDto implements Serializable {

    private String id;
    private String salContent;
    private String salType;
    private String salFormId;
    private String createUid;
    private String createUname;
    private Date createTime;

}
