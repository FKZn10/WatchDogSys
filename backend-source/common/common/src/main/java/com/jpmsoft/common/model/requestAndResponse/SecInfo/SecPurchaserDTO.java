package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 采购人代表信息
 * 其他采购子表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/8 16:54
 */
@Data
public class SecPurchaserDTO implements Serializable {

    private static final long serialVersionUID = -3889692996738158777L;

    private String id;
    private String sopId;
    private String spName;
    private String spTechTitle;
    private String spDepartment;
    private String spPhone;
    private Boolean spStatute;
    private Boolean spExperience;
    private Integer spSort;
}
