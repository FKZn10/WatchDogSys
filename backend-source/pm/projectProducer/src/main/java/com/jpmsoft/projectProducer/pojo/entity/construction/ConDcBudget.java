package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 施工变更预算
 */
@Data
@TableName("Con_Dc_Budget")
public class ConDcBudget {

    @TableId(type = IdType.INPUT)
    private String dcbId;
    private String dcoId;
    private String pId;
    private String conId;
    private String dcbType;
    private BigDecimal dcbAmount;
    private String dcbRemark;
    private String dcbCreateUid;
    private String dcbCreateUname;
    private Date dcbCreateTime;
    private Integer dcbState;
    private Date dcbStateTime;
    private String teId;

    private String dcbSupervisorId;
    private String dcbContractorId;
    private BigDecimal dcbSupervisorAmt;
    private BigDecimal dcbContractorAmt;
    private BigDecimal dcbMajorAmt;
    private BigDecimal dcbCostAmt;
    private BigDecimal dcbAuditAmt;
    private BigDecimal dcbEstimateAmt;

    private BigDecimal dcbNotContainAmt;
    private BigDecimal dcbContainAmt;
    private BigDecimal dcbConRate;
}
