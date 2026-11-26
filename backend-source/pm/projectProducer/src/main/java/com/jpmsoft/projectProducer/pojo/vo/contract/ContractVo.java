package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContractVo implements Serializable {

    /**
     * 合同id
     */
    private String contractId;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同编号
     */
    private String contractCode;
}
