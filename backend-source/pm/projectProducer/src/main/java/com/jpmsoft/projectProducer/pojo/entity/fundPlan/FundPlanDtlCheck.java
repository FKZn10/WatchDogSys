package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划表审核记录
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:01
 */
@Data
@TableName("Fund_Plan_Dtl_Check")
public class FundPlanDtlCheck {
    @TableId(type = IdType.INPUT)
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
    private String teId;
}
