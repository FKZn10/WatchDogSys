package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金管理-来款认领子表
 *
 * @author tianhelin
 * @date 2025-11-24
 */
@Data
@TableName("Claim_Incoming_Funds_Dtl")
public class ClaimIncomingFundsDtl {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 主表Id
     */
    private String cifId;

    /**
     * 项目Id
     */
    private String pId;

    /**
     * 是否已开收据
     */
    private String cifdIsReceipt;

    /**
     * 收款Id
     */
    private String cifdReceipt;

    /**
     * 核销状态
     */
    private String cifdVerifyState;

    /**
     * 核销金额
     */
    private BigDecimal cifdAccount;

    /**
     * 集团已到位资金
     */
    private BigDecimal cifdGroupFunds;

    private Date createTime;
}