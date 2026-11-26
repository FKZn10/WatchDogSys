package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合同支付明细
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:38
 */
@Data
public class ConPayFundStatisticsDTO extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 4131471634003961611L;
    private String conId;
    private BigDecimal payApply;
    private BigDecimal payApproval;
    private BigDecimal payApplicationDate;
}
