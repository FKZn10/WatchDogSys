package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 调整事项清单
 */
@Data
@TableName("Inv_Adjustment_Dtl")
public class InvAdjustmentDtl {

    @TableId(type = IdType.INPUT)
    private String adId;
    private String iaId;
    private String adNum;
    private String adContent;
    private String adInfo;
    private BigDecimal adApplyAmount;
    private BigDecimal adReviewAmount;
    private String adRemark;
    private Integer adOrder;
    private String adParentId;
}
