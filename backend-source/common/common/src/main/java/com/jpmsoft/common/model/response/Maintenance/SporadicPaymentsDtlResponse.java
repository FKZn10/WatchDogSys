package com.jpmsoft.common.model.response.Maintenance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SporadicPaymentsDtlResponse {

    private String id;

    /**
     * 保修项目名称
     */
    private String spDName;

    /**
     * 报修项目id
     */
    private String spDPid;

    /**
     * 流水号
     */
    private String spDNuber;

    /**
     * 审核价
     */
    private BigDecimal spDAuditprice;

    /**
     * 备注
     */
    private String spDRemarks;

    /**
     * 关联结算单id
     */
    private String spDId;

    /**
     * 关联主表id
     */
    private String spPDid;
}
