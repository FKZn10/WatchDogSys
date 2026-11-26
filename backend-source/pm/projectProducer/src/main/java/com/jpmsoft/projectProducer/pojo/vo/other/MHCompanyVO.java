package com.jpmsoft.projectProducer.pojo.vo.other;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MHCompanyVO {

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 单位类型
     */
    private String companyType;

    /**
     * 单位联系人
     */
    private String contactPerson;

    /**
     * 联系人方式
     */
    private String contactPersonTel;

    /**
     * 累计合同暂估价
     */
    private BigDecimal conEstimatedAmountTotal;

    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 使用合同暂估价（合同总金额）
     */
    private BigDecimal usedContractMoney;

    /**
     * 合同支付金额（已支付金额）
     */
    private BigDecimal paidContractMoney;

    /**
     * 剩余合同暂估价
     */
    private BigDecimal remainingContractMoney;

    /**
     * 单位ID
     */
    private String companyId;
}
