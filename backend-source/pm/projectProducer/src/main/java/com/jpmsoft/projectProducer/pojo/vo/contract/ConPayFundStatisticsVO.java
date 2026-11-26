package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同支付明细
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:38
 */
@Data
public class ConPayFundStatisticsVO implements Serializable {
    private static final long serialVersionUID = 4131471634003961611L;
    private String payId;
    private String payType;
    private String payTypeDetailed;
    private BigDecimal payApply;
    private BigDecimal payApproval;
    private BigDecimal payApplyOwner;
    private Date payApplicationDate;
}
