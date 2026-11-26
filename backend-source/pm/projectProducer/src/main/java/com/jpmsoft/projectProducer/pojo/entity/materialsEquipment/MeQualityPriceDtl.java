package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 认质认价清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:47
 */
@Data
@TableName("Me_Quality_Price")
public class MeQualityPriceDtl {

    @TableId(type = IdType.INPUT)
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
