package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

@Data
public class SKApiBodyDataT0001Response {
    /**
     * 账户编号
     */
    private String AccountNo;

    /**
     * 本方账户名称
     */
    private String AccountName;

    /**
     * 开户行名称
     */
    private String BranchName;

    /**
     * 流水号唯一标识
     */
    private String TransInfoNo;

    /**
     * 银行类别
     */
    private String BankType;

    /**
     * 币种编码
     */
    private String CurrencyType;

    /**
     * 交易金额
     */
    private String TransAmount;

    /**
     * 余额
     */
    private String TransBalance;

    /**
     * 收支方向
     */
    private Integer TransDirection;

    /**
     * 对方账号
     */
    private String OppAccountNo;

    /**
     * 对方账户名
     */
    private String OppAccountName;

    /**
     * 对方开户行
     */
    private String OppBankName;

    /**
     * 对方开户行地址
     */
    private String OppBankAddr;

    /**
     * 交易日期
     */
    private String TransTime;

    /**
     * 摘要
     */
    private String Memo;

    /**
     * 用途
     */
    private String UseInfo;

    /**
     * 附言
     */
    private String Remark;

    /**
     * 是否银行删除
     */
    private Integer DeleteByBank;

    /**
     * 银行流水号
     */
    private String BankTransNo;

    /**
     * 回单标识id
     */
    private String ReceiptId;

    /**
     * 付款单编号
     */
    private String PayOrderCode;

    /**
     * 应付单编号
     */
    private String VouchersPayCode;

    private String cleanAmount(String rawValue) {
        return rawValue == null ? null : rawValue.replace(",", "");
    }

    public void setTransAmount(String transAmount) {
        this.TransAmount = cleanAmount(transAmount);
    }

    public void setTransBalance(String transBalance) {
        this.TransBalance = cleanAmount(transBalance);
    }
}
