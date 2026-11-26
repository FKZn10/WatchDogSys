package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDtlDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金拨付申请清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:14
 */
@Data
public class FundAppropriationDtlVo extends FundAppropriationDtlDto implements Serializable {

    private static final long serialVersionUID = 3025426280630391150L;

    private String conName;
    private String conCode;
    private String conType;
    private BigDecimal conMoneyTotal;
    private String conPayClause;
    private Date faDate;
    private String createUname;
    private String faCode;
}
