package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 决算申请清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/2/23 15:56
 */
@Data
@TableName("Inv_Final_Accounts_Apply_Dtl")
public class InvFinalAccountsApplyDtl {

    @TableId(type = IdType.INPUT)
    private String fadId;
    private String faId;
    private String fadNumber;
    private String fadParentId;
    private String fadCode;
    private String fadName;
    private String fadUnit;
    private Float fadCount;
    private BigDecimal fadPrice;
    private BigDecimal fadTotal;
    private String fadRemark;
    private Integer fadOrder;
    private Integer fadLevel;
    private Integer fadType;
    private Integer fadState;
    private String fadPath;
    private Integer fadLeafNode;
}
