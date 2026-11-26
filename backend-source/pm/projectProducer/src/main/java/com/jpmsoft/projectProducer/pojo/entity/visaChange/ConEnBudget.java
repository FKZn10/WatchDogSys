package com.jpmsoft.projectProducer.pojo.entity.visaChange;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程洽商施工方案及预算
 */
@Data
@TableName("Con_En_Budget")
public class ConEnBudget {

    @TableId(type = IdType.INPUT)
    private String enbId;
    /**
     * 变更洽商单ID
     */
    private String enisId;
    /**
     * 项目ID
     */
    private String pId;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 施工方ID
     */
    private String enbContractorId;
    /**
     * 施工方案上传
     */
    private String enbConstructionPlan;
    /**
     * 施工预算
     */
    private BigDecimal enbBudget;
    /**
     * 备注
     */
    private String enbRemark;
    private String enbCreateUid;
    private String enbCreateUname;
    private Date enbCreateTime;
    private Integer enbState;
    private Date enbStateTime;
    private String teId;

    private String enbSupervisorId;
    private BigDecimal enbSupervisorAmt;
    private BigDecimal enbMajorAmt;
    private String enbCostOpinion;
    private BigDecimal enbAuditAmt;
    private BigDecimal enbEstimateAmt;

    private BigDecimal enbNotContainAmt;
    private BigDecimal enbContainAmt;
    private BigDecimal enbConRate;
}
