package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代建合同实拨
 */
@Data
@TableName("Agent_Actual_Allocation")
public class AgentActualAllocation {

    @TableId(type = IdType.INPUT)
    private String aalId;
    private String conId;
    private String pId;
    private String aalConCode;
    private BigDecimal aalConMoney;
    private BigDecimal aalAppropriated;
    private BigDecimal aalThisPay;
    private String aalApplicant;
    private Date aalApplyTime;
    private String aalContacts;
    private String aalContactTelephone;
    private String aalAgentConId;
    private String aalAgentConCode;
    private String aalAgentConType;
    private String aalPayType;
    private String aalCollectionAccount;
    private String aalReceivingBank;
    private String aalCollectionNumber;
    private String aalCollectionAccountG;
    private String aalReceivingBankG;
    private String aalCollectionNumbe;
    private String aalGuaranteeFile;
    private String aalLiquidatedDamages;
    private BigDecimal aalConApply;
    private BigDecimal aalConExamine;
    private BigDecimal aalLastTotalPay;
    private BigDecimal aalLastTotalPayExa;
    private BigDecimal aalLastTotalPayL;
    private BigDecimal aalLastTotalPayLexa;
    private BigDecimal aalViolateMoney;
    private BigDecimal aalViolateMoneyExa;
    private BigDecimal aalThisPayMoney;
    private BigDecimal aalThisPayMoneyExa;
    private BigDecimal aalSurplusMoney;
    private BigDecimal aalSurplusMoneyExa;
    private Date aalCreateTime;
    private String aalCreateUid;
    private String aalCreateUname;
    private Integer aalState;
    private String aalStateTime;
    private BigDecimal aalSurplusMoneyAgent;
    private BigDecimal aalLastTotalReal;
    private String teId;
    //是否结算款 1：是，0：否
    private Integer aalPaySettled;
    //合同结算价
    private BigDecimal aalConSettlementPrice;
}
