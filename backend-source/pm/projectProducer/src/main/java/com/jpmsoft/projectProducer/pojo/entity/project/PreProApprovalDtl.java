package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目注册清单-匡算清单子表
 */
@Data
@TableName("Pre_Pro_Approval_Dtl")
public class PreProApprovalDtl {
    @TableId(type = IdType.INPUT)
    private String padId;
    private String padNumber;
    private String padFid;
    private String padParenetId;
    private String padCode;
    private String padName;
    private String padUnit;
    private Float padCount;
    private BigDecimal padPrice;
    private BigDecimal padTotal;
    private String padRemark;
    private Integer padOrder;
    private Integer padLevel;
    private Integer padType;
    private Integer padState;
    private String padPath;
    private Integer padLeafNode;
}
