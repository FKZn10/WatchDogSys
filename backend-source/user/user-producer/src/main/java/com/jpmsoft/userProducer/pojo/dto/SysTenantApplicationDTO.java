package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:44
 */
@Data
public class SysTenantApplicationDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -9050483591427337081L;

    private String taId;
    private String appId;
    private String teId;

    private String appName;
    private String appCode;
    private String appType;
    private String teName;
    private String teCode;
    private String teType;
    private String teChargeType;
    private Date teExpirationTime;

}
