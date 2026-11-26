package com.jpmsoft.common.model.requestAndResponse.JpmOa.contract;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class UnpredictableFeeModel extends BaseFileDto {
    private static final long serialVersionUID = -537032662504487336L;
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

    private List<UnpredictableFeeDetailModel> detailModels;
    private List<UnpredictableFeeUseDetailModel> useDetailModels;
}
