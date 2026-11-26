package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("PayAmount_Init_Change")
public class PayAmountInitChange extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6935759944886609109L;
    private String id;
    private String payAmountInitId;
    private String pId;
    //PI_Fund_Project_Amount 项目已到位资金
    private BigDecimal piFundProjectAmount;
    //PI_Group_Book_Balances_Amount 集团账面余额
    private BigDecimal piGroupBookBalancesAmount;
    //PI_Member_Firms_Book_BalancesAmount 成员企业账面余额
    private BigDecimal piMemberFirmsBookBalancesAmount;
    //PI_Fund_Project_Amount 项目已到位资金
    private BigDecimal piFundProjectAmountOld;
    //PI_Group_Book_Balances_Amount 集团账面余额
    private BigDecimal piGroupBookBalancesAmountOld;
    //PI_Member_Firms_Book_BalancesAmount 成员企业账面余额
    private BigDecimal piMemberFirmsBookBalancesAmountOld;
}
