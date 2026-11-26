package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同工程量清单详情
 */
@Data
public class ConBoqDtlDto implements Serializable {

    private static final long serialVersionUID = -7971187657897411793L;

    private String boqdId;
    /**
     * 主表ID
     */
    private String boqId;
    /**
     * 项目编码
     */
    private String boqdCode;
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
     * 状态
     */
    private Integer boqdState;
    /**
     * 上级清单ID
     */
    private String boqdParentId;
    /**
     * 排序号
     */
    private Integer boqdSort;
    /**
     * 所属项目概算科目表ID
     */
    private String boqdBddId;
    /**
     * 创建人ID
     */
    private String boqdCreateUid;
    /**
     * 创建人姓名
     */
    private String boqdCreateUname;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdCreateTime;
    private String boqdType;
    private BigDecimal boqdProvisionalEstimate;
    private Float boqdRate;
    private String boqdXh;
    private BigDecimal boqdAqsg;
    private BigDecimal boqdGf;
    private String boqdJsjc;
    private BigDecimal boqdQfjs;
    private String boqdJsgz;
    private BigDecimal boqdRgf;
    private BigDecimal boqdZcf;
    private BigDecimal boqdSbf;
    private BigDecimal boqdFcf;
    private BigDecimal boqdClf;
    private BigDecimal boqdJxf;
    private BigDecimal boqdGlf;
    private BigDecimal boqdLr;
    private BigDecimal boqdZgr;
    private BigDecimal boqdRcjhl;
    private String boqdRcjId;
    private BigDecimal boqdJzDj;
    private BigDecimal boqdTbDj;
    private BigDecimal boqdFxxs;
    private String boqdFormula;
    private Float boqdFeesRatio;
    private Float boqdOtherRatio;
    private Float boqdTaxesRatio;

    //region  事项变更申请
    private String id;
    private String ecId;
    private String pageType;
    private Float boqdChangeAmount;
    //endregion
}
