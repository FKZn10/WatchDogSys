package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/6 17:52
 */
@Data
public class SysApplicationDTO extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = 3310743919397594248L;
    private String appId;
    private String appName;
    private String appCode;
    private String appType;
    private String appStatus;
    private String appRemark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;
    private String appIcon;

    private List<SysApplicationPowerDTO> sysApplicationPowers;

    private List<SysApplicationPriceDTO> sysApplicationPrices;
}
