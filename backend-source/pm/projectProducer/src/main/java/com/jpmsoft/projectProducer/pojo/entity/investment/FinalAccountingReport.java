package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//决算行文上报
@Data
@TableName("Final_Accounting_Report")
public class FinalAccountingReport {
    @TableId(type = IdType.INPUT)
    private String id;

    private String pId;

    private String pName;

    private BigDecimal approvedBudgetAmount;  //概算批复金额

    private BigDecimal amountSubmitted;  //送审金额

    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private Integer status;

}
