package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayAmountInitChangeVo extends BaseFileDto {
    private String id;
    private String payAmountInitId;
    private String pId;
    //项目
    private String pName;
    private String projectName;
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
