package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金计划申请明细
 */
@Data
@TableName("Fund_Scheme_Apply_Dtl")
public class FundSchemeApplyDtl {


    @TableId(type = IdType.INPUT)
    private String fsadId;
    private String fsaId;
    private String conId;
    private BigDecimal fsadHaveApplied;
    private BigDecimal fsadCurrentApplied;
    private BigDecimal fsadRemaining;
    private String fsadFile;
}
