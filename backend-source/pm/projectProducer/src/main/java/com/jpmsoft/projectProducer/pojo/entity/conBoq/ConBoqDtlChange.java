package com.jpmsoft.projectProducer.pojo.entity.conBoq;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同工程量清单变更子表
 */
@Data
@Accessors(chain = true)
@TableName("Con_Boq_Dtl_Change")
public class ConBoqDtlChange {
    @TableId(type = IdType.INPUT)
    private String boqdcId;
    /**
     * 项目编码
     */
    @TableField("Boqdc_Code")
    private String boqdcCode;

    /**
     * 项目名称
     */
    @TableField("Boqdc_Name")
    private String boqdcName;

    /**
     * 项目特征
     */
    @TableField("Boqdc_Feature")
    private String boqdcFeature;


    /**
     * 工程内容
     */
    @TableField("Boqdc_Content")
    private String boqdcContent;
    /**
     * 计量单位
     */
    @TableField("Boqdc_Unit")
    private String boqdcUnit;

    /**
     * 工程量
     */
    @TableField("Boqdc_Amount")
    private Float boqdcAmount;


    /**
     * 综合单价
     */
    @TableField("Boqdc_Price")
    private BigDecimal boqdcPrice;

    /**
     * 总价
     */
    @TableField("Boqdc_Total_Price")
    private BigDecimal boqdcTotalPrice;

    /**
     * 备注
     */
    @TableField("Boqdc_Remark")
    private String boqdcRemark;

    /**
     * 状态
     */
    @TableField("Boqdc_State")
    private Integer boqdcState;
    /**
     * 上级清单ID
     */
    @TableField("Boqdc_Parent_Id")
    private String boqdcParentId;
    /**
     * 主表ID
     */
    @TableField("Boqc_Id")
    private String boqcId;
    /**
     * 排序号
     */
    @TableField("Boqdc_Sort")
    private Integer boqdcSort;

    /**
     * 所属项目概算科目表ID
     */
    @TableField("Boqdc_Bdd_Id")
    private String boqdcBddId;

    /**
     * 创建人ID
     */
    @TableField("Boqdc_Create_Uid")
    private String boqdcCreateUid;

    /**
     * 创建人姓名
     */
    @TableField("Boqdc_Create_Uname")
    private String boqdcCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Boqdc_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdcCreateTime;
    /**
     * 更新时间
     */
    @TableField(value = "Boqdc_Update_Time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdcUpdateTime;

    @TableField("Boqdc_Type")
    private String boqdcType;

    @TableField("Boqdc_Provisional_Estimate")
    private BigDecimal boqdcProvisionalEstimate;

    @TableField("Boqdc_Rate")
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

    @TableField(exist = false)
    private String cName;
    @TableField(exist = false)
    private String cId;
    @TableField(exist = false)
    private String cCreditCode;

    @TableField(exist = false)
    private List<ConBoqDtlChange> childNodes;

    private String changeStatus;
    private String boqdId;
}

