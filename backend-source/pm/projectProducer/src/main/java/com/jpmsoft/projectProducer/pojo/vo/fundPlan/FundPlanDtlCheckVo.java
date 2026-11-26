package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划表审核记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:01
 */
@Data

public class FundPlanDtlCheckVo implements Serializable {


    private static final long serialVersionUID = -4526893441670861507L;
    private String fpdcId;
    private String fpdId;
    private BigDecimal fpdcDemand;
    private Float fpdcDemandRatioTotal;
    private String fpdcFile;
    private Date fpdcTime;
    private String fpdcUid;
    private String fpdcUname;
    private String fpdcCid;
    private String fpdcExplain;
    private String fpdcNodeName;

    private String fpdcCName;
    private String fpdcCAbbName;
}
