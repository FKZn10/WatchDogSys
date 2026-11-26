package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 认质认价清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:47
 */
@Data
public class MeQualityPriceDtlDto implements Serializable {

    private static final long serialVersionUID = 16868267215951898L;
    private String qpdId;
    private String qpId;
    private String qpdCode;
    private String qpdName;
    private String qpdStandardType;
    private Float qpdCount;
    private String qpdUnit;
    private String qpdDescription;
    private String qpdUseLocation;
    private Date qpdExpectEnterTime;
    private String qpdContractorBrandName;
    private BigDecimal qpdContractorPrice;
    private Date qpdCreateTime;
}
