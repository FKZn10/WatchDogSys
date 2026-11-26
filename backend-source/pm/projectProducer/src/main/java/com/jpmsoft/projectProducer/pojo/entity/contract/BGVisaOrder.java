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
@TableName("BG_VisaOrder")
public class BGVisaOrder {

    @TableId(type = IdType.INPUT)
    private String id;

    @TableField("VO_SGUnitID")
    private String voSGUnitID;
    @TableField("VO_Code")
    private String voCode;
    @TableField("VO_Type")
    private String voType;
    @TableField("VO_GLID")
    private String voGLID;
    @TableField("VO_GLCode")
    private String voGLCode;
    @TableField("VO_GLMoney")
    private BigDecimal voGLMoney;
    @TableField("VO_GLBL")
    private BigDecimal voGLBL;
    @TableField("VO_Content")
    private String voContent;
    @TableField("VO_GLTureMoney")
    private BigDecimal voGLTureMoney;
    @TableField("VO_Money")
    private BigDecimal voMoney;
    @TableField("VO_GLPName")
    private String voGLPName;
    @TableField("VO_GLConName")
    private String voGLConName;

    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;

    @TableField("vo_glconid")
    private String contractId;

    @TableField("vo_glpid")
    private String projectId;

    // 最终确认金额
    private BigDecimal finalConfirmMoney;

}
