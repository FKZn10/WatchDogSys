package com.jpmsoft.gmgws.pojo.vo;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 接口管理
 *
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:34
 */
@Data
public class SysInterfaceDTO extends BaseModeRequest {

    private String id;
    private String ifUrl;
    private String ifPower;
    private String ifType;
    private String ifRecord;
    private String ifStatus;
    private String ifName;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Date updateTime;
    private String updateUid;
    private String updateUname;

    private List<SysInterfaceRequest> requests;
    private List<SysInterfaceResponse> responses;
}
