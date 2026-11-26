package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Risk_Warn_Table_Filter")
public class RiskWarnTableFilter {
    @TableId(type = IdType.INPUT)
    private String rwtfId;
    private String rwtId;
    private String rwiId;
    private String rwtfConditionType;
    private String rwtfComparisonType;
    private String rwtfTableField;
    private String rwtfComparisonProcessing;
    private Integer rwtfComparisonPercentage;
    private String rwtfComparator;
    private String rwtfAssociationTable;
    private String rwtfComparedValue;
    private String rwtfComparedProcessingCover;
    private Integer rwtfComparisonPercentageCover;
    private Date rwtfCreateTime;


}
