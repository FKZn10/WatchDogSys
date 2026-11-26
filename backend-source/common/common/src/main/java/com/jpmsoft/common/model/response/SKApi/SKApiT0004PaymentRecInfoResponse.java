package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

/**
 * 收款记录信息
 */
@Data
public class SKApiT0004PaymentRecInfoResponse {

    /**
     * 收款方单位编号
     */
    private String receiveOpenOrgCode;

    /**
     * 收款方单位名称
     */
    private String receiveOpenOrgName;

    /**
     * 收款方银行账号
     */
    private String receiveAccountNo;
    /*  */
    /**
     * 收款方账户名称
     */
    private String receiveAccountName;

    /**
     * 收款方所属省
     */
    private String receiveAreaNameOfProvince;

    /**
     * 收款方所属市
     */
    private String receiveAreaNameOfCity;

    /**
     * 收款方开户行名称
     */
    private String receiveBranchName;

    /**
     * 收款方开户行行号
     */
    private String receiveBranchCode;

    /**
     * 收款方性质
     */
    private String receiveNature;

    /**
     * 收款方币种
     */
    private String receiveCurrencyType;

    /**
     * 收款方币种名称
     */
    private String receiveCurrencyName;
}
