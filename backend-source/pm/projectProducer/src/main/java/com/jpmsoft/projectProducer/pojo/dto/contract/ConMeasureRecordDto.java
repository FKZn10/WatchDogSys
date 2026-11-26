package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.baomidou.mybatisplus.annotation.TableField;
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



    /**
     * 序号
     */
    @TableField(value = "mear_Order")
    private Integer mearOrder;
    /**
     * 施工单位申报单价
     */
    @TableField(value = "Mear_Con_Price")
    private Float mearConPrice;

    /**
     * 施工单位申报本期总价
     */
    @TableField(value = "Mear_Con_Total")
    private BigDecimal mearConTotal;

    /**
     * 监理单位审核工程量
     */
    @TableField(value = "Mear_Supervise_Amount")
    private Float mearSuperviseAmount;

    /**
     * 监理单位审核单价
     */
    @TableField(value = "Mear_Supervise_Price")
    private Float mearSupervisePrice;

    /**
     * 监理单位审核本期总价
     */
    @TableField(value = "Mear_Supervise_Total")
    private BigDecimal mearSuperviseTotal;

    /**
     * 全过程咨询单位审核工程量
     */
    @TableField(value = "Mear_Consult_Amount")
    private Float mearConsultAmount;

    /**
     * 全过程咨询单位审核单价
     */
    @TableField(value = "Mear_Consult_Price")
    private Float mearConsultPrice;

    /**
     * 全过程咨询单位审核本期总价
     */
    @TableField(value = "Mear_Consult_Total")
    private BigDecimal mearConsultTotal;
}