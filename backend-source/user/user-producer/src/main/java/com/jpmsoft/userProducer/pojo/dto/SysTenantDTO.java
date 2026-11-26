package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:35
 */
@Data
public class SysTenantDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 6100832964824899819L;

    private String teId;
    private String teName;
    private String teCode;
    private String teType;
    private String teNo;
    private String teBusinessLicense;
    private String teLogo;
    private String teIndustry;
    private String teSize;
    private String teAddress;
    private String teLegal;
    private String teContacts;
    private String teContactsTel;
    private String teChargeType;
    private Date teExpirationTime;
    private String teStatus;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date stateTime;
    private Integer state;
}
