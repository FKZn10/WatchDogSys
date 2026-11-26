package com.jpmsoft.common.model.request.Construction;

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
public class CompletionAcceptanceContractDto implements Serializable {

    private static final long serialVersionUID = 437330128465377030L;
    private String cacId;
    private String caId;
    private String conId;
    private BigDecimal cacSettlementMoney;
    private String cacRemarks;
}
