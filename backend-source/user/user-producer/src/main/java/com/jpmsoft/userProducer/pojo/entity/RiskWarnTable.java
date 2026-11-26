package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Risk_Warn_Table")
public class RiskWarnTable {
    @TableId(type = IdType.INPUT)
    private String rwtId;
    private String rwiId;
    private String tableId;
    private String tableCode;
    private String tableField;
    private String rwtAssociationType;
    private String rwtAssociationTable;
    private String rwtAssociationField;
    private String rwtConditionalRelation;
    private String rwtCreateUname;
    private String rwtCreateUid;
    private Date rwtCreateTime;
    private String rwtOtherSet;

}
