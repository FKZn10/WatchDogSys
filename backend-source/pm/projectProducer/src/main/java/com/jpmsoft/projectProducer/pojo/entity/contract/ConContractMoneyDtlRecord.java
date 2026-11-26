package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同金额明细变更记录
 */
@Data
@TableName("Con_Contract_Money_Dtl_Record")
public class ConContractMoneyDtlRecord {
    @TableId(type = IdType.INPUT)
    private String cmdId;
    private String conRecordId;
    private String cmdName;
    private BigDecimal cmdMoney;
    private String cmdRemark;
    private String cmdParentId;
    private Integer cmdSort;
    private Date cmdCreateTime;
}
