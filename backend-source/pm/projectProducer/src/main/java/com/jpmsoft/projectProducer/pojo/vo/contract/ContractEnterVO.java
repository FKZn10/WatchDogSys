package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/12/9 10:54
 * @Description:
 * @Version: 1.0
 */
@Data
public class ContractEnterVO {

    //项目ID
    private String projectId;

    //合同ID
    private String contractId;

    //支付金额
    private String contractPayMoney;
}
