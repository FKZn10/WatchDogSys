package com.jpmsoft.projectProducer.pojo.dto.visaChange;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/21 17:00
 */
@Data
@TableName("Con_En_Fee")
public class ConEnFeeDTO extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = -1431962244261339367L;
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

    private String enisId;
    private String conId;
    private String cefCode;
    private String cefReason;
    private String cefLocation;
    private BigDecimal cefBudgetAmt;
    private BigDecimal cefDeclaredAmt;
    private BigDecimal cefConRate;
    private String cefContractorId;
    private String cefSupervisorId;
    private String cefApplicant;
    private String cefPm;
    private Integer cefDuration;
    private String cefRemark;
    private String cefImplementation;

    private String conName;
    private String enisName;
    private String enisCode;
    private String enisProjectName;
    private String conCode;
    private String cefContractorName;
    private String cefSupervisorName;
    private String cefApplicantName;
    private BigDecimal conMoneyTotal;
    private String enisSubCode;
}
