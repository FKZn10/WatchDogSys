package com.jpmsoft.projectConsumer.pojo.vo.contract;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ConPayDisbursementVo {

    private String payId;  //id

    private String panyId;  //单位id
    private String cName;  //单位名(合同乙方)
    private String pName;   //项目名
    private String conName;  //合同名
    private String conType;  //合同类型
    private BigDecimal conMoneyTotal;  //合同总额
    private BigDecimal pFinal;   //决算额
    private BigDecimal payApply;  //本次支付金额
    private BigDecimal payTotal;  //积累拨付(含本次)
    private String dName;  //主管部门
    private String conPartyAAgent;  //甲方联系人

    private LocalDateTime PayStateTime;

    private LocalDateTime PayCreateTime;

    //本次拨付总额
    private BigDecimal bursementAmtTotal;


    //本次拨付
    private BigDecimal thisAllocationAmt = BigDecimal.ZERO;

}
