package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class ConMeasureDto extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = 5587607207054397777L;
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String conId;
    @JsonProperty("pId")
    private String pId;
    /**
     *
     */
    private String meaSupervisorId;

    /**
     *
     */
    private Date meaStartTime;

    /**
     *
     */
    private Date meaEndTime;

    /**
     *
     */
    private String meaType;

    /**
     *
     */
    private String meaCode;

    /**
     *
     */
    private String meaTitle;

    /**
     *
     */
    private String meaBook;

    /**
     *
     */
    private Integer meaBookPage;

    /**
     *
     */
    private String meaChangeNotice;

    /**
     *
     */
    private String meaContractorPm;

    /**
     *
     */
    private String meaContractorId;

    /**
     *
     */
    private String createUid;

    /**
     *
     */
    private String createUname;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private Date stateTime;

    /**
     *
     */
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


    private String meaBookIds;
    @JsonProperty("pName")
    private String pName;
    private String conName;

    private List<ConMeasureRecordDto> conMeasureRecords;
    private List<ConUnitProjectSummaryDto> conUnitProjectSummarys;
}