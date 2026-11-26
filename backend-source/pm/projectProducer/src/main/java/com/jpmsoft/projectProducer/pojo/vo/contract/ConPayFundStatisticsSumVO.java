package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:39
 */
@Data
public class ConPayFundStatisticsSumVO implements Serializable {

    private static final long serialVersionUID = -1856309552618664234L;
    private BigDecimal payApplySum;
    private BigDecimal payApprovalSum;
    private BigDecimal payApplyOwnerSum;
    private List<ConPayFundStatisticsVO> data;
}
