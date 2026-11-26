package com.jpmsoft.common.model.requestAndResponse.ConBoq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单变更详情
 */
@Data
public class ConBoqDtlChangeDto implements Serializable {

    private static final long serialVersionUID = -3127604589213047851L;

    private String boqdcId;
    /**
     * 主表ID
     */
    private String boqcId;
    /**
     * 项目编码
     */
    private String boqdcCode;
    /**
     * 项目名称
     */
    private String boqdcName;
    /**
     * 项目特征
     */
    private String boqdcFeature;
    /**
     * 工程内容
     */
    private String boqdcContent;
    /**
     * 计量单位
     */
    private String boqdcUnit;
    /**
     * 工程量
     */
    private Float boqdcAmount;
    /**
     * 综合单价
     */
    private BigDecimal boqdcPrice;
    /**
     * 总价
     */
    private BigDecimal boqdcTotalPrice;
    /**
     * 备注
     */
    private String boqdcRemark;
    /**
     * 状态
     */
    private Integer boqdcState;
    /**
     * 上级清单ID
     */
    private String boqdcParentId;
    /**
     * 排序号
     */
    private Integer boqdcSort;
    /**
     * 所属项目概算科目表ID
     */
    private String boqdcBddId;
    /**
     * 创建人ID
     */
    private String boqdcCreateUid;
    /**
     * 创建人姓名
     */
    private String boqdcCreateUname;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdcCreateTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdcUpdateTime;
    /**
     * 清单类型
     */
    private String boqdcType;
    /**
     * 暂估价
     */
    private BigDecimal boqdcProvisionalEstimate;
    /**
     * 费率
     */
    private Float boqdcRate;
    private String boqdcXh;
    private BigDecimal boqdcAqsg;
    private BigDecimal boqdcGf;
    private String boqdcJsjc;
    private BigDecimal boqdcQfjs;
    private String boqdcJsgz;
    private BigDecimal boqdcRgf;
    private BigDecimal boqdcZcf;
    private BigDecimal boqdcSbf;
    private BigDecimal boqdcFcf;
    private BigDecimal boqdcClf;
    private BigDecimal boqdcJxf;
    private BigDecimal boqdcGlf;
    private BigDecimal boqdcLr;
    private BigDecimal boqdcZgr;
    private BigDecimal boqdcRcjhl;
    private String boqdcRcjId;
    private BigDecimal boqdcJzDj;
    private BigDecimal boqdcTbDj;
    private BigDecimal boqdcFxxs;
    private String boqdcFormula;
    private Float boqdcFeesRatio;
    private Float boqdcOtherRatio;
    private Float boqdcTaxesRatio;
    private String pageType;
    /**
     * 变更前ID
     */
    private String boqdcChangePreId;
    /**
     * 上级清单名称
     */
    private String boqdcParentName;
    /**
     * 单位名称
     */
    private String cName;
    /**
     * 单位ID
     */
    private String cId;
    /**
     * 单位信用代码
     */
    private String cCreditCode;

    private String changeStatus;

    private String boqdId;
    /**
     * 下级清单集合
     */
    private List<ConBoqDtlChangeDto> childNodes;
}

