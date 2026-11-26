package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更计量报审单
 *
 */
@TableName(value = "Con_Ec_Fee_Record")
@Data
public class ConEcFeeRecord {
    /**
     *
     */
    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     *
     */
    @TableField(value = "Mea_Id")
    private String meaId;

    /**
     *
     */
    @TableField(value = "Mear_Code")
    private String mearCode;

    /**
     *
     */
    @TableField(value = "Mear_Boqd_Code")
    private String mearBoqdCode;

    /**
     * 施工单位申报 - 单位
     */
    @TableField(value = "Mear_Boqd_Unit")
    private String mearBoqdUnit;

    /**
     *
     */
    @TableField(value = "Mear_Position")
    private String mearPosition;

    /**
     *
     */
    @TableField(value = "Mear_Price_Element")
    private String mearPriceElement;

    /**
     * 本期实际工程量
     */
    @TableField(value = "Mear_Declare_Amount")
    private Float mearDeclareAmount;

    /**
     * 子目或单项目名称
     */
    @TableField(value = "Mear_Boqd_Name")
    private String mearBoqdName;

    /**
     *
     */
    @TableField(value = "Mear_Draft")
    private String mearDraft;

    /**
     *
     */
    @TableField(value = "Mear_Draft_Supervisor")
    private String mearDraftSupervisor;

    /**
     *
     */
    @TableField(value = "Mear_Annex_Number")
    private Integer mearAnnexNumber;

    /**
     *
     */
    @TableField(value = "Mear_Annex_Page")
    private Integer mearAnnexPage;

    /**
     *
     */
    @TableField(value = "Mear_Final_Amount")
    private Float mearFinalAmount;

    /**
     *
     */
    @TableField(value = "Mear_Gauger")
    private String mearGauger;

    /**
     *
     */
    @TableField(value = "Mear_Contractor_Pm")
    private String mearContractorPm;

    /**
     *
     */
    @TableField(value = "Mear_Contractor_Id")
    private String mearContractorId;

    /**
     *
     */
    @TableField(value = "Create_Uid")
    private String createUid;

    /**
     *
     */
    @TableField(value = "Create_Uname")
    private String createUname;

    /**
     *
     */
    @TableField(value = "Create_Time")
    private Date createTime;

    /**
     *
     */
    @TableField(value = "State_Time")
    private Date stateTime;

    /**
     *
     */
    @TableField(value = "State")
    private Integer state;

    /**
     *
     */
    @TableField(value = "Mear_Remark")
    private String mearRemark;

    @TableField(value = "Con_Id")
    private String conId;

    @TableField(value = "P_Id")
    private String pId;

    @TableField(value = "Mear_Verb_Time")
    private Date mearVerbTime;

    @TableField(value = "Mear_Declared_Total")
    private BigDecimal mearDeclaredTotal;
    @TableField(value = "Mear_Final_Total")
    private BigDecimal mearFinalTotal;

    /**
     * 累计工程量
     */
    @TableField(value = "Mear_Upper_Amount")
    private Float mearUpperAmount;
    @TableField(value = "Mear_Upper_Total")
    private BigDecimal mearUpperTotal;
    @TableField(value = "Mear_Cumulative_Amount")
    private Float mearCumulativeAmount;
    @TableField(value = "Mear_Cumulative_Total")
    private BigDecimal mearCumulativeTotal;
    @TableField(value = "Boqd_Id")
    private String boqdId;
    @TableField(value = "Mear_Rate")
    private Float mearRate;
    @TableField(value = "Mear_Formula")
    private String mearFormula;
    private Float mearFeesRatio;
    private Float mearOtherRatio;
    private Float mearTaxesRatio;

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