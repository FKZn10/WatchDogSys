package com.jpmsoft.fileUpload.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/18 14:26
 */
@Data
public class ConBoqDtlVo implements Serializable {


    private static final long serialVersionUID = 6471210223229380961L;
    private String boqdId;
    private String boqdParentId;
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
    private String boqdBddId;
    private String bddName;
    private String boqdType;
    private Integer boqdSort;
    private List<ConBoqDtlVo> childNodes;
}
