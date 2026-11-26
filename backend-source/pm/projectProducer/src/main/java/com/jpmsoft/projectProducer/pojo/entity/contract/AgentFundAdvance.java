package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代建资金预拨
 */
@Data
@TableName("Agent_Fund_Advance")
public class AgentFundAdvance {

    @TableId(type = IdType.INPUT)
    private String afaId;
    private String pId;
    private String conId;
    private String conContractCode;
    private BigDecimal conContractMoney;
    private BigDecimal afaEstimatedAmount;
    private BigDecimal afaLastTotalMoney;
    private BigDecimal afaThisMoney;
    private BigDecimal afaSurplusMoney;
    private String afaApplicationBatch;
    private String afaApplicationDepartment;
    private Date afaApplicationTime;
    private String afaCreateUid;
    private String afaCreateUname;
    private Date afaCreateTime;
    private Integer afaState;
    private Date afaStateTime;
    private BigDecimal afaCapitalSurplus;
    private BigDecimal afaUnpaid;
    private String teId;
}
