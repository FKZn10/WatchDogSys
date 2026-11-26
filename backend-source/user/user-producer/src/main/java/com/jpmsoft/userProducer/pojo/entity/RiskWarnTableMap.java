package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Risk_Warn_Table_Map")
public class RiskWarnTableMap {
    @TableId(type = IdType.INPUT)
    private String rwtmId;
    private String rwiId;
    private String rwtmField;
    private String rwtmType;
    private String rwtmMappingValue;
    private Date rwtmCreateTime;

}
