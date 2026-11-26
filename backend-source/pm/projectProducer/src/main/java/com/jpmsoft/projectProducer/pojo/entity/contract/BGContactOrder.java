package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付申请
 */
@Data
@TableName("BG_ContactOrder")
public class BGContactOrder {
    @TableId(type = IdType.INPUT)
    private String id;
    private String coCode;
    @TableField("CO_GLId")
    private String coGLId;
    @TableField("CO_GLCode")
    private String coGLCode;
    @TableField("CO_GLMoney")
    private BigDecimal coGLMoney;
    @TableField("CO_GLBL")
    private BigDecimal coGLBL;
    @TableField("CO_PId")
    private String coPId;
    @TableField("CO_ConId")
    private String coConId;
    private Date coDate;
    @TableField("CO_ConCode")
    private String coConCode;
    private BigDecimal coMoney;
    @TableField("CO_ISCheck")
    private String coISCheck;
    @TableField("CO_BL")
    private BigDecimal coBL;
    @TableField("CO_ConMoney")
    private BigDecimal coConMoney;
    private String coType;
    @TableField("CO_TureMoney")
    private BigDecimal coTureMoney;
    @TableField("CO_GLType")
    private String coGLType;


    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;

    private BigDecimal finalConfirmMoney;
}
