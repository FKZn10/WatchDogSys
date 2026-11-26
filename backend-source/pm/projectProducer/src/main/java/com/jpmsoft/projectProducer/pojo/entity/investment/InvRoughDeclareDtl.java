package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 匡算申报清单
 */
@Data
@TableName("Inv_Rough_Declare_Dtl")
public class InvRoughDeclareDtl {

    @TableId(type = IdType.INPUT)
    private String rddId;
    private String rdId;
    private String rddNumber;
    private String rddParentId;
    private String rddCode;
    private String rddName;
    private String rddUnit;
    private Float rddCount;
    private BigDecimal rddPrice;
    private BigDecimal rddTotal;
    private String rddRemark;
    private Integer rddOrder;
    private Integer rddLevel;
    private Integer rddType;
    private Integer rddState;
    private String rddPath;
    private Integer rddLeafNode;

}
