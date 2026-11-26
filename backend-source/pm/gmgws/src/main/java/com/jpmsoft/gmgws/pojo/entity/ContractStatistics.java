package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Contract_Statistics")
public class ContractStatistics {
    @TableId(value = "id", type = IdType.INPUT)
    private String id; // 主键ID

    @TableField(value = "Create_Uid")
    private String createUid; // 创建者的唯一标识

    @TableField(value = "Create_Uname")
    private String createUname; // 创建者的名字

    @TableField(value = "Create_Time")
    private Date createTime; // 创建时间

    @TableField(value = "State")
    private Integer state; // 状态

    @TableField(value = "State_Time")
    private Date stateTime; // 状态更改时间

    @TableField(value = "Con_Id")
    private String conId; // 合同ID

    @TableField(value = "Cs_Money")
    private BigDecimal csMoney; // 统计金额

    @TableField(value = "Cs_Settlemen_Downgrading")
    private BigDecimal csSettlemenDowngrading;

    @TableField(value = "Cs_Payment_Method")
    private String csPaymentMethod;
}
