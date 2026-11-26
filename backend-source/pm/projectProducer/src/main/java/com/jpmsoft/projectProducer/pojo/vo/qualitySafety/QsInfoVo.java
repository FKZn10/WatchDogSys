package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class QsInfoVo {
    private Integer sum;
    private Integer finishSum;
    private BigDecimal finishRate;
    //private List<DangerQsInfoVo> dangers;
}
