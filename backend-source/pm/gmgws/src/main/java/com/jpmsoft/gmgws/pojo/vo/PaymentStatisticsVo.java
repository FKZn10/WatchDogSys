package com.jpmsoft.gmgws.pojo.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.jpmsoft.gmgws.annotations.ExcelValid;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 *基建支付统计导入vo类
 */
@Data
public class PaymentStatisticsVo  implements Serializable {


    private static final long serialVersionUID = 441519539834897796L;

    @ExcelValid(message = "项目名称必填")
    @ExcelProperty(index=0,value="项目名称*")
    private String entryName;

    @ExcelValid(message = "合同名称必填")
    @ExcelProperty(index=1,value="合同名称*")
    private String contractName;

//    @ExcelProperty(index=2,value="乙方单位")
//    private String partyCompany;
//
//    @ExcelProperty(index=3,value="合同序号")
//    private String contractId;

    @ExcelValid(message = "审批金额必填")
    @ExcelProperty(index=2,value="审批金额*")
    private BigDecimal approvalAmt;

    @ExcelValid(message = "支付金额必填")
    @ExcelProperty(index=3,value="支付金额*")
    private BigDecimal paymentAmt;

    @ExcelValid(message = "付款单位必填")
    @ExcelProperty(index=4,value="付款单位*")
    private String paymentCompany;

    @ExcelProperty(index=5,value="代建支付类型")
    private String paymentType;

    @ExcelValid(message = "支付年度必填")
    @ExcelProperty(index=6,value="支付年度*")
    private String paymentYear;

    @ExcelProperty(index=7,value="备注")
    private String mrk;

    @ExcelValid(message = "创建人必填")
    @ExcelProperty(index=8,value="创建人*")
    private String createPeople;


}
