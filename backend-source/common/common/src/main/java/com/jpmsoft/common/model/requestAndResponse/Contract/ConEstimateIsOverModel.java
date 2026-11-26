package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同是否超概
 */
@Data
public class ConEstimateIsOverModel extends BaseFileRequest {

    private String type;
    private BigDecimal Amount;

}
