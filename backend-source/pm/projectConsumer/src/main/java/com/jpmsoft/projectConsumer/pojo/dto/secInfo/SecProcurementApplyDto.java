package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 招标采购申请
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:16
 */
@Data
public class SecProcurementApplyDto extends BaseFileDto {

    private static final long serialVersionUID = 3257326870638311187L;

    private String secId;
    private String spaValuationMethod;
    private BigDecimal spaAmount;
    private String spaRemarks;
    private String spaOtherPricing;
}
