package com.jpmsoft.projectProducer.pojo.entity.performanceClaim;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 索赔清单
 */
@Data
@TableName("Pc_Claim_Dtl")
public class PcClaimDtl {

    @TableId(type = IdType.INPUT)
    private String cdId;
    private String claimId;
    private String cdCId;
    private Integer cdType;
    private Integer cdDuration;
    private BigDecimal cdCost;
    private String cdRemark;
}
