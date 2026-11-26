package com.jpmsoft.common.model.response.pay;

import lombok.Data;

/**
 * 支付填写清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/5 14:50
 */
@Data
public class PayFieldDtlVo {

    private String id;
    private String pfId;
    private String pfName;
    private String pfValue;
    private String pfCode;
    private String pfFormula;
    private String pfVerification;
    private String pfSort;
    private String pfWrite;
//    private Boolean pfVerificationPass;
//    private String createUid;
//    private String createUname;
//    private Date createTime;

}
