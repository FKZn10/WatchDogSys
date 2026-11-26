package com.jpmsoft.projectProducer.pojo.dto.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ProPurchaseDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 4963253254314264796L;

    private String id;

    private String projectType; // 项目类别
    @JsonFormat(pattern = "yyyy-MM")
    private Date purchaseDate;

    @JsonProperty("pId")
    private String pId; // 项目名称
    @JsonProperty("pName")
    private String pName; // 项目名称
    private BigDecimal budgetAmount; // 预算金额

    private String unitLeaderId;
    private String unitLeader; // 单位负责人
    private String purchaseCatalogue; // 采购目录名称

    private String CatalogueValue;   // 采购目录值
    private String userPhone; // 我的联系方式
    private String purchaseDemand; // 采购需求

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

    // 筛选字段 时间
    @JsonFormat(pattern = "yyyy-MM")
    private Date filerTime;

}
