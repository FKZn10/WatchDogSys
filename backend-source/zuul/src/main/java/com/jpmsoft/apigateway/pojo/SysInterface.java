package com.jpmsoft.apigateway.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口管理
 *
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:34
 */
@Data
public class SysInterface implements Serializable {

    private static final long serialVersionUID = 573100093366838009L;

    private String id;
    private String ifUrl;
    private String ifPower;
    private String ifStatus;
    private String ifName;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Date updateTime;
    private String updateUid;
    private String updateUname;
    private String ifRecord;
}
