package com.jpmsoft.common.model.request.project;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金使用计划明细
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:42
 */
@Data

public class ProFundsUsageDetailsDto implements Serializable {


    private static final long serialVersionUID = 4864356851160300455L;
    private String id;
    private String cplId;
    private String fudYear;
    private BigDecimal fudInvComplated;
    private BigDecimal fudInvDemand;
    private String fudRemark;
    private Integer fudSort;

}
