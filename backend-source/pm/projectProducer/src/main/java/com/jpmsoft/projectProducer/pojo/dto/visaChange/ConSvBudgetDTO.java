
package com.jpmsoft.projectProducer.pojo.dto.visaChange;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:30
 */
@Data
public class ConSvBudgetDTO extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -5760669049062390739L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String svisId;
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

    private String svisName;
    private String svisCode;
    private String svisProjectName;
    private String svisSubCode;
    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String csbContractorName;
    private String csbSupervisorName;
}
