package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 见证送检清单
 */
@Data
@TableName("Me_Witness_Dtl")
public class MeWitnessDtl {

    @TableId(type = IdType.INPUT)
    private String wdId;
    private String wiId;
    private String eadId;
    private String wdCode;
    private Integer wdSampleGroups;
    private String wdItem;
    private String wdTestBasis;
    private String wdEntrustedTime;
    private String wdStipulateSampleSize;
    private String wdActualSampleSize;
    private String wdActualPlace;
    private String wdActualInspectionLot;
    private String wdRemark;
    private Date wdCreateTime;
    private String wdName;
    private String wdSupplierName;
    private String wdBrandName;
    private String wdStandardType;
    private Float wdCount;
    private String wdUnit;

    private Integer wdQuantity;
    private String wdFormula;
    private BigDecimal wdAmount;
    private String wdResult;
    private String wdResRemark;
}
