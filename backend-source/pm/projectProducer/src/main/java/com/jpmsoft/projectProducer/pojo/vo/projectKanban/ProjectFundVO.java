package com.jpmsoft.projectProducer.pojo.vo.projectKanban;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProjectFundVO implements Serializable {
    private BigDecimal invesToral;//投资总额
    private BigDecimal contractTotal;//合同总额
    private BigDecimal payTotal;//应付总金额
    private BigDecimal realPatTotal;//实付总额
}
