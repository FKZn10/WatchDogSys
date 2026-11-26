package com.jpmsoft.projectProducer.pojo.entity.conBoq;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName("Con_Boq_Dtl")
public class ConBoqDtl {
    @TableId(type = IdType.INPUT)
    private String boqdId;
    /**
     * 项目编码
     */
    @TableField("Boqd_Code")
    private String boqdCode;

    /**
     * 项目名称
     */
    @TableField("Boqd_Name")
    private String boqdName;

    /**
     * 项目特征
     */
    @TableField("Boqd_Feature")
    private String boqdFeature;


    /**
     * 工程内容
     */
    @TableField("Boqd_Content")
    private String boqdContent;
    /**
     * 计量单位
     */
    @TableField("Boqd_Unit")
    private String boqdUnit;

    /**
     * 工程量
     */
    @TableField("Boqd_Amount")
    private Float boqdAmount;


    /**
     * 综合单价
     */
    @TableField("Boqd_Price")
    private BigDecimal boqdPrice;

    /**
     * 总价
     */
    @TableField("Boqd_Total_Price")
    private BigDecimal boqdTotalPrice;

    /**
     * 备注
     */
    @TableField("Boqd_Remark")
    private String boqdRemark;

    /**
     * 状态
     */
    @TableField("Boqd_State")
    private Integer boqdState;
    /**
     * 上级清单ID
     */
    @TableField("Boqd_Parent_Id")
    private String boqdParentId;
    /**
     * 主表ID
     */
    @TableField("Boq_Id")
    private String boqId;
    /**
     * 排序号
     */
    @TableField("Boqd_Sort")
    private Integer boqdSort;

    /**
     * 所属项目概算科目表ID
     */
    @TableField("Boqd_Bdd_Id")
    private String boqdBddId;

    /**
     * 创建人ID
     */
    @TableField("Boqd_Create_Uid")
    private String boqdCreateUid;

    /**
     * 创建人姓名
     */
    @TableField("Boqd_Create_Uname")
    private String boqdCreateUname;

    /**
     * 创建时间
     */
    @TableField(value = "Boqd_Create_Time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdCreateTime;
    /**
     * 更新时间
     */
    @TableField(value = "Boqd_Update_Time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date boqdUpdateTime;

    @TableField("Boqd_Type")
    private String boqdType;

    @TableField("Boqd_Provisional_Estimate")
    private BigDecimal boqdProvisionalEstimate;

    @TableField("Boqd_Rate")
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
    private String pageType;
    /**
     * 变更前ID
     */
    private String boqdChangePreId;

    @TableField(exist = false)
    private String cName;
    @TableField(exist = false)
    private String cId;
    @TableField(exist = false)
    private String cCreditCode;

    @TableField(exist = false)
    private List<ConBoqDtl> childNodes;


}
