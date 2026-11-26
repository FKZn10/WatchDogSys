package com.jpmsoft.common.model.requestAndResponse.Qs;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class QsTypeInfoModel extends QsTypeModel{
    //问题数量、整改数量、整改率
    private Integer problemCount;
    private Integer rectifiedProblemCount;
    private BigDecimal rectifiedProblemRate;
}
