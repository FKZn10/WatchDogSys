package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Con_Payfunds_Details")
public class ConPayFundsDetails {
    private String id;
    private String payId;
    private BigDecimal payFundAccountMoney;
    private String payFundAccount;
    private String payFundAccountNum;
}
