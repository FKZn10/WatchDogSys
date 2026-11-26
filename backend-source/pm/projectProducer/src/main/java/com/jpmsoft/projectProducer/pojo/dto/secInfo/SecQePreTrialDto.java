package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资格预审
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:14
 */
@Data
public class SecQePreTrialDto extends BaseFileDto {

    private static final long serialVersionUID = -5612860890879200428L;

    private String secId;
    private String sqtValuationMethod;
    private BigDecimal sqtAmount;
    private String sqtRemarks;
    private String sqtOtherPricing;
}
