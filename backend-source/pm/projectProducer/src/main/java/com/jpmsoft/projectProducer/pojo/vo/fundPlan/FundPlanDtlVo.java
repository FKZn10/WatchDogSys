package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划清单表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 11:59
 */
@Data
public class FundPlanDtlVo extends FundPlanDtlDto implements Serializable {

    private static final long serialVersionUID = -7797374995730821663L;

    private String conName;
    private String conCode;
    private String conType;
    private BigDecimal conMoneyTotal;
    private String conPayClause;
    private Date fpDate;
    private String createUname;
    private String fpCode;
    private String fpdPayeeName;
}
