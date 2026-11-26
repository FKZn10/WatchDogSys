package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 调整事项模板清单
 */
@Data
@TableName("Inv_Adjustment_Template_Dtl")
public class InvAdjustmentTemplateDtl {

    @TableId(type = IdType.INPUT)
    private String atdId;
    private String atId;
    private String atNum;
    private String atContent;
    private String atInfo;
    private BigDecimal atApplyAmount;
    private BigDecimal atReviewAmount;
    private String atRemark;
    private Integer atOrder;
    private String atParentId;
}
