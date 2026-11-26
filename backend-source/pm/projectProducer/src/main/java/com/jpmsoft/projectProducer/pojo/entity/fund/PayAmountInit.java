package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("PayAmount_Init")
public class PayAmountInit extends BaseEntity {
    private String id;
    private String pId;
    /**
     * PI_Fund_Project_Amount 项目已到位资金
     */
    private BigDecimal piFundProjectAmount;
    /**
     * PI_Group_Book_Balances_Amount 集团账面余额
     */
    private BigDecimal piGroupBookBalancesAmount;
    /**
     * PI_Member_Firms_Book_BalancesAmount 成员企业账面余额
     */
    private BigDecimal piMemberFirmsBookBalancesAmount;

    private Integer state;
}
