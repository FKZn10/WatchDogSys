package com.jpmsoft.common.model.request.InvestmentControl;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 决算申请清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/2/23 15:56
 */
@Data
public class InvFinalAccountsApplyDtlDto implements Serializable {


    private static final long serialVersionUID = 8245826104072184876L;
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


    private List<InvFinalAccountsApplyDtlDto> childNodes;
}
