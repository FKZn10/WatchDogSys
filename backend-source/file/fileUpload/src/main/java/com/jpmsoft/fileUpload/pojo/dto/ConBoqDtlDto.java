package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/18 14:26
 */
@Data
public class ConBoqDtlDto implements Serializable {

    private static final long serialVersionUID = -7090985818841466699L;
    private String boqdId;
    /**
     * 项目编码
     */
    private String boqdCode;
    /**
     * 父级编码
     */
    private String boqdParentCode;
    /**
     * 项目名称
     */
    private String boqdName;
    /**
     * 项目特征
     */
    private String boqdFeature;
    /**
     * 工程内容
     */
    private String boqdContent;
    /**
     * 计量单位
     */
    private String boqdUnit;
    /**
     * 工程量
     */
    private Float boqdAmount;
    /**
     * 综合单价
     */
    private BigDecimal boqdPrice;
    /**
     * 总价
     */
    private BigDecimal boqdTotalPrice;
    /**
     * 备注
     */
    private String boqdRemark;
    /**
     * 上级清单ID
     */
    private String boqdParentId;
    /**
     * 清单类别
     */
    private String boqdType;
    /**
     * 暂估价
     */
    private BigDecimal boqdProvisionalEstimate;
    /**
     * 措施费比率
     */
    private Float boqdRate;
    /**
     * 规费比率
     */
    private Float boqdFeesRatio;
    /**
     * 其他项目比率
     */
    private Float boqdOtherRatio;
    /**
     * 税金比率
     */
    private Float boqdTaxesRatio;
    /**
     * 计算公式
     */
    private String boqdFormula;
    private String boqdParentName;
    private String bddName;
}
