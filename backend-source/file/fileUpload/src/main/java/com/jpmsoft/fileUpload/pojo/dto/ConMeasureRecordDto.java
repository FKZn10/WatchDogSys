package com.jpmsoft.fileUpload.pojo.dto;

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
public class ConMeasureRecordDto implements Serializable {

    private static final long serialVersionUID = -3114067607310656772L;

    /**
     * 计量单编号
     */
    private String mearCode;

    /**
     * 清单号
     */
    private String mearBoqdCode;

    /**
     * 图号及部位
     */
    private String mearPosition;

    /**
     * 计价要素
     */
    private String mearPriceElement;
    /**
     * 草图、几何尺寸及计算式
     */
    private String mearRemark;
    /**
     * 申报数量
     */
    private Float mearDeclareAmount;

    /**
     * 子目或单项名称
     */
    private String mearBoqdName;

    /**
     * 计量员
     */
    private String mearGauger;

    /**
     * 项目经理
     */
    private String mearContractorPm;
    /**
     * 计量时间
     */
    private Date mearVerbTime;
    /**
     * 措施费比率
     */
    private Float mearRate;
    /**
     * 规费比率
     */
    private Float mearFeesRatio;
    /**
     * 其他项目比率
     */
    private Float mearOtherRatio;
    /**
     * 税金比率
     */
    private Float mearTaxesRatio;
    /**
     * 计算公式
     */
    private String mearFormula;
    /**
     * 最终审核合价
     */
    private Float mearFinalAmount;
    /**
     * 最终审核合价
     */
    private BigDecimal mearFinalTotal;
}