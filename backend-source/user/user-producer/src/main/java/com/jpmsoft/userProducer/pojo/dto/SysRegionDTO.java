package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 9:42
 */
@Data
public class SysRegionDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -4121031180476728172L;
    private String id;
    private String srCode;
    private String srParentCode;
    private Integer srLevel;
    private String srName;
    private String srProvince;
    private String srCity;
    private String srDistrict;
    private String srTown;
    private String srQp;
    private String srJp;
    private String srFirstChar;
    private BigDecimal srLat;
    private BigDecimal srLng;
    private Integer srTel;
    private Integer srZip;
    private Integer srStatus;

    private String key;
    private String title;
    private String value;
    private List<SysRegionDTO> children;
}
