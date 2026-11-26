package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:30
 */
@Data
@TableName("Con_Sv_Budget")
public class ConSvBudget extends BaseEntity {

    private String svisId;
    private String pId;
    private String conId;
    private String csbCode;
    private Date csbTime;
    private String csbContractorId;
    private BigDecimal csbContractorAmt;
    private String csbSupervisorId;
    private BigDecimal csbSupervisorAmt;
    private BigDecimal csbMajorAmt;
    private String csbCostOpinion;
    private BigDecimal csbAuditAmt;
    private BigDecimal csbEstimateAmt;
    private String csbRemark;
    private String teId;

    private BigDecimal csbNotContainAmt;
    private BigDecimal csbContainAmt;
    private BigDecimal csbConRate;
}
