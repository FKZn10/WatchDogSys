package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同计量计价支付申请清单
 */
@Data
@TableName("contract_payment_application")
public class ContractPaymentApplication {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String serverItem;
    private String serviceType;
    private BigDecimal contractPrice;
    private String payRule;
    private BigDecimal approvedEstimateAmount;
    private BigDecimal amountAsOfPrevious;
    private BigDecimal currentPlannedPaymentAmount;
    private BigDecimal cumulativeAmountPayable;
    private BigDecimal cumulativeContractPaymentRatio;
    private String contractMeasurementValuationId;
    private String requestDescription;
}
