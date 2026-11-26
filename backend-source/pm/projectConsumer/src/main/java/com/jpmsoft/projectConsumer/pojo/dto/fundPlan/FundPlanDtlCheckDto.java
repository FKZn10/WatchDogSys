package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.jpmsoft.common.model.BaseModeRequest;
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

public class FundPlanDtlCheckDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3332900244746788885L;

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

    private String fpdcFileIds;
}
