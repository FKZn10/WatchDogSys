package com.jpmsoft.projectProducer.pojo.entity.investment;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 决算申请
 */
@Data
@TableName("Inv_Final_Accounts_Apply")
public class InvFinalAccountsApply {
    @TableId(type = IdType.INPUT)
    private String faId;
    private String pId;
    private BigDecimal faTotalContract;
    private BigDecimal faTotalApproved;
    private BigDecimal faSubmitCost;
    private String faRemark;
    private String faCreateUid;
    private String faCreateUname;
    private Date faCreateTime;
    private Integer faState;
    private Date faStateTime;
    private BigDecimal faApprovedAmt;
    private Date faApprovedTime;

    private String faApprovalTitle;
    private String faApprovalNum;
    private Date faApprovalTime;
    private String faApprovalFile;
    private String faApprovalUnit;
    private String teId;

    private String conId;
}
