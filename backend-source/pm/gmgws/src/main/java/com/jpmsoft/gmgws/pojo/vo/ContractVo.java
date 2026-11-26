package com.jpmsoft.gmgws.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContractVo {

    private String createUid; // 创建者的唯一标识

    private String createUname; // 创建者的名字


    private Date createTime; // 创建时间

    private Integer state; // 状态

    private Date stateTime;

    private String conId; // 合同ID

    private BigDecimal csMoney; // 统计金额

    private BigDecimal csSettlemenDowngrading;

    private String csPaymentMethod;

}
