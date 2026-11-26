package com.jpmsoft.gmgws.pojo.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.gmgws.annotations.ExcelValid;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("PAYMENT_STATISTICS")
public class PaymentStatistics  {


    @TableId(type = IdType.INPUT)
    private  String id;


    private Integer State;


    private Date StateTime;


    private String createUid;


    private String createUname;

    private String pId;

    private String conId;

    private String conBId;

    private String conCode;

    private BigDecimal psApprovalAmt;

    private BigDecimal psPaymentAmt;

    private String psPaymentCompany;

    private String psPaymentType;

    private String psPaymentYear;

    private String psRemark;

    private String psRemark1;
    private String psRemark2;
    private String psRemark3;
    private String psRemark4;

    private Date createTime;


}
