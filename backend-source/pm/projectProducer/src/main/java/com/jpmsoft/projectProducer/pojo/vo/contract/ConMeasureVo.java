package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 计量报审表
 *
 * @author: 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Data
public class ConMeasureVo extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = -8899720953602435699L;

    private String id;
    private String conId;
    private String meaSupervisorId;
    private Date meaStartTime;
    private Date meaEndTime;
    private String meaType;
    private String meaCode;
    private String meaTitle;
    private String meaBook;
    private Integer meaBookPage;
    private String meaChangeNotice;
    private String meaContractorPm;
    private String meaContractorId;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String meaRemark;
    private BigDecimal meaSubItemAmount;
    private BigDecimal meaStepAmount;
    private BigDecimal meaUnitPriceAmount;
    private BigDecimal meaTotalPriceAmount;
    private BigDecimal meaOtherItemAmount;
    private BigDecimal meaFee;
    private BigDecimal meaTax;
    private BigDecimal meaLabour;
    private BigDecimal meaOtherAmount;
    private BigDecimal meaCompleted;
    private BigDecimal meaApplyAmt;
    private BigDecimal meaPeriodAmt;
    private BigDecimal meaCurrentlyAmt;
    private Float meaTaxProportion;
    private Float meaLabourProportion;
    private Float meaPayProportion;

    private String meaSupeName;
    private String meaSupeAbbName;
    private String meaContName;
    private String meaContAbbName;
    private String conName;
    private List<ConMeasureRecordVo> conMeasureRecords;
    private List<ConUnitProjectSummaryVo> conUnitProjectSummarys;

    @JsonProperty("pName")
    private String pName;

    private String ecChangeType;

    private String ecConstructionName;

}