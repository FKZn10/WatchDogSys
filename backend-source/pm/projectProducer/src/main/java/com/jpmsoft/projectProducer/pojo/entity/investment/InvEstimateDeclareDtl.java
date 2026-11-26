package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 估算申报清单
 */
@Data
@TableName("Inv_Estimate_Declare_Dtl")
public class InvEstimateDeclareDtl {

    @TableId(type = IdType.INPUT)
    private String eddId;
    private String edId;
    private String eddNumber;
    private String eddParentId;
    private String eddCode;
    private String eddName;
    private String eddUnit;
    private Float eddCount;
    private BigDecimal eddPrice;
    private BigDecimal eddTotal;
    private String eddRemark;
    private Integer eddOrder;
    private Integer eddLevel;
    private Integer eddType;
    private Integer eddState;
    private String eddPath;
    private Integer eddLeafNode;

}
