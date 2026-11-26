package com.jpmsoft.common.model.response.Construction;

import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceContractDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 工程验收——合同清单信息记录表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:34
 */
@Data
public class CompletionAcceptanceContractVo extends CompletionAcceptanceContractDto implements Serializable {

    private static final long serialVersionUID = 6688162637099160394L;

    private String conName;
    private BigDecimal conMoneyTotal;
    private String conPricingMethod;
    private String conOtherValuation;
}
