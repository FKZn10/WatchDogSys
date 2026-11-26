package com.jpmsoft.projectProducer.pojo.entity.purchase;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目采购表
 */
@TableName(value = "pro_purchase")
@Data
public class ProPurchase {

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private String id;


    /**
     * 项目类型
     */
    @TableField("project_type")
    private String projectType;

    /**
     * 采购日期
     */
    @TableField("purchase_date")
    private Date purchaseDate;

    /**
     * 项目名称
     */
    @TableField("p_name")
    @JsonProperty("pName")
    private String pName;

    @TableField("p_id")
    @JsonProperty("pId")
    private String pId;

    /**
     * 预算金额
     */
    @TableField("budget_amount")
    private BigDecimal budgetAmount;

    /**
     * 单位负责人
     */
    @TableField("unit_leader")
    private String unitLeader;

    @TableField("unit_leader_id")
    private String unitLeaderId;

    /**
     * 采购目录名称
     */
    @TableField("purchase_catalogue")
    private String purchaseCatalogue;

    /**
     * 采购目录值
     */
    @TableField("catalogue_value")
    private String catalogueValue;

    /**
     * 用户电话
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 采购需求
     */
    @TableField("purchase_demand")
    private String purchaseDemand;

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

}
