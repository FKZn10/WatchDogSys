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
@TableName("BG_Declaration")
public class BGDeclaration {

    @TableId(type = IdType.INPUT)
    private String id;

    @TableField("D_TCPartId")
    private String dTCPartId;
    private String dCode;
    @TableField("D_PId")
    private String dPId;
    @TableField("D_JBRId")
    private String dJBRId;
    @TableField("D_JBRName")
    private String dJBRName;
    @TableField("D_CId")
    private String dCId;
    private String dContent;
    private String dDescription;
    @TableField("D_DescriptionType")
    private String dDescriptionType;
    private String dDepartment;
    private BigDecimal dMoney;
    @TableField("D_MoneyBL")
    private BigDecimal dMoneyBL;
    @TableField("D_GLMoney")
    private BigDecimal dGLMoney;
    @TableField("D_GLMoneyBL")
    private BigDecimal dGLMoneyBL;
    @TableField("D_ISPassMoney")
    private String dISPassMoney;
    @TableField("D_ISPassBL")
    private String dISPassBL;
    @TableField("D_ISCheck")
    private String dISCheck;
    @TableField("D_BL")
    private BigDecimal dBL;
    @TableField("D_CMoney")
    private BigDecimal dCMoney;
    @TableField("D_CCode")
    private String dCCode;

    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;

    // 最终确认金额
    private BigDecimal finalConfirmMoney;
}
