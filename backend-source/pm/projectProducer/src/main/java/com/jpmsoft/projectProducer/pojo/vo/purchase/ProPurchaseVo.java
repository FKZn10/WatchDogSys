package com.jpmsoft.projectProducer.pojo.vo.purchase;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProPurchaseVo implements Serializable {

    private static final long serialVersionUID = -8627456872024552L;

    private String id;

    private String projectType; // 项目类别
    private String purchaseDate; // 预计采购时间
    @JsonProperty("pName")
    private String pName; // 项目名称
    private String pId;
    private BigDecimal budgetAmount; // 预算金额
    private String unitLeader; // 单位负责人
    private String unitLeaderId;
    private String purchaseCatalogue; // 采购目录
    private String userPhone; // 我的联系方式
    private String purchaseDemand; // 采购需求

    private String catalogueValue;

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

    private String fileRelationId;
}
