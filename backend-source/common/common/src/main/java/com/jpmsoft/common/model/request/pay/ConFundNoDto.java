package com.jpmsoft.common.model.request.pay;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同项目经费号
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/15 14:32
 */
@Data
public class ConFundNoDto implements Serializable {

    private static final long serialVersionUID = 2004261218859036178L;

    private String id;
    private String payId;
    private String fnCode;
    private BigDecimal fnMoney;
    private String fnRemark;
    private Integer fnSort;
    private Date createTime;
    private String fnFundCode;
}
