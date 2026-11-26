package com.jpmsoft.common.model.requestAndResponse.JpmOa.contract;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerServiceFeeModel extends BaseFileDto {
    private static final long serialVersionUID = -2768916001723502114L;
    /**
     *
     */
    private String id;

    /**
     * 金额
     */
    private BigDecimal amountTotal;

    /**
     * 已使用金额
     */
    private BigDecimal amountUsed;

    /**
     * 可使用金额
     */
    private BigDecimal amountUnused;
    /**
     * 备注
     */
    private String remark;

    private List<CustomerServiceFeeDetailModel> detailModels;
    private List<CustomerServiceFeeUseDetailModel> useDetailModels;
}
