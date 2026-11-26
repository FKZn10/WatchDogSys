package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量单
 *
 * @author: 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Data
public class ConMeasureRecordDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -162823030291851214L;

    /**
     *
     */
    private String id;

    /**
     *
     */
    private String meaId;

    /**
     *
     */
    private String mearCode;

    /**
     *
     */
    private String mearBoqdCode;

    /**
     *
     */
    private String mearBoqdUnit;

    /**
     *
     */
    private String mearPosition;

    /**
     *
     */
    private String mearPriceElement;

    /**
     *
     */
    private Float mearDeclareAmount;

    /**
     *
     */
    private String mearBoqdName;

    /**
     *
     */
    private String mearDraft;

    /**
     *
     */
    private String mearDraftSupervisor;

    /**
     *
     */
    private Integer mearAnnexNumber;

    /**
     *
     */
    private Integer mearAnnexPage;

    /**
     *
     */
    private Float mearFinalAmount;

    /**
     *
     */
    private String mearGauger;

    /**
     *
     */
    private String mearContractorPm;

    /**
     *
     */
    private String mearContractorId;

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
    private Date stateTime;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private String mearRemark;
    private String conId;
    @JsonProperty("pId")
    private String pId;
    private Date mearVerbTime;
    private BigDecimal mearDeclaredTotal;
    private BigDecimal mearFinalTotal;
    private Float mearUpperAmount;
    private BigDecimal mearUpperTotal;
    private Float mearCumulativeAmount;
    private BigDecimal mearCumulativeTotal;
    private String boqdId;
    private Float mearRate;
    private String mearFormula;
    private Float mearFeesRatio;
    private Float mearOtherRatio;
    private Float mearTaxesRatio;

    private String mearDraftIds;
    private String mearDraftSupervisorIds;
}