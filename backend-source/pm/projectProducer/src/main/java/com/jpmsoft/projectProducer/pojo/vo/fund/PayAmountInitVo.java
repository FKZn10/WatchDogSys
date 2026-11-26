package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

@Data
public class PayAmountInitVo extends BaseFileDto {
    private String id;
    private String pId;
    //项目
    private String pName;
    private String projectName;
    //PI_Fund_Project_Amount 项目已到位资金
    private Double piFundProjectAmount;
    //PI_Group_Book_Balances_Amount 集团账面余额
    private Double piGroupBookBalancesAmount;
    //PI_Member_Firms_Book_BalancesAmount 成员企业账面余额
    private Double piMemberFirmsBookBalancesAmount;
}
