package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 概算-标段-合同关联表
 */
@Data
public class InvBudgetConSecModel extends BaseFileResponse implements Serializable {
    //概算ID
    private String budgetId;
    //概算父节点ID
    private String budgetParentId;
    //标段ID
    private String secId;
    //支付ID
    private String payId;
   //合同ID
   private String conId;
   //合同名称
    private  String conName;
    //合同金额
    private  BigDecimal conMoneyTotal;
    //合同预留金
    private BigDecimal conReserveMoney;

    //概算选用金额
    private BigDecimal selectAmount;

    //概算总金额
    private BigDecimal bddTotal;

    //概算总金额
    private String costName;

    //招标文件
    private String conBidDocument;

}
